package de.metas.vertical.pharma.vendor.gateway.mvs3.common;

import org.adempiere.exceptions.AdempiereException;

import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Msv3FaultInfo;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
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

public class Msv3ClientException extends AdempiereException
{
	public static Msv3ClientException createForFaultInfo(@NonNull final Msv3FaultInfo msv3FaultInfo)
	{
		return new Msv3ClientException(msv3FaultInfo);
	}

	private static final long serialVersionUID = -8587023660085593406L;

	@Getter
	private final Msv3FaultInfo msv3FaultInfo;

	private Msv3ClientException(@NonNull final Msv3FaultInfo msv3FaultInfo)
	{
		this.msv3FaultInfo = msv3FaultInfo;
		this.appendParametersToMessage()
				.setParameter("TechnischerFehlertext", msv3FaultInfo.getTechnischerFehlertext())
				.setParameter("EndanwenderFehlertext", msv3FaultInfo.getEndanwenderFehlertext())
				.setParameter("ErrorCode", msv3FaultInfo.getErrorCode());
	}
}
