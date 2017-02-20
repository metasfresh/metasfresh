package de.metas.dlm.po;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.persistence.po.INoDataFoundHandler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
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

/**
 * This handler öoads the given record (even if that record is archived) and sets its {@link IDLMAware#COLUMNNAME_DLM_Level} to {@link IMigratorService#DLM_Level_LIVE}.
 * For details, see {@link #invoke(String, Object[], IContextAware)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/986
 */
@Immutable
public class UnArchiveRecordHandler implements INoDataFoundHandler
{
	private static final transient Logger logger = LogManager.getLogger(UnArchiveRecordHandler.class);

	public static UnArchiveRecordHandler INSTANCE = new UnArchiveRecordHandler();

	private UnArchiveRecordHandler()
	{
	}

	/**
	 * This method attempts to load the record using {@link DLMConnectionCustomizer#seeThemAllCustomizer()}. 
	 * If this succeeds and the record's {@code DLM_Level} is not {@link IMigratorService#DLM_Level_LIVE}, it will set the record's level to that value and save the record.
	 *
	 * @param tableName the table name to load from. May be empty or null. In that case, the metohd will return {@code false}.
	 * @param ids the IDs to load. May be {@code null}, not-int or the size might be not equal to one. In those cases, the method returns {@code false}.
	 * @param ctx
	 * @return
	 */
	@Override
	public boolean invoke(final String tableName, final Object[] ids, final IContextAware ctx)
	{
		// do some basic checking
		if (Check.isEmpty(tableName, true))
		{
			return false;
		}
		if (ids == null || ids.length != 1 || ids[0] == null)
		{
			return false;
		}
		if (!(ids[0] instanceof Integer))
		{
			return false;
		}

		// attempt to load the record
		final IConnectionCustomizerService connectionCustomizerService = Services.get(IConnectionCustomizerService.class);
		try (final AutoCloseable customizer = connectionCustomizerService.registerTemporaryCustomizer(DLMConnectionCustomizer.seeThemAllCustomizer()))
		{
			final TableRecordReference reference = TableRecordReference.of(tableName, (int)ids[0]);
			final IDLMAware model = reference.getModel(ctx, IDLMAware.class);

			if (model == null)
			{
				logger.info("Unable to load record for reference={}; returning false.", reference);
				return false;
			}

			if (model.getDLM_Level() == IMigratorService.DLM_Level_LIVE)
			{
				logger.info("The record could be loaded, but already had DLM_Level={}; returning false; reference={}; ", IMigratorService.DLM_Level_LIVE, reference);
				return false;
			}

			logger.info("Setting DLM_Level to {} for {}", IMigratorService.DLM_Level_LIVE, reference);

			model.setDLM_Level(IMigratorService.DLM_Level_LIVE);
			InterfaceWrapperHelper.save(model);
			return true;
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
