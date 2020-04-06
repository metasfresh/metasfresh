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


import java.lang.reflect.Method;
import java.util.Objects;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.appdict.validation.api.IADValidatorViolation;
import org.adempiere.appdict.validation.spi.AbstractADValidator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_ColumnCallout;

import de.metas.util.Check;
import de.metas.util.Services;

public class ADColumnCalloutADValidator extends AbstractADValidator<I_AD_ColumnCallout>
{
	@Override
	public void validate(I_AD_ColumnCallout callout)
	{
		if (Check.isEmpty(callout.getClassname(), true))
		{
			throw new AdempiereException("No classname specified");
		}

		final String fqMethodName = callout.getClassname();
		final int idx = fqMethodName.lastIndexOf('.');
		final String classname = fqMethodName.substring(0, idx);
		final String methodName = fqMethodName.substring(idx + 1);

		final Method method = validateJavaMethodName(classname, org.compiere.model.Callout.class, methodName);

		// Expected params, variant 1: Properties ctx, int windowNo, GridTab gridTab, GridField gridField, Object value
		final Class<?>[] expectedParams1 = new Class<?>[] { java.util.Properties.class, int.class, GridTab.class, GridField.class, Object.class };
		// Expected params, variant 2: Properties ctx, int windowNo, GridTab gridTab, GridField gridField, Object value, Object valueOld
		final Class<?>[] expectedParams2 = new Class<?>[] { java.util.Properties.class, int.class, GridTab.class, GridField.class, Object.class, Object.class };

		if (!Objects.equals(expectedParams1, method.getParameterTypes())
				&& !Objects.equals(expectedParams2, method.getParameterTypes()))
		{
			throw new AdempiereException("Invalid parameters for callout method " + method);
		}
	}

	@Override
	public String getLogMessage(final IADValidatorViolation violation)
	{
		final StringBuilder message = new StringBuilder();
		try
		{
			final I_AD_ColumnCallout callout = InterfaceWrapperHelper.create(violation.getItem(), I_AD_ColumnCallout.class);

			final I_AD_Column column = callout.getAD_Column();
			final String tableName = Services.get(IADTableDAO.class).retrieveTableName(column.getAD_Table_ID());

			message.append("Error on ").append(tableName).append(".").append(column.getColumnName()).append(" - ").append(callout.getClassname())
					.append(" (IsActive=").append(callout.isActive()).append("): ");
		}
		catch (Exception e)
		{
			message.append("Error (InterfaceWrapperHelper exception: ").append(e.getLocalizedMessage()).append(") on ").append(violation.getItem()).append(": ");
		}

		message.append(violation.getError().getLocalizedMessage());

		return message.toString();
	}

	@Override
	public Class<I_AD_ColumnCallout> getType()
	{
		return I_AD_ColumnCallout.class;
	}
}
