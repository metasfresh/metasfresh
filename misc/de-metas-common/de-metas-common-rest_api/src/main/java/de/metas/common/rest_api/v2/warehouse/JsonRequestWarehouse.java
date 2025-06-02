/*
 * #%L
 * de-metas-common-rest_api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.common.rest_api.v2.warehouse;

import de.metas.common.rest_api.v2.SyncAdvise;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static de.metas.common.rest_api.v2.SwaggerDocConstants.LOCATION_IDENTIFIER_DOC;
import static de.metas.common.rest_api.v2.SwaggerDocConstants.READ_ONLY_SYNC_ADVISE_DOC;

@Getter
@ToString
@EqualsAndHashCode
public class JsonRequestWarehouse
{
	@ApiModelProperty(position = 10, value = "Corresponding to `M_Warehouse.Value`")
	private String code;

	@ApiModelProperty(hidden = true)
	private boolean codeSet;

	@ApiModelProperty(position = 20, value = "Corresponding to `M_Warehouse.Name`")
	private String name;

	@ApiModelProperty(hidden = true)
	private boolean nameSet;

	@ApiModelProperty(position = 30, value = LOCATION_IDENTIFIER_DOC)
	private String bpartnerLocationIdentifier;

	@ApiModelProperty(hidden = true)
	private boolean bpartnerLocationIdentifierSet;

	@ApiModelProperty(position = 40, value = "Corresponding to `M_Warehouse.isActive`")
	private Boolean active;

	@ApiModelProperty(hidden = true)
	private boolean activeSet;

	@ApiModelProperty(position = 50, value = READ_ONLY_SYNC_ADVISE_DOC)
	private SyncAdvise syncAdvise;

	public void setCode(final String code)
	{
		this.code = code;
		this.codeSet = true;
	}

	public void setName(final String name)
	{
		this.name = name;
		this.nameSet = true;
	}

	public void setBpartnerLocationIdentifier(final String bpartnerLocationIdentifier)
	{
		this.bpartnerLocationIdentifier = bpartnerLocationIdentifier;
		this.bpartnerLocationIdentifierSet = true;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
	}
}
