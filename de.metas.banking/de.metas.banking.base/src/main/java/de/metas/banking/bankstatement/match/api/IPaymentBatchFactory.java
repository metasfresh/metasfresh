package de.metas.banking.bankstatement.match.api;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Payment;

import de.metas.banking.bankstatement.match.spi.IPaymentBatch;
import de.metas.banking.bankstatement.match.spi.IPaymentBatchProvider;

/*
 * #%L
 * de.metas.banking.base
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

/**
 * Creates {@link IPaymentBatch}es.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IPaymentBatchFactory extends ISingletonService
{
	IPaymentBatch retrievePaymentBatch(I_C_Payment payment);

	void addPaymentBatchProvider(IPaymentBatchProvider provider);
}
