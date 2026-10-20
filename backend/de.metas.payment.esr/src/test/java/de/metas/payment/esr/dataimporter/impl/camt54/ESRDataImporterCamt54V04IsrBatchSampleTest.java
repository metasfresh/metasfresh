package de.metas.payment.esr.dataimporter.impl.camt54;

import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.dataimporter.ESRType;
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
 * A camt.054.001.04 notification with 19 ISR credits, one batch per entry (synthetic file: no real account, party or reference).
 * The expected values are read from the file itself: 19 entries, each one transaction with an "ISR Reference" creditor reference, total CHF 5132.70.
 * The file must import the same way before and after the ISO 20022 version 2019 changes (the .04 namespace is read as .06).
 */
public class ESRDataImporterCamt54V04IsrBatchSampleTest
{
	private static final List<String> AMOUNTS_IN_FILE = Arrays.asList(
			"220.2", "159.6", "464.6", "83.5", "640.1", "145.2", "301.3", "212.8", "96.8", "571.75",
			"121", "224.45", "228.7", "395.7", "445.95", "96.8", "247.45", "215.4", "261.4");

	private static final List<String> REFERENCES_IN_FILE = Arrays.asList(
			"000000000000000000000003153", "000000000000000000000000366", "000000000000000000000000340", "000000000000000000000000351",
			"000000000000000000000003190", "000000000000000000000003116", "000000000000000000000003101", "000000000000000000000003169",
			"000000000000000000000003185", "000000000000000000000003177", "000000000000000000000000319", "000000000000000000000000327",
			"000000000000000000000000335", "000000000000000000000003124", "000000000000000000000003132", "000000000000000000000003148",
			"000000000000000000000000398", "000000000000000000000000382", "000000000000000000000000374");

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void camt054V04IsrBatchSample_2019()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/camt054_v04_isr_batch_sample.xml");
		assertThat(inputStream).isNotNull();

		final ESRStatement statement = new ESRDataImporterCamt54(newInstance(I_ESR_ImportFile.class), inputStream).importData();

		assertThat(statement.getErrorMsgs()).isEmpty();
		assertThat(statement.getCtrlAmount()).isEqualByComparingTo("5132.70");
		assertThat(statement.getCtrlQty()).as("CtrlQty").isEqualByComparingTo("19");

		final List<ESRTransaction> transactions = statement.getTransactions();
		assertThat(transactions).hasSize(19);
		assertThat(transactions).allSatisfy(trx -> {
			assertThat(trx.getErrorMsgs()).isEmpty();
			assertThat(trx.getType()).isEqualTo(ESRType.TYPE_ESR);
		});
		assertThat(transactions.stream().map(ESRTransaction::getEsrReferenceNumber).collect(Collectors.toList()))
				.containsExactlyElementsOf(REFERENCES_IN_FILE);
		assertThat(transactions.stream().map(ESRTransaction::getAmount).collect(Collectors.toList()))
				.usingElementComparator(BigDecimal::compareTo)
				.containsExactlyElementsOf(AMOUNTS_IN_FILE.stream().map(BigDecimal::new).collect(Collectors.toList()));
	}
}
