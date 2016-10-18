package de.metas.device.scales.request;

/*
 * #%L
 * de.metas.device.scales
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.device.api.IDeviceRequest;

/**
 * This deprecated requests is currently bound to the same command which {@link GetInstantGrossWeighRequest} is bound to. It is here for compatibility.
 *
 *
 * @author metas-dev <dev@metasfresh.com>
 * @deprecated please use either {@link GetStableGrossWeighRequest} or {@link GetInstantGrossWeighRequest} and remove this class as soon as the configs are up to date.
 */
@Deprecated
public class GetGrossWeighRequest implements IDeviceRequest<GetWeightResponse>
{
	@Override
	public Class<GetWeightResponse> getResponseClass()
	{
		return GetWeightResponse.class;
	}

	@Override
	public String toString()
	{
		return String.format("GetGrossWeighRequest []");
	}
}
