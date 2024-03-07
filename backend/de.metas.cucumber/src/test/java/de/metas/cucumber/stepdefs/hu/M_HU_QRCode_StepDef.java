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

package de.metas.cucumber.stepdefs.hu;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_QRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateForExistingHUsCommand;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateForExistingHUsResult;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.QRCodeConfigurationService;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class M_HU_QRCode_StepDef
{
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);
	private final QRCodeConfigurationService qrCodeConfigurationService;
	private final HUQRCodesRepository huQRCodesRepository;

	private final M_HU_StepDefData huTable;
	private final M_HU_QRCode_StepDefData qrCodesTable;
	private final M_HU_PI_Version_StepDefData huPiVersionTable;
	private final M_Product_StepDefData productTable;
	private final M_HU_PI_StepDefData huPiTable;
	private final HUQRCode_StepDefData huQRCodeStorage;

	public M_HU_QRCode_StepDef(
			@NonNull QRCodeConfigurationService qrCodeConfigurationService,
			@NonNull final M_HU_StepDefData huTable,
			@NonNull final M_HU_QRCode_StepDefData qrCodesTable,
			@NonNull final HUQRCodesRepository huQRCodesRepository,
			@NonNull final M_HU_PI_Version_StepDefData huPiVersionTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_HU_PI_StepDefData huPiTable,
			@NonNull final HUQRCode_StepDefData huQRCodeStorage)
	{
		this.qrCodeConfigurationService = qrCodeConfigurationService;
		this.huQRCodesRepository = huQRCodesRepository;
		this.huTable = huTable;
		this.qrCodesTable = qrCodesTable;
		this.huPiVersionTable = huPiVersionTable;
		this.productTable = productTable;
		this.huPiTable = huPiTable;
		this.huQRCodeStorage = huQRCodeStorage;
	}

	@And("metasfresh contains M_HU_QRCode")
	public void metasfresh_contains_qr_codes(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			createHUQRCode(row);
		}
	}

	@And("generate QR Codes for HUs")
	public void generate_qr_codes_for_HUs(@NonNull final DataTable dataTable)
	{
		for (final DataTableRow row : DataTableRow.toRows(dataTable))
		{
			generateQRCodes(row);
		}
	}

	private void createHUQRCode(@NonNull final Map<String, String> row)
	{
		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_HU hu = huTable.get(huIdentifier);

		final String piVersionIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_Version_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_HU_PI_Version piVersion = huPiVersionTable.get(piVersionIdentifier);

		final String piIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_PI.COLUMNNAME_M_HU_PI_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_HU_PI pi = huPiTable.get(piIdentifier);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Product product = productTable.get(productIdentifier);

		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		final HUQRCode huQRCode = HUQRCode.builder()
				.packingInfo(HUQRCodePackingInfo.builder()
									 .huUnitType(HUQRCodeUnitType.ofCode(piVersion.getHU_UnitType()))
									 .packingInstructionsId(HuPackingInstructionsId.ofRepoId(piVersion.getM_HU_PI_ID()))
									 .caption(pi.getName())
									 .build())
				.product(HUQRCodeProductInfo.builder()
								 .id(ProductId.ofRepoId(product.getM_Product_ID()))
								 .code(product.getValue())
								 .name(product.getName())
								 .build())
				.attributes(ImmutableList.of())
				.id(HUQRCodeUniqueId.ofUUID(UUID.randomUUID()))
				.build();

		final I_M_HU_QRCode qrCode = huQRCodesRepository.createNew(huQRCode, huId);

		final String qrCodeIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_QRCode.COLUMNNAME_M_HU_QRCode_ID + "." + TABLECOLUMN_IDENTIFIER);

		qrCodesTable.putOrReplace(qrCodeIdentifier, qrCode);
	}

	private void generateQRCodes(@NonNull final DataTableRow dataTableRow)
	{
		final HuId huId = HuId.ofRepoId(dataTableRow.getAsIdentifier("M_HU_ID").lookupIn(huTable).getM_HU_ID());
		final HUQRCodeGenerateForExistingHUsResult result = HUQRCodeGenerateForExistingHUsCommand.builder()
				.handlingUnitsBL(handlingUnitsBL)
				.productBL(productBL)
				.attributeDAO(attributeDAO)
				.huQRCodesRepository(huQRCodesRepository)
				.qrCodeConfigurationService(qrCodeConfigurationService)
				.attributeStorageFactoryService(attributeStorageFactoryService)
				.huId(huId)
				.build()
				.execute();

		result.getSingleQRCode(huId);

		dataTableRow.getAsIdentifier("HUQRCode").put(huQRCodeStorage, result.getSingleQRCode(huId));
	}
}
