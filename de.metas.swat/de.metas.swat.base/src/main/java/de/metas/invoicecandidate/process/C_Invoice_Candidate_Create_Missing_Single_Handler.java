package de.metas.invoicecandidate.process;

import java.util.Collections;
import java.util.List;

import de.metas.invoicecandidate.model.I_C_ILCandHandler;

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

/**
 * Creates missing invoice candidates for the currently selected invoice candidate handler.
 * <p>
 * Note: this should even work with an inactive handler.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class C_Invoice_Candidate_Create_Missing_Single_Handler
		extends C_Invoice_Candidate_Create_Missing
{

	@Override
	protected List<I_C_ILCandHandler> retrieveHandlers()
	{
		final I_C_ILCandHandler handlerRecord = getRecord(I_C_ILCandHandler.class);
		return Collections.singletonList(handlerRecord);
	}
}
