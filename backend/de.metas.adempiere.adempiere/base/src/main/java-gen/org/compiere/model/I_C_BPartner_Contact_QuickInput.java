/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.compiere.model;

// TODO: proper interface shall be generated. this is just a mocked prototype
public interface I_C_BPartner_Contact_QuickInput
{
	String Table_Name = "C_BPartner_Contact_QuickInput";

	String COLUMNNAME_C_BPartner_Contact_QuickInput_ID = "C_BPartner_Contact_QuickInput_ID";
	int getC_BPartner_Contact_QuickInput_ID();

	String COLUMNNAME_C_BPartner_QuickInput_ID = "C_BPartner_QuickInput_ID";
	int getC_BPartner_QuickInput_ID();

	String COLUMNNAME_Name = "Name";

	void setName(java.lang.String Name);

	java.lang.String getName();

	String COLUMNNAME_Firstname = "Firstname";

	java.lang.String getFirstname();

	String COLUMNNAME_Lastname = "Lastname";

	void setLastname(java.lang.String Lastname);

	java.lang.String getLastname();

	String COLUMNNAME_C_Greeting_ID = "C_Greeting_ID";

	int getC_Greeting_ID();

	String COLUMNNAME_IsMembershipContact = "IsMembershipContact";

	boolean isMembershipContact();
}
