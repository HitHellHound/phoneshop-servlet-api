<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="product" required="true" type="com.es.phoneshop.model.product.Product" %>

<div id="popup${product.id}" class="popup">
    <a href="#this-price" class="popup__area"/>
    <div class="popup__body">
       <div class="popup__content">
           <a href="#header" class="popup__close">X</a>
           <div class="popup__title">
               <h2>Price History</h2>
               <h3>${product.description}</h3>
           </div>
           <div class="popup__text">
               <table>
                   <tr>
                       <td>Start date</td>
                       <td>Price</td>
                   </tr>
                   <c:forEach var="price" items="${product.priceHistory}">
                       <tr>
                           <td>${price.startDate}</td>
                           <td><fmt:formatNumber value="${price.price}" type="currency" currencySymbol="${product.currency.symbol}"/></td>
                       </tr>
                   </c:forEach>
               </table>
           </div>
       </div>
    </div>
</div>
<a href="#popup${product.id}" id="this-price">
    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
</a>