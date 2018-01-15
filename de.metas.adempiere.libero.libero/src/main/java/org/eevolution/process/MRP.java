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
 *                 Teo Sarca, www.arhipac.ro                                  *
 *****************************************************************************/

package org.eevolution.process;

import org.eevolution.mrp.api.IMRPResult;

import de.metas.material.planning.IMaterialPlanningContext;

/**
 * Calculate Material Plan MRP
 *
 * @author Victor Perez, e-Evolution, S.C.
 * @author Teo Sarca, www.arhipac.ro
 */
public class MRP extends AbstractMRPProcess
{
	@Override
	protected IMRPResult run(final IMaterialPlanningContext mrpContext)
	{
		return null;
	//	return mrpExecutorService.run(mrpContext);
	}
}
