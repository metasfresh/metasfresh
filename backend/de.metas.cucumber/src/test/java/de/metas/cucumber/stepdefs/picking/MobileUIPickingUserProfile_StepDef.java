/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.picking;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions.PickingJobOptionsBuilder;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.logging.LogManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class MobileUIPickingUserProfile_StepDef
{
	private static final Logger logger = LogManager.getLogger(MobileUIPickingUserProfile_StepDef.class);
	private final MobileUIPickingUserProfileService profileService = SpringContextHolder.instance.getBean(MobileUIPickingUserProfileService.class);

	/**
	 * The profile as it was before this scenario's first {@link #updateProfile(DataTable)} call,
	 * or {@code null} if this scenario never touched the profile.
	 */
	@Nullable private MobileUIPickingUserProfile profileBeforeScenario = null;

	@And("set mobile UI picking profile")
	public void updateProfile(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		// Snapshot once per scenario, before the first mutation, so the @After below can restore it.
		if (profileBeforeScenario == null)
		{
			profileBeforeScenario = profileService.getProfile();
		}

		profileService.update((profile) -> {
			final PickingJobOptionsBuilder defaultPickingJobOptionsBuilder = profile.getDefaultPickingJobOptions().toBuilder();
			row.getAsOptionalBoolean("IsAllowPickingAnyHU").ifPresent(defaultPickingJobOptionsBuilder::isAllowPickingAnyHU);
			row.getAsOptionalString("CreateShipmentPolicy").map(CreateShipmentPolicy::ofCodeOrName).ifPresent(defaultPickingJobOptionsBuilder::createShipmentPolicy);
			row.getAsOptionalBoolean(I_MobileUI_UserProfile_Picking.COLUMNNAME_IsAlwaysSplitHUsEnabled).ifPresent(defaultPickingJobOptionsBuilder::isAlwaysSplitHUsEnabled);
			row.getAsOptionalBoolean(I_MobileUI_UserProfile_Picking.COLUMNNAME_IsAllowCompletingPartialPickingJob).ifPresent(defaultPickingJobOptionsBuilder::isAllowCompletingPartialPickingJob);
			row.getAsOptionalBoolean(I_MobileUI_UserProfile_Picking.COLUMNNAME_IsCatchWeightTUPickingEnabled).ifPresent(defaultPickingJobOptionsBuilder::isCatchWeightTUPickingEnabled);

			return profile.toBuilder()
					.defaultPickingJobOptions(defaultPickingJobOptionsBuilder.build())
					.build();
		});

		logger.info("Profile updated: {}", profileService.getProfile());
	}

	/**
	 * Restores the mobile-UI picking profile to whatever it was before this scenario first changed it.
	 * <p>
	 * The profile is a single system-wide record, and features sharing a CI executor run sequentially
	 * against one database, so a scenario that leaves a changed option behind silently alters the
	 * behaviour every later scenario sees - passing on one executor and failing when moved, with no code
	 * change (module CLAUDE.md rule 12). Restoring here rather than pinning the value in every other
	 * feature's Background keeps the cleanup at the mutation site and costs nothing as the suite grows.
	 * <p>
	 * Fires for every scenario regardless of pass/fail - which a teardown step in the feature file could
	 * not do, since it never runs once a scenario has failed. No-op when this scenario never touched the
	 * profile.
	 */
	@After
	public void restorePickingProfile()
	{
		final MobileUIPickingUserProfile profileToRestore = profileBeforeScenario;
		if (profileToRestore == null)
		{
			return;
		}
		profileBeforeScenario = null;

		profileService.update(profile -> profileToRestore);
		logger.info("Profile restored to pre-scenario state: {}", profileService.getProfile());
	}
}
