package lgbt.cray.app.rekognize.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import lgbt.cray.app.rekognize.configuration.Settings;
import lgbt.cray.app.rekognize.data.Rekognition;
import software.amazon.awssdk.services.rekognition.model.FaceDetail;

@Controller
public class ApiController {

	@RequestMapping(
	  value = "/api/face",
	  method = RequestMethod.POST,
	  produces = "application/json"
	)
	@ResponseBody
	public ResponseEntity<String> runFaceRekognition(@RequestParam("image") MultipartFile file, ModelMap model) {
		List<FaceDetail> faceResponse = null;
		String result = "";
		String errors = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			faceResponse = Rekognition.face(Rekognition.mpfToF(file), Settings.getSetting("awsRegion"));
		} catch (Exception e) {
			e.printStackTrace();
			errors += e.toString() + "\n";
		}
		try {
			if (faceResponse == null) {
				resultMap.put("success", false);
				resultMap.put("error", (errors.length() == 0)?"Result is null.":errors);
				resultMap.put("timestamp", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(resultMap);
			}else {
				resultMap.put("success", true);
				ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
				for(FaceDetail fd : faceResponse) {
					HashMap<String, Object> face = new HashMap<String, Object>();
					face.put("ageRangeHigh", fd.ageRange().high());
					face.put("ageRangeLow", fd.ageRange().low());
					face.put("hasBeard", fd.beard().confidence());
					Map<String, Float> bounds = new HashMap<String, Float>();
					bounds.put("height", fd.boundingBox().height());
					bounds.put("width", fd.boundingBox().width());
					bounds.put("top",  fd.boundingBox().top());
					bounds.put("left", fd.boundingBox().left());
					// TODO: add remaining features
					face.put("boundingBox", bounds);
					data.add(face);
				}
				resultMap.put("data", data);
				resultMap.put("timestamp", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(resultMap);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@RequestMapping(
	  value = "/api/test",
	  method = RequestMethod.POST,
	  produces = "application/json"
	)
	@ResponseBody
	public ResponseEntity<String> test(@RequestParam("image") MultipartFile file, ModelMap model) {
		File upl = null;
		String result = "";
		String errors = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			// faceResponse = Rekognition.face(file.getBytes(), file.getOriginalFilename(), Settings.getSetting("awsRegion"));
			upl = Rekognition.mpfToF(file);
			Rekognition.uploadToS3("", upl.getAbsolutePath(), "test.jpg");
		} catch (Exception e) {
			e.printStackTrace();
			errors += e.toString() + "\n";
		}
		try {
			if (upl == null) {
				resultMap.put("success", false);
				resultMap.put("error", (errors.length() == 0)?"Result is null.":errors);
				resultMap.put("timestamp", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				result = new ObjectMapper().writeValueAsString(resultMap);
			}else {
				resultMap.put("success", true);
				resultMap.put("data", upl.getAbsolutePath());
				resultMap.put("timestamp", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				result = new ObjectMapper().writeValueAsString(resultMap);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
