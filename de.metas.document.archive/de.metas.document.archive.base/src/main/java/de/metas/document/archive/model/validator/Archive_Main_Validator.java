package de.metas.document.archive.model.validator;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.service.IADProcessDAO;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.api.IArchiveStorageFactory.AccessMode;
import org.adempiere.archive.spi.impl.FilesystemArchiveStorage;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.CacheMgt;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.document.archive.process.ExportArchivePDF;
import de.metas.document.archive.spi.impl.DocOutboundArchiveEventListener;
import de.metas.document.archive.spi.impl.RemoteArchiveStorage;

/**
 * Main de.metas.document.document-archive module's entry point
 *
 * @author tsa
 *
 */
public class Archive_Main_Validator implements ModelValidator
{
	private static final transient Logger logger = LogManager.getLogger(Archive_Main_Validator.class);

	private int m_AD_Client_ID = -1;
	private ModelValidationEngine engine;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}
		this.engine = engine;

		//
		// Services
		final IArchiveStorageFactory archiveStorageFactory = Services.get(IArchiveStorageFactory.class);
		final IArchiveEventManager archiveEventManager = Services.get(IArchiveEventManager.class);
		final IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);

		//
		// Register services
		Services.registerService(de.metas.document.archive.api.IDocOutboundDAO.class, new de.metas.document.archive.api.impl.DocOutboundDAO());
		Services.registerService(org.adempiere.archive.api.IArchiveDAO.class, new org.adempiere.archive.api.impl.DocumentArchiveDAO());

		// Register RemoteArchiveStorage
		archiveStorageFactory.registerArchiveStorage(IArchiveStorageFactory.STORAGETYPE_Filesystem, AccessMode.CLIENT, RemoteArchiveStorage.class);

		// NOTE: if we are in developer mode, in most of the cases Remote storage is not accessible but the filesystem storage is on our machine
		if (developerModeBL.isEnabled())
		{
			archiveStorageFactory.registerArchiveStorage(IArchiveStorageFactory.STORAGETYPE_Filesystem, AccessMode.CLIENT, FilesystemArchiveStorage.class);
		}

		archiveEventManager.registerArchiveEventListener(new DocOutboundArchiveEventListener());

		engine.addModelValidator(new C_Doc_Outbound_Config(this), client);
		engine.addModelValidator(new AD_User(), client);
		engine.addModelValidator(new C_BPartner(), client);

		registerArchiveAwareTables();
		
		// task 09417: while we are at it, also make sure that config changes are propagated
		final CacheMgt cacheMgt = CacheMgt.get();
		cacheMgt.enableRemoteCacheInvalidationForTableName(I_C_Doc_Outbound_Config.Table_Name);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null; // nothing to do
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		// shall never reach this point
		throw new IllegalStateException("Not supported: po=" + po + ", type=" + type);
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		// shall never reach this point
		throw new IllegalStateException("Not supported: po=" + po + ", timing=" + timing);
	}

	/**
	 *
	 * @return {@link ModelValidationEngine} which was used on registration
	 */
	public ModelValidationEngine getEngine()
	{
		return engine;
	}

	private void registerArchiveAwareTables()
	{
		final Properties ctx = Env.getCtx();
		final List<I_AD_Column> archiveColumns = new Query(ctx, I_AD_Column.Table_Name, I_AD_Column.COLUMNNAME_ColumnName + "=?", ITrx.TRXNAME_None)
				.setParameters(org.compiere.model.I_AD_Archive.COLUMNNAME_AD_Archive_ID)
				.setOnlyActiveRecords(true)
				.list(I_AD_Column.class);

		if (archiveColumns.isEmpty())
		{
			return;
		}

		//
		// Services
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

		final int processId = adProcessDAO.retriveProcessIdByClassIfUnique(ctx, ExportArchivePDF.class);
		if (processId <= 0)
		{
			final AdempiereException ex = new AdempiereException("No AD_Process_ID found for " + ExportArchivePDF.class);
			Archive_Main_Validator.logger.error(ex.getLocalizedMessage(), ex);
			return;
		}

		for (final I_AD_Column column : archiveColumns)
		{
			if (!DisplayType.isLookup(column.getAD_Reference_ID()))
			{
				continue;
			}

			final int adTableId = column.getAD_Table_ID();
			adProcessDAO.registerTableProcess(adTableId, processId);
		}
	}
}
