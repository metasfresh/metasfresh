<<<<<<< HEAD
package de.metas.javaclasses.impl;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
<<<<<<< HEAD
 * Copyright (C) 2015 metas GmbH
=======
 * Copyright (C) 2024 metas GmbH
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
<<<<<<< HEAD
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
=======
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

<<<<<<< HEAD

=======
package de.metas.javaclasses.impl;

import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
<<<<<<< HEAD
import org.junit.Assert;
import org.junit.Test;

import de.metas.javaclasses.impl.JavaClassBL;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;

=======
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
		Assert.assertNotNull("Instance was not created", obj);
=======
		assertThat(obj).as("Instance was not created").isNotNull();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		Assert.assertNotNull("Instance was not created", obj);
	}
=======
		assertThat(obj).as("Instance was not created").isNotNull();
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		
=======

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		
		final TestInterface1 obj = javaClassBL.newInstance(javaClassDef);
		Assert.assertNotNull("Instance was not created", obj);
	}
	
	@Test (expected = Exception.class)
=======

		final TestInterface1 obj = javaClassBL.newInstance(javaClassDef);
		assertThat(obj).as("Instance was not created").isNotNull();
	}

	@Test
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public void test_newInstance_NonPublic_Constructor()
	{
		final I_AD_JavaClass_Type typeDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass_Type.class, ITrx.TRXNAME_None);
		typeDef.setClassname(TestSuperClass3.class.getName());
		InterfaceWrapperHelper.save(typeDef);

		final I_AD_JavaClass javaClassDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass.class, ITrx.TRXNAME_None);
		javaClassDef.setAD_JavaClass_Type(typeDef);
		javaClassDef.setClassname(TestClass4.class.getName());
		InterfaceWrapperHelper.save(javaClassDef);

<<<<<<< HEAD
		javaClassBL.newInstance(javaClassDef);
	}
	
	@Test (expected = AdempiereException.class)
=======
		final Object instance = javaClassBL.newInstance(javaClassDef);
		assertThat(instance).isInstanceOf(TestClass4.class);
	}

	@Test(expected = AdempiereException.class)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	
	@Test (expected = AdempiereException.class)
=======

	@Test
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public void test_newInstance_PrivateClass()
	{
		final I_AD_JavaClass_Type typeDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass_Type.class, ITrx.TRXNAME_None);
		typeDef.setClassname(TestSuperClass3.class.getName());
		InterfaceWrapperHelper.save(typeDef);

		final I_AD_JavaClass javaClassDef = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_JavaClass.class, ITrx.TRXNAME_None);
		javaClassDef.setAD_JavaClass_Type(typeDef);
		javaClassDef.setClassname(TestClass5.class.getName());
		InterfaceWrapperHelper.save(javaClassDef);

<<<<<<< HEAD
		javaClassBL.newInstance(javaClassDef);
=======
		final Object instance = javaClassBL.newInstance(javaClassDef);
		assertThat(instance).isInstanceOf(TestClass5.class);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
