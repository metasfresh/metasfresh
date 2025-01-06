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

package de.metas.contracts.process;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import java.util.Objects;
import java.util.stream.Stream;

import static de.metas.inout.model.I_M_InOutLine.COLUMNNAME_IsPackagingMaterial;

public class M_InOut_ReadyForInterimInvoicing extends JavaProcess implements IProcessPrecondition
{
	@Param(parameterName = "IsInterimInvoiceable")
	private boolean p_IsInterimInvoiceable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (streamFlatrateTermIds(context).anyMatch(Objects::isNull))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("At least one line has no contract set !");
		}

		final boolean isDocStatusEligible = getSelectedRecords()
				.map(I_M_InOut::getDocStatus)
				.map(DocStatus::ofCode)
				.allMatch(DocStatus::isCompleted);
		if (!isDocStatusEligible)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("At least one record is not completed");
		}

		final boolean areAllModularContracts = streamFlatrateTermIds(context)
				.map(flatrateBL::getById)
				.map(I_C_Flatrate_Term::getType_Conditions)
				.map(TypeConditions::ofCode)
				.allMatch(TypeConditions::isModularContractType);

		return ProcessPreconditionsResolution.acceptIf(areAllModularContracts);
	}

	@Override
	protected String doIt() throws Exception
	{
		getSelectedRecords()
				.forEach(inOutRecord -> {
					inOutRecord.setIsInterimInvoiceable(p_IsInterimInvoiceable);

					inOutBL.save(inOutRecord);
				});

		return MSG_OK;
	}

	@NonNull
	private Stream<I_M_InOut> getSelectedRecords()
	{
		return retrieveActiveSelectedRecordsQueryBuilder(I_M_InOut.class)
				.create()
				.iterateAndStream();
	}

	@NonNull
	private Stream<FlatrateTermId> streamFlatrateTermIds(@NonNull final IProcessPreconditionsContext context)
	{
		return queryBL.createQueryBuilder(I_M_InOut.class)
				.filter(context.getQueryFilter(I_M_InOut.class))
				.addOnlyActiveRecordsFilter()
				.andCollectChildren(I_M_InOutLine.COLUMN_M_InOut_ID)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(COLUMNNAME_IsPackagingMaterial, false)
				.stream()
				.map(I_M_InOutLine::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoIdOrNull);
	}
}
