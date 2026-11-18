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

/**
 * Writes the CU quantity entry mode and CU label format of one customer of the picking profile.
 * They are stored on the customer's picking job options row; a customer of a fresh masterdata run has none,
 * so it is created here and linked on the customer's row of the profile.
 * <p>
 * A customer's job options row replaces the profile's options as a whole (booleans have no "empty = profile" state), so a created row
 * starts as a copy of the profile's options: giving a customer a CU setting must not change its other options.
 * <p>
 * All persistence goes through {@link MobileUIPickingUserProfileService}.
 */
@RequiredArgsConstructor
final class CustomerCUQtyEntryWriter
{
	/** Name prefix of the rows created here; {@link MobileConfigCommand} deletes the ones that no customer references any more. */
	static final String JOB_OPTIONS_NAME_PREFIX = "E2E customer ";

	@NonNull private final MobileUIPickingUserProfileService profileService;

	void write(
			@NonNull final BPartnerId customerId,
			@Nullable final CUQtyEntryMode cuQtyEntryMode,
			@Nullable final ScannableCodeFormatId cuLabelFormatId)
	{
		final MobileUIPickingUserProfile profile = profileService.getProfile();
		final PickingCustomerConfig customerConfig = StreamSupport.stream(profile.getCustomerConfigs().spliterator(), false)
				.filter(config -> BPartnerId.equals(config.getCustomerId(), customerId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("Customer " + customerId + " is not a customer of the picking profile; it must be listed in mobileConfig.picking.customers"));

		final PickingJobOptionsId existingJobOptionsId = customerConfig.getPickingJobOptionsId();
		if (existingJobOptionsId != null)
		{
			profileService.updatePickingJobOptionsCUQtyEntry(existingJobOptionsId, cuQtyEntryMode, cuLabelFormatId);
			return;
		}

		if (cuQtyEntryMode == null && cuLabelFormatId == null)
		{
			return; // nothing to reset: the customer has no own job options row
		}

		final PickingJobOptions optionsCopy = profile.getDefaultPickingJobOptions().toBuilder()
				.cuQtyEntryMode(cuQtyEntryMode)
				.cuLabelFormatId(cuLabelFormatId)
				.build();
		final PickingJobOptionsId newJobOptionsId = profileService.createPickingJobOptions(
				JOB_OPTIONS_NAME_PREFIX + customerId.getRepoId() + " " + System.currentTimeMillis(),
				optionsCopy);

		profileService.update(currentProfile -> currentProfile.toBuilder()
				.customerConfigs(linkJobOptions(currentProfile.getCustomerConfigs(), customerId, newJobOptionsId))
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
