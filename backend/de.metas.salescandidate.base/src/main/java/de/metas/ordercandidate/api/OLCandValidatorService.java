package de.metas.ordercandidate.api;

import com.google.common.collect.ImmutableList;
import de.metas.async.AsyncBatchId;
import de.metas.i18n.AdMessageKey;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandValidator;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OLCandValidatorService
{
	/** AD_Message to be used by users of this implementation. */
	public static final AdMessageKey MSG_ERRORS_FOUND = AdMessageKey.of("de.metas.ordercandidate.spi.impl.OLCandPriceValidator.FoundErrors");

	private final ThreadLocal<Boolean> validationProcessInProgress = ThreadLocal.withInitial(() -> Boolean.FALSE);

	private final OLCandRegistry olCandRegistry;
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public OLCandValidatorService(@NonNull final OLCandRegistry olCandRegistry)
	{
		this.olCandRegistry = olCandRegistry;
	}

	public boolean validate(@NonNull final I_C_OLCand olCand)
	{
		final IOLCandValidator validators = olCandRegistry.getValidators();

		// 08072
		// before validating, unset the isserror and set the error message on null.
		// this way they will be up to date after validation
		olCand.setErrorMsg(null);
		olCand.setIsError(false);

		validators.validate(olCand);

		return !olCand.isError();
	}

	/**
	 * @return a thread-local variable that indicates if the current thread is currently validating olCands. Can be used to avoid unnecessary calls to the validation API (from MVs).
	 */
	public boolean isValidationProcessInProgress()
	{
		final Boolean isUpdateProcess = validationProcessInProgress.get();

		return isUpdateProcess != null && isUpdateProcess;
	}

	/**
	 * See {@link #isValidationProcessInProgress()}.
	 */
	public boolean setValidationProcessInProgress(final boolean value)
	{
		final boolean isUpdateProcess = isValidationProcessInProgress();
		validationProcessInProgress.set(value);

		return isUpdateProcess;
	}


	public List<OLCandValidationResult> clearOLCandidates(
			@NonNull final List<I_C_OLCand> olCandList,
			@Nullable final AsyncBatchId asyncBatchId)
	{
		final List<OLCandValidationResult> olCandValidationResults = validateOLCands(olCandList);

		final boolean allFine = olCandValidationResults.stream().allMatch(OLCandValidationResult::isOk);

		if (!allFine)
		{
			//don't update anything
			return olCandValidationResults;
		}

		final ICompositeQueryUpdater<org.adempiere.process.rpl.model.I_C_OLCand> updater = queryBL.createCompositeQueryUpdater(org.adempiere.process.rpl.model.I_C_OLCand.class)
				.addSetColumnValue(org.adempiere.process.rpl.model.I_C_OLCand.COLUMNNAME_IsImportedWithIssues, false);

		if (asyncBatchId != null)
		{
			updater.addSetColumnValue(I_C_OLCand.COLUMNNAME_C_Async_Batch_ID, asyncBatchId.getRepoId());
		}

		final Set<Integer> olCandIdsToUpdate = olCandList.stream()
				.map(I_C_OLCand::getC_OLCand_ID)
				.collect(Collectors.toSet());

		queryBL.createQueryBuilder(org.adempiere.process.rpl.model.I_C_OLCand.class)
				.addInArrayFilter(org.adempiere.process.rpl.model.I_C_OLCand.COLUMNNAME_C_OLCand_ID, olCandIdsToUpdate)
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_Processed, false)
				.create()
				.update(updater);

		return olCandValidationResults;
	}

	@NonNull
	private List<OLCandValidationResult> validateOLCands(@NonNull final List<I_C_OLCand> olCandList)
	{
		setValidationProcessInProgress(true); // avoid the InterfaceWrapperHelper.save to trigger another validation from a MV.

		final ImmutableList.Builder<OLCandValidationResult> olCandValidationResultBuilder = ImmutableList.builder();
		try
		{
			for (final I_C_OLCand cand : olCandList)
			{
				validate(cand);
				InterfaceWrapperHelper.save(cand); // will only access the DB is there are changes in cand

				olCandValidationResultBuilder.add(cand.isError()
														  ? OLCandValidationResult.error(OLCandId.ofRepoId(cand.getC_OLCand_ID()))
														  : OLCandValidationResult.ok(OLCandId.ofRepoId(cand.getC_OLCand_ID())));
			}

			return olCandValidationResultBuilder.build();
		}
		finally
		{
			setValidationProcessInProgress(false);
		}
	}
}
