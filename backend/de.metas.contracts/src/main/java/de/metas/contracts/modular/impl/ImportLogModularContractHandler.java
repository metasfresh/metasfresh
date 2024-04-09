/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.impl;

import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.model.X_I_ModCntr_Log;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.computing.IModularContractComputingMethodHandler;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Stream;

import static de.metas.contracts.modular.ComputingMethodType.IMPORT_LOG;

@Component
@RequiredArgsConstructor
public class ImportLogModularContractHandler implements IModularContractComputingMethodHandler
{
	@Override
	public boolean applies(final @NonNull TableRecordReference tableRecordReference)
	{
		if(tableRecordReference.getTableName().equals(I_I_ModCntr_Log.Table_Name))
		{
			final I_I_ModCntr_Log importLogRecord = InterfaceWrapperHelper.load(tableRecordReference.getRecord_ID(), I_I_ModCntr_Log.class);
			return Check.isBlank(importLogRecord.getI_ErrorMsg()) &&
					!Objects.equals(X_I_ModCntr_Log.I_ISIMPORTED_ImportFailed, importLogRecord.getI_IsImported()) ||
					CalendarId.ofRepoIdOrNull(importLogRecord.getC_Calendar_ID()) != null &&
							YearId.ofRepoIdOrNull(importLogRecord.getHarvesting_Year_ID()) != null &&
							FlatrateTermId.ofRepoIdOrNull(importLogRecord.getC_Flatrate_Term_ID()) != null;
		}
		return false;
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_I_ModCntr_Log model)
	{
		return Stream.of(FlatrateTermId.ofRepoId(model.getC_Flatrate_Term_ID()));
	}

	@Override
	public void validateAction(final @NonNull I_I_ModCntr_Log model, final @NonNull ModelAction action)
	{
		switch (action)
		{
			case COMPLETED, REVERSED ->
			{
			}
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}

	@Override
	public @NonNull ComputingMethodType getComputingMethodType()
	{
		return IMPORT_LOG;
	}
}
