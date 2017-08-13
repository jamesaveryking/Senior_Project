<!DOCTYPE html>
<html>
<head>
<title>Home</title>
<link rel="stylesheet" href="home_page.css">
</head>

<body bgcolor = "blue">
	<div class = "Title">
		<h1>Choice</h1>
		<h2>Welcome to the Future of Voting</h2>
	</div>
	<div class = "Page_Selection_Buttons">
		<table style = "margin: 0px auto;" id = "Initial_Menu">
			<tr>		
				<td>
					<button type = "button" onclick="location.href='http://statistics.senior-project-james-king.info';" >Election Statistics</button>
				</td>
				<td>
					<button type = "button" onclick="location.href='http://new-election.senior-project-james-king.info';">Create Election</button>
				</td>			
				<td>
					<button type = "button" onclick="location.href='http://modify-election-status.senior-project-james-king.info';">Change Election Status</button>
				</td>	
				<td>
					<button type = "button" onclick="location.href='http://view-online-users.senior-project-james-king.info';">View Online Users</button>
				</td>	
				<td>
					<button type = "button" onclick="location.href='http://senior-project-james-king.info';">Active Elections</button>
				</td>	
			</tr>
		</table>
	</div>
	
	<br>
	<br>
	<br>

	<div class = "Active_Elections">
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
			
		
		$retrieveUsers_sql = "SELECT electionID,electionName,electionDescription,is_active,state,city,state_only,city_only FROM Election_Information ORDER BY electionID ASC";
		$result = mysqli_query($connection,$retrieveUsers_sql);
		
		if($result)
		{
			echo '<table align="center" cellspacing="2" cellpadding="2" width="70%" border="3px" bgcolor="white">';
			
			while($row=mysqli_fetch_array($result, MYSQLI_ASSOC))
			{
				if($row['is_active'])
				{
					$election_id = $row['electionID'];
					$election_name = $row['electionName'];
					$election_description = $row['electionDescription'];
					if($row['city_only'])
					{
						$election_scope = "City";
						$election_city = $row['city'];
					}
					else if($row['state_only']&&(!$row['city_only']))
					{
						$election_scope = "State";
						$election_state = $row['state'];
					}
					else if((!$row['state_only'])&&(!$row['city_only']))
					{
						$election_scope = "Federal";
					}
					echo '<tr bgcolor = "gold"><td><p><b>Election:</b>'.$election_name.'</p></td>';
					echo '<td><p><b>ID:</b>'.$election_id.'</p></td>';
					echo '<td><p><b>Scope:</b>'.$election_scope.'</p></td>';
					if($row['city_only'])
					{
						echo '<td><p><b>City:</b>'.$election_city.'</p></td></tr>';
					}
					else if($row['state_only']&&(!$row['city_only']))
					{
						echo '<td><p><b>State:</b>'.$election_state.'</p></td></tr>';
					}
					else if((!$row['state_only'])&&(!$row['city_only']))
					{
						echo '<td><p><b>Nation:</b>United States of America</p></td></tr>';
					}
				}
			}
			
		}
		
		echo '</table>';
		}
	
	?>
	
	<div class = "Active_Users">
	
	
	</div>


</body>


</html>