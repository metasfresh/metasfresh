package org.adempiere.acct.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.GLDistributionResultLine.Sign;
import org.adempiere.acct.api.impl.AccountDimension;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_GL_Distribution;
import org.compiere.model.I_GL_DistributionLine;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.currency.ICurrencyDAO;
import de.metas.logging.LogManager;
import lombok.NonNull;

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
 * Helper class used to execute GL_Distribution on a given {@link IAccountDimension}, amount and qty.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class GLDistributionBuilder
{
	public static final GLDistributionBuilder newInstance()
	{
		return new GLDistributionBuilder();
	}

	// services
	private static final transient Logger log = LogManager.getLogger(GLDistributionBuilder.class);
	private final transient IGLDistributionDAO glDistributionDAO = Services.get(IGLDistributionDAO.class);
	private final transient ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

	// Parameters
	private Properties _ctx;
	private I_GL_Distribution _glDistribution;
	private BigDecimal _amountToDistribute;
	private Sign _amountSign = Sign.DETECT;
	private BigDecimal _qtyToDistribute;
	private Integer _currencyId;
	private Integer _precision;
	private IAccountDimension _accountDimension;

	private GLDistributionBuilder()
	{
	}

	public GLDistributionResult distribute()
	{
		final List<I_GL_DistributionLine> glDistributionLines = getGLDistributionLines();
		final List<GLDistributionResultLine> resultLines = new ArrayList<>(glDistributionLines.size());

		//
		// First Round
		BigDecimal amountDistributed = BigDecimal.ZERO;
		BigDecimal qtyDistributed = BigDecimal.ZERO;
		GLDistributionResultLine resultLine_BiggestAmt = null;
		GLDistributionResultLine resultLine_ZeroPercent = null;
		for (final I_GL_DistributionLine glDistributionLine : glDistributionLines)
		{
			Check.assume(glDistributionLine.isActive(), "Distribution line is active: {}", glDistributionLine);

			final GLDistributionResultLine resultLine = createResultLine(glDistributionLine);
			resultLines.add(resultLine);

			amountDistributed = amountDistributed.add(resultLine.getAmount());
			qtyDistributed = qtyDistributed.add(resultLine.getQty());

			// Remainder
			if (resultLine.getPercent().signum() == 0)
			{
				resultLine_ZeroPercent = resultLine;
			}
			if (resultLine_ZeroPercent == null)
			{
				if (resultLine_BiggestAmt == null)
				{
					resultLine_BiggestAmt = resultLine;
				}
				else if (resultLine.getAmount().compareTo(resultLine_BiggestAmt.getAmount()) > 0)
				{
					resultLine_BiggestAmt = resultLine;
				}
			}
		}

		// Adjust Amount Remainder
		{
			final BigDecimal amountToDistribute = getAmountToDistribute();
			final BigDecimal amountNotDistributed = amountToDistribute.subtract(amountDistributed);
			if (amountNotDistributed.signum() != 0)
			{
				if (resultLine_ZeroPercent != null)
				{
					resultLine_ZeroPercent.setAmount(amountNotDistributed);
				}
				else if (resultLine_BiggestAmt != null)
				{
					resultLine_BiggestAmt.setAmount(resultLine_BiggestAmt.getAmount().add(amountNotDistributed));
				}
				else
				{
					log.warn("distribute - Remaining Difference=" + amountNotDistributed);
				}
			}
		}

		// Adjust Qty Remainder
		{
			final BigDecimal qtyToDistribute = getQtyToDistribute();
			final BigDecimal qtyNotDistributed = qtyToDistribute.subtract(qtyDistributed);
			if (qtyNotDistributed.compareTo(Env.ZERO) != 0)
			{
				if (resultLine_ZeroPercent != null)
				{
					resultLine_ZeroPercent.setQty(qtyNotDistributed);
				}
				else if (resultLine_BiggestAmt != null)
				{
					resultLine_BiggestAmt.setQty(resultLine_BiggestAmt.getQty().add(qtyNotDistributed));
				}
				else
				{
					log.warn("distribute - Remaining Qty Difference=" + qtyNotDistributed);
				}
			}
		}

		return GLDistributionResult.of(resultLines);
	}

	private final GLDistributionResultLine createResultLine(final I_GL_DistributionLine glDistributionLine)
	{
		final IAccountDimension accountDimension = createAccountDimension(glDistributionLine);

		final GLDistributionResultLine resultLine = new GLDistributionResultLine();
		resultLine.setDescription(buildDescription(glDistributionLine));
		resultLine.setAccountDimension(accountDimension);

		final BigDecimal percent = glDistributionLine.getPercent();
		resultLine.setPercent(percent);

		//
		// Calculate Amount
		{
			final int precision = getPrecision();
			final BigDecimal amountToDistribute = getAmountToDistribute();
			final BigDecimal amt = amountToDistribute.multiply(percent).divide(Env.ONEHUNDRED, precision, BigDecimal.ROUND_HALF_UP);
			resultLine.setAmount(amt);
			resultLine.setAmountSign(getAmountSign());
			resultLine.setC_Currency_ID(getC_Currency_ID());
		}

		//
		// Calculate Qty
		{
			final BigDecimal qtyToDistribute = getQtyToDistribute();
			final BigDecimal qty = qtyToDistribute.multiply(percent).divide(Env.ONEHUNDRED, BigDecimal.ROUND_HALF_UP);
			resultLine.setQty(qty);
		}

		return resultLine;
	}	// setAmt

	private final IAccountDimension createAccountDimension(final I_GL_DistributionLine line)
	{
		final AccountDimension.Builder builder = AccountDimension.builder()
				.applyOverrides(getAccountDimension());

		if (line.isOverwriteOrg() && line.getOrg_ID() > 0)
		{
			builder.setAD_Org_ID(line.getOrg_ID());
		}
		if (line.isOverwriteAcct() && line.getAccount_ID() > 0)
		{
			builder.setC_ElementValue_ID(line.getAccount_ID());
		}
		if (line.isOverwriteProduct())
		{
			builder.setM_Product_ID(line.getM_Product_ID());
		}
		if (line.isOverwriteBPartner())
		{
			builder.setC_BPartner_ID(line.getC_BPartner_ID());
		}
		if (line.isOverwriteOrgTrx())
		{
			builder.setAD_OrgTrx_ID(line.getAD_OrgTrx_ID());
		}
		if (line.isOverwriteLocFrom())
		{
			builder.setC_LocFrom_ID(line.getC_LocFrom_ID());
		}
		if (line.isOverwriteLocTo())
		{
			builder.setC_LocTo_ID(line.getC_LocTo_ID());
		}
		if (line.isOverwriteSalesRegion())
		{
			builder.setC_SalesRegion_ID(line.getC_SalesRegion_ID());
		}
		if (line.isOverwriteProject())
		{
			builder.setC_Project_ID(line.getC_Project_ID());
		}
		if (line.isOverwriteCampaign())
		{
			builder.setC_Campaign_ID(line.getC_Campaign_ID());
		}
		if (line.isOverwriteActivity())
		{
			builder.setC_Activity_ID(line.getC_Activity_ID());
		}
		if (line.isOverwriteUser1())
		{
			builder.setUser1_ID(line.getUser1_ID());
		}
		if (line.isOverwriteUser2())
		{
			builder.setUser2_ID(line.getUser2_ID());
		}

		return builder.build();
	}

	private final String buildDescription(final I_GL_DistributionLine line)
	{
		final I_GL_Distribution distribution = getGLDistribution();

		final StringBuilder description = new StringBuilder()
				.append(distribution.getName()).append(" #").append(line.getLine());

		final String lineDescription = line.getDescription();
		if (!Check.isEmpty(lineDescription, true))
		{
			description.append(" - ").append(lineDescription.trim());
		}

		return description.toString();
	}

	public GLDistributionBuilder setGLDistribution(final I_GL_Distribution distribution)
	{
		_glDistribution = distribution;
		_ctx = null;
		return this;
	}

	private final I_GL_Distribution getGLDistribution()
	{
		Check.assumeNotNull(_glDistribution, "glDistribution not null");
		return _glDistribution;
	}

	private final Properties getCtx()
	{
		if (_ctx == null)
		{
			final I_GL_Distribution glDistribution = getGLDistribution();
			_ctx = InterfaceWrapperHelper.getCtx(glDistribution);
		}
		return _ctx;
	}

	private List<I_GL_DistributionLine> getGLDistributionLines()
	{
		final I_GL_Distribution glDistribution = getGLDistribution();
		return glDistributionDAO.retrieveLines(glDistribution);
	}

	public GLDistributionBuilder setC_Currency_ID(final int currencyId)
	{
		_currencyId = currencyId;
		_precision = null;
		return this;
	}

	private final int getC_Currency_ID()
	{
		Check.assumeNotNull(_currencyId, "currencyId not null");
		return _currencyId;
	}

	private final int getPrecision()
	{
		if (_precision == null)
		{
			_precision = currencyDAO.getStdPrecision(getCtx(), getC_Currency_ID());
		}
		return _precision;
	}

	public GLDistributionBuilder setAmountToDistribute(final BigDecimal amountToDistribute)
	{
		_amountToDistribute = amountToDistribute;
		return this;
	}

	private final BigDecimal getAmountToDistribute()
	{
		Check.assumeNotNull(_amountToDistribute, "amountToDistribute not null");
		return _amountToDistribute;
	}

	public GLDistributionBuilder setAmountSign(@NonNull final Sign amountSign)
	{
		_amountSign = amountSign;
		return this;
	}

	private final Sign getAmountSign()
	{
		Check.assumeNotNull(_amountSign, "amountSign not null");
		return _amountSign;
	}
	
	public GLDistributionBuilder setQtyToDistribute(final BigDecimal qtyToDistribute)
	{
		_qtyToDistribute = qtyToDistribute;
		return this;
	}

	private final BigDecimal getQtyToDistribute()
	{
		Check.assumeNotNull(_qtyToDistribute, "qtyToDistribute not null");
		return _qtyToDistribute;
	}

	public GLDistributionBuilder setAccountDimension(final IAccountDimension accountDimension)
	{
		_accountDimension = accountDimension;
		return this;
	}

	private final IAccountDimension getAccountDimension()
	{
		Check.assumeNotNull(_accountDimension, "_accountDimension not null");
		return _accountDimension;
	}

}
