console.log("boardComment.js in!");

// (비동기) cmtAddBtn 클릭 시 bno, writer, content를 비동기로 DB에 넣기
document.getElementById('cmtAddBtn').addEventListener('click', ()=>{
    const cmtText = document.getElementById('cmtText').value;
    const cmtWriter = document.getElementById('cmtWriter').innerText;

    if(cmtText == null || cmtText == ""){
        alert("댓글을 입력 후 시도해주세요.")
        document.getElementById('cmtText').focus();
        return false;
    }else{
            let cmtData = {
                bno : bnoVal,
                writer : cmtWriter,
                content : cmtText
            }
            console.log(cmtData);

            postCommentToServer(cmtData).then(result => {
                if(result == '1'){
                    alert("등록 완료");
                    document.getElementById('cmtText').value="";
                    spreadCommentList(bnoVal);
                }
            })

    }

})

// 보내기
async function postCommentToServer(cmtData){
    try {
        const url = "/comment/post";
        const config = {
            method : "post",
            headers : {
                "content-type" : "application/json; charset=utf-8"
            },
            body : JSON.stringify(cmtData)
        }

        const resp = await fetch(url, config);
        const result = await resp.text();

        return result;

    } catch (error) {
        console.log(error);
    }

}

// 가져오기
async function postCommentFromServer(bno, page){
    try {
        const resp = await fetch("/comment/list/" + bno + "/" + page);
        const result = await resp.json();
        return result;
    } catch (error) {
        console.log(error);
    }
}



// 출력하기
// 페이징 처리를 하여, 한 페이지(더보기)에 5개 댓글을 출력
// 전체 게시글 수에 따라, 페이지 수가 필요
function spreadCommentList(bno, page=1){
    postCommentFromServer(bno, page).then(result =>{
        console.log(result);
        const ul = document.getElementById('cmtListArea');
        // ul.innerHTML="";
        if(result.cmtList.length > 0 ){
            if(page==1){
                ul.innerHTML="";
            }

            for(let cvo of result.cmtList){
                let str = `<li class="list-group-item" data-cno="${cvo.cno}">`;
                str += `<div class="input-group mb-3"> No.${cvo.cno}　|`;
                str += `<div class="fw-bold">　${cvo.writer}　</div>`;
                str += `${cvo.content}`;
                str += `</div>`
                str += `<span class="badge rounded-pill text-bg-success">${cvo.regAt}</span> `;
                str += `<button type="button" class="btn btn-warning btn-sm mod" data-bs-toggle="modal" data-bs-target="#myModal">수정</button> `;
                str += `<button type="button" class="btn btn-danger btn-sm del" >삭제</button>`;
                str += `</li>`;
                
                ul.innerHTML += str;
            }
            console.log(result.cmtList);
            console.log(result);
            // 더보기 버튼 작업
            let moreBtn = document.getElementById('moreBtn');
            console.log(moreBtn);
            // moreBtn 표시되는 조건(페이지의 값이 5개 이상이되어야 나타남)
            // ex. 현재 내 페이지 : pgvo.pageNo = 1 / 최종페이지 realEndPage = 3
            // realEndPage 보다 현재 내 페이지가 적으면 표시
            if(result.pgvo.pageNo < result.realEndPage){
                // style = "visibility:hidden"  <->  "visibility:visible"
                moreBtn.style.visibility = 'visible';   // 버튼 표시 설정
                moreBtn.dataset.page = page+1;  // 1페이지 늘리기
            }else{
                moreBtn.style.visibility = 'hidden';
            }
        } else {
            ul.innerHTML = `<div class="input-group mb-3">CommentList Empty</div>`;
        }

    })

}


// 더보기 기능
document.addEventListener('click', (e)=>{
    if(e.target.id == 'moreBtn'){
        let page = parseInt(e.target.dataset.page);
        spreadCommentList(bnoVal, page);
    }

    // 수정 시 모달창을 통해 입력받기
    else if(e.target.classList.contains('mod')){
        // 수정버튼을 누른 댓글의 li를  가져오기
        let li = e.target.closest('li');

        // writer 찾아서 id=modWrihte
        let modWriter = li.querySelector(".fw-bold").innerText;
        document.getElementById('modWriter').innerText= modWriter;


        // nextSibling : 한 부모 안에서, 다음 형제를 찾기
        let cmtText = li.querySelector('.fw-bold').nextSibling;
        console.log(cmtText);
        document.getElementById('cmtTextMod').value = cmtText.nodeValue;

        // 수정 >>> cno dataset으로 달기, cno/content
        document.getElementById('cmtModBtn').setAttribute("data-cno", li.dataset.cno);

    }
    // 수정 처리
    else if(e.target.id == 'cmtModBtn'){
        let cmtModData = {
            cno : e.target.dataset.cno,
            content : document.getElementById('cmtTextMod').value
        }
        console.log(cmtModData);

        // 수정 비동기로 보내기
        modifyCommentToServer(cmtModData).then(result=>{
            if(result == '1'){
                alert("댓글 수정 완료");
                document.querySelector(".btn-close").click();
            }else{
                alert("댓글 수정 실패");
                document.querySelector(".btn-close").click();
            }
            spreadCommentList(bnoVal);

        })
    }


    // 삭제 처리
    else if(e.target.classList.contains('del')){
        // let cnoVal = e.target.dataset.cno;
        // e.target.closest('li').dataset.cno
        let li = e.target.closest('li');
        let cnoVal = li.dataset.cno;
        deleteCommentFromServer(cnoVal).then(result =>{
            if(result == '1'){
                alert("댓글 삭제 완료")
                spreadCommentList(bnoVal);
            }
        })
    }



})



// 수정 보내기
async function modifyCommentToServer(cmtModData){
    try {
        const url = "/comment/modify";
        const config = {
            method : "put",
            headers : {
                "content-type" : "application/json; charset=utf-8"
            },
            body : JSON.stringify(cmtModData)
        }

        const resp = await fetch(url, config);
        const result = await resp.text();

        return result;

    } catch (error) {
        console.log(error);
    }
}


// 삭제
async function deleteCommentFromServer(cnoVal){
    try {
        const url = "/comment/" + cnoVal;
        const config = {
            method : "delete"
        };
        
        const resp = await fetch(url, config);
        const result = await resp.text();

        return result;

    } catch (error) {
        console.log(error);
    }
}




