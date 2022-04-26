package parkinglot;

public class Vehicle {
	VehicleSize vehicleType;
	int vehicleNumber;
	int tokenNumber;
	boolean payment = false;

	public VehicleSize getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleSize type) {
		this.vehicleType = type;
	}

	public int getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(int vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public int getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(int tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

}
