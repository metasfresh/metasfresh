/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cucumber.stepdefs.pporder.maturing;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Maturing_Configuration;
import org.compiere.model.I_M_Maturing_Configuration_Line;
import org.compiere.model.I_M_Product;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class M_Maturing_Configuration_StepDef
{
	M_Maturing_Configuration_StepDefData maturingConfigurationTable;
	M_Maturing_Configuration_Line_StepDefData maturingConfigurationLineTable;
	M_Product_StepDefData productStepDefData;
	IQueryBL queryBL = Services.get(IQueryBL.class);

	public M_Maturing_Configuration_StepDef(@NonNull final M_Maturing_Configuration_StepDefData maturingConfigurationTable,
											@NonNull final M_Maturing_Configuration_Line_StepDefData maturingConfigurationLineTable,
											@NonNull final M_Product_StepDefData productStepDefData)
	{
		this.maturingConfigurationTable = maturingConfigurationTable;
		this.maturingConfigurationLineTable = maturingConfigurationLineTable;
		this.productStepDefData = productStepDefData;
	}

	@Given("metasfresh contains M_Maturing_Configurations")
	public void createMaturingConfigurations(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createUpdateMaturingConfigurationRow);

	}

	private void createUpdateMaturingConfigurationRow(final DataTableRow tableRow)
	{
		final StepDefDataIdentifier identifier = tableRow.getAsIdentifier(I_M_Maturing_Configuration.COLUMNNAME_M_Maturing_Configuration_ID);
		final I_M_Maturing_Configuration maturingConfiguration = maturingConfigurationTable.getOptional(identifier)
				.orElse(InterfaceWrapperHelper.newInstance(I_M_Maturing_Configuration.class));

		maturingConfiguration.setName(tableRow.getAsString(I_M_Maturing_Configuration.COLUMNNAME_Name));

		saveRecord(maturingConfiguration);
		maturingConfigurationTable.putOrReplace(identifier, maturingConfiguration);
	}

	@Given("metasfresh contains M_Maturing_Configuration_Lines")
	public void createMaturingConfigurationLines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createUpdateMaturingConfigurationLineRow);

	}

	private void createUpdateMaturingConfigurationLineRow(final DataTableRow tableRow)
	{
		final I_M_Maturing_Configuration maturingConfiguration = tableRow.getAsIdentifier(I_M_Maturing_Configuration.COLUMNNAME_M_Maturing_Configuration_ID).lookupIn(maturingConfigurationTable);
		final StepDefDataIdentifier identifier = tableRow.getAsIdentifier(I_M_Maturing_Configuration_Line.COLUMNNAME_M_Maturing_Configuration_Line_ID);
		final I_M_Product fromProduct = tableRow.getAsIdentifier(I_M_Maturing_Configuration_Line.COLUMNNAME_From_Product_ID).lookupIn(productStepDefData);
		final I_M_Product maturedProduct = tableRow.getAsIdentifier(I_M_Maturing_Configuration_Line.COLUMNNAME_Matured_Product_ID).lookupIn(productStepDefData);
		final int maturityAge = tableRow.getAsInt(I_M_Maturing_Configuration_Line.COLUMNNAME_MaturityAge);

		final I_M_Maturing_Configuration_Line maturingConfigurationLine = maturingConfigurationLineTable.getOptional(identifier)
				.orElse(loadMaturingConfigurationLine(fromProduct, maturedProduct));
		maturingConfigurationLine.setM_Maturing_Configuration_ID(maturingConfiguration.getM_Maturing_Configuration_ID());
		maturingConfigurationLine.setFrom_Product_ID(fromProduct.getM_Product_ID());
		maturingConfigurationLine.setMatured_Product_ID(maturedProduct.getM_Product_ID());
		maturingConfigurationLine.setMaturityAge(maturityAge);

		saveRecord(maturingConfigurationLine);

		maturingConfigurationLineTable.putOrReplace(identifier, maturingConfigurationLine);
	}

	private I_M_Maturing_Configuration_Line loadMaturingConfigurationLine(@NonNull final I_M_Product fromProduct,
																		  @NonNull final I_M_Product maturedProduct)
	{
		return queryBL.createQueryBuilder(I_M_Maturing_Configuration_Line.class)
				.addEqualsFilter(I_M_Maturing_Configuration_Line.COLUMNNAME_From_Product_ID, fromProduct.getM_Product_ID())
				.addEqualsFilter(I_M_Maturing_Configuration_Line.COLUMNNAME_Matured_Product_ID, maturedProduct.getM_Product_ID())
				.orderBy(I_M_Maturing_Configuration_Line.COLUMNNAME_M_Maturing_Configuration_Line_ID)
				.create()
				.firstOnlyOptional(I_M_Maturing_Configuration_Line.class)
				.orElse(InterfaceWrapperHelper.newInstance(I_M_Maturing_Configuration_Line.class));
	}
}
