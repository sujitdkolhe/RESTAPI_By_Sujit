package PayloadData;

public class Basic8Payload {
public static String loginCrediential() {
	return
			"{\r\n" + 
			"	\"username\":\"%s\",\r\n" + 
			"	\"password\": \"%s\"\r\n" + 
			"	\r\n" + 
			"}";
}
public static String commentOnIssue() {
	return "{ \r\n" + 
			"    \"body\": \"%s\",\r\n" + 
			"    \"visibility\": {\r\n" + 
			"        \"type\": \"role\",\r\n" + 
			"        \"value\": \"Administrators\"\r\n" + 
			"    }\r\n" + 
			"}";
}
public static String CreateStoryPayoad() {
	return "{ \r\n" + 
			" \"fields\": {\r\n" + 
			"       \"project\":\r\n" + 
			" {\r\n" + 
			"          \"key\": \"RES\"\r\n" + 
			"  },\r\n" + 
			"       \"summary\": \"Sujit Creating comment on Story\",\r\n" + 
			"       \"description\": \"Giving demo for creating a Story\",\r\n" + 
			"     \"issuetype\": {\r\n" + 
			"          \"name\": \"Story\"\r\n" + 
			" }\r\n" + 
			"   }\r\n" + 
			"}";
}
public static String CreateBugPayoad() {
	return "{ \r\n" + 
			" \"fields\": {\r\n" + 
			"       \"project\":\r\n" + 
			" {\r\n" + 
			"          \"key\": \"RES\"\r\n" + 
			"  },\r\n" + 
			"       \"summary\": \"Sujit Creating comment on Bug\",\r\n" + 
			"       \"description\": \"Giving demo for creating a Story\",\r\n" + 
			"     \"issuetype\": {\r\n" + 
			"          \"name\": \"Bug\"\r\n" + 
			" }\r\n" + 
			"   }\r\n" + 
			"}";
}
public static String expectedMassage() {
	return   
			"{" +
			"\"body\": \"%s\""+
			"}";
}
}