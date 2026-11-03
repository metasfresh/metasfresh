package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The launcher caption of an already-started picking job joins the job's product names, and that
 * order is the order {@code extractProducts} iterates the job's lines in. The lines arrive from a
 * query with no {@code ORDER BY}, so the loader has to impose the order itself — otherwise the same
 * job renders its products differently from one load to the next. The order it imposes is the
 * sales-order line order (C_OrderLine.Line): stable AND matching the order the picker reads off the
 * sales document, rather than the internal M_Picking_Job_Line_ID, which is stable but arbitrary.
 * <p>
 * JUnit is the right tier here rather than Playwright or Cucumber: the invariant is a pure in-memory
 * ordering contract of the loader, reachable without a database because {@code addAlreadyLoadedFromDB}
 * populates the line map directly. The mobile Playwright spec covers the user-visible caption and owns
 * that layer; this test owns the ordering contract underneath it, so the two do not duplicate.
 */
class PickingJobLoaderAndSaverProductOrderTest
{
	private final OrgId orgId = OrgId.ofRepoId(1);
	private MockedPickingJobLoaderSupportingServices loadingSupportServices;
	private I_C_UOM uomEach;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());

		// registers PickingJobLineCarrierServiceRepository in the JUnit bean registry, which
		// PickingJobSaver resolves when PickingJobLoaderAndSaver is constructed
		PickingJobLineCarrierServiceRepository.newInstanceForUnitTesting();

		loadingSupportServices = new MockedPickingJobLoaderSupportingServices();
		uomEach = BusinessTestHelper.createUomEach();
	}

	private I_M_Picking_Job createJob()
	{
		final I_M_Picking_Job job = InterfaceWrapperHelper.newInstance(I_M_Picking_Job.class);
		job.setAD_Org_ID(orgId.getRepoId());
		job.setDocStatus(PickingJobDocStatus.Drafted.getCode());
		job.setPickingJobAggregationType(PickingJobAggregationType.SALES_ORDER.getCode());
		job.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(job);
		return job;
	}

	private static final int SALES_ORDER_ID = 500;

	private I_M_Picking_Job_Line createLine(
			final I_M_Picking_Job job,
			final int productRepoId,
			final int shipmentScheduleRepoId,
			final int salesOrderLineRepoId,
			final int salesOrderLineSeqNo)
	{
		final I_M_Picking_Job_Line line = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Line.class);
		line.setAD_Org_ID(orgId.getRepoId());
		line.setM_Picking_Job_ID(job.getM_Picking_Job_ID());
		line.setM_Product_ID(productRepoId);
		line.setM_ShipmentSchedule_ID(shipmentScheduleRepoId);
		line.setC_Order_ID(SALES_ORDER_ID);
		line.setC_OrderLine_ID(salesOrderLineRepoId);
		// the full-job load path (loadById -> loadLine) needs a delivery BP location on each line
		line.setC_BPartner_ID(700);
		line.setC_BPartner_Location_ID(701);
		line.setC_UOM_ID(uomEach.getC_UOM_ID());
		line.setQtyToPick(BigDecimal.ONE);
		line.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(line);
		loadingSupportServices.setSalesOrderLineSeqNo(
				OrderAndLineId.ofRepoIds(SALES_ORDER_ID, salesOrderLineRepoId),
				salesOrderLineSeqNo);
		return line;
	}

	@Test
	void productNames_followSalesOrderLineOrder_whateverOrderTheLinesWereLoadedIn()
	{
		final I_M_Picking_Job job = createJob();
		// Three axes are deliberately made to disagree so only a sort on the SALES-ORDER-LINE SeqNo
		// (C_OrderLine.Line) can yield the expected string:
		//   creation / line-id order : 103, 101, 102   (the OLD behaviour — a sort on M_Picking_Job_Line_ID)
		//   product-id order         : 101, 102, 103
		//   sales-order-line SeqNo   :  20,  30,  10  -> ordered: 102(10), 103(20), 101(30)
		// so the expected below matches none of the others.
		final ImmutableList<I_M_Picking_Job_Line> linesInCreationOrder = ImmutableList.of(
				createLine(job, 103, 201, 5031, 20),
				createLine(job, 101, 202, 5032, 30),
				createLine(job, 102, 203, 5033, 10));

		final PickingJobLoaderAndSaver loader = PickingJobLoaderAndSaver.forLoading(loadingSupportServices);
		loader.addAlreadyLoadedFromDB(job);
		// fed in REVERSE: this is what an unordered query may hand back, and it is exactly what an
		// unsorted implementation would leak into the caption
		linesInCreationOrder.reverse().forEach(loader::addAlreadyLoadedFromDB);

		final PickingJobId pickingJobId = PickingJobId.ofRepoId(job.getM_Picking_Job_ID());
		final PickingJobReference reference = loader.streamPickingJobReferences(ImmutableSet.of(pickingJobId))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no PickingJobReference loaded"));

		assertThat(reference.getProducts().getProductNamesJoined(", ").getDefaultValue())
				.isEqualTo("productName-102, productName-103, productName-101");
	}

	@Test
	void openedJobProductNames_followSalesOrderLineOrder_matchingTheLauncherList()
	{
		// The opened job's ProductNames caption (PickingJob.getProductNamesJoined, DisplayValueProvider:225) must
		// order the same way as the launcher-list caption above, so the two never disagree for the same job.
		final I_M_Picking_Job job = createJob();
		final ImmutableList<I_M_Picking_Job_Line> linesInCreationOrder = ImmutableList.of(
				createLine(job, 103, 201, 5031, 20),
				createLine(job, 101, 202, 5032, 30),
				createLine(job, 102, 203, 5033, 10));

		final PickingJobLoaderAndSaver loader = PickingJobLoaderAndSaver.forLoading(loadingSupportServices);
		loader.addAlreadyLoadedFromDB(job);
		linesInCreationOrder.reverse().forEach(loader::addAlreadyLoadedFromDB);

		final PickingJob loaded = loader.loadById(PickingJobId.ofRepoId(job.getM_Picking_Job_ID()));

		assertThat(loaded.getProductNamesJoined(", ").getDefaultValue())
				.isEqualTo("productName-102, productName-103, productName-101");
	}
}
