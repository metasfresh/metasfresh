package de.metas.handlingunits.pporder.api.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.document.sequenceno.DateSequenceProvider;
import de.metas.handlingunits.allocation.impl.LUTUConfigurationPackingInstructionChecker;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Sequence;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class HUPPOrderBL_LotNumberFromSequence_Test
{
	private static final String SYSCONFIG_DATE_FORMAT = "de.metas.document.seqNo.DateSequenceProvider.dateFormat";

	private HUPPOrderBL huPPOrderBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		SystemTime.setFixedTimeSource(Instant.parse("2026-06-15T10:00:00Z").atZone(ZoneId.systemDefault()));
		Services.registerService(IDocumentNoBuilderFactory.class, DocumentNoBuilderFactory.newInstanceForUnitTesting());
		Services.get(ISysConfigBL.class).setValue(SYSCONFIG_DATE_FORMAT, "yy-DDD", ClientId.SYSTEM, OrgId.ANY);

		huPPOrderBL = new HUPPOrderBL();
	}

	@AfterEach
	public void afterEach()
	{
		SystemTime.resetTimeSource();
	}

	@Test
	public void lotSequenceConfigured_yieldsYYDDDOfReceiptDate()
	{
		final PPOrderId ppOrderId = createPPOrderBOM(createDateLotSequence());

		assertThat(huPPOrderBL.getAndIncrementLotNumberFromSequence(ppOrderId, LocalDate.of(2026, 10, 19))).contains("26-292");
		assertThat(huPPOrderBL.getAndIncrementLotNumberFromSequence(ppOrderId, LocalDate.of(2026, 1, 5))).contains("26-005");
	}

	@Test
	public void noLotSequenceConfigured_empty()
	{
		final PPOrderId ppOrderId = createPPOrderBOM(null);

		assertThat(huPPOrderBL.getAndIncrementLotNumberFromSequence(ppOrderId, LocalDate.of(2026, 10, 19))).isEmpty();
	}

	@Test
	public void receipt_lotSequenceConfigured_yieldsYYDDDOfMovementDate()
	{
		final PPOrderId ppOrderId = createPPOrderBOM(createDateLotSequence());

		assertThat(receiptLotNumberFromSequence(ppOrderId, LocalDate.of(2026, 10, 19).atStartOfDay(ZoneId.systemDefault()))).isEqualTo("26-292");
	}

	@Test
	public void receipt_noLotSequenceConfigured_noLotNumber()
	{
		final PPOrderId ppOrderId = createPPOrderBOM(null);

		assertThat(receiptLotNumberFromSequence(ppOrderId, SystemTime.asZonedDateTime())).isNull();
	}

	@Test
	public void receipt_movementDayDiffersInOrgTimeZone_lotAndProductionDateUseOrgDay()
	{
		final OrgId orgId = AdempiereTestHelper.createOrgWithTimeZone(ZoneId.of("Pacific/Auckland"));
		final PPOrderId ppOrderId = createPPOrderBOM(createPPOrder(orgId), createDateLotSequence());
		final AbstractPPOrderReceiptHUProducer producer = newReceiptProducer(ppOrderId, ZonedDateTime.parse("2026-10-19T12:00:00Z")); // 2026-10-20 01:00 in Auckland

		assertThat(producer.getOrLoadLotNumberFromSeq()).isEqualTo("26-293");
		assertThat(producer.getProductionDate()).isEqualTo(LocalDate.of(2026, 10, 20));
	}

	@Test
	public void receipt_noPPOrderBOM_noLotNumber()
	{
		final PPOrderId ppOrderId = createPPOrder();

		assertThat(receiptLotNumberFromSequence(ppOrderId, SystemTime.asZonedDateTime())).isNull();
	}

	@Test
	public void preview_dateOnlyLotSequence_yieldsYYDDDOfReceiptDate()
	{
		final PPOrderId ppOrderId = createPPOrderBOM(createDateLotSequence());

		assertThat(huPPOrderBL.previewLotNumberFromSequence(ppOrderId, LocalDate.of(2026, 10, 19))).contains("26-292");
	}

	@Test
	public void preview_lotSequenceWithCounter_empty()
	{
		final PPOrderId ppOrderId = createPPOrderBOM(createDateLotSequence("0000"));

		assertThat(huPPOrderBL.previewLotNumberFromSequence(ppOrderId, LocalDate.of(2026, 10, 19))).isEmpty();
	}

	@Test
	public void preview_invalidDateFormat_emptyWithoutException()
	{
		Services.get(ISysConfigBL.class).setValue(SYSCONFIG_DATE_FORMAT, "invalid-qq", ClientId.SYSTEM, OrgId.ANY);
		final PPOrderId ppOrderId = createPPOrderBOM(createDateLotSequence());

		assertThat(huPPOrderBL.previewLotNumberFromSequence(ppOrderId, LocalDate.of(2026, 10, 19))).isEmpty();
	}

	/**
	 * The lot sequence has its own date format (AD_Sequence.DateFormat), while the global one (used by other date sequences, e.g. with a counter) differs.
	 */
	@Test
	public void sequenceDateFormat_previewAndReceiptUseIt()
	{
		Services.get(ISysConfigBL.class).setValue(SYSCONFIG_DATE_FORMAT, "yyMMdd", ClientId.SYSTEM, OrgId.ANY);
		final DocSequenceId lotSequenceId = createDateLotSequence();
		final I_AD_Sequence sequence = load(lotSequenceId, I_AD_Sequence.class);
		sequence.setDateFormat("yy-DDD");
		saveRecord(sequence);
		final PPOrderId ppOrderId = createPPOrderBOM(lotSequenceId);

		assertThat(huPPOrderBL.previewLotNumberFromSequence(ppOrderId, LocalDate.of(2026, 10, 23))).contains("26-296");
		assertThat(receiptLotNumberFromSequence(ppOrderId, LocalDate.of(2026, 10, 23).atStartOfDay(ZoneId.systemDefault()))).isEqualTo("26-296");
	}

	@Test
	public void preview_noLotSequenceConfigured_empty()
	{
		final PPOrderId ppOrderId = createPPOrderBOM(null);

		assertThat(huPPOrderBL.previewLotNumberFromSequence(ppOrderId, LocalDate.of(2026, 10, 19))).isEmpty();
	}

	@Test
	public void preview_noPPOrderBOM_empty()
	{
		assertThat(huPPOrderBL.previewLotNumberFromSequence(createPPOrder(), LocalDate.of(2026, 10, 19))).isEmpty();
	}

	public static DocSequenceId createDateLotSequence()
	{
		return createDateLotSequence(null);
	}

	private static DocSequenceId createDateLotSequence(@Nullable final String decimalPattern)
	{
		final I_AD_JavaClass javaClass = newInstanceOutOfTrx(I_AD_JavaClass.class);
		javaClass.setName("DateSequenceProvider");
		javaClass.setClassname(DateSequenceProviderWithoutIncrement.class.getName());
		saveRecord(javaClass);

		final I_AD_Sequence sequence = newInstanceOutOfTrx(I_AD_Sequence.class);
		sequence.setName("LotNo");
		sequence.setIsAutoSequence(true);
		sequence.setIncrementNo(1);
		sequence.setCurrentNext(1);
		sequence.setDateColumn("MovementDate");
		sequence.setDecimalPattern(decimalPattern);
		sequence.setCustomSequenceNoProvider_JavaClass_ID(javaClass.getAD_JavaClass_ID());
		saveRecord(sequence);
		return DocSequenceId.ofRepoId(sequence.getAD_Sequence_ID());
	}

	private static PPOrderId createPPOrderBOM(@Nullable final DocSequenceId lotNoSequenceId)
	{
		return createPPOrderBOM(createPPOrder(), lotNoSequenceId);
	}

	public static PPOrderId createPPOrderBOM(@NonNull final PPOrderId ppOrderId, @Nullable final DocSequenceId lotNoSequenceId)
	{
		final I_PP_Order_BOM ppOrderBOM = newInstanceOutOfTrx(I_PP_Order_BOM.class);
		ppOrderBOM.setPP_Order_ID(ppOrderId.getRepoId());
		ppOrderBOM.setLotNo_Sequence_ID(lotNoSequenceId != null ? lotNoSequenceId.getRepoId() : -1);
		saveRecord(ppOrderBOM);
		return ppOrderId;
	}

	private static PPOrderId createPPOrder()
	{
		return createPPOrder(OrgId.ANY);
	}

	public static PPOrderId createPPOrder(@NonNull final OrgId orgId)
	{
		final I_PP_Order ppOrder = newInstanceOutOfTrx(I_PP_Order.class);
		ppOrder.setAD_Org_ID(orgId.getRepoId());
		ppOrder.setM_Product_ID(1);
		saveRecord(ppOrder);
		return PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
	}

	@Nullable
	private static String receiptLotNumberFromSequence(@NonNull final PPOrderId ppOrderId, @NonNull final ZonedDateTime movementDate)
	{
		return newReceiptProducer(ppOrderId, movementDate).getOrLoadLotNumberFromSeq();
	}

	private static AbstractPPOrderReceiptHUProducer newReceiptProducer(@NonNull final PPOrderId ppOrderId, @NonNull final ZonedDateTime movementDate)
	{
		final AbstractPPOrderReceiptHUProducer producer = new CostCollectorCandidateFinishedGoodsHUProducer(load(ppOrderId, I_PP_Order.class), LUTUConfigurationPackingInstructionChecker::new);
		producer.movementDate(movementDate);
		return producer;
	}

	/**
	 * Same output as {@link DateSequenceProvider} when the sequence has no decimal pattern;
	 * it only skips the incremental number, whose retrieval needs a real database.
	 */
	public static class DateSequenceProviderWithoutIncrement extends DateSequenceProvider
	{
		@Override
		public boolean isUseIncrementSeqNoAsPrefix()
		{
			return false;
		}
	}
}
