package sienimetsa.sienimetsa_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import sienimetsa.sienimetsa_backend.jwt.JwtUtil;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtUtil jwtUtil;

    public WebSocketConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register the WebSocket endpoint '/ws/chat' and allow all origins (for testing
        // purposes)
        registry.addEndpoint("/ws/chat")
                .setAllowedOriginPatterns("*") // change to actual url when deployed
                .addInterceptors(new HandshakeInterceptor() {
                    @Override
                    public boolean beforeHandshake(
                            org.springframework.http.server.ServerHttpRequest request,
                            org.springframework.http.server.ServerHttpResponse response,
                            org.springframework.web.socket.WebSocketHandler wsHandler,
                            Map<String, Object> attributes) throws Exception {

                        // Log attempt to establish a WebSocket connection
                        System.out.println("Attempting WebSocket handshake...");
                        System.out.println("Headers: " + request.getHeaders());

                        // Extract token from query parameters of the WebSocket connection request
                        String query = request.getURI().getQuery();
                        String token = null;
                        if (query != null) {
                            // Parse the query string to extract the token
                            for (String param : query.split("&")) {
                                if (param.startsWith("token=")) {
                                    token = param.substring(6); // Extract the value after "token="
                                    break;
                                }
                            }
                        }

                        // Validate the token if it is present
                        if (token != null) {
                            System.out.println("Extracted token: " + token);
                            try {
                                if (jwtUtil.validateJwtToken(token)) {
                                    String email = jwtUtil.extractEmail(token);
                                    Long u_id = jwtUtil.extractUId(token);

                                    // Add user information to WebSocket session attributes for further use
                                    attributes.put("username", email);
                                    attributes.put("userId", u_id);

                                    // Log successful authorization
                                    System.out.println("WebSocket connection authorized for user: " + email);
                                    return true;
                                } else {
                                    System.out.println("Invalid JWT token");
                                }
                            } catch (Exception e) {
                                System.out.println("Error validating JWT token: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Token missing in query parameters");
                        }
                        return false; // Reject the connection if the token is invalid or missing
                    }

                    @Override
                    public void afterHandshake(
                            org.springframework.http.server.ServerHttpRequest request,
                            org.springframework.http.server.ServerHttpResponse response,
                            org.springframework.web.socket.WebSocketHandler wsHandler,
                            Exception exception) {
                    }
                });

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
}
