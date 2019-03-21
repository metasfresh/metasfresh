package de.metas.vertical.creditscore.creditpass.process;

/*
 * #%L
 *  de.metas.vertical.creditscore.creditpass.process
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
import de.metas.i18n.impl.ADMessageDAO;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.process.*;
import de.metas.util.Services;
import de.metas.vertical.creditscore.base.spi.model.CreditScore;
import de.metas.vertical.creditscore.base.spi.service.TransactionResultService;
import de.metas.vertical.creditscore.creditpass.CreditPassClient;
import de.metas.vertical.creditscore.creditpass.CreditPassClientFactory;
import de.metas.vertical.creditscore.creditpass.CreditPassConstants;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfig;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigPaymentRule;
import de.metas.vertical.creditscore.creditpass.model.CreditPassTransactionData;
import de.metas.vertical.creditscore.creditpass.repository.CreditPassConfigRepository;
import de.metas.vertical.creditscore.creditpass.service.CreditPassTransactionDataFactory;
import org.apache.commons.lang3.StringUtils;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;

public class CS_Creditpass_TransactionFrom_C_BPartner extends JavaProcess implements IProcessPrecondition
{
	final CreditPassTransactionDataFactory creditPassTransactionDataService = Adempiere.getBean(CreditPassTransactionDataFactory.class);

	final TransactionResultService transactionResultService = Adempiere.getBean(TransactionResultService.class);

	final CreditPassClientFactory clientFactory = Adempiere.getBean(CreditPassClientFactory.class);

	final CreditPassConfigRepository creditPassConfigRepository = Adempiere.getBean(CreditPassConfigRepository.class);

	@Param(mandatory = true, parameterName = CreditPassConstants.PROCESS_PAYMENT_RULE_PARAM)
	private String paymentRule;

	@Override protected String doIt() throws Exception
	{
		BPartnerId bPartnerId = BPartnerId.ofRepoId(getProcessInfo().getRecord_ID());
		// TODO check if creation can be avoided sometimes
		CreditPassClient creditScoreClient = (CreditPassClient)clientFactory.newClientForBusinessPartner(bPartnerId);
		CreditPassTransactionData creditPassTransactionData = creditPassTransactionDataService.collectTransactionData(bPartnerId);
		if (StringUtils.isEmpty(paymentRule))
		{
			for (CreditPassConfigPaymentRule paymentRule : creditScoreClient.getCreditPassConfig().getCreditPassConfigPaymentRuleList())
			{
				CreditScore creditScore = creditScoreClient.getCreditScore(creditPassTransactionData, paymentRule.getPaymentRule());
				transactionResultService.createAndSaveResult(convertResult(creditScoreClient, creditScore), bPartnerId);
			}
		}
		else
		{
			CreditScore creditScore = creditScoreClient.getCreditScore(creditPassTransactionData, paymentRule);
			transactionResultService.createAndSaveResult(convertResult(creditScoreClient, creditScore), bPartnerId);
		}
		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{

		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		I_C_BPartner partner = context.getSelectedModel(I_C_BPartner.class);
		if (!partner.isCustomer())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Business partner is not a customer");
		}

		CreditPassConfig config = creditPassConfigRepository.getByBPartnerId(BPartnerId.ofRepoId(partner.getC_BPartner_ID()));

		if (config == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Business partner has no associated creditPass config");
		}

		return ProcessPreconditionsResolution.accept();
	}

	private CreditScore convertResult(CreditPassClient client, CreditScore creditScore)
	{
		if (creditScore.getResultCode() == CreditPassConstants.MANUAL_RESPONSE_CODE)
		{
			CreditScore convertedCreditScore = CreditScore.builder()
					.requestLogData(creditScore.getRequestLogData())
					.resultCode(client.getCreditPassConfig().getDefaultResult().getResultCode())
					.resultText(CreditPassConstants.MANUAL_RESPONSE_CONVERSION_TEXT)
					.build();
			String message = Services.get(ADMessageDAO.class).retrieveByValue(getCtx(), CreditPassConstants.CREDITPASS_NOTIFICATION_MESSAGE_KEY).getMsgText();
			UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
					.recipientUserId(client.getCreditPassConfig().getNotificationUserId())
					.contentADMessage(message)
					.build();
			Services.get(INotificationBL.class).sendAfterCommit(userNotificationRequest);
			return convertedCreditScore;
		}
		return creditScore;
	}

}
