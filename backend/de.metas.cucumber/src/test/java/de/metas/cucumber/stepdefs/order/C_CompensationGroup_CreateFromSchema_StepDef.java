/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.order;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.InterfaceWrapperHelperUtils;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.order.OrderGroupPIInheritanceService;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupCompensationLine;
import de.metas.order.compensationGroup.GroupRegularLine;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateCompensationLine;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupCompensationChangesHandler;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.compensationGroup.calibration.CompensationGroupCalibrationService;
import de.metas.order.compensationGroup.calibration.GroupCalibrations;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.product.ProductId;
import de.metas.util.collections.CollectionUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import java.math.BigDecimal;
import java.util.List;

/**
 * Step definitions for creating compensation groups from schema templates and applying PI inheritance.
 * <p>
 * Uses {@link OrderGroupRepository} to create group order lines from a {@link GroupTemplate},
 * then optionally applies packing instruction (PI) inheritance by calling the production code in
 * {@link OrderGroupPIInheritanceService#applyPackingInstructionInheritance}.
 * <p>
 * <b>Prerequisites</b>:
 * <ul>
 *     <li>The schema must already have {@link de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine} records
 *         (created via "metasfresh contains C_CompensationGroup_Schema_TemplateLine:" step)</li>
 *     <li>The target order must exist (created via "metasfresh contains C_Orders:" step)</li>
 *     <li>All template-line products must have prices in the order's price list</li>
 * </ul>
 * <p>
 * <b>DataTable columns</b> for {@code "create compensation group from schema template:"}:
 * <ul>
 *     <li>{@code C_Order_ID} (required) — identifier of the target order</li>
 *     <li>{@code C_CompensationGroup_Schema_ID} (required) — identifier of the schema</li>
 *     <li>{@code Qty} (optional, default 1) — number of template sets to create</li>
 *     <li>{@code M_HU_PI_Item_Product_ID} (optional) — identifier of the main article's PI Item Product.
 *         When the schema has {@code IsInheritPackingInstruction=Y} and this is provided,
 *         each created order line gets a <b>per-product compatible</b> {@code M_HU_PI_Item_Product}
 *         resolved via {@link IHUPIItemProductDAO#retrievePIMaterialItemProduct} for the same TU type.
 *         If no compatible PI exists for a sub-article, that line is skipped (keeps default PI=101).</li>
 * </ul>
 * <p>
 * <b>Output identifiers</b>: Created order lines are stored in {@link C_OrderLine_StepDefData}
 * as {@code schema_ol_1}, {@code schema_ol_2}, etc., following the template line sequence order.
 * Use these identifiers in subsequent "validate C_OrderLine:" steps.
 */
@RequiredArgsConstructor
public class C_CompensationGroup_CreateFromSchema_StepDef
{
	private final @NonNull C_Order_StepDefData orderTable;
	private final @NonNull C_OrderLine_StepDefData orderLineTable;
	private final @NonNull C_CompensationGroup_Schema_StepDefData schemaTable;
	private final @NonNull M_HU_PI_Item_Product_StepDefData huPiItemProductTable;
	private final @NonNull M_Product_StepDefData productTable;
	private final @NonNull C_BPartner_StepDefData bpartnerTable;

	private final CompensationGroupCalibrationService calibrationService = SpringContextHolder.instance.getBean(CompensationGroupCalibrationService.class);
	private final OrderGroupRepository orderGroupsRepo = SpringContextHolder.instance.getBean(OrderGroupRepository.class);
	private final GroupTemplateRepository groupTemplateRepo = SpringContextHolder.instance.getBean(GroupTemplateRepository.class);
	private final OrderGroupCompensationChangesHandler groupChangesHandler = SpringContextHolder.instance.getBean(OrderGroupCompensationChangesHandler.class);
	private final OrderGroupPIInheritanceService piInheritanceService = new OrderGroupPIInheritanceService();

	/**
	 * Creates a compensation group from a schema template on the given order, then optionally
	 * applies PI inheritance if the schema has {@code IsInheritPackingInstruction=Y}.
	 * <p>
	 * Created order lines are registered in {@link C_OrderLine_StepDefData} as
	 * {@code schema_ol_1}, {@code schema_ol_2}, etc. (1-based, following template line {@code SeqNo} order).
	 * These identifiers can be used in subsequent verification steps, e.g.:
	 * <pre>{@code
	 * Then validate C_OrderLine:
	 *   | C_OrderLine_ID | M_Product_ID | OPT.M_HU_PI_Item_Product_ID.Identifier |
	 *   | schema_ol_1    | subProduct1  | piProd_sub1                             |
	 * }</pre>
	 * <p>
	 * Optional columns:
	 * <ul>
	 *   <li>{@code IdentifyLinesBy} — {@code Product} to register each created regular line as
	 *       {@code schema_ol_<product identifier>} and each compensation (discount / surcharge) line as
	 *       {@code schema_comp_<product identifier>} instead of by position (default: by position)</li>
	 *   <li>{@code Calibrated} — {@code Y} to apply the calibration rules the way quick input and order candidates do
	 *       (default {@code N}: the group is created uncalibrated, as the other group creators do)</li>
	 * </ul>
	 */
	@When("create compensation group from schema template:")
	public void createGroupFromSchemaTemplate(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createGroupFromSchemaTemplate);
	}

	private void createGroupFromSchemaTemplate(@NonNull final DataTableRow row)
	{
		final I_C_Order order = row.getAsIdentifier("C_Order_ID")
				.lookupNotNullIn(orderTable);
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		final I_C_CompensationGroup_Schema schemaRecord = row.getAsIdentifier("C_CompensationGroup_Schema_ID")
				.lookupNotNullIn(schemaTable);

		final GroupTemplateId groupTemplateId = GroupTemplateId.ofRepoId(schemaRecord.getC_CompensationGroup_Schema_ID());
		final GroupTemplate groupTemplate = groupTemplateRepo.getById(groupTemplateId);

		final BigDecimal qty = row.getAsOptionalBigDecimal("Qty").orElse(BigDecimal.ONE);

		final GroupCalibrations calibrations = row.getAsOptionalBoolean("Calibrated").orElseFalse()
				? calibrationService.computeCalibrations(order, groupTemplate)
				: null;

		final Group group = orderGroupsRepo.prepareNewGroup()
				.groupTemplate(groupTemplate)
				.qty(qty)
				.calibrations(calibrations)
				.createGroup(orderId, null);

		// Apply PI inheritance using the production code path (OrderLineQuickInputProcessor)
		if (groupTemplate.isInheritPackingInstruction())
		{
			row.getAsOptionalIdentifier("M_HU_PI_Item_Product_ID")
					.ifPresent(piIdentifier -> {
						final I_M_HU_PI_Item_Product mainPiItemProductRecord = piIdentifier.lookupNotNullIn(huPiItemProductTable);
						final HUPIItemProductId mainPiItemProductId = HUPIItemProductId.ofRepoId(mainPiItemProductRecord.getM_HU_PI_Item_Product_ID());

						piInheritanceService.applyPackingInstructionInheritance(order, group, mainPiItemProductId);
					});
		}

		// Store created order lines for later verification
		final boolean identifyLinesByProduct = row.getAsOptionalString("IdentifyLinesBy").map("Product"::equals).orElse(false);
		int lineIndex = 1;
		for (final GroupRegularLine regularLine : group.getRegularLines())
		{
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.load(regularLine.getRepoId(), I_C_OrderLine.class);
			if (identifyLinesByProduct)
			{
				registerByProduct("schema_ol_", orderLine);
			}
			else
			{
				orderLineTable.putOrReplace("schema_ol_" + lineIndex, orderLine);
			}
			lineIndex++;
		}
		if (identifyLinesByProduct)
		{
			for (final GroupCompensationLine compensationLine : group.getCompensationLines())
			{
				final I_C_OrderLine orderLine = InterfaceWrapperHelper.load(compensationLine.getRepoId(), I_C_OrderLine.class);
				registerByProduct("schema_comp_", orderLine);
			}
		}
	}

	private void registerByProduct(@NonNull final String identifierPrefix, @NonNull final I_C_OrderLine orderLine)
	{
		productTable.forEach((productIdentifier, product) -> {
			if (product.getM_Product_ID() == orderLine.getM_Product_ID())
			{
				orderLineTable.putOrReplace(identifierPrefix + productIdentifier.getAsString(), orderLine);
			}
		});
	}

	/**
	 * Groups existing order lines the way the order's "create compensation group" action does (a group without a schema,
	 * whose compensation line takes its type from the compensation product), then edits the new compensation line
	 * the way a user does in the order line grid.
	 * <p>
	 * Columns:
	 * <ul>
	 *   <li>{@code C_OrderLine_ID} — comma-separated identifiers of the order lines to group</li>
	 *   <li>{@code M_Product_ID} — the compensation product</li>
	 *   <li>{@code Name} — the group's name</li>
	 *   <li>{@code CompensationLine} — identifier under which the new compensation order line is registered</li>
	 *   <li>{@code OPT.GroupCompensationAmtType} — optional; {@code P} (percent) or {@code Q} (price and quantity, i.e. a fixed amount)</li>
	 *   <li>{@code OPT.GroupCompensationPercentage} — optional; the percentage of a percent line</li>
	 *   <li>{@code OPT.PriceEntered} — optional; the amount of a fixed-amount line (quantity 1)</li>
	 *   <li>{@code OPT.C_BPartner_Vendor_ID} — optional; the vendor of the new compensation line (a drop-ship order needs one on every line)</li>
	 * </ul>
	 *
	 * @cucumber.example
	 * <pre>
	 * When create compensation group from order lines:
	 *   | C_OrderLine_ID      | M_Product_ID    | Name       | CompensationLine | OPT.GroupCompensationPercentage |
	 *   | ol_goods1,ol_goods2 | discountProduct | Bundle 3 % | ol_discount      | 3                               |
	 * </pre>
	 */
	@When("create compensation group from order lines:")
	public void createGroupFromOrderLines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final List<OrderLineId> orderLineIds = row.getAsIdentifier(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID)
					.toCommaSeparatedList()
					.stream()
					.map(identifier -> OrderLineId.ofRepoId(identifier.lookupNotNullIn(orderLineTable).getC_OrderLine_ID()))
					.collect(ImmutableList.toImmutableList());
			final ProductId compensationProductId = ProductId.ofRepoId(row.getAsIdentifier(I_C_OrderLine.COLUMNNAME_M_Product_ID)
					.lookupNotNullIn(productTable)
					.getM_Product_ID());

			final Group group = orderGroupsRepo.prepareNewGroup()
					.groupTemplate(GroupTemplate.builder()
							.name(row.getAsString("Name"))
							.compensationLine(GroupTemplateCompensationLine.ofProductId(compensationProductId))
							.regularLinesToAdd(ImmutableList.of())
							.build())
					.createGroup(orderLineIds);

			final I_C_OrderLine compensationLine = InterfaceWrapperHelper.load(
					CollectionUtils.singleElement(group.getCompensationLines()).getRepoId().getRepoId(),
					I_C_OrderLine.class);

			row.getAsOptionalString(I_C_OrderLine.COLUMNNAME_GroupCompensationAmtType)
					.ifPresent(compensationLine::setGroupCompensationAmtType);
			row.getAsOptionalBigDecimal(I_C_OrderLine.COLUMNNAME_GroupCompensationPercentage)
					.ifPresent(compensationLine::setGroupCompensationPercentage);
			row.getAsOptionalBigDecimal(I_C_OrderLine.COLUMNNAME_PriceEntered)
					.ifPresent(price -> {
						compensationLine.setQtyEntered(BigDecimal.ONE);
						compensationLine.setQtyOrdered(BigDecimal.ONE);
						compensationLine.setIsManualPrice(true);
						compensationLine.setPriceEntered(price);
						compensationLine.setPriceActual(price);
					});

			row.getAsOptionalIdentifier(I_C_OrderLine.COLUMNNAME_C_BPartner_Vendor_ID)
					.map(bpartnerTable::getId)
					.ifPresent(vendorId -> compensationLine.setC_BPartner_Vendor_ID(vendorId.getRepoId()));

			// what the order line grid's callout does when the user edits the compensation line
			groupChangesHandler.updateCompensationLineNoSave(compensationLine);
			InterfaceWrapperHelper.save(compensationLine);

			orderLineTable.putOrReplace(row.getAsIdentifier("CompensationLine"), compensationLine);
		});
	}

	/**
	 * Changes the percentage of a compensation (discount) order line the way a user does it in the sales order's line grid:
	 * the field's callout recomputes the line from its group, then the line is saved as a user edit,
	 * which makes the order line interceptor update the whole group.
	 * <p>
	 * The callout is invoked through the same handler method the order line callout calls
	 * ({@link OrderGroupCompensationChangesHandler#updateCompensationLineNoSave}), since callouts do not run on a programmatic save.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_OrderLine_ID</b> — (required, identifier-ref) the compensation order line<br>
	 *   <b>GroupCompensationPercentage</b> — (required) the new percentage<br>
	 * @cucumber.depends StepDefData: C_OrderLine_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the user changes the percentage of compensation order lines in the order line grid:
	 *   | C_OrderLine_ID | GroupCompensationPercentage |
	 *   | ol_discount    | 10                          |
	 * </pre>
	 */
	@When("the user changes the percentage of compensation order lines in the order line grid:")
	public void changeCompensationPercentageAsUser(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_OrderLine compensationLine = row.getAsIdentifier(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID).lookupNotNullIn(orderLineTable);
			InterfaceWrapperHelper.refresh(compensationLine);

			compensationLine.setGroupCompensationPercentage(row.getAsBigDecimal(I_C_OrderLine.COLUMNNAME_GroupCompensationPercentage));
			groupChangesHandler.updateCompensationLineNoSave(compensationLine);

			InterfaceWrapperHelperUtils.set_ManualUserAction(compensationLine);
			try
			{
				InterfaceWrapperHelper.save(compensationLine);
			}
			finally
			{
				// the flag lives on the cached PO and would leak into later, non-UI saves
				InterfaceWrapperHelperUtils.unset_ManualUserAction(compensationLine);
			}
		});
	}
}
