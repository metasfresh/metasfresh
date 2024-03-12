/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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
package de.metas.contracts.bpartner.process;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static de.metas.testsupport.MetasfreshAssertions.assertThat;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

public class C_BPartner_Location_updateFromPreviousAddressTest
{
	private int current_cBpartnerLocationId;
	private int replacing_cBpartnerLocationId;
	private int future_cBpartnerLocationId;

	@BeforeAll
	public static void init()
	{
		AdempiereTestHelper.get().staticInit();
		AdempiereTestHelper.get().init();
	}

	@BeforeEach
	public void createTestData()
	{
		AdempiereTestHelper.get().init();
		
		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));

		final Timestamp now = SystemTime.asTimestamp();
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		save(bpartner);

		// initial address
		final I_C_BPartner_Location bpartnerLocation1 = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation1.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpartnerLocation1.setC_Location_ID(1);
		save(bpartnerLocation1);

		// currently active
		final I_C_BPartner_Location bpartnerLocation2 = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation2.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpartnerLocation2.setC_Location_ID(2);
		bpartnerLocation2.setPrevious_ID(bpartnerLocation1.getC_BPartner_Location_ID());
		bpartnerLocation2.setValidFrom(TimeUtil.addWeeks(now, -10));
		bpartnerLocation2.setIsActive(true);
		bpartnerLocation2.setIsBillTo(true);
		bpartnerLocation2.setIsShipTo(true);
		bpartnerLocation2.setIsBillToDefault(true);
		bpartnerLocation2.setIsShipToDefault(true);
		save(bpartnerLocation2);
		current_cBpartnerLocationId = bpartnerLocation2.getC_BPartner_Location_ID();

		// replacing location
		final I_C_BPartner_Location bpartnerLocation3 = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation3.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpartnerLocation3.setC_Location_ID(3);
		bpartnerLocation3.setPrevious_ID(bpartnerLocation2.getC_BPartner_Location_ID());
		bpartnerLocation3.setValidFrom(TimeUtil.addWeeks(now, -1));
		save(bpartnerLocation3);
		replacing_cBpartnerLocationId = bpartnerLocation3.getC_BPartner_Location_ID();

		// future replacing location
		final I_C_BPartner_Location bpartnerLocation4 = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation4.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpartnerLocation4.setC_Location_ID(4);
		bpartnerLocation4.setPrevious_ID(bpartnerLocation2.getC_BPartner_Location_ID());
		bpartnerLocation4.setValidFrom(TimeUtil.addWeeks(now, 1));
		bpartnerLocation4.setIsBillTo(true);
		save(bpartnerLocation4);
		future_cBpartnerLocationId = bpartnerLocation4.getC_BPartner_Location_ID();
	}

	@Test
	public void testSuccessiveUpdates() throws Exception
	{
		final C_BPartner_Location_UpdateFromPreviousAddress c_bPartner_location_updateFromPreviousAddress = new C_BPartner_Location_UpdateFromPreviousAddress()
		{
			protected void logDeactivation(final BPartnerLocationId oldBPLocationId, final BPartnerLocationId newBPLocationId)
			{
				System.out.println("Replacing " + oldBPLocationId + " with " + newBPLocationId);
			}
		};

		c_bPartner_location_updateFromPreviousAddress.doIt();
		final I_C_BPartner_Location oldLoc = load(current_cBpartnerLocationId, I_C_BPartner_Location.class);
		assertThat(oldLoc.isActive()).isFalse();
		assertThat(oldLoc.isBillTo()).isTrue();
		assertThat(oldLoc.isShipTo()).isTrue();
		assertThat(oldLoc.isBillToDefault()).isFalse();
		assertThat(oldLoc.isShipToDefault()).isFalse();

		final I_C_BPartner_Location newLoc = load(replacing_cBpartnerLocationId, I_C_BPartner_Location.class);
		assertThat(newLoc.isActive()).isTrue();
		assertThat(newLoc.isBillTo()).isTrue();
		assertThat(newLoc.isShipTo()).isTrue();
		assertThat(newLoc.isBillToDefault()).isTrue();
		assertThat(newLoc.isShipToDefault()).isTrue();

		final I_C_BPartner_Location futureLoc = load(future_cBpartnerLocationId, I_C_BPartner_Location.class);
		assertThat(futureLoc.isActive()).isTrue();
		assertThat(futureLoc.isBillTo()).isTrue();
		assertThat(futureLoc.isShipTo()).isFalse();
		assertThat(futureLoc.isBillToDefault()).isFalse();
		assertThat(futureLoc.isShipToDefault()).isFalse();
	}
}
