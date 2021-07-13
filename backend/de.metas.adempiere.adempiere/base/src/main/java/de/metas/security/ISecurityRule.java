package de.metas.security;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Set;

import de.metas.organization.OrgId;
import de.metas.security.permissions.Access;

import javax.annotation.Nullable;

/**
 * Security Rule: it's called by {@link IUserRolePermissions} to fine tune some permissions before giving an answer to caller.
 * 
 * To register your rule, please use {@link ISecurityRuleEngine#registerRule(ISecurityRule)}.
 * 
 * @author tsa
 * 
 */
public interface ISecurityRule
{
	/**
	 * Initializes this rule.
	 * 
	 * NOTE: don't call it directly, it will be called by API one time
	 */
	void init();

	/**
	 * Filters <code>orgIds</code> list leaving there only those orgs on which <code>role</code> in given <code>ctx</code> has readonly or readwrite access (depends on <code>readWrite</code>) flag.
	 * 
	 * @param tableName TableName on which the org filter shall be applied or null if the request is not specific to any table name
	 */
	void filterOrgs(IUserRolePermissions rolePermissions, @Nullable String tableName, Access access, Set<OrgId> orgIds);
}
