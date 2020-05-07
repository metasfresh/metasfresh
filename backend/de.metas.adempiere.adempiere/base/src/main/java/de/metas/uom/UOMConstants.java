package de.metas.uom;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
/**
 * UOM related constants.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class UOMConstants
{

	/** X12 Element 355 Code Day */
	public static final String X12_DAY = "DA";

	/** X12 Element 355 Code Second */
	public static final String X12_SECOND = "03";

	/** X12 Element 355 Code Month */
	public static final String X12_MONTH = "MO";

	/** X12 Element 355 Code Minute */
	public static final String X12_MINUTE = "MJ";

	/** X12 Element 355 Code Hour */
	public static final String X12_HOUR = "HR";

	/** X12 Element 355 Code Work Day (8 hours / 5days) */
	public static final String X12_DAY_WORK = "WD";

	/** X12 Element 355 Code Week */
	public static final String X12_WEEK = "WK";

	/** X12 Element 355 Code Work Month (20 days / 4 weeks) */
	public static final String X12_MONTH_WORK = "WM";

	/** X12 Element 355 Code Year */
	public static final String X12_YEAR = "YR";

	private UOMConstants()
	{
		super();
	}
}
