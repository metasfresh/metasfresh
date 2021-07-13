/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.window.descriptor;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.ui.web.view.SqlViewFactory;
import de.metas.ui.web.view.descriptor.SqlViewKeyColumnNamesMap;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.Document;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner_Adv_Search_v;
import org.compiere.model.I_C_Order;

import java.util.List;
import java.util.Objects;

public class AdvancedSearchBPartnerProcessor implements AdvancedSearchDescriptor.AdvancedSearchSelectionProcessor
{
	private final SqlViewFactory sqlViewFactory;

	public AdvancedSearchBPartnerProcessor(final SqlViewFactory sqlViewFactory)
	{
		this.sqlViewFactory = sqlViewFactory;
	}

	@Override
	public void processSelection(final WindowId windowId, final Document document, final String bpartnerFieldName, final String selectionIdStr)
	{
		final List<Object> ids = DocumentId.of(selectionIdStr).toComposedKeyParts();
		final boolean firstValueLocationId = isFirstValueLocationId(windowId);
		final String locIdStr = (String)(firstValueLocationId ? ids.get(0) : ids.get(1));
		final String userIdStr = (String)(firstValueLocationId ? ids.get(1) : ids.get(0));
		final int locId = Integer.parseInt(locIdStr);
		final UserId userId = UserId.ofRepoIdOrNull(Integer.parseInt(userIdStr));

		final String locationFieldName = getLocationFieldNameForBPartnerField(bpartnerFieldName);
		final BPartnerLocationId locationId = Services.get(IBPartnerDAO.class).getBPartnerLocationIdByRepoId(locId);

		document.processValueChange(bpartnerFieldName, locationId.getBpartnerId(), null, false);
		document.processValueChange(locationFieldName, locationId.getRepoId(), null, false);

		if (userId != null)
		{
			final String userIdFieldName = getUserIdFieldNameForBPartnerField(bpartnerFieldName);
			document.processValueChange(userIdFieldName, userId.getRepoId(), null, false);
		}
	}

	private boolean isFirstValueLocationId(final WindowId windowId)
	{
		final SqlViewKeyColumnNamesMap keyColumnNamesMap = sqlViewFactory.getKeyColumnNamesMap(windowId);
		return Objects.equals(keyColumnNamesMap.getKeyColumnNames().get(0), I_C_BPartner_Adv_Search_v.COLUMNNAME_C_BPartner_Location_ID);
	}

	@NonNull
	private String getLocationFieldNameForBPartnerField(final String bpartnerFieldName)
	{
		switch (bpartnerFieldName)
		{
			case I_C_Order.COLUMNNAME_C_BPartner_ID:
				return I_C_Order.COLUMNNAME_C_BPartner_Location_ID;
			case I_C_Order.COLUMNNAME_Bill_BPartner_ID:
				return I_C_Order.COLUMNNAME_Bill_Location_ID;
			case I_C_Order.COLUMNNAME_DropShip_BPartner_ID:
				return I_C_Order.COLUMNNAME_DropShip_Location_ID;
			case I_C_Order.COLUMNNAME_Pay_BPartner_ID:
				return I_C_Order.COLUMNNAME_Pay_Location_ID;
			default:
				throw new AdempiereException("Can't find Location field for Bpartner field: " + bpartnerFieldName);
		}
	}

	@NonNull
	private String getUserIdFieldNameForBPartnerField(final String bpartnerFieldName)
	{
		switch (bpartnerFieldName)
		{
			case I_C_Order.COLUMNNAME_C_BPartner_ID:
				return I_C_Order.COLUMNNAME_AD_User_ID;
			case I_C_Order.COLUMNNAME_Bill_BPartner_ID:
				return I_C_Order.COLUMNNAME_Bill_User_ID;
			case I_C_Order.COLUMNNAME_DropShip_BPartner_ID:
				return I_C_Order.COLUMNNAME_DropShip_User_ID;
			default:
				throw new AdempiereException("Can't find Location field for Bpartner field: " + bpartnerFieldName);
		}
	}
}
