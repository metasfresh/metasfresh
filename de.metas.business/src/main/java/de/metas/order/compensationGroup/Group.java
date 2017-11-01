package de.metas.order.compensationGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
	private final int precision;
	private final ImmutableList<GroupRegularLine> regularLines;
	private final ArrayList<GroupCompensationLine> compensationLines;

	private transient BigDecimal _baseAmt; // lazy

	@Builder
	private Group(
			@NonNull final GroupId groupId,
			final int precision,
			@NonNull @Singular final List<GroupRegularLine> regularLines,
			@NonNull @Singular final List<GroupCompensationLine> compensationLines)
	{
		this.groupId = groupId;
		this.precision = precision;

		if (regularLines.isEmpty())
		{
			throw new AdempiereException("Group shall contain at least one regular line");
		}
		this.regularLines = ImmutableList.copyOf(regularLines);
		this.compensationLines = new ArrayList<>(compensationLines);
	}

	public BigDecimal getBaseAmt()
	{
		if (_baseAmt == null)
		{
			_baseAmt = regularLines.stream().map(GroupRegularLine::getLineNetAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
		}
		return _baseAmt;
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
		compensationLines.stream()
				.filter(GroupCompensationLine::isPercentage)
				.forEach(this::updatePercentageLine);
	}

	private void updatePercentageLine(final GroupCompensationLine compensationLine)
	{
		final BigDecimal baseAmt = getBaseAmt();
		compensationLine.setBaseAmt(baseAmt);

		final BigDecimal percentage = compensationLine.getPercentage();
		final GroupCompensationType compensationType = compensationLine.getType();

		final BigDecimal compensationAmt = baseAmt.multiply(percentage).divide(Env.ONEHUNDRED, precision, RoundingMode.HALF_UP);
		final BigDecimal amt = OrderGroupCompensationUtils.adjustAmtByCompensationType(compensationAmt, compensationType);

		compensationLine.setPriceAntQty(amt, BigDecimal.ONE);
	}

	public void addNewCompensationLine(final GroupCompensationLineCreateRequest request)
	{
		final GroupCompensationLine compensationLine = GroupCompensationLine.builder()
				.productId(request.getProductId())
				.uomId(request.getUomId())
				.type(request.getType())
				.amtType(request.getAmtType())
				.percentage(request.getPercentage())
				.price(request.getPrice())
				.qty(request.getQty())
				.build();

		if (compensationLine.isPercentage())
		{
			updatePercentageLine(compensationLine);
		}

		compensationLines.add(compensationLine);
	}
}
