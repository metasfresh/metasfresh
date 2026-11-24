package de.metas.handlingunits.impl;

import javax.annotation.Nullable;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
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
		genericItem30 = createItem(new BigDecimal("30"), null, null);
		productItem40 = createItem(new BigDecimal("40"), null, productXY);
	}

	/**
	 * Unset IDs are stored as {@code NULL} (like the generated {@code X_} setters do in the real DB), not as {@code -1},
	 * so the in-memory {@code ORDER BY ... NULLS FIRST/LAST} behaves like Postgres.
	 */
	private I_M_HU_PI_Item createItem(final BigDecimal qty, @Nullable final BPartnerId bpartnerId, @Nullable final ProductId productId)
	{
		final I_M_HU_PI_Item item = helper.createHU_PI_Item_IncludedHU(luPI, tuPI, qty, bpartnerId);
		InterfaceWrapperHelper.setValue(item, I_M_HU_PI_Item.COLUMNNAME_C_BPartner_ID, bpartnerId != null ? bpartnerId.getRepoId() : null);
		InterfaceWrapperHelper.setValue(item, I_M_HU_PI_Item.COLUMNNAME_M_Product_ID, productId != null ? productId.getRepoId() : null);
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
		// same filter and order as retrieveParentPIItemsForParentPI: the LU item is the partner's or partner-less; product, then partner, then item ID first
		assertThat(dao.getTUPIItemForLUPIAndItemProduct(bpartner, luPIId, itemProductId, otherProduct)).contains(partnerItem);
		assertThat(dao.getTUPIItemForLUPIAndItemProduct(bpartner, luPIId, itemProductId)).contains(partnerItem);
		// the LU item of another partner is not eligible
		final BPartnerId otherPartner = BPartnerId.ofRepoId(createBPartner("BP2").getC_BPartner_ID());
		assertThat(dao.getTUPIItemForLUPIAndItemProduct(otherPartner, luPIId, itemProductId, otherProduct)).contains(genericItem30);
		assertThat(dao.getTUPIItemForLUPIAndItemProduct(null, luPIId, itemProductId, otherProduct)).contains(genericItem30);
	}

	// ---- retrieveParentPIItemForChildHUOrNull

	private I_M_HU createLUHU()
	{
		final I_M_HU luHU = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		luHU.setM_HU_PI_Version_ID(dao.retrievePICurrentVersion(luPI).getM_HU_PI_Version_ID());
		InterfaceWrapperHelper.save(luHU);
		return luHU;
	}

	private I_M_HU_PI_Item parentPIItemForChild(final I_M_HU luHU)
	{
		return dao.retrieveParentPIItemForChildHUOrNull(luHU, tuPI, helper.getHUContext());
	}

	@Test
	void parentPIItemForChildHU_luWithoutItems_picksGeneric_neverProductItem()
	{
		assertThat(parentPIItemForChild(createLUHU())).isEqualTo(genericItem30);
	}

	@Test
	void parentPIItemForChildHU_luBuiltFromProductItem_keepsProductItem()
	{
		final I_M_HU luHU = createLUHU();
		dao.createHUItem(luHU, productItem40);
		assertThat(parentPIItemForChild(luHU)).isEqualTo(productItem40);
	}

	@Test
	void parentPIItemForChildHU_luBuiltFromGenericItem_keepsGenericItem()
	{
		final I_M_HU luHU = createLUHU();
		dao.createHUItem(luHU, genericItem30);
		assertThat(parentPIItemForChild(luHU)).isEqualTo(genericItem30);
	}

	/**
	 * Pins the pre-existing order: {@code C_BPartner_ID DESC} with the query builder's default {@code NULLS FIRST} for descending order,
	 * i.e. the generic item comes before the partner-specific one.
	 */
	@Test
	void parentPIItemForChildHU_partnerVsGenericOrderUnchanged()
	{
		final I_M_HU luHU = createLUHU();
		luHU.setC_BPartner_ID(bpartner.getRepoId());
		InterfaceWrapperHelper.save(luHU);
		partnerItem35();

		assertThat(parentPIItemForChild(luHU)).isEqualTo(genericItem30);
	}

	@Test
	void parentPIItemForChildHU_luWithProductLessExistingItem_sameAsBefore()
	{
		final I_M_HU luHU = createLUHU();
		luHU.setC_BPartner_ID(bpartner.getRepoId());
		InterfaceWrapperHelper.save(luHU);
		final I_M_HU_PI_Item partnerItem = partnerItem35();
		dao.createHUItem(luHU, partnerItem);

		// only an existing *product-specific* item is kept; product-less data is resolved by the query as before
		assertThat(parentPIItemForChild(luHU)).isEqualTo(genericItem30);
	}

	@Test
	void parentPIItemForChildHU_noProductItems_sameAsBefore()
	{
		InterfaceWrapperHelper.delete(productItem40);
		assertThat(parentPIItemForChild(createLUHU())).isEqualTo(genericItem30);
	}

	private void createAggregateItem(final I_M_HU luHU, final I_M_HU_PI_Item piItem)
	{
		final I_M_HU_Item haItem = dao.createAggregateHUItem(luHU);
		haItem.setM_HU_PI_Item(piItem);
		InterfaceWrapperHelper.save(haItem);
	}

	@Test
	void parentPIItemForChildHU_luWithAggregateItemFromProductItem_keepsProductItem()
	{
		final I_M_HU luHU = createLUHU();
		createAggregateItem(luHU, productItem40);
		assertThat(parentPIItemForChild(luHU)).isEqualTo(productItem40);
	}

	@Test
	void parentPIItemForChildHU_luWithAggregateItemFromGenericItem_sameAsBefore()
	{
		final I_M_HU luHU = createLUHU();
		luHU.setC_BPartner_ID(bpartner.getRepoId());
		InterfaceWrapperHelper.save(luHU);
		final I_M_HU_PI_Item partnerItem = partnerItem35();
		createAggregateItem(luHU, partnerItem);

		// not product-specific => resolved by the query as before (generic before partner-specific)
		assertThat(parentPIItemForChild(luHU)).isEqualTo(genericItem30);
	}

	// ---- retrieveParentPIItemForChildHUAllowingProductSpecificOrNull

	private I_M_HU_PI_Item parentPIItemForChildAllowingProductSpecific(final I_M_HU luHU, @Nullable final ProductId childProductId)
	{
		return dao.retrieveParentPIItemForChildHUAllowingProductSpecificOrNull(luHU, tuPI, childProductId, helper.getHUContext());
	}

	@Test
	void parentPIItemAllowingProductSpecific_genericExists_picksGeneric()
	{
		assertThat(parentPIItemForChildAllowingProductSpecific(createLUHU(), productXY)).isEqualTo(genericItem30);
	}

	@Test
	void parentPIItemAllowingProductSpecific_luBuiltFromProductItem_keepsProductItem()
	{
		final I_M_HU luHU = createLUHU();
		dao.createHUItem(luHU, productItem40);
		assertThat(parentPIItemForChildAllowingProductSpecific(luHU, otherProduct)).isEqualTo(productItem40);
	}

	@Test
	void parentPIItemAllowingProductSpecific_noGeneric_childOfThatProduct_picksProductItem()
	{
		InterfaceWrapperHelper.delete(genericItem30);
		assertThat(parentPIItemForChildAllowingProductSpecific(createLUHU(), productXY)).isEqualTo(productItem40);
	}

	@Test
	void parentPIItemAllowingProductSpecific_noGeneric_childOfOtherProduct_picksProductItem()
	{
		InterfaceWrapperHelper.delete(genericItem30);
		assertThat(parentPIItemForChildAllowingProductSpecific(createLUHU(), otherProduct)).isEqualTo(productItem40);
		assertThat(parentPIItemForChildAllowingProductSpecific(createLUHU(), null)).isEqualTo(productItem40);
	}

	@Test
	void parentPIItemAllowingProductSpecific_noGeneric_twoProductItems_picksTheChildsProductItem()
	{
		InterfaceWrapperHelper.delete(genericItem30);
		final I_M_HU_PI_Item otherProductItem50 = createItem(new BigDecimal("50"), null, otherProduct);

		assertThat(parentPIItemForChildAllowingProductSpecific(createLUHU(), otherProduct)).isEqualTo(otherProductItem50);
		assertThat(parentPIItemForChildAllowingProductSpecific(createLUHU(), productXY)).isEqualTo(productItem40);
		assertThat(parentPIItemForChildAllowingProductSpecific(createLUHU(), null)).as("no product: lowest ID").isEqualTo(productItem40);
	}

	@Test
	void parentPIItemAllowingProductSpecific_noGeneric_partnerSpecificProductItemOfOtherPartner_notEligible()
	{
		InterfaceWrapperHelper.delete(genericItem30);
		InterfaceWrapperHelper.delete(productItem40);
		createItem(new BigDecimal("45"), bpartner, productXY);

		assertThat(parentPIItemForChildAllowingProductSpecific(createLUHU(), productXY)).isNull();
	}

	@Test
	void parentPIItemAllowingProductSpecific_noItemAtAll_null()
	{
		InterfaceWrapperHelper.delete(genericItem30);
		InterfaceWrapperHelper.delete(productItem40);
		assertThat(parentPIItemForChildAllowingProductSpecific(createLUHU(), productXY)).isNull();
	}
}
