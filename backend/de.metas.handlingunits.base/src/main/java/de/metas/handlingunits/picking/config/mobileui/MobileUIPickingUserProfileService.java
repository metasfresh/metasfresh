package de.metas.handlingunits.picking.config.mobileui;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.config.PickingConfigRepositoryV2;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
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

	@NonNull
	public CUQtyEntryPolicy getCUQtyEntryPolicy(@Nullable final BPartnerId customerId)
	{
		return getPickingJobOptions(customerId).getCUQtyEntryPolicy();
	}

	/** True if the line's customer requires a scanned CU label to book a CU. */
	public boolean isLabelRequired(@NonNull final PickingJobLine line)
	{
		return getCUQtyEntryPolicy(line.getCustomerId()).isLabelRequired();
	}

	/** True if at least one of the (job's) lines needs a scanned CU label; pick-all books without one, so such a job cannot use it. */
	public boolean isAnyLabelRequiredLine(@NonNull final Collection<PickingJobLine> lines)
	{
		return lines.stream().anyMatch(this::isLabelRequired);
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
}
