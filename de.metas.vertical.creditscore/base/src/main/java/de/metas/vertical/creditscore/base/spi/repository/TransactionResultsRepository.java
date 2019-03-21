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
import de.metas.vertical.creditscore.base.model.I_CS_Transaction_Results;
import de.metas.vertical.creditscore.base.spi.model.CreditScore;
import de.metas.vertical.creditscore.base.spi.model.CreditScoreRequestLogData;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class TransactionResultsRepository
{

	public int createTransactionResult(@NonNull final CreditScore creditScore, @NonNull final BPartnerId bPartnerId)
	{
		final I_CS_Transaction_Results transactionResult;
		transactionResult = newInstance(I_CS_Transaction_Results.class);

		CreditScoreRequestLogData logData = creditScore.getRequestLogData();
		transactionResult.setRequestStartTime(Timestamp.valueOf(logData.getRequestTime()));
		transactionResult.setRequestEndTime(Timestamp.valueOf(logData.getResponseTime()));
		transactionResult.setResponseCode(BigDecimal.valueOf(creditScore.getResultCode()));
		transactionResult.setResponseCodeText(creditScore.getResultText());
		transactionResult.setResponseDetails(creditScore.getResultDetails());
		transactionResult.setTransactionCustomerId(logData.getCustomerTransactionID());
		transactionResult.setTransactionIdAPI(logData.getTransactionID());
		transactionResult.setC_BPartner_ID(bPartnerId.getRepoId());
		save(transactionResult);
		return transactionResult.getCS_Transaction_Results_ID();
	}

}
