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
import java.util.Comparator;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_C_OrderLine;
import org.adempiere.model.InterfaceWrapperHelper;
import de.metas.invoicecandidate.InvoiceCandidateId;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.util.StringUtils;
import org.compiere.model.I_C_BPartner;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.location.adapter.InvoiceDocumentLocationAdapterFactory;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.invoice.service.InvoiceLineReTaxInput;
import de.metas.invoice.vatid.InvoiceVATaxIdService;
import de.metas.invoice.vatid.InvoiceVATaxIds;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.api.impl.ShipmentTaxLocation;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
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
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Stores the buyer VAT ID, seller VAT ID and fiscal representation of a sales invoice before it is completed, from its final line taxes ("the VAT ID follows the tax").
 * <ol>
 *     <li>a reversal gets the values stored on the reversed invoice, as they are (also when empty): it keeps that invoice's taxes, its lines are not resolved again.</li>
 *     <li>if no line can use VAT IDs by delivery (see {@link #isAnyLineByDeliveryPossible}), the invoice stores none and no line is resolved.</li>
 *     <li>an invoice with lines created from invoice candidates gets the values its candidate lines resolve to with their final taxes, from the location and date that taxed them
 *     (see {@link LineResolver#getCandidateLineTaxLocation}); so a date or warehouse change that re-taxed them, by hand or on completion, also changes the stored values.
 *     The values resolved at invoicing only decided which candidates share an invoice.
 *     A line added by hand whose tax requires a tax certificate (i.e. is exempt on a VAT ID) must resolve to these values
 *     (the fiscal representation is not compared: the seller VAT ID is what the invoice shows) - unless neither the invoice nor that line uses VAT IDs by delivery.
 *     If the invoice's doc type overwrites the invoice date on completion, the hand-added lines are also checked with the tax the re-tax on that {@code DateInvoiced} change gives them;
 *     the date is overwritten at the start of the completion, before this check ({@code MInvoice.completeIt}), so normally the lines already carry that tax and the prediction finds the same one.</li>
 *     <li>any other invoice (manual invoices, copies, credit memos) gets the values its lines resolve to. A line of a credit memo that still has the tax of the sales invoice line it credits
 *     (or, if no line references a line of the credited invoice, one of that invoice's line taxes) was taxed where that line was,
 *     so it resolves to the values stored on the credited invoice (also when empty); a re-taxed one resolves from its own tax location;
 *     a line of such a credit memo that credits nothing was added by hand and is only checked like one on a candidate invoice.
 *     An exempt line whose tax location is not in its tax's destination country blocks the completion (the VAT ID that justified the exemption is unknown),
 *     if VAT IDs by delivery are used at all (see {@link #stampManualInvoice}).
 *     Lines that do not use VAT IDs by delivery resolve to none, so an invoice of only such lines stores none and completes as before.</li>
 * </ol>
 * In each case, lines that resolve different values block the completion.
 * A line is resolved from the location that taxed it and from its tax (see {@link LineResolver#getTaxLocation(I_C_InvoiceLine)}),
 * with the same rule as invoice candidates ({@link InvoiceVATaxIdService#resolveIfByDelivery}).
 */
@Component
@RequiredArgsConstructor
public class InvoiceVATaxIdStamper
{
	private static final AdMessageKey MSG_MixedVATaxIds = AdMessageKey.of("C_Invoice_MixedVATaxIds");
	private static final AdMessageKey MSG_ExemptLineVATaxIdMismatch = AdMessageKey.of("C_Invoice_ExemptLineVATaxIdMismatch");
	private static final AdMessageKey MSG_SellerVATaxIdMismatch = AdMessageKey.of("C_Invoice_SellerVATaxIdMismatch");
	private static final AdMessageKey MSG_ExemptLineNoDeliveryToTaxCountry = AdMessageKey.of("C_Invoice_ExemptLineNoDeliveryToTaxCountry");
	private static final String NO_VALUE = "-";

	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	@NonNull private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	@NonNull private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IBPartnerOrgBL bpartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	@NonNull private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
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

		final InvoiceId reversedInvoiceId = InvoiceId.ofRepoIdOrNull(invoice.getReversal_ID());
		if (reversedInvoiceId != null)
		{
			// a reversal keeps the reversed invoice's taxes (it is never re-taxed), so it also keeps its values
			setVATaxIds(invoice, getStoredVATaxIds(invoiceBL.getById(reversedInvoiceId)));
			return;
		}

		final List<I_C_InvoiceLine> lines = ImmutableList.copyOf(invoiceDAO.retrieveLines(invoice));
		final I_C_Invoice creditedInvoice = getCreditedSalesInvoice(invoice);
		if (!isAnyLineByDeliveryPossible(invoice, lines, creditedInvoice))
		{
			setVATaxIds(invoice, InvoiceVATaxIds.NONE);
			return;
		}

		final ImmutableMap<InvoiceLineId, ImmutableList<InvoiceCandidateId>> candidateIdsByLineId = invoiceCandDAO.retrieveInvoiceCandidateIdsByAllocatedLine(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
		final LineResolver lineResolver = new LineResolver(invoice, lines);
		if (!candidateIdsByLineId.isEmpty())
		{
			final Set<InvoiceLineId> allocatedLineIds = candidateIdsByLineId.keySet();
			stampCandidateInvoice(invoice, lines, candidateIdsByLineId, lineResolver);
			checkHandAddedExemptLines(invoice, lines, allocatedLineIds, lineResolver);
		}
		else
		{
			stampManualInvoice(invoice, lines, lineResolver, creditedInvoice);
		}
	}

	/**
	 * A pure optimisation, it never changes a result: {@code false} only if no line can resolve to values
	 * (see {@link InvoiceVATaxIdService#isVATaxIdByDeliveryPossible}): no org has a fiscal representation, no location has a VAT ID,
	 * no line's tax is exempt on a VAT ID, the completion does not re-tax the lines and a credited invoice has no stored values.
	 * For invoices of customers who cannot use VAT IDs by delivery this costs no query beyond the lines (the taxes and the doc type are cached).
	 */
	private boolean isAnyLineByDeliveryPossible(
			@NonNull final I_C_Invoice invoice,
			@NonNull final List<I_C_InvoiceLine> lines,
			@Nullable final I_C_Invoice creditedInvoice)
	{
		if (invoiceVATaxIdService.isVATaxIdByDeliveryPossible())
		{
			return true;
		}
		if (creditedInvoice != null && !getStoredVATaxIds(creditedInvoice).isNone())
		{
			return true;
		}
		if (isDateOverwrittenOnCompletion(invoice))
		{
			return true; // a line added by hand is checked with the tax the re-tax would give it
		}
		return lines.stream()
				.filter(line -> !line.isDescription() && line.getC_Tax_ID() > 0)
				.map(line -> TaxId.ofRepoId(line.getC_Tax_ID()))
				.distinct()
				.anyMatch(invoiceVATaxIdService::isRequiresTaxCertificate);
	}

	/**
	 * @return the sales invoice a credit memo credits (references), {@code null} otherwise.
	 * A referenced purchase invoice (e.g. the purchase credit memo of a sales counter document) has no stored values and gives {@code null}.
	 */
	@Nullable
	private I_C_Invoice getCreditedSalesInvoice(@NonNull final I_C_Invoice invoice)
	{
		final InvoiceId creditedInvoiceId = InvoiceId.ofRepoIdOrNull(invoice.getRef_Invoice_ID());
		if (creditedInvoiceId == null || !invoiceBL.isCreditMemo(invoice))
		{
			return null;
		}

		final I_C_Invoice creditedInvoice = invoiceBL.getById(creditedInvoiceId);
		return creditedInvoice.isSOTrx() ? creditedInvoice : null;
	}

	/**
	 * Stores the values the candidate lines resolve to with their final taxes: a line with a shipment line from its shipment's location (see {@code MInvoiceLine.setTax}),
	 * any other one from {@link LineResolver#getCandidateLineTaxLocation}.
	 * If the lines do not agree, the completion fails like a mixed manual invoice.
	 */
	private void stampCandidateInvoice(
			@NonNull final I_C_Invoice invoice,
			@NonNull final List<I_C_InvoiceLine> lines,
			@NonNull final ImmutableMap<InvoiceLineId, ImmutableList<InvoiceCandidateId>> candidateIdsByLineId,
			@NonNull final LineResolver lineResolver)
	{
		final Set<InvoiceVATaxIds> distinctVATaxIds = new LinkedHashSet<>();
		for (final I_C_InvoiceLine line : lines)
		{
			if (line.isDescription() || !isAllocated(line, candidateIdsByLineId.keySet()))
			{
				continue;
			}

			final TaxId lineTaxId = TaxId.ofRepoId(line.getC_Tax_ID());
			final TaxLocation taxLocation = line.getM_InOutLine_ID() > 0
					? lineResolver.getShipmentTaxLocation(InOutLineId.ofRepoId(line.getM_InOutLine_ID()))
					: lineResolver.getCandidateLineTaxLocation(line, lineTaxId, candidateIdsByLineId);
			distinctVATaxIds.add(lineResolver.resolve(taxLocation, lineTaxId));
		}

		setUniqueVATaxIds(invoice, distinctVATaxIds);
	}

	private static boolean isAllocated(@NonNull final I_C_InvoiceLine line, @NonNull final Set<InvoiceLineId> allocatedLineIds)
	{
		return allocatedLineIds.contains(InvoiceLineId.ofRepoId(line.getC_InvoiceLine_ID()));
	}

	/**
	 * The lines added by hand to a candidate invoice are checked against the values its candidate lines resolved to,
	 * with their tax and, if the completion re-taxes them, with the tax they will get.
	 * A line is not checked if neither the invoice nor the line uses VAT IDs by delivery.
	 */
	private void checkHandAddedExemptLines(
			@NonNull final I_C_Invoice invoice,
			@NonNull final List<I_C_InvoiceLine> lines,
			@NonNull final Set<InvoiceLineId> allocatedLineIds,
			@NonNull final LineResolver lineResolver)
	{
		final InvoiceVATaxIds stored = getStoredVATaxIds(invoice);
		final boolean isReTaxedOnCompletion = isDateOverwrittenOnCompletion(invoice);

		for (final I_C_InvoiceLine line : lines)
		{
			if (line.isDescription() || isAllocated(line, allocatedLineIds))
			{
				continue;
			}

			final TaxId lineTaxId = TaxId.ofRepoId(line.getC_Tax_ID());
			if (isRequiresTaxCertificate(lineTaxId))
			{
				assertSameVATaxIds(invoice, line, lineResolver.resolve(line), stored);
			}

			if (isReTaxedOnCompletion)
			{
				final InvoiceLineReTaxInput reTaxInput = invoiceBL.getReTaxInput(invoice, line, SystemTime.asTimestamp());
				final TaxId reTaxTaxId = predictReTaxTax(line, reTaxInput);
				if (reTaxTaxId != null && !reTaxTaxId.equals(lineTaxId) && isRequiresTaxCertificate(reTaxTaxId))
				{
					assertSameVATaxIds(invoice, line, lineResolver.resolve(toTaxLocation(reTaxInput), reTaxTaxId), stored);
				}
			}
		}
	}

	private boolean isRequiresTaxCertificate(@NonNull final TaxId taxId)
	{
		return invoiceVATaxIdService.isRequiresTaxCertificate(taxId);
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

	/**
	 * The given line's values must be the stored ones; the error shows the VAT IDs the line and the invoice show,
	 * i.e. for an empty value the bill partner's VAT ID (buyer) or the organisation's main VAT ID (seller).
	 */
	private void assertSameVATaxIds(
			@NonNull final I_C_Invoice invoice,
			@NonNull final I_C_InvoiceLine line,
			@NonNull final InvoiceVATaxIds lineVATaxIds,
			@NonNull final InvoiceVATaxIds stored)
	{
		if (stored.isNone() && lineVATaxIds.isNone())
		{
			return;
		}
		if (!InvoiceVATaxIdService.isSameVATaxId(lineVATaxIds.getBuyerVATaxId(), stored.getBuyerVATaxId()))
		{
			final Supplier<String> billPartnerVATaxId = () -> bpartnerDAO.getById(BPartnerId.ofRepoId(invoice.getC_BPartner_ID())).getVATaxID();
			throw new AdempiereException(MSG_ExemptLineVATaxIdMismatch,
					line.getLine(),
					toDisplayString(lineVATaxIds.getBuyerVATaxId(), billPartnerVATaxId),
					toDisplayString(stored.getBuyerVATaxId(), billPartnerVATaxId))
					.markAsUserValidationError();
		}
		if (!InvoiceVATaxIdService.isSameVATaxId(lineVATaxIds.getSellerVATaxId(), stored.getSellerVATaxId()))
		{
			final Supplier<String> mainVATaxId = () -> getMainVATaxId(OrgId.ofRepoId(invoice.getAD_Org_ID()));
			throw new AdempiereException(MSG_SellerVATaxIdMismatch,
					line.getLine(),
					toDisplayString(lineVATaxIds.getSellerVATaxId(), mainVATaxId),
					toDisplayString(stored.getSellerVATaxId(), mainVATaxId))
					.markAsUserValidationError();
		}
	}

	@Nullable
	private String getMainVATaxId(@NonNull final OrgId orgId)
	{
		return bpartnerOrgBL.retrieveLinkedBPartnerId(orgId)
				.map(bpartnerDAO::getById)
				.map(I_C_BPartner::getVATaxID)
				.orElse(null);
	}

	/**
	 * All non-description lines must resolve to the same values, which are stored; an invoice without such lines stores none.
	 * A line whose tax is exempt on the buyer's VAT ID, but whose tax location (see {@link LineResolver#getTaxLocation(I_C_InvoiceLine)}) is not in that tax's destination country,
	 * blocks the completion if VAT IDs by delivery are used (see {@link LineResolver#resolveManualLine}): the VAT ID that justified the exemption is not known
	 * (e.g. a copy whose line kept the exempt tax of a delivery but lost its shipment line).
	 * <p>
	 * A credit memo of a sales invoice resolves only its lines that credit that invoice:
	 * <ul>
	 *     <li>if any line credits a line of it ({@code Ref_InvoiceLine_ID}), those lines: a line that still has the tax of the line it credits resolves to that invoice's stored values (also when empty),
	 *     a re-taxed one from its own tax location;</li>
	 *     <li>else (the credit memo references the invoice in its header only) the lines that have one of that invoice's line taxes: they resolve to its stored values.
	 *     If no line has one, its lines were re-taxed, and the credit memo is resolved like any manual invoice.</li>
	 * </ul>
	 * Its other lines (e.g. a fee) were added by hand, so they are checked like a line added by hand to a candidate invoice:
	 * only if their tax is exempt on a VAT ID, it must be the one the credit memo stores.
	 *
	 * @param creditedInvoice the sales invoice the given credit memo credits, if any
	 */
	private void stampManualInvoice(
			@NonNull final I_C_Invoice invoice,
			@NonNull final List<I_C_InvoiceLine> lines,
			@NonNull final LineResolver lineResolver,
			@Nullable final I_C_Invoice creditedInvoice)
	{
		final List<I_C_InvoiceLine> nonDescriptionLines = lines.stream()
				.filter(line -> !line.isDescription())
				.collect(ImmutableList.toImmutableList());

		final CreditingLines creditingLines = creditedInvoice != null ? getCreditingLines(nonDescriptionLines, creditedInvoice) : null;
		if (creditingLines == null || creditingLines.isEmpty())
		{
			final Set<InvoiceVATaxIds> distinctVATaxIds = nonDescriptionLines.stream()
					.map(lineResolver::resolveManualLine)
					.collect(Collectors.toCollection(LinkedHashSet::new));
			if (distinctVATaxIds.isEmpty())
			{
				setVATaxIds(invoice, InvoiceVATaxIds.NONE);
				return;
			}
			setUniqueVATaxIds(invoice, distinctVATaxIds);
			return;
		}

		final Set<InvoiceVATaxIds> distinctVATaxIds = new LinkedHashSet<>();
		for (final I_C_InvoiceLine line : nonDescriptionLines)
		{
			if (!creditingLines.isCrediting(line))
			{
				continue;
			}
			distinctVATaxIds.add(creditingLines.isTaxedLikeCreditedLine(line) ? getStoredVATaxIds(creditedInvoice) : lineResolver.resolveManualLine(line));
		}
		setUniqueVATaxIds(invoice, distinctVATaxIds);

		final InvoiceVATaxIds stored = getStoredVATaxIds(invoice);
		for (final I_C_InvoiceLine line : nonDescriptionLines)
		{
			if (!creditingLines.isCrediting(line) && isRequiresTaxCertificate(TaxId.ofRepoId(line.getC_Tax_ID())))
			{
				assertSameVATaxIds(invoice, line, lineResolver.resolve(line), stored);
			}
		}
	}

	/**
	 * @return the lines of a credit memo that credit the given invoice: the ones that reference one of its lines, or, if none does, the ones that have one of its line taxes
	 */
	@NonNull
	private CreditingLines getCreditingLines(@NonNull final List<I_C_InvoiceLine> lines, @NonNull final I_C_Invoice creditedInvoice)
	{
		final Map<InvoiceLineId, TaxId> creditedLineTaxIds = getLineTaxIds(creditedInvoice);
		final boolean isReferencingLines = lines.stream().anyMatch(line -> line.getRef_InvoiceLine_ID() > 0);
		if (isReferencingLines)
		{
			return new CreditingLines(
					line -> line.getRef_InvoiceLine_ID() > 0,
					line -> TaxId.ofRepoId(line.getC_Tax_ID()).equals(creditedLineTaxIds.get(InvoiceLineId.ofRepoId(line.getRef_InvoiceLine_ID()))),
					true);
		}

		final ImmutableSet<TaxId> creditedTaxIds = ImmutableSet.copyOf(creditedLineTaxIds.values());
		final Predicate<I_C_InvoiceLine> hasCreditedTax = line -> line.getC_Tax_ID() > 0 && creditedTaxIds.contains(TaxId.ofRepoId(line.getC_Tax_ID()));
		return new CreditingLines(hasCreditedTax, hasCreditedTax, lines.stream().anyMatch(hasCreditedTax));
	}

	@Value
	private static class CreditingLines
	{
		@NonNull Predicate<I_C_InvoiceLine> creditingPredicate;
		@NonNull Predicate<I_C_InvoiceLine> taxedLikeCreditedLinePredicate;
		boolean anyCrediting;

		boolean isEmpty() {return !anyCrediting;}

		boolean isCrediting(@NonNull final I_C_InvoiceLine line) {return creditingPredicate.test(line);}

		/**
		 * Only for a crediting line.
		 */
		boolean isTaxedLikeCreditedLine(@NonNull final I_C_InvoiceLine line) {return taxedLikeCreditedLinePredicate.test(line);}
	}

	@NonNull
	private Map<InvoiceLineId, TaxId> getLineTaxIds(@NonNull final I_C_Invoice invoice)
	{
		return invoiceDAO.retrieveLines(invoice)
				.stream()
				.filter(line -> line.getC_Tax_ID() > 0)
				.collect(ImmutableMap.toImmutableMap(line -> InvoiceLineId.ofRepoId(line.getC_InvoiceLine_ID()), line -> TaxId.ofRepoId(line.getC_Tax_ID())));
	}

	/**
	 * Stores the one value set of the given ones; fails if there are several, leaves the invoice alone if there are none.
	 */
	private static void setUniqueVATaxIds(@NonNull final I_C_Invoice invoice, @NonNull final Set<InvoiceVATaxIds> distinctVATaxIds)
	{
		if (distinctVATaxIds.size() > 1)
		{
			throw new AdempiereException(MSG_MixedVATaxIds, joinDistinct(distinctVATaxIds, InvoiceVATaxIds::getBuyerVATaxId), joinDistinct(distinctVATaxIds, InvoiceVATaxIds::getSellerVATaxId))
					.markAsUserValidationError();
		}
		if (distinctVATaxIds.size() == 1)
		{
			setVATaxIds(invoice, distinctVATaxIds.iterator().next());
		}
	}

	private static void setVATaxIds(@NonNull final I_C_Invoice invoice, @NonNull final InvoiceVATaxIds vatTaxIds)
	{
		invoice.setBuyerVATaxID(vatTaxIds.getBuyerVATaxId());
		invoice.setSellerVATaxID(vatTaxIds.getSellerVATaxId());
		invoice.setC_Fiscal_Representation_ID(RepoIdAwares.toRepoId(vatTaxIds.getFiscalRepresentationId()));
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

	/**
	 * @param shownIfEmpty the VAT ID the outputs show if the value is empty
	 */
	@NonNull
	private static String toDisplayString(@Nullable final String vatTaxId, @NonNull final Supplier<String> shownIfEmpty)
	{
		return toDisplayString(vatTaxId != null ? vatTaxId : StringUtils.trimBlankToNull(shownIfEmpty.get()));
	}

	@Value
	private static class ResolveKey
	{
		@NonNull TaxLocation taxLocation;
		@NonNull TaxId taxId;
	}

	/**
	 * Resolves the lines of one invoice. Everything it reads per line is loaded once per invoice, in one query per kind (shipment lines, order lines, candidates),
	 * or once per shipment, candidate, order line and resolution.
	 */
	private class LineResolver
	{
		@NonNull private final I_C_Invoice invoice;
		@NonNull private final List<I_C_InvoiceLine> lines;
		@Nullable private TaxLocation billToTaxLocation;
		@Nullable private Map<InOutLineId, I_M_InOutLine> shipmentLinesById;
		@Nullable private Map<OrderLineId, I_C_OrderLine> orderLinesById;
		@Nullable private Map<InvoiceCandidateId, I_C_Invoice_Candidate> candidatesById;
		private final Map<InOutId, TaxLocation> shipmentTaxLocations = new HashMap<>();
		private final Map<InvoiceCandidateId, TaxLocation> candidateTaxLocations = new HashMap<>();
		private final Map<OrderLineId, InvoiceLineReTaxInput> orderLineReTaxInputs = new HashMap<>();
		private final Map<ResolveKey, InvoiceVATaxIds> resolvedVATaxIds = new HashMap<>();

		private LineResolver(@NonNull final I_C_Invoice invoice, @NonNull final List<I_C_InvoiceLine> lines)
		{
			this.invoice = invoice;
			this.lines = lines;
		}

		/**
		 * @return the line's values, {@link InvoiceVATaxIds#NONE} if it does not use VAT IDs by delivery
		 */
		@NonNull
		private InvoiceVATaxIds resolve(@NonNull final I_C_InvoiceLine line)
		{
			return resolve(getTaxLocation(line), TaxId.ofRepoId(line.getC_Tax_ID()));
		}

		/**
		 * Like {@link #resolve(I_C_InvoiceLine)}, for a line of a manual invoice (incl. copies and credit memos): if VAT IDs by delivery are used
		 * ({@link InvoiceVATaxIdService#isVATaxIdByDeliveryPossible}) and the line's tax is exempt on the buyer's VAT ID, its tax location must be in the tax's destination country.
		 * Else that location's VAT ID did not justify the exemption, and the one that did is unknown: the completion fails instead of declaring another VAT ID.
		 * Without VAT IDs by delivery such a line resolves as before.
		 */
		@NonNull
		private InvoiceVATaxIds resolveManualLine(@NonNull final I_C_InvoiceLine line)
		{
			final TaxLocation taxLocation = getTaxLocation(line);
			final TaxId taxId = TaxId.ofRepoId(line.getC_Tax_ID());
			if (invoiceVATaxIdService.isVATaxIdByDeliveryPossible())
			{
				final CountryId taxCountryId = invoiceVATaxIdService.getExemptTaxDestinationCountryIfLocationNotThere(taxLocation, taxId).orElse(null);
				if (taxCountryId != null)
				{
					throw new AdempiereException(MSG_ExemptLineNoDeliveryToTaxCountry, line.getLine(), countryDAO.getCountryNameById(taxCountryId))
							.markAsUserValidationError();
				}
			}
			return resolve(taxLocation, taxId);
		}

		@NonNull
		private InvoiceVATaxIds resolve(@NonNull final TaxLocation taxLocation, @NonNull final TaxId taxId)
		{
			return resolvedVATaxIds.computeIfAbsent(
					new ResolveKey(taxLocation, taxId),
					key -> invoiceVATaxIdService.resolveIfByDelivery(key.getTaxLocation(), key.getTaxId(), BPartnerId.ofRepoId(invoice.getC_BPartner_ID())));
		}

		/**
		 * The location and date that taxed a candidate line without shipment line: its first candidate's tax location (the one it was resolved with at invoicing, at the candidate's tax date)
		 * if the line still has that candidate's tax; else the line was re-taxed (after a date or warehouse change, or on completion), with the inputs of {@link IInvoiceBL#getReTaxInput}.
		 */
		@NonNull
		private TaxLocation getCandidateLineTaxLocation(
				@NonNull final I_C_InvoiceLine line,
				@NonNull final TaxId lineTaxId,
				@NonNull final ImmutableMap<InvoiceLineId, ImmutableList<InvoiceCandidateId>> candidateIdsByLineId)
		{
			final I_C_Invoice_Candidate firstCandidate = getFirstActiveCandidate(InvoiceLineId.ofRepoId(line.getC_InvoiceLine_ID()), candidateIdsByLineId);
			if (firstCandidate != null && lineTaxId.equals(invoiceCandBL.getTaxEffective(firstCandidate).getTaxId()))
			{
				return candidateTaxLocations.computeIfAbsent(
						InvoiceCandidateId.ofRepoId(firstCandidate.getC_Invoice_Candidate_ID()),
						id -> invoiceCandidateHandlerBL.getTaxLocation(firstCandidate, null));
			}
			return toTaxLocation(getReTaxInput(line));
		}

		/**
		 * @return the active candidate with the lowest ID of the given line (as {@link IInvoiceCandDAO#retrieveIcForIl}); the candidates of all lines are loaded in one query
		 */
		@Nullable
		private I_C_Invoice_Candidate getFirstActiveCandidate(
				@NonNull final InvoiceLineId lineId,
				@NonNull final ImmutableMap<InvoiceLineId, ImmutableList<InvoiceCandidateId>> candidateIdsByLineId)
		{
			if (candidatesById == null)
			{
				candidatesById = Maps.uniqueIndex(
						invoiceCandDAO.getByIds(candidateIdsByLineId.values().stream().flatMap(List::stream).collect(ImmutableSet.toImmutableSet())),
						candidate -> InvoiceCandidateId.ofRepoId(candidate.getC_Invoice_Candidate_ID()));
			}
			final Map<InvoiceCandidateId, I_C_Invoice_Candidate> candidates = candidatesById;
			return candidateIdsByLineId.getOrDefault(lineId, ImmutableList.of())
					.stream()
					.sorted(Comparator.comparing(InvoiceCandidateId::getRepoId))
					.map(candidates::get)
					.filter(candidate -> candidate != null && candidate.isActive())
					.findFirst()
					.orElse(null);
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
				return getShipmentTaxLocation(shipmentLineId);
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

		@NonNull
		private TaxLocation getShipmentTaxLocation(@NonNull final InOutLineId shipmentLineId)
		{
			final InOutId shipmentId = InOutId.ofRepoId(getShipmentLine(shipmentLineId).getM_InOut_ID());
			return shipmentTaxLocations.computeIfAbsent(shipmentId, id -> {
				final I_M_InOut shipment = inOutDAO.getById(id);
				return ShipmentTaxLocation.of(shipment, OrgId.ofRepoId(shipment.getAD_Org_ID()));
			});
		}

		/**
		 * The shipment lines of all invoice lines are loaded in one query.
		 */
		@NonNull
		private I_M_InOutLine getShipmentLine(@NonNull final InOutLineId shipmentLineId)
		{
			if (shipmentLinesById == null)
			{
				final ImmutableSet<InOutLineId> shipmentLineIds = lines.stream()
						.map(line -> InOutLineId.ofRepoIdOrNull(line.getM_InOutLine_ID()))
						.filter(Objects::nonNull)
						.collect(ImmutableSet.toImmutableSet());
				shipmentLinesById = new HashMap<>(Maps.uniqueIndex(
						inOutDAO.getLinesByIds(shipmentLineIds, I_M_InOutLine.class),
						shipmentLine -> InOutLineId.ofRepoId(shipmentLine.getM_InOutLine_ID())));
			}
			return shipmentLinesById.computeIfAbsent(shipmentLineId, inOutDAO::getLineByIdInTrx); // an inactive shipment line is not loaded by the batch
		}

		/**
		 * The order lines of all invoice lines are loaded in one query.
		 */
		@NonNull
		private I_C_OrderLine getOrderLine(@NonNull final OrderLineId orderLineId)
		{
			if (orderLinesById == null)
			{
				final ImmutableSet<OrderLineId> orderLineIds = lines.stream()
						.map(line -> OrderLineId.ofRepoIdOrNull(line.getC_OrderLine_ID()))
						.filter(Objects::nonNull)
						.collect(ImmutableSet.toImmutableSet());
				orderLinesById = new HashMap<>(Maps.uniqueIndex(
						InterfaceWrapperHelper.loadByRepoIdAwares(orderLineIds, I_C_OrderLine.class),
						orderLine -> OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID())));
			}
			return orderLinesById.computeIfAbsent(orderLineId, orderDAO::getOrderLineById);
		}

		/**
		 * The inputs {@link IInvoiceBL#getReTaxInput} gives the line; for a line with order line but without shipment line they depend only on the invoice and the order line,
		 * so they are computed once per order line.
		 */
		@NonNull
		private InvoiceLineReTaxInput getReTaxInput(@NonNull final I_C_InvoiceLine line)
		{
			final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(line.getC_OrderLine_ID());
			if (orderLineId == null || line.getM_InOutLine_ID() > 0)
			{
				return invoiceBL.getReTaxInput(invoice, line, null);
			}
			return orderLineReTaxInputs.computeIfAbsent(orderLineId, id -> invoiceBL.getReTaxInput(invoice, line, null));
		}

		/**
		 * @return the order line's tax location with the inputs of the re-tax (which uses the order line's location for such a line, see {@link IInvoiceBL#getReTaxInput}),
		 * if the order line's location taxed the given line; {@code null} otherwise
		 */
		@Nullable
		private TaxLocation getOrderLineTaxLocationIfItTaxedLine(@NonNull final I_C_InvoiceLine line, @NonNull final OrderLineId orderLineId)
		{
			final TaxId lineTaxId = TaxId.ofRepoId(line.getC_Tax_ID());
			final InvoiceLineReTaxInput reTaxInput = getReTaxInput(line);

			final boolean isOrderLineTax = lineTaxId.equals(TaxId.ofRepoIdOrNull(getOrderLine(orderLineId).getC_Tax_ID()));
			if (isOrderLineTax || lineTaxId.equals(predictReTaxTax(line, reTaxInput)))
			{
				return toTaxLocation(reTaxInput);
			}
			return null;
		}
	}
}
