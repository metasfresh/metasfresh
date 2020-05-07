package de.metas.handlingunits.ordercandidate.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.model.I_C_OLCand;

public class OLCandPIIPValidatorTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}
	
	/**
	 * Verifies that the validator does not change the Qty if a olCand has no PIIP and doesn't throw an exception etc.
	 */
	@Test
	public void testNoPIIPQtyOnChanged()
	{
		final I_C_OLCand olCand = InterfaceWrapperHelper.newInstance(I_C_OLCand.class, PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx()));
		olCand.setQty(BigDecimal.TEN);
		InterfaceWrapperHelper.save(olCand);
		final boolean validateOK = new OLCandPIIPValidator().validate(olCand);

		assertThat(validateOK, is(true));
		assertThat(olCand.getQty(), comparesEqualTo(BigDecimal.TEN));
	}
}
