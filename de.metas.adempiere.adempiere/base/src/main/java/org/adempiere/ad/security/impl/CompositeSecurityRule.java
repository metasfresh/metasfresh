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
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;

import org.adempiere.ad.security.ISecurityRule;
import org.adempiere.ad.security.IUserRolePermissions;

final class CompositeSecurityRule implements ISecurityRule
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private final CopyOnWriteArrayList<ISecurityRule> rulesAll = new CopyOnWriteArrayList<ISecurityRule>();
	private final CopyOnWriteArrayList<ISecurityRule> rulesActive = new CopyOnWriteArrayList<ISecurityRule>();

	/**
	 * Add a new child rule and initialize it automatically.
	 *
	 * @param rule
	 * @return true if rule was added
	 */
	public boolean addRule(final ISecurityRule rule)
	{
		Check.assumeNotNull(rule, "Param 'rule' not null");

		if (!rulesAll.addIfAbsent(rule))
		{
			return false;
		}

		init(rule);

		return true;
	}

	public boolean isInitialized(final ISecurityRule rule)
	{
		return rulesActive.contains(rule);
	}

	private void init(final ISecurityRule rule)
	{
		try
		{
			rule.init();
			rulesActive.add(rule);
		}
		catch (final Exception e)
		{
			logger.error("Error while initializing " + rule + ": " + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void init()
	{
		// rulesActive added to this composite are automatically initialized
		// nothing to do here
	}

	@Override
	public void filterOrgs(final IUserRolePermissions rolePermissions, final String tableName, final boolean rw, final Set<Integer> orgIds)
	{
		for (final ISecurityRule rule : rulesActive)
		{
			rule.filterOrgs(rolePermissions, tableName, rw, orgIds);
		}
	}

	@Override
	public String toString()
	{
		return "CompositeSecurityRule[" + rulesActive.toString() + "]";
	}

	@Override
	public Boolean hasFormAccess(final IUserRolePermissions rolePermissions, final Boolean roleAccess, final int AD_Form_ID)
	{
		// No rules were added to this composite rule => we are returing given parameter
		if (rulesActive.isEmpty())
		{
			return roleAccess;
		}

		Boolean result = Boolean.TRUE;
		for (final ISecurityRule rule : rulesActive)
		{
			final Boolean ruleAccess = rule.hasFormAccess(rolePermissions, roleAccess, AD_Form_ID);
			if (null == ruleAccess)
			{
				return null;
			}
			result = result && ruleAccess;
		}
		return result;
	}
}
