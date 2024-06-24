/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.server;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntryService;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService.TransactionMetadata;
import de.metas.monitoring.adapter.PerformanceMonitoringService.Type;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.report.ReportResultData;
import de.metas.scheduler.AdSchedulerId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import it.sauronsoftware.cron4j.Predictor;
import it.sauronsoftware.cron4j.SchedulingPattern;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Scheduler;
import org.compiere.model.I_AD_SchedulerLog;
import org.compiere.model.I_AD_Scheduler_Para;
import org.compiere.model.I_AD_Task;
import org.compiere.model.MNote;
import org.compiere.model.MScheduler;
import org.compiere.model.MTask;
import org.compiere.model.X_AD_Scheduler;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

/**
 * Scheduler
 *
 * @author Jorg Janke
 * @version $Id: Scheduler.java,v 1.5 2006/07/30 00:53:33 jjanke Exp $
 */
public class Scheduler extends AdempiereServer
{
	public static Scheduler cast(@NonNull final AdempiereServer server)
	{
		if(!(server instanceof Scheduler))
		{
			throw new AdempiereException("server is not a Scheduler: " + server);
		}
		else
		{
			return (Scheduler)server;
		}
	}

	private static final String SYSCONFIG_NOTIFY_ON_NOT_OK = "org.compiere.server.Scheduler.notifyOnNotOK";

	private static final String SYSCONFIG_NOTIFY_ON_OK = "org.compiere.server.Scheduler.notifyOnOK";

	/**
	 * <code>AD_Message_ID = 441</code>.
	 */
	private static final AdMessageKey MSG_PROCESS_OK = AdMessageKey.of("ProcessOK");

	/**
	 * <code>AD_Message_ID=442</code>.
	 */
	private static final AdMessageKey MSG_PROCESS_RUN_ERROR = AdMessageKey.of("ProcessRunError");

	public Scheduler(final MScheduler model)
	{
		super(model, 240);		// nap
		m_model = model;

		// metas us1030 updating status
		setSchedulerStatus(X_AD_Scheduler.STATUS_Started, null); // saveLogs=false
	}	// Scheduler

	private static final transient Logger log = LogManager.getLogger(Scheduler.class);

	/** The Concrete Model */
	private final MScheduler m_model;
	/** Last Summary; stored in the scheduler log. */
	private StringBuffer m_summary = new StringBuffer();

	/** last outcome; stored in the scheduler log. */
	private boolean m_success = false;

	//
	// Cron4J scheduling
	private it.sauronsoftware.cron4j.Scheduler cronScheduler;
	private Predictor predictor;

	/**
	 * Sets AD_Scheduler.Status and save the record
	 */
	private void setSchedulerStatus(final String status, final PInstanceId pinstanceId)
	{
		Services.get(ITrxManager.class).runInNewTrx(new TrxRunnableAdapter()
		{
			@Override
			public void run(final String localTrxName)
			{
				if (pinstanceId != null)
				{
					saveLogs(pinstanceId, localTrxName);
				}

				m_model.setStatus(status);
				InterfaceWrapperHelper.save(m_model, localTrxName);
			}
		});
	}

	/**
	 * <ul>
	 * <li>Delete old logs
	 * <li>create a new AD_SchedulerLog record based on {@link #m_summary}.
	 * </ul>
	 */
	private void saveLogs(final PInstanceId pinstanceId, final String trxName)
	{
		final int no = m_model.deleteLog(trxName);
		m_summary.append("Logs deleted=").append(no);
		//
		final Properties ctx = InterfaceWrapperHelper.getCtx(m_model);
		final I_AD_SchedulerLog pLog = InterfaceWrapperHelper.create(ctx, I_AD_SchedulerLog.class, trxName);

		pLog.setAD_Org_ID(m_model.getAD_Org_ID());
		pLog.setAD_Scheduler_ID(m_model.getAD_Scheduler_ID());
		pLog.setSummary(m_summary.toString());
		pLog.setIsError(!m_success);
		pLog.setReference("#" + getRunCount() + " - " + TimeUtil.formatElapsed(getStartWork()));

		if (pinstanceId != null)
		{
			pLog.setAD_PInstance_ID(pinstanceId.getRepoId());
		}

		InterfaceWrapperHelper.save(pLog);

		m_summary = new StringBuffer();
	}

	@Override
	protected void doWork()
	{
		final PerformanceMonitoringService service = SpringContextHolder.instance.getBeanOr(
				PerformanceMonitoringService.class,
				NoopPerformanceMonitoringService.INSTANCE);

		service.monitorTransaction(
				this::doWork0,
				TransactionMetadata.builder()
						.name("Scheduler - " + m_model.getName())
						.type(Type.SCHEDULER)
						.label("scheduler.name", m_model.getName())
						.build());
	}

	private void doWork0()
	{
		// metas us1030 updating staus
		setSchedulerStatus(X_AD_Scheduler.STATUS_Running, null);

		m_summary = new StringBuffer(m_model.toString()).append(" - ");

		// Prepare a ctx for the report/process - BF [1966880]
		final Properties schedulerCtx = createSchedulerCtxForDoWork();
		final Mutable<PInstanceId> adPInstanceId = new Mutable<>();

		try (final IAutoCloseable contextRestorer = Env.switchContext(schedulerCtx))
		{
			final String type = m_model.getSchedulerProcessType();
			if (X_AD_Scheduler.SCHEDULERPROCESSTYPE_Task.equals(type))
			{
				if (m_model.getAD_Task_ID() <= 0)
				{
					throw new AdempiereException("@NotFound@ @AD_Task_ID@");
				}
				final MTask task = new MTask(schedulerCtx, m_model.getAD_Task_ID(), ITrx.TRXNAME_None);
				m_summary.append(runTask(task));
			}
			else if (X_AD_Scheduler.SCHEDULERPROCESSTYPE_Process.equals(type)
					|| X_AD_Scheduler.SCHEDULERPROCESSTYPE_Report.equals(type))
			{
				if (m_model.getAD_Process_ID() <= 0)
				{
					throw new AdempiereException("@NotFound@ @AD_Process_ID@");
				}

				//
				// Create process info
				// metas-ts using process with scheduler-ctx
				final I_AD_Process process = m_model.getAD_Process();
				final ProcessInfo pi = createProcessInfo(schedulerCtx, m_model);
				adPInstanceId.setValue(pi.getPinstanceId());

				//
				// Create process runner
				final TrxRunnableAdapter processRunner = new TrxRunnableAdapter()
				{
					@Override
					public void run(final String localTrxName) throws Exception
					{
						final String result;
						if (process.isReport())
						{
							result = runReport(pi, process);
						}
						else
						{
							result = runProcess(pi);
						}
						m_summary.append(result);
					}

					@Override
					public boolean doCatch(final Throwable e)
					{
						log.warn("Failed running process/report: {}", process, e);
						m_summary.append(e.toString());
						return ROLLBACK;
					}

					@Override
					public void doFinally()
					{
						adPInstanceId.setValue(pi.getPinstanceId());
					}
				};

				//
				// Execute the process runner
				final Stopwatch stopwatch = Stopwatch.createStarted();
				final ITrxManager trxManager = Services.get(ITrxManager.class);
				if (pi.getProcessClassInfo().isRunOutOfTransaction())
				{
					trxManager.runOutOfTransaction(processRunner);
				}
				else
				{
					trxManager.runInNewTrx(processRunner);
				}
				log.debug("Executed {} in {}", process, stopwatch);
			}
			else
			{
				throw new AdempiereException("@NotSupported@ @" + I_AD_Scheduler.COLUMNNAME_SchedulerProcessType + "@ - " + type);
			}
		}

		// metas us1030 updating status: Running->Started
		setSchedulerStatus(X_AD_Scheduler.STATUS_Started, adPInstanceId.getValue());
		// metas end

	}	// doWork

	private Properties createSchedulerCtxForDoWork()
	{
		final Properties schedulerCtx = Env.newTemporaryCtx();

		//
		// AD_Client, AD_Language
		final IClientDAO clientDAO = Services.get(IClientDAO.class);
		final ClientId clientId = ClientId.ofRepoId(m_model.getAD_Client_ID());
		final I_AD_Client schedClient = clientDAO.getById(clientId);
		Env.setContext(schedulerCtx, Env.CTXNAME_AD_Client_ID, schedClient.getAD_Client_ID());
		Env.setContext(schedulerCtx, Env.CTXNAME_AD_Language, schedClient.getAD_Language());

		//
		// AD_Org, M_Warehouse
		final OrgId orgId = OrgId.ofRepoId(m_model.getAD_Org_ID());
		Env.setContext(schedulerCtx, Env.CTXNAME_AD_Org_ID, orgId.getRepoId());
		if (orgId.isRegular())
		{
			final OrgInfo schedOrg = Services.get(IOrgDAO.class).getOrgInfoById(orgId);
			if (schedOrg.getWarehouseId() != null)
			{
				Env.setContext(schedulerCtx, Env.CTXNAME_M_Warehouse_ID, schedOrg.getWarehouseId().getRepoId());
			}
		}

		//
		// AD_User_ID, SalesRep_ID
		final UserId adUserId = getUserId();
		Env.setContext(schedulerCtx, Env.CTXNAME_AD_User_ID, adUserId.getRepoId());
		Env.setContext(schedulerCtx, Env.CTXNAME_SalesRep_ID, adUserId.getRepoId());

		//
		// AD_Role
		final RoleId roleId;
		if (!InterfaceWrapperHelper.isNull(m_model, I_AD_Scheduler.COLUMNNAME_AD_Role_ID))
		{
			roleId = RoleId.ofRepoId(m_model.getAD_Role_ID());
		}
		else
		{
			// Use the first user role, which has access to our organization.
			final IUserRolePermissions role = Services.get(IUserRolePermissionsDAO.class)
					.retrieveFirstUserRolesPermissionsForUserWithOrgAccess(
							clientId,
							orgId,
							adUserId,
							Env.getLocalDate(schedulerCtx))
					.orElse(null);

			// gh #2092: without a role, we won't be able to run the process, because ProcessExecutor.assertPermissions() will fail.
			Check.errorIf(role == null,
					"Scheduler {} has does not reference an AD_Role and we were unable to retrieve one for AD_User_ID={} and AD_Org_ID={}; AD_Scheduler={}",
					m_model.getName(), adUserId, orgId, m_model);
			roleId = role.getRoleId();
		}
		Env.setContext(schedulerCtx, Env.CTXNAME_AD_Role_ID, roleId.getRepoId());

		//
		// Date
		final Timestamp date = SystemTime.asDayTimestamp();
		Env.setContext(schedulerCtx, Env.CTXNAME_Date, date);

		return schedulerCtx;
	}

	/**
	 * Run Report
	 *
	 * @return summary
	 */
	private String runReport(final ProcessInfo pi, final I_AD_Process process)
	{
		log.debug("Run report: {}", process);

		if (!process.isReport() || process.getAD_ReportView_ID() <= 0)
		{
			return "Not a Report AD_Process_ID=" + process.getAD_Process_ID() + " - " + process.getName();
		}

		// Process
		final ProcessExecutionResult result = ProcessExecutor.builder(pi)
				// .switchContextWhenRunning() // NOTE: not needed, context was already switched in caller method
				.executeSync()
				.getResult();
		if (result.isError())
		{
			return "Process failed: (" + pi.getClassName() + ") " + result.getSummary();
		}

		// Report
		final Properties ctx = pi.getCtx();
		final ReportResultData reportData = pi.getResult().getReportData();
		if (reportData == null)
		{
			return "Cannot create Report AD_Process_ID=" + process.getAD_Process_ID() + " - " + process.getName();
		}
		final File report = reportData.writeToTemporaryFile("report_");
		// Notice
		final int AD_Message_ID = 884;		// HARDCODED SchedulerResult
		for (final UserId userId : m_model.getRecipientAD_User_IDs())
		{
			final MNote noteRecord = new MNote(ctx, AD_Message_ID, userId.getRepoId(), ITrx.TRXNAME_ThreadInherited);
			noteRecord.setClientOrg(pi.getAD_Client_ID(), pi.getAD_Org_ID());
			noteRecord.setTextMsg(m_model.getName());
			noteRecord.setDescription(m_model.getDescription());
			noteRecord.setRecord(pi.getTable_ID(), pi.getRecord_ID());
			noteRecord.save();

			// Attachment
			final AttachmentEntryService attachmentEntryService = Adempiere.getBean(AttachmentEntryService.class);
			attachmentEntryService.createNewAttachment(noteRecord, report);
		}
		//
		return result.getSummary();
	}	// runReport

	/**
	 * Run Process
	 */
	private String runProcess(final ProcessInfo pi) throws Exception
	{
		log.debug("Run process: {}", pi);

		final ProcessExecutionResult result = ProcessExecutor.builder(pi)
				// .switchContextWhenRunning() // NOTE: not needed, context was already switched in caller method
				.executeSync()
				.getResult();
		final boolean ok = !result.isError();

		// notify supervisor if error
		// metas: c.ghita@metas.ro: start
		final int adPInstanceTableId = Services.get(IADTableDAO.class).retrieveTableId(I_AD_PInstance.Table_Name);

		notify(ok,
				pi.getTitle(),
				result.getSummary(),
				result.getLogInfo(),
				adPInstanceTableId,
				PInstanceId.toRepoId(result.getPinstanceId()));
		// metas: c.ghita@metas.ro: end

		m_success = ok; // stored it, so we can persist it in the scheduler log
		return result.getSummary();
	}	// runProcess

	/**
	 * Creates and setup the {@link ProcessInfo}.
	 */
	private static ProcessInfo createProcessInfo(final Properties schedulerCtx, final MScheduler adScheduler)
	{
		final I_AD_Process adProcess = adScheduler.getAD_Process();

		return ProcessInfo.builder()
				.setCtx(schedulerCtx)
				.setAD_Process(adProcess)
				.addParameters(createProcessInfoParameters(schedulerCtx, adScheduler))
				.setInvokedBySchedulerId(AdSchedulerId.ofRepoId(adScheduler.getAD_Scheduler_ID()))
				.build();
	}

	/**
	 * metas: c.ghita@metas.ro
	 * method for run a task
	 *
	 */
	private String runTask(final MTask task)
	{
		final String summary = task.execute() + task.getTask().getErrorLog();
		final Integer exitValue = task.getTask().getExitValue();
		final boolean ok = exitValue == 0;
		final int adTaskTableID = Services.get(IADTableDAO.class).retrieveTableId(I_AD_Task.Table_Name);

		notify(ok,
				task.getName(), // subject
				summary,
				"", // log info
				adTaskTableID,
				task.get_ID());
		return summary;
	}

	private UserId getUserId()
	{
		// FIXME: i think we need to brainstorm and figure out how to get rid of checking UpdatedBy/CreatedBy,
		// because those are totally unpredictable!!!

		if (m_model.getSupervisor_ID() > 0)
		{
			return UserId.ofRepoId(m_model.getSupervisor_ID());
		}
		// NOTE: for now i am turning off the UpdateBy checking because that is clearly not predictable
		// else if (m_model.getUpdatedBy() > 0)
		// {
		// return m_model.getUpdatedBy();
		// }
		else if (m_model.getCreatedBy() > 0)
		{
			return UserId.ofRepoId(m_model.getCreatedBy());
		}
		else
		{
			return UserId.METASFRESH; // fall back to SuperUser
		}
	}

	/**
	 * Fill Parameter
	 */
	private static List<ProcessInfoParameter> createProcessInfoParameters(final Properties schedulerCtx, final MScheduler adScheduler)
	{
		// NOTE: we want to re-query the parameters each time, in case they were changed
		// This will allow the SysAdmin to do quick tweaks without restarting metasfresh server
		final List<I_AD_Scheduler_Para> schedulerParams = adScheduler.getParameters(true);

		final ImmutableList.Builder<ProcessInfoParameter> processInfoParameters = ImmutableList.builder();
		for (final I_AD_Scheduler_Para schedulerPara : schedulerParams)
		{
			final String variable = schedulerPara.getParameterDefault();

			final I_AD_Process_Para adProcessPara = schedulerPara.getAD_Process_Para();
			final String parameterName = adProcessPara.getColumnName();
			final int displayType = adProcessPara.getAD_Reference_ID();
			final ProcessInfoParameter pip = createProcessInfoParameter(schedulerCtx, parameterName, variable, displayType);
			if (pip == null)
			{
				continue;
			}

			processInfoParameters.add(pip);
		}	// instance parameter loop

		return processInfoParameters.build();
	}	// fillParameter

	private static ProcessInfoParameter createProcessInfoParameter(final Properties schedulerCtx, final String parameterName, final String variable, final int displayType)
	{
		log.debug("Filling parameter: {} = {}", parameterName, variable);
		// Value - Constant/Variable
		Object value = variable;
		if (variable == null || variable.isEmpty())
		{
			value = null;
		}
		else if (variable.indexOf('@') != -1)	// we have a variable
		{
			// Strip
			int index = variable.indexOf('@');
			String columnName = variable.substring(index + 1);
			index = columnName.indexOf('@');
			if (index == -1)
			{
				log.warn(parameterName + " - cannot evaluate=" + variable);
				return null;
			}
			columnName = columnName.substring(0, index);

			// try Env
			final String env = Env.getContext(schedulerCtx, columnName);
			if (Check.isEmpty(env))
			{
				log.warn(parameterName + " - not in environment =" + columnName + "(" + variable + ")");
				return null;
			}
			else
			{
				value = env;
			}
		}	// @variable@

		// No Value
		if (value == null)
		{
			log.debug("{} - empty", parameterName);
			return null;
		}

		// Convert to Type
		try
		{
			if (DisplayType.isNumeric(displayType)
					|| DisplayType.isID(displayType))
			{
				final BigDecimal bd;
				if (value instanceof BigDecimal)
				{
					bd = (BigDecimal)value;
				}
				else if (value instanceof Integer)
				{
					bd = new BigDecimal(((Integer)value).intValue());
				}
				else
				{
					bd = new BigDecimal(value.toString());
				}
				final ProcessInfoParameter pip = ProcessInfoParameter.of(parameterName, bd);
				log.debug("Created: {}", pip);
				return pip;
			}
			else if (DisplayType.isDate(displayType))
			{
				final Timestamp ts;
				if (value instanceof java.util.Date)
				{
					ts = TimeUtil.asTimestamp((java.util.Date)value);
				}
				else
				{
					ts = Timestamp.valueOf(value.toString());
				}
				final ProcessInfoParameter pip = ProcessInfoParameter.of(parameterName, ts);
				log.debug("Created: {}", pip);
				return pip;
			}
			else
			{
				final ProcessInfoParameter pip = ProcessInfoParameter.of(parameterName, value.toString());
				log.debug("Created: {}", pip);
				return pip;
			}
		}
		catch (final Exception e)
		{
			log.warn(parameterName + " = " + variable + " (" + value + ") " + value.getClass().getName(), e);
			return null;
		}
	}

	/**
	 * Get Server Info
	 *
	 * @return info
	 */
	@Override
	public String getServerInfo()
	{
		return "#" + getRunCount() + " - Last=" + m_summary.toString();
	}	// getServerInfo

	/**
	 * metas: c.ghita@metas.ro
	 * notify trough mail in case of abnormal termination
	 */

	private void notify(
			final boolean ok,
			final String subject,
			final String summary,
			final String logInfo,
			final int adTableId,
			final int recordId)
	{
		final Properties ctx = getCtx();

		// notify supervisor if error
		final INotificationBL notificationBL = Services.get(INotificationBL.class);
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		if (!ok)
		{
			if (sysConfigBL.getBooleanValue(SYSCONFIG_NOTIFY_ON_NOT_OK, false, adClientId, adOrgId))
			{
				final UserId supervisorId = m_model.getSupervisor_ID() > 0 ? UserId.ofRepoId(m_model.getSupervisor_ID()) : null;
				if (supervisorId != null)
				{
					notificationBL.send(UserNotificationRequest.builder()
							.recipientUserId(supervisorId)
							.subjectADMessage(MSG_PROCESS_RUN_ERROR)
							.contentPlain(summary + " " + logInfo)
							.targetAction(TargetRecordAction.of(TableRecordReference.of(adTableId, recordId)))
							.build());
				}
			}
		}
		else if (sysConfigBL.getBooleanValue(SYSCONFIG_NOTIFY_ON_OK, false, adClientId, adOrgId))
		{
			for (final UserId userId : m_model.getRecipientAD_User_IDs())
			{
				notificationBL.send(UserNotificationRequest.builder()
						.recipientUserId(userId)
						.subjectADMessage(MSG_PROCESS_OK)
						.contentPlain(summary + " " + logInfo)
						.targetAction(TargetRecordAction.of(TableRecordReference.of(adTableId, recordId)))
						.build());
			}
		}
	}

	/**
	 * This implementation evaluated a cron pattern to do the scheduling. If the model's scheduling type is not "cron",
	 * then the super classe's scheduling is used instead.
	 */
	@Override
	public void run()
	{
		if (!X_AD_Scheduler.SCHEDULETYPE_CronSchedulingPattern.equals(m_model.getScheduleType()))
		{
			super.run();
			return;
		}

		final String cronPattern = m_model.getCronPattern();
		if (cronPattern != null && cronPattern.trim().length() > 0 && SchedulingPattern.validate(cronPattern))
		{
			cronScheduler = new it.sauronsoftware.cron4j.Scheduler();
			cronScheduler.schedule(cronPattern, () -> {
				runNow();
				final long next = predictor.nextMatchingTime();
				setDateNextRun(new Timestamp(next));
			});
			predictor = new Predictor(cronPattern);
			final long next = predictor.nextMatchingTime();
			setDateNextRun(new Timestamp(next));
			cronScheduler.start();
			while (true)
			{
				if (!sleep())
				{
					cronScheduler.stop();
					break;
				}
				else if (!cronScheduler.isStarted())
				{
					break;
				}
			}
		}
		else
		{
			super.run();
		}
	}
}	// Scheduler
