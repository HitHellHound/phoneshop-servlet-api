<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <c:if test="${not empty param.message and empty error}">
    <p class="success">${param.message}</p>
  </c:if>
  <c:if test="${not empty error}">
    <p class="error">
      There was an error adding to cart
    </p>
  </c:if>
  <form>
    <input name="query" value="${param.query}">
    <button>Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>
            Description
            <tags:sortLink sort="description" order="asc"/>
            <tags:sortLink sort="description" order="desc"/>
        </td>
        <td>
          Quantity
        </td>
        <td class="price">
            Price
            <tags:sortLink sort="price" order="asc"/>
            <tags:sortLink sort="price" order="desc"/>
        </td>
        <td></td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <form method="post" action="${pageContext.servletContext.contextPath}/products">
        <tr>
          <td>
            <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
          </td>
          <td>
            <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
              ${product.description}
            </a>
          </td>
          <td class="quantity">
            <input name="quantity" class="quantity"
                   value="${not empty error and not empty param.quantity and param.id eq product.id ? param.quantity : 1}">
            <c:if test="${not empty error and param.id eq product.id}">
              <div class="error">
                ${error}
              </div>
            </c:if>
            <input type="hidden" name="id" value="${product.id}">
            <c:if test="${not empty param.sort}"><input type="hidden" name="sort" value="${param.sort}"></c:if>
            <c:if test="${not empty param.order}"><input type="hidden" name="order" value="${param.order}"></c:if>
            <c:if test="${not empty param.query}"><input type="hidden" name="query" value="${param.query}"></c:if>
          </td>
          <td class="price">
            <tags:popupPriceHistory product="${product}"/>
          </td>
          <td>
            <button>Add to cart</button>
          </td>
        </tr>
      </form>
    </c:forEach>
  </table>
</tags:master>