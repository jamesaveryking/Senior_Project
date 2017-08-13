<!DOCTYPE html>
<html>

<head>
	<link rel="stylesheet" href="view_users_page.css">
	<title>View Online Users</title>
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
	

	<div class = "Active_Users">
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
		
		
		$retrieveUsers_sql = "SELECT username FROM Users_Online WHERE is_online = true ORDER BY username ASC";
		$result = mysqli_query($connection,$retrieveUsers_sql);
		
		if($result)
		{
			echo '<table align="center" cellspacing="2" cellpadding="2" width="70%" border="2"><tr><td align="center"><h1><b>Usernames:</b></h1></td>';
			
			while($row=mysqli_fetch_array($result, MYSQLI_ASSOC))
			{
				echo '<tr><td align="center"><h3>'.$row['username'].'</h3></td></tr>';
			}
			
		}
		
		echo '</table>';
		}
	
	?>
	</div>


</body>

</html>