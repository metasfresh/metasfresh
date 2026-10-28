package de.metas.handlingunits.grai;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.i18n.ExplainedOptional;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Loader-level tests for {@link HUGraiSnapshotLoader} focused on the dispatch in {@code load(I_M_HU)}.
 * The richer per-snapshot behaviour (delta computation, distribution across blocks, etc.) is covered
 * by {@link HUGraiSnapshotTest}.
 */
class HUGraiSnapshotLoaderTest
{
	private IHandlingUnitsBL handlingUnitsBL;
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHUAttributesBL huAttributesBL;
	private HUGraiSnapshotLoader loader;

	@BeforeEach
	void setup()
	{
		handlingUnitsBL = mock(IHandlingUnitsBL.class);
		handlingUnitsDAO = mock(IHandlingUnitsDAO.class);
		huAttributesBL = mock(IHUAttributesBL.class);

		loader = HUGraiSnapshotLoader.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.handlingUnitsDAO(handlingUnitsDAO)
				.huAttributesBL(huAttributesBL)
				.build();
	}

	private ExplainedOptional<HUGraiSnapshot> loadVia(final I_M_HU hu)
	{
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		when(handlingUnitsDAO.getById(huId)).thenReturn(hu);
		return loader.loadById(huId);
	}

	@Nested
	class LoadVirtualHU
	{
		@Test
		void vhuUnderHAItem_returnsAggregateBlockSnapshot()
		{
			// HA item with tuCount=5
			final I_M_HU_Item haItem = mock(I_M_HU_Item.class);
			when(haItem.getItemType()).thenReturn(X_M_HU_Item.ITEMTYPE_HUAggregate);
			when(haItem.getQty()).thenReturn(new BigDecimal("5"));

			// VHU sitting under that HA item
			final I_M_HU vhu = mock(I_M_HU.class);
			when(vhu.getM_HU_ID()).thenReturn(33681691);

			when(handlingUnitsBL.isVirtual(vhu)).thenReturn(true);
			when(handlingUnitsBL.isAggregateHU(vhu)).thenReturn(true);
			when(handlingUnitsDAO.retrieveParentItem(vhu)).thenReturn(haItem);
			when(huAttributesBL.getHUAttributeValue(eq(vhu), eq(AttributeConstants.ATTR_GRAI))).thenReturn(null);

			final ExplainedOptional<HUGraiSnapshot> result = loadVia(vhu);

			assertThat(result.isPresent()).isTrue();
			final HUGraiSnapshot snapshot = result.get();
			assertThat(snapshot.getHuId()).isEqualTo(HuId.ofRepoId(33681691));
			assertThat(snapshot.getTus()).isEmpty();
			assertThat(snapshot.getAggregateBlocks()).hasSize(1);

			final HUGraiSnapshot.AggregateBlock block = snapshot.getAggregateBlocks().get(0);
			assertThat(block.getVhuId()).isEqualTo(HuId.ofRepoId(33681691));
			assertThat(block.getTuCount().toInt()).isEqualTo(5);
			assertThat(block.getGrais().isEmpty()).isTrue();
		}

		@Test
		void vhuUnderHAItem_withExistingGrais_preservesThem()
		{
			final I_M_HU_Item haItem = mock(I_M_HU_Item.class);
			when(haItem.getItemType()).thenReturn(X_M_HU_Item.ITEMTYPE_HUAggregate);
			when(haItem.getQty()).thenReturn(new BigDecimal("3"));

			final I_M_HU vhu = mock(I_M_HU.class);
			when(vhu.getM_HU_ID()).thenReturn(42);

			when(handlingUnitsBL.isVirtual(vhu)).thenReturn(true);
			when(handlingUnitsBL.isAggregateHU(vhu)).thenReturn(true);
			when(handlingUnitsDAO.retrieveParentItem(vhu)).thenReturn(haItem);
			when(huAttributesBL.getHUAttributeValue(eq(vhu), eq(AttributeConstants.ATTR_GRAI)))
					.thenReturn("7613204.00307.1000000001,7613204.00307.1000000002");

			final HUGraiSnapshot snapshot = loadVia(vhu).get();

			assertThat(snapshot.getAggregateBlocks()).hasSize(1);
			final HUGraiSnapshot.AggregateBlock block = snapshot.getAggregateBlocks().get(0);
			assertThat(block.getTuCount().toInt()).isEqualTo(3);
			assertThat(block.getGrais().size()).isEqualTo(2);
		}

		@Test
		void pureVirtualHU_notUnderHA_returnsEmpty()
		{
			final I_M_HU vhu = mock(I_M_HU.class);
			when(vhu.getM_HU_ID()).thenReturn(99);
			when(handlingUnitsBL.isVirtual(vhu)).thenReturn(true);
			when(handlingUnitsBL.isAggregateHU(vhu)).thenReturn(false);

			final ExplainedOptional<HUGraiSnapshot> result = loadVia(vhu);

			assertThat(result.isPresent()).isFalse();
			assertThat(result.getExplanation().getDefaultValue()).contains("99");
		}
	}

	@Nested
	class LoadStandaloneTU
	{
		@Test
		void nonVirtualNonLU_loadsAsSingleSlotTU()
		{
			final I_M_HU tu = mock(I_M_HU.class);
			when(tu.getM_HU_ID()).thenReturn(123);
			when(handlingUnitsBL.isVirtual(tu)).thenReturn(false);
			when(handlingUnitsBL.isLoadingUnit(tu)).thenReturn(false);
			when(huAttributesBL.getHUAttributeValue(eq(tu), eq(AttributeConstants.ATTR_GRAI)))
					.thenReturn("7613204.00307.1000000007");

			final HUGraiSnapshot snapshot = loadVia(tu).get();

			assertThat(snapshot.getHuId()).isEqualTo(HuId.ofRepoId(123));
			assertThat(snapshot.getTus()).hasSize(1);
			assertThat(snapshot.getTus().get(0).getHuId()).isEqualTo(HuId.ofRepoId(123));
			assertThat(snapshot.getTus().get(0).getGrai()).isEqualTo(GRAI.ofCanonicalString("7613204.00307.1000000007"));
			assertThat(snapshot.getAggregateBlocks()).isEmpty();
		}
	}
}
