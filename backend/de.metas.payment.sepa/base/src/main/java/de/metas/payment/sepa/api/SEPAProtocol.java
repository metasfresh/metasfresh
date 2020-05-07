package de.metas.payment.sepa.api;

import org.adempiere.exceptions.AdempiereException;

import de.metas.banking.payment.PaySelectionTrxType;
import de.metas.payment.sepa.model.X_SEPA_Export;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.sepa.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public enum SEPAProtocol implements ReferenceListAwareEnum
{
	CREDIT_TRANSFER_PAIN_001_001_03_CH_02(X_SEPA_Export.SEPA_PROTOCOL_CreditTransferPain00100103Ch02), //
	DIRECT_DEBIT_PAIN_008_003_02(X_SEPA_Export.SEPA_PROTOCOL_DirectDebitPain00800302) //
	;

	private static final ValuesIndex<SEPAProtocol> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	SEPAProtocol(@NonNull final String code)
	{
		this.code = code;
	}

	public static SEPAProtocol ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static SEPAProtocol ofPaySelectionTrxType(@NonNull final PaySelectionTrxType paySelectionTrxType)
	{
		if (PaySelectionTrxType.CREDIT_TRANSFER.equals(paySelectionTrxType))
		{
			return CREDIT_TRANSFER_PAIN_001_001_03_CH_02;
		}
		else if (PaySelectionTrxType.DIRECT_DEBIT.equals(paySelectionTrxType))
		{
			return DIRECT_DEBIT_PAIN_008_003_02;
		}
		else
		{
			throw new AdempiereException("Unknown " + PaySelectionTrxType.class.getSimpleName() + ": " + paySelectionTrxType);
		}
	}
}
