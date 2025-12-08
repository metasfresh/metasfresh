package de.metas.contracts.flatrate.process;

import de.metas.common.util.time.SystemTime;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateBL.ContractExtendingRequest;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.document.engine.IDocument;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;

public class C_Flatrate_Term_Extend_And_Notify_User
		extends JavaProcess
{
	final private IQueryBL queryBL = Services.get(IQueryBL.class);
	final private IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Param(parameterName = I_C_Flatrate_Transition.COLUMNNAME_IsAutoCompleteNewTerm)
	private String p_forceComplete;

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_StartDate)
	private Timestamp p_startDate;

	@Override
	@RunOutOfTrx // each individual term (either one or many) is extended in its own transaction
	protected String doIt() throws Exception
	{
		final Boolean forceComplete = StringUtils.toBooleanOrNull(p_forceComplete);

		if (I_C_Flatrate_Term.Table_Name.equals(getTableName()))
		{
			extendSingleTerm(forceComplete);
		}
		else
		{
			extendAllEligibleTerms(forceComplete);
		}
		return MSG_OK;
	}

	private void extendSingleTerm(@Nullable final Boolean forceComplete)
	{
		final I_C_Flatrate_Term contractToExtend = getRecord(I_C_Flatrate_Term.class);

		// we are called from a given term => extend the term
		final ContractExtendingRequest context = ContractExtendingRequest.builder()
				.AD_PInstance_ID(getPinstanceId())
				.contract(contractToExtend)
				.forceExtend(true)
				.forceComplete(forceComplete)
				.nextTermStartDate(p_startDate)
				.build();

		trxManager.run(ITrx.TRXNAME_ThreadInherited, localTrxName_IGNORED -> flatrateBL.extendContractAndNotifyUser(context));

		addLog("@Processed@: @C_Flatrate_Term_ID@ " + contractToExtend.getC_Flatrate_Term_ID());

		getResult().setRecordToRefreshAfterExecution(TableRecordReference.of(contractToExtend));
	}

	private void extendAllEligibleTerms(@Nullable final Boolean forceComplete)
	{
		final ICompositeQueryFilter<I_C_Flatrate_Term> notQuitOrVoidedFilter = queryBL.createCompositeQueryFilter(I_C_Flatrate_Term.class)
				.setJoinOr()
				.addNotInArrayFilter(I_C_Flatrate_Term.COLUMN_ContractStatus, Arrays.asList(X_C_Flatrate_Term.CONTRACTSTATUS_Quit, X_C_Flatrate_Term.CONTRACTSTATUS_Voided))
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_ContractStatus, null);

		final int chunkSize = sysConfigBL.getIntValue("de.metas.contracts.flatrate.process.C_Flatrate_Term_Extend_And_Notify_User.chunkSize", 500);

		final Iterator<I_C_Flatrate_Term> termsToExtend = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_AD_PInstance_EndOfTerm_ID, 0, null)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMN_DocStatus, IDocument.STATUS_Completed)
				.addCompareFilter(I_C_Flatrate_Term.COLUMN_NoticeDate, Operator.LESS, SystemTime.asTimestamp())
				.filter(notQuitOrVoidedFilter)
				.orderBy().addColumn(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID).endOrderBy()
				.create()
				.setClient_ID()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true) // guaranteed = true, because the term extension changes AD_PInstance_EndOfTerm_ID
				.setOption(IQuery.OPTION_IteratorBufferSize, chunkSize) // the default is just 50
				.iterate(I_C_Flatrate_Term.class);

		final ExtendTermsResult result = new ExtendTermsResult();
		while (termsToExtend.hasNext())
		{
			// create up to 'chunkSize' terms at a time.
			// CreateMissingInvoiceCandidatesWorkpackageProcessor will be scheduled on commit and we need to avoid the overhead of 1000s of workpackages of which each one has just one term.
			trxManager.runInNewTrx(() -> result.addToThis(extendTermsChunk(termsToExtend, forceComplete, chunkSize)));
		}
		addLog("Processed {} terms; Processing failed for {} terms, see the log for AD_PInstance_ID={} for details", result.extendedCounter, result.errorCounter, getPinstanceId());
		if (result.errorCounter > 0)
		{
			throw new AdempiereException("At least one C_Flatrate_Term could not be extended; Check AD_PInstance_ID=" + PInstanceId.toRepoId(getPinstanceId()) + " for details");
		}
	}

	/**
	 * Extends up to {@code chunkSize} terms from the given iterator, then returns-
	 */
	private ExtendTermsResult extendTermsChunk(
			final @NonNull Iterator<I_C_Flatrate_Term> termsToExtend,
			final Boolean forceComplete,
			final int chunkSize)
	{
		final ExtendTermsResult result = new ExtendTermsResult();

		while (termsToExtend.hasNext())
		{
			final I_C_Flatrate_Term contractToExtend = termsToExtend.next();
			final ContractExtendingRequest context = ContractExtendingRequest.builder()
					.AD_PInstance_ID(getPinstanceId())
					.contract(contractToExtend)
					.forceExtend(false)
					.forceComplete(forceComplete)
					.nextTermStartDate(p_startDate)
					.build();
			if (tryExtendTerm(context))
			{
				result.extendedCounter++;
			}
			else
			{
				result.errorCounter++;
			}
			if (result.getCounterSum() >= chunkSize)
			{
				return result;
			}
		}
		return result;
	}

	@Data
	private static class ExtendTermsResult
	{
		int extendedCounter = 0;
		int errorCounter = 0;

		public void addToThis(@NonNull final C_Flatrate_Term_Extend_And_Notify_User.ExtendTermsResult other)
		{
			extendedCounter += other.extendedCounter;
			errorCounter += other.errorCounter;
		}

		public int getCounterSum()
		{
			return extendedCounter + errorCounter;
		}
	}

	private boolean tryExtendTerm(@NonNull final ContractExtendingRequest context)
	{
		try
		{
			flatrateBL.extendContractAndNotifyUser(context);
			return true;
		}
		catch (final RuntimeException e)
		{
			final I_C_Flatrate_Term contract = context.getContract();
			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(e);
			addLog("Error extending C_FlatrateTerm_ID={} with C_Flatrate_Data_ID={}; AD_Issue_ID={}; {} with message={}",
				   contract.getC_Flatrate_Term_ID(), contract.getC_Flatrate_Data_ID(), issueId, e.getClass().getName(), e.getMessage());
			return false;
		}
	}

}
