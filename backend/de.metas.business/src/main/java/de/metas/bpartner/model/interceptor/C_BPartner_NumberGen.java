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

import javax.annotation.Nullable;

/**
 * Wires BPartner number generation into the model-save lifecycle.
 * Generates debtor/creditor numbers and advances sequences past explicitly-supplied values.
 * <p>
 * A number is assigned when the partner first carries the role — at creation, or at the later
 * customer/vendor flip for a partner that was created without it. An already-set number is never
 * replaced, only reserved.
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

	/**
	 * Generate-or-reserve for an existing partner. Both concerns share one {@code BEFORE_CHANGE} method
	 * because they are not independent: {@code ifColumnsChanged} is re-evaluated per method at invocation
	 * time ({@code AnnotatedModelInterceptor}), and method order within an interceptor class is reflection
	 * order. Split in two, a generate-then-reserve run would see the {@code DebtorId} it had just written
	 * as a hand-typed value and reserve it a second time — order-dependently, and against the customer's
	 * override function in the override branch.
	 * <p>
	 * <b>Generate</b> — a partner can reach the database before it is a customer/vendor (the WebUI persists
	 * a new record as soon as its mandatory fields are filled, and the flag is ticked afterwards), so
	 * {@link #generateOnNew} has nothing to assign for those; the flag flip back-fills the number.
	 * <p>
	 * <b>Reserve</b> — a hand-typed number advances the sequence past itself, so a number generated later
	 * cannot collide with it.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = {
					I_C_BPartner.COLUMNNAME_IsCustomer,
					I_C_BPartner.COLUMNNAME_IsVendor,
					I_C_BPartner.COLUMNNAME_DebtorId,
					I_C_BPartner.COLUMNNAME_CreditorId })
	public void generateOrReserveOnChange(@NonNull final I_C_BPartner bpartner)
	{
		if (isDisabled(bpartner))
		{
			return;
		}

		final DebtorId debtorId = DebtorId.ofNullableNo(bpartner.getDebtorId());
		final CreditorId creditorId = CreditorId.ofNullableNo(bpartner.getCreditorId());

		final boolean roleAdded = InterfaceWrapperHelper.isValueChanged(
				bpartner, I_C_BPartner.COLUMNNAME_IsCustomer, I_C_BPartner.COLUMNNAME_IsVendor);
		if (roleAdded
				&& ((bpartner.isCustomer() && debtorId == null) || (bpartner.isVendor() && creditorId == null)))
		{
			// generateNumbers draws the missing number(s) and reserves the already-set one of a partner that
			// is both customer and vendor, in the same pass — so reserveExplicitNumbers must not run on top.
			bpartnerNumberGenerator.generateNumbers(bpartner).applyTo(bpartner);
			return;
		}

		reserveExplicitNumbers(bpartner, debtorId, creditorId);
	}

	/**
	 * Advances the sequence past a debtor/creditor number that this save changed, so a number generated
	 * later cannot collide with it. Only ever called for numbers that came in with the save — never for
	 * one {@link #generateOrReserveOnChange} just generated, which the sequence is already past.
	 */
	private void reserveExplicitNumbers(
			@NonNull final I_C_BPartner bpartner,
			@Nullable final DebtorId debtorId,
			@Nullable final CreditorId creditorId)
	{
		if (bpartner.isCustomer()
				&& debtorId != null
				&& InterfaceWrapperHelper.isValueChanged(bpartner, I_C_BPartner.COLUMNNAME_DebtorId))
		{
			bpartnerNumberGenerator.reserveExplicit(
					BPartnerNumberContext.ofBPartner(bpartner, BPartnerNumberContext.Kind.DEBTOR), debtorId.toInt());
		}

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
