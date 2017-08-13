<!DOCTYPE html>
<html>

<head>
	<link rel="stylesheet" href="modify_election_status.css">
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js" type="text/javascript"></script>
	<title>Change Election Status</title>
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
	
	<div class = "Modify_Election">
		<form action="modify_election_call.php" method="post">
		<div class = "Election_Information" align = "right">
			<fieldset>
			<legend style="color:white;">Election Status Modification</legend>
			<p style="color:white;"><b>Election ID:</b><input name="electionID" type="text" size="49" maxlength="35"/></p>			
			<p style="color:white;"><label for="status">Activation Status:</label><input type="radio" name="status" value="T"/>Active<input type="radio" name="status" value="F"/>Inactive</p>
			<p style="color:white;"><label for="state">State Only:</label><input type="radio" name="state" value="T"/>Yes<input type="radio" name="state" value="F"/>No</p>
			<p style="color:white;"><label for="city">City Only:</label><input type="radio" name="city" value="T"/>Yes<input type="radio" name="city" value="F"/>No</p>
			<div align="center">
				<input type="submit" name="modifyElection" value="Modify Election Status"/>
			</div>
			</fieldset>
		</div>		
		
		</form>
	</div>

	


	
</body>

</html>