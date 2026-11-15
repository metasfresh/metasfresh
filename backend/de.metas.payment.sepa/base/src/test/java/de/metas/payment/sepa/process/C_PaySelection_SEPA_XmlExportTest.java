package de.metas.payment.sepa.process;

import de.metas.banking.api.BankAccountService;
import de.metas.banking.api.BankRepository;
import de.metas.banking.payment.PaySelectionTrxType;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.Msg;
import de.metas.i18n.TranslatableStrings;
import de.metas.payment.sepa.api.SEPACreditTransferXML;
import de.metas.process.JavaProcess;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.ProcessInfo;
import de.metas.user.UserId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_PaySelection;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

class C_PaySelection_SEPA_XmlExportTest
{
	@Test
	void createSummary_withoutWarnings_isOK()
	{
		final SEPACreditTransferXML xml = xmlBuilder().build();

		assertThat(C_PaySelection_SEPA_XmlExport.createSummary(xml, "de_CH")).isEqualTo(JavaProcess.MSG_OK);
	}

	@Test
	void createSummary_withOneWarning_showsTheWarning()
	{
		final SEPACreditTransferXML xml = xmlBuilder()
				.warning(TranslatableStrings.constant("warning 1"))
				.build();

		assertThat(C_PaySelection_SEPA_XmlExport.createSummary(xml, "de_CH")).isEqualTo("warning 1");
	}

	@Test
	void createSummary_withWarnings_showsAllWarningsNumbered()
	{
		final SEPACreditTransferXML xml = xmlBuilder()
				.warning(TranslatableStrings.constant("warning 1"))
				.warning(TranslatableStrings.constant("warning 2"))
				.build();

		// the WebUI notification collapses line breaks, so each warning is numbered and separated by " · "
		assertThat(C_PaySelection_SEPA_XmlExport.createSummary(xml, "de_CH")).isEqualTo("1) warning 1 · 2) warning 2"); // U+00B7 middle dot
	}

	@Test
	void createSummary_warningWithAtSigns_survivesParseTranslation()
	{
		final String warning = "Business partner TestCreditor (Test Creditor AG), location info@muster.ch / buchhaltung@muster.ch: the address is incomplete.";
		final SEPACreditTransferXML xml = xmlBuilder()
				.warning(TranslatableStrings.constant(warning))
				.build();

		final String summary = C_PaySelection_SEPA_XmlExport.createSummary(xml, "de_CH");

		// JavaProcess runs the summary through parseTranslation, which would take "@muster.ch / buchhaltung@" for an AD_Message/AD_Element; "@@" stands for a plain "@"
		assertThat(summary).isEqualTo("Business partner TestCreditor (Test Creditor AG), location info@@muster.ch / buchhaltung@@muster.ch: the address is incomplete.");
		assertThat(Msg.parseTranslation("de_CH", summary)).isEqualTo(warning);
	}

	private static SEPACreditTransferXML.SEPACreditTransferXMLBuilder xmlBuilder()
	{
		return SEPACreditTransferXML.builder()
				.filename("1000001.xml")
				.contentType("application/xml")
				.content(new byte[] {});
	}

	@ParameterizedTest
	@CsvSource({
			"CREDIT_TRANSFER, pain.001.001.09.ch.03",
			"DIRECT_DEBIT, pain.008.001.08",
	})
	void defaultSepaProtocolCode_dependsOnTheTransactionType(final PaySelectionTrxType paySelectionTrxType, final String expectedProtocolCode)
	{
		assertThat(C_PaySelection_SEPA_XmlExport.getDefaultSepaProtocolCode(paySelectionTrxType)).isEqualTo(expectedProtocolCode);
	}

	@Test
	void defaultSepaProtocolCode_withoutTransactionType_isNull()
	{
		assertThat(C_PaySelection_SEPA_XmlExport.getDefaultSepaProtocolCode(null)).isNull();
	}

	/**
	 * The default the WebUI process dialog gets: read from the selected pay selection's transaction type.
	 */
	@Nested
	class getParameterDefaultValue
	{
		@BeforeEach
		void beforeEach()
		{
			AdempiereTestHelper.get().init();
			Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH); // the process info needs a user
			// needed to construct the process: its ISEPADocumentBL field resolves this bean
			final BankRepository bankRepository = new BankRepository();
			SpringContextHolder.registerJUnitBean(bankRepository);
			SpringContextHolder.registerJUnitBean(new BankAccountService(bankRepository, new CurrencyRepository()));
		}

		@ParameterizedTest
		@CsvSource({
				"CT, pain.001.001.09.ch.03",
				"DD, pain.008.001.08",
		})
		void sepaProtocol_dependsOnTheSelectedPaySelection(final String paySelectionTrxType, final String expectedProtocolCode)
		{
			final C_PaySelection_SEPA_XmlExport process = processFor(paySelection(paySelectionTrxType));

			assertThat(process.getParameterDefaultValue(parameterNamed("SEPA_Protocol"))).isEqualTo(expectedProtocolCode);
		}

		@Test
		void otherParameters_areNotProvided()
		{
			final C_PaySelection_SEPA_XmlExport process = processFor(paySelection("DD"));

			assertThat(process.getParameterDefaultValue(parameterNamed("IsGroupTransactions"))).isSameAs(IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE);
		}

		private I_C_PaySelection paySelection(final String paySelectionTrxType)
		{
			final I_C_PaySelection paySelection = newInstance(I_C_PaySelection.class);
			paySelection.setPaySelectionTrxType(paySelectionTrxType);
			save(paySelection);
			return paySelection;
		}

		private C_PaySelection_SEPA_XmlExport processFor(final I_C_PaySelection paySelection)
		{
			final C_PaySelection_SEPA_XmlExport process = new C_PaySelection_SEPA_XmlExport();
			process.init(ProcessInfo.builder()
					.setCtx(Env.getCtx())
					.setRecord(I_C_PaySelection.Table_Name, paySelection.getC_PaySelection_ID())
					.build());
			return process;
		}

		private IProcessDefaultParameter parameterNamed(final String columnName)
		{
			return new IProcessDefaultParameter()
			{
				@Override
				public String getColumnName()
				{
					return columnName;
				}

				@Override
				public int getContextAsInt(final String name)
				{
					throw new UnsupportedOperationException("not used by the process");
				}
			};
		}
	}
}
