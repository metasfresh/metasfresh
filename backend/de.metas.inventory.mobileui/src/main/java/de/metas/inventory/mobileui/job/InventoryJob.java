package de.metas.inventory.mobileui.job;

import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;

@Value
@Builder
public class InventoryJob
{
	@NonNull InventoryJobId id;
	@Nullable @With UserId responsibleId;

	@NonNull ImmutableList<InventoryJobLine> lines;
}
