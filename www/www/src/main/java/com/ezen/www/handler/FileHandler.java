package com.ezen.www.handler;

import com.ezen.www.domain.FileVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class FileHandler {

    private String UP_DIR="D:\\W\\SpringBoot\\_myproject\\_java\\_fileupload\\";

    public List<FileVO> uploadFiles(MultipartFile[] files){

        List<FileVO> flist = new ArrayList<>();
        LocalDate date = LocalDate.now();
        String today = date.toString().replace("-", File.separator);
        File folders = new File(UP_DIR, today);

        // folder 생성 완료
        if(!folders.exists()){
            folders.mkdirs();
        }

        for(MultipartFile file : files){
            FileVO fvo = new FileVO();
            fvo.setSaveDir(today);
            fvo.setFileSize(file.getSize());
            String originalFileName = file.getOriginalFilename();
            String onlyFileName = originalFileName.substring(originalFileName.lastIndexOf(File.separator)+1);
            fvo.setFileName(onlyFileName);

            UUID uuid = UUID.randomUUID();
            String uuidStr = uuid.toString();
            fvo.setUuid(uuidStr);

            // 디스크에 저장 설정
            String fullFileName = uuidStr+"_"+onlyFileName;
            File storeFile = new File(folders, fullFileName);

            try {
                file.transferTo(storeFile);
                if(isImageFile(storeFile)){
                    fvo.setFileType(1);
                    File thumbnail = new File(folders,uuidStr+"_"+onlyFileName);
                    Thumbnails.of(storeFile).size(75, 75).toFile(thumbnail);
                }
            }catch (Exception e){
                log.info("file error");
                e.printStackTrace();
            }

            flist.add(fvo);

        }

        return flist;
    }

    private boolean isImageFile(File file) throws IOException {
        String mimeType = new Tika().detect(file);
        return mimeType.startsWith("static/image") ? true : false;
    }

}
