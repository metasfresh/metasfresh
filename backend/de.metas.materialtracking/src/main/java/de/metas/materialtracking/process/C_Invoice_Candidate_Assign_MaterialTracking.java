/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.materialtracking.process;

import de.metas.materialtracking.MaterialTrackingId;
import de.metas.materialtracking.impl.MaterialTrackingInvoiceCandService;
import de.metas.materialtracking.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;

import java.util.List;

public class C_Invoice_Candidate_Assign_MaterialTracking extends JavaProcess implements IProcessPrecondition
{
	private final MaterialTrackingInvoiceCandService invoiceCandService = SpringContextHolder.instance.getBean(MaterialTrackingInvoiceCandService.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Param(parameterName = I_M_Material_Tracking.COLUMNNAME_M_Material_Tracking_ID, mandatory = true)
	private int p_M_Material_Tracking_ID;

	@Param(parameterName = "IsClearAggregationKeyOverride", mandatory = true)
	private boolean p_isClearAggregationKeyOverride;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<I_C_Invoice_Candidate> ics = getSelectedInvoiceCandidates();
		final MaterialTrackingId materialTrackingId = MaterialTrackingId.ofRepoIdOrNull(p_M_Material_Tracking_ID);
		if (materialTrackingId == null)
		{
			invoiceCandService.unlinkMaterialTrackings(ics, p_isClearAggregationKeyOverride);
		}
		else
		{
			invoiceCandService.linkMaterialTrackings(ics, materialTrackingId);
		}

		return MSG_OK;
	}

	private List<I_C_Invoice_Candidate> getSelectedInvoiceCandidates()
	{
		final IQueryFilter<I_C_Invoice_Candidate> selectedPartners = getProcessInfo().getQueryFilterOrElseFalse();

		return queryBL.createQueryBuilder(I_C_Invoice_Candidate.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false)
				.addFilter(selectedPartners)
				.create()
				.list();
	}

}
