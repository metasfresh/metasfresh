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

package de.metas.postgrest.process.json;

import de.metas.postgrest.process.PostgRESTProcessExecutor;
import de.metas.process.ProcessCalledFrom;

public class Invoice_RKSV_JSON extends PostgRESTProcessExecutor
{
	@Override
	protected final CustomPostgRESTParameters beforePostgRESTCall()
	{
		final boolean calledViaAPI = isCalledViaAPI();

		return CustomPostgRESTParameters.builder()
				.storeJsonFile(!calledViaAPI)
				.expectSingleResult(true) // because we export exactly one record, we don't want the JSON to be an array
				.build();
	}

	private boolean isCalledViaAPI()
	{
		return ProcessCalledFrom.API.equals(getProcessInfo().getProcessCalledFrom());
	}
}
