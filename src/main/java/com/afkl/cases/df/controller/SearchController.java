package com.afkl.cases.df.controller;

import com.afkl.cases.df.model.Location;
import com.afkl.cases.df.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SearchController {


    @Autowired
    private SearchService searchService;

    @RequestMapping("/")
    public String get(Model model) {

        return "searchForm";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam("source") String source,
                                 @RequestParam("destination") String destination,
                         RedirectAttributes redirectAttributes) {
        try {
                model.addAttribute("searchResult", searchService.searchFare(source, destination));
            }catch (Exception e) {
                redirectAttributes.addFlashAttribute("message",
                    "Error fetching result");
         }
        return "redirect:/";
    }

    @RequestMapping(value = "/autosuggest/cities", method = RequestMethod.POST)
    public Location autosuggest(@RequestParam String term) {
        return searchService.searchAirport(term);
    }

}
