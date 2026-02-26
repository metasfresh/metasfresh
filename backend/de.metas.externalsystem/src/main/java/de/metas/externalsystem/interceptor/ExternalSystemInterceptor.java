/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.interceptor;

import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalsystem.model.I_ExternalSystem;
import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Interceptor(I_ExternalSystem.class)
@Component
public class ExternalSystemInterceptor
{
	private static final AdMessageKey ERR_INVALID_EXTERNAL_SYSTEM_VALUE = AdMessageKey.of("ERR_INVALID_EXTERNAL_SYSTEM_VALUE");

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_ExternalSystem.COLUMNNAME_Value)
	public void validateValue(@NonNull final I_ExternalSystem externalSystemRecord)
	{
		final String value = externalSystemRecord.getValue();

		final Matcher matcher = ExternalIdentifier.Type.EXTERNAL_REFERENCE.getPattern().matcher("ext-" + value + "-dummy");
		if (!matcher.matches() || !value.equals(matcher.group(1)))
		{
			throw new AdempiereException(ERR_INVALID_EXTERNAL_SYSTEM_VALUE);
		}
	}
}
