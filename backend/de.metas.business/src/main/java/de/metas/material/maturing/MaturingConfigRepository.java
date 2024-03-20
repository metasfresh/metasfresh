/*
 * #%L
 * de.metas.business
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

package de.metas.material.maturing;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Maturing_Configuration_Line;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class MaturingConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, MaturingConfigMap> cache = CCache.<Integer, MaturingConfigMap>builder()
			.tableName(I_M_Maturing_Configuration_Line.Table_Name)
			.build();

	public MaturingConfigLine getById(@NonNull final MaturingConfigLineId lineId)
	{
		return getMaturingConfigMap().getById(lineId);
	}

	public List<MaturingConfigLine> getByMaturedProductId(@NonNull final ProductId maturedProductId)
	{
		return getMaturingConfigMap().getByMaturedProductId(maturedProductId);
	}

	@NonNull
	public List<MaturingConfigLine> getByFromProductId(@NonNull final ProductId maturedProductId)
	{
		return getMaturingConfigMap().getByFromProductId(maturedProductId);
	}

	final MaturingConfigMap getMaturingConfigMap()
	{
		return cache.getOrLoad(0, this::retrieveMaturingConfigMap);
	}

	private MaturingConfigMap retrieveMaturingConfigMap()
	{
		final ImmutableList<MaturingConfigLine> configs = queryBL
				.createQueryBuilderOutOfTrx(I_M_Maturing_Configuration_Line.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(MaturingConfigRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new MaturingConfigMap(configs);
	}

	private static MaturingConfigLine fromRecord(@NonNull final I_M_Maturing_Configuration_Line record)
	{
		return MaturingConfigLine.builder()
				.id(MaturingConfigLineId.ofRepoIdOrNull(record.getM_Maturing_Configuration_Line_ID())) // accept not saved records
				.maturingConfigId(MaturingConfigId.ofRepoIdOrNull(record.getM_Maturing_Configuration_ID()))
				.fromProductId((ProductId.ofRepoIdOrNull(record.getFrom_Product_ID())))
				.maturedProductId(ProductId.ofRepoIdOrNull(record.getMatured_Product_ID()))
				.orgId(OrgId.ofRepoIdOrAny(record.getAD_Org_ID()))
				.maturityAge(Integer.valueOf(record.getMaturityAge()))
				.build();
	}

	public MaturingConfigLine save(@NonNull final MaturingConfigLine maturingConfigLine)
	{

		final I_M_Maturing_Configuration_Line record = InterfaceWrapperHelper.loadOrNew(maturingConfigLine.getId(), I_M_Maturing_Configuration_Line.class);
		updateRecord(record, maturingConfigLine);
		save(record);

		return fromRecord(record);
	}

	private static void updateRecord(@NonNull final I_M_Maturing_Configuration_Line record, @NonNull final MaturingConfigLine from)
	{
		record.setM_Maturing_Configuration_ID(from.getMaturingConfigId().getRepoId());
		record.setFrom_Product_ID(ProductId.toRepoId(from.getFromProductId()));
		record.setMatured_Product_ID(ProductId.toRepoId(from.getMaturedProductId()));
		record.setAD_Org_ID(from.getOrgId().getRepoId());
		record.setMaturityAge(from.getMaturityAge());
	}

	private static void save(@NonNull final I_M_Maturing_Configuration_Line record)
	{
		saveRecord(record);
	}
}
