package de.metas.payment.esr.dataimporter.impl.camt54;

import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.ESRTransaction;
import de.metas.payment.esr.dataimporter.ESRType;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * camt.054.001.08 real-life samples; expected values are derived from each file's own content
 * (one {@link ESRTransaction} per {@code TxDtls}).
 */
public class ESRDataImporterCamt54V08PostFinanceSamplesTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private ESRStatement importFile(@NonNull final String fileName)
	{
		final String resourceName = "/postfinance/" + fileName;
		final InputStream inputStream = getClass().getResourceAsStream(resourceName);
		assertThat(inputStream).as("Unable to load %s", resourceName).isNotNull();

		return new ESRDataImporterCamt54(newInstance(I_ESR_ImportFile.class), inputStream).importData();
	}

	private static void assertLine(final ESRTransaction trx, final String expectedAmount, final String expectedReference)
	{
		assertThat(trx.getType()).isEqualTo(ESRType.TYPE_QRR);
		assertThat(trx.getEsrReferenceNumber()).isEqualTo(expectedReference);
		assertThat(trx.getAmount()).isEqualByComparingTo(expectedAmount);
		assertThat(trx.getErrorMsgs()).isEmpty();
	}

	@Test
	public void maskedSample_singleQrrLine()
	{
		final ESRStatement statement = importFile("camt054_v08_att11_masked.xml");

		assertThat(statement.getErrorMsgs()).isEmpty();
		assertThat(statement.getTransactions()).hasSize(1);
		assertLine(statement.getTransactions().get(0), "6045.85", "000000000102175968010376491");
		assertThat(statement.getCtrlAmount()).isEqualByComparingTo("6045.85");
		assertThat(statement.getCtrlQty()).isNull();
	}

	@Test
	public void postFinance_1111091335_2020_twoQrrLines()
	{
		final ESRStatement statement = importFile("200519_camt054_P_CH2909000000250094239_1111091335_0_2020061900081727_v2019.xml");

		assertThat(statement.getErrorMsgs()).isEmpty();
		final List<ESRTransaction> trxs = statement.getTransactions();
		assertThat(trxs).hasSize(2);
		assertLine(trxs.get(0), "778.29", "539115429773825311971477453");
		assertLine(trxs.get(1), "913.93", "662437765447746478179744715");
		assertThat(statement.getCtrlAmount()).isEqualByComparingTo("1692.22");
		assertThat(statement.getCtrlQty()).isEqualByComparingTo("2");
	}

	@Test
	public void postFinance_1111091335_2024_threeQrrLines()
	{
		final ESRStatement statement = importFile("camt054_P_CH2909000000250094239_1111091335_0_2024012400081727_v2019.xml");

		assertThat(statement.getErrorMsgs()).isEmpty();
		final List<ESRTransaction> trxs = statement.getTransactions();
		assertThat(trxs).hasSize(3);
		assertLine(trxs.get(0), "500.50", "000000000000000000000000105");
		assertLine(trxs.get(1), "778.29", "539115429773825311971477453");
		assertLine(trxs.get(2), "913.93", "662437765447746478179744715");
		assertThat(statement.getCtrlAmount()).isEqualByComparingTo("2192.72");
		assertThat(statement.getCtrlQty()).isEqualByComparingTo("3");
	}

	@Test
	public void postFinance_1120564403_2025_63QrrLines()
	{
		final ESRStatement statement = importFile("camt.054_P_CH0309000000772897159_1120564403_0_2025050601041344.xml");

		assertThat(statement.getErrorMsgs()).isEmpty();
		assertThat(statement.getTransactions()).hasSize(63);
		assertThat(statement.getTransactions()).allSatisfy(trx -> {
			assertThat(trx.getType()).isEqualTo(ESRType.TYPE_QRR);
			assertThat(trx.getErrorMsgs()).isEmpty();
		});
		assertLine(statement.getTransactions().get(0), "852.10", "374275749662732624797381579");
		assertThat(statement.getCtrlAmount()).isEqualByComparingTo("30279.53");
		assertThat(statement.getCtrlQty()).isEqualByComparingTo("63");
	}
}
