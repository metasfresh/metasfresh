/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.report;

import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_PrintForm;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultPrintFormatsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<ClientId, DefaultPrintFormats> cache = CCache.<ClientId, DefaultPrintFormats>builder()
			.tableName(I_AD_PrintForm.Table_Name)
			.build();

	public DefaultPrintFormats getByClientId(@NonNull final ClientId clientId)
	{
		return cache.getOrLoad(clientId, this::retrieveByClientId);
	}

	private DefaultPrintFormats retrieveByClientId(@NonNull final ClientId clientId)
	{
		final I_AD_PrintForm record = queryBL.createQueryBuilderOutOfTrx(I_AD_PrintForm.class)
				.addEqualsFilter(I_AD_PrintForm.COLUMNNAME_AD_Client_ID, clientId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_AD_PrintForm.class);

		if (record == null)
		{
			return DefaultPrintFormats.NONE;
		}

		return toDefaultPrintFormats(record);
	}

	private static DefaultPrintFormats toDefaultPrintFormats(final I_AD_PrintForm record)
	{
		return DefaultPrintFormats.builder()
				.orderPrintFormatId(PrintFormatId.ofRepoIdOrNull(record.getOrder_PrintFormat_ID()))
				.invoicePrintFormatId(PrintFormatId.ofRepoIdOrNull(record.getInvoice_PrintFormat_ID()))
				.shipmentPrintFormatId(PrintFormatId.ofRepoIdOrNull(record.getShipment_PrintFormat_ID()))
				.manufacturingOrderPrintFormatId(PrintFormatId.ofRepoIdOrNull(record.getManuf_Order_PrintFormat_ID()))
				.distributionOrderPrintFormatId(PrintFormatId.ofRepoIdOrNull(record.getDistrib_Order_PrintFormat_ID()))
				.build();
	}

}
