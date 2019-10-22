package de.metas.contracts.commission.testhelpers;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.time.Instant;

import de.metas.contracts.commission.model.I_C_Commission_Fact;
import lombok.Builder;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@Builder
public class CommissionFactTestRecord
{
	long timestamp;
	String commissionPoints;
	String state;

	public void createCommissionData(int C_Commission_Share_ID)
	{
		final I_C_Commission_Fact factRecord = newInstance(I_C_Commission_Fact.class);
		factRecord.setC_Commission_Share_ID(C_Commission_Share_ID);
		factRecord.setCommissionFactTimestamp(Instant.ofEpochMilli(timestamp).toString());
		factRecord.setCommission_Fact_State(state);
		factRecord.setCommissionPoints(new BigDecimal(commissionPoints));
		saveRecord(factRecord);
	}
}
