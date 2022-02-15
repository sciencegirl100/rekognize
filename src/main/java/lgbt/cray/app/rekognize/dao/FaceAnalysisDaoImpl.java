package lgbt.cray.app.rekognize.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import lgbt.cray.app.rekognize.config.FaceAnalysis;

 
@Repository("faceAnalysisDao")
public class FaceAnalysisDaoImpl extends AbstractDao implements FaceAnalysisDao{
 
    public void saveFaceAnalysis(FaceAnalysis fa) {
        persist(fa);
    }
 
    public void deleteFaceAnalysisById(Integer id) {
        Query query = getSession().createSQLQuery("delete from FaceAnalysis where id = :id");
        query.setInteger("id", id);
        query.executeUpdate();
    }
 
    public FaceAnalysis findById(Integer id){
        Criteria criteria = getSession().createCriteria(FaceAnalysis.class);
        criteria.add(Restrictions.eq("id", id));
        return (FaceAnalysis) criteria.uniqueResult();
    }
     
    public void updateFaceAnalysis(FaceAnalysis fa){
        getSession().update(fa);
    }

	@SuppressWarnings("unchecked")
	public List<FaceAnalysis> getAllFaceAnalysis() {
		Criteria criteria = getSession().createCriteria(FaceAnalysis.class);
		return (List<FaceAnalysis>) criteria.list();
	}
     
}