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

package de.metas.externalsystem.scriptedexportconversion.interceptor;

import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableAndClientId;
import org.adempiere.service.ClientId;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ExternalSystemScriptedExportConversionInterceptorProducer
{
	private final Map<AdTableAndClientId, ExternalSystemScriptedExportConversionInterceptor> tableId2ModelInterceptor = new HashMap<>();
	private final ReentrantLock externalSystemScriptedExportConversionLock = new ReentrantLock();

	public void registerInterceptor(@NonNull final ExternalSystemScriptedExportConversionInterceptor interceptor)
	{
		externalSystemScriptedExportConversionLock.lock();
		try
		{
			registerInterceptor0(interceptor);
		}
		finally
		{
			externalSystemScriptedExportConversionLock.unlock();
		}
	}

	public void unregisterInterceptorByTableAndClientId(@NonNull final AdTableAndClientId tableAndClientId)
	{
		externalSystemScriptedExportConversionLock.lock();
		try
		{
			unregisterInterceptorByTableAndClientId0(tableAndClientId);
		}
		finally
		{
			externalSystemScriptedExportConversionLock.unlock();
		}
	}

	private void registerInterceptor0(@NonNull final ExternalSystemScriptedExportConversionInterceptor interceptor)
	{
		final AdTableAndClientId tableAndClientId = AdTableAndClientId.of(interceptor.getTableId(), ClientId.ofRepoId(interceptor.getAD_Client_ID()));
		unregisterInterceptorByTableAndClientId0(tableAndClientId);

		interceptor.init();
		tableId2ModelInterceptor.put(tableAndClientId, interceptor);
	}
	
	private void unregisterInterceptorByTableAndClientId0(@NonNull final AdTableAndClientId tableAndClientId)
	{
		final ExternalSystemScriptedExportConversionInterceptor interceptor = tableId2ModelInterceptor.get(tableAndClientId);
		if (interceptor == null)
		{
			// no interceptor was not registered for given table, nothing to unregister
			return;
		}

		interceptor.destroy();
		tableId2ModelInterceptor.remove(tableAndClientId);
	}
}
