package de.metas.device.api;

import java.io.Serial;

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
 * Generic exception super class for all device related problems.
 * 
 * @author ts
 * 
 */
public class DeviceException extends RuntimeException
{

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -4788618351964381176L;

	public DeviceException(String message)
	{
		super(message);
	}

	public DeviceException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
