package de.metas.shipper.gateway.spi.model;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.shipper.gateway.api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class PackageDimensions
{
	int lengthInCM;
	int widthInCM;
	int heightInCM;

	/**
	 * Note: dimensions may be <= 0 which can stand for "not specified".
	 */
	@Builder
	private PackageDimensions(final int lengthInCM, final int widthInCM, final int heightInCM)
	{
		this.lengthInCM = lengthInCM;
		this.widthInCM = widthInCM;
		this.heightInCM = heightInCM;
	}

}
