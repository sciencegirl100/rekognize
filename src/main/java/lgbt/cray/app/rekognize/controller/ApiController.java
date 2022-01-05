package lgbt.cray.app.rekognize.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lgbt.cray.app.rekognize.data.FaceDetails;
import lgbt.cray.app.rekognize.data.Rekognition;

@Controller
public class ApiController {

	@RequestMapping(
	  value = "/api", 
	  produces = "application/json"
	)
	@ResponseBody
	public ResponseEntity<String> runRekognition(@RequestParam("image") MultipartFile file) {
		FaceDetails resp = null;
		try {
			resp = Rekognition.face("", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>(resp.toString(), HttpStatus.OK);
	}
}
