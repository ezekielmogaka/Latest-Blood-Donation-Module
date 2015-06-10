package org.openmrs.module.blooddonationmanager.web.controller.patient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.PatientService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.blooddonationmanager.Donor;
import org.openmrs.module.blooddonationmanager.api.BloodDonationManagerService;
import org.openmrs.module.blooddonationmanager.api.DonorService;
import org.openmrs.module.blooddonationmanager.api.model.PreparedDonorId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.PersonName;


@Controller
@RequestMapping("/module/blooddonationmanager/addOrUpdate.form")
public class AddOrUpdateDonorController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());

	/*@RequestMapping("/module/BloodDonationManager/addOrUpdate.form")
	public void test(){

		System.out.println("testing");
	}*/


	@ModelAttribute("donorIdentifier")
	public String getDonorId(){
		return getNewIdentifier(getDonorIdPrefix());
	}

	@ModelAttribute("donorIdPrefix")
	public String getDonorIdPrefix(){
		return Context.getAdministrationService().getGlobalProperty("billing.identifier_prefix");
	}

	@ModelAttribute("patientIdentifier")
	public String getPatientIdentifier(){
		return getNewIdentifier(getDonorIdPrefix());
	}

	@RequestMapping(method=RequestMethod.GET)
	public String main(ModelMap model){
		Patient pat = new Patient();
		model.addAttribute("donor", pat);
		return "/module/blooddonationmanager/patient/addOrUpdateDonorForm";
	}

	@RequestMapping(value = "/module/blooddonationmanager/addOrUpdate.form", method = RequestMethod.GET)
	public void listDonors(ModelMap model) {


		PersonService personService=Context.getService(PersonService.class);
		DonorService donorService=Context.getService(DonorService.class);
		List<Donor> donorList=donorService.getAllDonors();
		Map<Integer,Person>personMap=new HashMap<Integer,Person>();


		for(Donor donor:donorList){
			personMap.put(donor.getDonorId(), personService.getPerson(donor.getPersonId()));
		}

		model.addAttribute("map", personMap);
		model.addAttribute("donorList", donorList);
		model.addAttribute("user", Context.getAuthenticatedUser());
	}

	@RequestMapping(method=RequestMethod.POST)
	public String processSubmit(ModelMap map,
								@RequestParam("donorName") String donorName,
								@RequestParam(value="dobEstimated", required=false) Boolean dobEstimated,
								@RequestParam(value="existPat", required=false) String existPat,
								@RequestParam("donId") String donId,
								@RequestParam("patId") String patId,
								@RequestParam("donorAddress1") String donorAddress1,
								@RequestParam(value="donorAddress2", required=false) String donorAddress2,
								@RequestParam("cityVillage") String cityVillage,
								@RequestParam("country") String country,
								@RequestParam("countyDistrict") String countyDistrict,
								@RequestParam("neighborhoodCell") String neighborhoodCell,
								@RequestParam("postalCode") String postalCode,
								@RequestParam("region") String region,
								@RequestParam("stateProvince") String stateProvince,
								@RequestParam("subregion") String subregion,
								@RequestParam("townshipDivision") String townshipDivision,
								@RequestParam(value="donorPrepId", required=false) String donorPrepId,
								@RequestParam(value="preregistered", required=false) String preregistered,
								@RequestParam("fatherHusbandName") String fatherHusbandName,
								@RequestParam("gender") String gender,
								@RequestParam(value = "donorDob", required = false) String donorDob
	) throws ParseException{

		Person person = new Person();
		Donor donor =new Donor();
		//int personIdentifier = Integer.parseInt(patId);



		PersonService personService= Context.getPersonService();
		DonorService donorService=Context.getService(DonorService.class);

		BloodDonationManagerService bbService = Context.getService(BloodDonationManagerService.class);
		Location location = Context.getLocationService().getLocationByUuid("8d6c993e-c2cc-11de-8d13-0010c6dffd0f");

		if(existPat != ""){

			person = personService.getPerson(Integer.valueOf(existPat));

		}else{
			if(preregistered == ""){

				PreparedDonorId pdi = bbService.getPrepDonorIdbyIdentifier(donorPrepId);
				pdi.setUsed(true);
				pdi.setChangedBy(Context.getAuthenticatedUser());
				pdi.setDateChanged(new Date());
				pdi.setIdentifier(donorPrepId);

				bbService.savePreparedId(pdi);

			}


			String[] personName = donorName.split("\\s+");
			PersonName name = new PersonName();
			if(personName.length>2){
				name = new PersonName(personName[0],personName[1],personName[2]);
			}else{
				name.setGivenName(personName[0]);
				if(personName.length>1)
					name.setFamilyName(personName[1]);
				name.setMiddleName("");
			}

			DateFormat df = new SimpleDateFormat( "dd/MM/yy" );
			boolean dobE = false;
			if(dobEstimated != null)
				dobE = true;



			PersonAddress donorAddress = new PersonAddress();
			//DonorAddress donorAddress = new PersonAddress();
			donorAddress.setAddress1(donorAddress1);
			donorAddress.setAddress2(donorAddress2);
			donorAddress.setCityVillage(cityVillage);
			donorAddress.setCountry(country);
			donorAddress.setCountyDistrict(countyDistrict);
			donorAddress.setNeighborhoodCell(neighborhoodCell);
			donorAddress.setPostalCode(postalCode);
			donorAddress.setRegion(region);
			donorAddress.setStateProvince(stateProvince);
			donorAddress.setSubregion(subregion);
			donorAddress.setTownshipDivision(townshipDivision);
			donorAddress.setPreferred(true);

			person.addName(name);
			//donor.addName();
//	        PersonAttributeType pat = Context.getPersonService().getPersonAttributeTypeByName( "Father/Husband Name" );
		//    PersonAttribute pa = new PersonAttribute(pat, fatherHusbandName);
//            person.addAttribute(pa);
			person.setGender(gender);
			person.setBirthdate(df.parse(donorDob));
			person.setBirthdateEstimated(dobE);
			person.addAddress(donorAddress);

/*
			donor.setGender(gender);
			donor.setBirthdate(df.parse(donorDob));
			donor.setBirthdateEstimated(dobE);
			donor.addAddress(donorAddress);*/




		}

		personService.savePerson(person);
		//donorService.saveDonor(donor);
		//Donor donor = new Donor();
		donor.setPersonId(person.getPersonId());

		//Saves personId in donor table
		donorService.saveDonor(donor);






		//redirects to donor encounter view for new patient.

		return "redirect:/module/blooddonationmanager/showDonorEncounters.form?personId=" + person.getPersonId();
	}


	private String getNewIdentifier(String prefix)
	{
		Calendar now = Calendar.getInstance();
		String shortName = prefix;
		String noCheck = shortName
				+ String.valueOf( now.get( Calendar.YEAR ) ).substring( 2, 4 )
				+ String.valueOf( now.get( Calendar.MONTH ) + 1 )
				+ String.valueOf( now.get( Calendar.DATE ) )
				+ String.valueOf( now.get( Calendar.MINUTE ) )
				+ String.valueOf( new Random().nextInt( 999 ) );
		return noCheck + "-" + getCheckdigit( noCheck );
	}

	/**
	 * Using the Luhn Algorithm to generate check digits
	 * @param idWithoutCheckdigit
	 * @return idWithCheckdigit
	 */
	private static int getCheckdigit( String idWithoutCheckdigit )
	{
		idWithoutCheckdigit = idWithoutCheckdigit.trim().toUpperCase();
		int sum = 0;
		for ( int i = 0; i < idWithoutCheckdigit.length(); i++ )
		{
			char ch = idWithoutCheckdigit.charAt( idWithoutCheckdigit.length() - i - 1 );

			int digit = (int) ch - 48;
			int weight;
			if ( i % 2 == 0 )
			{
				weight = ( 2 * digit ) - (int) ( digit / 5 ) * 9;
			} else
			{
				weight = digit;
			}
			sum += weight;
		}
		sum = Math.abs( sum ) + 10;
		return ( 10 - ( sum % 10 ) ) % 10;
	}

}
