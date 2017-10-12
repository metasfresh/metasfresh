package de.metas.ui.web.pporder.process;

import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.eevolution.api.IPPOrderDAO;

import de.metas.fresh.ordercheckup.OrderCheckupBarcode;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.pporder.PPOrderConstants;
import de.metas.ui.web.process.adprocess.WebuiProcess;
import de.metas.ui.web.window.datatypes.PanelLayoutType;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Accepts an {@link OrderCheckupBarcode}, searches for coresponding manufacturing order and then opens Issue/Receipt panel for it.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh-webui-api/issues/280
 */
@WebuiProcess(layoutType = PanelLayoutType.SingleOverlayField)
public class WEBUI_PP_Order_IssueReceipt_BarcodeLauncher extends JavaProcess
{
	@Param(parameterName = "Barcode", mandatory = true)
	private String p_Barcode;

	@Override
	protected String doIt() throws Exception
	{
		final int orderLineId = OrderCheckupBarcode.fromBarcodeString(p_Barcode).getC_OrderLine_ID();
		final int ppOrderId = Services.get(IPPOrderDAO.class).retrievePPOrderIdByOrderLineId(orderLineId);
		if (ppOrderId <= 0)
		{
			throw new EntityNotFoundException("@NotFound@ @PP_Order_ID@");
		}

		final TableRecordReference ppOrderRef = TableRecordReference.of(org.eevolution.model.I_PP_Order.Table_Name, ppOrderId);
		getResult().setRecordToOpen(ppOrderRef, PPOrderConstants.AD_WINDOW_ID_IssueReceipt.toInt(), OpenTarget.GridView);
		return MSG_OK;
	}
}
