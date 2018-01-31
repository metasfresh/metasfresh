package de.metas.vertical.pharma.vendor.gateway.mvs3.common;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import javax.annotation.Nullable;

import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Msv3FaultInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

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

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Msv3FaultInfoDataPersister
{
	public static Msv3FaultInfoDataPersister newInstanceWithOrgId(final int orgId)
	{
		return new Msv3FaultInfoDataPersister(orgId);
	}

	private final int orgId;

	public I_MSV3_FaultInfo storeMsv3FaultInfoOrNull(@Nullable final Msv3FaultInfo msv3FaultInfo)
	{
		if (msv3FaultInfo == null)
		{
			return null;
		}

		final I_MSV3_FaultInfo faultInfoRecord = newInstance(I_MSV3_FaultInfo.class);
		faultInfoRecord.setAD_Org_ID(orgId);
		faultInfoRecord.setMSV3_EndanwenderFehlertext(msv3FaultInfo.getEndanwenderFehlertext());
		faultInfoRecord.setMSV3_ErrorCode(msv3FaultInfo.getErrorCode());
		faultInfoRecord.setMSV3_FaultInfoType(msv3FaultInfo.getClass().getSimpleName());
		faultInfoRecord.setMSV3_TechnischerFehlertext(msv3FaultInfo.getTechnischerFehlertext());
		save(faultInfoRecord);

		return faultInfoRecord;
	}
}
