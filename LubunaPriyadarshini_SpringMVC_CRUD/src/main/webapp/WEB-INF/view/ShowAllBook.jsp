<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<title>BIS-Book List</title>
</head>
<body>
	<div class="modal fade" id="deletePopup" tabindex="-1"
		aria-labelledby="deletePopupLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="deletePopupLabel">Deleting...</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">Are you sure to delete the Employee
					permanently?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
					<form action="delete" method="post" name="form">
						<input type="hidden" name="idtodelete" value="">
						<button type="button" class="btn btn-primary"
							id="confirmDeleteBtn" onclick="confirmDelete()">Yes</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">BIS</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="home">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="add">Add</a></li>
					<li class="nav-item"><a class="nav-link active" href="#">All</a>
					</li>
				</ul>

			</div>
		</div>
	</nav>
	<br>
	<c:choose>
		<c:when test="${id ne null and id ne 0}">
			<c:if test="${successmsg ne null and not empty successmsg}">
				<div class="alert alert-success" role="alert">
					<c:out value='${successmsg}' />
				</div>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="${failuremsg ne null and not empty failuremsg}">
				<div class="alert alert-danger" role="alert">
					<c:out value='${failuremsg}' />
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${updatecount ne null and updatecount ne 0}">
			<c:if test="${updated ne null and not empty updated}">
				<div class="alert alert-success" role="alert">
					<c:out value='${updated}' />
				</div>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="${notupdated ne null and not empty notupdated}">
				<div class="alert alert-danger" role="alert">
					<c:out value='${notupdated}' />
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${deletecount ne null and deletecount ne 0}">
			<c:if test="${deleted ne null and not empty deleted}">
				<div class="alert alert-success" role="alert">
					<c:out value='${deleted}' />
				</div>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="${notdeleted ne null and not empty notdeleted}">
				<div class="alert alert-danger" role="alert">
					<c:out value='${notdeleted}' />
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
	<br>
	
	<div class="card border-info mb-3">
		<div class="card-body">
			<table
				class="table table-hover caption-top table-bordered border-secondary">
				<caption>List of Books</caption>
				<thead>
					<tr>
						<td colspan="6">
							<div class="d-flex justify-content-end">
								<a class="btn btn-primary me-2" onclick="updateBook()"
									href="#" role="button">Update</a> <a class="btn btn-primary"
									onclick="deleteBook()" href="#" role="button">Delete</a>

							</div>
						</td>
					</tr>
	 <form action="getAll">			
					<tr>
						<th></th>
						<th>ID</th>
						<th>Name</th>
						<th>Author</th>
						<th>Price</th>
						
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${empty bookList}">
							<tr>
								<td colspan="6">No books found.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr></tr>
							<c:forEach items="${bookList}" var="book">
								<tr>
									<td align="center"><input type="checkbox"
										class="book-checkbox" value="${book.id}"
										style="align: middle" onclick="handleCheckboxClick(this)"></td>
									<td><c:out value="${book.id}"/></td>
									<td><c:out value="${book.name}"/></td>
									<td><c:out value="${book.author}"/></td>
									<td><c:out value="${book.price}"/></td>
									
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</div>
</form>
</body>

<script>
	function handleCheckboxClick(clickedCheckbox) {
		var checkboxes = document.querySelectorAll('.book-checkbox');
		checkboxes.forEach(function(checkbox) {
			if (checkbox !== clickedCheckbox) {
				checkbox.checked = false;
			}
		});
	}
	function deleteBook() {
		var checkedCheckbox = document
				.querySelector('.book-checkbox:checked');
		if (checkedCheckbox) {
			$('#deletePopup').modal('show');

		} else {
			console.log("No book selected for deletion.");
		}
	}
	function confirmDelete() {
		var checkedCheckbox = document
				.querySelector('.book-checkbox:checked');
		if (checkedCheckbox) {
			document.form.idtodelete.value = checkedCheckbox.value;
			document.form.submit();
		}
	}
	function updateBook() {
		var checkedCheckbox = document
				.querySelector('.book-checkbox:checked');
		if (checkedCheckbox) {
			var updateUrl = 'getBook?id=' + checkedCheckbox.value;
			//var updateUrl = 'getBook/' + checkedCheckbox.value;
			window.location.href = updateUrl;
		} else {
			console.log("No Book selected for update.");
		}
	}
</script>



</html>
