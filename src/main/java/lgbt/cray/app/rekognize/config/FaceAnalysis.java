package lgbt.cray.app.rekognize.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.rekognition.model.Emotion;
import software.amazon.awssdk.services.rekognition.model.FaceDetail;


@Entity
@Table(name="Faces")
public class FaceAnalysis {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="Image")
	private byte[] Image;

	// Raw string stored in database and returned with .toString()
	@Column(name="Details")
	private String DetailsJson; 

	// Main list of details to be interacted with and used
	private ArrayList<HashMap<String, Object>> Details;
	
	// Raw input from AWS API
	private List<FaceDetail> DetailsList; 
	
	// Getters and Setters
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public byte[] getImage() {
		return this.Image;
	}
	
	public void setImage(byte[] bs) {
		this.Image = bs;
	}
	
	/**
	 * Returns a JSON string of the FaceAnalysis or null if an exception is thrown
	 * @return JSON String
	 * */
	@Override
	public String toString() {
		return this.DetailsJson;
	}

	/**
	 * Returns a list of the FaceAnalysis
	 * @return ArrayList<HashMap<String, Object> List of FaceAnalysis
	 */
	public ArrayList<HashMap<String, Object>> getList(){
		if (this.Details == null){
			this.Details = JsonToList();
		}
		return this.Details;
	}
	
	/**
	 * Set details from AWS Rekognition output
	 * @param List<FaceDetail> FaceDetailList
	 */
	public void setDetails(List<FaceDetail> FaceDetailList) {
		this.DetailsList = FaceDetailList;
		this.Details = this.AWSDetailsToList();
		this.DetailsJson = this.detailsToJson();
	}

	private String detailsToJson() {
		String stringifiedResult = "";
		try{
			stringifiedResult =  new ObjectMapper().writeValueAsString(this.Details);
		} catch(Exception e) {
			stringifiedResult = null;
		}
		return stringifiedResult;
	}

	private ArrayList<HashMap<String, Object>> JsonToList(){
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try{
			list = new ObjectMapper().readValue(this.DetailsJson, ArrayList.class);
		} catch(Exception e) {
			list = null;
		}
		return list;
	}
	
	private ArrayList<HashMap<String, Object>> AWSDetailsToList() {
		// TODO: Handle case of DetailsList being null
		ArrayList<HashMap<String, Object>> BuiltDetails = new ArrayList<HashMap<String, Object>>();
		for(FaceDetail fd : this.DetailsList) {
			// TODO: Missing Landmarks and Quality
			HashMap<String, Object> face = new HashMap<String, Object>();
			HashMap<String, Object> cache = null;
			face.put("ageRangeHigh", fd.ageRange().high());
			face.put("ageRangeLow", fd.ageRange().low());
			// Beard
			cache = new HashMap<String, Object>();
			cache.put("exists", fd.beard().value());
			cache.put("confidence", fd.beard().confidence());
			face.put("beard", cache);
			// EyeGlasses
			cache = new HashMap<String, Object>();
			cache.put("exists", fd.eyeglasses().value());
			cache.put("confidence", fd.eyeglasses().confidence());
			face.put("eyeglasses", cache);
			// Smile
			cache = new HashMap<String, Object>();
			cache.put("exists", fd.smile().value());
			cache.put("confidence", fd.smile().confidence());
			face.put("smile", cache);
			// Sunglasses
			cache = new HashMap<String, Object>();
			cache.put("exists", fd.sunglasses().value());
			cache.put("confidence", fd.sunglasses().confidence());
			face.put("sunglasses", cache);
			// Mustache
			cache = new HashMap<String, Object>();
			cache.put("exists", fd.mustache().value());
			cache.put("confidence", fd.mustache().confidence());
			face.put("mustache", cache);
			// MouthOpen
			cache = new HashMap<String, Object>();
			cache.put("exists", fd.mouthOpen().value());
			cache.put("confidence", fd.mouthOpen().confidence());
			face.put("mouthOpen", cache);
			// EyesOpen
			cache = new HashMap<String, Object>();
			cache.put("exists", fd.eyesOpen().value());
			cache.put("confidence", fd.eyesOpen().confidence());
			face.put("eyesOpen", cache);
			// Emotions
			cache = new HashMap<String, Object>();
			for( Emotion e : fd.emotions()) {
				cache.put(e.type().toString(), e.confidence());
			}
			face.put("emotions", cache);
			// Pose
			cache = new HashMap<String, Object>();
			cache.put("roll", fd.pose().roll());
			cache.put("yaw", fd.pose().yaw());
			cache.put("pitch", fd.pose().pitch());
			face.put("pose", cache);
			// Bounds
			Map<String, Float> bounds = new HashMap<String, Float>();
			bounds.put("height", fd.boundingBox().height());
			bounds.put("width", fd.boundingBox().width());
			bounds.put("top",  fd.boundingBox().top());
			bounds.put("left", fd.boundingBox().left());
			face.put("boundingBox", bounds);
			// Overall Confidence
			face.put("confidence", fd.confidence());
			
			BuiltDetails.add(face);
		}
		return BuiltDetails;
	}
	
}
