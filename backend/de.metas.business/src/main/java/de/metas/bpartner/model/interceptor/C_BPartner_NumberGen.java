package de.metas.bpartner.model.interceptor;

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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.CreditorId;
import de.metas.bpartner.DebtorId;
import de.metas.bpartner.service.BPartnerNumberContext;
import de.metas.bpartner.service.BPartnerNumberGenerator;
import de.metas.interfaces.I_C_BPartner;
import de.metas.organization.OrgId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Wires BPartner number generation into the model-save lifecycle.
 * Generates debtor/creditor numbers for new partners and advances sequences past
 * explicitly-supplied values on first save or when the value column changes.
 * <p>
 * Numbers are assigned at creation only; a later customer/vendor flip does not back-fill an
 * auto-generated number (see the {@code if (isNew)} guards below).
 * <p>
 * This interceptor is registered automatically as a Spring {@code @Component}.
 * It complements the existing {@code de.metas.bpartner.model.interceptor.C_BPartner}
 * in the base module (which handles other concerns), focusing solely on number generation.
 */
@Interceptor(I_C_BPartner.class)
@Component
@RequiredArgsConstructor
public class C_BPartner_NumberGen
{
	@NonNull private final BPartnerNumberGenerator bpartnerNumberGenerator;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void generateOrReserveNumbers(@NonNull final I_C_BPartner bpartner)
	{
		final boolean isNew = InterfaceWrapperHelper.isNew(bpartner);

		final ClientId clientId = ClientId.ofRepoId(bpartner.getAD_Client_ID());
		final OrgId orgId = OrgId.ofRepoId(bpartner.getAD_Org_ID());
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
		final boolean isCustomer = bpartner.isCustomer();
		final boolean isVendor = bpartner.isVendor();
		final boolean isCompany = bpartner.isCompany();

		if (isCustomer)
		{
			final BPartnerNumberContext debtorCtx = BPartnerNumberContext.builder()
					.clientId(clientId)
					.orgId(orgId)
					.bPartnerId(bpartnerId)
					.isCustomer(isCustomer)
					.isVendor(isVendor)
					.isCompany(isCompany)
					.kind(BPartnerNumberContext.Kind.DEBTOR)
					.build();

			if (DebtorId.ofRepoIdOrNull(bpartner.getDebtorId()) == null)
			{
				// No explicit debtor number: generate one, but only for new records.
				// On BEFORE_CHANGE with an unset value, do nothing — generation happens at creation only.
				if (isNew)
				{
					final Optional<Integer> generated = bpartnerNumberGenerator.generateNext(debtorCtx);
					generated.ifPresent(bpartner::setDebtorId);
				}
			}
			else
			{
				bpartnerNumberGenerator.reserveExplicitIfChanged(
						bpartner, debtorCtx, isNew, I_C_BPartner.COLUMNNAME_DebtorId, bpartner.getDebtorId());
			}
		}

		if (isVendor)
		{
			final BPartnerNumberContext creditorCtx = BPartnerNumberContext.builder()
					.clientId(clientId)
					.orgId(orgId)
					.bPartnerId(bpartnerId)
					.isCustomer(isCustomer)
					.isVendor(isVendor)
					.isCompany(isCompany)
					.kind(BPartnerNumberContext.Kind.CREDITOR)
					.build();

			if (CreditorId.ofRepoIdOrNull(bpartner.getCreditorId()) == null)
			{
				// No explicit creditor number: generate one, but only for new records.
				if (isNew)
				{
					final Optional<Integer> generated = bpartnerNumberGenerator.generateNext(creditorCtx);
					generated.ifPresent(bpartner::setCreditorId);
				}
			}
			else
			{
				bpartnerNumberGenerator.reserveExplicitIfChanged(
						bpartner, creditorCtx, isNew, I_C_BPartner.COLUMNNAME_CreditorId, bpartner.getCreditorId());
			}
		}
	}
}
