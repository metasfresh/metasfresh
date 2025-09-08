package de.metas.event.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.event.model.I_AD_EventLog;
import de.metas.event.model.I_AD_EventLog_Entry;
import de.metas.event.model.X_AD_EventLog;
import de.metas.event.model.X_AD_EventLog_Entry;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.persistence.TableModelClassLoader;

import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class Main extends AbstractModuleInterceptor
{
	public static final Main INSTANCE = new Main();

	private Main()
	{
	}

	@Override
	protected void onAfterInit()
	{
		TableModelClassLoader.instance.registerSpecialClassName(I_AD_EventLog.Table_Name, X_AD_EventLog.class.getName());
		TableModelClassLoader.instance.registerSpecialClassName(I_AD_EventLog_Entry.Table_Name, X_AD_EventLog_Entry.class.getName());
	}

	@Override
	protected Set<String> getTableNamesToSkipOnMigrationScriptsLogging()
	{
		return ImmutableSet.of(
				I_AD_EventLog.Table_Name,
				I_AD_EventLog_Entry.Table_Name
		);
	}
}
