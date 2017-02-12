package org.compiere.acct;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.acct.api.GLDistributionBuilder;
import org.adempiere.acct.api.GLDistributionResult;
import org.adempiere.acct.api.GLDistributionResultLine;
import org.adempiere.acct.api.IAccountDimension;
import org.adempiere.acct.api.IFactAcctBL;
import org.adempiere.acct.api.IGLDistributionDAO;
import org.adempiere.acct.api.impl.AccountDimension;
import org.adempiere.acct.api.impl.AcctSegmentType;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_GL_Distribution;
import org.compiere.model.MAccount;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import com.google.common.collect.ImmutableList;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Helper class to apply {@link I_GL_Distribution}s on a given list of {@link FactLine}s.
 * 
 * It is used internally by {@link Fact}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */class FactGLDistributor
{
	public static final FactGLDistributor newInstance()
	{
		return new FactGLDistributor();
	}

	// Services
	private static final transient Logger logger = LogManager.getLogger(FactGLDistributor.class);
	private final transient IGLDistributionDAO glDistributionDAO = Services.get(IGLDistributionDAO.class);
	private final transient IFactAcctBL factAcctBL = Services.get(IFactAcctBL.class);

	private FactGLDistributor()
	{
		super();
	}

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
			final IAccountDimension lineDimension = factAcctBL.createAccountDimension(line);
			final I_GL_Distribution distribution = findGL_Distribution(line, lineDimension);
			if (distribution == null)
			{
				newLines.add(line); // keep the line as is
				continue;
			}

			//
			// Create GL_Distribution fact lines
			final GLDistributionResult distributionResult = GLDistributionBuilder.newInstance()
					.setGLDistribution(distribution)
					.setAccountDimension(lineDimension)
					.setAmountToDistribute(line.getSourceBalance())
					.setC_Currency_ID(line.getC_Currency_ID())
					.setQtyToDistribute(line.getQty())
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

	/**
	 * @param baseLine
	 * @return {@link I_GL_Distribution} or null
	 */
	private I_GL_Distribution findGL_Distribution(final FactLine baseLine, final IAccountDimension baseLineDimension)
	{
		final Properties ctx = baseLine.getCtx();
		final String postingType = baseLine.getPostingType();
		final Doc doc = baseLine.getDoc();
		final int docTypeId = doc.getC_DocType_ID();

		final List<I_GL_Distribution> distributions = glDistributionDAO.retrieve(ctx, baseLineDimension, postingType, docTypeId);
		if (distributions.isEmpty())
		{
			return null;
		}
		if (distributions.size() > 1)
		{
			final AdempiereException ex = new AdempiereException("More then one GL_Distribution found for " + baseLine
					+ "\nDimension: " + baseLineDimension
					+ "\nPostingType: " + postingType
					+ "\nC_DocType_ID: " + docTypeId
					+ "\nGL_Distribution(s): " + distributions);
			logger.warn("More then one GL_Distribution found. Using the first one.", ex);
		}
		final I_GL_Distribution distribution = distributions.get(0);
		return distribution;
	}

	private final List<FactLine> createFactLines(final FactLine baseLine, final GLDistributionResult glDistribution)
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

	private final FactLine createFactLine(final FactLine baseLine, final GLDistributionResultLine glDistributionLine)
	{
		final BigDecimal amount = glDistributionLine.getAmount();
		if (amount.signum() == 0)
		{
			return null;
		}

		final Doc doc = baseLine.getDoc();
		final DocLine docLine = baseLine.getDocLine();
		final Properties ctx = doc.getCtx();

		final IAccountDimension accountDimension = glDistributionLine.getAccountDimension();
		final MAccount account = MAccount.get(ctx, accountDimension);

		final FactLine factLine = new FactLine(ctx, baseLine.getAD_Table_ID(), baseLine.getRecord_ID(), baseLine.getLine_ID(), ITrx.TRXNAME_ThreadInherited);

		//
		// Set Info & Account
		factLine.setDocumentInfo(doc, docLine);
		factLine.setSubLine_ID(baseLine.getSubLine_ID());
		factLine.setAccount(baseLine.getAcctSchema(), account);
		factLine.setPostingType(baseLine.getPostingType());

		//
		// Update accounting dimensions
		factAcctBL.updateFactLineFromDimension(factLine, AccountDimension.builder()
				.applyOverrides(accountDimension)
				.clearC_AcctSchema_ID()
				.clearSegmentValue(AcctSegmentType.Account)
				.build());

		// Amount
		if (amount.signum() < 0)
		{
			factLine.setAmtSource(glDistributionLine.getC_Currency_ID(), null, amount.negate());
		}
		else
		{
			factLine.setAmtSource(glDistributionLine.getC_Currency_ID(), amount, null);
		}
		factLine.setQty(glDistributionLine.getQty());
		// Convert
		factLine.convert();

		// Description
		factLine.addDescription(glDistributionLine.getDescription());

		logger.info("{}", factLine);
		return factLine;
	}
}
