package de.metas.payment.esr.dataimporter.impl.camt54;

import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * {@code TxDtls/RmtInf} is optional in camt.054. A payment without it is not an exception: the import turns it into a line with the error
 * "missing ESR reference" (for camt.054.001.08 see the PostFinance sample test).
 * <p>
 * The fixtures are the existing camt.054.001.06 / .02 test files with every {@code RmtInf} block removed (see the comment on top of each file):
 * 10 {@code TxDtls} in one {@code Ntry}, amounts 100, 50, 200, 50, 100, 200, 50, 100, 50, 100 (sum 1000), read from the files.
 */
public class ESRDataImporterCamt54NoRemittanceInfoTest
{
	private static final List<String> AMOUNTS_IN_FILE = Arrays.asList("100", "50", "200", "50", "100", "200", "50", "100", "50", "100");

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void v06_paymentsWithoutRmtInf_becomeErrorLines()
	{
		assertEveryPaymentBecomesAnErrorLine("/camt054_v06_no_RmtInf.xml");
	}

	@Test
	public void v02_paymentsWithoutRmtInf_becomeErrorLines()
	{
		assertEveryPaymentBecomesAnErrorLine("/camt54_v02_no_RmtInf.xml");
	}

	private void assertEveryPaymentBecomesAnErrorLine(final String resourceName)
	{
		final InputStream inputStream = getClass().getResourceAsStream(resourceName);
		assertThat(inputStream).isNotNull();

		final ESRStatement importData = new ESRDataImporterCamt54(newInstance(I_ESR_ImportFile.class), inputStream).importData();

		assertThat(importData.getTransactions()).hasSize(AMOUNTS_IN_FILE.size());
		assertThat(importData.getTransactions())
				.allSatisfy(trx -> assertThat(trx.getErrorMsgs()).contains(ReferenceStringHelper.MSG_MISSING_ESR_REFERENCE.toAD_Message()));
		assertThat(importData.getTransactions().stream().map(ESRDataImporterCamt54NoRemittanceInfoTest::absAmount).collect(Collectors.toList()))
				.usingElementComparator(BigDecimal::compareTo)
				.containsExactlyElementsOf(AMOUNTS_IN_FILE.stream().map(BigDecimal::new).collect(Collectors.toList()));
	}

	private static BigDecimal absAmount(final ESRTransaction trx)
	{
		return trx.getAmountNotNull().abs();
	}
}
