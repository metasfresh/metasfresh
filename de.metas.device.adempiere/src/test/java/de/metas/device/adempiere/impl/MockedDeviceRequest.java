package de.metas.device.adempiere.impl;

import java.math.BigDecimal;

import de.metas.device.adempiere.impl.MockedDeviceRequest.Response;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.ISingleValueResponse;

/*
 * #%L
 * de.metas.device.adempiere
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class MockedDeviceRequest implements IDeviceRequest<Response>
{
	public static final class Response implements ISingleValueResponse<BigDecimal>
	{
		@Override
		public BigDecimal getSingleValue()
		{
			return BigDecimal.ZERO;
		}
		
	}

	@Override
	public Class<Response> getResponseClass()
	{
		return Response.class;
	}
}
