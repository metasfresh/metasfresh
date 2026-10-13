package de.metas.cucumber.stepdefs.shipment;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_AttributeConfig;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;

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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

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

		final List<I_M_ShipmentSchedule_AttributeConfig> configs = queryBL.createQueryBuilder(I_M_ShipmentSchedule_AttributeConfig.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule_AttributeConfig.COLUMNNAME_M_Attribute_ID, attributeId)
				.create()
				.list();

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
}
