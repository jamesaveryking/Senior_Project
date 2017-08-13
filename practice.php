<?php
$host = "160.153.96.96";
$user = "james_king";
$password = "graduation";
$db_name = "choice_senior_project";

$connection = mysqli_connect($host,$user,$password,$db_name);

if(!$connection)
{
	echo "<h3>Failed Connection.</h3><br>";
}
else
{
			
		
		$retrieveInfo_sql = "SELECT DISTINCT username, First_ID, Second_ID, Third_ID, electionID, electionName, is_active FROM Users_Registry,Election_Information WHERE Users_Registry.city = Election_Information.city OR Users_Registry.state = Election_Information.state OR (Election_Information.city_only = false AND Election_Information.state_only = false)";
		$result = mysqli_query($connection,$retrieveInfo_sql);
		
		if($result)
		{			
			while($row=mysqli_fetch_array($result, MYSQLI_ASSOC))
			{
				if($row['is_active'])
				{
					$username = $row['username'];
					$firstID = $row['First_ID'];
					$secondID = $row['Second_ID'];
					$thirdID = $row['Third_ID'];
					$electionID = $row['electionID'];
					$electionName =  $row['electionName'];
					
							
					$updateInfo_sql = "INSERT INTO Active_Voters_In_Elections (username, firstID, secondID, thirdID, electionID, electionName" .")VALUES('$username','$firstID','$secondID','$thirdID','$electionID','$electionName')";
										
					echo mysqli_query($connection,$updateInfo_sql);					
				}
			}
		}
}	
?>