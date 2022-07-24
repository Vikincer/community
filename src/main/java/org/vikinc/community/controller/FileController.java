package org.vikinc.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.vikinc.community.dto.DTOFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Controller
public class FileController {

    private static final String BASE_PATH = "D:\\Hword\\java\\java_project\\community\\src\\main\\resources\\static\\upload\\";

    private static final String SERVER_PATH = "/upload/";

    @RequestMapping("/file/upload")
    @ResponseBody
    public DTOFile upload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        String filename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-","");
        String newFilename = uuid + "-" + filename;
        File img = new File(BASE_PATH,newFilename);
        try{
            file.transferTo(img);
        }catch (Exception e){
            e.printStackTrace();
        }
        DTOFile dtoFile = new DTOFile();
        dtoFile.setSuccess(1);
        dtoFile.setUrl(SERVER_PATH + newFilename);
        return dtoFile;
    }
}
