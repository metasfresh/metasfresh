package org.adempiere.mm.attributes.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListenerService;

import de.metas.util.Services;
import lombok.NonNull;

public class ModelAttributeSetInstanceListenerService implements IModelAttributeSetInstanceListenerService
{
	private final List<IModelAttributeSetInstanceListener> listeners = new ArrayList<>();

	@Override
	public void registerListener(@NonNull final IModelAttributeSetInstanceListener listener)
	{
		if (listeners.contains(listener))
		{
			return;
		}
		listeners.add(listener);

		final ModelAttributeSetInstanceListenerInterceptor listenerInterceptor = new ModelAttributeSetInstanceListenerInterceptor(listener);
		Services.get(IModelInterceptorRegistry.class)
				.addModelInterceptor(listenerInterceptor);
	}

}
