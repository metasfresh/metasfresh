package de.metas.handlingunits.allocation.transfer.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link LUTUProducerDestination#setLUPIAndItemForProduct(HuPackingInstructionsId, ProductId)}: the LU item for the TU packing set before and a product.
 * Pallet "Pallet": generic item 30 and an item 40 bound to product XY, both for TU packing "IFCO".
 */
class LUTUProducerDestination_SetLUPIAndItemForProductTest extends AbstractHUTest
{
	private ProductId productXY;
	private ProductId productOther;
	private I_M_HU_PI tuPI;
	private I_M_HU_PI luPI;
	private I_M_HU_PI_Item genericItem30;
	private I_M_HU_PI_Item xyItem40;

	@Override
	protected void initialize()
	{
		productXY = pTomatoId;
		productOther = pSaladId;

		tuPI = helper.createHUDefinition("IFCO", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		helper.createHU_PI_Item_Material(tuPI);

		luPI = helper.createHUDefinition("Pallet", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		genericItem30 = createLUItem(luPI, 30, null);
		xyItem40 = createLUItem(luPI, 40, productXY);
	}

	private I_M_HU_PI_Item createLUItem(final I_M_HU_PI luPI, final int qty, @Nullable final ProductId productId)
	{
		final I_M_HU_PI_Item item = helper.createHU_PI_Item_IncludedHU(luPI, tuPI, new BigDecimal(qty), (BPartnerId)null);
		InterfaceWrapperHelper.setValue(item, I_M_HU_PI_Item.COLUMNNAME_C_BPartner_ID, null);
		InterfaceWrapperHelper.setValue(item, I_M_HU_PI_Item.COLUMNNAME_M_Product_ID, productId != null ? productId.getRepoId() : null);
		InterfaceWrapperHelper.save(item);
		return item;
	}

	private LUTUProducerDestination producerFor(final I_M_HU_PI luPI, @Nullable final ProductId productId)
	{
		final LUTUProducerDestination producer = new LUTUProducerDestination();
		producer.setTUPI(tuPI);
		producer.setLUPIAndItemForProduct(HuPackingInstructionsId.ofRepoId(luPI.getM_HU_PI_ID()), productId);
		return producer;
	}

	@Test
	void productXY_usesTheXYItem()
	{
		final LUTUProducerDestination producer = producerFor(luPI, productXY);

		assertThat(producer.getLUPI().getM_HU_PI_ID()).isEqualTo(luPI.getM_HU_PI_ID());
		assertThat(producer.getLUItemPI().getM_HU_PI_Item_ID()).isEqualTo(xyItem40.getM_HU_PI_Item_ID());
	}

	@Test
	void otherProduct_usesTheGenericItem()
	{
		final LUTUProducerDestination producer = producerFor(luPI, productOther);

		assertThat(producer.getLUItemPI().getM_HU_PI_Item_ID()).isEqualTo(genericItem30.getM_HU_PI_Item_ID());
	}

	@Test
	void noProduct_usesTheGenericItem()
	{
		final LUTUProducerDestination producer = producerFor(luPI, null);

		assertThat(producer.getLUItemPI().getM_HU_PI_Item_ID()).isEqualTo(genericItem30.getM_HU_PI_Item_ID());
	}

	@Test
	void palletWithoutAnItemForTheTU_givesAUserError()
	{
		final I_M_HU_PI otherPallet = helper.createHUDefinition("OtherPallet", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);

		assertThatThrownBy(() -> producerFor(otherPallet, productOther))
				.isInstanceOf(HUException.class)
				.hasMessageContaining("ERR_LU_HAS_NO_TU_SUB_PACK_INSTR")
				.matches(ex -> ((HUException)ex).isUserValidationError(), "is a user validation error");
	}
}
