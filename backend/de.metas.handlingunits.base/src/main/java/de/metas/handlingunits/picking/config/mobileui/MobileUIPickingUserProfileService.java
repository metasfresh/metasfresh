package de.metas.handlingunits.picking.config.mobileui;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.config.PickingConfigRepositoryV2;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
@RequiredArgsConstructor
public class MobileUIPickingUserProfileService
{
	@NonNull private final MobileUIPickingUserProfileRepository profileRepository;
	@NonNull private final PickingConfigRepositoryV2 pickingConfigRepositoryV2;

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
	public PickingJobAggregationType getAggregationType(@Nullable final BPartnerId customerId)
	{
		return profileRepository.getAggregationType(customerId);
	}

	public boolean isConsiderAttributes()
	{
		return pickingConfigRepositoryV2.getPickingConfig().isConsiderAttributes();
	}

}
