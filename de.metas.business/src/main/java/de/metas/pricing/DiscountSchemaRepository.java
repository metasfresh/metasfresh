package de.metas.pricing;

import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import org.compiere.model.I_M_DiscountSchemaLine;
import org.springframework.stereotype.Repository;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Repository
public class DiscountSchemaRepository
{
//	private I_M_DiscountSchema getById(final DiscountSchemaId id)
//	{
//		return load(id, I_M_DiscountSchema.class);
//	}

	private I_M_DiscountSchemaLine getLineById(final DiscountSchemaLineId id)
	{
		return load(id, I_M_DiscountSchemaLine.class);
	}

	public void createLineCopy(final DiscountSchemaLineId discountSchemaLineId, final DiscountSchemaId discountSchemaId)
	{

		final I_M_DiscountSchemaLine lineRecord = getLineById(discountSchemaLineId);

		final I_M_DiscountSchemaLine newLine = newInstance(I_M_DiscountSchemaLine.class);
		newLine.setM_DiscountSchema_ID(discountSchemaId.getRepoId());

		copy()
				.setSkipCalculatedColumns(true)
				.addTargetColumnNameToSkip(I_M_DiscountSchemaLine.COLUMNNAME_M_DiscountSchema_ID)
				.setFrom(lineRecord)
				.setTo(newLine)
				.copy();

	}
}
