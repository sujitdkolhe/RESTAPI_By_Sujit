import org.testng.Assert;
import org.testng.annotations.Test;

import PayloadData.Payload1;
import io.restassured.path.json.JsonPath;

public class Basic4ComplexJsonParsor {
	@Test
	public static void CoursesDetails() {
		JsonPath js = new JsonPath(Payload1.CoursePrice());
		// Print number of courses returned by API
		int size = js.getInt("courses.size()");
		System.out.println(size);

		// Print purchase Amount of All Courses
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount of All Courses:-"+purchaseAmount);

		// Print Title of first course
		String title = js.get("courses[" + 0 + "].title");
		System.out.println("course:" + title);

		// Print All courses title and price 
		for (int i = 0; i < size; i++) {
			String coursesTitle = js.get("courses[" + i + "].title");
			System.out.println("course title:" + coursesTitle);
			int price = js.getInt("courses[" + i + "].price");
			System.out.println("course price:" + price);

			// We can Also print price by using below line directly
			// System.out.println(js.get("courses[" + i + "].price").toString());

		}
		// Print number of copies sold by RPA courses
		System.out.println("Print number of copies sold by RPA courses");
		for (int i = 0; i < size; i++) {
			String coursesTitle = js.get("courses[" + i + "].title");
			if (coursesTitle.equals("RPA")) {
				int copies = js.getInt("courses[" + i + "].copies");
				System.out.println("Copies of courses:- " + copies);
				break;
			}
		}
		// Price of courses
		/*
		 * int total = 0;
		 *  for (int i = 0; i < size; i++) { 
		 *  int priceOfCourse =js.getInt("courses[" + i + "].price"); 
		 *  total = total + priceOfCourse; 
		 *  }
		 * System.out.println("All Courses parchase amount is:- " + total);
		 * Assert.assertEquals(purchaseAmount, total);
		 */
		
		// Price and copies total of courses
		int total = 0;
		for (int i = 0; i < size; i++) {
			int priceOfCourse = js.getInt("courses[" + i + "].price");
			int CopiesOfCourse = js.getInt("courses[" + i + "].copies");
			int totalAllCorses = priceOfCourse * CopiesOfCourse;
			//System.out.println(totalAllCorses);
			total = total + totalAllCorses;
		}
		System.out.println("All Courses parchase amount is:- " + total);
		Assert.assertEquals(purchaseAmount, total);
	}
}
