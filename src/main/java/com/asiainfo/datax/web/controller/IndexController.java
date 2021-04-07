package com.asiainfo.datax.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController
{
    @RequestMapping({"/monitorSearch"})
    public ModelAndView index()
    {
        return new ModelAndView("html/monitorSearch");
    }

    @RequestMapping({"/taskInfoPage"})
    public ModelAndView index2()
    {
        return new ModelAndView("html/taskInfoPage");
    }

    @RequestMapping({"/taskInfoPageTime"})
    public ModelAndView index3()
    {
        return new ModelAndView("html/taskInfoPageTime");
    }

    @RequestMapping({"/chartBar"})
    public ModelAndView index4()
    {
        return new ModelAndView("html/chartBar");
    }

    @RequestMapping({"/list"})
    public ModelAndView index5()
    {
        return new ModelAndView("html/list");
    }
}
