package de.metas.contracts.commission.testhelpers;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionInstanceId;
import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.invoicecandidate.InvoiceCandidateId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@Builder
public class CommissionInstanceTestRecord
{
	@NonNull
	String pointsBase_Forecasted;
	@NonNull
	String pointsBase_Invoiceable;
	@NonNull
	String pointsBase_Invoiced;

	@NonNull
	Long mostRecentTriggerTimestamp;

	@NonNull
	InvoiceCandidateId C_INVOICE_CANDIDATE_ID;

	@Singular
	List<CommissionShareTestRecord> commissionShareTestRecords;

	public CreateCommissionInstanceResult createCommissionData()
	{
		final I_C_Commission_Instance instanceRecord = newInstance(I_C_Commission_Instance.class);
		instanceRecord.setC_Invoice_Candidate_ID(C_INVOICE_CANDIDATE_ID.getRepoId());
		instanceRecord.setMostRecentTriggerTimestamp(Timestamp.from(Instant.ofEpochMilli(mostRecentTriggerTimestamp)));
		instanceRecord.setPointsBase_Forecasted(new BigDecimal(pointsBase_Forecasted));
		instanceRecord.setPointsBase_Invoiceable(new BigDecimal(pointsBase_Invoiceable));
		instanceRecord.setPointsBase_Invoiced(new BigDecimal(pointsBase_Invoiced));
		saveRecord(instanceRecord);

		final ImmutableMap.Builder<BPartnerId, Integer> bpartnerId2commissionShareId = ImmutableMap.builder();
		for (final CommissionShareTestRecord commissionShareTestRecord : commissionShareTestRecords)
		{
			final int C_Commission_Share_ID = commissionShareTestRecord.createCommissionData(instanceRecord.getC_Commission_Instance_ID());
			bpartnerId2commissionShareId.put(commissionShareTestRecord.getC_BPartner_SalesRep_ID(), C_Commission_Share_ID);
		}
		return new CreateCommissionInstanceResult(
				CommissionInstanceId.ofRepoId(instanceRecord.getC_Commission_Instance_ID()),
				bpartnerId2commissionShareId.build());
	}

	@Value
	public static class CreateCommissionInstanceResult
	{
		@NonNull
		CommissionInstanceId commissionInstanceId;

		ImmutableMap<BPartnerId, Integer> bpartnerId2commissionShareId;
	}

}
