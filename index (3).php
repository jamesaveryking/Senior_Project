<!DOCTYPE html>
<html>
<head>
<title>Create Election Page</title>
<link rel="stylesheet" href="create_election.css">
</head>

<body bgcolor = blue>

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
	

	<div class = "Insert_Election">
		<form action="insert_election_call.php" method="post">
		<div class = "Election_Information" align = "right">
			<fieldset>
			<legend style="color:white;">Election Insertion</legend>
			<p><b>Election ID:</b><input name="electionID" type="text" size="49" maxlength="35"/></p>
			<p><b>Election Name:</b><input name="electionName" type="text" size="56" maxlength="56"/></p>
			<p><label><b>Election Description:</b><textarea name="electionDescription" rows="4" cols="49"></textarea></label></p>
			<p><b>Election State:</b><input name="electionState" type="text" size="14" maxlength="35"/></p>
			<p><b>Election City:</b><input name="electionCity" type="text" size="35" maxlength="35"/></p>
			<p><label for="status">Activation Status:</label><input type="radio" name="status" value="T"/>Active<input type="radio" name="status" value="F"/>Inactive</p>
			<p><label for="state">State Only:</label><input type="radio" name="state" value="T"/>Yes<input type="radio" name="state" value="F"/>No</p>
			<p><label for="city">City Only:</label><input type="radio" name="city" value="T"/>Yes<input type="radio" name="city" value="F"/>No</p>
			<div align="center">
				<input type="submit" name="insertElection" value="Insert New Election"/>
			</div>
			</fieldset>
		</div>		
		
		</form>
	</div>

</body>


</html>