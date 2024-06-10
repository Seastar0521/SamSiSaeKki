package com.example.Sam.service;
import com.example.Sam.entity.allergy;
import com.example.Sam.entity.disease;
import com.example.Sam.entity.member;
import com.example.Sam.repository.AllergyRepository;
import com.example.Sam.repository.DiseaseRepository;
import com.example.Sam.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberrepository;
    @Autowired
    private final AllergyRepository allergyRepository;
    @Autowired
    private final DiseaseRepository diseaseRepository;


    public boolean signup(member member) {

        member member1 =  memberrepository.findById(member.getMemid()).orElse(null);
    if(member1 == null)  {
        allergy allergy = new allergy();
        allergy.setMemid(member.getMemid());
        disease disease = new disease();
        disease.setMemid(member.getMemid());
        allergyRepository.save(allergy);
        diseaseRepository.save(disease);
        memberrepository.save(member);
        return true;
    }
    else {
        return false;
    }
    }

    public member checkid(String id) {
        return memberrepository.findById(id).get();
    }

    public void write(member member) {
    }

    public Object memberList() {
        return null;
    }

    public member login(member member) {
        String id = member.getMemid();
        //System.out.println(memberrepository.existsById(id));
        if(memberrepository.existsById(id)){
            member mem = memberrepository.findById(id).get();
            return mem;
        } else {
            return null;
        }
    }


    public void infosave(member member) {
        memberrepository.save(member);
    }

    public void changeprofile(String id, MultipartFile file) throws Exception {
        if(!file.getOriginalFilename().equals("")){
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\profile";

            UUID uuid = UUID.randomUUID();

            String fileName = uuid + "_" + file.getOriginalFilename();

            File saveFile = new File(projectPath, fileName);

            file.transferTo(saveFile);

            member member = memberrepository.findById(id).get();
            member.setProfilename(fileName);
            member.setProfilepath("/profile/" + fileName);
        }
    }

}

