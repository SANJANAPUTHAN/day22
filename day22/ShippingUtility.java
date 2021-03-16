package day22;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

interface Shipping
{
	public String getShipmentDetails();
}
public class ShippingUtility implements Shipping{
	public String getShipmentDetails()
	{
		System.out.println("Enter invoice number");
		Scanner sc=new Scanner(System.in);
		int invno=sc.nextInt();
		InvoiceMasterDTO invoice=new InvoiceMasterDTO();
		InvoiceMasterImpl impl=new InvoiceMasterImpl();
		invoice = impl.getInvoiceMaster(invno);
		String inv[]=invoice.toString().split(" ");
		LocalDate date=LocalDate.parse(inv[1]);
		int customerno=Integer.parseInt(inv[2]);		
		System.out.println("Enter arrival time: ");
		LocalTime arrivaltime=LocalTime.parse(sc.next());
		System.out.println("Enter distance in kilometers: ");
		int distance=sc.nextInt();
		System.out.println("Enter speed in hours: ");
		int travelspeed=sc.nextInt();
		System.out.println("Enter breaktime in intervals of hours: ");
		int travelbreak=sc.nextInt();
		LocalTime t;
		int day;
		if(checkSpecialconstraints(date))
		{
			date=setspecialconstraints(date);
			arrivaltime=setConstraintTime(arrivaltime);
		}
		else if((checkconstraint(date))!=0)
		{
			day=checkconstraint(date);
			date=setconstraintDate(date, day);
			arrivaltime=setConstraintTime(arrivaltime);
			
		}
		float time=getTime(distance,travelspeed);
		if(travelbreak!=0)
		{
			time=getbreak(time,travelbreak);
		}
		float minutes=time%1;
		minutes=(float)(time-Math.floor(time));
		minutes=minutes*100;
		ArrayList<String> datetime=getdatetime(arrivaltime,time,date);
		LocalTime nextTime=LocalTime.parse(datetime.get(1));
		nextTime=nextTime.plus((long) minutes,ChronoUnit.MINUTES);
		LocalDate d=LocalDate.parse(datetime.get(0));
		return "The parcel will reach at "+nextTime+"(approximately)  "+d+" "+d.getDayOfWeek();
	}
	private static LocalDate setspecialconstraints(LocalDate start_date) 
	{
		
			int day=1;
			start_date=setconstraintDate(start_date, day);
			if(checkconstraint(start_date)!=0)
			{
				day=checkconstraint(start_date);
				start_date=setconstraintDate(start_date, day);				
			}
			
		
		return start_date;
	}
	private static boolean checkSpecialconstraints(LocalDate start_date)
	{
		if(start_date.isEqual(LocalDate.of(start_date.getYear(), 1, 1))||start_date.isEqual(LocalDate.of(start_date.getYear(), 8, 15))||
				start_date.isEqual(LocalDate.of(start_date.getYear(), 10, 2))||start_date.isEqual(LocalDate.of(start_date.getYear(), 1, 26)))
		{
			return true;
		}
		return false;
	}
	private static LocalTime setConstraintTime(LocalTime time)
	{
		return LocalTime.parse("06:00:00");
	}
	private static int checkconstraint(LocalDate start_date) {
		if(start_date.getDayOfWeek()==DayOfWeek.SUNDAY)
		{
			return 1;
		}
		else if(start_date.getDayOfWeek()==DayOfWeek.SATURDAY)
		{
			return 2;
		}
		return 0;
	}

	private static LocalDate setconstraintDate(LocalDate date,int day)
	{
		date=date.plus(day,ChronoUnit.DAYS);
		return date;
		
	}
	private static ArrayList<String> getdatetime(LocalTime start_time, float time, LocalDate start_date) {

		int hours=start_time.getHour();
		ArrayList<String> datetime=new ArrayList<String>();
		if(hours<12 && time<24)
		{
			start_time=getTime(start_time,time);
			datetime.add(String.valueOf(start_date));
			datetime.add(String.valueOf(start_time));
		}
		else if((hours<12 || hours>12) && time>24)
		{
			while(time>24)
			{
				start_date=start_date.plus(1,ChronoUnit.DAYS);
				if(checkSpecialconstraints(start_date))
				{
					start_date=setspecialconstraints(start_date);
					start_time=setConstraintTime(start_time);
				}
				else if((checkconstraint(start_date))!=0)
				{
					int day=checkconstraint(start_date);
					start_date=setconstraintDate(start_date, day);
					time=time+(start_time.getHour()-6);
					start_time=setConstraintTime(start_time);
					if(time<24)
					{
						break;
					}
					
				}
				start_time=start_time.plus(24,ChronoUnit.HOURS);
				time=time-24;
			}
			if(24-hours < time)
			{
				start_date=start_date.plus(1,ChronoUnit.DAYS);
				
			}
			start_time=getTime(start_time, time);
			datetime.add(String.valueOf(start_date));
			datetime.add(String.valueOf(start_time));
		}
		else if(hours>12 && time<24)
		{
			if(24-hours < time)
			{
				start_date=start_date.plus(1,ChronoUnit.DAYS);
				if(checkSpecialconstraints(start_date))
				{
					start_date=setspecialconstraints(start_date);
					start_time=setConstraintTime(start_time);
				}
				else if((checkconstraint(start_date))!=0)
				{
					int day=checkconstraint(start_date);
					time=time-(24-hours);
					start_date=setconstraintDate(start_date, day);
					start_time=setConstraintTime(start_time);
					
					
				}
				
			}
			start_time=getTime(start_time, time);
			datetime.add(String.valueOf(start_date));
			datetime.add(String.valueOf(start_time));
		}
		
		return datetime;
	}

	private static LocalTime getTime(LocalTime start_time,float time) {
		start_time=start_time.plus((long)time,ChronoUnit.HOURS);
		return start_time;
	}
	private static float getbreak(float time, int breaktime) {
		if(breaktime<time)
		{
			int cal=(int)time/breaktime;
			time+=cal;
		}
		return time;
	}
	private static float getTime(int distance, int travelspeed)
	{
		return (float)distance/(float)travelspeed;
	}


}
