/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/

package org.eevolution.process;

import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductLowLevelUpdater;

import de.metas.process.JavaProcess;
import de.metas.util.Services;

/**
 * CalculateLowLevel for MRP
 *
 * @author Victor Perez, e-Evolution, S.C.
 * @version $Id: CalculateLowLevel.java,v 1.1 2004/06/22 05:24:03 vpj-cd Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 */
public class CalculateLowLevel extends JavaProcess
{
	private final transient IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);

	@Override
	protected void prepare()
	{
	} // prepare

	@Override
	protected String doIt() throws Exception
	{
		final IProductLowLevelUpdater updater = productBOMBL
				.updateProductLowLevels()
				.setContext(this)
				.setFailOnFirstError(false)
				.update();

		final int count_ok = updater.getUpdatedCount();
		final int count_err = updater.getErrorsCount();

		return "@Ok@ #" + count_ok + " @Error@ #" + count_err;
	}
}
