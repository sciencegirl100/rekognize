package lgbt.cray.app.rekognize.configuration;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Settings {
	/* This class is for handling data storage, volatile for the moment
	 * TODO: more data validation before getting/setting
	 **/
	
	// This will get replaced with a database in the future
	
	private static Map<String, String> settings = new HashMap<String, String>();
	
	public static void setSetting(String key, String payload) {
		if(isValidKey(key)) {
			settings.put(key, payload);
		}
	}
	
	public static String getSetting(String key) {
		if(isValidKey(key)) {
			return settings.get(key);
		}else {
			return "";
		}
	}
	
	private static Boolean isValidKey(String key) {
		if(key != null && key.length() > 0) {
			return true;
		}else {
			System.err.println("Key was not provided.");
			return false;
		}
	}
	
	public static final Map<String, String> awsRegions;
	static {
		Map<String, String> tempAwsRegions = new HashMap<String, String>();
		tempAwsRegions.put("us-east-1", "US East (N. Virginia)");
		tempAwsRegions.put("us-east-2", "US East (Ohio)");
		tempAwsRegions.put("us-west-1", "US West (N. California)");
		tempAwsRegions.put("us-west-2", "US West (Oregon)");
		tempAwsRegions.put("eu-central-1", "EU (Frankfurt)");
		tempAwsRegions.put("eu-west-1", "EU (Ireland)");
		tempAwsRegions.put("eu-west-2", "EU (London)");
		tempAwsRegions.put("ap-northeast-1", "Asia Pacific (Tokyo)");
		tempAwsRegions.put("ap-northeast-2", "Asia Pacific (Seoul)");
		tempAwsRegions.put("ap-southeast-1", "Asia Pacific (Singapore)");
		tempAwsRegions.put("ap-southeast-2", "Asia Pacific (Sydney)");
		tempAwsRegions.put("ap-south-1", "Asia Pacific (Mumbai)");
		awsRegions = Collections.unmodifiableMap(tempAwsRegions);
	}
}
