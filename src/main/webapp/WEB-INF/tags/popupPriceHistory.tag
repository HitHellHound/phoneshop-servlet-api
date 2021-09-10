<%@ tag trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="product" required="true" type="com.es.phoneshop.model.product.Product" %>

<style>
    .popup {
        position: fixed;
        width: 100%;
        height: 100%;
        background: rgba(0, 0, 0, 0.8);
        top: 0;
        left: 0;
        opacity: 0;
        visibility: hidden;
        overflow-y: auto;
        overflow-x: hidden;
        transition: all 300ms ease 0s;
    }

    .popup:target {
        opacity: 1;
        visibility: visible;
    }

    .popup:target .popup__content{
        transform: translate(0px, 0px);
        opacity: 1;
    }

    .popup__area {
        position: absolute;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
    }

    .popup__body {
        min-height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 30px 10px;
    }

    .popup__content {
        background-color: #fff;
        color: #000;
        max-width: 800px;
        padding: 30px;
        position: relative;
        transition: all 300ms ease 0s;
        opacity: 0;
        transform: translate(0px, -100%);
    }

    .popup__close {
        position: absolute;
        right: 10px;
        top: 10px;
        font-size: 20px;
        color: black;
        text-decoration: none;
    }

    .popup__title {
        font-size: 30px;
        text-align: center;
    }

    .popup__text {
        text-align: left;
    }
</style>
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