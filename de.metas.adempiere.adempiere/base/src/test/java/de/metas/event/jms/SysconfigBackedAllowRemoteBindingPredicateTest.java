package de.metas.event.jms;

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


import static de.metas.event.jms.SysconfigBackedAllowRemoteBindingPredicate.AnyLoginUserId;
import static de.metas.event.jms.SysconfigBackedAllowRemoteBindingPredicate.AnyTopicName;
import static de.metas.event.jms.SysconfigBackedAllowRemoteBindingPredicate.SYSCONFIG_PREFIX_AllowedSubscriptions;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SysconfigBackedAllowRemoteBindingPredicateTest
{
	private static final boolean ALLOW = true;
	private static final boolean DISALLOW = false;

	private SysconfigBackedAllowRemoteBindingPredicate allowPredicate;
	private int loginUserId;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		allowPredicate = new SysconfigBackedAllowRemoteBindingPredicate();
		loginUserId = Env.getAD_User_ID(Env.getCtx());
	}

	@Test
	public void test_NoRules()
	{
		testAllow(ALLOW, "topic1");
	}

	@Test
	public void test_DirectRule_Allow()
	{
		createMatchingRule("topic1", loginUserId, ALLOW);
		testAllow(ALLOW, "topic1");
	}

	@Test
	public void test_DirectRule_Disallow()
	{
		createMatchingRule("topic1", loginUserId, DISALLOW);
		testAllow(DISALLOW, "topic1");
	}

	@Test
	public void test_AllowOneTopic_DisallowOthers()
	{
		createMatchingRule("topic1", loginUserId, ALLOW);
		createMatchingRule(AnyTopicName, AnyLoginUserId, DISALLOW);
		testAllow(ALLOW, "topic1");
		testAllow(DISALLOW, "topic2");
	}

	private final void testAllow(final boolean allowExpected, final String topicName)
	{
		final boolean allowActual = allowPredicate.applyForTopicNameAndLoginUser(topicName);
		Assert.assertEquals("Invalid expectation for topic=" + topicName, allowExpected, allowActual);

	}

	private void createMatchingRule(final String topicName, final int userId, final boolean allow)
	{
		final int adOrgId = 0;

		final String selector = SysconfigBackedAllowRemoteBindingPredicate.createSelector(topicName, userId);
		Services.get(ISysConfigBL.class).setValue(SYSCONFIG_PREFIX_AllowedSubscriptions + selector, allow, adOrgId);
	}
}
