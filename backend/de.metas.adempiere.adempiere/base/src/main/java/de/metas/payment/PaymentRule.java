package de.metas.payment;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Order;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

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

public enum PaymentRule implements ReferenceListAwareEnum
{
	Cash(X_C_Order.PAYMENTRULE_Cash), // B
	CreditCard(X_C_Order.PAYMENTRULE_CreditCard), // K
	DirectDeposit(X_C_Order.PAYMENTRULE_DirectDeposit), // T
	Check(X_C_Order.PAYMENTRULE_Check), // S
	OnCredit(X_C_Order.PAYMENTRULE_OnCredit), // P
	DirectDebit(X_C_Order.PAYMENTRULE_DirectDebit), //
	Mixed(X_C_Order.PAYMENTRULE_Mixed), // M
	PayPal(X_C_Order.PAYMENTRULE_PayPal) // L
	;

	@Getter
	private final String code;

	PaymentRule(@NonNull final String code)
	{
		this.code = code;
	}

	public static PaymentRule ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static Optional<PaymentRule> optionalOfCode(@Nullable final String code)
	{
		return Optional.ofNullable(ofNullableCode(code));
	}

	public static PaymentRule ofCode(@NonNull final String code)
	{
		PaymentRule type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PaymentRule.class + " found for code: " + code);
		}
		return type;
	}

	public static String toCodeOrNull(final PaymentRule type)
	{
		return type != null ? type.getCode() : null;
	}

	private static final ImmutableMap<String, PaymentRule> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), PaymentRule::getCode);

	public boolean isCashOrCheck()
	{
		return isCash() || isCheck();
	}

	public boolean isCash()
	{
		return this == Cash;
	}

	public boolean isCheck()
	{
		return this == Check;
	}

	public boolean isDirectDebit()
	{
		return this == DirectDebit;
	}
}
