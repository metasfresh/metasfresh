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

package de.metas.contracts.modular.interim.logImpl;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutLineQuery;
import de.metas.inout.InOutQuery;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class InterimContractHandler implements IModularContractTypeHandler<I_C_Flatrate_Term>
{
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);

	@Override
	public @NonNull Class<I_C_Flatrate_Term> getType()
	{
		return I_C_Flatrate_Term.class;
	}

	@Override
	public boolean applies(final @NonNull I_C_Flatrate_Term flatrateTermRecord)
	{
		return TypeConditions.ofCode(flatrateTermRecord.getType_Conditions()).isInterimContractType();
	}

	@Override
	public boolean applies(final @NonNull LogEntryContractType logEntryContractType)
	{
		return logEntryContractType.isInterimContractType();
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_C_Flatrate_Term flatrateTermRecord)
	{
		return Stream.ofNullable(FlatrateTermId.ofRepoIdOrNull(flatrateTermRecord.getC_Flatrate_Term_ID()));
	}

	@Override
	public void validateDocAction(final @NonNull I_C_Flatrate_Term model, final @NonNull ModelAction action)
	{
		if (action != ModelAction.COMPLETED)
		{
			throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}

	public void handleAction(
			@NonNull final I_C_Flatrate_Term interimContract,
			@NonNull final ModelAction modelAction,
			@NonNull final ModularContractService contractService)
	{
		if (modelAction != ModelAction.COMPLETED)
		{
			return;
		}

		inoutBL.streamLines(
						InOutLineQuery.builder()
								.headerQuery(InOutQuery.builder()
										.docStatus(DocStatus.Completed)
										.movementDateFrom(interimContract.getStartDate().toInstant())
										.movementDateTo(Check.assumeNotNull(interimContract.getEndDate(), "End Date shouldn't be null").toInstant())
										.build())
								.flatrateTermId(interimContract.getModular_Flatrate_Term_ID())
								.build())
				.forEach(inoutLine -> contractService.invokeWithModel(inoutLine, modelAction, LogEntryContractType.INTERIM));
	}
}
