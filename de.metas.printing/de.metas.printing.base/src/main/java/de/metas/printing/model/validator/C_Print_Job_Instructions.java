package de.metas.printing.model.validator;

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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_User;
import org.compiere.model.ModelValidator;

import de.metas.i18n.IMsgBL;
import de.metas.notification.INotificationBL;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Print_Job_Instructions;
import lombok.NonNull;

@Validator(I_C_Print_Job_Instructions.class)
public class C_Print_Job_Instructions
{

	private static final String MSG_CLIENT_REPORTS_PRINT_ERROR = "de.metas.printing.C_Print_Job_Instructions.ClientReportsPrintError";
	private static final String MSG_CLIENT_PRINT_TIMEOUT = "de.metas.printing.C_Print_Job_Instructions.ClientStatusTimeOut";
	private static final String MSG_CLIENT_PRINT_TIMEOUT_DETAILS = "de.metas.printing.C_Print_Job_Instructions.ClientStatusTimeOutDetails";

	private static final String SYSCONFIG_NOTIFY_PRINT_RECEIVER_ON_ERROR = "de.metas.printing.C_Print_Job_Instructions.NotifyPrintReceiverOnError";
	private static final String SYSCONFIG_NOTIFY_PRINT_RECEIVER_SEND_TIMEOUT_SECONDS = "de.metas.printing.C_Print_Job_Instructions.NotifyPrintReceiverOnSendTimeoutSeconds";
	private static final String SYSCONFIG_NOTIFY_PRINT_RECEIVER_PENDING_TIMEOUT_SECONDS = "de.metas.printing.C_Print_Job_Instructions.NotifyPrintReceiverOnPendingTimeoutSeconds";

	/**
	 * Create Document Outbound only if Status column just changed to Done
	 *
	 * @param jobInstructions
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_CHANGE_REPLICATION }, ifColumnsChanged = I_C_Print_Job_Instructions.COLUMNNAME_Status)
	public void logDocOutbound(final I_C_Print_Job_Instructions jobInstructions)
	{
		// We log the doc outbound only when Status changed to Done
		if (!X_C_Print_Job_Instructions.STATUS_Done.equals(jobInstructions.getStatus()))
		{
			return;
		}

		final I_AD_User userToPrint = jobInstructions.getAD_User_ToPrint();
		final Iterator<I_C_Print_Job_Line> lines = Services.get(IPrintingDAO.class).retrievePrintJobLines(jobInstructions);
		for (final I_C_Print_Job_Line line : IteratorUtils.asIterable(lines))
		{
			logDocOutbound(line, userToPrint);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_CHANGE_REPLICATION }, ifColumnsChanged = I_C_Print_Job_Instructions.COLUMNNAME_Status)
	public void createNoticeOnError(final I_C_Print_Job_Instructions jobInstructions)
	{
		if (!X_C_Print_Job_Instructions.STATUS_Error.equals(jobInstructions.getStatus()))
		{
			return;
		}

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final INotificationBL notificationBL = Services.get(INotificationBL.class);

		final boolean notifyUser = sysConfigBL.getBooleanValue(SYSCONFIG_NOTIFY_PRINT_RECEIVER_ON_ERROR,
				false, // default
				jobInstructions.getAD_Client_ID(),
				jobInstructions.getAD_Org_ID());
		if (notifyUser)
		{
			final int printJobInstructionsID = jobInstructions.getC_Print_Job_Instructions_ID();
			final int userToPrintID = jobInstructions.getAD_User_ToPrint_ID();
			final Properties ctx = InterfaceWrapperHelper.getCtx(jobInstructions);
			final String trxName = InterfaceWrapperHelper.getTrxName(jobInstructions);

			// do the notification after commit, because e.g. if we send a mail, and even if that fails, we don't want this method to fail.
			final ITrxManager trxManager = Services.get(ITrxManager.class);

			trxManager.getTrxListenerManagerOrAutoCommit(trxName)
					.newEventListener(TrxEventTiming.AFTER_COMMIT)
					.registerHandlingMethod(innerTrx -> {

						final IPair<I_C_Print_Job_Instructions, I_AD_User> reloadRecords = reloadRecords(ctx, printJobInstructionsID, userToPrintID);
						final I_C_Print_Job_Instructions printJobInstructionsReloaded = reloadRecords.getLeft();
						final I_AD_User userToPrintReloaded = reloadRecords.getRight();

						notificationBL.notifyUser(
								userToPrintReloaded,
								MSG_CLIENT_REPORTS_PRINT_ERROR,
								printJobInstructionsReloaded.getErrorMsg(),
								TableRecordReference.of(printJobInstructionsReloaded));
					});
		}
	}

	/**
	 * If the status of the given <code>jobInstructions</code> is "pending" or send", this method can check via {@link ITaskExecutorService#schedule(Callable, int, TimeUnit, String)} whether the
	 * status is unchanged after <code>n</code> seconds or not.
	 * <p>
	 * The goal is to notify users about timeout problems with the printing client.
	 *
	 * @param jobInstructions
	 * @task http://dewiki908/mediawiki/index.php/09618_Bestellkontrolle_Druck_Probleme_%28106933593952%29
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_NEW_REPLICATION,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_AFTER_CHANGE_REPLICATION }, ifColumnsChanged = I_C_Print_Job_Instructions.COLUMNNAME_Status)
	public void scheduleCheckForStatustimeout(final I_C_Print_Job_Instructions jobInstructions)
	{
		final String sysconfigName;
		if (X_C_Print_Job_Instructions.STATUS_Pending.equals(jobInstructions.getStatus()))
		{
			sysconfigName = SYSCONFIG_NOTIFY_PRINT_RECEIVER_PENDING_TIMEOUT_SECONDS;
		}
		else if (X_C_Print_Job_Instructions.STATUS_Send.equals(jobInstructions.getStatus()))
		{
			sysconfigName = SYSCONFIG_NOTIFY_PRINT_RECEIVER_SEND_TIMEOUT_SECONDS;
		}
		else
		{
			return; // nothing to do
		}

		scheduleTimeoutCheck(jobInstructions, sysconfigName);
	}

	/**
	 * Checks the given <code>sysConfigName</code> for a timeout value <code>n</code>. If the value is greater than zero, it scheduled a job to be run in <code>n</code> seconds. When the scheduled job
	 * is executed, it will check if the status of the given <code>jobInstructions</code> is still unchanged.<br>
	 * If that is the case, the print-receiver will be notified, because in this case something is wrong with the printing client.<br>
	 * If the integer value of the given <code>sysConfigName</code> is less or equal to zero, then the method does nothing.
	 *
	 * @param jobInstructions
	 * @param sysconfigName
	 * @task http://dewiki908/mediawiki/index.php/09618_Bestellkontrolle_Druck_Probleme_%28106933593952%29
	 */
	private void scheduleTimeoutCheck(
			@NonNull final I_C_Print_Job_Instructions jobInstructions,
			@NonNull final String sysconfigName)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final ITaskExecutorService taskExecutorService = Services.get(ITaskExecutorService.class);
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final int printTimeOutSeconds = sysConfigBL.getIntValue(sysconfigName,
				-1, // default
				jobInstructions.getAD_Client_ID(),
				jobInstructions.getAD_Org_ID());

		if (printTimeOutSeconds <= 0)
		{
			return; // nothing to do
		}

		final int printJobInstructionsID = jobInstructions.getC_Print_Job_Instructions_ID();
		final int userToPrintID = jobInstructions.getAD_User_ToPrint_ID();
		final String status = jobInstructions.getStatus();
		final Properties ctx = InterfaceWrapperHelper.getCtx(jobInstructions);
		final String trxName = InterfaceWrapperHelper.getTrxName(jobInstructions);

		// note: despite the fact that we only will check in the future,
		// we still won't schedule that check before the current trx is committed.
		// that is because we want to be on the safe side, even if
		// the timeout is e.g. just one second and there is a unexpected delay with the commit of the given 'jobInstructions'.
		trxManager.getTrxListenerManagerOrAutoCommit(trxName)
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(innerTrx -> {

					// schedule our check to be run after 'printTimeOutSeconds' seconds
					taskExecutorService.schedule(
							new Callable<Void>()
							{
								@Override
								public Void call() throws Exception
								{
									final INotificationBL notificationBL = Services.get(INotificationBL.class);
									final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
									final IMsgBL msgBL = Services.get(IMsgBL.class);

									final IPair<I_C_Print_Job_Instructions, I_AD_User> reloadRecords = reloadRecords(ctx, printJobInstructionsID, userToPrintID);
									final I_C_Print_Job_Instructions printJobInstructionsReloaded = reloadRecords.getLeft();
									final I_AD_User userToPrintReloaded = reloadRecords.getRight();

									if (status.equals(printJobInstructionsReloaded.getStatus()))
									{
										// the status is still unchanged after the specified timeout => notify the user
										final String statusName = adReferenceDAO.retrieveListNameTrl(ctx, X_C_Print_Job_Instructions.STATUS_AD_Reference_ID, status);
										final String timeoutMsg = msgBL.getMsg(ctx, MSG_CLIENT_PRINT_TIMEOUT_DETAILS, new Object[] { printTimeOutSeconds, statusName });

										notificationBL.notifyUser(
												userToPrintReloaded,
												MSG_CLIENT_PRINT_TIMEOUT,
												timeoutMsg,
												TableRecordReference.of(printJobInstructionsReloaded));
									}
									return null;
								}
							},
							printTimeOutSeconds,
							TimeUnit.SECONDS,
							C_Print_Job_Instructions.class.getSimpleName());
				});
	}

	/**
	 * Reload the records. This method is supposed to be called from within the after-trx-commit listeners.<br>
	 * Note that we don't want to hold a reference to a record from the model interceptor method in there.
	 *
	 * @param ctx
	 * @param printJobInstructionsID
	 * @param userToPrintID
	 * @return
	 */
	private IPair<I_C_Print_Job_Instructions, I_AD_User> reloadRecords(final Properties ctx,
			final int printJobInstructionsID,
			final int userToPrintID)
	{
		final I_C_Print_Job_Instructions printJobInstructionsReloaded = InterfaceWrapperHelper.create(ctx,
				printJobInstructionsID,
				I_C_Print_Job_Instructions.class,
				ITrx.TRXNAME_None);

		final I_AD_User userToPrintReloaded = InterfaceWrapperHelper.create(ctx,
				userToPrintID,
				I_AD_User.class,
				ITrx.TRXNAME_None);

		return ImmutablePair.of(printJobInstructionsReloaded, userToPrintReloaded);
	}

	private void logDocOutbound(final I_C_Print_Job_Line line, final I_AD_User userToPrint)
	{
		final Set<String> printerNames = new HashSet<String>();
		for (final I_C_Print_Job_Detail detail : Services.get(IPrintingDAO.class).retrievePrintJobDetails(line))
		{
			final I_AD_PrinterRouting routing = detail.getAD_PrinterRouting();
			final String printerName = routing.getAD_Printer().getPrinterName();

			if (!printerNames.add(printerName))
			{
				continue; // we already added a log record for this printer name
			}

			final I_C_Printing_Queue queueItem = line.getC_Printing_Queue();
			final I_AD_Archive archive = queueItem.getAD_Archive();

			Services.get(IArchiveEventManager.class).firePrintOut(archive,
					userToPrint,
					printerName,
					IArchiveEventManager.COPIES_ONE,
					IArchiveEventManager.STATUS_Success);
		}
	}
}
