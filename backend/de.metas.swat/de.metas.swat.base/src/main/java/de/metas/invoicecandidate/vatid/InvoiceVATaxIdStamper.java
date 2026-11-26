/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.invoicecandidate.vatid;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapterFactory;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.vatid.InvoiceVATaxIdService;
import de.metas.invoice.vatid.InvoiceVATaxIds;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.api.impl.ShipmentTaxLocation;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.organization.FiscalRepresentationId;
import de.metas.organization.OrgId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Stores or checks the buyer VAT ID, seller VAT ID and fiscal representation of a sales invoice before it is completed.
 * The first matching rule wins:
 * <ol>
 *     <li>an invoice with lines created from invoice candidates keeps the values stored at invoicing;
 *     a line added by hand whose tax requires a tax certificate (i.e. is exempt on a VAT ID) must resolve to these values</li>
 *     <li>a manual invoice gets the values its lines resolve to; all lines must resolve to the same values</li>
 * </ol>
 * A line is resolved from the location that taxed it (its shipment's location, else the invoice's bill-to) and from its tax.
 */
@Component
@RequiredArgsConstructor
public class InvoiceVATaxIdStamper
{
	private static final AdMessageKey MSG_MixedVATaxIds = AdMessageKey.of("C_Invoice_MixedVATaxIds");
	private static final AdMessageKey MSG_ExemptLineVATaxIdMismatch = AdMessageKey.of("C_Invoice_ExemptLineVATaxIdMismatch");
	private static final String NO_VALUE = "-";

	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	@NonNull private final InvoiceVATaxIdService invoiceVATaxIdService;

	@VisibleForTesting
	public static InvoiceVATaxIdStamper newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(
				InvoiceVATaxIdStamper.class,
				() -> new InvoiceVATaxIdStamper(InvoiceVATaxIdService.newInstanceForUnitTesting()));
	}

	/**
	 * Sets the VAT ID columns on the given invoice (without saving it) or fails if its lines do not agree; purchase invoices are left alone.
	 */
	public void stampOrCheck(@NonNull final I_C_Invoice invoice)
	{
		if (!invoice.isSOTrx())
		{
			return;
		}

		final List<I_C_InvoiceLine> lines = ImmutableList.copyOf(invoiceDAO.retrieveLines(invoice));
		final Set<Integer> allocatedLineIds = retrieveLineIdsWithAllocation(lines);
		if (!allocatedLineIds.isEmpty())
		{
			checkHandAddedExemptLines(invoice, lines, allocatedLineIds);
		}
		else
		{
			stampManualInvoice(invoice, lines);
		}
	}

	/**
	 * A candidate invoice keeps its stored values; an invoice without stored buyer VAT ID was not stamped (e.g. prepared before the values were stored) and is not checked.
	 * Lines with allocations were resolved at invoicing; only the lines added by hand are checked.
	 */
	private void checkHandAddedExemptLines(
			@NonNull final I_C_Invoice invoice,
			@NonNull final List<I_C_InvoiceLine> lines,
			@NonNull final Set<Integer> allocatedLineIds)
	{
		final InvoiceVATaxIds stored = getStoredVATaxIds(invoice);
		if (stored.getBuyerVATaxId() == null)
		{
			return;
		}

		for (final I_C_InvoiceLine line : lines)
		{
			if (line.isDescription() || allocatedLineIds.contains(line.getC_InvoiceLine_ID()))
			{
				continue;
			}

			final TaxId lineTaxId = TaxId.ofRepoId(line.getC_Tax_ID());
			if (!taxDAO.getTaxById(lineTaxId).isRequiresTaxCertificate())
			{
				continue;
			}

			final InvoiceVATaxIds lineVATaxIds = resolve(invoice, line, lineTaxId);
			assertSameVATaxId(line, lineVATaxIds, stored, InvoiceVATaxIds::getBuyerVATaxId);
			assertSameVATaxId(line, lineVATaxIds, stored, InvoiceVATaxIds::getSellerVATaxId);
		}
	}

	private static void assertSameVATaxId(
			@NonNull final I_C_InvoiceLine line,
			@NonNull final InvoiceVATaxIds lineVATaxIds,
			@NonNull final InvoiceVATaxIds stored,
			@NonNull final Function<InvoiceVATaxIds, String> vatTaxIdGetter)
	{
		final String lineVATaxId = vatTaxIdGetter.apply(lineVATaxIds);
		final String storedVATaxId = vatTaxIdGetter.apply(stored);
		if (!Objects.equals(lineVATaxId, storedVATaxId))
		{
			throw new AdempiereException(MSG_ExemptLineVATaxIdMismatch, line.getLine(), toDisplayString(lineVATaxId), toDisplayString(storedVATaxId))
					.markAsUserValidationError();
		}
	}

	/**
	 * All non-description lines must resolve to the same values, which are stored; an invoice without such lines stores none.
	 */
	private void stampManualInvoice(@NonNull final I_C_Invoice invoice, @NonNull final List<I_C_InvoiceLine> lines)
	{
		final Set<InvoiceVATaxIds> distinctVATaxIds = new LinkedHashSet<>();
		for (final I_C_InvoiceLine line : lines)
		{
			if (!line.isDescription())
			{
				distinctVATaxIds.add(resolve(invoice, line, TaxId.ofRepoId(line.getC_Tax_ID())));
			}
		}

		if (distinctVATaxIds.size() > 1)
		{
			throw new AdempiereException(MSG_MixedVATaxIds, joinDistinct(distinctVATaxIds, InvoiceVATaxIds::getBuyerVATaxId), joinDistinct(distinctVATaxIds, InvoiceVATaxIds::getSellerVATaxId))
					.markAsUserValidationError();
		}

		final InvoiceVATaxIds vatTaxIds = distinctVATaxIds.isEmpty() ? new InvoiceVATaxIds(null, null, null) : distinctVATaxIds.iterator().next();
		invoice.setBuyerVATaxID(vatTaxIds.getBuyerVATaxId());
		invoice.setSellerVATaxID(vatTaxIds.getSellerVATaxId());
		invoice.setC_Fiscal_Representation_ID(RepoIdAwares.toRepoId(vatTaxIds.getFiscalRepresentationId()));
	}

	@NonNull
	private InvoiceVATaxIds resolve(@NonNull final I_C_Invoice invoice, @NonNull final I_C_InvoiceLine line, @NonNull final TaxId lineTaxId)
	{
		return invoiceVATaxIdService.resolve(getTaxLocation(invoice, line), lineTaxId);
	}

	/**
	 * The location that taxed a line without allocation (see {@code MInvoiceLine.setTax}): its shipment's location if it has a shipment line, else the invoice's bill-to.
	 */
	@NonNull
	private TaxLocation getTaxLocation(@NonNull final I_C_Invoice invoice, @NonNull final I_C_InvoiceLine line)
	{
		final InOutLineId shipmentLineId = InOutLineId.ofRepoIdOrNull(line.getM_InOutLine_ID());
		if (shipmentLineId != null)
		{
			final I_M_InOut shipment = inOutDAO.getById(InOutId.ofRepoId(inOutDAO.getLineByIdInTrx(shipmentLineId).getM_InOut_ID()));
			return ShipmentTaxLocation.of(shipment, OrgId.ofRepoId(shipment.getAD_Org_ID()));
		}

		return new TaxLocation(
				InvoiceDocumentLocationAdapterFactory.locationAdapter(invoice).getBPartnerLocationAndCaptureId().getBpartnerLocationId(),
				OrgId.ofRepoId(invoice.getAD_Org_ID()),
				invoice.getDateInvoiced(),
				null);
	}

	@NonNull
	private Set<Integer> retrieveLineIdsWithAllocation(@NonNull final List<I_C_InvoiceLine> lines)
	{
		if (lines.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL.createQueryBuilder(I_C_Invoice_Line_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Invoice_Line_Alloc.COLUMNNAME_C_InvoiceLine_ID, lines.stream().map(I_C_InvoiceLine::getC_InvoiceLine_ID).collect(ImmutableSet.toImmutableSet()))
				.create()
				.stream()
				.map(I_C_Invoice_Line_Alloc::getC_InvoiceLine_ID)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private static InvoiceVATaxIds getStoredVATaxIds(@NonNull final I_C_Invoice invoice)
	{
		return new InvoiceVATaxIds(
				invoice.getBuyerVATaxID(),
				invoice.getSellerVATaxID(),
				FiscalRepresentationId.ofRepoIdOrNull(invoice.getC_Fiscal_Representation_ID()));
	}

	@NonNull
	private static String joinDistinct(@NonNull final Set<InvoiceVATaxIds> vatTaxIds, @NonNull final Function<InvoiceVATaxIds, String> vatTaxIdGetter)
	{
		return vatTaxIds.stream()
				.map(vatTaxIdGetter)
				.map(InvoiceVATaxIdStamper::toDisplayString)
				.distinct()
				.collect(Collectors.joining(", "));
	}

	/**
	 * An empty seller VAT ID means the organisation's main VAT ID; an empty buyer VAT ID means none was resolved.
	 */
	@NonNull
	private static String toDisplayString(@Nullable final String vatTaxId)
	{
		return vatTaxId != null ? vatTaxId : NO_VALUE;
	}
}
