package de.metas.order.compensationGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

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
	private final int groupTemplateId;
	private final int precision;
	@Getter
	private final int bpartnerId;
	@Getter
	private final boolean isSOTrx;
	@Getter
	private final int flatrateConditionsId;

	private final ImmutableList<GroupRegularLine> regularLines;
	private final ArrayList<GroupCompensationLine> compensationLines;

	private transient BigDecimal _regularLinesNetAmt; // lazy

	@Builder
	private Group(
			@NonNull final GroupId groupId,
			final int groupTemplateId,
			final int precision,
			final int bpartnerId,
			@NonNull final Boolean isSOTrx,
			final int flatrateConditionsId,
			@NonNull @Singular final List<GroupRegularLine> regularLines,
			@NonNull @Singular final List<GroupCompensationLine> compensationLines)
	{
		this.groupId = groupId;
		this.groupTemplateId = groupTemplateId;
		this.precision = precision;
		this.bpartnerId = bpartnerId > 0 ? bpartnerId : -1;
		this.isSOTrx = isSOTrx;
		this.flatrateConditionsId = flatrateConditionsId > 0 ? flatrateConditionsId : -1;

		if (regularLines.isEmpty())
		{
			throw new AdempiereException("Group shall contain at least one regular line");
		}

		this.regularLines = ImmutableList.copyOf(regularLines);
		this.compensationLines = new ArrayList<>(compensationLines);
		Collections.sort(this.compensationLines, Comparator.comparing(GroupCompensationLine::getSeqNo));
	}

	public BigDecimal getRegularLinesNetAmt()
	{
		if (_regularLinesNetAmt == null)
		{
			_regularLinesNetAmt = regularLines.stream().map(GroupRegularLine::getLineNetAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		return _regularLinesNetAmt;
	}

	public BigDecimal getTotalNetAmt()
	{
		final BigDecimal regularLinesNetAmt = getRegularLinesNetAmt();
		final BigDecimal compensationLinesNetAmt = compensationLines.stream().map(GroupCompensationLine::getLineNetAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
		final BigDecimal totalNetAmt = regularLinesNetAmt.add(compensationLinesNetAmt);
		return totalNetAmt;
	}

	public List<GroupRegularLine> getRegularLines()
	{
		return regularLines;
	}

	public boolean hasCompensationLines()
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

	public void updateAllPercentageLines()
	{
		BigDecimal previousNetAmt = getRegularLinesNetAmt();

		for (final GroupCompensationLine compensationLine : compensationLines)
		{
			if (compensationLine.isPercentage())
			{
				updatePercentageLine(compensationLine, previousNetAmt);
			}

			previousNetAmt = previousNetAmt.add(compensationLine.getLineNetAmt());
		}
	}

	private void updatePercentageLine(final GroupCompensationLine compensationLine, final BigDecimal baseAmt)
	{
		compensationLine.setBaseAmt(baseAmt);

		final BigDecimal percentage = compensationLine.getPercentage();
		final GroupCompensationType compensationType = compensationLine.getType();

		final BigDecimal compensationAmt = baseAmt.multiply(percentage).divide(Env.ONEHUNDRED, precision, RoundingMode.HALF_UP);
		final BigDecimal amt = OrderGroupCompensationUtils.adjustAmtByCompensationType(compensationAmt, compensationType);

		compensationLine.setPriceAndQty(amt, BigDecimal.ONE, precision);
	}

	public void addNewCompensationLine(final GroupCompensationLineCreateRequest request)
	{
		final BigDecimal price = request.getPrice();
		final BigDecimal qty = request.getQty();
		final BigDecimal lineNetAmt = price != null && qty != null ? price.multiply(qty).setScale(precision, RoundingMode.HALF_UP) : null;
		final GroupCompensationLine compensationLine = GroupCompensationLine.builder()
				.productId(request.getProductId())
				.uomId(request.getUomId())
				.type(request.getType())
				.amtType(request.getAmtType())
				.percentage(request.getPercentage())
				.price(price)
				.qty(qty)
				.lineNetAmt(lineNetAmt)
				.build();

		if (compensationLine.isPercentage())
		{
			updatePercentageLine(compensationLine, getTotalNetAmt());
		}

		compensationLines.add(compensationLine);
	}
	
	public void removeAllCompensationLines()
	{
		compensationLines.clear();
	}
}
