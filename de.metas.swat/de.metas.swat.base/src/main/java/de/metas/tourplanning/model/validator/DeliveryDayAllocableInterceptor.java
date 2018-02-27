package de.metas.tourplanning.model.validator;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;

import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IDeliveryDayDAO;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.tourplanning.spi.IDeliveryDayCreateHandler;
import lombok.NonNull;

/**
 * Interceptor which wraps {@link IDeliveryDayCreateHandler} and triggers {@link I_M_DeliveryDay_Alloc} create/update/delete when model changes.
 *
 * @author tsa
 *
 */
public class DeliveryDayAllocableInterceptor extends AbstractModelInterceptor
{
	private final IDeliveryDayCreateHandler handler;
	private final String modelTableName;

	public DeliveryDayAllocableInterceptor(@NonNull final IDeliveryDayCreateHandler handler)
	{
		this.handler = handler;

		this.modelTableName = handler.getModelTableName();
		Check.assumeNotNull(modelTableName, "modelTableName not null");
	}

	@Override
	public String toString()
	{
		return "DeliveryDayAllocableInterceptor [modelTableName=" + modelTableName + ", handler=" + handler + "]";
	}

	public final String getModelTableName()
	{
		return modelTableName;
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelChange(modelTableName, this);
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
	{
		if (!changeType.isAfter())
		{
			return;
		}

		//
		// Model was created on changed
		if (changeType.isNewOrChange())
		{
			createOrUpdateAllocation(model);
		}
		//
		// Model was deleted
		else if (changeType.isDelete())
		{
			deleteAllocation(model);
		}
	}

	private void createOrUpdateAllocation(final Object model)
	{
		final IDeliveryDayBL deliveryDayBL = Services.get(IDeliveryDayBL.class);

		final IContextAware context = InterfaceWrapperHelper.getContextAware(model);
		final IDeliveryDayAllocable deliveryDayAllocable = handler.asDeliveryDayAllocable(model);

		final I_M_DeliveryDay_Alloc deliveryDayAlloc = deliveryDayBL.getCreateDeliveryDayAlloc(context, deliveryDayAllocable);
		if (deliveryDayAlloc == null)
		{
			// Case: no delivery day allocation was found and no delivery day on which we could allocate was found
			return;
		}

		deliveryDayBL.getDeliveryDayHandlers()
				.updateDeliveryDayAllocFromModel(deliveryDayAlloc, deliveryDayAllocable);

		InterfaceWrapperHelper.save(deliveryDayAlloc);
	}

	private void deleteAllocation(Object model)
	{
		final IDeliveryDayDAO deliveryDayDAO = Services.get(IDeliveryDayDAO.class);

		final IContextAware context = InterfaceWrapperHelper.getContextAware(model);
		final IDeliveryDayAllocable deliveryDayAllocable = handler.asDeliveryDayAllocable(model);

		final I_M_DeliveryDay_Alloc deliveryDayAlloc = deliveryDayDAO.retrieveDeliveryDayAllocForModel(context, deliveryDayAllocable);
		if (deliveryDayAlloc != null)
		{
			InterfaceWrapperHelper.delete(deliveryDayAlloc);
		}
	}
}
