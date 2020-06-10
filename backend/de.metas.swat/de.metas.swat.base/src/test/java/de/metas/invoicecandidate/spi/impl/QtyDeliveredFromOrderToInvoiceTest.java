package de.metas.invoicecandidate.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import de.metas.acct.api.IProductAcctDAO;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.business.BusinessTestHelper;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.interfaces.I_C_BPartner;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.order.InvoiceRule;
import de.metas.order.invoicecandidate.C_OrderLine_Handler;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.user.UserRepository;
import de.metas.util.Services;

@ExtendWith(AdempiereTestWatcher.class)
public class QtyDeliveredFromOrderToInvoiceTest
{
	protected final Properties ctx = Env.getCtx();
	protected final String trxName = ITrx.TRXNAME_None;

	protected final C_OrderLine_Handler olHandler = new C_OrderLine_Handler();
	protected I_C_ILCandHandler handler;

	// task 07442
	private final ClientId clientId = ClientId.ofRepoId(2);
	private final OrgId orgId = OrgId.ofRepoId(2);
	private ProductId productId;
	private final ActivityId activityId = ActivityId.ofRepoId(4);

	protected I_C_Order order;
	protected I_C_OrderLine orderLine;

	protected I_M_InOut mInOut;
	protected I_M_InOutLine mInOutLine;

	protected I_C_BPartner bPartner;

	private UomId stockUomId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Client_ID, clientId.getRepoId());

		initHandlers();

		final I_C_UOM stockUom = newInstance(I_C_UOM.class);
		saveRecord(stockUom);
		stockUomId = UomId.ofRepoId(stockUom.getC_UOM_ID());

		final I_M_Product product = BusinessTestHelper.createProduct("product", stockUom);
		productId = ProductId.ofRepoId(product.getM_Product_ID());

		// initialize C_BPartner data
		{
			initC_BPartner();
		}
		// initialize C_Order data
		{
			initC_Order();
			initC_OrderLine();
		}

		// initialize M_InOut data
		{
			initM_InOut();
			initM_InOutLine();
		}

		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));

		mockTaxAndProductAcctServices();

		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
	}

	private void mockTaxAndProductAcctServices()
	{
		final IProductAcctDAO productAcctDAO = Mockito.mock(IProductAcctDAO.class);
		final ITaxBL taxBL = Mockito.mock(ITaxBL.class);

		Services.registerService(IProductAcctDAO.class, productAcctDAO);
		Services.registerService(ITaxBL.class, taxBL);

		Mockito.doReturn(activityId).when(productAcctDAO).retrieveActivityForAcct(clientId, orgId, productId);

		final Properties ctx = Env.getCtx();
		Mockito.doReturn(3).when(taxBL).getTax(
				ctx,
				order,
				(TaxCategoryId)null, // taxCategoryId
				orderLine.getM_Product_ID(),
				order.getDatePromised(),
				OrgId.ofRepoId(order.getAD_Org_ID()),
				WarehouseId.ofRepoIdOrNull(order.getM_Warehouse_ID()),
				order.getC_BPartner_Location_ID(),
				order.isSOTrx());
	}

	private void initC_BPartner()
	{
		bPartner = InterfaceWrapperHelper.create(ctx, I_C_BPartner.class, trxName);

		// ...

		InterfaceWrapperHelper.save(bPartner);
	}

	private void initHandlers()
	{
		handler = InterfaceWrapperHelper.create(ctx, I_C_ILCandHandler.class, trxName);

		// current DB structure for OLHandler
		handler.setC_ILCandHandler_ID(540001);
		handler.setClassname(C_OrderLine_Handler.class.getName());
		handler.setName("Auftragszeilen");
		handler.setTableName(I_C_OrderLine.Table_Name);

		InterfaceWrapperHelper.save(handler);

		// configure olHandler
		olHandler.setHandlerRecord(handler);
	}

	private void initC_Order()
	{
		order = InterfaceWrapperHelper.create(ctx, I_C_Order.class, trxName);
		order.setAD_Org_ID(orgId.getRepoId());
		order.setBill_BPartner_ID(bPartner.getC_BPartner_ID());
		order.setDocStatus(DocStatus.Completed.getCode());
		order.setInvoiceRule(InvoiceRule.AfterDelivery.getCode());
		InterfaceWrapperHelper.save(order);
	}

	private void initC_OrderLine()
	{
		orderLine = InterfaceWrapperHelper.create(ctx, I_C_OrderLine.class, trxName);

		orderLine.setAD_Org_ID(orgId.getRepoId());
		orderLine.setM_Product_ID(productId.getRepoId());

		final BigDecimal qty = new BigDecimal(13);
		orderLine.setQtyEntered(qty);
		orderLine.setQtyOrdered(qty);
		orderLine.setC_UOM_ID(stockUomId.getRepoId());
		orderLine.setQtyReserved(qty);

		// assume that the process (no direct access in decoupled mode) already set the orderLine qty
		orderLine.setQtyDelivered(qty);
		orderLine.setQtyInvoiced(qty);

		orderLine.setC_Order_ID(order.getC_Order_ID());

		InterfaceWrapperHelper.save(orderLine);
	}

	private void initM_InOut()
	{
		mInOut = InterfaceWrapperHelper.create(ctx, I_M_InOut.class, trxName);

		mInOut.setC_Order_ID(order.getC_Order_ID());

		mInOut.setDocStatus(IDocument.STATUS_Completed);
		mInOut.setDocAction(IDocument.ACTION_Close);

		InterfaceWrapperHelper.save(mInOut);
	}

	private void initM_InOutLine()
	{
		mInOutLine = InterfaceWrapperHelper.create(ctx, I_M_InOutLine.class, trxName);

		mInOutLine.setM_InOut_ID(mInOut.getM_InOut_ID());

		// link to C_OrderLine
		mInOutLine.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());

		// assume that it's true; our test case at the moment always has it true when dealing with qtyDelivered
		mInOutLine.setIsInvoiced(true);

		// set the orderLine's qty
		mInOutLine.setQtyEntered(orderLine.getQtyEntered());
		mInOutLine.setMovementQty(orderLine.getQtyEntered()); // TODO should use ReceiptSchedule for conversion
		mInOutLine.setC_UOM_ID(stockUomId.getRepoId());

		InterfaceWrapperHelper.save(mInOutLine);
	}

	@Test
	public void testQtyDelivered()
	{
		final List<I_C_Invoice_Candidate> invoiceCandidates = olHandler
				.createCandidatesFor(InvoiceCandidateGenerateRequest.of(olHandler, orderLine))
				.getC_Invoice_Candidates();
		assertThat(invoiceCandidates).as("There is 1 invoice candidate").hasSize(1);

		final I_C_Invoice_Candidate candidate = invoiceCandidates.get(0);
		assertThat(candidate.getInvoiceRule()).isNotNull(); // guard
		saveRecord(candidate);

		olHandler.setDeliveredData(candidate);
		assertThat(candidate.getQtyDelivered()).as("Invalid qty delivered").isEqualByComparingTo(orderLine.getQtyDelivered());
	}
}
