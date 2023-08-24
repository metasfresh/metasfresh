/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.acctschema;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.calendar.C_Calendar_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_AcctSchema_Element;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Year;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;

public class C_AcctSchema_Element_StepDef
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_AcctSchema_Element_StepDefData acctElementTable;
	private final C_AcctSchema_StepDefData acctSchemaTable;
	private final C_Calendar_StepDefData calendarTable;
	private final C_Year_StepDefData yearTable;

	public C_AcctSchema_Element_StepDef(@NonNull final C_AcctSchema_Element_StepDefData acctElementTable,
			@NonNull final C_AcctSchema_StepDefData acctSchemaTable,
			@NonNull final C_Calendar_StepDefData calendarTable,
			@NonNull final C_Year_StepDefData yearTable
	)
	{
		this.acctElementTable = acctElementTable;
		this.acctSchemaTable = acctSchemaTable;
		this.calendarTable = calendarTable;
		this.yearTable = yearTable;
	}

	@And("metasfresh contains C_AcctSchema_Element:")
	public void metasfreshContainsC_AcctSchema_Element(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			createC_AcctSchema_Element(row);
		}
	}

	private void createC_AcctSchema_Element(@NonNull final Map<String, String> row)
	{
		final String name = DataTableUtil.extractStringForColumnName(row, I_C_AcctSchema_Element.COLUMNNAME_Name);


		final I_C_AcctSchema_Element acctSchemaElement = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_C_AcctSchema_Element.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_C_AcctSchema_Element.COLUMNNAME_Name, name)
						.create()
						.firstOnlyOrNull(I_C_AcctSchema_Element.class),
				() -> InterfaceWrapperHelper.newInstance(I_C_AcctSchema_Element.class));


		final String type = DataTableUtil.extractStringForColumnName(row, I_C_AcctSchema_Element.COLUMN_ElementType.getColumnName());

		final String calendarIdentifier = DataTableUtil.extractNullableStringForColumnName(row, "OPT." +I_C_AcctSchema_Element.COLUMNNAME_C_Harvesting_Calendar_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Optional<I_C_Calendar> calendar = calendarIdentifier!= null ? Optional.of(calendarTable.get(calendarIdentifier)) : Optional.empty();

		final String yearIdentifier = DataTableUtil.extractNullableStringForColumnName(row, "OPT." +I_C_AcctSchema_Element.COLUMNNAME_Harvesting_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Optional<I_C_Year> year =  yearIdentifier!=null ? Optional.of(yearTable.get(yearIdentifier)) : Optional.empty();

		final String schemaIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_AcctSchema_Element.COLUMNNAME_C_AcctSchema_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_AcctSchema schema = acctSchemaTable.get(schemaIdentifier);

		acctSchemaElement.setName(name);
		acctSchemaElement.setElementType(type);
		calendar.ifPresent(calendarRec -> acctSchemaElement.setC_Harvesting_Calendar_ID(calendarRec.getC_Calendar_ID()));
		year.ifPresent(yearRec -> acctSchemaElement.setHarvesting_Year_ID(yearRec.getC_Year_ID()));

		acctSchemaElement.setC_AcctSchema_ID(schema.getC_AcctSchema_ID());
		acctSchemaElement.setSeqNo(10);
		InterfaceWrapperHelper.save(acctSchemaElement);

		final String acctSchemaElementIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_AcctSchema_Element.COLUMNNAME_C_AcctSchema_Element_ID + "." + TABLECOLUMN_IDENTIFIER);

		acctElementTable.put(acctSchemaElementIdentifier, acctSchemaElement);
	}
}
