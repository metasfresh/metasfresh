package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AccountDimension;
import de.metas.acct.api.PostingType;
import de.metas.acct.api.impl.AcctSegmentType;
import de.metas.acct.gldistribution.GLDistributionBuilder;
import de.metas.acct.gldistribution.GLDistributionResult;
import de.metas.acct.gldistribution.GLDistributionResultLine;
import de.metas.acct.gldistribution.GLDistributionResultLine.Sign;
import de.metas.acct.gldistribution.IGLDistributionDAO;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.document.DocTypeId;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.quantity.Quantitys;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_GL_Distribution;
import org.compiere.model.MAccount;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Helper class to apply {@link I_GL_Distribution}s on a given list of {@link FactLine}s.
 * It is used internally by {@link Fact}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
/* package */class FactGLDistributor
{
	public static FactGLDistributor newInstance()
	{
		return new FactGLDistributor();
	}

	// Services
	private static final Logger logger = LogManager.getLogger(FactGLDistributor.class);
	private final transient IGLDistributionDAO glDistributionDAO = Services.get(IGLDistributionDAO.class);

	private FactGLDistributor() {}

	public List<FactLine> distribute(final List<FactLine> lines)
	{
		// no lines -> nothing to distribute
		if (lines.isEmpty())
		{
			return lines;
		}

		final List<FactLine> newLines = new ArrayList<>();
		final List<FactLine> newLines_Last = new ArrayList<>();

		// For all fact lines
		for (final FactLine line : lines)
		{
			final AccountDimension lineDimension = line.toAccountDimension();
			final I_GL_Distribution distribution = findGL_Distribution(line, lineDimension);
			if (distribution == null)
			{
				newLines.add(line); // keep the line as is
				continue;
			}

			//
			// Create GL_Distribution fact lines
			final IPair<Sign, BigDecimal> amountToDistribute = deriveAmountAndSign(line);

			final GLDistributionResult distributionResult = GLDistributionBuilder.newInstance()
					.setGLDistribution(distribution)
					.setAccountDimension(lineDimension)
					.setAmountSign(amountToDistribute.getLeft())
					.setAmountToDistribute(amountToDistribute.getRight())
					.setCurrencyId(line.getCurrencyId())
					.setQtyToDistribute(line.getQty() != null ? line.getQty().toBigDecimal() : null)
					.distribute();
			final List<FactLine> lines_Distributed = createFactLines(line, distributionResult);

			// FR 2685367 - GL Distribution delete line instead reverse
			if (distribution.isCreateReversal())
			{
				newLines.add(line); // keep the original line in it's place

				// Add Reversal
				final FactLine reversal = line.accrue(distribution.getName());
				newLines_Last.add(reversal);

				// Add the "distribution to" lines
				newLines_Last.addAll(lines_Distributed);
			}
			else
			{
				// NOTE don't add the original line because we are replacing it

				// Add the "distribution to" lines
				newLines.addAll(lines_Distributed);
			}
		}

		//
		// Add last lines to our new lines
		newLines.addAll(newLines_Last);

		return newLines;
	}

	private IPair<Sign, BigDecimal> deriveAmountAndSign(@NonNull final FactLine line)
	{
		final Sign amountSign;
		final BigDecimal amount;
		if (line.getAmtSourceDr() != null && line.getAmtSourceDr().signum() != 0)
		{
			amountSign = Sign.DEBIT;
			amount = line.getAmtSourceDr();
		}
		else
		{
			amountSign = Sign.CREDIT;
			amount = line.getAmtSourceCr();
		}
		return ImmutablePair.of(amountSign, amount);
	}

	@Nullable
	private I_GL_Distribution findGL_Distribution(final FactLine baseLine, final AccountDimension baseLineDimension)
	{
		final PostingType postingType = baseLine.getPostingType();
		final DocTypeId docTypeId = baseLine.getC_DocType_ID();

		final List<I_GL_Distribution> distributions = glDistributionDAO.retrieve(Env.getCtx(), baseLineDimension, postingType, docTypeId);
		if (distributions.isEmpty())
		{
			return null;
		}
		else if (distributions.size() > 1)
		{
			final AdempiereException ex = new AdempiereException("More then one GL_Distribution found for " + baseLine
					+ "\nDimension: " + baseLineDimension
					+ "\nPostingType: " + postingType
					+ "\nC_DocType_ID: " + docTypeId
					+ "\nGL_Distribution(s): " + distributions);
			logger.warn("More then one GL_Distribution found. Using the first one.", ex);
			return distributions.get(0);
		}
		else
		{
			return distributions.get(0);
		}
	}

	private List<FactLine> createFactLines(final FactLine baseLine, final GLDistributionResult glDistribution)
	{
		final List<GLDistributionResultLine> glDistributionLines = glDistribution.getResultLines();
		if (glDistributionLines.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<FactLine> factLines = new ArrayList<>(glDistributionLines.size());
		for (final GLDistributionResultLine glDistributionLine : glDistributionLines)
		{
			final FactLine factLine = createFactLine(baseLine, glDistributionLine);
			if (factLine == null)
			{
				continue;
			}
			factLines.add(factLine);
		}

		return factLines;
	}

	private FactLine createFactLine(final FactLine baseLine, final GLDistributionResultLine glDistributionLine)
	{
		final BigDecimal amount = glDistributionLine.getAmount();
		if (amount.signum() == 0)
		{
			return null;
		}

		final AccountDimension accountDimension = glDistributionLine.getAccountDimension();
		final MAccount account = MAccount.get(Env.getCtx(), accountDimension);

		final FactLine factLine = FactLine.builder()
				.services(baseLine.getServices())
				.doc(baseLine.getDoc())
				.docLine(baseLine.getDocLine())
				.docRecordRef(baseLine.getDocRecordRef())
				.Line_ID(baseLine.getLine_ID())
				.SubLine_ID(baseLine.getSubLine_ID())
				.postingType(baseLine.getPostingType())
				.acctSchema(baseLine.getAcctSchema())
				.account(account)
				.accountConceptualName(null)
				.additionalDescription(glDistributionLine.getDescription())
				.qty(baseLine.getQty() != null ? Quantitys.of(glDistributionLine.getQty(), baseLine.getQty().getUomId()) : null)
				.build();

		//
		// Update accounting dimensions
		factLine.updateFromDimension(AccountDimension.builder()
				.applyOverrides(accountDimension)
				.clearC_AcctSchema_ID()
				.clearSegmentValue(AcctSegmentType.Account)
				.build());

		// Amount
		setAmountToFactLine(glDistributionLine, factLine);

		logger.debug("{}", factLine);
		return factLine;
	}

	private void setAmountToFactLine(
			@NonNull final GLDistributionResultLine glDistributionLine,
			@NonNull final FactLine factLine)
	{
		final Money amount = Money.of(glDistributionLine.getAmount(), glDistributionLine.getCurrencyId());

		switch (glDistributionLine.getAmountSign())
		{
			case CREDIT:
				factLine.setAmtSource(null, amount);
				break;

			case DEBIT:
				factLine.setAmtSource(amount, null);
				break;

			case DETECT:
				if (amount.signum() < 0)
				{
					factLine.setAmtSource(null, amount.negate());
				}
				else
				{
					factLine.setAmtSource(amount, null);
				}
				break;
		}

		factLine.convert();
	}
}
