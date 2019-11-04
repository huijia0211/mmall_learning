<html>
<%@ page contentType="text/html;charset=utf-8" %>
<body>
<h2>Hello World!</h2>

springmvc上传文件
<form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="file"/>
    <input type="submit" value="上传文件"/>
</form>
</body>
</html>
