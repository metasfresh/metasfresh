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
import de.metas.bpartner.BPartnerLocationId;
import de.metas.organization.OrgId;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDConfig;
import de.metas.vatid.VATaxIDConfigRepository;
import de.metas.vatid.VATaxIDValidationUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.session.ISessionBL;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

@Interceptor(I_C_BPartner_Location.class)
@Component
@RequiredArgsConstructor
public class C_BPartner_Location
{
	@NonNull private final ISessionBL sessionBL = Services.get(ISessionBL.class);

	@NonNull private final VATaxIDCheckTrigger vataxIDCheckTrigger;
	@NonNull private final VATaxIDConfigRepository configRepository;

	/**
	 * Unchanged synchronous format check: still {@code BEFORE_NEW}/{@code BEFORE_CHANGE}, still throws
	 * hard on a malformed value, still gated — now by {@link VATaxIDConfigRepository#getByOrgId(OrgId)},
	 * the same resolver {@code VATaxIDCheckService} uses, so this organisation's own
	 * {@code VATaxID_Config.IsFormatCheckEnabled} (or, absent a record, the
	 * {@code VATaxID_Config.IsFormatCheckEnabledByDefault} SysConfig) can never diverge from what the
	 * after-commit online check enforces.
	 *
	 * <p><b>{@link #scheduleVATaxIDCheck(I_C_BPartner_Location)} must not assume this ran.</b> The framework
	 * does order {@code BEFORE_*} before {@code AFTER_*} within one save, but that guarantees only that this
	 * <em>method</em> was invoked — not that it <em>validated</em> anything: the resolved config above can
	 * make it a no-op, and inter-class interceptor ordering is not guaranteed, so another interceptor's
	 * {@code BEFORE_*} handler could in principle set {@code VATaxID} after this one already looked at it (no
	 * production interceptor does today — only test step-defs write the column). So a malformed value can
	 * reach the after-commit path. That is safe, but only because
	 * {@code VATaxIDCheckService#check(de.metas.vatid.VATaxIDCheckRequest)} re-runs the format check itself
	 * and {@link VATaxIDCheckTrigger} logs rather than propagates the resulting throw: the outcome is a
	 * skipped check with a warning, never a failed save and never a check-log row claiming a malformed value
	 * was checked.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_BPartner_Location.COLUMNNAME_VATaxID)
	public void validateVATaxID(@NonNull final I_C_BPartner_Location bpLocation)
	{
		final VATaxIDConfig config = configRepository.getByOrgId(OrgId.ofRepoId(bpLocation.getAD_Org_ID()));
		if (config.isFormatCheckEnabled())
		{
			VATaxIDValidationUtil.validate(VATIdentifier.ofNullable(bpLocation.getVATaxID()));
		}
	}

	/**
	 * Schedules the online VAT-ID check once the save commits. Deliberately {@code AFTER_NEW}/
	 * {@code AFTER_CHANGE}, not {@code BEFORE_*}: on {@code BEFORE_NEW} the location has no
	 * {@code C_BPartner_Location_ID} yet, and
	 * {@link de.metas.vatid.VATaxIDCheckRequest#getBpartnerLocationId()} would be unresolvable.
	 *
	 * <p>The acting {@code AD_Session_ID} is resolved <b>here</b>, not inside {@link VATaxIDCheckTrigger} —
	 * see {@link C_BPartner#scheduleVATaxIDCheck} for why this {@code @ModelChange} method, and not the
	 * shared trigger, is the right place to read {@link Env#getCtx()}.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = I_C_BPartner_Location.COLUMNNAME_VATaxID)
	public void scheduleVATaxIDCheck(@NonNull final I_C_BPartner_Location bpLocation)
	{
		final Integer adSessionId = sessionBL.getCurrentSessionIdOrNull(Env.getCtx());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpLocation.getC_BPartner_ID());
		vataxIDCheckTrigger.scheduleCheckAfterCommit(
				bpartnerId,
				BPartnerLocationId.ofRepoId(bpartnerId, bpLocation.getC_BPartner_Location_ID()),
				bpLocation.getVATaxID(),
				adSessionId);
	}
}
