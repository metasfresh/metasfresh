package de.metas.async.spi.impl;

import com.google.common.annotations.VisibleForTesting;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.spi.IWorkpackagePrioStrategy;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Returns the workpackage priority based on the number of WPs that were already enqueued so far.<br>
 * The configuration is done via <code>AD_SysConfig</code>. See {@link SysconfigBackedSizeBasedWorkpackagePrioConfig} for details.
 * For the case that no size-based priority was configured, <code>medium</code> is returned as the default priority.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * task http://dewiki908/mediawiki/index.php/09049_Priorit%C3%A4ten_Strategie_asynch_%28105016248827%29
 */
public class SizeBasedWorkpackagePrio implements IWorkpackagePrioStrategy
{
	/**
	 * Returns an instance with a <code>medium</code> default prio. The default prio will be used if there is no size-based configuration available.
	 */
	public static final IWorkpackagePrioStrategy INSTANCE = new SizeBasedWorkpackagePrio(ConstantWorkpackagePrio.medium());

	private final ConstantWorkpackagePrio defaultPrio;

	/**
	 * can be set from the outside to change the behavior for testing.
	 */
	private Function<Integer, ConstantWorkpackagePrio> alternativeSize2constantPrio = null;

	private SizeBasedWorkpackagePrio(final ConstantWorkpackagePrio defaultPrio)
	{
		this.defaultPrio = defaultPrio;
	}

	/**
	 * Allow to inject an alternative function. Currently this is intended only for testing purposes, but that might change in future.
	 * Note that by default this implementation uses {@link SysconfigBackedSizeBasedWorkpackagePrioConfig}.
	 */
	@VisibleForTesting
	public void setAlternativeSize2constantPrio(@Nullable final Function<Integer, ConstantWorkpackagePrio> size2constantPrio)
	{
		this.alternativeSize2constantPrio = size2constantPrio;
	}

	@Override
	public String getPrioriy(final IWorkPackageQueue queue)
	{
		final int packageCount = queue.getLocalPackageCount();

		final Function<Integer, ConstantWorkpackagePrio> size2constantPrioToUse;

		if (alternativeSize2constantPrio == null)
		{
			size2constantPrioToUse =
					new SysconfigBackedSizeBasedWorkpackagePrioConfig(
							queue.getCtx(),
							queue.getEnquingPackageProcessorInternalName(),
							defaultPrio);
		}
		else
		{
			size2constantPrioToUse = alternativeSize2constantPrio;
		}

		final ConstantWorkpackagePrio constantPrioForSize = size2constantPrioToUse.apply(packageCount);
		final IWorkPackageQueue ignored = null;
		return constantPrioForSize.getPrioriy(ignored);
	}
}
