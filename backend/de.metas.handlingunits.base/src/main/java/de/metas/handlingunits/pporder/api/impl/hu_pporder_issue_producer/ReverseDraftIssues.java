package de.metas.handlingunits.pporder.api.impl.hu_pporder_issue_producer;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderQtyDAO;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ReverseDraftIssues
{
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final transient IHUPPOrderQtyDAO huPPOrderQtyDAO = Services.get(IHUPPOrderQtyDAO.class);
	private final transient IPPOrderProductAttributeDAO ppOrderProductAttributeDAO = Services.get(IPPOrderProductAttributeDAO.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient SourceHUsService sourceHuService = SourceHUsService.get();

	public void reverseDraftIssue(@NonNull final I_PP_Order_Qty candidate)
	{
		if (candidate.isProcessed())
		{
			throw new HUException("Cannot reverse candidate because it's already processed: " + candidate);
		}

		final I_M_HU huToIssue = candidate.getM_HU();

		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(candidate);
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(contextProvider);

		huStatusBL.setHUStatus(huContext, huToIssue, X_M_HU.HUSTATUS_Active);
		handlingUnitsDAO.saveHU(huToIssue);

		// Delete PP_Order_ProductAttributes for issue candidate's HU
		ppOrderProductAttributeDAO.deleteForHU(candidate.getPP_Order_ID(), huToIssue.getM_HU_ID());

		//
		// Delete the candidate
		huPPOrderQtyDAO.delete(candidate);

		// Make sure the HU is marked as source
		sourceHuService.addSourceHuMarker(HuId.ofRepoId(huToIssue.getM_HU_ID()));
	}

}
