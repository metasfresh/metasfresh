package de.metas.handlingunits.picking.job.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJobDocStatus;
import de.metas.handlingunits.picking.job.model.PickingJobId;
import de.metas.handlingunits.picking.job.model.PickingJobReference;
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
 * job renders its products differently from one load to the next.
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

	private I_M_Picking_Job_Line createLine(final I_M_Picking_Job job, final int productRepoId, final int shipmentScheduleRepoId)
	{
		final I_M_Picking_Job_Line line = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Line.class);
		line.setAD_Org_ID(orgId.getRepoId());
		line.setM_Picking_Job_ID(job.getM_Picking_Job_ID());
		line.setM_Product_ID(productRepoId);
		line.setM_ShipmentSchedule_ID(shipmentScheduleRepoId);
		line.setC_UOM_ID(uomEach.getC_UOM_ID());
		line.setQtyToPick(BigDecimal.ONE);
		line.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(line);
		return line;
	}

	@Test
	void productNames_followLineIdOrder_whateverOrderTheLinesWereLoadedIn()
	{
		final I_M_Picking_Job job = createJob();
		// created ascending, so line id order and product id order agree
		final ImmutableList<I_M_Picking_Job_Line> linesInIdOrder = ImmutableList.of(
				createLine(job, 101, 201),
				createLine(job, 102, 202),
				createLine(job, 103, 203));

		final PickingJobLoaderAndSaver loader = PickingJobLoaderAndSaver.forLoading(loadingSupportServices);
		loader.addAlreadyLoadedFromDB(job);
		// fed in REVERSE: this is what an unordered query may hand back, and it is exactly what an
		// unsorted implementation would leak into the caption
		linesInIdOrder.reverse().forEach(loader::addAlreadyLoadedFromDB);

		final PickingJobId pickingJobId = PickingJobId.ofRepoId(job.getM_Picking_Job_ID());
		final PickingJobReference reference = loader.streamPickingJobReferences(ImmutableSet.of(pickingJobId))
				.findFirst()
				.orElseThrow(() -> new AssertionError("no PickingJobReference loaded"));

		assertThat(reference.getProducts().getProductNamesJoined().getDefaultValue())
				.isEqualTo("productName-101, productName-102, productName-103");
	}
}
