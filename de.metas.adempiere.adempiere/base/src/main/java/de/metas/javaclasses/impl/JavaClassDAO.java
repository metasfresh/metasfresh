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

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.proxy.Cached;

import de.metas.cache.annotation.CacheCtx;
import de.metas.javaclasses.IJavaClassDAO;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.util.Services;

public class JavaClassDAO implements IJavaClassDAO
{
	@Override
	public List<I_AD_JavaClass> retrieveAllJavaClasses(final I_AD_JavaClass_Type type)
	{
		final int javaClassTypeID = type.getAD_JavaClass_Type_ID();
		final Properties ctx = InterfaceWrapperHelper.getCtx(type);

		return retrieveAllJavaClasses(ctx, javaClassTypeID);
	}

	@Cached(cacheName = I_AD_JavaClass.Table_Name + "#by#" + I_AD_JavaClass.COLUMNNAME_AD_JavaClass_Type_ID)
	/* package */ List<I_AD_JavaClass> retrieveAllJavaClasses(final @CacheCtx Properties ctx, final int javaClassTypeID)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_JavaClass.class, new PlainContextAware(ctx))
				.addEqualsFilter(I_AD_JavaClass.COLUMNNAME_AD_JavaClass_Type_ID, javaClassTypeID)
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
	public List<I_AD_JavaClass> retrieveJavaClasses(
			@CacheCtx final Properties ctx,
			final String javaClassTypeInternalName)
	{
		if (javaClassTypeInternalName == null)
		{
			return Collections.emptyList();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_AD_JavaClass_Type.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_JavaClass_Type.COLUMNNAME_InternalName, javaClassTypeInternalName)
				.andCollectChildren(I_AD_JavaClass.COLUMN_AD_JavaClass_Type_ID, I_AD_JavaClass.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	@Override
	@Cached(cacheName = I_AD_JavaClass_Type.Table_Name + "#by#" + I_AD_JavaClass_Type.COLUMNNAME_InternalName)
	public I_AD_JavaClass_Type retrieveJavaClassTypeOrNull(
			@CacheCtx final Properties ctx,
			final String internalName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_AD_JavaClass_Type.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_JavaClass_Type.COLUMNNAME_InternalName, internalName)
				.create()
				.firstOnly(I_AD_JavaClass_Type.class);
	}
}
