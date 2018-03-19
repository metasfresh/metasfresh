package de.metas.vertical.pharma.vendor.gateway.msv3.common;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import de.metas.vertical.pharma.vendor.gateway.msv3.schema.Msv3FaultInfo;
import lombok.Builder;
import lombok.Getter;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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

public class Msv3ClientException
		extends AdempiereException
{
	private static final long serialVersionUID = -8587023660085593406L;

	@Getter
	private final Msv3FaultInfo msv3FaultInfo;

	@Builder
	private Msv3ClientException(
			@Nullable final Msv3FaultInfo msv3FaultInfo,
			@Nullable final Throwable cause)
	{
		super(cause);
		this.msv3FaultInfo = msv3FaultInfo;

		Check.errorIf(msv3FaultInfo == null && cause == null,
				"At elast one of the given msv3FaultInfo and cause parameters need to be not-null");

		if (msv3FaultInfo != null)
		{
			this.appendParametersToMessage()
					.setParameter("TechnischerFehlertext", msv3FaultInfo.getTechnischerFehlertext())
					.setParameter("EndanwenderFehlertext", msv3FaultInfo.getEndanwenderFehlertext())
					.setParameter("ErrorCode", msv3FaultInfo.getErrorCode());
		}
	}
}
