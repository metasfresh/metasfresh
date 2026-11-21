package de.metas.handlingunits.picking.config.mobileui;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.config.PickingConfigRepositoryV2;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class MobileUIPickingUserProfileService
{
	@NonNull private final MobileUIPickingUserProfileRepository profileRepository;
	@NonNull private final PickingConfigRepositoryV2 pickingConfigRepositoryV2;

	@NonNull
	public static MobileUIPickingUserProfileService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new MobileUIPickingUserProfileService(new MobileUIPickingUserProfileRepository(), new PickingConfigRepositoryV2());
	}

	@NonNull
	public MobileUIPickingUserProfile getProfile()
	{
		return profileRepository.getProfile();
	}

	@NonNull
	public PickingJobOptions getPickingJobOptions(@Nullable final BPartnerId customerId)
	{
		return profileRepository.getPickingJobOptions(customerId);
	}

	/** @return what the customer's effective CU quantity entry mode (customer value, else profile value) means for a pick */
	@NonNull
	public CUQtyEntryPolicy getCUQtyEntryPolicy(@Nullable final BPartnerId customerId)
	{
		return getPickingJobOptions(customerId).getCUQtyEntryPolicy();
	}

	@NonNull
	public PickingJobAggregationType getAggregationType(@Nullable final BPartnerId customerId)
	{
		return profileRepository.getAggregationType(customerId);
	}

	public boolean isConsiderAttributes()
	{
		return pickingConfigRepositoryV2.getPickingConfig().isConsiderAttributes();
	}

	public void update(@NonNull UnaryOperator<MobileUIPickingUserProfile> updater)
	{
		profileRepository.update(updater);
	}

	@NonNull
	public PickingJobOptionsId createPickingJobOptions(@NonNull final String name, @NonNull final PickingJobOptions from)
	{
		return profileRepository.createPickingJobOptions(name, from);
	}

	public void updatePickingJobOptionsCUQtyEntry(@NonNull final PickingJobOptionsId id, @Nullable final CUQtyEntryMode mode, @Nullable final ScannableCodeFormatId formatId)
	{
		profileRepository.updatePickingJobOptionsCUQtyEntry(id, mode, formatId);
	}

	public void deleteUnusedPickingJobOptions(@NonNull final String namePrefix)
	{
		profileRepository.deleteUnusedPickingJobOptions(namePrefix);
	}

	public void unlinkCULabelFormats(@NonNull final Collection<ScannableCodeFormatId> formatIds)
	{
		profileRepository.unlinkCULabelFormats(formatIds);
	}
}
