/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.api.impl;

import de.metas.common.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Instant;

@EqualsAndHashCode
@ToString
public final class ReceiptMovementDateRule
{
	public static final ReceiptMovementDateRule ORDER_DATE_PROMISED = new ReceiptMovementDateRule(Type.ORDER_DATE_PROMISED, null);
	public static final ReceiptMovementDateRule EXTERNAL_DATE_IF_AVAIL = new ReceiptMovementDateRule(Type.EXTERNAL_DATE_IF_AVAIL, null);
	public static final ReceiptMovementDateRule CURRENT_DATE = new ReceiptMovementDateRule(Type.CURRENT_DATE, null);

	public static ReceiptMovementDateRule fixedDate(@NonNull Instant fixedDate) {return new ReceiptMovementDateRule(Type.FIXED_DATE, fixedDate);}

	private enum Type
	{
		ORDER_DATE_PROMISED,
		EXTERNAL_DATE_IF_AVAIL,
		CURRENT_DATE,
		FIXED_DATE,
	}

	@NonNull private final Type type;
	@Nullable private final Instant fixedDate;

	private ReceiptMovementDateRule(@NonNull final Type type, @Nullable final Instant fixedDate)
	{
		this.type = type;
		this.fixedDate = fixedDate;
	}

	public interface CaseMapper<T>
	{
		T orderDatePromised();

		T externalDateIfAvailable();

		T currentDate();

		T fixedDate(@NonNull Instant fixedDate);
	}

	public <T> T map(@NonNull final CaseMapper<T> mapper)
	{
		switch (type)
		{
			case ORDER_DATE_PROMISED:
				return mapper.orderDatePromised();
			case EXTERNAL_DATE_IF_AVAIL:
				return mapper.externalDateIfAvailable();
			case CURRENT_DATE:
				return mapper.currentDate();
			case FIXED_DATE:
				final Instant fixedDate = Check.assumeNotNull(this.fixedDate, "Fixed date is set");
				return mapper.fixedDate(fixedDate);
			default:
				throw new AdempiereException("Type not handled: " + type);
		}
	}
}
