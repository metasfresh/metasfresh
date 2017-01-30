package de.metas.materialtracking.process;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.api.IParams;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import de.metas.adempiere.service.IOrderDAO;
import de.metas.inout.IInOutDAO;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.materialtracking.IMaterialTrackingAttributeBL;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingListener;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

/**
 * Links a given purchase order line and its inOut lines to a given <code>M_Material_Tracking_ID</code>. <br>
 * This process might override pre-existing <code>M_Material_Tracking_ID</code> references.<br>
 * Note that part of this task is also a {@link IMaterialTrackingListener} which is implemented in
 * the HU module and which takes care of updating the <code>M_Material_Tracking_ID</code> HU-Attribute of the assigned HUs. Search for references to this class to find it.
 *
 *
 *
 * @author ts
 * @task http://dewiki908/mediawiki/index.php/09106_Material-Vorgangs-ID_nachtr%C3%A4glich_erfassen_%28101556035702%29
 */
public class M_Material_Tracking_CreateOrUpdate_ID
		extends JavaProcess
		implements IProcessPrecondition
{
	// Services

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

	private final IMaterialTrackingAttributeBL materialTrackingAttributeBL = Services.get(IMaterialTrackingAttributeBL.class);

	private static final String PARA_C_Order_ID = I_C_OrderLine.COLUMNNAME_C_Order_ID;
	private static final String PARA_Line = I_C_OrderLine.COLUMNNAME_Line;
	private static final String PARA_M_Material_Tracking_ID = I_M_Material_Tracking.COLUMNNAME_M_Material_Tracking_ID;

	//
	// Parameters

	// the order line (i.e. starting point) for our updates
	private int p_C_Order_ID;
	private int p_Line;

	// the material tracking to set
	private int p_Material_Tracking_ID;

	private I_C_OrderLine orderLine;

	@Override
	protected void prepare()
	{
		final IParams params = getParameterAsIParams();

		if (I_C_OrderLine.Table_Name.equals(getTableName()))
		{
			orderLine = getRecord(I_C_OrderLine.class);

			// for the sake of completeness:
			p_C_Order_ID = orderLine.getC_Order_ID();
			p_Line = orderLine.getLine();
		}
		else
		{
			if (I_C_Order.Table_Name.equals(getTableName()))
			{
				p_C_Order_ID = getRecord_ID();
				p_Line = params.getParameterAsInt(PARA_Line);
			}
			else
			{
				p_C_Order_ID = params.getParameterAsInt(PARA_C_Order_ID);
				p_Line = params.getParameterAsInt(PARA_Line);
			}

			final I_C_Order order = InterfaceWrapperHelper.create(getCtx(), p_C_Order_ID, I_C_Order.class, getTrxName());
			orderLine = orderDAO.retrieveOrderLine(order, p_Line, I_C_OrderLine.class);
		}

		p_Material_Tracking_ID = params.getParameterAsInt(PARA_M_Material_Tracking_ID);
	}

	@Override
	protected String doIt() throws Exception
	{
		doIt0();
		return MSG_OK;
	}

	private void doIt0()
	{
		final I_M_Material_Tracking materialTracking = InterfaceWrapperHelper.create(getCtx(), p_Material_Tracking_ID, I_M_Material_Tracking.class, getTrxName());
		if (materialTracking.getM_Product_ID() != orderLine.getM_Product_ID())
		{
			final String msg = "@C_OrderLine_ID@ @M_Product_ID@ (" + orderLine.getM_Product().getValue() + ") "
					+ "<> "
					+ "@M_Material_Tracking@ @M_Product_ID@ (" + materialTracking.getM_Product().getValue() + ")";
			final String msgTrl = msgBL.parseTranslation(getCtx(), msg);
			throw new AdempiereException(msgTrl);
		}

		//
		// order line
		{
			createUpdateASIAndLink(orderLine, materialTracking);
			addLog(msgBL.parseTranslation(getCtx(), "@Processed@: @C_OrderLine_ID@ @Line@ " + p_Line));

			final List<I_C_Invoice_Candidate> icsToDelete = InterfaceWrapperHelper.createList(
					invoiceCandDAO.retrieveInvoiceCandidatesForOrderLine(orderLine),
					I_C_Invoice_Candidate.class);
			deleteOrUpdate(icsToDelete, materialTracking);
		}
		//
		// receipt schedules
		{
			final I_M_ReceiptSchedule receiptSchedule = Services.get(IReceiptScheduleDAO.class).retrieveForRecord(orderLine);
			materialTrackingAttributeBL.createOrUpdateMaterialTrackingASI(receiptSchedule, materialTracking);
			InterfaceWrapperHelper.save(receiptSchedule); // note that we have a M_ReceiptSchedule model interceptor in the HU module, which takes care of the hu-attributes
		}

		//
		// inout lines.
		// note that we also want to update the respective package lines, but those don't reference 'orderLine'
		final List<I_M_InOutLine> inOutLines = inOutDAO.retrieveLinesForOrderLine(orderLine, I_M_InOutLine.class);
		for (final I_M_InOutLine inOutLine : inOutLines)
		{
			// update the packaging line
			if (inOutLine.getM_PackingMaterial_InOutLine_ID() > 0)
			{
				updateInOutLine(inOutLine.getM_PackingMaterial_InOutLine(), materialTracking);
			}
			else
			{
				// fallback: if we don't have an explicitly referenced package line,
				// then iterate all package lines and change those which have same tracking ID as the current inOut line.
				// note: i don't think there can be more than one such line, but if there is, it shall not be this processe's problem.
				final List<I_M_InOutLine> packageInOutLines = Services.get(IQueryBL.class).createQueryBuilder(I_M_InOutLine.class, orderLine)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, inOutLine.getM_InOut_ID())
						.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_Material_Tracking_ID, inOutLine.getM_Material_Tracking_ID())
						.addEqualsFilter(I_M_InOutLine.COLUMNNAME_IsPackagingMaterial, true)
						.create()
						.list();
				for (final I_M_InOutLine packageInOutLine : packageInOutLines)
				{
					updateInOutLine(packageInOutLine, materialTracking);
				}
			}

			// update the actual inout line
			updateInOutLine(inOutLine, materialTracking);
		}
	}

	/**
	 * Deletes if not yet invoiced, just updates the M_MAterialTRracking_ID otherwise.
	 *
	 * @param ics
	 * @param materialTracking
	 */
	private void deleteOrUpdate(final List<I_C_Invoice_Candidate> ics,
			final I_M_Material_Tracking materialTracking)
	{
		for (final I_C_Invoice_Candidate ic : ics)
		{
			if (ic.isProcessed() || ic.getQtyInvoiced().signum() != 0)
			{
				ic.setM_Material_Tracking(materialTracking);
				InterfaceWrapperHelper.save(ic);
				addLog(msgBL.parseTranslation(getCtx(), "@Updated@: @C_Invoice_Candidate_ID@ " + ic));
			}
			else
			{
				InterfaceWrapperHelper.delete(ic);
				addLog(msgBL.parseTranslation(getCtx(), "@Deleted@: @C_Invoice_Candidate_ID@ " + ic));
			}
		}
	}

	private void updateInOutLine(final org.compiere.model.I_M_InOutLine inOutLine, final I_M_Material_Tracking materialTracking)
	{
		createUpdateASIAndLink(inOutLine, materialTracking);

		addLog(msgBL.parseTranslation(getCtx(), "@Processed@: @M_InOut_ID@ " + inOutLine.getM_InOut().getDocumentNo() + " @Line@ " + inOutLine.getLine()));

		final List<I_C_Invoice_Candidate> ics = InterfaceWrapperHelper.createList(
				invoiceCandDAO.retrieveInvoiceCandidatesForInOutLine(inOutLine),
				I_C_Invoice_Candidate.class);
		deleteOrUpdate(ics, materialTracking);
	}

	private void createUpdateASIAndLink(final Object documentLine,
			final I_M_Material_Tracking materialTracking)
	{
		materialTrackingAttributeBL.createOrUpdateMaterialTrackingASI(documentLine, materialTracking);
		InterfaceWrapperHelper.save(documentLine);

		materialTrackingBL.linkModelToMaterialTracking(
				MTLinkRequest.builder()
						.setModel(documentLine)
						.setMaterialTracking(materialTracking)

						// pass the process parameters on. They contain HU specific infos which this class and module doesn't know or care about, but which are required to
						// happen when this process runs. Search for references to this process class name in the HU module to find out specifics.
						.setParams(getParameterAsIParams())

						// unlink from another material tracking if necessary
						.setAssumeNotAlreadyAssigned(false)
						.build());
	}

	/**
	 * @return <code>true</code> for orders and order lines with <code>SOTrx=N</code> (i.e. purchase order lines), <code>false</code> otherwise.
	 */
	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (I_C_Order.Table_Name.equals(context.getTableName()))
		{
			final I_C_Order order = context.getSelectedModel(I_C_Order.class);
			return ProcessPreconditionsResolution.acceptIf(!order.isSOTrx());
		}
		else if (I_C_OrderLine.Table_Name.equals(context.getTableName()))
		{
			final I_C_OrderLine orderLine = context.getSelectedModel(I_C_OrderLine.class);
			return ProcessPreconditionsResolution.acceptIf(!orderLine.getC_Order().isSOTrx());
		}

		return ProcessPreconditionsResolution.reject();
	}
}
