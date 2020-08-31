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

package de.metas.rest_api.payment;

import de.metas.banking.BankAccountId;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgQuery;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.rest_api.bpartner_pricelist.BpartnerPriceListServicesFacade;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import de.metas.util.time.SystemTime;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class JsonPaymentService
{
	private final CurrencyService currencyService;
	private final BpartnerPriceListServicesFacade bpartnerPriceListServicesFacade;

	private final IBPBankAccountDAO bankAccountDAO = Services.get(IBPBankAccountDAO.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public JsonPaymentService(final CurrencyService currencyService, final BpartnerPriceListServicesFacade bpartnerPriceListServicesFacade)
	{
		this.currencyService = currencyService;
		this.bpartnerPriceListServicesFacade = bpartnerPriceListServicesFacade;
	}

	public ResponseEntity<String> createInboundPaymentFromJson(@NonNull @RequestBody final JsonInboundPaymentInfo jsonInboundPaymentInfo)
	{
		final LocalDate dateTrx = CoalesceUtil.coalesce(jsonInboundPaymentInfo.getTransactionDate(), SystemTime.asLocalDate());

		final CurrencyId currencyId = currencyService.getCurrencyId(jsonInboundPaymentInfo.getCurrencyCode());
		if (currencyId == null)
		{
			return ResponseEntity.unprocessableEntity().body("Wrong currency: " + jsonInboundPaymentInfo.getCurrencyCode());
		}

		final OrgId orgId = retrieveOrg(jsonInboundPaymentInfo);
		if (!orgId.isRegular())
		{
			return ResponseEntity.unprocessableEntity().body("Cannot find the orgId from either orgCode=" + jsonInboundPaymentInfo.getOrgCode() + " or the current user's context.");
		}

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

		final Optional<BPartnerId> bPartnerIdOptional = retrieveBPartnerId(IdentifierString.of(jsonInboundPaymentInfo.getBpartnerIdentifier()));
		if (!bPartnerIdOptional.isPresent())
		{
			return ResponseEntity.unprocessableEntity().body("Cannot find bpartner: " + jsonInboundPaymentInfo.getBpartnerIdentifier());
		}
		final BPartnerId bPartnerId = bPartnerIdOptional.get();

		final I_C_Payment payment = paymentBL.newInboundReceiptBuilder()
				.bpartnerId(bPartnerId)
				.payAmt(jsonInboundPaymentInfo.getAmount())
				.currencyId(currencyId)
				.orgBankAccountId(bankAccountIdOptional.get())
				.adOrgId(orgId)
				.tenderType(TenderType.DirectDeposit)
				.dateAcct(dateTrx)
				.dateTrx(dateTrx)
				.createAndProcess();

		final String externalOrderId = jsonInboundPaymentInfo.getExternalOrderId();
		payment.setExternalOrderId(externalOrderId);
		payment.setIsAutoAllocateAvailableAmt(true);
		InterfaceWrapperHelper.save(payment);

		return ResponseEntity.ok().build();
	}

	private OrgId retrieveOrg(@RequestBody @NonNull final JsonInboundPaymentInfo jsonInboundPaymentInfo)
	{
		final Optional<OrgId> orgId;
		if (Check.isNotBlank(jsonInboundPaymentInfo.getOrgCode()))
		{
			final OrgQuery query = OrgQuery.builder()
					.orgValue(jsonInboundPaymentInfo.getOrgCode())
					.build();
			orgId = orgDAO.retrieveOrgIdBy(query);
		}
		else
		{
			orgId = Optional.empty();
		}
		return orgId.orElse(Env.getOrgId());
	}

	private Optional<BPartnerId> retrieveBPartnerId(final IdentifierString bPartnerIdentifierString)
	{
		// TODO it would be nice here if we would also use orgID when searching for the bpartner, since an ExternalId may belong to multiple users from different orgs.
		return bpartnerPriceListServicesFacade.getBPartnerId(bPartnerIdentifierString);
	}
}
