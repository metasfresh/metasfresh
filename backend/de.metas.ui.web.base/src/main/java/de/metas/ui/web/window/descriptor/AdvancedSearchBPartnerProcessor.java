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

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.ui.web.view.SqlViewFactory;
import de.metas.ui.web.view.descriptor.SqlViewKeyColumnNamesMap;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentFieldLogicExpressionResultRevaluator;
import de.metas.ui.web.window.model.sql.SqlComposedKey;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner_Adv_Search;
import org.compiere.model.I_C_Order;

import javax.annotation.Nullable;

public class AdvancedSearchBPartnerProcessor implements AdvancedSearchDescriptor.AdvancedSearchSelectionProcessor
{
	private final SqlViewFactory sqlViewFactory;

	public AdvancedSearchBPartnerProcessor(final SqlViewFactory sqlViewFactory)
	{
		this.sqlViewFactory = sqlViewFactory;
	}

	@Override
	public void processSelection(
			@NonNull final WindowId windowId,
			@NonNull final Document document,
			@NonNull final String bpartnerFieldName,
			@NonNull final String selectionIdStr)
	{
		final DocumentFieldLogicExpressionResultRevaluator readonlyRevaluator = DocumentFieldLogicExpressionResultRevaluator.DEFAULT;

		final SqlViewKeyColumnNamesMap keyColumnNamesMap = sqlViewFactory.getKeyColumnNamesMap(windowId);
		final SqlComposedKey composedKey = keyColumnNamesMap.extractComposedKey(DocumentId.of(selectionIdStr));

		//
		// BPartner
		final BPartnerId bpartnerId = composedKey.getValueAsId(I_C_BPartner_Adv_Search.COLUMNNAME_C_BPartner_ID, BPartnerId.class).orElse(null);
		if (bpartnerId == null)
		{
			throw new AdempiereException("@NoSelection@"); // shall not happen
		}
		document.processValueChange(bpartnerFieldName, bpartnerId, null, readonlyRevaluator);

		//
		// Location
		final String locationFieldName = getLocationFieldNameForBPartnerField(bpartnerFieldName);
		if (locationFieldName != null && document.containsField(locationFieldName))
		{
			final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoIdOrNull(
					bpartnerId,
					composedKey.getValueAsInteger(I_C_BPartner_Adv_Search.COLUMNNAME_C_BPartner_Location_ID).orElse(-1));
			if (bpLocationId != null)
			{
				document.processValueChange(locationFieldName, bpLocationId.getRepoId(), null, readonlyRevaluator);
			}
		}

		//
		// Contact
		final String bpContactIdFieldName = getUserIdFieldNameForBPartnerField(bpartnerFieldName);
		if (bpContactIdFieldName != null && document.containsField(bpContactIdFieldName))
		{
			final BPartnerContactId bpContactId = BPartnerContactId.ofRepoIdOrNull(
					bpartnerId,
					composedKey.getValueAsInteger(I_C_BPartner_Adv_Search.COLUMNNAME_C_BP_Contact_ID).orElse(-1));
			if (bpContactId != null)
			{
				document.processValueChange(bpContactIdFieldName, bpContactId.getRepoId(), null, readonlyRevaluator);
			}
		}
	}

	@Nullable
	private static String getLocationFieldNameForBPartnerField(final String bpartnerFieldName)
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
				return null;
		}
	}

	@Nullable
	private static String getUserIdFieldNameForBPartnerField(final String bpartnerFieldName)
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
				return null;
		}
	}
}
