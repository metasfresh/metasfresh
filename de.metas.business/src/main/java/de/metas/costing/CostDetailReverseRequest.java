package de.metas.costing;

import java.time.LocalDate;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
	int acctSchemaId;
	CostingDocumentRef reversalDocumentRef;
	CostingDocumentRef initialDocumentRef;
	LocalDate date;
	String description;

	@Builder
	private CostDetailReverseRequest(
			int acctSchemaId,
			@NonNull CostingDocumentRef reversalDocumentRef,
			@NonNull CostingDocumentRef initialDocumentRef,
			@NonNull LocalDate date,
			@NonNull String description)
	{
		Check.assume(acctSchemaId > 0, "acctSchemaId > 0");
		this.acctSchemaId = acctSchemaId;
		this.reversalDocumentRef = reversalDocumentRef;
		this.initialDocumentRef = initialDocumentRef;
		this.date = date;
		this.description = description;
	}

}
