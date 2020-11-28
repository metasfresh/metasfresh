package de.metas.ordercandidate.api;

import de.metas.i18n.AdMessageKey;
import org.springframework.stereotype.Service;

import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandValidator;
import lombok.NonNull;

@Service
public class OLCandValidatorService
{
	/** AD_Message to be used by users of this implementation. */
	public static final AdMessageKey MSG_ERRORS_FOUND = AdMessageKey.of("de.metas.ordercandidate.spi.impl.OLCandPriceValidator.FoundErrors");

	private final ThreadLocal<Boolean> validationProcessInProgress = ThreadLocal.withInitial(() -> Boolean.FALSE);

	private final OLCandRegistry olCandRegistry;

	public OLCandValidatorService(
			@NonNull final OLCandRegistry olCandRegistry)
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
}
