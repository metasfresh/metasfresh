package de.metas.invoicecandidate.spi.impl;

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.business.BusinessTestHelper;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.OrderLineDimensionFactory;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.inoutcandidate.document.dimension.ReceiptScheduleDimensionFactoryTestWrapper;
import de.metas.invoicecandidate.document.dimension.InvoiceCandidateDimensionFactory;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.lang.SOTrx;
import de.metas.lock.api.LockOwner;
import de.metas.order.InvoiceRule;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.order.invoicecandidate.C_OrderLine_Handler;
import de.metas.order.invoicecandidate.OrderLineHandlerExtension;
import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("FieldCanBeLocal")
@ExtendWith(AdempiereTestWatcher.class)
public class QtyDeliveredFromOrderToInvoiceTest
{
	private final Properties ctx = Env.getCtx();
	private final String trxName = ITrx.TRXNAME_None;

	private C_OrderLine_Handler olHandler;
	private I_C_ILCandHandler handler;

	// task 07442
	private final ClientId clientId = ClientId.ofRepoId(2);
	private final OrgId orgId = OrgId.ofRepoId(2);
	private ProductId productId;
	private final ActivityId activityId = ActivityId.ofRepoId(4);

	private I_C_Order order;
	private I_C_OrderLine orderLine;

	private I_M_InOut mInOut;
	private I_M_InOutLine mInOutLine;

	private BPartnerLocationId bpartnerAndLocationId;

	private UomId stockUomId;

	private I_C_DocType docType;

	private final DocTypeId docTypeId = DocTypeId.ofRepoId(1000016);

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Client_ID, clientId.getRepoId());

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new OrderLineDimensionFactory());
		dimensionFactories.add(new ReceiptScheduleDimensionFactoryTestWrapper());
		dimensionFactories.add(new InvoiceCandidateDimensionFactory());

		final DimensionService dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(dimensionService);

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		SpringContextHolder.registerJUnitBean(new OrderEmailPropagationSysConfigRepository(sysConfigBL));
		SpringContextHolder.registerJUnitBean(OrderLineHandlerExtension.class, Mockito.mock(OrderLineHandlerExtension.class));

		olHandler = new C_OrderLine_Handler();
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

		initC_DocType();
		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));

		mockTaxAndProductAcctServices();

		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());

	}

	private void mockTaxAndProductAcctServices()
	{
		final IProductActivityProvider productActivityProvider = Mockito.mock(IProductActivityProvider.class);
		final IDocTypeBL docTypeBL = Mockito.mock(IDocTypeBL.class);
		final ITaxBL taxBL = Mockito.mock(ITaxBL.class);

		Services.registerService(IProductActivityProvider.class, productActivityProvider);
		Services.registerService(IDocTypeBL.class, docTypeBL);
		Services.registerService(ITaxBL.class, taxBL);

		Mockito.doReturn(activityId).when(productActivityProvider).getActivityForAcct(clientId, orgId, productId);
		Mockito.doReturn(docType).when(docTypeBL).getById(docTypeId);

		final Properties ctx = Env.getCtx();
		Mockito
				.when(taxBL.getTaxNotNull(
						order,
						null, // taxCategoryId
						orderLine.getM_Product_ID(),
						order.getDatePromised(),
						OrgId.ofRepoId(order.getAD_Org_ID()),
						WarehouseId.ofRepoIdOrNull(order.getM_Warehouse_ID()),
						BPartnerLocationAndCaptureId.ofRepoId(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID(), order.getC_BPartner_Location_Value_ID()),
						SOTrx.ofBoolean(order.isSOTrx()),
						null))//vatCodeId
				.thenReturn(TaxId.ofRepoId(3));
	}

	private void initC_BPartner()
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		save(bpartner);

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		save(bpLocation);

		bpartnerAndLocationId = BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
	}

	private void initHandlers()
	{
		handler = create(ctx, I_C_ILCandHandler.class, trxName);

		// current DB structure for OLHandler
		handler.setC_ILCandHandler_ID(540001);
		handler.setClassname(C_OrderLine_Handler.class.getName());
		handler.setName("Auftragszeilen");
		handler.setTableName(I_C_OrderLine.Table_Name);

		save(handler);

		// configure olHandler
		olHandler.setHandlerRecord(handler);
	}

	private void initC_DocType()
	{
		docType = create(ctx, I_C_DocType.class, trxName);
		docType.setAD_Org_ID(orgId.getRepoId());
		docType.setC_DocType_ID(1000016);
		save(docType);
	}

	private void initC_Order()
	{
		order = create(ctx, I_C_Order.class, trxName);
		order.setAD_Org_ID(orgId.getRepoId());

		order.setC_BPartner_ID(bpartnerAndLocationId.getBpartnerId().getRepoId());
		order.setC_BPartner_Location_ID(bpartnerAndLocationId.getRepoId());

		order.setBill_BPartner_ID(bpartnerAndLocationId.getBpartnerId().getRepoId());
		order.setBill_Location_ID(bpartnerAndLocationId.getRepoId());

		order.setDocStatus(DocStatus.Completed.getCode());
		order.setInvoiceRule(InvoiceRule.AfterDelivery.getCode());
		order.setC_DocTypeTarget_ID(1000016);
		order.setDatePromised(Timestamp.valueOf("2021-11-30 00:00:00"));
		order.setC_Currency_ID(10);
		order.setM_PricingSystem_ID(20);
		order.setC_PaymentTerm_ID(100);
		save(order);
	}

	private void initC_OrderLine()
	{
		orderLine = create(ctx, I_C_OrderLine.class, trxName);

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
		
		orderLine.setC_Tax_ID(createTax().getRepoId());

		save(orderLine);
	}

	private TaxId createTax()
	{
		final I_C_Tax tax = newInstance(I_C_Tax.class);
		saveRecord(tax);

		return TaxId.ofRepoId(tax.getC_Tax_ID());
	}

	private void initM_InOut()
	{
		mInOut = create(ctx, I_M_InOut.class, trxName);

		mInOut.setC_Order_ID(order.getC_Order_ID());

		mInOut.setDocStatus(IDocument.STATUS_Completed);
		mInOut.setDocAction(IDocument.ACTION_Close);

		save(mInOut);
	}

	private void initM_InOutLine()
	{
		mInOutLine = create(ctx, I_M_InOutLine.class, trxName);

		mInOutLine.setM_InOut_ID(mInOut.getM_InOut_ID());

		// link to C_OrderLine
		mInOutLine.setC_Order_ID(orderLine.getC_Order_ID());
		mInOutLine.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());

		// assume that it's true; our test case at the moment always has it true when dealing with qtyDelivered
		mInOutLine.setIsInvoiced(true);

		// set the orderLine's qty
		mInOutLine.setQtyEntered(orderLine.getQtyEntered());
		mInOutLine.setMovementQty(orderLine.getQtyEntered()); // TODO should use ReceiptSchedule for conversion
		mInOutLine.setC_UOM_ID(stockUomId.getRepoId());

		save(mInOutLine);
	}

	@Test
	public void testQtyDelivered()
	{
		final LockOwner lockOwner = LockOwner.newOwner(getClass().getSimpleName() + "#generateInvoiceCandidates");

		final List<I_C_Invoice_Candidate> invoiceCandidates = olHandler
				.createCandidatesFor(InvoiceCandidateGenerateRequest.of(olHandler, orderLine, lockOwner))
				.getC_Invoice_Candidates();
		assertThat(invoiceCandidates).as("There is 1 invoice candidate").hasSize(1);

		final I_C_Invoice_Candidate candidate = invoiceCandidates.get(0);
		assertThat(candidate.getInvoiceRule()).isNotNull(); // guard
		saveRecord(candidate);

		olHandler.setDeliveredData(candidate);
		assertThat(candidate.getQtyDelivered()).as("Invalid qty delivered").isEqualByComparingTo(orderLine.getQtyDelivered());
	}
}
