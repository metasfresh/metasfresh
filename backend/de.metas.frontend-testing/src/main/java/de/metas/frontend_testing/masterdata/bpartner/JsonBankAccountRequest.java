package de.metas.frontend_testing.masterdata.bpartner;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Creates a {@code C_BP_BankAccount} (bank account / IBAN) for a business partner.
 * Required by EN16931 / ZUGFeRD CII for the seller CreditorFinancialAccount.
 */
@Value
@Builder
@Jacksonized
public class JsonBankAccountRequest
{
	/**
	 * IBAN of the bank account (e.g. {@code "DE89370400440532013000"}).
	 * Required.
	 */
	String iban;

	/**
	 * ISO currency code (e.g. {@code "EUR"}).
	 * If null, defaults to EUR.
	 */
	@Nullable String currencyCode;
}
