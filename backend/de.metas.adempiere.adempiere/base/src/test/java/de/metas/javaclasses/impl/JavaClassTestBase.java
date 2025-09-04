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

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Properties;

@ExtendWith(AdempiereTestWatcher.class)
public class JavaClassTestBase
{
	@BeforeAll
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
		POJOWrapper.setDefaultStrictValues(false);
	}

	protected Properties ctx;
	protected IContextAware ctxAware;

	@BeforeEach
	public void init()
	{
		ctx = Env.getCtx();
		ctxAware = new PlainContextAware(ctx);

		AdempiereTestHelper.get().init();

		// setupMasterData();

		setup();
	}

	protected void setup()
	{
		// nothing to do

	}

	protected Properties getCtx()
	{
		return ctx;
	}

}
