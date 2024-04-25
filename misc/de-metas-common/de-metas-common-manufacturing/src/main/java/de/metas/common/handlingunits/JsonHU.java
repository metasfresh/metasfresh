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
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonHU
{
	@Nullable String id;
	@Nullable String huStatus;
	@Nullable String huStatusCaption;

	@NonNull String displayName;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable
	JsonHUQRCode qrCode;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable
	String warehouseValue;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Nullable
	String locatorValue;

	int numberOfAggregatedHUs;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String topLevelParentId;

	@NonNull
	@Singular
	List<JsonHUProduct> products;

	/**
	 * Just a simple map of attribute code and values.
	 * In the next versions of the API it will be replaced by {@link #attributes2}.
	 */
	@Deprecated
	@NonNull
	JsonHUAttributeCodeAndValues attributes;

	@NonNull
	JsonHUAttributes attributes2;

	@Nullable
	JsonClearanceStatusInfo clearanceStatus;

	@Nullable
	String clearanceNote;

	@Nullable
	JsonHUType jsonHUType;

	@Nullable
	List<JsonHU> includedHUs;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonAllowedHUClearanceStatuses allowedHUClearanceStatuses;

	@With
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Boolean isDisposalPending;

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String packingInstructionName;

	public JsonHU(
			@Nullable final String id,
			@Nullable final String huStatus,
			@Nullable final String huStatusCaption,
			@NonNull final String displayName,
			@Nullable final JsonHUQRCode qrCode,
			@Nullable final String warehouseValue,
			@Nullable final String locatorValue,
			final int numberOfAggregatedHUs,
			@Nullable final String topLevelParentId,
			@NonNull final List<JsonHUProduct> products,
			@Nullable final JsonHUAttributeCodeAndValues attributes,
			@Nullable final JsonHUAttributes attributes2,
			@Nullable final JsonClearanceStatusInfo clearanceStatus,
			@Nullable final String clearanceNote,
			@Nullable final JsonHUType jsonHUType,
			@Nullable final List<JsonHU> includedHUs,
			@Nullable final JsonAllowedHUClearanceStatuses allowedHUClearanceStatuses,
			final Boolean isDisposalPending,
			@Nullable final String packingInstructionName)
	{
		this.id = id;
		this.huStatus = huStatus;
		this.huStatusCaption = huStatusCaption;
		this.displayName = displayName;
		this.qrCode = qrCode;
		this.warehouseValue = warehouseValue;
		this.locatorValue = locatorValue;
		this.numberOfAggregatedHUs = numberOfAggregatedHUs;
		this.topLevelParentId = topLevelParentId;
		this.products = products;
		this.clearanceStatus = clearanceStatus;
		this.clearanceNote = clearanceNote;
		this.jsonHUType = jsonHUType;
		this.includedHUs = includedHUs;
		this.allowedHUClearanceStatuses = allowedHUClearanceStatuses;
		this.isDisposalPending = isDisposalPending;
		this.packingInstructionName = packingInstructionName;

		if(attributes2 == null)
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
}
