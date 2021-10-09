package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.contracts.commission.model.X_C_Commission_Instance;
import de.metas.order.InvoiceRule;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;

/*
 * #%L
 * de.metas.contracts
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

public enum CommissionTriggerType implements ReferenceListAwareEnum
{
	InvoiceCandidate(X_C_Commission_Instance.COMMISSIONTRIGGER_TYPE_InvoiceCandidate), //
	SalesInvoice(X_C_Commission_Instance.COMMISSIONTRIGGER_TYPE_CustomerInvoice), //
	SalesCreditmemo(X_C_Commission_Instance.COMMISSIONTRIGGER_TYPE_CustomerCreditmemo),//
	MediatedOrder(X_C_Commission_Instance.COMMISSIONTRIGGER_TYPE_MediatedOrder),

	/**
	 * Note persisted in DB
	 */
	Plain("Plain");

	@Getter
	private final String code;

	private CommissionTriggerType(@NonNull final String code)
	{
		this.code = code;
	}

	public static CommissionTriggerType ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static CommissionTriggerType ofCode(@NonNull final String code)
	{
		final CommissionTriggerType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + InvoiceRule.class + " found for code: " + code);
		}
		return type;
	}

	public static String toCodeOrNull(@Nullable final CommissionTriggerType type)
	{
		return type != null ? type.getCode() : null;
	}

	private static final ImmutableMap<String, CommissionTriggerType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), CommissionTriggerType::getCode);

}
