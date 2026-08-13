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
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDValidationUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_BPartner_Location.class)
@Component
@RequiredArgsConstructor
public class C_BPartner_Location
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@NonNull private final VATaxIDCheckTrigger vataxIDCheckTrigger;

	/**
	 * Unchanged synchronous format check: still {@code BEFORE_NEW}/{@code BEFORE_CHANGE}, still throws
	 * hard on a malformed value, still gated by the same {@code C_BPartner.validateVATaxID} SysConfig. Not
	 * touched by the after-commit trigger below — a value this rejects never reaches a save at all, let
	 * alone the online check.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_BPartner_Location.COLUMNNAME_VATaxID)
	public void validateVATaxID(@NonNull final I_C_BPartner_Location bpLocation)
	{
		if (sysConfigBL.getBooleanValue(VATaxIDValidationUtil.SYSCONFIG_validateVATaxID, true))
		{
			VATaxIDValidationUtil.validate(VATIdentifier.ofNullable(bpLocation.getVATaxID()));
		}
	}

	/**
	 * Schedules the online VAT-ID check once the save commits. Deliberately {@code AFTER_NEW}/
	 * {@code AFTER_CHANGE}, not {@code BEFORE_*}: on {@code BEFORE_NEW} the location has no
	 * {@code C_BPartner_Location_ID} yet, and
	 * {@link de.metas.vatid.VATaxIDCheckRequest#getBpartnerLocationId()} would be unresolvable.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = I_C_BPartner_Location.COLUMNNAME_VATaxID)
	public void scheduleVATaxIDCheck(@NonNull final I_C_BPartner_Location bpLocation)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpLocation.getC_BPartner_ID());
		vataxIDCheckTrigger.scheduleCheckAfterCommit(
				bpartnerId,
				BPartnerLocationId.ofRepoId(bpartnerId, bpLocation.getC_BPartner_Location_ID()),
				bpLocation.getVATaxID());
	}
}
