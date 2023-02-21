
function OnDoneButtonClick(id, userid, done) {
  console.log(id);
  console.log(userid);
  console.log(document.location.href);
  pos = document.location.href.lastIndexOf('/');  // 현재 위치한 url 문자열에서 뒤에서부터 '/'를 찾는다
  url = document.location.href.substr(0, pos);    // url변수에 포트번호까지만 잘라낸다

  url = url + '/updatetodo' + '?id=' + id + '&userid=' + userid + '&done=' + done;   // 업데이트를 호출할 url을 만든다
  // url = url + '/todos?userid=' + userid;
  console.log(url);


  var data = new FormData();
  var xhr = new XMLHttpRequest();
  xhr.withCredentials = true;

  xhr.addEventListener("readystatechange", function() {
  if(this.readyState === 4) {
    //document.write(this.responseText);
    pos = document.location.href.lastIndexOf('/');  // 현재 위치한 url 문자열에서 뒤에서부터 '/'를 찾는다
    url = document.location.href.substr(0, pos);
    console.log(url);
    url = url + '/todos?userid=' + userid;
    window.location=url;
  }
});

  xhr.open("POST", url);
  xhr.send(data);



//  let formdata = new FormData();
//var requestOptions = {
//  method: 'POST',
//  body: formdata,
//  redirect: 'follow'
//};
//fetch(url, requestOptions)
//  .then(response => response.text())
//  .then(result => console.log(result))
//  .catch(error => console.log('error', error));
}