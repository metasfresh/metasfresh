/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2009 www.metas.de                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.exceptions;

import org.adempiere.model.ZoomInfoFactory.IZoomSource;
import org.compiere.util.DisplayType;

/**
 * 
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and
 *         RelationTypes
 */
@SuppressWarnings("serial")
public class PORelationException extends AdempiereException
{
	public static PORelationException throwWrongKeyColumnCount(final IZoomSource source)
	{
		final Object[] msgParams = new Object[] { source.toString(), source.getKeyColumnNames().size() };
		throw new PORelationException(MSG_ERR_KEY_COLUMNS_2P, msgParams);
	}

	public static PORelationException throwMissingWindowId(final String referenceName, final String tableName, final boolean isSOTrx)
	{
		final Object[] msgParams = { referenceName, tableName, DisplayType.toBooleanString(isSOTrx) };
		throw new PORelationException(MSG_ERR_WINDOW_3P, msgParams);
	}

	/**
	 * Message indicates that a po has more or less than one key columns.
	 * <ul>
	 * <li>Param 1: the po (toString)</li>
	 * <li>Param 2: the number of key columns</li>
	 * </ul>
	 */
	private static final String MSG_ERR_KEY_COLUMNS_2P = "MRelationType_Err_KeyColumns_2P";

	/**
	 * Message indicates that neither the reference nor the table have an
	 * AD_Window_ID set.
	 * <ul>
	 * <li>Param 1: The AD_Reference's name</li>
	 * <li>Param 2: The Table name</li>
	 * <li>Param 3: Whether we are in the ctx of a SO (Y or N)</li>
	 * </ul>
	 */
	private static final String MSG_ERR_WINDOW_3P = "MRelationType_Err_Window_3P";

	private PORelationException(final String adMsg, final Object... msgParams)
	{
		super(adMsg, msgParams);
	}
}
