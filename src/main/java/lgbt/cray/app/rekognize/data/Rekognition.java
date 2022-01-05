package lgbt.cray.app.rekognize.data;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public class Rekognition {
	// TODO: Add permission checks
	
	public static FaceDetails face(String s3Key, String region) throws Exception {
		String AWSRekognitionJSONResult = "";
//		FaceDetails result = new ObjectMapper().readValue(AWSRekognitionJSONResult, FaceDetails.class);
		FaceDetails result = new FaceDetails();
		return result;
	}
	
	/**
	 * Uploads file to S3 and returns a string of the object ID in S3 of the given file
	 * @param bucket       name of the S3 bucket
	 * @param regionString name of AWS region
	 * @param imagePath	   full file-path of image to upload
	 * @return             S3 Object Key (filename)
	 * @throws Exception Throws exception if an error occurs with the S3 upload or there is a file error ( >15MB or non-existent)
	 */
	public String uploadToS3(String regionString, String imagePath) throws Exception {
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
				.key(S3Key)
				.build();
		PutObjectResponse putObjectResponse = s3.putObject(putRequest, RequestBody.fromFile(Paths.get(imagePath)));
		System.out.println(putObjectResponse.eTag());
		// Throw any errors, or return S3 Key (UUID)
		return S3Key;
	}
}
