package de.metas.dlm.po;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.persistence.po.INoDataFoundHandler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import de.metas.connection.IConnectionCustomizerService;
import de.metas.dlm.connection.DLMConnectionCustomizer;
import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.IDLMAware;
import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-dlm-base
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

@Immutable
public class UnArchiveRecordHandler implements INoDataFoundHandler
{
	private static final transient Logger logger = LogManager.getLogger(UnArchiveRecordHandler.class);

	public static UnArchiveRecordHandler INSTANCE = new UnArchiveRecordHandler();

	private UnArchiveRecordHandler()
	{
	}

	@Override
	public boolean invoke(String tableName, Object[] ids, IContextAware ctx)
	{
		if (ids == null || ids.length != 1 || ids[0] == null)
		{
			return false;
		}
		if (!(ids[0] instanceof Integer))
		{
			return false;
		}

		final IConnectionCustomizerService connectionCustomizerService = Services.get(IConnectionCustomizerService.class);
		try (final AutoCloseable customizer = connectionCustomizerService.registerTemporaryCustomizer(DLMConnectionCustomizer.seeThemAllCustomizer()))
		{
			final TableRecordReference reference = TableRecordReference.of(tableName, (int)ids[0]);
			final IDLMAware model = reference.getModel(ctx, IDLMAware.class);

			if (model == null)
			{
				logger.info("Unable to load reference={}; nothing more to do.", reference);
				return false;
			}

			logger.info("Setting DLM_Level to {} for {}", IMigratorService.DLM_Level_LIVE, reference);

			model.setDLM_Level(IMigratorService.DLM_Level_LIVE);
			InterfaceWrapperHelper.save(model);
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		return false;
	}

}
