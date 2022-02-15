package lgbt.cray.app.rekognize.dao;

import java.util.List;

import lgbt.cray.app.rekognize.config.FaceAnalysis;

public interface FaceAnalysisDao {
	void saveFaceAnalysis(FaceAnalysis fa);
	List<FaceAnalysis> getAllFaceAnalysis();
	void deleteFaceAnalysisById(Integer id);
	FaceAnalysis findById(Integer id);
	void updateFaceAnalysis(FaceAnalysis fa);
}
