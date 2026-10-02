package de.metas.order.compensationGroup;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.currency.CurrencyPrecision;
import de.metas.lang.SOTrx;
import de.metas.product.ProductCategoryId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.Percent;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ONE;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

@ToString
public class Group
{
	@Getter
	private final GroupId groupId;
	@Getter
	private final GroupTemplateId groupTemplateId;

	@Getter
	private final ActivityId activityId;

	private final CurrencyPrecision pricePrecision;
	private final CurrencyPrecision amountPrecision;

	@Getter
	private final BPartnerId bpartnerId;
	@Getter
	private final SOTrx soTrx;
	@Getter
	private final ConditionsId contractConditionsId;

	/** If {@code true}, every compensation line is computed on the regular-line total of its own applies-to category; if {@code false} (default), compensation lines of the same applies-to category compound with each other */
	@Getter
	private final boolean additive;

	private final ImmutableList<GroupRegularLine> regularLines;
	private final ArrayList<GroupCompensationLine> compensationLines;

	private transient BigDecimal _regularLinesNetAmt; // lazy

	@Builder
	private Group(
			@NonNull final GroupId groupId,
			@Nullable final GroupTemplateId groupTemplateId,
			@Nullable final ActivityId activityId,
			@NonNull final CurrencyPrecision pricePrecision,
			@NonNull final CurrencyPrecision amountPrecision,
			@Nullable final BPartnerId bpartnerId,
			@NonNull final SOTrx soTrx,
			@Nullable final ConditionsId contractConditionsId,
			final boolean additive,
			@NonNull @Singular final List<GroupRegularLine> regularLines,
			@NonNull @Singular final List<GroupCompensationLine> compensationLines)
	{
		this.groupId = groupId;
		this.groupTemplateId = groupTemplateId;
		this.activityId = activityId;
		this.pricePrecision = pricePrecision;
		this.amountPrecision = amountPrecision;
		this.bpartnerId = bpartnerId;
		this.soTrx = soTrx;
		this.contractConditionsId = contractConditionsId;
		this.additive = additive;

		if (regularLines.isEmpty())
		{
			final String compensatedProductIds = compensationLines.stream()
					.map(GroupCompensationLine::getProductId)
					.map(RepoIdAware::getRepoId)
					.map(Object::toString)
					.collect(Collectors.joining(", "));
			throw new AdempiereException("Group shall contain at least one regular line. It has only compensated lines for the product ID(s):" + compensatedProductIds);
		}

		this.regularLines = ImmutableList.copyOf(regularLines);
		this.compensationLines = new ArrayList<>(compensationLines);
		this.compensationLines.sort(Comparator.comparing(GroupCompensationLine::getSeqNo));
	}

	BigDecimal getRegularLinesNetAmt()
	{
		BigDecimal regularLinesNetAmt = _regularLinesNetAmt;
		if (regularLinesNetAmt == null)
		{
			regularLinesNetAmt = _regularLinesNetAmt = regularLines.stream().map(GroupRegularLine::getLineNetAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		return regularLinesNetAmt;
	}

	/**
	 * @param appliesToProductCategoryId {@code null} = the whole group's regular lines; else only the regular lines whose {@link GroupRegularLine#getProductCategoryIds()} contains it
	 */
	BigDecimal getRegularLinesNetAmt(@Nullable final ProductCategoryId appliesToProductCategoryId)
	{
		if (appliesToProductCategoryId == null)
		{
			return getRegularLinesNetAmt();
		}

		return regularLines.stream()
				.filter(regularLine -> regularLine.getProductCategoryIds().contains(appliesToProductCategoryId))
				.map(GroupRegularLine::getLineNetAmt)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	BigDecimal getTotalNetAmt()
	{
		final BigDecimal regularLinesNetAmt = getRegularLinesNetAmt();
		final BigDecimal compensationLinesNetAmt = compensationLines.stream().map(GroupCompensationLine::getLineNetAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
		return regularLinesNetAmt.add(compensationLinesNetAmt);
	}

	public ImmutableList<GroupRegularLine> getRegularLines()
	{
		return regularLines;
	}

	boolean hasCompensationLines()
	{
		return !compensationLines.isEmpty();
	}

	public List<GroupCompensationLine> getCompensationLines()
	{
		return ImmutableList.copyOf(compensationLines);
	}

	public GroupCompensationLine getCompensationLineById(@NonNull final RepoIdAware id)
	{
		return compensationLines.stream()
				.filter(line -> Objects.equals(line.getRepoId(), id))
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("None or more then one compensation lines found for id=" + id + " in group " + this)));
	}

	public void updateAllCompensationLines()
	{
		moveAllManualCompensationLinesToEnd();

		// non-additive (compounding) mode: one running total PER MATCHING CATEGORY, so a discount line compounds only
		// with previous discount lines that apply to the same category; a null/empty category compounds against the
		// whole group, as before.
		final Map<Optional<ProductCategoryId>, BigDecimal> runningNetAmtsByAppliesToProductCategory = new HashMap<>();
		for (final GroupCompensationLine compensationLine : compensationLines)
		{
			final ProductCategoryId appliesToProductCategoryId = compensationLine.getAppliesToProductCategoryId();
			final BigDecimal baseAmt;
			if (additive)
			{
				baseAmt = getRegularLinesNetAmt(appliesToProductCategoryId);
			}
			else
			{
				baseAmt = runningNetAmtsByAppliesToProductCategory.computeIfAbsent(Optional.ofNullable(appliesToProductCategoryId), key -> getRegularLinesNetAmt(appliesToProductCategoryId));
			}

			updateCompensationLine(compensationLine, baseAmt);

			if (!additive)
			{
				runningNetAmtsByAppliesToProductCategory.put(Optional.ofNullable(appliesToProductCategoryId), baseAmt.add(compensationLine.getLineNetAmt()));
			}
		}
	}

	private void updateCompensationLine(@NonNull final GroupCompensationLine compensationLine, @NonNull final BigDecimal baseAmt)
	{
		compensationLine.setBaseAmt(baseAmt);

		if (compensationLine.isPercentage())
		{
			final Percent percentage = compensationLine.getPercentage();
			final GroupCompensationType compensationType = compensationLine.getType();

			final BigDecimal compensationAmt = percentage.computePercentageOf(baseAmt, pricePrecision.toInt());
			final BigDecimal amt = OrderGroupCompensationUtils.adjustAmtByCompensationType(compensationAmt, compensationType);

			final Quantity one = Quantity.of(ONE,
					loadOutOfTrx(compensationLine.getUomId(), I_C_UOM.class));
			compensationLine.setPriceAndQty(amt, one, amountPrecision);
		}
	}

	public void addNewCompensationLine(@NonNull final GroupCompensationLineCreateRequest request)
	{
		final BigDecimal price = request.getPrice();
		final BigDecimal qtyEntered = request.getQtyEntered();
		final BigDecimal lineNetAmt = price != null && qtyEntered != null
				? amountPrecision.roundIfNeeded(price.multiply(qtyEntered))
				: null;
		final GroupCompensationLine compensationLine = GroupCompensationLine.builder()
				.productId(request.getProductId())
				.uomId(request.getUomId())
				.type(request.getType())
				.amtType(request.getAmtType())
				.percentage(request.getPercentage())
				.price(price)
				.qtyEntered(qtyEntered)
				.lineNetAmt(lineNetAmt)
				.groupTemplateLineId(request.getGroupTemplateLineId())
				.appliesToProductCategoryId(request.getAppliesToProductCategoryId())
				.takeOverId(request.getTakeOverId())
				.description(request.getDescription())
				.build();

		updateCompensationLine(compensationLine, computeInitialBaseAmt(compensationLine.getAppliesToProductCategoryId()));

		compensationLines.add(compensationLine);
	}

	/** Single-line variant of {@link #updateAllCompensationLines()}'s per-matching-category running total, for one new line */
	private BigDecimal computeInitialBaseAmt(@Nullable final ProductCategoryId appliesToProductCategoryId)
	{
		BigDecimal baseAmt = getRegularLinesNetAmt(appliesToProductCategoryId);

		if (!additive)
		{
			for (final GroupCompensationLine existingLine : compensationLines)
			{
				if (Objects.equals(existingLine.getAppliesToProductCategoryId(), appliesToProductCategoryId))
				{
					baseAmt = baseAmt.add(existingLine.getLineNetAmt());
				}
			}
		}

		return baseAmt;
	}

	void removeAllGeneratedLines()
	{
		compensationLines.removeIf(GroupCompensationLine::isGeneratedLine);
	}

	private void moveAllManualCompensationLinesToEnd()
	{
		final ArrayList<GroupCompensationLine> manualCompensationLines = new ArrayList<>();
		for (final Iterator<GroupCompensationLine> it = compensationLines.iterator(); it.hasNext(); )
		{
			final GroupCompensationLine compensationLine = it.next();
			if (compensationLine.isManualLine())
			{
				manualCompensationLines.add(compensationLine);
				it.remove();
			}
		}

		compensationLines.addAll(manualCompensationLines);
	}

	boolean isBasedOnGroupTemplate()
	{
		return getGroupTemplateId() != null;
	}
}
