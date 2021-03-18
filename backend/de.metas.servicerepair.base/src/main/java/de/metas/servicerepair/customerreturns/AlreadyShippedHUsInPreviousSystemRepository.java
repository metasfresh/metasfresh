/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.customerreturns;

import de.metas.bpartner.BPartnerId;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.servicerepair.repository.model.I_ServiceRepair_Old_Shipped_HU;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.QueryLimit;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Repository
class AlreadyShippedHUsInPreviousSystemRepository
{
	private static final Logger logger = LogManager.getLogger(AlreadyShippedHUsInPreviousSystemRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public Optional<AlreadyShippedHUsInPreviousSystem> getBySerialNo(@Nullable final String serialNo)
	{
		final String serialNoNorm = StringUtils.trimBlankToNull(serialNo);
		if (serialNoNorm == null)
		{
			return Optional.empty();
		}

		try
		{
			final List<I_ServiceRepair_Old_Shipped_HU> rows = queryBL
					.createQueryBuilder(I_ServiceRepair_Old_Shipped_HU.class)
					.addEqualsFilter(I_ServiceRepair_Old_Shipped_HU.COLUMNNAME_SerialNo, serialNoNorm)
					.addNotNull(I_ServiceRepair_Old_Shipped_HU.COLUMNNAME_M_Product_ID)
					.addOnlyActiveRecordsFilter()
					.setLimit(QueryLimit.ofInt(100))
					.create()
					.list();
			if (rows.isEmpty())
			{
				return Optional.empty();
			}
			else if (rows.size() == 1)
			{
				return Optional.of(fromRecord(rows.get(0)));
			}
			else
			{
				logger.debug("More than result found for {}: {} => returning empty", serialNo, rows);
				return Optional.empty();
			}
		}
		catch (final Exception ex)
		{
			logger.debug("Failed fetching for {}. Returning empty.", serialNo, ex);
			return Optional.empty();
		}
	}

	private static AlreadyShippedHUsInPreviousSystem fromRecord(@NonNull final I_ServiceRepair_Old_Shipped_HU record)
	{
		return AlreadyShippedHUsInPreviousSystem.builder()
				.serialNo(record.getSerialNo())
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.customerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_Customer_ID()))
				.warrantyStartDate(TimeUtil.asLocalDate(record.getWarrantyStartDate()))
				.build();
	}

}
