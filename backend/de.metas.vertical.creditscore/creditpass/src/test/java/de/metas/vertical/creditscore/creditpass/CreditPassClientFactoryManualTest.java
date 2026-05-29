package de.metas.vertical.creditscore.creditpass;

import de.metas.bpartner.BPartnerId;
import de.metas.user.UserId;
import de.metas.vertical.creditscore.base.spi.model.CreditScore;
import de.metas.vertical.creditscore.base.spi.model.ResultCode;
import de.metas.vertical.creditscore.base.spi.model.TransactionData;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfig;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigId;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigPaymentRule;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigPaymentRuleId;
import de.metas.vertical.creditscore.creditpass.model.CreditPassTransactionData;
import de.metas.vertical.creditscore.creditpass.repository.CreditPassConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreditPassClientFactoryManualTest
{

	@InjectMocks
	CreditPassClientFactory clientFactory;

	@Mock
	CreditPassConfigRepository configRepository;

	@BeforeEach
	public void init()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Disabled
	public void test() throws Exception
	{
		final List<CreditPassConfigPaymentRule> creditPassConfigPaymentRuleList = new ArrayList<>();
		CreditPassConfigPaymentRule paymentRule = CreditPassConfigPaymentRule.builder()
				.paymentRule("P")
				.purchaseType(1)
				.paymentRuleId(CreditPassConfigPaymentRuleId.ofRepoId(1))
				.build();
		creditPassConfigPaymentRuleList.add(paymentRule);
		// please request auth ID and auth password for testing
		final CreditPassConfig creditScoreConfig = CreditPassConfig.builder()
				.restApiBaseUrl("https://secure.creditpass.de/atgw/authorize.cfm")
				.authId("")
				.authPassword("")
				.creditPassConfigPaymentRuleList(creditPassConfigPaymentRuleList)
				.requestReason("ABK")
				.transactionType(11920)
				.processingCode(8)
				.resultCode(ResultCode.N)
				.notificationUserId(UserId.ofRepoId(1))
				.creditPassConfigId(CreditPassConfigId.ofRepoId(1))
				.build();
		BPartnerId partnerId = BPartnerId.ofRepoId(1);
		Mockito.when(configRepository.getConfigByBPartnerId(partnerId)).thenReturn(creditScoreConfig);
		final CreditPassClient client = (CreditPassClient)clientFactory.newClientForBusinessPartner(BPartnerId.ofRepoId(1));

		TransactionData transactionData = CreditPassTransactionData.builder()
				.accountNr("0123456789")
				.bankRoutingCode("12345678")
				.firstName("Peter")
				.lastName("Test")
				.dateOfBirth(LocalDate.now())
				.build();

		final CreditScore creditScore = client.getCreditScore(transactionData, "P");
		assertNotNull(creditScore);

	}
}
