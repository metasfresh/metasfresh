package de.metas.costing;

import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostDetail.CostDetailBuilder;
import de.metas.costing.methods.CostAmountType;
import de.metas.currency.CurrencyConversionContext;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

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
	@Nullable AcctSchemaId acctSchemaId;
	@NonNull ClientId clientId;
	@NonNull OrgId orgId;
	@NonNull ProductId productId;
	@NonNull AttributeSetInstanceId attributeSetInstanceId;
	@NonNull CostingDocumentRef documentRef;
	/**
	 * Initial document reference (in case of reversal)
	 */
	@Nullable CostingDocumentRef initialDocumentRef;
	@Nullable CostElement costElement;
	@NonNull CostAmountType amtType;
	@NonNull CostAmount amt;
	@NonNull Quantity qty;
	@Nullable CurrencyConversionContext currencyConversionContext;
	@NonNull Instant date;
	@Nullable String description;

	@Nullable CostAmount explicitCostPrice;
	@Nullable CostAmountAndQty externallyOwned;

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
			@Nullable final CostAmountType amtType,
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			@Nullable final CurrencyConversionContext currencyConversionContext,
			@NonNull final Instant date,
			@Nullable final String description,
			@Nullable final CostAmount explicitCostPrice,
			@Nullable final CostAmountAndQty externallyOwned)
	{
		this.acctSchemaId = acctSchemaId;
		this.clientId = clientId;
		this.orgId = orgId;
		this.productId = productId;
		this.attributeSetInstanceId = attributeSetInstanceId;
		this.documentRef = documentRef;
		this.costElement = costElement;
		this.initialDocumentRef = initialDocumentRef;
		this.amtType = amtType != null ? amtType : CostAmountType.MAIN;
		this.amt = amt;
		this.qty = qty;
		this.currencyConversionContext = currencyConversionContext;
		this.date = date;
		this.description = description;
		this.explicitCostPrice = explicitCostPrice;
		this.externallyOwned = externallyOwned;
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

	public boolean isExplicitCostElement()
	{
		return costElement != null;
	}

	public boolean isReversal()
	{
		return getInitialDocumentRef() != null;
	}

	public boolean isOutbound()
	{
		return getQty().signum() < 0 && !isReversal();
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
		return withAmountAndType(amt, this.amtType);
	}

	public CostDetailCreateRequest withAmountAndType(@NonNull final CostAmount amt, @NonNull final CostAmountType amtType)
	{
		if (Objects.equals(this.amt, amt)
				&& Objects.equals(this.amtType, amtType))
		{
			return this;
		}

		return toBuilder().amt(amt).amtType(amtType).build();
	}

	public CostDetailCreateRequest withAmountAndTypeAndQty(@NonNull final CostAmount amt, @NonNull final CostAmountType amtType, @NonNull final Quantity qty)
	{
		if (Objects.equals(this.amt, amt)
				&& Objects.equals(this.amtType, amtType)
				&& Objects.equals(this.qty, qty))
		{
			return this;
		}

		return toBuilder().amt(amt).amtType(amtType).qty(qty).build();
	}

	public CostDetailCreateRequest withAmountAndTypeAndQty(@NonNull final CostAmountAndQty amtAndQty, @NonNull final CostAmountType amtType)
	{
		return withAmountAndTypeAndQty(amtAndQty.getAmt(), amtType, amtAndQty.getQty());
	}

	public CostDetailCreateRequest withAmountAndQty(
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty)
	{
		if (Objects.equals(this.amt, amt)
				&& Objects.equals(this.qty, qty))
		{
			return this;
		}

		return toBuilder().amt(amt).qty(qty).build();
	}

	public CostDetailCreateRequest withProductId(@NonNull final ProductId productId)
	{
		if (ProductId.equals(this.productId, productId))
		{
			return this;
		}

		return toBuilder().productId(productId).build();
	}

	public CostDetailCreateRequest withProductIdAndQty(
			@NonNull final ProductId productId,
			@NonNull final Quantity qty)
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

	public CostDetailCreateRequest withQtyZero()
	{
		return withQty(qty.toZero());
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
				.amtType(getAmtType())
				.amt(getAmt())
				.qty(getQty())
				//
				.documentRef(getDocumentRef())
				.description(getDescription())
				.dateAcct(getDate());

		if (isExplicitCostElement())
		{
			costDetail.costElementId(getCostElementId());
		}

		return costDetail;
	}

	public Optional<CostAmountAndQty> getExternallyOwned() {return Optional.ofNullable(externallyOwned);}
}
