<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<p>
  <h2>Recently viewed</h2>
  <c:if test="${not empty sessionScope.recently_viewed}">
    <table>
      <tr>
        <c:forEach var="viewed" items="${sessionScope.recently_viewed.products}">
          <td class="viewed-products">
            <div>
              <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${viewed.imageUrl}">
            </div>
            <a href="${pageContext.servletContext.contextPath}/products/${viewed.id}">
                ${viewed.description}
            </a>
            <div>
              <fmt:formatNumber value="${viewed.price}" type="currency" currencySymbol="${viewed.currency.symbol}"/>
            </div>
          </td>
        </c:forEach>
      </tr>
    </table>
  </c:if>
</p>