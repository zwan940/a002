package com.bim.controller;

import com.bim.dto.CaseDetailDto;
import com.bim.service.CaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller @RequestMapping("/detail")
public class CaseDetailController {
    @Autowired
    private CaseDetailService caseDetailService;

    @ResponseBody @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public CaseDetailDto handleDetail(@PathVariable("id") String caseId){
        return caseDetailService.getCaseDetailByCaseId(caseId);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView init(@RequestParam String id){
        ModelAndView result = new ModelAndView();
        if (Objects.equals("", id)){
            result.setViewName("index");
        } else {
            result.setViewName("detail");
            result.addObject("id", id);
        }
        return result;
    }
}
