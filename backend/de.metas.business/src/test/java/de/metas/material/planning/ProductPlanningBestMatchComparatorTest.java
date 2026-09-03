package de.metas.material.planning;

import org.eevolution.model.I_PP_Product_Planning;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AdempiereTestWatcher.class)
class ProductPlanningBestMatchComparatorTest
{
	private ProductPlanningBestMatchComparator comparator;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		comparator = new ProductPlanningBestMatchComparator();
	}

	@Test
	void lowestSeqNoWins()
	{
		final I_PP_Product_Planning pp1 = createRecord(10, false, 1000, 100, 0);
		final I_PP_Product_Planning pp2 = createRecord(5, false, 1000, 100, 0);

		final I_PP_Product_Planning best = pickBest(pp1, pp2);
		assertThat(best).isSameAs(pp2);
	}

	@Test
	void attributeDependantPreferredAtSameSeqNo()
	{
		final I_PP_Product_Planning ppGeneric = createRecord(10, false, 1000, 100, 0);
		final I_PP_Product_Planning ppSpecific = createRecord(10, true, 1000, 100, 0);

		final I_PP_Product_Planning best = pickBest(ppGeneric, ppSpecific);
		assertThat(best).isSameAs(ppSpecific);
	}

	@Test
	void specificOrgPreferredOverWildcard()
	{
		final I_PP_Product_Planning ppAnyOrg = createRecord(10, false, 0, 100, 0);
		final I_PP_Product_Planning ppSpecificOrg = createRecord(10, false, 1000, 100, 0);

		final I_PP_Product_Planning best = pickBest(ppAnyOrg, ppSpecificOrg);
		assertThat(best).isSameAs(ppSpecificOrg);
	}

	@Test
	void specificWarehousePreferredOverNull()
	{
		final I_PP_Product_Planning ppNoWh = createRecord(10, false, 1000, 0, 0);
		final I_PP_Product_Planning ppWithWh = createRecord(10, false, 1000, 100, 0);

		final I_PP_Product_Planning best = pickBest(ppNoWh, ppWithWh);
		assertThat(best).isSameAs(ppWithWh);
	}

	@Test
	void specificPlantPreferredOverNull()
	{
		final I_PP_Product_Planning ppNoPlant = createRecord(10, false, 1000, 100, 0);
		final I_PP_Product_Planning ppWithPlant = createRecord(10, false, 1000, 100, 200);

		final I_PP_Product_Planning best = pickBest(ppNoPlant, ppWithPlant);
		assertThat(best).isSameAs(ppWithPlant);
	}

	@Test
	void fullTiebreakChain()
	{
		// All same SeqNo=10, not attribute-dependant, same org
		// Warehouse and plant differ
		final I_PP_Product_Planning ppGeneric = createRecord(10, false, 1000, 0, 0);
		final I_PP_Product_Planning ppWithWh = createRecord(10, false, 1000, 100, 0);
		final I_PP_Product_Planning ppWithWhAndPlant = createRecord(10, false, 1000, 100, 200);

		final List<I_PP_Product_Planning> sorted = Arrays.asList(ppGeneric, ppWithWhAndPlant, ppWithWh);
		sorted.sort(comparator);

		assertThat(sorted).containsExactly(ppWithWhAndPlant, ppWithWh, ppGeneric);
	}

	private I_PP_Product_Planning pickBest(final I_PP_Product_Planning... records)
	{
		return Arrays.stream(records).min(comparator).orElseThrow(() -> new IllegalStateException("No records"));
	}

	private I_PP_Product_Planning createRecord(
			final int seqNo,
			final boolean isAttributeDependant,
			final int orgId,
			final int warehouseId,
			final int plantId)
	{
		final I_PP_Product_Planning record = newInstance(I_PP_Product_Planning.class);
		record.setSeqNo(seqNo);
		record.setIsAttributeDependant(isAttributeDependant);
		record.setAD_Org_ID(orgId);
		record.setM_Warehouse_ID(warehouseId);
		record.setS_Resource_ID(plantId);
		save(record);
		return record;
	}
}
