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
import de.metas.vertical.creditscore.base.spi.model.TransactionResult;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class TransactionResultRepository
{

	public TransactionResultId createTransactionResult(@NonNull final CreditScore creditScore, @NonNull final BPartnerId bPartnerId)
	{
		final I_CS_Transaction_Result transactionResult = newInstance(I_CS_Transaction_Result.class);

		mapResultData(creditScore, bPartnerId, transactionResult);
		save(transactionResult);
		return TransactionResultId.ofRepoId(transactionResult.getCS_Transaction_Result_ID());
	}

	private void mapResultData(@NonNull CreditScore creditScore, @NonNull BPartnerId bPartnerId, I_CS_Transaction_Result transactionResult)
	{
		CreditScoreRequestLogData logData = creditScore.getRequestLogData();
		transactionResult.setRequestStartTime(Timestamp.valueOf(logData.getRequestTime()));
		transactionResult.setRequestEndTime(Timestamp.valueOf(logData.getResponseTime()));
		transactionResult.setResponseCode(BigDecimal.valueOf(creditScore.getResultCode()));
		transactionResult.setPaymentRule(creditScore.getPaymentRule());
		transactionResult.setResponseCodeText(creditScore.getResultText());
		transactionResult.setResponseDetails(creditScore.getResultDetails());
		transactionResult.setTransactionCustomerId(logData.getCustomerTransactionID());
		transactionResult.setTransactionIdAPI(logData.getTransactionID());
		transactionResult.setC_BPartner_ID(bPartnerId.getRepoId());
	}

	public TransactionResult createTransactionResult(@NonNull final CreditScore creditScore, @NonNull final BPartnerId bPartnerId,
			@NonNull final OrderId orderId)
	{
		final I_CS_Transaction_Result transactionResult = newInstance(I_CS_Transaction_Result.class);

		transactionResult.setC_Order_ID(orderId.getRepoId());
		mapResultData(creditScore, bPartnerId, transactionResult);
		save(transactionResult);
		return TransactionResult.builder()
				.resultCode(transactionResult.getResponseCode().intValue())
				.requestDate(transactionResult.getRequestStartTime().toLocalDateTime())
				.transactionResultId(TransactionResultId.ofRepoId(transactionResult.getCS_Transaction_Result_ID()))
				.build();
	}

	public TransactionResult getLastTransactionResult(@NonNull final String paymentRule, @NonNull final BPartnerId bPartnerId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_CS_Transaction_Result transactionResult = queryBL
				.createQueryBuilder(I_CS_Transaction_Result.class)
				.addOnlyActiveRecordsFilter()
				.orderByDescending(I_CS_Transaction_Result.COLUMNNAME_RequestStartTime)
				.addEqualsFilter(I_CS_Transaction_Result.COLUMNNAME_PaymentRule, paymentRule)
				.addEqualsFilter(I_CS_Transaction_Result.COLUMNNAME_C_BPartner_ID, bPartnerId.getRepoId())
				.create().first();
		if (transactionResult != null)
		{
			return TransactionResult.builder()
					.resultCode(transactionResult.getResponseCode().intValue())
					.requestDate(transactionResult.getRequestStartTime().toLocalDateTime())
					.transactionResultId(TransactionResultId.ofRepoId(transactionResult.getCS_Transaction_Result_ID()))
					.build();
		}
		else
		{
			return null;
		}
	}

}
