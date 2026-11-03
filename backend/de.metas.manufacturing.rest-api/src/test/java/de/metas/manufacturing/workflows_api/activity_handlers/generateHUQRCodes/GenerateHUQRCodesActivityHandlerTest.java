package de.metas.manufacturing.workflows_api.activity_handlers.generateHUQRCodes;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class GenerateHUQRCodesActivityHandlerTest
{
	private GenerateHUQRCodesActivityHandler handler;
	private I_C_UOM uomKg;
	private ProductId productId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uomKg = BusinessTestHelper.createUomKg();
		productId = BusinessTestHelper.createProductId("By-product", uomKg);
		handler = new GenerateHUQRCodesActivityHandler();
	}

	@Test
	void finiteCapacity_roundsUp()
	{
		final I_M_HU_PI_Item_Product piip = createFinitePIItemProduct();

		final QtyTU result = handler.computeQtyTUsRequired(
				receiveLine(new Quantity(BigDecimal.valueOf(25), uomKg)),
				piip);

		assertThat(result).isEqualTo(QtyTU.ofInt(3));
	}

	@Test
	void infiniteCapacity_returnsOne()
	{
		final I_M_HU_PI_Item_Product piip = createInfinitePIItemProduct();

		final QtyTU result = handler.computeQtyTUsRequired(
				receiveLine(new Quantity(new BigDecimal("0.3"), uomKg)),
				piip);

		assertThat(result).isEqualTo(QtyTU.ONE);
	}

	private I_M_HU_PI_Item_Product createFinitePIItemProduct()
	{
		final I_M_HU_PI_Item_Product r = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		r.setName("Finite TU");
		r.setM_HU_PI_Item_ID(1); // required by HUPIItemProductDAO.fromRecord; not exercised here
		r.setM_Product_ID(productId.getRepoId());
		r.setIsInfiniteCapacity(false);
		r.setQty(BigDecimal.TEN);
		r.setC_UOM_ID(uomKg.getC_UOM_ID());
		InterfaceWrapperHelper.save(r);
		return r;
	}

	private I_M_HU_PI_Item_Product createInfinitePIItemProduct()
	{
		final I_M_HU_PI_Item_Product r = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		r.setName("Infinite TU");
		r.setM_HU_PI_Item_ID(1); // required by HUPIItemProductDAO.fromRecord; not exercised here
		r.setM_Product_ID(productId.getRepoId());
		r.setIsInfiniteCapacity(true);
		InterfaceWrapperHelper.save(r);
		return r;
	}

	private FinishedGoodsReceiveLine receiveLine(final Quantity qtyToReceive)
	{
		return FinishedGoodsReceiveLine.builder()
				.productId(productId)
				.productName(TranslatableStrings.anyLanguage("By-product"))
				.productValue("BY")
				.attributes(ImmutableAttributeSet.EMPTY)
				.qtyToReceive(qtyToReceive)
				.qtyReceived(new Quantity(BigDecimal.ZERO, uomKg))
				.build();
	}
}
