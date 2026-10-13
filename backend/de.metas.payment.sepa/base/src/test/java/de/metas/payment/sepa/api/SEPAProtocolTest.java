package de.metas.payment.sepa.api;

import de.metas.banking.payment.PaySelectionTrxType;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

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
	void directDebit_ignoresChoice()
	{
		assertThat(SEPAProtocol.ofPaySelectionTrxType(PaySelectionTrxType.DIRECT_DEBIT, SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02))
				.isSameAs(SEPAProtocol.DIRECT_DEBIT_PAIN_008_003_02);
		assertThat(SEPAProtocol.ofPaySelectionTrxType(PaySelectionTrxType.DIRECT_DEBIT, null))
				.isSameAs(SEPAProtocol.DIRECT_DEBIT_PAIN_008_003_02);
	}

	@Test
	void creditTransfer_withDirectDebitChosen_fails()
	{
		assertThatThrownBy(() -> SEPAProtocol.ofPaySelectionTrxType(PaySelectionTrxType.CREDIT_TRANSFER, SEPAProtocol.DIRECT_DEBIT_PAIN_008_003_02))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void isCreditTransfer()
	{
		assertThat(SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_03_CH_02.isCreditTransfer()).isTrue();
		assertThat(SEPAProtocol.CREDIT_TRANSFER_PAIN_001_001_09_CH_03.isCreditTransfer()).isTrue();
		assertThat(SEPAProtocol.DIRECT_DEBIT_PAIN_008_003_02.isCreditTransfer()).isFalse();
	}
}
