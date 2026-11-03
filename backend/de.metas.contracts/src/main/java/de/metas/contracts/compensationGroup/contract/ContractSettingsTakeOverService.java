package de.metas.contracts.compensationGroup.contract;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyConfigId;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.GroupCompensationLine;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupCompensationType;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateCompensationLine;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Takes over, onto a drop-ship purchase order's compensation group schema, the percentages of the linked sales order's
 * contract discount lines and of the invoice partner's qualifying refund contracts that the vendor's take-overs list.
 */
@Service
@RequiredArgsConstructor
public class ContractSettingsTakeOverService
{
	@NonNull private final ContractSettingsTakeOverRepository takeOverRepository;
	@NonNull private final OrderGroupRepository orderGroupRepository;
	@NonNull private final ContractServiceFeeTakeOverService feeService;
	@NonNull private final ContractSettingsTakeOverRefundService refundService;
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	@VisibleForTesting
	public static ContractSettingsTakeOverService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				ContractSettingsTakeOverService.class,
				() -> new ContractSettingsTakeOverService(
						ContractSettingsTakeOverRepository.newInstanceForUnitTesting(),
						OrderGroupRepository.newInstanceForUnitTesting(),
						ContractServiceFeeTakeOverService.newInstanceForUnitTesting(),
						ContractSettingsTakeOverRefundService.newInstanceForUnitTesting()));
	}

	/**
	 * @return the schema with one own compensation line appended per matching take-over and, when the linked sales order's
	 * invoice partner has a payment-service fee, one more own line carrying only that fee; the vendor's lines unchanged;
	 * the given schema when nothing is taken over and no fee applies
	 */
	public GroupTemplate applyToSchema(
			@NonNull final GroupTemplate schema,
			@NonNull final OrderDropShipInfo order,
			@NonNull final ContractCompensationGroupSettings settings)
	{
		final ImmutableList<ContractSettingsTakeOverMatch> matches = computeMatches(order, settings);

		final ContractServiceFee fee = resolveFeeToPassOn(order, settings);
		final ContractSettingsTakeOver feeTakeOver = fee.isGreaterThanZero() ? findTakeOverForServiceFeeOrNull(matches, order, settings, fee) : null;

		// note: like every contract line, the fee line is skipped later when no purchase order line falls into its base category
		if (matches.isEmpty() && feeTakeOver == null)
		{
			return schema;
		}

		final List<GroupTemplateCompensationLine> lines = new ArrayList<>(schema.getCompensationLines());
		matches.forEach(match -> lines.add(createTakeOverLine(match)));
		if (feeTakeOver != null)
		{
			lines.add(createServiceFeeLine(feeTakeOver, fee));
		}

		return schema.toBuilder()
				.clearCompensationLines()
				.compensationLines(lines)
				.build();
	}

	/**
	 * The customer's payment-service fee, keyed by the linked SALES order's invoice partner and date, counts only when
	 * the settings reference the very service company the fee belongs to; otherwise it is zero.
	 */
	private ContractServiceFee resolveFeeToPassOn(@NonNull final OrderDropShipInfo order, @NonNull final ContractCompensationGroupSettings settings)
	{
		final InvoiceProcessingServiceCompanyConfigId passOnConfigId = settings.getPassOnServiceCompanyConfigId();
		if (passOnConfigId == null || order.getInvoicePartnerId() == null || order.getSoDate() == null)
		{
			return ContractServiceFee.ZERO;
		}

		final ContractServiceFee customerFee = feeService.resolveServiceFee(order.getInvoicePartnerId(), order.getSoDate());
		if (!customerFee.isGreaterThanZero())
		{
			return ContractServiceFee.ZERO;
		}

		final BPartnerId passOnServiceCompanyId = feeService.getServiceCompanyBPartnerId(passOnConfigId);
		return BPartnerId.equals(passOnServiceCompanyId, customerFee.getServiceCompanyBPartnerId()) ? customerFee : ContractServiceFee.ZERO;
	}

	/**
	 * The fee line takes its discount product and base from the take-over row that took over the customer's bonus or refund or,
	 * when no bonus was taken over, from the settings' only take-over row.
	 *
	 * @return {@code null} when the settings have no take-over row (the vendor passes nothing on)
	 */
	@Nullable
	private ContractSettingsTakeOver findTakeOverForServiceFeeOrNull(
			@NonNull final ImmutableList<ContractSettingsTakeOverMatch> matches,
			@NonNull final OrderDropShipInfo order,
			@NonNull final ContractCompensationGroupSettings settings,
			@NonNull final ContractServiceFee fee)
	{
		final ImmutableList<ContractSettingsTakeOver> candidates = !matches.isEmpty()
				? matches.stream().map(ContractSettingsTakeOverMatch::getTakeOver).collect(ImmutableList.toImmutableList())
				: takeOverRepository.getBySettingsId(settings.getSettingsId());
		if (candidates.isEmpty())
		{
			return null;
		}
		if (candidates.size() > 1)
		{
			throw new AdempiereException("The customer's payment-service fee cannot be unambiguously attached to more than one take-over line")
					.setParameter("invoicePartnerId", order.getInvoicePartnerId())
					.setParameter("soDate", order.getSoDate())
					.setParameter("feePercent", fee.getPercent())
					.setParameter("takeOverCount", candidates.size())
					.appendParametersToMessage();
		}
		return candidates.get(0);
	}

	private GroupTemplateCompensationLine createTakeOverLine(@NonNull final ContractSettingsTakeOverMatch match)
	{
		final ContractSettingsTakeOver takeOver = match.getTakeOver();
		return newOwnLine(takeOver)
				.percentage(match.getRoundedSummedPercent())
				.description(createTakeOverLineDescription(match))
				.build();
	}

	private GroupTemplateCompensationLine createServiceFeeLine(@NonNull final ContractSettingsTakeOver takeOver, @NonNull final ContractServiceFee fee)
	{
		//noinspection ConstantConditions -- feeProductId is non-null when the fee is greater than zero
		return newOwnLine(takeOver)
				.percentage(fee.getPercent())
				.description(formatPercent(fee.getPercent()) + " " + productBL.getProductName(fee.getFeeProductId()))
				.build();
	}

	private static GroupTemplateCompensationLine.GroupTemplateCompensationLineBuilder newOwnLine(@NonNull final ContractSettingsTakeOver takeOver)
	{
		return GroupTemplateCompensationLine.builder()
				.productId(takeOver.getOwnLineProductId())
				.compensationType(GroupCompensationType.Discount)
				.appliesToProductCategoryId(takeOver.getProductCategoryId())
				.isOwnBase(true);
	}

	/**
	 * e.g. {@code "3% Bonus Ware + 1% RV Ware 1 % vierteljährlich + 0.125% RV Ware 0,125 % jährlich"}: one entry per taken-over
	 * contract discount line, then one per taken-over refund contract (with its condition's name), in the refund lookup's order
	 */
	private String createTakeOverLineDescription(@NonNull final ContractSettingsTakeOverMatch match)
	{
		final Map<ProductId, String> productNames = productBL.getProductNames(match.getTakenOverProductIds());
		final List<String> parts = new ArrayList<>();
		match.getTakenOverPercentages()
				.forEach(takenOver -> parts.add(formatPercent(takenOver.getPercent()) + " " + productNames.get(takenOver.getCustomerDiscountProductId())));
		match.getTakenOverRefunds()
				.forEach(refund -> parts.add(formatPercent(refund.getPercent()) + " " + refund.getConditionsName()));
		return String.join(" + ", parts);
	}

	static String formatPercent(@NonNull final Percent percent)
	{
		return percent.toBigDecimal().stripTrailingZeros().toPlainString() + "%";
	}

	/**
	 * @return one match per take-over whose customer discount products are on the percentage discount lines of the linked
	 * sales order's contract-created groups (with the nominal percentage of each such line) and/or whose listed refund conditions
	 * have qualifying refund contracts of the linked sales order's invoice partner at its date; take-overs with neither are dropped
	 */
	@VisibleForTesting
	ImmutableList<ContractSettingsTakeOverMatch> computeMatches(
			@NonNull final OrderDropShipInfo order,
			@NonNull final ContractCompensationGroupSettings settings)
	{
		final OrderId salesOrderId = order.getDropShipLinkedSalesOrderId().orElse(null);
		if (salesOrderId == null)
		{
			return ImmutableList.of();
		}

		final ImmutableList<ContractSettingsTakeOver> takeOvers = takeOverRepository.getBySettingsId(settings.getSettingsId());
		if (takeOvers.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList<GroupCompensationLine> salesOrderDiscountLines = orderGroupRepository.retrieveContractCreatedGroupsByOrderId(salesOrderId)
				.stream()
				.flatMap(group -> group.getCompensationLines().stream())
				.filter(line -> GroupCompensationLineCreateRequestFactory.isPercentDiscount(line.getType(), line.getAmtType()))
				.collect(ImmutableList.toImmutableList());

		final ImmutableListMultimap<ConditionsId, TakenOverRefund> refundsByConditionsId = findTakenOverRefunds(order, takeOvers);

		return takeOvers.stream()
				.map(takeOver -> computeMatchOrNull(takeOver, salesOrderDiscountLines, refundsByConditionsId))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * One lookup for the refund conditions of all take-overs; none when no take-over lists a refund condition,
	 * the linked sales order's invoice partner or date is unknown, or its refunds may not be taken over (reactivated sales order).
	 */
	private ImmutableListMultimap<ConditionsId, TakenOverRefund> findTakenOverRefunds(
			@NonNull final OrderDropShipInfo order,
			@NonNull final ImmutableList<ContractSettingsTakeOver> takeOvers)
	{
		final BPartnerId invoicePartnerId = order.getInvoicePartnerId();
		final ZonedDateTime soDate = order.getSoDate();
		final ImmutableSet<ConditionsId> conditionsIds = takeOvers.stream()
				.flatMap(takeOver -> takeOver.getRefundConditionsIds().stream())
				.collect(ImmutableSet.toImmutableSet());
		if (!order.isRefundTakeOverAllowed() || invoicePartnerId == null || soDate == null || conditionsIds.isEmpty())
		{
			return ImmutableListMultimap.of();
		}

		// the SO's DateOrdered as a date in the same (system) zone in which the refund lookup compares it with the terms' StartDate/EndDate
		return refundService.findTakenOverRefunds(invoicePartnerId, TimeUtil.asLocalDateNonNull(TimeUtil.asTimestampNotNull(soDate)), conditionsIds);
	}

	/**
	 * Every qualifying refund contract of a listed condition counts, also two contracts of the same condition
	 * (REQUIREMENTS: every configured bonus reaches the purchase order).
	 */
	@Nullable
	private static ContractSettingsTakeOverMatch computeMatchOrNull(
			@NonNull final ContractSettingsTakeOver takeOver,
			@NonNull final List<GroupCompensationLine> salesOrderDiscountLines,
			@NonNull final ImmutableListMultimap<ConditionsId, TakenOverRefund> refundsByConditionsId)
	{
		final ImmutableList<ContractSettingsTakeOverMatch.TakenOverPercentage> takenOverPercentages = salesOrderDiscountLines.stream()
				.filter(line -> takeOver.getCustomerDiscountProductIds().contains(line.getProductId()) && !line.getPercentage().isZero())
				.map(line -> ContractSettingsTakeOverMatch.TakenOverPercentage.of(line.getProductId(), line.getPercentage()))
				.collect(ImmutableList.toImmutableList());
		final ImmutableList<TakenOverRefund> takenOverRefunds = refundsByConditionsId.entries()
				.stream()
				.filter(entry -> takeOver.getRefundConditionsIds().contains(entry.getKey()))
				.map(Map.Entry::getValue)
				.collect(ImmutableList.toImmutableList());
		return takenOverPercentages.isEmpty() && takenOverRefunds.isEmpty()
				? null
				: new ContractSettingsTakeOverMatch(takeOver, takenOverPercentages, takenOverRefunds);
	}
}
