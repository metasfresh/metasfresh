package de.metas.frontend_testing.describe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonDescribeHUsResponse
{
	@NonNull @Singular("hu") ImmutableList<HU> hus;

	@Value
	@Builder
	@Jacksonized
	public static class HU
	{
		int huId;
		int topLevelHuId;

		/**
		 * {@code LU}, {@code TU} or {@code V} (CU)
		 */
		@NonNull String huUnitType;

		/**
		 * product value ({@code M_Product.Value}) to {@code "<qty> <X12DE355>"}, the format {@code Backend.expect} accepts
		 */
		@NonNull ImmutableMap<String, String> storages;

		/**
		 * Sum of the TU counts of the included TUs; an aggregated TU counts with its TU quantity.
		 */
		int includedTUsCount;

		/**
		 * Distinct {@code M_HU_PI_Item.Qty} of the items via which the included TUs (and aggregated TUs) hang on this HU.
		 */
		@NonNull ImmutableList<Integer> tuParentPIItemQtys;
	}
}
