package de.metas.inventory;

import com.google.common.collect.ImmutableList;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class InventoryValuationResponse
{
	@NonNull ImmutableList<InventoryValue> lines;

	@NonNull
	public InventoryValue getSingleLine() {return CollectionUtils.singleElement(lines);}
}
