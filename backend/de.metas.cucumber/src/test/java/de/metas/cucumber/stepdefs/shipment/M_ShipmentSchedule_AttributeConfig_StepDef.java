package de.metas.cucumber.stepdefs.shipment;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.frontend_testing.masterdata.shipment_schedule.EnsureShipmentScheduleAttributeConfigCommand;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_AttributeConfig;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Step definitions for configuring {@link I_M_ShipmentSchedule_AttributeConfig} records.
 * <p>
 * These control which HU attributes are included on shipment lines and how they interact
 * with the schedule ASI (order line attributes).
 *
 * <p><b>Required columns:</b>
 * <ul>
 *   <li><b>M_Attribute.Value</b> — attribute code (e.g., "1000001" for Herkunft)</li>
 *   <li><b>IsHUAttributeOverridesASI</b> — Y/N: if N, the order line's ASI value takes precedence over the HU value</li>
 * </ul>
 *
 * @cucumber.example <pre>{@code
 * And update M_ShipmentSchedule_AttributeConfig:
 *   | M_Attribute.Value | IsHUAttributeOverridesASI |
 *   | 1000001           | N                         |
 * }</pre>
 */
public class M_ShipmentSchedule_AttributeConfig_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	/** Rows the "inactive" step deactivated; re-activated after the scenario. */
	private final List<I_M_ShipmentSchedule_AttributeConfig> deactivatedConfigs = new ArrayList<>();
	/** Rows the "active" step inserted; deactivated after the scenario. */
	private final List<I_M_ShipmentSchedule_AttributeConfig> insertedConfigs = new ArrayList<>();

	@And("update M_ShipmentSchedule_AttributeConfig:")
	public void update_config(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::updateConfig);
	}

	private void updateConfig(@NonNull final DataTableRow row)
	{
		final String attributeCode = row.getAsString("M_Attribute.Value");
		final boolean isHUOverridesASI = row.getAsBoolean("IsHUAttributeOverridesASI");

		final AttributeId attributeId = attributeDAO.getAttributeIdByCode(AttributeCode.ofString(attributeCode));

		final List<I_M_ShipmentSchedule_AttributeConfig> configs = retrieveActiveConfigs(attributeId);

		if (configs.isEmpty())
		{
			throw new AdempiereException("No M_ShipmentSchedule_AttributeConfig found for M_Attribute.Value=" + attributeCode);
		}

		for (final I_M_ShipmentSchedule_AttributeConfig config : configs)
		{
			config.setIsHUAttributeOverridesASI(isHUOverridesASI);
			InterfaceWrapperHelper.saveRecord(config);
		}
	}

	/**
	 * Makes sure an active config row exists for the attribute (and the order line handler), so the attribute splits the shipment line.
	 * A row inserted here is deactivated again after the scenario.
	 *
	 * @cucumber.example <pre>{@code
	 * And M_ShipmentSchedule_AttributeConfig is active for M_Attribute.Value Lot-Nummer
	 * }</pre>
	 */
	@And("^M_ShipmentSchedule_AttributeConfig is active for M_Attribute.Value (.*)$")
	public void config_is_active(@NonNull final String attributeCode)
	{
		final AttributeId attributeId = attributeDAO.getAttributeIdByCode(AttributeCode.ofString(attributeCode));
		final List<I_M_ShipmentSchedule_AttributeConfig> configsBefore = retrieveActiveConfigs(attributeId);

		EnsureShipmentScheduleAttributeConfigCommand.builder()
				.attributeCodes(ImmutableList.of(attributeCode))
				.build()
				.execute();

		retrieveActiveConfigs(attributeId).stream()
				.filter(config -> configsBefore.stream().noneMatch(before -> before.getM_ShipmentSchedule_AttributeConfig_ID() == config.getM_ShipmentSchedule_AttributeConfig_ID()))
				.forEach(insertedConfigs::add);
	}

	private List<I_M_ShipmentSchedule_AttributeConfig> retrieveActiveConfigs(@NonNull final AttributeId attributeId)
	{
		return queryBL.createQueryBuilder(I_M_ShipmentSchedule_AttributeConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_AttributeConfig.COLUMNNAME_M_Attribute_ID, attributeId)
				.create()
				.list();
	}

	/**
	 * Deactivates the active config rows of the attribute, so the attribute no longer splits the shipment line.
	 * The rows are re-activated after the scenario.
	 *
	 * @cucumber.example <pre>{@code
	 * And M_ShipmentSchedule_AttributeConfig is inactive for M_Attribute.Value Lot-Nummer
	 * }</pre>
	 */
	@And("^M_ShipmentSchedule_AttributeConfig is inactive for M_Attribute.Value (.*)$")
	public void config_is_inactive(@NonNull final String attributeCode)
	{
		final AttributeId attributeId = attributeDAO.getAttributeIdByCode(AttributeCode.ofString(attributeCode));
		for (final I_M_ShipmentSchedule_AttributeConfig config : retrieveActiveConfigs(attributeId))
		{
			config.setIsActive(false);
			InterfaceWrapperHelper.saveRecord(config);
			deactivatedConfigs.add(config);
		}
	}

	/**
	 * Restores the config: first re-activates what the "inactive" step deactivated, then deactivates what the "active" step inserted
	 * (the reverse order would leave a row active when one scenario uses both steps for one attribute).
	 */
	@After
	public void restoreConfigs()
	{
		for (final I_M_ShipmentSchedule_AttributeConfig config : deactivatedConfigs)
		{
			config.setIsActive(true);
			InterfaceWrapperHelper.saveRecord(config);
		}
		deactivatedConfigs.clear();

		for (final I_M_ShipmentSchedule_AttributeConfig config : insertedConfigs)
		{
			config.setIsActive(false);
			InterfaceWrapperHelper.saveRecord(config);
		}
		insertedConfigs.clear();
	}
}
