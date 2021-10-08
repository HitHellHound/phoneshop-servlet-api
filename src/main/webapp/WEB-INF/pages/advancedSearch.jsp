<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="Advanced Search">
  <c:if test="${not empty errors}">
    <p class="error">
      There was an error
    </p>
  </c:if>
  <h2>Advanced search</h2>
  <form method="post">
    Description
    <span><input name="query" value="${param.query}"></span>
    <span>
      <select name="queryOption">
        <option value="allWords" ${param['queryOption'] eq 'allWords' ? 'selected="selected"' : ''}>all words</option>
        <option value="anyWords" ${param['queryOption'] eq 'anyWords' ? 'selected="selected"' : ''}>any words</option>
      </select>
    </span>
    <p>
      Min price
      <c:set var="error" value="${errors['minPrice']}"/>
      <span><input class="price" name="minPrice" value="${param.minPrice}"></span>
      <c:if test="${not empty error}">
        <br>
        <span class="error">
          ${error}
        </span>
      </c:if>
    </p>
    <p>
      Max price
      <c:set var="error" value="${errors['maxPrice']}"/>
      <span><input class="price" name="maxPrice" value="${param.maxPrice}"></span>
      <c:if test="${not empty error}">
        <br>
        <span class="error">
            ${error}
        </span>
      </c:if>
    </p>
    <button>Search</button>
  </form>
  <c:if test="${not empty products}">
    <table>
      <thead>
        <tr>
          <td>Image</td>
          <td>
              Description
          </td>
          <td class="price">
              Price
          </td>
        </tr>
      </thead>
      <c:forEach var="product" items="${products}">
          <tr>
            <td>
              <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>
              <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                ${product.description}
              </a>
            </td>
            <td class="price">
              <tags:popupPriceHistory product="${product}"/>
            </td>
          </tr>
      </c:forEach>
    </table>
  </c:if>
</tags:master>