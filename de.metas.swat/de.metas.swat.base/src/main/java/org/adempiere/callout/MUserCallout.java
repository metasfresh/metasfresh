package org.adempiere.callout;

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


import java.util.Properties;

import org.adempiere.util.CustomColNames;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_User;
import org.compiere.model.MUser;

public final class MUserCallout extends CalloutEngine {

	public String isDefault(Properties ctx, int WindowNo, GridTab mTab,
			GridField mField, Object value, Object oldValue) {

		if (isCalloutActive()) {// prevent recursive
			return "";
		}
		Integer bPartnerID = (Integer) mTab.getField(
				I_AD_User.COLUMNNAME_C_BPartner_ID).getValue();

		if (bPartnerID == null || bPartnerID == 0) {
			return "";
		}

		if (MUser.getOfBPartner(ctx, bPartnerID.intValue()).length == 0) {
			// This is the first user for the given bpartner
			mTab.setValue(CustomColNames.AD_USER_ISDEFAULTCONTACT, "Y");
			return "";
		}
		return "";
	}
}
