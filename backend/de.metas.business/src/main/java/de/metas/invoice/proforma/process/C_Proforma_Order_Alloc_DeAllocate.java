/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.invoice.proforma.process;

import de.metas.invoice.proforma.ProformaOrderAllocId;
import de.metas.invoice.proforma.ProformaOrderAllocService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Proforma_Order_Alloc;

public class C_Proforma_Order_Alloc_DeAllocate extends JavaProcess implements IProcessPrecondition
{
	private final ProformaOrderAllocService proformaOrderAllocService = SpringContextHolder.instance.getBean(ProformaOrderAllocService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (!I_C_Proforma_Order_Alloc.Table_Name.equals(context.getTableName()))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Process must be run from Proforma-Order Allocation tab");
		}

		if (context.getSingleSelectedRecordId() <= 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No allocation record selected");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		proformaOrderAllocService.deallocate(proformaOrderAllocService.getById(ProformaOrderAllocId.ofRepoId(getRecord_ID())));

		return MSG_OK;
	}
}
