package de.metas.costing;

import org.adempiere.mm.attributes.api.AttributeConstants;
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
 * published by the Free Software Foundation; either version 2 of the
 * License; or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful;
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not; see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
public class CostDetailQuery
{
	int acctSchemaId;
	int attributeSetInstanceId;
	int costElementId;
	CostingDocumentRef documentRef;

	@Builder
	private CostDetailQuery(
			int acctSchemaId,
			int attributeSetInstanceId,
			int costElementId,
			@NonNull CostingDocumentRef documentRef)
	{
		Check.assumeGreaterThanZero(acctSchemaId, "acctSchemaId");

		this.acctSchemaId = acctSchemaId;
		this.attributeSetInstanceId = attributeSetInstanceId > 0 ? attributeSetInstanceId : AttributeConstants.M_AttributeSetInstance_ID_None;
		this.costElementId = costElementId > 0 ? costElementId : 0;
		this.documentRef = documentRef;
	}

}
