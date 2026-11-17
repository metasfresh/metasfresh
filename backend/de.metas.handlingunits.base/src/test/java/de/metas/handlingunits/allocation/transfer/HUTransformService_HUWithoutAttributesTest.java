package de.metas.handlingunits.allocation.transfer;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A TU whose TU and CU have no {@code M_HU_Attribute} rows at all (crates picked before the attribute rows existed)
 * must still be movable onto a new or an existing LU - the path used by the mobile HU consolidation.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class HUTransformService_HUWithoutAttributesTest
{
	private IHandlingUnitsDAO handlingUnitsDAO;
	private LUTUProducerDestinationTestSupport data;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		SpringContextHolder.registerJUnitBean(HUQRCodesService.newInstanceForUnitTesting());

		data = new HUTransformTestsBase().getData();
		data.disableHUPackingMaterialsCollector("new LUs would otherwise want a packing material movement");
	}

	private HuPackingInstructionsId luPIId()
	{
		return HuPackingInstructionsId.ofRepoId(data.piLU.getM_HU_PI_ID());
	}

	/**
	 * @return a TU with one CU, where neither the TU nor the CU has any {@code M_HU_Attribute} row
	 */
	private I_M_HU createTUWithoutAnyAttributeRows()
	{
		final I_M_HU cu = data.mkRealCUWithTUandQtyCU("20");
		final I_M_HU tu = handlingUnitsDAO.retrieveParent(cu);

		deleteAllAttributeRows(cu);
		deleteAllAttributeRows(tu);
		return tu;
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
	 * A transform service with a fresh HU context, so no attribute storage cached while creating the HUs is reused
	 * (live, the crate is loaded from the DB in a new request).
	 */
	private static IHUContext freshHUContext()
	{
		return Services.get(IHUContextFactory.class).createMutableHUContext();
	}

	private void assertNoWeightNet(final IHUContext huContext, final I_M_HU tu)
	{
		final I_M_HU cu = handlingUnitsDAO.retrieveIncludedHUs(tu).get(0);
		final IAttributeStorage cuStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(cu);
		assertThat(cuStorage.hasAttribute(Weightables.ATTR_WeightNet)).as("precondition: the CU has no WeightNet, like the live crate").isFalse();
	}

	@Test
	public void tuToNewLU_tuAndCuWithoutAttributeRows()
	{
		final I_M_HU tu = createTUWithoutAnyAttributeRows();
		final IHUContext huContext = freshHUContext();
		assertNoWeightNet(huContext, tu);
		final HUTransformService service = HUTransformService.newInstance(huContext);

		final I_M_HU lu = service.tuToNewLU(tu, QtyTU.ONE, luPIId()).getSingleLURecord();

		assertThat(handlingUnitsDAO.retrieveParent(tu).getM_HU_ID()).isEqualTo(lu.getM_HU_ID());
	}

	@Test
	public void tusToExistingLU_tuAndCuWithoutAttributeRows()
	{
		final I_M_HU firstTU = createTUWithoutAnyAttributeRows();
		final I_M_HU secondTU = createTUWithoutAnyAttributeRows();
		final IHUContext huContext = freshHUContext();
		assertNoWeightNet(huContext, firstTU);
		assertNoWeightNet(huContext, secondTU);
		final HUTransformService service = HUTransformService.newInstance(huContext);

		final I_M_HU lu = service.tuToNewLU(firstTU, QtyTU.ONE, luPIId()).getSingleLURecord();
		service.tusToExistingLUId(ImmutableList.of(secondTU), HuId.ofRepoId(lu.getM_HU_ID()));

		assertThat(handlingUnitsDAO.retrieveParent(secondTU).getM_HU_ID()).isEqualTo(lu.getM_HU_ID());
	}
}
