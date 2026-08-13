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

package de.metas.cucumber.stepdefs;

import de.metas.util.Services;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_AD_Column;

import static org.assertj.core.api.Assertions.*;

public class AD_Column_StepDef
{
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Given("^assert defaultValue is (.*) for tableName (.*) and columnName (.*)$")
	public void assertDefaultValue(
			@NonNull final String expectedDefaultValue,
			@NonNull final String tableName,
			@NonNull final String columnName)
	{
		final AdTableId tableId = AdTableId.ofRepoIdOrNull(tableDAO.retrieveTableId(tableName));
		assertThat(tableId).isNotNull();

		final String defaultValue = queryBL.createQueryBuilder(I_AD_Column.class)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_ColumnName, columnName)
				.create()
				.firstOnlyNotNull(I_AD_Column.class)
				.getDefaultValue();

		assertThat(defaultValue).isEqualTo(expectedDefaultValue);
	}
}