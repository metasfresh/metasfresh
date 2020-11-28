package de.metas.contracts.commission.commissioninstance.businesslogic.settlement;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.contracts.commission.model.X_C_Commission_Fact;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.commission
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

/** Please keep in sync with the respective values of {@code AD_Reference_ID=541042}. */
public enum CommissionSettlementState
{
	/** basically this is the effective qty to invoice of the sales rep's commission settlement invoice candidate. */
	TO_SETTLE(X_C_Commission_Fact.COMMISSION_FACT_STATE_TO_SETTLE),

	/** related to the sales rep's invoice candidate where he/she got his commission settlement invoice. */
	SETTLED(X_C_Commission_Fact.COMMISSION_FACT_STATE_SETTLED);

	private static ImmutableMap<String, CommissionSettlementState> recordCode2State = ImmutableMap.of(
			SETTLED.getRecordCode(), SETTLED,
			TO_SETTLE.getRecordCode(), TO_SETTLE);

	@Getter
	private final String recordCode;

	private CommissionSettlementState(String recordCode)
	{
		this.recordCode = recordCode;
	}

	public static CommissionSettlementState forRecordCode(@NonNull final String recordCode)
	{
		return recordCode2State.get(recordCode);
	}

	public static Collection<String> allRecordCodes()
	{
		return Arrays.asList(CommissionSettlementState.values())
				.stream()
				.map(CommissionSettlementState::getRecordCode)
				.collect(ImmutableList.toImmutableList());
	}
}
