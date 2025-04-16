package sienimetsa.sienimetsa_backend.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import sienimetsa.sienimetsa_backend.domain.Appuser;
import sienimetsa.sienimetsa_backend.domain.AppuserRepository;
import sienimetsa.sienimetsa_backend.domain.FindingRepository;

@RestController
@RequestMapping("/gdpr")
public class GDPRController {

    @Autowired
    private AppuserRepository appuserRepository;

    @Autowired
    private FindingRepository findingRepository;

    /**
     * Endpoint to generate and return a PDF of user information.
     * 
     * @param userId The ID of the user.
     * @return A PDF file containing the user's information.
     */
    @GetMapping("/pdf/{userId}")
    public ResponseEntity<byte[]> getUserInfoPdf(@PathVariable Long userId) {
        Optional<Appuser> userOptional = appuserRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Appuser user = userOptional.get();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            document.add(new Paragraph("User Information"));
            document.add(new Paragraph("ID: " + user.getU_id()));
            document.add(new Paragraph("Username: " + user.getUsername()));
            document.add(new Paragraph("Email: " + user.getEmail()));
            document.add(new Paragraph("Phone: " + user.getPhone()));
            document.add(new Paragraph("Country: " + user.getCountry()));
            document.add(new Paragraph("Level: " + user.getLevel()));
            document.add(new Paragraph("Progress: " + user.getProgress()));

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
