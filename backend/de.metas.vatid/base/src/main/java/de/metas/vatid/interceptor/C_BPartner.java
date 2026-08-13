/*
 * #%L
 * de.metas.vatid
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

package de.metas.vatid.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDValidationUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

@Interceptor(I_C_BPartner.class)
@Component
@RequiredArgsConstructor
public class C_BPartner
{
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final ISessionBL sessionBL = Services.get(ISessionBL.class);

	@NonNull private final VATaxIDCheckTrigger vataxIDCheckTrigger;

	/**
	 * Unchanged synchronous format check: still {@code BEFORE_NEW}/{@code BEFORE_CHANGE}, still throws
	 * hard on a malformed value, still gated by the same {@code C_BPartner.validateVATaxID} SysConfig. Not
	 * touched by the after-commit trigger below — a value this rejects never reaches a save at all, let
	 * alone the online check.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_BPartner.COLUMNNAME_VATaxID)
	public void validateVATaxID(@NonNull final I_C_BPartner bpartner)
	{
		if (sysConfigBL.getBooleanValue(VATaxIDValidationUtil.SYSCONFIG_validateVATaxID, true))
		{
			VATaxIDValidationUtil.validate(VATIdentifier.ofNullable(bpartner.getVATaxID()));
		}
	}

	/**
	 * Schedules the online VAT-ID check once the save commits. Deliberately {@code AFTER_NEW}/
	 * {@code AFTER_CHANGE}, not {@code BEFORE_*}: on {@code BEFORE_NEW} the partner has no
	 * {@code C_BPartner_ID} yet, and {@link de.metas.vatid.VATaxIDCheckRequest#getBpartnerId()} is
	 * {@code @NonNull}.
	 *
	 * <p>The acting {@code AD_Session_ID} is resolved <b>here</b>, not inside {@link VATaxIDCheckTrigger}:
	 * this {@code @ModelChange} method is the near-user, save-time boundary — the same role a callout or a
	 * REST controller plays elsewhere — so it is the one place allowed to read {@link Env#getCtx()}
	 * (service-injection rule: a shared {@code @Component} never reads ambient thread-local context
	 * itself). Resolved eagerly, on the thread doing the save, and passed down as a plain
	 * {@code Integer}.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = I_C_BPartner.COLUMNNAME_VATaxID)
	public void scheduleVATaxIDCheck(@NonNull final I_C_BPartner bpartner)
	{
		final MFSession currentSession = sessionBL.getCurrentSession(Env.getCtx());
		final Integer adSessionId = currentSession != null ? currentSession.getAD_Session_ID() : null;

		vataxIDCheckTrigger.scheduleCheckAfterCommit(
				BPartnerId.ofRepoId(bpartner.getC_BPartner_ID()),
				null,
				bpartner.getVATaxID(),
				adSessionId);
	}
}
