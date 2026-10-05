package de.metas.contracts.refund.grossprofit;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig_PackingOption;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.BonusRecipient;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.packaging.RefundPackagingFilter;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.money.grossprofit.CalculateProfitPriceActualRequest;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class RefundProfitPriceActualComponentTest
{
	private static final LocalDate DATE = LocalDate.of(2026, 7, 15);
	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(21);
	private static final BPartnerId SHIPMENT_BPARTNER_ID = BPartnerId.ofRepoId(23);
	private static final HUPIItemProductId CARTON_PI = HUPIItemProductId.ofRepoId(31);
	private static final HUPIItemProductId CRATE_PI = HUPIItemProductId.ofRepoId(32);
	private static final HUPIItemProductId PI_WITHOUT_PACKING_MATERIAL = HUPIItemProductId.ofRepoId(33);
	private static final int CARTON = 201;
	private static final int CRATE = 202;
	private static final Map<HUPIItemProductId, Integer> PACKING_MATERIAL_BY_PI = ImmutableMap.of(CARTON_PI, CARTON, CRATE_PI, CRATE);
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(22);

	private RefundContractRepository refundContractRepository;
	private MoneyService moneyService;
	private RefundPackagingFilter refundPackagingFilter;
	private CurrencyId currencyId;
	private I_C_InvoiceSchedule invoiceSchedule;
	private I_C_UOM uom;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		refundContractRepository = new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository()));
		moneyService = new MoneyService(new CurrencyRepository());
		refundPackagingFilter = new RefundPackagingFilter(Optional.of(ImmutableList.of((piItemProductId, bpartnerId) -> Optional.ofNullable(PACKING_MATERIAL_BY_PI.get(piItemProductId)))));
		currencyId = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId();

		invoiceSchedule = newInstance(I_C_InvoiceSchedule.class);
		invoiceSchedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		invoiceSchedule.setInvoiceDay(28);
		invoiceSchedule.setInvoiceDistance(1);
		saveRecord(invoiceSchedule);

		uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
	}

	/**
	 * The percentages of the parallel terms are summed up: 100 - (10% + 5%) = 85, and not 100 * 0.9 * 0.95 = 85.5
	 */
	@Test
	public void applyToInput_subtractsTheSumOfAllMatchingPercentages()
	{
		createTermWithPercentageConfig(new BigDecimal("10"), true);
		createTermWithPercentageConfig(new BigDecimal("5"), true);
		createTermWithPercentageConfig(new BigDecimal("50"), false); // not used in the profit calculation

		final Money result = applyToInput(Money.of(100, currencyId));

		assertThat(result.toBigDecimal()).isEqualByComparingTo("85");
	}

	@Test
	public void applyToInput_withoutMatchingTerm_returnsTheInput()
	{
		final Money result = applyToInput(Money.of(100, currencyId));

		assertThat(result.toBigDecimal()).isEqualByComparingTo("100");
	}

	/**
	 * A term of the partner that the goods are shipped to, whose bonus recipient is the shipment partner, reduces the profit price as well.
	 */
	@Test
	public void applyToInput_subtractsTheTermsOfTheShipmentPartner()
	{
		createTermWithPercentageConfig(new BigDecimal("10"), true);
		createTermWithPercentageConfig(SHIPMENT_BPARTNER_ID, BonusRecipient.SHIPMENT_PARTNER, new BigDecimal("5"), true);
		// not matching: the shipment partner's term is for its invoice partner, the invoice partner's term for its shipment partner
		createTermWithPercentageConfig(SHIPMENT_BPARTNER_ID, BonusRecipient.INVOICE_PARTNER, new BigDecimal("20"), true);
		createTermWithPercentageConfig(BPARTNER_ID, BonusRecipient.SHIPMENT_PARTNER, new BigDecimal("30"), true);

		assertThat(applyToInput(Money.of(100, currencyId), SHIPMENT_BPARTNER_ID).toBigDecimal()).isEqualByComparingTo("85");
	}

	@Test
	public void applyToInput_withoutShipmentPartner_ignoresTheTermsForTheShipmentPartner()
	{
		createTermWithPercentageConfig(new BigDecimal("10"), true);
		createTermWithPercentageConfig(SHIPMENT_BPARTNER_ID, BonusRecipient.SHIPMENT_PARTNER, new BigDecimal("5"), true);

		assertThat(applyToInput(Money.of(100, currencyId), null).toBigDecimal()).isEqualByComparingTo("90");
	}

	/**
	 * A term that is restricted to cartons does not reduce the profit price of a line on a crate, or of a line without packing instruction.
	 */
	@Test
	public void applyToInput_skipsTheTermsWhosePackagingDoesNotMatch()
	{
		createTermWithPercentageConfig(new BigDecimal("10"), true);
		final ConditionsId cartonConditionsId = createTermWithPercentageConfig(BPARTNER_ID, BonusRecipient.INVOICE_PARTNER, new BigDecimal("5"), true);
		final I_C_Flatrate_RefundConfig cartonConfig = retrieveConfig(cartonConditionsId);
		cartonConfig.setIsPackingOptionFiltered(true);
		saveRecord(cartonConfig);
		final I_C_Flatrate_RefundConfig_PackingOption option = newInstance(I_C_Flatrate_RefundConfig_PackingOption.class);
		option.setC_Flatrate_Conditions_ID(cartonConditionsId.getRepoId());
		option.setC_Flatrate_RefundConfig_ID(cartonConfig.getC_Flatrate_RefundConfig_ID());
		option.setM_HU_PackingMaterial_ID(CARTON);
		saveRecord(option);

		assertThat(applyToInput(Money.of(100, currencyId), null, CARTON_PI).toBigDecimal()).isEqualByComparingTo("85");
		assertThat(applyToInput(Money.of(100, currencyId), null, CRATE_PI).toBigDecimal()).isEqualByComparingTo("90");
		assertThat(applyToInput(Money.of(100, currencyId), null, PI_WITHOUT_PACKING_MATERIAL).toBigDecimal()).isEqualByComparingTo("90");
		assertThat(applyToInput(Money.of(100, currencyId), null, null).toBigDecimal()).isEqualByComparingTo("90");
	}

	private static I_C_Flatrate_RefundConfig retrieveConfig(@NonNull final ConditionsId conditionsId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Flatrate_RefundConfig.class)
				.addEqualsFilter(I_C_Flatrate_RefundConfig.COLUMNNAME_C_Flatrate_Conditions_ID, conditionsId)
				.create()
				.firstOnlyNotNull(I_C_Flatrate_RefundConfig.class);
	}

	private Money applyToInput(@NonNull final Money input)
	{
		return applyToInput(input, null);
	}

	private Money applyToInput(@NonNull final Money input, @Nullable final BPartnerId shipmentBPartnerId)
	{
		return applyToInput(input, shipmentBPartnerId, null);
	}

	private Money applyToInput(@NonNull final Money input, @Nullable final BPartnerId shipmentBPartnerId, @Nullable final HUPIItemProductId huPIItemProductId)
	{
		final CalculateProfitPriceActualRequest request = CalculateProfitPriceActualRequest.builder()
				.bPartnerId(BPARTNER_ID)
				.shipmentBPartnerId(shipmentBPartnerId)
				.huPIItemProductId(huPIItemProductId)
				.productId(PRODUCT_ID)
				.date(DATE)
				.baseAmount(input)
				.quantity(Quantity.of(BigDecimal.ONE, uom))
				.build();

		return new RefundProfitPriceActualComponent(request, refundContractRepository, moneyService, refundPackagingFilter).applyToInput(input);
	}

	private void createTermWithPercentageConfig(@NonNull final BigDecimal percent, final boolean useInProfitCalculation)
	{
		createTermWithPercentageConfig(BPARTNER_ID, BonusRecipient.INVOICE_PARTNER, percent, useInProfitCalculation);
	}

	private ConditionsId createTermWithPercentageConfig(@NonNull final BPartnerId termBPartnerId, @NonNull final BonusRecipient bonusRecipient, @NonNull final BigDecimal percent, final boolean useInProfitCalculation)
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditions);

		final I_C_Flatrate_RefundConfig config = newInstance(I_C_Flatrate_RefundConfig.class);
		config.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		config.setC_InvoiceSchedule_ID(invoiceSchedule.getC_InvoiceSchedule_ID());
		config.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		config.setRefundPercent(percent);
		config.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		config.setMinQty(BigDecimal.ZERO);
		config.setBonusRecipient(bonusRecipient.getCode());
		config.setIsUseInProfitCalculation(useInProfitCalculation);
		saveRecord(config);

		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		term.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		term.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		term.setBill_BPartner_ID(termBPartnerId.getRepoId());
		term.setM_Product_ID(PRODUCT_ID.getRepoId());
		term.setStartDate(TimeUtil.asTimestamp(DATE.minusDays(5)));
		term.setEndDate(TimeUtil.asTimestamp(DATE.plusDays(5)));
		saveRecord(term);

		return ConditionsId.ofRepoId(conditions.getC_Flatrate_Conditions_ID());
	}
}
