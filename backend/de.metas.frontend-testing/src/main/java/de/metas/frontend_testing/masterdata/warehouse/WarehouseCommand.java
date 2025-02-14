package de.metas.frontend_testing.masterdata.warehouse;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Builder
public class WarehouseCommand
{
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonWarehouseRequest request;
	@NonNull private final Identifier identifier;

	public JsonWarehouseResponse execute()
	{
		final String value = identifier.toUniqueString();

		final I_M_Warehouse warehouseRecord = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		warehouseRecord.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		warehouseRecord.setValue(value);
		warehouseRecord.setName(value);
		warehouseRecord.setSeparator("*");
		warehouseRecord.setIsInTransit(request.isInTransit());
		warehouseRecord.setC_BPartner_ID(MasterdataContext.METASFRESH_ORG_BPARTNER_LOCATION_ID.getBpartnerId().getRepoId());
		warehouseRecord.setC_BPartner_Location_ID(MasterdataContext.METASFRESH_ORG_BPARTNER_LOCATION_ID.getRepoId());

		saveRecord(warehouseRecord);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID());
		context.putIdentifier(identifier, warehouseId);

		warehouseBL.getOrCreateDefaultLocator(warehouseId);

		return JsonWarehouseResponse.builder()
				.warehouseCode(warehouseRecord.getValue())
				.warehouseName(warehouseRecord.getName())
				.build();
	}
}
