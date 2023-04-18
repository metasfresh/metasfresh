/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v2.project.workorder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.function.Function;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.OBJECT_UNDER_TEST_IDENTIFIER_DOC;

@Getter
@ToString
@EqualsAndHashCode
public class JsonWorkOrderObjectUnderTestUpsertItemRequest
{
	@Schema(required = true,
			description = OBJECT_UNDER_TEST_IDENTIFIER_DOC) //
	@Setter
	String identifier;

	@Schema(required = true)
	@Setter
	Integer numberOfObjectsUnderTest;

	String woDeliveryNote;

	@Schema(hidden = true)
	boolean woDeliveryNoteSet;

	String woManufacturer;

	@Schema(hidden = true)
	boolean woManufacturerSet;

	String woObjectType;

	@Schema(hidden = true)
	boolean woObjectTypeSet;

	String woObjectName;

	@Schema(hidden = true)
	boolean woObjectNameSet;

	String woObjectWhereabouts;

	@Schema(hidden = true)
	boolean woObjectWhereaboutsSet;

	String externalId;

	@Schema(hidden = true)
	boolean externalIdSet;

	public void setWoDeliveryNote(final String woDeliveryNote)
	{
		this.woDeliveryNote = woDeliveryNote;
		this.woDeliveryNoteSet = true;
	}

	public void setWoManufacturer(final String woManufacturer)
	{
		this.woManufacturer = woManufacturer;
		this.woManufacturerSet = true;
	}

	public void setWoObjectType(final String woObjectType)
	{
		this.woObjectType = woObjectType;
		this.woObjectTypeSet = true;
	}

	public void setWoObjectName(final String woObjectName)
	{
		this.woObjectName = woObjectName;
		this.woObjectNameSet = true;
	}

	public void setWoObjectWhereabouts(final String woObjectWhereabouts)
	{
		this.woObjectWhereabouts = woObjectWhereabouts;
		this.woObjectWhereaboutsSet = true;
	}

	public void setExternalId(final String externalId)
	{
		this.externalId = externalId;
		this.externalIdSet = true;
	}

	@JsonIgnore
	@NonNull
	public <T> T mapObjectIdentifier(@NonNull final Function<String, T> mappingFunction)
	{
		return mappingFunction.apply(identifier);
	}
}
