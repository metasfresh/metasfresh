package de.metas.handlingunits.shipping;

import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.product.ProductId;
import de.metas.uom.X12DE355;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(AdempiereTestWatcher.class)
public class PackedHUShippingInfoServiceTest
{
	private LUTUProducerDestinationTestSupport data;
	private PackedHUShippingInfoService service;

	@BeforeEach
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();
		service = PackedHUShippingInfoService.newInstanceForUnitTesting();

		// The packing material DAO needs a centimetre UOM to convert dimensions.
		// Set it on the packing materials so getPackageDimensions can succeed.
		final I_C_UOM uomCm = createCentimetreUOM();
		setPackingMaterialDimensions(data.helper.pmIFCO, uomCm);
		setPackingMaterialDimensions(data.helper.pmPalet, uomCm);
	}

	private static I_C_UOM createCentimetreUOM()
	{
		final I_C_UOM uomCm = newInstance(I_C_UOM.class);
		uomCm.setName("cm");
		uomCm.setUOMSymbol("cm");
		uomCm.setX12DE355(X12DE355.CENTIMETRE.getCode());
		uomCm.setStdPrecision(2);
		saveRecord(uomCm);
		return uomCm;
	}

	private static void setPackingMaterialDimensions(
			final I_M_HU_PackingMaterial pm,
			final I_C_UOM uomCm)
	{
		pm.setC_UOM_Dimension_ID(uomCm.getC_UOM_ID());
		pm.setLength(new BigDecimal("60"));
		pm.setWidth(new BigDecimal("40"));
		pm.setHeight(new BigDecimal("30"));
		save(pm);
	}

	@Test
	public void topLevelTU()
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLocatorId(data.defaultLocatorId);
		lutuProducer.setNoLU();
		lutuProducer.setTUPI(data.piTU_IFCO);

		data.helper.load(lutuProducer, data.helper.pTomatoProductId, new BigDecimal("20"), data.helper.uomKg);

		final List<I_M_HU> createdTUs = lutuProducer.getCreatedHUs();
		assertThat(createdTUs).hasSize(1);
		final I_M_HU tu = createdTUs.get(0);

		final PackedHUShippingInfo info = service.of(tu);

		assertThat(info.getTopLevelType()).isEqualTo(HuUnitType.TU);
	}

	@Test
	public void topLevelLU()
	{
		final I_M_HU lu = data.createLU(2, 20);

		final PackedHUShippingInfo info = service.of(lu);

		assertThat(info.getTopLevelType()).isEqualTo(HuUnitType.LU);
		assertThat(info.getDimensions().isUnspecified()).isFalse();
	}

	@Test
	public void selfPackedCU()
	{
		// Create a self-packed product with defined package dimensions
		data.helper.pTomato.setIsSelfPacked(true);
		data.helper.pTomato.setLengthInCm(30);
		data.helper.pTomato.setWidthInCm(20);
		data.helper.pTomato.setHeightInCm(10);
		save(data.helper.pTomato);

		// Create a standalone virtual HU (CU = no packing material)
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		producer.setLocatorId(data.defaultLocatorId);

		data.helper.load(producer, data.helper.pTomatoProductId, BigDecimal.ONE, data.helper.uomKg);

		final List<I_M_HU> createdCUs = producer.getCreatedHUs();
		assertThat(createdCUs).hasSize(1);
		final I_M_HU cu = CollectionUtils.singleElement(createdCUs);

		final PackedHUShippingInfo info = service.of(cu);

		assertThat(info.getTopLevelType()).isEqualTo(HuUnitType.VHU);
	}

	@Test
	public void getProductItems_multiProduct()
	{
		// A virtual HU has unlimited capacity (ofVirtualPI → maxHUsToCreate=1), so loading two
		// products into the same producer puts both into ONE top-level HU's storage.
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		producer.setLocatorId(data.defaultLocatorId);

		data.helper.load(producer, data.helper.pTomatoProductId, new BigDecimal("20"), data.helper.uomKg);
		data.helper.load(producer, data.helper.pSaladProductId, new BigDecimal("3"), data.helper.uomEach);

		final List<I_M_HU> createdHUs = producer.getCreatedHUs();
		assertThat(createdHUs).hasSize(1);
		final I_M_HU hu = createdHUs.get(0);

		final List<PackedHUProductItem> items = service.getProductItems(hu);

		assertThat(items).hasSize(2);
		assertThat(items)
				.extracting(PackedHUProductItem::getProductId)
				.containsExactlyInAnyOrder(data.helper.pTomatoProductId, data.helper.pSaladProductId);

		final PackedHUProductItem tomato = findItem(items, data.helper.pTomatoProductId);
		assertThat(tomato.getQty().toBigDecimal()).isEqualByComparingTo("20");

		final PackedHUProductItem salad = findItem(items, data.helper.pSaladProductId);
		assertThat(salad.getQty().toBigDecimal()).isEqualByComparingTo("3");
	}

	private static PackedHUProductItem findItem(final List<PackedHUProductItem> items, final ProductId productId)
	{
		return items.stream()
				.filter(item -> productId.equals(item.getProductId()))
				.findFirst()
				.orElseThrow(() -> new AssertionError("No item for product " + productId));
	}
}
