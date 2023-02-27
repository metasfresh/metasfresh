/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.bpartner.blockstatus.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.blockstatus.BPartnerBlockStatusService;
import de.metas.i18n.AdMessageKey;
import de.metas.interfaces.I_C_BPartner;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_BPartner.class)
@Component
public class C_BPartner
{
	private static final AdMessageKey MSG_CANNOT_ACTIVATE_BLOCKED_PARTNER = AdMessageKey.of("CannotActivateBlockedPartner");

	private final BPartnerBlockStatusService bPartnerBlockStatusService;

	public C_BPartner(@NonNull final BPartnerBlockStatusService bPartnerBlockStatusService)
	{
		this.bPartnerBlockStatusService = bPartnerBlockStatusService;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_BPartner.COLUMNNAME_IsActive)
	public void validateBPartnerBlockedStatus(@NonNull final I_C_BPartner bpartner)
	{
		if (!bpartner.isActive())
		{
			return;
		}

		if (bPartnerBlockStatusService.isBPartnerBlocked(BPartnerId.ofRepoId(bpartner.getC_BPartner_ID())))
		{
			throw new AdempiereException(MSG_CANNOT_ACTIVATE_BLOCKED_PARTNER)
					.markAsUserValidationError();
		}
	}
}
