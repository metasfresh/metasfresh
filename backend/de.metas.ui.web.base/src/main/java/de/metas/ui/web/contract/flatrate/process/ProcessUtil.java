/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.contract.flatrate.process;

import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessInfo;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.Set;

@UtilityClass
class ProcessUtil
{
	@Nullable
	I_C_Flatrate_DataEntry extractEntryOrNull(@NonNull final IProcessPreconditionsContext context)
	{
		final Set<TableRecordReference> includedRecords = context.getSelectedIncludedRecords();
		return extractDataEntryOrNull(includedRecords);
	}

	@Nullable
	I_C_Flatrate_DataEntry extractEntryOrNull(@NonNull final ProcessInfo pi)
	{
		final Set<TableRecordReference> includedRecords = pi.getSelectedIncludedRecords();
		return extractDataEntryOrNull(includedRecords);
	}

	@NonNull
	I_C_Flatrate_DataEntry extractEntry(@NonNull final ProcessInfo pi)
	{
		final Set<TableRecordReference> includedRecords = pi.getSelectedIncludedRecords();
		final I_C_Flatrate_DataEntry result = extractDataEntryOrNull(includedRecords);

		// we can assume this because otherwise the processes' checkPreconditionsApplicable whould not have been passed
		return Check.assumeNotNull(result, "There should be one selected included C_Flatrate_DataEntry. ProcessInfo={}", pi);
	}

	private static I_C_Flatrate_DataEntry extractDataEntryOrNull(@Nullable final Set<TableRecordReference> includedRecords)
	{
		if (includedRecords == null || includedRecords.size() != 1)
		{
			return null;
		}

		final TableRecordReference reference = includedRecords.iterator().next();
		return reference.getModel(I_C_Flatrate_DataEntry.class);
	}
}
