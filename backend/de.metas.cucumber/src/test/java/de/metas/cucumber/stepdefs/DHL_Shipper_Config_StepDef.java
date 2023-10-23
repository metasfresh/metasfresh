package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDefData;
import de.metas.shipper.gateway.dhl.model.I_DHL_Shipper_Config;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AllArgsConstructor
public class DHL_Shipper_Config_StepDef
{
	private final DHL_Shipper_Config_StepDefData dhlConfigTable;
	private final M_Shipper_StepDefData shipperTable;

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@Given("metasfresh contains DHL Configuration:")
	public void metasfresh_contains_dhl_configuration(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{

			final String configIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_DHL_Shipper_Config.COLUMNNAME_DHL_Shipper_Config_ID);
			final I_DHL_Shipper_Config config = dhlConfigTable.getOptional(configIdentifier)
					.orElseGet(() -> this.createConfig(tableRow));

			final String accountNumber = DataTableUtil.extractStringForColumnName(tableRow, I_DHL_Shipper_Config.COLUMNNAME_AccountNumber);
			config.setAccountNumber(accountNumber);

			saveRecord(config);

			dhlConfigTable.putOrReplace(configIdentifier, config);
		}
	}

	private I_DHL_Shipper_Config createConfig(final Map<String, String> tableRow)
	{
		final String shipperIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DHL_Shipper_Config.COLUMNNAME_M_Shipper_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Shipper shipper = shipperTable.get(shipperIdentifier);
		assertThat(shipper).isNotNull();

		final I_DHL_Shipper_Config shipperConfig = InterfaceWrapperHelper.newInstance(I_DHL_Shipper_Config.class);
		shipperConfig.setM_Shipper_ID(shipper.getM_Shipper_ID());
		shipperConfig.setAD_Org_ID(shipper.getAD_Org_ID());
		shipperConfig.setIsActive(true);
		shipperConfig.setDhl_LenghtUOM_ID(uomDAO.getUomIdByX12DE355(X12DE355.CENTIMETRE).getRepoId());
		//Dummy data, we don't want an actual call
		shipperConfig.setdhl_api_url("http://definitely.bad.url/");
		shipperConfig.setapplicationID("1");
		shipperConfig.setApplicationToken("12");
		shipperConfig.setUserName("123");
		shipperConfig.setSignature("1234");

		return shipperConfig;
	}

}