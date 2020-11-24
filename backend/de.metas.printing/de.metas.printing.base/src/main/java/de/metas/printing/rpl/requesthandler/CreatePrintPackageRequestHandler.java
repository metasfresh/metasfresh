package de.metas.printing.rpl.requesthandler;

import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.printing.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerCtx;
import org.adempiere.process.rpl.requesthandler.spi.impl.LoadPORequestHandler;
import org.compiere.model.PO;
import org.compiere.util.Util;

import com.google.common.annotations.VisibleForTesting;

import de.metas.lock.api.ILockManager;
import de.metas.printing.api.IPrintClientsBL;
import de.metas.printing.api.IPrintPackageBL;
import de.metas.printing.api.IPrintPackageCtx;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * For a given input print package (request) it is fetching the next print job and populates the print package header, infos and data.
 *
 * If there is no next print job, null PO respose will be returned
 *
 * @author tsa
 *
 */
public class CreatePrintPackageRequestHandler extends LoadPORequestHandler
{
	@Override
	protected PO createResponse(final IReplRequestHandlerCtx ctx, final PO requestPO)
	{
		Check.assume(requestPO != null, "requestPO not null");

		final I_C_Print_Package printPackage = InterfaceWrapperHelper.create(requestPO, I_C_Print_Package.class);
		Check.assume(printPackage != null, "No print package found for {}", requestPO);

		final Properties envCtxToUse = ctx.getEnvCtxToUse();

		// Validate requestPO context: this shall not happen because is already validated by LoadPORequestHandler
		final Properties requestCtx = InterfaceWrapperHelper.getCtx(printPackage);
		Check.assume(Util.same(requestCtx, envCtxToUse), "Invalid context for {}", requestPO);

		final I_C_Print_Package responsePrintPackage = createResponse(printPackage);
		if (responsePrintPackage == null)
		{
			return null;
		}

		final PO responsePO = InterfaceWrapperHelper.getPO(printPackage);
		return responsePO;
	}

	/**
	 * Updates given printPackage request by setting printing informations and generating printing data.
	 *
	 * If there is no data to be encapsulated in print package, null is returned.
	 *
	 * @return {@link I_C_Print_Package} response or null
	 */
	@VisibleForTesting
	public I_C_Print_Package createResponse(final I_C_Print_Package printPackage)
	{
		final Properties envCtxToUse = InterfaceWrapperHelper.getCtx(printPackage);

		// create/update information for Druck-Clients
		final IPrintClientsBL printClientsBL = Services.get(IPrintClientsBL.class);

		printClientsBL
				.createPrintClientsEntry(
						envCtxToUse,
						printClientsBL.getHostKeyOrNull(envCtxToUse));

		boolean packageUpdated = false;
		final I_C_Print_Package responsePrintPackage;
		try
		{
			packageUpdated = updatePrintPackage(printPackage);
		}
		finally
		{
			if (packageUpdated)
			{
				responsePrintPackage = printPackage;
			}
			else
			{
				logger.debug("There was no update on print package. Deleting it");
				InterfaceWrapperHelper.delete(printPackage);
				responsePrintPackage = null;
			}
		}

		return responsePrintPackage;
	}

	private boolean updatePrintPackage(@NonNull final I_C_Print_Package printPackage)
	{
		if (Check.isEmpty(printPackage.getTransactionID()))
		{
			logger.debug("Skip package {} because transactionId is not set");
			return false;
		}

		//
		// Services used
		final IPrintingDAO dao = Services.get(IPrintingDAO.class);
		final IPrintPackageBL printPackageBL = Services.get(IPrintPackageBL.class);
		final ILockManager lockManager = Services.get(ILockManager.class);

		//
		// Context
		final Properties ctx = InterfaceWrapperHelper.getCtx(printPackage);
		final String trxName = InterfaceWrapperHelper.getTrxName(printPackage);
		final IPrintPackageCtx printPackageCtx = printPackageBL.createInitialCtx(ctx);
		printPackageCtx.setTransactionId(printPackage.getTransactionID());

		boolean packageCreated = false;
		int trialCount = 0;
		final int trialMax = 5;
		while (!packageCreated)
		{
			trialCount++;
			if (trialCount > trialMax)
			{
				throw new AdempiereException("To many retries while trying to create the print packages (Max:" + trialMax + ")");
			}

			I_C_Print_Job_Instructions printJobInstructions = null;
			try
			{
				// do the locking and unlocking within one try-finally block

				// printPackage and printJobInstructions need to be in the same trx, because we will use the printJob's trxName when
				// creating print package infos and referencing the print package
				printJobInstructions = dao.retrieveAndLockNextPrintJobInstructions(ctx, trxName);
				if (printJobInstructions == null)
				{
					logger.debug("No next print jobs found");
					return false;
				}
				logger.debug("Considering {}", printJobInstructions);

				// Try creating the print package. Make sure the printjob instructions are unlocked at the end
				if (printPackage.getC_Print_Package_ID() <= 0)
				{
					save(printPackage); // if the package did not enter the system per replication, then it's probably not yet saved
				}
				packageCreated = printPackageBL.addPrintingDataToPrintPackage(printPackage, printJobInstructions, printPackageCtx);

				logger.debug("PackageCreated:{} - {}", new Object[] { packageCreated, printPackage });
			}
			finally
			{
				if (printJobInstructions != null)
				{
					lockManager.unlock(printJobInstructions);
				}
			}
		}

		return packageCreated;
	}

}
