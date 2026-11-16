package de.metas.manufacturing.workflows_api.activity_handlers.receive;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MaterialReceiptActivityHandlerLUTargetsTest
{
	private static final int LU_PI_1 = 1;
	private static final int LU_PI_2 = 2;
	private static final int PRODUCT_XY = 100;

	private static List<I_M_HU_PI_Item> filter(final I_M_HU_PI_Item... items)
	{
		return MaterialReceiptActivityHandler.removeGenericItemsCompetingWithProductSpecificOnes(
				ImmutableList.copyOf(items),
				item -> item.getM_HU_PI_Version_ID()); // the test uses the version ID as LU PI ID
	}

	private static I_M_HU_PI_Item item(final int itemId, final int luPIId, final int productId)
	{
		final I_M_HU_PI_Item item = org.mockito.Mockito.mock(I_M_HU_PI_Item.class);
		org.mockito.Mockito.when(item.getM_HU_PI_Item_ID()).thenReturn(itemId);
		org.mockito.Mockito.when(item.getM_HU_PI_Version_ID()).thenReturn(luPIId);
		org.mockito.Mockito.when(item.getM_Product_ID()).thenReturn(productId);
		return item;
	}

	@Test
	void productSpecificItem_hidesGenericItemOfSameLUPI()
	{
		final I_M_HU_PI_Item specific = item(11, LU_PI_1, PRODUCT_XY);
		final I_M_HU_PI_Item generic = item(10, LU_PI_1, 0);

		assertThat(filter(specific, generic)).containsExactly(specific);
	}

	@Test
	void noProductSpecificItem_listIsUnchanged()
	{
		final I_M_HU_PI_Item generic1 = item(10, LU_PI_1, 0);
		final I_M_HU_PI_Item generic2 = item(20, LU_PI_2, 0);

		assertThat(filter(generic1, generic2)).containsExactly(generic1, generic2);
	}

	@Test
	void productSpecificItem_doesNotHideGenericItemOfOtherLUPI()
	{
		final I_M_HU_PI_Item specific = item(11, LU_PI_1, PRODUCT_XY);
		final I_M_HU_PI_Item genericOfOtherLU = item(20, LU_PI_2, 0);

		assertThat(filter(specific, genericOfOtherLU)).containsExactly(specific, genericOfOtherLU);
	}
}
