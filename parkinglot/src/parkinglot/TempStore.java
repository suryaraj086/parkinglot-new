package parkinglot;

import java.util.*;

public class TempStore {
	int token = 1;
	int userId = 1;
	Map<Integer, Map<VehicleSize, List<Spots>>> emptySpots = new HashMap<>();
	Map<Integer, Map<VehicleSize, List<Spots>>> occupied = new HashMap<Integer, Map<VehicleSize, List<Spots>>>();
	Map<Integer, Token> tokenMap = new HashMap<Integer, Token>();
	Map<Integer, UserDetails> userMap = new HashMap<Integer, UserDetails>();
	Map<Integer, UserDetails> existingCustomer = new HashMap<Integer, UserDetails>();

	public int tokenNumber() {
		return ++token;
	}

	public int userNumber() {
		return ++token;
	}

	TempStore() {
		int floor = 3;
		int spots = 3;
		int k = 0;
		for (int i = 1; i <= floor; i++) {

			Map<VehicleSize, List<Spots>> tempMap = emptySpots.get(i);
			if (tempMap == null) {
				tempMap = new HashMap<VehicleSize, List<Spots>>();
				occupied.put(floor, tempMap);
			}
			List<Spots> list = new ArrayList<Spots>();
			for (int j = 1; j <= spots; j++) {
				Spots spot = new Spots();
				spot.setChargingPort(true);
				spot.setSpotNumber(k++);
				spot.setType(VehicleSize.Electric);
				list.add(spot);
			}
			tempMap.put(VehicleSize.Electric, list);
			list = new ArrayList<Spots>();
			for (int j = 1; j <= spots; j++) {
				Spots spot = new Spots();
				spot.setChargingPort(false);
				spot.setSpotNumber(k++);
				spot.setType(VehicleSize.Compact);
				list.add(spot);
			}
			tempMap.put(VehicleSize.Compact, list);
			list = new ArrayList<Spots>();
			for (int j = 1; j <= spots; j++) {
				Spots spot = new Spots();
				spot.setChargingPort(false);
				spot.setSpotNumber(k++);
				spot.setType(VehicleSize.Motorcycle);
				list.add(spot);
			}
			tempMap.put(VehicleSize.Motorcycle, list);
			emptySpots.put(i, tempMap);
		}
	}

	public void floorCreator(int floor, int spots) {
		int k = 0;
		for (int i = 1; i <= floor; i++) {

			Map<VehicleSize, List<Spots>> tempMap = emptySpots.get(i);
			if (tempMap == null) {
				tempMap = new HashMap<VehicleSize, List<Spots>>();
				occupied.put(floor, tempMap);
			}
			List<Spots> list = new ArrayList<Spots>();
			for (int j = 1; j <= spots; j++) {
				Spots spot = new Spots();
				spot.setChargingPort(true);
				spot.setSpotNumber(k++);
				spot.setType(VehicleSize.Electric);
				list.add(spot);
			}
			tempMap.put(VehicleSize.Electric, list);
			list = new ArrayList<Spots>();
			for (int j = 1; j <= spots; j++) {
				Spots spot = new Spots();
				spot.setChargingPort(false);
				spot.setSpotNumber(k++);
				spot.setType(VehicleSize.Compact);
				list.add(spot);
			}
			tempMap.put(VehicleSize.Compact, list);
			list = new ArrayList<Spots>();
			for (int j = 1; j <= spots; j++) {
				Spots spot = new Spots();
				spot.setChargingPort(false);
				spot.setSpotNumber(k++);
				spot.setType(VehicleSize.Motorcycle);
				list.add(spot);
			}
			tempMap.put(VehicleSize.Motorcycle, list);
			emptySpots.put(i, tempMap);
		}
	}

//booking
	public String bookSlot(int floor, VehicleSize type, Token token, boolean payment) throws Exception {

		int vehicleNumber = token.getVehicleNumber();
		if (tokenMap.get(vehicleNumber) != null) {
			throw new Exception("vehicle already parked");
		}
		nullChecker(token);
		if (type == VehicleSize.Electric && payment) {
			UserDetails user = new UserDetails();
			userMap.put(token.getTokenNumber(), user);
		}
		List<Spots> spot = spotChecker(type, floor);
		Spots empty = spot.get(0);
		nullChecker(empty);
		empty.setTokenNumber(token.getTokenNumber());
		tokenMap.put(token.getVehicleNumber(), token);
		spot.remove(0);
		List<Spots> occupy = occupyChecker(type, floor);
		if (occupy == null) {
			occupy = new ArrayList<Spots>();
		}
		occupy.add(empty);
		Map<VehicleSize, List<Spots>> tempMap = occupied.get(floor);
		tempMap.put(type, occupy);
		occupied.put(floor, tempMap);
		return "Booked succesfully and spot number is " + empty.getSpotNumber() + " token number is "
				+ token.getTokenNumber();
	}

	public List<Spots> spotChecker(VehicleSize type, int floor) throws Exception {
		Map<VehicleSize, List<Spots>> tempMap = emptySpots.get(floor);
		if (tempMap == null) {
			throw new Exception("floor not found");
		}
		List<Spots> spot = tempMap.get(type);
		if (spot == null) {
			throw new Exception("fully occupied");
		}
		return spot;
	}

	public List<Spots> occupyChecker(VehicleSize type, int floor) throws Exception {
		Map<VehicleSize, List<Spots>> tempMap = occupied.get(floor);
		if (tempMap == null) {
			tempMap = new HashMap<VehicleSize, List<Spots>>();
			occupied.put(floor, tempMap);
		}
		List<Spots> spot = tempMap.get(type);
		return spot;
	}

	// exit
	public int exitSlot(int tokenNum, int vehicleNum) throws Exception {
		Token token = tokenMap.get(vehicleNum);
		nullChecker(token);
		if (tokenNum != token.getTokenNumber()) {
			throw new Exception("failed");
		}
		List<Spots> list = spotChecker(token.getVehicleType(), token.getFloor());

		List<Spots> occupy = occupyChecker(token.getVehicleType(), token.getFloor());
		if (occupy == null) {
			throw new Exception("not found");
		}
		for (int i = 0; i < occupy.size(); i++) {
			Spots occupyObj = occupy.get(i);
			if (occupyObj.getTokenNumber() == tokenNum) {
				list.add(occupyObj);
				occupied.remove(i);
			}
		}
		tokenMap.remove(vehicleNum);
		long entry = token.getEntryTime();
		long exit = System.currentTimeMillis();
		long difference = entry - exit;
		return paymentCalculator(difference / 1000);
	}

	public void nullChecker(Object value) throws Exception {
		if (value == null) {
			throw new Exception("failed");
		}
	}

	public String onlinePortalPayment(UserDetails user, int tokenNum) {
		userMap.put(tokenNum, user);
		return "Payment Succesful";
	}

	// amount calculator
	public int payment(int vehicleNum, int token1) throws Exception {
		Token token = tokenMap.get(vehicleNum);
		nullChecker(token);
		long entry = token.getEntryTime();
		long exit = System.currentTimeMillis();
		long difference = entry - exit;
		return paymentCalculator(difference / 1000);
	}

	public int paymentCalculator(long time) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		int price = 4;
		map.put(10, 3);
		map.put(20, 2);
		for (int i = 1; i <= time; i++) {
			if (map.get(i) != null) {
				price += map.get(i);
			}
		}
		return price;
	}

	public int walletAmountChecker(int tokenNum) throws Exception {
		if (userMap.get(tokenNum) == null) {
			throw new Exception("user not found");
		}
		return userMap.get(tokenNum).getAmount();
	}

	public String emptySlots(VehicleSize type) {
		String out = "";
		for (int i = 0; i < emptySpots.size(); i++) {
			out += "floor " + (i + 1) + " free spots " + emptySpots.get(i + 1).get(type).size();
			out += "\n";
		}
		return out;
	}

	public boolean checkPaidStatus(int token) {
		if (userMap.get(token) != null) {
			return true;
		}
		return false;
	}

}
