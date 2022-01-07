package lgbt.cray.app.rekognize.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.Attribute;
import software.amazon.awssdk.services.rekognition.model.DetectFacesRequest;
import software.amazon.awssdk.services.rekognition.model.DetectFacesResponse;
import software.amazon.awssdk.services.rekognition.model.FaceDetail;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.S3Object;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public class Rekognition {
	// TODO: Add permission checks
	// TODO: Add AWS Region Selection
	
	public static List<FaceDetail> face(File file, String region) throws Exception {
		String AWSRekognitionJSONResult = "{}";
		List<FaceDetail> result = null;
		try {
			System.out.println("Image: " + file.getAbsolutePath());
			String S3Key = uploadToS3(region, file.getAbsolutePath(), file.getName());
			System.out.println("S3 Upload Result: " + S3Key);
			S3Object s3o = S3Object.builder()
					.bucket("rekognize-app")
					.name(S3Key)
					.build();
			Image s3Image = Image.builder()
					.s3Object(s3o)
					.build();
			DetectFacesRequest faceReq = DetectFacesRequest.builder()
					.attributes(Attribute.ALL)
					.image(s3Image)
					.build();
			 RekognitionClient rekClient = RekognitionClient.builder()
		                .region(Region.US_EAST_1)
		                .build();
			 DetectFacesResponse facesResp = rekClient.detectFaces(faceReq);
			result = facesResp.faceDetails();
			deleteFromS3(region, S3Key);
//			tempImageFile.delete();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = null;
			e.printStackTrace();
		}
//		FaceDetails result = new ObjectMapper().readValue(AWSRekognitionJSONResult, FaceDetails.class);
		
		return result;
	}
	
	public static void deleteFromS3(String region, String key) {
		
	}
	/**
	 * Uploads file to S3 and returns a string of the object ID in S3 of the given file
	 * @param bucket       name of the S3 bucket
	 * @param regionString name of AWS region
	 * @param imagePath	   full file-path of image to upload
	 * @return             S3 Object Key (filename)
	 * @throws Exception Throws exception if an error occurs with the S3 upload or there is a file error ( >15MB or non-existent)
	 */
	public static String uploadToS3(String regionString, String imagePath, String filename) throws Exception {
		// take input image and upload to S3
		
		// Generate UUID for image
		String S3Key = UUID.randomUUID().toString();
		
		// Verify File Exists & is < 15MB
		File analysisTarget = new File(imagePath);
		if (!analysisTarget.exists()) {
			throw new Exception("File not found.");
		}
		if (Files.size(Paths.get(imagePath)) >= 15728640) {
			// File too large
			throw new Exception("File too large");
		}
		
		// Verify destination bucket exists, create if not
		Region region = Region.US_EAST_1;
		S3Client s3 = S3Client.builder()
				.region(region)
				.build();
		Boolean bucketExists = false;
		for(Bucket b : s3.listBuckets().buckets()) {
			bucketExists = bucketExists || (b.name() == "rekognize-app");
		}
		if (!bucketExists) {
			// create new bucket
			CreateBucketRequest cbr = CreateBucketRequest.builder()
					.bucket("rekognize-app")
					.build();
			s3.createBucket(cbr);
		}
		
		// Upload file to bucket with UUID as Key
		PutObjectRequest putRequest = PutObjectRequest.builder()
				.bucket("rekognize-app")
				.key(S3Key+filename)
				.build();
		PutObjectResponse putObjectResponse = s3.putObject(putRequest, RequestBody.fromFile(Paths.get(imagePath)));
		System.out.println("S3 Put Tag: " + putObjectResponse.eTag());
		// Throw any errors, or return S3 Key (UUID)
		return S3Key+filename;
	}
	
	public static File mpfToF(MultipartFile file) throws IOException{    
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile(); 
		FileOutputStream fos = new FileOutputStream(convFile); 
		fos.write(file.getBytes());
		fos.close(); 
		return convFile;
	}
}
