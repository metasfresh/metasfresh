package de.metas.payment.sepa.api;

import de.metas.banking.payment.PaySelectionTrxType;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SEPAProtocolTest
{
	@Test
	void ofCode_pain_001_001_09_ch_03()
	{
		assertThat(SEPAProtocol.ofCode("pain.001.001.09.ch.03")).isSameAs(SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_09_CH_03);
	}

	@Test
	void creditTransfer_chosen_09()
	{
		assertThat(SEPAProtocol.ofPaySelectionTrxType(PaySelectionTrxType.CREDIT_TRANSFER, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_09_CH_03))
				.isSameAs(SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_09_CH_03);
	}

	@Test
	void creditTransfer_chosen_03()
	{
		assertThat(SEPAProtocol.ofPaySelectionTrxType(PaySelectionTrxType.CREDIT_TRANSFER, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02))
				.isSameAs(SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02);
	}

	@Test
	void creditTransfer_nothingChosen_defaultsTo09()
	{
		assertThat(SEPAProtocol.ofPaySelectionTrxType(PaySelectionTrxType.CREDIT_TRANSFER, null))
				.isSameAs(SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_09_CH_03);
	}

	@Test
	void directDebit_nothingChosen_defaultsTo_pain_008_001_08()
	{
		assertThat(SEPAProtocol.ofPaySelectionTrxType(PaySelectionTrxType.DIRECT_DEBIT, null))
				.isSameAs(SEPAProtocol.DIRECT_DEBIT_PAIN_008_001_08);
	}

	@Test
	void directDebit_chosen_pain_008_001_08()
	{
		assertThat(SEPAProtocol.ofPaySelectionTrxType(PaySelectionTrxType.DIRECT_DEBIT, SEPAProtocol.DIRECT_DEBIT_PAIN_008_001_08))
				.isSameAs(SEPAProtocol.DIRECT_DEBIT_PAIN_008_001_08);
	}

	@ParameterizedTest
	@EnumSource(value = SEPAProtocol.class, names = { "CREDIT_TRANSFER_PAIN_001_001_03_CH_02", "CREDIT_TRANSFER_PAIN_001_001_09_CH_03" })
	void directDebit_withCreditTransferChosen_fails(final SEPAProtocol chosenProtocol)
	{
		assertThatThrownBy(() -> SEPAProtocol.ofPaySelectionTrxType(PaySelectionTrxType.DIRECT_DEBIT, chosenProtocol))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(chosenProtocol.getCode());
	}

	/**
	 * pain.008.003.02 is only kept to read old exports; German banks reject it for new ones.
	 */
	@Test
	void directDebit_with_pain_008_003_02_chosen_fails()
	{
		assertThatThrownBy(() -> SEPAProtocol.ofPaySelectionTrxType(PaySelectionTrxType.DIRECT_DEBIT, SEPAProtocol.DIRECT_DEBIT_PAIN_008_003_02))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(SEPAProtocol.DIRECT_DEBIT_PAIN_008_003_02.getCode());
	}

	@ParameterizedTest
	@EnumSource(value = SEPAProtocol.class, names = { "DIRECT_DEBIT_PAIN_008_003_02", "DIRECT_DEBIT_PAIN_008_001_08" })
	void creditTransfer_withDirectDebitChosen_fails(final SEPAProtocol chosenProtocol)
	{
		assertThatThrownBy(() -> SEPAProtocol.ofPaySelectionTrxType(PaySelectionTrxType.CREDIT_TRANSFER, chosenProtocol))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(chosenProtocol.getCode());
	}

	@Test
	void isCreditTransfer()
	{
		assertThat(SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02.isCreditTransfer()).isTrue();
		assertThat(SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_09_CH_03.isCreditTransfer()).isTrue();
		assertThat(SEPAProtocol.DIRECT_DEBIT_PAIN_008_003_02.isCreditTransfer()).isFalse();
	}

	@Test
	void isDirectDebit()
	{
		assertThat(SEPAProtocol.DIRECT_DEBIT_PAIN_008_003_02.isDirectDebit()).isTrue();
		assertThat(SEPAProtocol.DIRECT_DEBIT_PAIN_008_001_08.isDirectDebit()).isTrue();
		assertThat(SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02.isDirectDebit()).isFalse();
		assertThat(SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_09_CH_03.isDirectDebit()).isFalse();
	}

	@Test
	void ofNullableCode_blankOrNull_returnsNull()
	{
		assertThat(SEPAProtocol.ofNullableCode(null)).isNull();
		assertThat(SEPAProtocol.ofNullableCode("")).isNull();
		assertThat(SEPAProtocol.ofNullableCode("  ")).isNull();
		assertThat(SEPAProtocol.ofNullableCode("pain.001.001.03.ch.02")).isSameAs(SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02);
	}
}
