package de.metas.costing;

<<<<<<< HEAD
import java.time.LocalDate;

import javax.annotation.Nullable;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.acct.api.AcctSchemaId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

<<<<<<< HEAD
=======
import javax.annotation.Nullable;
import java.time.Instant;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	LocalDate date;
=======
	Instant date;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	String description;

	@Builder
	private CostDetailReverseRequest(
			@NonNull AcctSchemaId acctSchemaId,
			@NonNull CostingDocumentRef reversalDocumentRef,
			@NonNull CostingDocumentRef initialDocumentRef,
<<<<<<< HEAD
			@NonNull LocalDate date,
=======
			@NonNull Instant date,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			@Nullable String description)
	{
		this.acctSchemaId = acctSchemaId;
		this.reversalDocumentRef = reversalDocumentRef;
		this.initialDocumentRef = initialDocumentRef;
		this.date = date;
		this.description = description;
	}

}
