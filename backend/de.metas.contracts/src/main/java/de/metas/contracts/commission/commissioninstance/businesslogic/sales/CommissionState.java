package de.metas.contracts.commission.commissioninstance.businesslogic.sales;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.commission.model.X_C_Commission_Fact;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Collection;

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
public enum CommissionState implements ReferenceListAwareEnum
{
	/** Related to an invoice candidate's open (i.e. not-yet-invoiced) QtyOrdered. */
	FORECASTED(X_C_Commission_Fact.COMMISSION_FACT_STATE_FORECASTED),

	/** Related to an invoice candidate's QtyToInvoice. */
	INVOICEABLE(X_C_Commission_Fact.COMMISSION_FACT_STATE_INVOICEABLE),

	/** Related to an invoice candidate's QtyInvoiced. */
	INVOICED(X_C_Commission_Fact.COMMISSION_FACT_STATE_INVOICED);

	private static final ReferenceListAwareEnums.ValuesIndex<CommissionState> recordCode2State = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	CommissionState(final String code)
	{
		this.code = code;
	}

	public static Collection<String> allRecordCodes()
	{
		return Arrays.stream(CommissionState.values())
				.map(CommissionState::getCode)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public static CommissionState ofCode(@NonNull final String code)
	{
		return recordCode2State.ofCode(code);
	}
}
