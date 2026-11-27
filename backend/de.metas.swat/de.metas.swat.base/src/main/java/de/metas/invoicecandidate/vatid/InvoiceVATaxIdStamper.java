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
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapterFactory;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.invoice.service.InvoiceLineReTaxInput;
import de.metas.invoice.vatid.InvoiceVATaxIdService;
import de.metas.invoice.vatid.InvoiceVATaxIds;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.api.impl.ShipmentTaxLocation;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.organization.FiscalRepresentationId;
import de.metas.organization.OrgId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Stores or checks the buyer VAT ID, seller VAT ID and fiscal representation of a sales invoice before it is completed.
 * The first matching rule wins:
 * <ol>
 *     <li>a reversal, or a credit memo that references the sales invoice it was copied from, gets the values stored on that invoice, as they are (also when empty);
 *     its lines are never resolved again (see {@link #getCopiedFromInvoice(I_C_Invoice)}). Any other copy starts without values ({@code IInvoiceBL#copyFrom})
 *     and is handled like an invoice of its own by the next rules.</li>
 *     <li>an invoice with lines created from invoice candidates keeps the values stored at invoicing;
 *     a line added by hand whose tax requires a tax certificate (i.e. is exempt on a VAT ID) must resolve to the stored buyer and seller VAT ID
 *     (the fiscal representation is not compared: the seller VAT ID is what the invoice shows).
 *     If the invoice's doc type overwrites the invoice date on completion, the lines are also checked with the tax the re-tax on that {@code DateInvoiced} change gives them;
 *     the date is overwritten at the start of the completion, before this check ({@code MInvoice.completeIt}), so normally the lines already carry that tax and the prediction finds the same one.</li>
 *     <li>a manual invoice gets the values its lines resolve to, and all lines must resolve to the same values -
 *     but only if at least one line resolves a VAT ID of an address or a fiscal representation; otherwise it stores none and completes as before</li>
 * </ol>
 * A line is resolved from the location that taxed it and from its tax (see {@link LineResolver#getTaxLocation(I_C_InvoiceLine)}).
 */
@Component
@RequiredArgsConstructor
public class InvoiceVATaxIdStamper
{
	private static final AdMessageKey MSG_MixedVATaxIds = AdMessageKey.of("C_Invoice_MixedVATaxIds");
	private static final AdMessageKey MSG_ExemptLineVATaxIdMismatch = AdMessageKey.of("C_Invoice_ExemptLineVATaxIdMismatch");
	private static final String NO_VALUE = "-";
	
	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
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

		final I_C_Invoice copiedFromInvoice = getCopiedFromInvoice(invoice);
		if (copiedFromInvoice != null)
		{
			// the copy's lines were resolved when the original was completed; re-resolving them from today's master data could block the copy or change its values
			setVATaxIds(invoice, getStoredVATaxIds(copiedFromInvoice));
			return;
		}

		final List<I_C_InvoiceLine> lines = ImmutableList.copyOf(invoiceDAO.retrieveLines(invoice));
		final Set<Integer> allocatedLineIds = retrieveLineIdsWithAllocation(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
		final LineResolver lineResolver = new LineResolver(invoice);
		if (!allocatedLineIds.isEmpty())
		{
			checkHandAddedExemptLines(invoice, lines, allocatedLineIds, lineResolver);
		}
		else
		{
			stampManualInvoice(invoice, lines, lineResolver);
		}
	}

	/**
	 * @return the sales invoice the given one was copied from, if its VAT IDs come from there: the reversed invoice of a reversal,
	 * or the credited invoice of a credit memo that references it; {@code null} otherwise.
	 * A referenced purchase invoice (e.g. the purchase credit memo of a sales counter document) has no stored values and gives {@code null}.
	 */
	@Nullable
	private I_C_Invoice getCopiedFromInvoice(@NonNull final I_C_Invoice invoice)
	{
		InvoiceId copiedFromInvoiceId = InvoiceId.ofRepoIdOrNull(invoice.getReversal_ID());
		if (copiedFromInvoiceId == null)
		{
			final InvoiceId creditedInvoiceId = InvoiceId.ofRepoIdOrNull(invoice.getRef_Invoice_ID());
			if (creditedInvoiceId == null || !invoiceBL.isCreditMemo(invoice))
			{
				return null;
			}
			copiedFromInvoiceId = creditedInvoiceId;
		}

		final I_C_Invoice copiedFromInvoice = invoiceBL.getById(copiedFromInvoiceId);
		return copiedFromInvoice.isSOTrx() ? copiedFromInvoice : null;
	}

	/**
	 * A candidate invoice keeps its stored values; an invoice without stored buyer VAT ID was not stamped (e.g. prepared before the values were stored) and is not checked.
	 * Lines with allocations were resolved at invoicing; only the lines added by hand are checked, with their tax and, if the completion re-taxes them, with the tax they will get.
	 */
	private void checkHandAddedExemptLines(
			@NonNull final I_C_Invoice invoice,
			@NonNull final List<I_C_InvoiceLine> lines,
			@NonNull final Set<Integer> allocatedLineIds,
			@NonNull final LineResolver lineResolver)
	{
		final InvoiceVATaxIds stored = getStoredVATaxIds(invoice);
		if (stored.getBuyerVATaxId() == null)
		{
			return;
		}

		final boolean isReTaxedOnCompletion = isDateOverwrittenOnCompletion(invoice);

		for (final I_C_InvoiceLine line : lines)
		{
			if (line.isDescription() || allocatedLineIds.contains(line.getC_InvoiceLine_ID()))
			{
				continue;
			}

			final TaxId lineTaxId = TaxId.ofRepoId(line.getC_Tax_ID());
			if (isRequiresTaxCertificate(lineTaxId))
			{
				assertSameVATaxIds(line, lineResolver.resolve(line).getVatTaxIds(), stored);
			}

			if (isReTaxedOnCompletion)
			{
				final InvoiceLineReTaxInput reTaxInput = invoiceBL.getReTaxInput(invoice, line, SystemTime.asTimestamp());
				final TaxId reTaxTaxId = predictReTaxTax(line, reTaxInput);
				if (reTaxTaxId != null && !reTaxTaxId.equals(lineTaxId) && isRequiresTaxCertificate(reTaxTaxId))
				{
					assertSameVATaxIds(line, lineResolver.resolve(toTaxLocation(reTaxInput), reTaxTaxId), stored);
				}
			}
		}
	}

	private boolean isRequiresTaxCertificate(@NonNull final TaxId taxId)
	{
		return taxDAO.getTaxById(taxId).isRequiresTaxCertificate();
	}

	/**
	 * @return {@code true} if completing the invoice sets {@code DateInvoiced} to now ({@code C_DocType.IsOverwriteDateOnComplete}), which re-taxes its lines.
	 */
	private boolean isDateOverwrittenOnCompletion(@NonNull final I_C_Invoice invoice)
	{
		final DocTypeId docTypeId = CoalesceUtil.coalesce(DocTypeId.ofRepoIdOrNull(invoice.getC_DocType_ID()), DocTypeId.ofRepoIdOrNull(invoice.getC_DocTypeTarget_ID()));
		return docTypeId != null && docTypeDAO.getById(docTypeId).isOverwriteDateOnComplete();
	}

	/**
	 * The tax the re-tax ({@link IInvoiceBL#setInvoiceLineTaxes}) gives the given line with the given inputs; read-only.
	 *
	 * @return {@code null} if the re-tax leaves the line alone (no product) or finds no tax
	 */
	@Nullable
	private TaxId predictReTaxTax(@NonNull final I_C_InvoiceLine line, @NonNull final InvoiceLineReTaxInput reTaxInput)
	{
		return invoiceLineBL.createReTaxQuery(line, reTaxInput)
				.map(taxDAO::getBy)
				.map(Tax::getTaxId)
				.orElse(null);
	}

	@NonNull
	private static TaxLocation toTaxLocation(@NonNull final InvoiceLineReTaxInput reTaxInput)
	{
		final BPartnerLocationAndCaptureId location = reTaxInput.getTaxLocation();
		return new TaxLocation(location.getBpartnerLocationId(), reTaxInput.getOrgId(), reTaxInput.getTaxDate(), reTaxInput.getCountryFromId(), reTaxInput.getWarehouseId())
				.withLocationCaptureId(location.getLocationCaptureId());
	}

	private static void assertSameVATaxIds(
			@NonNull final I_C_InvoiceLine line,
			@NonNull final InvoiceVATaxIds lineVATaxIds,
			@NonNull final InvoiceVATaxIds stored)
	{
		assertSameVATaxId(line, lineVATaxIds, stored, InvoiceVATaxIds::getBuyerVATaxId);
		assertSameVATaxId(line, lineVATaxIds, stored, InvoiceVATaxIds::getSellerVATaxId);
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
	 * If any non-description line resolves a VAT ID of an address or a fiscal representation, all such lines must resolve to the same values, which are stored.
	 * Otherwise (also: no line) no values are stored.
	 */
	private void stampManualInvoice(
			@NonNull final I_C_Invoice invoice,
			@NonNull final List<I_C_InvoiceLine> lines,
			@NonNull final LineResolver lineResolver)
	{
		final Set<InvoiceVATaxIds> distinctVATaxIds = new LinkedHashSet<>();
		boolean isAnyLineByAddressOrFiscalRepresentation = false;
		for (final I_C_InvoiceLine line : lines)
		{
			if (line.isDescription())
			{
				continue;
			}
			final ResolvedLine resolvedLine = lineResolver.resolve(line);
			distinctVATaxIds.add(resolvedLine.getVatTaxIds());
			isAnyLineByAddressOrFiscalRepresentation = isAnyLineByAddressOrFiscalRepresentation || resolvedLine.isByAddressOrFiscalRepresentation();
		}

		if (!isAnyLineByAddressOrFiscalRepresentation)
		{
			setVATaxIds(invoice, InvoiceVATaxIds.NONE);
			return;
		}

		if (distinctVATaxIds.size() > 1)
		{
			throw new AdempiereException(MSG_MixedVATaxIds, joinDistinct(distinctVATaxIds, InvoiceVATaxIds::getBuyerVATaxId), joinDistinct(distinctVATaxIds, InvoiceVATaxIds::getSellerVATaxId))
					.markAsUserValidationError();
		}

		setVATaxIds(invoice, distinctVATaxIds.iterator().next());
	}

	private static void setVATaxIds(@NonNull final I_C_Invoice invoice, @NonNull final InvoiceVATaxIds vatTaxIds)
	{
		invoice.setBuyerVATaxID(vatTaxIds.getBuyerVATaxId());
		invoice.setSellerVATaxID(vatTaxIds.getSellerVATaxId());
		invoice.setC_Fiscal_Representation_ID(RepoIdAwares.toRepoId(vatTaxIds.getFiscalRepresentationId()));
	}

	@NonNull
	private Set<Integer> retrieveLineIdsWithAllocation(@NonNull final InvoiceId invoiceId)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Line_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(
						I_C_Invoice_Line_Alloc.COLUMNNAME_C_InvoiceLine_ID,
						I_C_InvoiceLine.COLUMNNAME_C_InvoiceLine_ID,
						queryBL.createQueryBuilder(I_C_InvoiceLine.class)
								.addEqualsFilter(I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID, invoiceId)
								.create())
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

	@Value
	private static class ResolvedLine
	{
		@NonNull InvoiceVATaxIds vatTaxIds;

		/**
		 * {@code true} if the line's location has an own VAT ID or the line got a fiscal representation, i.e. the line uses VAT IDs by delivery
		 */
		boolean byAddressOrFiscalRepresentation;
	}

	@Value
	private static class ResolveKey
	{
		@NonNull TaxLocation taxLocation;
		@NonNull TaxId taxId;
	}

	/**
	 * Resolves the lines of one invoice; shipments, resolutions and location VAT IDs are looked up once per invoice.
	 */
	private class LineResolver
	{
		@NonNull private final I_C_Invoice invoice;
		@Nullable private TaxLocation billToTaxLocation;
		private final Map<InOutId, TaxLocation> shipmentTaxLocations = new HashMap<>();
		private final Map<ResolveKey, InvoiceVATaxIds> resolvedVATaxIds = new HashMap<>();
		private final Map<BPartnerLocationId, Boolean> locationsWithVATaxId = new HashMap<>();

		private LineResolver(@NonNull final I_C_Invoice invoice)
		{
			this.invoice = invoice;
		}

		@NonNull
		private ResolvedLine resolve(@NonNull final I_C_InvoiceLine line)
		{
			final TaxLocation taxLocation = getTaxLocation(line);
			final InvoiceVATaxIds vatTaxIds = resolve(taxLocation, TaxId.ofRepoId(line.getC_Tax_ID()));

			final boolean byAddressOrFiscalRepresentation = vatTaxIds.getFiscalRepresentationId() != null
					|| locationsWithVATaxId.computeIfAbsent(taxLocation.getBpartnerLocationId(), invoiceVATaxIdService::hasOwnVATaxId);

			return new ResolvedLine(vatTaxIds, byAddressOrFiscalRepresentation);
		}

		@NonNull
		private InvoiceVATaxIds resolve(@NonNull final TaxLocation taxLocation, @NonNull final TaxId taxId)
		{
			return resolvedVATaxIds.computeIfAbsent(
					new ResolveKey(taxLocation, taxId),
					key -> invoiceVATaxIdService.resolve(key.getTaxLocation(), key.getTaxId()));
		}

		/**
		 * The location that taxed a line without allocation:
		 * <ul>
		 *     <li>its shipment's location if it has a shipment line (see {@code MInvoiceLine.setTax});</li>
		 *     <li>if it has an order line and the order line's tax (copied when the line is created from the order) or the tax the re-tax gives from the order line's location
		 *     (i.e. it was re-taxed after a date or warehouse change): the order line's location with the re-tax inputs;</li>
		 *     <li>else the invoice's bill-to ({@code MInvoiceLine.setTax} does not look at the order line).</li>
		 * </ul>
		 */
		@NonNull
		private TaxLocation getTaxLocation(@NonNull final I_C_InvoiceLine line)
		{
			final InOutLineId shipmentLineId = InOutLineId.ofRepoIdOrNull(line.getM_InOutLine_ID());
			if (shipmentLineId != null)
			{
				final InOutId shipmentId = InOutId.ofRepoId(inOutDAO.getLineByIdInTrx(shipmentLineId).getM_InOut_ID());
				return shipmentTaxLocations.computeIfAbsent(shipmentId, id -> {
					final I_M_InOut shipment = inOutDAO.getById(id);
					return ShipmentTaxLocation.of(shipment, OrgId.ofRepoId(shipment.getAD_Org_ID()));
				});
			}

			final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(line.getC_OrderLine_ID());
			if (orderLineId != null)
			{
				final TaxLocation orderLineTaxLocation = getOrderLineTaxLocationIfItTaxedLine(line, orderLineId);
				if (orderLineTaxLocation != null)
				{
					return orderLineTaxLocation;
				}
			}

			if (billToTaxLocation == null)
			{
				billToTaxLocation = new TaxLocation(
						InvoiceDocumentLocationAdapterFactory.locationAdapter(invoice).getBPartnerLocationAndCaptureId().getBpartnerLocationId(),
						OrgId.ofRepoId(invoice.getAD_Org_ID()),
						invoice.getDateInvoiced(),
						null);
			}
			return billToTaxLocation;
		}

		/**
		 * @return the order line's tax location with the inputs of the re-tax (which uses the order line's location for such a line, see {@link IInvoiceBL#getReTaxInput}),
		 * if the order line's location taxed the given line; {@code null} otherwise
		 */
		@Nullable
		private TaxLocation getOrderLineTaxLocationIfItTaxedLine(@NonNull final I_C_InvoiceLine line, @NonNull final OrderLineId orderLineId)
		{
			final TaxId lineTaxId = TaxId.ofRepoId(line.getC_Tax_ID());
			final InvoiceLineReTaxInput reTaxInput = invoiceBL.getReTaxInput(invoice, line, null);

			final boolean isOrderLineTax = lineTaxId.equals(TaxId.ofRepoIdOrNull(orderDAO.getOrderLineById(orderLineId).getC_Tax_ID()));
			if (isOrderLineTax || lineTaxId.equals(predictReTaxTax(line, reTaxInput)))
			{
				return toTaxLocation(reTaxInput);
			}
			return null;
		}
	}
}
