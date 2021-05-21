/*
 * #%L
 * de-metas-common-bpartner
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.bpartner.v2.request.alberta;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class JsonAlbertaCareGiver
{
	@ApiModelProperty(position = 10)
	private String caregiverIdentifier;

	@ApiModelProperty(hidden = true)
	private boolean caregiverIdentifierSet;

	@ApiModelProperty(position = 20)
	private String type;

	@ApiModelProperty(hidden = true)
	private boolean typeSet;

	@ApiModelProperty(position = 30)
	private Boolean isLegalCarer;

	@ApiModelProperty(hidden = true)
	private boolean legalCarerSet;

	public void setCaregiverIdentifier(final String caregiverIdentifier)
	{
		this.caregiverIdentifier = caregiverIdentifier;
		this.caregiverIdentifierSet = true;
	}

	public void setType(final String type)
	{
		this.type = type;
		this.typeSet = true;
	}

	public void setIsLegalCarer(final Boolean isLegalCarer)
	{
		this.isLegalCarer = isLegalCarer;
		this.legalCarerSet = true;
	}
}
