package de.metas.frontend_testing.masterdata.mobile_configuration;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfile;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfig;
import de.metas.handlingunits.picking.config.mobileui.PickingCustomerConfigsCollection;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptions;
import de.metas.handlingunits.picking.config.mobileui.PickingJobOptionsId;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.stream.StreamSupport;

/** Writes the CU quantity entry mode and CU label format of one customer of the picking profile. */
@RequiredArgsConstructor
final class CustomerCUQtyEntryWriter
{
	static final String JOB_OPTIONS_NAME_PREFIX = "E2E customer ";

	@NonNull private final MobileUIPickingUserProfileService profileService;
	@NonNull private final PickingJobOptionsWriter jobOptionsWriter = new PickingJobOptionsWriter();

	void write(
			@NonNull final BPartnerId customerId,
			@Nullable final CUQtyEntryMode cuQtyEntryMode,
			@Nullable final ScannableCodeFormatId cuLabelFormatId)
	{
		final MobileUIPickingUserProfile profile = profileService.getProfile();
		final PickingCustomerConfigsCollection customerConfigs = profile.getCustomerConfigs();
		if (!customerConfigs.getCustomerIds().contains(customerId))
		{
			throw new AdempiereException("Customer " + customerId + " is not a customer of the picking profile; it must be listed in mobileConfig.picking.customers");
		}

		final PickingJobOptionsId existingJobOptionsId = customerConfigs.getPickingJobOptionsId(customerId).orElse(null);
		if (existingJobOptionsId != null)
		{
			jobOptionsWriter.updatePickingJobOptionsCUQtyEntry(existingJobOptionsId, cuQtyEntryMode, cuLabelFormatId);
		}
		else
		{
			createAndLinkJobOptions(profile, customerId, cuQtyEntryMode, cuLabelFormatId);
		}
	}

	private void createAndLinkJobOptions(
			@NonNull final MobileUIPickingUserProfile profile,
			@NonNull final BPartnerId customerId,
			@Nullable final CUQtyEntryMode cuQtyEntryMode,
			@Nullable final ScannableCodeFormatId cuLabelFormatId)
	{
		if (cuQtyEntryMode == null && cuLabelFormatId == null)
		{
			return; // nothing to reset: the customer has no own job options row
		}

		final PickingJobOptions optionsCopy = profile.getDefaultPickingJobOptions().toBuilder()
				.cuQtyEntryMode(cuQtyEntryMode)
				.cuLabelFormatId(cuLabelFormatId)
				.build();
		final PickingJobOptionsId createdJobOptionsId = jobOptionsWriter.createPickingJobOptions(
				JOB_OPTIONS_NAME_PREFIX + customerId.getRepoId() + " " + System.currentTimeMillis(),
				optionsCopy);

		profileService.update(profileToUpdate -> profileToUpdate.toBuilder()
				.customerConfigs(linkJobOptions(profileToUpdate.getCustomerConfigs(), customerId, createdJobOptionsId))
				.build());
	}

	private static PickingCustomerConfigsCollection linkJobOptions(
			@NonNull final PickingCustomerConfigsCollection customerConfigs,
			@NonNull final BPartnerId customerId,
			@NonNull final PickingJobOptionsId jobOptionsId)
	{
		return StreamSupport.stream(customerConfigs.spliterator(), false)
				.map(config -> BPartnerId.equals(config.getCustomerId(), customerId)
						? PickingCustomerConfig.builder().customerId(customerId).pickingJobOptionsId(jobOptionsId).build()
						: config)
				.collect(PickingCustomerConfigsCollection.collect());
	}
}
