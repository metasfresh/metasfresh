package de.metas.material.dispo.candidate;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;

import de.metas.material.dispo.model.I_MD_Candidate_Transaction_Detail;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class TransactionDetail
{
	public static TransactionDetail forTransactionDetailRecord(
			@NonNull final I_MD_Candidate_Transaction_Detail transactionDetailRecord)
	{
		return new TransactionDetail(
				transactionDetailRecord.getMovementQty(),
				transactionDetailRecord.getM_Transaction_ID());
	}

	BigDecimal quantity;

	int transactionId;

	public TransactionDetail(@NonNull final BigDecimal quantity, final int transactionId)
	{
		Preconditions.checkArgument(transactionId > 0, "The given parameter transactionId=%s needs to be > 0", transactionId);
		this.transactionId = transactionId;

		this.quantity = quantity;
	}
}
