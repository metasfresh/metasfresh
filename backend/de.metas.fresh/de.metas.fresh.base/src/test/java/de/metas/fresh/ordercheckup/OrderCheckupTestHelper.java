package de.metas.fresh.ordercheckup;

import de.metas.adempiere.model.I_M_Product;
import de.metas.document.archive.api.ArchiveFileNameService;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_ReportLine;
import de.metas.fresh.ordercheckup.printing.spi.impl.OrderCheckupPrintingQueueHandler;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.organization.OrgId;
import de.metas.printing.HardwarePrinterRepository;
import de.metas.printing.PrintOutputFacade;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.model.I_AD_Archive;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.I_C_Printing_Queue_Recipient;
import de.metas.printing.model.validator.AD_Archive;
import de.metas.printing.printingdata.PrintingDataFactory;
import de.metas.printing.printingdata.PrintingDataToPDFFileStorer;
import de.metas.printing.spi.impl.ExternalSystemsPrintingNotifier;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_AD_Workflow;
import org.compiere.model.X_S_Resource;
import org.compiere.util.Env;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderCheckupTestHelper
{
	private Properties ctx;
	private IProductPlanningDAO productPlanningDAO;
	private PrintOutputFacade printOutputFacade;

	public void init()
	{
		AdempiereTestHelper.get().init();
		ctx = Env.getCtx();
		productPlanningDAO = Services.get(IProductPlanningDAO.class);

		Services.get(IPrintingQueueBL.class).registerHandler(OrderCheckupPrintingQueueHandler.instance);

		SpringContextHolder.registerJUnitBean(new ExternalSystemsPrintingNotifier(new ArrayList<>()));

		printOutputFacade = new PrintOutputFacade(
				new PrintingDataFactory(new HardwarePrinterRepository(), new ArchiveFileNameService()),
				new PrintingDataToPDFFileStorer());
	}

	public Masterdata createMasterdata()
	{
		return new Masterdata(this);
	}

	public I_AD_User createAD_User(final String name)
	{
		final I_AD_User user = InterfaceWrapperHelper.create(ctx, I_AD_User.class, ITrx.TRXNAME_None);
		user.setName(name);
		InterfaceWrapperHelper.save(user);
		return user;
	}

	public I_S_Resource createPlant(final String name, final I_AD_User responsibleUser)
	{
		final I_S_Resource plant = InterfaceWrapperHelper.create(ctx, I_S_Resource.class, ITrx.TRXNAME_None);
		plant.setIsManufacturingResource(true);
		plant.setManufacturingResourceType(X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant);
		plant.setValue(name);
		plant.setName(name);
		plant.setAD_User_ID(responsibleUser.getAD_User_ID());
		InterfaceWrapperHelper.save(plant);
		return plant;
	}

	public I_M_Warehouse createWarehouse(final String name, final I_S_Resource plant)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.create(ctx, I_M_Warehouse.class, ITrx.TRXNAME_None);
		warehouse.setValue(name);
		warehouse.setName(name);
		warehouse.setPP_Plant(plant);
		InterfaceWrapperHelper.save(warehouse);
		return warehouse;
	}

	public I_M_Product createProduct(final String name, final I_M_Warehouse mfgWarehouse, final I_AD_User responsibleUser)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		product.setValue(name);
		product.setName(name);
		InterfaceWrapperHelper.save(product);

		createManufacturingProductPlanning(product, mfgWarehouse, responsibleUser);

		return product;
	}

	public void createManufacturingProductPlanning(final I_M_Product product, final I_M_Warehouse warehouse, final I_AD_User responsibleUser)
	{
		final I_AD_Workflow workflow = createManufacturingRouting(responsibleUser);

		productPlanningDAO.save(ProductPlanning.builder()
				.productId(ProductId.ofRepoId(product.getM_Product_ID()))
				.warehouseId(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()))
				.orgId(OrgId.ofRepoId(warehouse.getAD_Org_ID()))
				.plantId(ResourceId.ofRepoIdOrNull(warehouse.getPP_Plant_ID()))
				.isManufactured(true)
				.workflowId(PPRoutingId.ofRepoId(workflow.getAD_Workflow_ID()))
				.build());
	}

	private I_AD_Workflow createManufacturingRouting(final I_AD_User responsibleUser)
	{
		final I_AD_Workflow workflow = newInstance(I_AD_Workflow.class);
		workflow.setValue("wf");
		workflow.setAD_User_InCharge_ID(responsibleUser.getAD_User_ID());
		workflow.setDurationUnit(X_AD_Workflow.DURATIONUNIT_Hour);
		save(workflow);

		final I_AD_WF_Node wfNode = newInstance(I_AD_WF_Node.class);
		wfNode.setValue("node1");
		wfNode.setName("node1");
		wfNode.setAD_Workflow_ID(workflow.getAD_Workflow_ID());
		wfNode.setS_Resource_ID(123);
		save(wfNode);

		workflow.setAD_WF_Node_ID(wfNode.getAD_WF_Node_ID());
		save(workflow);

		return workflow;
	}

	public I_C_Order createSalesOrder(final I_M_Warehouse warehouse)
	{
		final I_C_Order order = InterfaceWrapperHelper.create(ctx, I_C_Order.class, ITrx.TRXNAME_None);
		order.setIsSOTrx(true);
		order.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		InterfaceWrapperHelper.save(order);
		return order;
	}

	public I_C_OrderLine createOrderLine(@NonNull final I_C_Order order, @NonNull final I_M_Product product)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(ctx, I_C_OrderLine.class, ITrx.TRXNAME_None);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setM_Product_ID(product.getM_Product_ID());
		InterfaceWrapperHelper.save(orderLine);
		return orderLine;
	}

	public I_C_Order_MFGWarehouse_Report retrieveReport(final String documentType, final I_M_Warehouse warehouse, final I_S_Resource plant)
	{
		final Integer warehouseId = warehouse == null ? null : warehouse.getM_Warehouse_ID();
		final Integer plantId = plant == null ? null : plant.getS_Resource_ID();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order_MFGWarehouse_Report.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Order_MFGWarehouse_Report.COLUMN_DocumentType, documentType)
				.addEqualsFilter(I_C_Order_MFGWarehouse_Report.COLUMN_M_Warehouse_ID, warehouseId)
				.addEqualsFilter(I_C_Order_MFGWarehouse_Report.COLUMN_PP_Plant_ID, plantId)
				.create()
				.firstOnly(I_C_Order_MFGWarehouse_Report.class);
	}

	public void assertReportHasOrderLines(final I_C_Order_MFGWarehouse_Report report, final I_C_OrderLine... expectedOrderLines)
	{
		final Map<Integer, I_C_OrderLine> expectedOrderLinesMap = new HashMap<>();
		for (final I_C_OrderLine orderLine : expectedOrderLines)
		{
			expectedOrderLinesMap.put(orderLine.getC_OrderLine_ID(), orderLine);
		}

		final List<I_C_Order_MFGWarehouse_ReportLine> reportLines = Services.get(IOrderCheckupDAO.class).retrieveAllReportLines(report);
		for (final I_C_Order_MFGWarehouse_ReportLine reportLine : reportLines)
		{
			final I_C_OrderLine expectedOrderLine = expectedOrderLinesMap.remove(reportLine.getC_OrderLine_ID());
			Assertions.assertThat(expectedOrderLine).as("Unexpected report line: " + reportLine).isNotNull();

			Assertions.assertThat(reportLine.getM_Product_ID()).as("Product for " + reportLine).isEqualTo(expectedOrderLine.getM_Product_ID());
		}

		Assertions.assertThat(expectedOrderLinesMap).as("All expected order lines were found in report lines: " + expectedOrderLinesMap).isEmpty();
	}

	public void generateReportsAndEnqueueToPrinting(final I_C_Order order)
	{
		Services.get(IOrderCheckupBL.class).generateReportsIfEligible(order);
		enqueueToPrinting(order);
	}

	public void enqueueToPrinting(final I_C_Order order)
	{
		final List<I_C_Order_MFGWarehouse_Report> reports = Services.get(IOrderCheckupDAO.class).retrieveAllReports(order);

		for (final I_C_Order_MFGWarehouse_Report report : reports)
		{
			enqueueToPrinting(report);
		}
	}

	private void enqueueToPrinting(final I_C_Order_MFGWarehouse_Report report)
	{
		// Create the archive
		final I_AD_Archive archive = InterfaceWrapperHelper.newInstance(I_AD_Archive.class, report);
		archive.setAD_Table_ID(InterfaceWrapperHelper.getModelTableId(report));
		archive.setRecord_ID(report.getC_Order_MFGWarehouse_Report_ID());
		archive.setIsDirectEnqueue(true);
		InterfaceWrapperHelper.save(archive);

		// Enqueue to printing
		new AD_Archive(printOutputFacade).printArchive(archive);

		// Get the printing queue item and the recipient(s)
		final I_C_Printing_Queue printingItem = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Printing_Queue.class, report)
				.addEqualsFilter(I_C_Printing_Queue.COLUMN_AD_Archive_ID, archive.getAD_Archive_ID())
				.create()
				.firstOnlyNotNull(I_C_Printing_Queue.class);
		final List<I_C_Printing_Queue_Recipient> printoutRecipients = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Printing_Queue_Recipient.class, report)
				.addEqualsFilter(I_C_Printing_Queue.COLUMNNAME_C_Printing_Queue_ID, printingItem.getC_Printing_Queue_ID())
				.create()
				.list(I_C_Printing_Queue_Recipient.class);

		// Validate the printing queue item
		Assertions.assertThat(printingItem.isPrintoutForOtherUser()).as("Printing queue item - PrintoutForOtherUser").isTrue();
		Assertions.assertThat(printingItem.isActive()).as("Printing queue item - IsActive").isTrue();

		assertThat("Printout recipients - wrong number", printoutRecipients.size(), is(1));
		assertThat("Printout recipient - wrong AD_User_ToPrint_ID", printoutRecipients.get(0).getAD_User_ToPrint_ID(), is(report.getAD_User_Responsible_ID()));
	}

}
