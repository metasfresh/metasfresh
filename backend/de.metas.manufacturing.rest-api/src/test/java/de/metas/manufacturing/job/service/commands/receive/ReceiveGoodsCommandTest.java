package de.metas.manufacturing.job.service.commands.receive;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
				null,
				false);

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
				piip,
				false);

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
				piip,
				false);

		assertThat(result.toBigDecimal()).isEqualByComparingTo("0.3");
		assertThat(result.getUomId()).isEqualTo(uomKgId);
	}

	/**
	 * Catch weight with a mandatory scan: one receipt = one piece carrying its own scanned weight, lot and best-before date.
	 * In TU receive mode the qty must NOT be converted from TUs to the packing's N pieces,
	 * else one scanned weight would land on N pieces.
	 */
	@Test
	void catchWeightScanRequired_TUMode_finitePacking_receivesOnePiece()
	{
		final HUPIItemProduct fourPiecesPerTU = finiteHUPIItemProduct(4);

		final Quantity result = ReceiveGoodsCommand.computeQtyToReceive(
				BigDecimal.ONE,
				uomKgId,
				fourPiecesPerTU,
				true);

		assertThat(result.toBigDecimal()).isEqualByComparingTo("1");
		assertThat(result.getUomId()).isEqualTo(uomKgId);
	}

	@Test
	void catchWeightScanRequired_TUMode_twoPieces_rejected()
	{
		// counts pieces, not TUs: 2 is two pieces even though the TU packing would hold them
		assertThatThrownBy(() -> ReceiveGoodsCommand.computeQtyToReceive(BigDecimal.valueOf(2), uomKgId, finiteHUPIItemProduct(4), true))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(ReceiveGoodsCommand.MSG_CatchWeightReceiveOneAtATime.toAD_Message());
	}

	@Test
	void catchWeightScanRequired_moreThanOnePiece_rejected()
	{
		assertThatThrownBy(() -> ReceiveGoodsCommand.computeQtyToReceive(BigDecimal.valueOf(2), uomKgId, null, true))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(ReceiveGoodsCommand.MSG_CatchWeightReceiveOneAtATime.toAD_Message());
	}

	@Test
	void catchWeightScanRequired_fractionOfAPiece_rejected()
	{
		assertThatThrownBy(() -> ReceiveGoodsCommand.computeQtyToReceive(new BigDecimal("0.5"), uomKgId, null, true))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(ReceiveGoodsCommand.MSG_CatchWeightReceiveOneAtATime.toAD_Message());
	}

	@Test
	void catchWeightScanRequired_onePiece_accepted()
	{
		final Quantity result = ReceiveGoodsCommand.computeQtyToReceive(new BigDecimal("1.000"), uomKgId, null, true);

		assertThat(result.toBigDecimal()).isEqualByComparingTo("1");
	}

	@Test
	void catchWeightScanNotRequired_TUMode_qtyIsTUs()
	{
		// setting off: TU mode behaviour unchanged, 1 TU = the packing's 4 pieces
		final Quantity result = ReceiveGoodsCommand.computeQtyToReceive(BigDecimal.ONE, uomKgId, finiteHUPIItemProduct(4), false);

		assertThat(result.toBigDecimal()).isEqualByComparingTo("4");
	}

	@Test
	void isFull_belowCapacity()
	{
		assertThat(ReceiveGoodsCommand.isFull(new Quantity(BigDecimal.ONE, uomKg), finiteHUPIItemProduct(2))).isFalse();
	}

	@Test
	void isFull_atCapacity()
	{
		assertThat(ReceiveGoodsCommand.isFull(new Quantity(BigDecimal.valueOf(2), uomKg), finiteHUPIItemProduct(2))).isTrue();
	}

	@Test
	void isFull_infiniteCapacity_never()
	{
		assertThat(ReceiveGoodsCommand.isFull(new Quantity(BigDecimal.valueOf(1000), uomKg), infiniteHUPIItemProduct())).isFalse();
	}

	private HUPIItemProduct finiteHUPIItemProduct()
	{
		return finiteHUPIItemProduct(10);
	}

	private HUPIItemProduct finiteHUPIItemProduct(final int qtyCUsPerTU)
	{
		// id and piItemId required by HUPIItemProduct builder; not dereferenced by computeQtyToReceive
		return HUPIItemProduct.builder()
				.id(HUPIItemProductId.ofRepoId(1))
				.name(TranslatableStrings.anyLanguage("Finite TU"))
				.piItemId(HuPackingInstructionsItemId.ofRepoId(1))
				.productId(productId)
				.qtyCUsPerTU(new Quantity(BigDecimal.valueOf(qtyCUsPerTU), uomKg))
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
