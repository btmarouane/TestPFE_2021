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
	 * Constructor - Map description of gas consumptions to diesel, fuel, hybrid attributes
	 * @param description
	 */
	public Vehicles(String description) {
		List<String> consumptions = Arrays.asList(description.split(","));
		
		this.diesel = getGasConsumtionPercentage(consumptions.get(0));
		
		this.fuel = getGasConsumtionPercentage(consumptions.get(1));
		
		this.hybrid = getGasConsumtionPercentage(consumptions.get(2));

	}


	/**
	 * @param type
	 * @param gasType
	 * @param closedDoors
	 * @param distance
	 * @return A String representing the car state
	 */
	public String move(String type, String gasType, String closedDoors, String distance) {
		Set<String> doorsSet = new HashSet<String>(Arrays.asList("1","2","3","4"));
		Set<String> closedDoorsSet = new HashSet<String>(Arrays.asList(closedDoors.split(" ")));
		
		String result = null;
		float consumption = 0;
		
		if(type.equalsIgnoreCase("truck")) {
			doorsSet.remove("3");
			doorsSet.remove("4");
		}
		else if(type.equalsIgnoreCase("motorcycle")) {
			doorsSet.removeAll(doorsSet);
		}
		
		doorsSet.removeAll(closedDoorsSet);
		
		if(!doorsSet.isEmpty()) {
			result = getCarShape(doorsSet);
		}
		
		else {
			result = "DOORS OK, MOVING. The "+type+" will consume ";
			if(gasType.equals("Diesel")) {
				consumption = getGasConsumption(distance, this.diesel);
			}
			else if(gasType.equals("Fuel")) {
				consumption = getGasConsumption(distance, this.fuel);
			}
			else {
				consumption = getGasConsumption(distance, this.hybrid);
			}
			
			result += String.format("%.2f",consumption)+" L";
		}
		
		return result;
	}

		
	/**
	 * @param gas consumption - for Example : "Diesel:5%"
	 * @return percentage of gas consumption - for Example : 5%
	 */
	static public float getGasConsumtionPercentage(String consumption) {
		return Float.parseFloat(consumption.
				substring(consumption.indexOf(":")+1, consumption.indexOf("%")));
	}
	
	
	/**
	 * @param distance - for Example : "200 KM"
	 * @param gasConsumptionPercentage - for Example : 5%
	 * @return gas consumption in liter
	 */
	static public float getGasConsumption(String distance, float gasConsumptionPercentage) {
		return  Float.valueOf(Float.valueOf(distance.split(" ")[0])*gasConsumptionPercentage/100);
	}
	
	/**
	 * @param openDoors
	 * @return A String shape indicating opened doors of the car
	 */
	static public String getCarShape(Set<String> openDoors) {
		String result = "DOORS KO, BLOCKED \n"+
				"  _\n";
		for(int i=1; i< 5; i++) {
			if(openDoors.contains(String.valueOf(i))) {
				result += (i%2==0)?"\\\n":" /";
			}
			else {
				result += (i%2==0)?(i!=4)?"|\n":"|":(i==3)?" |":" | ";
			}
			
			result += i==3?"_":"";
		}
		
		return result;
	}
	
}