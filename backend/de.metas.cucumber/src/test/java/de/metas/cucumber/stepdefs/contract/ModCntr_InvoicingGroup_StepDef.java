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
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.message.AD_Message_StepDefData;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_ModCntr_InvoicingGroup;
import org.compiere.util.Env;

import java.sql.Timestamp;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_AD_Message.COLUMNNAME_AD_Message_ID;

public class ModCntr_InvoicingGroup_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final ModCntr_InvoicingGroup_StepDefData modCntrInvoicingGroupTable;
	private final M_Product_StepDefData productTable;
	private final AD_Message_StepDefData messageTable;

	public ModCntr_InvoicingGroup_StepDef(
			@NonNull final ModCntr_InvoicingGroup_StepDefData modCntrInvoicingGroupTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final AD_Message_StepDefData messageTable)
	{
		this.modCntrInvoicingGroupTable = modCntrInvoicingGroupTable;
		this.productTable = productTable;
		this.messageTable = messageTable;
	}

	@Given("metasfresh contains ModCntr_InvoicingGroup:")
	public void metasfresh_contains_ModCntr_InvoicingGroup(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			createModCntrInvoicingGroup(row);
		}
	}

	@And("the ModCntr_InvoicingGroup is updated expecting error:")
	public void update_ModCntr_InvoicingGroup_expectingError(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			boolean errorThrown = false;

			try
			{
				updateModCntrInvoicingGroup(row);
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

	private void createModCntrInvoicingGroup(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_Group_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String name = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_Name);
		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_ValidFrom);
		final Timestamp validTo = DataTableUtil.extractDateTimestampForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_ValidTo);

		final I_ModCntr_InvoicingGroup modCntrInvoicingGroupRecord = CoalesceUtil.coalesceSuppliers(
				() -> queryBL.createQueryBuilder(I_ModCntr_InvoicingGroup.class)
						.addEqualsFilter(I_ModCntr_InvoicingGroup.COLUMNNAME_Group_Product_ID, productRecord.getM_Product_ID())
						.addEqualsFilter(I_ModCntr_InvoicingGroup.COLUMNNAME_Name, name)
						.addEqualsFilter(I_ModCntr_InvoicingGroup.COLUMNNAME_ValidFrom, validFrom)
						.addEqualsFilter(I_ModCntr_InvoicingGroup.COLUMNNAME_ValidTo, validTo)
						.create()
						.firstOnlyOrNull(I_ModCntr_InvoicingGroup.class),
				() -> InterfaceWrapperHelper.newInstance(I_ModCntr_InvoicingGroup.class));

		modCntrInvoicingGroupRecord.setGroup_Product_ID(productRecord.getM_Product_ID());
		modCntrInvoicingGroupRecord.setName(name);
		modCntrInvoicingGroupRecord.setValidFrom(validFrom);
		modCntrInvoicingGroupRecord.setValidTo(validTo);

		InterfaceWrapperHelper.saveRecord(modCntrInvoicingGroupRecord);

		final String modCntrInvoicingGroupIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_ModCntr_InvoicingGroup_ID + "." + TABLECOLUMN_IDENTIFIER);
		modCntrInvoicingGroupTable.putOrReplace(modCntrInvoicingGroupIdentifier, modCntrInvoicingGroupRecord);
	}

	private void updateModCntrInvoicingGroup(@NonNull final Map<String, String> row)
	{
		final String invoicingGroupIdentifier = DataTableUtil.extractStringForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_ModCntr_InvoicingGroup_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_ModCntr_InvoicingGroup invoicingGroup = modCntrInvoicingGroupTable.get(invoicingGroupIdentifier);

		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(row, I_ModCntr_InvoicingGroup.COLUMNNAME_ValidFrom);
		invoicingGroup.setValidFrom(validFrom);

		InterfaceWrapperHelper.saveRecord(invoicingGroup);

		modCntrInvoicingGroupTable.putOrReplace(invoicingGroupIdentifier, invoicingGroup);
	}
}
