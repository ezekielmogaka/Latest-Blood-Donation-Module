/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.blooddonationmanager;

import org.openmrs.*;

import java.io.Serializable;
import java.util.Set;


/**
 * It is a model class. It should extend either {@link BaseOpenmrsObject} or {@link BaseOpenmrsMetadata}.
 */
public class Donor extends BaseOpenmrsObject implements Serializable{


	private static final long serialVersionUID = 1L;

	private Integer donorId;
	private Integer personId;
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    private Integer id;
    private String gender;
	private String given_name;
	private String family_name;
	private String middle_name;
	private String address1;
	private String city_village;
    private String county_district;
	private String state_province;
	private String country;



	private Set<Donor> donor;

    public Set<Donor> getDonors() {
        return donor;
    }




    @Override
	public Integer getId() {
		return getDonorId();
	}

	@Override
	public void setId(Integer id) {
		setDonorId(id);
	}


	public Integer getDonorId() {
		return donorId;
	}

	public void setDonorId(Integer donorId) {
		this.donorId= donorId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId= personId;
	}

    public String getCountry() {
        return country;
    }
    public String getGender() {
        return gender;
    }
    public String getCity_village() {
        return city_village;
    }
    public String getGiven_name() {
        return given_name;
    }
    public String getMiddle_name() {
        return middle_name;
    }
    public String getFamily_name() {
        return family_name;
    }
    public String getCounty_district() {
        return county_district;
    }
    public String getAddress1() {
        return address1;
    }
    public String getState_province() {
        return state_province;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }


    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setCity_village(String city_village) {
        this.city_village = city_village;
    }


    public void setState_province(String state_province) {
        this.state_province = state_province;
    }
    public void setCounty_district(String county_district) {
        this.county_district = county_district;
    }
}
