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

package de.metas.servicerepair.project.service.commands.createQuotationFromProjectCommand;

import de.metas.product.ProductId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorType;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
@Builder
class QuotationLineKey
{
	@NonNull ServiceRepairProjectCostCollectorType type;
	@NonNull ProductId productId;
	@NonNull @Builder.Default AttributeSetInstanceId asiId = AttributeSetInstanceId.NONE;
	@NonNull UomId uomId;
	@Nullable ServiceRepairProjectCostCollectorId singleCostCollectorId;

	public static boolean equals(@Nullable final QuotationLineKey o1, @Nullable final QuotationLineKey o2) { return Objects.equals(o1, o2); }
}
