package de.metas.vertical.creditscore.creditpass;

import org.junit.Test;

public class CreditPassClientFactoryManualTest
{

	@Test
	public void test() throws Exception
	{

		/*final CreditPassClientFactory clientFactory = new CreditPassClientFactory();

		final CreditPassConfig creditScoreConfig = CreditPassConfig.builder()
				.restApiBaseUrl("https://secure.creditpass.de/atgw/authorize.cfm")
				.authId("T920433")
				.authPassword("riV5sXWH96L4Nq")
				.purchaseType(1)
				.requestReason("ABK")
				.transactionType(11920)
				.build();
		final CreditPassClient client = clientFactory.createCreditPassClient(creditScoreConfig);

		final Customer customer = Customer.builder()
				.authId(creditScoreConfig.getAuthId())
				.authPassword(creditScoreConfig.getAuthPassword())
				.customerTransactionId("acf5a2923fe911e9b210d663bd873d93")
				.build();
		final ProcessRequest processRequest = ProcessRequest.builder()
				.transactionType(creditScoreConfig.getTransactionType())
				.processingCode(8)
				.requestReason(creditScoreConfig.getRequestReason())
				.build();

		final Query query = Query.builder()
				.purchaseType(1)
				.amount("13050")
				.bankAccount(BankAccount.builder()
						.bankRoutingCode("12345678")
						.accountNr("0123456789")
						.build())
				.contact(Contact.builder()
						.firstName("Peter")
						.lastName("Test")
						.dateOfBirth(LocalDate.of(2019, 03, 06))
						.build())
				.build();

		final Request request = Request.builder()
				.customer(customer)
				.process(processRequest)
				.query(query)
				.build();

		final CreditScore creditScore = client.getCreditScore(request);
		assertNotNull(creditScore);*/

	}
}
