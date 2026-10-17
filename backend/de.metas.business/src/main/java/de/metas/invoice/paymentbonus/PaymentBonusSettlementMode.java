package de.metas.invoice.paymentbonus;

import de.metas.organization.ClientAndOrgId;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;

import javax.annotation.Nullable;

/**
 * Which way of settling an invoice with its payment bonus ("Abzug bei Zahlung") a payment is allowed to take; an instance setting,
 * read from the sysconfig {@link #SYSCONFIG_NAME} with the payment's client and org.
 */
@RequiredArgsConstructor
public enum PaymentBonusSettlementMode
{
	/** The customer deducts the bonus from what it pays (a short payment). A customer who pays in full is granted nothing. This is the default. */
	UNDERPAY("underpay", true, false),
	/** The customer who pays in full is granted the bonus. A short payment that deducts the bonus is not honoured: nothing is booked for it. */
	FULLPAY("fullpay", false, true),
	/** Both of the above. */
	BOTH("both", true, true);

	public static final String SYSCONFIG_NAME = "de.metas.invoice.paymentbonus.SettlementMode";

	public static final PaymentBonusSettlementMode DEFAULT = UNDERPAY;

	@Getter @NonNull private final String code;
	private final boolean selfDeductAllowed;
	private final boolean payFullGrantAllowed;

	public static PaymentBonusSettlementMode ofCode(@NonNull final String code)
	{
		final String codeNorm = code.trim();
		for (final PaymentBonusSettlementMode mode : values())
		{
			if (mode.code.equalsIgnoreCase(codeNorm))
			{
				return mode;
			}
		}
		throw new AdempiereException("Invalid " + SYSCONFIG_NAME + " value: `" + code + "`; expected one of underpay, fullpay, both");
	}

	/**
	 * @return the mode of the sysconfig; {@link #DEFAULT} if it is not set or blank
	 */
	public static PaymentBonusSettlementMode ofSysConfig(@NonNull final ISysConfigBL sysConfigBL, @NonNull final ClientAndOrgId clientAndOrgId)
	{
		final String code = sysConfigBL.getValue(SYSCONFIG_NAME, DEFAULT.getCode(), clientAndOrgId);
		return fromNullableCode(code);
	}

	private static PaymentBonusSettlementMode fromNullableCode(@Nullable final String code)
	{
		return code == null || code.trim().isEmpty() ? DEFAULT : ofCode(code);
	}

	/** @return whether a payment that deducts the bonus from what it pays (a short payment) is honoured */
	public boolean isSelfDeductAllowed() {return selfDeductAllowed;}

	/** @return whether a customer who pays in full is granted the bonus */
	public boolean isPayFullGrantAllowed() {return payFullGrantAllowed;}
}
