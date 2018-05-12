package com.simBowlingApp.controllers;


import com.simBowlingApp.entities.RoundScore;
import com.simBowlingApp.service.simulationEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SimpleController
{

    @Autowired
    private simulationEngine simulationEngine;

    @RequestMapping("/Score")
    @ResponseBody
    public List<RoundScore> getRoundScore()
    {
        simulationEngine.simRound();

        return simulationEngine.getResponseList();

    }


    @RequestMapping("/Reset")
    @ResponseBody
    public void resetGame()
    {
        simulationEngine.reset();

    }

}
