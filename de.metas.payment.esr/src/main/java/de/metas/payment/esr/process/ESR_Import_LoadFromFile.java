package de.metas.payment.esr.process;

/*
 * #%L
 * de.metas.payment.esr
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


import java.util.Iterator;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

/**
 * Creates ESR import lines for the given V11 file and matches them against system invoices and bPartners.
 * 
 * @author ts
 * 
 */
public class ESR_Import_LoadFromFile extends SvrProcess
{
	// services
	private final transient IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient IESRImportBL esrImportBL = Services.get(IESRImportBL.class);
	private final transient IESRImportDAO esrImportDAO = Services.get(IESRImportDAO.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);

	// Parameters
	private int p_ESR_Import_ID;
	private String p_FileName;
	private String p_AsyncBatchName = "ESR Import";
	private String p_AsyncBatchDesc = "ESR Import process";

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			String name = para.getParameterName();
			if (para.getParameter() == null)
				;
			else if ("FileName".equals(name))
			{
				p_FileName = (String)para.getParameter();
			}
			else if (I_ESR_Import.COLUMNNAME_ESR_Import_ID.equals(name))
			{
				p_ESR_Import_ID = para.getParameterAsInt();
			}
			 else if (I_C_Async_Batch.COLUMNNAME_Name.equals(name))
			 {
			 p_AsyncBatchName = (String)para.getParameter();
			 }
			 else if (I_C_Async_Batch.COLUMNNAME_Description.equals(name))
			 {
			 p_AsyncBatchDesc = (String)para.getParameter();
			 }
		}

		if (I_ESR_Import.Table_Name.equals(getTableName()))
		{
			p_ESR_Import_ID = getRecord_ID();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (p_ESR_Import_ID <= 0)
		{
			throw new FillMandatoryException(I_ESR_Import.COLUMNNAME_ESR_Import_ID);
		}

		//
		// Create Async Batch for tracking
		final I_C_Async_Batch asyncBatch = asyncBatchBL.newAsyncBatch()
				.setContext(getCtx())
				.setC_Async_Batch_Type(ESRConstants.C_Async_Batch_InternalName)
				.setAD_PInstance_Creator_ID(getAD_PInstance_ID())
				.setName(p_AsyncBatchName)
				.setDescription(p_AsyncBatchDesc)
				.build();

		final I_ESR_Import esrImport = InterfaceWrapperHelper.create(getCtx(), p_ESR_Import_ID, I_ESR_Import.class, get_TrxName());

		final String preventDuplicates = sysConfigBL.getValue(ESRConstants.SYSCONFIG_PreventDuplicateImportFiles);

		final String esrHash = esrImportBL.getMD5Checksum(p_FileName);

		if (preventDuplicates == null)
		{
			// the sys config not defined. Functionality to work as before
		}

		else
		{

			final Properties ctx = InterfaceWrapperHelper.getCtx(esrImport);

			final Iterator<I_ESR_Import> esrImports = esrImportDAO.retrieveESRImports(ctx, esrImport.getAD_Org_ID());

			// will turn true if another identical hash was seen in the list of esr imports
			boolean seen = false;

			while (esrImports.hasNext())
			{

				if (esrHash.equals(esrImports.next().getHash()))
				{
					seen = true;
					break;
				}
			}

			if (seen)
			{
				if ("W".equalsIgnoreCase(preventDuplicates))
				{
					// when sys config is set to prevent Duplicates with warning then show the user a warning with yes and no

					final boolean answer = clientUI.ask(Env.WINDOW_MAIN, ESRConstants.ASK_PreventDuplicateImportFiles);

					if (answer == false)
					{
						// If the user doesn't want to re-import the file, the process stops here
						return "File not imported";
					}

				}
				else if ("E".equalsIgnoreCase(preventDuplicates))
				{
					// when sys config is set to prevent Duplicates entirely then show the user a warning with OK button

					clientUI.warn(Env.WINDOW_MAIN, ESRConstants.WARN_PreventDuplicateImportFilesEntirely);
					return "File not imported";
				}
				else
				{
					throw new AdempiereException("Sysconfig " + ESRConstants.SYSCONFIG_PreventDuplicateImportFiles + " must be either W or E");
				}
			}
		}

		// Set the hash of the file in the esr header
		esrImport.setHash(esrHash);

		InterfaceWrapperHelper.save(esrImport);

		// 04582: making sure we will use the trxName of this process in our business logic
		Check.assume(get_TrxName().equals(InterfaceWrapperHelper.getTrxName(esrImport)), "TrxName {} of {} is equal to the process-TrxName {}",
				InterfaceWrapperHelper.getTrxName(esrImport),
				esrImport,
				get_TrxName());

		esrImportBL.loadESRImportFile(esrImport, p_FileName, asyncBatch);

		return MSG_OK;
	}
}
