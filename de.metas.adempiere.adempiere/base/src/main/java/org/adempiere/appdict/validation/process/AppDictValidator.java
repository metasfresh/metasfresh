package org.adempiere.appdict.validation.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.List;

import org.adempiere.appdict.validation.api.IADValidatorRegistryBL;
import org.adempiere.appdict.validation.api.IADValidatorResult;
import org.adempiere.appdict.validation.api.IADValidatorViolation;
import org.adempiere.appdict.validation.spi.IADValidator;
import org.adempiere.util.Services;

import de.metas.process.JavaProcess;

/**
 * 
 * @author tsa
 */
public class AppDictValidator extends JavaProcess
{
	@Override
	protected void prepare()
	{
		return;
	}

	@Override
	protected String doIt() throws Exception
	{
		final IADValidatorRegistryBL validatorRegistry = Services.get(IADValidatorRegistryBL.class);

		final List<Class<?>> registeredClasses = validatorRegistry.getRegisteredClasses();

		for (final Class<?> registeredClass : registeredClasses)
		{
			final IADValidatorResult errorLog = validatorRegistry.validate(getCtx(), registeredClass);

			logAllExceptions(errorLog, validatorRegistry.getValidator(registeredClass));
		}

		return "OK";
	}

	private void logAllExceptions(final IADValidatorResult errorLog, final IADValidator<?> validator)
	{
		for (final IADValidatorViolation violation : errorLog.getViolations())
		{
			addLog(validator.getLogMessage(violation));
		}
	}
}
