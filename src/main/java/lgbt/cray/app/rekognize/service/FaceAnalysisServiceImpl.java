package lgbt.cray.app.rekognize.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lgbt.cray.app.rekognize.config.FaceAnalysis;
import lgbt.cray.app.rekognize.dao.FaceAnalysisDao;

public class FaceAnalysisServiceImpl  implements FaceAnalysisService{

	@Autowired
	private FaceAnalysisDao dao;
	
	@Override
	public void saveFaceAnalysis(FaceAnalysis fa) {
		dao.saveFaceAnalysis(fa);
	}

	@Override
	public List<FaceAnalysis> getAllFaceAnalysis() {
		return dao.getAllFaceAnalysis();
	}

	@Override
	public void deleteFaceAnalysisById(Integer id) {
		dao.deleteFaceAnalysisById(id);
	}

	@Override
	public FaceAnalysis findById(Integer id) {
		return dao.findById(id);
	}

	@Override
	public void updateFaceAnalysis(FaceAnalysis fa) {
		dao.updateFaceAnalysis(fa);
	}

}
