package de.metas.contracts.refund.paymentdeduction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.refund.BonusRecipient;
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
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;
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
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
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
				.filter(invoice -> refundContractRepository.hasAnyDeductedAtPaymentContract(TimeUtil.asLocalDate(invoice.getDateInvoiced()))) // the usual case, cached: none at all
				.collect(ImmutableList.toImmutableList());
		if (salesInvoices.isEmpty())
		{
			return new Calculator(ImmutableMap.of(), ImmutableListMultimap.of(), ImmutableList.of());
		}

		final ImmutableList<RefundContract> contracts = retrieveDeductedAtPaymentContracts(salesInvoices);

		// only the invoices that a contract may apply to; the lines of the others are not loaded
		final ImmutableList<I_C_Invoice> invoicesWithPossibleBonus = salesInvoices.stream()
				.filter(invoice -> contracts.stream().anyMatch(contract -> isPossiblyApplicable(contract, invoice)))
				.collect(ImmutableList.toImmutableList());
		if (invoicesWithPossibleBonus.isEmpty())
		{
			return new Calculator(ImmutableMap.of(), ImmutableListMultimap.of(), ImmutableList.of());
		}

		// once per invoice; e.g. the second allocation of a partially paid invoice deducts nothing
		final ImmutableSet<InvoiceId> invoiceIdsWithCreditMemo = creditMemoService.retainIfCreditMemoWasAlreadyGenerated(
				invoicesWithPossibleBonus.stream().map(PaymentBonusDeductionService::extractInvoiceId).collect(ImmutableSet.toImmutableSet()));
		final ImmutableMap<InvoiceId, I_C_Invoice> invoicesById = invoicesWithPossibleBonus.stream()
				.filter(invoice -> !invoiceIdsWithCreditMemo.contains(extractInvoiceId(invoice)))
				.collect(ImmutableMap.toImmutableMap(PaymentBonusDeductionService::extractInvoiceId, invoice -> invoice));

		return new Calculator(invoicesById, retrieveLines(invoicesById.values()), contracts);
	}

	/**
	 * @return {@code true} if the contract is valid at the invoice date and its bonus may go to the invoice partner, or to a shipment partner of the invoice's lines (only known from the lines)
	 */
	private static boolean isPossiblyApplicable(@NonNull final RefundContract contract, @NonNull final I_C_Invoice invoice)
	{
		final LocalDate dateInvoiced = TimeUtil.asLocalDate(invoice.getDateInvoiced());
		if (dateInvoiced.isBefore(contract.getStartDate()) || dateInvoiced.isAfter(contract.getEndDate()))
		{
			return false;
		}
		return BonusRecipient.SHIPMENT_PARTNER.equals(contract.extractBonusRecipient())
				|| contract.getBPartnerId().getRepoId() == invoice.getC_BPartner_ID();
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
		final ImmutableSet<Integer> contractCompensationInvoiceLineIds = retrieveContractCompensationInvoiceLineIds(lineRecordsByInvoiceId.build().values());
		final ImmutableListMultimap<InvoiceId, I_C_InvoiceLine> lineRecords = ImmutableListMultimap.copyOf(Multimaps.filterValues(
				lineRecordsByInvoiceId.build(),
				line -> !contractCompensationInvoiceLineIds.contains(line.getC_InvoiceLine_ID())));

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
	 * @return the IDs of the given invoice lines that are the discount line of a compensation group that a contract created on the order (the on-invoice bonus).
	 *         They are not part of any base: the bonus at payment is computed on the goods value before that discount.
	 *         A line is recognised through the invoice candidates it was created from ({@code C_Invoice_Line_Alloc}), because invoicing may
	 *         aggregate the discount lines of several orders into one invoice line without order line; it is left out if all of them are such discount lines.
	 *         A mixed line (a contract discount candidate aggregated with any other candidate) stays whole in the base: this errs on the safe side,
	 *         the base can only stay too high, never too low.
	 *         A discount line of a group the user put together (no contract) stays in the base, as does a line that no invoice candidate created.
	 */
	private ImmutableSet<Integer> retrieveContractCompensationInvoiceLineIds(@NonNull final Collection<I_C_InvoiceLine> lines)
	{
		if (lines.isEmpty())
		{
			return ImmutableSet.of();
		}
		final ImmutableSet<Integer> invoiceLineIds = lines.stream().map(I_C_InvoiceLine::getC_InvoiceLine_ID).collect(ImmutableSet.toImmutableSet());
		final ImmutableListMultimap<Integer, Integer> candidateIdsByInvoiceLineId = queryBL.createQueryBuilder(I_C_Invoice_Line_Alloc.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Invoice_Line_Alloc.COLUMNNAME_C_InvoiceLine_ID, invoiceLineIds)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(I_C_Invoice_Line_Alloc::getC_InvoiceLine_ID, I_C_Invoice_Line_Alloc::getC_Invoice_Candidate_ID));
		if (candidateIdsByInvoiceLineId.isEmpty())
		{
			return ImmutableSet.of();
		}

		final ImmutableMap<Integer, GroupId> groupIdsOfCompensationCandidates = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addInArrayFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID, ImmutableSet.copyOf(candidateIdsByInvoiceLineId.values()))
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_IsGroupCompensationLine, true)
				.create()
				.stream()
				.filter(candidate -> candidate.getC_Order_ID() > 0 && candidate.getC_Order_CompensationGroup_ID() > 0)
				.collect(ImmutableMap.toImmutableMap(
						I_C_Invoice_Candidate::getC_Invoice_Candidate_ID,
						candidate -> OrderGroupRepository.createGroupId(OrderId.ofRepoId(candidate.getC_Order_ID()), candidate.getC_Order_CompensationGroup_ID())));
		final ImmutableSet<GroupId> contractCreatedGroupIds = OrderGroupRepository.filterContractCreatedGroupIds(ImmutableSet.copyOf(groupIdsOfCompensationCandidates.values()));

		return candidateIdsByInvoiceLineId.asMap().entrySet().stream()
				.filter(entry -> entry.getValue().stream().allMatch(candidateId -> contractCreatedGroupIds.contains(groupIdsOfCompensationCandidates.get(candidateId))))
				.map(Map.Entry::getKey)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * @return the completed refund contracts that are deducted at payment, valid at any of the invoices' dates, and whose bonus may go to one of the invoices' partners:
	 *         the contracts of the invoice partners, and all the contracts whose bonus goes to the shipment partner (who is only known from the invoice lines).
	 *         Which of them applies to which line is up to {@link RefundContractRepository#isMatching}.
	 */
	private ImmutableList<RefundContract> retrieveDeductedAtPaymentContracts(@NonNull final Collection<I_C_Invoice> invoices)
	{
		final Timestamp minDateInvoiced = invoices.stream().map(I_C_Invoice::getDateInvoiced).min(Comparator.naturalOrder()).get();
		final Timestamp maxDateInvoiced = invoices.stream().map(I_C_Invoice::getDateInvoiced).max(Comparator.naturalOrder()).get();
		final ImmutableSet<Integer> customerIds = invoices.stream().map(I_C_Invoice::getC_BPartner_ID).collect(ImmutableSet.toImmutableSet());

		final ICompositeQueryFilter<I_C_Flatrate_Term> customerOrShipmentPartnerRecipient = queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
				.setJoinOr()
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, customerIds)
				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID,
						I_C_Flatrate_RefundConfig.COLUMNNAME_C_Flatrate_Conditions_ID,
						queryBL.createQueryBuilder(I_C_Flatrate_RefundConfig.class)
								.addOnlyActiveRecordsFilter()
								.addEqualsFilter(I_C_Flatrate_RefundConfig.COLUMNNAME_BonusRecipient, X_C_Flatrate_RefundConfig.BONUSRECIPIENT_ShipmentPartner)
								.create());

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, X_C_Flatrate_Term.TYPE_CONDITIONS_Refund)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, X_C_Flatrate_Term.DOCSTATUS_Completed)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, maxDateInvoiced)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, minDateInvoiced)
				.filter(customerOrShipmentPartnerRecipient)
				// only the conditions that are deducted at payment; the other refund contracts are not loaded at all
				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID,
						I_C_Flatrate_RefundConfig.COLUMNNAME_C_Flatrate_Conditions_ID,
						RefundContractRepository.queryDeductedAtPaymentConfigs())
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
