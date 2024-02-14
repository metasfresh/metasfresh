/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.audit.request;

import de.metas.audit.apirequest.request.ApiRequestAudit;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_API_Request_Audit;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

@Value(staticConstructor = "of")
public class ApiRequestIterator implements Iterator<ApiRequestAudit>
{
	@NonNull
	Iterator<I_API_Request_Audit> apiRequestRecordsIterator;

	@NonNull
	Function<I_API_Request_Audit, ApiRequestAudit> recordToModelMapper;

	@Override
	public boolean hasNext()
	{
		return apiRequestRecordsIterator.hasNext();
	}

	@Override
	public ApiRequestAudit next()
	{
		return recordToModelMapper.apply(apiRequestRecordsIterator.next());
	}

	public void forEach(@NonNull final Consumer<ApiRequestAudit> apiRequestAuditConsumer)
	{
		while (hasNext())
		{
			apiRequestAuditConsumer.accept(next());
		}
	}
}
