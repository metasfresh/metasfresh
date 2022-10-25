/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.adempiere.ad.expression.api;

import de.metas.i18n.ITranslatableString;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@EqualsAndHashCode(callSuper = true)
public class LogicExpressionResultWithReason extends LogicExpressionResult
{
	@Nullable
	ITranslatableString reason;

	@NonNull
	public static LogicExpressionResultWithReason of(@NonNull final LogicExpressionResult result)
	{
		return new LogicExpressionResultWithReason(result, null);
	}

	public LogicExpressionResultWithReason(@NonNull final LogicExpressionResult result, @Nullable final ITranslatableString reason)
	{
		super(result.getName(), result.booleanValue(), result.getExpression(), result.getUsedParameters());

		this.reason = reason;
	}

	@Nullable
	public String getTranslatedReason(@NonNull final String adLanguage)
	{
		return Optional.ofNullable(reason)
				.map(res -> res.translate(adLanguage))
				.orElse(null);
	}
}
