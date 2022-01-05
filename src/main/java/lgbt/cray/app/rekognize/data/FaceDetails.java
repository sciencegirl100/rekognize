package lgbt.cray.app.rekognize.data;

public class FaceDetails extends Object {
	public BoundingBox bounds;
	public Landmark landmarks[];
	public Pose pose;
	public Quality quality;
	public Long confidence;
	
	@Override
	public String toString() {
		return "Test";
	}
}

class BoundingBox {
	public Long width;
	public Long height;
	public Long left;
	public Long top;
}

class Landmark {
	public String type;
	public Long x;
	public Long y;
}

class Pose {
	public Long roll;
	public Long yaw;
	public Long pitch;
}

class Quality {
	public Long brightness;
	public Long sharpness;
}