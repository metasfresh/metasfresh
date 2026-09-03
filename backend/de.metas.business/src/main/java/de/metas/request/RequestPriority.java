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

package de.metas.request;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_R_Request;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum RequestPriority implements ReferenceListAwareEnum
{
	/**
	 * Urgent = 1
	 */
	Urgent(X_R_Request.PRIORITY_Urgent),
	/**
	 * High = 3
	 */
	High(X_R_Request.PRIORITY_High),
	/**
	 * Medium = 5
	 */
	Medium(X_R_Request.PRIORITY_Medium),
	/**
	 * Low = 7
	 */
	Low(X_R_Request.PRIORITY_Low),
	/**
	 * Minor = 9
	 */
	Minor(X_R_Request.PRIORITY_Minor);

	@Getter
	private final String code;

	/**
	 * Returns the AD reference code ("1","3","5","7","9") as defined in org.compiere.model.X_R_Request.
	 */
	// getCode() provided by Lombok @Getter on field 'code'
	public static RequestPriority ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<RequestPriority> typesByCode = ReferenceListAwareEnums.index(values());

	@Nullable
	public static String toValue(@Nullable final RequestPriority priority)
	{
		return priority == null ? null : priority.getCode();
	}
}
