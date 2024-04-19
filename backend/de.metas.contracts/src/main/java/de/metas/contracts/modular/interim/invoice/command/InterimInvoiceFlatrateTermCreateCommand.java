/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.interim.invoice.command;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.process.FlatrateTermCreator;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Collections;

public class InterimInvoiceFlatrateTermCreateCommand
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@NonNull
	private final BPartnerId bpartnerId;
	@NonNull
	private final ProductId productId;
	@NonNull
	private final Instant dateFrom;
	@NonNull
	private final Instant dateTo;
	@NonNull
	private final FlatrateTermId modulareFlatrateTermId;

	@NonNull
	private final OrderLine orderLine;
	@NonNull
	private final I_C_Flatrate_Conditions conditions;
	@NonNull
	private final I_M_Product product;
	@NonNull
	private final I_C_Flatrate_Term modularContract;

	@NonNull
	private final YearAndCalendarId yearAndCalendarId;

	@Builder
	public InterimInvoiceFlatrateTermCreateCommand(
			@Nullable final BPartnerId bpartnerId,
			@NonNull final ConditionsId conditionsId,
			@Nullable final ProductId productId,
			@NonNull final Instant dateFrom,
			@NonNull final Instant dateTo,
			@NonNull final OrderLineId orderLineId,
			@NonNull final FlatrateTermId modulareFlatrateTermId,
			@NonNull final YearAndCalendarId yearAndCalendarId)
	{
		final OrderLineRepository orderLineRepository = SpringContextHolder.instance.getBean(OrderLineRepository.class);
		final IProductDAO productDAO = Services.get(IProductDAO.class);

		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.orderLine = orderLineRepository.getById(orderLineId);
		this.bpartnerId = CoalesceUtil.coalesceSuppliersNotNull(
				() -> bpartnerId,
				() -> BPartnerId.ofRepoId(orderDAO.getById(orderLine.getOrderId()).getBill_BPartner_ID()));
		this.productId = CoalesceUtil.coalesceNotNull(productId, orderLine.getProductId());
		this.conditions = flatrateBL.getConditionsById(conditionsId);
		this.modulareFlatrateTermId = modulareFlatrateTermId;
		this.product = productDAO.getById(this.productId);
		this.modularContract = flatrateBL.getById(modulareFlatrateTermId);
		this.yearAndCalendarId = yearAndCalendarId;
	}

	public void execute()
	{
		Check.assumeEquals(conditions.getType_Conditions(), X_C_Flatrate_Conditions.TYPE_CONDITIONS_InterimInvoice);
		final I_C_BPartner bpartner = bpartnerDAO.getById(bpartnerId);
		createFlatrateTerm(bpartner);
	}

	private void createFlatrateTerm(final I_C_BPartner bpartner)
	{
		final I_C_Flatrate_Term flatrateTermRecord = FlatrateTermCreator.builder()
				.bPartners(Collections.singleton(bpartner))
				.orgId(orderLine.getOrgId())
				.conditions(conditions)
				.ctx(InterfaceWrapperHelper.getCtx(bpartner))
				.product(product)
				.startDate(TimeUtil.asTimestamp(dateFrom))
				.endDate(TimeUtil.asTimestamp(dateTo))
				.isCompleteDocument(false)
				.build()
				.createTermsForBPartners()
				.stream()
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No flatrate term created"));

		flatrateTermRecord.setM_PricingSystem_ID(modularContract.getM_PricingSystem_ID());
		flatrateTermRecord.setPlannedQtyPerUnit(modularContract.getPlannedQtyPerUnit());
		flatrateTermRecord.setContractStatus(modularContract.getContractStatus());
		flatrateTermRecord.setMasterStartDate(TimeUtil.asTimestamp(dateFrom));
		flatrateTermRecord.setMasterEndDate(TimeUtil.asTimestamp(dateTo));
		flatrateTermRecord.setPriceActual(modularContract.getPriceActual());
		flatrateTermRecord.setC_TaxCategory_ID(modularContract.getC_TaxCategory_ID());
		flatrateTermRecord.setC_Order_Term(modularContract.getC_Order_Term());
		flatrateTermRecord.setC_OrderLine_Term(modularContract.getC_OrderLine_Term());
		flatrateTermRecord.setModular_Flatrate_Term_ID(modulareFlatrateTermId.getRepoId());
		flatrateTermRecord.setC_UOM_ID(modularContract.getC_UOM_ID());
		flatrateTermRecord.setDeliveryRule(modularContract.getDeliveryRule());
		flatrateTermRecord.setDeliveryViaRule(modularContract.getDeliveryViaRule());
		flatrateTermRecord.setC_Currency_ID(modularContract.getC_Currency_ID());
		flatrateTermRecord.setC_Harvesting_Calendar_ID(yearAndCalendarId.calendarId().getRepoId());
		flatrateTermRecord.setHarvesting_Year_ID(yearAndCalendarId.yearId().getRepoId());

		flatrateBL.save(flatrateTermRecord);
		flatrateBL.complete(flatrateTermRecord);
	}

}
