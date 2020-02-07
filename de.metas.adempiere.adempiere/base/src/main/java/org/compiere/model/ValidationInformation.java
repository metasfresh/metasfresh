/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

package org.compiere.model;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import de.metas.util.Check;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.NonNull;
import lombok.Value;

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ValidationInformation
{
	private String validationMessage;
	private String validationQuestionMessageYES;
	private String validationQuestionMessageNO;

	public ValidationInformation(final String validationMessage, final String validationQuestionMessageYES, final String validationQuestionMessageNO)
	{
		this.validationMessage = validationMessage;
		this.validationQuestionMessageYES = validationQuestionMessageYES;
		this.validationQuestionMessageNO = validationQuestionMessageNO;
	}

	public String getValidationMessage()
	{
		return validationMessage;
	}

	public String getValidationQuestionMessageYES()
	{
		return validationQuestionMessageYES;
	}

	public String getValidationQuestionMessageNO()
	{
		return validationQuestionMessageNO;
	}
}
