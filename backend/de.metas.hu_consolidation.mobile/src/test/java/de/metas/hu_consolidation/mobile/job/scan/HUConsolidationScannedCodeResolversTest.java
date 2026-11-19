package de.metas.hu_consolidation.mobile.job.scan;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.handlingunits.qrcodes.mobile.MobileQRCodeMessages;
import de.metas.i18n.AdMessageKey;
import de.metas.picking.api.PickingSlotId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Resolver chain "scanned code -> crate in this job's slots".
 * Runs on real (POJO) HU / attribute rows, so the query filters (active, top-level, HU ids of the job, GRAI value) are exercised:
 * a HU of another job that carries the same GRAI is planted and must never be reached.
 */
class HUConsolidationScannedCodeResolversTest
{
	private static final String GRAI = "7613204.00307.1234567890";
	private static final String OTHER_GRAI = "7613204.00307.1234567891";

	private static final PickingSlotId SLOT_1 = PickingSlotId.ofRepoId(11);
	private static final PickingSlotId SLOT_2 = PickingSlotId.ofRepoId(12);

	private I_M_Attribute graiAttribute;
	private GRAIScannedCodeResolver graiResolver;
	private HUConsolidationScannedCodeResolvers resolvers;
	private PickingSlotService pickingSlotService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		graiAttribute = newInstance(I_M_Attribute.class);
		graiAttribute.setValue(AttributeConstants.ATTR_GRAI.getCode());
		graiAttribute.setName("GRAI");
		graiAttribute.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		save(graiAttribute);

		// HUQueryBuilder only filters by attributes that are HU-relevant, i.e. mapped on the virtual packing instructions version
		final I_M_HU_PI_Attribute graiPIAttribute = newInstance(I_M_HU_PI_Attribute.class);
		graiPIAttribute.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		graiPIAttribute.setM_Attribute_ID(graiAttribute.getM_Attribute_ID());
		save(graiPIAttribute);

		graiResolver = new GRAIScannedCodeResolver();
		resolvers = new HUConsolidationScannedCodeResolvers(ImmutableList.of(graiResolver));
		pickingSlotService = PickingSlotService.newInstanceForUnitTesting();
	}

	private I_M_HU createTopLevelHU(final String graiValue)
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setIsActive(true);
		save(hu);
		if (graiValue != null)
		{
			setGRAI(hu, graiValue);
		}
		return hu;
	}

	/** A TU that sits inside the given LU (it has a parent HU item, so it is not top-level). */
	private I_M_HU createTUInside(final I_M_HU lu, final String graiValue)
	{
		final I_M_HU_Item luItem = newInstance(I_M_HU_Item.class);
		luItem.setM_HU_ID(lu.getM_HU_ID());
		save(luItem);

		final I_M_HU tu = newInstance(I_M_HU.class);
		tu.setIsActive(true);
		tu.setM_HU_Item_Parent_ID(luItem.getM_HU_Item_ID());
		save(tu);
		setGRAI(tu, graiValue);
		return tu;
	}

	private void setGRAI(final I_M_HU hu, final String value)
	{
		final I_M_HU_Attribute huAttribute = newInstance(I_M_HU_Attribute.class);
		huAttribute.setM_HU_ID(hu.getM_HU_ID());
		huAttribute.setM_Attribute_ID(graiAttribute.getM_Attribute_ID());
		huAttribute.setValue(value);
		save(huAttribute);
	}

	private static HuId id(final I_M_HU hu) {return HuId.ofRepoId(hu.getM_HU_ID());}

	private static HUConsolidationJobCrates jobCrates(final I_M_HU hu1, final I_M_HU hu2)
	{
		return HUConsolidationJobCrates.of(ImmutableMap.of(id(hu1), SLOT_1, id(hu2), SLOT_2));
	}

	private static void assertMessage(final Throwable ex, final AdMessageKey expected)
	{
		assertThat(ex).isInstanceOf(AdempiereException.class);
		assertThat(((AdempiereException)ex).getErrorCode()).isEqualTo(expected.toAD_Message());
	}

	@Test
	void emptyCrateList_isHuNotFound_evenIfAnotherActiveTopLevelHUCarriesTheGRAI()
	{
		// without the guard the filter on HU ids would vanish and the HU of the other job would be found
		createTopLevelHU(GRAI);

		assertThatThrownBy(() -> resolvers.resolve(GRAI, HUConsolidationJobCrates.EMPTY))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.HU_NOT_FOUND));
	}

	@Test
	void zeroMatches_isHuNotFound()
	{
		final I_M_HU crate1 = createTopLevelHU(OTHER_GRAI);
		final I_M_HU crate2 = createTopLevelHU(null);

		assertThatThrownBy(() -> resolvers.resolve(GRAI, jobCrates(crate1, crate2)))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.HU_NOT_FOUND));
	}

	@Test
	void twoMatches_isHuAmbiguous()
	{
		final I_M_HU crate1 = createTopLevelHU(GRAI);
		final I_M_HU crate2 = createTopLevelHU(GRAI);

		assertThatThrownBy(() -> resolvers.resolve(GRAI, jobCrates(crate1, crate2)))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.HU_AMBIGUOUS));
	}

	@Test
	void oneMatch_isResolvedWithItsOwnSlot_andTheCrateOfAnotherJobWithTheSameGRAIIsNotReached()
	{
		final I_M_HU crate1 = createTopLevelHU(OTHER_GRAI);
		final I_M_HU crate2 = createTopLevelHU(GRAI);
		createTopLevelHU(GRAI); // another job: same GRAI, not in the job's crates

		final HUConsolidationScannedCrate crate = resolvers.resolve(GRAI, jobCrates(crate1, crate2));

		assertThat(crate).isEqualTo(HUConsolidationScannedCrate.of(id(crate2), SLOT_2));
	}

	@Test
	void inactiveCrateWithGRAI_isHuNotFound()
	{
		final I_M_HU crate1 = createTopLevelHU(GRAI);
		crate1.setIsActive(false);
		save(crate1);
		final I_M_HU crate2 = createTopLevelHU(null);

		assertThatThrownBy(() -> resolvers.resolve(GRAI, jobCrates(crate1, crate2)))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.HU_NOT_FOUND));
	}

	@Test
	void graiTUInsideQueuedLU_isHuNotFound()
	{
		final I_M_HU lu = createTopLevelHU(null);
		final I_M_HU tuInsideLU = createTUInside(lu, GRAI);

		// even if its id were among the job's crates, only top-level HUs are crates
		assertThatThrownBy(() -> resolvers.resolve(GRAI, jobCrates(lu, tuInsideLU)))
				.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.HU_NOT_FOUND));
	}

	@Nested
	class nonGraiCode
	{
		@Test
		void isNotMyCodeType_emptyResult()
		{
			final I_M_HU crate1 = createTopLevelHU(GRAI);
			final I_M_HU crate2 = createTopLevelHU(null);

			final Optional<HUConsolidationScannedCrate> crate = graiResolver.resolve("not-a-grai", jobCrates(crate1, crate2));

			assertThat(crate).isEmpty();
		}

		@Test
		void throughChain_isInvalidGraiBarcode()
		{
			final I_M_HU crate1 = createTopLevelHU(GRAI);
			final I_M_HU crate2 = createTopLevelHU(null);

			assertThatThrownBy(() -> resolvers.resolve("not-a-grai", jobCrates(crate1, crate2)))
					.satisfies(ex -> assertMessage(ex, MobileQRCodeMessages.INVALID_GRAI_BARCODE));
		}
	}

	@Test
	void chain_firstNonEmptyResolverWins()
	{
		final HUConsolidationScannedCrate expected = HUConsolidationScannedCrate.of(HuId.ofRepoId(101), SLOT_1);
		final HUConsolidationScannedCodeResolver notMine = (code, crates) -> Optional.empty();
		final HUConsolidationScannedCodeResolver mine = (code, crates) -> Optional.of(expected);
		final HUConsolidationScannedCodeResolver neverReached = (code, crates) -> {throw new AssertionError("must not be called");};

		final HUConsolidationScannedCodeResolvers chain = new HUConsolidationScannedCodeResolvers(ImmutableList.of(notMine, mine, neverReached));

		assertThat(chain.resolve("whatever", HUConsolidationJobCrates.EMPTY)).isEqualTo(expected);
	}

	@Test
	void loader_emptySlotSet_isEmpty_withoutQueueQuery()
	{
		final HUConsolidationJobCrates crates = HUConsolidationJobCrates.load(ImmutableSet.of(), pickingSlotService);

		assertThat(crates).isSameAs(HUConsolidationJobCrates.EMPTY);
		assertThat(crates.getHuIds()).isEmpty();
	}

	@Test
	void crates_getPickingSlotId()
	{
		final I_M_HU crate1 = createTopLevelHU(null);
		final I_M_HU crate2 = createTopLevelHU(null);
		final HUConsolidationJobCrates crates = jobCrates(crate1, crate2);

		assertThat(crates.getPickingSlotId(id(crate2))).isEqualTo(SLOT_2);
		assertThat(crates.getHuIds()).containsExactlyInAnyOrder(id(crate1), id(crate2));
	}
}
