package parkinglot;

public class ObjectGenerator {

	public static Payment paymentSetter(int price, int token1) {
		Payment payment = new Payment();
		payment.setAmount(price);
		payment.setTokenNumber(token1);
		payment.setPaymentStatus(true);
		return payment;
	}

	public static Token tokenSetter(int currToken, long time, int floor, VehicleSize type, int number) {
		Token token = new Token();
		token.setEntryTime(time);
		token.setFloor(floor);
		token.setVehicleType(type);
		token.setTokenNumber(currToken);
		token.setVehicleNumber(number);
		return token;
	}

	public static UserDetails userSetter(int price, long tokennum, String name) {
		UserDetails user = new UserDetails();
		user.setUserName(name);
		user.setAmount(price);
		return user;
	}
}
