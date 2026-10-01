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
import org.adempiere.ad.session.AdSessionId;
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
	 * Unchanged synchronous format check, gated by {@link VATaxIDConfigRepository#getByOrgId(OrgId)} — the
	 * same resolver {@code VATaxIDCheckService} uses, so the save-time gate and the after-commit online check
	 * cannot diverge.
	 *
	 * <p><b>{@link #scheduleVATaxIDCheck(I_C_BPartner_Location)} must not assume this validated anything</b> —
	 * see {@link C_BPartner#validateVATaxID} for why a malformed value can still reach the after-commit path,
	 * and why that is safe.
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
		final AdSessionId adSessionId = sessionBL.getCurrentOrCreateSessionId(Env.getCtx());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpLocation.getC_BPartner_ID());
		vataxIDCheckTrigger.scheduleCheckAfterCommit(
				OrgId.ofRepoId(bpLocation.getAD_Org_ID()),
				bpartnerId,
				BPartnerLocationId.ofRepoId(bpartnerId, bpLocation.getC_BPartner_Location_ID()),
				bpLocation.getVATaxID(),
				adSessionId);
	}
}
