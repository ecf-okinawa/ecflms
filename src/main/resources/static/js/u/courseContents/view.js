//受講ボタン押下
function pushAttendance(courseId,lessonId){
	let form = document.createElement('form');
	document.body.append(form);
	form.action = `/u/lesson/view/${courseId}/${lessonId}`;
	form.method = 'GET';
	form.submit();
}