package de.metas.frontend_testing.masterdata.shipper;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.Env;

import java.sql.Timestamp;

@Builder
public class CreateShipperCommand
{
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCreateShipperRequest request;
	@NonNull private final Identifier identifier;

	public JsonCreateShipperResponse execute()
	{
		final String uniqueSuffix = "_" + java.time.Instant.now().toString().replace(":", "").replace("-", "");
		final String name = (request.getName() != null ? request.getName() : "Shipper_" + identifier.getAsString()) + uniqueSuffix;

		// Create M_Shipper
		final I_M_Shipper shipper = InterfaceWrapperHelper.newInstance(I_M_Shipper.class);
		shipper.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		shipper.setName(name);
		shipper.setIsActive(true);
		shipper.setPickupTimeFrom(Timestamp.valueOf("2025-01-01 08:00:00"));
		shipper.setPickupTimeTo(Timestamp.valueOf("2025-01-01 18:00:00"));
		InterfaceWrapperHelper.save(shipper);

		final ShipperId shipperId = ShipperId.ofRepoId(shipper.getM_Shipper_ID());
		context.putIdentifier(identifier, shipperId);

		// Create DHL config if requested
		final JsonCreateShipperRequest.DhlConfig dhlConfig = request.getDhlConfig();
		if (dhlConfig != null)
		{
			createDhlShipperConfig(shipperId, dhlConfig);
		}

		return JsonCreateShipperResponse.builder()
				.shipperId(shipperId.getRepoId())
				.name(name)
				.build();
	}

	private void createDhlShipperConfig(
			@NonNull final ShipperId shipperId,
			@NonNull final JsonCreateShipperRequest.DhlConfig dhlConfig)
	{
		// Use GenericPO since de.metas.frontend-testing doesn't depend on de.metas.shipper.gateway.dhl
		final org.compiere.model.MTable table = org.compiere.model.MTable.get(Env.getCtx(), "DHL_Shipper_Config");
		final org.compiere.model.PO configRecord = table.getPO(0, null);
		configRecord.set_ValueOfColumn("AD_Org_ID", MasterdataContext.ORG_ID.getRepoId());
		configRecord.set_ValueOfColumn("M_Shipper_ID", shipperId.getRepoId());
		configRecord.set_ValueOfColumn("dhl_api_url", dhlConfig.getApiUrl());
		configRecord.set_ValueOfColumn("applicationID", dhlConfig.getApplicationID());
		configRecord.set_ValueOfColumn("ApplicationToken", dhlConfig.getApplicationToken());
		configRecord.set_ValueOfColumn("AccountNumber", dhlConfig.getAccountNumber());
		configRecord.set_ValueOfColumn("UserName", dhlConfig.getUsername());
		configRecord.set_ValueOfColumn("Signature", dhlConfig.getSignature());
		configRecord.saveEx();
	}
}
