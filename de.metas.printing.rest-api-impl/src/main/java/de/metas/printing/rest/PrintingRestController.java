package de.metas.printing.rest;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.esb.api.LoginRequest;
import de.metas.printing.esb.api.LoginResponse;
import de.metas.printing.esb.api.PrintJobInstructionsConfirm;
import de.metas.printing.esb.api.PrintPackage;
import de.metas.printing.esb.api.PrintPackageInfo;
import de.metas.printing.esb.api.PrinterHWList;
import de.metas.printing.model.I_AD_PrinterHW;
import de.metas.printing.model.I_AD_PrinterHW_MediaTray;
import de.metas.printing.model.I_C_PrintPackageData;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Package;
import de.metas.printing.model.I_C_Print_PackageInfo;
import de.metas.printing.model.X_C_Print_Job_Instructions;
import de.metas.printing.rpl.requesthandler.CreatePrintPackageRequestHandler;
import lombok.NonNull;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
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

@RestController
@RequestMapping(PrintingRestController.ENDPOINT)
public class PrintingRestController
{
	public static final String ENDPOINT = "/api/printing";

	@Autowired
	private PrinterHWRepo printerHwRepo;

	/**
	 *
	 * @param sessionIdIGNORED
	 * @param loginRequest the request's user and password are ignored.
	 *            They are required for another (legacy) endpoint.
	 * @return
	 */
	@PostMapping("/login/{sessionId}")
	public LoginResponse login(
			@PathVariable("sessionId") int sessionIdIGNORED,
			@RequestBody final LoginRequest loginRequest)
	{
		final MFSession session = Services.get(ISessionBL.class).getCurrentOrCreateNewSession(Env.getCtx());
		session.setHostKey(loginRequest.getHostKey(), Env.getCtx());

		final LoginResponse loginResponse = new LoginResponse();
		loginResponse.setHostKey(loginRequest.getHostKey());
		loginResponse.setSessionId(Integer.toString(session.getAD_Session_ID()));
		loginResponse.setUsername(loginRequest.getUsername());

		return loginResponse;
	}

	@PostMapping("/addPrinterHW/session/{sessionId}")
	public ResponseEntity<?> addPrinterHW(
			@PathVariable("sessionId") final int sessionId,
			@RequestBody final PrinterHWList printerHWList)
	{
		final String hostKey = updateCtxAndRetrieveHostKey(sessionId);

		assertCanAddPrinters();
		printerHwRepo.createOrUpdatePrinters(hostKey, printerHWList);

		return ResponseEntity.ok().build();
	}

	private String updateCtxAndRetrieveHostKey(final int sessionId)
	{
		final MFSession sessionById = Services.get(ISessionBL.class).getSessionById(Env.getCtx(), sessionId);
		sessionById.updateContext(Env.getCtx());

		return sessionById.getOrCreateHostKey(Env.getCtx());
	}

	@PostMapping("/getNextPrintPackage/{sessionId}/{transactionId}")
	public PrintPackage getNextPrintPackage(
			@PathVariable("sessionId") int sessionId,
			@PathVariable("transactionId") final String transactionId)
	{
		updateCtxAndRetrieveHostKey(sessionId);

		final I_C_Print_Package requestPrintPackage = newInstance(I_C_Print_Package.class);
		requestPrintPackage.setTransactionID(transactionId);
		final I_C_Print_Package responsePrintPackage = new CreatePrintPackageRequestHandler().createResponse(requestPrintPackage);

		final PrintPackage response;
		if (responsePrintPackage != null)
		{
			save(responsePrintPackage);
			response = createResponseFromPrintPackage(responsePrintPackage);
			response.setTransactionId(transactionId);

		}
		else // create and return an empty response
		{
			response = new PrintPackage();
			response.setTransactionId(transactionId);
		}

		return response;
	}

	private PrintPackage createResponseFromPrintPackage(
			@NonNull final I_C_Print_Package responsePrintPackage)
	{
		final PrintPackage response = new PrintPackage();

		response.setCopies(responsePrintPackage.getCopies());
		response.setFormat(responsePrintPackage.getBinaryFormat());
		response.setPageCount(responsePrintPackage.getPageCount());
		response.setPrintJobInstructionsID(Integer.toString(responsePrintPackage.getC_Print_Job_Instructions_ID()));
		response.setPrintPackageId(Integer.toString(responsePrintPackage.getC_Print_Package_ID()));

		final List<PrintPackageInfo> printPackageInfos = new ArrayList<>();

		final List<I_C_Print_PackageInfo> printPackageInfoRecords = //
				Services.get(IPrintingDAO.class).retrievePrintPackageInfos(responsePrintPackage);

		for (final I_C_Print_PackageInfo printPackageInfoRecord : printPackageInfoRecords)
		{
			final PrintPackageInfo printPackageInfo = new PrintPackageInfo();
			printPackageInfo.setCalX(printPackageInfoRecord.getCalX());
			printPackageInfo.setCalY(printPackageInfoRecord.getCalY());
			printPackageInfo.setPageFrom(printPackageInfoRecord.getPageFrom());
			printPackageInfo.setPageTo(printPackageInfoRecord.getPageTo());

			final I_AD_PrinterHW printerHwRecord = printPackageInfoRecord.getAD_PrinterHW();
			printPackageInfo.setPrintService(printerHwRecord.getName());

			if (printPackageInfoRecord.getAD_PrinterHW_MediaTray_ID() > 0)
			{
				final I_AD_PrinterHW_MediaTray printerHwMediaTray = printPackageInfoRecord.getAD_PrinterHW_MediaTray();
				printPackageInfo.setTray(printerHwMediaTray.getName());
				printPackageInfo.setTrayNumber(printerHwMediaTray.getTrayNumber());
			}
			else
			{
				printPackageInfo.setTrayNumber(-1);
			}
			printPackageInfos.add(printPackageInfo);
		}
		response.setPrintPackageInfos(printPackageInfos);
		return response;
	}

	/**
	 * Returns a binary stream of data to be printed.
	 */
	@PostMapping("/getPrintPackageData/{sessionId}/{transactionId}")
	public ResponseEntity<?> getPrintPackageData(
			@PathVariable("sessionId") int sessionId,
			@PathVariable("transactionId") String transactionId)
	{
		updateCtxAndRetrieveHostKey(sessionId);

		final I_C_Print_Package printPackage = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Print_Package.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Print_Package.COLUMN_TransactionID, transactionId)
				.create()
				.firstOnly(I_C_Print_Package.class);

		final I_C_PrintPackageData data = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PrintPackageData.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PrintPackageData.COLUMN_C_Print_Package_ID, printPackage.getC_Print_Package_ID())
				.create()
				.firstOnly(I_C_PrintPackageData.class);

		return ResponseEntity.ok(data.getPrintData());
	}

	/**
	 * Sends feedback regarding the print job to ADempiere.
	 */
	@PostMapping("/sendPrintPackageResponse/{sessionId}/{transactionId}")
	public void sendPrintPackageResponse(
			@PathVariable("sessionId") int sessionId,
			@PathVariable("transactionId") String transactionId,
			@RequestBody final PrintJobInstructionsConfirm input)
	{
		updateCtxAndRetrieveHostKey(sessionId);

		final I_C_Print_Job_Instructions printJobInstructions = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Print_Job_Instructions.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_C_Print_Job_Instructions.COLUMN_C_Print_Job_Instructions_ID,
						Integer.parseInt(input.getPrintJobInstructionsID()))
				.create()
				.firstOnly(I_C_Print_Job_Instructions.class);

		switch (input.getStatus())
		{
			case Gedruckt:
				printJobInstructions.setStatus(X_C_Print_Job_Instructions.STATUS_Done);
				break;
			case Druckfehler:
				printJobInstructions.setStatus(X_C_Print_Job_Instructions.STATUS_Error);
				break;
			case Im_Druck:
				printJobInstructions.setStatus(X_C_Print_Job_Instructions.STATUS_Send);
				break;
			case Wartet_auf_druck:
				printJobInstructions.setStatus(X_C_Print_Job_Instructions.STATUS_Pending);
				break;
			default:
				throw new IllegalArgumentException("Invalid response status: " + input.getStatus());
		}
		printJobInstructions.setErrorMsg(input.getErrorMsg());
		save(printJobInstructions);
	}

	private void assertCanAddPrinters()
	{
		final IUserRolePermissions userPermissions = Env.getUserRolePermissions();
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		final int adTableId = InterfaceWrapperHelper.getTableId(I_AD_PrinterHW.class);
		final int recordId = -1; // NEW
		final String errmsg = userPermissions.checkCanUpdate(adClientId, adOrgId, adTableId, recordId);
		if (errmsg != null)
		{
			throw new AdempiereException(errmsg);
		}
	}
}
