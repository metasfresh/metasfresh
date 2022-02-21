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
	@NonNull String id;
	@NonNull String huStatus;
	@NonNull String huStatusCaption;

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

	@NonNull
	@Singular
	List<JsonHUProduct> products;

	@NonNull
	JsonHUAttributes attributes;

	@Nullable
	JsonClearanceStatusInfo clearanceStatus;

	@Nullable
	String clearanceNote;

	@Nullable
	JsonHUType jsonHUType;

	@Nullable
	List<JsonHU> includedHUs;

	@With
	@JsonInclude(JsonInclude.Include.NON_NULL)
	Boolean isDisposalPending;
}
