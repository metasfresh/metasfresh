package de.metas.manufacturing.job.service.commands.receive;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	 * One piece per scan (mandatory catch-weight scan with the weight captured at receipt): one receipt = one piece carrying its own scanned weight, lot and best-before date.
	 * In TU receive mode the qty must NOT be converted from TUs to the packing's N pieces,
	 * else one scanned weight would land on N pieces.
	 */
	@Test
	void onePiecePerScan_TUMode_finitePacking_receivesOnePiece()
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
	void onePiecePerScan_TUMode_twoPieces_rejected()
	{
		// counts pieces, not TUs: 2 is two pieces even though the TU packing would hold them
		assertThatThrownBy(() -> ReceiveGoodsCommand.computeQtyToReceive(BigDecimal.valueOf(2), uomKgId, finiteHUPIItemProduct(4), true))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(ReceiveGoodsCommand.MSG_CatchWeightReceiveOneAtATime.toAD_Message());
	}

	@Test
	void onePiecePerScan_moreThanOnePiece_rejected()
	{
		assertThatThrownBy(() -> ReceiveGoodsCommand.computeQtyToReceive(BigDecimal.valueOf(2), uomKgId, null, true))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(ReceiveGoodsCommand.MSG_CatchWeightReceiveOneAtATime.toAD_Message());
	}

	@Test
	void onePiecePerScan_fractionOfAPiece_rejected()
	{
		assertThatThrownBy(() -> ReceiveGoodsCommand.computeQtyToReceive(new BigDecimal("0.5"), uomKgId, null, true))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(ReceiveGoodsCommand.MSG_CatchWeightReceiveOneAtATime.toAD_Message());
	}

	@Test
	void onePiecePerScan_onePiece_accepted()
	{
		final Quantity result = ReceiveGoodsCommand.computeQtyToReceive(new BigDecimal("1.000"), uomKgId, null, true);

		assertThat(result.toBigDecimal()).isEqualByComparingTo("1");
	}

	@Test
	void notOnePiecePerScan_TUMode_qtyIsTUs()
	{
		// setting off: TU mode behaviour unchanged, 1 TU = the packing's 4 pieces
		final Quantity result = ReceiveGoodsCommand.computeQtyToReceive(BigDecimal.ONE, uomKgId, finiteHUPIItemProduct(4), false);

		assertThat(result.toBigDecimal()).isEqualByComparingTo("4");
	}

	/**
	 * Scan required but weight not captured at receipt: a normal receipt of N, e.g. 3 blocks with the last block's lot and best-before date.
	 */
	@Test
	void notOnePiecePerScan_CUMode_qtyIsPieces()
	{
		final Quantity result = ReceiveGoodsCommand.computeQtyToReceive(BigDecimal.valueOf(3), uomKgId, null, false);

		assertThat(result.toBigDecimal()).isEqualByComparingTo("3");
	}

	@Test
	void notOnePiecePerScan_TUMode_twoTUs()
	{
		final Quantity result = ReceiveGoodsCommand.computeQtyToReceive(BigDecimal.valueOf(2), uomKgId, finiteHUPIItemProduct(4), false);

		assertThat(result.toBigDecimal()).isEqualByComparingTo("8");
	}

	@Test
	void isFull_belowCapacity()
	{
		assertThat(ReceiveGoodsCommand.isFull(finiteHUPIItemProduct(2), uom -> new Quantity(BigDecimal.ONE, uomKg))).isFalse();
	}

	@Test
	void isFull_atCapacity()
	{
		assertThat(ReceiveGoodsCommand.isFull(finiteHUPIItemProduct(2), uom -> new Quantity(BigDecimal.valueOf(2), uomKg))).isTrue();
	}

	@Test
	void isFull_infiniteCapacity_never()
	{
		assertThat(ReceiveGoodsCommand.isFull(infiniteHUPIItemProduct(), uom -> {
			throw new AssertionError("the TU's qty is not needed for an infinite capacity");
		})).isFalse();
	}

	/**
	 * Only the newest TU on the LU can be the current one (the older ones were filled before it was started),
	 * so only that one is checked for packing and capacity, not every TU of the pallet on every scan.
	 */
	@Test
	void findCurrentNotFullTU_checksOnlyTheNewestRealTU()
	{
		final I_M_HU olderTU = newHU();
		final I_M_HU newestTU = newHU();
		final List<I_M_HU> checked = new ArrayList<>();

		final Optional<I_M_HU> currentTU = ReceiveGoodsCommand.findCurrentNotFullTU(
				ImmutableList.of(newestTU, olderTU),
				hu -> true,
				hu -> {
					checked.add(hu);
					return true;
				});

		assertThat(currentTU).containsSame(newestTU);
		assertThat(checked).containsExactly(newestTU);
	}

	@Test
	void findCurrentNotFullTU_newestTUFull_none()
	{
		final I_M_HU olderNotFullTU = newHU();
		final I_M_HU newestFullTU = newHU();

		final Optional<I_M_HU> currentTU = ReceiveGoodsCommand.findCurrentNotFullTU(
				ImmutableList.of(olderNotFullTU, newestFullTU),
				hu -> true,
				hu -> hu != newestFullTU);

		assertThat(currentTU).isEmpty();
	}

	@Test
	void findCurrentNotFullTU_ignoresWhatIsNotARealTU()
	{
		final I_M_HU realTU = newHU();
		final I_M_HU newestAggregate = newHU();

		final Optional<I_M_HU> currentTU = ReceiveGoodsCommand.findCurrentNotFullTU(
				ImmutableList.of(realTU, newestAggregate),
				hu -> hu != newestAggregate,
				hu -> true);

		assertThat(currentTU).containsSame(realTU);
	}

	private static I_M_HU newHU()
	{
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		InterfaceWrapperHelper.saveRecord(hu);
		return hu;
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
