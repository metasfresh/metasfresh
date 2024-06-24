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
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
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
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateRequest;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

@RequiredArgsConstructor
public class M_HU_QRCode_StepDef
{
	@NonNull private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
	@NonNull private final HUQRCodesRepository huQRCodesRepository = SpringContextHolder.instance.getBean(HUQRCodesRepository.class);
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	@NonNull private final M_HU_StepDefData huTable;
	@NonNull private final M_HU_QRCode_StepDefData qrCodesTable;
	@NonNull private final M_HU_PI_Version_StepDefData huPiVersionTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_HU_PI_StepDefData huPiTable;
	@NonNull private final HUQRCode_StepDefData huQRCodeStorage;
	@NonNull private final TestContext restTestContext;

	@And("metasfresh contains M_HU_QRCode")
	public void metasfresh_contains_qr_codes(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			createHUQRCode(row);
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

	@And("generate QR Codes for HUs")
	public void generate_qr_codes_for_HUs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::generateQRCodes);
	}

	private void generateQRCodes(@NonNull final DataTableRow row)
	{
		final HuId huId = HuId.ofRepoId(row.getAsIdentifier("M_HU_ID").lookupIn(huTable).getM_HU_ID());
		final HUQRCodeGenerateForExistingHUsResult result = HUQRCodeGenerateForExistingHUsCommand.builder()
				.huId(huId)
				.huQRCodesRepository(huQRCodesRepository)
				.build()
				.execute();

		final HUQRCode qrCode = result.getSingleQRCode(huId);
		row.getAsOptionalIdentifier("HUQRCode").ifPresent(identifier -> identifier.put(huQRCodeStorage, qrCode));
		restTestContext.setStringVariableFromRow(row, qrCode::toGlobalQRCodeString);
	}

	@And("generate new QR Codes")
	public void generateNewQRCodes(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::generateNewQRCode);
	}

	private void generateNewQRCode(@NonNull final DataTableRow row)
	{
		final HuPackingInstructionsId huPackingInstructionsId = huPiTable.getId(row.getAsIdentifier(I_M_HU_PI.COLUMNNAME_M_HU_PI_ID));
		final ProductId productId = productTable.getId(row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID));
		final String lotNo = row.getAsOptionalString("LotNo").map(StringUtils::trimBlankToNull).orElse(null);

		final List<HUQRCode> qrCodes = huQRCodesService.generate(
				HUQRCodeGenerateRequest.builder()
						.count(1)
						.huPackingInstructionsId(huPackingInstructionsId)
						.productId(productId)
						.lotNo(lotNo, attributeDAO::getAttributeIdByCode)
						.build());

		assertThat(qrCodes).hasSize(1);
		final HUQRCode qrCode = qrCodes.get(0);

		row.getAsOptionalIdentifier().ifPresent(identifier -> huQRCodeStorage.put(identifier, qrCode));
		restTestContext.setStringVariableFromRow(row, qrCode::toGlobalQRCodeString);
	}

}
