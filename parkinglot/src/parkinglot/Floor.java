package parkinglot;

public class Floor {

	int totalSpace = 10;
	int compactSize = 4;
	int electricSpot = 2;
	int largeSize = 1;
	int motorCycle = 2;
	int handicap = 1;

	public int getHandicap() {
		return handicap;
	}

	public void setHandicap(int handicap) {
		this.handicap = handicap;
	}

	public int getTotalSpace() {
		return totalSpace;
	}

	public void setTotalSpace(int totalSpace) {
		this.totalSpace = totalSpace;
	}

	public int getCompactSize() {
		return compactSize;
	}

	public void setCompactSize(int compackSize) {
		this.compactSize = compackSize;
	}

	public int getElectricSpot() {
		return electricSpot;
	}

	public void setElectricSpot(int electricSpot) {
		this.electricSpot = electricSpot;
	}

	public int getLargeSize() {
		return largeSize;
	}

	public void setLargeSize(int largeSize) {
		this.largeSize = largeSize;
	}

	public int getMotorCycle() {
		return motorCycle;
	}

	public void setMotorCycle(int motorCycle) {
		this.motorCycle = motorCycle;
	}

	@Override
	public String toString() {
		return " Compact = " + compactSize + ", Electric = " + electricSpot + ", large = " + largeSize
				+ ", Motor Cycle = " + motorCycle + ", Handicap = " + handicap;
	}

}
