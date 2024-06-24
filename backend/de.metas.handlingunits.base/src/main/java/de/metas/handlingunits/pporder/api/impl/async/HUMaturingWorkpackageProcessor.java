/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.pporder.api.impl.async;

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Properties;

public class HUMaturingWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IHUPPOrderBL huOrderBL = Services.get(IHUPPOrderBL.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, @Nullable final String localTrxName)
	{
		final I_PP_Order ppOrder = CollectionUtils.singleElement(queueDAO.retrieveAllItems(workPackage, I_PP_Order.class));

		huOrderBL.issueAndReceiveMaturingHUs(ppOrder);

		return Result.SUCCESS;
	}

	@Builder(builderMethodName = "prepareWorkpackage", buildMethodName = "enqueue")
	public static I_C_Queue_WorkPackage enqueueWorkpackage(@NonNull final I_PP_Order ppOrder)
	{
		final Properties ctx = Env.getCtx();
		return Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, HUMaturingWorkpackageProcessor.class)
				.newWorkPackage()
				.addElement(ppOrder)
				.setUserInChargeId(Env.getLoggedUserIdIfExists(ctx).orElse(null)) // invoker
				.buildAndEnqueue();
	}
}
