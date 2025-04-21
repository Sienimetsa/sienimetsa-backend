package sienimetsa.sienimetsa_backend.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.Finding;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;
import sienimetsa.sienimetsa_backend.jwt.JwtUtil;

@RestController
@RequestMapping("/gdpr")
public class GDPRController {

    @Autowired
    private AppuserRepository appuserRepository;
    private static final Logger logger = LoggerFactory.getLogger(GDPRController.class);

    @Autowired
    private FindingRepository findingRepository;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Endpoint to generate and return a PDF of user information.
     * 
     * @param userId The ID of the user.
     * @return A PDF file containing the user's information.
     */
@GetMapping("/pdf/{userId}")
public ResponseEntity<byte[]> getUserInfoPdf(@PathVariable Long userId, HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    String token = authorizationHeader.substring(7);
    Long authenticatedUId = jwtUtil.extractUId(token);

    if (!authenticatedUId.equals(userId)) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    // Retrieve the requested user's data
    Optional<Appuser> userOptional = appuserRepository.findById(userId);
    if (userOptional.isEmpty()) {
        logger.warn("No user found with ID: {}", userId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

        Appuser user = userOptional.get();

        // Retrieve all findings for the user
        List<Finding> userFindings = findingRepository.findByAppuser(user);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Introduction section
            document.add(new Paragraph("In response to your request regarding the personal data we hold about you, "
                + "please find below the relevant information in accordance with Article 15 of the General Data Protection Regulation (GDPR)."));
            document.add(new Paragraph("\nThis document includes details about the personal data we process, the purposes of processing, "
                + "the categories of data, any data recipients, retention periods, and your rights as a data subject."));
            document.add(new Paragraph("\nPlease review the information carefully. If you have any questions "
                + "or require further clarification, feel free to contact us."));

            // User personal information section
            document.add(new Paragraph("\nUser Personal Information"));
            document.add(new Paragraph("• ID: " + user.getU_id() + " - Used for unique identification of your account within our system"));
            document.add(new Paragraph("• Username: " + user.getUsername() + " - Used for logging in and identifying you within the application"));
            document.add(new Paragraph("• Email: " + user.getEmail() + " - Used for account notifications and password recovery"));
            document.add(new Paragraph("• Phone: " + user.getPhone() + " - Collected for account security and verification purposes"));
            document.add(new Paragraph("• Country: " + user.getCountry() + " - Used for regional content customization and legal compliance"));

            // Findings section
            document.add(new Paragraph("\nUser Findings"));
            if (userFindings.isEmpty()) {
                document.add(new Paragraph("• No findings recorded."));
            } else {
                for (Finding finding : userFindings) {
                    document.add(new Paragraph("• Finding ID: " + finding.getF_Id()));
                    document.add(new Paragraph("• Mushroom: " + finding.getMushroom().getMname()));
                    document.add(new Paragraph("• Location (City): " + finding.getCity()));
                    document.add(new Paragraph("• Date/Time: " + finding.getF_time()));
                    document.add(new Paragraph("• Notes: " + (finding.getNotes() != null ? finding.getNotes() : "None")));
                    document.add(new Paragraph("• Image URL: " + (finding.getImageURL() != null ? finding.getImageURL() : "None")));
                    document.add(new Paragraph("\n"));
                }
            }

            // Categories section
            document.add(new Paragraph("Categories of Personal Data"));
            document.add(new Paragraph("We process identity data (username, user ID), contact data (email address, phone number), geographic data (country, finding locations), usage data (level, progress information), and device data for functionality purposes."));

            // Recipients section
            document.add(new Paragraph("\nData Recipients"));
            document.add(new Paragraph("Your personal data is shared with our application service providers and database hosting services. We do not sell your data to third parties."));

            // Retention section
            document.add(new Paragraph("\nRetention Periods"));
            document.add(new Paragraph("We retain your personal data for as long as your account remains active, as required by law for financial and tax purposes (typically 7 years), and as necessary to resolve disputes or enforce our agreements."));

            // Rights section
            document.add(new Paragraph("\nYour Rights as a Data Subject"));
            document.add(new Paragraph("Under GDPR, you have the right to access, correct, delete, or restrict processing of your personal data. You may also request data portability and lodge complaints with a supervisory authority."));
            document.add(new Paragraph("• Request deletion of your data (right to be forgotten)"));
            document.add(new Paragraph("• Object to processing of your data"));
            document.add(new Paragraph("To exercise these rights, please contact our data protection officer via email."));

            document.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "user-info.pdf");

            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
        } catch (DocumentException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint to delete a user's data.
     * 
     * @param userId The ID of the user to delete.
     * @return A response indicating the success or failure of the deletion.
     */
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        Optional<Appuser> userOptional = appuserRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Appuser user = userOptional.get();
        findingRepository.deleteByAppuser(user);
        appuserRepository.deleteById(userId);

        return ResponseEntity.ok("User data and related findings deleted successfully");
    }
}
