/*******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution * Copyright (C)
 * 1999-2009 Adempiere, Inc. All Rights Reserved. * This program is free
 * software; you can redistribute it and/or modify it * under the terms version
 * 2 of the GNU General Public License as published * by the Free Software
 * Foundation. This program is distributed in the hope * that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied * warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. * See the GNU General
 * Public License for more details. * You should have received a copy of the GNU
 * General Public License along * with this program; if not, write to the Free
 * Software Foundation, Inc., * 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA. *
 * 
 ******************************************************************************/

package org.compiere.process;

import org.adempiere.ad.migration.model.I_AD_Migration;
import org.adempiere.ad.migration.xml.XMLWriter;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * 
 * Process to export an AD migration script as XML
 * 
 * @author Paul Bowden, Adaxa Pty Ltd
 * @author Teo Sarca
 * 
 */
public class MigrationToXML extends JavaProcess
{

	private int migrationId = 0;
	private String fileName;

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameterName().equals("AD_Migration_ID"))
			{
				migrationId = para.getParameterAsInt();
			}
			else if (para.getParameterName().equals("FileName"))
			{
				fileName = (String)para.getParameter();
			}
		}

		// if run from Migration window
		if (migrationId <= 0 && I_AD_Migration.Table_Name.equals(getTableName()))
		{
			migrationId = getRecord_ID();
		}

		log.debug("AD_Migration_ID = " + migrationId + ", filename = " + fileName);
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_AD_Migration migration = InterfaceWrapperHelper.create(getCtx(), migrationId, I_AD_Migration.class, get_TrxName());

		final XMLWriter writer = new XMLWriter(fileName);
		writer.write(migration);

		return "Exported migration to: " + fileName;
	}
}
