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

import java.util.List;
import java.util.Optional;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Role_OrgAccess;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.MOrg;
import org.compiere.model.MWarehouse;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfoUpdateRequest;
import de.metas.organization.OrgTypeId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.util.Services;

/**
 * Link Business Partner to Organization.
 * Either to existing or create new one
 *
 * @author Jorg Janke
 * @version $Id: BPartnerOrgLink.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class BPartnerOrgLink extends JavaProcess
{
	private final transient IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final transient IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
	private final transient IUserRolePermissionsDAO permissionsDAO = Services.get(IUserRolePermissionsDAO.class);

	private OrgId p_AD_Org_ID = OrgId.ANY;
	private OrgTypeId p_AD_OrgType_ID;
	private BPartnerId p_C_BPartner_ID;
	private BPartnerLocationId p_C_BPartner_Location_ID;
	private int p_AD_Role_ID;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		int bpartnerLocationRepoId = -1;

		final ProcessInfoParameter[] para = getParametersAsArray();
		for (final ProcessInfoParameter element : para)
		{
			final String name = element.getParameterName();
			if (element.getParameter() == null)
			{
				// nothing
			}
			else if (name.equals("AD_Org_ID"))
			{
				p_AD_Org_ID = OrgId.ofRepoIdOrAny(element.getParameterAsInt());
			}
			else if (name.equals("AD_OrgType_ID"))
			{
				p_AD_OrgType_ID = OrgTypeId.ofRepoIdOrNull(element.getParameterAsInt());
			}
			else if (name.equals("AD_Role_ID"))
			{
				p_AD_Role_ID = element.getParameterAsInt();
			}
			else if (name.equals("C_BPartner_Location_ID"))
			{
				bpartnerLocationRepoId = element.getParameterAsInt();
			}
			else
			{
				log.error("prepare - Unknown Parameter: {}", name);
			}
		}

		p_C_BPartner_ID = BPartnerId.ofRepoId(getRecord_ID());
		p_C_BPartner_Location_ID = BPartnerLocationId.ofRepoIdOrNull(p_C_BPartner_ID, bpartnerLocationRepoId);
	}	// prepare

	@Override
	protected String doIt()
	{
		if (p_C_BPartner_ID == null)
		{
			throw new FillMandatoryException("C_BPartner_ID");
		}

		final I_C_BPartner bp = bpartnersRepo.getByIdInTrx(p_C_BPartner_ID);

		// Create Org
		final boolean newOrg = p_AD_Org_ID.isAny();
		final MOrg org;
		if (newOrg)
		{
			org = new MOrg(getCtx(), 0, get_TrxName());
			org.setValue(bp.getValue());
			org.setName(bp.getName());
			org.setDescription(bp.getDescription());
			orgsRepo.save(org);

			p_AD_Org_ID = OrgId.ofRepoId(org.getAD_Org_ID());
		}
		else	// check if linked to already
		{
			org = LegacyAdapters.convertToPO(orgsRepo.getById(p_AD_Org_ID));
			final int C_BPartner_ID = org.getLinkedC_BPartner_ID(get_TrxName());
			if (C_BPartner_ID > 0)
			{
				throw new IllegalArgumentException("Organization '" + org.getName()
						+ "' already linked (to C_BPartner_ID=" + C_BPartner_ID + ")");
			}
		}

		// Create Warehouse
		I_M_Warehouse wh = null;
		if (!newOrg)
		{
			final List<I_M_Warehouse> whs = Services.get(IWarehouseDAO.class).getByOrgId(p_AD_Org_ID);
			if (!whs.isEmpty())
			{
				wh = whs.get(0);	// pick first
			}
		}
		// New Warehouse
		if (wh == null)
		{
			wh = new MWarehouse(org);
			configureWarehouse(wh, p_C_BPartner_Location_ID); // metas: 03084
			InterfaceWrapperHelper.save(wh);
		}

		// Get/Create Locator
		Services.get(IWarehouseBL.class).getDefaultLocator(wh);

		//
		// Update Org Info
		Services.get(IOrgDAO.class).createOrUpdateOrgInfo(OrgInfoUpdateRequest.builder()
				.orgId(p_AD_Org_ID)
				.orgTypeId(Optional.ofNullable(p_AD_OrgType_ID))
				.orgBPartnerLocationId(Optional.ofNullable(p_C_BPartner_Location_ID))
				// NOTE (task 03084): We are no longer setting the location to AD_OrgInfo. Location is contained in linked bpartner's location if (newOrg).
				.warehouseId(Optional.of(WarehouseId.ofRepoId(wh.getM_Warehouse_ID())))
				.build());

		// Update BPartner
		bp.setAD_OrgBP_ID(p_AD_Org_ID.getRepoId());
		bp.setAD_Org_ID(OrgId.ANY.getRepoId());	// Shared BPartner

		// Save BP
		bpartnersRepo.save(bp);

		//
		// Limit to specific Role
		if (p_AD_Role_ID > 0)
		{
			boolean found = false;
			// delete all accesses except the specific
			for (final I_AD_Role_OrgAccess orgAccess : permissionsDAO.retrieveRoleOrgAccessRecordsForOrg(p_AD_Org_ID))
			{
				if (orgAccess.getAD_Role_ID() == p_AD_Role_ID)
				{
					found = true;
				}
				else
				{
					InterfaceWrapperHelper.delete(orgAccess);
				}
			}
			// create access
			if (!found)
			{
				permissionsDAO.createOrgAccess(RoleId.ofRepoId(p_AD_Role_ID), OrgId.ofRepoId(org.getAD_Org_ID()));
			}
		}

		// Reset Client Role
		// FIXME: MRole.getDefault(getCtx(), true);

		return "Business Partner - Organization Link created";
	}	// doIt

	/**
	 * Configure/Update Warehouse from given linked BPartner
	 *
	 * @param warehouse
	 * @param bpartner
	 * @task http://dewiki908/mediawiki/index.php/03084:_Move_Org-Infos_to_related_BPartners_%282012080310000055%29
	 */
	private void configureWarehouse(final I_M_Warehouse warehouse, final BPartnerLocationId bpartnerLocationId)
	{
		warehouse.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(bpartnerLocationId));
	}
}	// BPartnerOrgLink
