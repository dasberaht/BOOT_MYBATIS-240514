console.log("boardDetail.js in")


document.getElementById('listBtn').addEventListener('click', ()=>{
    location.href="/board/list";
})




document.getElementById('delBtn').addEventListener('click', ()=>{
    document.getElementById('delForm').submit();
})

document.getElementById('modBtn').addEventListener('click', ()=>{
    document.getElementById('title').readOnly=false;
    document.getElementById('content').readOnly=false;


    // 별도의 modBtn 을 생성
    let modBtn = document.createElement('button');          // <<<    <button></button> 처리
    modBtn.setAttribute('type', 'submit');                  // <<<    <button type="submit"></button>
    modBtn.classList.add('btn', 'btn-outline-warning');
    modBtn.innerText="Submit";

    // 생성한 modBtn을 modForm 영역 하단에 추가
    document.getElementById('modForm').appendChild(modBtn);

    // modBtn과 delBtn을 임시 삭제
    document.getElementById('modBtn').remove();
    document.getElementById('delBtn').remove();


})



