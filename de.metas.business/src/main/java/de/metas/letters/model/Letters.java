package de.metas.letters.model;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.business
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

@UtilityClass
public final class Letters
{
	public static final String MSG_Letter = "de.metas.letters.Letter";

	public static boolean isEnabled()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue("de.metas.letters.enabled", Boolean.FALSE);
	}
}
