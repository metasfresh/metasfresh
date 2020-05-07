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
 * Contributor: Carlos Ruiz - globalqss                                       *
 *****************************************************************************/
package org.compiere.process;

import org.adempiere.impexp.IImportProcess;
import org.adempiere.impexp.IImportProcessFactory;
import org.adempiere.util.Services;
import org.compiere.model.I_I_GLJournal;

import de.metas.process.RunOutOfTrx;
import de.metas.process.JavaProcess;

/**
 * Import {@link I_I_GLJournal} records to {@link I_GLJournal}.
 * 
 * @author cg
 */
public class ImportGLJournal extends JavaProcess
{
	private IImportProcess<I_I_GLJournal> importProcess;

	@Override
	protected void prepare()
	{
		importProcess = Services.get(IImportProcessFactory.class).newImportProcess(I_I_GLJournal.class);
		importProcess.setCtx(getCtx());
		importProcess.setParameters(getParameterAsIParams());
		importProcess.setLoggable(this);
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		importProcess.run();
		return MSG_OK;
	}
}	// ImportGLJournal
