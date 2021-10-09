package de.metas.edi.model.validator;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.edi.api.IDesadvBL;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Interceptor(I_EDI_Desadv.class)
@Component
public class EDI_Desadv
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDesadvDelete(final I_EDI_Desadv desadv)
	{
		final List<I_EDI_DesadvLine> allLines = Services.get(IDesadvDAO.class).retrieveAllDesadvLines(desadv);
		for (final I_EDI_DesadvLine line : allLines)
		{
			InterfaceWrapperHelper.delete(line);
		}

		final List<I_M_InOut> allInOuts = Services.get(IDesadvDAO.class).retrieveAllInOuts(desadv);
		for (final I_M_InOut inOut : allInOuts)
		{
			inOut.setEDI_Desadv_ID(0);
			InterfaceWrapperHelper.save(inOut);
		}

		final List<I_C_Order> allIOrders = Services.get(IDesadvDAO.class).retrieveAllOrders(desadv);
		for (final I_C_Order order : allIOrders)
		{
			order.setEDI_Desadv_ID(0);
			InterfaceWrapperHelper.save(order);
		}
	}

	/**
	 * Update InOuts' export status when their DESADV is changed. Also updates the DESADV's processing and processed flags.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, //
			ifColumnsChanged = I_EDI_Desadv.COLUMNNAME_EDI_ExportStatus)
	public void onDesadvStatusChanged(final I_EDI_Desadv desadv)
	{
		// Services
		final String exportStatus = desadv.getEDI_ExportStatus();

		final boolean processing = I_EDI_Document.EDI_EXPORTSTATUS_Enqueued.equals(exportStatus) || I_EDI_Document.EDI_EXPORTSTATUS_SendingStarted.equals(exportStatus);
		desadv.setProcessing(processing);

		final boolean processed = I_EDI_Document.EDI_EXPORTSTATUS_Sent.equals(exportStatus) || I_EDI_Document.EDI_EXPORTSTATUS_DontSend.equals(exportStatus);
		desadv.setProcessed(processed);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_EDI_Desadv.COLUMNNAME_EDIErrorMsg })
	public void translateErrorMessage(final I_EDI_Desadv desadv)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final String errorMsgTrl = msgBL.parseTranslation(InterfaceWrapperHelper.getCtx(desadv), desadv.getEDIErrorMsg());
		desadv.setEDIErrorMsg(errorMsgTrl);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void setMinimumSumPercentage(final I_EDI_Desadv desadv)
	{
		// set the minimum sum percentage on each new desadv.
		// Even if the percentage will be changed via sys config, for this desadv it won't change
		Services.get(IDesadvBL.class).setMinimumPercentage(desadv);
	}
}
