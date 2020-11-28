package de.metas.payment;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Payment;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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
public enum PaymentTrxType implements ReferenceListAwareEnum

{
	Sales(X_C_Payment.TRXTYPE_Sales), //
	DelayedCapture(X_C_Payment.TRXTYPE_DelayedCapture), //
	CreditPayment(X_C_Payment.TRXTYPE_CreditPayment), //
	VoiceAuthorization(X_C_Payment.TRXTYPE_VoiceAuthorization), //
	Authorization(X_C_Payment.TRXTYPE_Authorization), //
	Void(X_C_Payment.TRXTYPE_Void), //
	Refund(X_C_Payment.TRXTYPE_Rueckzahlung) //
	;

	@Getter
	private final String code;

	PaymentTrxType(@NonNull final String code)
	{
		this.code = code;
	}

	public static PaymentTrxType ofNullableCode(final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static PaymentTrxType ofCode(@NonNull final String code)
	{
		PaymentTrxType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PaymentTrxType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, PaymentTrxType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), PaymentTrxType::getCode);
}
