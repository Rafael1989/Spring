<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:url value="/resources/css" var="cssPath" />
<link rel="stylesheet" href="${cssPath}/bootstrap.min.css" />
<link rel="stylesheet" href="${cssPath}/bootstrap-theme.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Livros de Java, Android, iPhone, Ruby, PHP e muito mais -
	Casa do Código</title>
</head>
<body>
	<nav class="navbar navbar-inverse">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${s:mvcUrl('HC#index').build()} ">Casa do Código</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="${s:mvcUrl('PC#lista').build()}">Lista de Produtos</a></li>
				<li><a href="${s:mvcUrl('PC#form').build()}">Cadastro de Produtos</a></li>
			</ul>
			<security:authorize access="isAuthenticated()">
			<ul class="nav navbar-nav navbar-right">
				<li>
					<a href="#">
						<security:authentication property="principal.username" var="usuario"/>
						Usuário: ${usuario.username}
					</a>
				</li>
			</ul>
			</security:authorize>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	</nav>
	<div class="container">
		<h1>Lista de Produtos</h1>
		<div>${sucesso}</div>
		<table class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<th>Título</th>
					<th>Descrição</th>
					<th>Páginas</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${produtos}" var="produto">
					<tr>
						<td><a
							href="${s:mvcUrl('PC#detalha').arg(0,produto.id).build()}">${produto.titulo}</a></td>
						<td>${produto.descricao}</td>
						<td>${produto.paginas}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div>${mensagem}</div>
	</div>
</body>
</html>