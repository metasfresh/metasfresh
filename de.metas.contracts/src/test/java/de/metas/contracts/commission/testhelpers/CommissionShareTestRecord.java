package de.metas.contracts.commission.testhelpers;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.util.List;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import lombok.Builder;
import lombok.Builder.Default;
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
public class CommissionShareTestRecord
{
	@NonNull
	BPartnerId C_BPartner_SalesRep_ID;
	@NonNull
	@Default
	String pointsSum_Forecasted = "0";
	@NonNull
	@Default
	String pointsSum_Invoiceable = "0";
	@NonNull
	@Default
	String pointsSum_Invoiced = "0";

	@NonNull
	@Default
	String pointsSum_ToSettle = "0";

	@NonNull
	@Default
	String pointsSum_Settled = "0";

	@NonNull
	Integer levelHierarchy;

	@NonNull
	FlatrateTermId flatrateTermId;

	@Singular
	List<CommissionFactTestRecord> commissionFactTestRecords;

	public int createCommissionData(final int C_Commission_Instance_ID)
	{
		final I_C_Commission_Share shareRecord = newInstance(I_C_Commission_Share.class);
		shareRecord.setC_Flatrate_Term_ID(flatrateTermId.getRepoId());
		shareRecord.setC_Commission_Instance_ID(C_Commission_Instance_ID);
		shareRecord.setC_BPartner_SalesRep_ID(C_BPartner_SalesRep_ID.getRepoId());
		shareRecord.setLevelHierarchy(levelHierarchy);
		shareRecord.setPointsSum_Forecasted(new BigDecimal(pointsSum_Forecasted));
		shareRecord.setPointsSum_Invoiceable(new BigDecimal(pointsSum_Invoiceable));
		shareRecord.setPointsSum_Invoiced(new BigDecimal(pointsSum_Invoiced));
		shareRecord.setPointsSum_ToSettle(new BigDecimal(pointsSum_ToSettle));
		shareRecord.setPointsSum_Settled(new BigDecimal(pointsSum_Settled));

		saveRecord(shareRecord);

		for (final CommissionFactTestRecord commissionFactTestRecord : commissionFactTestRecords)
		{
			commissionFactTestRecord.createCommissionData(shareRecord.getC_Commission_Share_ID());
		}
		return shareRecord.getC_Commission_Share_ID();
	}
}
