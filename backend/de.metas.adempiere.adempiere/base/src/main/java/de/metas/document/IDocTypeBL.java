package de.metas.document;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_DocType;

public interface IDocTypeBL extends ISingletonService
{
	/**
	 * 	Is this a Quotation (Binding)
	 *	@return true if Quotation
	 */
	boolean isQuotation(I_C_DocType dt);
	
	/**
	 * 	Is this a Proposal (Not binding)
	 *	@return true if proposal
	 */
	boolean isProposal(I_C_DocType dt);
	
	/**
	 * 	Is this a Proposal or Quotation
	 *	@return true if proposal or quotation
	 */
	boolean isOffer(I_C_DocType dt);
	
	boolean isSOTrx(String docBaseType);
}
