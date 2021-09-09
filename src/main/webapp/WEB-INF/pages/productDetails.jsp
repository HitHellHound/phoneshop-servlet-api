<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">
  <h1>
    ${product.description}
  </h1>
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
      <td>
        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
      </td>
    </tr>
  </table>
</tags:master>