package de.metas.adempiere.util.cache;

/*
 * #%L
 * de.metas.fresh.base
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


import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.proxy.Cached;
import org.junit.Before;
import org.junit.Test;

import de.metas.cache.interceptor.ClasspathCachedMethodsTester;

/**
 * {@link Cached} integration test: makes sure all classes are valid
 * 
 * @author tsa
 *
 */
public class Fresh_AllCachedMethodsTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test()
	{
		new ClasspathCachedMethodsTester()
				// Classes which does not implement services => those won't be cached
				// FIXME: we have to fix the underlying problem
				.skipIfClassnameStartsWith("org.compiere.model.MReference")
				.skipIfClassnameStartsWith("org.compiere.model.M_Element")
				.skipIfClassnameStartsWith("de.metas.adempiere.form.terminal.PropertiesPanelModelConfigurator")
				.skipIfClassnameStartsWith("org.adempiere.model.MFreightCost")
				.skipIfClassnameStartsWith("de.metas.dpd.model.MDPDDepot")
				.skipIfClassnameStartsWith("de.metas.dpd.model.MDPDCountry")
				.skipIfClassnameStartsWith("de.metas.dpd.model.MDPDScanCode")
				.skipIfClassnameStartsWith("de.metas.dpd.model.MDPDFileInfo")
				.skipIfClassnameStartsWith("de.metas.banking.misc.ImportBankstatementCtrl")
				.skipIfClassnameStartsWith("de.metas.handlingunits.client.terminal.editor.model.impl.HUKeyNameBuilder")
				// Legacy code
				.skipIfClassnameStartsWith("de.metas.dpd.process.ImportStatusData")
				// Tests:
				.skipIfClassnameStartsWith("de.metas.cache.interceptor.testservices.")
				.skipIfClassnameStartsWith("de.metas.cache.interceptor.CachedMethodDescriptorTest$TestClass")
				//
				.test();
	}
}
