package de.metas.payment.esr.dataimporter.impl.camt54;

import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.dataimporter.ESRType;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

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
 * camt.054.001.06: an ISO 11649 creditor reference ({@code Cd=SCOR}) is read, and a payment without {@code RmtInf} becomes a line with the error "missing ESR reference";
 * neither aborts the import. The expected values are what the camt.054 import of the main line produces for the same entries.
 * <p>
 * The fixtures are synthetic, written after the structure of the PostFinance camt.054 samples (1 entry, 1 transaction, CHF credit); see the comment on top of each file.
 */
public class ESRDataImporterCamt54V06ScorAndNoRmtInfTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void scorReference_isReadAsReference_typeScor_noError()
	{
		final ESRStatement statement = importFile("/camt054_v06_scor_synthetic.xml");

		assertThat(statement.getTransactions()).hasSize(1);
		final ESRTransaction trx = statement.getTransactions().get(0);
		assertThat(trx.getEsrReferenceNumber()).isEqualTo("RF18539007547034");
		assertThat(trx.getType().getCode()).isEqualTo("SCOR");
		assertThat(trx.getAmount()).isEqualByComparingTo("250.00");
		assertThat(trx.getTrxType()).isEqualTo(ESRConstants.ESRTRXTYPE_CreditMemo);
		assertThat(trx.getErrorMsgs()).isEmpty();
		assertThat(statement.getCtrlAmount()).isEqualByComparingTo("250.00");
		assertThat(statement.getCtrlQty()).isEqualByComparingTo("1");
	}

	@Test
	public void paymentWithoutRmtInf_becomesLineWithMissingReferenceError()
	{
		final ESRStatement statement = importFile("/camt054_v06_no_rmtinf_synthetic.xml");

		assertThat(statement.getTransactions()).hasSize(1);
		final ESRTransaction trx = statement.getTransactions().get(0);
		assertThat(trx.getEsrReferenceNumber()).isNull();
		assertThat(trx.getType()).isEqualTo(ESRType.TYPE_ESR);
		assertThat(trx.getAmount()).isEqualByComparingTo("120.00");
		assertThat(trx.getTrxType()).isEqualTo(ESRConstants.ESRTRXTYPE_CreditMemo);
		assertThat(trx.getErrorMsgs()).containsExactly(ReferenceStringHelper.MSG_MISSING_ESR_REFERENCE.toAD_Message());
		assertThat(statement.getCtrlAmount()).isEqualByComparingTo("120.00");
		assertThat(statement.getCtrlQty()).isEqualByComparingTo("1");
	}

	private ESRStatement importFile(final String resourceName)
	{
		final InputStream inputStream = getClass().getResourceAsStream(resourceName);
		assertThat(inputStream).as(resourceName).isNotNull();
		return new ESRDataImporterCamt54(newInstance(I_ESR_ImportFile.class), inputStream).importData();
	}
}
