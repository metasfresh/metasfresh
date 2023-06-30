/*
 * #%L
 * de.metas.business
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

package de.metas.requisition.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.requisition.RequisitionId;
import de.metas.requisition.RequisitionService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Interceptor(I_M_RequisitionLine.class)
public class M_RequisitionLine
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final RequisitionService requisitionService;

	public M_RequisitionLine(@NonNull final RequisitionService requisitionService)
	{
		this.requisitionService = requisitionService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_M_RequisitionLine.COLUMNNAME_C_BPartner_ID })
	public void updateIsVendor(@NonNull final I_M_RequisitionLine requisitionLine)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(requisitionLine.getC_BPartner_ID());
		if (bpartnerId != null)
		{
			final I_C_BPartner bpartner = bpartnerDAO.getById(bpartnerId);
			requisitionLine.setIsVendor(bpartner.isVendor());
		}
	}

	@ModelChange(timings = {ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE }, ifColumnsChanged = { I_M_RequisitionLine.COLUMNNAME_LineNetAmt })
	public void updateRequisitionTotalLines(@NonNull final I_M_RequisitionLine requisitionLine)
	{
		final RequisitionId requisitionId = RequisitionId.ofRepoId(requisitionLine.getM_Requisition_ID());

		trxManager.accumulateAndProcessAfterCommit(
				"requisitionIdsToUpdateTotalAmt",
				ImmutableSet.of(requisitionId),
				this::updateTotalAmtFromLines
		);
	}

	private void updateTotalAmtFromLines( final List<RequisitionId> requisitionIds)
	{
		for (final RequisitionId requisitionId : ImmutableSet.copyOf(requisitionIds))
		{
			requisitionService.updateTotalAmtFromLines(requisitionId);
		}
	}

}
