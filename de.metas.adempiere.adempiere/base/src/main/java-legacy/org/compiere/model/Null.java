/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

/**
 * Database Null Indicator
 * 
 * @author Jorg Janke
 */
public final class Null
{
	/** Singleton */
	public static final Null NULL = new Null();

	/**
	 * @return true if given object is <code>null</code> or {@link #NULL}.
	 */
	public static final boolean isNull(final Object obj)
	{
		return obj == NULL || obj == null;
	}

	/**
	 * Unbox {@value #NULL} object
	 * 
	 * @param obj
	 * @return <code>obj</code> or <code>null</code>
	 */
	public static final Object unbox(final Object obj)
	{
		return obj == NULL ? null : obj;
	}

	/**
	 * Box <code>null</code> to {@link #NULL}.
	 * 
	 * @param obj
	 * @return <code>obj</code> or {@link #NULL} if the <code>obj</code> was <code>null</code>; this method never returns <code>null</code>.
	 */
	public static final Object box(final Object obj)
	{
		return obj == null ? NULL : obj;
	}

	private Null()
	{
	}

	@Override
	public String toString()
	{
		return "NULL";
	}
}
