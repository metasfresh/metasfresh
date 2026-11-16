package de.metas.handlingunits.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static de.metas.business.BusinessTestHelper.createBPartner;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * LU->TU item selection is product-aware: product-match > partner-match > generic; other products are excluded;
 * a caller without product never gets a product-specific item.
 */
public class HandlingUnitsDAO_ProductSelectionTest extends AbstractHUTest
{
	private static final String LU_UNIT_TYPE = X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit;

	private HandlingUnitsDAO dao;

	private BPartnerId bpartner;
	private ProductId productXY;
	private ProductId otherProduct;

	private I_M_HU_PI tuPI;
	private I_M_HU_PI luPI;
	private I_M_HU_PI_Item_Product tuPIItemProduct;
	private I_M_HU_PI_Item genericItem30;
	private I_M_HU_PI_Item productItem40;

	@Override
	protected void initialize()
	{
		dao = new HandlingUnitsDAO();
		Services.registerService(IHandlingUnitsDAO.class, dao);

		bpartner = BPartnerId.ofRepoId(createBPartner("BP1").getC_BPartner_ID());
		productXY = pTomatoId;
		otherProduct = pSaladId;

		tuPI = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		tuPIItemProduct = helper.assignProduct(helper.createHU_PI_Item_Material(tuPI), productXY, BigDecimal.TEN, uomEach);

		luPI = helper.createHUDefinition("LU", LU_UNIT_TYPE);
		genericItem30 = helper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("30"), (BPartnerId)null);
		productItem40 = createItem(new BigDecimal("40"), null, productXY);
	}

	private I_M_HU_PI_Item createItem(final BigDecimal qty, final BPartnerId bpartnerId, final ProductId productId)
	{
		final I_M_HU_PI_Item item = helper.createHU_PI_Item_IncludedHU(luPI, tuPI, qty, bpartnerId);
		item.setM_Product_ID(ProductId.toRepoId(productId));
		InterfaceWrapperHelper.save(item);
		return item;
	}

	private I_M_HU_PI_Item partnerItem35()
	{
		return createItem(new BigDecimal("35"), bpartner, null);
	}

	private I_M_HU_PI_Item defaultItem(final BPartnerId bpartnerId, final ProductId productId)
	{
		return dao.retrieveDefaultParentPIItem(tuPI, LU_UNIT_TYPE, bpartnerId, productId);
	}

	// ---- retrieveDefaultParentPIItem

	@Test
	void default_productXY_picksProductItem()
	{
		assertThat(defaultItem(null, productXY)).isEqualTo(productItem40);
		assertThat(defaultItem(bpartner, productXY)).isEqualTo(productItem40);
	}

	@Test
	void default_otherProduct_picksGeneric()
	{
		assertThat(defaultItem(null, otherProduct)).isEqualTo(genericItem30);
	}

	@Test
	void default_productBeatsPartner()
	{
		partnerItem35();
		assertThat(defaultItem(bpartner, productXY)).isEqualTo(productItem40);
	}

	@Test
	void default_noProductItem_partnerBeatsGeneric()
	{
		final I_M_HU_PI_Item partnerItem = partnerItem35();
		assertThat(defaultItem(bpartner, otherProduct)).isEqualTo(partnerItem);
		assertThat(defaultItem(null, otherProduct)).isEqualTo(genericItem30);
	}

	@Test
	void default_productLessCaller_neverPicksProductItem_newAndOldSignature()
	{
		assertThat(defaultItem(null, null)).isEqualTo(genericItem30);
		assertThat(dao.retrieveDefaultParentPIItem(tuPI, LU_UNIT_TYPE, null)).isEqualTo(genericItem30);
		assertThat(dao.retrieveDefaultParentPIItemId(tuPI, LU_UNIT_TYPE, null)).isPresent();
	}

	@Test
	void default_productLessCaller_onlyProductItemExists_returnsNull()
	{
		InterfaceWrapperHelper.delete(genericItem30);
		assertThat(defaultItem(null, null)).isNull();
		assertThat(dao.retrieveDefaultParentPIItem(tuPI, LU_UNIT_TYPE, null)).isNull();
	}

	@Test
	void default_noProductSpecificItem_sameAsToday()
	{
		InterfaceWrapperHelper.delete(productItem40);
		assertThat(defaultItem(null, productXY)).isEqualTo(genericItem30);
		assertThat(defaultItem(bpartner, productXY)).isEqualTo(genericItem30);
	}

	// ---- retrieveParentPIItemsForParentPI

	@Test
	void parentPIItems_orderAndFilter()
	{
		final HuPackingInstructionsId tuPIId = HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID());

		assertThat(dao.retrieveParentPIItemsForParentPI(tuPIId, LU_UNIT_TYPE, null, productXY)).containsExactly(productItem40);
		assertThat(dao.retrieveParentPIItemsForParentPI(tuPIId, LU_UNIT_TYPE, null, otherProduct)).containsExactly(genericItem30);
		assertThat(dao.retrieveParentPIItemsForParentPI(tuPIId, LU_UNIT_TYPE, null, null)).containsExactly(genericItem30);
		assertThat(dao.retrieveParentPIItemsForParentPI(tuPIId, LU_UNIT_TYPE, null)).containsExactly(genericItem30);
		assertThat(dao.retrieveParentPIItemsForParentPI(tuPI, LU_UNIT_TYPE, null)).containsExactly(genericItem30);
		assertThat(dao.retrieveParentPIItemsForParentPI(tuPI, LU_UNIT_TYPE, null, productXY)).containsExactly(productItem40);
	}

	@Test
	void parentPIItems_productFirstThenPartner_acrossLUPIs()
	{
		// a second LU PI, so that no per-version de-duplication hides the ordering
		final I_M_HU_PI luPI2 = helper.createHUDefinition("LU2", LU_UNIT_TYPE);
		final I_M_HU_PI_Item partnerItemLU2 = helper.createHU_PI_Item_IncludedHU(luPI2, tuPI, new BigDecimal("35"), bpartner);
		final HuPackingInstructionsId tuPIId = HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID());

		final List<I_M_HU_PI_Item> items = dao.retrieveParentPIItemsForParentPI(tuPIId, LU_UNIT_TYPE, bpartner, productXY);
		assertThat(items).containsExactly(productItem40, partnerItemLU2);

		final List<I_M_HU_PI_Item> noProduct = dao.retrieveParentPIItemsForParentPI(tuPIId, LU_UNIT_TYPE, bpartner, null);
		assertThat(noProduct.stream().map(I_M_HU_PI_Item::getM_HU_PI_Item_ID).collect(Collectors.toList()))
				.containsExactly(partnerItemLU2.getM_HU_PI_Item_ID(), genericItem30.getM_HU_PI_Item_ID());
	}

	// ---- retrieveFirstPIItem

	@Test
	void firstPIItem()
	{
		final HuPackingInstructionsId luPIId = HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID());
		final HuPackingInstructionsId tuPIId = HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID());

		assertThat(dao.retrieveFirstPIItem(luPIId, tuPIId, null, productXY)).contains(productItem40);
		assertThat(dao.retrieveFirstPIItem(luPIId, tuPIId, null, otherProduct)).contains(genericItem30);
		assertThat(dao.retrieveFirstPIItem(luPIId, tuPIId, null, null)).contains(genericItem30);
		assertThat(dao.retrieveFirstPIItem(luPIId, tuPIId, null)).contains(genericItem30);

		// retrieveFirstPIItem keeps its pre-existing (non partner-aware) order: the first eligible item of the version
		partnerItem35();
		assertThat(dao.retrieveFirstPIItem(luPIId, tuPIId, bpartner, productXY)).contains(productItem40);
		assertThat(dao.retrieveFirstPIItem(luPIId, tuPIId, bpartner, otherProduct)).contains(genericItem30);
		assertThat(dao.retrieveFirstPIItem(luPIId, tuPIId, bpartner)).contains(genericItem30);
	}

	// ---- getTUPIItemForLUPIAndItemProduct

	@Test
	void tuPIItemForLUPIAndItemProduct()
	{
		final HuPackingInstructionsId luPIId = HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID());
		final HUPIItemProductId itemProductId = HUPIItemProductId.ofRepoId(tuPIItemProduct.getM_HU_PI_Item_Product_ID());

		assertThat(dao.getTUPIItemForLUPIAndItemProduct(null, luPIId, itemProductId, productXY)).contains(productItem40);
		assertThat(dao.getTUPIItemForLUPIAndItemProduct(null, luPIId, itemProductId, otherProduct)).contains(genericItem30);
		assertThat(dao.getTUPIItemForLUPIAndItemProduct(null, luPIId, itemProductId, null)).contains(genericItem30);
		assertThat(dao.getTUPIItemForLUPIAndItemProduct(null, luPIId, itemProductId)).contains(genericItem30);

		final I_M_HU_PI_Item partnerItem = partnerItem35();
		assertThat(dao.getTUPIItemForLUPIAndItemProduct(bpartner, luPIId, itemProductId, productXY)).contains(productItem40);
		assertThat(dao.getTUPIItemForLUPIAndItemProduct(bpartner, luPIId, itemProductId, otherProduct)).contains(partnerItem);
	}
}
