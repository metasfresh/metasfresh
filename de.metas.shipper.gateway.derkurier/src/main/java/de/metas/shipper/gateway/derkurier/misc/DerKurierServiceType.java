package de.metas.shipper.gateway.derkurier.misc;

import de.metas.shipper.gateway.spi.model.ServiceType;
import lombok.Getter;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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

public enum DerKurierServiceType implements ServiceType
{
	OVERNIGHT("overnight");

	@Getter
	private final String code;

	private DerKurierServiceType(final String code)
	{
		this.code = code;
	}
}
