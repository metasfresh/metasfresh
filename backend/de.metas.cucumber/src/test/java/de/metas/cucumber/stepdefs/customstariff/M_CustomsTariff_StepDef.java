/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.customstariff;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.customstariff.CustomsTariff;
import de.metas.customstariff.CustomsTariffId;
import de.metas.customstariff.CustomsTariffRepository;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_CustomsTariff;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@RequiredArgsConstructor
public class M_CustomsTariff_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final CustomsTariffRepository customsTariffRepository = SpringContextHolder.instance.getBean(CustomsTariffRepository.class);

	@NonNull private final M_CustomsTariff_StepDefData customsTariffTable;

	/**
	 * Upserts by {@code Value} so re-runs don't collide on the unique constraint.
	 */
	@And("metasfresh contains M_CustomsTariff:")
	public void contains_M_CustomsTariff(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final String value = row.getAsString(I_M_CustomsTariff.COLUMNNAME_Value);
			final String name = row.getAsOptionalString(I_M_CustomsTariff.COLUMNNAME_Name).orElse(value);
			final String description = row.getAsOptionalString(I_M_CustomsTariff.COLUMNNAME_Description).orElse(null);

			final I_M_CustomsTariff record = CoalesceUtil.coalesceSuppliersNotNull(
					() -> queryBL.createQueryBuilder(I_M_CustomsTariff.class)
							.addOnlyActiveRecordsFilter()
							.addEqualsFilter(I_M_CustomsTariff.COLUMNNAME_Value, value)
							.create()
							.firstOnly(I_M_CustomsTariff.class),
					() -> newInstance(I_M_CustomsTariff.class));

			record.setValue(value);
			record.setName(name);
			record.setDescription(description);
			saveRecord(record);

			final CustomsTariff pojo = customsTariffRepository.getById(CustomsTariffId.ofRepoId(record.getM_CustomsTariff_ID()));
			customsTariffTable.putOrReplace(row.getAsIdentifier(), pojo);
		});
	}
}
