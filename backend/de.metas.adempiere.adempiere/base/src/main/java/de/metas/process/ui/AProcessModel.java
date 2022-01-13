package de.metas.process.ui;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.process.ADProcessService;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionChecker;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.security.IUserRolePermissions;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.AdTableId;
import org.compiere.SpringContextHolder;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;

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

		final IProcessPreconditionsContext preconditionsContext = gridTab.toPreconditionsContext();
		final AdTableId adTableId = AdTableId.ofRepoId(gridTab.getAD_Table_ID());
		final AdWindowId adWindowId = preconditionsContext.getAdWindowId();
		final AdTabId adTabId = preconditionsContext.getAdTabId();

		return SpringContextHolder.instance.getBean(ADProcessService.class).getRelatedProcessDescriptors(adTableId, adWindowId, adTabId)
				.stream()
				.filter(relatedProcess -> isExecutionGrantedOrLog(relatedProcess, role))
				.map(relatedProcess -> createSwingRelatedProcess(relatedProcess, preconditionsContext))
				.filter(this::isEnabledOrLog)
				.collect(GuavaCollectors.toImmutableList());
	}

	private SwingRelatedProcessDescriptor createSwingRelatedProcess(final RelatedProcessDescriptor relatedProcess, final IProcessPreconditionsContext preconditionsContext)
	{
		final Supplier<ProcessPreconditionsResolution> preconditionsResolutionSupplier = () -> checkPreconditionApplicable(relatedProcess, preconditionsContext);
		return SwingRelatedProcessDescriptor.of(relatedProcess, preconditionsResolutionSupplier);
	}

	private boolean isExecutionGrantedOrLog(final RelatedProcessDescriptor relatedProcess, final IUserRolePermissions permissions)
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

	private boolean isEnabledOrLog(final SwingRelatedProcessDescriptor relatedProcess)
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
