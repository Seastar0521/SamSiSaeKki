package com.example.Sam.service;

import com.example.Sam.entity.shareboard;
import com.example.Sam.repository.shareBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class shareBoardService {

    @Autowired
    private shareBoardRepository shareBoardRepository;

    //글 작성 처리
    public void write(shareboard board, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        shareBoardRepository.save(board);
    }

    //게시글 리스트 처리
    public List<shareboard> shareboardList() {
        return shareBoardRepository.findAll();
    }

    //특정 게시글 불러오기
    public shareboard boardView(Integer id) {
        if(shareBoardRepository.findById(id).isEmpty())
            return null;
        else
            return shareBoardRepository.findById(id).get();
    }

    public void boardDelete(Integer id) {
        shareBoardRepository.deleteById(id);
    }
}
