package de.metas.payment.sepa.sepamarshaller;

/*
 * #%L
 * de.metas.payment.sepa
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.io.OutputStream;
import java.util.Set;

import de.metas.payment.sepa.api.SepaMarshallerException;
import de.metas.payment.sepa.model.I_SEPA_Export;

/**
 * Interface for classes that can transform {@link I_SEPA_Export}s into ISO-20022 payment initiation (pain) XML messages.
 * <p>
 * Notes:
 * <ul>
 * <li>Afaik there is no single pain spec to support credit and debit transactions, but i wouldn't be too sure about that.
 * <li>This interface is currently <b>not</b> used. Instead, as of now it's hard-coded. We are going to make the used implementation configurable as soon as there is time and we have to or want to.
 * </ul>
 * 
 * @author ts
 *
 */
public interface ISEPAMarshaller
{
	enum SupportedTransactionType
	{
		/**
		 * A transaction where we want to "send" money.
		 */
		credit,

		/**
		 * A transaction where we want to "receive" money.
		 */
		debit
	}

	/**
	 * @return the identifier of the <b>Pa</b>yment <b>In</b>itiation format (XSD) used by this marshaller.
	 */
	String getPainIdentifier();

	/**
	 * 
	 * @return the types of transactions which this marshaler supports.
	 */
	Set<SupportedTransactionType> getSupportedTransactionTypes();

	/**
	 * 
	 * @return <code>true</code> if the marshaler supports currencies other than "EUR".
	 */
	boolean isSupportsOtherCurrencies();

	/**
	 * 
	 * @return <code>true</code> if the marshaler can deal with accounts that have no IBAN, but e.g. (=>switzerland) a post account number.
	 */
	boolean isSupportsGenericAccountIdentification();

	/**
	 * 
	 * @param sepaExport
	 * @param out
	 * @throws SepaMarshallerException if the given <code>sepaExport</code> can't be marshaled.
	 */
	public void marshal(I_SEPA_Export sepaExport, OutputStream out) throws SepaMarshallerException;
}
