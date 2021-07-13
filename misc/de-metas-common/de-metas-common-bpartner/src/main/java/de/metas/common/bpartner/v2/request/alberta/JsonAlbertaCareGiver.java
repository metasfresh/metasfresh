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
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;

@Getter
@ToString
@EqualsAndHashCode
public class JsonAlbertaCareGiver
{
	@ApiModelProperty(position = 10)
	private String caregiverIdentifier;

	@ApiModelProperty(position = 20)
	@Nullable
	private String type;

	@ApiModelProperty(hidden = true)
	private boolean typeSet;

	@ApiModelProperty(position = 30)
	@Nullable
	private Boolean isLegalCarer;

	@ApiModelProperty(hidden = true)
	private boolean legalCarerSet;

	public void setCaregiverIdentifier(@NonNull final String caregiverIdentifier)
	{
		this.caregiverIdentifier = caregiverIdentifier;
	}

	public void setType(@Nullable final String type)
	{
		this.type = type;
		this.typeSet = true;
	}

	public void setIsLegalCarer(@Nullable final Boolean isLegalCarer)
	{
		this.isLegalCarer = isLegalCarer;
		this.legalCarerSet = true;
	}
}
