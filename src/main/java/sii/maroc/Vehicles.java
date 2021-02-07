package sii.maroc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author marouane
 *
 */
public class Vehicles {
	
	private float diesel, fuel, hybrid;
	
	
	/**
	 * Constructor - Map description of gas consumption values to diesel, fuel, hybrid attributes
	 * @param description
	 */
	public Vehicles(String description) {
		
		List<String> consumptions = Arrays.asList(description.split(","));
		
		this.diesel = getGasConsumtionPercentage(consumptions.get(0));
		
		this.fuel = getGasConsumtionPercentage(consumptions.get(1));
		
		this.hybrid = getGasConsumtionPercentage(consumptions.get(2));
	}


	/**
	 * @param vehicleType
	 * @param gasType
	 * @param closedDoors
	 * @param distance
	 * @return A String representing the vehicle state
	 */
	public String move(String vehicleType, String gasType, String closedDoors, String distance) {
		
		Set<String> closedDoorsSet = new HashSet<String>(Arrays.asList(closedDoors.split(" ")));
		Set<String> openDoorsSet = null;
		String result = null;
		float consumption = 0;
		
		openDoorsSet = getOpenDoors(vehicleType, closedDoorsSet);
		
		if( !openDoorsSet.isEmpty() ) {
			result = getCarShape(openDoorsSet);
		}
		
		else {
			consumption = this.getGasConsumption(gasType, distance.split(" ")[0]);
			result = "DOORS OK, MOVING. The "+
					vehicleType+" will consume "+
					String.format("%.2f",consumption)+" L";
		}
		
		return result;
	}


	/**
	 * @param gasType
	 * @param distance 
	 * @return Corresponding gas consumption in liter
	 */
	public float getGasConsumption(String gasType, String distance) {
		
		float consumption = 0;
		
		if( gasType.equals("Diesel") ) {
			consumption = Float.valueOf(Float.valueOf(distance)*this.diesel/100);
		}
		else if( gasType.equals("Fuel") ) {
			consumption = Float.valueOf(Float.valueOf(distance)*this.fuel/100);
		}
		else {
			consumption =Float.valueOf(Float.valueOf(distance)*this.hybrid/100);
		}
		
		return consumption;
	}
		
	/**
	 * @param vehicleType
	 * @param closedDoorsSet
	 * @return Set containing open doors 
	 */
	static public Set<String> getOpenDoors(String vehicleType, Set<String> closedDoorsSet){
		
		Set<String> openDoorsSet = new HashSet<String>(Arrays.asList("1","2","3","4"));
		
		if( vehicleType.equalsIgnoreCase("truck") ) {
			openDoorsSet.remove("3");
			openDoorsSet.remove("4");
		}
		else if( vehicleType.equalsIgnoreCase("motorcycle") ) {
			openDoorsSet.removeAll(openDoorsSet);
		}
		openDoorsSet.removeAll(closedDoorsSet);
		
		return openDoorsSet;
	}
	
	/**
	 * @param gas consumption - for Example : "Diesel:5%"
	 * @return percentage of gas consumption - for Example : 5
	 */
	static public float getGasConsumtionPercentage(String consumption) {
		
		return Float.parseFloat(consumption.
				substring(consumption.indexOf(":")+1, consumption.indexOf("%")));
	}
	
	
	/**
	 * @param openDoors
	 * @return A String shape indicating opened doors of the car
	 * Example :
	 * /***********
	 * DOORS KO, BLOCKED
           _
          | | 
          /_|
          
        ************/
	static public String getCarShape(Set<String> openDoors) {
		
		String result = "DOORS KO, BLOCKED \n"+
				"  _\n";
		for( int i=1 ; i< 5 ; i++ ) {
			if( openDoors.contains(String.valueOf(i)) ) {
				result += (i%2==0)?(i==2)?" \\\n":"\\\n":" /";
			}
			else {
				result += (i%2==0)?(i==4)?"|":" |\n":" |";
			}
			
			result += i==3?"_":"";
		}
		
		return result;
	}
	
}
