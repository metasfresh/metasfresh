package de.metas.costing;

import java.time.LocalDate;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;

import de.metas.acct.api.AcctSchemaId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Value
@Builder(toBuilder = true)
public class MoveCostsRequest
{
	@NonNull
	AcctSchemaId acctSchemaId;
	@NonNull
	ClientId clientId;

	@Nullable
	CostElement costElement;

	@NonNull
	LocalDate date;

	@NonNull
	ProductId productId;
	@NonNull
	AttributeSetInstanceId attributeSetInstanceId;

	@NonNull
	Quantity qtyToMove;

	@NonNull
	OrgId outboundOrgId;
	@NonNull
	CostingDocumentRef outboundDocumentRef;

	@NonNull
	OrgId inboundOrgId;
	@NonNull
	CostingDocumentRef inboundDocumentRef;

	public boolean isAllCostElements()
	{
		return costElement == null;
	}

	public CostElementId getCostElementId()
	{
		if (costElement == null)
		{
			throw new AdempiereException("No cost element");
		}

		return costElement.getId();
	}

	public MoveCostsRequest withCostElement(@NonNull final CostElement costElement)
	{
		return Objects.equals(this.costElement, costElement)
				? this
				: toBuilder().costElement(costElement).build();
	}
}
