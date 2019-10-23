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
package org.compiere.print;

import org.adempiere.pdf.Document;
import org.compiere.print.layout.LayoutEngine;

/**
 * Archive Engine.
 * Based on Settings on Client Level
 * Keys set for
 * - Menu Reports - AD_Report_ID
 * - Win Report - AD_Table_ID
 * - Documents - AD_Table_ID & Record_ID & C_Customer_ID
 *
 * @author Jorg Janke
 * @version $Id: ArchiveEngine.java,v 1.3 2006/07/30 00:53:02 jjanke Exp $
 *
 */
public class ArchiveEngine
{
	/**
	 * Can we archive the document?
	 *
	 * @return true if can be archived
	 */
	public static boolean isValid(LayoutEngine layout)
	{
		return (layout != null
				&& Document.isValid(layout)
				&& layout.getNumberOfPages() > 0);
	}

	public static ArchiveEngine get()
	{
		if (s_engine == null)
			s_engine = new ArchiveEngine();
		return s_engine;
	}

	// Create Archiver
	static
	{
		s_engine = new ArchiveEngine();
	}

	/** Singleton */
	private static ArchiveEngine s_engine = null;

	private ArchiveEngine()
	{
		if (s_engine == null)
			s_engine = this;
	}
}
