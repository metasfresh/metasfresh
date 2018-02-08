package de.metas.costing;

import java.time.LocalDate;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
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
public class CostDetailCreateRequest
{
	int acctSchemaId;
	int clientId;
	int orgId;
	int productId;
	int attributeSetInstanceId;
	CostingDocumentRef documentRef;
	/** Initial document reference (in case of reversal) */
	CostingDocumentRef initialDocumentRef;
	CostElement costElement;
	CostAmount amt;
	Quantity qty;
	int currencyConversionTypeId;
	LocalDate date;
	String description;

	@Builder(toBuilder = true)
	private CostDetailCreateRequest(
			final int acctSchemaId,
			final int clientId,
			final int orgId,
			final int productId,
			final int attributeSetInstanceId,
			@NonNull final CostingDocumentRef documentRef,
			@Nullable final CostingDocumentRef initialDocumentRef,
			@Nullable final CostElement costElement,
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			final int currencyConversionTypeId,
			@NonNull final LocalDate date,
			@Nullable final String description)
	{
		// acctSchema: not set is OK
		Check.assume(clientId > 0, "clientId > 0");
		Check.assume(productId > 0, "productId > 0");

		this.acctSchemaId = normalizeAcctSchemaId(acctSchemaId);
		this.clientId = clientId;
		this.orgId = orgId > 0 ? orgId : 0;
		this.productId = productId;
		this.attributeSetInstanceId = attributeSetInstanceId;
		this.documentRef = documentRef;
		this.costElement = costElement;
		this.initialDocumentRef = initialDocumentRef;
		this.amt = amt;
		this.qty = qty;
		this.currencyConversionTypeId = currencyConversionTypeId > 0 ? currencyConversionTypeId : 0;
		this.date = date;
		this.description = description;
	}

	private static final int normalizeAcctSchemaId(final int acctSchemaId)
	{
		return acctSchemaId > 0 ? acctSchemaId : 0;
	}

	public int getAcctSchemaId()
	{
		Check.assume(acctSchemaId > 0, "acctSchemaId shall be set for {}", this);
		return acctSchemaId;
	}

	public boolean isAllAcctSchemas()
	{
		return acctSchemaId <= 0;
	}

	public CostElement getCostElement()
	{
		Check.assumeNotNull(costElement, "costElement shall be set for {}", this);
		return costElement;
	}

	public boolean isAllCostElements()
	{
		return costElement != null;
	}

	public boolean isReversal()
	{
		return initialDocumentRef != null;
	}

	public CostDetailCreateRequest deriveByAcctSchemaId(final int acctSchemaId)
	{
		final int acctSchemaIdNorm = normalizeAcctSchemaId(acctSchemaId);
		if (this.acctSchemaId == acctSchemaIdNorm)
		{
			return this;
		}

		return toBuilder().acctSchemaId(acctSchemaIdNorm).build();
	}

	public CostDetailCreateRequest deriveByCostElement(final CostElement costElement)
	{
		if (Objects.equals(this.costElement, costElement))
		{
			return this;
		}

		return toBuilder().costElement(costElement).build();
	}

	public CostDetailCreateRequest deriveByAmount(final CostAmount amt)
	{
		if (Objects.equals(this.amt, amt))
		{
			return this;
		}

		return toBuilder().amt(amt).build();
	}
}
