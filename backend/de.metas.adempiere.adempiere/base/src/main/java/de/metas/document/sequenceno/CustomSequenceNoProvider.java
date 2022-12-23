package de.metas.document.sequenceno;

import de.metas.document.DocumentSequenceInfo;
import lombok.NonNull;
import org.compiere.util.Evaluatee;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public interface CustomSequenceNoProvider
{

	boolean isApplicable(@NonNull Evaluatee context, @NonNull final DocumentSequenceInfo docSeqInfo);

	String provideSequenceNo(@NonNull Evaluatee context, @NonNull final DocumentSequenceInfo docSeqInfo, @Nullable final String autoIncrementedSeqNumber);

	/**
	 * Indicate to metasfresh if this implementation wants its sequence number to be "standalone" or, be the prefix for a "normal", incremental number.
	 * Note that if the incremental number is appended, that is <i>without</i> applying the {@code AD_Sequence}'s decimal pattern.
	 */
	default boolean isUseIncrementSeqNoAsPrefix()
	{
		return true;
	}

}
