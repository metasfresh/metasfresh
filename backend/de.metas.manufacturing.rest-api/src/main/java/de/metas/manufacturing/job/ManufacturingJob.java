package de.metas.manufacturing.job;

import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;

@Value
@Builder
public class ManufacturingJob
{
	@NonNull PPOrderId ppOrderId;
	@Nullable UserId responsibleId;

	@NonNull ImmutableList<ManufacturingJobActivity> activities;

	public ManufacturingJobActivity getActivityById(@NonNull ManufacturingJobActivityId id)
	{
		return activities.stream()
				.filter(activity -> ManufacturingJobActivityId.equals(activity.getId(), id))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No activity found for " + id));
	}
}
