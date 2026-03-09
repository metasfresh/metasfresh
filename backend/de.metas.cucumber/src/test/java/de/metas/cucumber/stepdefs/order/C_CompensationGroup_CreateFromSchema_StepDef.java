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

import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.hu.M_HU_PI_Item_Product_StepDefData;
import de.metas.handlingunits.HUPIItemProduct;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupRegularLine;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.product.ProductId;
import de.metas.util.Services;
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
 * This tests the same logic path as the Quick Input flow in {@code OrderLineQuickInputProcessor},
 * but without requiring the WebUI Quick Input abstraction.
 * <p>
 * DataTable columns for "create compensation group from schema template":
 * <ul>
 *     <li>{@code C_Order_ID} (required) — identifier of the target order</li>
 *     <li>{@code C_CompensationGroup_Schema_ID} (required) — identifier of the schema</li>
 *     <li>{@code Qty} (optional, default 1) — number of template sets to create</li>
 *     <li>{@code M_HU_PI_Item_Product_ID} (optional) — identifier of the main article's PI Item Product.
 *         If the schema has IsInheritPackingInstruction=Y and this is provided, PI inheritance is applied
 *         to all template-created order lines.</li>
 * </ul>
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

	/**
	 * Creates a compensation group from a schema template on the given order, then optionally
	 * applies PI inheritance if the schema has IsInheritPackingInstruction=Y.
	 * <p>
	 * Created order lines are stored in {@link C_OrderLine_StepDefData} using generated identifiers
	 * of the form {@code schema_ol_1}, {@code schema_ol_2}, etc.
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

			// Apply PI inheritance if the schema flag is set and a PI was provided
			if (groupTemplate.isInheritPackingInstruction())
			{
				row.getAsOptionalIdentifier("M_HU_PI_Item_Product_ID")
						.ifPresent(piIdentifier -> {
							final I_M_HU_PI_Item_Product mainPiItemProductRecord = piIdentifier.lookupNotNullIn(huPiItemProductTable);
							final HUPIItemProductId mainPiItemProductId = HUPIItemProductId.ofRepoId(mainPiItemProductRecord.getM_HU_PI_Item_Product_ID());

							applyPackingInstructionInheritance(order, group, mainPiItemProductId);
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

	/**
	 * Applies PI inheritance: resolves a compatible M_HU_PI_Item_Product for each sub-article
	 * using the same M_HU_PI_Item (TU type) as the main article's PI.
	 * <p>
	 * This mirrors the logic in {@code OrderLineQuickInputProcessor.applyPackingInstructionFromQuickInput()}.
	 */
	private static void applyPackingInstructionInheritance(
			@NonNull final I_C_Order order,
			@NonNull final Group group,
			@NonNull final HUPIItemProductId mainPiItemProductId)
	{
		final IHUPIItemProductDAO piItemProductDAO = Services.get(IHUPIItemProductDAO.class);
		final HUPIItemProduct mainPiItemProduct = piItemProductDAO.getById(mainPiItemProductId);
		final HuPackingInstructionsItemId piItemId = mainPiItemProduct.getPiItemId();
		final I_M_HU_PI_Item piItem = InterfaceWrapperHelper.load(piItemId.getRepoId(), I_M_HU_PI_Item.class);

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());

		for (final GroupRegularLine regularLine : group.getRegularLines())
		{
			final de.metas.handlingunits.model.I_C_OrderLine orderLine = InterfaceWrapperHelper.load(
					regularLine.getRepoId(), de.metas.handlingunits.model.I_C_OrderLine.class);
			final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());

			final I_M_HU_PI_Item_Product matchingPiItemProduct = piItemProductDAO.retrievePIMaterialItemProduct(
					piItem, bpartnerId, productId, null);
			if (matchingPiItemProduct != null)
			{
				orderLine.setM_HU_PI_Item_Product_ID(matchingPiItemProduct.getM_HU_PI_Item_Product_ID());
				InterfaceWrapperHelper.save(orderLine);
			}
		}
	}
}
