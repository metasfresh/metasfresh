/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.payment;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Payment;

import javax.annotation.Nullable;
import java.util.Arrays;

public enum RefundStatus implements ReferenceListAwareEnum
{
	ScheduledForRefund(X_C_Payment.REFUNDSTATUS_ScheduledForRefund),
	Refunded(X_C_Payment.REFUNDSTATUS_Refunded);

	private static final ImmutableMap<String, RefundStatus> statusesByCode = Maps.uniqueIndex(Arrays.asList(values()), RefundStatus::getCode);

	@Getter
	private final String code;

	RefundStatus(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static RefundStatus ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static RefundStatus ofCode(@NonNull final String code)
	{
		final RefundStatus status = statusesByCode.get(code);
		if (status == null)
		{
			throw new AdempiereException("No " + RefundStatus.class + " found for code: " + code);
		}
		return status;
	}

	public boolean isScheduledForRefund()
	{
		return this == ScheduledForRefund;
	}

}
