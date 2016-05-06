package de.metas.printing.esb.base.jaxb;

/*
 * #%L
 * de.metas.printing.esb.base
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


import de.metas.printing.esb.base.util.jaxb.DynamicObjectFactory;

/**
 * ADempiere constants for data conversion
 * 
 * @author al
 *
 */
public final class JAXBConstants
{
	private JAXBConstants()
	{
		super();
	}

	/**
	 * JAXB Contact Path
	 */
	public static final String JAXB_ContextPath = "de.metas.printing.esb.base.jaxb.generated";
	public static final DynamicObjectFactory JAXB_ObjectFactory = new DynamicObjectFactory(new de.metas.printing.esb.base.jaxb.generated.ObjectFactory());

	/*
	 * AD_PRINTER_HW Formats' Versions
	 */
	public static final String PRT_AD_PRINTER_HW_FORMAT_VERSION = "*";
	public static final String PRT_AD_PRINTER_HW_MEDIA_SIZE_FORMAT_VERSION = "*";
	public static final String PRT_AD_PRINTER_HW_MEDIA_TRAY_FORMAT_VERSION = "*";

	/*
	 * C_PRINT_PACKAGE Formats' Versions
	 */
	public static final String PRT_C_PRINT_PACKAGE_FORMAT_VERSION = "*";
	public static final String PRT_C_PRINT_PACKAGE_INFO_FORMAT_VERSION = "*";
	public static final String PRT_C_PRINT_JOB_INSTRUCTIONS_CONFIRM_FORMAT_VERSION = "*";

	/*
	 * Misc Formats' Versions
	 */
	public static final String PRT_LOGINREQUEST_VERSION = "*";

}
