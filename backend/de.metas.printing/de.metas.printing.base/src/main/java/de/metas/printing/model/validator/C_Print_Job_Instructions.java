package de.metas.printing.model.validator;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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

import de.metas.ad_reference.ADReferenceService;
import de.metas.i18n.AdMessageKey;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.archive.api.ArchivePrintOutStatus;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.ModelValidator;

import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Print_Job_Instructions;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

@Validator(I_C_Print_Job_Instructions.class)
public class C_Print_Job_Instructions
{

	private static final AdMessageKey MSG_CLIENT_REPORTS_PRINT_ERROR = AdMessageKey.of("de.metas.printing.C_Print_Job_Instructions.ClientReportsPrintError");
	private static final AdMessageKey MSG_CLIENT_PRINT_TIMEOUT = AdMessageKey.of("de.metas.printing.C_Print_Job_Instructions.ClientStatusTimeOut");
	private static final AdMessageKey MSG_CLIENT_PRINT_TIMEOUT_DETAILS = AdMessageKey.of("de.metas.printing.C_Print_Job_Instructions.ClientStatusTimeOutDetails");

	private static final String SYSCONFIG_NOTIFY_PRINT_RECEIVER_ON_ERROR = "de.metas.printing.C_Print_Job_Instructions.NotifyPrintReceiverOnError";
	private static final String SYSCONFIG_NOTIFY_PRINT_RECEIVER_SEND_TIMEOUT_SECONDS = "de.metas.printing.C_Print_Job_Instructions.NotifyPrintReceiverOnSendTimeoutSeconds";
	private static final String SYSCONFIG_NOTIFY_PRINT_RECEIVER_PENDING_TIMEOUT_SECONDS = "de.metas.printing.C_Print_Job_Instructions.NotifyPrintReceiverOnPendingTimeoutSeconds";

	private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	private final ADReferenceService adReferenceService = ADReferenceService.get();

	/**
	 * Create Document Outbound only if Status column just changed to Done
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_CHANGE_REPLICATION }, ifColumnsChanged = I_C_Print_Job_Instructions.COLUMNNAME_Status)
	public void logDocOutbound(final I_C_Print_Job_Instructions jobInstructions)
	{
		// We log the doc outbound only when Status changed to Done
		if (!X_C_Print_Job_Instructions.STATUS_Done.equals(jobInstructions.getStatus()))
		{
			return;
		}

		final UserId userToPrintId = UserId.ofRepoId(jobInstructions.getAD_User_ToPrint_ID());
		final Iterator<I_C_Print_Job_Line> lines = Services.get(IPrintingDAO.class).retrievePrintJobLines(jobInstructions);
		for (final I_C_Print_Job_Line line : IteratorUtils.asIterable(lines))
		{
			logDocOutbound(line, userToPrintId);
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
		final boolean notifyUser = sysConfigBL.getBooleanValue(SYSCONFIG_NOTIFY_PRINT_RECEIVER_ON_ERROR,
				false, // default
				jobInstructions.getAD_Client_ID(),
				jobInstructions.getAD_Org_ID());
		if (!notifyUser)
		{
			return;
		}

		// do the notification after commit, because e.g. if we send a mail, and even if that fails, we don't want this method to fail.
		notificationBL.sendAfterCommit(UserNotificationRequest.builder()
				.recipientUserId(UserId.ofRepoId(jobInstructions.getAD_User_ToPrint_ID()))
				.subjectADMessage(MSG_CLIENT_REPORTS_PRINT_ERROR)
				.contentPlain(jobInstructions.getErrorMsg())
				.targetAction(TargetRecordAction.of(I_C_Print_Job_Instructions.Table_Name, jobInstructions.getC_Print_Job_Instructions_ID()))
				.build());
	}

	/**
	 * If the status of the given <code>jobInstructions</code> is "pending" or send", this method can check via {@link ITaskExecutorService#schedule(Callable, int, TimeUnit, String)} whether the
	 * status is unchanged after <code>n</code> seconds or not.
	 * <p>
	 * The goal is to notify users about timeout problems with the printing client.
	 *
	 * Task http://dewiki908/mediawiki/index.php/09618_Bestellkontrolle_Druck_Probleme_%28106933593952%29
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
	 * Task http://dewiki908/mediawiki/index.php/09618_Bestellkontrolle_Druck_Probleme_%28106933593952%29
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

		final int printJobInstructionsId = jobInstructions.getC_Print_Job_Instructions_ID();
		final UserId userToPrintId = UserId.ofRepoId(jobInstructions.getAD_User_ToPrint_ID());
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
							() -> {
								final I_C_Print_Job_Instructions printJobInstructionsReloaded = loadOutOfTrx(printJobInstructionsId, I_C_Print_Job_Instructions.class);
								if (status.equals(printJobInstructionsReloaded.getStatus()))
								{
									// the status is still unchanged after the specified timeout => notify the user
									notificationBL.send(UserNotificationRequest.builder()
											.recipientUserId(userToPrintId)
											.subjectADMessage(MSG_CLIENT_PRINT_TIMEOUT)
											.contentADMessage(MSG_CLIENT_PRINT_TIMEOUT_DETAILS)
											.contentADMessageParam(printTimeOutSeconds)
											.contentADMessageParam(adReferenceService.retrieveListNameTrl(ctx, X_C_Print_Job_Instructions.STATUS_AD_Reference_ID, status))
											.targetAction(TargetRecordAction.of(I_C_Print_Job_Instructions.Table_Name, printJobInstructionsId))
											.build());
								}
								return null;
							},
							printTimeOutSeconds,
							TimeUnit.SECONDS,
							C_Print_Job_Instructions.class.getSimpleName());
				});
	}

	private void logDocOutbound(final I_C_Print_Job_Line line, @NonNull final UserId userToPrintId)
	{
		final Set<String> printerNames = new HashSet<>();
		for (final I_C_Print_Job_Detail detail : Services.get(IPrintingDAO.class).retrievePrintJobDetails(line))
		{
			final I_AD_PrinterRouting routing = load(detail.getAD_PrinterRouting_ID(), I_AD_PrinterRouting.class);
			final String printerName = routing.getAD_Printer().getPrinterName();

			if (!printerNames.add(printerName))
			{
				continue; // we already added a log record for this printer name
			}

			final I_C_Printing_Queue queueItem = line.getC_Printing_Queue();
			final I_AD_Archive archive = queueItem.getAD_Archive();

			Services.get(IArchiveEventManager.class).firePrintOut(archive,
					userToPrintId,
					printerName,
					IArchiveEventManager.COPIES_ONE,
					ArchivePrintOutStatus.Success);
		}
	}
}
