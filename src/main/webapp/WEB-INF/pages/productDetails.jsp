<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">
  <h1>
    ${product.description}
  </h1>
    <c:if test="${not empty param.message and empty error}">
        <div class="success">${param.message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error">
            There was an error adding to cart
        </div>
    </c:if>
  <p>
    ${cart}
  </p>
  <form method="post" action="${pageContext.servletContext.contextPath}/products/${product.id}">
    <table>
      <tr>
        <td>Image</td>
        <td>
          <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
      </tr>
      <tr>
        <td>Code</td>
        <td>
          ${product.code}
        </td>
      </tr>
      <tr>
        <td>Stock</td>
        <td>
          ${product.stock}
        </td>
      </tr>
      <tr>
        <td>Price</td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
      <tr>
        <td>Quantity</td>
        <td class="quantity">
          <input name="quantity" class="quantity" value="${not empty error and not empty param.quantity ? param.quantity : 1}">
          <button>Add to cart</button>
          <c:if test="${not empty error}">
              <div class="error">${error}</div>
          </c:if>
        </td>
      </tr>
    </table>
  </form>
</tags:master>