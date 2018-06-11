/**
 *
 */
package de.metas.letter;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.springframework.stereotype.Repository;

import de.metas.letters.model.I_AD_BoilerPlate;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Repository
public class BoilerPlateRepository
{
	public BoilerPlate toBoilerPlate(@NonNull final I_AD_BoilerPlate boilerPlateRecord)
	{
		return BoilerPlate.builder()
				.id(BoilerPlateId.ofRepoId(boilerPlateRecord.getAD_BoilerPlate_ID()))
				.subject(boilerPlateRecord.getSubject())
				.textSnippext(boilerPlateRecord.getTextSnippext())
				.build();
	}


	public BoilerPlate getByBoilerPlateId(@NonNull final BoilerPlateId boilerPlateId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_BoilerPlate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_BoilerPlate.COLUMNNAME_AD_BoilerPlate_ID, boilerPlateId.getRepoId())
				.create()
				.stream()
				.map(this::toBoilerPlate)
				.findFirst()
				.orElse(null);
	}
}
