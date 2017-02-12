package test.integration.commission.bPartner;

/*
 * #%L
 * de.metas.commission.ait
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.util.Env;

import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.event.TestEvent;
import de.metas.adempiere.ait.test.annotation.ITEventListener;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.modelvalidator.SponsorValidator;
import de.metas.commission.service.ISponsorDAO;
import test.integration.swat.bPartner.BPartnerTestDriver;

public class MBPartnerTestEventListener
{

	private List<I_C_Sponsor_SalesRep> parentSponsorSSRs;

	private I_C_Sponsor parentSponsor;

	/**
	 * Check the default parent sponsor and make sure that it has at least two parent link SSRs (i.e. two commission
	 * systems).
	 * 
	 * Otherwise there is no point in testing. Also loads those SSRs to chack agains the parent link SSRs of the new
	 * BPartner, after it has been created
	 * 
	 * @param evt
	 */
	@ITEventListener(
			driver = BPartnerTestDriver.class,
			eventTypes = EventType.BPARTNER_CREATE_BEFORE)
	public void onTestBegins(final TestEvent evt)
	{
		assertEquals(EventType.BPARTNER_CREATE_BEFORE, evt.getEventType());

		final String parentSponsorNo =
				MSysConfig.getValue(SponsorValidator.SYSCONFIG_DEFAULT_PARENT_SPONSOR_NO, "",
						Env.getAD_Client_ID(Env.getCtx()),
						Env.getAD_Org_ID(Env.getCtx()));
		assertNotNull(SponsorValidator.SYSCONFIG_DEFAULT_PARENT_SPONSOR_NO + " is not defined in AD_SysConfig", parentSponsorNo);

		parentSponsor = new Query(Env.getCtx(), I_C_Sponsor.Table_Name, I_C_Sponsor.COLUMNNAME_SponsorNo + "=?", null)
				.setParameters(parentSponsorNo)
				.setApplyAccessFilter(true)
				.firstOnly(I_C_Sponsor.class);
		assertNotNull("Default parent sponsor '" + parentSponsorNo + "' can'T be loaded", parentSponsor);

		parentSponsorSSRs = Services.get(ISponsorDAO.class).retrieveParentLinksSSRs(parentSponsor);
		assertTrue(parentSponsor + " has less than two parent links", parentSponsorSSRs.size() > 1);
	}

	@ITEventListener(
			driver = BPartnerTestDriver.class,
			eventTypes = EventType.BPARTNER_CREATE_AFTER)
	public void onBPartnerCreated(final TestEvent evt)
	{
		assertEquals(EventType.BPARTNER_CREATE_AFTER, evt.getEventType());

		final I_C_BPartner newBPartner = InterfaceWrapperHelper.create(evt.getObj(), I_C_BPartner.class);

		// verify that 'newBPartner' has been created with the expected default C_Sponsor_Parent_ID
		assertEquals(newBPartner + " has the wrong C_Sponsor_Parent_ID",
				parentSponsor.getC_Sponsor_ID(), newBPartner.getC_Sponsor_Parent_ID());

		final I_C_Sponsor newSponsor = Services.get(ISponsorDAO.class).retrieveForBPartner(newBPartner, true);
		assertNotNull(newBPartner + " has no sponsor", newSponsor);

		
		//
		// Here come the actual checks for 02171
		final List<I_C_Sponsor_SalesRep> newSponsorSSRs = Services.get(ISponsorDAO.class).retrieveParentLinksSSRs(newSponsor);
		assertEquals(newSponsor + " has wthe wrong number of parent links",
				parentSponsorSSRs.size(), newSponsorSSRs.size());

		// Note: we also expect the order within 'parentSponsorSSRs' and 'newSponsorSSRs' to match
		for (int i = 0; i < newSponsorSSRs.size(); i++)
		{
			final I_C_Sponsor_SalesRep newSSR = newSponsorSSRs.get(i);
			final I_C_Sponsor_SalesRep parentSSR = parentSponsorSSRs.get(i);

			assertEquals(newSSR + " has a wrong C_Sponsor_Parent_ID",
					parentSponsor.getC_Sponsor_ID(), newSSR.getC_Sponsor_Parent_ID());

			assertEquals(newSSR + " has a wrong C_AdvComSystem_ID",
					parentSSR.getC_AdvComSystem_ID(), newSSR.getC_AdvComSystem_ID());

			assertEquals(newSSR + " has a wrong ValidFrom",
					parentSSR.getValidFrom(), newSSR.getValidFrom());

			assertEquals(newSSR + " has a wrong ValidTo",
					parentSSR.getValidTo(), newSSR.getValidTo());
		}
	}
}
