/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.invoice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Function;

@Value
public class ManualInvoice
{
	@NonNull
	InvoiceId invoiceId;

	@NonNull
	OrgId orgId;

	@NonNull
	String docNumber;

	@NonNull
	BPartnerLocationId billBPartnerLocationId;

	@NonNull
	PriceListId priceListId;

	@NonNull
	Instant dateInvoiced;

	@NonNull
	Instant dateAcct;

	@NonNull
	Instant dateOrdered;

	@NonNull
	DocTypeId docTypeId;

	@NonNull
	DocTypeId targetDocTypeId;

	@NonNull
	SOTrx soTrx;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	@Getter(AccessLevel.NONE)
	BigDecimal grandTotal;

	@NonNull
	@Getter(AccessLevel.NONE)
	BigDecimal totalLines;

	@Nullable
	BPartnerContactId billContactId;

	@Nullable
	String poReference;

	@Nullable
	String externalHeaderId;

	@NonNull
	ImmutableList<ManualInvoiceLine> lines;

	@NonNull
	ImmutableMap<String, ManualInvoiceLine> externalLineId2Line;

	@Builder
	public ManualInvoice(
			@NonNull final InvoiceId invoiceId,
			@NonNull final OrgId orgId,
			@NonNull final String docNumber,
			@NonNull final BPartnerLocationId billBPartnerLocationId,
			@NonNull final PriceListId priceListId,
			@NonNull final Instant dateInvoiced,
			@NonNull final Instant dateAcct,
			@NonNull final Instant dateOrdered,
			@NonNull final DocTypeId docTypeId,
			@NonNull final DocTypeId targetDocTypeId,
			@NonNull final SOTrx soTrx,
			@NonNull final CurrencyId currencyId,
			@NonNull final BigDecimal grandTotal,
			@NonNull final BigDecimal totalLines,
			@Nullable final BPartnerContactId billContactId,
			@Nullable final String poReference,
			@Nullable final String externalHeaderId,
			@NonNull final ImmutableList<ManualInvoiceLine> lines)
	{
		this.invoiceId = invoiceId;
		this.orgId = orgId;
		this.docNumber = docNumber;
		this.billBPartnerLocationId = billBPartnerLocationId;
		this.priceListId = priceListId;
		this.dateInvoiced = dateInvoiced;
		this.dateAcct = dateAcct;
		this.dateOrdered = dateOrdered;
		this.docTypeId = docTypeId;
		this.targetDocTypeId = targetDocTypeId;
		this.soTrx = soTrx;
		this.currencyId = currencyId;
		this.grandTotal = grandTotal;
		this.totalLines = totalLines;
		this.billContactId = billContactId;
		this.poReference = poReference;
		this.externalHeaderId = externalHeaderId;
		this.lines = lines;
		this.externalLineId2Line = lines.stream()
				.filter(line -> line.getExternalLineId() != null)
				.collect(ImmutableMap.toImmutableMap(ManualInvoiceLine::getExternalLineId, Function.identity()));
	}

	@NonNull
	public InvoiceAndLineId getRepoIdByExternalLineId(@NonNull final String externalLineId)
	{
		return Optional.ofNullable(externalLineId2Line.get(externalLineId))
				.map(ManualInvoiceLine::getId)
				.orElseThrow(() -> new AdempiereException("No Invoice Line found for ExternalLineId!")
						.appendParametersToMessage()
						.setParameter("ExternalLineId", externalLineId));
	}

	@NonNull
	public Money getTotalLines()
	{
		return Money.of(totalLines, currencyId);
	}

	@NonNull
	public Money getGrandTotal()
	{
		return Money.of(grandTotal, currencyId);
	}

	public void validateGrandTotal(@NonNull final Money grandTotalToTest)
	{
		final Money grandTotal = getGrandTotal();

		if (!grandTotal.equals(grandTotalToTest))
		{
			throw new AdempiereException("Given Invoice Amount does not match the grand total computed from the invoice lines!")
					.appendParametersToMessage()
					.setParameter("GrandTotalToTest", grandTotalToTest)
					.setParameter("Invoice.GrandTotal", grandTotal);
		}
	}

	public void validateTaxTotal(@NonNull final Money taxTotalToTest)
	{
		final Money grandTotal = getGrandTotal();
		final Money totalLines = getTotalLines();

		final Money taxTotal = grandTotal.subtract(totalLines);
		if (!taxTotal.equals(taxTotalToTest))
		{
			throw new AdempiereException("Given Tax Amount does not match the result from the invoice lines!")
					.appendParametersToMessage()
					.setParameter("TaxTotalToTest", taxTotalToTest)
					.setParameter("Invoice.TaxTotal", taxTotal);
		}
	}
}
