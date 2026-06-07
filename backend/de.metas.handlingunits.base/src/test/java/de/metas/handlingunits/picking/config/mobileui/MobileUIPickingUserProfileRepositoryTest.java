/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.handlingunits.picking.config.mobileui;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.model.X_MobileUI_UserProfile_Picking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class MobileUIPickingUserProfileRepositoryTest
{
	private MobileUIPickingUserProfileRepository repository;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());
		repository = new MobileUIPickingUserProfileRepository();
	}

	private I_MobileUI_UserProfile_Picking newProfileRecord()
	{
		final I_MobileUI_UserProfile_Picking record = newInstance(I_MobileUI_UserProfile_Picking.class);
		record.setIsActive(true);
		record.setName("test");
		record.setIsAllowAnyCustomer(true);
		record.setPickingJobAggregationType(X_MobileUI_UserProfile_Picking.PICKINGJOBAGGREGATIONTYPE_Sales_order);
		record.setCreateShipmentPolicy(X_MobileUI_UserProfile_Picking.CREATESHIPMENTPOLICY_DO_NOT_CREATE);
		return record;
	}

	@Test
	void isMassPrinting_Y()
	{
		final I_MobileUI_UserProfile_Picking record = newProfileRecord();
		record.setIsMassPrinting(true);
		saveRecord(record);

		final MobileUIPickingUserProfile profile = repository.getProfile();

		assertThat(profile.isMassPrinting()).isTrue();
	}

	@Test
	void isMassPrinting_N()
	{
		final I_MobileUI_UserProfile_Picking record = newProfileRecord();
		record.setIsMassPrinting(false);
		saveRecord(record);

		final MobileUIPickingUserProfile profile = repository.getProfile();

		assertThat(profile.isMassPrinting()).isFalse();
	}

	@Test
	void isMassPrinting_default()
	{
		// no profile record — should return default (false)
		final MobileUIPickingUserProfile profile = repository.getProfile();

		assertThat(profile.isMassPrinting()).isFalse();
	}
}
