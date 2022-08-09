/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contacts.invoice.interim.command;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contacts.invoice.interim.InterimInvoiceOverview;
import de.metas.contacts.invoice.interim.InterimInvoiceSettings;
import de.metas.contacts.invoice.interim.InterimInvoiceSettingsId;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceOverviewDAO;
import de.metas.contacts.invoice.interim.service.IInterimInvoiceSettingsDAO;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.process.FlatrateTermCreator;
import de.metas.inout.InOutDocStatus;
import de.metas.inout.InOutLine;
import de.metas.inout.InOutLineQuery;
import de.metas.inout.impl.InOutDAO;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.GREATER_OR_EQUAL;
import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.LESS_OR_EQUAL;

@Value

public class InterimInvoiceFlatrateTermCreateCommand
{
	@Getter(AccessLevel.PRIVATE)
	Properties ctx;

	@Getter(AccessLevel.PRIVATE)
	BPartnerId bpartnerId;

	@Getter(AccessLevel.PRIVATE)
	ConditionsId conditionsId;

	@Getter(AccessLevel.PRIVATE)
	ProductId productId;

	@Getter(AccessLevel.PRIVATE)
	Timestamp dateFrom;

	@Getter(AccessLevel.PRIVATE)
	Timestamp dateTo;

	@Getter(AccessLevel.PRIVATE)
	I_C_Flatrate_Conditions conditions;
	@Getter(AccessLevel.PRIVATE)
	InterimInvoiceSettings interimInvoiceSettings;
	@Getter(AccessLevel.PRIVATE)
	I_M_Product product;

	ITrxManager trxManager = Services.get(ITrxManager.class);
	IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	IProductDAO productDAO = Services.get(IProductDAO.class);
	InOutDAO inOutDAO = Services.get(InOutDAO.class);
	IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	IInterimInvoiceSettingsDAO interimInvoiceSettingsDAO = Services.get(IInterimInvoiceSettingsDAO.class);
	IInterimInvoiceOverviewDAO interimInvoiceOverviewDAO = Services.get(IInterimInvoiceOverviewDAO.class);
	OrderLineRepository orderLineRepository = SpringContextHolder.instance.getBean(OrderLineRepository.class);
	IQueryBL queryBL = Services.get(IQueryBL.class);

	@Builder
	public InterimInvoiceFlatrateTermCreateCommand(final Properties ctx, final BPartnerId bpartnerId, final ConditionsId conditionsId, final ProductId productId, final Timestamp dateFrom, final Timestamp dateTo)
	{
		this.ctx = ctx;
		this.bpartnerId = bpartnerId;
		this.conditionsId = conditionsId;
		this.productId = productId;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.conditions = flatrateDAO.getConditionsById(conditionsId);
		this.interimInvoiceSettings = interimInvoiceSettingsDAO.getById(InterimInvoiceSettingsId.ofRepoId(conditions.getC_Interim_Invoice_Settings_ID()));
		this.product = productDAO.getById(productId);
	}

	public void execute()
	{
		trxManager.runInThreadInheritedTrx(this::createInterimInvoiceFlatrateTerm);
	}

	private void createInterimInvoiceFlatrateTerm()
	{
		final I_C_Flatrate_Term flatrateTerm = createFlatrateTerm();

		final InterimInvoiceOverview interimInvoiceOverview = createInterimInvoiceOverview(flatrateTerm);

		final Collection<InOutLine> matchingInOuts = getMatchingInOuts();

	}

	private I_C_Flatrate_Term createFlatrateTerm()
	{
		final I_C_BPartner bpartner = bpartnerDAO.getById(bpartnerId);

		Check.assumeEquals(conditions.getType_Conditions(), X_C_Flatrate_Conditions.TYPE_CONDITIONS_InterimInvoice);

		return FlatrateTermCreator.builder()
				.bPartners(Collections.singleton(bpartner))
				.conditions(conditions)
				.ctx(ctx)
				.product(product)
				.startDate(dateFrom)
				.endDate(dateTo)
				.isCompleteDocument(true)
				.build()
				.createTermsForBPartners()
				.stream()
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No flatrate term created"));
	}

	private InterimInvoiceOverview createInterimInvoiceOverview(final I_C_Flatrate_Term flatrateTerm)
	{
		final InterimInvoiceOverview interimInvoiceOverview = InterimInvoiceOverview.builder()
				.flatrateTermId(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.calendarId(interimInvoiceSettings.getHarvestingCalendarId())
				.orderLineId(OrderLineId.ofRepoId(0))   // where to take this. Also, move it to Line level???
				.qtyOrdered(BigDecimal.ZERO) // to make virtual ??
				.qtyDelivered(BigDecimal.ZERO) // to make virtual ??
				.qtyInvoiced(BigDecimal.ZERO) // to make virtual ??
				.productId(productId)
				.uomId(UomId.ofRepoId(product.getC_UOM_ID())) // to make virtual, based on M_Product.C_UOM_ID ??

				.withholdingInvoiceCandidateId(null) //TODO
				.partialPaymentInvoiceCandidateId(null) //TODO
				.priceActual(BigDecimal.ZERO) // dependent on orderLine price. How to calc if orderLine is moved at line level ??
				.currencyId(null) // get from orderLine if it does not move. How to calc if orderLine is moved at line level ??
				.build();
		return interimInvoiceOverviewDAO.save(interimInvoiceOverview);
	}

	private Collection<InOutLine> getMatchingInOuts()
	{
		return inOutDAO.retrieveInOutLinesBy(InOutLineQuery.builder()
				.bPartnerId(bpartnerId)
				.productId(productId)
				.dateFrom(dateFrom)
				.dateTo(dateTo)
				.clientandOrgId(Env.getClientAndOrgId(ctx))
				.docStatuses(ImmutableList.of(InOutDocStatus.Closed, InOutDocStatus.Completed))
				.build());
	}

	private Collection<OrderLine> getOrderLines()
	{
		return queryBL.createQueryBuilder(I_C_Order.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addCompareFilter(I_C_Order.COLUMNNAME_DatePromised, GREATER_OR_EQUAL, dateFrom)
				.addCompareFilter(I_C_Order.COLUMNNAME_DatePromised, LESS_OR_EQUAL, dateTo)
				.addEqualsFilter(I_C_Order.COLUMNNAME_M_PricingSystem_ID, conditions.getM_PricingSystem_ID())
				.addInArrayFilter(I_C_Order.COLUMNNAME_DocStatus, X_C_Order.DOCACTION_Complete, X_C_Order.DOCACTION_Close)
				.orderByDescending(I_C_Order.COLUMNNAME_Created)
				.setLimit(QueryLimit.ONE)
				.andCollectChildren(I_C_OrderLine.COLUMNNAME_C_Order_ID, I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_M_Product_ID, productId)
				.create()
				.stream()
				.map(orderLineRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}
}
