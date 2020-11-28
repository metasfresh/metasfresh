package de.metas.acct.process;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.dao.IQueryAggregateBuilder;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IRangeAwareParams;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_GL_JournalBatch;
import org.compiere.model.X_C_ElementValue;
import org.compiere.model.X_GL_JournalBatch;
import org.compiere.util.TimeUtil;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaGeneralLedger;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.PostingType;
import de.metas.acct.gljournal.GL_JournalLine_Builder;
import de.metas.acct.gljournal.GL_Journal_Builder;
import de.metas.process.JavaProcess;
import de.metas.util.Check;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.acct.base
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
 * Process used to create year ending bookings.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class GL_Journal_GenerateYearEnding extends JavaProcess
{
	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);

	//
	// Parameters
	private I_GL_JournalBatch p_GL_JournalBatch;
	private Date p_DateFrom;
	private Date p_DateTo;

	private static final String PARAM_C_AcctSchema_ID = "C_AcctSchema_ID";
	private AcctSchema acctSchema;
	private AccountId p_Account_IncomeSummaryId;
	private AccountId p_Account_RetainedEarningId;

	private static final ModelDynAttributeAccessor<I_C_ElementValue, BigDecimal> DYNATTR_AmtAcctDr = new ModelDynAttributeAccessor<>("AmtAcctDr", BigDecimal.class);
	private static final ModelDynAttributeAccessor<I_C_ElementValue, BigDecimal> DYNATTR_AmtAcctCr = new ModelDynAttributeAccessor<>("AmtAcctCr", BigDecimal.class);

	@Override
	protected void prepare()
	{
		final IRangeAwareParams params = getParameterAsIParams();

		p_GL_JournalBatch = getRecord(I_GL_JournalBatch.class);
		Check.assumeNotNull(p_GL_JournalBatch, "p_GL_JournalBatch not null");

		final Timestamp dateAcct = p_GL_JournalBatch.getDateAcct();
		p_DateFrom = TimeUtil.trunc(dateAcct, TimeUtil.TRUNC_YEAR);
		p_DateTo = TimeUtil.trunc(dateAcct, TimeUtil.TRUNC_DAY);

		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(params.getParameterAsInt(PARAM_C_AcctSchema_ID, -1));
		acctSchema = acctSchemasRepo.getById(acctSchemaId);
		final AcctSchemaGeneralLedger acctSchemaGL = acctSchema.getGeneralLedger();
		p_Account_IncomeSummaryId = acctSchemaGL.getIncomeSummaryAcctId();
		p_Account_RetainedEarningId = acctSchemaGL.getRetainedEarningAcctId();
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<I_C_ElementValue> accounts = retrieveExpenseAndRevenueAccountsWithBalances();
		if (accounts.isEmpty())
		{
			return MSG_OK;
		}

		p_GL_JournalBatch.setPostingType(X_GL_JournalBatch.POSTINGTYPE_Statistical);
		InterfaceWrapperHelper.save(p_GL_JournalBatch);

		final GL_Journal_Builder glJournalBuilder = GL_Journal_Builder.newBuilder(p_GL_JournalBatch)
				.setDateAcct(p_DateTo)
				.setDateDoc(p_DateTo)
				.setC_AcctSchema_ID(acctSchema.getId().getRepoId())
				.setC_Currency_ID(acctSchema.getCurrencyId().getRepoId())
				.setC_ConversionType_Default()
				.setDescription(getName());

		BigDecimal accountBalanceSum = BigDecimal.ZERO;
		for (final I_C_ElementValue account : accounts)
		{
			final BigDecimal amtAcctDr = DYNATTR_AmtAcctDr.getValue(account, BigDecimal.ZERO);
			final BigDecimal amtAcctCr = DYNATTR_AmtAcctCr.getValue(account, BigDecimal.ZERO);
			final BigDecimal accountBalance = amtAcctDr.subtract(amtAcctCr);
			createFacts_ProfitAndLoss_ThisYear(glJournalBuilder, account, accountBalance);

			accountBalanceSum = accountBalanceSum.add(accountBalance);
		}

		createFacts_ProfitAndLoss_BroughtForward(glJournalBuilder, accountBalanceSum);

		glJournalBuilder.build();

		return MSG_OK;
	}

	/**
	 * Create bookings to transfer amount from <code>account</code> to {@link #p_Account_IncomeSummaryId}.
	 *
	 * @param glJournalBuilder
	 * @param account
	 * @param accountBalance
	 */
	private void createFacts_ProfitAndLoss_ThisYear(final GL_Journal_Builder glJournalBuilder, final I_C_ElementValue account, final BigDecimal accountBalance)
	{
		if (accountBalance.signum() == 0)
		{
			return;
		}

		final GL_JournalLine_Builder glJournalLineBuilder = glJournalBuilder.newLine();
		if (accountBalance.signum() > 0)
		{
			glJournalLineBuilder.setAccountDR(p_Account_IncomeSummaryId);
			glJournalLineBuilder.setAccountCR(account);
			glJournalLineBuilder.setAmount(accountBalance);
		}
		else
		{
			glJournalLineBuilder.setAccountDR(account);
			glJournalLineBuilder.setAccountCR(p_Account_IncomeSummaryId);
			glJournalLineBuilder.setAmount(accountBalance.negate());
		}
	}

	/**
	 * Create bookings to transfer amount from {@link #p_Account_IncomeSummaryId} to {@link #p_Account_RetainedEarningId}.
	 *
	 * @param glJournalBuilder
	 * @param amount
	 */
	private void createFacts_ProfitAndLoss_BroughtForward(final GL_Journal_Builder glJournalBuilder, final BigDecimal amount)
	{
		if (amount.signum() == 0)
		{
			return;
		}

		final GL_JournalLine_Builder glJournalLineBuilder = glJournalBuilder.newLine();
		if (amount.signum() > 0)
		{
			glJournalLineBuilder.setAccountDR(p_Account_RetainedEarningId);
			glJournalLineBuilder.setAccountCR(p_Account_IncomeSummaryId);
			glJournalLineBuilder.setAmount(amount);
		}
		else
		{
			glJournalLineBuilder.setAccountDR(p_Account_IncomeSummaryId);
			glJournalLineBuilder.setAccountCR(p_Account_RetainedEarningId);
			glJournalLineBuilder.setAmount(amount.negate());
		}
	}

	private final List<I_C_ElementValue> retrieveExpenseAndRevenueAccountsWithBalances()
	{
		final IQuery<I_C_ElementValue> expenseAndRevenueAccountsQuery = queryBL.createQueryBuilder(I_C_ElementValue.class, this)
				.addOnlyContextClient()
				.addInArrayOrAllFilter(I_C_ElementValue.COLUMN_AccountType, X_C_ElementValue.ACCOUNTTYPE_Expense, X_C_ElementValue.ACCOUNTTYPE_Revenue)
				.create();

		final IQueryAggregateBuilder<I_Fact_Acct, I_C_ElementValue> aggregateOnAccountBuilder = queryBL.createQueryBuilder(I_Fact_Acct.class, this)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Client_ID, getAD_Client_ID())
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Org_ID, p_GL_JournalBatch.getAD_Org_ID())
				.addBetweenFilter(I_Fact_Acct.COLUMN_DateAcct, p_DateFrom, p_DateTo)
				.addEqualsFilter(I_Fact_Acct.COLUMN_PostingType, PostingType.Actual.getCode())
				.addEqualsFilter(I_Fact_Acct.COLUMN_C_AcctSchema_ID, acctSchema.getId())
				.addInSubQueryFilter(I_Fact_Acct.COLUMN_Account_ID, I_C_ElementValue.COLUMN_C_ElementValue_ID, expenseAndRevenueAccountsQuery)
				//
				.aggregateOnColumn(I_Fact_Acct.COLUMN_Account_ID);

		aggregateOnAccountBuilder.sum(DYNATTR_AmtAcctDr, I_Fact_Acct.COLUMN_AmtAcctDr);
		aggregateOnAccountBuilder.sum(DYNATTR_AmtAcctCr, I_Fact_Acct.COLUMN_AmtAcctCr);

		final List<I_C_ElementValue> accounts = aggregateOnAccountBuilder.aggregate();
		return accounts;
	}

}
