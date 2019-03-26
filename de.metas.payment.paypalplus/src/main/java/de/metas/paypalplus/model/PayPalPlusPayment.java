package de.metas.paypalplus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;

/*
 * #%L
 * de.metas.paypalplus.model
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Value
public class PayPalPlusPayment
{
	@Builder.Default private long created = System.currentTimeMillis();

	@NonNull
	String id;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate paymentDate;

	PaymentAmount paymentAmount;

	String paymentCurrency;

	String transactionDescription;

	BillingAddress billingAddress;

	CreditCard creditCard;

	@java.beans.ConstructorProperties({ "created", "id", "paymentDocumentNumber", "paymentDate", "paymentAmount", "paymentCurrency", "transactionDescription", "billingAddress", "creditCard" }) PayPalPlusPayment(long created, String id, String paymentDocumentNumber, LocalDate paymentDate, PaymentAmount paymentAmount, String paymentCurrency, String transactionDescription,
			BillingAddress billingAddress, CreditCard creditCard)
	{
		this.id = id;
		this.paymentDate = paymentDate;
		this.paymentAmount = paymentAmount;
		this.paymentCurrency = paymentCurrency;
		this.transactionDescription = transactionDescription;
		this.billingAddress = billingAddress;
		this.creditCard = creditCard;
	}

	public static PayPalPlusPaymentBuilder builder()
	{
		return new PayPalPlusPaymentBuilder();
	}

	public static class PayPalPlusPaymentBuilder
	{
		private long created;
		private String id;
		private String paymentDocumentNumber;
		private LocalDate paymentDate;
		private PaymentAmount paymentAmount;
		private String paymentCurrency;
		private String transactionDescription;
		private BillingAddress billingAddress;
		private CreditCard creditCard;

		PayPalPlusPaymentBuilder()
		{
		}

		public PayPalPlusPaymentBuilder created(long created)
		{
			this.created = created;
			return this;
		}

		public PayPalPlusPaymentBuilder id(String id)
		{
			this.id = id;
			return this;
		}

		public PayPalPlusPaymentBuilder paymentDocumentNumber(String paymentDocumentNumber)
		{
			this.paymentDocumentNumber = paymentDocumentNumber;
			return this;
		}

		public PayPalPlusPaymentBuilder paymentDate(LocalDate paymentDate)
		{
			this.paymentDate = paymentDate;
			return this;
		}

		public PayPalPlusPaymentBuilder paymentAmount(PaymentAmount paymentAmount)
		{
			this.paymentAmount = paymentAmount;
			return this;
		}

		public PayPalPlusPaymentBuilder paymentCurrency(String paymentCurrency)
		{
			this.paymentCurrency = paymentCurrency;
			return this;
		}

		public PayPalPlusPaymentBuilder transactionDescription(String transactionDescription)
		{
			this.transactionDescription = transactionDescription;
			return this;
		}

		public PayPalPlusPaymentBuilder billingAddress(BillingAddress billingAddress)
		{
			this.billingAddress = billingAddress;
			return this;
		}

		public PayPalPlusPaymentBuilder creditCard(CreditCard creditCard)
		{
			this.creditCard = creditCard;
			return this;
		}

		public PayPalPlusPayment build()
		{
			return new PayPalPlusPayment(created, id, paymentDocumentNumber, paymentDate, paymentAmount, paymentCurrency, transactionDescription, billingAddress, creditCard);
		}

		public String toString()
		{
			return "PayPalPlusPayment.PayPalPlusPaymentBuilder(created=" + this.created + ", id=" + this.id + ", paymentDocumentNumber=" + this.paymentDocumentNumber + ", paymentDate=" + this.paymentDate + ", paymentAmount=" + this.paymentAmount + ", paymentCurrency=" + this.paymentCurrency + ", transactionDescription=" + this.transactionDescription + ", billingAddress=" + this.billingAddress
					+ ", creditCard=" + this.creditCard + ")";
		}
	}
}
