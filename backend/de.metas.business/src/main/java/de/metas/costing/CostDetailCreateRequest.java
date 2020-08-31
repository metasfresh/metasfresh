package de.metas.costing;

import java.time.LocalDate;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;

import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostDetail.CostDetailBuilder;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License; or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful;
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not; see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@ToString(doNotUseGetters = true) // because we are throwing exception on some getters, see below...
@EqualsAndHashCode(doNotUseGetters = true) // because we are throwing exception on some getters, see below...
public class CostDetailCreateRequest
{
	AcctSchemaId acctSchemaId;
	ClientId clientId;
	OrgId orgId;
	ProductId productId;
	AttributeSetInstanceId attributeSetInstanceId;
	CostingDocumentRef documentRef;
	/** Initial document reference (in case of reversal) */
	CostingDocumentRef initialDocumentRef;
	CostElement costElement;
	CostAmount amt;
	Quantity qty;
	CurrencyConversionTypeId currencyConversionTypeId;
	LocalDate date;
	String description;

	@Builder(toBuilder = true)
	private CostDetailCreateRequest(
			@Nullable final AcctSchemaId acctSchemaId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final ProductId productId,
			@NonNull final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final CostingDocumentRef documentRef,
			@Nullable final CostingDocumentRef initialDocumentRef,
			@Nullable final CostElement costElement,
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			@Nullable final CurrencyConversionTypeId currencyConversionTypeId,
			@NonNull final LocalDate date,
			@Nullable final String description)
	{
		this.acctSchemaId = acctSchemaId;
		this.clientId = clientId;
		this.orgId = orgId;
		this.productId = productId;
		this.attributeSetInstanceId = attributeSetInstanceId;
		this.documentRef = documentRef;
		this.costElement = costElement;
		this.initialDocumentRef = initialDocumentRef;
		this.amt = amt;
		this.qty = qty;
		this.currencyConversionTypeId = currencyConversionTypeId;
		this.date = date;
		this.description = description;
	}

	public AcctSchemaId getAcctSchemaId()
	{
		Check.assumeNotNull(acctSchemaId, "acctSchemaId shall be set for {}", this);
		return acctSchemaId;
	}

	public boolean isAllAcctSchemas()
	{
		return acctSchemaId == null;
	}

	public CostElement getCostElement()
	{
		Check.assumeNotNull(costElement, "costElement shall be set for {}", this);
		return costElement;
	}

	public CostElementId getCostElementId()
	{
		return getCostElement().getId();
	}

	public boolean isAllCostElements()
	{
		return costElement == null;
	}

	public boolean isReversal()
	{
		return getInitialDocumentRef() != null;
	}

	public CostDetailCreateRequest withAcctSchemaId(@NonNull final AcctSchemaId acctSchemaId)
	{
		if (AcctSchemaId.equals(this.acctSchemaId, acctSchemaId))
		{
			return this;
		}

		return toBuilder().acctSchemaId(acctSchemaId).build();
	}

	public CostDetailCreateRequest withCostElement(@NonNull final CostElement costElement)
	{
		if (Objects.equals(this.costElement, costElement))
		{
			return this;
		}

		return toBuilder().costElement(costElement).build();
	}

	public CostDetailCreateRequest withAmount(@NonNull final CostAmount amt)
	{
		if (Objects.equals(this.amt, amt))
		{
			return this;
		}

		return toBuilder().amt(amt).build();
	}

	public CostDetailCreateRequest withProductId(@NonNull final ProductId productId)
	{
		if (ProductId.equals(this.productId, productId))
		{
			return this;
		}

		return toBuilder().productId(productId).build();
	}

	public CostDetailCreateRequest withProductIdAndQty(@NonNull final ProductId productId, @NonNull final Quantity qty)
	{
		if (ProductId.equals(this.productId, productId)
				&& Objects.equals(this.qty, qty))
		{
			return this;
		}

		return toBuilder().productId(productId).qty(qty).build();
	}

	public CostDetailCreateRequest withQty(@NonNull final Quantity qty)
	{
		if (Objects.equals(this.qty, qty))
		{
			return this;
		}

		return toBuilder().qty(qty).build();
	}

	public CostDetailBuilder toCostDetailBuilder()
	{
		final CostDetailBuilder costDetail = CostDetail.builder()
				.clientId(getClientId())
				.orgId(getOrgId())
				.acctSchemaId(getAcctSchemaId())
				.productId(getProductId())
				.attributeSetInstanceId(getAttributeSetInstanceId())
				//
				.amt(getAmt())
				.qty(getQty())
				//
				.documentRef(getDocumentRef())
				.description(getDescription());

		if (!isAllCostElements())
		{
			costDetail.costElementId(getCostElementId());
		}

		return costDetail;
	}
}
