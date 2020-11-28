/**
 *
 */
package de.metas.callcenter.form;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.apps.AEnv;
import org.compiere.apps.search.InfoSimple;
import org.compiere.apps.search.InfoSimpleFactory;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_R_Group;
import org.compiere.util.Env;

import de.metas.callcenter.model.MRGroupProspect;
import de.metas.interfaces.I_C_BPartner;

/**
 *
 * @author Teo Sarca, teo.sarca@gmail.com
 */
public class CalloutR_Group extends CalloutEngine
{
	public String selectProspects(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		final I_R_Group bundle = InterfaceWrapperHelper.create(mTab, I_R_Group.class);

		// If new record, we need to save it first
		final int R_Group_ID = bundle.getR_Group_ID();
		if (R_Group_ID <= 0)
		{
			return "";
		}

		final List<BPartnerContact> contacts = new ArrayList<BPartnerContact>();

		final String whereClause =
				// Exclude already added partners
				"NOT EXISTS (SELECT 1 FROM R_Group_Prospect rgp"
						+ " WHERE rgp.R_Group_ID=" + R_Group_ID + " AND rgp.C_BPartner_ID=C_BPartner.C_BPartner_ID)"
						// Should have at least one active contact
						+ " AND EXISTS (SELECT 1 FROM AD_User u"
						+ " WHERE u.C_BPartner_ID=C_BPartner.C_BPartner_ID AND u.IsActive='Y')";

		final InfoSimple info = InfoSimpleFactory.get().create(
				true, // modal,
				WindowNo,
				I_C_BPartner.Table_Name, // tableName,
				true, // multiSelection,
				whereClause,
				null // attributes
				);
		info.setDefaultSelected(true);
		info.setDoubleClickTogglesSelection(true);
		info.addPropertyChangeListener(InfoSimple.PROP_SaveSelection, new PropertyChangeListener()
		{

			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				contacts.clear();
				if (info.isOkPressed())
				{
					fetchSelectedContacts(info, contacts);
				}
			}
		});
		AEnv.showCenterWindow(Env.getWindow(WindowNo), info.getWindow());

		int count_added = 0;
		String msg = "";
		try
		{
			TreeSet<Integer> addedBPartners = new TreeSet<Integer>();
			for (BPartnerContact contact : contacts)
			{
				if (addedBPartners.contains(contact.getC_BPartner_ID()))
				{
					continue;
				}
				if (MRGroupProspect.existContact(ctx, R_Group_ID, contact.getC_BPartner_ID(), contact.getAD_User_ID(), null))
				{
					log.info("Partner already added: " + contact + " [SKIP]");
					continue;
				}
				if (contact.getAD_User_ID() <= 0)
				{
					log.warn("Invalid contact (no user found): " + contact + " [SKIP]");
					continue;
				}
				MRGroupProspect rgp = new MRGroupProspect(ctx, R_Group_ID, contact.getC_BPartner_ID(), contact.getAD_User_ID(), null);
				rgp.saveEx();
				//
				addedBPartners.add(contact.getC_BPartner_ID());
				count_added++;
			}
		}
		finally
		{
			if (count_added > 0)
			{
				refreshIncludedTabs(mTab);
				msg = "@Added@ #" + count_added;
			}
		}

		log.info(msg);
		return "";
	}

	private void fetchSelectedContacts(InfoSimple info, List<BPartnerContact> list)
	{
		final int rows = info.getRowCount();
		for (int row = 0; row < rows; row++)
		{
			if (!info.isDataRow(row))
				continue;
			if (info.isSelected(row))
			{
				int bpartnerId = info.getValueAsInt(row, I_C_BPartner.COLUMNNAME_C_BPartner_ID);
				int userId = info.getValueAsInt(row, I_AD_User.COLUMNNAME_AD_User_ID);
				list.add(new BPartnerContact(bpartnerId, userId));
			}
		}
	}

	/**
	 * Refresh all included tabs
	 *
	 * @param mTab
	 *            parent tab
	 */
	private void refreshIncludedTabs(GridTab mTab)
	{
		for (GridTab detailTab : mTab.getIncludedTabs())
		{
			detailTab.dataRefreshAll();
		}
	}

	public static class BPartnerContact
	{
		private final int C_BPartner_ID;
		private final int AD_User_ID;

		public BPartnerContact(int cBPartnerID, int aDUserID)
		{
			super();
			C_BPartner_ID = cBPartnerID;
			AD_User_ID = aDUserID;
		}

		public int getC_BPartner_ID()
		{
			return C_BPartner_ID;
		}

		public int getAD_User_ID()
		{
			return AD_User_ID;
		}

		@Override
		public String toString()
		{
			return "BPartnerContact [C_BPartner_ID=" + C_BPartner_ID
					+ ", AD_User_ID=" + AD_User_ID + "]";
		}
	}

}
