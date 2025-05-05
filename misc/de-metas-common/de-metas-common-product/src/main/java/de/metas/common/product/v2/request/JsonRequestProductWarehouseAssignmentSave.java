/*
 * #%L
 * de-metas-common-product
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.common.product.v2.request;

import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

import static de.metas.common.product.v2.request.constants.SwaggerDocConstants.WAREHOUSE_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.READ_ONLY_SYNC_ADVISE_DOC;

@Value
@Builder
@Jacksonized
public class JsonRequestProductWarehouseAssignmentSave
{
	@Schema(description = WAREHOUSE_IDENTIFIER_DOC)
	@NonNull
	List<String> warehouseIdentifiers;

	@Schema(description = READ_ONLY_SYNC_ADVISE_DOC)
	@NonNull
	SyncAdvise syncAdvise;
}
