package de.metas.manufacturing.job.model;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueSchedule;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.PPOrderBOMLineId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reproduces issuing from a piece-stocked HU (Stk) against a BOM line measured in kg.
 * <p>
 * The BOM line's {@code qtyToIssue} is in the order-line UOM (kg) while the picked step's issued qty
 * comes back in the HU's UOM (Stk). {@code RawMaterialsIssueLine} compared/subtracted the two without
 * converting, throwing {@code QuantitiesUOMNotMatchingException} at {@code computeStatus} /
 * {@code getQtyLeftToIssue} when the step is marked issued via {@link RawMaterialsIssueLine#withChangedRawMaterialsIssueStep}
 * (the exact production path: {@code IssueRawMaterialsCommand}).
 */
@ExtendWith(AdempiereTestWatcher.class)
class RawMaterialsIssueLineTest
{
	private static final PPOrderIssueScheduleId SCHEDULE_ID = PPOrderIssueScheduleId.ofRepoId(1);

	private I_C_UOM uomKg;
	private I_C_UOM uomStk;
	private UomId uomKgId;
	private ProductId productId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uomKg = BusinessTestHelper.createUOM("Kg", 3);
		uomStk = BusinessTestHelper.createUOM("Stk", 0); // pieces are whole units (precision 0)
		uomKgId = UomId.ofRepoId(uomKg.getC_UOM_ID());

		// Product stocked in pieces (1 Stk = 35 kg)
		final I_M_Product product = BusinessTestHelper.createProduct("PieceStocked35kg", uomStk);
		productId = ProductId.ofRepoId(product.getM_Product_ID());

		// 1 Stk = 35 kg
		BusinessTestHelper.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(productId)
				.fromUomId(UomId.ofRepoId(uomStk.getC_UOM_ID()))
				.toUomId(uomKgId)
				.fromToMultiplier(new BigDecimal("35"))
				.build());
	}

	private RawMaterialsIssueStep newStep(final PPOrderIssueScheduleId id, final String qtyToIssueKg)
	{
		final LocatorId locatorId = LocatorId.ofRepoId(1, 1);
		return RawMaterialsIssueStep.builder()
				.id(id)
				.isAlternativeIssue(false)
				.productId(productId)
				.productName(TranslatableStrings.anyLanguage("PieceStocked35kg"))
				.qtyToIssue(Quantity.of(qtyToIssueKg, uomKg))
				.issueFromLocator(LocatorInfo.builder()
						.id(locatorId)
						.caption("Hauptlager")
						.qrCode(LocatorQRCode.builder().locatorId(locatorId).caption("Hauptlager").build())
						.build())
				.issueFromHU(HUInfo.builder()
						.id(HuId.ofRepoId(1))
						.huCapacity(Quantity.of("40", uomStk))
						.build())
				.issued(null)
				.build();
	}

	private RawMaterialsIssueLine newLine(final String qtyToIssueKg, final ImmutableList<RawMaterialsIssueStep> steps)
	{
		return RawMaterialsIssueLine.builder()
				.uomConversionBL(Services.get(IUOMConversionBL.class))
				.orderBOMLineId(PPOrderBOMLineId.ofRepoId(1))
				.productId(productId)
				.productName(TranslatableStrings.anyLanguage("PieceStocked35kg"))
				.productValue("PieceStocked35kg")
				.isWeightable(false)
				.qtyToIssue(Quantity.of(qtyToIssueKg, uomKg))
				.steps(steps)
				.seqNo(10)
				.build();
	}

	private static RawMaterialsIssueStep issueStk(final RawMaterialsIssueStep step, final String qtyStk, final I_C_UOM uomStk)
	{
		return step.withIssued(PPOrderIssueSchedule.Issued.builder()
				.qtyIssued(Quantity.of(qtyStk, uomStk))
				.build());
	}

	@Test
	void issuingStkFromHU_againstKgBomLine_normalizesQtyIssuedToKg()
	{
		final RawMaterialsIssueLine line = newLine("35", ImmutableList.of(newStep(SCHEDULE_ID, "35")));
		assertThat(line.getStatus()).isEqualTo(WFActivityStatus.NOT_STARTED);

		// Pick 1 Stk (= 35 kg) from the piece-stocked HU — the production path through IssueRawMaterialsCommand.
		final RawMaterialsIssueLine issued = line.withChangedRawMaterialsIssueStep(
				SCHEDULE_ID,
				step -> issueStk(step, "1", uomStk));

		// qtyIssued must be expressed in the BOM line's UOM (kg), not the picked HU's UOM (Stk).
		assertThat(issued.getQtyIssued().getUomId()).isEqualTo(uomKgId);
		assertThat(issued.getQtyIssued().toBigDecimal()).isEqualByComparingTo("35");
		assertThat(issued.getQtyLeftToIssue().getUomId()).isEqualTo(uomKgId);
		assertThat(issued.getQtyLeftToIssue().toBigDecimal()).isEqualByComparingTo("0");
		assertThat(issued.getStatus()).isEqualTo(WFActivityStatus.COMPLETED);
	}

	@Test
	void remainingQtyToIssueMaxInUOM_convertsKgDemandToWholeStk_roundedUp()
	{
		final UomId uomStkId = UomId.ofRepoId(uomStk.getC_UOM_ID());

		// 34.5 kg still to issue against a 35 kg/Stk product => 0.986 Stk => rounded UP to 1 Stk.
		final RawMaterialsIssueLine line = newLine("34.5", ImmutableList.of(newStep(SCHEDULE_ID, "34.5")));

		final Quantity max = line.getRemainingQtyToIssueMaxInUOM(uomStkId);
		assertThat(max.getUomId()).isEqualTo(uomStkId);
		assertThat(max.toBigDecimal()).isEqualByComparingTo("1");
	}

	@Test
	void remainingQtyToIssueMaxInUOM_afterPartialIssue_convertsTheRemainderInStk()
	{
		final UomId uomStkId = UomId.ofRepoId(uomStk.getC_UOM_ID());

		// 70 kg demand, one 35 kg (=1 Stk) already issued => 35 kg left => exactly 1 Stk.
		RawMaterialsIssueLine line = newLine("70", ImmutableList.of(newStep(SCHEDULE_ID, "70")));
		line = line.withChangedRawMaterialsIssueStep(SCHEDULE_ID, step -> issueStk(step, "1", uomStk));

		final Quantity max = line.getRemainingQtyToIssueMaxInUOM(uomStkId);
		assertThat(max.getUomId()).isEqualTo(uomStkId);
		assertThat(max.toBigDecimal()).isEqualByComparingTo("1");
	}

	@Test
	void issuingStkFromHU_acrossTwoSteps_sumsConvertedQtyInKg()
	{
		final PPOrderIssueScheduleId id1 = PPOrderIssueScheduleId.ofRepoId(1);
		final PPOrderIssueScheduleId id2 = PPOrderIssueScheduleId.ofRepoId(2);

		RawMaterialsIssueLine line = newLine("70", ImmutableList.of(newStep(id1, "35"), newStep(id2, "35")));

		// First of two steps issued: 35 kg of 70 kg -> IN_PROGRESS (and must not crash on mixed UOMs).
		line = line.withChangedRawMaterialsIssueStep(id1, step -> issueStk(step, "1", uomStk));
		assertThat(line.getQtyIssued().toBigDecimal()).isEqualByComparingTo("35");
		assertThat(line.getStatus()).isEqualTo(WFActivityStatus.IN_PROGRESS);

		// Second step issued: reduce(Quantity::add) sums the two converted-to-kg quantities -> 70 kg, COMPLETED.
		line = line.withChangedRawMaterialsIssueStep(id2, step -> issueStk(step, "1", uomStk));
		assertThat(line.getQtyIssued().getUomId()).isEqualTo(uomKgId);
		assertThat(line.getQtyIssued().toBigDecimal()).isEqualByComparingTo("70");
		assertThat(line.getQtyLeftToIssue().toBigDecimal()).isEqualByComparingTo("0");
		assertThat(line.getStatus()).isEqualTo(WFActivityStatus.COMPLETED);
	}
}
