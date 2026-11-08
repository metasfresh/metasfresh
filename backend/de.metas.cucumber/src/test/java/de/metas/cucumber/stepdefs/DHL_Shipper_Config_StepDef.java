package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDefData;
import de.metas.shipper.gateway.dhl.model.I_DHL_Shipper_Config;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_M_Shipper;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Step definitions for the DHL configuration ({@code DHL_Shipper_Config}) of a shipper.
 *
 * @see de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDef creates the shipper the configuration belongs to
 * @see de.metas.cucumber.stepdefs.shipper.dhl.Dhl_ShipmentOrder_Log_StepDef asserts what was sent to DHL with this configuration
 */
@RequiredArgsConstructor
public class DHL_Shipper_Config_StepDef
{
	@NonNull private final DHL_Shipper_Config_StepDefData dhlConfigTable;
	@NonNull private final M_Shipper_StepDefData shipperTable;

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	/**
	 * Creates or updates {@code DHL_Shipper_Config} records with dummy API credentials (no real DHL call is possible).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>DHL_Shipper_Config_ID.Identifier</b> — alias to store the config under; an existing alias is updated<br>
	 *   <b>M_Shipper_ID.Identifier</b> — the shipper the config belongs to (used only when the config is created)<br>
	 *   <b>AccountNumber</b> — national billing number (V01PAK); {@code null} leaves it empty<br>
	 *   <b>AccountNumber_International</b> — (optional) international billing number (V53WPAK); {@code null} or omitted leaves it empty<br>
	 * @cucumber.depends StepDefData: DHL_Shipper_Config_StepDefData, M_Shipper_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains DHL Configuration:
	 *   | DHL_Shipper_Config_ID.Identifier | M_Shipper_ID.Identifier | AccountNumber  | OPT.AccountNumber_International |
	 *   | dhl1                             | shipper_DHL             | 33333333330102 | 33333333335301                  |
	 * </pre>
	 * @see de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDef#createOrUpdateShippers(DataTable)
	 */
	@Given("metasfresh contains DHL Configuration:")
	public void createOrUpdateConfigs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_DHL_Shipper_Config.COLUMNNAME_DHL_Shipper_Config_ID)
				.forEach(this::createOrUpdateConfig);
	}

	private void createOrUpdateConfig(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier configIdentifier = row.getAsIdentifier();
		final I_DHL_Shipper_Config config = dhlConfigTable.getOptional(configIdentifier)
				.orElseGet(() -> newConfig(row));

		config.setAccountNumber(DataTableUtil.nullToken2Null(row.getAsString(I_DHL_Shipper_Config.COLUMNNAME_AccountNumber)));
		config.setAccountNumber_International(row.getAsOptionalString(I_DHL_Shipper_Config.COLUMNNAME_AccountNumber_International)
				.map(DataTableUtil::nullToken2Null)
				.orElse(null));
		saveRecord(config);

		dhlConfigTable.putOrReplace(configIdentifier, config);
	}

	@NonNull
	private I_DHL_Shipper_Config newConfig(@NonNull final DataTableRow row)
	{
		final I_M_Shipper shipper = row.getAsIdentifier(I_DHL_Shipper_Config.COLUMNNAME_M_Shipper_ID).lookupNotNullIn(shipperTable);

		final I_DHL_Shipper_Config config = newInstance(I_DHL_Shipper_Config.class);
		config.setM_Shipper_ID(shipper.getM_Shipper_ID());
		config.setAD_Org_ID(shipper.getAD_Org_ID());
		config.setIsActive(true);
		config.setDhl_LenghtUOM_ID(uomDAO.getUomIdByX12DE355(X12DE355.CENTIMETRE).getRepoId());
		// dummy endpoint and credentials: no real DHL call can happen
		config.setdhl_api_url("http://definitely.bad.url/");
		config.setapplicationID("1");
		config.setApplicationToken("12");
		config.setUserName("123");
		config.setSignature("1234");
		return config;
	}
}
