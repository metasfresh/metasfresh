/**
 *
 */
package org.adempiere.bpartner.service;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BP_PrintFormat;
import org.springframework.stereotype.Repository;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Repository
public class BPartnerPrintFormatRepository
{
	public int retrieveBPartnerPrintFormatId(@NonNull final BPPrintFormat bpPrintFormat)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_PrintFormat.class)
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_C_BPartner_ID, bpPrintFormat.getBpartnerId())
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_AD_Table_ID, bpPrintFormat.getAdTableId())
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_C_DocType_ID, bpPrintFormat.getDocTypeId())
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_AD_PrintFormat_ID, bpPrintFormat.getPrintFormatId())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly();
	}
}
