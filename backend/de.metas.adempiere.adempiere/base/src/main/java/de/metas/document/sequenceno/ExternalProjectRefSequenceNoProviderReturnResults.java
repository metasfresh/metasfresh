/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.sequenceno;

import de.metas.document.DocumentSequenceInfo;
import io.micrometer.core.lang.NonNull;
import org.compiere.util.Evaluatee;

import java.util.function.Supplier;

public class ExternalProjectRefSequenceNoProviderReturnResults extends AbstractExternalProjectRefSequenceNoProvider
{
	@Override
	public boolean isApplicable(@lombok.NonNull final Evaluatee context, @lombok.NonNull final DocumentSequenceInfo docSeqInfo)
	{
		return true;
	}

	public @NonNull String getSeqNo(
			@NonNull final Supplier<String> incrementalSeqNoSupplier,
			@NonNull final Evaluatee context,
			@NonNull final DocumentSequenceInfo documentSequenceInfo)
	{
		final String parentResult = super.provideSeqNo(incrementalSeqNoSupplier, context, documentSequenceInfo);
		return parentResult;
	}
}
