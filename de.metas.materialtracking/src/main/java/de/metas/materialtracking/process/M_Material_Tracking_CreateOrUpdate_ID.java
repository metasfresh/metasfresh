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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.inout.service.IInOutDAO;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.api.IParams;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.process.SvrProcess;

import de.metas.adempiere.service.IAttributeSetInstanceBL;
import de.metas.adempiere.service.IOrderDAO;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingListener;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;

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
		extends SvrProcess
		implements ISvrProcessPrecondition
{
	// Services
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

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
			InterfaceWrapperHelper.save(orderLine);
			addLog(msgBL.parseTranslation(getCtx(), "@Processed@: @C_OrderLine_ID@ @Line@ " + p_Line));

			final List<I_C_Invoice_Candidate> icsToDelete = invoiceCandDAO.retrieveInvoiceCandidatesForOrderLine(orderLine);
			for (final I_C_Invoice_Candidate icToDelete : icsToDelete)
			{
				addLog(msgBL.parseTranslation(getCtx(), "@Deleted@: @C_Invoice_Candidate_ID@ " + icToDelete));
				InterfaceWrapperHelper.delete(icToDelete);
			}
		}

		//
		// inout lines
		final List<I_M_InOutLine> inOutLines = inOutDAO.retrieveLinesForOrderLine(orderLine, I_M_InOutLine.class);
		for (final I_M_InOutLine inOutLine : inOutLines)
		{
			// first update the material tracking ID, else the "old" link would be reestablished by some MV
			inOutLine.setM_Material_Tracking(materialTracking);
			InterfaceWrapperHelper.save(inOutLine);

			createUpdateASIAndLink(inOutLine, materialTracking);

			addLog(msgBL.parseTranslation(getCtx(), "@Processed@: @M_InOut_ID@ " + inOutLine.getM_InOut().getDocumentNo() + " @Line@ " + inOutLine.getLine()));

			final List<I_C_Invoice_Candidate> icsToDelete = invoiceCandDAO.retrieveInvoiceCandidatesForInOutLine(inOutLine);
			for (final I_C_Invoice_Candidate icToDelete : icsToDelete)
			{
				addLog(msgBL.parseTranslation(getCtx(), "@Deleted@: @C_Invoice_Candidate_ID@ " + icToDelete));
				InterfaceWrapperHelper.delete(icToDelete);
			}
		}

		// PP_Orders
		
		
//		"PP_Cost_Collector"
		
		return MSG_OK;
	}

	private void createUpdateASIAndLink(final Object model, final I_M_Material_Tracking materialTracking)
	{
		final I_M_AttributeSetInstance modelASI;
		final IAttributeSetInstanceAware modelASIAware = Services.get(IAttributeSetInstanceAwareFactoryService.class).createOrNull(model);
		Check.assumeNotNull(modelASIAware, "IAttributeSetInstanceAwareFactoryService.createOrNull() does not return null for {0}", model);

		if (modelASIAware.getM_AttributeSetInstance_ID() > 0)
		{
			modelASI = modelASIAware.getM_AttributeSetInstance();
		}
		else
		{
			modelASI = attributeSetInstanceBL.createASI(modelASIAware.getM_Product());
			modelASIAware.setM_AttributeSetInstance(modelASI);
		}

		final I_M_Attribute materialTrackingAttribute = attributeDAO.retrieveAttributeByValue(getCtx(), PARA_M_Material_Tracking_ID, I_M_Attribute.class);
		final I_M_AttributeInstance modelAI = attributeSetInstanceBL.getCreateAttributeInstance(modelASI, materialTrackingAttribute.getM_Attribute_ID());
		modelAI.setValue(Integer.toString(materialTracking.getM_Material_Tracking_ID()));
		InterfaceWrapperHelper.save(modelAI);

		// unlink from old material tracking (if any).
		materialTrackingBL.unlinkModelFromMaterialTracking(model);

		// link to new material tracking
		materialTrackingBL.linkModelToMaterialTracking(
				MTLinkRequest.builder()
						.setModel(model)
						.setMaterialTracking(materialTracking)
						.setParams(getParameterAsIParams()) // pass the parameters on. They contain HU specific infos which this class and module doesn't know or care about, but which are required to
															// happen when this process runs. Search for references to this process class name in the HU module to find out specifics.
						.setAssumeNotAlreadyAssigned(false) // there might already be an existing assignment, which we would want to override
						.setLoggable(this)
						.build());
	}

	/**
	 * @return <code>true</code> for orders and order lines with <code>SOTrx=N</code> (i.e. purchase order lines), <code>false</code> otherwise.
	 */
	@Override
	public boolean isPreconditionApplicable(final GridTab gridTab)
	{
		if (InterfaceWrapperHelper.isInstanceOf(gridTab, I_C_Order.class))
		{
			final I_C_Order order = InterfaceWrapperHelper.create(gridTab, I_C_Order.class);
			return !order.isSOTrx();
		}
		else if (InterfaceWrapperHelper.isInstanceOf(gridTab, I_C_OrderLine.class))
		{
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(gridTab, I_C_OrderLine.class);
			return !orderLine.getC_Order().isSOTrx();
		}

		return false;
	}
}
