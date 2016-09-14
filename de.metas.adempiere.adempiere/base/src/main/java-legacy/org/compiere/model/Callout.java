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

import org.adempiere.ad.callout.api.ICalloutField;

/**
 *  Callout Interface for Callout.
 *  Used in MTab and ImpFormatRow
 *
 *  @author     Jorg Janke
 *  @version    $Id: Callout.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 *  
 *  @deprecated Please use {@link org.adempiere.ad.callout.api.ICallout}
 */
@Deprecated
public interface Callout
{
	/**
	 * Start Callout.
	 * <p>
	 * Callout's are used for cross field validation and setting values in other fields when returning a non empty (error message) string, an exception is raised
	 * <p>
	 * When invoked, the Tab model has the new value!
	 *
	 * @param methodName Method name
	 * @param calloutField
	 */
	public void start (final String methodName, final ICalloutField calloutField);

	/**
	 *	Conversion Rules.
	 *	Convert a String
	 *
	 *	@param method   in notation User_Function
	 *  @param value    the value
	 *	@return converted String or Null if no method found
	 *
	 * @deprecated Only used in import formats; we need to introduce a dedicated interface for that
	 */
	@Deprecated
	public String convert (String method, String value);

}   //  callout
