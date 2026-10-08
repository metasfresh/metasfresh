package de.metas.contracts.compensationGroup.contract;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.currency.CurrencyRepository;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyConfigRepository;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.money.MoneyService;
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
						new ContractServiceFeeTakeOverService(new InvoiceProcessingServiceCompanyService(
								new InvoiceProcessingServiceCompanyConfigRepository(),
								new MoneyService(new CurrencyRepository())))));
	}

	/** @return the schema with one own compensation line appended per matching take-over, the vendor's lines unchanged; the given schema when nothing is taken over */
	public GroupTemplate applyToSchema(
			@NonNull final GroupTemplate schema,
			@NonNull final OrderDropShipInfo order,
			@NonNull final ContractCompensationGroupSettings settings)
	{
		final ImmutableList<ContractSettingsTakeOverMatch> matches = computeMatches(order, settings);
		if (matches.isEmpty())
		{
			return schema;
		}

		// Resolve the customer's payment-service fee once, keyed by the linked SALES order's invoice partner and date.
		final ContractServiceFee fee = order.getInvoicePartnerId() != null && order.getSoDate() != null
				? feeService.resolveServiceFee(order.getInvoicePartnerId(), order.getSoDate())
				: ContractServiceFee.ZERO;

		// The per-customer fee is attached once, folded into the single take-over own-line; it cannot be split unambiguously across several.
		if (fee.isGreaterThanZero() && matches.size() > 1)
		{
			throw new AdempiereException("The customer's payment-service fee cannot be unambiguously attached to more than one take-over line")
					.setParameter("invoicePartnerId", order.getInvoicePartnerId())
					.setParameter("soDate", order.getSoDate())
					.setParameter("feePercent", fee.getPercent())
					.setParameter("matchCount", matches.size())
					.appendParametersToMessage();
		}

		// After the guard, more than one match implies a zero fee, so each extra line simply gets no fee.
		final ContractServiceFee feeToApply = matches.size() == 1 ? fee : ContractServiceFee.ZERO;

		final List<GroupTemplateCompensationLine> lines = new ArrayList<>(schema.getCompensationLines());
		matches.forEach(match -> appendOwnLine(lines, match, feeToApply));

		return schema.toBuilder()
				.clearCompensationLines()
				.compensationLines(lines)
				.build();
	}

	private void appendOwnLine(
			@NonNull final List<GroupTemplateCompensationLine> lines,
			@NonNull final ContractSettingsTakeOverMatch match,
			@NonNull final ContractServiceFee serviceFee)
	{
		final ContractSettingsTakeOver takeOver = match.getTakeOver();
		lines.add(GroupTemplateCompensationLine.builder()
				.productId(takeOver.getOwnLineProductId())
				.compensationType(GroupCompensationType.Discount)
				.percentage(match.getSummedPercent().add(serviceFee.getPercent()))
				.appliesToProductCategoryId(takeOver.getProductCategoryId())
				.isOwnBase(true)
				.description(createOwnLineDescription(match, serviceFee))
				.build());
	}

	/**
	 * e.g. {@code "3% Bonus Ware A + 1% Bonus Ware B"}, one entry per taken-over contract discount line; when the service
	 * fee applies, its own entry (its percent + the service-fee product's name) is appended in the same style.
	 */
	private String createOwnLineDescription(@NonNull final ContractSettingsTakeOverMatch match, @NonNull final ContractServiceFee serviceFee)
	{
		final Map<ProductId, String> productNames = productBL.getProductNames(match.getTakenOverProductIds());
		final String bonusEntries = match.getTakenOverPercentages()
				.stream()
				.map(takenOver -> formatPercent(takenOver.getPercent()) + " " + productNames.get(takenOver.getCustomerDiscountProductId()))
				.collect(Collectors.joining(" + "));

		if (!serviceFee.isGreaterThanZero())
		{
			return bonusEntries;
		}

		//noinspection ConstantConditions -- feeProductId is non-null when the fee is greater than zero
		final String feeEntry = formatPercent(serviceFee.getPercent()) + " " + productBL.getProductName(serviceFee.getFeeProductId());
		return bonusEntries + " + " + feeEntry;
	}

	private static String formatPercent(@NonNull final Percent percent)
	{
		return percent.toBigDecimal().toPlainString() + "%";
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
