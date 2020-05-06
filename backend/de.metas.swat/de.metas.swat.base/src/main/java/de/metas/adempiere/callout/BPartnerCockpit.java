package de.metas.adempiere.callout;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import static org.adempiere.util.MiscUtils.getCalloutId;
import static org.compiere.model.I_C_Order.Table_Name;

import java.util.Properties;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.GridTab.DataNewCopyMode;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.MQuery;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.IMsgBL;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.util.Services;

public class BPartnerCockpit extends CalloutEngine
{

	public static final String MSG_MISSING_BILL_LOC = "BPartnerCockpitMissingBillLoc";
	public static final String MSG_MISSING_SHIP_LOC = "BPartnerCockpitMissingShipLoc";

	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	public String createSO(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{

		if (isCalloutActive())
		{
			return "";
		}

		final int bPartnerId = getCalloutId(mTab, I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID);
		if (bPartnerId <= 0)
		{
			return "";
		}

		final I_C_BPartner_Location shipLoc = bPartnerDAO.retrieveShipToLocation(ctx, bPartnerId, ITrx.TRXNAME_None);
		if (shipLoc == null)
		{
			ADialog.error(WindowNo, null, MSG_MISSING_SHIP_LOC);
			return msgBL.getMsg(ctx, MSG_MISSING_SHIP_LOC);
		}

		final I_C_BPartner_Location billLoc = bPartnerDAO.retrieveBillToLocation(ctx, bPartnerId,
				false, // alsoTryBParnterRelation. Calling with 'false' to preserve the old/default behavior
				ITrx.TRXNAME_None);
		if (billLoc == null)
		{
			ADialog.error(WindowNo, null, MSG_MISSING_BILL_LOC);
			return msgBL.getMsg(ctx, MSG_MISSING_BILL_LOC);
		}

		final I_AD_User contact = bPartnerBL.retrieveShipContact(ctx, bPartnerId, ITrx.TRXNAME_None);

		final User billContact = bPartnerBL.retrieveContactOrNull(RetrieveContactRequest.builder()
				.bpartnerId(BPartnerId.ofRepoId(bPartnerId))
				.bPartnerLocationId(BPartnerLocationId.ofRepoId(bPartnerId, billLoc.getC_BPartner_Location_ID()))
				.build());

		openSOWindow(bPartnerId,
				getIDOrNull(shipLoc),
				getIDOrNull(billLoc),
				getIDOrNull(contact),
				billContact == null ? 0 : UserId.toRepoId(billContact.getId()));

		mField.setValue(oldValue, false);
		return "";
	}

	private int getIDOrNull(final Object object)
	{
		return object == null ? 0 : InterfaceWrapperHelper.getId(object);
	}

	private boolean openSOWindow(final int bPartnerId,
			final int locationShipId, final int locationBillId,
			final int contactId, final int billContactId)
	{

		final AdWindowId SALES_ORDER_WINDOW_ID = AdWindowId.ofRepoId(143);

		final AWindow soFrame = new AWindow();

		final MQuery query = new MQuery(Table_Name);
		query.addRestriction(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID
				+ "=" + bPartnerId);

		final boolean success = soFrame
				.initWindow(SALES_ORDER_WINDOW_ID, query);
		if (!success)
		{
			return false;
		}
		soFrame.pack();
		AEnv.addToWindowManager(soFrame);

		final GridTab tab = soFrame.getAPanel().getCurrentTab();
		tab.dataNew(DataNewCopyMode.NoCopy);
		tab.setValue(I_C_Order.COLUMNNAME_C_BPartner_ID, bPartnerId);
		tab.setValue(I_C_Order.COLUMNNAME_C_BPartner_Location_ID,
				locationShipId);
		tab.setValue(I_C_Order.COLUMNNAME_Bill_Location_ID, locationBillId);

		if (contactId > 0)
		{
			tab.setValue(I_C_Order.COLUMNNAME_AD_User_ID, contactId);
		}
		if (billContactId > 0)
		{
			tab.setValue(I_C_Order.COLUMNNAME_Bill_User_ID, billContactId);
		}
		AEnv.showCenterScreen(soFrame);

		return success;
	}
}
