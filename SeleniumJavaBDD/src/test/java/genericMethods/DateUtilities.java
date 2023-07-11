package genericMethods;

/**
 * returns date types in different specified forms as specified by user
 * @author Justice Mohuba
 * Date: 04/07/2023
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtilities {
	
	/**
	 * Get the current Date and Time of a system by the date format 
	 * @param format - the format of the date to get date from
	 * @author Justice Mohuba
	 */
	public String getcurrentDateTime(String format) 
	{
		Date date = new Date();
		
		String today = new SimpleDateFormat(format).format(date);

		return today;
	}
	/**
	 * Get the current Date  of a system by the date format 
	 * @param format - the format of the date to get date from
	 * @author Justice Mohuba
	 */
	public String getCurrentDate(String format) {

		Date date = new Date();
		
		String today = new SimpleDateFormat(format).format(date);

		return today;

	}
	/**
	 * Get the current Date and Time of a system by adding the number of days to the date format
	 * @param days - the number of days you need added
	 * @param format - the format of the date to get date from
	 * @author Justice Mohuba
	 */
	public String getSpecifiedAddedDays(int days, String format) {
		Date date = new Date();
		
		Date  chanDate = new Date(date.getTime() + TimeUnit.DAYS.toMillis(days)); //Adds Number of Days

		String newDate = new SimpleDateFormat(format).format(chanDate);	

		return newDate;
	}
	/**
	 * Get the current Date and Time of a system by subtracting the number of days from the current date
	 * @param days - the number of days you need subtracted
	 * @param format - the format of the date to get date from
	 * @author Justice Mohuba
	 */
	public String getSpecifiedsubtractedDays(int days, String format) {

		Date date = new Date();
		
		Date  chanDate = new Date(date.getTime() - TimeUnit.DAYS.toMillis(days)); //subtracts number of Number of Days

		String newDate = new SimpleDateFormat(format).format(chanDate);	

		return newDate;
	}
	/**
	 * Get the current Date and Time of a system by subtracting the number of minutes from the current date
	 * @param minutes - the number of minutes you need subtracted
	 * @param format - the format of the date to get date from
	 * @author Justice Mohuba
	 */
	public String getSpecifiedSubtractedMin(int minutes, String format) {

		Date date = new Date();
		
		Date  chanDate = new Date(date.getTime() - TimeUnit.MINUTES.toMillis(minutes)); //subtracts number of Number of Days

		String newDate = new SimpleDateFormat(format).format(chanDate);	

		return newDate;
	}
	/**
	 * Get the current Date and Time of a system by adding the number of minutes to the date format
	 * @param minutes - the number of minutes you need added
	 * @param format - the format of the date to get date from
	 * @author Justice Mohuba
	 */
	public String getSpecifiedAddedMin(int minutes, String format) {

		Date date = new Date();
		
		Date  chanDate = new Date(date.getTime() + TimeUnit.MINUTES.toMillis(minutes)); //subtracts number of Number of Days

		String newDate = new SimpleDateFormat(format).format(chanDate);	

		return newDate;
	}
	/**
	 * Get the current Date and Time of a system by adding the number of hours to the date format
	 * @param hours - the number of hours you need added
	 * @param format - the format of the date to get date from
	 * @author Justice Mohuba
	 */
	public String getSpecifiedAddedhours(int hours, String format) {

		Date date = new Date();
		
		Date  chanDate = new Date(date.getTime() + TimeUnit.HOURS.toMillis(hours)); //subtracts number of Number of Days

		String newDate = new SimpleDateFormat(format).format(chanDate);	

		return newDate;
	}
	/**
	 * Get the current Date and Time of a system by subtracting the number of hours from the current date
	 * @param hours - the number of hours you need subtracted
	 * @param format - the format of the date to get date from
	 * @author Justice Mohuba
	 */
	public String getSpecifiedsubtractedHours(int hours, String format) {

		Date date = new Date();
		
		Date  chanDate = new Date(date.getTime() - TimeUnit.HOURS.toMillis(hours)); //subtracts number of Number of Days

		String newDate = new SimpleDateFormat(format).format(chanDate);	

		return newDate;
	}
}
