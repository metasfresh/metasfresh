package de.metas.dlm.connection;

import java.sql.Connection;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.util.DB;

import de.metas.connection.IConnectionCustomizer;

/*
 * #%L
 * metasfresh-dlm
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

public class DLMPermanentSysConfigCustomizer extends AbstractDLMCustomizer
{
	public static IConnectionCustomizer PERMANENT_INSTANCE = new DLMPermanentSysConfigCustomizer();

	private DLMPermanentSysConfigCustomizer()
	{
	}

	@Override
	public int getDlmLevel(final Connection c)
	{
		if (DB.isConnected())
		{
			try (final AutoCloseable autoClosable = DB.getDatabase().setCachedCollectionOneTime(c))
			{
				return Services.get(ISysConfigBL.class).getIntValue("de.metas.dlm.DLM_Level", 0);
			}
			catch (Exception e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		}

		return 0;
	}

	@Override
	public int getDlmCoalesceLevel(final Connection c)
	{
		if (DB.isConnected())
		{
			try (final AutoCloseable autoClosable = DB.getDatabase().setCachedCollectionOneTime(c))
			{
				return Services.get(ISysConfigBL.class).getIntValue("de.metas.dlm.DLM_Coalesce_Level", 2);
			}
			catch (Exception e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		}
		return 0;
	}

}
