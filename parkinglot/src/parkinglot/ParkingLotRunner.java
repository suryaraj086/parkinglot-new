package parkinglot;

import java.util.Scanner;

public class ParkingLotRunner {

	public static void main(String[] args) {
		ParkingLot pObj = new ParkingLot();
		Scanner scan = new Scanner(System.in);
		Boolean bool = true;
		while (bool) {

			String info = "";
			System.out.println("1.Entry\n2.Exit");
			int choice = scan.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter the vehicle number");
				int number = scan.nextInt();
				System.out.println("1.Car\n2.MotorCycle\n3.Large\n4.Electric");
				System.out.println("Enter the Vehicle type");
				int val = scan.nextInt();
				VehicleSize type = null;
				switch (val) {
				case 1: {
					type = VehicleSize.Compact;
					break;
				}
				case 2: {
					type = VehicleSize.Motorcycle;
					break;
				}
				case 3: {
					type = VehicleSize.Large;
					break;
				}
				case 4: {
					type = VehicleSize.Electric;
					break;
				}
				}
				if (type == null) {
					System.out.println("Invalid vehicle type");
					continue;
				}

				System.out.println("Enter the floor");
				int floor = scan.nextInt();
				try {
					System.out.println(pObj.emptySlots(type));
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					continue;
				}
				int currToken = pObj.tokennumber();
				Token token = ObjectGenerator.tokenSetter(currToken, System.currentTimeMillis(), floor, type, number);
				boolean payment = false;
				if (type == VehicleSize.Electric) {
					scan.nextLine();
					System.out.println("Do you want to pay at electric panel");
					System.out.println("1.Yes\n2.No");
					int pay = scan.nextInt();
					if (pay == 1) {
						payment = true;
					}
				}
				try {
					System.out.println(pObj.bookSlot(floor, type, token, payment));
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
				scan.nextLine();
				System.out.println("Do you want to pay at customer info portal");
				System.out.println("1.Yes\n2.No");
				int temp = scan.nextInt();
				if (temp == 1) {
					info = "Yes";
				} else {
					info = "No";
				}
				if (info.equals("Yes")) {
					System.out.println("Enter the name");
					String name = scan.nextLine();
					System.out.println("Enter the price for wallet");
					int price = scan.nextInt();
					UserDetails user = ObjectGenerator.userSetter(price, currToken, name);
					try {
						System.out.println(pObj.payment(user, currToken));
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					System.out.println("Thanks for coming");
				}
				break;
			case 2:
				System.out.println("Enter the vehicle number");
				int num = scan.nextInt();
				System.out.println("Enter the token number");
				int token1 = scan.nextInt();
				int price = 0;
				info = pObj.checkPaidStatus(token1);
				try {
					price = pObj.calculate(num, token1);
					System.out.println("The price is " + price);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				Payment payment1 = ObjectGenerator.paymentSetter(price, token1);
				if (!info.equals("Yes")) {
					scan.nextLine();
					System.out.println("1.Cash\n2.Credit card");
					System.out.println("Enter the payment type");
					int val1 = scan.nextInt();
					if (val1 == 1) {
						payment1.setPaymentType(val1);
					} else if (val1 == 2) {
						System.out.println("Enter credit card number");
						int credit = scan.nextInt();
						payment1.setPaymentType(val1);
						payment1.setCreditCardNumber(credit);
					} else {
						System.out.println("Enter valid number");
					}
					try {
						System.out.println(pObj.exit(num, token1));
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
				} else {
					int wallet = 0;
					try {
						wallet = pObj.walletAmountChecker(token1);
					} catch (Exception e1) {
						e1.printStackTrace();
						System.out.println(e1.getMessage());
					}
					int balance = wallet - price;
					if (balance < 0) {
						System.out.println("1.Cash\n2.Credit card");
						System.out.println("Enter the payment type");
						int val1 = scan.nextInt();
						if (val1 == 1) {
							payment1.setPaymentType(val1);
						} else if (val1 == 2) {
							System.out.println("Enter credit card number");
							int credit = scan.nextInt();
							payment1.setPaymentType(val1);
							payment1.setCreditCardNumber(credit);
						} else {
							System.out.println("Enter valid number");
						}
					}
					try {
						System.out.println(pObj.exit(num, token1));
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
				}
				break;
			}
		}
		scan.close();
	}
}
