package de.metas.cucumber.stepdefs.mobileui;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.hu.HUQRCode_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.distribution.mobileui.rest_api.DistributionRestController;
import de.metas.distribution.mobileui.rest_api.json.JsonHUInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.picking.rest_api.PickingRestController;
import de.metas.picking.rest_api.json.JsonGetHUInfoByScannedCodeRequest;
import de.metas.rest_api.v2.warehouse.WarehouseRestService;
import de.metas.scannable_code.ScannedCode;
import de.metas.rest_api.v2.warehouse.json.JsonLocator;
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

/**
 * Step definitions for testing the byScannedCode / resolveLocator POST endpoints
 * with QR codes that contain {@code #} characters (format: {@code TYPE#VERSION#JSON_PAYLOAD}).
 * <p>
 * Calls the REST controllers (or service) directly via Spring bean injection — no HTTP layer involved.
 * <p>
 * Prerequisites: HU QR codes must be generated beforehand via "generate QR Codes for HUs" step;
 * warehouses must be loaded via "load M_Warehouse" step.
 */
@RequiredArgsConstructor
public class ByScannedCode_StepDef
{
	private final PickingRestController pickingRestController = SpringContextHolder.instance.getBean(PickingRestController.class);
	private final DistributionRestController distributionRestController = SpringContextHolder.instance.getBean(DistributionRestController.class);
	private final WarehouseRestService warehouseRestService = SpringContextHolder.instance.getBean(WarehouseRestService.class);
	private final IWarehouseDAO warehouseDAO = get(IWarehouseDAO.class);

	private final HUQRCode_StepDefData huQRCodeStorage;
	private final M_Warehouse_StepDefData warehouseTable;

	/**
	 * Calls {@link PickingRestController#getHUInfoByQRCode} with an HU QR code (containing {@code #}) and
	 * verifies the response contains the full QR code including {@code #} separators.
	 * <p>
	 * Required columns:
	 * <ul>
	 *     <li>{@code HUQRCode} / {@code HUQRCode.Identifier} — identifier of a previously generated HU QR code</li>
	 * </ul>
	 * Prerequisite: "generate QR Codes for HUs" step must have been called before.
	 */
	@And("call POST picking\\/hu\\/byScannedCode with HU QR code containing hash:")
	public void picking_byScannedCode(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final HUQRCode huQRCode = row.getAsIdentifier("HUQRCode").lookupIn(huQRCodeStorage);
		final String qrCodeString = huQRCode.toGlobalQRCodeString();

		assertThat(qrCodeString).as("HU QR code must contain # separator").contains("#");

		final JsonGetHUInfoByScannedCodeRequest request = JsonGetHUInfoByScannedCodeRequest.builder()
				.scannedCode(qrCodeString)
				.build();

		final de.metas.picking.rest_api.json.JsonHUInfo response = pickingRestController.getHUInfoByQRCode(request);

		assertThat(response).isNotNull();
		assertThat(response.getHuQRCode()).isNotNull();
		assertThat(response.getHuQRCode().getCode()).contains("#");
	}

	/**
	 * Calls {@link DistributionRestController#getHUInfoByQRCode} with an HU QR code (containing {@code #}) and
	 * verifies the response contains a non-null QR code.
	 * <p>
	 * Required columns:
	 * <ul>
	 *     <li>{@code HUQRCode} / {@code HUQRCode.Identifier} — identifier of a previously generated HU QR code</li>
	 * </ul>
	 * Prerequisite: "generate QR Codes for HUs" step must have been called before.
	 */
	@And("call POST distribution\\/hu\\/byScannedCode with HU QR code containing hash:")
	public void distribution_byScannedCode(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRow.singleRow(dataTable);
		final HUQRCode huQRCode = row.getAsIdentifier("HUQRCode").lookupIn(huQRCodeStorage);
		final String qrCodeString = huQRCode.toGlobalQRCodeString();

		assertThat(qrCodeString).as("HU QR code must contain # separator").contains("#");

		final de.metas.distribution.mobileui.rest_api.json.JsonGetHUInfoByScannedCodeRequest request =
				de.metas.distribution.mobileui.rest_api.json.JsonGetHUInfoByScannedCodeRequest.builder()
						.scannedCode(qrCodeString)
						.build();

		final JsonHUInfo response = distributionRestController.getHUInfoByQRCode(request);

		assertThat(response).isNotNull();
		assertThat(response.getQrCode()).isNotNull();
		assertThat(response.getQrCode().getCode()).as("Distribution response QR code must contain # separator").contains("#");
	}

	/**
	 * Builds a locator QR code (containing {@code #}) from the warehouse's first locator, then calls
	 * {@link WarehouseRestService#resolveLocatorScannedCode} and verifies the response contains a resolved locator.
	 * <p>
	 * Required columns:
	 * <ul>
	 *     <li>{@code M_Warehouse_ID} / {@code M_Warehouse_ID.Identifier} — identifier of a previously loaded warehouse</li>
	 * </ul>
	 * Prerequisite: "load M_Warehouse" step must have been called before. The warehouse must have at least one locator.
	 */
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

		final ScannedCode scannedCode = ScannedCode.ofString(qrCodeString);
		final JsonLocator jsonLocator = warehouseRestService.resolveLocatorScannedCode(scannedCode);

		assertThat(jsonLocator).isNotNull();
		assertThat(jsonLocator.getQrCode()).as("Locator response QR code must contain # separator").contains("#");
	}
}
