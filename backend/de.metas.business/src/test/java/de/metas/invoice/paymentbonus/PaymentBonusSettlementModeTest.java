package de.metas.invoice.paymentbonus;

import de.metas.organization.ClientAndOrgId;
import org.adempiere.service.ISysConfigBL;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentBonusSettlementModeTest
{
	private static final ClientAndOrgId CLIENT_AND_ORG = ClientAndOrgId.ofClientAndOrg(1000000, 1000000);

	private static PaymentBonusSettlementMode ofSysConfigValue(final String value)
	{
		final ISysConfigBL sysConfigBL = Mockito.mock(ISysConfigBL.class);
		Mockito.when(sysConfigBL.getValue(Mockito.eq(PaymentBonusSettlementMode.SYSCONFIG_NAME), Mockito.any(), Mockito.eq(CLIENT_AND_ORG))).thenReturn(value);
		return PaymentBonusSettlementMode.ofSysConfig(sysConfigBL, CLIENT_AND_ORG);
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "   ", "fullpayy", "both,", "garbage" })
	void unsetBlankOrUnknown_resolvesToUnderpay_andDoesNotThrow(final String value)
	{
		assertThat(ofSysConfigValue(value)).isEqualTo(PaymentBonusSettlementMode.UNDERPAY);
	}

	@Test
	void validCodes()
	{
		assertThat(ofSysConfigValue("underpay")).isEqualTo(PaymentBonusSettlementMode.UNDERPAY);
		assertThat(ofSysConfigValue("fullpay")).isEqualTo(PaymentBonusSettlementMode.FULLPAY);
		assertThat(ofSysConfigValue("both")).isEqualTo(PaymentBonusSettlementMode.BOTH);
		assertThat(ofSysConfigValue("FullPay")).isEqualTo(PaymentBonusSettlementMode.FULLPAY);
		assertThat(ofSysConfigValue(" Both ")).isEqualTo(PaymentBonusSettlementMode.BOTH);
	}

	@Test
	void gateFlags()
	{
		assertThat(PaymentBonusSettlementMode.UNDERPAY.isSelfDeductAllowed()).isTrue();
		assertThat(PaymentBonusSettlementMode.UNDERPAY.isPayFullGrantAllowed()).isFalse();
		assertThat(PaymentBonusSettlementMode.FULLPAY.isSelfDeductAllowed()).isFalse();
		assertThat(PaymentBonusSettlementMode.FULLPAY.isPayFullGrantAllowed()).isTrue();
		assertThat(PaymentBonusSettlementMode.BOTH.isSelfDeductAllowed()).isTrue();
		assertThat(PaymentBonusSettlementMode.BOTH.isPayFullGrantAllowed()).isTrue();
	}
}
