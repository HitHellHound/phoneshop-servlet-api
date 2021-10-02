<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
<header>
  <a href="${pageContext.servletContext.contextPath}">
    <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
    PhoneShop
  </a>
  <a class="cart" href="${pageContext.servletContext.contextPath}/cart">
    <jsp:include page="/cart/minicart"/>
  </a>
</header>
<main>
  <h1>
    Order not found
  </h1>
  <h3>
    <%=exception.getMessage()%>
  </h3>
</main>
<jsp:include page="/recently-viewed"/>
<p>
  (c)Expert-Soft
</p>
</body>
</html>