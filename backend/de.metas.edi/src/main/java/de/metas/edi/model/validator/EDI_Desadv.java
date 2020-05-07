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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.edi.api.IDesadvBL;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.i18n.IMsgBL;

@Validator(I_EDI_Desadv.class)
public class EDI_Desadv
{
	public static final Object INSTANCE = new EDI_Desadv();

	private EDI_Desadv()
	{
		super();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
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
			inOut.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_Pending); // consider the EDI file not sent

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
	 *
	 * @param desadv
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_EDI_Desadv.COLUMNNAME_EDI_ExportStatus })
	public void onDesadvStatusChanged(final I_EDI_Desadv desadv)
	{
		//
		// Services
		final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);

		final String exportStatus = desadv.getEDI_ExportStatus();

		final List<I_M_InOut> inOuts = desadvDAO.retrieveAllInOuts(desadv);
		for (final I_M_InOut inOut : inOuts)
		{
			inOut.setEDI_ExportStatus(exportStatus);
			InterfaceWrapperHelper.save(inOut);
		}

		final boolean processing = I_EDI_Document.EDI_EXPORTSTATUS_Enqueued.equals(exportStatus) || I_EDI_Document.EDI_EXPORTSTATUS_SendingStarted.equals(exportStatus);
		desadv.setProcessing(processing);

		final boolean processed = I_EDI_Document.EDI_EXPORTSTATUS_Sent.equals(exportStatus);
		desadv.setProcessed(processed);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_EDI_Desadv.COLUMNNAME_EDIErrorMsg })
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
