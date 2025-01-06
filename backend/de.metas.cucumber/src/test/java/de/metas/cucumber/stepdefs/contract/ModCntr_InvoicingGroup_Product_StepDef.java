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

package de.metas.cucumber.stepdefs.contract;

import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.model.I_ModCntr_InvoicingGroup;
import de.metas.contracts.model.I_ModCntr_InvoicingGroup_Product;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.message.AD_Message_StepDefData;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_AD_Message.COLUMNNAME_AD_Message_ID;

public class ModCntr_InvoicingGroup_Product_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final ModCntr_InvoicingGroup_Product_StepDefData modCntrInvoicingGroupProductTable;
	private final ModCntr_InvoicingGroup_StepDefData modCntrInvoicingGroupTable;
	private final M_Product_StepDefData productTable;
	private final AD_Message_StepDefData messageTable;

	public ModCntr_InvoicingGroup_Product_StepDef(
			@NonNull final ModCntr_InvoicingGroup_Product_StepDefData modCntrInvoicingGroupProductTable,
			@NonNull final ModCntr_InvoicingGroup_StepDefData modCntrInvoicingGroupTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final AD_Message_StepDefData messageTable)
	{
		this.modCntrInvoicingGroupProductTable = modCntrInvoicingGroupProductTable;
		this.modCntrInvoicingGroupTable = modCntrInvoicingGroupTable;
		this.productTable = productTable;
		this.messageTable = messageTable;
	}

	@Given("metasfresh contains ModCntr_InvoicingGroup_Product:")
	public void metasfresh_contains_ModCntr_InvoicingGroup_Product(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			createModCntrInvoicingGroupProduct(row);
		}
	}

	@Given("the ModCntr_InvoicingGroup_Product is added expecting error:")
	public void metasfresh_adds_ModCntr_InvoicingGroup_Product_expectingError(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			boolean errorThrown = false;

			try
			{
				createModCntrInvoicingGroupProduct(row);
				assertThat(1).as("An Exception should have been thrown !").isEqualTo(2);
			}
			catch (final AdempiereException e)
			{
				errorThrown = true;

				final String errorMessageIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_AD_Message_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(errorMessageIdentifier))
				{
					final I_AD_Message errorMessage = messageTable.get(errorMessageIdentifier);
					assertThat(e.getMessage()).contains(msgBL.getMsg(Env.getCtx(), AdMessageKey.of(errorMessage.getValue())));
				}
			}

			assertThat(errorThrown).isTrue();
		}
	}

	private void createModCntrInvoicingGroupProduct(@NonNull final Map<String, String> row)
	{
		final I_ModCntr_InvoicingGroup_Product modCntrInvoicingGroupProductRecord = CoalesceUtil.coalesceSuppliersNotNull(
				() -> createLookupQuery(row)
						.create()
						.firstOnlyOrNull(I_ModCntr_InvoicingGroup_Product.class),
				() -> InterfaceWrapperHelper.newInstance(I_ModCntr_InvoicingGroup_Product.class));

		final String modCntrInvoicingGroupIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup_Product.COLUMNNAME_ModCntr_InvoicingGroup_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_ModCntr_InvoicingGroup modCntrInvoicingGroup = modCntrInvoicingGroupTable.get(modCntrInvoicingGroupIdentifier);
		modCntrInvoicingGroupProductRecord.setModCntr_InvoicingGroup_ID(modCntrInvoicingGroup.getModCntr_InvoicingGroup_ID());

		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup_Product.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);
		modCntrInvoicingGroupProductRecord.setM_Product_ID(productRecord.getM_Product_ID());

		InterfaceWrapperHelper.saveRecord(modCntrInvoicingGroupProductRecord);

		final String modCntrInvoicingGroupProductIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup_Product.COLUMNNAME_ModCntr_InvoicingGroup_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		modCntrInvoicingGroupProductTable.putOrReplace(modCntrInvoicingGroupProductIdentifier, modCntrInvoicingGroupProductRecord);
	}

	@NonNull
	private IQueryBuilder<I_ModCntr_InvoicingGroup_Product> createLookupQuery(@NonNull final Map<String, String> row)
	{
		final String modCntrInvoicingGroupIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup_Product.COLUMNNAME_ModCntr_InvoicingGroup_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_ModCntr_InvoicingGroup modCntrInvoicingGroup = modCntrInvoicingGroupTable.get(modCntrInvoicingGroupIdentifier);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup_Product.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		return queryBL.createQueryBuilder(I_ModCntr_InvoicingGroup_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ModCntr_InvoicingGroup_Product.COLUMNNAME_ModCntr_InvoicingGroup_ID, modCntrInvoicingGroup.getModCntr_InvoicingGroup_ID())
				.addEqualsFilter(I_ModCntr_InvoicingGroup_Product.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID());
	}
}
