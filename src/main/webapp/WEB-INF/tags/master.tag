<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ attribute name="pageTitle" required="true" %>

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
    <jsp:doBody/>
  </main>
  <jsp:include page="/recently-viewed"/>
  <p>
    (c)Expert-Soft
  </p>
</body>
</html>