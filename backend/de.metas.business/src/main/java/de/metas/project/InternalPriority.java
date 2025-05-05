/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.project;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Project;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

/**
 * Keep in sync with reference list "_PriorityRule" {@code AD_Reference_ID=154}
 */
@AllArgsConstructor
public enum InternalPriority implements ReferenceListAwareEnum
{
	URGENT(X_C_Project.INTERNALPRIORITY_Urgent, 1),
	HIGH(X_C_Project.INTERNALPRIORITY_High, 3),
	MEDIUM(X_C_Project.INTERNALPRIORITY_Medium, 5),
	LOW(X_C_Project.INTERNALPRIORITY_Low, 7),
	MINOR(X_C_Project.INTERNALPRIORITY_Minor, 9);

	@Getter @NonNull private final String code;
	private final int intValue;

	@Nullable
	public static InternalPriority ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	@Nullable
	public static String toCode(@Nullable final InternalPriority internalPriority)
	{
		return Optional.ofNullable(internalPriority)
				.map(InternalPriority::getCode)
				.orElse(null);
	}

	public static InternalPriority ofCode(@NonNull final String code)
	{
		final InternalPriority priority = prioritiesByCode.get(code);
		if (priority == null)
		{
			throw new AdempiereException("No " + InternalPriority.class + " found for code: " + code);
		}
		return priority;
	}

	private static final ImmutableMap<String, InternalPriority> prioritiesByCode = Maps.uniqueIndex(Arrays.asList(values()), InternalPriority::getCode);

	public boolean isHigherThan(@NonNull final InternalPriority other)
	{
		return intValue < other.intValue;
	}

	public int toIntUrgentToMinor() {return intValue;}

	public int toIntMinorToUrgent() {return MINOR.intValue - toIntUrgentToMinor() + 1;}
}
