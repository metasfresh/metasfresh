package de.metas.vertical.creditscore.creditpass.repository;

/*
 * #%L
 *  de.metas.vertical.creditscore.creditpass.repository
 * %%
 * Copyright (C) 2018 metas GmbH
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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.cache.CCache;
import de.metas.util.Services;
import de.metas.vertical.creditscore.creditpass.CreditPassConstants;
import de.metas.vertical.creditscore.creditpass.model.*;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.user.UserId;
import org.compiere.model.I_C_BP_Group;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CreditPassConfigRepository
{

	private final CCache<BPartnerId, CreditPassConfig> configCache = CCache.<BPartnerId, CreditPassConfig>builder()
			.initialCapacity(1)
			.tableName(I_CS_Creditpass_Config.Table_Name)
			.additionalTableNameToResetFor(I_C_BP_Group.Table_Name)
			.additionalTableNameToResetFor(I_CS_Creditpass_Config_PaymentRule.Table_Name)
			.build();

	public CreditPassConfig getConfigByBPartnerId(BPartnerId businessPartnerId)
	{
		return configCache.getOrLoad(businessPartnerId, () -> getByBPartnerId(businessPartnerId));
	}

	private CreditPassConfig getByBPartnerId(BPartnerId businessPartnerId)
	{

		final IBPGroupDAO bpGroupRepo = Services.get(IBPGroupDAO.class);
		final I_C_BP_Group bpGroup = bpGroupRepo.getByBPartnerId(businessPartnerId);

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_CS_Creditpass_Config config = queryBL
				.createQueryBuilder(I_CS_Creditpass_Config.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_CS_Creditpass_Config.COLUMN_Created).create().list()
				.stream().filter(c -> queryBL
						.createQueryBuilder(I_CS_Creditpass_BP_Group.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_CS_Creditpass_BP_Group.COLUMN_CS_Creditpass_Config_ID, c.getCS_Creditpass_Config_ID())
						.create().list()
						.stream()
						.allMatch(group -> group.getC_BP_Group_ID() != bpGroup.getC_BP_Group_ID()))
				.findFirst().orElse(null);

		if (config == null)
		{
			return null;
		}

		final List<I_CS_Creditpass_Config_PaymentRule> configPaymentRules = queryBL
				.createQueryBuilder(I_CS_Creditpass_Config_PaymentRule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_CS_Creditpass_Config_PaymentRule.COLUMN_CS_Creditpass_Config_ID, config.getCS_Creditpass_Config_ID())
				.create().list();

		final List<CreditPassConfigPaymentRule> mappedConfigPaymentRules = configPaymentRules.stream().map(temp ->
				CreditPassConfigPaymentRule.builder()
						.paymentRule(temp.getPaymentRule())
						.purchaseType(temp.getPurchaseType().intValue())
						.build()).collect(Collectors.toList());

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		int transactionType = sysConfigBL.getIntValue(CreditPassConstants.SYSCONFIG_TRANSACTION_TYPE, CreditPassConstants.DEFAULT_TRANSACTION_ID);
		int processingCode = sysConfigBL.getIntValue(CreditPassConstants.SYSCONFIG_PROCESSING_CODE, CreditPassConstants.DEFAULT_PROCESSING_CODE);

		return CreditPassConfig.builder()
				.authId(config.getAuthId())
				.authPassword(config.getPassword())
				.processingCode(processingCode)
				.transactionType(transactionType)
				.restApiBaseUrl(config.getRestApiBaseURL())
				.requestReason(config.getRequestReason())
				.notificationUserId(UserId.ofRepoId(config.getManual_Check_User_ID()))
				.defaultResult(DefaultResult.valueOf(config.getDefaultCheckResult()))
				.retryDays(config.getRetryAfterDays().intValue())
				.creditPassConfigPaymentRuleList(mappedConfigPaymentRules)
				.build();

	}

}
