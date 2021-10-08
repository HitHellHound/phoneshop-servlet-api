<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="recentlyViewed" type="com.es.phoneshop.model.features.RecentlyViewedProducts" scope="request"/>
<c:if test="${not empty recentlyViewed.products}">
    <p>
    <h2>Recently viewed</h2>
    <table>
        <tr>
            <c:forEach var="viewed" items="${recentlyViewed.products}">
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
    </p>
</c:if>