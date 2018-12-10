package de.metas.costing;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;

import de.metas.acct.api.AcctSchemaId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Wither;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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
@Builder
public class CostDetail
{
	@Wither
	int repoId;

	@NonNull
	ClientId clientId;
	@NonNull
	OrgId orgId;

	@NonNull
	AcctSchemaId acctSchemaId;
	@NonNull
	CostElementId costElementId;
	@NonNull
	ProductId productId;
	@NonNull
	AttributeSetInstanceId attributeSetInstanceId;

	@NonNull
	CostAmount amt;
	@NonNull
	Quantity qty;

	boolean changingCosts;

	// @NonNull
	CostDetailPreviousAmounts previousAmounts;

	@NonNull
	CostingDocumentRef documentRef;

	String description;
}
