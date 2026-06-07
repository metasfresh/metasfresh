/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.picking.workflow.handlers;

import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.i18n.TranslatableStrings;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.mobile.application.MobileApplicationInfo;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.picking.workflow.DisplayValueProviderService;
import de.metas.picking.workflow.PickingJobRestService;
import de.metas.picking.workflow.lauchers.PickingWorkflowLaunchersProvider;
import de.metas.user.UserId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.model.X_MobileUI_UserProfile_Picking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
class PickingMobileApplicationTest
{
	private MobileUIPickingUserProfileService profileService;
	private PickingMobileApplication app;

	private static final MobileApplicationInfo EMPTY_APP_INFO = MobileApplicationInfo.builder()
			.repoId(MobileApplicationRepoId.ofRepoId(1))
			.id(MobileApplicationId.ofString("picking"))
			.caption(TranslatableStrings.anyLanguage("Picking"))
			.build();

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());

		profileService = MobileUIPickingUserProfileService.newInstanceForUnitTesting();
		app = new PickingMobileApplication(
				profileService,
				Mockito.mock(PickingJobRestService.class),
				Mockito.mock(PickingWorkflowLaunchersProvider.class),
				Mockito.mock(DisplayValueProviderService.class));
	}

	private void saveProfileWithMassPrinting(final boolean isMassPrinting)
	{
		final I_MobileUI_UserProfile_Picking record = newInstance(I_MobileUI_UserProfile_Picking.class);
		record.setIsActive(true);
		record.setName("test");
		record.setIsAllowAnyCustomer(true);
		record.setPickingJobAggregationType(X_MobileUI_UserProfile_Picking.PICKINGJOBAGGREGATIONTYPE_Sales_order);
		record.setCreateShipmentPolicy(X_MobileUI_UserProfile_Picking.CREATESHIPMENTPOLICY_DO_NOT_CREATE);
		record.setIsMassPrinting(isMassPrinting);
		saveRecord(record);
	}

	@Test
	void massPrinting_parameter_true_when_profile_flag_is_on()
	{
		saveProfileWithMassPrinting(true);

		final MobileApplicationInfo result = app.customizeApplicationInfo(EMPTY_APP_INFO, UserId.ofRepoId(1));

		assertThat(result.getApplicationParameters())
				.containsEntry("massPrinting", true);
	}

	@Test
	void massPrinting_parameter_false_when_profile_flag_is_off()
	{
		saveProfileWithMassPrinting(false);

		final MobileApplicationInfo result = app.customizeApplicationInfo(EMPTY_APP_INFO, UserId.ofRepoId(1));

		assertThat(result.getApplicationParameters())
				.containsEntry("massPrinting", false);
	}

	@Test
	void massPrinting_parameter_false_by_default_when_no_profile()
	{
		// no profile record in DB — falls back to DEFAULT (isMassPrinting=false)
		final MobileApplicationInfo result = app.customizeApplicationInfo(EMPTY_APP_INFO, UserId.ofRepoId(1));

		assertThat(result.getApplicationParameters())
				.containsEntry("massPrinting", false);
	}
}
