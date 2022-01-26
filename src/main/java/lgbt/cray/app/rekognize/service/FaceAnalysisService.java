package lgbt.cray.app.rekognize.service;

import java.util.List;

import lgbt.cray.app.rekognize.config.FaceAnalysis;

public interface FaceAnalysisService {
	void saveFaceAnalysis(FaceAnalysis fa);
	List<FaceAnalysis> getAllFaceAnalysis();
	void deleteFaceAnalysisById(Integer id);
	FaceAnalysis findById(Integer id);
	void updateFaceAnalysis(FaceAnalysis fa);
}
