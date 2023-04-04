package de.metas.payment.paymentterm;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_PaymentTerm;

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

public enum BaseLineType implements ReferenceListAwareEnum
{
	AfterDelivery(X_C_PaymentTerm.BASELINETYPES_AfterDelivery), // AD
	AfterBillOfLanding(X_C_PaymentTerm.BASELINETYPES_AfterBillOfLanding), // ABL
	InvoiceDate(X_C_PaymentTerm.BASELINETYPES_InvoiceDate) // ID
	;

	@Getter
	private final String code;

	BaseLineType(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static BaseLineType ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static Optional<BaseLineType> optionalOfCode(@Nullable final String code)
	{
		return Optional.ofNullable(ofNullableCode(code));
	}

	public static BaseLineType ofCode(@NonNull final String code)
	{
		final BaseLineType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + BaseLineType.class + " found for code: " + code);
		}
		return type;
	}

	@Nullable
	public static String toCodeOrNull(@Nullable final BaseLineType type)
	{
		return type != null ? type.getCode() : null;
	}

	private static final ImmutableMap<String, BaseLineType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), BaseLineType::getCode);


	public boolean isAfterDelivery()
	{
		return this == AfterDelivery;
	}

	public boolean isAfterBillOfLanding()
	{
		return this == AfterBillOfLanding;
	}

	public boolean isInvoiceDate()
	{
		return this == InvoiceDate;
	}
}
