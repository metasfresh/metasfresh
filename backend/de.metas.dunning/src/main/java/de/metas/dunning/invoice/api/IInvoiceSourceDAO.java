package de.metas.dunning.invoice.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;

import org.compiere.model.I_C_Invoice;

import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.model.I_C_Dunning_Candidate_Invoice_v1;
import de.metas.util.ISingletonService;


/**
 * DAO methods related to {@link I_C_Invoice}s
 *
 * @author tsa
 *
 */
public interface IInvoiceSourceDAO extends ISingletonService
{
	int computeDueDays(Date dueDate, Date date);

	Iterator<I_C_Dunning_Candidate_Invoice_v1> retrieveDunningCandidateInvoices(IDunningContext context);
}
