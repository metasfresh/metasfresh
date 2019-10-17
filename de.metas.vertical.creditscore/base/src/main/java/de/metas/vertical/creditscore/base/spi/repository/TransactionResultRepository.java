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
			final OrderId orderId)
	{
		final I_CS_Transaction_Result transactionResultRecord = newInstance(I_CS_Transaction_Result.class);

		final CreditScoreRequestLogData logData = creditScore.getRequestLogData();
		transactionResultRecord.setRequestStartTime(Timestamp.valueOf(logData.getRequestTime()));
		transactionResultRecord.setRequestEndTime(Timestamp.valueOf(logData.getResponseTime()));
		transactionResultRecord.setResponseCode(creditScore.getResultCode().name());
		if (creditScore.getResultCodeOverride() != null)
		{
			transactionResultRecord.setResponseCodeOverride(creditScore.getResultCodeOverride().name());
			transactionResultRecord.setResponseCodeEffective(creditScore.getResultCodeOverride().name());
		}
		else
		{
			transactionResultRecord.setResponseCodeEffective(creditScore.getResultCode().name());
		}
		transactionResultRecord.setPaymentRule(creditScore.getPaymentRule());
		transactionResultRecord.setRequestPrice(creditScore.getRequestPrice());
		if (creditScore.getCurrency() != null)
		{
			transactionResultRecord.setC_Currency_ID(creditScore.getCurrency().getRepoId());
		}
		transactionResultRecord.setResponseCodeText(creditScore.getResultText());
		transactionResultRecord.setResponseDetails(creditScore.getResultDetails());
		transactionResultRecord.setTransactionCustomerId(logData.getCustomerTransactionID());
		transactionResultRecord.setTransactionIdAPI(logData.getTransactionID());
		transactionResultRecord.setC_BPartner_ID(bPartnerId.getRepoId());
		if (orderId != null)
		{
			transactionResultRecord.setC_Order_ID(orderId.getRepoId());
		}
		save(transactionResultRecord);
		return ofRecord(transactionResultRecord);
	}

	public Optional<TransactionResult> getLastTransactionResult(@NonNull final String paymentRule, @NonNull final BPartnerId bPartnerId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_CS_Transaction_Result transactionResultRecord = queryBL
				.createQueryBuilder(I_CS_Transaction_Result.class)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_CS_Transaction_Result.COLUMNNAME_RequestStartTime)
				.addEqualsFilter(I_CS_Transaction_Result.COLUMNNAME_PaymentRule, paymentRule)
				.addEqualsFilter(I_CS_Transaction_Result.COLUMNNAME_C_BPartner_ID, bPartnerId.getRepoId())
				.create().first();
		TransactionResult transactionResult = null;
		if (transactionResultRecord != null)
		{
			transactionResult = ofRecord(transactionResultRecord);
		}
		return Optional.ofNullable(transactionResult);
	}

	private TransactionResult ofRecord(I_CS_Transaction_Result transactionResult)
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
