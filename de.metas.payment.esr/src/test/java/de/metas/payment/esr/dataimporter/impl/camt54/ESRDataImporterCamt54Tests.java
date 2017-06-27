package de.metas.payment.esr.dataimporter.impl.camt54;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.model.I_ESR_Import;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ESRDataImporterCamt54Tests
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/camt.054-ESR-ASR_P_CH0309000000250090342_38000000_0_2016052723010603.xml");
		assertThat(inputStream, notNullValue());

		final ESRStatement importData = new ESRDataImporterCamt54(newInstance(I_ESR_Import.class), inputStream).importData();
		assertThat(importData.getCtrlAmount(), comparesEqualTo(new BigDecimal("1000")));
		assertThat(importData.getCtrlQty(), comparesEqualTo(new BigDecimal("10")));
		assertThat(importData.getTransactions().size(), is(10));
	}

}
