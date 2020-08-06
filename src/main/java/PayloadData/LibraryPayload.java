package PayloadData;

public class LibraryPayload {
public static String addBook() {
	return 
			"{\r\n" + 
			"\r\n" + 
			"\"name\":\"Learn Appium Automation with Java\",\r\n" + 
			"\"isbn\":\"%s\",\r\n" + 
			"\"aisle\":\"%s\",\r\n" + 
			"\"author\":\"John foe\"\r\n" + 
			"}\r\n"+
			"";
}
public static String deleteResponse() {
	return 
			"{\r\n" + 
			"    \"Msg\": \"successfully added\",\r\n" + 
			"    \"ID\": \"%s\"\r\n" + 
			"}\r\n"+
			"";
}
}
