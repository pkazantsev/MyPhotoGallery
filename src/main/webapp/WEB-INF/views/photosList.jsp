<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored ="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp" />
<div class="top_level">
  <div class="content">
    <div class="page_header"><c:out value="${album.title}"></c:out></div>
    <div><a href="<%=application.getContextPath() %>/album_${album.albumId}/upload.html">&#160;Загрузить фотографии в этот альбом&#160;</a></div>
    <%-- здесь будет список страниц для постраничного вывода --%>
<c:forEach items="${photos}" var="photo">
    <div style="float:left;">
      <table class="album_minitables">
        <tr>
          <td style="height:210px; width:210px; background-color:#cfcfcf;">
            <a href="" title="<c:out value="${photo.title}"></c:out>">
              <img src="<%=application.getContextPath() %>/photo_<c:out value="${photo.photoId}"></c:out>/size_200/show.html" alt="<c:out value="${photo.title}"></c:out>" border="0" />
            </a>
          </td>
        </tr>
        <tr>
          <td style="height:20px;vertical-align:top;">
            <a href=""><c:out value="${photo.title}"></c:out></a>
          </td>
        </tr>
        <tr>
          <td class="photo_caption" style="text-align:left; padding-left:5px;">
              Добавлено: <fmt:formatDate value="${photo.addDate}" pattern="yyyy-MM-dd hh:mm:ss" />
          </td>
        </tr>
      </table>
    </div>
</c:forEach>
  </div>
  <div>
    <table style="width:220px; height:100%; background-color:#deecaa; margin:0px; padding:0px;vertical-align:top;">
      <tr>
        <td style="vertical-align:top;"></td>
      </tr>
    </table>
  </div>
</div>

<jsp:include page="footer.jsp" />