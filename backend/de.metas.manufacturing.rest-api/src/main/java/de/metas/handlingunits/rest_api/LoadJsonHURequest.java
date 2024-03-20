/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.handlingunits.rest_api;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import java.util.Set;

@Value
@Builder(toBuilder = true)
class LoadJsonHURequest
{
	public static final Set<String> INCLUDE_EMPTY_ATTRIBUTES_NONE = ImmutableSet.of();

	@NonNull I_M_HU hu;
	@Nullable HUQRCode expectedQRCode;
	@NonNull String adLanguage;
	boolean includeAllowedClearanceStatuses;

	/**
	 * If {@code true}, then generally exclude all HU-attributes with an empty value, unless their {@code  M_Attribute.Value} is explicitly included in {@link #getEmptyAttributesToInclude()}.
	 */
	@Builder.Default
	boolean excludeEmptyAttributes = true;

	/**
	 * Represents a set of {@code  M_Attribute.Value}s to include the result even if they have an empty value.
	 */
	@Builder.Default
	Set<String> emptyAttributesToInclude = INCLUDE_EMPTY_ATTRIBUTES_NONE;

	@NonNull
	public static LoadJsonHURequest ofHUAndLanguage(@NonNull final I_M_HU hu, @NonNull final String adLanguage)
	{
		return LoadJsonHURequest.builder()
				.adLanguage(adLanguage)
				.hu(hu)
				.build();
	}

	public LoadJsonHURequest withIncludedHU(final I_M_HU includedHU)
	{
		return toBuilder().hu(includedHU).expectedQRCode(null).build();
	}
}
