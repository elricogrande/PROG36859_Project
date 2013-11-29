package ca.sheridan.data;

public class DataConverter {
	public static long isbn(String searchTerm) {
		long isbnNumber;
		if (searchTerm.length() != 13 && searchTerm.length() != 10) {
			return 0;
		}
		try {
			isbnNumber = Long.parseLong(searchTerm);
		}
		catch (NumberFormatException e) {
			return 0;
		}
		return isbnNumber;
	}
}
