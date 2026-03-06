package de.metas.cucumber.stepdefs.mobileui;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.hu.HUQRCode_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.distribution.mobileui.rest_api.DistributionRestController;
import de.metas.distribution.mobileui.rest_api.json.JsonGetHUInfoByScannedCodeRequest;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.picking.rest_api.PickingRestController;
import de.metas.rest_api.v2.warehouse.WarehouseRestController;
import de.metas.rest_api.v2.warehouse.json.JsonResolveLocatorRequest;
import de.metas.rest_api.v2.warehouse.json.JsonResolveLocatorResponse;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import java.util.List;

import static de.metas.util.Services.get;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ByScannedCode_StepDef
{
	private final PickingRestController pickingRestController = SpringContextHolder.instance.getBean(PickingRestController.class);
	private final DistributionRestController distributionRestController = SpringContextHolder.instance.getBean(DistributionRestController.class);
	private final WarehouseRestController warehouseRestController = SpringContextHolder.instance.getBean(WarehouseRestController.class);
	private final IWarehouseDAO warehouseDAO = get(IWarehouseDAO.class);

	private final HUQRCode_StepDefData huQRCodeStorage;
	private final M_Warehouse_StepDefData warehouseTable;

	@And("call POST picking\\/hu\\/byScannedCode with HU QR code containing hash:")
	public void picking_byScannedCode(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final HUQRCode huQRCode = row.getAsIdentifier("HUQRCode").lookupIn(huQRCodeStorage);
		final String qrCodeString = huQRCode.toGlobalQRCodeString();

		assertThat(qrCodeString).as("HU QR code must contain # separator").contains("#");

		final var request = de.metas.picking.rest_api.json.JsonGetHUInfoByScannedCodeRequest.builder()
				.scannedCode(qrCodeString)
				.build();

		final var response = pickingRestController.getHUInfoByQRCode(request);

		assertThat(response).isNotNull();
		assertThat(response.getHuQRCode()).isNotNull();
		assertThat(response.getHuQRCode().getCode()).contains("#");
	}

	@And("call POST distribution\\/hu\\/byScannedCode with HU QR code containing hash:")
	public void distribution_byScannedCode(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final HUQRCode huQRCode = row.getAsIdentifier("HUQRCode").lookupIn(huQRCodeStorage);
		final String qrCodeString = huQRCode.toGlobalQRCodeString();

		assertThat(qrCodeString).as("HU QR code must contain # separator").contains("#");

		final var request = JsonGetHUInfoByScannedCodeRequest.builder()
				.scannedCode(qrCodeString)
				.build();

		final var response = distributionRestController.getHUInfoByQRCode(request);

		assertThat(response).isNotNull();
		assertThat(response.getQrCode()).isNotNull();
	}

	@And("call POST material\\/warehouses\\/resolveLocator with locator QR code containing hash:")
	public void warehouse_resolveLocator(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final I_M_Warehouse warehouse = warehouseTable.get(row.getAsIdentifier("M_Warehouse_ID"));
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

		final List<I_M_Locator> locators = warehouseDAO.getLocators(warehouseId);
		assertThat(locators).as("Warehouse must have at least one locator").isNotEmpty();
		final I_M_Locator locator = locators.get(0);

		final LocatorQRCode locatorQRCode = LocatorQRCode.ofLocator(locator);
		final String qrCodeString = locatorQRCode.toGlobalQRCodeJsonString();

		assertThat(qrCodeString).as("Locator QR code must contain # separator").contains("#");

		final var request = JsonResolveLocatorRequest.builder()
				.scannedBarcode(qrCodeString)
				.build();

		final JsonResolveLocatorResponse response = warehouseRestController.resolveLocatorScannedCode(request);

		assertThat(response).isNotNull();
		assertThat(response.getError()).isNull();
		assertThat(response.getLocator()).isNotNull();
	}
}
