package de.metas.costing;

import de.metas.acct.api.AcctSchemaId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class CostDetailReverseRequest
{
	AcctSchemaId acctSchemaId;
	CostingDocumentRef reversalDocumentRef;
	CostingDocumentRef initialDocumentRef;
	Instant date;
	String description;

	@Builder
	private CostDetailReverseRequest(
			@NonNull AcctSchemaId acctSchemaId,
			@NonNull CostingDocumentRef reversalDocumentRef,
			@NonNull CostingDocumentRef initialDocumentRef,
			@NonNull Instant date,
			@Nullable String description)
	{
		this.acctSchemaId = acctSchemaId;
		this.reversalDocumentRef = reversalDocumentRef;
		this.initialDocumentRef = initialDocumentRef;
		this.date = date;
		this.description = description;
	}

}
