package de.metas.contracts.compensationGroup.contract;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
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
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
 * contract discount lines that the vendor's take-overs list.
 */
@Service
@RequiredArgsConstructor
public class ContractSettingsTakeOverService
{
	@NonNull private final ContractSettingsTakeOverRepository takeOverRepository;
	@NonNull private final OrderGroupRepository orderGroupRepository;
	@NonNull private final ContractServiceFeeTakeOverService feeService;
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
						ContractServiceFeeTakeOverService.newInstanceForUnitTesting()));
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
		return passOnServiceCompanyId.equals(customerFee.getServiceCompanyBPartnerId()) ? customerFee : ContractServiceFee.ZERO;
	}

	/**
	 * The fee line takes its discount product and base from the take-over row that took over the customer's bonus or,
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
				.percentage(match.getSummedPercent())
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
	 * e.g. {@code "3% Bonus Ware A + 1% Bonus Ware B"}, one entry per taken-over contract discount line
	 */
	private String createTakeOverLineDescription(@NonNull final ContractSettingsTakeOverMatch match)
	{
		final Map<ProductId, String> productNames = productBL.getProductNames(match.getTakenOverProductIds());
		return match.getTakenOverPercentages()
				.stream()
				.map(takenOver -> formatPercent(takenOver.getPercent()) + " " + productNames.get(takenOver.getCustomerDiscountProductId()))
				.collect(Collectors.joining(" + "));
	}

	static String formatPercent(@NonNull final Percent percent)
	{
		return percent.toBigDecimal().stripTrailingZeros().toPlainString() + "%";
	}

	/**
	 * @return one match per take-over whose customer discount products are on the percentage discount lines of the linked
	 * sales order's contract-created groups, with the nominal percentage of each such line; take-overs without such a line are dropped
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

		return takeOvers.stream()
				.map(takeOver -> computeMatchOrNull(takeOver, salesOrderDiscountLines))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	private static ContractSettingsTakeOverMatch computeMatchOrNull(
			@NonNull final ContractSettingsTakeOver takeOver,
			@NonNull final List<GroupCompensationLine> salesOrderDiscountLines)
	{
		final ImmutableList<ContractSettingsTakeOverMatch.TakenOverPercentage> takenOverPercentages = salesOrderDiscountLines.stream()
				.filter(line -> takeOver.getCustomerDiscountProductIds().contains(line.getProductId()) && !line.getPercentage().isZero())
				.map(line -> ContractSettingsTakeOverMatch.TakenOverPercentage.of(line.getProductId(), line.getPercentage()))
				.collect(ImmutableList.toImmutableList());
		return takenOverPercentages.isEmpty() ? null : new ContractSettingsTakeOverMatch(takeOver, takenOverPercentages);
	}
}
