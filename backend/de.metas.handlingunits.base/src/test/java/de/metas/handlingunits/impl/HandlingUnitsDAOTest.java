package de.metas.handlingunits.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static de.metas.business.BusinessTestHelper.createBPartner;

public class HandlingUnitsDAOTest extends AbstractHUTest
{
	protected HandlingUnitsDAO dao;

	@Override
	protected void initialize()
	{
		dao = new HandlingUnitsDAO();
		Services.registerService(IHandlingUnitsDAO.class, dao);
	}

	@Test
	public void test_retrieveItems_from_HU()
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, helper.contextProvider);
		InterfaceWrapperHelper.save(hu);

		final I_M_HU_Item huItem = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class, helper.contextProvider);
		huItem.setM_HU(hu);
		InterfaceWrapperHelper.save(huItem);

		final List<I_M_HU_Item> items = dao.retrieveItems(hu);

		Assertions.assertEquals(1, items.size(), "Invalid size: " + items);
		Assertions.assertEquals(huItem.getM_HU_Item_ID(), items.get(0).getM_HU_Item_ID(), "Invalid item: " + items);
	}

	@Test
	public void test_retrivePIItems()
	{
		final BPartnerId bpartner_NULL = null;
		final BPartnerId bpartner1 = BPartnerId.ofRepoId(createBPartner("BP1").getC_BPartner_ID());
		final BPartnerId bpartner2 = BPartnerId.ofRepoId(createBPartner("BP2").getC_BPartner_ID());
		final BPartnerId bpartner3 = BPartnerId.ofRepoId(createBPartner("BP3").getC_BPartner_ID());
		final I_M_HU_PI piIncluded = null; // not relevant
		final I_M_HU_PI pi = helper.createHUDefinition("PI", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);

		final I_M_HU_PI_Item item1 = helper.createHU_PI_Item_IncludedHU(pi, piIncluded, BigDecimal.ONE, bpartner_NULL);
		final I_M_HU_PI_Item item2 = helper.createHU_PI_Item_IncludedHU(pi, piIncluded, BigDecimal.ONE, bpartner1);
		final I_M_HU_PI_Item item3 = helper.createHU_PI_Item_IncludedHU(pi, piIncluded, BigDecimal.ONE, bpartner2);
		final I_M_HU_PI_Item item4 = helper.createHU_PI_Item_IncludedHU(pi, piIncluded, BigDecimal.ONE, bpartner_NULL);

		Assertions.assertEquals(Arrays.asList(item1, item4)
				, dao.retrievePIItems(pi, bpartner_NULL)
				, "Invalid result for BPartner=" + bpartner_NULL
		);

		Assertions.assertEquals(Arrays.asList(item1, item2, item4)
				, dao.retrievePIItems(pi, bpartner1)
				, "Invalid result for BPartner=" + bpartner1);

		Assertions.assertEquals(Arrays.asList(item1, item3, item4)
				, dao.retrievePIItems(pi, bpartner2)
				, "Invalid result for BPartner=" + bpartner2);

		Assertions.assertEquals(Arrays.asList(item1, item4) // same as for bpartner_NULL
				, dao.retrievePIItems(pi, bpartner3)
				, "Invalid result for BPartner=" + bpartner3);
	}

	@Test
	public void countActiveHUsByProductId_countsEachActiveHUHoldingTheProductOnce()
	{
		final ProductId productId = ProductId.ofRepoId(1001);
		final ProductId otherProductId = ProductId.ofRepoId(1002);

		final I_M_HU huWithProduct = createHU(true);
		createStorage(huWithProduct, productId, true);

		final I_M_HU huWithProductAndOtherProduct = createHU(true);
		createStorage(huWithProductAndOtherProduct, productId, true);
		createStorage(huWithProductAndOtherProduct, otherProductId, true);

		final I_M_HU huWithOtherProductOnly = createHU(true);
		createStorage(huWithOtherProductOnly, otherProductId, true);

		final I_M_HU inactiveHU = createHU(false);
		createStorage(inactiveHU, productId, true);

		final I_M_HU huWithInactiveStorage = createHU(true);
		createStorage(huWithInactiveStorage, productId, false);

		Assertions.assertEquals(2, dao.countActiveHUsByProductId(productId));
		Assertions.assertEquals(2, dao.countActiveHUsByProductId(otherProductId));
		Assertions.assertEquals(0, dao.countActiveHUsByProductId(ProductId.ofRepoId(1003)));
	}

	@Test
	public void countActiveHUsByProductId_countsIncludedHUsToo()
	{
		final ProductId productId = ProductId.ofRepoId(1001);

		final I_M_HU lu = createHU(true);
		createStorage(lu, productId, true);

		final I_M_HU_Item luItem = InterfaceWrapperHelper.newInstance(I_M_HU_Item.class, helper.contextProvider);
		luItem.setM_HU_ID(lu.getM_HU_ID());
		InterfaceWrapperHelper.save(luItem);

		final I_M_HU includedTU = createHU(true);
		includedTU.setM_HU_Item_Parent_ID(luItem.getM_HU_Item_ID());
		InterfaceWrapperHelper.save(includedTU);
		createStorage(includedTU, productId, true);

		Assertions.assertEquals(2, dao.countActiveHUsByProductId(productId), "the LU and its included TU both hold the product");
	}

	private I_M_HU createHU(final boolean active)
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, helper.contextProvider);
		hu.setIsActive(active);
		InterfaceWrapperHelper.save(hu);
		return hu;
	}

	private void createStorage(final I_M_HU hu, final ProductId productId, final boolean active)
	{
		final I_M_HU_Storage storage = InterfaceWrapperHelper.newInstance(I_M_HU_Storage.class, helper.contextProvider);
		storage.setM_HU_ID(hu.getM_HU_ID());
		storage.setM_Product_ID(productId.getRepoId());
		storage.setIsActive(active);
		InterfaceWrapperHelper.save(storage);
	}
}
