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

import org.adempiere.ad.callout.api.ICallout;
import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.ad.callout.api.impl.DefaultCalloutInstance;
import org.adempiere.ad.callout.api.impl.PlainCalloutField;
import org.adempiere.ad.callout.api.impl.legacy.LegacyCalloutAdapter;
import org.adempiere.ad.callout.exceptions.CalloutInitException;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_ColumnCallout;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultCalloutProviderTest
{
	public static abstract class AbstractMockedCallout implements ICallout
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

		@Override
		public void onFieldChanged(ICalloutExecutor executor, ICalloutField field)
		{
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

	public static class LegacyMockedCallout extends CalloutEngine
	{
		public static final String METHOD_Method1 = "method1";

		public String method1(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
		{
			return "";
		}

	}

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
		final PlainCalloutField field = new PlainCalloutField();
		field.setAD_Column_ID(POJOLookupMap.get().nextId(I_AD_Column.Table_Name));

		createAD_ColumnCallout(field, Callout1.class);
		createAD_ColumnCallout(field, Callout2.class);
		createAD_ColumnCallout(field, Callout3.class);
		createAD_ColumnCallout(field, Callout4.class);

		final List<ICalloutInstance> calloutInstances = calloutsProvider.getCallouts(field);
		Assert.assertEquals("Invalid callouts list size: " + calloutInstances, 4, calloutInstances.size());

		assertDefaultCalloutInstance(calloutInstances.get(0), Callout1.class);
		assertDefaultCalloutInstance(calloutInstances.get(1), Callout2.class);
		assertDefaultCalloutInstance(calloutInstances.get(2), Callout3.class);
		assertDefaultCalloutInstance(calloutInstances.get(3), Callout4.class);
	}

	@Test
	public void test_StandardCase_WithLegacyCallouts()
	{
		final PlainCalloutField field = new PlainCalloutField();
		field.setAD_Column_ID(POJOLookupMap.get().nextId(I_AD_Column.Table_Name));

		createAD_ColumnCallout(field, Callout1.class);
		createAD_ColumnCallout(field, Callout2.class);
		createAD_ColumnCallout(field, LegacyMockedCallout.class.getName() + "." + LegacyMockedCallout.METHOD_Method1);
		createAD_ColumnCallout(field, Callout3.class);
		createAD_ColumnCallout(field, Callout4.class);

		final List<ICalloutInstance> calloutInstances = calloutsProvider.getCallouts(field);
		Assert.assertEquals("Invalid callouts list size: " + calloutInstances, 5, calloutInstances.size());

		assertDefaultCalloutInstance(calloutInstances.get(0), Callout1.class);
		assertDefaultCalloutInstance(calloutInstances.get(1), Callout2.class);
		assertLegacyCalloutInstance(calloutInstances.get(2), LegacyMockedCallout.class, LegacyMockedCallout.METHOD_Method1);
		assertDefaultCalloutInstance(calloutInstances.get(3), Callout3.class);
		assertDefaultCalloutInstance(calloutInstances.get(4), Callout4.class);
	}

	@Test
	public void test_NoCallouts()
	{
		final PlainCalloutField field = new PlainCalloutField();
		field.setAD_Column_ID(POJOLookupMap.get().nextId(I_AD_Column.Table_Name));

		final List<ICalloutInstance> calloutInstances = calloutsProvider.getCallouts(field);
		Assert.assertEquals("Invalid callouts list size: " + calloutInstances, 0, calloutInstances.size());
	}

	@Test
	public void test_OnlyInactiveCallouts()
	{
		final PlainCalloutField field = new PlainCalloutField();
		field.setAD_Column_ID(POJOLookupMap.get().nextId(I_AD_Column.Table_Name));

		final I_AD_ColumnCallout cc1 = createAD_ColumnCallout(field, Callout1.class);
		cc1.setIsActive(false);
		InterfaceWrapperHelper.save(cc1);

		final List<ICalloutInstance> calloutInstances = calloutsProvider.getCallouts(field);
		Assert.assertEquals("Invalid callouts list size: " + calloutInstances, 0, calloutInstances.size());
	}

	@Test
	public void test_StandardCase_OneCalloutFailsOnInit()
	{
		final PlainCalloutField field = new PlainCalloutField();
		field.setAD_Column_ID(POJOLookupMap.get().nextId(I_AD_Column.Table_Name));

		createAD_ColumnCallout(field, Callout1.class);
		createAD_ColumnCallout(field, Callout2.class);
		createAD_ColumnCallout(field, Callout_FailOnInit.class);
		createAD_ColumnCallout(field, Callout4.class);

		final List<ICalloutInstance> calloutInstances = calloutsProvider.getCallouts(field);
		Assert.assertEquals("Invalid callouts list size: " + calloutInstances, 3, calloutInstances.size());

		assertDefaultCalloutInstance(calloutInstances.get(0), Callout1.class);
		assertDefaultCalloutInstance(calloutInstances.get(1), Callout2.class);
		assertDefaultCalloutInstance(calloutInstances.get(2), Callout4.class);
	}

	@Test
	public void test_DuplicateCallouts()
	{
		final PlainCalloutField field = new PlainCalloutField();
		field.setAD_Column_ID(POJOLookupMap.get().nextId(I_AD_Column.Table_Name));

		createAD_ColumnCallout(field, Callout1.class);
		createAD_ColumnCallout(field, Callout1.class);
		createAD_ColumnCallout(field, Callout2.class);

		final List<ICalloutInstance> calloutInstances = calloutsProvider.getCallouts(field);
		Assert.assertEquals("Invalid callouts list size: " + calloutInstances, 2, calloutInstances.size());

		assertDefaultCalloutInstance(calloutInstances.get(0), Callout1.class);
		assertDefaultCalloutInstance(calloutInstances.get(1), Callout2.class);
	}

	private I_AD_ColumnCallout createAD_ColumnCallout(final ICalloutField field, final Class<?> calloutClass)
	{
		final String calloutClassName = calloutClass.getName();
		return createAD_ColumnCallout(field, calloutClassName);
	}

	private I_AD_ColumnCallout createAD_ColumnCallout(final ICalloutField field, final String calloutClassName)
	{
		final I_AD_ColumnCallout cc = InterfaceWrapperHelper.create(field.getCtx(), I_AD_ColumnCallout.class, ITrx.TRXNAME_None);
		cc.setAD_Column_ID(field.getAD_Column_ID());
		cc.setClassname(calloutClassName);
		cc.setSeqNo(0);
		cc.setIsActive(true);
		InterfaceWrapperHelper.save(cc);
		return cc;
	}

	private void assertDefaultCalloutInstance(final ICalloutInstance calloutInstance, final Class<?> calloutClass)
	{
		Assert.assertNotNull("calloutInstance not null", calloutInstance);
		Assert.assertTrue("calloutInstance is not instanceof " + DefaultCalloutInstance.class + ": " + calloutInstance,
				calloutInstance instanceof DefaultCalloutInstance);

		final DefaultCalloutInstance defaultCalloutInstance = (DefaultCalloutInstance)calloutInstance;
		final ICallout callout = defaultCalloutInstance.getCallout();

		final Class<?> calloutClassActual = callout.getClass();
		Assert.assertTrue("Callout class is not assignable from " + calloutClass + ": " + calloutClassActual,
				calloutClass.isAssignableFrom(calloutClassActual));
	}

	private void assertLegacyCalloutInstance(final ICalloutInstance calloutInstance, final Class<?> calloutClass, String methodName)
	{
		Assert.assertNotNull("calloutInstance not null", calloutInstance);
		Assert.assertTrue("calloutInstance is not instanceof " + DefaultCalloutInstance.class + ": " + calloutInstance,
				calloutInstance instanceof DefaultCalloutInstance);

		final DefaultCalloutInstance defaultCalloutInstance = (DefaultCalloutInstance)calloutInstance;
		final ICallout callout = defaultCalloutInstance.getCallout();

		Assert.assertTrue("callout is not instanceof " + LegacyCalloutAdapter.class + ": " + calloutInstance,
				callout instanceof LegacyCalloutAdapter);
		final LegacyCalloutAdapter legacyCallout = (LegacyCalloutAdapter)callout;

		Assert.assertEquals("LegacyCalloutAdapter is not wrapping the right class: " + legacyCallout,
				calloutClass, legacyCallout.getCallout().getClass());

		Assert.assertEquals("LegacyCalloutAdapter is not using the right method: " + legacyCallout,
				methodName, legacyCallout.getMethodName());
	}
}
