/*
 * #%L
 * de-metas-common-manufacturing
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.common.handlingunits;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@Value
@SuppressWarnings("DeprecatedIsStillUsed")
public class JsonHU
{
	@Nullable String id;
	@Nullable String huStatus;
	@Nullable String huStatusCaption;

	@NonNull String displayName;

	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) JsonHUQRCode qrCode;

	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) String warehouseValue;
	@Nullable @JsonInclude(JsonInclude.Include.NON_EMPTY) String locatorValue;

	boolean aggregatedTU;
	int numberOfAggregatedHUs;
	@Nullable @JsonInclude(JsonInclude.Include.NON_NULL) Integer qtyTUs;

	@Nullable @JsonInclude(JsonInclude.Include.NON_NULL) String topLevelParentId;

	@NonNull List<JsonHUProduct> products;

	/**
	 * Just a simple map of attribute code and values.
	 * In the next versions of the API it will be replaced by {@link #attributes2}.
	 */
	@NonNull @Deprecated JsonHUAttributeCodeAndValues attributes;
	@NonNull JsonHUAttributes attributes2;

	@Nullable JsonClearanceStatusInfo clearanceStatus;

	@Nullable String clearanceNote;

	@NonNull JsonHUType unitType;
	@NonNull @Deprecated JsonHUType jsonHUType;

	@Nullable List<JsonHU> includedHUs;

	@Nullable @JsonInclude(JsonInclude.Include.NON_NULL) JsonAllowedHUClearanceStatuses allowedHUClearanceStatuses;

	@Nullable @JsonInclude(JsonInclude.Include.NON_NULL) Boolean isDisposalPending;

	@Nullable @JsonInclude(JsonInclude.Include.NON_NULL) String packingInstructionName;

	@Builder(toBuilder = true)
	@Jacksonized
	private JsonHU(
			@Nullable final String id,
			@Nullable final String huStatus,
			@Nullable final String huStatusCaption,
			@NonNull final String displayName,
			@Nullable final JsonHUQRCode qrCode,
			@Nullable final String warehouseValue,
			@Nullable final String locatorValue,
			final boolean aggregatedTU,
			final int numberOfAggregatedHUs,
			@Nullable final String topLevelParentId,
			@NonNull @Singular final List<JsonHUProduct> products,
			@Nullable final JsonHUAttributeCodeAndValues attributes,
			@Nullable final JsonHUAttributes attributes2,
			@Nullable final JsonClearanceStatusInfo clearanceStatus,
			@Nullable final String clearanceNote,
			@Nullable JsonHUType unitType,
			@Nullable @Deprecated JsonHUType jsonHUType,
			@Nullable final List<JsonHU> includedHUs,
			@Nullable final JsonAllowedHUClearanceStatuses allowedHUClearanceStatuses,
			@Nullable final Boolean isDisposalPending,
			@Nullable final String packingInstructionName,
			@Nullable @Deprecated Integer qtyTUs // ignored
	)
	{
		this.id = id;
		this.huStatus = huStatus;
		this.huStatusCaption = huStatusCaption;
		this.unitType = this.jsonHUType = singleNonNullValue("unitType", unitType, jsonHUType);
		this.displayName = displayName;
		this.qrCode = qrCode;
		this.warehouseValue = warehouseValue;
		this.locatorValue = locatorValue;
		this.aggregatedTU = aggregatedTU;
		this.numberOfAggregatedHUs = numberOfAggregatedHUs;
		this.qtyTUs = computeQtyTUs(this.unitType, includedHUs);
		this.topLevelParentId = topLevelParentId;
		this.products = products;
		this.clearanceStatus = clearanceStatus;
		this.clearanceNote = clearanceNote;
		this.includedHUs = includedHUs;
		this.allowedHUClearanceStatuses = allowedHUClearanceStatuses;
		this.isDisposalPending = isDisposalPending;
		this.packingInstructionName = packingInstructionName;

		if (attributes2 == null)
		{
			Check.assumeNotNull(attributes, "attributes is not null");
			this.attributes2 = JsonHUAttributes.ofJsonHUAttributeCodeAndValues(attributes);
		}
		else
		{
			this.attributes2 = attributes2;
		}
		this.attributes = attributes != null ? attributes : this.attributes2.toJsonHUAttributeCodeAndValues();
	}

	@NonNull
	@SuppressWarnings("SameParameterValue")
	private static <T> T singleNonNullValue(@NonNull final String name, @Nullable final T value1, @Nullable final T value2)
	{
		if (value1 == null && value2 == null)
		{
			throw new IllegalArgumentException("No value for " + name + " found");
		}
		if (value1 != null && value2 != null && !Objects.equals(value2, value1))
		{
			throw new IllegalArgumentException("More than one value for " + name + " found: " + value1 + ", " + value2);
		}
		else
		{
			return value1 != null ? value1 : value2;
		}
	}

	private static Integer computeQtyTUs(@NonNull final JsonHUType unitType, final List<JsonHU> includedHUs)
	{
		if (unitType == JsonHUType.LU)
		{
			return includedHUs.stream()
					.mapToInt(tu -> tu.isAggregatedTU() ? tu.numberOfAggregatedHUs : 1)
					.sum();
		}
		else
		{
			return null;
		}
	}

	public JsonHU withIsDisposalPending(@Nullable final Boolean isDisposalPending)
	{
		return Objects.equals(this.isDisposalPending, isDisposalPending)
				? this
				: toBuilder().isDisposalPending(isDisposalPending).build();
	}

	public JsonHU withDisplayedAttributesOnly(@Nullable final List<String> displayedAttributeCodesOnly)
	{
		if (displayedAttributeCodesOnly == null || displayedAttributeCodesOnly.isEmpty())
		{
			return this;
		}

		final JsonHUAttributes attributes2New = attributes2.retainOnlyAttributesInOrder(displayedAttributeCodesOnly);
		if (Objects.equals(attributes2New, this.attributes2))
		{
			return this;
		}

		return toBuilder()
				.attributes2(attributes2New)
				.attributes(attributes2New.toJsonHUAttributeCodeAndValues())
				.build();
	}

}
