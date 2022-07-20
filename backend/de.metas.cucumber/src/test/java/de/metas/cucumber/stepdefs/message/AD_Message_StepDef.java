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

package de.metas.cucumber.stepdefs.message;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Message;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AD_Message_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_Message_StepDefData messageTable;

	public AD_Message_StepDef(@NonNull final AD_Message_StepDefData messageTable)
	{
		this.messageTable = messageTable;
	}

	@And("metasfresh contains AD_Message:")
	public void metasfresh_contains_ad_message(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{

			final String value = DataTableUtil.extractStringForColumnName(row, I_AD_Message.COLUMNNAME_Value);

			final I_AD_Message adMessageRecord = CoalesceUtil.coalesceSuppliers(
					() -> queryBL.createQueryBuilder(I_AD_Message.class)
							.addStringLikeFilter(I_AD_Message.COLUMNNAME_Value, value, true)
							.create()
							.firstOnlyOrNull(I_AD_Message.class),
					() -> InterfaceWrapperHelper.newInstance(I_AD_Message.class));

			assertThat(adMessageRecord).isNotNull();

			final String messageIdentifier = DataTableUtil.extractRecordIdentifier(row, StepDefConstants.TABLECOLUMN_IDENTIFIER);
			messageTable.putOrReplace(messageIdentifier, adMessageRecord);
		}
	}
}
