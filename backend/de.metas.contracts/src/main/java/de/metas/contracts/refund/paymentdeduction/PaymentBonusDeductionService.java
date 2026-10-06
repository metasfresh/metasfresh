package de.metas.contracts.refund.paymentdeduction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfigs;
import de.metas.contracts.refund.RefundContract;
import de.metas.contracts.refund.RefundContractQuery;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.contracts.refund.packaging.RefundPackagingFilter;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.paymentbonus.PaymentBonusCreditMemoService;
import de.metas.invoice.paymentbonus.PaymentBonusDeduction;
import de.metas.invoice.paymentbonus.PaymentBonusDeductionLine;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLinePackingInstructions;
import de.metas.order.OrderShipmentBPartners;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Computes the bonus that a customer deducts when paying a sales invoice ("bei Zahlung").
 * <p>
 * The bonus comes from the completed refund terms whose configs are deducted at payment and that are valid at the invoice date.
 * A term applies to an invoice line if its partner is the line's bonus recipient (the invoice partner, or the partner that the line's order was shipped to),
 * if the line's product matches the term's product and base category (including sub-categories), and if the line passes the term's packaging filter.
 * The bonus is the term's percentage of the net value of the matching lines, booked on the term's bonus product; the VAT of the bonus product comes on top.
 * It is deducted once per invoice: there is none if the invoice already has a payment bonus credit memo.
 */
@Service
public class PaymentBonusDeductionService
{
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final RefundContractRepository refundContractRepository;
	private final RefundPackagingFilter refundPackagingFilter;
	private final PaymentBonusTaxProvider taxProvider;
	private final PaymentBonusCreditMemoService creditMemoService;

	public PaymentBonusDeductionService(
			@NonNull final RefundContractRepository refundContractRepository,
			@NonNull final RefundPackagingFilter refundPackagingFilter,
			@NonNull final PaymentBonusTaxProvider taxProvider,
			@NonNull final PaymentBonusCreditMemoService creditMemoService)
	{
		this.refundContractRepository = refundContractRepository;
		this.refundPackagingFilter = refundPackagingFilter;
		this.taxProvider = taxProvider;
		this.creditMemoService = creditMemoService;
	}

	/**
	 * @return the bonus that the customer may deduct when paying the given invoice; empty if there is none, e.g. because it is no sales invoice, or none of its lines is in the base of a term that is deducted at payment
	 */
	public Optional<PaymentBonusDeduction> computeForInvoice(@NonNull final InvoiceId invoiceId)
	{
		return newCalculator(ImmutableSet.of(invoiceId)).computeForInvoice(invoiceId);
	}

	/**
	 * Loads what is needed to compute the bonuses of the given invoices at once, in particular the refund terms of all their partners,
	 * e.g. for the invoices of the payment allocation view.
	 */
	public Calculator newCalculator(@NonNull final Collection<InvoiceId> invoiceIds)
	{
		final ImmutableList<I_C_Invoice> salesInvoices = invoiceIds.isEmpty()
				? ImmutableList.of()
				: invoiceBL.getByIds(invoiceIds).stream()
				.filter(invoice -> invoice.isSOTrx() && !invoiceBL.isCreditMemo(invoice)) // the customer deducts a bonus when paying a sales invoice
				.filter(invoice -> refundContractRepository.hasAnyRefundContract(TimeUtil.asLocalDate(invoice.getDateInvoiced()))) // the usual case, cached: no refund contracts at all
				.collect(ImmutableList.toImmutableList());
		if (salesInvoices.isEmpty())
		{
			return new Calculator(ImmutableMap.of(), ImmutableListMultimap.of(), ImmutableList.of());
		}

		// once per invoice; e.g. the second allocation of a partially paid invoice deducts nothing
		final ImmutableSet<InvoiceId> invoiceIdsWithCreditMemo = creditMemoService.retainIfCreditMemoWasAlreadyGenerated(
				salesInvoices.stream().map(PaymentBonusDeductionService::extractInvoiceId).collect(ImmutableSet.toImmutableSet()));
		final ImmutableMap<InvoiceId, I_C_Invoice> invoicesById = salesInvoices.stream()
				.filter(invoice -> !invoiceIdsWithCreditMemo.contains(extractInvoiceId(invoice)))
				.collect(ImmutableMap.toImmutableMap(PaymentBonusDeductionService::extractInvoiceId, invoice -> invoice));

		final ImmutableListMultimap<InvoiceId, InvoiceLineInfo> linesByInvoiceId = retrieveLines(invoicesById.values());

		final ImmutableSet<BPartnerId> partnerIds = ImmutableSet.<BPartnerId>builder()
				.addAll(invoicesById.values().stream().map(invoice -> BPartnerId.ofRepoId(invoice.getC_BPartner_ID())).iterator())
				.addAll(linesByInvoiceId.values().stream().map(InvoiceLineInfo::getShipmentBPartnerId).filter(Objects::nonNull).iterator())
				.build();
		final ImmutableList<RefundContract> contracts = invoicesById.isEmpty()
				? ImmutableList.of()
				: retrieveDeductedAtPaymentContracts(partnerIds, invoicesById.values());

		return new Calculator(invoicesById, linesByInvoiceId, contracts);
	}

	private static InvoiceId extractInvoiceId(@NonNull final I_C_Invoice invoice)
	{
		return InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
	}

	private ImmutableListMultimap<InvoiceId, InvoiceLineInfo> retrieveLines(@NonNull final Collection<I_C_Invoice> invoices)
	{
		final ImmutableListMultimap.Builder<InvoiceId, I_C_InvoiceLine> lineRecordsByInvoiceId = ImmutableListMultimap.builder();
		for (final I_C_Invoice invoice : invoices)
		{
			final InvoiceId invoiceId = extractInvoiceId(invoice);
			invoiceDAO.retrieveLines(invoiceId).stream()
					.filter(line -> line.getM_Product_ID() > 0)
					.forEach(line -> lineRecordsByInvoiceId.put(invoiceId, line));
		}
		final ImmutableListMultimap<InvoiceId, I_C_InvoiceLine> lineRecords = lineRecordsByInvoiceId.build();

		final ImmutableMap<ProductId, ImmutableSet<ProductCategoryId>> categoryIdAndAncestorsByProductId = productDAO.getProductCategoryIdAndAncestorsByProductIds(
				lineRecords.values().stream().map(line -> ProductId.ofRepoId(line.getM_Product_ID())).collect(ImmutableSet.toImmutableSet()));

		final ImmutableListMultimap.Builder<InvoiceId, InvoiceLineInfo> result = ImmutableListMultimap.builder();
		for (final I_C_Invoice invoice : invoices)
		{
			final InvoiceId invoiceId = extractInvoiceId(invoice);
			final CurrencyPrecision precision = currencyBL.getStdPrecision(CurrencyId.ofRepoId(invoice.getC_Currency_ID()));
			lineRecords.get(invoiceId).forEach(line -> result.put(invoiceId, toInvoiceLineInfo(invoice, line, categoryIdAndAncestorsByProductId, precision)));
		}
		return result.build();
	}

	/**
	 * @return the completed refund contracts of the given partners that are deducted at payment and valid at any of the invoices' dates; which of them applies to which line is up to {@link RefundContractRepository#isMatching}
	 */
	private ImmutableList<RefundContract> retrieveDeductedAtPaymentContracts(
			@NonNull final ImmutableSet<BPartnerId> partnerIds,
			@NonNull final Collection<I_C_Invoice> invoices)
	{
		final Timestamp minDateInvoiced = invoices.stream().map(I_C_Invoice::getDateInvoiced).min(Comparator.naturalOrder()).get();
		final Timestamp maxDateInvoiced = invoices.stream().map(I_C_Invoice::getDateInvoiced).max(Comparator.naturalOrder()).get();

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, X_C_Flatrate_Term.TYPE_CONDITIONS_Refund)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, X_C_Flatrate_Term.DOCSTATUS_Completed)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, maxDateInvoiced)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, minDateInvoiced)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, partnerIds)
				// only the conditions that are deducted at payment; the other refund contracts are not loaded at all
				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID,
						I_C_Flatrate_RefundConfig.COLUMNNAME_C_Flatrate_Conditions_ID,
						queryBL.createQueryBuilder(I_C_Flatrate_RefundConfig.class)
								.addOnlyActiveRecordsFilter()
								.addEqualsFilter(I_C_Flatrate_RefundConfig.COLUMNNAME_IsDeductedAtPayment, true)
								.create())
				.orderBy(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
				.create()
				.stream()
				.map(refundContractRepository::ofRecord)
				.filter(RefundContract::isDeductedAtPayment)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Computes the bonuses of the invoices that it was loaded for; see {@link #newCalculator(Collection)}.
	 */
	public final class Calculator
	{
		private final ImmutableMap<InvoiceId, I_C_Invoice> invoicesById;
		private final ImmutableListMultimap<InvoiceId, InvoiceLineInfo> linesByInvoiceId;
		private final ImmutableList<RefundContract> contracts;

		private Calculator(
				@NonNull final ImmutableMap<InvoiceId, I_C_Invoice> invoicesById,
				@NonNull final ImmutableListMultimap<InvoiceId, InvoiceLineInfo> linesByInvoiceId,
				@NonNull final ImmutableList<RefundContract> contracts)
		{
			this.invoicesById = invoicesById;
			this.linesByInvoiceId = linesByInvoiceId;
			this.contracts = contracts;
		}

		/**
		 * @return the bonus that the customer may deduct when paying the given invoice; empty if there is none, also if the invoice was not given to {@link #newCalculator(Collection)}
		 */
		public Optional<PaymentBonusDeduction> computeForInvoice(@NonNull final InvoiceId invoiceId)
		{
			final I_C_Invoice invoice = invoicesById.get(invoiceId);
			final ImmutableList<InvoiceLineInfo> lines = linesByInvoiceId.get(invoiceId);
			if (invoice == null || lines.isEmpty())
			{
				return Optional.empty();
			}

			final BPartnerId customerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
			final CurrencyId currencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());
			final CurrencyPrecision precision = currencyBL.getStdPrecision(currencyId);
			final LocalDate dateInvoiced = TimeUtil.asLocalDate(invoice.getDateInvoiced());

			final Map<ProductId, Money> netAmtsByBonusProductId = new LinkedHashMap<>();
			for (final RefundContract contract : contracts)
			{
				// the bonus at payment is one flat percentage: the condition's only line (validated when it is saved). The config that the contract adds for quantity 0 is not one of the condition's.
				final ImmutableList<RefundConfig> conditionConfigs = contract.getRefundConfigs().stream()
						.filter(refundConfig -> refundConfig.getId() != null)
						.collect(ImmutableList.toImmutableList());
				RefundConfigs.assertDeductedAtPaymentIsSingleLine(conditionConfigs);
				final RefundConfig config = conditionConfigs.get(0);
				RefundConfigs.assertDeductedAtPaymentIsComputable(config);
				final ProductId bonusProductId = Check.assumeNotNull(config.getBonusProductId(), "bonus product of {}", config);

				final Money baseNetAmt = lines.stream()
						.filter(line -> RefundContractRepository.isMatching(contract, toRefundContractQuery(customerId, line, dateInvoiced), line::getProductCategoryIdAndAncestors))
						.filter(line -> config.getProductId() == null || config.getProductId().equals(line.getProductId()))
						.filter(line -> refundPackagingFilter.isIncluded(contract.getConditionsId(), line.getHuPIItemProductId(), customerId))
						.map(InvoiceLineInfo::getNetAmt)
						.reduce(Money.zero(currencyId), Money::add);

				final Money bonusNetAmt = baseNetAmt.multiply(config.getPercent(), precision);
				if (bonusNetAmt.signum() <= 0)
				{
					continue;
				}
				netAmtsByBonusProductId.merge(bonusProductId, bonusNetAmt, Money::add);
			}

			if (netAmtsByBonusProductId.isEmpty())
			{
				return Optional.empty();
			}

			final ImmutableList<PaymentBonusDeductionLine> deductionLines = netAmtsByBonusProductId.entrySet().stream()
					.map(entry -> PaymentBonusDeductionLine.builder()
							.bonusProductId(entry.getKey())
							.tax(taxProvider.getTax(invoice, entry.getKey()))
							.netAmt(entry.getValue())
							.build())
					.collect(ImmutableList.toImmutableList());

			return Optional.of(PaymentBonusDeduction.builder()
					.orgId(OrgId.ofRepoId(invoice.getAD_Org_ID()))
					.invoiceId(invoiceId)
					.customerId(customerId)
					.currencyId(currencyId)
					.precision(precision)
					.lines(deductionLines)
					.build());
		}
	}

	private static RefundContractQuery toRefundContractQuery(@NonNull final BPartnerId customerId, @NonNull final InvoiceLineInfo line, @NonNull final LocalDate dateInvoiced)
	{
		return new RefundContractQuery(customerId, line.getShipmentBPartnerId(), line.getProductId(), dateInvoiced);
	}

	private InvoiceLineInfo toInvoiceLineInfo(
			@NonNull final I_C_Invoice invoice,
			@NonNull final I_C_InvoiceLine line,
			@NonNull final ImmutableMap<ProductId, ImmutableSet<ProductCategoryId>> categoryIdAndAncestorsByProductId,
			@NonNull final CurrencyPrecision precision)
	{
		final ProductId productId = ProductId.ofRepoId(line.getM_Product_ID());

		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(line.getC_OrderLine_ID());
		final I_C_OrderLine orderLine = orderLineId != null ? orderDAO.getOrderLineById(orderLineId) : null;
		final OrderId orderId = orderLine != null
				? OrderId.ofRepoId(orderLine.getC_Order_ID())
				: OrderId.ofRepoIdOrNull(invoice.getC_Order_ID());

		return InvoiceLineInfo.builder()
				.productId(productId)
				.productCategoryIdAndAncestors(categoryIdAndAncestorsByProductId.getOrDefault(productId, ImmutableSet.of()))
				.shipmentBPartnerId(OrderShipmentBPartners.extractShipmentBPartnerId(orderId))
				.huPIItemProductId(orderLine != null ? OrderLinePackingInstructions.extractHUPIItemProductId(orderLine) : null)
				.netAmt(Money.of(extractNetAmt(invoice, line, precision), CurrencyId.ofRepoId(invoice.getC_Currency_ID())))
				.build();
	}

	private BigDecimal extractNetAmt(@NonNull final I_C_Invoice invoice, @NonNull final I_C_InvoiceLine line, @NonNull final CurrencyPrecision precision)
	{
		if (!invoice.isTaxIncluded())
		{
			return line.getLineNetAmt();
		}
		final Tax tax = taxDAO.getTaxById(line.getC_Tax_ID());
		return tax.calculateBaseAmt(line.getLineNetAmt(), true, precision.toInt());
	}

	@Value
	@lombok.Builder
	private static class InvoiceLineInfo
	{
		@NonNull ProductId productId;
		@NonNull ImmutableSet<ProductCategoryId> productCategoryIdAndAncestors;
		@Nullable BPartnerId shipmentBPartnerId;
		@Nullable HUPIItemProductId huPIItemProductId;
		@NonNull Money netAmt;
	}
}
