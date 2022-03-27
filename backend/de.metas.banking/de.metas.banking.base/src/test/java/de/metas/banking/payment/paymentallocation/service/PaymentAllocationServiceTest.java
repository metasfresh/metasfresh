/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.banking.payment.paymentallocation.service;

import de.metas.banking.payment.paymentallocation.PaymentAllocationCriteria;
import de.metas.banking.payment.paymentallocation.PaymentAllocationPayableItem;
import de.metas.banking.remittanceadvice.process.C_RemittanceAdvice_CreateAndAllocatePayment;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.Amount;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyConfigRepository;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.tax.api.TaxCategoryId;
import de.metas.user.UserRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_InvoiceProcessingServiceCompany;
import org.compiere.model.I_InvoiceProcessingServiceCompany_BPartnerAssignment;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.metas.invoice.InvoiceDocBaseType.CustomerInvoice;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class PaymentAllocationServiceTest
{
	private static final boolean INVOICE_AMT_IsSOTrxAdjusted = false;
	private static final boolean INVOICE_AMT_IsCreditMemoAdjusted = false;

	private PaymentAllocationService paymentAllocationService;
	private Map<InvoiceDocBaseType, I_C_DocType> invoiceDocTypes;
	private CurrencyId euroCurrencyId;
	private int nextInvoiceId = 10000;
	private int nextPaymentId = 1;
	private OrgId adOrgId;
	private final ClientId clientId = ClientId.ofRepoId(1000000); // just a dummy value
	private BPartnerId bpartnerId;
	private DocTypeId serviceInvoiceDocTypeId;
	private ProductId serviceFeeProductId;
	private BPartnerId feeCompanyId1;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(MoneyService.class, new MoneyService(new CurrencyRepository()));
		SpringContextHolder.registerJUnitBean(IBPartnerBL.class, new BPartnerBL(new UserRepository()));

		final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

		final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService = new InvoiceProcessingServiceCompanyService(new InvoiceProcessingServiceCompanyConfigRepository(), moneyService);

		paymentAllocationService = new PaymentAllocationService(moneyService, invoiceProcessingServiceCompanyService);

		invoiceDocTypes = new HashMap<>();
		adOrgId = AdempiereTestHelper.createOrgWithTimeZone();
		euroCurrencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		bpartnerId = createBPartnerId();

		serviceInvoiceDocTypeId = DocTypeId.ofRepoId(222);
		serviceFeeProductId = ProductId.ofRepoId(333);

		final CountryId countryId = BusinessTestHelper.createCountry("DE");

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		serviceInvoiceDocTypeId = docTypeDAO.createDocType(IDocTypeDAO.DocTypeCreateRequest.builder()
																   .ctx(Env.getCtx())
																   .name("invoice processing fee vendor invoice")
																   .docBaseType(InvoiceDocBaseType.VendorInvoice.getDocBaseType())
																   .build());

		final I_C_UOM uomEach = BusinessTestHelper.createUomEach();
		serviceFeeProductId = createServiceProduct("Service Fee", uomEach);

		final TaxCategoryId taxCategoryId = createTaxCategory();
		final PricingSystemId servicePricingSystemId = createPricingSystem();
		final PriceListId servicePriceListId = createPriceList(servicePricingSystemId, countryId, CurrencyCode.EUR);
		final PriceListVersionId servicePriceListVersionId = createPriceListVersion(servicePriceListId);
		createProductPrice(servicePriceListVersionId, serviceFeeProductId, uomEach, taxCategoryId);

		bpartnerAndLocation()
				.countryId(countryId)
				.purchasePricingSystemId(servicePricingSystemId)
				.build();

		final ConfigBuilder configBuilder = processingServiceCompanyConfig()
				.customerId(bpartnerId)
				.validFrom(LocalDate.parse("2020-02-11").atStartOfDay(ZoneId.of("UTC+5")));

		configBuilder.feePercentageOfGrandTotal("1").serviceCompanyBPartnerId(feeCompanyId1).build();

	}

	private PricingSystemId createPricingSystem()
	{
		final I_M_PricingSystem pricingSystem = newInstance(I_M_PricingSystem.class);
		saveRecord(pricingSystem);
		return PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID());
	}

	@SuppressWarnings("SameParameterValue")
	private PriceListId createPriceList(
			@NonNull final PricingSystemId pricingSystemId,
			@NonNull final CountryId countryId,
			@NonNull final CurrencyCode currencyCode)
	{
		final Currency currency = Services.get(ICurrencyDAO.class).getByCurrencyCode(currencyCode);

		final I_M_PriceList priceList = newInstance(I_M_PriceList.class);
		priceList.setM_PricingSystem_ID(pricingSystemId.getRepoId());
		priceList.setC_Country_ID(countryId.getRepoId());
		priceList.setC_Currency_ID(currency.getId().getRepoId());
		priceList.setPricePrecision(2);
		saveRecord(priceList);
		return PriceListId.ofRepoId(priceList.getM_PriceList_ID());
	}

	private PriceListVersionId createPriceListVersion(final PriceListId priceListId)
	{
		final I_M_PriceList_Version priceListVersion = newInstance(I_M_PriceList_Version.class);
		priceListVersion.setM_PriceList_ID(priceListId.getRepoId());
		priceListVersion.setValidFrom(TimeUtil.asTimestamp(LocalDate.parse("1970-01-01")));
		saveRecord(priceListVersion);
		return PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID());
	}

	private void createProductPrice(
			@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final ProductId productId,
			@NonNull final I_C_UOM uom,
			@NonNull final TaxCategoryId taxCategoryId)
	{
		final I_M_ProductPrice productPrice = newInstance(I_M_ProductPrice.class);
		productPrice.setM_PriceList_Version_ID(priceListVersionId.getRepoId());
		productPrice.setM_Product_ID(productId.getRepoId());
		productPrice.setC_UOM_ID(uom.getC_UOM_ID());
		productPrice.setC_TaxCategory_ID(taxCategoryId.getRepoId());
		saveRecord(productPrice);
	}

	private TaxCategoryId createTaxCategory()
	{
		final I_C_TaxCategory taxCategory = newInstance(I_C_TaxCategory.class);
		saveRecord(taxCategory);
		return TaxCategoryId.ofRepoId(taxCategory.getC_TaxCategory_ID());
	}

	@SuppressWarnings({ "SameParameterValue", "ConstantConditions" })
	private ProductId createServiceProduct(
			@NonNull final String name,
			@Nullable final I_C_UOM uom)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		product.setProductType(ProductType.Service.getCode());
		product.setIsStocked(false);
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	@Builder(builderMethodName = "bpartnerAndLocation", builderClassName = "$BPartnerAndLocationBuilder")
	private BPartnerLocationId createBPartnerAndLocation(
			@NonNull final PricingSystemId purchasePricingSystemId,
			@NonNull final CountryId countryId)
	{

		final de.metas.interfaces.I_C_BPartner bpartner = newInstance(de.metas.interfaces.I_C_BPartner.class);
		bpartner.setPO_PricingSystem_ID(purchasePricingSystemId.getRepoId());
		bpartner.setPaymentRule(PaymentRule.OnCredit.getCode());
		saveRecord(bpartner);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
		feeCompanyId1 = bpartnerId;

		final I_C_Location location = newInstance(I_C_Location.class);
		location.setC_Country_ID(countryId.getRepoId());
		saveRecord(location);

		final I_C_BPartner_Location bpartnerLocation = newInstance(I_C_BPartner_Location.class);
		bpartnerLocation.setC_BPartner_ID(bpartnerId.getRepoId());
		bpartnerLocation.setIsBillToDefault(true);
		bpartnerLocation.setIsBillTo(true);
		bpartnerLocation.setC_Location_ID(location.getC_Location_ID());
		saveRecord(bpartnerLocation);

		return BPartnerLocationId.ofRepoId(bpartnerId, bpartnerLocation.getC_BPartner_Location_ID());
	}

	private PaymentAllocationCriteria createPaymentAllocationCriteriaWithNoPayable()
	{

		final I_C_Payment payment = payment().payAmt(new BigDecimal(100)).build();

		return getPaymentAllocationCriteria(payment, new ArrayList<>());

	}

	private PaymentAllocationCriteria createPaymentAllocationCriteriaWithOnePayable()
	{

		final I_C_Payment payment = payment().payAmt(new BigDecimal(100)).build();

		final I_C_Invoice invoice = invoice().type(CustomerInvoice).open("100").currency(euroCurrencyId).build();

		final PaymentAllocationPayableItem paymentAllocationPayableItem = payableItem().payAmt(new BigDecimal(100)).openAmt(new BigDecimal(100)).invoice(invoice).soTrx(SOTrx.SALES).build();

		return getPaymentAllocationCriteria(payment, Collections.singletonList(paymentAllocationPayableItem));

	}

	private PaymentAllocationCriteria createPaymentAllocationCriteriaWithOnePayableAndServiceFee()
	{

		final I_C_Payment payment = payment().payAmt(new BigDecimal(100)).build();

		final I_C_Invoice invoice = invoice().type(CustomerInvoice).open("100").currency(euroCurrencyId).build();

		final PaymentAllocationPayableItem paymentAllocationPayableItem = payableItem().payAmt(new BigDecimal(100)).openAmt(new BigDecimal(100)).serviceFeeAmt(new BigDecimal(10)).invoice(invoice).soTrx(SOTrx.SALES).build();

		return getPaymentAllocationCriteria(payment, Collections.singletonList(paymentAllocationPayableItem));

	}

	private PaymentAllocationCriteria createPaymentAllocationCriteriaWithMultiplePayableAndServiceFee()
	{

		final I_C_Payment payment = payment().payAmt(new BigDecimal(78)).build();

		final I_C_Invoice firstInvoice = invoice().type(CustomerInvoice).open("50").currency(euroCurrencyId).build();
		final I_C_Invoice secondInvoice = invoice().type(CustomerInvoice).open("50").currency(euroCurrencyId).build();

		final PaymentAllocationPayableItem firstPaymentAllocationPayableItem = payableItem().payAmt(new BigDecimal(40)).openAmt(new BigDecimal(40)).serviceFeeAmt(new BigDecimal(10)).invoice(firstInvoice).soTrx(SOTrx.SALES).build();
		final PaymentAllocationPayableItem secondPaymentAllocationPayableItem = payableItem().payAmt(new BigDecimal(38)).openAmt(new BigDecimal(48)).serviceFeeAmt(new BigDecimal(12)).invoice(secondInvoice).soTrx(SOTrx.SALES).build();

		return getPaymentAllocationCriteria(payment, Arrays.asList(firstPaymentAllocationPayableItem, secondPaymentAllocationPayableItem));

	}

	private PaymentAllocationCriteria createPaymentAllocationCriteriaWithMultiplePayable()
	{

		final I_C_Payment payment = payment().payAmt(new BigDecimal(100)).build();

		final I_C_Invoice firstInvoice = invoice().type(CustomerInvoice).open("50").currency(euroCurrencyId).build();
		final I_C_Invoice secondInvoice = invoice().type(CustomerInvoice).open("50").currency(euroCurrencyId).build();

		final PaymentAllocationPayableItem firstPaymentAllocationPayableItem = payableItem().payAmt(new BigDecimal(50)).openAmt(new BigDecimal(50)).invoice(firstInvoice).soTrx(SOTrx.SALES).build();
		final PaymentAllocationPayableItem secondPaymentAllocationPayableItem = payableItem().payAmt(new BigDecimal(50)).openAmt(new BigDecimal(50)).invoice(secondInvoice).soTrx(SOTrx.SALES).build();

		return getPaymentAllocationCriteria(payment, Arrays.asList(firstPaymentAllocationPayableItem, secondPaymentAllocationPayableItem));

	}

	private PaymentAllocationCriteria createPaymentAllocationCriteriaWithMultiplePayable_OverAllocated()
	{

		final I_C_Payment payment = payment().payAmt(new BigDecimal(150)).build();

		final I_C_Invoice firstInvoice = invoice().type(CustomerInvoice).open("25").currency(euroCurrencyId).build();
		final I_C_Invoice secondInvoice = invoice().type(CustomerInvoice).open("75").currency(euroCurrencyId).build();

		final PaymentAllocationPayableItem firstPaymentAllocationPayableItem = payableItem().payAmt(new BigDecimal(25)).openAmt(new BigDecimal(25))
				.invoice(firstInvoice).soTrx(SOTrx.SALES).build();
		final PaymentAllocationPayableItem secondPaymentAllocationPayableItem = payableItem().payAmt(new BigDecimal(75)).openAmt(new BigDecimal(75))
				.invoice(secondInvoice).soTrx(SOTrx.SALES).build();

		return getPaymentAllocationCriteria(payment, Arrays.asList(firstPaymentAllocationPayableItem, secondPaymentAllocationPayableItem));

	}

	private PaymentAllocationCriteria getPaymentAllocationCriteria(final I_C_Payment payment, final List<PaymentAllocationPayableItem> paymentAllocationPayableItems)
	{
		return PaymentAllocationCriteria.builder()
				.payment(payment)
				.dateTrx(Instant.now())
				.paymentAllocationPayableItems(paymentAllocationPayableItems)
				.build();
	}

	@Builder(builderMethodName = "processingServiceCompanyConfig", builderClassName = "ConfigBuilder")
	private void createConfig(
			@NonNull final String feePercentageOfGrandTotal,
			@NonNull final BPartnerId customerId,
			@NonNull final ZonedDateTime validFrom,
			@NonNull final BPartnerId serviceCompanyBPartnerId)
	{
		final I_InvoiceProcessingServiceCompany configRecord = newInstance(I_InvoiceProcessingServiceCompany.class);
		configRecord.setIsActive(true);
		configRecord.setServiceCompany_BPartner_ID(serviceCompanyBPartnerId.getRepoId());
		configRecord.setServiceInvoice_DocType_ID(serviceInvoiceDocTypeId.getRepoId());
		configRecord.setServiceFee_Product_ID(serviceFeeProductId.getRepoId());
		configRecord.setValidFrom(TimeUtil.asTimestamp(validFrom));
		saveRecord(configRecord);

		final I_InvoiceProcessingServiceCompany_BPartnerAssignment assignmentRecord = newInstance(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class);
		assignmentRecord.setIsActive(true);
		assignmentRecord.setInvoiceProcessingServiceCompany_ID(configRecord.getInvoiceProcessingServiceCompany_ID());
		assignmentRecord.setC_BPartner_ID(customerId.getRepoId());
		assignmentRecord.setFeePercentageOfGrandTotal(new BigDecimal(feePercentageOfGrandTotal));
		saveRecord(assignmentRecord);
	}

	@Builder(builderMethodName = "payableItem")
	private PaymentAllocationPayableItem getPaymentAllocationPayableItem(
			@NonNull final BigDecimal payAmt,
			@NonNull final BigDecimal openAmt,
			@Nullable final BigDecimal serviceFeeAmt,
			@Nullable final BigDecimal discountAmt,
			@NonNull final I_C_Invoice invoice,
			@NonNull final SOTrx soTrx)
	{
		final InvoiceAmtMultiplier amtMultiplier = C_RemittanceAdvice_CreateAndAllocatePayment.toInvoiceAmtMultiplier(soTrx, isCreditMemo(invoice));

		return PaymentAllocationPayableItem.builder()
				.amtMultiplier(amtMultiplier)
				.payAmt(Amount.of(payAmt, CurrencyCode.EUR))
				.openAmt(Amount.of(openAmt, CurrencyCode.EUR))
				.serviceFeeAmt(serviceFeeAmt == null ? null : Amount.of(serviceFeeAmt, CurrencyCode.EUR))
				.discountAmt(discountAmt == null ? Amount.of(0, CurrencyCode.EUR) : Amount.of(discountAmt, CurrencyCode.EUR))
				.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
				.invoiceBPartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID()))
				.orgId(adOrgId)
				.clientId(clientId)
				.paymentDate(Instant.now())
				.bPartnerId(bpartnerId)
				.documentNo(invoice.getDocumentNo())
				.soTrx(soTrx)
				.dateInvoiced(LocalDate.now())
				.build();
	}

	/**
	 * @param open open amount - we assume the open amount is a relative value (i.e. same as you see the invoice grand total in sales/purcahse invoice window)
	 */
	@Builder(builderMethodName = "invoice")
	private I_C_Invoice createInvoice(
			final InvoiceDocBaseType type,
			final String open,
			@Nullable final CurrencyId currency)
	{
		final Money openAmt = money(open, currency);
		final LocalDate acctDate = LocalDate.parse("2020-09-04");

		final int invoiceId = nextInvoiceId++;
		final I_C_DocType docType = getInvoiceDocType(type);
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_Invoice_ID(invoiceId);
		invoice.setDocumentNo("InvoiceDocNo" + invoiceId);
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setIsSOTrx(docType.isSOTrx());
		invoice.setDateInvoiced(TimeUtil.asTimestamp(acctDate));
		invoice.setC_BPartner_ID(bpartnerId.getRepoId());
		invoice.setC_Currency_ID(openAmt.getCurrencyId().getRepoId());
		invoice.setGrandTotal(openAmt.toBigDecimal());
		invoice.setProcessed(true);
		invoice.setDocStatus(IDocument.STATUS_Completed);
		invoice.setDateAcct(TimeUtil.asTimestamp(acctDate));
		InterfaceWrapperHelper.saveRecord(invoice);

		return invoice;
	}

	private Money money(final String amount, @Nullable CurrencyId currencyId)
	{
		final BigDecimal amountBD = Check.isNotBlank(amount) ? new BigDecimal(amount) : BigDecimal.ZERO;
		currencyId = currencyId == null ? euroCurrencyId : currencyId;
		return Money.of(amountBD, currencyId);
	}

	private I_C_DocType getInvoiceDocType(final InvoiceDocBaseType InvoiceDocBaseType)
	{
		return invoiceDocTypes.computeIfAbsent(InvoiceDocBaseType, this::createInvoiceDocType);
	}

	private I_C_DocType createInvoiceDocType(final InvoiceDocBaseType invoiceDocBaseType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setDocBaseType(invoiceDocBaseType.getCode());
		docType.setName(invoiceDocBaseType.toString());
		docType.setIsSOTrx(invoiceDocBaseType.isSales());
		InterfaceWrapperHelper.save(docType);
		return docType;
	}

	private boolean isCreditMemo(final I_C_Invoice invoice)
	{
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		return invoiceBL.isCreditMemo(invoice);
	}

	@Builder(builderMethodName = "payment", builderClassName = "$DefaultPaymentBuilder")
	private I_C_Payment getPayment(
			@NonNull final BigDecimal payAmt
	)
	{
		final I_C_Payment payment;
		{
			final int paymentId = nextPaymentId++;
			payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
			payment.setC_Payment_ID(paymentId);
			payment.setDocumentNo("PaymentDocNo" + paymentId);
			payment.setC_BPartner_ID(bpartnerId.getRepoId());
			payment.setC_Currency_ID(euroCurrencyId.getRepoId());
			payment.setDateTrx(Timestamp.valueOf(LocalDate.now().atStartOfDay()));
			payment.setAD_Org_ID(adOrgId.getRepoId());
			payment.setPayAmt(payAmt);
			payment.setIsReceipt(true);
			InterfaceWrapperHelper.save(payment);
		}
		return payment;

	}

	private BPartnerId createBPartnerId()
	{
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bpartnerRecord);
		return BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
	}

	@org.junit.jupiter.api.Test
	public void checkTestsAreUsingSameInvoiceAmtMultiplierAsRealLife()
	{
		final InvoiceAmtMultiplier multiplierInRealLife = C_RemittanceAdvice_CreateAndAllocatePayment.toInvoiceAmtMultiplier(SOTrx.SALES, false);

		//noinspection AssertThatBooleanCondition
		assertThat(multiplierInRealLife.isSOTrxAdjusted()).isEqualTo(INVOICE_AMT_IsSOTrxAdjusted);

		//noinspection AssertThatBooleanCondition
		assertThat(multiplierInRealLife.isCreditMemoAdjusted()).isEqualTo(INVOICE_AMT_IsCreditMemoAdjusted);
	}

	@Test
	public void paymentValid_EmptyPayableItems()
	{
		//shall throw exception because payable items array is empty
		assertThatThrownBy(() -> paymentAllocationService.allocatePayment(createPaymentAllocationCriteriaWithNoPayable()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("Invalid allocation");

	}

	@Test
	public void paymentValid_OnePayableItem_NoServiceFee_FullyAllocated()
	{
		final PaymentAllocationResult paymentAllocationResult = paymentAllocationService.allocatePayment(createPaymentAllocationCriteriaWithOnePayable());
		assertThat(paymentAllocationResult.getCandidates().size())
				.as("Allocation candidates found =" + paymentAllocationResult.getCandidates().size())
				.isEqualByComparingTo(1);

	}

	@Test
	public void paymentValid_OnePayableItem_WithServiceFee_FullyAllocated()
	{
		final PaymentAllocationResult paymentAllocationResult = paymentAllocationService.allocatePayment(createPaymentAllocationCriteriaWithOnePayableAndServiceFee());
		assertThat(paymentAllocationResult.getCandidates().size())
				.as("Allocation candidates found =" + paymentAllocationResult.getCandidates().size())
				.isEqualByComparingTo(2);

	}

	@Test
	public void paymentValid_MultiplePayableItems_WithServiceFee_FullyAllocated()
	{

		final PaymentAllocationResult paymentAllocationResult = paymentAllocationService.allocatePayment(createPaymentAllocationCriteriaWithMultiplePayableAndServiceFee());
		assertThat(paymentAllocationResult.getCandidates().size())
				.as("Allocation candidates found =" + paymentAllocationResult.getCandidates().size())
				.isEqualByComparingTo(4);

	}

	@Test
	public void paymentValid_MultiplePayableItems_NoServiceFee_FullyAllocated()
	{
		final PaymentAllocationResult paymentAllocationResult = paymentAllocationService.allocatePayment(createPaymentAllocationCriteriaWithMultiplePayable());
		assertThat(paymentAllocationResult.getCandidates().size())
				.as("Allocation candidates found =" + paymentAllocationResult.getCandidates().size())
				.isEqualByComparingTo(2);

	}

	@Test
	public void paymentValid_MultiplePayableItems_NoServiceFee_OverAllocated()
	{
		assertThatThrownBy(() -> paymentAllocationService.allocatePayment(createPaymentAllocationCriteriaWithMultiplePayable_OverAllocated()))
				.isInstanceOf(PaymentDocumentNotAllocatedException.class);
	}
}
