<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Checkout">
  <p>
    <c:if test="${not empty param.message and empty errors}">
        <div class="success">
                ${param.message}
        </div>
    </c:if>
    <c:if test="${not empty errors}">
        <div class="error">
            There ware an errors in placing order
        </div>
    </c:if>
  </p>
  <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
    <table>
      <thead>
        <tr>
          <td>Image</td>
          <td>Description</td>
          <td class="quantity">
            Quantity
          </td>
          <td class="price">
              Price
          </td>
        </tr>
      </thead>
      <c:forEach var="item" items="${order.items}" varStatus="status">
        <tr>
          <td>
            <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
          </td>
          <td>
            <a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">
              ${item.product.description}
            </a>
          </td>
          <td class="quantity">
            <fmt:formatNumber value="${item.quantity}" var="quantity"/>
            ${quantity}
          </td>
          <td class="price">
            <tags:popupPriceHistory product="${item.product}"/>
          </td>
        </tr>
      </c:forEach>
      <tr>
        <td></td>
        <td>Subtotal:</td>
        <td class="quantity">
          ${order.totalQuantity}
        </td>
        <td class="price">
          <fmt:formatNumber value="${order.subtotal}" type="currency" currencySymbol="${order.currency.symbol}"/>
        </td>
      </tr>
      <tr>
        <td></td>
        <td></td>
        <td>
            Delivery cost:
        </td>
        <td class="price">
          <fmt:formatNumber value="${order.deliveryCost}" type="currency" currencySymbol="${order.currency.symbol}"/>
        </td>
      </tr>
      <tr>
        <td></td>
        <td></td>
        <td>
          Total cost:
        </td>
        <td class="price">
          <fmt:formatNumber value="${order.totalCost}" type="currency" currencySymbol="${order.currency.symbol}"/>
        </td>
      </tr>
    </table>
    <h2>
      Your details
    </h2>
    <table>
      <tags:orderFormRow name="firstName" label="First name" order="${order}" errors="${errors}"/>
      <tags:orderFormRow name="lastName" label="Last name" order="${order}" errors="${errors}"/>
      <tags:orderFormRow name="phone" label="Phone" order="${order}" errors="${errors}"/>
      <tr>
        <td>Delivery date<span style="color: red">*</span></td>
        <td>
          <c:set var="error" value="${errors['deliveryDate']}"/>
          <input name="deliveryDate" value="${empty error ? param['deliveryDate'] : order['deliveryDate']}"
                 placeholder="dd/mm/yy" pattern="\d{1,2}/\d{1,2}/\d{2}"/>
          <c:if test="${not empty error}">
            <div class="error">
                ${error}
            </div>
          </c:if>
        </td>
      </tr>
      <tags:orderFormRow name="deliveryAddress" label="Delivery address" order="${order}" errors="${errors}"/>
      <tr>
        <td>Payment method<span style="color: red">*</span></td>
        <td>
          <select name="paymentMethod">
            <option></option>
            <c:forEach var="paymentMethod" items="${paymentMethods}">
              <option ${param['paymentMethod'] eq paymentMethod ? 'selected="selected"' : ''}>${paymentMethod}</option>
            </c:forEach>
          </select>
          <c:set var="error" value="${errors['paymentMethod']}" />
          <c:if test="${not empty error}">
            <div class="error">
                ${error}
            </div>
          </c:if>
        </td>
      </tr>
    </table>
    <p>
      <button>Order</button>
    </p>
  </form>
</tags:master>