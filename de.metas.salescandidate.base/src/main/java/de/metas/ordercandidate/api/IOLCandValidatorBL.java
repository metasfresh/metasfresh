package de.metas.ordercandidate.api;

import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.ISingletonService;

public interface IOLCandValidatorBL extends ISingletonService
{
	/**
	 * AD_Message to be used by users of this implementation.
	 */
	public static final String MSG_ERRORS_FOUND = "de.metas.ordercandidate.spi.impl.OLCandPriceValidator.FoundErrors";

	boolean validate(I_C_OLCand olCand);

	/**
	 * 
	 * @return a thread-local variable that indicates if the current thread is currently validating olCands. Can be used to avoid unnecessary calls to the validation API (from MVs).
	 */
	boolean isValidationProcessInProgress();

	/**
	 * See {@link #isValidationProcessInProgress()}.
	 * 
	 * @param value
	 * @return
	 */
	boolean setValidationProcessInProgress(boolean value);

}
