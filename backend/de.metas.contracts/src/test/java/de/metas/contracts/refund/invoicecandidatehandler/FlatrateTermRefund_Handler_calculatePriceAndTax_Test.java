package de.metas.contracts.refund.invoicecandidatehandler;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FlatrateTermRefund_Handler_calculatePriceAndTax_Test
{
	private static final TaxCategoryId TAX_CATEGORY_ID = TaxCategoryId.ofRepoId(51);
	private static final TaxId TAX_ID = TaxId.ofRepoId(52);
	private static final OrgId ORG_ID = OrgId.ofRepoId(1);
	private static final BPartnerId BILL_BPARTNER_ID = BPartnerId.ofRepoId(30);
	private static final int BILL_LOCATION_ID = 77;
	private static final CountryId COUNTRY_ID = CountryId.ofRepoId(78);
	private static final LocalDate DATE_ORDERED = LocalDate.of(2026, 7, 31);

	private FlatrateTermRefund_Handler handler;
	private I_C_InvoiceSchedule invoiceSchedule;
	private IPricingBL pricingBL;
	private ITaxBL taxBL;
	private IEditablePricingContext pricingContext;
	private IPricingResult pricingResult;
	private I_C_UOM uom;
	private CurrencyId eur;
	private CurrencyId chf;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository())));

		pricingBL = Mockito.mock(IPricingBL.class);
		Services.registerService(IPricingBL.class, pricingBL);
		pricingContext = Mockito.mock(IEditablePricingContext.class, Mockito.RETURNS_SELF);
		pricingResult = Mockito.mock(IPricingResult.class);
		when(pricingResult.isCalculated()).thenReturn(true);
		when(pricingResult.getTaxCategoryId()).thenReturn(TAX_CATEGORY_ID);
		when(pricingBL.calculatePrice(pricingContext)).thenReturn(pricingResult);

		taxBL = Mockito.mock(ITaxBL.class);
		when(taxBL.getTaxNotNull(any(), any(), anyIntValue(), any(), any(), any(), any(), any())).thenReturn(TAX_ID);
		Services.registerService(ITaxBL.class, taxBL);

		final IOrgDAO orgDAO = Mockito.mock(IOrgDAO.class);
		when(orgDAO.getTimeZone(any(OrgId.class))).thenReturn(ZoneId.systemDefault());
		Services.registerService(IOrgDAO.class, orgDAO);

		final IBPartnerDAO bpartnerDAO = Mockito.mock(IBPartnerDAO.class);
		when(bpartnerDAO.getCountryId(any(BPartnerLocationId.class))).thenReturn(COUNTRY_ID);
		Services.registerService(IBPartnerDAO.class, bpartnerDAO);

		handler = new FlatrateTermRefund_Handler(); // after the services are registered, because it holds them as fields

		invoiceSchedule = newInstance(I_C_InvoiceSchedule.class);
		invoiceSchedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		invoiceSchedule.setInvoiceDay(28);
		invoiceSchedule.setInvoiceDistance(1);
		saveRecord(invoiceSchedule);

		uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		eur = PlainCurrencyDAO.createCurrency(CurrencyCode.EUR).getId();
		chf = PlainCurrencyDAO.createCurrency(CurrencyCode.CHF).getId();
	}

	private static int anyIntValue()
	{
		return org.mockito.ArgumentMatchers.anyInt();
	}

	/**
	 * The tax category comes from the pricing of the bonus product (for the bill partner, the bill location's country and the date), the tax from that category and the bill location.
	 */
	@Test
	public void taxFollowsTheBonusProduct()
	{
		final ProductId bonusProductId = createProduct();
		final I_C_Invoice_Candidate ic = createRefundCandidate(bonusProductId, null, BILL_BPARTNER_ID.getRepoId(), DATE_ORDERED, null);
		when(pricingBL.createInitialContext(eq(ORG_ID), eq(bonusProductId), eq(BILL_BPARTNER_ID), argThat(quantity -> quantity.toBigDecimal().compareTo(BigDecimal.ONE) == 0 && quantity.getUomId().getRepoId() == uom.getC_UOM_ID()), eq(SOTrx.SALES))).thenReturn(pricingContext);

		final PriceAndTax result = handler.calculatePriceAndTax(ic);

		assertThat(result.getTaxId()).isEqualTo(TAX_ID);
		assertThat(result.getTaxCategoryId()).isEqualTo(TAX_CATEGORY_ID);
		verify(pricingContext).setPriceDate(DATE_ORDERED);
		verify(pricingContext).setCountryId(COUNTRY_ID);

		final ArgumentCaptor<BPartnerLocationAndCaptureId> locationCaptor = ArgumentCaptor.forClass(BPartnerLocationAndCaptureId.class);
		final ArgumentCaptor<Timestamp> dateCaptor = ArgumentCaptor.forClass(Timestamp.class);
		verify(taxBL).getTaxNotNull(any(), eq(TAX_CATEGORY_ID), eq(bonusProductId.getRepoId()), dateCaptor.capture(), eq(ORG_ID), any(), locationCaptor.capture(), eq(SOTrx.SALES));
		assertThat(TimeUtil.asLocalDate(dateCaptor.getValue(), ZoneId.systemDefault())).isEqualTo(DATE_ORDERED);
		assertThat(locationCaptor.getValue().getBpartnerLocationId()).isEqualTo(BPartnerLocationId.ofRepoId(BILL_BPARTNER_ID, BILL_LOCATION_ID));
	}

	/**
	 * Without a bonus product, the config's product is the one that the refund is booked on.
	 */
	@Test
	public void withoutBonusProduct_taxFollowsTheConfigProduct()
	{
		final ProductId configProductId = createProduct();
		final I_C_Invoice_Candidate ic = createRefundCandidate(null, configProductId, BILL_BPARTNER_ID.getRepoId(), DATE_ORDERED, null);
		when(pricingBL.createInitialContext(eq(ORG_ID), eq(configProductId), eq(BILL_BPARTNER_ID), any(), eq(SOTrx.SALES))).thenReturn(pricingContext);

		final PriceAndTax result = handler.calculatePriceAndTax(ic);

		assertThat(result.getTaxId()).isEqualTo(TAX_ID);
	}

	/**
	 * Without a bonus product and a product, nothing is looked up and the tax stays as it is.
	 */
	@Test
	public void withoutProduct_taxRemainsUnchanged()
	{
		final I_C_Invoice_Candidate ic = createRefundCandidate(null, null, BILL_BPARTNER_ID.getRepoId(), DATE_ORDERED, null);

		assertThat(handler.calculatePriceAndTax(ic)).isSameAs(PriceAndTax.NONE);
		verify(pricingBL, never()).calculatePrice(any());
	}

	/**
	 * If the bonus product has no price for the bill partner, then the pricing fails (and the invoice candidate gets an error), instead of the refund keeping the tax of the goods.
	 */
	@Test
	public void priceNotFound_fails()
	{
		final ProductId bonusProductId = createProduct();
		final I_C_Invoice_Candidate ic = createRefundCandidate(bonusProductId, null, BILL_BPARTNER_ID.getRepoId(), DATE_ORDERED, null);
		when(pricingBL.createInitialContext(any(), any(), any(), any(), any())).thenReturn(pricingContext);
		final ProductNotOnPriceListException productNotOnPriceListException = new ProductNotOnPriceListException(pricingContext);
		when(pricingBL.calculatePrice(pricingContext)).thenThrow(productNotOnPriceListException);

		assertThatThrownBy(() -> handler.calculatePriceAndTax(ic)).isSameAs(productNotOnPriceListException);
		verify(pricingContext).setFailIfNotCalculated();
	}

	/**
	 * If the price has no tax category, then the tax is not found (and the invoice candidate gets an error), instead of the refund keeping the tax of the goods.
	 */
	@Test
	public void noTaxCategory_taxNotFound()
	{
		final ProductId bonusProductId = createProduct();
		final I_C_Invoice_Candidate ic = createRefundCandidate(bonusProductId, null, BILL_BPARTNER_ID.getRepoId(), DATE_ORDERED, null);
		when(pricingBL.createInitialContext(any(), any(), any(), any(), any())).thenReturn(pricingContext);
		when(pricingResult.getTaxCategoryId()).thenReturn(null);
		when(taxBL.getTaxNotNull(any(), isNull(), anyIntValue(), any(), any(), any(), any(), any())).thenReturn(TaxId.ofRepoId(Tax.C_TAX_ID_NO_TAX_FOUND));

		assertThat(handler.calculatePriceAndTax(ic).getTaxId()).isEqualTo(TaxId.ofRepoId(Tax.C_TAX_ID_NO_TAX_FOUND));
	}

	@Test
	public void withoutBillPartner_taxRemainsUnchanged()
	{
		final ProductId bonusProductId = createProduct();
		final I_C_Invoice_Candidate ic = createRefundCandidate(bonusProductId, null, 0, DATE_ORDERED, null);

		assertThat(handler.calculatePriceAndTax(ic)).isSameAs(PriceAndTax.NONE);
		verify(pricingBL, never()).calculatePrice(any());
	}

	@Test
	public void withoutBillLocation_taxRemainsUnchanged()
	{
		final ProductId bonusProductId = createProduct();
		final I_C_Invoice_Candidate ic = createRefundCandidate(bonusProductId, null, BILL_BPARTNER_ID.getRepoId(), DATE_ORDERED, null);
		ic.setBill_Location_ID(0);
		saveRecord(ic);
		when(pricingBL.createInitialContext(any(), any(), any(), any(), any())).thenReturn(pricingContext);

		assertThat(handler.calculatePriceAndTax(ic)).isSameAs(PriceAndTax.NONE);
	}

	/**
	 * A refund candidate gets its date ordered from its invoice schedule when it is created; if it has none, the date to invoice is used, and without both the tax stays as it is.
	 */
	@Test
	public void dateOrderedFallsBackToDateToInvoice_thenToUnchangedTax()
	{
		final ProductId bonusProductId = createProduct();
		when(pricingBL.createInitialContext(any(), any(), any(), any(), any())).thenReturn(pricingContext);

		final I_C_Invoice_Candidate icWithDateToInvoice = createRefundCandidate(bonusProductId, null, BILL_BPARTNER_ID.getRepoId(), null, DATE_ORDERED.plusDays(1));
		assertThat(handler.calculatePriceAndTax(icWithDateToInvoice).getTaxId()).isEqualTo(TAX_ID);
		verify(pricingContext).setPriceDate(DATE_ORDERED.plusDays(1));

		final I_C_Invoice_Candidate icWithoutDates = createRefundCandidate(bonusProductId, null, BILL_BPARTNER_ID.getRepoId(), null, null);
		assertThat(handler.calculatePriceAndTax(icWithoutDates)).isSameAs(PriceAndTax.NONE);
	}

	/**
	 * A per-unit refund amount in another currency than the refunded sales can't be computed: the refund candidate gets an error that names both currencies, instead of a silent 0.
	 */
	@Test
	public void amountPerUnitInOtherCurrency_fails()
	{
		final ProductId bonusProductId = createProduct();
		final I_C_Invoice_Candidate ic = createRefundCandidate(bonusProductId, null, BILL_BPARTNER_ID.getRepoId(), DATE_ORDERED, null);
		ic.setC_Currency_ID(eur.getRepoId());
		saveRecord(ic);
		changeToAmountPerUnit(ic, chf);
		when(pricingBL.createInitialContext(any(), any(), any(), any(), any())).thenReturn(pricingContext);

		assertThatThrownBy(() -> handler.calculatePriceAndTax(ic))
				.isInstanceOfSatisfying(AdempiereException.class, e -> {
					assertThat(e.getErrorCode()).isEqualTo(FlatrateTermRefund_Handler.MSG_REFUND_AMOUNT_CURRENCY_MISMATCH.toAD_Message());
					assertThat(e.isUserValidationError()).isTrue();
				})
				.hasMessageContaining("refundConditions")
				.hasMessageContaining("CHF")
				.hasMessageContaining("EUR");
		verify(pricingBL, never()).calculatePrice(any());
	}

	@Test
	public void amountPerUnitInSalesCurrency_taxFollowsTheBonusProduct()
	{
		final ProductId bonusProductId = createProduct();
		final I_C_Invoice_Candidate ic = createRefundCandidate(bonusProductId, null, BILL_BPARTNER_ID.getRepoId(), DATE_ORDERED, null);
		ic.setC_Currency_ID(eur.getRepoId());
		saveRecord(ic);
		changeToAmountPerUnit(ic, eur);
		when(pricingBL.createInitialContext(any(), any(), any(), any(), any())).thenReturn(pricingContext);

		assertThat(handler.calculatePriceAndTax(ic).getTaxId()).isEqualTo(TAX_ID);
	}

	/**
	 * The currency of a percentage config plays no role: the refund is a percentage of the sales, in their currency.
	 */
	@Test
	public void percentageWithOtherCurrency_taxFollowsTheBonusProduct()
	{
		final ProductId bonusProductId = createProduct();
		final I_C_Invoice_Candidate ic = createRefundCandidate(bonusProductId, null, BILL_BPARTNER_ID.getRepoId(), DATE_ORDERED, null);
		ic.setC_Currency_ID(eur.getRepoId());
		saveRecord(ic);
		final I_C_Flatrate_RefundConfig config = retrieveConfig(ic);
		config.setC_Currency_ID(chf.getRepoId());
		saveRecord(config);
		when(pricingBL.createInitialContext(any(), any(), any(), any(), any())).thenReturn(pricingContext);

		assertThat(handler.calculatePriceAndTax(ic).getTaxId()).isEqualTo(TAX_ID);
	}

	private static void changeToAmountPerUnit(final I_C_Invoice_Candidate ic, final CurrencyId currencyId)
	{
		final I_C_Flatrate_RefundConfig config = retrieveConfig(ic);
		config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Amount);
		config.setRefundPercent(null);
		config.setRefundAmt(new BigDecimal("0.50"));
		config.setC_Currency_ID(currencyId.getRepoId());
		saveRecord(config);
	}

	private static I_C_Flatrate_RefundConfig retrieveConfig(final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = load(ic.getRecord_ID(), I_C_Flatrate_Term.class);
		return POJOLookupMap.get().getFirstOnly(I_C_Flatrate_RefundConfig.class, config -> config.getC_Flatrate_Conditions_ID() == term.getC_Flatrate_Conditions_ID());
	}

	private ProductId createProduct()
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private I_C_Invoice_Candidate createRefundCandidate(
			final ProductId bonusProductId,
			final ProductId configProductId,
			final int billPartnerId,
			final LocalDate dateOrdered,
			final LocalDate dateToInvoice)
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		conditions.setName("refundConditions");
		saveRecord(conditions);

		final I_C_Flatrate_RefundConfig config = newInstance(I_C_Flatrate_RefundConfig.class);
		config.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		config.setC_InvoiceSchedule_ID(invoiceSchedule.getC_InvoiceSchedule_ID());
		config.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		config.setRefundPercent(BigDecimal.TEN);
		config.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		config.setMinQty(BigDecimal.ZERO);
		config.setBonus_Product_ID(ProductId.toRepoId(bonusProductId));
		config.setM_Product_ID(ProductId.toRepoId(configProductId));
		saveRecord(config);

		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		term.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		term.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		term.setM_Product_ID(ProductId.toRepoId(configProductId));
		term.setBill_BPartner_ID(billPartnerId > 0 ? billPartnerId : 29);
		term.setStartDate(TimeUtil.asTimestamp(LocalDate.of(2026, 7, 1)));
		term.setEndDate(TimeUtil.asTimestamp(LocalDate.of(2026, 12, 31)));
		saveRecord(term);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setAD_Org_ID(ORG_ID.getRepoId());
		ic.setIsSOTrx(true);
		ic.setBill_BPartner_ID(billPartnerId);
		ic.setBill_Location_ID(BILL_LOCATION_ID);
		ic.setRecord_ID(term.getC_Flatrate_Term_ID());
		ic.setDateOrdered(dateOrdered != null ? TimeUtil.asTimestamp(dateOrdered) : null);
		ic.setDateToInvoice(dateToInvoice != null ? TimeUtil.asTimestamp(dateToInvoice) : null);
		saveRecord(ic);
		return ic;
	}
}
