package lgbt.cray.app.rekognize.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
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

import lgbt.cray.app.rekognize.config.FaceAnalysis;
import lgbt.cray.app.rekognize.config.MvcConfiguration;
import lgbt.cray.app.rekognize.configuration.Settings;
import lgbt.cray.app.rekognize.data.Rekognition;
import lgbt.cray.app.rekognize.service.FaceAnalysisService;
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
		// Hibernate Persistence
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(MvcConfiguration.class);
		FaceAnalysisService service = (FaceAnalysisService) context.getBean("faceAnalysisService");
		FaceAnalysis fa = new FaceAnalysis();
		
		List<FaceDetail> faceResponse = null;
		String result = "";
		String errors = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			faceResponse = Rekognition.face(Rekognition.mpfToF(file), Settings.getSetting("awsRegion"));
			fa.setDetails(faceResponse);
			fa.setImage(file.getBytes());
			service.saveFaceAnalysis(fa);
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
				resultMap.put("name", fa.getName());
				resultMap.put("data", fa.toString());
				resultMap.put("timestamp", new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(resultMap);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@RequestMapping(
	  value = "/api/list",
	  method = RequestMethod.POST,
	  produces = "application/json"
	)
	@ResponseBody
	public ResponseEntity<String> list(ModelMap model) {
		// Hibernate Persistence
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(MvcConfiguration.class);
		FaceAnalysisService service = (FaceAnalysisService) context.getBean("faceAnalysisService");
		List<FaceAnalysis> list = service.getAllFaceAnalysis();
		String result = "";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("success", true);
			resultMap.put("data", list);
			result = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(resultMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@RequestMapping(
	  value = "/api/delete",
	  method = RequestMethod.POST,
	  produces = "application/json"
	)
	@ResponseBody
	public ResponseEntity<String> delete(@RequestParam("id") String idStr){
		Integer id = Integer.parseInt(idStr);

		// Hibernate Persistence
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(MvcConfiguration.class);
		FaceAnalysisService service = (FaceAnalysisService) context.getBean("faceAnalysisService");
		service.deleteFaceAnalysisById(id);
		return null;
	}

	@RequestMapping(
	  value = "/api/rename",
	  method = RequestMethod.POST,
	  produces = "application/json"
	)
	@ResponseBody
	public ResponseEntity<String> rename(@RequestParam("id") String idStr, @RequestParam("name") String name){
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(MvcConfiguration.class);
		FaceAnalysisService service = (FaceAnalysisService) context.getBean("faceAnalysisService");
		FaceAnalysis fa = service.findById(Integer.parseInt(idStr));
		fa.setName(name);
		service.updateFaceAnalysis(fa);
		return null;
	}
}
