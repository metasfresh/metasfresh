package de.metas.payment.sepa.api;

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


import java.math.BigDecimal;

/**
 * SEPA Document - A document that will correspond to a SEPA_Export_Line (an entry in a SEPA direct debit).
 *
 * @author ad
 *
 */
public interface ISEPADocument
{

	int getAD_Client_ID();

	int getAD_Org_ID();

	BigDecimal getAmt();

	String getDescription();

	String getIBAN();

	String getSwiftCode();

	/**
	 * Note that this property is acutally used to identify the creditor
	 * @return
	 */
	String getMandateRefNo();

	String getTableName();

	int getRecordId();

	int getC_BPartner_ID();

}
