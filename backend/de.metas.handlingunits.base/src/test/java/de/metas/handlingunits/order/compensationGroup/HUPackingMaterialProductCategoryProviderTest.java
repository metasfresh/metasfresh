package de.metas.handlingunits.order.compensationGroup;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.product.ProductCategoryId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;

import java.util.Arrays;
import java.util.List;

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
	private static final List<Class<?>> PACKING_INSTRUCTION_MODEL_CLASSES = Arrays.asList(I_M_HU_PI_Item_Product.class, I_M_HU_PI_Item.class, I_M_HU_PackingMaterial.class);

	private IQueryBL queryBLSpy;
	private HUPackingMaterialProductCategoryProvider provider;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// register the spy BEFORE the provider (and the DAO it gets via Services) is created, so their queryBL fields pick it up
		queryBLSpy = Mockito.spy(Services.get(IQueryBL.class));
		Services.registerService(IQueryBL.class, queryBLSpy);

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

	@Test
	void twoPIPs_categoriesNotMixedUp()
	{
		final ProductCategoryId root = category("root", null);
		final ProductCategoryId carton = category("carton", root);
		final ProductCategoryId foil = category("foil", null);

		final I_M_HU_PI_Item_Product pipA = pip();
		addPackingMaterialItem(pipA, product(carton), true);
		final I_M_HU_PI_Item_Product pipB = pip();
		addPackingMaterialItem(pipB, product(foil), true);

		final HUPIItemProductId idA = HUPIItemProductId.ofRepoId(pipA.getM_HU_PI_Item_Product_ID());
		final HUPIItemProductId idB = HUPIItemProductId.ofRepoId(pipB.getM_HU_PI_Item_Product_ID());
		final ImmutableMap<HUPIItemProductId, ImmutableSet<ProductCategoryId>> result = provider.getPackingMaterialProductCategoryIdsAndAncestors(ImmutableSet.of(idA, idB));

		assertThat(result.get(idA)).containsExactlyInAnyOrder(root, carton).doesNotContain(foil);
		assertThat(result.get(idB)).containsExactly(foil).doesNotContain(carton);
	}

	@Test
	void mixedRegularAndVirtual_onlyRegularHasEntry()
	{
		final ProductCategoryId carton = category("carton", null);
		final I_M_HU_PI_Item_Product pip = pip();
		addPackingMaterialItem(pip, product(carton), true);

		final HUPIItemProductId pipId = HUPIItemProductId.ofRepoId(pip.getM_HU_PI_Item_Product_ID());
		final ImmutableMap<HUPIItemProductId, ImmutableSet<ProductCategoryId>> result = provider.getPackingMaterialProductCategoryIdsAndAncestors(ImmutableSet.of(pipId, HUPIItemProductId.VIRTUAL_HU));

		assertThat(result.get(pipId)).containsExactly(carton);
		assertThat(result).doesNotContainKey(HUPIItemProductId.VIRTUAL_HU);
	}

	@Test
	void inactivePackingMaterial_ignored()
	{
		final ProductCategoryId carton = category("carton", null);
		final I_M_HU_PI_Item_Product pip = pip();
		addPackingMaterialItem(pip, product(carton), true, false);

		final HUPIItemProductId pipId = HUPIItemProductId.ofRepoId(pip.getM_HU_PI_Item_Product_ID());

		assertThat(provider.getPackingMaterialProductCategoryIdsAndAncestors(ImmutableSet.of(pipId))).doesNotContainKey(pipId);
	}

	/**
	 * Several packing instructions on one PI version (sharing a material item, or with their own material item) and one on another version.
	 * The inactive packing-material item and the inactive packing material on the shared version must be ignored for all its instructions.
	 */
	@Test
	void pipsOnSharedAndSeparateVersions_eachGetsItsVersionsActivePackingMaterials()
	{
		final ProductCategoryId carton = category("carton", null);
		final ProductCategoryId foil = category("foil", null);
		final ProductCategoryId pallet = category("pallet", null);
		final ProductCategoryId crate = category("crate", null);

		final I_M_HU_PI_Item_Product pipA = pip();
		addPackingMaterialItem(pipA, product(carton), true);
		addPackingMaterialItem(pipA, product(pallet), false); // inactive packing-material item
		addPackingMaterialItem(pipA, product(crate), true, false); // inactive packing material
		final I_M_HU_PI_Item_Product pipB = pipWithSameMaterialItem(pipA);
		final I_M_HU_PI_Item_Product pipC = pipWithOwnMaterialItemOnSameVersion(pipA);

		final I_M_HU_PI_Item_Product pipD = pip();
		addPackingMaterialItem(pipD, product(foil), true);

		final HUPIItemProductId idA = HUPIItemProductId.ofRepoId(pipA.getM_HU_PI_Item_Product_ID());
		final HUPIItemProductId idB = HUPIItemProductId.ofRepoId(pipB.getM_HU_PI_Item_Product_ID());
		final HUPIItemProductId idC = HUPIItemProductId.ofRepoId(pipC.getM_HU_PI_Item_Product_ID());
		final HUPIItemProductId idD = HUPIItemProductId.ofRepoId(pipD.getM_HU_PI_Item_Product_ID());
		final ImmutableMap<HUPIItemProductId, ImmutableSet<ProductCategoryId>> result = provider.getPackingMaterialProductCategoryIdsAndAncestors(ImmutableSet.of(idA, idB, idC, idD));

		assertThat(result).containsOnlyKeys(idA, idB, idC, idD);
		assertThat(result.get(idA)).containsExactly(carton);
		assertThat(result.get(idB)).containsExactly(carton);
		assertThat(result.get(idC)).containsExactly(carton);
		assertThat(result.get(idD)).containsExactly(foil);
	}

	/**
	 * The packing-instruction lookups (M_HU_PI_Item_Product, M_HU_PI_Item, M_HU_PackingMaterial) are batched:
	 * resolving three packing instructions issues no more such queries than resolving one.
	 */
	@Test
	void packingInstructionQueries_doNotGrowWithNumberOfPackingInstructions()
	{
		final I_M_Product cartonProduct = product(category("carton", null));
		final I_M_HU_PI_Item_Product pipA = pip();
		addPackingMaterialItem(pipA, cartonProduct, true);
		final I_M_HU_PI_Item_Product pipB = pip();
		addPackingMaterialItem(pipB, cartonProduct, true);
		final I_M_HU_PI_Item_Product pipC = pipWithSameMaterialItem(pipA);

		final HUPIItemProductId idA = HUPIItemProductId.ofRepoId(pipA.getM_HU_PI_Item_Product_ID());
		final HUPIItemProductId idB = HUPIItemProductId.ofRepoId(pipB.getM_HU_PI_Item_Product_ID());
		final HUPIItemProductId idC = HUPIItemProductId.ofRepoId(pipC.getM_HU_PI_Item_Product_ID());

		final long queriesForOne = countPackingInstructionQueries(() -> assertThat(provider.getPackingMaterialProductCategoryIdsAndAncestors(ImmutableSet.of(idA))).hasSize(1));
		final long queriesForThree = countPackingInstructionQueries(() -> assertThat(provider.getPackingMaterialProductCategoryIdsAndAncestors(ImmutableSet.of(idA, idB, idC))).hasSize(3));

		assertThat(queriesForOne).isPositive();
		assertThat(queriesForThree).isEqualTo(queriesForOne);
	}

	private long countPackingInstructionQueries(final Runnable runnable)
	{
		Mockito.clearInvocations(queryBLSpy);
		runnable.run();
		return Mockito.mockingDetails(queryBLSpy).getInvocations().stream()
				.filter(invocation -> invocation.getMethod().getName().startsWith("createQueryBuilder"))
				.filter(HUPackingMaterialProductCategoryProviderTest::isOnPackingInstructionModel)
				.count();
	}

	private static boolean isOnPackingInstructionModel(final Invocation invocation)
	{
		return Arrays.stream(invocation.getArguments()).anyMatch(PACKING_INSTRUCTION_MODEL_CLASSES::contains);
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

	private int nextPIVersionId = 1;

	/** Each packing instruction gets its own PI version, so its packing-material items are not shared with other instructions. */
	private I_M_HU_PI_Item_Product pip()
	{
		final I_M_HU_PI_Item materialItem = newInstance(I_M_HU_PI_Item.class);
		materialItem.setM_HU_PI_Version_ID(nextPIVersionId++);
		materialItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_Material);
		saveRecord(materialItem);

		final I_M_HU_PI_Item_Product pip = newInstance(I_M_HU_PI_Item_Product.class);
		pip.setM_HU_PI_Item_ID(materialItem.getM_HU_PI_Item_ID());
		saveRecord(pip);
		return pip;
	}

	/** Another packing instruction on the same material item, e.g. the same PI for a different product. */
	private I_M_HU_PI_Item_Product pipWithSameMaterialItem(final I_M_HU_PI_Item_Product other)
	{
		final I_M_HU_PI_Item_Product pip = newInstance(I_M_HU_PI_Item_Product.class);
		pip.setM_HU_PI_Item_ID(other.getM_HU_PI_Item_ID());
		saveRecord(pip);
		return pip;
	}

	/** Another packing instruction with its own material item, but on the same PI version as {@code other}. */
	private I_M_HU_PI_Item_Product pipWithOwnMaterialItemOnSameVersion(final I_M_HU_PI_Item_Product other)
	{
		final I_M_HU_PI_Item materialItem = newInstance(I_M_HU_PI_Item.class);
		materialItem.setM_HU_PI_Version_ID(other.getM_HU_PI_Item().getM_HU_PI_Version_ID());
		materialItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_Material);
		saveRecord(materialItem);

		final I_M_HU_PI_Item_Product pip = newInstance(I_M_HU_PI_Item_Product.class);
		pip.setM_HU_PI_Item_ID(materialItem.getM_HU_PI_Item_ID());
		saveRecord(pip);
		return pip;
	}

	private void addPackingMaterialItem(final I_M_HU_PI_Item_Product pip, final I_M_Product product, final boolean active)
	{
		addPackingMaterialItem(pip, product, active, true);
	}

	private void addPackingMaterialItem(final I_M_HU_PI_Item_Product pip, final I_M_Product product, final boolean itemActive, final boolean packingMaterialActive)
	{
		final I_M_HU_PackingMaterial packingMaterial = newInstance(I_M_HU_PackingMaterial.class);
		packingMaterial.setName("pm");
		packingMaterial.setIsActive(packingMaterialActive);
		if (product != null)
		{
			packingMaterial.setM_Product_ID(product.getM_Product_ID());
		}
		saveRecord(packingMaterial);

		final I_M_HU_PI_Item item = newInstance(I_M_HU_PI_Item.class);
		item.setM_HU_PI_Version_ID(pip.getM_HU_PI_Item().getM_HU_PI_Version_ID());
		item.setItemType(X_M_HU_PI_Item.ITEMTYPE_PackingMaterial);
		item.setM_HU_PackingMaterial_ID(packingMaterial.getM_HU_PackingMaterial_ID());
		item.setIsActive(itemActive);
		saveRecord(item);
	}
}
