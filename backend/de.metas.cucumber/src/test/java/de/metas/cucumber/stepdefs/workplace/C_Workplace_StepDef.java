package de.metas.cucumber.stepdefs.workplace;

import de.metas.cache.CacheMgt;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.bpgroup.C_BP_Group_StepDefData;
import de.metas.cucumber.stepdefs.doctype.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.picking.PickingSlot_StepDefData;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.cucumber.stepdefs.shipper.Carrier_Product_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.model.I_ExternalSystem;
import de.metas.order.OrderPickingType;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceCreateRequest;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Workplace;
import org.compiere.model.I_C_Workplace_BP_Group;
import org.compiere.model.I_C_Workplace_Carrier_Product;
import org.compiere.model.I_C_Workplace_DocType;
import org.compiere.model.I_C_Workplace_Product;
import org.compiere.model.I_C_Workplace_ProductCategory;
import org.compiere.model.I_C_Workplace_User_Assign;

/**
 * Step definitions for creating and managing {@code C_Workplace} records in Cucumber scenarios.
 *
 * <p>Provides steps to:
 * <ul>
 *   <li>Create workplaces (including picking slots, product/category/carrier restrictions, external systems)</li>
 *   <li>Assign workplaces to users (mirroring a picker being logged in at a workplace)</li>
 *   <li>Deactivate all workplace records (setup/teardown)</li>
 *   <li>Delete all {@code C_Workplace_User_Assign} rows — leak-safe teardown of user&rarr;workplace links</li>
 * </ul>
 *
 * <p>Required DataTable columns for {@code metasfresh contains C_Workplaces}:
 * <ul>
 *   <li>{@code Identifier} — local reference for later steps</li>
 *   <li>{@code M_Warehouse_ID} — warehouse identifier loaded via {@code load M_Warehouse}</li>
 * </ul>
 * <p>Optional columns: {@code M_PickingSlot_ID}, {@code MaxPickingJobs}, {@code SeqNo},
 * {@code OrderPickingType}, {@code M_Product_ID}, {@code M_Product_Category_ID},
 * {@code Carrier_Product_ID}, {@code ExternalSystem.Value}.
 *
 * <p>Example:
 * <pre>
 * And metasfresh contains C_Workplaces
 *   | Identifier | M_Warehouse_ID | M_PickingSlot_ID |
 *   | workplace  | warehouse      | pickingSlot      |
 * And assign C_Workplace to user
 *   | C_Workplace_ID | AD_User_ID.Login |
 *   | workplace      | metasfresh       |
 * </pre>
 */
@RequiredArgsConstructor
public class C_Workplace_StepDef
{
	@NonNull private final WorkplaceService workplaceService = SpringContextHolder.instance.getBean(WorkplaceService.class);
	@NonNull private final ExternalSystemRepository externalSystemRepository = SpringContextHolder.instance.getBean(ExternalSystemRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final C_Workplace_StepDefData workplaceTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Product_Category_StepDefData productCategoryTable;
	@NonNull private final Carrier_Product_StepDefData carrierProductTable;
	@NonNull private final C_BP_Group_StepDefData bpGroupTable;
	@NonNull private final C_DocType_StepDefData docTypeTable;
	@NonNull private final M_Locator_StepDefData locatorTable;
	@NonNull private final PickingSlot_StepDefData pickingSlotTable;

	/**
	 * @cucumber.stepdef Creates {@code C_Workplace} records, optionally restricted by warehouse, picking type,
	 * product / product category, carrier product, external system, business-partner group and document type.
	 * @cucumber.columns
	 *   <b>Identifier</b> — (optional) alias for cross-step reference<br>
	 *   <b>M_Warehouse_ID</b> — (required, identifier-ref) warehouse the workplace picks from<br>
	 *   <b>MaxPickingJobs</b> — (optional) maximum concurrently assigned picking jobs<br>
	 *   <b>SeqNo</b> — (optional) order in which workplaces are evaluated for assignment<br>
	 *   <b>OrderPickingType</b> — (optional) order picking type code<br>
	 *   <b>M_Product_ID</b> — (optional, identifier-ref) comma-separated products the workplace is restricted to<br>
	 *   <b>M_Product_Category_ID</b> — (optional, identifier-ref) comma-separated product categories<br>
	 *   <b>Carrier_Product_ID</b> — (optional, identifier-ref) comma-separated carrier products<br>
	 *   <b>ExternalSystem.Value</b> — (optional) comma-separated external system values<br>
	 *   <b>C_BP_Group_ID</b> — (optional, identifier-ref) comma-separated business-partner groups<br>
	 *   <b>C_DocType_ID</b> — (optional, identifier-ref) comma-separated document types<br>
	 * @cucumber.depends StepDefData: M_Warehouse_StepDefData, M_Product_StepDefData, M_Product_Category_StepDefData, Carrier_Product_StepDefData, C_BP_Group_StepDefData, C_DocType_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_Workplaces
	 *   | Identifier | SeqNo | M_Warehouse_ID | MaxPickingJobs | C_BP_Group_ID  | C_DocType_ID    |
	 *   | workplace1 | 10    | wh             | 10             | groupPreferred | docTypeStandard |
	 * </pre>
	 */
	@Given("metasfresh contains C_Workplaces")
	public void createWorkplaces(final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_Workplace.COLUMNNAME_C_Workplace_ID)
				.forEach(this::createWorkplace);
	}

	private void createWorkplace(final DataTableRow row)
	{
		final String name = row.suggestValueAndName().getName();
		final WarehouseId warehouseId = row.getAsIdentifier(I_C_Workplace.COLUMNNAME_M_Warehouse_ID).lookupNotNullIdIn(warehouseTable);

		final WorkplaceCreateRequest.WorkplaceCreateRequestBuilder builder = WorkplaceCreateRequest.builder()
				.name(name)
				.warehouseId(warehouseId);

		row.getAsOptionalIdentifier(I_C_Workplace.COLUMNNAME_PickFrom_Locator_ID)
				.map(locatorTable::getId)
				.ifPresent(builder::pickFromLocatorId);

		row.getAsOptionalInt(I_C_Workplace.COLUMNNAME_MaxPickingJobs).ifPresent(builder::maxPickingJobs);
		row.getAsOptionalInt(I_C_Workplace.COLUMNNAME_SeqNo).ifPresent(seqNo -> builder.seqNo(SeqNo.ofInt(seqNo)));
		row.getAsOptionalString(I_C_Workplace.COLUMNNAME_OrderPickingType).ifPresent(type -> builder.orderPickingType(OrderPickingType.ofCode(type)));

		row.getAsOptionalIdentifier(I_M_PickingSlot.COLUMNNAME_M_PickingSlot_ID).ifPresent(pickingSlotIdentifier -> {
			final I_M_PickingSlot pickingSlot = pickingSlotTable.get(pickingSlotIdentifier);
			builder.pickingSlotId(PickingSlotId.ofRepoId(pickingSlot.getM_PickingSlot_ID()));
		});

		row.getAsOptionalCommaSeparatedString(I_C_Workplace_Product.COLUMNNAME_M_Product_ID).ifPresent(list -> list.forEach(
				product -> builder.productId(productTable.getId(product))));
		row.getAsOptionalCommaSeparatedString(I_C_Workplace_ProductCategory.COLUMNNAME_M_Product_Category_ID).ifPresent(list -> list.forEach(
				pc -> builder.productCategoryId((productCategoryTable.getId(pc)))));
		row.getAsOptionalCommaSeparatedString(I_C_Workplace_Carrier_Product.COLUMNNAME_Carrier_Product_ID).ifPresent(list -> list.forEach(
				cp -> builder.carrierProductId((carrierProductTable.getId(cp)))));
		row.getAsOptionalCommaSeparatedString(I_C_Workplace_BP_Group.COLUMNNAME_C_BP_Group_ID).ifPresent(list -> list.forEach(
				bpGroup -> builder.bpGroupId(bpGroupTable.getId(bpGroup))));
		row.getAsOptionalCommaSeparatedString(I_C_Workplace_DocType.COLUMNNAME_C_DocType_ID).ifPresent(list -> list.forEach(
				docType -> builder.docTypeId(docTypeTable.getId(docType))));
		row.getAsOptionalCommaSeparatedString(I_ExternalSystem.Table_Name + "." + I_ExternalSystem.COLUMNNAME_Value)
				.ifPresent(list -> list.forEach(
						externalSystemValue -> {
							final ExternalSystemId externalSystemId = externalSystemRepository.getIdByType(ExternalSystemType.ofValue(externalSystemValue));
							builder.externalSystemId(externalSystemId);
						})
				);

		final Workplace workplace = workplaceService.create(builder.build());
		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> workplaceTable.put(identifier, workplace));
	}

	/**
	 * Assigns a previously-created workplace to a user (by login), so that
	 * {@code WorkplaceService.getWorkplaceByUserId(userId)} resolves it — mirroring a picker
	 * being logged in at a workplace. This is what lets a programmatically-created PRODUCT picking
	 * job auto-allocate the workplace's picking slot.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code C_Workplace_ID} — identifier of a workplace created by {@code metasfresh contains C_Workplaces}</li>
	 *   <li>{@code AD_User_ID.Login} — login name of the user to assign (e.g. {@code metasfresh})</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * And assign C_Workplace to user
	 *   | C_Workplace_ID | AD_User_ID.Login |
	 *   | workplace      | metasfresh       |
	 * </pre>
	 */
	@Given("assign C_Workplace to user")
	public void assignWorkplaceToUser(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final WorkplaceId workplaceId = row.getAsIdentifier(I_C_Workplace.COLUMNNAME_C_Workplace_ID).lookupNotNullIdIn(workplaceTable);
			final String login = row.getAsString("AD_User_ID.Login");
			final UserId userId = Services.get(IUserDAO.class).retrieveUserIdByLogin(login);
			workplaceService.assignWorkplace(userId, workplaceId);
		});
	}

	@Given("deactivate all C_Workplace records")
	public void deactivate_C_Workplace()
	{
		// Delete the user->workplace assignments FIRST: deactivating a workplace while leaving its
		// C_Workplace_User_Assign rows behind creates a dangling assignment — a later getWorkplaceByUserId
		// resolves the assignment, then fails to load the now-inactive workplace ("No workplace found for
		// WorkplaceId"). Clearing assignments here makes "deactivate all C_Workplace" a complete reset and
		// prevents that cross-scenario/feature leak on the shared single-JVM executor.
		delete_C_Workplace_User_Assign();

		final ICompositeQueryUpdater<I_C_Workplace> updater = queryBL
				.createCompositeQueryUpdater(I_C_Workplace.class)
				.addSetColumnValue(I_C_Workplace.COLUMNNAME_IsActive, false);

		queryBL.createQueryBuilder(I_C_Workplace.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.update(updater);

		// Bulk SQL update fires a CacheInvalidation event, but that event may not be processed in time for the
		// next step / feature in the shared single-JVM executor (same trap as AD_SysConfig_StepDef). Reset the
		// WorkplaceRepository cache (keyed on C_Workplace) synchronously so a stale workplace map cannot leak.
		CacheMgt.get().reset(I_C_Workplace.Table_Name);
	}

	/**
	 * Deletes all {@code C_Workplace_User_Assign} records (the user&rarr;workplace links created by
	 * {@code assign C_Workplace to user}).
	 *
	 * <p><b>Leak-safety / teardown.</b> {@code assignWorkplace} persists a {@code C_Workplace_User_Assign}
	 * row that survives the scenario in the shared single-JVM sequential executor. When the assignment is
	 * made to the shared {@code metasfresh} login it would otherwise leak into later scenarios/features:
	 * {@code WorkplaceService.getWorkplaceByUserId} resolves the assign row and then calls
	 * {@code WorkplaceRepository.getById(...)}, which throws {@code "No workplace found for ..."} once the
	 * referenced {@code C_Workplace} has been deactivated (the prior HTTP 422 regression).
	 *
	 * <p><b>Why DELETE and not deactivate.</b> The table has a unique index {@code one_user_per_org} on
	 * {@code (AD_User_ID, AD_Org_ID)} that ignores {@code IsActive}. {@code WorkplaceUserAssignRepository.create}
	 * only re-uses an <i>active</i> row, so a left-behind <i>inactive</i> row would permanently block any future
	 * re-assignment of that user with a {@code duplicate key value violates unique constraint "one_user_per_org"}
	 * error. Deleting the row clears both the leak and that constraint trap.
	 *
	 * <p>Takes no DataTable. Pair it with {@code deactivate all C_Workplace records} around any scenario that
	 * assigns a workplace to the shared {@code metasfresh} user.
	 *
	 * <p>Example:
	 * <pre>
	 * And delete all C_Workplace_User_Assign records
	 * And deactivate all C_Workplace records
	 * </pre>
	 */
	@Given("delete all C_Workplace_User_Assign records")
	public void delete_C_Workplace_User_Assign()
	{
		queryBL.createQueryBuilder(I_C_Workplace_User_Assign.class)
				.create()
				.deleteDirectly();

		// Synchronous cache reset — see Javadoc above (bulk SQL delete's async invalidation is not timely enough).
		CacheMgt.get().reset(I_C_Workplace_User_Assign.Table_Name);
	}
}
