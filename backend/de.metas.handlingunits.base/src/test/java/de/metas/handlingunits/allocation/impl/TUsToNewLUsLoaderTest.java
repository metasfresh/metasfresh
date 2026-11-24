package de.metas.handlingunits.allocation.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link TUsToNewLUsLoader} loads TUs onto new LUs: TUs whose pallet item is product-specific go onto pallets of that item,
 * one set of pallets per product; all other TUs share the generic pallets as {@link LULoader} does.
 * Real data shape: pallet with a generic item 30 and an XY item 40, TU of 4 pieces.
 */
class TUsToNewLUsLoaderTest extends AbstractHUTest
{
	private static final BigDecimal PIECES_PER_TU = new BigDecimal("4");

	private ProductId productXY;
	private ProductId productOther;

	private I_M_HU_PI tuPI;
	private I_M_HU_PI luPI;
	private I_M_HU_PI_Item genericItem30;

	private IHandlingUnitsBL handlingUnitsBL;
	private IHandlingUnitsDAO handlingUnitsDAO;

	@Override
	protected void initialize()
	{
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		productXY = pTomatoId;
		productOther = pSaladId;

		tuPI = createTUPI("IFCO");
		luPI = helper.createHUDefinition("Pallet", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		genericItem30 = createLUItem(luPI, tuPI, 30, null);
		createLUItem(luPI, tuPI, 40, productXY);
	}

	private I_M_HU_PI createTUPI(final String name)
	{
		final I_M_HU_PI pi = helper.createHUDefinition(name, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item materialItem = helper.createHU_PI_Item_Material(pi);
		helper.assignProduct(materialItem, productXY, PIECES_PER_TU, uomEach);
		helper.assignProduct(materialItem, productOther, PIECES_PER_TU, uomEach);
		return pi;
	}

	private I_M_HU_PI_Item createLUItem(final I_M_HU_PI luPI, final I_M_HU_PI includedTUPI, final int qty, final ProductId productId)
	{
		final I_M_HU_PI_Item item = helper.createHU_PI_Item_IncludedHU(luPI, includedTUPI, new BigDecimal(qty), (BPartnerId)null);
		InterfaceWrapperHelper.setValue(item, I_M_HU_PI_Item.COLUMNNAME_C_BPartner_ID, null);
		InterfaceWrapperHelper.setValue(item, I_M_HU_PI_Item.COLUMNNAME_M_Product_ID, productId != null ? productId.getRepoId() : null);
		InterfaceWrapperHelper.save(item);
		return item;
	}

	private I_M_HU createTU(final I_M_HU_PI tuPI, final ProductId productId)
	{
		final List<I_M_HU> tus = helper.createHUs(helper.getHUContext(), tuPI, productId, Quantity.of(PIECES_PER_TU, uomEach));
		assertThat(tus).hasSize(1);
		return tus.get(0);
	}

	private List<I_M_HU> createTUs(final I_M_HU_PI tuPI, final ProductId productId, final int count)
	{
		final List<I_M_HU> tus = new ArrayList<>();
		for (int i = 0; i < count; i++)
		{
			tus.add(createTU(tuPI, productId));
		}
		return tus;
	}

	private List<I_M_HU> load(final Collection<I_M_HU> tus)
	{
		final TUsToNewLUsLoader loader = TUsToNewLUsLoader.builder().huContext(helper.getHUContext()).build();
		loader.addTUs(tus);
		loader.close();
		return loader.getLU_HUs();
	}

	/**
	 * @return one line per LU, sorted: "<products of its TUs> x<TU count> item<qty of the item the TUs hang on>"
	 */
	private List<String> describe(final List<I_M_HU> lus)
	{
		return lus.stream()
				.map(lu -> {
					final List<I_M_HU> tus = handlingUnitsDAO.retrieveIncludedHUs(lu);
					final String products = handlingUnitsBL.getStorageFactory().getStorage(lu).streamProductStorages()
							.map(IHUProductStorage::getProductId)
							.map(ProductId::getRepoId)
							.sorted()
							.map(String::valueOf)
							.collect(Collectors.joining("+"));
					final String itemQtys = tus.stream()
							.map(tu -> handlingUnitsBL.getPIItem(handlingUnitsDAO.retrieveParentItem(tu)).getQty().intValueExact())
							.distinct()
							.map(String::valueOf)
							.collect(Collectors.joining(","));
					return products + " x" + tus.size() + " item" + itemQtys;
				})
				.sorted()
				.collect(ImmutableList.toImmutableList());
	}

	private String xy() {return String.valueOf(productXY.getRepoId());}

	private String other() {return String.valueOf(productOther.getRepoId());}

	@Test
	void palletLinkedOnlyThroughTheXYItem_acceptsXY_refusesOther()
	{
		final I_M_HU_PI tuPI2 = createTUPI("IFCO2");
		final I_M_HU_PI xyOnlyPallet = helper.createHUDefinition("PalletXYOnly", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU_PI_Item xyOnlyItem = createLUItem(xyOnlyPallet, tuPI2, 40, productXY);

		final I_M_HU xyTU = createTU(tuPI2, productXY);
		final I_M_HU otherTU = createTU(tuPI2, productOther);

		assertThat(TUsToNewLUsLoader.isPackToLUAllowed(xyTU)).isTrue();
		assertThat(TUsToNewLUsLoader.isPackToLUAllowed(otherTU)).isFalse();

		final List<I_M_HU> lus = load(ImmutableList.of(xyTU));
		assertThat(lus).hasSize(1);
		assertThat(describe(lus)).containsExactly(xy() + " x1 item40");
		InterfaceWrapperHelper.refresh(xyTU);
		assertThat(handlingUnitsBL.getPIItem(xyTU.getM_HU_Item_Parent())).isEqualTo(xyOnlyItem);
	}

	/**
	 * The pallet holds generic and XY items for two TU packings. XY TUs of both TU packings go onto XY pallets of their own TU packing's
	 * XY item, never onto a generic item.
	 */
	@Test
	void xyTUsOfTwoTUPackings_eachUseTheirXYItem()
	{
		final I_M_HU_PI tuPI2 = createTUPI("IFCO2");
		createLUItem(luPI, tuPI2, 30, null);
		createLUItem(luPI, tuPI2, 40, productXY);

		final List<I_M_HU> tus = new ArrayList<>();
		tus.add(createTU(tuPI, productXY));
		tus.add(createTU(tuPI2, productXY));

		assertThat(describe(load(tus))).containsExactly(
				xy() + " x1 item40",
				xy() + " x1 item40");
	}

	@Test
	void tuHoldingTwoProducts_usesTheGenericItem()
	{
		final I_M_HU mixedTU = createTU(tuPI, productXY);
		// add a second product into the same TU
		HULoader.of(
						helper.createDummySourceDestination(productOther, new BigDecimal("1000"), uomEach, true),
						HUListAllocationSourceDestination.of(mixedTU))
				.load(AllocationUtils.builder()
						.setHUContext(helper.getHUContext())
						.setProduct(productOther)
						.setQuantity(Quantity.of(new BigDecimal("2"), uomEach))
						.setDateAsToday()
						.setForceQtyAllocation(true)
						.create());
		assertThat(handlingUnitsBL.getStorageFactory().getStorage(mixedTU).getSingleProductIdOrNull())
				.as("fixture: the TU holds two products").isNull();

		assertThat(TUsToNewLUsLoader.isPackToLUAllowed(mixedTU)).isTrue();
		final List<I_M_HU> lus = load(ImmutableList.of(mixedTU));

		InterfaceWrapperHelper.refresh(mixedTU);
		assertThat(handlingUnitsBL.getPIItem(mixedTU.getM_HU_Item_Parent())).isEqualTo(genericItem30);
		assertThat(lus).hasSize(1);
	}

	@Test
	void sameResultForAnyLoadingOrder()
	{
		final List<String> expected = ImmutableList.<String>builder()
				.add(xy() + " x40 item40")
				.add(xy() + " x5 item40")
				.add(other() + " x30 item30")
				.add(other() + " x5 item30")
				.build()
				.stream().sorted().collect(ImmutableList.toImmutableList());

		// XY first
		{
			final List<I_M_HU> tus = new ArrayList<>(createTUs(tuPI, productXY, 45));
			tus.addAll(createTUs(tuPI, productOther, 35));
			assertThat(describe(load(tus))).as("XY first").containsExactlyElementsOf(expected);
		}
		// OTHER first
		{
			final List<I_M_HU> tus = new ArrayList<>(createTUs(tuPI, productOther, 35));
			tus.addAll(createTUs(tuPI, productXY, 45));
			assertThat(describe(load(tus))).as("OTHER first").containsExactlyElementsOf(expected);
		}
		// interleaved
		{
			final List<I_M_HU> xyTUs = createTUs(tuPI, productXY, 45);
			final List<I_M_HU> otherTUs = createTUs(tuPI, productOther, 35);
			final List<I_M_HU> tus = new ArrayList<>();
			for (int i = 0; i < 45; i++)
			{
				tus.add(xyTUs.get(i));
				if (i < 35)
				{
					tus.add(otherTUs.get(i));
				}
			}
			assertThat(describe(load(tus))).as("interleaved").containsExactlyElementsOf(expected);
		}
	}

	@Test
	void genericDataOnly_productsShareThePalletsLikeLULoader()
	{
		final I_M_HU_PI tuPI3 = createTUPI("IFCO3");
		final I_M_HU_PI genericPallet = helper.createHUDefinition("PalletGeneric", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		createLUItem(genericPallet, tuPI3, 30, null);

		final List<I_M_HU> tusForLULoader = interleave(createTUs(tuPI3, productXY, 20), createTUs(tuPI3, productOther, 20));
		final LULoader luLoader = new LULoader(helper.getHUContext());
		tusForLULoader.forEach(luLoader::addTU);
		luLoader.close();

		final List<I_M_HU> tus = interleave(createTUs(tuPI3, productXY, 20), createTUs(tuPI3, productOther, 20));
		final List<String> actual = describe(load(tus));

		assertThat(actual).containsExactlyElementsOf(describe(luLoader.getLU_HUs()));
		assertThat(actual).as("mixed pallets 30 + 10 on the generic item")
				.containsExactlyInAnyOrder(
						xy() + "+" + other() + " x30 item30",
						xy() + "+" + other() + " x10 item30");
	}

	private static List<I_M_HU> interleave(final List<I_M_HU> a, final List<I_M_HU> b)
	{
		final List<I_M_HU> result = new ArrayList<>();
		for (int i = 0; i < Math.max(a.size(), b.size()); i++)
		{
			if (i < a.size())
			{
				result.add(a.get(i));
			}
			if (i < b.size())
			{
				result.add(b.get(i));
			}
		}
		return result;
	}
}
