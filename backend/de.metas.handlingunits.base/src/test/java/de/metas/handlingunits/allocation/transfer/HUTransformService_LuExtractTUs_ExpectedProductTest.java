package de.metas.handlingunits.allocation.transfer;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link HUTransformService#luExtractTUs} with {@code expectedProductId}: only TUs of that product are taken.
 * An HU from which another product was taken away completely still holds that product's storage record with qty zero;
 * that HU holds only the expected product and must therefore be taken.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class HUTransformService_LuExtractTUs_ExpectedProductTest
{
	private IHandlingUnitsDAO handlingUnitsDAO;
	private IHandlingUnitsBL handlingUnitsBL;
	private LUTUProducerDestinationTestSupport data;
	private HUTransformService huTransformService;
	private ProductId tomato;
	private ProductId salad;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		SpringContextHolder.registerJUnitBean(HUQRCodesService.newInstanceForUnitTesting());

		data = new HUTransformTestsBase().getData();
		huTransformService = HUTransformService.newInstance(data.helper.getHUContext());
		data.disableHUPackingMaterialsCollector("new LUs would otherwise want a packing material movement");
		tomato = data.helper.pTomatoProductId;
		salad = data.helper.pSaladProductId;
	}

	/** @return a standalone real TU holding 40 of the given product */
	private I_M_HU createRealTU(final ProductId productId)
	{
		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLocatorId(data.defaultLocatorId);
		lutuProducer.setNoLU();
		lutuProducer.setTUPI(data.piTU_IFCO);
		lutuProducer.addCUPerTU(productId, BigDecimal.valueOf(40), data.helper.uomKg);
		data.helper.load(lutuProducer, productId, BigDecimal.valueOf(40), data.helper.uomKg);
		return CollectionUtils.singleElement(lutuProducer.getCreatedHUs());
	}

	/** @return an LU with an aggregated TU of {@code qtyTomatoTUs} tomato TUs and {@code qtySaladTUs} real salad TUs */
	private I_M_HU createLU(final int qtyTomatoTUs, final int qtySaladTUs)
	{
		final I_M_HU lu = data.createLU(qtyTomatoTUs, 40);
		for (int i = 0; i < qtySaladTUs; i++)
		{
			huTransformService.tuToExistingLU(createRealTU(salad), QtyTU.ONE, lu);
		}
		return lu;
	}

	private LUTUResult extract(final I_M_HU sourceLU, final int qtyTUs, final ProductId expectedProductId, final HUTransformService.TargetLU targetLU)
	{
		return huTransformService.luExtractTUs(HUTransformService.LUExtractTUsRequest.builder()
				.sourceLU(sourceLU)
				.qtyTU(QtyTU.ofInt(qtyTUs))
				.targetLU(targetLU)
				.expectedProductId(expectedProductId)
				.newLUProductId(expectedProductId)
				.build());
	}

	private List<IHUProductStorage> storages(final I_M_HU hu)
	{
		return handlingUnitsBL.getStorageFactory().getStorage(hu).getProductStorages();
	}

	/** @return the products with a positive quantity on the given HUs */
	private List<ProductId> productsOf(final List<I_M_HU> hus)
	{
		return hus.stream()
				.flatMap(hu -> storages(hu).stream())
				.filter(storage -> storage.getQty().isPositive())
				.map(IHUProductStorage::getProductId)
				.distinct()
				.collect(Collectors.toList());
	}

	@Test
	public void sourceLUWhoseOtherProductWasTakenAway_isTakenAsIs()
	{
		final I_M_HU sourceLU = createLU(3, 2);
		assertThat(extract(sourceLU, 2, salad, HUTransformService.TargetLU.NONE).getQtyTUs()).isEqualTo(QtyTU.TWO);
		assertThat(storages(sourceLU))
				.as("precondition: the LU still has the salad storage record, with qty zero")
				.anySatisfy(storage -> {
					assertThat(storage.getProductId()).isEqualTo(salad);
					assertThat(storage.getQty().isZero()).isTrue();
				});

		final LUTUResult result = extract(sourceLU, 3, tomato, HUTransformService.TargetLU.ofNewLU(data.piLU));

		assertThat(result.getLURecords())
				.as("the source LU holds exactly the 3 requested tomato TUs and has the target's packing instructions, so it is taken as it is")
				.extracting(I_M_HU::getM_HU_ID)
				.containsExactly(sourceLU.getM_HU_ID());
	}

	@Test
	public void tuWhoseOtherProductWasTakenAway_isTaken()
	{
		// a real tomato TU which also held a salad CU; the salad CU was taken out again
		final I_M_HU tomatoTU = createRealTU(tomato);
		final I_M_HU saladTU = createRealTU(salad);
		final I_M_HU saladCU = CollectionUtils.singleElement(handlingUnitsDAO.retrieveIncludedHUs(saladTU));
		huTransformService.cusToExistingTU(ImmutableList.of(saladCU), tomatoTU);
		huTransformService.cuToNewCU(saladCU, CollectionUtils.singleElement(storages(saladCU)).getQty()); // the whole salad CU
		assertThat(productsOf(ImmutableList.of(tomatoTU))).as("precondition").containsOnly(tomato);
		assertThat(storages(tomatoTU)).as("precondition: salad storage record with qty zero").extracting(IHUProductStorage::getProductId).contains(salad);

		final I_M_HU sourceLU = data.createLU(2, 40);
		huTransformService.tuToExistingLU(tomatoTU, QtyTU.ONE, sourceLU);
		huTransformService.tuToExistingLU(createRealTU(salad), QtyTU.ONE, sourceLU); // genuinely other product: never taken

		final LUTUResult result = extract(sourceLU, 3, tomato, HUTransformService.TargetLU.NONE);

		assertThat(result.getQtyTUs()).isEqualTo(QtyTU.THREE);
		assertThat(productsOf(result.getAllTURecords())).containsOnly(tomato);
	}

	@Test
	public void mixedProductLU_onlyExpectedProductsTUsAreTaken()
	{
		final I_M_HU sourceLU = createLU(3, 2);

		final LUTUResult result = extract(sourceLU, 3, tomato, HUTransformService.TargetLU.NONE);

		assertThat(result.getQtyTUs()).isEqualTo(QtyTU.THREE);
		assertThat(productsOf(result.getAllTURecords())).containsOnly(tomato);
		assertThat(productsOf(ImmutableList.of(sourceLU))).containsOnly(salad);
	}

	@Test
	public void mixedProductLU_fewerTUsOfExpectedProductThanRequested_onlyThoseAreTaken()
	{
		final I_M_HU sourceLU = createLU(3, 2);

		final LUTUResult result = extract(sourceLU, 4, tomato, HUTransformService.TargetLU.NONE);

		// the caller detects the shortfall (e.g. mobile picking fails with "not enough TUs")
		assertThat(result.getQtyTUs()).isEqualTo(QtyTU.THREE);
		assertThat(productsOf(result.getAllTURecords())).containsOnly(tomato);
	}

	/**
	 * The real TUs (listed before the aggregated TU) start a new LU; the whole aggregated TU then still fits onto that LU,
	 * so it is de-aggregated onto it, and its TUs hang on the product-specific item that LU was created with.
	 */
	@Test
	public void wholeAggregatedTUFitsOntoLastNewLU_attachedViaItsProductSpecificItem()
	{
		final I_M_HU_PI_Item specificItem = data.helper.createHU_PI_Item_IncludedHU(data.piLU, data.piTU_IFCO, new BigDecimal("8"));
		specificItem.setM_Product_ID(tomato.getRepoId());
		save(specificItem);

		final I_M_HU sourceLU = data.createLU(4, 40);
		for (int i = 0; i < 3; i++)
		{
			huTransformService.tuToExistingLU(createRealTU(tomato), QtyTU.ONE, sourceLU);
		}
		huTransformService.tuToExistingLU(createRealTU(salad), QtyTU.ONE, sourceLU); // so that not the whole source LU is picked

		final LUTUResult result = extract(sourceLU, 7, tomato, HUTransformService.TargetLU.ofNewLU(data.piLU));

		final I_M_HU newLU = CollectionUtils.singleElement(result.getLURecords());
		final List<I_M_HU> tus = handlingUnitsDAO.retrieveIncludedHUs(newLU);
		assertThat(tus.stream().mapToInt(tu -> huTransformService.getMaximumQtyTU(tu).toInt()).sum()).isEqualTo(7);
		assertThat(tus)
				.as("all 7 TUs are real TUs on the product-specific item")
				.hasSize(7)
				.extracting(tu -> tu.getM_HU_Item_Parent().getM_HU_PI_Item_ID())
				.containsOnly(specificItem.getM_HU_PI_Item_ID());
		assertThat(productsOf(ImmutableList.of(sourceLU))).containsOnly(salad);
	}
}
