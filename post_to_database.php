<?php

ini_set('display_errors', 1);
error_reporting(E_ALL);


$host = "160.153.96.96";
$user = "james_king";
$password = "graduation";
$db_name = "choice_senior_project";

$connection = mysqli_connect($host,$user,$password,$db_name);

if(!$connection)
{
	echo "F";
}
else
{
	echo "S";
}


$action = $_POST["action"];

switch($action)
{
	/*Works*/
	case "loginUser":
	{		
		$username = $_POST["username"];
		$password = $_POST["password"];
		
		$checkRegistry_sql = "SELECT * FROM Users_Registry WHERE username = '$username' AND password = '$password' ";
		$updateOnlineUser_sql = "UPDATE Users_Online SET is_online = true WHERE username = '$username' ";
		$updateActiveUser_sql = "UPDATE Active_Voters_In_Elections SET is_online = true WHERE username = '$username' ";
		
		if((mysqli_num_rows(mysqli_query($connection,$checkRegistry_sql))==1) && mysqli_query($connection,$updateOnlineUser_sql) && mysqli_query($connection,$updateActiveUser_sql))
		{
			echo "S";
		}
		else
		{
			echo "F";
		}
	
		break;
	}
	
	/*Works*/
	case "insertUser":
	{
		$name = $_POST["name"];
		$username = $_POST["username"];
		$password = $_POST["password"];
		$state = $_POST["state"];
		$city = $_POST["city"];
		$id1 = $_POST["firstID"];
		$id2 = $_POST["secondID"];
		$id3 = $_POST["thirdID"];
		
		$insertUser_sql = "INSERT INTO Users_Registry VALUES('$username','$password','$state','$city','$id1','$id2','$id3','$name');";
		$insertUserOnline_sql = "INSERT INTO Users_Online VALUES('$username','$password',false)";
		
		if(mysqli_query($connection,$insertUser_sql) && mysqli_query($connection,$insertUserOnline_sql))
		{
			echo "S";
		}
		else
		{
			echo "F";
		}
		
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
					mysqli_query($connection,$updateInfo_sql);					
				}
			}
		}
		
		
		break;
	}
	
	/*Works*/
	case "addSecurityInformation":
	{
		$id1 = $_POST["firstID"];
		$id2 = $_POST["secondID"];
		$id3 = $_POST["thirdID"];
		$sq1 = $_POST["firstSQ"];
		$sqA1 = $_POST["firstSQA"];		
		$sq2 = $_POST["secondSQ"];
		$sqA2 = $_POST["secondSQA"];
		$password = $_POST["password"];
		
		$insertSecurityInfo_sql = "INSERT INTO Users_Security_Information VALUES('$id1','$id2','$id3','$sq1','$sqA1','$sq2','$sqA2','$password');";
		
		if(mysqli_query($connection,$insertSecurityInfo_sql))
		{
			echo "S";
		}
		else
		{
			echo "F";
		}
		
		break;
	}
	
	/*Works*/
	case "requestPassword":
	{		
		$id1 = $_POST["firstID"];
		$sq1 = $_POST["firstSQ"];
		$sq1A = $_POST["firstSQA"];
		$sq2 = $_POST["secondSQ"];
		$sq2A = $_POST["secondSQA"];
		
		$requestPassword_sql = "SELECT password FROM Users_Security_Information WHERE firstID = '$id1' AND firstSQ = '$sq1' AND firstSQA = '$sq1A' AND 						secondSQ = '$sq2' AND secondSQA = '$sq2A'";
			
		$output = mysqli_query($connection,$requestPassword_sql);		
		if(mysqli_num_rows($output)==1)
		{
			$password = mysqli_fetch_assoc($output);
			echo $password["password"];
		}
		else
		{
			echo "F";
		}		
		break;
	}
	
	/*Works*/
	case "validateElection":
	{
		$username = $_POST["username"];
		$electionID = $_POST["electionID"];
		
		$electionValidate_sql = "SELECT * FROM Active_Voters_In_Elections WHERE username = '$username' AND electionID = '$electionID'";
		$output = mysqli_query($connection,$electionValidate_sql);
		
		if(mysqli_num_rows($output)==1)
		{
			echo "S";
		}
		else
		{
			echo "F";
		}		
		break;
	}
	
	/*Works*/
	case "loadElectionName":
	{
		$electionID = $_POST["electionID"];
			
		$loadElectionName_sql = "SELECT electionName FROM Election_Information WHERE electionID = '$electionID'";
		$output = mysqli_query($connection,$loadElectionName_sql);
		
		if(mysqli_num_rows($output)==1)
		{
			$electionName = mysqli_fetch_assoc($output);
			echo $electionName["electionName"];
		}
		else
		{
			echo "F";
		}	
		
		break;
	}
	
	/*Works*/
	case "loadElectionInfo":
	{
		$electionID = $_POST["electionID"];
			
		$loadElectionInfo_sql = "SELECT electionDescription FROM Election_Information WHERE electionID = '$electionID'";
		$output = mysqli_query($connection,$loadElectionInfo_sql);
		
		if(mysqli_num_rows($output)==1)
		{
			$electionDescription = mysqli_fetch_assoc($output);
			echo $electionDescription["electionDescription"];
		}
		else
		{
			echo "F";
		}	
		
		break;
	}
	
	/*Works*/
	case "validateFirstID":
	{
		$id1 = $_POST["firstID"];
		$username = $_POST["username"];
		$electionID = $_POST["electionID"];
		
		$validateFirst_sql = "SELECT * FROM Active_Voters_In_Elections WHERE firstID = '$id1' AND username = '$username' AND electionID = '$electionID'";
		$output = mysqli_query($connection,$validateFirst_sql);		
		
		if(mysqli_num_rows($output)==1)
		{
			echo "S";
		}
		else
		{
			echo "F";
		}		
		break;
	}
	
	/*Works*/
	case "validateSecondID":
	{
		$id2 = $_POST["secondID"];
		$username = $_POST["username"];
		$electionID = $_POST["electionID"];		
		
		$validateSecond_sql = "SELECT * FROM Active_Voters_In_Elections WHERE secondID = '$id2' AND username = '$username' AND electionID = '$electionID'";
		$output = mysqli_query($connection,$validateSecond_sql);		

		if(mysqli_num_rows($output)==1)
		{
			echo "S";
		}
		else
		{
			echo "F";
		}		
		break;
	}
	
	/*Works*/
	case "validateThirdID":
	{
		$id3 = $_POST["thirdID"];
		$username = $_POST["username"];
		$electionID = $_POST["electionID"];
		$vote = $_POST["vote"];
				
		$validateThird_sql = "SELECT * FROM Active_Voters_In_Elections WHERE thirdID = '$id3' AND username = '$username' AND electionID = '$electionID'";
		$updateVoteApprove_sql = "UPDATE Active_Voters_In_Elections SET vote = true WHERE username = '$username'";
		$updateVoteAgainst_sql = "UPDATE Active_Voters_In_Elections SET vote = false WHERE username = '$username'";

		$outputValidateThirdID = mysqli_query($connection,$validateThird_sql);		

		if(mysqli_num_rows(mysqli_query($connection,$validateThird_sql))==1)
		{
			if($vote == "Approve")
			{
				$outputUpdateVoteApprove = mysqli_query($connection,$updateVoteApprove_sql);
				if($outputUpdateVoteApprove)
				{
					echo "S";
				}
				else
				{
					echo "F";
				}
			}
			else
			{
				$outputUpdateVoteAgainst = mysqli_query($connection,$updateVoteAgainst_sql);
				if($outputUpdateVoteAgainst)
				{
					echo "S";
				}
				else
				{
					echo "F";
				}
			}			
		}
		else
		{
			echo "FF";
		}
		break;
	}
	

	case "logoutUser":
	{		
		$username = $_POST["username"];
		
		$updateOfflineUser_sql = "UPDATE Users_Online SET is_online = false WHERE username = '$username' ";
		$updateOfflineActiveUser_sql = "UPDATE Active_Voters_In_Elections SET is_online = false WHERE username = '$username' ";
		
		if(mysqli_query($connection,$updateOfflineUser_sql)&&mysqli_query($connection,$updateOfflineActiveUser_sql))
		{
			echo "S";
		}
		else
		{
			echo "F";
		}
	
		break;
	}
	
$mysqli->close();	
}
?>