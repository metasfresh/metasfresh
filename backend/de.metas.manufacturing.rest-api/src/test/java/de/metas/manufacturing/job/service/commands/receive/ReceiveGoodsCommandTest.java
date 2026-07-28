package de.metas.manufacturing.job.service.commands.receive;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiveGoodsCommandTest
{
	private I_C_UOM uomKg;
	private UomId uomKgId;
	private ProductId productId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uomKg = BusinessTestHelper.createUomKg();
		uomKgId = UomId.ofRepoId(uomKg.getC_UOM_ID());
		productId = BusinessTestHelper.createProductId("By-product", uomKg);
	}

	@Test
	void noTUMode_returnsQtyAsCU()
	{
		final Quantity result = ReceiveGoodsCommand.computeQtyToReceive(
				BigDecimal.valueOf(7),
				uomKgId,
				null);

		assertThat(result.toBigDecimal()).isEqualByComparingTo("7");
		assertThat(result.getUomId()).isEqualTo(uomKgId);
	}

	@Test
	void finiteCapacity_multipliesByQtyCUsPerTU()
	{
		final HUPIItemProduct piip = finiteHUPIItemProduct();

		final Quantity result = ReceiveGoodsCommand.computeQtyToReceive(
				BigDecimal.valueOf(3),
				uomKgId,
				piip);

		assertThat(result.toBigDecimal()).isEqualByComparingTo("30");
		assertThat(result.getUomId()).isEqualTo(uomKgId);
	}

	@Test
	void infiniteCapacity_returnsQtyAsCUWithoutThrowing()
	{
		final HUPIItemProduct piip = infiniteHUPIItemProduct();

		final Quantity result = ReceiveGoodsCommand.computeQtyToReceive(
				new BigDecimal("0.3"),
				uomKgId,
				piip);

		assertThat(result.toBigDecimal()).isEqualByComparingTo("0.3");
		assertThat(result.getUomId()).isEqualTo(uomKgId);
	}

	private HUPIItemProduct finiteHUPIItemProduct()
	{
		// id and piItemId required by HUPIItemProduct builder; not dereferenced by computeQtyToReceive
		return HUPIItemProduct.builder()
				.id(HUPIItemProductId.ofRepoId(1))
				.name(TranslatableStrings.anyLanguage("Finite TU"))
				.piItemId(HuPackingInstructionsItemId.ofRepoId(1))
				.productId(productId)
				.qtyCUsPerTU(new Quantity(BigDecimal.TEN, uomKg))
				.build();
	}

	private HUPIItemProduct infiniteHUPIItemProduct()
	{
		return HUPIItemProduct.builder()
				.id(HUPIItemProductId.ofRepoId(1))
				.name(TranslatableStrings.anyLanguage("Infinite TU"))
				.piItemId(HuPackingInstructionsItemId.ofRepoId(1))
				.productId(productId)
				.build();
	}
}
