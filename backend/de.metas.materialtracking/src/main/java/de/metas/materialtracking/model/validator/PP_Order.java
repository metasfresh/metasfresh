/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.materialtracking.model.validator;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.IIsInvoiceCandidateAware;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.IMaterialTrackingPPOrderDAO;
import de.metas.materialtracking.model.I_C_Invoice_Detail;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedInvoicingDAO;
import de.metas.materialtracking.qualityBasedInvoicing.impl.PPOrderQualityCalculator;
import de.metas.materialtracking.qualityBasedInvoicing.impl.PPOrderReportWriter;
import de.metas.materialtracking.spi.impl.listeners.PPOrderMaterialTrackingListener;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import java.util.List;

@Interceptor(I_PP_Order.class)
public class PP_Order
{
	public static final PP_Order instance = new PP_Order();

	private PP_Order()
	{
	}

	@Init
	public void init()
	{
		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		materialTrackingBL.addModelTrackingListener(I_PP_Order.Table_Name, PPOrderMaterialTrackingListener.instance);

		CopyRecordFactory.enableForTableName(I_PP_Order.Table_Name);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_CLOSE,
			ModelValidator.TIMING_AFTER_UNCLOSE })
	public void updateQualityFields(final I_PP_Order ppOrder, final int timing)
	{
		// used services
		final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final IQualityBasedInvoicingDAO qualityBasedInvoicingDAO = Services.get(IQualityBasedInvoicingDAO.class);

		// Applies only on Quality Inspection orders
		if (!materialTrackingPPOrderBL.isQualityInspection(ppOrder))
		{
			return;
		}

		// a quality inspection has at most one material tracking
		final I_M_Material_Tracking materialTracking = materialTrackingDAO.retrieveSingleMaterialTrackingForModel(ppOrder);
		if (materialTracking == null)
		{
			return;
		}

		// if the value was true for whatever reason, then we need to make it false now
		ppOrder.setIsInvoiceCandidate(false);

		final IMaterialTrackingDocuments materialTrackingDocuments = qualityBasedInvoicingDAO.retrieveMaterialTrackingDocuments(materialTracking);

		if (timing == ModelValidator.TIMING_AFTER_CLOSE)
		{
			materialTrackingDocuments.considerPPOrderAsClosed(ppOrder); // task 09657
		}
		else if (timing == ModelValidator.TIMING_AFTER_UNCLOSE)
		{
			materialTrackingDocuments.considerPPOrderAsNotClosed(ppOrder);
		}

		final PPOrderQualityCalculator calculator = new PPOrderQualityCalculator();
		calculator.update(materialTrackingDocuments);
	}

	/**
	 * Before a PP_Order is unclosed, this interceptor makes sure that the PP_Order is not associated with completed or closed invoices.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_UNCLOSE })
	public void verifyInvoiceDetailsBeforeUnclose(final I_PP_Order ppOrder)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_C_Invoice> invoices = queryBL
				.createQueryBuilder(I_C_Invoice_Detail.class, ppOrder)
				.addEqualsFilter(I_C_Invoice_Detail.COLUMN_PP_Order_ID, ppOrder.getPP_Order_ID())
				.andCollect(I_C_Invoice_Detail.COLUMN_C_InvoiceLine_ID)
				.andCollect(I_C_InvoiceLine.COLUMN_C_Invoice_ID)
				.addInArrayOrAllFilter(I_C_Invoice.COLUMNNAME_DocStatus, IDocument.STATUS_Completed, IDocument.STATUS_Closed)
				.orderBy().addColumn(I_C_Invoice.COLUMNNAME_DocumentNo).endOrderBy()
				.create()
				.list(I_C_Invoice.class);
		if (invoices.isEmpty())
		{
			return; // we are fine to unclose
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

		final StringBuilder sb = new StringBuilder("@Processed@ @C_Invoice_ID@:\n");
		for (final I_C_Invoice invoice : invoices)
		{
			sb
					.append(docTypeDAO.getById(invoice.getC_DocType_ID()).getName())
					.append(" ")
					.append(invoice.getDocumentNo());
		}

		final String msg = msgBL.translate(InterfaceWrapperHelper.getCtx(ppOrder), sb.toString());
		Loggables.addLog(msg);
		throw new AdempiereException(msg);
	}

	/**
	 * After a PP_Order was unclosed, this interceptor
	 * <ul>
	 * <li>sets <code>PP_Order.IsInvoiceCandidate='N'</code>
	 * <li>sets <code>M_InOutLine.IsInvoiceCandidate='N'</code> for the iols whose material was issued to the ppOrder.
	 * <li>schedules the given <code>ppOrder</code>'s invoice candidates to be updated/recreated.
	 * <li>deletes all <code>C_Invoice_Details</code> which reference it (somewhat redundant as the ICs will be recreated)
	 * </ul>
	 * All of this so that if the ppOrder is closed again, further invoice candidates can be created for it and also that while it is unclosed, no incorrect ICs will be created.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_UNCLOSE })
	public void afterUnclose(final I_PP_Order ppOrder)
	{
		final IMaterialTrackingPPOrderDAO materialTrackingPPOrderDAO = Services.get(IMaterialTrackingPPOrderDAO.class);
		final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);

		materialTrackingPPOrderDAO.deleteInvoiceDetails(ppOrder);

		final IIsInvoiceCandidateAware ppOrderIsInvoiceCandidateAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(ppOrder, IIsInvoiceCandidateAware.class);
		ppOrderIsInvoiceCandidateAware.setIsInvoiceCandidate(false);

		final List<I_M_InOutLine> issuedInOutLines = materialTrackingPPOrderBL.retrieveIssuedInOutLines(ppOrder);
		for (final I_M_InOutLine iol : issuedInOutLines)
		{
			final IIsInvoiceCandidateAware iolIsInvoiceCandidateAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(iol, IIsInvoiceCandidateAware.class);
			iolIsInvoiceCandidateAware.setIsInvoiceCandidate(false);
			InterfaceWrapperHelper.save(iol);
		}

		new PPOrderReportWriter(ppOrder).deleteReportLines();

		// task 09502 IT-2: make sure that existing invoice candidates are braught up to date
		final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
		invoiceCandidateHandlerBL.invalidateCandidatesFor(ppOrder);
	}

	/**
	 * If a PP_Order is deleted, then this method deletes the candidates which directly reference that line via <code>AD_Table_ID</code> and <code>Record_ID</code>.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteC_Invoice_Candidates(final I_PP_Order ppOrder)
	{
		Services.get(IInvoiceCandDAO.class).deleteAllReferencingInvoiceCandidates(ppOrder);
	}
}
