package de.metas.manufacturing.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;

@Value
@Builder
public class ManufacturingJob
{
	@NonNull PPOrderId ppOrderId;
	@Nullable UserId responsibleId;

	@NonNull ImmutableList<ManufacturingJobActivity> activities;
}
