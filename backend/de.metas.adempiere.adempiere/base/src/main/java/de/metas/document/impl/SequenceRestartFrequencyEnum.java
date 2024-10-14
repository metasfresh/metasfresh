/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.document.impl;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_Sequence;

import javax.annotation.Nullable;

public enum SequenceRestartFrequencyEnum implements ReferenceListAwareEnum
{
	Year(X_AD_Sequence.RESTARTFREQUENCY_Year),
	Month(X_AD_Sequence.RESTARTFREQUENCY_Month),
	Day(X_AD_Sequence.RESTARTFREQUENCY_Day);

	private static final ReferenceListAwareEnums.ValuesIndex<SequenceRestartFrequencyEnum> typesByCode = ReferenceListAwareEnums.index(values());

	@Getter
	@NonNull
	private final String code;

	SequenceRestartFrequencyEnum(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static SequenceRestartFrequencyEnum ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	@NonNull
	public static SequenceRestartFrequencyEnum ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}
}
