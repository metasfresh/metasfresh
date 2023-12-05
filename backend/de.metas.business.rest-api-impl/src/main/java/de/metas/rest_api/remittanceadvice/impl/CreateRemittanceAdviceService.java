/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.remittanceadvice.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v1.remittanceadvice.JsonCreateRemittanceAdviceRequest;
import de.metas.common.rest_api.v1.remittanceadvice.JsonCreateRemittanceAdviceResponse;
import de.metas.common.rest_api.v1.remittanceadvice.JsonCreateRemittanceAdviceResponseItem;
import de.metas.common.rest_api.v1.remittanceadvice.JsonRemittanceAdvice;
import de.metas.common.rest_api.v1.remittanceadvice.JsonRemittanceAdviceLine;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgQuery;
import de.metas.remittanceadvice.CreateRemittanceAdviceLineRequest;
import de.metas.remittanceadvice.CreateRemittanceAdviceRequest;
import de.metas.remittanceadvice.RemittanceAdvice;
import de.metas.remittanceadvice.RemittanceAdviceId;
import de.metas.remittanceadvice.RemittanceAdviceRepository;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CreateRemittanceAdviceService
{
	private static final transient Logger logger = LogManager.getLogger(CreateRemittanceAdviceService.class);

	private final CurrencyRepository currencyRepository;
	private final RemittanceAdviceRepository remittanceAdviceRepository;

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IBPBankAccountDAO bankAccountDAO = Services.get(IBPBankAccountDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public CreateRemittanceAdviceService(
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final RemittanceAdviceRepository remittanceAdviceRepository)
	{
		this.currencyRepository = currencyRepository;
		this.remittanceAdviceRepository = remittanceAdviceRepository;
	}

	@Nullable
	public JsonCreateRemittanceAdviceResponse createRemittanceAdviceList(@NonNull final JsonCreateRemittanceAdviceRequest request)
	{
		return trxManager.callInNewTrx(() -> createRemittanceInTrx(request));
	}

	private JsonCreateRemittanceAdviceResponse createRemittanceInTrx(@NonNull final JsonCreateRemittanceAdviceRequest request)
	{
		final ImmutableList.Builder<RemittanceAdviceId> remittanceAdviceIdList = ImmutableList.builder();

		try
		{
			final List<JsonRemittanceAdvice> jsonRemittanceAdviceList = request.getRemittanceAdviceList();
			for (final JsonRemittanceAdvice jsonRemittanceAdvice : jsonRemittanceAdviceList)
			{
				final CreateRemittanceAdviceRequest remittanceAdviceReq = buildRemittanceAdviceRequest(jsonRemittanceAdvice);

				final RemittanceAdvice remittanceAdvice = remittanceAdviceRepository.createRemittanceAdviceHeader(remittanceAdviceReq);
				final RemittanceAdviceId remittanceAdviceId = remittanceAdvice.getRemittanceAdviceId();
				remittanceAdviceIdList.add(remittanceAdviceId);

				final List<JsonRemittanceAdviceLine> jsonRemittanceAdviceLines = jsonRemittanceAdvice.getLines();
				for (final JsonRemittanceAdviceLine jsonRemittanceAdviceLine : jsonRemittanceAdviceLines)
				{
					final CreateRemittanceAdviceLineRequest createRemittanceAdviceLineReq = buildRemittanceAdviceLineRequest(
							jsonRemittanceAdviceLine, remittanceAdviceId, remittanceAdvice.getOrgId());

					remittanceAdviceRepository.createRemittanceAdviceLine(createRemittanceAdviceLineReq);
				}
			}

			final ImmutableList.Builder<JsonCreateRemittanceAdviceResponseItem> ids = ImmutableList.builder();
			for (final RemittanceAdviceId remittanceAdviceId : remittanceAdviceIdList.build())
			{
				final JsonMetasfreshId jsonMetasfreshId = JsonMetasfreshId.of(remittanceAdviceId.getRepoId());
				final JsonCreateRemittanceAdviceResponseItem item = JsonCreateRemittanceAdviceResponseItem.builder()
						.remittanceAdviceId(jsonMetasfreshId).build();
				ids.add(item);
			}

			return JsonCreateRemittanceAdviceResponse.builder()
					.ids(ids.build())
					.build();
		}
		catch (final Exception e)
		{
			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(e);
			logger.error("Could not save the given model; message={}; AD_Issue_ID={}", e.getLocalizedMessage(), issueId);
			throw e;
		}
	}

	private CreateRemittanceAdviceRequest buildRemittanceAdviceRequest(@NonNull final JsonRemittanceAdvice jsonRemittanceAdvice)
	{
		final ClientId clientId = Env.getClientId();

		final BPartnerId sourceBPartnerId = getBPartnerId(IdentifierString.of(jsonRemittanceAdvice.getSenderId()));
		final BPartnerId destinationBPartnerId = getBPartnerId(IdentifierString.of(jsonRemittanceAdvice.getRecipientId()));

		final OrgId orgId;

		if (jsonRemittanceAdvice.getOrgCode() == null)
		{
			orgId = jsonRemittanceAdvice.getRemittanceAdviceType().isInbound()
					? retrieveOrgIdByBPartnerId(sourceBPartnerId)
					: retrieveOrgIdByBPartnerId(destinationBPartnerId);
		}
		else
		{
			orgId = retrieveOrgId(jsonRemittanceAdvice.getOrgCode());
		}

		final BPartnerBankAccountId sourceBPartnerBankAccountId = getBPartnerBankAccountId(sourceBPartnerId);
		final BPartnerBankAccountId destinationBPartnerBankAccountId = getBPartnerBankAccountId(destinationBPartnerId);

		final CurrencyId remittedAmountCurrencyId = getCurrencyIdByCurrencyISO(jsonRemittanceAdvice.getRemittanceAmountCurrencyISO());

		CurrencyId serviceFeeCurrencyId = null;
		final BigDecimal serviceFeeAmt = jsonRemittanceAdvice.getServiceFeeAmount();
		if (serviceFeeAmt.compareTo(BigDecimal.ZERO) > 0)
		{
			final String serviceFeeCurrencyISO = jsonRemittanceAdvice.getServiceFeeCurrencyISO();
			if (serviceFeeCurrencyISO == null)
			{
				throw new AdempiereException("Missing service fee currency ISO for service fee amount!")
						.appendParametersToMessage()
						.setParameter("serviceFeeAmount", jsonRemittanceAdvice.getServiceFeeAmount());
			}

			serviceFeeCurrencyId = getCurrencyIdByCurrencyISO(jsonRemittanceAdvice.getServiceFeeCurrencyISO());
		}

		final DocTypeId targetPaymentDocTypeId = getDocTypeIdByType(DocBaseType.ofCode(jsonRemittanceAdvice.getRemittanceAdviceType().getDocBaseType()), clientId, orgId);
		final DocTypeId remittanceDocTypeId = getDocTypeIdByType(DocBaseType.RemittanceAdvice, clientId, orgId);
		final String remittanceDocNumber = buildDocumentNo(remittanceDocTypeId);

		final Instant sendDate = jsonRemittanceAdvice.getSendDate() != null ? Instant.parse(jsonRemittanceAdvice.getSendDate()) : null;
		final Instant documentDate = jsonRemittanceAdvice.getDocumentDate() != null ?
				Instant.parse(jsonRemittanceAdvice.getDocumentDate()) :
				SystemTime.asInstant();

		return CreateRemittanceAdviceRequest.builder()
				.isImported(Boolean.TRUE)
				.orgId(orgId)
				.clientId(clientId)
				.docTypeId(remittanceDocTypeId)
				.documentNumber(remittanceDocNumber)
				.sourceBPartnerId(sourceBPartnerId)
				.sourceBPartnerBankAccountId(sourceBPartnerBankAccountId)
				.destinationBPartnerId(destinationBPartnerId)
				.destinationBPartnerBankAccountId(destinationBPartnerBankAccountId)
				.externalDocumentNumber(jsonRemittanceAdvice.getDocumentNumber())
				.sendDate(sendDate)
				.documentDate(documentDate)
				.targetPaymentDocTypeId(targetPaymentDocTypeId)
				.remittedAmountSum(jsonRemittanceAdvice.getRemittedAmountSum())
				.remittedAmountCurrencyId(remittedAmountCurrencyId)
				.serviceFeeAmount(jsonRemittanceAdvice.getServiceFeeAmount())
				.serviceFeeCurrencyId(serviceFeeCurrencyId)
				.paymentDiscountAmountSum(jsonRemittanceAdvice.getPaymentDiscountAmountSum())
				.additionalNotes(jsonRemittanceAdvice.getAdditionalNotes())
				.build();
	}

	private CreateRemittanceAdviceLineRequest buildRemittanceAdviceLineRequest(
			@NonNull final JsonRemittanceAdviceLine jsonRemittanceAdviceLine,
			@NonNull final RemittanceAdviceId remittanceAdviceId,
			@NonNull final OrgId orgId)
	{
		final BPartnerId bPartnerId = Check.isEmpty(jsonRemittanceAdviceLine.getBpartnerIdentifier())
				? null
				: getOptionalBPartnerId(IdentifierString.of(jsonRemittanceAdviceLine.getBpartnerIdentifier())).orElse(null);

		final Instant dateInvoiced = jsonRemittanceAdviceLine.getDateInvoiced() != null
				? Instant.parse(jsonRemittanceAdviceLine.getDateInvoiced())
				: null;

		return CreateRemittanceAdviceLineRequest.builder()
				.remittanceAdviceId(remittanceAdviceId)
				.orgId(orgId)
				.invoiceIdentifier(jsonRemittanceAdviceLine.getInvoiceIdentifier())
				.remittedAmount(jsonRemittanceAdviceLine.getRemittedAmount())
				.dateInvoiced(dateInvoiced)
				.bpartnerIdentifier(bPartnerId)
				.externalInvoiceDocBaseType(jsonRemittanceAdviceLine.getInvoiceBaseDocType())
				.invoiceGrossAmount(jsonRemittanceAdviceLine.getInvoiceGrossAmount())
				.paymentDiscountAmount(jsonRemittanceAdviceLine.getPaymentDiscountAmount())
				.serviceFeeAmount(jsonRemittanceAdviceLine.getServiceFeeAmount())
				.serviceFeeVatRate(jsonRemittanceAdviceLine.getServiceFeeVatRate())
				.build();
	}

	private OrgId retrieveOrgId(@NonNull final String orgCode)
	{
		final Optional<OrgId> orgId;
		final OrgQuery query = OrgQuery.builder()
				.orgValue(orgCode)
				.build();
		orgId = orgDAO.retrieveOrgIdBy(query);

		return orgId.orElse(Env.getOrgId());
	}

	private OrgId retrieveOrgIdByBPartnerId(@NonNull final BPartnerId bPartnerId)
	{
		final I_C_BPartner bPartner = bPartnerDAO.getById(bPartnerId);
		return OrgId.ofRepoId(bPartner.getAD_Org_ID());
	}

	@NonNull
	private BPartnerId getBPartnerId(final IdentifierString identifierString)
	{
		final Optional<BPartnerId> optionalBPartnerId = getOptionalBPartnerId(identifierString);

		return optionalBPartnerId.orElseThrow(() -> new AdempiereException("No BPartner found for IdentifierString")
				.appendParametersToMessage().setParameter("IdentifierString", identifierString));
	}

	@NonNull
	private Optional<BPartnerId> getOptionalBPartnerId(@NonNull final IdentifierString identifierString)
	{
		final BPartnerQuery query = buildBPartnerQuery(identifierString);
		return bPartnerDAO.retrieveBPartnerIdBy(query);
	}

	@NonNull
	private CurrencyId getCurrencyIdByCurrencyISO(@NonNull final String currencyISO)
	{
		final CurrencyCode convertedToCurrencyCode = CurrencyCode.ofThreeLetterCode(currencyISO);
		return currencyRepository.getCurrencyIdByCurrencyCode(convertedToCurrencyCode);
	}

	@NonNull
	private BPartnerBankAccountId getBPartnerBankAccountId(@NonNull final BPartnerId bPartnerId)
	{
		final I_C_BP_BankAccount sourceBPartnerBankAccount = bankAccountDAO.retrieveDefaultBankAccountInTrx(bPartnerId)
				.orElseThrow(() -> new AdempiereException("No BPartnerBankAccount found for BPartnerId")
						.appendParametersToMessage()
						.setParameter("BPartnerId", bPartnerId));
		return BPartnerBankAccountId.ofRepoId(BPartnerId.toRepoId(bPartnerId), sourceBPartnerBankAccount.getC_BP_BankAccount_ID());
	}

	@NonNull
	private DocTypeId getDocTypeIdByType(
			@NonNull final DocBaseType type,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(type)
				.adClientId(clientId.getRepoId())
				.adOrgId(orgId.getRepoId())
				.build();
		return docTypeDAO.getDocTypeId(docTypeQuery);
	}

	private BPartnerQuery buildBPartnerQuery(@NonNull final IdentifierString bpartnerIdentifier)
	{
		final IdentifierString.Type type = bpartnerIdentifier.getType();
		final BPartnerQuery query;

		switch (type)
		{
			case METASFRESH_ID:
				query = BPartnerQuery.builder()
						.bPartnerId(bpartnerIdentifier.asMetasfreshId(BPartnerId::ofRepoId))
						.build();
				break;
			case EXTERNAL_ID:
				query = BPartnerQuery.builder()
						.externalId(bpartnerIdentifier.asExternalId())
						.build();
				break;
			case GLN:
				query = BPartnerQuery.builder()
						.gln(bpartnerIdentifier.asGLN())
						.build();
				break;
			case VALUE:
				query = BPartnerQuery.builder()
						.bpartnerValue(bpartnerIdentifier.asValue())
						.build();
				break;
			default:
				throw new AdempiereException("Invalid bpartnerIdentifier: " + bpartnerIdentifier);
		}
		return query;
	}

	@VisibleForTesting
	public String buildDocumentNo(@NonNull final DocTypeId docTypeId)
	{
		final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);

		final String documentNo = documentNoFactory.forDocType(docTypeId.getRepoId(), /* useDefiniteSequence */false)
				.setClientId(Env.getClientId())
				.setFailOnError(true)
				.build();

		if (documentNo == null)
		{
			throw new AdempiereException("Cannot fetch documentNo for " + docTypeId);
		}

		return documentNo;
	}
}
