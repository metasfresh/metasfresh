/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.vatid;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.location.CountryId;
import de.metas.organization.FiscalRepresentationId;
import de.metas.organization.IFiscalRepresentationBL;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Fiscal_Representation;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * Resolves the buyer and seller VAT IDs of an invoice from the {@link TaxLocation} of its lines.
 */
@Service
public class InvoiceVATaxIdService
{
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	@NonNull private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	@NonNull private final IFiscalRepresentationBL fiscalRepresentationBL = Services.get(IFiscalRepresentationBL.class);

	@VisibleForTesting
	public static InvoiceVATaxIdService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(InvoiceVATaxIdService.class, InvoiceVATaxIdService::new);
	}

	/**
	 * Like {@link #resolve(TaxLocation, TaxId)}, but only if the line uses VAT IDs by delivery: a fiscal representation applies,
	 * or its location has a VAT ID of its own that differs from the bill partner's VAT ID (the value the invoice's outputs show without stored values).
	 * Otherwise {@link InvoiceVATaxIds#NONE}: the invoice is grouped and stores no values, so its outputs show the bill partner's VAT ID, as without per-address VAT IDs.
	 */
	@NonNull
	public InvoiceVATaxIds resolveIfByDelivery(@NonNull final TaxLocation taxLocation, @NonNull final TaxId lineTax, @NonNull final BPartnerId billPartnerId)
	{
		final InvoiceVATaxIds vatTaxIds = resolve(taxLocation, lineTax);
		if (vatTaxIds.getFiscalRepresentationId() != null)
		{
			return vatTaxIds;
		}
		if (hasOwnVATaxId(taxLocation.getBpartnerLocationId())
				&& !Objects.equals(vatTaxIds.getBuyerVATaxId(), getHeaderVATaxId(billPartnerId)))
		{
			return vatTaxIds;
		}
		return InvoiceVATaxIds.NONE;
	}

	@Nullable
	private String getHeaderVATaxId(@NonNull final BPartnerId bpartnerId)
	{
		return VATIdentifier.toString(VATIdentifier.ofNullable(bpartnerDAO.getById(bpartnerId).getVATaxID()));
	}

	/**
	 * @return {@code true} if the given location has a VAT ID of its own (not only its partner's)
	 */
	public boolean hasOwnVATaxId(@NonNull final BPartnerLocationId locationId)
	{
		return Check.isNotBlank(bpartnerDAO.getBPartnerLocationByIdEvenInactive(locationId).getVATaxID());
	}

	/**
	 * @param lineTax the tax of the line; the seller VAT ID / fiscal representation are only set
	 *                if this tax is flagged {@code IsFiscalRepresentation}.
	 */
	@NonNull
	public InvoiceVATaxIds resolve(@NonNull final TaxLocation taxLocation, @NonNull final TaxId lineTax)
	{
		final String buyerVATaxId = VATIdentifier.toString(bpartnerBL.getVATTaxId(taxLocation.getBpartnerLocationId()).orElse(null));

		if (!Boolean.TRUE.equals(taxDAO.getTaxById(lineTax).getIsFiscalRepresentation()))
		{
			return new InvoiceVATaxIds(buyerVATaxId, null, null);
		}

		final CountryId destCountryId = bpartnerDAO.getCountryId(taxLocation.getBpartnerLocationId());
		final Optional<I_C_Fiscal_Representation> registration = fiscalRepresentationBL.getFiscalRepresentation(destCountryId, taxLocation.getOrgId(), TimeUtil.asTimestampNotNull(taxLocation.getTaxDate()));
		if (!registration.isPresent())
		{
			return new InvoiceVATaxIds(buyerVATaxId, null, null);
		}

		return new InvoiceVATaxIds(
				buyerVATaxId,
				getSellerVATaxId(registration.get()),
				FiscalRepresentationId.ofRepoId(registration.get().getC_Fiscal_Representation_ID()));
	}

	/**
	 * The registration's own VAT ID, else the representative partner's header VAT ID, else {@code null}.
	 */
	@Nullable
	private String getSellerVATaxId(@NonNull final I_C_Fiscal_Representation registration)
	{
		final VATIdentifier ownVATaxId = VATIdentifier.ofNullable(registration.getVATaxID());
		if (ownVATaxId != null)
		{
			return ownVATaxId.getAsString();
		}

		final BPartnerId representativeId = BPartnerId.ofRepoIdOrNull(registration.getC_BPartner_Representative_ID());
		if (representativeId == null)
		{
			return null;
		}
		return VATIdentifier.toString(VATIdentifier.ofNullable(bpartnerDAO.getById(representativeId).getVATaxID()));
	}
}
