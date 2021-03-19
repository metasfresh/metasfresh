/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.project.repository.requests;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class CreateSparePartsProjectTaskRequest
{
	@NonNull ProjectId projectId;
	@NonNull OrgId orgId;

	@NonNull ProductId productId;
	@NonNull Quantity qtyRequired;

	@NonNull @Singular ImmutableList<AlreadyReturnedQty> alreadyReturnedQtys;

	@Value
	@Builder
	public static class AlreadyReturnedQty
	{
		@NonNull Quantity qty;
		@NonNull HuId sparePartsVhuId;
	}
}
