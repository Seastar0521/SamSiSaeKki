package com.example.Sam.service;

import com.example.Sam.entity.board;
import com.example.Sam.entity.shareboard;
import com.example.Sam.repository.BoardRepository;
import com.example.Sam.repository.shareBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardrepository;
    @Autowired
    private shareBoardRepository shareboardrepository;

    //글 작성 처리
    public void write(board board, MultipartFile file) throws Exception{
        if(file != null){
            if(!file.getOriginalFilename().equals("")){
                String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

                UUID uuid = UUID.randomUUID();

                String fileName = uuid + "_" + file.getOriginalFilename();

                File saveFile = new File(projectPath, fileName);

                file.transferTo(saveFile);

                board.setFilename(fileName);
                board.setFilepath("/files/" + fileName);
            }
        }

        System.out.println(board);

        boardrepository.save(board);
    }

    //게시글 리스트 처리
    public Page<board> boardList(Pageable pageable) {
        return boardrepository.findAll(pageable);
    }

    //특정 게시글 불러오기
    public board boardView(Integer id) {
        if(boardrepository.findById(id).isEmpty())
            return null;
        else
            return boardrepository.findById(id).get();
    }

    //게시글 지우기
    public void boardDelete(Integer id) {
        boardrepository.deleteById(id);
    }
    
    //게시글 공개 처리
    public void boardShare(Integer id) throws Exception{
        board board = boardrepository.findById(id).get();
        shareboard shareboard = new shareboard();
        shareboard.setTitle(board.getTitle());
        shareboard.setContent(board.getContent());
        shareboard.setFilename(board.getFilename());
        shareboard.setFilepath(board.getFilepath());

        shareboardrepository.save(shareboard);
    }
}
