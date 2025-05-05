package de.metas.sales_region;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class SalesRegion
{
	@NonNull SalesRegionId id;
	@NonNull String value;
	@NonNull String name;
	boolean isActive;
	@Nullable UserId salesRepId;
}
