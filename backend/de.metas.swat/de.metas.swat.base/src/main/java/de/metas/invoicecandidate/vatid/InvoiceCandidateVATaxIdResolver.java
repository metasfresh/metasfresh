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
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.invoice.vatid.InvoiceVATaxIdService;
import de.metas.invoice.vatid.InvoiceVATaxIds;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.organization.IFiscalRepresentationBL;
import de.metas.organization.OrgId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * The VAT IDs of the invoice line that an invoice candidate (and one of its shipment lines) becomes:
 * the candidate's tax location and the tax its invoice line will get, resolved by {@link InvoiceVATaxIdService}
 * only if the line uses VAT IDs by delivery (see {@link InvoiceVATaxIdService#resolveIfByDelivery}).
 * <p>
 * Invoicing runs of customers who cannot use VAT IDs by delivery pay for none of this: if the candidate's org has no active fiscal representation
 * and no partner location has a VAT ID of its own, the result is {@link InvoiceVATaxIds#NONE} without looking at the candidate's location or tax.
 * If only the fiscal representation is missing, the line's tax cannot give a seller VAT ID, so it is not looked up.
 * Both facts are looked up once per invoicing run ({@link Cache}).
 */
@Component
@RequiredArgsConstructor
public class InvoiceCandidateVATaxIdResolver
{
	@NonNull private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	@NonNull private final IFiscalRepresentationBL fiscalRepresentationBL = Services.get(IFiscalRepresentationBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final InvoiceVATaxIdService invoiceVATaxIdService;
	@NonNull private final InvoiceCandidateLineTaxResolver lineTaxResolver;

	@VisibleForTesting
	public static InvoiceCandidateVATaxIdResolver newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(
				InvoiceCandidateVATaxIdResolver.class,
				() -> new InvoiceCandidateVATaxIdResolver(InvoiceVATaxIdService.newInstanceForUnitTesting(), new InvoiceCandidateLineTaxResolver()));
	}

	/**
	 * @param shipmentLine the shipment line the invoice line is created for, if any
	 * @param cache        resolutions of the current invoicing run, see {@link #newCache()}
	 * @return the resolved VAT IDs ({@link InvoiceVATaxIds#NONE} if the line does not use VAT IDs by delivery); {@code null} for a purchase candidate, whose invoice does not store any
	 */
	@Nullable
	public InvoiceVATaxIds resolve(
			@NonNull final I_C_Invoice_Candidate ic,
			@Nullable final I_M_InOutLine shipmentLine,
			@NonNull final Cache cache)
	{
		if (!ic.isSOTrx())
		{
			return null;
		}

		if (!hasFiscalRepresentation(OrgId.ofRepoId(ic.getAD_Org_ID()), cache) && !isAnyLocationWithVATaxId(cache))
		{
			return InvoiceVATaxIds.NONE;
		}

		final TaxLocation taxLocation = invoiceCandidateHandlerBL.getTaxLocation(ic, shipmentLine);
		final BPartnerId billPartnerId = BPartnerId.ofRepoId(ic.getBill_BPartner_ID());
		if (!hasFiscalRepresentation(taxLocation.getOrgId(), cache))
		{
			return cache.resolved.computeIfAbsent(
					new ResolveKey(taxLocation, null, billPartnerId),
					key -> invoiceVATaxIdService.resolveBuyerIfByDelivery(key.getTaxLocation(), key.getBillPartnerId()));
		}

		final TaxId lineTax = lineTaxResolver.getLineTax(ic, shipmentLine);
		return cache.resolved.computeIfAbsent(
				new ResolveKey(taxLocation, lineTax, billPartnerId),
				key -> invoiceVATaxIdService.resolveIfByDelivery(key.getTaxLocation(), lineTax, key.getBillPartnerId()));
	}

	private boolean hasFiscalRepresentation(@NonNull final OrgId orgId, @NonNull final Cache cache)
	{
		return cache.orgsWithFiscalRepresentation.computeIfAbsent(orgId, fiscalRepresentationBL::hasAnyActiveFiscalRepresentation);
	}

	private boolean isAnyLocationWithVATaxId(@NonNull final Cache cache)
	{
		if (cache.anyLocationWithVATaxId == null)
		{
			cache.anyLocationWithVATaxId = bpartnerDAO.isAnyLocationWithVATaxId();
		}
		return cache.anyLocationWithVATaxId;
	}

	/**
	 * @return a cache for one invoicing run: candidates with the same tax location, line tax and bill partner are resolved once
	 */
	@NonNull
	public Cache newCache()
	{
		return new Cache();
	}

	/**
	 * The resolutions of one invoicing run. Not shared between runs, so master data changed meanwhile is seen by the next run.
	 */
	public static final class Cache
	{
		private final Map<ResolveKey, InvoiceVATaxIds> resolved = new HashMap<>();
		private final Map<OrgId, Boolean> orgsWithFiscalRepresentation = new HashMap<>();
		@Nullable private Boolean anyLocationWithVATaxId;

		private Cache() {}
	}

	/**
	 * {@code taxId} is {@code null} for a buyer-only resolution (org without fiscal representation)
	 */
	@Value
	private static class ResolveKey
	{
		@NonNull TaxLocation taxLocation;
		@Nullable TaxId taxId;
		@NonNull BPartnerId billPartnerId;
	}
}
