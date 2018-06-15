import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.AmazonRekognitionException;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.TextDetection;
import java.util.List;



public class TextDetectionInImage {

   public static void main(String[] args) throws Exception {


      String photo = "id-check-drivers-licence.png";
      String bucket = "fv-rekognition-bucket";

      AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();



      DetectTextRequest request = new DetectTextRequest()
              .withImage(new Image()
              .withS3Object(new S3Object()
              .withName(photo)
              .withBucket(bucket)));


      try {
         DetectTextResult result = rekognitionClient.detectText(request);
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