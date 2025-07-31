package de.metas.edi.api.impl;

import de.metas.edi.api.IEDIOLCandBL;
import de.metas.edi.model.I_AD_InputDataSource;
import de.metas.edi.model.I_C_OLCand;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.edi
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class EDIOLCandBLTest
{
	/** service under test */
	private IEDIOLCandBL ediOLCandBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		
		ediOLCandBL = Services.get(IEDIOLCandBL.class);
	}

	@Test
	public void test_IsEdiEnabled_Yes()
	{
		I_C_OLCand olCand = createOLCandWithInputDataSource(true);
		assertThat(ediOLCandBL.isEDIInput(olCand)).isTrue();
	}

	@Test
	public void test_IsEdiEnabled_No()
	{
		I_C_OLCand olCand = createOLCandWithInputDataSource(false);
		assertThat(ediOLCandBL.isEDIInput(olCand)).isFalse();
	}

	@Test
	public void test_IsEdiEnabled_No_WhenDataSourceIsNull()
	{
		final I_C_OLCand olCand = InterfaceWrapperHelper.create(Env.getCtx(), I_C_OLCand.class, ITrx.TRXNAME_None);
		olCand.setAD_InputDataSource_ID(-1);
		InterfaceWrapperHelper.save(olCand);
		
		assertThat(ediOLCandBL.isEDIInput(olCand)).isFalse();
	}

	private I_C_OLCand createOLCandWithInputDataSource(final boolean isEdiEnabled)
	{
		final I_C_OLCand olCand = InterfaceWrapperHelper.create(Env.getCtx(), I_C_OLCand.class, ITrx.TRXNAME_None);
		olCand.setAD_InputDataSource_ID(createInputDataSource(isEdiEnabled).getAD_InputDataSource_ID());
		InterfaceWrapperHelper.save(olCand);
		return olCand;
		
	}
	
	private final I_AD_InputDataSource createInputDataSource(final boolean isEdiEnabled)
	{
		final I_AD_InputDataSource dataSource = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_InputDataSource.class, ITrx.TRXNAME_None);
		dataSource.setIsEdiEnabled(isEdiEnabled);
		InterfaceWrapperHelper.save(dataSource);
		return dataSource;
	}
}
