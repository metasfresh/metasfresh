package org.adempiere.appdict.impl;

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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.appdict.IJavaClassDAO;
import org.adempiere.appdict.model.I_AD_JavaClass;
import org.adempiere.appdict.model.I_AD_JavaClass_Type;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;

import de.metas.adempiere.util.CacheCtx;

public class JavaClassDAO implements IJavaClassDAO
{
	@Override
	public List<I_AD_JavaClass> retrieveJavaClasses(final I_AD_JavaClass_Type type)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_JavaClass.class, type)
				.filter(new EqualsQueryFilter<I_AD_JavaClass>(I_AD_JavaClass.COLUMNNAME_AD_JavaClass_Type_ID, type.getAD_JavaClass_Type_ID()))
				.create()
				.list(I_AD_JavaClass.class);
	}

	@Override
	@Cached(cacheName = I_AD_JavaClass.Table_Name + "#by#" + I_AD_JavaClass.COLUMNNAME_AD_JavaClass_ID)
	public I_AD_JavaClass retriveJavaClassOrNull(
			@CacheCtx final Properties ctx,
			final int adJavaClassId)
	{
		if (adJavaClassId <= 0)
		{
			return null;
		}
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_JavaClass.class, ctx, ITrx.TRXNAME_None)
				.filter(new EqualsQueryFilter<I_AD_JavaClass>(I_AD_JavaClass.COLUMNNAME_AD_JavaClass_ID, adJavaClassId))
				.create()
				.firstOnly(I_AD_JavaClass.class);
	}

	@Override
	@Cached(cacheName = I_AD_JavaClass_Type.Table_Name + "#by#" + I_AD_JavaClass_Type.COLUMNNAME_AD_JavaClass_Type_ID)
	public I_AD_JavaClass_Type retrieveJavaClassTypeOrNull(@CacheCtx final Properties ctx, final int adJavaClassTypeId)
	{
		if (adJavaClassTypeId <= 0)
		{
			return null;
		}
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_JavaClass_Type.class, ctx, ITrx.TRXNAME_None)
				.filter(new EqualsQueryFilter<I_AD_JavaClass_Type>(I_AD_JavaClass_Type.COLUMNNAME_AD_JavaClass_Type_ID, adJavaClassTypeId))
				.create()
				.firstOnly(I_AD_JavaClass_Type.class);

	}

	@Override
	public I_AD_JavaClass_Type retrieveJavaClassTypeOrNull(I_AD_JavaClass javaClassDef)
	{
		Check.assumeNotNull(javaClassDef, "javaClassDef not null");
		final Properties ctx = InterfaceWrapperHelper.getCtx(javaClassDef);
		final int adJavaClassTypeId = javaClassDef.getAD_JavaClass_Type_ID();
		
		return retrieveJavaClassTypeOrNull(ctx, adJavaClassTypeId);
	}

}
