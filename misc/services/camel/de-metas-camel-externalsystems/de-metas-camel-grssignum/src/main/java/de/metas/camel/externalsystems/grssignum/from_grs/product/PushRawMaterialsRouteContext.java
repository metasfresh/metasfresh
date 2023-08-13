/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.from_grs.product;

import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonProduct;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;

@Data
@Builder
public class PushRawMaterialsRouteContext
{
	@NonNull
	private final JsonProduct jsonProduct;

	@Nullable
	private JsonMetasfreshId currentBPartnerID;
}
