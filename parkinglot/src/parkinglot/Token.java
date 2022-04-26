package parkinglot;

public class Token {
	int tokenNumber;
	long entryTime;
	long exitTime;
	int vehicleNumber;
	VehicleSize VehicleType;
	int floor;

	public int getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(int vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public VehicleSize getVehicleType() {
		return VehicleType;
	}

	public void setVehicleType(VehicleSize type) {
		VehicleType = type;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(int tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public long getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(long entryTime) {
		this.entryTime = entryTime;
	}

	public long getExitTime() {
		return exitTime;
	}

	public void setExitTime(long exitTime) {
		this.exitTime = exitTime;
	}

	@Override
	public String toString() {
		return "Token [tokenNumber=" + tokenNumber + ", entryTime=" + entryTime + ", exitTime=" + exitTime
				+ ", vehicleNumber=" + vehicleNumber + ", VehicleType=" + VehicleType + ", floor=" + floor + "]";
	}

}
