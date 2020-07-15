package de.metas.invoicecandidate.api;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;

import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.util.ISingletonService;

public interface IInvoiceCandidateHandlerDAO extends ISingletonService
{
	List<I_C_ILCandHandler> retrieveAll(Properties ctx);

	List<I_C_ILCandHandler> retrieveForTable(Properties ctx, String tableName);

	/**
	 * Loads the {@link I_C_ILCandHandler} records that have given class.
	 *
	 * Note: The method returns both active and inactive records.
	 *
	 * @param ctx
	 * @param clazz
	 * @return
	 */
	List<I_C_ILCandHandler> retrieveForClass(Properties ctx, Class<? extends IInvoiceCandidateHandler> clazz);

	/**
	 * Retrieve {@link I_C_ILCandHandler} by given <code>handlerClass</code>
	 *
	 * NOTE: this method returns only the active one
	 *
	 * @param ctx
	 * @param handlerClass
	 * @return {@link I_C_ILCandHandler}
	 * @throws AdempiereException if not handlers found or more then one handler was found for <code>handlerClass</code>
	 */
	I_C_ILCandHandler retrieveForClassOneOnly(Properties ctx, Class<? extends IInvoiceCandidateHandler> handlerClass);

	I_C_ILCandHandler retrieveFor(I_C_Invoice_Candidate ic);
}
