package de.metas.handlingunits.inventory.draftlinescreator.aggregator;

import de.metas.document.DocBaseAndSubType;
import de.metas.inventory.AggregationType;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

/*
 * #%L
 * de.metas.handlingunits.base
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

@UtilityClass
public class InventoryLineAggregatorFactory
{
	public static InventoryLineAggregator getForDocBaseAndSubType(@NonNull final DocBaseAndSubType docBaseAndSubType)
	{
		final AggregationType aggregationMode = AggregationType.getByDocTypeOrNull(docBaseAndSubType);
		Check.assumeNotNull(aggregationMode, "Unexpected docBaseAndSubType={} with no registered aggregationMode", docBaseAndSubType);

		try
		{
			return getForAggregationMode(aggregationMode);
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("docBaseAndSubType", docBaseAndSubType)
					.appendParametersToMessage();
		}
	}

	public static InventoryLineAggregator getForAggregationMode(@NonNull final AggregationType aggregationMode)
	{
		switch (aggregationMode)
		{
			case SINGLE_HU:
				return SingleHUInventoryLineAggregator.INSTANCE;

			case MULTIPLE_HUS:
				return MultipleHUInventoryLineAggregator.INSTANCE;

			default:
				throw new AdempiereException("Unexpected aggregationMode: " + aggregationMode);
		}
	}
}
