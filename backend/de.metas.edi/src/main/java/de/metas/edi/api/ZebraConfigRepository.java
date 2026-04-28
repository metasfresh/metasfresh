/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.api;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ZebraConfigId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Zebra_Config;
import org.compiere.model.I_C_BP_PrintFormat;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

@Repository
public class ZebraConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private static final AdMessageKey MSG_NO_ZEBRA_CONFIG = AdMessageKey.of("WEBUI_NoZebraConfigDefined");

	public I_AD_Zebra_Config getById(@NonNull final ZebraConfigId zebraConfigId)
	{
		return loadOutOfTrx(zebraConfigId.getRepoId(), I_AD_Zebra_Config.class);
	}

	public ZebraConfigId getDefaultZebraConfigId()
	{
		final ZebraConfigId defaultZebraConfigId = queryBL
				.createQueryBuilderOutOfTrx(I_AD_Zebra_Config.class)
				.addEqualsFilter(I_AD_Zebra_Config.COLUMNNAME_IsDefault, true)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.create()
				.firstId(ZebraConfigId::ofRepoIdOrNull);

		if (defaultZebraConfigId == null)
		{
			throw new AdempiereException(MSG_NO_ZEBRA_CONFIG);
		}

		return defaultZebraConfigId;
	}

	public ZebraConfigId retrieveZebraConfigId(final BPartnerId partnerId, final ZebraConfigId defaultZebraConfigId)
	{
		final I_C_BP_PrintFormat printFormat = queryBL
				.createQueryBuilder(I_C_BP_PrintFormat.class)
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_C_BPartner_ID, partnerId)
				.addNotNull(I_C_BP_PrintFormat.COLUMN_AD_Zebra_Config_ID)
				.create()
				.firstOnly(I_C_BP_PrintFormat.class);

		if (printFormat != null)
		{
			return ZebraConfigId.ofRepoIdOrNull(printFormat.getAD_Zebra_Config_ID());
		}
		return defaultZebraConfigId;
	}
}
