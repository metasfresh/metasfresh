package de.metas.acct.process;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.acct.process.Fact_Acct_ActivityChangeRequest_Process.IActivityAware;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class Fact_Acct_ActivityChangeRequest_Process_Test
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testImplements_IActivityAware()
	{
		for (final Class<?> lineModelClass : Fact_Acct_ActivityChangeRequest_Process.headerTableName2lineModelClass.values())
		{
			assertClassImplements(lineModelClass, IActivityAware.class);
		}
	}

	private final <T> T createDummyRecord(final Class<T> modelClass)
	{
		final T record = InterfaceWrapperHelper.create(Env.getCtx(), modelClass, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private final void assertClassImplements(final Class<?> modelClass, final Class<?> interfaceClass)
	{
		final Object model = createDummyRecord(modelClass);
		assertModelImplements(model, interfaceClass);
	}

	private final void assertModelImplements(final Object model, final Class<?> interfaceClass)
	{
		final Object modelWrapped = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(model, interfaceClass);
		assertThat(modelWrapped)
				.withFailMessage("Model shall implement implement " + interfaceClass)
				.isNotNull();
	}
}
