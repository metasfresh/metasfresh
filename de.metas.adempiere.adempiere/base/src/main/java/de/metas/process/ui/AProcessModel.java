/**
 *
 */
package de.metas.process.ui;

import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.process.IADProcessDAO;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionChecker;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor;

/**
 * @author tsa
 *
 */
public class AProcessModel
{
	private static final String ACTION_Name = "Process";

	private static final transient Logger logger = LogManager.getLogger(AProcessModel.class);

	public String getActionName()
	{
		return ACTION_Name;
	}

	public List<SwingRelatedProcessDescriptor> fetchProcesses(final Properties ctx, final GridTab gridTab)
	{
		if (gridTab == null)
		{
			return ImmutableList.of();
		}

		final IUserRolePermissions role = Env.getUserRolePermissions(ctx);
		Check.assumeNotNull(role, "No role found for {}", ctx);

		final int AD_Table_ID = gridTab.getAD_Table_ID();
		final IProcessPreconditionsContext preconditionsContext = gridTab.toPreconditionsContext();

		return Services.get(IADProcessDAO.class).retrieveRelatedProcessesForTableIndexedByProcessId(ctx, AD_Table_ID)
				.values().stream()
				.filter(relatedProcess -> isExecutionGrantedOrLog(relatedProcess, role))
				.map(relatedProcess -> createSwingRelatedProcess(relatedProcess, preconditionsContext))
				.filter(this::isEnabledOrLog)
				.collect(GuavaCollectors.toImmutableList());
	}

	private final SwingRelatedProcessDescriptor createSwingRelatedProcess(final RelatedProcessDescriptor relatedProcess, final IProcessPreconditionsContext preconditionsContext)
	{
		final Supplier<ProcessPreconditionsResolution> preconditionsResolutionSupplier = () -> checkPreconditionApplicable(relatedProcess, preconditionsContext);
		return SwingRelatedProcessDescriptor.of(relatedProcess, preconditionsResolutionSupplier);
	}

	private final boolean isExecutionGrantedOrLog(final RelatedProcessDescriptor relatedProcess, final IUserRolePermissions permissions)
	{
		if (relatedProcess.isExecutionGranted(permissions))
		{
			return true;
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("Skip process {} because execution was not granted using {}", relatedProcess, permissions);
		}

		return false;
	}

	private final boolean isEnabledOrLog(final SwingRelatedProcessDescriptor relatedProcess)
	{
		if (relatedProcess.isEnabled())
		{
			return true;
		}
		
		if (!relatedProcess.isSilentRejection())
		{
			return true;
		}

		//
		// Log and filter it out
		if (logger.isDebugEnabled())
		{
			final String disabledReason = relatedProcess.getDisabledReason(Env.getAD_Language(Env.getCtx()));
			logger.debug("Skip process {} because {} (silent={})", relatedProcess, disabledReason, relatedProcess.isSilentRejection());
		}
		return false;
	}

	@VisibleForTesting
	/* package */ProcessPreconditionsResolution checkPreconditionApplicable(final RelatedProcessDescriptor relatedProcess, final IProcessPreconditionsContext preconditionsContext)
	{
		return ProcessPreconditionChecker.newInstance()
				.setProcess(relatedProcess)
				.setPreconditionsContext(preconditionsContext)
				.checkApplies();
	}
}
