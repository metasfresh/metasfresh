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

import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_C_BPartner;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Services;

/**
 * UnLink Business Partner from Organization
 *
 * @author Jorg Janke
 * @version $Id: BPartnerOrgUnLink.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class BPartnerOrgUnLink extends JavaProcess
{
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

	private BPartnerId p_C_BPartner_ID;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
				;
			}
			else if (name.equals("C_BPartner_ID"))
			{
				p_C_BPartner_ID = BPartnerId.ofRepoId(para[i].getParameterAsInt());
			}
			else
			{
				log.error("Unknown Parameter: {}", name);
			}
		}
	}	// prepare

	@Override
	protected String doIt()
	{
		if (p_C_BPartner_ID != null)
		{
			throw new FillMandatoryException("C_BPartner_ID");
		}

		final I_C_BPartner bp = bpartnersRepo.getByIdInTrx(p_C_BPartner_ID);
		//
		if (bp.getAD_OrgBP_ID() <= 0)
		{
			throw new IllegalArgumentException("Business Partner not linked to an Organization");
		}
		bp.setAD_OrgBP_ID(-1);

		bpartnersRepo.save(bp);

		return MSG_OK;
	}	// doIt
}
