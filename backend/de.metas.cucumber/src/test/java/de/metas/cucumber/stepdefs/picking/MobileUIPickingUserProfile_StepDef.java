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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfig;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfigsCollection;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions.PickingJobOptionsBuilder;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptionsId;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.logging.LogManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_MobileUI_UserProfile_Picking;
import org.compiere.model.I_MobileUI_UserProfile_Picking_Job;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MobileUIPickingUserProfile_StepDef
{
	private static final Logger logger = LogManager.getLogger(MobileUIPickingUserProfile_StepDef.class);
	@NonNull private final C_BPartner_StepDefData bPartnerTable;
	private final MobileUIPickingUserProfileService profileService = SpringContextHolder.instance.getBean(MobileUIPickingUserProfileService.class);

	/**
	 * Sets or updates fields on the (default) mobile UI picking profile in-memory (single-row DataTable).
	 *
	 * <p>All columns are optional; supply any subset:
	 * <ul>
	 *   <li>{@code IsAllowPickingAnyHU} — boolean</li>
	 *   <li>{@code CreateShipmentPolicy} — policy code/name: DO_NOT_CREATE / CREATE_DRAFT / CREATE_AND_COMPLETE / CREATE_COMPLETE_CLOSE</li>
	 *   <li>{@code IsAlwaysSplitHUsEnabled} — boolean</li>
	 *   <li>{@code IsAllowCompletingPartialPickingJob} — boolean</li>
	 *   <li>{@code IsCatchWeightTUPickingEnabled} — boolean</li>
	 *   <li>{@code IsMassPrinting} — boolean</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * And set mobile UI picking profile
	 *   | IsMassPrinting | CreateShipmentPolicy |
	 *   | Y              | CREATE_DRAFT         |
	 * </pre>
	 *
	 * @see #setPerCustomerShipmentPolicy(DataTable) for per-customer policy overrides
	 */
	@And("set mobile UI picking profile")
	public void updateProfile(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);

		profileService.update((profile) -> {
			final PickingJobOptionsBuilder defaultPickingJobOptionsBuilder = profile.getDefaultPickingJobOptions().toBuilder();
			row.getAsOptionalBoolean("IsAllowPickingAnyHU").ifPresent(defaultPickingJobOptionsBuilder::isAllowPickingAnyHU);
			row.getAsOptionalString("CreateShipmentPolicy").map(CreateShipmentPolicy::ofCodeOrName).ifPresent(defaultPickingJobOptionsBuilder::createShipmentPolicy);
			row.getAsOptionalBoolean(I_MobileUI_UserProfile_Picking.COLUMNNAME_IsAlwaysSplitHUsEnabled).ifPresent(defaultPickingJobOptionsBuilder::isAlwaysSplitHUsEnabled);
			row.getAsOptionalBoolean(I_MobileUI_UserProfile_Picking.COLUMNNAME_IsAllowCompletingPartialPickingJob).ifPresent(defaultPickingJobOptionsBuilder::isAllowCompletingPartialPickingJob);
			row.getAsOptionalBoolean(I_MobileUI_UserProfile_Picking.COLUMNNAME_IsCatchWeightTUPickingEnabled).ifPresent(defaultPickingJobOptionsBuilder::isCatchWeightTUPickingEnabled);

			final MobileUIPickingUserProfile.MobileUIPickingUserProfileBuilder profileBuilder = profile.toBuilder()
					.defaultPickingJobOptions(defaultPickingJobOptionsBuilder.build());

			row.getAsOptionalBoolean(I_MobileUI_UserProfile_Picking.COLUMNNAME_IsMassPrinting).ifPresent(profileBuilder::isMassPrinting);

			return profileBuilder.build();
		});

		logger.info("Profile updated: {}", profileService.getProfile());
	}

	/**
	 * Sets a per-customer {@code CreateShipmentPolicy} override on the picking profile.
	 * Creates a {@code MobileUI_UserProfile_Picking_Job} record for each row and links it to the
	 * given customer via {@code MobileUI_UserProfile_Picking_BPartner}.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code C_BPartner_ID} — identifier of the customer</li>
	 *   <li>{@code CreateShipmentPolicy} — policy code: DO_NOT_CREATE / CREATE_DRAFT / CREATE_AND_COMPLETE / CREATE_COMPLETE_CLOSE</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * And set per-customer mobile UI shipment policy:
	 *   | C_BPartner_ID | CreateShipmentPolicy |
	 *   | customer1     | CREATE_DRAFT         |
	 *   | customer2     | DO_NOT_CREATE        |
	 * </pre>
	 */
	@And("set per-customer mobile UI shipment policy:")
	public void setPerCustomerShipmentPolicy(@NonNull final DataTable dataTable)
	{
		// Build per-customer PickingJobOptionsId entries, creating a MobileUI_UserProfile_Picking_Job
		// record per row so the PickingJobOptionsCollection cache can load them from DB.
		final List<PickingCustomerConfig> newConfigs = new ArrayList<>();
		DataTableRows.of(dataTable).forEach(row -> {
			final BPartnerId customerId = bPartnerTable.getId(row.getAsIdentifier("C_BPartner_ID"));
			final CreateShipmentPolicy policy = CreateShipmentPolicy.ofCodeOrName(row.getAsString("CreateShipmentPolicy"));

			// Insert a MobileUI_UserProfile_Picking_Job record carrying only the shipment policy;
			// all other options fall back to the profile default via PickingJobOptions.fallbackTo().
			final I_MobileUI_UserProfile_Picking_Job jobRecord = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_Picking_Job.class);
			jobRecord.setIsActive(true);
			jobRecord.setName("per-customer-" + customerId.getRepoId());
			jobRecord.setCreateShipmentPolicy(policy.getCode());
			jobRecord.setIsAllowCompletingPartialPickingJob(true);
			InterfaceWrapperHelper.saveRecord(jobRecord);

			final PickingJobOptionsId optionsId = PickingJobOptionsId.ofRepoId(jobRecord.getMobileUI_UserProfile_Picking_Job_ID());
			newConfigs.add(PickingCustomerConfig.builder()
					.customerId(customerId)
					.pickingJobOptionsId(optionsId)
					.build());
		});

		// Update the profile's customer configs (persists MobileUI_UserProfile_Picking_BPartner records).
		// This invalidates the profileCache (backed on BPartner table) and the pickingJobOptionsCache
		// (backed on MobileUI_UserProfile_Picking_Job) so both reload fresh on next access.
		profileService.update(profile -> {
			final List<PickingCustomerConfig> merged = new ArrayList<>(ImmutableList.copyOf(profile.getCustomerConfigs()));
			// Replace or add entries for the customers defined in this step.
			for (final PickingCustomerConfig newConfig : newConfigs)
			{
				merged.removeIf(existing -> existing.getCustomerId().equals(newConfig.getCustomerId()));
				merged.add(newConfig);
			}
			return profile.toBuilder()
					.customerConfigs(PickingCustomerConfigsCollection.ofCollection(merged))
					.build();
		});

		logger.info("Per-customer shipment policies set: {}", newConfigs);
	}
}
