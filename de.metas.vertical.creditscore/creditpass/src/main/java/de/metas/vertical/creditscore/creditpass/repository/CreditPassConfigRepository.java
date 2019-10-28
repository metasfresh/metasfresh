package de.metas.vertical.creditscore.creditpass.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

/*
 * #%L
 * de.metas.vertical.creditscore.creditpass.repository
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

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.cache.CCache;
import de.metas.money.CurrencyId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.vertical.creditscore.base.spi.model.ResultCode;
import de.metas.vertical.creditscore.creditpass.CreditPassConstants;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfig;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigId;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigPRFallback;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigPaymentRule;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigPaymentRuleId;
import de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_BP_Group;
import de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_CP_Fallback;
import de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config;
import de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config_PaymentRule;

@Repository
public class CreditPassConfigRepository
{

	private final CCache<BPGroupId, CreditPassConfig> configCache = CCache.<BPGroupId, CreditPassConfig> builder()
			.initialCapacity(1)
			.tableName(I_CS_Creditpass_Config.Table_Name)
			.additionalTableNameToResetFor(I_CS_Creditpass_Config_PaymentRule.Table_Name)
			.additionalTableNameToResetFor(I_CS_Creditpass_CP_Fallback.Table_Name)
			.additionalTableNameToResetFor(I_CS_Creditpass_BP_Group.Table_Name)
			.build();

	public CreditPassConfig getConfigByBPartnerId(final BPartnerId businessPartnerId)
	{
		final IBPGroupDAO bpGroupRepo = Services.get(IBPGroupDAO.class);
		final BPGroupId bpGroupId = bpGroupRepo.getBPGroupByBPartnerId(businessPartnerId);
		return configCache.getOrLoad(bpGroupId, () -> getByBPGroupId(bpGroupId));
	}

	private CreditPassConfig getByBPGroupId(final BPGroupId bpGroupId)
	{

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_CS_Creditpass_BP_Group> queryGroups = queryBL
				.createQueryBuilder(I_CS_Creditpass_BP_Group.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_CS_Creditpass_BP_Group.COLUMNNAME_C_BP_Group_ID, bpGroupId.getRepoId())
				.create();

		final I_CS_Creditpass_Config configRecord = queryBL
				.createQueryBuilder(I_CS_Creditpass_Config.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_CS_Creditpass_Config.COLUMN_SeqNo)
				.addNotInSubQueryFilter(I_CS_Creditpass_Config.COLUMN_CS_Creditpass_Config_ID, I_CS_Creditpass_BP_Group.COLUMN_CS_Creditpass_Config_ID, queryGroups)
				.create().first();

		if (configRecord == null)
		{
			return null;
		}

		final List<CreditPassConfigPaymentRule> configPaymentRules = queryBL
				.createQueryBuilder(I_CS_Creditpass_Config_PaymentRule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_CS_Creditpass_Config_PaymentRule.COLUMN_CS_Creditpass_Config_ID, configRecord.getCS_Creditpass_Config_ID())
				.create()
				.list()
				.stream()
				.map(configPaymentRule -> {
					final boolean hasCurrency = configPaymentRule.getC_Currency_ID() > 0;
					final CreditPassConfigPaymentRule creditPassConfigPaymentRule = CreditPassConfigPaymentRule.builder()
							.paymentRule(configPaymentRule.getPaymentRule())
							.requestPrice(configPaymentRule.getRequestPrice())
							.purchaseType(configPaymentRule.getPurchaseType().intValue())
							.paymentRuleId(CreditPassConfigPaymentRuleId.ofRepoId(configPaymentRule.getCS_Creditpass_Config_PaymentRule_ID()))
							.creditPassConfigPRFallbacks(new ArrayList<>())
							.build();
					if (hasCurrency)
					{
						creditPassConfigPaymentRule.setRequestPriceCurrency(CurrencyId.ofRepoId(configPaymentRule.getC_Currency_ID()));
					}
					return creditPassConfigPaymentRule;
				})
				.collect(Collectors.toList());

		for (final CreditPassConfigPaymentRule paymentRule : configPaymentRules)
		{
			final List<CreditPassConfigPRFallback> configPaymentRuleFallbacks = queryBL
					.createQueryBuilder(I_CS_Creditpass_CP_Fallback.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_CS_Creditpass_CP_Fallback.COLUMNNAME_CS_Creditpass_Config_PaymentRule_ID, paymentRule.getPaymentRuleId().getRepoId())
					.create()
					.list()
					.stream()
					.map(configPaymentRule -> CreditPassConfigPRFallback.builder()
							.fallbackPaymentRule(configPaymentRule.getFallbackPaymentRule())
							.build())
					.collect(Collectors.toList());

			paymentRule.getCreditPassConfigPRFallbacks().addAll(configPaymentRuleFallbacks);
		}

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final int clientId = Env.getAD_Client_ID();
		final int orgId = Env.getAD_Org_ID(Env.getCtx());
		final int transactionType = sysConfigBL.getIntValue(CreditPassConstants.SYSCONFIG_TRANSACTION_TYPE, CreditPassConstants.DEFAULT_TRANSACTION_ID, clientId, orgId);
		final int processingCode = sysConfigBL.getIntValue(CreditPassConstants.SYSCONFIG_PROCESSING_CODE, CreditPassConstants.DEFAULT_PROCESSING_CODE, clientId, orgId);

		return CreditPassConfig.builder()
				.authId(configRecord.getAuthId())
				.authPassword(configRecord.getPassword())
				.processingCode(processingCode)
				.transactionType(transactionType)
				.restApiBaseUrl(configRecord.getRestApiBaseURL())
				.requestReason(configRecord.getRequestReason())
				.notificationUserId(UserId.ofRepoId(configRecord.getManual_Check_User_ID()))
				.resultCode(ResultCode.fromName(configRecord.getDefaultCheckResult()))
				.retryDays(configRecord.getRetryAfterDays().intValue())
				.creditPassConfigId(CreditPassConfigId.ofRepoId(configRecord.getCS_Creditpass_Config_ID()))
				.creditPassConfigPaymentRuleList(configPaymentRules)
				.build();

	}

}
