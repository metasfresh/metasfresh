package de.metas.dunning.api;

/*
 * #%L
 * de.metas.dunning
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
import java.util.Date;

/**
 * Dunnable document.
 * 
 * 
 * A dunnable document, is a document which can be dunned.
 * 
 * @author tsa
 * 
 */
public interface IDunnableDoc
{
	int getAD_Client_ID();

	int getAD_Org_ID();

	int getC_BPartner_ID();

	int getC_BPartner_Location_ID();

	int getContact_ID();

	int getC_Currency_ID();

	BigDecimal getTotalAmt();

	BigDecimal getOpenAmt();

	Date getDueDate();

	Date getGraceDate();

	int getDaysDue();

	String getTableName();

	int getRecordId();

	boolean isInDispute();
	
	//FRESH-504: Add DocumentNo
	
	String getDocumentNo();
}
