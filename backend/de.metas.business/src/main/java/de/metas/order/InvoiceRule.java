package de.metas.order;

<<<<<<< HEAD
import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Order;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
=======
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_Order;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.swat.base
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

<<<<<<< HEAD
public enum InvoiceRule implements ReferenceListAwareEnum
{
	AfterDelivery(X_C_Order.INVOICERULE_AfterDelivery), //
	AfterOrderDelivered(X_C_Order.INVOICERULE_AfterOrderDelivered), //
	CustomerScheduleAfterDelivery(X_C_Order.INVOICERULE_CustomerScheduleAfterDelivery), //
	Immediate(X_C_Order.INVOICERULE_Immediate),
	OrderCompletelyDelivered(X_C_Order.INVOICERULE_OrderCompletelyDelivered),//
	AfterPick(X_C_Order.INVOICERULE_AfterPick)//
	;

	@Getter
	private final String code;

	InvoiceRule(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static InvoiceRule ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static InvoiceRule ofCode(@NonNull final String code)
	{
		final InvoiceRule type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + InvoiceRule.class + " found for code: " + code);
		}
		return type;
	}

	@Nullable
	public static String toCodeOrNull(final InvoiceRule type)
	{
		return type != null ? type.getCode() : null;
	}

	private static final ImmutableMap<String, InvoiceRule> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), InvoiceRule::getCode);
=======
@RequiredArgsConstructor
@Getter
public enum InvoiceRule implements ReferenceListAwareEnum
{
	AfterDelivery(X_C_Order.INVOICERULE_AfterDelivery),
	AfterOrderDelivered(X_C_Order.INVOICERULE_AfterOrderDelivered),
	CustomerScheduleAfterDelivery(X_C_Order.INVOICERULE_CustomerScheduleAfterDelivery),
	Immediate(X_C_Order.INVOICERULE_Immediate),
	OrderCompletelyDelivered(X_C_Order.INVOICERULE_OrderCompletelyDelivered),
	AfterPick(X_C_Order.INVOICERULE_AfterPick),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<InvoiceRule> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@Nullable
	public static InvoiceRule ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	@NonNull
	public static InvoiceRule ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static String toCodeOrNull(@Nullable final InvoiceRule type) {return type != null ? type.getCode() : null;}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
