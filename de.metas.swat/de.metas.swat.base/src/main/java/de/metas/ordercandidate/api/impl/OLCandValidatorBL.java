package de.metas.ordercandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.ArrayList;
import java.util.List;

import de.metas.ordercandidate.api.IOLCandValidatorBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandValidator;

public class OLCandValidatorBL implements IOLCandValidatorBL
{

	private final List<IOLCandValidator> validators = new ArrayList<IOLCandValidator>();

	@Override
	public void registerValidator(final IOLCandValidator olCandValdiator)
	{
		validators.add(olCandValdiator);

	}

	@Override
	public boolean validate(final I_C_OLCand olCand)
	{
		// 08072
		// before validating, unset the isserror and set the error message on null.
		// this way they will be up to date after validation
		olCand.setErrorMsg(null);
		olCand.setIsError(false);

		for (final IOLCandValidator olCandValdiator : validators)
		{
			if (!olCandValdiator.validate(olCand))
			{
				return false;
			}
		}
		return true;
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
