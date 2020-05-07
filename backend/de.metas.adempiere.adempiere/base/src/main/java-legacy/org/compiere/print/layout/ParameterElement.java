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
package org.compiere.print.layout;

import java.awt.Font;
import java.util.Properties;

import org.compiere.model.MQuery;
import org.compiere.print.MPrintTableFormat;

import de.metas.i18n.Msg;

/**
 *	Parameter Table
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ParameterElement.java,v 1.2 2006/07/30 00:53:02 jjanke Exp $
 */
public class ParameterElement extends GridElement
{
	/**
	 * 	Parameter Element.
	 *  <pre>
	 *  Parameter fromValue - toValue
	 *  </pre>
	 * 	@param query query
	 *  @param ctx context
	 *  @param tFormat Table Format
	 */
	public ParameterElement(MQuery query, Properties ctx, MPrintTableFormat tFormat)
	{
		
	
		
		super (query.getRestrictionCount(), 4);
		
		final Font parameterFont = new Font (tFormat.getStandard_Font().getName(), Font.BOLD, tFormat.getStandard_Font().getSize());		

		setData (0, 0, Msg.getMsg(ctx, "Parameter") + ":", parameterFont, tFormat.getPageHeaderFG_Color());
		for (int r = 0; r < query.getRestrictionCount(); r++)
		{
			setData (r, 1, query.getInfoName(r), tFormat.getParameter_Font(), tFormat.getParameter_Color());
			setData (r, 2, query.getInfoOperator(r), tFormat.getParameter_Font(), tFormat.getParameter_Color());
			setData (r, 3, query.getInfoDisplayAll(r), tFormat.getParameter_Font(), tFormat.getParameter_Color());
		}
	}	//	ParameterElement

}	//	ParameterElement

