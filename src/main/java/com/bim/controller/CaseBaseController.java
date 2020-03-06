package com.bim.controller;

import com.bim.dto.CaseBaseDto;
import com.bim.entry.BIMProject;
import com.bim.entry.CaseEntry;
import com.bim.service.CaseBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @CrossOrigin(origins="*",maxAge=3600)
    @RequestMapping(value="/getProjects",method=RequestMethod.GET)
    public @ResponseBody List<CaseBaseDto> getProject() {
        List<CaseBaseDto> projects = new ArrayList<>();
        projects = caseBaseService.getCasesSortByName();
        return projects;
    }

    @CrossOrigin(origins="*",maxAge=3600)
    @RequestMapping(value="/getProject/{id}",method=RequestMethod.GET)
    public @ResponseBody CaseBaseDto getProjectbyId(@PathVariable("id") String id) {
        CaseBaseDto project = new CaseBaseDto();
        project = caseBaseService.getCaseBaseById(id);
        return project;
    }
}
