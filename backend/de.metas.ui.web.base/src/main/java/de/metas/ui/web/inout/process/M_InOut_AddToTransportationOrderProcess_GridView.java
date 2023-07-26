/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.ui.web.inout.process;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import com.google.common.collect.ImmutableList;

import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;

@SuppressWarnings("NullableProblems")
public class M_InOut_AddToTransportationOrderProcess_GridView extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Param(parameterName = "M_ShipperTransportation_ID", mandatory = true)
	private I_M_ShipperTransportation transportationOrder;

	public static final AdMessageKey ALL_SELECTED_SHIPMENTS_SHOULD_BE_COMPLETED_MSG = AdMessageKey.of("de.metas.ui.web.inout.process.M_InOut_AddToTransportationOrderProcess.AllSelectedShipmentsShouldBeCompleted");

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final List<I_M_InOut> inOuts = getSelectedInOuts();
		for (final I_M_InOut inOut : inOuts)
		{
			final DocStatus docStatus = DocStatus.ofCode(inOut.getDocStatus());
			if (!docStatus.isCompleted())
			{
				return ProcessPreconditionsResolution.reject(Services.get(IMsgBL.class).getTranslatableMsgText(ALL_SELECTED_SHIPMENTS_SHOULD_BE_COMPLETED_MSG));
			}
		}

		return ProcessPreconditionsResolution.accept();
	}

	private ImmutableList<I_M_InOut> getSelectedInOuts()
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		return getSelectedRowIds()
				.stream()
				.map(rowId -> inOutDAO.getById(InOutId.ofRepoId(rowId.toInt()), I_M_InOut.class))
				.collect(GuavaCollectors.toImmutableList());
	}

	@Override
	protected String doIt() throws Exception
	{
		final DocStatus docStatus = DocStatus.ofCode(transportationOrder.getDocStatus());
		if (docStatus.isCompleted())
		{
			// this error should not be thrown since we have AD_Val_Rule for the parameter
			throw new AdempiereException("Transportation Order should not be closed");
		}

		final ImmutableList<InOutId> inOutIds = getSelectedInOuts()
				.stream()
				.map(it -> InOutId.ofRepoId(it.getM_InOut_ID()))
				.collect(GuavaCollectors.toImmutableList());

		final InOutToTransportationOrderService service = SpringContextHolder.instance.getBean(InOutToTransportationOrderService.class);
		service.addShipmentsToTransportationOrder(ShipperTransportationId.ofRepoId(transportationOrder.getM_ShipperTransportation_ID()), inOutIds);

		return MSG_OK;
	}
}
