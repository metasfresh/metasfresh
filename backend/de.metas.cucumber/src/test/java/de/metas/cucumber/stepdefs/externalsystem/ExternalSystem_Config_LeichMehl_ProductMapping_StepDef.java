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

package de.metas.cucumber.stepdefs.externalsystem;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.externalsystem.leichmehl.LeichMehlPluFileConfigGroupId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl_ProductMapping;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ExternalSystem_Config_LeichMehl_ProductMapping_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Product_StepDefData productTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final LeichMehl_PluFile_ConfigGroup_StepDefData leichMehlPluFileConfigGroupTable;
	private final ExternalSystem_Config_LeichMehl_ProductMapping_StepDefData externalSystemConfigLeichMehlProductMappingTable;

	public ExternalSystem_Config_LeichMehl_ProductMapping_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final LeichMehl_PluFile_ConfigGroup_StepDefData leichMehlPluFileConfigGroupTable,
			@NonNull final ExternalSystem_Config_LeichMehl_ProductMapping_StepDefData externalSystemConfigLeichMehlProductMappingTable
	)
	{
		this.productTable = productTable;
		this.bPartnerTable = bPartnerTable;
		this.leichMehlPluFileConfigGroupTable = leichMehlPluFileConfigGroupTable;
		this.externalSystemConfigLeichMehlProductMappingTable = externalSystemConfigLeichMehlProductMappingTable;
	}
	@And("metasfresh contains ExternalSystem_Config_LeichMehl_ProductMapping:")
	public void add_ExternalSystem_Config_LeichMehl_ProductMapping(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String externalSystemConfigLeichMehlProductMappingIdentifier = DataTableUtil.extractStringForColumnName(row, I_ExternalSystem_Config_LeichMehl_ProductMapping.COLUMNNAME_ExternalSystem_Config_LeichMehl_ProductMapping_ID
					+ "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

			final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_ExternalSystem_Config_LeichMehl_ProductMapping.COLUMNNAME_M_Product_ID
					+ "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final ProductId productId = ProductId.ofRepoId(productTable.get(productIdentifier).getM_Product_ID());

			final  String bPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_ExternalSystem_Config_LeichMehl_ProductMapping.COLUMNNAME_C_BPartner_ID
					+ "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final BPartnerId bPartnerId = bPartnerIdentifier == null ? null : BPartnerId.ofRepoId(bPartnerTable.get(bPartnerIdentifier).getC_BPartner_ID());

			final I_ExternalSystem_Config_LeichMehl_ProductMapping productMapping = CoalesceUtil
					.coalesceSuppliers(() -> queryBL.createQueryBuilder(I_ExternalSystem_Config_LeichMehl_ProductMapping.class)
											   .addEqualsFilter(I_ExternalSystem_Config_LeichMehl_ProductMapping.COLUMNNAME_M_Product_ID, productId)
											   .addEqualsFilter(I_ExternalSystem_Config_LeichMehl_ProductMapping.COLUMNNAME_C_BPartner_ID, bPartnerId)
											   .create()
											   .firstOnly(I_ExternalSystem_Config_LeichMehl_ProductMapping.class),
									   () -> InterfaceWrapperHelper.newInstance(I_ExternalSystem_Config_LeichMehl_ProductMapping.class));

			assertThat(productMapping).isNotNull();

			productMapping.setM_Product_ID(productId.getRepoId());
			if(bPartnerId != null)
			{
				productMapping.setC_BPartner_ID(bPartnerId.getRepoId());
			}

			final String leichMehlPluFileConfigGroupIdentifier = DataTableUtil.extractStringForColumnName(row, I_ExternalSystem_Config_LeichMehl_ProductMapping.COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID
					+ "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final LeichMehlPluFileConfigGroupId leichMehlPluFileConfigGroupId = LeichMehlPluFileConfigGroupId.ofRepoId(leichMehlPluFileConfigGroupTable.get(leichMehlPluFileConfigGroupIdentifier).getLeichMehl_PluFile_ConfigGroup_ID());
			productMapping.setLeichMehl_PluFile_ConfigGroup_ID(leichMehlPluFileConfigGroupId.getRepoId());

			final String pluFile = DataTableUtil.extractStringForColumnName(row, I_ExternalSystem_Config_LeichMehl_ProductMapping.COLUMNNAME_PLU_File);
			productMapping.setPLU_File(pluFile);

			InterfaceWrapperHelper.saveRecord(productMapping);
			externalSystemConfigLeichMehlProductMappingTable.putOrReplace(externalSystemConfigLeichMehlProductMappingIdentifier, productMapping);
		}
	}
}
