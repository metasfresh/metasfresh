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
import java.awt.Paint;
import java.util.Properties;
import java.util.regex.Pattern;

import org.compiere.model.MLocation;

/**
 *	Location/Address Element.
 *  Prints Addresses
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: LocationElement.java,v 1.2 2006/07/30 00:53:02 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1888085 ] One line location field not working
 * 			<li>BF [ 1888094 ] PF: label & label suffix not working for location field
 * 			<li>BF [ 2695078 } Country is not translated on invoice
 * 
 *  @author Michael Judd (Akuna Ltd)
 * 				<li>BF [ 2695078 ] Country is not translated on invoice
 */
public class LocationElement extends GridElement
{
	/**
	 * Constructor
	 * @param ctx context
	 * @param C_Location_ID location
	 * @param font font
	 * @param color color
	 * @param isHeightOneLine
	 * @param label
	 * @param labelSuffix
	 */
	public LocationElement(Properties ctx, int C_Location_ID, Font font, Paint color,
			boolean isHeightOneLine,
			String label, String labelSuffix, String language)
	{
		super(isHeightOneLine ? 1 : 10, 1);		//	max
		setGap(0,0);
		MLocation ml = MLocation.get (ctx, C_Location_ID, null);
	//	log.fine("C_Location_ID=" + C_Location_ID);
		if (ml != null)
		{
			int index = 0;
			if (isHeightOneLine)
			{
				String line = (label != null ? label : "") 
								+ ml.toString()
								+ (labelSuffix != null ? labelSuffix : "");
				setData(index++, 0, line, font, color);
			}
			else if (ml.isAddressLinesReverse())
			{
				setData(index++, 0, ml.getCountry(true, language), font, color);
				String[] lines = Pattern.compile("$", Pattern.MULTILINE).split(ml.getCityRegionPostal());
				for (int i = 0; i < lines.length; i++)
					setData(index++, 0, lines[i], font, color);
				if (ml.getAddress4() != null && ml.getAddress4().length() > 0)
					setData(index++, 0, ml.getAddress4(), font, color);
				if (ml.getAddress3() != null && ml.getAddress3().length() > 0)
					setData(index++, 0, ml.getAddress3(), font, color);
				if (ml.getAddress2() != null && ml.getAddress2().length() > 0)
					setData(index++, 0, ml.getAddress2(), font, color);
				if (ml.getAddress1() != null && ml.getAddress1().length() > 0)
					setData(index++, 0, ml.getAddress1(), font, color);
			}
			else
			{
				if (ml.getAddress1() != null && ml.getAddress1().length() > 0)
					setData(index++, 0, ml.getAddress1(), font, color);
				if (ml.getAddress2() != null && ml.getAddress2().length() > 0)
					setData(index++, 0, ml.getAddress2(), font, color);
				if (ml.getAddress3() != null && ml.getAddress3().length() > 0)
					setData(index++, 0, ml.getAddress3(), font, color);
				if (ml.getAddress4() != null && ml.getAddress4().length() > 0)
					setData(index++, 0, ml.getAddress4(), font, color);
				String[] lines = Pattern.compile("$", Pattern.MULTILINE).split(ml.getCityRegionPostal());
				for (int i = 0; i < lines.length; i++)
					setData(index++, 0, lines[i], font, color);
				setData(index++, 0, ml.getCountry(true, language), font, color);
			}
		}
	}	//	LocationElement
	
	/**
	 * @deprecated since 3.3.1b
	 * @see #LocationElement(Properties, int, Font, Paint, boolean, String, String)
	 */
	public LocationElement(Properties ctx, int C_Location_ID, Font font, Paint color) {
		this(ctx, C_Location_ID, font, color, false, null, null, null);
	}
	
}	//	LocationElement
