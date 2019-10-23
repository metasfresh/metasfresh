package de.metas.order.compensationGroup;

import static java.math.BigDecimal.ONE;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import de.metas.currency.CurrencyPrecision;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.lang.SOTrx;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

import javax.annotation.Nullable;

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

	private final CurrencyPrecision pricePrecision;
	private final CurrencyPrecision amountPrecision;

	@Getter
	private final BPartnerId bpartnerId;
	@Getter
	private final SOTrx soTrx;
	@Getter
	private final int flatrateConditionsId;

	private final ImmutableList<GroupRegularLine> regularLines;
	private final ArrayList<GroupCompensationLine> compensationLines;

	private transient BigDecimal _regularLinesNetAmt; // lazy

	@Builder
	private Group(
			@NonNull final GroupId groupId,
			@Nullable final GroupTemplateId groupTemplateId,
			@NonNull final CurrencyPrecision pricePrecision,
			@NonNull final CurrencyPrecision amountPrecision,
			@Nullable final BPartnerId bpartnerId,
			@NonNull final SOTrx soTrx,
			final int flatrateConditionsId,
			@NonNull @Singular final List<GroupRegularLine> regularLines,
			@NonNull @Singular final List<GroupCompensationLine> compensationLines)
	{
		this.groupId = groupId;
		this.groupTemplateId = groupTemplateId;
		this.pricePrecision = pricePrecision;
		this.amountPrecision = amountPrecision;
		this.bpartnerId = bpartnerId;
		this.soTrx = soTrx;
		this.flatrateConditionsId = flatrateConditionsId > 0 ? flatrateConditionsId : -1;

		if (regularLines.isEmpty())
		{
			throw new AdempiereException("Group shall contain at least one regular line");
		}

		this.regularLines = ImmutableList.copyOf(regularLines);
		this.compensationLines = new ArrayList<>(compensationLines);
		this.compensationLines.sort(Comparator.comparing(GroupCompensationLine::getSeqNo));
	}

	BigDecimal getRegularLinesNetAmt()
	{
		if (_regularLinesNetAmt == null)
		{
			_regularLinesNetAmt = regularLines.stream().map(GroupRegularLine::getLineNetAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		return _regularLinesNetAmt;
	}

	BigDecimal getTotalNetAmt()
	{
		final BigDecimal regularLinesNetAmt = getRegularLinesNetAmt();
		final BigDecimal compensationLinesNetAmt = compensationLines.stream().map(GroupCompensationLine::getLineNetAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
		final BigDecimal totalNetAmt = regularLinesNetAmt.add(compensationLinesNetAmt);
		return totalNetAmt;
	}

	List<GroupRegularLine> getRegularLines()
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

	public GroupCompensationLine getCompensationLineById(final int id)
	{
		if (id <= 0)
		{
			throw new AdempiereException("No compensation line found for id=" + id + " in group " + this);
		}
		return compensationLines.stream()
				.filter(line -> line.getRepoId() == id)
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("None or more then one compensation lines found for id=" + id + " in group " + this)));
	}

	public void updateAllCompensationLines()
	{
		moveAllManualCompensationLinesToEnd();

		BigDecimal previousNetAmt = getRegularLinesNetAmt();
		for (final GroupCompensationLine compensationLine : compensationLines)
		{
			updateCompensationLine(compensationLine, previousNetAmt);

			previousNetAmt = previousNetAmt.add(compensationLine.getLineNetAmt());
		}
	}

	private void updateCompensationLine(final GroupCompensationLine compensationLine, final BigDecimal baseAmt)
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

	public void addNewCompensationLine(final GroupCompensationLineCreateRequest request)
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
				.build();

		updateCompensationLine(compensationLine, getTotalNetAmt());

		compensationLines.add(compensationLine);
	}

	void removeAllGeneratedCompensationLines()
	{
		compensationLines.removeIf(GroupCompensationLine::isGeneratedLine);
	}

	private void moveAllManualCompensationLinesToEnd()
	{
		final ArrayList<GroupCompensationLine> manualCompensationLines = new ArrayList<>();
		for (final Iterator<GroupCompensationLine> it = compensationLines.iterator(); it.hasNext();)
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
