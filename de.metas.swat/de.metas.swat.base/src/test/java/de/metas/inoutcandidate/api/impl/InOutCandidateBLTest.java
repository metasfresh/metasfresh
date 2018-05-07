package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.math.BigDecimal;

import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.expectations.InOutLineExpectation;
import de.metas.inoutcandidate.spi.impl.IQtyAndQuality;
import de.metas.inoutcandidate.spi.impl.QtyAndQualityExpectation;

public class InOutCandidateBLTest
{
	private InOutCandidateBL inOutCandidateBL;
	private IContextAware context;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		this.context = new PlainContextAware(Env.getCtx());

		this.inOutCandidateBL = (InOutCandidateBL)Services.get(IInOutCandidateBL.class);
	}

	@Test
	public void test_getQtyAndQuality_NotInDispute()
	{
		final I_M_InOutLine inoutLine = new InOutLineExpectation<>(null, context)
				.movementQty(new BigDecimal("33"))
				.inDispute(false)
				.qualityNote("note 1. note 2. note 3")
				.createInOutLine(I_M_InOutLine.class);

		final IQtyAndQuality qtys = inOutCandidateBL.getQtyAndQuality(inoutLine);
		QtyAndQualityExpectation.newInstance()
				.qty("33")
				.qtyWithIssuesExact("0")
				.qualityNotices("note 1. note 2. note 3")
				.assertExpected(qtys);
	}

	@Test
	public void test_getQtyAndQuality_InDispute()
	{
		final I_M_InOutLine inoutLine = new InOutLineExpectation<>(null, context)
				.movementQty(new BigDecimal("33"))
				.inDispute(true)
				.qualityNote("note 1. note 2. note 3")
				.createInOutLine(I_M_InOutLine.class);

		final IQtyAndQuality qtys = inOutCandidateBL.getQtyAndQuality(inoutLine);
		QtyAndQualityExpectation.newInstance()
				.qty("33")
				.qtyWithIssuesExact("33")
				.qualityNotices("note 1. note 2. note 3")
				.assertExpected(qtys);
	}

}
