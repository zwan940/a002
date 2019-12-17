package com.bim.controller;

import com.bim.dto.CaseBaseDto;
import com.bim.service.CaseBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller @RequestMapping("/")
public class CaseBaseController {

    @Autowired
    private CaseBaseService caseBaseService;

    @RequestMapping(value = "")
    public String init(){
        return "forward:/index";
    }

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/sortByName") @ResponseBody
    public List<CaseBaseDto> handleSortByName(){
        return caseBaseService.getCasesSortByName();
    }

    @RequestMapping("/sortByDate") @ResponseBody
    public List<CaseBaseDto> handleSortByDate(){
        return caseBaseService.getCasesSortByDate();
    }

    @RequestMapping("/sortByKeyword") @ResponseBody
    public List<CaseBaseDto> handleSortByKeyword(
            @RequestParam("keyword") String keyword){
        return caseBaseService.getCasesSortByKeyword(keyword);
    }
}
