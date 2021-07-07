/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.bpartner.process;

import de.metas.bpartner.BPSupplierApprovalId;
import de.metas.bpartner.BPartnerSupplierApprovalRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import java.time.Instant;

public class C_BPartner_Modify_SupplierApproval extends JavaProcess implements IProcessPrecondition
{
	final BPartnerSupplierApprovalRepository repo = SpringContextHolder.instance.getBean(BPartnerSupplierApprovalRepository.class);

	@Param(parameterName = "SupplierApproval_Parameter")
	private String p_SupplierApproval;

	@Param(parameterName = "SupplierApproval_Date")
	private Instant p_SupplierApproval_Date;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final BPSupplierApprovalId bpSupplierApprovalId = BPSupplierApprovalId.ofRepoId(getRecord_ID());

		repo.updateBPSupplierApproval(bpSupplierApprovalId,
									  p_SupplierApproval,
									  p_SupplierApproval_Date);

		return MSG_OK;
	}
}
