/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.javaclasses.impl;

import de.metas.javaclasses.IJavaClassDAO;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JavaClassDAOTests extends JavaClassTestBase
{

	protected JavaClassDAO dao;

	@BeforeEach
	@Override
	public void init()
	{
		super.init();

		dao = new JavaClassDAO();
		Services.registerService(IJavaClassDAO.class, dao);
	}

	@Test
	public void test_retrieveJavaClasses()
	{
		final String trxName = ITrx.TRXNAME_None;

		// input:
		final I_AD_JavaClass_Type type = InterfaceWrapperHelper.create(ctx, I_AD_JavaClass_Type.class, trxName);

		InterfaceWrapperHelper.save(type);

		//testing

		final List<I_AD_JavaClass> javaClasses1 = dao.retrieveAllJavaClasses(type);

		Assertions.assertTrue(javaClasses1.isEmpty(), "List not empty");

		// input
		final I_AD_JavaClass javaClass = InterfaceWrapperHelper.create(ctx, I_AD_JavaClass.class, trxName);
		javaClass.setAD_JavaClass_Type_ID(type.getAD_JavaClass_Type_ID());

		InterfaceWrapperHelper.save(javaClass);

		// testing

		final List<I_AD_JavaClass> javaClasses = dao.retrieveAllJavaClasses(type);

		// output

		Assertions.assertTrue(javaClasses.contains(javaClass), "No class found for type id");
		Assertions.assertEquals(1, javaClasses.size(), "Size not equals one");

	}
}
