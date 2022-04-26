package parkinglot;

import java.util.*;

public class TempStore {
	int token = 1;
	Map<Integer, Map<VehicleSize, List<Spots>>> emptySpots = new HashMap<>();
	List<Spots> occupied = new ArrayList<Spots>();
	Map<Integer, Token> tokenMap = new HashMap<Integer, Token>();
	Map<Integer, UserDetails> userMap = new HashMap<Integer, UserDetails>();

	public int tokennumber() {
		return ++token;
	}

	TempStore() {
		Spots spot = new Spots();
		spot.setChargingPort(true);
		spot.setSpotNumber(1);
		spot.setType(VehicleSize.Electric);
		List<Spots> list = new ArrayList<Spots>();
		list.add(spot);
		Map<VehicleSize, List<Spots>> map = new HashMap<VehicleSize, List<Spots>>();
		map.put(VehicleSize.Electric, list);
		emptySpots.put(1, map);
		for (int i = 2; i < 4; i++) {
			Spots spot1 = new Spots();
			spot1.setChargingPort(false);
			spot1.setFloor(0);
			spot1.setSpotNumber(i);
		}
	}

	public String bookSlot(int floor, VehicleSize type, Token token, boolean payment) throws Exception {

		int vehicleNumber = token.getVehicleNumber();
		if (tokenMap.get(vehicleNumber) != null) {
			throw new Exception("vehicle already parked");
		}
		nullChecker(token);
		List<Spots> spot = spotChecker(type, floor);
		for (int i = 0; i < spot.size(); i++) {
			Spots empty = spot.get(i);
			nullChecker(empty);
			empty.setTokenNumber(token.getTokenNumber());
			tokenMap.put(token.getVehicleNumber(), token);
			spot.remove(i);
			occupied.add(empty);
			return "Booked succesfully and spot number is " + empty.getSpotNumber() + " token number is "
					+ token.getTokenNumber();

		}
		if (type == VehicleSize.Electric && payment) {
			UserDetails user = new UserDetails();
			userMap.put(token.getTokenNumber(), user);
		}
		throw new Exception("spots not found");
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

	public int exitSlot(int tokenNum, int vehicleNum) throws Exception {
		Token token = tokenMap.get(vehicleNum);
		nullChecker(token);
		token.getTokenNumber();
		List<Spots> list = spotChecker(token.getVehicleType(), token.getFloor());
		for (int i = 0; i < occupied.size(); i++) {
			Spots occupy = occupied.get(i);
			nullChecker(occupy);
			if (occupy.getTokenNumber() == tokenNum) {
				list.add(occupy);
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
