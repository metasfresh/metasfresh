package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WebuiHUTransformParametersFiller_DefaultLUPIItemTest
{
	@Test
	void productSpecificItemIsPreferredOverLowerIdGenericItem()
	{
		final I_M_HU_PI_Item generic = luPIItem(100, 0);
		final I_M_HU_PI_Item productSpecific = luPIItem(200, 555);

		assertThat(WebuiHUTransformParametersFiller.pickDefaultLUPIItem(ImmutableList.of(generic, productSpecific))).isSameAs(productSpecific);
		assertThat(WebuiHUTransformParametersFiller.pickDefaultLUPIItem(ImmutableList.of(productSpecific, generic))).isSameAs(productSpecific);
	}

	@Test
	void withoutProductSpecificItem_lowestIdWins()
	{
		final I_M_HU_PI_Item first = luPIItem(100, 0);
		final I_M_HU_PI_Item second = luPIItem(200, 0);

		assertThat(WebuiHUTransformParametersFiller.pickDefaultLUPIItem(ImmutableList.of(second, first))).isSameAs(first);
	}

	@Test
	void noItems_null()
	{
		assertThat(WebuiHUTransformParametersFiller.pickDefaultLUPIItem(ImmutableList.of())).isNull();
	}

	private static I_M_HU_PI_Item luPIItem(final int id, final int productId)
	{
		final I_M_HU_PI pi = mock(I_M_HU_PI.class);
		when(pi.isActive()).thenReturn(true);
		final I_M_HU_PI_Version version = mock(I_M_HU_PI_Version.class);
		when(version.isCurrent()).thenReturn(true);
		when(version.isActive()).thenReturn(true);
		when(version.getM_HU_PI()).thenReturn(pi);
		final I_M_HU_PI_Item item = mock(I_M_HU_PI_Item.class);
		when(item.getM_HU_PI_Item_ID()).thenReturn(id);
		when(item.getM_Product_ID()).thenReturn(productId);
		when(item.getM_HU_PI_Version()).thenReturn(version);
		return item;
	}
}
