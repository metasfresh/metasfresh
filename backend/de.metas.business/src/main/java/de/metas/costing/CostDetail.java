package de.metas.costing;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;

import de.metas.acct.api.AcctSchemaId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

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
public class CostDetail
{
	@With
	CostDetailId id;

	ClientId clientId;
	OrgId orgId;

	AcctSchemaId acctSchemaId;
	CostElementId costElementId;
	ProductId productId;
	AttributeSetInstanceId attributeSetInstanceId;

	CostAmount amt;
	Quantity qty;

	boolean changingCosts;

	CostDetailPreviousAmounts previousAmounts;

	CostingDocumentRef documentRef;

	String description;

	@Builder
	private CostDetail(
			final CostDetailId id,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId,
			@NonNull final ProductId productId,
			@NonNull final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			final boolean changingCosts,
			final CostDetailPreviousAmounts previousAmounts,
			@NonNull final CostingDocumentRef documentRef,
			@Nullable final String description)
	{
		this.id = id;
		this.clientId = clientId;
		this.orgId = orgId;
		this.acctSchemaId = acctSchemaId;
		this.costElementId = costElementId;
		this.productId = productId;
		this.attributeSetInstanceId = attributeSetInstanceId;
		this.amt = amt;
		this.qty = qty;
		this.changingCosts = changingCosts;
		this.previousAmounts = previousAmounts;
		this.documentRef = documentRef;
		this.description = description;

		if (this.previousAmounts != null
				&& !CurrencyId.equals(this.previousAmounts.getCurrencyId(), amt.getCurrencyId()))
		{
			throw new AdempiereException("Previous amounts shall have same currency as the amount: " + this);
		}
	}

	public boolean isInboundTrx()
	{
		return !isOutboundTrx();
	}

	public boolean isOutboundTrx()
	{
		final Boolean outboundTrx = getDocumentRef().getOutboundTrx();
		if (outboundTrx != null)
		{
			return outboundTrx;
		}
		else
		{
			return getQty().signum() < 0;
		}
	}
}
