package com.nicokuchling.wegfest.survey_service.services;

import com.nicokuchling.wegfest.api.core.survey.MultipleChoiceQuestion;
import com.nicokuchling.wegfest.api.core.survey.SurveyResponse;
import com.nicokuchling.wegfest.api.core.survey.SurveyService;
import com.nicokuchling.wegfest.api.exceptions.InvalidInputException;
import com.nicokuchling.wegfest.api.exceptions.NotFoundException;
import com.nicokuchling.wegfest.shared.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class SurveyServiceImpl implements SurveyService {

    private static final Logger LOG = LoggerFactory.getLogger(SurveyServiceImpl.class);

    private ServiceUtil serviceUtil;

    @Autowired
    public SurveyServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }


    @Override
    public Set<MultipleChoiceQuestion> getAllMultipleChoiceQuestions() {
        LOG.debug("/survey/multiple-choice-question return all available questions");

        MultipleChoiceQuestion question1 = new MultipleChoiceQuestion(
                1,
                "question-1",
                Arrays.asList("answer-1", "answer-2"),
                serviceUtil.getServiceAddress());

        MultipleChoiceQuestion question2 = new MultipleChoiceQuestion(
                2,
                "question-2",
                Arrays.asList("answer-1", "answer-2"),
                serviceUtil.getServiceAddress());

        Set<MultipleChoiceQuestion> questions = new HashSet<>();
        questions.add(question1);
        questions.add(question2);

        return questions;
    }

    @Override
    public SurveyResponse getSurveyResponseByPersonId(int personId) {
        LOG.debug("/survey/response/person return the found survey response for personId={}", personId);

        if(personId < 0) {
            throw new InvalidInputException("Invalid personId: " + personId);
        }

        if(personId == 13) {
            throw new NotFoundException("No person found for personId: " + personId);
        }

        Map<Integer, String> multipleChoiceQuestionIdToAnswerMap = new HashMap<>();
        multipleChoiceQuestionIdToAnswerMap.put(1, "answer-1");
        multipleChoiceQuestionIdToAnswerMap.put(2, "answer-2");

        return new SurveyResponse(
                1,
                1,
                multipleChoiceQuestionIdToAnswerMap, serviceUtil.getServiceAddress());
    }
}
