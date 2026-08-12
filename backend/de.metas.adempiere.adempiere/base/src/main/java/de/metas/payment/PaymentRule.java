/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.payment;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Order;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

/**
 * Defines how a document (order, invoice) will be paid.
 * Corresponds to AD_Reference_ID=195 ({@code _Payment Rule}).
 *
 * <h2>Downstream behavior</h2>
 * <p>
 * PaymentRule propagates: {@code C_OLCand -> C_Order -> C_Invoice_Candidate -> C_Invoice}.
 * OLCands with different PaymentRules always produce separate orders
 * (hard-coded split in {@code OLCandProcessingHelper.isOrderSplit()}).
 * </p>
 *
 * <h3>PaySelection / SEPA export eligibility</h3>
 * <p>
 * {@link PaySelectionUpdater#buildSelectSQL_InvoiceMatchRequirement()} uses a hardcoded SQL WHERE
 * that only includes certain PaymentRules in payment proposals:
 * </p>
 * <ul>
 *   <li>Vendor invoices (IsSOTrx=N): {@code PaymentRule IN ('T','P')} &rarr; {@link #DirectDeposit}, {@link #OnCredit}</li>
 *   <li>Customer invoices (IsSOTrx=Y, non-credit-memo): {@code PaymentRule = 'D'} &rarr; {@link #DirectDebit}</li>
 *   <li>Customer credit memos (IsSOTrx=Y, DocBaseType=ARC): {@code PaymentRule IN ('T','P')} &rarr; {@link #DirectDeposit}, {@link #OnCredit}</li>
 * </ul>
 * <p>
 * All other PaymentRules (including {@link #CreditCardExtern}, {@link #PayPalExtern}, {@link #Cash}, etc.)
 * are <b>excluded</b> from payment proposals and SEPA export.
 * Invoices with these rules must be allocated via the Payment Allocation view (WebUI),
 * the REST API ({@code POST /api/v2/invoices/payment}), or Remittance Advice.
 * </p>
 *
 * @see de.metas.banking.payment.impl.PaySelectionUpdater
 * @see de.metas.ordercandidate.api.impl.OLCandProcessingHelper#isOrderSplit
 */
public enum PaymentRule implements ReferenceListAwareEnum
{
	/** Code {@code B}. Immediate cash payment. Not included in PaySelection. */
	Cash(X_C_Order.PAYMENTRULE_Cash),
	/** Code {@code K}. Internal credit card payment. Not included in PaySelection. */
	CreditCard(X_C_Order.PAYMENTRULE_CreditCard),
	/** Code {@code T}. Bank transfer to vendor. Included in PaySelection (vendor invoices + customer credit memos). SEPA credit transfer. */
	DirectDeposit(X_C_Order.PAYMENTRULE_DirectDeposit),
	/** Code {@code S}. Payment by check. Not included in PaySelection. */
	Check(X_C_Order.PAYMENTRULE_Check),
	/** Code {@code P}. Payment on account per payment terms (Auf Ziel). Included in PaySelection (vendor invoices + customer credit memos). SEPA credit transfer. System default. */
	OnCredit(X_C_Order.PAYMENTRULE_OnCredit),
	/** Code {@code D}. SEPA direct debit from customer account. Included in PaySelection for customer invoices (IsSOTrx=Y). */
	DirectDebit(X_C_Order.PAYMENTRULE_DirectDebit),
	/** Code {@code M}. Multiple payment methods combined. Not included in PaySelection. */
	Mixed(X_C_Order.PAYMENTRULE_Mixed),
	/** Code {@code L}. PayPal payment. Not included in PaySelection. */
	PayPal(X_C_Order.PAYMENTRULE_PayPal),
	/** Code {@code V}. Externally processed PayPal payment. Not included in PaySelection. Allocate via WebUI, REST API, or Remittance Advice. */
	PayPalExtern(X_C_Order.PAYMENTRULE_PayPalExtern),
	/** Code {@code U}. Externally processed credit card payment (e.g. Stripe, Adyen). Not included in PaySelection. Allocate via WebUI, REST API, or Remittance Advice. */
	CreditCardExtern(X_C_Order.PAYMENTRULE_KreditkarteExtern),
	/** Code {@code R}. Instant bank transfer (e.g. Klarna Sofortüberweisung). Not included in PaySelection. */
	InstantBankTransfer(X_C_Order.PAYMENTRULE_Sofortueberweisung),
	/** Code {@code E}. Reimbursement/refund. Not included in PaySelection. */
	Reimbursement(X_C_Order.PAYMENTRULE_Reimbursement),
	/** Code {@code F}. Settlement/compensation between business partners. Not included in PaySelection. */
	Settlement(X_C_Order.PAYMENTRULE_Settlement),
	;

	@Getter
	private final String code;

	PaymentRule(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static PaymentRule ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static Optional<PaymentRule> optionalOfCode(@Nullable final String code)
	{
		return Optional.ofNullable(ofNullableCode(code));
	}

	public static PaymentRule ofCode(@NonNull final String code)
	{
		final PaymentRule type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + PaymentRule.class + " found for code: " + code);
		}
		return type;
	}

	@Nullable
	public static String toCodeOrNull(@Nullable final PaymentRule type)
	{
		return type != null ? type.getCode() : null;
	}

	private static final ImmutableMap<String, PaymentRule> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), PaymentRule::getCode);

	public boolean isCashOrCheck()
	{
		return isCash() || isCheck();
	}

	public boolean isCash()
	{
		return this == Cash;
	}

	public boolean isCheck()
	{
		return this == Check;
	}

	public boolean isOnCredit() {return OnCredit.equals(this);}
	public boolean isPayPal() {return PayPal.equals(this);}
	public boolean isSettlement() {return Settlement.equals(this);}

	public boolean isDirectDebit()
	{
		return this == DirectDebit;
	}
}
