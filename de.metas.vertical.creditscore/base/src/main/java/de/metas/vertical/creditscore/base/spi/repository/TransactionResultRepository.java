package de.metas.vertical.creditscore.base.spi.repository;

/*
 * #%L
 * de.metas.vertical.creditscore.base.spi.repository
 *
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
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.util.Services;
import de.metas.vertical.creditscore.base.model.I_CS_Transaction_Result;
import de.metas.vertical.creditscore.base.spi.model.CreditScore;
import de.metas.vertical.creditscore.base.spi.model.CreditScoreRequestLogData;
import de.metas.vertical.creditscore.base.spi.model.ResultCode;
import de.metas.vertical.creditscore.base.spi.model.TransactionResult;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class TransactionResultRepository
{

	public TransactionResult createTransactionResult(@NonNull final CreditScore creditScore, @NonNull final BPartnerId bPartnerId,
			@NonNull final Optional<OrderId> orderId)
	{
		final I_CS_Transaction_Result transactionResult = newInstance(I_CS_Transaction_Result.class);

		final CreditScoreRequestLogData logData = creditScore.getRequestLogData();
		transactionResult.setRequestStartTime(Timestamp.valueOf(logData.getRequestTime()));
		transactionResult.setRequestEndTime(Timestamp.valueOf(logData.getResponseTime()));
		transactionResult.setResponseCode(creditScore.getResultCode().name());
		if (creditScore.getResultCodeOverride() != null)
		{
			transactionResult.setResponseCodeOverride(creditScore.getResultCodeOverride().name());
			transactionResult.setResponseCodeEffective(creditScore.getResultCodeOverride().name());
		}
		else
		{
			transactionResult.setResponseCodeEffective(creditScore.getResultCode().name());
		}
		transactionResult.setPaymentRule(creditScore.getPaymentRule());
		transactionResult.setRequestPrice(creditScore.getRequestPrice());
		if (creditScore.getCurrency() != null)
		{
			transactionResult.setC_Currency_ID(creditScore.getCurrency().getRepoId());
		}
		transactionResult.setResponseCodeText(creditScore.getResultText());
		transactionResult.setResponseDetails(creditScore.getResultDetails());
		transactionResult.setTransactionCustomerId(logData.getCustomerTransactionID());
		transactionResult.setTransactionIdAPI(logData.getTransactionID());
		transactionResult.setC_BPartner_ID(bPartnerId.getRepoId());
		orderId.ifPresent(orderId1 -> transactionResult.setC_Order_ID(orderId1.getRepoId()));
		save(transactionResult);
		return mapTransactionResult(transactionResult);
	}

	public Optional<TransactionResult> getLastTransactionResult(@NonNull final String paymentRule, @NonNull final BPartnerId bPartnerId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_CS_Transaction_Result transactionResult = queryBL
				.createQueryBuilder(I_CS_Transaction_Result.class)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_CS_Transaction_Result.COLUMNNAME_RequestStartTime)
				.addEqualsFilter(I_CS_Transaction_Result.COLUMNNAME_PaymentRule, paymentRule)
				.addEqualsFilter(I_CS_Transaction_Result.COLUMNNAME_C_BPartner_ID, bPartnerId.getRepoId())
				.create().first();
		TransactionResult mappedTransactionResult = null;
		if (transactionResult != null)
		{
			mappedTransactionResult = mapTransactionResult(transactionResult);
		}
		return Optional.ofNullable(mappedTransactionResult);
	}

	private TransactionResult mapTransactionResult(I_CS_Transaction_Result transactionResult)
	{
		return TransactionResult.builder()
				.resultCode(ResultCode.fromName(transactionResult.getResponseCode()))
				.resultCodeEffective(ResultCode.fromName(transactionResult.getResponseCodeEffective()))
				.resultCodeOverride(ResultCode.fromName(transactionResult.getResponseCodeOverride()))
				.requestDate(transactionResult.getRequestStartTime().toLocalDateTime())
				.transactionResultId(TransactionResultId.ofRepoId(transactionResult.getCS_Transaction_Result_ID()))
				.build();
	}

}
