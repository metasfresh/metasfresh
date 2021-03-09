package de.metas.device.api;

/*
 * #%L
 * de.metas.device.api
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


/**
 * Special sort of device response class that can return just one value.
 * <p>
 * Background: in our current usage scenario in ADempiere we have buttons that are supposed to query scales and add set result into a UI numerical field. To support this, ADempiere only adds buttons
 * for those {@link IDeviceRequest}s that have a single value response.
 *
 * @author ts
 * 
 * @param <T> the type of the return value.
 * 
 */
public interface ISingleValueResponse<T> extends IDeviceResponse
{
	T getSingleValue();
}
