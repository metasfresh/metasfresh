/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.order.sales;

import ch.qos.logback.classic.Level;
import de.metas.RestUtils;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.common.rest_api.v2.order.JsonOrderPaymentCreateRequest;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderQuery;
import de.metas.organization.OrgId;
import de.metas.payment.TenderType;
import de.metas.payment.api.DefaultPaymentBuilder;
import de.metas.payment.api.IPaymentDAO;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.payment.PaymentService;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OrderService
{
	private final static Logger logger = LogManager.getLogger(OrderService.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);

	private final CurrencyService currencyService;
	private final JsonRetrieverService jsonRetrieverService;
	private final PaymentService paymentService;

	public OrderService(
			final JsonServiceFactory jsonServiceFactory,
			final CurrencyService currencyService,
			final PaymentService paymentService)
	{
		this.currencyService = currencyService;
		this.jsonRetrieverService = jsonServiceFactory.createRetriever();
		this.paymentService = paymentService;
	}

	public void createOrderPayment(@NonNull final JsonOrderPaymentCreateRequest request)
	{
		final LocalDate dateTrx = CoalesceUtil.coalesce(request.getTransactionDate(), SystemTime.asLocalDate());

		final OrgId orgId = RestUtils.retrieveOrgIdOrDefault(request.getOrgCode());
		if (!orgId.isRegular())
		{
			throw new AdempiereException("Cannot find the orgId from either orgCode=" + request.getOrgCode() + " or the current user's context.");
		}
		final ExternalId paymentExternalId = ExternalId.ofOrNull(request.getExternalPaymentId());
		if (paymentExternalId != null && paymentDAO.getByExternalId(paymentExternalId, orgId).isPresent())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Payment with AD_Ord_ID={} and ExternalId={} already exists; -> ignoring this request.",orgId.getRepoId(), paymentExternalId.getValue());
			return; // nothing to do, external payment already registered
		}

		final CurrencyId currencyId = currencyService.getCurrencyId(request.getCurrencyCode());
		if (currencyId == null)
		{
			throw new AdempiereException("Wrong currency: " + request.getCurrencyCode());
		}

		final BankAccountId targetBankAccount = paymentService.determineOrgBPartnerBankAccountId(orgId, currencyId, request.getTargetIBAN())
				.orElseThrow(() -> new AdempiereException(String.format(
						"Cannot find Bank Account for the org-bpartner of org-id: %s, currency-id: %s and iban: %s", orgId.getRepoId(), currencyId.getRepoId(), request.getTargetIBAN())));

		final ExternalIdentifier bPartnerExternalIdentifier = ExternalIdentifier.of(request.getBpartnerIdentifier());
		final BPartnerId bPartnerId = jsonRetrieverService.resolveBPartnerExternalIdentifier(bPartnerExternalIdentifier, orgId)
				.orElseThrow(() -> new AdempiereException("No BPartner could be found for the given external BPartner identifier!")
						.appendParametersToMessage()
						.setParameter("externalBPartnerIdentifier", bPartnerExternalIdentifier.getRawValue())
						.setParameter("orgId", orgId));

		trxManager.runInThreadInheritedTrx(() -> {
			final DefaultPaymentBuilder paymentBuilder = paymentService.newInboundReceiptBuilder()
					.adOrgId(orgId)
					.bpartnerId(bPartnerId)
					.payAmt(request.getAmount())
					.discountAmt(request.getDiscountAmt())
					.writeoffAmt(request.getWriteOffAmt())
					.currencyId(currencyId)
					.orgBankAccountId(targetBankAccount)
					.tenderType(TenderType.DirectDeposit)
					.dateAcct(dateTrx)
					.dateTrx(dateTrx)
					.externalId(paymentExternalId);

			final IdentifierString orderIdentifier = IdentifierString.of(request.getOrderIdentifier());

			final Optional<OrderId> orderId = resolveOrderId(orderIdentifier, orgId);
			orderId.ifPresent(paymentBuilder::orderId);

			if (!orderId.isPresent() && IdentifierString.Type.EXTERNAL_ID.equals(orderIdentifier.getType()))
			{
				paymentBuilder.orderExternalId(orderIdentifier.asExternalId().getValue());
			}
			else if (!orderId.isPresent())
			{
				throw MissingResourceException.builder()
						.resourceName("I_C_Order")
						.resourceIdentifier(orderIdentifier.getRawIdentifierString())
						.build();
			}

			paymentBuilder.createAndProcess();
		});
	}

	private Optional<OrderId> resolveOrderId(@NonNull final IdentifierString orderIdentifier, @NonNull final OrgId orgId)
	{
		final OrderQuery.OrderQueryBuilder queryBuilder = OrderQuery.builder().orgId(orgId);

		switch (orderIdentifier.getType())
		{
			case METASFRESH_ID:
				final OrderId orderId = OrderId.ofRepoId(orderIdentifier.asMetasfreshId().getValue());
				return Optional.of(orderId);
			case DOC:
				final OrderQuery getOrderByDocQuery = queryBuilder
						.documentNo(orderIdentifier.asDoc())
						.build();

				return orderDAO.retrieveByOrderCriteria(getOrderByDocQuery)
						.map(I_C_Order::getC_Order_ID)
						.map(OrderId::ofRepoId);
			case EXTERNAL_ID:
				final OrderQuery getOrderByExternalId = queryBuilder
						.externalId(orderIdentifier.asExternalId())
						.build();

				return orderDAO.retrieveByOrderCriteria(getOrderByExternalId)
						.map(I_C_Order::getC_Order_ID)
						.map(OrderId::ofRepoId);
			default:
				throw new InvalidIdentifierException("Given IdentifierString type is not supported!")
						.appendParametersToMessage()
						.setParameter("IdentifierStringType", orderIdentifier.getType())
						.setParameter("rawIdentifierString", orderIdentifier.getRawIdentifierString());
		}
	}
}
