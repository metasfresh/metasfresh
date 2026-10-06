package de.metas.handlingunits.order.compensationGroup;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.product.ProductCategoryId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

class HUPackingMaterialProductCategoryProviderTest
{
	private HUPackingMaterialProductCategoryProvider provider;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		provider = new HUPackingMaterialProductCategoryProvider();
	}

	/** Review focus: virtual packing instruction yields no entry and no exception; the virtual record does not even exist in this DB. */
	@Test
	void virtualPI_noEntry_noQuery()
	{
		final ImmutableMap<HUPIItemProductId, ImmutableSet<ProductCategoryId>> result = provider.getPackingMaterialProductCategoryIdsAndAncestors(
				ImmutableSet.of(HUPIItemProductId.VIRTUAL_HU, HUPIItemProductId.TEMPLATE_HU));

		assertThat(result).isEmpty();
	}

	@Test
	void twoPMItems_unionOfCategoriesAndAncestors()
	{
		final ProductCategoryId root = category("root", null);
		final ProductCategoryId carton = category("carton", root);
		final ProductCategoryId foil = category("foil", null);

		final I_M_HU_PI_Item_Product pip = pip();
		addPackingMaterialItem(pip, product(carton), true);
		addPackingMaterialItem(pip, product(foil), true);

		final HUPIItemProductId pipId = HUPIItemProductId.ofRepoId(pip.getM_HU_PI_Item_Product_ID());
		final ImmutableMap<HUPIItemProductId, ImmutableSet<ProductCategoryId>> result = provider.getPackingMaterialProductCategoryIdsAndAncestors(ImmutableSet.of(pipId));

		assertThat(result.get(pipId)).containsExactlyInAnyOrder(root, carton, foil);
	}

	/** Review focus: an inactive PM item is ignored. */
	@Test
	void inactivePMItem_ignored()
	{
		final ProductCategoryId carton = category("carton", null);
		final I_M_HU_PI_Item_Product pip = pip();
		addPackingMaterialItem(pip, product(carton), false);

		final HUPIItemProductId pipId = HUPIItemProductId.ofRepoId(pip.getM_HU_PI_Item_Product_ID());

		assertThat(provider.getPackingMaterialProductCategoryIdsAndAncestors(ImmutableSet.of(pipId))).doesNotContainKey(pipId);
	}

	@Test
	void pmWithoutProduct_noCategory()
	{
		final I_M_HU_PI_Item_Product pip = pip();
		addPackingMaterialItem(pip, null, true);

		final HUPIItemProductId pipId = HUPIItemProductId.ofRepoId(pip.getM_HU_PI_Item_Product_ID());

		assertThat(provider.getPackingMaterialProductCategoryIdsAndAncestors(ImmutableSet.of(pipId))).doesNotContainKey(pipId);
	}

	private ProductCategoryId category(final String name, final ProductCategoryId parentId)
	{
		final I_M_Product_Category record = newInstance(I_M_Product_Category.class);
		record.setName(name);
		record.setValue(name);
		record.setM_Product_Category_Parent_ID(parentId != null ? parentId.getRepoId() : 0);
		saveRecord(record);
		return ProductCategoryId.ofRepoId(record.getM_Product_Category_ID());
	}

	private I_M_Product product(final ProductCategoryId categoryId)
	{
		final I_M_Product record = newInstance(I_M_Product.class);
		record.setM_Product_Category_ID(categoryId.getRepoId());
		saveRecord(record);
		return record;
	}

	private I_M_HU_PI_Item_Product pip()
	{
		final I_M_HU_PI_Item materialItem = newInstance(I_M_HU_PI_Item.class);
		materialItem.setM_HU_PI_Version_ID(1);
		materialItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_Material);
		saveRecord(materialItem);

		final I_M_HU_PI_Item_Product pip = newInstance(I_M_HU_PI_Item_Product.class);
		pip.setM_HU_PI_Item_ID(materialItem.getM_HU_PI_Item_ID());
		saveRecord(pip);
		return pip;
	}

	private void addPackingMaterialItem(final I_M_HU_PI_Item_Product pip, final I_M_Product product, final boolean active)
	{
		final I_M_HU_PackingMaterial packingMaterial = newInstance(I_M_HU_PackingMaterial.class);
		packingMaterial.setName("pm");
		if (product != null)
		{
			packingMaterial.setM_Product_ID(product.getM_Product_ID());
		}
		saveRecord(packingMaterial);

		final I_M_HU_PI_Item item = newInstance(I_M_HU_PI_Item.class);
		item.setM_HU_PI_Version_ID(pip.getM_HU_PI_Item().getM_HU_PI_Version_ID());
		item.setItemType(X_M_HU_PI_Item.ITEMTYPE_PackingMaterial);
		item.setM_HU_PackingMaterial_ID(packingMaterial.getM_HU_PackingMaterial_ID());
		item.setIsActive(active);
		saveRecord(item);
	}
}
