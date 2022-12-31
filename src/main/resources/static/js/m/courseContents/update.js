//JSONデータ作成
function makeJson(){
	let courseContents = [];
	//講座ID取得
	let courseId = document.getElementById('sections').dataset.courseid;
	//セクション情報取得
	let element = document.getElementById('sections');
	let sections = element.children;
	//新規ID採番用
	let tmpLessonId = -500;
	let tmpSectionId = -500;
	let tmpSectionName = '';
	//セクションからレッスン情報取得
	let order = 1;
	for( let i = 0; i < sections.length; i++){
		let section = sections[i];
		let lessons = section.children[1].children;
		let sectionHeader = section.firstElementChild;
		let sectionName = sectionHeader.children[1].innerHTML;
		//セクションID決定(新規も考慮)
		let sectionId = section.getAttribute('data-sectionid');
		if(sectionId == '0') {
			sectionId = tmpSectionId++;
		}
		//各レッスンを取得
		for( let j = 0; j < lessons.length; j++){
			let lesson = lessons[j];
			//セクションID決定(新規も考慮)
			let lessonId = lesson.getAttribute('data-lessonid');
			let lessonTitle = lesson.children[1].innerHTML;
			if(lessonId == '0') lessonId = tmpLessonId++;
			courseContents.push({
				courseId:courseId,
				sectionId:sectionId,
				lessonId:lessonId,
				orderNo:order++,
				sectionName:sectionName,
				lessonTitle:lessonTitle
			});
		}
	}
	return {
		"courseId": courseId,
		"courseContents": courseContents
	};
}

//メッセージセット
function setMessage(messages){
	deleteMessage();
	let messageArea = document.getElementById('message-area');
	for( let message of messages ){
		let messageItem = document.createElement('li');
		messageItem.innerText = message;
		messageArea.appendChild(messageItem);
	}
	messageArea.style.display = 'block';
}

//メッセージ削除
function deleteMessage(){
	let messageArea = document.getElementById('message-area');
	messageArea.innerHTML = '';
	messageArea.style.display = 'none';
}

//各レッスンの上ボタン押下
function pushUp(element){
	//レッスンdiv取得
	let lesson = element.parentElement;
	//レッスンの親要素
	let lessons = lesson.parentElement;
	//自分の前の要素があるか？
	if( lesson.previousElementSibling == null ){
		let nowSection = lessons.parentElement;	//現セクション
		//前のセクションがあるか
		if( nowSection.previousElementSibling == null ){

		} else {
			//あれば1つ前のセクションの一番後ろへ
			let beforeSection = nowSection.previousElementSibling;
			//前のセクションのレッスン
			let beforeLessons = beforeSection.children[1];
			beforeLessons.insertBefore(lesson,null);
		}

	} else {
		//あれば1つ前に移動
		lessons.insertBefore(lesson,lesson.previousElementSibling);
	}
	sortNumber();
}

//各レッスンの下ボタン押下
function pushDown(element){
	//レッスンdiv取得
	let lesson = element.parentElement;
	//レッスンの親要素
	let lessons = lesson.parentElement;
	//自分の次の要素があるか
	let nextLesson = lesson.nextElementSibling;
	if( nextLesson != null ){
		//あるなら次の要素の下へ
		//次の次の前に入れないといけないので、次の次をチェック
		if( nextLesson.nextElementSibling != null ){
			lessons.insertBefore(lesson,nextLesson.nextElementSibling);
		} else {
			//ないなら末尾
			lessons.insertBefore(lesson,null);
		}
	} else {
		let nowSection = lessons.parentElement;	//現セクション
		//次のセクションがあるか
		let nextSection = nowSection.nextElementSibling;
		if( nextSection != null ){
			//あれば次のレッスンリストの先頭へ
			let nextLessons = nextSection.children[1];
			nextLessons.insertBefore(lesson,nextLessons.firstChild);
		} else {
			//次のセクション無ければ何もしない
		}
	}
	sortNumber();
}

//番号を並べる
function sortNumber(){
	//セクション情報取得
	let element = document.getElementById('sections');
	let sections = element.children;
	//セクションからレッスン情報取得
	let order = 1;
	for( let i = 0; i < sections.length; i++){
		let section = sections[i];
		let sectionHeader = section.children[0];
		let lessons = section.children[1].children;
		//セクション番号設定
		let sectionNo = sectionHeader.firstElementChild;
		sectionNo.innerHTML = 'セクション' + (i+1);
		//各レッスンを取得
		for( let j = 0; j < lessons.length; j++){
			let lesson = lessons[j];
			let lessonNo = lesson.children[0];
			lessonNo.innerHTML = 'レッスン' + (order++);
		}
	}
}

//レッスン追加ボタン押下時
function addNewLesson(element){
	//レッスン名を取得
	let newLessonName = element.previousElementSibling.value;
	if( newLessonName == '' ){
		setMessage(['レッスン名を入力してください']);
		return;
	}
	//押されたセクションを特定(2つ上の親)
	let section = element.parentElement.parentElement;
	//セクションNo取得
	let sectionNo = section.getAttribute('data-sectionid');
	//レッスン一覧
	let lessons = section.children[1];
	//該当セクションの末尾に追加
	let lesson = createLesson(sectionNo,0,lessons.children.length+1,newLessonName);
	lessons.appendChild(lesson);
	//テキストボックスクリア
	element.previousElementSibling.value = '';
	//番号ソート
	sortNumber();
}

//レッスン作成
function createLesson(sectionId,lessonId,no,title){
	let lesson = document.createElement('div');
	lesson.className = 'lesson';
	lesson.setAttribute('data-sectionid',sectionId);
	lesson.setAttribute('data-lessonid',lessonId);
	let inner = `<div class="lesson-no">レッスン${no}</div>`;
	inner += `<div class="lesson-title">${title}</div>`;
	inner += `<button onclick="pushUp(this)">△</button>`;
	inner += `<button onclick="pushDown(this)">▽</button>`;
	inner += `<button onclick="deleteLesson(this)">削除</button>`;
	inner += `<button th:onclick="pushEdit(${lessonId})" disabled>編集</button>`;
	lesson.innerHTML = inner;
	return lesson;
}

//セクション追加
function addNewSection(){
	//名前取得
	let newSectionName = document.getElementById('newSectionName').value;
	if( newSectionName == '' ){
		setMessage(['セクション名を入力してください']);
		return;
	}
	//現在のセクション数
	let sections = document.getElementById('sections');
	let sectionList = sections.children;
	let newSection = createSection(0,newSectionName,sectionList.length+1);
	sections.appendChild(newSection);
	//セクション名のテキストボックスクリア
	document.getElementById('newSectionName').value = '';
}

//セクション作成
function createSection(sectionId,title,no){
	let section = document.createElement('div');
	section.className = 'section';
	section.setAttribute('data-sectionid',sectionId);
	let inner = `<div class="section-header">`;
	inner += `<div class="section-no">セクション${no}</div>`;
	inner += `<div class="section-title">${title}</div>`;
	inner += `<button onclick="pushUpSection(this)">△</button>`;
	inner += `<button onclick="pushDownSection(this)">▽</button>`;
	inner += `<button onclick="deleteSection(this)">削除</button>`;
	inner += `</div>`;
	inner += `<div id="lessons"${sectionId}">`;
	inner += `</div>`;
	inner += `<div class="addLesson lesson">`;
	inner += `<input type="text" id="newLessonName" />`;
	inner += `<button onclick="addNewLesson(this)">レッスンを追加</button>`;
	inner += `</div>`;
	section.innerHTML = inner;
	return section;
}

//各セクションの上ボタン押下
function pushUpSection(element){
	//セクションdiv取得
	let section = element.parentElement.parentElement;
	//レッスンの親要素
	let sections = section.parentElement;
	//自分の前の要素があるか？
	if( section.previousElementSibling == null ){
		//無ければ何もしない
	} else {
		//あれば1つ前に移動
		sections.insertBefore(section,section.previousElementSibling);
	}
	sortNumber();
}

//各セクションの下ボタン押下
function pushDownSection(element){
	//セクションdiv取得
	let section = element.parentElement.parentElement;
	//レッスンの親要素
	let sections = section.parentElement;
	//自分の次の要素があるか
	let nextSection = section.nextElementSibling;
	if( nextSection != null ){
		//あるなら次の要素の下へ
		//次の次の前に入れないといけないので、次の次をチェック
		if( nextSection.nextElementSibling != null ){
			sections.insertBefore(section,nextSection.nextElementSibling);
		} else {
			//ないなら末尾
			sections.insertBefore(section,null);
		}
	}
	sortNumber();
}

//レッスン削除
function deleteLesson(element){
	let lesson = element.parentElement;
	lesson.remove();
	sortNumber();
}

//セクション削除
function deleteSection(element){
	let section = element.parentElement.parentElement;
	section.remove();
	sortNumber();
}

//コース情報の更新
function updateCourse(){
	let xhr = new XMLHttpRequest();
	let data = makeJson();
	xhr.open('POST','/m/course/content/doupdate');
	//ヘッダー設定
	xhr.setRequestHeader('Content-Type', 'application/json');
	//正常送信時処理
	xhr.onload = () => {
		//同一画面を更新
		let form = document.createElement('form');
		form.method = 'GET';
		form.action = `/m/course/content/update/${data.courseId}`;
		document.body.append(form);
		form.submit();
	};

	//送信
	xhr.send(JSON.stringify(data.courseContents));
}

//編集ボタン押下
function pushEdit(lessonId){
	let form = document.createElement('form');
	document.body.append(form);
	form.action = `/m/lesson/update/${lessonId}`;
	form.method = 'POST';
	form.submit();
}