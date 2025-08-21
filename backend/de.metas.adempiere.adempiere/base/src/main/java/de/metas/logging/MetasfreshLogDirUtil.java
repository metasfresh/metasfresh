package de.metas.logging;



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

import lombok.experimental.UtilityClass;
import org.compiere.util.Ini;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliersNotNull;


@UtilityClass
public class MetasfreshLogDirUtil
{
	public String getLogDir()
	{
		return coalesceSuppliersNotNull(
				() -> System.getProperty(LoggingConstants.SYSTEM_PROP_LogDir),
				() -> System.getenv(LoggingConstants.ENV_VAR_LogDir),
				() -> Ini.getMetasfreshHome());
	}
}
