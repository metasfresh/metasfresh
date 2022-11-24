/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.workpackage;

import com.google.common.collect.ImmutableSet;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_Processor_Assign;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class C_Queue_Processor_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	private final C_Queue_PackageProcessor_StepDefData packageProcessorTable;
	@NonNull
	private final C_Queue_Processor_StepDefData processorTable;

	public C_Queue_Processor_StepDef(
			@NonNull final C_Queue_PackageProcessor_StepDefData packageProcessorTable,
			@NonNull final C_Queue_Processor_StepDefData processorTable)
	{
		this.packageProcessorTable = packageProcessorTable;
		this.processorTable = processorTable;
	}

	@And("load C_Queue_Processor by C_Queue_PackageProcessor:")
	public void load_C_Queue_Processor(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String packageProcessIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Queue_PackageProcessor.COLUMNNAME_C_Queue_PackageProcessor_ID + ".Identifier");
			assertThat(packageProcessIdentifier).isNotNull();

			final I_C_Queue_PackageProcessor packageProcessor = packageProcessorTable.get(packageProcessIdentifier);

			final Set<Integer> queueProcessorIds = queryBL.createQueryBuilder(I_C_Queue_Processor_Assign.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_Queue_Processor_Assign.COLUMNNAME_C_Queue_PackageProcessor_ID, packageProcessor.getC_Queue_PackageProcessor_ID())
					.andCollect(I_C_Queue_Processor.COLUMNNAME_C_Queue_Processor_ID, I_C_Queue_Processor.class)
					.addOnlyActiveRecordsFilter()
					.create()
					.stream()
					.map(I_C_Queue_Processor::getC_Queue_Processor_ID)
					.collect(ImmutableSet.toImmutableSet());

			assertThat(queueProcessorIds.size()).isEqualTo(1);

			final Integer queueProcessorId = queueProcessorIds.iterator().next();

			final String queueProcessorIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Queue_Processor.COLUMNNAME_C_Queue_Processor_ID + ".Identifier");
			processorTable.putOrReplace(queueProcessorIdentifier, InterfaceWrapperHelper.load(queueProcessorId, I_C_Queue_Processor.class));
		}
	}
}
