package de.metas.frontend_testing.describe;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonDescribeHUsRequest
{
	@NonNull @Singular ImmutableList<Integer> huIds;

	/**
	 * If set, the HUs of this inventory's lines ({@code M_InventoryLine_HU.M_HU_ID}, e.g. the pallets built on completion) are described too, after {@link #huIds}.
	 */
	@Nullable Integer inventoryId;
}
