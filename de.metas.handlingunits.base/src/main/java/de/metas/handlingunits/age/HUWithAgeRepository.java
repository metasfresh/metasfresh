package de.metas.handlingunits.age;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.util.Services;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

/*
 * #%L
 * metasfresh-pharma
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

@Repository
public class HUWithAgeRepository
{
	private final ImmutableList<String> validHuStatuses = ImmutableList.<String>builder()
			.add(X_M_HU.HUSTATUS_Active)
			.build();

	public Stream<I_M_HU> getAllWhereProductionDateIsNotEmptyAndQtyOnHandStatus()
	{
		return Services.get(IHandlingUnitsDAO.class)
				.createHUQueryBuilder()
				.addOnlyWithAttributeNotNull(HUAttributeConstants.ATTR_ProductionDate)
				.addHUStatusesToInclude(validHuStatuses)
				.createQueryBuilder()
				.create()
				.stream();
	}
}
