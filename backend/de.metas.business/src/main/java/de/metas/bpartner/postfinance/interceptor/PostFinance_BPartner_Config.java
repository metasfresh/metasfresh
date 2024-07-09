/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.bpartner.postfinance.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_PostFinance_BPartner_Config;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Interceptor(I_PostFinance_BPartner_Config.class)
@Component
public class PostFinance_BPartner_Config
{
	private static final AdMessageKey ERROR_ORG_BP_POST_FINANCE_CONFIG = AdMessageKey.of("PostFinance_BPartner_Config.ERROR_ORG_BP_POSTFINANCE_CONFIG");

	private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_PostFinance_BPartner_Config.COLUMNNAME_IsActive)
	public void validateBPartner(@NonNull final I_PostFinance_BPartner_Config config)
	{
		if (!config.isActive())
		{
			return;
		}

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(config.getC_BPartner_ID());
		final I_C_BPartner bPartnerRecord = bPartnerBL.getById(bPartnerId);

		final Optional<OrgId> orgBPIdOptional = InterfaceWrapperHelper.getRepoIdOptional(bPartnerRecord, I_C_BPartner.COLUMNNAME_AD_OrgBP_ID, OrgId::ofRepoId);
		if (orgBPIdOptional.isPresent())
		{
			throw new AdempiereException(ERROR_ORG_BP_POST_FINANCE_CONFIG);
		}
	}
}
