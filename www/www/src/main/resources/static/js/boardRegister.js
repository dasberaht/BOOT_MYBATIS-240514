console.log("boardRegister.js in");

document.getElementById('trigger').addEventListener('click', ()=>{
    document.getElementById('files').click();
});

const regExp = new RegExp("\.(exe|sh|bat|jar|dll|msi)$");   // 첨부 조건설정(실행파일 막기)
const maxSize = 1024*1024*20;

function fileValidation(fileName, fileSize){
    if(regExp.test(fileName)){
        return 0;
    }
    else if(fileSize > maxSize){
        return 0;
    }
    else{
        return 1;
    }
}


document.addEventListener('change', (e)=>{
    if(e.target.id =='files'){
        const fileObject = document.getElementById('files').files;
        console.log(fileObject);

        document.getElementById('regBtn').disabled=false;

        const div = document.getElementById('fileZone');
        div.innerHTML="";


        let ul =`<ul class="list-group list-group-flush">`;

        let isOk = 1;       // 여러 파일에 대한 값을 확인하는 값
        for(let file of fileObject){
            // 개별적으로 파일이 통과하는지 결과 받기
            let validResult = fileValidation(file.name, file.size);
            isOk *= validResult;    // 하나씩 모든 파일에 대한 확인
            ul += `<li class="list-group-item">`;
            ul += `<div class="mb-3">`;
            ul += `${validResult ? '<div class="fw-bold">업로드 가능</div>' : '<div class="fw-bold text-danger">업로드 불가</div>'}`;
            ul += `${file.name}</div>`;
            ul += `<span class="badge rounded-pill text-bg-${validResult ? 'success' : 'danger'}">${file.size} Byte</span>`;
            ul += `</li>`;
        }
        ul += `</ul>`;
        div.innerHTML = ul;

        if(isOk == 0){
            // 파일 중 맞지 않은 값이 있는 경우
            document.getElementById('regBtn').disabled=true;     // 비활성화
        }

    }
})





document.getElementById('logoutLink').addEventListener('click', (e)=>{
    // a tag의 href를 없애는 기능
    e.preventDefault();

    document.getElementById('logoutForm').submit();
})

