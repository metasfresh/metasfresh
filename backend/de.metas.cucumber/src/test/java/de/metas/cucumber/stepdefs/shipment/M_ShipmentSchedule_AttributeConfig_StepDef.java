package de.metas.cucumber.stepdefs.shipment;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_AttributeConfig;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("update M_ShipmentSchedule_AttributeConfig:")
	public void update_config(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::updateConfig);
	}

	private void updateConfig(@NonNull final DataTableRow row)
	{
		final String attributeCode = row.getAsString("M_Attribute.Value");
		final boolean isHUOverridesASI = row.getAsBoolean("IsHUAttributeOverridesASI");

		final int updated = queryBL.createQueryBuilder(I_M_ShipmentSchedule_AttributeConfig.class)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(
						I_M_ShipmentSchedule_AttributeConfig.COLUMNNAME_M_Attribute_ID,
						org.compiere.model.I_M_Attribute.COLUMNNAME_M_Attribute_ID,
						queryBL.createQueryBuilder(org.compiere.model.I_M_Attribute.class)
								.addEqualsFilter(org.compiere.model.I_M_Attribute.COLUMNNAME_Value, attributeCode)
								.create())
				.create()
				.list()
				.stream()
				.mapToInt(config -> {
					config.setIsHUAttributeOverridesASI(isHUOverridesASI);
					InterfaceWrapperHelper.saveRecord(config);
					return 1;
				})
				.sum();

		if (updated == 0)
		{
			throw new org.adempiere.exceptions.AdempiereException("No M_ShipmentSchedule_AttributeConfig found for M_Attribute.Value=" + attributeCode);
		}
	}
}
