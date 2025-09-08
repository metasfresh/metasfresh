package de.metas.i18n;

import java.util.Set;


/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * A string which can be translated to a given <code>AD_Language</code>.<br>
 * For more helpers, check {@link TranslatableStrings}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface ITranslatableString
{
	String translate(final String adLanguage);

	String getDefaultValue();

	Set<String> getAD_Languages();

	default boolean isTranslatedTo(final String adLanguage)
	{
		return getAD_Languages().contains(adLanguage);
	}
}
