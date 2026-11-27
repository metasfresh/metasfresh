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
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.effective.BPartnerAddressEffectiveBL;
import de.metas.location.CountryId;
import de.metas.organization.FiscalRepresentationId;
import de.metas.organization.IFiscalRepresentationBL;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Fiscal_Representation;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Resolves the buyer and seller VAT IDs of an invoice from the {@link TaxLocation} of its lines.
 */
@Service
@RequiredArgsConstructor
public class InvoiceVATaxIdService
{
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	@NonNull private final IFiscalRepresentationBL fiscalRepresentationBL = Services.get(IFiscalRepresentationBL.class);
	@NonNull private final BPartnerAddressEffectiveBL bpartnerAddressEffectiveBL;

	@VisibleForTesting
	public static InvoiceVATaxIdService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(InvoiceVATaxIdService.class, () -> new InvoiceVATaxIdService(BPartnerAddressEffectiveBL.newInstanceForUnitTesting()));
	}

	/**
	 * @param lineTax the tax of the line; the seller VAT ID / fiscal representation are only set
	 *                if this tax is flagged {@code IsFiscalRepresentation}.
	 */
	@NonNull
	public InvoiceVATaxIds resolve(@NonNull final TaxLocation taxLocation, @NonNull final TaxId lineTax)
	{
		final String buyerVATaxId = VATIdentifier.toString(bpartnerAddressEffectiveBL.getDeliveryEffective(null, taxLocation.getBpartnerLocationId()).getVatTaxId());

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
