package org.adempiere.inout.modelvalidator;

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


import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

public final class MInOutLineValidator implements ModelValidator {

	private int ad_Client_ID = -1;

	public int getAD_Client_ID() {
		return ad_Client_ID;
	}

	public final void initialize(final ModelValidationEngine engine,
			final MClient client) {

		if (client != null) {
			ad_Client_ID = client.getAD_Client_ID();
		}
	}

	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		return null;
	}

	public String modelChange(final PO po, final int type)
			throws Exception {

		//nothing to do
		return null;
	}

	public String docValidate(PO po, int timing) {
		// nothing to do
		return null;
	}
}
