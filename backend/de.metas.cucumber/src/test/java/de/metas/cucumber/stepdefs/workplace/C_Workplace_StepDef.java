package de.metas.cucumber.stepdefs.workplace;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.cucumber.stepdefs.shipper.Carrier_Product_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.model.I_ExternalSystem;
import de.metas.order.OrderPickingType;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceCreateRequest;
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
import org.compiere.model.I_C_Workplace_Carrier_Product;
import org.compiere.model.I_C_Workplace_Product;
import org.compiere.model.I_C_Workplace_ProductCategory;

@RequiredArgsConstructor
public class C_Workplace_StepDef
{
	@NonNull private final WorkplaceService workplaceService = SpringContextHolder.instance.getBean(WorkplaceService.class);
	@NonNull private final ExternalSystemRepository externalSystemRepository = SpringContextHolder.instance.getBean(ExternalSystemRepository.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final C_Workplace_StepDefData workplaceTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Product_Category_StepDefData productCategoryTable;
    @NonNull private final Carrier_Product_StepDefData carrierProductTable;

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

		row.getAsOptionalInt(I_C_Workplace.COLUMNNAME_MaxPickingJobs).ifPresent(builder::maxPickingJobs);
		row.getAsOptionalInt(I_C_Workplace.COLUMNNAME_SeqNo).ifPresent(seqNo -> builder.seqNo(SeqNo.ofInt(seqNo)));
		row.getAsOptionalString(I_C_Workplace.COLUMNNAME_OrderPickingType).ifPresent(type -> builder.orderPickingType(OrderPickingType.ofCode(type)));

		row.getAsOptionalCommaSeparatedString(I_C_Workplace_Product.COLUMNNAME_M_Product_ID).ifPresent(list -> list.forEach(
				product -> builder.productId(productTable.getId(product))));
		row.getAsOptionalCommaSeparatedString(I_C_Workplace_ProductCategory.COLUMNNAME_M_Product_Category_ID).ifPresent(list -> list.forEach(
				pc -> builder.productCategoryId((productCategoryTable.getId(pc)))));
		row.getAsOptionalCommaSeparatedString(I_C_Workplace_Carrier_Product.COLUMNNAME_Carrier_Product_ID).ifPresent(list -> list.forEach(
				cp -> builder.carrierProductId((carrierProductTable.getId(cp)))));
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

	@Given("deactivate all C_Workplace records")
	public void deactivate_C_Workplace()
	{
		final ICompositeQueryUpdater<I_C_Workplace> updater = queryBL
				.createCompositeQueryUpdater(I_C_Workplace.class)
				.addSetColumnValue(I_C_Workplace.COLUMNNAME_IsActive, false);

		queryBL.createQueryBuilder(I_C_Workplace.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.update(updater);
	}
}
