package de.metas.javaclasses.impl;

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

import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaClassBLTests extends JavaClassTestBase
{
	public static interface TestInterface1
	{
	}

	public static class TestClass1 implements TestInterface1
	{
	}

	public static interface TestInterface2
	{
	}

	public static class TestClass2 implements TestInterface2
	{
	}

	public static class TestSuperClass3
	{

	}

	public static class TestClass3 extends TestSuperClass3
	{

	}

	private static class TestClass5 extends TestSuperClass3
	{

	}

	public static class TestClass4 extends TestSuperClass3
	{
		TestClass4()
		{
		}
	}

	public static interface TestInterface3 extends TestInterface2
	{
	}

	private JavaClassBL javaClassBL;

	@Override
	protected void setup()
	{
		javaClassBL = new JavaClassBL();

	}

	@Test
	public void test_newInstance_Interface()
	{
		final I_AD_JavaClass_Type typeDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass_Type.class, ITrx.TRXNAME_None);
		typeDef.setClassname(TestInterface1.class.getName());
		InterfaceWrapperHelper.save(typeDef);

		final I_AD_JavaClass javaClassDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass.class, ITrx.TRXNAME_None);
		javaClassDef.setAD_JavaClass_Type(typeDef);
		javaClassDef.setClassname(TestClass1.class.getName());
		InterfaceWrapperHelper.save(javaClassDef);

		final TestInterface1 obj = javaClassBL.newInstance(javaClassDef);
		assertThat(obj).as("Instance was not created").isNotNull();
	}

	@Test
	public void test_newInstance_SuperClass()
	{
		final I_AD_JavaClass_Type typeDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass_Type.class, ITrx.TRXNAME_None);
		typeDef.setClassname(TestSuperClass3.class.getName());
		InterfaceWrapperHelper.save(typeDef);

		final I_AD_JavaClass javaClassDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass.class, ITrx.TRXNAME_None);
		javaClassDef.setAD_JavaClass_Type(typeDef);
		javaClassDef.setClassname(TestClass3.class.getName());
		InterfaceWrapperHelper.save(javaClassDef);

		final TestSuperClass3 obj = javaClassBL.newInstance(javaClassDef);
		assertThat(obj).as("Instance was not created").isNotNull();
	}

	@Test(expected = AdempiereException.class)
	public void test_newInstance_invalidConfig()
	{
		final I_AD_JavaClass_Type typeDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass_Type.class, ITrx.TRXNAME_None);
		typeDef.setClassname(TestInterface1.class.getName());
		InterfaceWrapperHelper.save(typeDef);

		final I_AD_JavaClass javaClassDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass.class, ITrx.TRXNAME_None);
		javaClassDef.setAD_JavaClass_Type(typeDef);
		javaClassDef.setClassname(TestClass2.class.getName());
		InterfaceWrapperHelper.save(javaClassDef);

		javaClassBL.newInstance(javaClassDef);

	}

	@Test
	public void test_newInstance_no_type_classname()
	{
		final I_AD_JavaClass_Type typeDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass_Type.class, ITrx.TRXNAME_None);
		typeDef.setClassname(null);
		InterfaceWrapperHelper.save(typeDef);

		final I_AD_JavaClass javaClassDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass.class, ITrx.TRXNAME_None);
		javaClassDef.setAD_JavaClass_Type(typeDef);
		javaClassDef.setClassname(TestClass1.class.getName());
		InterfaceWrapperHelper.save(javaClassDef);

		javaClassBL.newInstance(javaClassDef);

		final TestInterface1 obj = javaClassBL.newInstance(javaClassDef);
		assertThat(obj).as("Instance was not created").isNotNull();
	}

	@Test
	public void test_newInstance_NonPublic_Constructor()
	{
		final I_AD_JavaClass_Type typeDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass_Type.class, ITrx.TRXNAME_None);
		typeDef.setClassname(TestSuperClass3.class.getName());
		InterfaceWrapperHelper.save(typeDef);

		final I_AD_JavaClass javaClassDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass.class, ITrx.TRXNAME_None);
		javaClassDef.setAD_JavaClass_Type(typeDef);
		javaClassDef.setClassname(TestClass4.class.getName());
		InterfaceWrapperHelper.save(javaClassDef);

		final Object instance = javaClassBL.newInstance(javaClassDef);
		assertThat(instance).isInstanceOf(TestClass4.class);
	}

	@Test(expected = AdempiereException.class)
	public void test_newInstance_Interface_Extending_Interface()
	{
		final I_AD_JavaClass_Type typeDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass_Type.class, ITrx.TRXNAME_None);
		typeDef.setClassname(TestInterface2.class.getName());
		InterfaceWrapperHelper.save(typeDef);

		final I_AD_JavaClass javaClassDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass.class, ITrx.TRXNAME_None);
		javaClassDef.setAD_JavaClass_Type(typeDef);
		javaClassDef.setClassname(TestInterface3.class.getName());
		InterfaceWrapperHelper.save(javaClassDef);

		javaClassBL.newInstance(javaClassDef);
	}

	@Test
	public void test_newInstance_PrivateClass()
	{
		final I_AD_JavaClass_Type typeDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass_Type.class, ITrx.TRXNAME_None);
		typeDef.setClassname(TestSuperClass3.class.getName());
		InterfaceWrapperHelper.save(typeDef);

		final I_AD_JavaClass javaClassDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass.class, ITrx.TRXNAME_None);
		javaClassDef.setAD_JavaClass_Type(typeDef);
		javaClassDef.setClassname(TestClass5.class.getName());
		InterfaceWrapperHelper.save(javaClassDef);

		final Object instance = javaClassBL.newInstance(javaClassDef);
		assertThat(instance).isInstanceOf(TestClass5.class);
	}
}
