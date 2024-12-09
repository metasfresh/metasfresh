package de.metas.costing;

import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostDetail.CostDetailBuilder;
<<<<<<< HEAD
import de.metas.money.CurrencyConversionTypeId;
=======
import de.metas.costing.methods.CostAmountType;
import de.metas.currency.CurrencyConversionContext;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
import java.time.LocalDate;
=======
import java.time.Instant;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.util.Objects;

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
<<<<<<< HEAD
	AcctSchemaId acctSchemaId;
	ClientId clientId;
	OrgId orgId;
	ProductId productId;
	AttributeSetInstanceId attributeSetInstanceId;
	CostingDocumentRef documentRef;
	/**
	 * Initial document reference (in case of reversal)
	 */
	CostingDocumentRef initialDocumentRef;
	CostElement costElement;
	CostAmount amt;
	Quantity qty;
	CurrencyConversionTypeId currencyConversionTypeId;
	LocalDate date;
	String description;

	CostAmount explicitCostPrice;
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			@Nullable final CurrencyConversionTypeId currencyConversionTypeId,
			@NonNull final LocalDate date,
=======
			@Nullable final CostAmountType amtType,
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			@Nullable final CurrencyConversionContext currencyConversionContext,
			@NonNull final Instant date,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			@Nullable final String description,
			@Nullable final CostAmount explicitCostPrice)
	{
		this.acctSchemaId = acctSchemaId;
		this.clientId = clientId;
		this.orgId = orgId;
		this.productId = productId;
		this.attributeSetInstanceId = attributeSetInstanceId;
		this.documentRef = documentRef;
		this.costElement = costElement;
		this.initialDocumentRef = initialDocumentRef;
<<<<<<< HEAD
		this.amt = amt;
		this.qty = qty;
		this.currencyConversionTypeId = currencyConversionTypeId;
=======
		this.amtType = amtType != null ? amtType : CostAmountType.MAIN;
		this.amt = amt;
		this.qty = qty;
		this.currencyConversionContext = currencyConversionContext;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		this.date = date;
		this.description = description;
		this.explicitCostPrice = explicitCostPrice;
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

<<<<<<< HEAD
	public boolean isAllCostElements()
	{
		return costElement == null;
=======
	public boolean isExplicitCostElement()
	{
		return costElement != null;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public boolean isReversal()
	{
		return getInitialDocumentRef() != null;
	}

<<<<<<< HEAD
=======
	public boolean isOutbound()
	{
		return getQty().signum() < 0 && !isReversal();
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		if (Objects.equals(this.amt, amt))
=======
		return withAmountAndType(amt, this.amtType);
	}

	public CostDetailCreateRequest withAmountAndType(@NonNull final CostAmount amt, @NonNull final CostAmountType amtType)
	{
		if (Objects.equals(this.amt, amt)
				&& Objects.equals(this.amtType, amtType))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return this;
		}

<<<<<<< HEAD
		return toBuilder().amt(amt).build();
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
=======
	public CostDetailCreateRequest withQtyZero()
	{
		return withQty(qty.toZero());
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public CostDetailBuilder toCostDetailBuilder()
	{
		final CostDetailBuilder costDetail = CostDetail.builder()
				.clientId(getClientId())
				.orgId(getOrgId())
				.acctSchemaId(getAcctSchemaId())
				.productId(getProductId())
				.attributeSetInstanceId(getAttributeSetInstanceId())
				//
<<<<<<< HEAD
=======
				.amtType(getAmtType())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.amt(getAmt())
				.qty(getQty())
				//
				.documentRef(getDocumentRef())
<<<<<<< HEAD
				.description(getDescription());

		if (!isAllCostElements())
=======
				.description(getDescription())
				.dateAcct(getDate());

		if (isExplicitCostElement())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			costDetail.costElementId(getCostElementId());
		}

		return costDetail;
	}
}
