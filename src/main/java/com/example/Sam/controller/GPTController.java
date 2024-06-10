package com.example.Sam.controller;

import com.example.Sam.entity.allergy;
import com.example.Sam.entity.board;
import com.example.Sam.entity.disease;
import com.example.Sam.entity.member;
import com.example.Sam.repository.AllergyRepository;
import com.example.Sam.repository.BoardRepository;
import com.example.Sam.repository.DiseaseRepository;
import com.example.Sam.service.MemberService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
public class GPTController {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private AllergyRepository allergyRepository;
    @Autowired
    private DiseaseRepository diseaseRepository;
    @Autowired
    private MemberService memberService;

    @GetMapping("/goToGPTInput")
    public ModelAndView goToGPTInput(@CookieValue(value = "id", defaultValue = "") String memid ,Model model) {
        ModelAndView modelAndView = new ModelAndView("GPTinput");
        String valueToSet = "이곳에 CHAT GPT의 대답이 출력됩니다.";
        modelAndView.addObject("requestValue", valueToSet);
        String allergyString = "";
        String diseaseString = "";
        allergy allergy = allergyRepository.findById(memid).get();
        disease disease = diseaseRepository.findById(memid).get();

        String all = "";
        if(allergy.getEgg()) {
            all = "계란";
            allergyString += all;
        }
        if(allergy.getMilk()) {
            all = " 우유";
            allergyString += all;
        }
        if(allergy.getWheat()) {
            all = " 밀";
            allergyString += all;
        }
        if(allergy.getBean()) {
            all = " 콩";
            allergyString += all;
        }
        if(allergy.getPeanut()) {
            all = " 땅콩";
            allergyString += all;
        }
        if(allergy.getChestnut()) {
            all = " 밤";
            allergyString += all;
        }
        if(allergy.getFish()) {
            all = " 생선";
            allergyString += all;
        }
        if(allergy.getClam()) {
            all = " 조개";
            allergyString += all;
        }
        if(allergy.getCrab()) {
            all = " 갑각류";
            allergyString += all;
        }
        if(allergy.getNut()) {
            all = " 견과류";
            allergyString += all;
        }
        if(allergy.getPeach()) {
            all = " 복숭아";
            allergyString += all;
        }

        if(disease.getDiabete()) {
            all = " 당뇨병";
            diseaseString += all;
        }
        if(disease.getHighbloodpressure()) {
            all = " 고혈압";
            diseaseString += all;
        }
        if(disease.getHeartdisease()) {
            all = " 심장질환";
            diseaseString += all;
        }
        if(disease.getObesity()) {
            all = " 비만";
            diseaseString += all;
        }
        if(disease.getOsteoporosis()) {
            all = " 골다공증";
            diseaseString += all;
        }
        if(disease.getAnemia()) {
            all = " 빈혈";
            diseaseString += all;
        }
        if(disease.getUrinarystones()) {
            all = " 요로결석";
            diseaseString += all;
        }
        if(disease.getGout()) {
            all = " 통풍";
            diseaseString += all;
        }

        member member = memberService.checkid(memid);
        model.addAttribute("member", member);
        model.addAttribute("allergy", allergyString);
        model.addAttribute("disease", diseaseString);
        return modelAndView;
    }



    @PostMapping("/makedietplan")
    @ResponseBody
    public Map<String, String> getDietPlan(@CookieValue(value = "id", defaultValue = "") String memid ,@RequestBody Map<String, String> requestMap) throws Exception {

        String allergyString = "";
        String diseaseString = "";
        allergy allergy = allergyRepository.findById(memid).get();
        disease disease = diseaseRepository.findById(memid).get();

        String all = "";
        if(allergy.getEgg()) {
            all = "계란";
            allergyString += all;
        }
        if(allergy.getMilk()) {
            all = " 우유";
            allergyString += all;
        }
        if(allergy.getWheat()) {
            all = " 밀";
            allergyString += all;
        }
        if(allergy.getBean()) {
            all = " 콩";
            allergyString += all;
        }
        if(allergy.getPeanut()) {
            all = " 땅콩";
            allergyString += all;
        }
        if(allergy.getChestnut()) {
            all = " 밤";
            allergyString += all;
        }
        if(allergy.getFish()) {
            all = " 생선";
            allergyString += all;
        }
        if(allergy.getClam()) {
            all = " 조개";
            allergyString += all;
        }
        if(allergy.getCrab()) {
            all = " 갑각류";
            allergyString += all;
        }
        if(allergy.getNut()) {
            all = " 견과류";
            allergyString += all;
        }
        if(allergy.getPeach()) {
            all = " 복숭아";
            allergyString += all;
        }

        if(disease.getDiabete()) {
            all = " 당뇨병";
            diseaseString += all;
        }
        if(disease.getHighbloodpressure()) {
            all = " 고혈압";
            diseaseString += all;
        }
        if(disease.getHeartdisease()) {
            all = " 심장질환";
            diseaseString += all;
        }
        if(disease.getObesity()) {
            all = " 비만";
            diseaseString += all;
        }
        if(disease.getOsteoporosis()) {
            all = " 골다공증";
            diseaseString += all;
        }
        if(disease.getAnemia()) {
            all = " 빈혈";
            diseaseString += all;
        }
        if(disease.getUrinarystones()) {
            all = " 요로결석";
            diseaseString += all;
        }
        if(disease.getGout()) {
            all = " 통풍";
            diseaseString += all;
        }

        Map<String, String> responseMap = new HashMap<>();

        ModelAndView modelAndView = new ModelAndView("GPTresult");

        // OpenAI API 엔드포인트 URL
        String url = "https://api.openai.com/v1/chat/completions";

        // OpenAI API 키
        String apiKey = "sk-proj-CnWwy9nRlxZc7xbmgoa3T3BlbkFJSf4FHaNPNw382iRTLI8c";

        // 만약 API 키가 null이면 오류 처리
        if (apiKey == null) {
            responseMap.put("error", "API 키를 찾을 수 없습니다. 시스템 변수에 apikey를 설정하세요.");
            return responseMap;
        }

        // 요청 바디 생성
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "ft:gpt-3.5-turbo-1106:personal:ccap-1:9WPhhF0g");

        JsonArray messages = new JsonArray();

        JsonObject systemMessage = new JsonObject();
        systemMessage.addProperty("role", "system");
        systemMessage.addProperty("content", "사용자의 정보와 요구사항에 맞춰서 식단을 생성해라.");
        messages.add(systemMessage);

        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        member member = memberService.checkid(memid);
        userMessage.addProperty("content", "식단 만들어줘.\n" +
                "/사용자 정보: " + " 키: " + member.getMemheight() + " 몸무게: " + member.getMemweight() + " 알레르기: " + allergyString + " 질병: " + diseaseString + "\n" +
                "/사용자의 요구사항: " + requestMap.get("userRequest"));
        messages.add(userMessage);

        System.out.println(messages);
        requestBody.add("messages", messages);

        // HTTP 연결 설정
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + apiKey);
        con.setDoOutput(true);

        // 요청 바디 전송
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.write(requestBody.toString().getBytes("UTF-8"));
            wr.flush();
        }

        // 응답 코드 확인
        int responseCode = con.getResponseCode();
        System.out.println("응답 코드: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // 응답 읽기
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // 응답 출력
                String responseString = response.toString();
                System.out.println(responseString);

                // JSON 파싱
                JSONObject jsonObject = new JSONObject(responseString);
                JSONArray choicesArray = jsonObject.getJSONArray("choices");
                JSONObject firstChoice = choicesArray.getJSONObject(0);
                JSONObject message = firstChoice.getJSONObject("message");
                String contentValue = message.getString("content");

                //board board = new board();
                //board.setContent(contentValue);
                //board.setTitle("^^ㅣ");
                //boardRepository.save(board);

                /*board board = boardRepository.getById(134);
                board.setFilename(contentValue);
                boardRepository.save(board);*/
    
                responseMap.put("dietPlan", contentValue);
            }
        } else {
            // 오류 메시지 확인
            StringBuilder errorResponse = new StringBuilder();
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                String errorInputLine;
                while ((errorInputLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorInputLine);
                }
            }
            System.out.println("오류 메시지: " + con.getResponseMessage());
            System.out.println("상세 오류 내용: " + errorResponse.toString());

            responseMap.put("error", "식단 생성 중 오류가 발생했습니다.");
        }

        return responseMap;
    }


}
