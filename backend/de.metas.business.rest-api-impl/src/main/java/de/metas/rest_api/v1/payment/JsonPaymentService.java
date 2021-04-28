/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.rest_api.v1.payment;

import de.metas.allocation.api.C_AllocationHdr_Builder;
import de.metas.allocation.api.IAllocationBL;
import de.metas.banking.BankAccountId;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.common.rest_api.v1.payment.JsonInboundPaymentInfo;
import de.metas.common.rest_api.v1.payment.JsonPaymentAllocationLine;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocBaseAndSubType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceQuery;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderQuery;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgQuery;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.rest_api.bpartner_pricelist.BpartnerPriceListServicesFacade;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JsonPaymentService
{
	private final CurrencyService currencyService;
	private final BpartnerPriceListServicesFacade bpartnerPriceListServicesFacade;

	private final IBPBankAccountDAO bankAccountDAO = Services.get(IBPBankAccountDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public JsonPaymentService(final CurrencyService currencyService, final BpartnerPriceListServicesFacade bpartnerPriceListServicesFacade)
	{
		this.currencyService = currencyService;
		this.bpartnerPriceListServicesFacade = bpartnerPriceListServicesFacade;
	}

	public ResponseEntity<String> createInboundPaymentFromJson(@NonNull @RequestBody final JsonInboundPaymentInfo jsonInboundPaymentInfo)
	{
		final LocalDate dateTrx = CoalesceUtil.coalesce(jsonInboundPaymentInfo.getTransactionDate(), SystemTime.asLocalDate());

		final List<JsonPaymentAllocationLine> lines = jsonInboundPaymentInfo.getLines();
		if (validateAllocationLineAmounts(lines))
		{
			return ResponseEntity.unprocessableEntity().body("At least one of the following allocation amounts are mandatory in every line: amount, discountAmt, writeOffAmt");
		}

		final CurrencyId currencyId = currencyService.getCurrencyId(jsonInboundPaymentInfo.getCurrencyCode());
		if (currencyId == null)
		{
			return ResponseEntity.unprocessableEntity().body("Wrong currency: " + jsonInboundPaymentInfo.getCurrencyCode());
		}

		final OrgId orgId = retrieveOrg(jsonInboundPaymentInfo.getOrgCode());
		if (!orgId.isRegular())
		{
			return ResponseEntity.unprocessableEntity().body("Cannot find the orgId from either orgCode=" + jsonInboundPaymentInfo.getOrgCode() + " or the current user's context.");
		}

		final ExternalId externalId = ExternalId.ofOrNull(jsonInboundPaymentInfo.getExternalPaymentId());
		validatePaymentId(externalId, orgId);

		final Optional<BPartnerId> orgBPartnerIdOptional = Services.get(IBPartnerOrgBL.class).retrieveLinkedBPartnerId(orgId);
		if (!orgBPartnerIdOptional.isPresent())
		{
			return ResponseEntity.unprocessableEntity().body("Cannot find the org-bpartner linked to orgId=" + orgId + "; orgCode=" + jsonInboundPaymentInfo.getOrgCode());
		}

		final Optional<BankAccountId> bankAccountIdOptional = bankAccountDAO.retrieveByBPartnerAndCurrencyAndIBAN(orgBPartnerIdOptional.get(), currencyId, jsonInboundPaymentInfo.getTargetIBAN());
		if (!bankAccountIdOptional.isPresent())
		{
			return ResponseEntity.unprocessableEntity().body(String.format(
					"Cannot find Bank Account for org-bpartner-id: %s, currency: %s and account: %s",
					orgBPartnerIdOptional.get().getRepoId(), jsonInboundPaymentInfo.getCurrencyCode(), jsonInboundPaymentInfo.getTargetIBAN()));
		}

		final Optional<BPartnerId> bPartnerIdOptional = retrieveBPartnerId(IdentifierString.of(jsonInboundPaymentInfo.getBpartnerIdentifier()), orgId);
		if (!bPartnerIdOptional.isPresent())
		{
			return ResponseEntity.unprocessableEntity().body("Cannot find bpartner: " + jsonInboundPaymentInfo.getBpartnerIdentifier());
		}
		final BPartnerId bPartnerId = bPartnerIdOptional.get();

		trxManager.run(ITrx.TRXNAME_ThreadInherited, new TrxRunnableAdapter()
		{

			@Override
			public void run(final String localTrxName)
			{
				final I_C_Payment payment = paymentBL.newInboundReceiptBuilder()
						.bpartnerId(bPartnerId)
						.payAmt(jsonInboundPaymentInfo.getAmount())
						.discountAmt(jsonInboundPaymentInfo.getDiscountAmt())
						.writeoffAmt(jsonInboundPaymentInfo.getWriteOffAmt())
						.currencyId(currencyId)
						.orgBankAccountId(bankAccountIdOptional.get())
						.adOrgId(orgId)
						.tenderType(TenderType.DirectDeposit)
						.dateAcct(dateTrx)
						.dateTrx(dateTrx)
						.externalId(externalId)
						.createAndProcess();

				final String orderIdentifierString = jsonInboundPaymentInfo.getOrderIdentifier();
				if (!Check.isEmpty(orderIdentifierString))
				{
					final IdentifierString orderIdentifier = IdentifierString.of(orderIdentifierString);
					if (orderIdentifier.getType().equals(IdentifierString.Type.EXTERNAL_ID))
					{
						payment.setExternalOrderId(orderIdentifier.asExternalId().getValue());
					}
					else
					{
						final Optional<I_C_Order> potentialOrder = getOrderIdFromIdentifier(orderIdentifier, orgId);
						Check.assumeNotEmpty(potentialOrder, "Could not find order for identifier: " + orderIdentifierString);
						final I_C_Order order = potentialOrder.get();
						payment.setExternalOrderId(order.getExternalId());
						payment.setC_Order_ID(order.getC_Order_ID());
					}
				}
				payment.setIsAutoAllocateAvailableAmt(true);
				InterfaceWrapperHelper.save(payment);

				createAllocationsForPayment(payment, lines);
			}
		});
		return ResponseEntity.ok().build();
	}

	private void validatePaymentId(@Nullable final ExternalId externalId, @NonNull final OrgId orgId)
	{
		if (externalId != null)
		{
			Check.assumeNull(paymentBL.getByExtIdOrgId(externalId, orgId).orElse(null), "A payment with this ID already exists for this organization.");
		}
	}

	private void createAllocationsForPayment(final I_C_Payment payment, final List<JsonPaymentAllocationLine> lines)
	{
		if (Check.isEmpty(lines))
		{
			return;
		}
		final int orgId = payment.getAD_Org_ID();
		final C_AllocationHdr_Builder allocationBuilder = allocationBL.newBuilder()
				.currencyId(payment.getC_Currency_ID())
				.dateTrx(payment.getDateTrx())
				.dateAcct(payment.getDateAcct())
				.orgId(orgId);
		for (final JsonPaymentAllocationLine line : lines)
		{
			final String invoiceId = line.getInvoiceIdentifier();
			final String docBaseType = line.getDocBaseType();
			final DocBaseAndSubType docType = Check.isBlank(docBaseType) ? null : DocBaseAndSubType.of(docBaseType, line.getDocSubType());
			final Optional<InvoiceId> invoice = retrieveInvoice(IdentifierString.of(invoiceId), OrgId.ofRepoIdOrNull(orgId), docType);
			Check.assumeNotEmpty(invoice, "Cannot find invoice for identifier: " + invoiceId);
			allocationBuilder.addLine()
					.skipIfAllAmountsAreZero()
					.invoiceId(invoice.get().getRepoId())
					.orgId(orgId)
					.bpartnerId(payment.getC_BPartner_ID())
					.amount(line.getAmount())
					.discountAmt(line.getDiscountAmt())
					.writeOffAmt(line.getWriteOffAmt())
					.paymentId(payment.getC_Payment_ID())
					.lineDone();
		}
		allocationBuilder.createAndComplete();
	}

	/**
	 * @param lines list of {@code JsonPaymentAllocationLine} to check
	 * @return true if any line in {@code lines} has no valid amounts, false otherwise
	 */
	private boolean validateAllocationLineAmounts(@Nullable final List<JsonPaymentAllocationLine> lines)
	{
		return !Check.isEmpty(lines) && lines.stream().anyMatch(line -> Check.isEmpty(line.getAmount()));
	}

	private OrgId retrieveOrg(@Nullable final String orgCode)
	{
		final Optional<OrgId> orgId;
		if (Check.isNotBlank(orgCode))
		{
			final OrgQuery query = OrgQuery.builder()
					.orgValue(orgCode)
					.build();
			orgId = orgDAO.retrieveOrgIdBy(query);
		}
		else
		{
			orgId = Optional.empty();
		}
		return orgId.orElse(Env.getOrgId());
	}

	private Optional<BPartnerId> retrieveBPartnerId(final IdentifierString bPartnerIdentifierString, final OrgId orgId)
	{
		return bpartnerPriceListServicesFacade.getBPartnerId(bPartnerIdentifierString, orgId);
	}

	private Optional<InvoiceId> retrieveInvoice(final IdentifierString invoiceIdentifier, final OrgId orgId, final DocBaseAndSubType docType)
	{
		final InvoiceQuery invoiceQuery = createInvoiceQuery(invoiceIdentifier).docType(docType).orgId(orgId).build();
		return invoiceDAO.retrieveIdByInvoiceQuery(invoiceQuery);
	}

	private static InvoiceQuery.InvoiceQueryBuilder createInvoiceQuery(@NonNull final IdentifierString identifierString)
	{
		final IdentifierString.Type type = identifierString.getType();
		if (IdentifierString.Type.METASFRESH_ID.equals(type))
		{
			return InvoiceQuery.builder().invoiceId(MetasfreshId.toValue(identifierString.asMetasfreshId()));
		}
		else if (IdentifierString.Type.EXTERNAL_ID.equals(type))
		{
			return InvoiceQuery.builder().externalId(identifierString.asExternalId());
		}
		else if (IdentifierString.Type.DOC.equals(type))
		{
			return InvoiceQuery.builder().documentNo(identifierString.asDoc());
		}
		else
		{
			throw new AdempiereException("Invalid identifierString: " + identifierString);
		}
	}

	private Optional<I_C_Order> getOrderIdFromIdentifier(final IdentifierString orderIdentifier, final OrgId orgId)
	{
		return orderDAO.retrieveByOrderCriteria(createOrderQuery(orderIdentifier, orgId));
	}

	private OrderQuery createOrderQuery(final IdentifierString identifierString, final OrgId orgId)
	{
		final IdentifierString.Type type = identifierString.getType();
		final OrderQuery.OrderQueryBuilder builder = OrderQuery.builder().orgId(orgId);
		if (IdentifierString.Type.METASFRESH_ID.equals(type))
		{
			return builder
					.orderId(MetasfreshId.toValue(identifierString.asMetasfreshId()))
					.build();
		}
		else if (IdentifierString.Type.EXTERNAL_ID.equals(type))
		{
			return builder
					.externalId(identifierString.asExternalId())
					.build();
		}
		else if (IdentifierString.Type.DOC.equals(type))
		{
			return builder
					.documentNo(identifierString.asDoc())
					.build();
		}
		else
		{
			throw new AdempiereException("Invalid identifierString: " + identifierString);
		}
	}
}
