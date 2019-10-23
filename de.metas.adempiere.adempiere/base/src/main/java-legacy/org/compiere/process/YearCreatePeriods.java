/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.sql.Timestamp;
import java.util.Locale;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Year;
import org.compiere.model.MYear;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;

/**
 * Create Periods of year
 * 
 * @author Jorg Janke
 * @version $Id: YearCreatePeriods.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class YearCreatePeriods extends JavaProcess
{
	private int p_C_Year_ID = 0;
	private Timestamp p_StartDate;
	private String p_DateFormat;

	/**
	 * Prepare
	 */
	@Override
	protected void prepare()
	{

		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("StartDate"))
				p_StartDate = (Timestamp)para[i].getParameter();
			else if (name.equals("DateFormat"))
				p_DateFormat = (String)para[i].getParameter();
			else
				log.error("Unknown Parameter: " + name);
		}
		p_C_Year_ID = getRecord_ID();
	}	// prepare

	@Override
	protected String doIt()
	{
		final I_C_Year year = load(p_C_Year_ID, I_C_Year.class);
		if (year == null)
		{
			throw new AdempiereException("@NotFound@: @C_Year_ID@ - " + p_C_Year_ID);
		}
		log.info("Year: {}", year);

		//
		final Locale locale = null;
		MYear.createStdPeriods(year, locale, p_StartDate, p_DateFormat);

		return "@OK@";
	}
}
