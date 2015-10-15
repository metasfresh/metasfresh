package de.metas.async.spi.impl;

/*
 * #%L
 * de.metas.async
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


import com.google.common.base.Function;

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.spi.IWorkpackagePrioStrategy;

/**
 * Returns the workpackage priority based on the number of WPs that were already enqueued so far.<br>
 * The configuration is done via <code>AD_SysConfig</code>. See {@link SysconfigBackedSizeBasedWorkpackagePrioConfig} for details.
 * For the case that no size-based priority was configured, <code>medium</code> is returned as the default priority.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 * @task http://dewiki908/mediawiki/index.php/09049_Priorit%C3%A4ten_Strategie_asynch_%28105016248827%29
 */
public class SizeBasedWorkpackagePrio implements IWorkpackagePrioStrategy
{
	/**
	 * Returns an instance with a <code>medium</code> default prio. The default prio will be used if there is no size-based configuration available.
	 */
	public static final IWorkpackagePrioStrategy INSTANCE = new SizeBasedWorkpackagePrio(ConstantWorkpackagePrio.medium());

	private final ConstantWorkpackagePrio defaultPrio;

	private SizeBasedWorkpackagePrio(final ConstantWorkpackagePrio defaultPrio)
	{
		this.defaultPrio = defaultPrio;
	}

	@Override
	public String getPrioriy(final IWorkPackageQueue queue)
	{
		final int packageCount = queue.getLocalPackageCount();

		final Function<Integer, ConstantWorkpackagePrio> size2constantPrio =
				new SysconfigBackedSizeBasedWorkpackagePrioConfig(
						queue.getCtx(),
						queue.getEnquingPackageProcessorInternalName(),
						defaultPrio);

		final ConstantWorkpackagePrio constantPrioForSize = size2constantPrio.apply(packageCount);
		final IWorkPackageQueue ignored = null;
		return constantPrioForSize.getPrioriy(ignored);
	}
}
