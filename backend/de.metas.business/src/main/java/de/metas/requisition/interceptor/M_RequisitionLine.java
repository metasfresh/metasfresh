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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.requisition.RequisitionRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Interceptor(I_M_RequisitionLine.class)
public class M_RequisitionLine
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final RequisitionRepository requisitionRepository;

	public M_RequisitionLine(@NonNull final RequisitionRepository requisitionRepository)
	{
		this.requisitionRepository = requisitionRepository;
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

	@ModelChange(timings = {ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_M_RequisitionLine.COLUMNNAME_LineNetAmt })
	public void updateRequisitionTotalLines(@NonNull final I_M_RequisitionLine line)
	{
		calculateTotalLines(line);
	}

	private void calculateTotalLines(@NonNull final I_M_RequisitionLine requisitionLine)
	{
		final I_M_Requisition requisition = requisitionLine.getM_Requisition();
		final int requisitionId = requisition.getM_Requisition_ID();
		final List<I_M_RequisitionLine> lines = requisitionRepository.getLinesByRequisitionId(requisitionId);

		BigDecimal calculatedTotalAmt = BigDecimal.ZERO;
		for (final I_M_RequisitionLine line : lines)
		{
			calculatedTotalAmt = calculatedTotalAmt.add(line.getLineNetAmt());
		}

		requisition.setTotalLines(calculatedTotalAmt);
		InterfaceWrapperHelper.save(requisition);

		CacheMgt.get().reset(CacheInvalidateMultiRequest.fromTableNameAndRecordId(I_M_Requisition.Table_Name, requisitionId));
	}

}
