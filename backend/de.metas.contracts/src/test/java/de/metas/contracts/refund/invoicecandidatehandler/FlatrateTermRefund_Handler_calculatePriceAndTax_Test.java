package de.metas.contracts.refund.invoicecandidatehandler;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class FlatrateTermRefund_Handler_calculatePriceAndTax_Test
{
	private static final TaxCategoryId TAX_CATEGORY_ID = TaxCategoryId.ofRepoId(51);
	private static final TaxId TAX_ID = TaxId.ofRepoId(52);

	private final FlatrateTermRefund_Handler handler = new FlatrateTermRefund_Handler();
	private I_C_InvoiceSchedule invoiceSchedule;
	private IPricingBL pricingBL;
	private ITaxBL taxBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository())));

		pricingBL = Mockito.mock(IPricingBL.class, Mockito.RETURNS_DEEP_STUBS);
		Services.registerService(IPricingBL.class, pricingBL);
		taxBL = Mockito.mock(ITaxBL.class);
		Services.registerService(ITaxBL.class, taxBL);
		final IOrgDAO orgDAO = Mockito.mock(IOrgDAO.class);
		when(orgDAO.getTimeZone(any(OrgId.class))).thenReturn(ZoneId.of("UTC"));
		Services.registerService(IOrgDAO.class, orgDAO);

		invoiceSchedule = newInstance(I_C_InvoiceSchedule.class);
		invoiceSchedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		invoiceSchedule.setInvoiceDay(28);
		invoiceSchedule.setInvoiceDistance(1);
		saveRecord(invoiceSchedule);
	}

	/**
	 * The tax category comes from the pricing of the bonus product, the tax from that category.
	 */
	@Test
	public void taxFollowsTheBonusProduct()
	{
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
		final I_M_Product bonusProduct = newInstance(I_M_Product.class);
		bonusProduct.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(bonusProduct);
		final int bonusProductId = bonusProduct.getM_Product_ID();
		final I_C_Invoice_Candidate ic = createRefundCandidate(30, bonusProductId);

		final IEditablePricingContext pricingContext = Mockito.mock(IEditablePricingContext.class);
		when(pricingBL.createInitialContext(any(), eq(ProductId.ofRepoId(bonusProductId)), any(), any(), eq(SOTrx.SALES)).setReferencedObject(any()).setPriceDate(any())).thenReturn(pricingContext);
		final IPricingResult pricingResult = Mockito.mock(IPricingResult.class);
		when(pricingResult.isCalculated()).thenReturn(true);
		when(pricingResult.getTaxCategoryId()).thenReturn(TAX_CATEGORY_ID);
		when(pricingBL.calculatePrice(pricingContext)).thenReturn(pricingResult);
		when(taxBL.getTaxNotNull(any(), eq(TAX_CATEGORY_ID), eq(bonusProductId), any(), any(), any(), any(), eq(SOTrx.SALES))).thenReturn(TAX_ID);

		final PriceAndTax result = handler.calculatePriceAndTax(ic);

		assertThat(result.getTaxId()).isEqualTo(TAX_ID);
		assertThat(result.getTaxCategoryId()).isEqualTo(TAX_CATEGORY_ID);
	}

	/**
	 * Without a bonus product or a product, nothing is looked up and the tax stays as it is.
	 */
	@Test
	public void withoutProduct_taxRemainsUnchanged()
	{
		final I_C_Invoice_Candidate ic = createRefundCandidate(30, 0);

		final PriceAndTax result = handler.calculatePriceAndTax(ic);

		assertThat(result).isSameAs(PriceAndTax.NONE);
	}

	private I_C_Invoice_Candidate createRefundCandidate(final int billPartnerId, final int bonusProductId)
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditions);

		final I_C_Flatrate_RefundConfig config = newInstance(I_C_Flatrate_RefundConfig.class);
		config.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		config.setC_InvoiceSchedule_ID(invoiceSchedule.getC_InvoiceSchedule_ID());
		config.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		config.setRefundPercent(BigDecimal.TEN);
		config.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		config.setMinQty(BigDecimal.ZERO);
		config.setM_Product_Category_ID(60);
		config.setBonus_Product_ID(bonusProductId);
		saveRecord(config);

		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		term.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		term.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		term.setBill_BPartner_ID(billPartnerId);
		term.setStartDate(TimeUtil.asTimestamp(LocalDate.of(2026, 7, 1)));
		term.setEndDate(TimeUtil.asTimestamp(LocalDate.of(2026, 12, 31)));
		saveRecord(term);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setAD_Org_ID(1);
		ic.setIsSOTrx(true);
		ic.setBill_BPartner_ID(billPartnerId);
		ic.setBill_Location_ID(77);
		ic.setRecord_ID(term.getC_Flatrate_Term_ID());
		ic.setDateOrdered(TimeUtil.asTimestamp(LocalDate.of(2026, 7, 31)));
		InterfaceWrapperHelper.saveRecord(ic);
		return ic;
	}
}
