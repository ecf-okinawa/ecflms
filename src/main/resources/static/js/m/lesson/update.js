function preview(id){
	//同一画面を更新
	let form = document.createElement('form');
	form.method = 'POST';
	form.action = `/m/lesson/preview/${id}`;
	form.target = '_blank'
	document.body.append(form);
	form.submit();
}