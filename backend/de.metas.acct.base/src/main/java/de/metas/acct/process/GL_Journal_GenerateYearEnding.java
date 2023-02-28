package de.metas.acct.process;

import de.metas.acct.GLCategoryId;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaGeneralLedger;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.PostingType;
import de.metas.acct.gljournal.GL_JournalLine_Builder;
import de.metas.acct.gljournal.GL_Journal_Builder;
import de.metas.common.util.time.SystemTime;
import de.metas.organization.OrgId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryAggregateBuilder;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import de.metas.acct.Account;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.X_C_ElementValue;
import org.compiere.model.X_GL_JournalBatch;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

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
 */
public class GL_Journal_GenerateYearEnding extends JavaProcess implements IProcessDefaultParametersProvider
{
	// services
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);

	private static final String PARAM_C_AcctSchema_ID = I_GL_Journal.COLUMNNAME_C_AcctSchema_ID;
	private static final String PARAM_AD_ORG_ID = I_GL_Journal.COLUMNNAME_AD_Org_ID;
	private static final String PARAM_DateAcct = I_GL_Journal.COLUMNNAME_DateAcct;
	private static final String PARAM_GL_Category_ID = I_GL_Journal.COLUMNNAME_GL_Category_ID;

	@Param(parameterName = PARAM_C_AcctSchema_ID, mandatory = true)
	private AcctSchemaId p_acctSchemaId;
	private AcctSchema acctSchema;

	@Param(parameterName = PARAM_AD_ORG_ID, mandatory = true)
	private OrgId p_orgId;

	@Param(parameterName = PARAM_DateAcct, mandatory = true)
	private Instant p_dateAcct;

	@Param(parameterName = PARAM_GL_Category_ID, mandatory = false)
	private GLCategoryId p_glCategoryId;

	private static final ModelDynAttributeAccessor<I_C_ElementValue, BigDecimal> DYNATTR_AmtAcctDr = new ModelDynAttributeAccessor<>("AmtAcctDr", BigDecimal.class);
	private static final ModelDynAttributeAccessor<I_C_ElementValue, BigDecimal> DYNATTR_AmtAcctCr = new ModelDynAttributeAccessor<>("AmtAcctCr", BigDecimal.class);

	private Account p_Account_IncomeSummary;
	private Account p_Account_RetainedEarning;

	@Override
	protected void prepare()
	{
		acctSchema = acctSchemasRepo.getById(p_acctSchemaId);
		final AcctSchemaGeneralLedger acctSchemaGL = acctSchema.getGeneralLedger();
		p_Account_IncomeSummary = acctSchemaGL.getIncomeSummaryAcct();
		p_Account_RetainedEarning = acctSchemaGL.getRetainedEarningAcct();
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<I_C_ElementValue> accounts = retrieveExpenseAndRevenueAccountsWithBalances();
		if (accounts.isEmpty())
		{
			return MSG_OK;
		}

		final GLJournalRequest request = GLJournalRequest.builder()
				.clientId(getProcessInfo().getClientId())
				.orgId(p_orgId)
				.dateAcct(p_dateAcct)
				.dateDoc(p_dateAcct)
				.postingType(X_GL_JournalBatch.POSTINGTYPE_Statistical)
				.glCategoryId(p_glCategoryId)
				.acctSchemaId(p_acctSchemaId)
				.currencyId(acctSchema.getCurrencyId())
				.description(getName())
				.build();

		final GL_Journal_Builder glJournalBuilder = GL_Journal_Builder.newBuilder(request);

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
	 * Create bookings to transfer amount from <code>account</code> to {@link #p_Account_IncomeSummary}.
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
			glJournalLineBuilder.setAccountDR(p_Account_IncomeSummary.getAccountId());
			glJournalLineBuilder.setAccountCR(account);
			glJournalLineBuilder.setAmount(accountBalance);
		}
		else
		{
			glJournalLineBuilder.setAccountDR(account);
			glJournalLineBuilder.setAccountCR(p_Account_IncomeSummary.getAccountId());
			glJournalLineBuilder.setAmount(accountBalance.negate());
		}
	}

	/**
	 * Create bookings to transfer amount from {@link #p_Account_IncomeSummary} to {@link #p_Account_RetainedEarning}.
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
			glJournalLineBuilder.setAccountDR(p_Account_RetainedEarning.getAccountId());
			glJournalLineBuilder.setAccountCR(p_Account_IncomeSummary.getAccountId());
			glJournalLineBuilder.setAmount(amount);
		}
		else
		{
			glJournalLineBuilder.setAccountDR(p_Account_IncomeSummary.getAccountId());
			glJournalLineBuilder.setAccountCR(p_Account_RetainedEarning.getAccountId());
			glJournalLineBuilder.setAmount(amount.negate());
		}
	}

	private final List<I_C_ElementValue> retrieveExpenseAndRevenueAccountsWithBalances()
	{
		final IQuery<I_C_ElementValue> expenseAndRevenueAccountsQuery = queryBL.createQueryBuilder(I_C_ElementValue.class, this)
				.addOnlyContextClient()
				.addInArrayOrAllFilter(I_C_ElementValue.COLUMN_AccountType, X_C_ElementValue.ACCOUNTTYPE_Expense, X_C_ElementValue.ACCOUNTTYPE_Revenue)
				.create();

		final Timestamp day = TimeUtil.asTimestamp(p_dateAcct);
		final Date dateFrom = TimeUtil.getYearFirstDay(day);
		final Date dateTo = day;

		final IQueryAggregateBuilder<I_Fact_Acct, I_C_ElementValue> aggregateOnAccountBuilder = queryBL.createQueryBuilder(I_Fact_Acct.class, this)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Client_ID, getClientId())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_AD_Org_ID, p_orgId.getRepoId())
				.addBetweenFilter(I_Fact_Acct.COLUMNNAME_DateAcct, dateFrom, dateTo)
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_PostingType, PostingType.Actual.getCode())
				.addEqualsFilter(I_Fact_Acct.COLUMNNAME_C_AcctSchema_ID, p_acctSchemaId)
				.addInSubQueryFilter(I_Fact_Acct.COLUMNNAME_Account_ID, I_C_ElementValue.COLUMNNAME_C_ElementValue_ID, expenseAndRevenueAccountsQuery)
				//
				.aggregateOnColumn(I_Fact_Acct.COLUMNNAME_Account_ID, I_C_ElementValue.class);

		aggregateOnAccountBuilder.sum(DYNATTR_AmtAcctDr, I_Fact_Acct.COLUMN_AmtAcctDr);
		aggregateOnAccountBuilder.sum(DYNATTR_AmtAcctCr, I_Fact_Acct.COLUMN_AmtAcctCr);

		final List<I_C_ElementValue> accounts = aggregateOnAccountBuilder.aggregate();
		return accounts;
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_DateAcct.equals(parameter.getColumnName()))
		{
			return TimeUtil.getYearLastDay(SystemTime.asDate());
		}

		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

}
