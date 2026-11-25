package de.metas.handlingunits.allocation.transfer.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link LUTUProducerDestination#setLUPIAndItemForSourceHUs(I_M_HU_PI, List)}: the LU item for the sources' TU packing and product.
 * Pallet "Pallet": generic item 30 and an item 40 bound to product XY, both for TU packing "IFCO" (4 pieces).
 */
class LUTUProducerDestination_SetLUPIAndItemForSourceHUsTest extends AbstractHUTest
{
	private static final BigDecimal PIECES_PER_TU = new BigDecimal("4");

	private IHandlingUnitsDAO handlingUnitsDAO;

	private ProductId productXY;
	private ProductId productOther;
	private I_M_HU_PI tuPI;
	private I_M_HU_PI luPI;
	private I_M_HU_PI_Item genericItem30;
	private I_M_HU_PI_Item xyItem40;

	@Override
	protected void initialize()
	{
		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		productXY = pTomatoId;
		productOther = pSaladId;

		tuPI = helper.createHUDefinition("IFCO", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item materialItem = helper.createHU_PI_Item_Material(tuPI);
		helper.assignProduct(materialItem, productXY, PIECES_PER_TU, uomEach);
		helper.assignProduct(materialItem, productOther, PIECES_PER_TU, uomEach);

		luPI = helper.createHUDefinition("Pallet", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		genericItem30 = createLUItem(luPI, tuPI, 30, null);
		xyItem40 = createLUItem(luPI, tuPI, 40, productXY);
	}

	private I_M_HU_PI_Item createLUItem(final I_M_HU_PI luPI, final I_M_HU_PI includedTUPI, final int qty, final ProductId productId)
	{
		final I_M_HU_PI_Item item = helper.createHU_PI_Item_IncludedHU(luPI, includedTUPI, new BigDecimal(qty), (BPartnerId)null);
		InterfaceWrapperHelper.setValue(item, I_M_HU_PI_Item.COLUMNNAME_C_BPartner_ID, null);
		InterfaceWrapperHelper.setValue(item, I_M_HU_PI_Item.COLUMNNAME_M_Product_ID, productId != null ? productId.getRepoId() : null);
		InterfaceWrapperHelper.save(item);
		return item;
	}

	private I_M_HU createTU(final ProductId productId)
	{
		final List<I_M_HU> tus = helper.createHUs(helper.getHUContext(), tuPI, productId, Quantity.of(PIECES_PER_TU, uomEach));
		assertThat(tus).hasSize(1);
		return tus.get(0);
	}

	private I_M_HU createEmptyTU()
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, helper.contextProvider);
		hu.setM_HU_PI_Version(handlingUnitsDAO.retrievePICurrentVersion(tuPI));
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		InterfaceWrapperHelper.save(hu);
		return hu;
	}

	private static LUTUProducerDestination producerFor(final I_M_HU_PI luPI, final List<I_M_HU> sourceHUs)
	{
		final LUTUProducerDestination producer = new LUTUProducerDestination();
		producer.setLUPIAndItemForSourceHUs(luPI, sourceHUs);
		return producer;
	}

	@Test
	void xySources_useTheXYItem()
	{
		final LUTUProducerDestination producer = producerFor(luPI, ImmutableList.of(createTU(productXY), createTU(productXY)));

		assertThat(producer.getLUPI().getM_HU_PI_ID()).isEqualTo(luPI.getM_HU_PI_ID());
		assertThat(producer.getLUItemPI().getM_HU_PI_Item_ID()).isEqualTo(xyItem40.getM_HU_PI_Item_ID());
		assertThat(producer.getTUPI().getM_HU_PI_ID()).isEqualTo(tuPI.getM_HU_PI_ID());
	}

	@Test
	void otherSources_useTheGenericItem()
	{
		final LUTUProducerDestination producer = producerFor(luPI, ImmutableList.of(createTU(productOther)));

		assertThat(producer.getLUItemPI().getM_HU_PI_Item_ID()).isEqualTo(genericItem30.getM_HU_PI_Item_ID());
	}

	@Test
	void sourcesOfSeveralProducts_useTheGenericItem()
	{
		final LUTUProducerDestination producer = producerFor(luPI, ImmutableList.of(createTU(productXY), createTU(productOther)));

		assertThat(producer.getLUItemPI().getM_HU_PI_Item_ID()).isEqualTo(genericItem30.getM_HU_PI_Item_ID());
	}

	@Test
	void anEmptySource_doesNotCountAsAnotherProduct()
	{
		final LUTUProducerDestination producer = producerFor(luPI, ImmutableList.of(createTU(productXY), createEmptyTU()));

		assertThat(producer.getLUItemPI().getM_HU_PI_Item_ID()).isEqualTo(xyItem40.getM_HU_PI_Item_ID());
	}

	@Test
	void palletWithoutAnItemForTheTU_givesAUserError()
	{
		final I_M_HU_PI otherPallet = helper.createHUDefinition("OtherPallet", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		final I_M_HU source = createTU(productOther);

		assertThatThrownBy(() -> producerFor(otherPallet, ImmutableList.of(source)))
				.isInstanceOf(HUException.class)
				.matches(ex -> ((HUException)ex).isUserValidationError(), "is a user validation error");
	}
}
