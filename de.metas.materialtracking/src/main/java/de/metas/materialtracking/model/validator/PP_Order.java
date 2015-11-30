package de.metas.materialtracking.model.validator;

/*
 * #%L
 * de.metas.materialtracking
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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.ModelValidator;
import org.compiere.process.DocAction;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.IMaterialTrackingPPOrderDAO;
import de.metas.materialtracking.model.I_C_Invoice_Detail;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedInvoicingDAO;
import de.metas.materialtracking.qualityBasedInvoicing.impl.PPOrderQualityCalculator;
import de.metas.materialtracking.spi.impl.listeners.PPOrderMaterialTrackingListener;

@Interceptor(I_PP_Order.class)
public class PP_Order
{
	@Init
	public void init()
	{
		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		materialTrackingBL.addModelTrackingListener(I_PP_Order.Table_Name, PPOrderMaterialTrackingListener.instance);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_CLOSE })
	public void updateQualityFieldsAfterClose(final I_PP_Order ppOrder)
	{
		// Applies only on Quality Inspection orders
		if (!Services.get(IMaterialTrackingPPOrderBL.class).isQualityInspection(ppOrder))
		{
			return;
		}

		// Applies only on those Quality Inspection orders which are linked to a material tracking
		final I_M_Material_Tracking materialTracking = Services.get(IMaterialTrackingDAO.class).retrieveMaterialTrackingForModel(ppOrder);
		if (materialTracking == null)
		{
			return;
		}

		final IQualityBasedInvoicingDAO qualityBasedInvoicingDAO = Services.get(IQualityBasedInvoicingDAO.class);
		final IMaterialTrackingDocuments materialTrackingDocuments = qualityBasedInvoicingDAO.retrieveMaterialTrackingDocuments(materialTracking);

		final PPOrderQualityCalculator calculator = new PPOrderQualityCalculator();
		calculator.update(materialTrackingDocuments);

		ppOrder.setIsInvoiceCandidate(false); // if the value was true for whatever reason, then we need to make it false now
	}

	/**
	 * Before a PP_Order is unclosed, this interceptor makes sure that the PP_Order is not associated with completed or closed invoices.
	 *
	 * @param ppOrder
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_UNCLOSE })
	public void verifyInvoiceDetailsBeforeUnclose(final I_PP_Order ppOrder)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_C_Invoice> invoices = queryBL
				.createQueryBuilder(I_C_Invoice_Detail.class, ppOrder)
				.addEqualsFilter(I_C_Invoice_Detail.COLUMN_PP_Order_ID, ppOrder.getPP_Order_ID())
				.andCollect(I_C_InvoiceLine.COLUMN_C_InvoiceLine_ID)
				.andCollect(I_C_Invoice.COLUMN_C_Invoice_ID)
				.addInArrayFilter(I_C_Invoice.COLUMNNAME_DocStatus, DocAction.STATUS_Completed, DocAction.STATUS_Closed)
				.orderBy().addColumn(I_C_Invoice.COLUMNNAME_DocumentNo).endOrderBy()
				.create()
				.list();
		if (invoices.isEmpty())
		{
			return; // we are fine to unclose
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final StringBuilder sb = new StringBuilder("@Processed@ @C_Invoice_ID@:\n");
		for (final I_C_Invoice invoice : invoices)
		{
			sb
					.append(invoice.getC_DocType().getName())
					.append(" ")
					.append(invoice.getDocumentNo());
		}

		final String msg = msgBL.translate(InterfaceWrapperHelper.getCtx(ppOrder), sb.toString());
		ILoggable.THREADLOCAL.getLoggable().addLog(msg);
		throw new AdempiereException(msg);
	}

	/**
	 * After a PP_Order was unclosed, this interceptor deletes all <code>C_Invoice_Details</code> which reference it and sets <code>PP_Order.IsInvoiceCandidate='N'</code>.<br>
	 * Both so that if the ppOrder is closed again, further invoice candidates can be created for it.
	 *
	 * @param ppOrder
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_UNCLOSE })
	public void deleteInvoiceDetailsAfterUnclose(final I_PP_Order ppOrder)
	{
		Services.get(IMaterialTrackingPPOrderDAO.class).deleteInvoiceDetails(ppOrder);

		ppOrder.setIsInvoiceCandidate(false);
	}
}
