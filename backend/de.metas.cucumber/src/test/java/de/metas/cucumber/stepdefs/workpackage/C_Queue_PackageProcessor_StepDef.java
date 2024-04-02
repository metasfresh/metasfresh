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

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class C_Queue_PackageProcessor_StepDef
{
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

	@NonNull
	private final C_Queue_PackageProcessor_StepDefData packageProcessorTable;

	public C_Queue_PackageProcessor_StepDef(@NonNull final C_Queue_PackageProcessor_StepDefData packageProcessorTable)
	{
		this.packageProcessorTable = packageProcessorTable;
	}

	@And("load C_Queue_PackageProcessor by classname:")
	public void load_C_Queue_PackageProcessor(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String classname = DataTableUtil.extractStringForColumnName(row, I_C_Queue_PackageProcessor.COLUMNNAME_Classname);

			assertThat(classname).as("Missing classname").isNotNull();

			final QueuePackageProcessorId packageProcessorId = queueDAO.retrieveQueuePackageProcessorIdFor(classname);

			assertThat(packageProcessorId).as("Missing C_Queue_PackageProcessor for classname=%s", classname).isNotNull();

			final I_C_Queue_PackageProcessor packageProcessor = InterfaceWrapperHelper.load(packageProcessorId, I_C_Queue_PackageProcessor.class);

			final String packageProcessIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Queue_PackageProcessor.COLUMNNAME_C_Queue_PackageProcessor_ID + ".Identifier");

			packageProcessorTable.putOrReplace(packageProcessIdentifier, packageProcessor);
		}
	}
}
