package org.openmrs.module.blooddonationmanager.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.blooddonationmanager.Donor;
import org.openmrs.module.blooddonationmanager.Questionnaire;
import org.openmrs.module.blooddonationmanager.api.BloodDonationManagerService;
import org.openmrs.module.blooddonationmanager.api.QuestionnareService;
import org.openmrs.module.blooddonationmanager.api.DonorService;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.*;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
@RequestMapping("/module/blooddonationmanager/questionnare.form")
public class QuestionnareController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());


	@RequestMapping(method=RequestMethod.GET)
	public String main(ModelMap model){
        //Donor donor=new Donor();
		Person person = new Person();
        DonorService donorService=Context.getService(DonorService.class);
        PersonService personService= Context.getPersonService();
		model.addAttribute("donor", person);

		List<Donor> donorList=donorService.getAllDonors();
		Map<Integer,Person>personMap=new HashMap<Integer,Person>();


		for(Donor donor:donorList){
			personMap.put(donor.getDonorId(), personService.getPerson(donor.getPersonId()));
		}

		model.addAttribute("map", personMap);
		model.addAttribute("donorList", donorList);
		model.addAttribute("user", Context.getAuthenticatedUser());

		return "/module/blooddonationmanager/questionnare";
	}


	@RequestMapping(method=RequestMethod.POST)
	public String processSubmit(ModelMap map, HttpSession httpSession,
                                @RequestParam("healthy") String donorId,
	                            @RequestParam("healthy") String healthy,
								@RequestParam("malaria") String malaria,
	                            @RequestParam("pregnant") String pregnant,
								@RequestParam("allergy") String allergy,
								@RequestParam("onMedication") String onMedication,
	                            @RequestParam("onAsprin") String onAsprin,
	                            @RequestParam("donatedBlood") String donatedBlood,
	                            @RequestParam("bloodTransfusion") String bloodTransfusion,
	                            @RequestParam("organTransplant") String organTransplant,
	                            @RequestParam("needleStick") String needleStick,
	                            @RequestParam("syphilisGonorrhea") String syphilisGonorrhea,
	                            @RequestParam("earBodyPiercing") String earBodyPercing,
	                            @RequestParam("tatoo") String tatoo,
	                            @RequestParam("questionnareDate") String questionnareDate
	                            ) throws ParseException{

        Person person = new Person();


		QuestionnareService questionnareService=Context.getService(QuestionnareService.class);

		BloodDonationManagerService bbService = Context.getService(BloodDonationManagerService.class);


        try{



			SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
			//String dateInString = "7-Jun-2013";

			try {

				Date date = formatter.parse(questionnareDate);


				Questionnaire questionnaire = new Questionnaire();
				int donorID = Integer.parseInt(donorId);
				//Saving the details

				questionnaire.setAllergy(allergy);
                questionnaire.setDonorId(donorID);
                questionnaire.setMalaria(malaria);
				questionnaire.setBloodTransfusion(bloodTransfusion);
				questionnaire.setDonatedBlood(donatedBlood);
				questionnaire.setHealthy(healthy);
				questionnaire.setEarBodyPiercing(earBodyPercing);
				questionnaire.setOnMedication(onMedication);
				questionnaire.setPerson(person);
				questionnaire.setOrganTransplant(organTransplant);
				questionnaire.setPregnant(pregnant);
				questionnaire.setQuestionnareDate(date);
				questionnaire.setTatoo(tatoo);
                questionnaire.setOnAsprin(onAsprin);



				//save questionnare
				questionnareService.saveQuestionnare(questionnaire);
				httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Added Donor Questionnare Successfully");

			}
			catch (ParseException e) {
				e.printStackTrace();
			}
        }
        catch (Exception ex)
        {
            httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Error adding donor questionnare");
            return "redirect:questionnareForm.form";
        }

        return "redirect:questionnareForm.form";

    }

			}



