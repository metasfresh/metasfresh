package de.metas.product.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductPlanningSchemaBL;
import de.metas.product.ProductPlanningSchemaId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
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

public class M_ProductPlanning_CreateDefaultForSchema extends JavaProcess implements IProcessPrecondition
{
	private final IProductPlanningSchemaBL productPlanningSchemaBL = Services.get(IProductPlanningSchemaBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		final ProductPlanningSchemaId schemaId = ProductPlanningSchemaId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (schemaId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no schema selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ProductPlanningSchemaId schemaId = ProductPlanningSchemaId.ofRepoId(getRecord_ID());
		productPlanningSchemaBL.createOrUpdateDefaultProductPlanningsForSchemaId(schemaId);
		return MSG_OK;
	}
}
