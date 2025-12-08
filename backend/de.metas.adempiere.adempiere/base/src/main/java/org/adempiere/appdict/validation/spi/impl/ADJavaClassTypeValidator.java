package org.adempiere.appdict.validation.spi.impl;

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

import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.appdict.validation.api.IADValidatorViolation;
import org.adempiere.appdict.validation.spi.AbstractADValidator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Util;

public class ADJavaClassTypeValidator extends AbstractADValidator<I_AD_JavaClass_Type>
{

	@Override
	public String getLogMessage(@NonNull final IADValidatorViolation violation)
	{
		final StringBuilder message = new StringBuilder();
		try
		{
			final I_AD_JavaClass_Type javaClassType = InterfaceWrapperHelper.create(violation.getItem(), I_AD_JavaClass_Type.class);

			message.append("Error on ").append(javaClassType).append(" (IsActive=").append(javaClassType.isActive()).append("): ");
		}
		catch(final Exception e)
		{
			message.append("Error (InterfaceWrapperHelper exception: ").append(e.getLocalizedMessage()).append(") on ").append(violation.getItem()).append(": ");
		}

		message.append(violation.getError().getLocalizedMessage());

		return message.toString();
	}

	@Override
	public Class<I_AD_JavaClass_Type> getType()
	{
		return I_AD_JavaClass_Type.class;
	}

	@Override
	public void validate(@NonNull final I_AD_JavaClass_Type item)
	{
		if (Check.isBlank(item.getClassname()))
		{
			return;
		}

		Util.validateJavaClassname(item.getClassname(), null);
	}

}
