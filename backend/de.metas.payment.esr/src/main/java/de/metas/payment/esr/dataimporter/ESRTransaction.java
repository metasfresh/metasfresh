package de.metas.payment.esr.dataimporter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

/*
 * #%L
 * de.metas.payment.esr
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

@Data
@Builder
public class ESRTransaction
{
	/**
	 * Might be null in case the loader wasn't able to extract the date. In this case, there is an error message in the list.
	 */
	private final Date accountingDate;

	/**
	 * Might be null in case the loader wasn't able to extract the date. In this case, there is an error message in the list.
	 */
	private final Date paymentDate;

	/**
	 * The monetary amount. Currency is the one from the respective header.<br>
	 * Might be null in case the loader wasn't able to extract the date. In this case, there is an error message in the list.
	 */
	private final BigDecimal amount;

	private final String esrParticipantNo;

	/**
	 * This if the <b>full</b> ESR number. A substring of it is also included in the invoice we did sent to the BPartner and can include all sorts of metasfresh-custom info, such as the org.
	 */
	private final String esrReferenceNumber;

	/**
	 * Used to decide if a given transaction from a file was already imported or no.
	 */
	@NonNull
	private final String transactionKey;

	@NonNull
	private final String trxType;
	
	@NonNull
	private final ESRType type;
	
	@Singular
	private final List<String> errorMsgs;

	public BigDecimal getAmountNotNull()
	{
		if (amount == null)
		{
			return BigDecimal.ZERO;
		}
		return amount;
	}
}
