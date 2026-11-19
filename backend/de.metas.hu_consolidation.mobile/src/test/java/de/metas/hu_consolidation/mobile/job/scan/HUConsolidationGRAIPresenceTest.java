package de.metas.hu_consolidation.mobile.job.scan;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * TC21 part 2: presence of a GRAI on an active top-level HU among the given crates (the "Scan (GRAI)" button rule).
 * Runs on real (POJO) HU / attribute rows, so the query filters (active, top-level, attribute not null) are exercised.
 */
class HUConsolidationGRAIPresenceTest
{
	private static final String GRAI_CODE = "7613204.00307.1234567890";

	private HUConsolidationGRAIPresence presence;
	private I_M_Attribute graiAttribute;

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

		presence = new HUConsolidationGRAIPresence();
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

	@Test
	void topLevelCrateWithGRAI_counts()
	{
		final I_M_HU crate = createTopLevelHU(GRAI_CODE);
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(id(crate)))).isTrue();
	}

	@Test
	void severalCratesWithGRAI_count_withoutException()
	{
		// regression: the real reference case has several GRAI crates in the slots, the check must not demand a unique match
		final I_M_HU crate1 = createTopLevelHU(GRAI_CODE);
		final I_M_HU crate2 = createTopLevelHU("7613204.00307.1234567891");
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(id(crate1), id(crate2)))).isTrue();
	}

	@Test
	void dummyGRAI_counts()
	{
		final I_M_HU crate = createTopLevelHU("7613204.00307.DUMMY-1");
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(id(crate)))).isTrue();
	}

	@Test
	void topLevelLUQueuedInSlotWithGRAI_counts()
	{
		// slot case (AC27): the queued HU is a top-level LU that itself carries the GRAI
		final I_M_HU lu = createTopLevelHU(GRAI_CODE);
		assertThat(presence.isAnyGRAIPresent(ImmutableList.of(id(lu)))).isTrue();
	}

	@Test
	void graiTUInsideQueuedLU_doesNotCount()
	{
		final I_M_HU lu = createTopLevelHU(null);
		createTUInside(lu, GRAI_CODE);
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(id(lu)))).isFalse();
	}

	@Test
	void crateWithoutGRAI_doesNotCount()
	{
		final I_M_HU crate = createTopLevelHU(null);
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(id(crate)))).isFalse();
	}

	@Test
	void inactiveCrateWithGRAI_doesNotCount()
	{
		final I_M_HU crate = createTopLevelHU(GRAI_CODE);
		crate.setIsActive(false);
		save(crate);
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(id(crate)))).isFalse();
	}

	@Test
	void emptyStringValue_isCountedByTheFilter_butNotAGRAIOnTheReadSide()
	{
		// pins E8: the filter is "attribute value IS NOT NULL", so '' counts as present ...
		final I_M_HU crate = createTopLevelHU("");
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(id(crate)))).isTrue();
		// ... while the read side (HUGraiSnapshotLoader.readGRAI -> GRAI.ofNullableCanonicalString) sees no GRAI
		assertThat(GRAI.ofNullableCanonicalString("")).isNull();
		assertThat(GRAI.ofNullableCanonicalString("  ")).isNull();
	}

	@Test
	void nonCanonicalValue_isCountedByTheFilter_readSideRejectsIt()
	{
		final I_M_HU crate = createTopLevelHU("not-canonical");
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(id(crate)))).isTrue();
		assertThrows(RuntimeException.class, () -> GRAI.ofNullableCanonicalString("not-canonical"));
	}

	@Test
	void emptyCrateList_isFalse_withoutQuery_evenIfAnotherActiveTopLevelHUCarriesAGRAI()
	{
		// a HU of another job carries a GRAI: without the guard addOnlyHUIds(empty) would drop the filter and find it
		createTopLevelHU(GRAI_CODE);
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of())).isFalse();
	}

	@Test
	void hUsOfOtherJobsAreNotSeen()
	{
		final I_M_HU mine = createTopLevelHU(null);
		createTopLevelHU(GRAI_CODE); // other job
		assertThat(presence.isAnyGRAIPresent(ImmutableSet.of(id(mine)))).isFalse();
	}

	@Test
	void jobWithoutPickingSlots_hasNoCrates_noQueueQuery_noPresence()
	{
		createTopLevelHU(GRAI_CODE); // another job's crate
		final HUConsolidationJobCrates crates = HUConsolidationJobCrates.load(ImmutableSet.of(), PickingSlotService.newInstanceForUnitTesting());
		assertThat(crates.isEmpty()).isTrue();
		assertThat(presence.isAnyGRAIPresent(crates.getHuIds())).isFalse();
	}
}
