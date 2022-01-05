package lgbt.cray.app.rekognize.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import lgbt.cray.app.rekognize.data.FaceDetails;
import lgbt.cray.app.rekognize.data.Rekognition;

@Controller
public class ApiController {

	@RequestMapping(
	  value = "/api",
	  method = RequestMethod.POST,
	  produces = "application/json"
	)
	@ResponseBody
	public ResponseEntity<String> runRekognition(@RequestParam("image") MultipartFile file) {
		FaceDetails faceResponse = null;
		String result = "";
		String errors = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			faceResponse = Rekognition.face("", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errors += e.toString() + "\n";
		}
		try {
			if (faceResponse == null) {
				resultMap.put("success", false);
				resultMap.put("error", errors);
				resultMap.put("timestamp", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				result = new ObjectMapper().writeValueAsString(resultMap);
			}else {
				resultMap.put("success", true);
				resultMap.put("data", faceResponse);
				resultMap.put("timestamp", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				result = new ObjectMapper().writeValueAsString(resultMap);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
