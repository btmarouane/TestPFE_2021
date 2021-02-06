package sii.maroc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vehicles {
	private float diesel, fuel, hybrid;
	
	
	public Vehicles(String description) {
		List<String> consumptions = Arrays.asList(description.split(","));
		this.diesel = Float.parseFloat(consumptions.get(0).
				substring(consumptions.get(0).indexOf(":")+1, consumptions.get(0).indexOf("%")));
		
		this.fuel = Float.parseFloat(consumptions.get(1).
				substring(consumptions.get(1).indexOf(":")+1, consumptions.get(1).indexOf("%")));
		
		this.hybrid = Float.parseFloat(consumptions.get(2).
				substring(consumptions.get(2).indexOf(":")+1, consumptions.get(2).indexOf("%")));

	}


	public String move(String type, String gazType, String closedDoors, String distance) {
		Set<String> doorsSet = new HashSet<String>(Arrays.asList("1","2","3","4"));
		String result = "";
		float consumption = 0;
		
		if(type.equalsIgnoreCase("truck")) {
			doorsSet.remove("3");
			doorsSet.remove("4");
		}
		else if(type.equalsIgnoreCase("motorcycle")) {
			doorsSet.removeAll(doorsSet);
		}
		
		Set<String> closedDoorsSet = new HashSet<String>(Arrays.asList(closedDoors.split(" ")));

		doorsSet.removeAll(closedDoorsSet);
		
		if(!doorsSet.isEmpty()) {
			result += "DOORS KO, BLOCKED \n"+
						"  _\n";
			for(int i=1; i< 5; i++) {
				if(doorsSet.contains(String.valueOf(i))) {
					result += (i%2==0)?"\\\n":" /";
				}
				else {
					result += (i%2==0)?(i!=4)?"|\n":"|":(i==3)?" |":" | ";
				}
				
				result += i==3?"_":"";
			}
			
		}
		else {
			
			result += "DOORS OK, MOVING. The "+type+" will consume ";
			if(gazType.equals("Diesel")) {
				consumption = Float.valueOf(Float.valueOf(distance.split(" ")[0])*this.diesel/100);
			}
			else if(gazType.equals("Fuel")) {
				consumption = Float.valueOf(Float.valueOf(distance.split(" ")[0])*this.fuel/100);
			}
			else {
				consumption = Float.valueOf(Float.valueOf(distance.split(" ")[0])*this.hybrid/100);
			}
			
			result += String.format("%.2f",consumption)+" L";
		}
		
		
        
		return result;
	}

	
}