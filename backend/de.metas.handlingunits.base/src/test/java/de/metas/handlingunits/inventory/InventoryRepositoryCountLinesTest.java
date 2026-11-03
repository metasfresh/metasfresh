package de.metas.handlingunits.inventory;

import de.metas.product.ProductId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_InventoryLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class InventoryRepositoryCountLinesTest
{
	private InventoryRepository inventoryRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		inventoryRepository = new InventoryRepository();
	}

	@Test
	void countLinesByProductId_countsOnlyActiveLinesOfThatProduct()
	{
		final ProductId productId = ProductId.ofRepoId(1001);
		createLine(productId, true);
		createLine(productId, true);
		createLine(productId, false);
		createLine(ProductId.ofRepoId(1002), true);

		assertThat(inventoryRepository.countLinesByProductId(productId)).isEqualTo(2);
		assertThat(inventoryRepository.countLinesByProductId(ProductId.ofRepoId(1003))).isZero();
	}

	private static void createLine(final ProductId productId, final boolean active)
	{
		final I_M_InventoryLine line = newInstance(I_M_InventoryLine.class);
		line.setM_Product_ID(productId.getRepoId());
		line.setIsActive(active);
		saveRecord(line);
	}
}
