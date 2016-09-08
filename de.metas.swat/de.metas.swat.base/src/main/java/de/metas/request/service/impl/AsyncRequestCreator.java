package de.metas.request.service.impl;

import java.util.Properties;
import java.util.Set;

import de.metas.request.service.IRequestCreator;
import de.metas.request.service.async.spi.impl.C_Request_CreateFromInout;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class AsyncRequestCreator implements IRequestCreator
{

	@Override
	public void createRequest(final Properties ctx, final Set<Integer> inOutLineIds, final String trxName)
	{
		C_Request_CreateFromInout.createWorkpackage(ctx, inOutLineIds, trxName);
		
	}

}
