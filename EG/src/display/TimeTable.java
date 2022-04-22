package display;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TimeTable {
	
				private Connection connect()
 {
						Connection con = null;
				 try
			 {
					 	Class.forName("com.mysql.cj.jdbc.Driver");

					 	//connect to database
					 	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/powercut", "root", "sandaru@1S");
			 }
				 catch (Exception e){	
					 	e.printStackTrace();}
				    	return con;
			 }
				

				
				
				public String readByAcc(String acc) { //method for search power cut schedule by giving account number

							Boolean existschedule= false,existacc=false;
							String output = "";
							String letter= "";
	
						try {
		                        //check database connectivity
								Connection con = connect();
								if (con == null)
				{
									return "Error while connecting to the database for reading.";
	 		
                }                   //preparing HTML table to output
									output ="<table border='1'><tr>"
								 			+"<th>zone</th>"
								 			+"<th>location</th>"
								 			+"<th>start</th>"
								 			+"<th>end</th>"
								 			+"<th>date</th></tr><tr>";
									            //read consumer data which matches to given account number
											 	String query = "select * from consumerinfo where accountNo='"+acc+"'"; 
											 	Statement stmt = con.createStatement(); 
											 	ResultSet rs = stmt.executeQuery(query);
											 	while(rs.next()) {
														String location2 = rs.getString("location"); //get location of that consumer
												 	    existacc= true;
												 	    
	 	    
	 	                                            //read power cut schedules assigned for that consumer's location 
												 	String query1 = "select * from schedules where location='"+location2+"'"; 
													Statement stmt1 = con.createStatement(); 
													ResultSet rs1 = stmt1.executeQuery(query1); 
												 	while(rs1.next()) {
																existschedule= true;
																//get schedule data including location, power cut start time,end time and the date
					                                             String start = rs1.getString("start");
																String location = rs1.getString("location");
																String end = rs1.getString("end"); 
																String onDate = rs1.getString("onDate");	
	 	
	                                                                    //get zone matches to that location
																		String query2 = "select * from zone where zone='"+location2+"'"; 
																		Statement stmt2 = con.createStatement(); 
																		ResultSet rs2 = stmt2.executeQuery(query2); 
																		while(rs2.next()) {
																		String zonecharacter = rs2.getString("zone_character");
																		letter = zonecharacter;
																		System.out.println("pass");
																		//prepare output including power cut zone,location,starting time, ending time and date
																	    output += "<td>" + zonecharacter + "</td>"
																			   +"<td>" + location + "</td>"
																			   +"<td>" + start + "</td>"
																			   +"<td>" + end + "</td>"
																			   +"<td>" + onDate + "</td>";
																	    output += "</tr>";
																			 
			 
			
	                                                                   }
	                                                            }
                                                         }
								con.close();
								output += "</table>";
							 		if(existacc == false) {//if user input account number is not included in database, it will give error as invalid account 
							 			return output = "INVALID ACCOUNT NUMBER!!!  PLEASE RECHECK"; 
							 		}	
							 		if(existschedule == false) {//if user's location has no schedules assigned, it will inform 
							 			return output = "NO SCHEDULE ADDED YET FOR YOUR AREA!!!"; 
							 		}	
			
							 		return output;
	
						}catch(Exception e) {
							output = "Error while reading the items.";
							System.err.println(e.getMessage());
							return output;
						}

				}


				public String readByZone(String zoneLetter) {//method for search power cut schedule by zone letter
	
						Boolean existschedule= false,existzone=false;
						String output = "";
	
	
						try {
							//check database connectivity
							Connection con = connect();
							if (con == null)
                           {
								return "Error while connecting to the database for reading.";
	 		
                            } //preparing HTML table to output
							 	output ="<table border='1'><tr>"
							 			+"<th>zone</th>"
							 			+"<th>location</th>"
							 			+"<th>start</th>"
							 			+"<th>end</th>"
							 			+"<th>date</th></tr><tr>";
							 	       //reading zone information which matches to user input zone letter
									 	String query = "select * from zone where zone_character='"+zoneLetter+"'";
									 	Statement stmt = con.createStatement(); 
									 	ResultSet rs = stmt.executeQuery(query);
									 	while(rs.next()) {
											String zone = rs.getString("zone"); //getting the zone
											existzone = true;
			                                        //reading information matches to user's location
												    String query1 = "select * from schedules where location='"+zone+"'"; 
													Statement stmt1 = con.createStatement(); 
													ResultSet rs1 = stmt1.executeQuery(query1); 
												 	while(rs1.next()) {
														existschedule= true;
														//getting power cut location, power cut starting time, ending time,and the date
														String location = rs1.getString("location");
														String start = rs1.getString("start");
														String end = rs1.getString("end"); 
														String onDate = rs1.getString("onDate");	
														 output += "<td>" + zoneLetter + "</td>"//setting those data in output
																 +"<td>" + location + "</td>"
																 +"<td>" + start + "</td>"
																 +"<td>" + end + "</td>"
																 +"<td>" + onDate + "</td>";
														  output += "</tr>";

												 	}
		 	
									 	}
							 	con.close();
								output += "</table>";
								if(existzone == false) {//if user input zone is not between A-Z, it will give error as not available zone 
									return output = "REQUESTED ZONE IS NOT AN AVAILABLE ZONE!!!  PLEASE ENTER ZONE BETWEEN A-Z"; 
								}	
								if(existschedule == false) {//if user's location has no schedules assigned, it will inform 
									return output = "NO SCHEDULE ADDED YET FOR YOUR AREA!!!"; 
								}	
								return output;
						}catch(Exception e) {
							return "";
						}
				}



				public String readByLocation(String loc) {//method for search power cut schedule by location
					// TODO Auto-generated method stub
	
						String output = "";
						Boolean existschedule= false, existlocation=false;
						try {
							//check database connectivity
							Connection con = connect();
							if (con == null)
						{
								return "Error while connecting to the database for reading.";
			 		
						}
						//preparing HTML table to output
					 	output ="<table border='1'><tr>"
					 			+"<th>zone</th>"
					 			+"<th>location</th>"
					 			+"<th>start</th>"
					 			+"<th>end</th>"
					 			+"<th>date</th></tr><tr>";
					 	//reading zone information which matches to user input zone 
					 	String query = "select * from zone where zone='"+loc+"'"; 
					 	Statement stmt = con.createStatement(); 
					 	ResultSet rs = stmt.executeQuery(query);
					 	while(rs.next()) {
							String zoneLetter = rs.getString("zone_character"); //getting the zone letter
							existlocation = true;
										//reading power cut schedules matches to user's location
										String query1 = "select * from schedules where location='"+loc+"'"; 
									 	Statement stmt1 = con.createStatement(); 
									 	ResultSet rs1 = stmt1.executeQuery(query1);
									 	while(rs1.next()) {
									 		existschedule= true;
									 		//getting power cut location, power cut starting time, ending time,and the date
											String location = rs1.getString("location");
											String start = rs1.getString("start");
											String end = rs1.getString("end"); 
											String onDate = rs1.getString("onDate");	
											//setting those data in output
											 output += "<td>" + zoneLetter + "</td>"
													 +"<td>" + location + "</td>"
													 +"<td>" + start + "</td>"
													 +"<td>" + end + "</td>"
													 +"<td>" + onDate + "</td>";
											  output += "</tr>";
		  
		  
									 	}
						 	}
						 	con.close();
							output += "</table>";
							if(existlocation== false) {//if user input location is incorrect, it will give error as not available location
								return output = "REQUESTED LOCATION IS NOT AN AVAILABLE LOCATION!!!"; 
							}	
							if(existschedule == false) {//if user's location has no schedules assigned, it will inform 
								return output = "NO SCHEDULE ADDED YET FOR REQUESTED AREA!!!"; 
							}	
							return output;

						}catch(Exception e) {
							return "";
						}
				}

	}
