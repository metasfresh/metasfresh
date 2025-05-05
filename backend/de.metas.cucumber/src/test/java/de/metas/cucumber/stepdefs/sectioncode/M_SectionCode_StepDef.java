/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.sectioncode;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_SectionCode;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class M_SectionCode_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_SectionCode_StepDefData sectionCodeTable;

	public M_SectionCode_StepDef(@NonNull final M_SectionCode_StepDefData sectionCodeTable)
	{
		this.sectionCodeTable = sectionCodeTable;
	}

	@Given("metasfresh contains M_SectionCode:")
	public void metasfresh_contains_M_SectionCode(@NonNull final DataTable dataTable)
	{
		dataTable.asMaps().forEach(this::upsertM_SectionCode);
	}

	@Given("create M_SectionCode:")
	public void create_M_SectionCode(@NonNull final DataTable dataTable)
	{
		dataTable.asMaps().forEach(this::createM_SectionCode);
	}

	private void upsertM_SectionCode(@NonNull final Map<String, String> row)
	{
		final String value = DataTableUtil.extractStringForColumnName(row, I_M_SectionCode.COLUMNNAME_Value);

		final I_M_SectionCode record = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_M_SectionCode.class)
						.addEqualsFilter(I_M_SectionCode.COLUMNNAME_Value, value)
						.create()
						.firstOnlyOrNull(I_M_SectionCode.class),
				() -> InterfaceWrapperHelper.newInstance(I_M_SectionCode.class));

		assertThat(record).isNotNull();

		record.setValue(value);
		record.setName(value);

		InterfaceWrapperHelper.save(record);

		final String sectionCodeIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_SectionCode.COLUMNNAME_M_SectionCode_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		sectionCodeTable.putOrReplace(sectionCodeIdentifier, record);
	}

	private void createM_SectionCode(@NonNull final Map<String, String> row)
	{
		final String value = DataTableUtil.extractStringForColumnName(row, I_M_SectionCode.COLUMNNAME_Value);
		final int sectionCodeId = DataTableUtil.extractIntForColumnName(row, I_M_SectionCode.COLUMNNAME_M_SectionCode_ID);

		final String sectionCodeIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_SectionCode.COLUMNNAME_M_SectionCode_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		final I_M_SectionCode existingSectionCode = queryBL.createQueryBuilder(I_M_SectionCode.class)
				.addEqualsFilter(I_M_SectionCode.COLUMNNAME_M_SectionCode_ID, sectionCodeId)
				.create()
				.firstOnlyOrNull(I_M_SectionCode.class);

		if (existingSectionCode != null)
		{
			sectionCodeTable.putOrReplace(sectionCodeIdentifier, existingSectionCode);
			return;
		}

		final I_M_SectionCode record = InterfaceWrapperHelper.newInstance(I_M_SectionCode.class);
		record.setM_SectionCode_ID(sectionCodeId);

		record.setValue(value);
		record.setName(value);

		InterfaceWrapperHelper.save(record);

		sectionCodeTable.putOrReplace(sectionCodeIdentifier, record);
	}
}
