/**
 * 
 */
package de.metas.modelvalidator;

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


import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MUser;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

/**
 * @author teo_sarca
 * 
 */
public class BPUserNameValidator implements ModelValidator {
	public static final String COLUMNNAME_AD_User_Nachname = "Lastname";
	public static final String COLUMNNAME_AD_User_Vorname = "Firstname";
	public static final String COLUMNNAME_AD_User_IsDefault = "IsDefaultContact";

	public static final String COLUMNNAME_C_BPartner_CompanyName = "CompanyName";
	public static final String COLUMNNAME_C_BPartner_IsCompany = "IsCompany";

	private int m_AD_Client_ID = -1;

	@Override
	public int getAD_Client_ID() {
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client) {
		if (client != null)
			m_AD_Client_ID = client.getAD_Client_ID();
		//
		engine.addModelChange(MBPartner.Table_Name, this);
		engine.addModelChange(MUser.Table_Name, this);
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID) {
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception {
		final boolean isNew = TYPE_BEFORE_NEW == type || TYPE_AFTER_NEW == type;
		final boolean isBeforeSave = TYPE_BEFORE_NEW == type
				|| TYPE_BEFORE_CHANGE == type;
		final boolean isAfterSave = TYPE_AFTER_NEW == type
				|| TYPE_AFTER_CHANGE == type;

		//
		// AD_User: Set Name from Nachname and Vorname
		if (po instanceof I_AD_User
				&& isBeforeSave
				&& (isNew || po.is_ValueChanged(I_AD_User.COLUMNNAME_Name)
						|| po.is_ValueChanged(COLUMNNAME_AD_User_Nachname) || po
						.is_ValueChanged(COLUMNNAME_AD_User_Vorname))) {
			final String nachname = get_ValueAsString(po,
					COLUMNNAME_AD_User_Nachname);
			final String vorname = get_ValueAsString(po,
					COLUMNNAME_AD_User_Vorname);
			//
			if ((isNew && Check.isEmpty(nachname) && Check.isEmpty(vorname))
					|| (!isNew && po.is_ValueChanged(I_AD_User.COLUMNNAME_Name))) {
				// If the user manualy changes the Name then do not change
				// update the name
				// (CR079.1, per Norbert's request, 08.04.2010, skype)
			} else {
				final String name = nachname + ", " + vorname;
				po.set_ValueOfColumn(MUser.COLUMNNAME_Name, name);
			}
		}
		//
		// C_BPartner: copy CompanyName to Name
		if (po instanceof I_C_BPartner
				&& isBeforeSave
				&& (isNew || po.is_ValueChanged(COLUMNNAME_C_BPartner_CompanyName) 
					|| po.is_ValueChanged(COLUMNNAME_C_BPartner_IsCompany)
					)
			)
		{
			if (true == po.get_ValueAsBoolean(COLUMNNAME_C_BPartner_IsCompany))
			{
				String companyName = po.get_ValueAsString(COLUMNNAME_C_BPartner_CompanyName);
				if (Check.isEmpty(companyName, true))
				{
					throw new FillMandatoryException(COLUMNNAME_C_BPartner_CompanyName);
				}
				((I_C_BPartner)po).setName(companyName);
			}
			else // c.ghita@metas.ro : start : US321
			{
				I_C_BPartner bp = (I_C_BPartner)po;
				MUser[] users = MUser.getOfBPartner(po.getCtx(), bp.getC_BPartner_ID(), po.get_TrxName());
				for (MUser user : users)
				{
					//set name from first default user
					if (true == user.get_ValueAsBoolean(COLUMNNAME_AD_User_IsDefault)) 
					{
						bp.setName(user.getName());
						po.set_ValueOfColumn(COLUMNNAME_C_BPartner_CompanyName, "");
						break;
					}
				}
			}
			// c.ghita@metas.ro : end : US321
		}
		//
		// AD_User: update C_BPartner if we set this as default contact
		// or we change the name of the contact and the BP is not company
		if (po instanceof I_AD_User
				&& isAfterSave
				&& (isNew || po.is_ValueChanged(COLUMNNAME_AD_User_IsDefault) || po
						.is_ValueChanged(I_AD_User.COLUMNNAME_Name))) {
			if (true == po.get_ValueAsBoolean(COLUMNNAME_AD_User_IsDefault)) {
				I_AD_User user = (I_AD_User) po;
				I_C_BPartner bp = user.getC_BPartner();
				if (false == ((PO) bp)
						.get_ValueAsBoolean(COLUMNNAME_C_BPartner_IsCompany)) {
					bp.setName(user.getName());
					save(bp);
				}
			}
		}

		return null;
	}

	@Override
	public String docValidate(PO po, int timing) {
		return null;
	}

	private static final String get_ValueAsString(PO po, String columnName) {
		Object o = po.get_Value(columnName);
		return o == null ? "" : o.toString();
	}

	private static final void save(Object po) {
		if (po instanceof PO) {
			((PO) po).saveEx();
		} else {
			throw new IllegalArgumentException(
					"Saving this object is not supported - " + po);
		}
	}
}
