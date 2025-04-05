/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.fresh.partnerreporttext.api.impl;

import de.metas.fresh.partnerreporttext.api.IBPartnerDocTypeDAO;
import de.metas.fresh.partnerreporttext.model.I_C_BPartner_DocType;
import de.metas.fresh.partnerreporttext.model.I_C_BPartner_Report_Text;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;

public class BPartnerDocTypeDAO implements IBPartnerDocTypeDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public void setBPartner(final I_C_BPartner_DocType bPartnerDocType, final int bpartnerId)
	{
		bPartnerDocType.setC_BPartner_ID(bpartnerId);
	}

	@Override
	public void save(@NonNull final I_C_BPartner_DocType bPartnerDocType)
	{
		InterfaceWrapperHelper.save(bPartnerDocType);
	}

	@Override
	public boolean isPartnerDocTypeUnique(final I_C_BPartner_Report_Text reportTextRecord, final int docTypeID)
	{
		if (reportTextRecord == null || reportTextRecord.getC_BPartner_Report_Text_ID() <= 0)
		{
			return false;
		}

		final IQueryBuilder<I_C_BPartner_DocType> queryBuilder = queryBL.createQueryBuilder(I_C_BPartner_DocType.class)
				.addEqualsFilter(I_C_BPartner_DocType.COLUMNNAME_C_BPartner_ID, reportTextRecord.getC_BPartner_ID())
				.addEqualsFilter(I_C_BPartner_DocType.COLUMNNAME_C_DocType_ID, docTypeID)
				.orderBy(I_C_BPartner_DocType.COLUMNNAME_C_BPartner_DocType_ID);

		return queryBuilder.create().list().isEmpty();
	}
}
