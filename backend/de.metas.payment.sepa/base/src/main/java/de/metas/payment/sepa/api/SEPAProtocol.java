package de.metas.payment.sepa.api;

import org.adempiere.exceptions.AdempiereException;

import de.metas.banking.payment.PaySelectionTrxType;
import de.metas.payment.sepa.model.X_SEPA_Export;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

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
	CREDIT_TRANSFER_PAIN_001_001_09_CH_03(X_SEPA_Export.SEPA_PROTOCOL_CreditTransferPain00100109Ch03), //
	/**
	 * Old direct debit format; refused for new exports (see {@link #ofPaySelectionTrxType}), kept for exports created with it.
	 */
	DIRECT_DEBIT_PAIN_008_003_02(X_SEPA_Export.SEPA_PROTOCOL_DirectDebitPain00800302), //
	DIRECT_DEBIT_PAIN_008_001_08(X_SEPA_Export.SEPA_PROTOCOL_DirectDebitPain00800108) //
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

	@Nullable
	public static SEPAProtocol ofNullableCode(@Nullable final String code)
	{
		return code != null && !code.trim().isEmpty() ? ofCode(code) : null;
	}

	public boolean isCreditTransfer()
	{
		return this == CREDIT_TRANSFER_PAIN_001_001_03_CH_02 || this == CREDIT_TRANSFER_PAIN_001_001_09_CH_03;
	}

	public boolean isDirectDebit()
	{
		return this == DIRECT_DEBIT_PAIN_008_003_02 || this == DIRECT_DEBIT_PAIN_008_001_08;
	}

	/**
	 * @return the protocol used for a new export of the given type if none is chosen:
	 * {@link #CREDIT_TRANSFER_PAIN_001_001_09_CH_03} for credit transfer, {@link #DIRECT_DEBIT_PAIN_008_001_08} for direct debit
	 */
	public static SEPAProtocol getDefault(@NonNull final PaySelectionTrxType paySelectionTrxType)
	{
		if (PaySelectionTrxType.CREDIT_TRANSFER.equals(paySelectionTrxType))
		{
			return CREDIT_TRANSFER_PAIN_001_001_09_CH_03;
		}
		else if (PaySelectionTrxType.DIRECT_DEBIT.equals(paySelectionTrxType))
		{
			return DIRECT_DEBIT_PAIN_008_001_08;
		}
		else
		{
			throw new AdempiereException("Unknown " + PaySelectionTrxType.class.getSimpleName() + ": " + paySelectionTrxType);
		}
	}

	/**
	 * @param chosenProtocol the protocol chosen by the user; if {@code null}, {@link #getDefault} of the given type is used
	 * @throws AdempiereException if the chosen protocol does not match the type, or is {@link #DIRECT_DEBIT_PAIN_008_003_02}, which can no longer be used for new exports
	 */
	public static SEPAProtocol ofPaySelectionTrxType(
			@NonNull final PaySelectionTrxType paySelectionTrxType,
			@Nullable final SEPAProtocol chosenProtocol)
	{
		if (chosenProtocol == null)
		{
			return getDefault(paySelectionTrxType);
		}

		if (PaySelectionTrxType.CREDIT_TRANSFER.equals(paySelectionTrxType))
		{
			if (!chosenProtocol.isCreditTransfer())
			{
				throw new AdempiereException("SEPA protocol " + chosenProtocol.getCode() + " is not a credit transfer protocol");
			}
			return chosenProtocol;
		}
		else if (PaySelectionTrxType.DIRECT_DEBIT.equals(paySelectionTrxType))
		{
			if (!chosenProtocol.isDirectDebit())
			{
				throw new AdempiereException("SEPA protocol " + chosenProtocol.getCode() + " is not a direct debit protocol");
			}
			if (chosenProtocol == DIRECT_DEBIT_PAIN_008_003_02)
			{
				throw new AdempiereException("SEPA protocol " + chosenProtocol.getCode() + " can no longer be used for new direct debit exports");
			}
			return chosenProtocol;
		}
		else
		{
			throw new AdempiereException("Unknown " + PaySelectionTrxType.class.getSimpleName() + ": " + paySelectionTrxType);
		}
	}
}
