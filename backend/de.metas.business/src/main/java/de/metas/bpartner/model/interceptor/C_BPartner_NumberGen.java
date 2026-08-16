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

import de.metas.bpartner.CreditorId;
import de.metas.bpartner.DebtorId;
import de.metas.bpartner.service.BPartnerNumberContext;
import de.metas.bpartner.service.BPartnerNumberGenerator;
import de.metas.interfaces.I_C_BPartner;
import de.metas.organization.ClientAndOrgId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

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

	/**
	 * On creation: generate the debtor/creditor number(s) and set them on the record so they are written
	 * by the INSERT. Runs at {@code TYPE_BEFORE_NEW} — the only timing at which a value set on the record
	 * is persisted without a second save. (A re-save at {@code TYPE_AFTER_NEW} is illegal: the PO still
	 * has its {@code createNew} flag set until {@code saveFinish}, so {@code save()} re-enters as a new
	 * record — {@code "Object is already involved in a model change event … AFTER_NEW, BEFORE_NEW"}.)
	 * <p>
	 * At this timing {@code C_BPartner_ID} is not yet assigned (the native sequence sets it during the INSERT),
	 * which is fine: number resolution is by org, kind and company-flag, not by the partner id.
	 * <p>
	 * When an explicit number was supplied at creation, the sequence is advanced past it instead (inside
	 * {@link BPartnerNumberGenerator#generateNumbers}).
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void generateOnNew(@NonNull final I_C_BPartner bpartner)
	{
		if (isDisabled(bpartner))
		{
			return;
		}

		// One pass generates both roles (a partner can be customer AND vendor); explicitly-supplied
		// numbers are reserved (not re-generated) inside generateNumbers. BEFORE_NEW ⇒ the values
		// applyTo() sets are written by the INSERT (no re-save).
		bpartnerNumberGenerator.generateNumbers(bpartner).applyTo(bpartner);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = { I_C_BPartner.COLUMNNAME_IsCustomer, I_C_BPartner.COLUMNNAME_IsVendor })
	public void generateOnChange(@NonNull final I_C_BPartner bpartner)
	{
		if (isDisabled(bpartner))
		{
			return;
		}
		final DebtorId debtorId = DebtorId.ofNullableNo(bpartner.getDebtorId());
		final CreditorId creditorId = CreditorId.ofNullableNo(bpartner.getCreditorId());
		if ((bpartner.isCustomer() && debtorId == null) || (bpartner.isVendor() && creditorId == null))
		{
			bpartnerNumberGenerator.generateNumbers(bpartner).applyTo(bpartner);
		}
	}

	/**
	 * On change of an explicitly-set debtor/creditor number on an existing partner: advance the
	 * sequence past it so a later generated number cannot collide. Generation happens at creation
	 * only (see {@link #generateOnNew}).
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = { I_C_BPartner.COLUMNNAME_DebtorId, I_C_BPartner.COLUMNNAME_CreditorId })
	public void reserveOnChange(@NonNull final I_C_BPartner bpartner)
	{
		if (isDisabled(bpartner))
		{
			return;
		}

		final DebtorId debtorId = DebtorId.ofNullableNo(bpartner.getDebtorId());
		if (bpartner.isCustomer()
				&& debtorId != null
				&& InterfaceWrapperHelper.isValueChanged(bpartner, I_C_BPartner.COLUMNNAME_DebtorId))
		{
			bpartnerNumberGenerator.reserveExplicit(
					BPartnerNumberContext.ofBPartner(bpartner, BPartnerNumberContext.Kind.DEBTOR), debtorId.toInt());
		}

		final CreditorId creditorId = CreditorId.ofNullableNo(bpartner.getCreditorId());
		if (bpartner.isVendor()
				&& creditorId != null
				&& InterfaceWrapperHelper.isValueChanged(bpartner, I_C_BPartner.COLUMNNAME_CreditorId))
		{
			bpartnerNumberGenerator.reserveExplicit(
					BPartnerNumberContext.ofBPartner(bpartner, BPartnerNumberContext.Kind.CREDITOR), creditorId.toInt());
		}
	}

	/**
	 * Master on/off switch (default off) — checked first so the interceptor is a no-op on instances not using the feature.
	 */
	private boolean isDisabled(@NonNull final I_C_BPartner bpartner)
	{
		return !bpartnerNumberGenerator.isEnabled(
				ClientAndOrgId.ofClientAndOrg(bpartner.getAD_Client_ID(), bpartner.getAD_Org_ID()));
	}
}
