package de.metas.contracts.refund.paymentdeduction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.BonusRecipient;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfigs;
import de.metas.contracts.refund.RefundContract;
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
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
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
		final I_C_Invoice invoice = invoiceBL.getById(invoiceId);
		if (!invoice.isSOTrx() || invoiceBL.isCreditMemo(invoice))
		{
			return Optional.empty(); // the customer deducts a bonus when paying a sales invoice
		}
		if (!refundContractRepository.hasAnyRefundContract(TimeUtil.asLocalDate(invoice.getDateInvoiced())))
		{
			return Optional.empty(); // the usual case, cached: no refund contracts at all
		}
		if (creditMemoService.isCreditMemoAlreadyGenerated(invoiceId))
		{
			return Optional.empty(); // once per invoice; e.g. the second allocation of a partially paid invoice deducts nothing
		}

		final BPartnerId customerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final CurrencyId currencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());
		final CurrencyPrecision precision = currencyBL.getStdPrecision(currencyId);

		final ImmutableList<I_C_InvoiceLine> lineRecords = invoiceDAO.retrieveLines(invoiceId).stream()
				.filter(line -> line.getM_Product_ID() > 0)
				.collect(ImmutableList.toImmutableList());
		final ImmutableMap<ProductId, ImmutableSet<ProductCategoryId>> categoryIdAndAncestorsByProductId = productDAO.getProductCategoryIdAndAncestorsByProductIds(
				lineRecords.stream().map(line -> ProductId.ofRepoId(line.getM_Product_ID())).collect(ImmutableSet.toImmutableSet()));
		final ImmutableList<InvoiceLineInfo> lines = lineRecords.stream()
				.map(line -> toInvoiceLineInfo(invoice, line, categoryIdAndAncestorsByProductId, precision))
				.collect(ImmutableList.toImmutableList());
		if (lines.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableSet<BPartnerId> possibleTermPartnerIds = lines.stream()
				.map(InvoiceLineInfo::getShipmentBPartnerId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final Map<ProductId, Money> netAmtsByBonusProductId = new LinkedHashMap<>();
		for (final I_C_Flatrate_Term term : retrieveRefundTerms(invoice.getDateInvoiced(), customerId, possibleTermPartnerIds))
		{
			final RefundContract contract = refundContractRepository.ofRecord(term);
			if (!contract.isDeductedAtPayment())
			{
				continue;
			}

			final List<RefundConfig> configs = contract.getRefundConfigs();
			final RefundConfig config = RefundConfigs.smallestMinQty(configs); // the bonus at payment is a flat percentage; there are no quantity scales
			if (!RefundBase.PERCENTAGE.equals(config.getRefundBase()))
			{
				throw new AdempiereException("A refund that is deducted at payment needs a percentage base")
						.appendParametersToMessage()
						.setParameter("C_Flatrate_Term_ID", term.getC_Flatrate_Term_ID());
			}
			final ProductId bonusProductId = RefundConfigs.extractRefundProductId(configs);
			if (bonusProductId == null)
			{
				throw new AdempiereException("A refund that is deducted at payment needs a bonus product")
						.appendParametersToMessage()
						.setParameter("C_Flatrate_Term_ID", term.getC_Flatrate_Term_ID());
			}

			final ProductId termProductId = ProductId.ofRepoIdOrNull(term.getM_Product_ID());
			final Money baseNetAmt = lines.stream()
					.filter(line -> isRecipient(contract, customerId, line))
					.filter(line -> termProductId == null || termProductId.equals(line.getProductId()))
					.filter(line -> config.getProductId() == null || config.getProductId().equals(line.getProductId()))
					.filter(line -> config.getProductCategoryId() == null || line.getProductCategoryIdAndAncestors().contains(config.getProductCategoryId()))
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

	private static boolean isRecipient(@NonNull final RefundContract contract, @NonNull final BPartnerId customerId, @NonNull final InvoiceLineInfo line)
	{
		final BPartnerId recipientId = contract.extractBonusRecipient() == BonusRecipient.SHIPMENT_PARTNER
				? line.getShipmentBPartnerId()
				: customerId;
		return contract.getBPartnerId().equals(recipientId);
	}

	private List<I_C_Flatrate_Term> retrieveRefundTerms(
			@NonNull final Timestamp dateInvoiced,
			@NonNull final BPartnerId customerId,
			@NonNull final ImmutableSet<BPartnerId> shipmentBPartnerIds)
	{
		final ImmutableSet<BPartnerId> partnerIds = ImmutableSet.<BPartnerId>builder()
				.add(customerId)
				.addAll(shipmentBPartnerIds)
				.build();

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, X_C_Flatrate_Term.TYPE_CONDITIONS_Refund)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, X_C_Flatrate_Term.DOCSTATUS_Completed)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, dateInvoiced)
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, dateInvoiced)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, partnerIds)
				.orderBy(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID)
				.create()
				.list();
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
