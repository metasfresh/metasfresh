package de.metas.frontend_testing.masterdata.warehouse;

import com.google.common.collect.ImmutableMap;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.CreateOrUpdateLocatorRequest;
import org.adempiere.warehouse.api.CreateWarehousePickingGroupRequest;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.groups.picking.WarehousePickingGroupId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.model.I_M_Locator;

import java.util.HashMap;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class WarehouseCommand
{
	// services
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	// params
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonWarehouseRequest request;
	@NonNull private final Identifier identifier;
	@NonNull private final String warehouseCode;

	// state
	private I_M_Warehouse warehouseRecord;

	@Builder
	private WarehouseCommand(
			@NonNull final MasterdataContext context,
			@NonNull final JsonWarehouseRequest request,
			@NonNull final Identifier identifier)
	{
		this.context = context;
		this.request = request;
		this.identifier = identifier;

		this.warehouseCode = identifier.toUniqueString();

	}

	public JsonWarehouseResponse execute()
	{
		createWarehouse();
		assignPickingGroup();

		final JsonWarehouseResponse.JsonWarehouseResponseBuilder responseBuilder = JsonWarehouseResponse.builder()
				.warehouseId(warehouseRecord.getM_Warehouse_ID())
				.warehouseCode(warehouseRecord.getValue())
				.warehouseName(warehouseRecord.getName())
				.inTransit(warehouseRecord.isInTransit());

		final Map<String, JsonWarehouseResponse.Locator> locators = createLocators();
		responseBuilder.locators(locators);

		if (locators.isEmpty() || !Check.isBlank(request.getLocatorCode()))
		{
			final I_M_Locator defaultLocator = createDefaultLocator();
			responseBuilder
					.locatorId(defaultLocator.getM_Locator_ID())
					.locatorCode(defaultLocator.getValue())
					.locatorQRCode(LocatorQRCode.ofLocator(defaultLocator).toGlobalQRCodeJsonString());
		}

		return responseBuilder.build();
	}

	private WarehouseId getWarehouseId()
	{
		final I_M_Warehouse warehouseRecord = Check.assumeNotNull(this.warehouseRecord, "warehouseRecord not null");
		return WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID());
	}

	private void createWarehouse()
	{
		this.warehouseRecord = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		warehouseRecord.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		warehouseRecord.setValue(warehouseCode);
		warehouseRecord.setName(warehouseCode);
		warehouseRecord.setSeparator("*");
		warehouseRecord.setIsInTransit(request.isInTransit());
		warehouseRecord.setC_BPartner_ID(MasterdataContext.METASFRESH_ORG_BPARTNER_LOCATION_ID.getBpartnerId().getRepoId());
		warehouseRecord.setC_BPartner_Location_ID(MasterdataContext.METASFRESH_ORG_BPARTNER_LOCATION_ID.getRepoId());

		saveRecord(warehouseRecord);
		final WarehouseId warehouseId = getWarehouseId();
		context.putIdentifier(identifier, warehouseId);
	}

	/**
	 * If the request named a {@code pickingGroup}, assign this warehouse to the shared
	 * {@code M_Warehouse_PickingGroup} identified by that value (looked up in / created once into the
	 * masterdata context, keyed by the group identifier). Warehouses naming the same {@code pickingGroup}
	 * thus end up in the same picking group.
	 */
	private void assignPickingGroup()
	{
		final String pickingGroupName = StringUtils.trimBlankToNull(request.getPickingGroup());
		if (pickingGroupName == null)
		{
			return;
		}

		final Identifier pickingGroupIdentifier = Identifier.ofString(pickingGroupName);
		final WarehousePickingGroupId pickingGroupId = context.getOptionalId(pickingGroupIdentifier, WarehousePickingGroupId.class)
				.orElseGet(() -> {
					// Use a unique Name (string + datetime): the picking group lives in the shared DB and a raw
					// pickingGroupName would collide across tests reusing the same group label.
					final WarehousePickingGroupId newId = warehouseBL.createWarehousePickingGroup(
							CreateWarehousePickingGroupRequest.builder()
									.orgId(MasterdataContext.ORG_ID)
									.name(pickingGroupIdentifier.toUniqueString())
									.build());
					context.putIdentifier(pickingGroupIdentifier, newId);
					return newId;
				});

		warehouseRecord.setM_Warehouse_PickingGroup_ID(pickingGroupId.getRepoId());
		saveRecord(warehouseRecord);
	}

	private Map<String, JsonWarehouseResponse.Locator> createLocators()
	{
		if (request.getLocators() == null || request.getLocators().isEmpty())
		{
			return ImmutableMap.of();
		}

		final HashMap<String, JsonWarehouseResponse.Locator> response = new HashMap<>();
		request.getLocators().forEach((locatorIdentifierStr, locatorRequest) -> {
			final JsonWarehouseResponse.Locator locator = createLocator(locatorIdentifierStr, locatorRequest);
			response.put(locatorIdentifierStr, locator);
		});

		return response;
	}

	private I_M_Locator createDefaultLocator()
	{
		final I_M_Locator defaultLocator = warehouseBL.getOrCreateDefaultLocator(getWarehouseId());
		defaultLocator.setValue(StringUtils.trimBlankToOptional(request.getLocatorCode()).orElseGet(() -> warehouseCode + "_Locator"));
		saveRecord(defaultLocator);
		context.putIdentifier(identifier, LocatorId.ofRecord(defaultLocator));
		return defaultLocator;
	}

	private JsonWarehouseResponse.Locator createLocator(@NonNull final String identifierStr, @NonNull final JsonWarehouseRequest.Locator locatorRequest)
	{
		final Identifier identifier = Identifier.ofString(identifierStr);

		final LocatorId locatorId = warehouseBL.createOrUpdateLocator(
				CreateOrUpdateLocatorRequest.builder()
						.warehouseId(getWarehouseId())
						.locatorValue(identifier.getAsString())
						.x(StringUtils.trimBlankToOptional(locatorRequest.getX()).orElse(identifierStr))
						.y(StringUtils.trimBlankToOptional(locatorRequest.getY()).orElse(identifierStr))
						.z(StringUtils.trimBlankToOptional(locatorRequest.getZ()).orElse(identifierStr))
						.x1(StringUtils.trimBlankToOptional(locatorRequest.getX1()).orElse(identifierStr))
						.priorityNo(locatorRequest.getPriorityNo())
						.isGroundLocator(locatorRequest.getIsGroundLocator())
						.build()
		);
		context.putIdentifier(identifier, locatorId);

		final I_M_Locator locatorRecord = warehouseBL.getLocatorById(locatorId);

		return toJson(locatorRecord);
	}

	private static JsonWarehouseResponse.Locator toJson(final I_M_Locator locatorRecord)
	{
		return JsonWarehouseResponse.Locator.builder()
				.id(locatorRecord.getM_Locator_ID())
				.code(locatorRecord.getValue())
				.qrCode(LocatorQRCode.ofLocator(locatorRecord).toGlobalQRCodeJsonString())
				.isDefault(locatorRecord.isDefault())
				.x(locatorRecord.getX())
				.y(locatorRecord.getY())
				.z(locatorRecord.getZ())
				.x1(locatorRecord.getX1())
				.priorityNo(locatorRecord.getPriorityNo())
				.isGroundLocator(locatorRecord.isGroundLocator())
				.build();
	}
}
