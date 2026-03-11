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

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.order.OrderGroupPIInheritanceService;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupRegularLine;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;

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

	private final OrderGroupRepository orderGroupsRepo = SpringContextHolder.instance.getBean(OrderGroupRepository.class);
	private final GroupTemplateRepository groupTemplateRepo = SpringContextHolder.instance.getBean(GroupTemplateRepository.class);
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
	 */
	@When("create compensation group from schema template:")
	public void createGroupFromSchemaTemplate(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_Order order = row.getAsIdentifier("C_Order_ID")
					.lookupNotNullIn(orderTable);
			final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

			final I_C_CompensationGroup_Schema schemaRecord = row.getAsIdentifier("C_CompensationGroup_Schema_ID")
					.lookupNotNullIn(schemaTable);

			final GroupTemplateId groupTemplateId = GroupTemplateId.ofRepoId(schemaRecord.getC_CompensationGroup_Schema_ID());
			final GroupTemplate groupTemplate = groupTemplateRepo.getById(groupTemplateId);

			final BigDecimal qty = row.getAsOptionalBigDecimal("Qty").orElse(BigDecimal.ONE);

			final Group group = orderGroupsRepo.prepareNewGroup()
					.groupTemplate(groupTemplate)
					.qty(qty)
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
			int lineIndex = 1;
			for (final GroupRegularLine regularLine : group.getRegularLines())
			{
				final I_C_OrderLine orderLine = InterfaceWrapperHelper.load(regularLine.getRepoId(), I_C_OrderLine.class);
				orderLineTable.putOrReplace("schema_ol_" + lineIndex, orderLine);
				lineIndex++;
			}
		});
	}
}
