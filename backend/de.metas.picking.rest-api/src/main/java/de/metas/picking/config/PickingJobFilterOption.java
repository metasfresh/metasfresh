/*
 * #%L
 * de.metas.picking.rest-api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.picking.config;

import com.google.common.collect.ImmutableMap;
import de.metas.common.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

import static de.metas.picking.model.X_PickingProfile_Filter.FILTERTYPE_Customer;
import static de.metas.picking.model.X_PickingProfile_Filter.FILTERTYPE_DeliveryDate;
import static de.metas.picking.model.X_PickingProfile_Filter.FILTERTYPE_HandoverLocation;

@AllArgsConstructor
@Getter
public enum PickingJobFilterOption implements ReferenceListAwareEnum
{
	CUSTOMER(FILTERTYPE_Customer),
	DELIVERY_DATE(FILTERTYPE_DeliveryDate),
	HANDOVER_LOCATION(FILTERTYPE_HandoverLocation),
	;

	@Nullable
	public static PickingJobFilterOption ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	@NonNull
	public static PickingJobFilterOption ofCode(@NonNull final String code)
	{
		return Check.assumeNotNull(typesByCode.get(code), "No Type found for code=" + code);
	}

	private final String code;

	private static final ImmutableMap<String, PickingJobFilterOption> typesByCode = ReferenceListAwareEnums.indexByCode(values());
}
