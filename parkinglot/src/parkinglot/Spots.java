package parkinglot;

public class Spots {

	int spotNumber;
	VehicleSize type;
	int tokenNumber;
	boolean chargingPort;
	int floor;

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getSpotNumber() {
		return spotNumber;
	}

	public void setSpotNumber(int spotNumber) {
		this.spotNumber = spotNumber;
	}

	public VehicleSize getType() {
		return type;
	}

	public void setType(VehicleSize type) {
		this.type = type;
	}

	public int getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(int tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public boolean isChargingPort() {
		return chargingPort;
	}

	public void setChargingPort(boolean chargingPort) {
		this.chargingPort = chargingPort;
	}

	@Override
	public String toString() {
		return "Spots [spotNumber=" + spotNumber + ", type=" + type + ", tokenNumber=" + tokenNumber + ", chargingPort="
				+ chargingPort + ", floor=" + floor + "]";
	}

}
