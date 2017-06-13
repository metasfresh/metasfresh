package de.metas.adempiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.compiere.model.I_AD_Org;

public interface I_AD_Role extends org.compiere.model.I_AD_Role
{
	// @formatter:off
	public static final String COLUMNNAME_IsAutoRoleLogin = "IsAutoRoleLogin";
	@Override
	public void setIsAutoRoleLogin(boolean IsAutoRoleLogin);
	@Override
	public boolean isAutoRoleLogin();

	public static final String COLUMNNAME_IsOrgLoginMandatory = "IsOrgLoginMandatory";
	public void setIsOrgLoginMandatory(boolean IsOrgLoginMandatory);
	public boolean isOrgLoginMandatory();

	public static final String COLUMNNAME_Login_Org_ID = "Login_Org_ID";
	public void setLogin_Org_ID(int Login_Org_ID);
	public void setLogin_Org(I_AD_Org Login_Org);
	public int getLogin_Org_ID();
	public I_AD_Org getLogin_Org();

	public static final String COLUMNNAME_IsAllowedTrlBox = "IsAllowedTrlBox";
	public boolean isAllowedTrlBox();
	public void setIsAllowedTrlBox(boolean isAllowedTrlBox);

	public static final String COLUMNNAME_IsAllowedInvoicingPriority = "IsAllowedInvoicingPriority";
	public boolean isAllowedInvoicingPriority();
	public void setIsAllowedInvoicingPriority(boolean IsAllowedInvoicingPriority);

	public static final String COLUMNNAME_IsAllowedMigrationScripts = "IsAllowedMigrationScripts";
	public boolean isAllowedMigrationScripts();
	public void setIsAllowedMigrationScripts(boolean IsAllowedMigrationScripts);
	// @formatter:on
}
