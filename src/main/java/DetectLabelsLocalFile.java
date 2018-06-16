

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class DetectLabelsLocalFile {
    public static void main(String[] args) throws Exception {
    	String photo="./src/main/resources/id-check-drivers-licence.png";


        ByteBuffer imageBytes;
        try (InputStream inputStream = new FileInputStream(new File(photo))) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }


        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image()
                        .withBytes(imageBytes))
                .withMaxLabels(10)
                .withMinConfidence(77F);


        try {

            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List <Label> labels = result.getLabels();

            System.out.println("Detected labels for " + photo);
            for (Label label: labels) {
               System.out.println(label.getName() + ": " + label.getConfidence().toString());
            }

        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }




        DetectTextRequest textRequest = new DetectTextRequest()
                .withImage(new Image()
                        .withBytes(imageBytes));

        try {
            DetectTextResult result = rekognitionClient.detectText(textRequest);
            List<TextDetection> textDetections = result.getTextDetections();

            System.out.println("Detected lines and words for " + photo);
//         for (TextDetection text: textDetections) {
//
//                 System.out.println("Detected: " + text.getDetectedText());
//                 System.out.println("Confidence: " + text.getConfidence().toString());
//                 System.out.println("Id : " + text.getId());
//                 System.out.println("Parent Id: " + text.getParentId());
//                 System.out.println("Type: " + text.getType());
//                 System.out.println();
//         }


            for(TextDetection text: textDetections){
                System.err.println(text.getDetectedText());
            }
        } catch(AmazonRekognitionException e) {
            e.printStackTrace();
        }
    }
}