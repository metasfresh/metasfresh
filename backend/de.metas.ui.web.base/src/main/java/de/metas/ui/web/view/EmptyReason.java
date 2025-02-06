/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.view;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class EmptyReason
{
	@NonNull ITranslatableString text;
	@NonNull ITranslatableString hint;

	private EmptyReason(
			@NonNull final ITranslatableString text,
			@NonNull final ITranslatableString hint)
	{
		this.text = text;
		this.hint = hint;
	}

	public static EmptyReason of(
			@Nullable final ITranslatableString text,
			@Nullable final ITranslatableString hint)
	{
		final ITranslatableString textNorm = TranslatableStrings.nullToEmpty(text);
		final ITranslatableString hintNorm = TranslatableStrings.nullToEmpty(hint);
		return new EmptyReason(textNorm, hintNorm);
	}
}
