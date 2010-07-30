<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored ="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="header.jsp" />

<div class="top_level">
  <div class="content">
    <div class="page_header">Редактирование фотографии:
      <a href="${pageContext.request.contextPath}/photo_${photo.photoId}.html">${photo.title}</a>
      <br />
      <span class="additional">
        из <c:choose>
          <c:when test="${photo.album == null}">нераспределенных фотографий</c:when>
          <c:otherwise>альбома <a href="<%=application.getContextPath() %>/album_${photo.album.albumId}.html">${photo.album.title}</a></c:otherwise>
        </c:choose>
        <c:if test="${photo.user.userId != sessionScope.User.userId}">
        пользователя <a href="<%=application.getContextPath() %>/user_${photo.user.userId}.html">${photo.user.login}</a>
        </c:if>
      </span>
    </div>
<form:form commandName="photo">
    <table class="main">
      <colgroup>
        <col style="width:90px;">
      </colgroup>
      <tr>
        <td>Превьюшка:</td>
        <td class="photo">
          <img src="${pageContext.request.contextPath}/photo_${photo.photoId}/size_150/show.html" alt="${photo.title}" border="0" />
        </td>
      </tr>
      <tr>
        <td>Название:</td>
        <td>
          <form:input path="title" cssStyle="width:500px;"/>&#160;
          <form:errors path="title"/>
        </td>
      </tr>
      <tr>
        <td>Описание:</td>
        <td>
          <form:textarea path="description" cols="60" rows="4"/>&#160;
          <form:errors path="description"/>
        </td>
      </tr>
      <tr><td colspan=2 style="height:10px;"></td></tr>
      <tr>
        <td></td>
        <td><input type="submit" value="Сохранить" /></td>
      </tr>
    </table>
</form:form>
    <br />
<form:form commandName="album" action="${pageContext.request.contextPath}/photo_${photo.photoId}/move.html">
    <div>
      <input type="submit" value="Переместить в альбом" />
      <form:select path="albumId">
        <form:option value="0">Отсутствует</form:option>
        <form:options items="${albums}" itemLabel="title" itemValue="albumId" />
      </form:select>
    </div>
    <c:if test="${photo.album.preview == photo}">
    <div>
      Внимание! данное изображение установлено в качестве изображения для предварительного просмотра в альбоме.
      При перемещении изображение для предварительного просмотра у данного альбома будет сброшено!
    </div>
    </c:if>
</form:form>
  </div>
  <div>
    <table class="left_panel">
      <tr>
        <td style="vertical-align:top;"></td>
      </tr>
    </table>
  </div>
</div>
<jsp:include page="footer.jsp" />