package org.adempiere.ad.callout.spi.impl;

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

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.api.TableCalloutsMap;
import org.adempiere.ad.callout.api.impl.MethodNameCalloutInstance;
import org.adempiere.ad.callout.api.impl.MockedCalloutField;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_ColumnCallout;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DefaultCalloutProviderTest
{
	public static abstract class AbstractMockedCallout extends CalloutEngine
	{
		public AbstractMockedCallout()
		{
			super();
		}

		public AbstractMockedCallout(final boolean throwInitExceptionOnCtor)
		{
			this();
			if (throwInitExceptionOnCtor)
			{
				throw new CalloutInitException("Programatic init exception");
			}
		}

		public static final String METHOD_method1 = "method1";

		public String method1(final ICalloutField calloutField)
		{
			return NO_ERROR;
		}

		public static final String METHOD_method2 = "method2";

		public String method2(final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value)
		{
			return NO_ERROR;
		}

		public static final String METHOD_method3 = "method3";

		public String method3(final Properties ctx, final int WindowNo, final GridTab mTab, final GridField mField, final Object value, final Object valueOld)
		{
			return NO_ERROR;
		}
	}

	public static class Callout1 extends AbstractMockedCallout
	{
	};

	public static class Callout2 extends AbstractMockedCallout
	{
	};

	public static class Callout3 extends AbstractMockedCallout
	{
	};

	public static class Callout4 extends AbstractMockedCallout
	{
	}

	public static class Callout_FailOnInit extends AbstractMockedCallout
	{
		public Callout_FailOnInit()
		{
			super(true);
		}
	}

	@Rule
	public final AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	private DefaultCalloutProvider calloutsProvider;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		calloutsProvider = new DefaultCalloutProvider();
	}

	@Test
	public void test_StandardCase()
	{
		final MockedCalloutField field = MockedCalloutField.createNewField();

		field.createAD_ColumnCallout(Callout1.class, AbstractMockedCallout.METHOD_method1);
		field.createAD_ColumnCallout(Callout1.class, AbstractMockedCallout.METHOD_method2);
		field.createAD_ColumnCallout(Callout1.class, AbstractMockedCallout.METHOD_method3);
		//
		field.createAD_ColumnCallout(Callout2.class, AbstractMockedCallout.METHOD_method1);
		field.createAD_ColumnCallout(Callout3.class, AbstractMockedCallout.METHOD_method1);
		field.createAD_ColumnCallout(Callout4.class, AbstractMockedCallout.METHOD_method1);

		final TableCalloutsMap tableCallouts = calloutsProvider.getCallouts(field.getCtx(), field.getTableName());
		final List<ICalloutInstance> calloutInstances = tableCallouts.getColumnCallouts(field.getColumnName());
		Assert.assertEquals("Invalid callouts list size: " + calloutInstances, 6, calloutInstances.size());

		assertLegacyCalloutInstance(calloutInstances.get(0), Callout1.class, AbstractMockedCallout.METHOD_method1);
		assertLegacyCalloutInstance(calloutInstances.get(1), Callout1.class, AbstractMockedCallout.METHOD_method2);
		assertLegacyCalloutInstance(calloutInstances.get(2), Callout1.class, AbstractMockedCallout.METHOD_method3);
		//
		assertLegacyCalloutInstance(calloutInstances.get(3), Callout2.class, AbstractMockedCallout.METHOD_method1);
		assertLegacyCalloutInstance(calloutInstances.get(4), Callout3.class, AbstractMockedCallout.METHOD_method1);
		assertLegacyCalloutInstance(calloutInstances.get(5), Callout4.class, AbstractMockedCallout.METHOD_method1);
	}

	@Test
	public void test_NoCallouts()
	{
		final MockedCalloutField field = MockedCalloutField.createNewField();

		final TableCalloutsMap tableCallouts = calloutsProvider.getCallouts(field.getCtx(), field.getTableName());
		;
		final List<ICalloutInstance> calloutInstances = tableCallouts.getColumnCallouts(field.getColumnName());
		Assert.assertEquals("Invalid callouts list size: " + calloutInstances, 0, calloutInstances.size());
	}

	@Test
	public void test_OnlyInactiveCallouts()
	{
		final MockedCalloutField field = MockedCalloutField.createNewField();

		final I_AD_ColumnCallout cc1 = field.createAD_ColumnCallout(Callout1.class, AbstractMockedCallout.METHOD_method1);
		cc1.setIsActive(false);
		InterfaceWrapperHelper.save(cc1);

		final TableCalloutsMap tableCallouts = calloutsProvider.getCallouts(field.getCtx(), field.getTableName());
		final List<ICalloutInstance> calloutInstances = tableCallouts.getColumnCallouts(field.getColumnName());
		Assert.assertEquals("Invalid callouts list size: " + calloutInstances, 0, calloutInstances.size());
	}

	@Test
	public void test_StandardCase_OneCalloutFailsOnInit()
	{
		final MockedCalloutField field = MockedCalloutField.createNewField();

		field.createAD_ColumnCallout(Callout1.class, AbstractMockedCallout.METHOD_method1);
		field.createAD_ColumnCallout(Callout2.class, AbstractMockedCallout.METHOD_method1);
		field.createAD_ColumnCallout(Callout_FailOnInit.class, AbstractMockedCallout.METHOD_method1);
		field.createAD_ColumnCallout(Callout4.class, AbstractMockedCallout.METHOD_method1);

		final TableCalloutsMap tableCallouts = calloutsProvider.getCallouts(field.getCtx(), field.getTableName());
		final List<ICalloutInstance> calloutInstances = tableCallouts.getColumnCallouts(field.getColumnName());
		Assert.assertEquals("Invalid callouts list size: " + calloutInstances, 3, calloutInstances.size());

		assertLegacyCalloutInstance(calloutInstances.get(0), Callout1.class, AbstractMockedCallout.METHOD_method1);
		assertLegacyCalloutInstance(calloutInstances.get(1), Callout2.class, AbstractMockedCallout.METHOD_method1);
		assertLegacyCalloutInstance(calloutInstances.get(2), Callout4.class, AbstractMockedCallout.METHOD_method1);
	}

	@Test
	public void test_DuplicateCallouts()
	{
		final MockedCalloutField field = MockedCalloutField.createNewField();

		field.createAD_ColumnCallout(Callout1.class, AbstractMockedCallout.METHOD_method1);
		field.createAD_ColumnCallout(Callout1.class, AbstractMockedCallout.METHOD_method1);
		field.createAD_ColumnCallout(Callout2.class, AbstractMockedCallout.METHOD_method1);

		final TableCalloutsMap tableCallouts = calloutsProvider.getCallouts(field.getCtx(), field.getTableName());
		testWatcher.putContext(tableCallouts);

		final List<ICalloutInstance> calloutInstances = tableCallouts.getColumnCallouts(field.getColumnName());
		Assert.assertEquals("Invalid callouts list size: " + calloutInstances, 2, calloutInstances.size());

		assertLegacyCalloutInstance(calloutInstances.get(0), Callout1.class, AbstractMockedCallout.METHOD_method1);
		assertLegacyCalloutInstance(calloutInstances.get(1), Callout2.class, AbstractMockedCallout.METHOD_method1);
	}

	private void assertLegacyCalloutInstance(final ICalloutInstance calloutInstance, final Class<?> calloutClass, final String methodName)
	{
		Assert.assertNotNull("calloutInstance not null", calloutInstance);
		Assert.assertTrue("calloutInstance is not instanceof " + MethodNameCalloutInstance.class + ": " + calloutInstance,
				calloutInstance instanceof MethodNameCalloutInstance);

		final MethodNameCalloutInstance methodnameCallout = (MethodNameCalloutInstance)calloutInstance;
		@SuppressWarnings("deprecation")
		final org.compiere.model.Callout legacyCallout = methodnameCallout.getLegacyCallout();

		final Class<?> calloutClassActual = legacyCallout.getClass();
		Assert.assertTrue("Callout class is not assignable from " + calloutClass + ": " + calloutClassActual,
				calloutClass.isAssignableFrom(calloutClassActual));

		Assert.assertEquals("LegacyCalloutAdapter is not using the right method: " + legacyCallout, methodName, methodnameCallout.getMethodName());
	}
}
