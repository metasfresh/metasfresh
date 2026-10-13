package de.metas.order.inoutcandidate;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.adempiere.model.I_C_Order;
import de.metas.inout.PriorityRule;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers {@link OrderLineShipmentScheduleHandler#computePriorityRuleCode(I_C_Order, ShipperId, ClientAndOrgId)}.
 */
public class OrderLineShipmentScheduleHandlerPriorityRuleTest
{
	private static final ClientAndOrgId CLIENT_AND_ORG_ID = ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, OrgId.ANY);

	private OrderLineShipmentScheduleHandler handler;
	private ISysConfigBL sysConfigBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		// computePriorityRuleCode() only relies on the field-initialized Services.get() beans (sysConfigBL, shipperDAO);
		// the constructor-injected collaborators below are never exercised by it, so a mock + a plain no-extensions
		// service stand in for them (IShipmentScheduleInvalidateBL's real impl needs a Spring context to construct).
		handler = new OrderLineShipmentScheduleHandler(
				Mockito.mock(IShipmentScheduleInvalidateBL.class),
				new PickingBOMService(),
				Optional.empty());
		// assigned AFTER init(): AdempiereTestHelper.get().init() calls Services.clear() in @BeforeEach, so a field
		// initialized at construction time would hold a stale/cleared service registry entry.
		sysConfigBL = Services.get(ISysConfigBL.class);
	}

	private void setSwitch(final boolean value)
	{
		sysConfigBL.setValue(
				OrderLineShipmentScheduleHandler.SYSCONFIG_PriorityRuleFromShipper,
				value,
				CLIENT_AND_ORG_ID.getClientId(),
				CLIENT_AND_ORG_ID.getOrgId());
	}

	private static I_C_Order createOrder(final PriorityRule priorityRule)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setPriorityRule(priorityRule.getCode());
		save(order);
		return order;
	}

	private static ShipperId createShipper(@Nullable final String priorityRuleCode)
	{
		final I_M_Shipper shipper = newInstance(I_M_Shipper.class);
		shipper.setPriorityRule(priorityRuleCode);
		save(shipper);
		return ShipperId.ofRepoId(shipper.getM_Shipper_ID());
	}

	@Test
	void switchOff_usesOrderPriority()
	{
		setSwitch(false);
		final I_C_Order order = createOrder(PriorityRule.Low);
		final ShipperId shipperId = createShipper(PriorityRule.Urgent.getCode());

		final String result = handler.computePriorityRuleCode(order, shipperId, CLIENT_AND_ORG_ID);

		assertThat(result).isEqualTo(PriorityRule.Low.getCode());
	}

	@Test
	void switchOn_shipperHasPriority_usesShipperPriority()
	{
		setSwitch(true);
		final I_C_Order order = createOrder(PriorityRule.Low);
		final ShipperId shipperId = createShipper(PriorityRule.Urgent.getCode());

		final String result = handler.computePriorityRuleCode(order, shipperId, CLIENT_AND_ORG_ID);

		assertThat(result).isEqualTo(PriorityRule.Urgent.getCode());
	}

	@Test
	void switchOn_shipperPriorityBlank_fallsBackToOrder()
	{
		setSwitch(true);
		final I_C_Order order = createOrder(PriorityRule.High);
		final ShipperId shipperId = createShipper(null);

		final String result = handler.computePriorityRuleCode(order, shipperId, CLIENT_AND_ORG_ID);

		assertThat(result).isEqualTo(PriorityRule.High.getCode());
	}

	@Test
	void switchOn_shipperIdNull_fallsBackToOrder()
	{
		setSwitch(true);
		final I_C_Order order = createOrder(PriorityRule.High);

		final String result = handler.computePriorityRuleCode(order, null, CLIENT_AND_ORG_ID);

		assertThat(result).isEqualTo(PriorityRule.High.getCode());
	}

	/**
	 * An out-of-list code can only reach {@code M_Shipper.PriorityRule} through an import, the REST API or direct SQL
	 * (the column has no CHECK constraint; the value list is enforced in the UI layer only). Such a code must NOT
	 * abort the derivation: this method runs inside {@code ShipmentScheduleUpdater.updateSchedules}, which iterates a
	 * whole recompute batch with no per-item error handling, so a throw here would roll back the batch's transaction
	 * and take unrelated schedules down with it. The order's own priority is likewise returned unvalidated, so
	 * falling back keeps the two sides of this method symmetric.
	 */
	@Test
	void switchOn_shipperHasUnknownCode_fallsBackToOrder()
	{
		setSwitch(true);
		final I_C_Order order = createOrder(PriorityRule.High);
		final ShipperId shipperId = createShipper("not-a-known-priority-code");

		final String result = handler.computePriorityRuleCode(order, shipperId, CLIENT_AND_ORG_ID);

		assertThat(result).isEqualTo(PriorityRule.High.getCode());
	}
}
