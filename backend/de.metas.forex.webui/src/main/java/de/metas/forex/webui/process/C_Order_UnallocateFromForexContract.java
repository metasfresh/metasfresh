/*
 * #%L
 * de.metas.forex.webui
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

package de.metas.forex.webui.process;

import com.google.common.collect.ImmutableList;
import de.metas.forex.ForexContractAllocation;
import de.metas.forex.ForexContractAllocationRepository;
import de.metas.forex.ForexContractService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_ForeignExchangeContract_Alloc;

import java.util.List;

public class C_Order_UnallocateFromForexContract extends JavaProcess implements IProcessPrecondition
{
	private final ForexContractService forexContractService = SpringContextHolder.instance.getBean(ForexContractService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		final long count = context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_C_ForeignExchangeContract_Alloc.Table_Name.equals(recordRef.getTableName()))
				.count();

		if (count < 1)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		forexContractService.unallocateLines(getSelectionToUnallocate());

		return MSG_OK;
	}

	@NonNull
	private List<ForexContractAllocation> getSelectionToUnallocate()
	{
		return getSelectedIncludedRecords(I_C_ForeignExchangeContract_Alloc.class)
				.stream()
				.map(ForexContractAllocationRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}
}
