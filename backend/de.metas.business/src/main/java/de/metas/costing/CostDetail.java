package de.metas.costing;

<<<<<<< HEAD
import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;

import de.metas.acct.api.AcctSchemaId;
=======
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.methods.CostAmountAndQtyDetailed;
import de.metas.costing.methods.CostAmountType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
<<<<<<< HEAD
=======
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.time.Instant;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
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
=======
	@With CostDetailId id;

	@NonNull ClientId clientId;
	@NonNull OrgId orgId;

	@NonNull AcctSchemaId acctSchemaId;
	@NonNull CostElementId costElementId;
	@NonNull ProductId productId;
	@NonNull AttributeSetInstanceId attributeSetInstanceId;

	@NonNull CostAmountType amtType;
	@NonNull CostAmount amt;
	@NonNull @With Quantity qty;

	@With boolean changingCosts;

	CostDetailPreviousAmounts previousAmounts;

	@NonNull CostingDocumentRef documentRef;

	@Nullable String description;

	@NonNull @With Instant dateAcct;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@Builder
	private CostDetail(
			final CostDetailId id,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId,
			@NonNull final ProductId productId,
			@NonNull final AttributeSetInstanceId attributeSetInstanceId,
<<<<<<< HEAD
=======
			@NonNull final CostAmountType amtType,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			final boolean changingCosts,
			final CostDetailPreviousAmounts previousAmounts,
			@NonNull final CostingDocumentRef documentRef,
<<<<<<< HEAD
			@Nullable final String description)
=======
			@Nullable final String description,
			@NonNull Instant dateAcct)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		this.id = id;
		this.clientId = clientId;
		this.orgId = orgId;
		this.acctSchemaId = acctSchemaId;
		this.costElementId = costElementId;
		this.productId = productId;
		this.attributeSetInstanceId = attributeSetInstanceId;
<<<<<<< HEAD
=======
		this.amtType = amtType;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		this.amt = amt;
		this.qty = qty;
		this.changingCosts = changingCosts;
		this.previousAmounts = previousAmounts;
		this.documentRef = documentRef;
		this.description = description;
<<<<<<< HEAD
=======
		this.dateAcct = dateAcct;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
=======

	public CostAmountAndQtyDetailed getAmtAndQtyDetailed() {return CostAmountAndQtyDetailed.of(amt, qty, amtType);}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
