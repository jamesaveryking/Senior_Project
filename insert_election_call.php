<?php
$host = "160.153.96.96";
$user = "james_king";
$password = "graduation";
$db_name = "choice_senior_project";

$connection = mysqli_connect($host,$user,$password,$db_name);

if(!$connection)
{
	echo "Failed Connection.";
}
else
{
	echo "Successful Connection. <br/>";
}

if(!empty($_REQUEST['electionID']) && is_numeric($_REQUEST['electionID']))
{
$electionID = $_REQUEST['electionID'];
}
else if(empty($_REQUEST['electionID']))
{
$electionID = null;
echo "Please click this to go back and enter an election identification number: <a href=\"http://new-election.senior-project-james-king.info\">Back</a><br/>";
}
else if(!is_numeric($_REQUEST['electionID']))
{
$electionID = null;
echo "Please click this to go back and enter a numeric election identification number: <a href=\"http://new-election.senior-project-james-king.info\">Back</a><br/>";
}

if(!empty($_REQUEST['electionName']))
{
$electionName = $_REQUEST['electionName'];
}
else
{
$electionName = null;
echo "Please click this to go back and enter an election name: <a href=\"http://new-election.senior-project-james-king.info\">Back</a><br/>";
}

if(!empty($_REQUEST['electionDescription']))
{
$electionDescription = $_REQUEST['electionDescription'];
}
else
{
$electionDescription = null;
echo "Please click this to go back and enter an election description: <a href=\"http://new-election.senior-project-james-king.info\">Back</a><br/>";
}

if(!empty($_REQUEST['electionState']))
{
$electionState = $_REQUEST['electionState'];
}
else
{
$electionState = null;
echo "Please click this to go back and enter an election state: <a href=\"http://new-election.senior-project-james-king.info\">Back</a><br/>";
}

if(!empty($_REQUEST['electionCity']))
{
$electionCity = $_REQUEST['electionCity'];
}
else
{
$electionCity = null;
echo "Please click this to go back and enter an election city: <a href=\"http://new-election.senior-project-james-king.info\">Back</a><br/>";
}

if(isset($_REQUEST['status']))
{
	$status = $_REQUEST['status'];
	if($status == "T")
	{
		$active = true;
	}
	else if($status == "F")
	{
		$active = false;
	}
	else
	{
	echo "Please click this to go back and enter the activation status: <a href=\"http://new-election.senior-project-james-king.info\">Back</a><br/>";
	}
}
else
{
	$status = null;
}



if(isset($_REQUEST['state']))
{
	$state_status = $_REQUEST['state'];
	if($state_status == "T")
	{
		$state = true;
	}
	else if($state_status == "F")
	{
		$state = false;
	}
	else
	{
	echo "Please click this to go back and enter if the election is local to the state: <a href=\"http://new-election.senior-project-james-king.info\">Back</a><br/>";
	}
}
else
{
	$state_status = null;
}



if(isset($_REQUEST['city']))
{
	$city_status = $_REQUEST['city'];
	
	if($city_status == "T")
	{
		$city = true;
	}
	else if($city_status == "F")
	{
		$city = false;
	}
	else
	{
	echo "Please click this to go back and enter if the election is local to the city: <a href=\"http://new-election.senior-project-james-king.info\">Back</a><br/>";
	}
}
else
{
	$city_status = null;
}


if($electionID!=null && $electionName!=null && $electionDescription!=null && $electionState!=null && $electionCity!=null && $status!=null && $state_status!=null && $city_status!=null)
{
$insertElection_sql = "INSERT INTO Election_Information VALUES('$electionID','$electionName','$electionDescription','$electionState','$electionCity','$active','$state','$city');";
	if(mysqli_query($connection,$insertElection_sql))
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
	else
	{
		echo "Database Connection Failure";
	}
	
}

else
{
	echo $electionID;
}
?>

