package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class FactTrxLines
{
	public static enum FactTrxLinesType
	{
		/** one debit line, one or more credit lines */
		Debit,
		/** one credit line, one or more debit lines */
		Credit,
		/** no debit or credit lines, might have zero lines */
		EmptyOrZero,
	}

	FactTrxLinesType type;
	List<FactLine> drLines;
	List<FactLine> crLines;
	List<FactLine> zeroLines;

	@Builder
	private FactTrxLines(@NonNull @Singular final List<FactLine> factLines)
	{
		final ArrayList<FactLine> drLines = new ArrayList<>();
		final ArrayList<FactLine> crLines = new ArrayList<>();
		final ArrayList<FactLine> zeroLines = new ArrayList<>();

		for (final FactLine factLine : factLines)
		{
			final boolean isDR = factLine.getAmtAcctDr().signum() != 0;
			final boolean isCR = factLine.getAmtAcctCr().signum() != 0;
			if (isDR && isCR)
			{
				throw new AdempiereException("Fact lines with both DR and CR amounts set are not allowed: " + factLine);
			}
			else if (!isDR && !isCR)
			{
				// skip zero lines, they are pointless
				zeroLines.add(factLine);
			}
			else if (isDR)
			{
				drLines.add(factLine);
			}
			else // if (isCR)
			{
				crLines.add(factLine);
			}
		}

		// https://github.com/metasfresh/metasfresh/issues/4147
		// if
		// * all not-zero lines are in dr and sum up to zero
		// * and we have at least one zero line,
		// then move the last one of those zero-line to cr
		// why the last: the line we add to cr defines which account is linked.
		// we want to cover the case of invoices when customer receivables/vendor liability is zero and link that account
		//
		// ..likewise for cr
		if (!zeroLines.isEmpty())// if (zeroLines.size() == 1)
		{
			if (drLines.isEmpty() && crLines.isEmpty())
			{
				// all the lines have zero-amounts; leave them in there; extractType() can handle it
			}
			else if (drLines.isEmpty() && !crLines.isEmpty())
			{
				final BigDecimal crLineSum = crLines.stream()
						.map(FactLine::getAmtAcctCr)
						.reduce(BigDecimal.ZERO, BigDecimal::add);
				if (crLineSum.signum() == 0)
				{
					drLines.add(zeroLines.remove(zeroLines.size() - 1));
				}
			}
			else if (!drLines.isEmpty() && crLines.isEmpty())
			{
				final BigDecimal drLineSum = crLines.stream()
						.map(FactLine::getAmtAcctDr)
						.reduce(BigDecimal.ZERO, BigDecimal::add);
				if (drLineSum.signum() == 0)
				{
					crLines.add(zeroLines.remove(zeroLines.size() - 1));
				}
			}
		}

		this.drLines = ImmutableList.copyOf(drLines);
		this.crLines = ImmutableList.copyOf(crLines);
		this.zeroLines = ImmutableList.copyOf(zeroLines);

		type = extractType(this.drLines, this.crLines);
	}

	private static FactTrxLinesType extractType(
			@NonNull final List<FactLine> drLines,
			@NonNull final List<FactLine> crLines)
	{
		if (drLines.size() == 1 && crLines.size() >= 1)
		{
			return FactTrxLinesType.Debit;
		}
		//
		// Case: 1 credit line, one or more debit lines
		else if (drLines.size() >= 1 && crLines.size() == 1)
		{
			return FactTrxLinesType.Credit;
		}
		//
		// Case: no debit lines, no credit lines
		else if (drLines.isEmpty() && crLines.isEmpty())
		{
			return FactTrxLinesType.EmptyOrZero;
		}
		else
		{
			final AdempiereException ex = new AdempiereException("Invalid accounting operation structure (" + drLines.size() + " DR lines, " + crLines.size() + " CR lines)")
					.appendParametersToMessage();
			for (int i = 0, size = drLines.size(); i < size; i++)
			{
				ex.setParameter("DR " + (i + 1), drLines.get(i));
			}
			for (int i = 0, size = crLines.size(); i < size; i++)
			{
				ex.setParameter("CR " + (i + 1), crLines.get(i));
			}

			throw ex;
		}
	}

	private void assertType(final FactTrxLinesType expected)
	{
		Check.assume(type == expected, "Invalid type. Expected {} but it was {}", expected, type);
	}

	public FactLine getDebitLine()
	{
		assertType(FactTrxLinesType.Debit);
		return drLines.get(0);
	}

	public FactLine getCreditLine()
	{
		assertType(FactTrxLinesType.Credit);
		return crLines.get(0);
	}

	public void forEachDebitLine(final Consumer<FactLine> debitLineConsumer)
	{
		drLines.forEach(debitLineConsumer);
	}

	public void forEachCreditLine(final Consumer<FactLine> creditLineConsumer)
	{
		crLines.forEach(creditLineConsumer);
	}

	public void forEachZeroLine(final Consumer<FactLine> zeroLineConsumer)
	{
		zeroLines.forEach(zeroLineConsumer);
	}
}
