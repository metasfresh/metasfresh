/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.bpartner.composite.repository;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.MockedBPartnerCompositeUtil;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.greeting.GreetingRepository;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static de.metas.bpartner.composite.MockedBPartnerCompositeUtil.GROUP_ID;
import static de.metas.bpartner.composite.MockedBPartnerCompositeUtil.ORG_ID;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(AdempiereTestWatcher.class)
public class BPartnerCompositeSaverTest
{

	public static final int CH_COUNTRY_ID = 101;
	public static final int HU_COUNTRY_ID = 100;
	public static final String CH_POSTAL_CODE = "5000";
	public static final String HU_COUNTRY_CODE = "HU";
	public static final String CH_COUNTRY_CODE = "CH";
	public static final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	public static final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	public static final String HU_CITY = "Szolnok";

	private BPartnerBL bpartnerBL;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new GreetingRepository());

		bpartnerBL = new BPartnerBL(new UserRepository());
		//Services.registerService(IBPartnerBL.class, bpartnerBL);
		//Services.registerService(IBPartnerDAO.class, new BPartnerDAO());

		final I_C_BP_Group bpGroupRecord = newInstance(I_C_BP_Group.class);
		bpGroupRecord.setC_BP_Group_ID(GROUP_ID);
		bpGroupRecord.setName("BP_GROUP_RECORD_NAME");
		saveRecord(bpGroupRecord);

		createLocationData();

		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Org_ID, ORG_ID);
	}

	private void createLocationData()
	{
		createCountry(HU_COUNTRY_ID, HU_COUNTRY_CODE);
		createCountry(CH_COUNTRY_ID, CH_COUNTRY_CODE);

		final I_C_Postal postal = newInstance(I_C_Postal.class);
		postal.setPostal(CH_POSTAL_CODE);
		postal.setC_Country_ID(CH_COUNTRY_ID);
		postal.setCity("TestCity");
		postal.setAD_Org_ID(ORG_ID);
		saveRecord(postal);
	}

	private void createCountry(final int countryId, final String countryCode)
	{
		final I_C_Country country = newInstance(I_C_Country.class);
		country.setCountryCode(countryCode);
		country.setC_Country_ID(countryId);
		country.setAD_Org_ID(ORG_ID);
		saveRecord(country);
	}

	@Test
	public void postalCodeMatchesDifferentCountry()
	{
		final ExternalId externalId = ExternalId.of("1234");
		final BPartnerComposite bpartnerComposite = MockedBPartnerCompositeUtil
				.createMockedBPartnerComposite(externalId);

		final ExternalId locExternalId = ExternalId.of("loc-123");
		//Location added with postal code that already exists in C_Postal, but it set to another city&country
		bpartnerComposite.getLocations().add(MockedBPartnerCompositeUtil.addLocation(locExternalId, HU_CITY, CH_POSTAL_CODE, HU_COUNTRY_CODE));
		new BPartnerCompositeSaver(bpartnerBL).save(bpartnerComposite, true);

		final Optional<BPartnerId> bpId = bPartnerDAO.getBPartnerIdByExternalId(externalId);
		assertThat(bpId).isPresent();
		final Optional<BPartnerLocationId> bpLocId = bPartnerDAO.getBPartnerLocationIdByExternalId(bpId.get(), locExternalId);
		assertThat(bpLocId).isPresent();
		final I_C_BPartner_Location bPartnerLocationById = bPartnerDAO.getBPartnerLocationById(bpLocId.get());
		assertThat(bPartnerLocationById).isNotNull();

		final I_C_Location savedLocation = locationDAO.getById(LocationId.ofRepoId(bPartnerLocationById.getC_Location_ID()));
		assertThat(savedLocation).isNotNull();
		assertThat(savedLocation.getCity()).isEqualTo(HU_CITY);
		assertThat(savedLocation.getC_Country_ID()).isEqualTo(HU_COUNTRY_ID);
	}

}
