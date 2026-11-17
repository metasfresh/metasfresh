package de.metas.handlingunits.allocation.transfer;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleDAO;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Moving a TU onto a new LU while the service updates the receipt schedule the TU is assigned to
 * (the {@code referencedObjects} path, used by the WebUI HU editor).
 * <p>
 * The TU and its CU have no {@code M_HU_Attribute} rows (crates created before the attribute rows existed).
 * The move must succeed and move the receipt schedule allocation onto the new LU, recording no catch weight unit.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class HUTransformService_HUWithoutAttributes_ReceiptScheduleTest
{
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHUReceiptScheduleDAO huReceiptScheduleDAO;
	private LUTUProducerDestinationTestSupport data;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		huReceiptScheduleDAO = Services.get(IHUReceiptScheduleDAO.class);
		SpringContextHolder.registerJUnitBean(HUQRCodesService.newInstanceForUnitTesting());

		data = new LUTUProducerDestinationTestSupport();
	}

	@Test
	public void tuToNewLU_withReceiptSchedule_tuAndCuWithoutAttributeRows()
	{
		final I_M_HU cu = data.mkRealCUWithTUandQtyCU("20");
		final I_M_HU tu = handlingUnitsDAO.retrieveParent(cu);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptScheduleAssignedTo(cu, tu);
		final int fixtureAllocId = singleAllocationId(receiptSchedule);

		deleteAllAttributeRows(cu);
		deleteAllAttributeRows(tu);
		final IHUContext huContext = freshHUContext();
		assertThat(huContext.getHUAttributeStorageFactory().getAttributeStorage(cu).hasAttribute(Weightables.ATTR_WeightNet))
				.as("precondition: the CU has no WeightNet, like the live crate").isFalse();

		final I_M_HU lu = moveTUToNewLU(huContext, receiptSchedule, tu);

		assertThat(handlingUnitsDAO.retrieveParent(tu).getM_HU_ID()).isEqualTo(lu.getM_HU_ID());
		assertAllocationMovedToLU(receiptSchedule, fixtureAllocId, cu, tu, lu);
		for (final I_M_ReceiptSchedule_Alloc alloc : allocationsAddedByMove(receiptSchedule, fixtureAllocId))
		{
			assertThat(alloc.getCatch_UOM_ID()).as("no catch weight unit without a WeightNet").isLessThanOrEqualTo(0);
			assertThat(InterfaceWrapperHelper.isNull(alloc, I_M_ReceiptSchedule_Alloc.COLUMNNAME_QtyAllocatedInCatchUOM)).isTrue();
		}
	}

	/**
	 * Control: the same move for a crate that still has its attribute rows. Shows what such a move stores today:
	 * the catch weight unit is set, the catch quantity is 0 (receipt allocations never store a catch weight).
	 */
	@Test
	public void tuToNewLU_withReceiptSchedule_withAttributeRows()
	{
		final I_M_HU cu = data.mkRealCUWithTUandQtyCU("20");
		final I_M_HU tu = handlingUnitsDAO.retrieveParent(cu);
		final I_M_ReceiptSchedule receiptSchedule = createReceiptScheduleAssignedTo(cu, tu);
		final int fixtureAllocId = singleAllocationId(receiptSchedule);

		final IHUContext huContext = freshHUContext();
		assertThat(huContext.getHUAttributeStorageFactory().getAttributeStorage(cu).hasAttribute(Weightables.ATTR_WeightNet))
				.as("precondition: the CU has WeightNet").isTrue();

		final I_M_HU lu = moveTUToNewLU(huContext, receiptSchedule, tu);

		assertAllocationMovedToLU(receiptSchedule, fixtureAllocId, cu, tu, lu);
		for (final I_M_ReceiptSchedule_Alloc alloc : allocationsAddedByMove(receiptSchedule, fixtureAllocId))
		{
			assertThat(alloc.getCatch_UOM_ID()).as("catch weight unit = WeightNet's UOM").isEqualTo(data.helper.uomKg.getC_UOM_ID());
			assertThat(alloc.getQtyAllocatedInCatchUOM()).as("catch qty is stored as 0").isEqualByComparingTo(BigDecimal.ZERO);
		}
	}

	private I_M_HU moveTUToNewLU(final IHUContext huContext, final I_M_ReceiptSchedule receiptSchedule, final I_M_HU tu)
	{
		return HUTransformService.builderForHUcontext()
				.huContext(huContext)
				.referencedObjects(ImmutableList.of(TableRecordReference.of(receiptSchedule)))
				.build()
				.tuToNewLU(tu, QtyTU.ONE, HuPackingInstructionsId.ofRepoId(data.piLU.getM_HU_PI_ID()))
				.getSingleLURecord();
	}

	/**
	 * The move adds two rows next to the fixture row: -20 (TU taken off its old place) and +20 (TU put onto the new LU).
	 * After the parent change, {@code ReceiptScheduleHUTrxListener.huParentChanged} points every open row of the TU at the new LU,
	 * so all three rows end up with {@code M_LU_HU_ID} = the new LU and the net allocated qty stays 20.
	 */
	private void assertAllocationMovedToLU(
			final I_M_ReceiptSchedule receiptSchedule,
			final int fixtureAllocId,
			final I_M_HU cu,
			final I_M_HU tu,
			final I_M_HU lu)
	{
		final List<I_M_ReceiptSchedule_Alloc> all = huReceiptScheduleDAO.retrieveAllHandlingUnitAllocations(receiptSchedule, data.helper.getHUContext().getTrxName());
		assertThat(all).hasSize(3);
		assertThat(all).allSatisfy(alloc -> {
			assertThat(alloc.getVHU_ID()).isEqualTo(cu.getM_HU_ID());
			assertThat(alloc.getM_TU_HU_ID()).isEqualTo(tu.getM_HU_ID());
			assertThat(alloc.getM_LU_HU_ID()).isEqualTo(lu.getM_HU_ID());
		});
		assertThat(all.stream().map(I_M_ReceiptSchedule_Alloc::getHU_QtyAllocated).reduce(BigDecimal.ZERO, BigDecimal::add))
				.isEqualByComparingTo("20");

		final List<I_M_ReceiptSchedule_Alloc> added = allocationsAddedByMove(receiptSchedule, fixtureAllocId);
		assertThat(added).extracting(alloc -> alloc.getHU_QtyAllocated().intValueExact())
				.containsExactlyInAnyOrder(-20, 20);
	}

	private List<I_M_ReceiptSchedule_Alloc> allocationsAddedByMove(final I_M_ReceiptSchedule receiptSchedule, final int fixtureAllocId)
	{
		return huReceiptScheduleDAO.retrieveAllHandlingUnitAllocations(receiptSchedule, data.helper.getHUContext().getTrxName())
				.stream()
				.filter(alloc -> alloc.getM_ReceiptSchedule_Alloc_ID() != fixtureAllocId)
				.collect(Collectors.toList());
	}

	private int singleAllocationId(final I_M_ReceiptSchedule receiptSchedule)
	{
		final List<I_M_ReceiptSchedule_Alloc> allocs = huReceiptScheduleDAO.retrieveAllHandlingUnitAllocations(receiptSchedule, data.helper.getHUContext().getTrxName());
		assertThat(allocs).as("precondition: one fixture allocation").hasSize(1);
		return allocs.get(0).getM_ReceiptSchedule_Alloc_ID();
	}

	/**
	 * Same set-up as {@code HUTransformServiceReceiptCandidatesTests#create_receiptSchedule_for_CU} for a CU in a standalone TU.
	 */
	private I_M_ReceiptSchedule createReceiptScheduleAssignedTo(final I_M_HU cu, final I_M_HU tu)
	{
		final List<IHUProductStorage> storages = data.helper.getHUContext().getHUStorageFactory().getStorage(cu).getProductStorages();
		assertThat(storages).hasSize(1);

		final I_M_ReceiptSchedule receiptSchedule = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
		receiptSchedule.setM_Warehouse_ID(BusinessTestHelper.createWarehouse("test").getM_Warehouse_ID());
		receiptSchedule.setC_BPartner_ID(BusinessTestHelper.createBPartner("test").getC_BPartner_ID());
		receiptSchedule.setM_Product_ID(storages.get(0).getProductId().getRepoId());
		receiptSchedule.setC_UOM_ID(storages.get(0).getC_UOM().getC_UOM_ID());
		InterfaceWrapperHelper.save(receiptSchedule);

		final I_M_ReceiptSchedule_Alloc alloc = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule_Alloc.class);
		alloc.setM_ReceiptSchedule(receiptSchedule);
		alloc.setVHU(cu);
		alloc.setM_TU_HU(tu);
		alloc.setHU_QtyAllocated(new BigDecimal("20"));
		InterfaceWrapperHelper.save(alloc);

		final I_M_HU_Assignment huAssignment = InterfaceWrapperHelper.newInstance(I_M_HU_Assignment.class);
		huAssignment.setM_HU(tu);
		final TableRecordReference rsRef = TableRecordReference.of(receiptSchedule);
		huAssignment.setAD_Table_ID(rsRef.getAD_Table_ID());
		huAssignment.setRecord_ID(rsRef.getRecord_ID());
		InterfaceWrapperHelper.save(huAssignment);

		return receiptSchedule;
	}

	private static void deleteAllAttributeRows(final I_M_HU hu)
	{
		final List<I_M_HU_Attribute> rows = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Attribute.class)
				.addEqualsFilter(I_M_HU_Attribute.COLUMNNAME_M_HU_ID, hu.getM_HU_ID())
				.create()
				.list();
		assertThat(rows).as("precondition: the test HU had attribute rows to remove").isNotEmpty();
		rows.forEach(InterfaceWrapperHelper::delete);
	}

	/**
	 * Fresh HU context: no attribute storage cached while creating the HUs is reused (live, the crate is loaded in a new request).
	 */
	private static IHUContext freshHUContext()
	{
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext();
		huContext.getHUPackingMaterialsCollector().disable(); // new LUs would otherwise want a packing material movement (needs the empties warehouse bean)
		return huContext;
	}
}
