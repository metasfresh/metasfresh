package de.metas.payment.esr.dataimporter.impl.camt54.v08;

import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.dataimporter.impl.camt54.ESRDataImporterCamt54;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.*;

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

public class ESRDataImporterCamt54V08Tests
{
	private final Condition<? super ESRTransaction> trxHasNoErrors = new Condition<>(
			t -> t.getErrorMsgs().isEmpty(),
			"ESRTransaction has no error messages");

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testWithSampleFile()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/camt054_v08.xml");
		assertThat(inputStream).isNotNull();

		final ESRStatement importData = new ESRDataImporterCamt54(newInstance(I_ESR_ImportFile.class), inputStream).importData();

		// no errors
		assertThat(importData.getErrorMsgs()).isEmpty();
		assertThat(importData.getTransactions())
				.allMatch(t -> t.getErrorMsgs().isEmpty());

		assertThat(importData.getCtrlAmount())
				.isEqualByComparingTo("380");
	}

}
