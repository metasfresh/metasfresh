package de.metas.handlingunits.util;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.util.List;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;

public final class TraceUtils
{
	private TraceUtils()
	{
	}

	private static HUTracerInstance tracerInstance = new HUTracerInstance();

	public static void reset()
	{
		tracerInstance = new HUTracerInstance();
	}

	public static HUTracerInstance newInstance()
	{
		return new HUTracerInstance();
	}

	public static void dumpAllHUs()
	{
		tracerInstance.dumpAllHUs();
	}

	public static void dumpAllHUs(final String title)
	{
		tracerInstance.dumpAllHUs(title);
	}

	public static void dump(final List<I_M_HU> hus)
	{
		tracerInstance.dump(hus);
	}

	public static void dump(final I_M_HU hu)
	{
		tracerInstance.dump(hu);
	}

	public static void dump(final String title, final I_M_HU hu)
	{
		tracerInstance.dump(title, hu);
	}

	public static void dumpTransactions()
	{
		tracerInstance.dumpTransactions();
	}

	public static String toStringPath(final I_M_HU_Item item)
	{
		return tracerInstance.toStringPath(item);
	}

	public static String toStringName(final I_M_HU_Item item)
	{
		return tracerInstance.toStringName(item);
	}

	public static String toStringPath(final I_M_HU hui)
	{
		return toStringPath(hui);
	}

	public static String toStringName(final I_M_HU hui)
	{
		return tracerInstance.toStringName(hui);
	}
}
