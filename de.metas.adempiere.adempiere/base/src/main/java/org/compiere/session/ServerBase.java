package org.compiere.session;

import java.util.concurrent.atomic.AtomicInteger;

import org.compiere.Adempiere;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.email.MailService;
import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.session.jaxrs.IServerService;

/**
 * Base implementation of {@link Server}.
 *
 * AIM: have a technology (EJB or any other) implementaton of {@link Server}
 *
 * @author tsa
 *
 */
public class ServerBase implements IServerService
{
	protected static final transient Logger log = LogManager.getLogger(ServerBase.class);

	private static final AtomicInteger nextInstanceNo = new AtomicInteger(1);
	private final int instanceNo;
	private final AtomicInteger processExecutionCount = new AtomicInteger(0);

	public ServerBase()
	{
		instanceNo = nextInstanceNo.getAndIncrement();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("instanceNo", instanceNo)
				.add("processExecutionCount", processExecutionCount)
				.toString();
	}

	@Override
	public ProcessExecutionResult process(final int adPInstanceRepoId)
	{
		final PInstanceId pinstanceId = PInstanceId.ofRepoId(adPInstanceRepoId);
		
		processExecutionCount.incrementAndGet();

		final Thread currentThread = Thread.currentThread();
		final String threadNameBkp = currentThread.getName();
		try
		{
			currentThread.setName("Server_process_" + pinstanceId.getRepoId());

			final ProcessExecutionResult result = ProcessInfo.builder()
					.setPInstanceId(pinstanceId)
					.setCreateTemporaryCtx()
					//
					.buildAndPrepareExecution()
					.switchContextWhenRunning()
					.executeSync()
					.getResult();

			return result;
		}
		finally
		{
			currentThread.setName(threadNameBkp);
		}
	}

	@Override
	public EMailSentStatus sendEMail(final EMail email)
	{
		final MailService mailService = Adempiere.getBean(MailService.class);
		email.setDebugMailToAddress(mailService.getDebugMailToAddressOrNull());
		return email.send();
	}
}
