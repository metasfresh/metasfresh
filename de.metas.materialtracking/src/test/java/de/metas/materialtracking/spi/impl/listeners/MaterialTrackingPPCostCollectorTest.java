package de.metas.materialtracking.spi.impl.listeners;

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


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_Cost_Collector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.engine.impl.PlainDocumentBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.impl.MaterialTrackingDAO;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;
import de.metas.materialtracking.model.validator.Main;

/**
 * 
 * @author cg
 *
 */
public class MaterialTrackingPPCostCollectorTest
{
	protected IContextAware context;
	private PlainDocumentBL docActionBL;
	private MaterialTrackingDAO materialTrackingDAO;

	@Before
	public final void init()
	{
		AdempiereTestHelper.get().init();
		this.context = new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_None);

		this.docActionBL = (PlainDocumentBL)Services.get(IDocumentBL.class);

		this.materialTrackingDAO = (MaterialTrackingDAO)Services.get(IMaterialTrackingDAO.class);

		docActionBL.setDefaultProcessInterceptor(PlainDocumentBL.PROCESSINTERCEPTOR_CompleteDirectly);

		final I_AD_Client adClient = InterfaceWrapperHelper.newInstance(I_AD_Client.class, context);
		adClient.setValue("Test");
		adClient.setName("Test");
		adClient.setAD_Language("de_DE");
		InterfaceWrapperHelper.save(adClient);

		// Make sure tracking validator interceptor is registered
		final Main trackingValidator = new Main();
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(trackingValidator, adClient);

	}

	@Test
	public void testQtyIssuedSum()
	{
		// prepare data: create order and link it manually
		final I_PP_Order ppOrder = createPP_Order(null);
		linkPPOrder(ppOrder);

		final I_PP_Cost_Collector ppCostColector1 = createPP_CostColector(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, ppOrder, BigDecimal.valueOf(10));
		final I_PP_Cost_Collector ppCostColector2 = createPP_CostColector(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, ppOrder, BigDecimal.valueOf(20));
		final I_PP_Cost_Collector ppCostColector3 = createPP_CostColector(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, ppOrder, BigDecimal.valueOf(30));

		this.docActionBL.processEx(ppCostColector1, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		this.docActionBL.processEx(ppCostColector2, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		this.docActionBL.processEx(ppCostColector3, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		final I_M_Material_Tracking materialTracking1 = Services.get(IMaterialTrackingDAO.class).retrieveMaterialTrackingForModel(ppCostColector1.getPP_Order());
		final I_M_Material_Tracking materialTracking2 = Services.get(IMaterialTrackingDAO.class).retrieveMaterialTrackingForModel(ppCostColector2.getPP_Order());
		final I_M_Material_Tracking materialTracking3 = Services.get(IMaterialTrackingDAO.class).retrieveMaterialTrackingForModel(ppCostColector3.getPP_Order());
		assertEquals("Not expected value!", materialTracking1, materialTracking2);
		assertEquals("Not expected value!", materialTracking2, materialTracking3);
		assertEquals("Not expected value!", materialTracking1, materialTracking3);
		
		final BigDecimal sum = ppCostColector1.getMovementQty().add(ppCostColector2.getMovementQty()).add(ppCostColector3.getMovementQty());
		assertEquals("Not expected value!", sum, materialTracking1.getQtyIssued());
	}

	@Test
	public void testQtyIssuedPerPPOrder()
	{
		// prepare data: create order and link it manually
		final I_PP_Order ppOrder1 = createPP_Order(null);
		final I_PP_Order ppOrder2 = createPP_Order(null);
		linkPPOrder(ppOrder1);
		linkPPOrder(ppOrder2);

		final I_PP_Cost_Collector ppCostColector1 = createPP_CostColector(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, ppOrder1, BigDecimal.valueOf(10));
		final I_PP_Cost_Collector ppCostColector2 = createPP_CostColector(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, ppOrder2, BigDecimal.valueOf(20));
		final I_PP_Cost_Collector ppCostColector3 = createPP_CostColector(X_PP_Cost_Collector.COSTCOLLECTORTYPE_ComponentIssue, ppOrder2, BigDecimal.valueOf(30));

		this.docActionBL.processEx(ppCostColector1, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		this.docActionBL.processEx(ppCostColector2, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		
		this.docActionBL.processEx(ppCostColector3, IDocument.ACTION_Complete, IDocument.STATUS_Completed);


		final I_M_Material_Tracking materialTracking1 = Services.get(IMaterialTrackingDAO.class).retrieveMaterialTrackingForModel(ppCostColector1.getPP_Order());
		final I_M_Material_Tracking materialTracking2 = Services.get(IMaterialTrackingDAO.class).retrieveMaterialTrackingForModel(ppCostColector2.getPP_Order());
		final I_M_Material_Tracking materialTracking3 = Services.get(IMaterialTrackingDAO.class).retrieveMaterialTrackingForModel(ppCostColector3.getPP_Order());
		Assert.assertNotEquals("Should be 2 different material trackings", materialTracking1, materialTracking2);
		assertEquals("Should be same materila tracking!", materialTracking2, materialTracking3);
		
		assertEquals("Not expected value!", ppCostColector1.getMovementQty(), materialTracking1.getQtyIssued());
		
		final BigDecimal sum = ppCostColector2.getMovementQty().add(ppCostColector3.getMovementQty());
		assertEquals("Not expected value!", sum, materialTracking2.getQtyIssued());
		
		final I_M_Material_Tracking_Ref ref1 = materialTrackingDAO.retrieveMaterialTrackingRefForModel(ppOrder1);
		final I_M_Material_Tracking_Ref ref2 = materialTrackingDAO.retrieveMaterialTrackingRefForModel(ppOrder2);
		
		assertEquals("Not expected value!", ppCostColector1.getMovementQty(), ref1.getQtyIssued());
		
		final BigDecimal sumPerOrder = ppCostColector2.getMovementQty().add(ppCostColector3.getMovementQty());
		assertEquals("Not expected value!", sumPerOrder, ref2.getQtyIssued());
	}
	
	
	private I_PP_Order createPP_Order(final String orderType)
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, context);
		ppOrder.setOrderType(orderType);
		InterfaceWrapperHelper.save(ppOrder);

		return ppOrder;
	}

	private I_M_Material_Tracking linkPPOrder(final I_PP_Order ppOrder)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.newInstance(I_M_Attribute.class, context);
		attribute.setValue("M_Material_Tracking_ID");
		InterfaceWrapperHelper.save(attribute);

		final I_M_AttributeValue attributeValue = InterfaceWrapperHelper.newInstance(I_M_AttributeValue.class, context);
		attributeValue.setAD_Org_ID(attribute.getAD_Org_ID());
		attributeValue.setM_Attribute(attribute);
		InterfaceWrapperHelper.save(attributeValue);

		final I_M_Material_Tracking materialTracking = InterfaceWrapperHelper.newInstance(I_M_Material_Tracking.class, context);
		materialTracking.setM_AttributeValue(attributeValue);
		InterfaceWrapperHelper.save(materialTracking);

		final I_M_Material_Tracking_Ref ref = materialTrackingDAO.createMaterialTrackingRefNoSave(materialTracking, ppOrder);
		ref.setIsQualityInspectionDoc(true);
		InterfaceWrapperHelper.save(ref);

		return materialTracking;
	}

	private I_PP_Cost_Collector createPP_CostColector(final String type, final I_PP_Order ppOrder, final BigDecimal movementQty)
	{
		final I_PP_Cost_Collector ppCostColector = InterfaceWrapperHelper.newInstance(I_PP_Cost_Collector.class, context);
		ppCostColector.setCostCollectorType(type);
		ppCostColector.setPP_Order(ppOrder);
		ppCostColector.setMovementQty(movementQty);
		InterfaceWrapperHelper.save(ppCostColector);

		return ppCostColector;
	}

}
