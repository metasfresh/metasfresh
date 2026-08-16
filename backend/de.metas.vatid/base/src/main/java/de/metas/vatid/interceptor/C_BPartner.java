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
import org.adempiere.ad.session.AdSessionId;
import org.adempiere.ad.session.ISessionBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

@Interceptor(I_C_BPartner.class)
@Component
@RequiredArgsConstructor
public class C_BPartner
{
	@NonNull private final ISessionBL sessionBL = Services.get(ISessionBL.class);

	@NonNull private final VATaxIDCheckTrigger vataxIDCheckTrigger;
	@NonNull private final VATaxIDConfigRepository configRepository;

	/**
	 * Unchanged synchronous format check, gated by {@link VATaxIDConfigRepository#getByOrgId(OrgId)} — the
	 * same resolver {@code VATaxIDCheckService} uses, so the save-time gate and the after-commit online check
	 * cannot diverge.
	 *
	 * <p><b>{@link #scheduleVATaxIDCheck(I_C_BPartner)} must not assume this validated anything.</b>
	 * {@code BEFORE_*} does run before {@code AFTER_*}, but the config can make this a no-op, and inter-class
	 * interceptor ordering is not guaranteed. A malformed value can therefore reach the after-commit path;
	 * that is safe only because {@code VATaxIDCheckService#check} re-runs the format check and
	 * {@link VATaxIDCheckTrigger} logs rather than propagates the throw — a skipped check with a warning,
	 * never a failed save or a log row claiming a malformed value was checked.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_BPartner.COLUMNNAME_VATaxID)
	public void validateVATaxID(@NonNull final I_C_BPartner bpartner)
	{
		final VATaxIDConfig config = configRepository.getByOrgId(OrgId.ofRepoId(bpartner.getAD_Org_ID()));
		if (config.isFormatCheckEnabled())
		{
			VATaxIDValidationUtil.validate(VATIdentifier.ofNullable(bpartner.getVATaxID()));
		}
	}

	/**
	 * Schedules the online check once the save commits. Deliberately {@code AFTER_*}: on {@code BEFORE_NEW}
	 * the partner has no {@code C_BPartner_ID} yet, and
	 * {@link de.metas.vatid.VATaxIDCheckRequest#getBpartnerId()} is {@code @NonNull}.
	 *
	 * <p>The acting session is resolved here rather than inside {@link VATaxIDCheckTrigger}: that shared
	 * {@code @Component} must not read ambient thread-local context (service-injection rule on
	 * {@code Env.get*}), whereas this method is the actual call site on the saving thread. A save with no
	 * logged-in session (batch/import) gets one created, so the evidence row always names an acting session.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = I_C_BPartner.COLUMNNAME_VATaxID)
	public void scheduleVATaxIDCheck(@NonNull final I_C_BPartner bpartner)
	{
		final AdSessionId adSessionId = sessionBL.getCurrentOrCreateSessionId(Env.getCtx());

		vataxIDCheckTrigger.scheduleCheckAfterCommit(
				OrgId.ofRepoId(bpartner.getAD_Org_ID()),
				BPartnerId.ofRepoId(bpartner.getC_BPartner_ID()),
				null,
				bpartner.getVATaxID(),
				adSessionId);
	}
}
