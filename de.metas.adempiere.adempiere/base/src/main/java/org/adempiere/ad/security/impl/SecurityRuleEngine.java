package org.adempiere.ad.security.impl;

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


import java.util.Set;

import org.adempiere.ad.security.ISecurityRuleEngine;
import org.adempiere.ad.security.ISecurityRule;
import org.adempiere.ad.security.IUserRolePermissions;

public class SecurityRuleEngine implements ISecurityRuleEngine
{
	final CompositeSecurityRule compositeRule = new CompositeSecurityRule();

	@Override
	public void registerRule(final ISecurityRule rule)
	{
		compositeRule.addRule(rule);
	}

	@Override
	public void filterOrgs(final IUserRolePermissions rolePermissions, final String tableName, final boolean rw, final Set<Integer> orgIds)
	{
		compositeRule.filterOrgs(rolePermissions, tableName, rw, orgIds);
	}

	@Override
	public String toString()
	{
		return "SecurityEngine[" + compositeRule + "]";
	}

	@Override
	public Boolean checkFormAccess(final IUserRolePermissions rolePermissions, final Boolean retValue, final int AD_Form_ID)
	{
		return compositeRule.hasFormAccess(rolePermissions, retValue, AD_Form_ID);
	}

}
