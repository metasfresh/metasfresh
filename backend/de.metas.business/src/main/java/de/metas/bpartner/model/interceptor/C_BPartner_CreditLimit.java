package de.metas.bpartner.model.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater.BPartnerStatisticsUpdateRequest;
import de.metas.bpartner.service.impl.BPartnerStatsService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.compiere.model.I_C_CreditLimit_Type;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Interceptor(I_C_BPartner_CreditLimit.class)
@Component
public class C_BPartner_CreditLimit
{
	private final BPartnerStatsService bpartnerStatsService = SpringContextHolder.instance.getBean(BPartnerStatsService.class);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void updateBPartnerStatsRecord(@NonNull final I_C_BPartner_CreditLimit bpCreditLimit)
	{
		Services.get(IBPartnerStatisticsUpdater.class)
				.updateBPartnerStatistics(BPartnerStatisticsUpdateRequest.builder()
												  .bpartnerId(bpCreditLimit.getC_BPartner_ID())
												  .build());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE })
	public void setApproved(@NonNull final I_C_BPartner_CreditLimit bpCreditLimit)
	{
		final I_C_CreditLimit_Type type = bpCreditLimit.getC_CreditLimit_Type();
		if (type != null)
		{
			final boolean isAutoApproval = type.isAutoApproval();
			if (isAutoApproval)
			{
				bpCreditLimit.setProcessed(true);
			}
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = I_C_BPartner_CreditLimit.COLUMNNAME_ApprovedBy_ID)
	public void enableCreditLimitCheck(@NonNull final I_C_BPartner_CreditLimit bpCreditLimit)
	{
		if (bpCreditLimit.getApprovedBy_ID() <= 0 || bpCreditLimit.getC_BPartner_ID() <= 0)
		{
			return;
		}

		bpartnerStatsService.enableCreditLimitCheck(BPartnerId.ofRepoId(bpCreditLimit.getC_BPartner_ID()));
	}


	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_C_BPartner_CreditLimit.COLUMNNAME_IsActive)
	public void disapproveOnDeactivation(@NonNull final I_C_BPartner_CreditLimit bpCreditLimit)
	{
		if(!bpCreditLimit.isActive())
		{
			bpCreditLimit.setApprovedBy_ID(-1);
			bpCreditLimit.setProcessed(false);
		}
	}
}
