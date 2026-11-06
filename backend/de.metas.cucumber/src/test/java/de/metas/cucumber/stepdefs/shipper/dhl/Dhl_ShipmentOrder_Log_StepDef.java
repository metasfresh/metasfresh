package de.metas.cucumber.stepdefs.shipper.dhl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.cucumber.stepdefs.DHL_Shipper_Config_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Package_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipper.M_Shipper_StepDefData;
import de.metas.shipper.gateway.dhl.DhlDeliveryOrderRepository;
import de.metas.shipper.gateway.dhl.model.I_DHL_ShipmentOrder;
import de.metas.shipper.gateway.dhl.model.I_DHL_Shipper_Config;
import de.metas.shipper.gateway.dhl.model.I_Dhl_ShipmentOrder_Log;
import de.metas.shipping.mpackage.PackageId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Shipper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Asserts the request that was sent (or attempted) to DHL, as persisted in {@code Dhl_ShipmentOrder_Log}.
 */
@RequiredArgsConstructor
public class Dhl_ShipmentOrder_Log_StepDef
{
	private static final int WAIT_SECONDS = 60;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IWorkpackageParamDAO workpackageParamDAO = Services.get(IWorkpackageParamDAO.class);
	private final ObjectMapper objectMapper = new ObjectMapper();
	@NonNull private final DhlDeliveryOrderRepository dhlDeliveryOrderRepository = SpringContextHolder.instance.getBean(DhlDeliveryOrderRepository.class);
	@NonNull private final M_Package_StepDefData packageTable;
	@NonNull private final DHL_Shipper_Config_StepDefData dhlConfigTable;
	@NonNull private final M_Shipper_StepDefData shipperTable;

	/**
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>product</b> - (optional) expected {@code shipments[0].product} of the logged DHL request<br>
	 *   <b>billingNumber</b> - (optional) expected {@code shipments[0].billingNumber} of the logged DHL request<br>
	 * @cucumber.depends StepDefData: M_Package_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the DHL request logged for the DHL_ShipmentOrder of M_Package package1 has:
	 *   | product | billingNumber  |
	 *   | V01PAK  | 33333333330102 |
	 * </pre>
	 */
	@And("the DHL request logged for the DHL_ShipmentOrder of M_Package {word} has:")
	public void validateLoggedDhlRequest(@NonNull final String packageIdentifier, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final PackageId packageId = StepDefDataIdentifier.ofString(packageIdentifier).lookupNotNullIdIn(packageTable);
		final I_DHL_ShipmentOrder shipmentOrder = dhlDeliveryOrderRepository.getShipmentOrderByPackageId(packageId)
				.orElseThrow(() -> new AdempiereException("No DHL_ShipmentOrder found for " + packageId));

		// the DHL request is sent by an async work package, so wait for its log record
		StepDefUtil.tryAndWait(WAIT_SECONDS, 500, () -> retrieveLatestLog(shipmentOrder).isPresent());
		final I_Dhl_ShipmentOrder_Log log = retrieveLatestLog(shipmentOrder)
				.orElseThrow(() -> new AdempiereException("No Dhl_ShipmentOrder_Log found within " + WAIT_SECONDS + "s for DHL_ShipmentOrderRequest_ID=" + shipmentOrder.getDHL_ShipmentOrderRequest_ID()));

		final JsonNode shipment = parseFirstShipment(log);

		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final SoftAssertions softly = new SoftAssertions();
		row.getAsOptionalString("product")
				.ifPresent(product -> softly.assertThat(shipment.path("product").asText()).as("product").isEqualTo(product));
		row.getAsOptionalString("billingNumber")
				.ifPresent(billingNumber -> softly.assertThat(shipment.path("billingNumber").asText()).as("billingNumber").isEqualTo(billingNumber));
		softly.assertAll();
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>DHL_Shipper_Config_ID.Identifier</b> - the config whose id the error message must name<br>
	 *   <b>M_Shipper_ID.Identifier</b> - the shipper whose name the error message must name<br>
	 *   <b>missingField</b> - the field name the error message must name<br>
	 * @cucumber.depends StepDefData: M_Package_StepDefData, DHL_Shipper_Config_StepDefData, M_Shipper_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the DHL work package of M_Package package1 failed because the DHL configuration lacks a billing number:
	 *   | DHL_Shipper_Config_ID.Identifier | M_Shipper_ID.Identifier | missingField |
	 *   | dhl_cfg                          | shipper_dhl             | Kontonummer  |
	 * </pre>
	 */
	@And("the DHL work package of M_Package {word} failed because the DHL configuration lacks a billing number:")
	public void validateFailedDhlWorkPackage(@NonNull final String packageIdentifier, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final I_DHL_ShipmentOrder shipmentOrder = retrieveShipmentOrder(packageIdentifier);

		StepDefUtil.tryAndWait(WAIT_SECONDS, 500, () -> retrieveFailedWorkPackage(shipmentOrder).isPresent());
		final I_C_Queue_WorkPackage workPackage = retrieveFailedWorkPackage(shipmentOrder)
				.orElseThrow(() -> new AdempiereException("No failed DHL work package found within " + WAIT_SECONDS + "s for DHL_ShipmentOrderRequest_ID=" + shipmentOrder.getDHL_ShipmentOrderRequest_ID()));

		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final I_DHL_Shipper_Config config = dhlConfigTable.get(row.getAsIdentifier(I_DHL_Shipper_Config.COLUMNNAME_DHL_Shipper_Config_ID));
		final I_M_Shipper shipper = shipperTable.get(row.getAsIdentifier(I_M_Shipper.COLUMNNAME_M_Shipper_ID));

		assertThat(workPackage.getErrorMsg())
				.as("work package error message")
				.contains("DHL_Shipper_Config_ID=" + config.getDHL_Shipper_Config_ID())
				.contains(shipper.getName())
				.contains(row.getAsString("missingField"));
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_Package_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And no DHL request is logged for the DHL_ShipmentOrder of M_Package package1
	 * </pre>
	 */
	@And("no DHL request is logged for the DHL_ShipmentOrder of M_Package {word}")
	public void validateNoLoggedDhlRequest(@NonNull final String packageIdentifier)
	{
		final I_DHL_ShipmentOrder shipmentOrder = retrieveShipmentOrder(packageIdentifier);
		assertThat(retrieveLatestLog(shipmentOrder)).as("Dhl_ShipmentOrder_Log of DHL_ShipmentOrderRequest_ID=" + shipmentOrder.getDHL_ShipmentOrderRequest_ID()).isEmpty();
	}

	private I_DHL_ShipmentOrder retrieveShipmentOrder(@NonNull final String packageIdentifier)
	{
		final PackageId packageId = StepDefDataIdentifier.ofString(packageIdentifier).lookupNotNullIdIn(packageTable);
		return dhlDeliveryOrderRepository.getShipmentOrderByPackageId(packageId)
				.orElseThrow(() -> new AdempiereException("No DHL_ShipmentOrder found for " + packageId));
	}

	private Optional<I_C_Queue_WorkPackage> retrieveFailedWorkPackage(@NonNull final I_DHL_ShipmentOrder shipmentOrder)
	{
		final List<Integer> processorIds = queryBL.createQueryBuilder(I_C_Queue_PackageProcessor.class)
				.addStringLikeFilter(I_C_Queue_PackageProcessor.COLUMNNAME_Classname, "%DeliveryOrderWorkpackageProcessor", false)
				.create()
				.listIds();
		return queryBL.createQueryBuilder(I_C_Queue_WorkPackage.class)
				.addInArrayFilter(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_PackageProcessor_ID, processorIds)
				.addEqualsFilter(I_C_Queue_WorkPackage.COLUMNNAME_IsError, true)
				.orderByDescending(I_C_Queue_WorkPackage.COLUMNNAME_C_Queue_WorkPackage_ID)
				.create()
				.stream()
				.filter(wp -> workpackageParamDAO.retrieveWorkpackageParams(wp).getParameterAsInt("DeliveryOrderRepoId", -1) == shipmentOrder.getDHL_ShipmentOrderRequest_ID())
				.findFirst();
	}

	private Optional<I_Dhl_ShipmentOrder_Log> retrieveLatestLog(@NonNull final I_DHL_ShipmentOrder shipmentOrder)
	{
		return queryBL.createQueryBuilder(I_Dhl_ShipmentOrder_Log.class)
				.addEqualsFilter(I_Dhl_ShipmentOrder_Log.COLUMNNAME_DHL_ShipmentOrderRequest_ID, shipmentOrder.getDHL_ShipmentOrderRequest_ID())
				.orderByDescending(I_Dhl_ShipmentOrder_Log.COLUMNNAME_Dhl_ShipmentOrder_Log_ID)
				.create()
				.firstOptional(I_Dhl_ShipmentOrder_Log.class);
	}

	private JsonNode parseFirstShipment(@NonNull final I_Dhl_ShipmentOrder_Log log)
	{
		try
		{
			return objectMapper.readTree(log.getRequestMessage()).path("shipments").path(0);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Cannot parse the logged DHL request: " + log.getRequestMessage(), e);
		}
	}
}
