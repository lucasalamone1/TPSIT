<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


<% 
	
	String username = request.getParameter("nome");
	String password = request.getParameter("psw");

	if(username.equals("Luca") && password.equals("admin"))
		out.println("Accesso consentito");
	else
		out.println("Accesso negato");

%>

</body>
</html>