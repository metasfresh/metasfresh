/*
 * #%L
 * de.metas.business
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

package de.metas.order.compensationGroup;

import de.metas.contracts.ConditionsId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class GroupTemplateRegularLine
{
	@NonNull GroupTemplateRegularLineId id;
	@NonNull ProductId productId;
	@NonNull Quantity qty;
	@Nullable ConditionsId contractConditionsId;
	boolean isSkipInvoicing;
	boolean isHideWhenPrinting;

	public boolean isMatching(@Nullable final ConditionsId contextContractConditionsId)
	{
		return this.contractConditionsId == null
				|| ConditionsId.equals(this.contractConditionsId, contextContractConditionsId);
	}
}
