/*
 * #%L
 * de.metas.manufacturing.rest-api
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

package de.metas.handlingunits.rest_api;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeCode;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class GetByIdRequest
{
	@NonNull HuId huId;
	@Nullable HUQRCode expectedQRCode;

	@Nullable List<AttributeCode> orderedAttributeCodes;

	boolean includeAllowedClearanceStatuses;
}
