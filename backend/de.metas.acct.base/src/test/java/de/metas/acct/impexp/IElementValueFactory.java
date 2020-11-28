package de.metas.acct.impexp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_I_ElementValue;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Fluent {@link I_I_ELementValue} factory
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Builder
@Value
/* package */class IElementValueFactory
{

	private final String elementName;
	private final String value;
	private final String name;
	private final String parentValue;
	private final String accountType;
	private final String accountSign;
	private final boolean summary;
	private final boolean postActual;
	private final boolean postBudget;
	private final boolean postStatistical;
	private final boolean docControlled;

	public static class IElementValueFactoryBuilder
	{
		public I_I_ElementValue build()
		{
			return createImportRecord();
		}

		private I_I_ElementValue createImportRecord()
		{
			final I_I_ElementValue iElementValue = InterfaceWrapperHelper.newInstance(I_I_ElementValue.class);
			iElementValue.setElementName(elementName);
			iElementValue.setParentValue(parentValue);
			iElementValue.setValue(value);
			iElementValue.setName(name);
			iElementValue.setAccountSign(accountSign);
			iElementValue.setAccountType(accountType);
			iElementValue.setPostActual(postActual);
			iElementValue.setPostBudget(postBudget);
			iElementValue.setPostStatistical(postStatistical);
			iElementValue.setIsDocControlled(docControlled);

			InterfaceWrapperHelper.save(iElementValue);
			return iElementValue;
		}
	}
}
