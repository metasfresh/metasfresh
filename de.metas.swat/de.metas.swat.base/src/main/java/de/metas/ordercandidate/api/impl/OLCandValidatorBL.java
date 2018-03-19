package de.metas.ordercandidate.api.impl;

import org.compiere.Adempiere;

import de.metas.ordercandidate.api.IOLCandValidatorBL;
import de.metas.ordercandidate.api.OLCandRegistry;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandValidator;

public class OLCandValidatorBL implements IOLCandValidatorBL
{
	@Override
	public boolean validate(final I_C_OLCand olCand)
	{
		final OLCandRegistry olCandRegistry = Adempiere.getBean(OLCandRegistry.class);
		final IOLCandValidator validators = olCandRegistry.getValidators();
		
		// 08072
		// before validating, unset the isserror and set the error message on null.
		// this way they will be up to date after validation
		olCand.setErrorMsg(null);
		olCand.setIsError(false);

		return validators.validate(olCand);
	}

	private final ThreadLocal<Boolean> validationProcessInProgress = new ThreadLocal<Boolean>()
	{
		@Override
		protected Boolean initialValue()
		{
			return false;
		};
	};

	@Override
	public boolean isValidationProcessInProgress()
	{
		final Boolean isUpdateProcess = validationProcessInProgress.get();

		return isUpdateProcess == null
				? false
				: isUpdateProcess.booleanValue();
	}

	@Override
	public boolean setValidationProcessInProgress(final boolean value)
	{
		final Boolean isUpdateProcess = isValidationProcessInProgress();
		validationProcessInProgress.set(value);

		return isUpdateProcess;
	}
}
