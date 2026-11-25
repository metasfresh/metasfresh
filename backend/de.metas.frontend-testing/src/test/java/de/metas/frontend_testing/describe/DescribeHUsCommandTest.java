package de.metas.frontend_testing.describe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.impl.LULoader;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.I_M_InventoryLine_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Tests {@link DescribeHUsCommand}: the read-only testing-API view of HUs that the frontend E2E tests compare as a set.
 */
class DescribeHUsCommandTest extends AbstractHUTest
{
	private I_C_UOM uomPCE;
	private I_M_Product productP;
	private I_M_Product productQ;
	private I_M_HU_PI tuPI;
	private I_M_HU_PI luPI;
	private I_M_HU_PI_Item luItem30;

	private IHandlingUnitsBL handlingUnitsBL;
	private IHandlingUnitsDAO handlingUnitsDAO;

	@Override
	protected void initialize()
	{
		handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		// the UOM symbol differs from its X12DE355 code, so a symbol/code mix-up fails the string assertions
		uomPCE = helper.uomPCE;
		uomPCE.setUOMSymbol("Stk");
		InterfaceWrapperHelper.saveRecord(uomPCE);

		productP = BusinessTestHelper.createProduct("P", uomPCE);
		productQ = BusinessTestHelper.createProduct("Q", uomPCE);

		tuPI = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item tuMaterialItem = helper.createHU_PI_Item_Material(tuPI);
		helper.assignProduct(tuMaterialItem, productP, new BigDecimal("4"), uomPCE);
		helper.assignProduct(tuMaterialItem, productQ, new BigDecimal("4"), uomPCE);

		luPI = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		luItem30 = helper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal("30"));
	}

	private static JsonDescribeHUsResponse describe(final HuId... huIds)
	{
		final ImmutableList.Builder<Integer> ids = ImmutableList.builder();
		for (final HuId huId : huIds)
		{
			ids.add(huId.getRepoId());
		}

		return DescribeHUsCommand.builder()
				.request(JsonDescribeHUsRequest.builder().huIds(ids.build()).build())
				.build()
				.execute();
	}

	private I_M_HU createTU(final I_M_Product product)
	{
		final List<I_M_HU> tus = helper.createHUs(
				helper.getHUContext(),
				tuPI,
				ProductId.ofRepoId(product.getM_Product_ID()),
				Quantity.of(new BigDecimal("4.000"), uomPCE));
		assertThat(tus).hasSize(1);
		return tus.get(0);
	}

	private I_M_HU loadOnNewLU(final List<I_M_HU> tus)
	{
		final LULoader luLoader = new LULoader(helper.getHUContext());
		tus.forEach(luLoader::addTU);
		luLoader.close();
		assertThat(luLoader.getLU_HUs()).hasSize(1);
		return luLoader.getLU_HUs().get(0);
	}

	private static HuId idOf(final I_M_HU hu)
	{
		return HuId.ofRepoId(hu.getM_HU_ID());
	}

	@Test
	void luWithThreeSingleTUs()
	{
		final I_M_HU lu = loadOnNewLU(ImmutableList.of(createTU(productP), createTU(productP), createTU(productP)));

		final JsonDescribeHUsResponse response = describe(idOf(lu));

		assertThat(response.getHus()).containsExactly(
				JsonDescribeHUsResponse.HU.builder()
						.huId(lu.getM_HU_ID())
						.topLevelHuId(lu.getM_HU_ID())
						.huUnitType("LU")
						.storages(ImmutableMap.of("P", "12 PCE"))
						.includedTUsCount(3)
						.tuParentPIItemQtys(ImmutableList.of(30))
						.build());
	}

	@Test
	void tuOnLU_hasTheLUAsTopLevelHU()
	{
		final I_M_HU tu = createTU(productP);
		final I_M_HU lu = loadOnNewLU(ImmutableList.of(tu));

		final JsonDescribeHUsResponse.HU described = describe(idOf(tu)).getHus().get(0);

		assertThat(described.getHuId()).isEqualTo(tu.getM_HU_ID());
		assertThat(described.getTopLevelHuId()).isEqualTo(lu.getM_HU_ID());
		assertThat(described.getHuUnitType()).isEqualTo("TU");
		assertThat(described.getStorages()).containsExactly(entry("P", "4 PCE"));
		assertThat(described.getIncludedTUsCount()).isZero();
		assertThat(described.getTuParentPIItemQtys()).isEmpty();
	}

	@Test
	void luWithAggregatedTU_countsTheTUsOfTheAggregate()
	{
		final IHUContext huContext = helper.getHUContext();
		final LUTUProducerDestination producer = new LUTUProducerDestination();
		producer.setTUPI(tuPI);
		producer.addCUPerTU(ProductId.ofRepoId(productP.getM_Product_ID()), new BigDecimal("4"), uomPCE);
		producer.setLUPI(luPI);
		producer.setLUItemPI(luItem30);
		producer.setMaxLUs(1);
		producer.setCreateTUsForRemainingQty(false);
		final List<I_M_HU> lus = helper.createHUs(huContext, producer, new BigDecimal("20"));
		assertThat(lus).hasSize(1);
		final I_M_HU lu = lus.get(0);
		assertThat(handlingUnitsBL.isAggregateHU(handlingUnitsDAO.retrieveIncludedHUs(lu).get(0)))
				.as("fixture: the LU holds an aggregated TU").isTrue();

		final JsonDescribeHUsResponse.HU described = describe(idOf(lu)).getHus().get(0);

		assertThat(described.getIncludedTUsCount()).isEqualTo(5);
		assertThat(described.getStorages()).containsExactly(entry("P", "20 PCE"));
		assertThat(described.getTuParentPIItemQtys()).containsExactly(30);
	}

	@Test
	void luWithTwoProducts_listsBothStorages()
	{
		final List<I_M_HU> tus = new ArrayList<>();
		tus.add(createTU(productP));
		tus.add(createTU(productQ));
		tus.add(createTU(productQ));
		final I_M_HU lu = loadOnNewLU(tus);

		final JsonDescribeHUsResponse.HU described = describe(idOf(lu)).getHus().get(0);

		assertThat(described.getStorages()).isEqualTo(ImmutableMap.of("P", "4 PCE", "Q", "8 PCE"));
		assertThat(described.getIncludedTUsCount()).isEqualTo(3);
		assertThat(described.getTuParentPIItemQtys()).containsExactly(30);
	}

	private I_M_Inventory createInventoryWithLineHUs(final I_M_HU... hus)
	{
		final I_M_Inventory inventory = InterfaceWrapperHelper.newInstance(I_M_Inventory.class);
		InterfaceWrapperHelper.saveRecord(inventory);

		final I_M_InventoryLine line = InterfaceWrapperHelper.newInstance(I_M_InventoryLine.class);
		line.setM_Inventory_ID(inventory.getM_Inventory_ID());
		InterfaceWrapperHelper.saveRecord(line);

		for (final I_M_HU hu : hus)
		{
			final I_M_InventoryLine_HU lineHU = InterfaceWrapperHelper.newInstance(I_M_InventoryLine_HU.class);
			lineHU.setM_Inventory_ID(inventory.getM_Inventory_ID());
			lineHU.setM_InventoryLine_ID(line.getM_InventoryLine_ID());
			lineHU.setM_HU_ID(hu.getM_HU_ID());
			InterfaceWrapperHelper.saveRecord(lineHU);
		}
		return inventory;
	}

	@Test
	void inventoryId_describesTheHUsOfItsLines()
	{
		final I_M_HU lu1 = loadOnNewLU(ImmutableList.of(createTU(productP), createTU(productP)));
		final I_M_HU lu2 = loadOnNewLU(ImmutableList.of(createTU(productQ)));
		final I_M_HU luOfAnotherInventory = loadOnNewLU(ImmutableList.of(createTU(productQ)));
		final I_M_Inventory inventory = createInventoryWithLineHUs(lu1, lu2);
		createInventoryWithLineHUs(luOfAnotherInventory);

		final JsonDescribeHUsResponse response = DescribeHUsCommand.builder()
				.request(JsonDescribeHUsRequest.builder().inventoryId(inventory.getM_Inventory_ID()).build())
				.build()
				.execute();

		assertThat(response.getHus())
				.extracting(JsonDescribeHUsResponse.HU::getHuId, JsonDescribeHUsResponse.HU::getStorages)
				.containsExactlyInAnyOrder(
						tuple(lu1.getM_HU_ID(), ImmutableMap.of("P", "8 PCE")),
						tuple(lu2.getM_HU_ID(), ImmutableMap.of("Q", "4 PCE")));
	}

	@Test
	void unknownHUId_fails()
	{
		assertThatThrownBy(() -> describe(HuId.ofRepoId(999999)))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("999999");
	}
}
