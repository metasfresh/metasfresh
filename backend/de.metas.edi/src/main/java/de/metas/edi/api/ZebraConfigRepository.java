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

import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine_Pack;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Zebra_Config;
import org.compiere.model.I_C_BP_PrintFormat;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class ZebraConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private static final AdMessageKey MSG_NO_ZEBRA_CONFIG = AdMessageKey.of("WEBUI_NoZebraConfigDefined");

	@Nullable
	public I_AD_Zebra_Config getZebraConfigByIdOrDefault(@Nullable final ZebraConfigId zebraConfigId)
	{
		I_AD_Zebra_Config item = null;
		if (zebraConfigId == null)
		{
			item = queryBL
					.createQueryBuilderOutOfTrx(I_AD_Zebra_Config.class)
					.addEqualsFilter(I_AD_Zebra_Config.COLUMNNAME_IsDefault, true)
					.addOnlyActiveRecordsFilter()
					.addOnlyContextClient()
					.create()
					.firstOnly(I_AD_Zebra_Config.class);
			if (item == null)
			{
				throw new AdempiereException(msgBL.getTranslatableMsgText(MSG_NO_ZEBRA_CONFIG));
			}
		}
		else
		{

			item = queryBL
					.createQueryBuilderOutOfTrx(I_AD_Zebra_Config.class)
					.addEqualsFilter(I_AD_Zebra_Config.COLUMNNAME_AD_Zebra_Config_ID, zebraConfigId)
					.addOnlyActiveRecordsFilter()
					.addOnlyContextClient()
					.create()
					.firstOnly(I_AD_Zebra_Config.class);
		}
		return item;
	}

	public ZebraConfigId getZebraConfigForDesadvLinePackID(Integer desadvLinePackID)
	{
		return queryBL
				.createQueryBuilder(I_EDI_DesadvLine_Pack.class)
				.addEqualsFilter(I_EDI_DesadvLine_Pack.COLUMNNAME_EDI_DesadvLine_Pack_ID, desadvLinePackID)
				.andCollect(I_EDI_Desadv.COLUMNNAME_EDI_Desadv_ID, I_EDI_Desadv.class)
				.andCollect(I_EDI_Desadv.COLUMNNAME_C_BPartner_ID, I_C_BPartner.class)
				.andCollectChildren(I_C_BPartner.COLUMNNAME_C_BPartner_ID, I_C_BP_PrintFormat.class)
				.andCollect(I_C_BP_PrintFormat.COLUMNNAME_AD_Zebra_Config_ID, I_AD_Zebra_Config.class)
				.create()
				.firstId(ZebraConfigId::ofRepoIdOrNull);
	}
}
