package de.metas.rest_api.invoicecandidates.impl;

import static de.metas.util.Check.isEmpty;
import static de.metas.util.Check.newException;
import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.util.Map.Entry;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.service.BPartnerInfo.BPartnerInfoBuilder;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.externallyreferenced.ExternallyReferencedCandidate;
import de.metas.invoicecandidate.externallyreferenced.ExternallyReferencedCandidateRepository;
import de.metas.invoicecandidate.externallyreferenced.InvoiceCandidateLookupKey;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgIdNotFoundException;
import de.metas.organization.OrgQuery;
import de.metas.rest_api.JsonDocTypeInfo;
import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.SyncAdvise;
import de.metas.rest_api.invoicecandidates.request.JsonOverride;
import de.metas.rest_api.invoicecandidates.request.JsonRequestInvoiceCandidateUpsert;
import de.metas.rest_api.invoicecandidates.request.JsonRequestInvoiceCandidateUpsertItem;
import de.metas.rest_api.invoicecandidates.response.JsonResponseInvoiceCandidateUpsert;
import de.metas.rest_api.invoicecandidates.response.JsonResponseInvoiceCandidateUpsert.JsonResponseInvoiceCandidateUpsertBuilder;
import de.metas.rest_api.invoicecandidates.response.JsonResponseInvoiceCandidateUpsertItem;
import de.metas.rest_api.invoicecandidates.response.JsonResponseInvoiceCandidateUpsertItem.Action;
import de.metas.rest_api.utils.BPartnerCompositeLookupKey;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.utils.DocTypeService;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.InvalidEntityException;
import de.metas.rest_api.utils.JsonExternalIds;
import de.metas.rest_api.utils.MissingPropertyException;
import de.metas.rest_api.utils.MissingResourceException;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;

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

@Service
public class JsonInvoiceCandidateUpsertService
{

	// private final transient IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final BPartnerQueryService bPartnerQueryService;
	private final BPartnerCompositeRepository bpartnerCompositeRepository;

	private final ExternallyReferencedCandidateRepository externallyReferencedCandidateRepository;
	private final DocTypeService docTypeService;
	private final CurrencyService currencyService;

	private JsonInvoiceCandidateUpsertService(
			@NonNull final BPartnerQueryService bPartnerQueryService,
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository,
			@NonNull final DocTypeService docTypeService,
			@NonNull final CurrencyService currencyService,
			@NonNull final ExternallyReferencedCandidateRepository externallyReferencedCandidateRepository)
	{
		this.bPartnerQueryService = bPartnerQueryService;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
		this.currencyService = currencyService;
		this.docTypeService = docTypeService;
		this.externallyReferencedCandidateRepository = externallyReferencedCandidateRepository;
	}

	public JsonResponseInvoiceCandidateUpsert createOrUpdateInvoiceCandidates(@NonNull final JsonRequestInvoiceCandidateUpsert request)
	{
		final ImmutableMap<InvoiceCandidateLookupKey, JsonRequestInvoiceCandidateUpsertItem> lookupKey2Item = Maps.uniqueIndex(request.getRequestItems(), this::createInvoiceCandidateLookupKey);

		// candidates
		final ImmutableMap<InvoiceCandidateLookupKey, Optional<ExternallyReferencedCandidate>> //
		key2Candidate = externallyReferencedCandidateRepository.getAllBy(lookupKey2Item.keySet());

		final ImmutableSet<Entry<InvoiceCandidateLookupKey, JsonRequestInvoiceCandidateUpsertItem>> entrySet = lookupKey2Item.entrySet();

		final ImmutableList.Builder<ExternallyReferencedCandidate> candidatesToSave = ImmutableList.builder();
		for (final Entry<InvoiceCandidateLookupKey, JsonRequestInvoiceCandidateUpsertItem> keyWithItem : lookupKey2Item.entrySet())
		{
			ExternallyReferencedCandidate candidate;
			final Optional<ExternallyReferencedCandidate> candidateOpt = key2Candidate.get(keyWithItem.getKey());
			if (candidateOpt.isPresent())
			{
				candidate = candidateOpt.get();
			}
			else
			{
				if (request.getSyncAdvise().isFailIfNotExists())
				{
					throw new MissingResourceException(JsonRequestInvoiceCandidateUpsertItem.class.getSimpleName(), request);
				}
				candidate = new ExternallyReferencedCandidate();
			}

			final JsonRequestInvoiceCandidateUpsertItem item = keyWithItem.getValue();
			final SyncAdvise effectiveSyncAdvise = coalesce(item.getSynchAdvise(), request.getSyncAdvise());

			if (!candidate.isNew() && !effectiveSyncAdvise.getIfExists().isUpdate())
			{
				continue; // candidate existed and we shall not update it
			}

			if (!Check.isEmpty(item.getOrgCode()))
			{
				final OrgQuery query = OrgQuery.builder()
						.orgValue(item.getOrgCode())
						.failIfNotExists(true)
						.outOfTrx(true)
						.build();
				try
				{
					final OrgId orgId = Services.get(IOrgDAO.class)
							.retrieveOrgIdBy(query)
							.get();
					if (!candidate.isNew() && !candidate.getOrgId().equals(orgId))
					{
						throw new InvalidEntityException(TranslatableStrings.constant("Request entity has orgId=" + item.getOrgCode() + ", but existing candidate has orgId=" + orgId + ""))
						.appendParametersToMessage()						;
					}
				}
				catch (OrgIdNotFoundException e)
				{
					throw new MissingResourceException("orgCode", item, e);
				}
			}
			else
			{
				final OrgId orgId = Env.getOrgId(Env.getCtx());
			}

			syncBPartnerToCandidate(candidate, item, effectiveSyncAdvise);

			syncTargetDocTypeToCandidate(candidate, item.getInvoiceDocType(), effectiveSyncAdvise);

			syncDiscountOverrideToCandidate(candidate, item.getDiscountOverride(), effectiveSyncAdvise);
			syncPriceEnteredOverrideToCandidate(candidate, item.getPriceEnteredOverride(), effectiveSyncAdvise);

			// syncAllTheRemainingTrivialStuffToCandidate(candidate, item, effectiveSyncAdvise);

		}

		final JsonResponseInvoiceCandidateUpsertBuilder result = JsonResponseInvoiceCandidateUpsert.builder();

		for (final ExternallyReferencedCandidate candidateToSave : candidatesToSave.build())
		{
			final Action action = candidateToSave.isNew() ? Action.INSERTED : Action.UPDATED;
			final JsonExternalId headerId = JsonExternalIds.ofOrNull(candidateToSave.getLookupKey().getExternalHeaderId());
			final JsonExternalId lineId = JsonExternalIds.ofOrNull(candidateToSave.getLookupKey().getExternalLineId());

			final InvoiceCandidateId candidateId = externallyReferencedCandidateRepository.save(candidateToSave);

			final JsonResponseInvoiceCandidateUpsertItem responseItem = JsonResponseInvoiceCandidateUpsertItem.builder()
					.action(action)
					.externalHeaderId(headerId)
					.externalLineId(lineId)
					.metasfreshId(MetasfreshId.of(candidateId))
					.build();
			result.responseItem(responseItem);
		}

		return result.build();
	}

	private void syncTargetDocTypeToCandidate(
			@NonNull final ExternallyReferencedCandidate candidate,
			@Nullable final JsonDocTypeInfo docType,
			@NonNull final OrgId orgId,
			@NonNull final SyncAdvise effectiveSyncAdvise)
	{
		if (docType == null)
		{
			if (effectiveSyncAdvise.getIfExists().isUpdateRemove())
			{
				candidate.setInvoiceDocTypeId(null);
			}
			return;
		}

		candidate.setInvoiceDocTypeId(docTypeService.getDocTypeId(docType, orgId));
	}

	private void syncBPartnerToCandidate(
			@NonNull final ExternallyReferencedCandidate candidate,
			@NonNull final JsonRequestInvoiceCandidateUpsertItem item,
			@NonNull final SyncAdvise effectiveSyncAdvise)
	{
		final BPartnerQuery query;
		final boolean noBPartnerIdentifier = isEmpty(item.getBillPartnerIdentifier(), true);

		final BPartnerInfoBuilder bpartnerInfo = candidate.getBillPartnerInfo() == null
				? BPartnerInfo.builder()
				: candidate.getBillPartnerInfo().toBuilder();

		if (noBPartnerIdentifier)
		{
			if (effectiveSyncAdvise.getIfExists().isUpdateRemove() // the advise wants us to unset the bill-partner, but we can't do that
					|| candidate.isNew() // we can't create a new candidate without a bill-partner
			)
			{
				throw new MissingPropertyException("billPartnerIdentifier", item);
			}

			final boolean candidateAlreadyHasBPartner = candidate.getBillPartnerInfo() != null && candidate.getBillPartnerInfo().getBpartnerId() != null;
			if (!candidateAlreadyHasBPartner)
			{
				return;
			}
			query = BPartnerQuery.builder().bPartnerId(candidate.getBillPartnerInfo().getBpartnerId()).build();
		}
		else
		{
			final IdentifierString bpartnerIdentifier = IdentifierString.of(item.getBillPartnerIdentifier());
			final BPartnerCompositeLookupKey bpartnerIdLookupKey = BPartnerCompositeLookupKey.ofIdentifierString(bpartnerIdentifier);
			query = bPartnerQueryService.createQueryFailIfNotExists(bpartnerIdLookupKey);
		}

		final BPartnerComposite bpartnerComposite;
		try
		{
			bpartnerComposite = bpartnerCompositeRepository.getSingleByQuery(query).get();
		}
		catch (final AdempiereException e)
		{
			throw new MissingResourceException("billPartnerIdentifier", item, e);
		}

		bpartnerInfo.bpartnerId(bpartnerComposite.getBpartner().getId());

		final IdentifierString billLocationIdentifier = IdentifierString.ofOrNull(item.getBillLocationIdentifier());
		if (billLocationIdentifier == null)
		{
			if (candidate.isNew())
			{
				final BPartnerLocation location = bpartnerComposite
						.extractBillToLocation()
						.orElseThrow(() -> new MissingResourceException("billLocationIdentifier", item));
				bpartnerInfo.bpartnerLocationId(location.getId());
			}
			else if (effectiveSyncAdvise.getIfExists().isUpdateRemove())
			{
				// fail, because we can't remove the billto-location
				throw new MissingPropertyException("billLocationIdentifier", item);
			}
			else
			{
				// don't change the candidate's location
			}
		}
		else
		{
			final BPartnerLocation location = bpartnerComposite
					.extractLocation(l -> matches(billLocationIdentifier, l))
					.orElseThrow(() -> new MissingResourceException("billLocationIdentifier", item));
			bpartnerInfo.bpartnerLocationId(location.getId());
		}

		final IdentifierString billContactIdentifier = IdentifierString.ofOrNull(item.getBillContactIdentifier());
		if (billContactIdentifier == null)
		{
			if (effectiveSyncAdvise.getIfExists().isUpdateRemove())
			{
				bpartnerInfo.contactId(null); // that's OK, because the contact is not mandatory in C_Invoice_Candidate
			}
		}
		else
		{
			// extract the composite's location that has the given billContactIdentifier
			final BPartnerContact contact = bpartnerComposite
					.extractContact(c -> matches(billContactIdentifier, c))
					.orElseThrow(() -> new MissingResourceException("billContactIdentifier", item));

			bpartnerInfo.contactId(contact.getId());
		}

		candidate.setBillPartnerInfo(bpartnerInfo.build());
	}

	private boolean matches(final IdentifierString locationIdentifier, final BPartnerLocation location)
	{
		switch (locationIdentifier.getType())
		{
			case EXTERNAL_ID:
				return locationIdentifier.asExternalId().equals(location.getExternalId());
			case GLN:
				return locationIdentifier.asGLN().equals(location.getGln());
			case METASFRESH_ID:
				return locationIdentifier.asMetasfreshId().equals(MetasfreshId.of(location.getId()));
			case VALUE:
				throw new AdempiereException("value not supported for locations"); // TODO polish
			default:
				throw new AdempiereException("Unexpected type; locationIdentifier=" + locationIdentifier);
		}
	}

	private boolean matches(final IdentifierString contactIdentifier, final BPartnerContact contact)
	{
		switch (contactIdentifier.getType())
		{
			case EXTERNAL_ID:
				return contactIdentifier.asExternalId().equals(contact.getExternalId());
			case GLN:
				throw new AdempiereException("GLN not supported for contacts"); // TODO polish
			case METASFRESH_ID:
				return contactIdentifier.asMetasfreshId().equals(MetasfreshId.of(contact.getId()));
			case VALUE:
				throw new AdempiereException("Value not supported for contacts"); // TODO polish
			default:
				throw new AdempiereException("Unexpected type; contactIdentifier=" + contactIdentifier);
		}
	}

	private void syncDiscountOverrideToCandidate(
			@NonNull final ExternallyReferencedCandidate candidate,
			@NonNull final JsonOverride discountOverride,
			@NonNull final SyncAdvise effectiveSyncAdvise)
	{
		final boolean unsetValue = discountOverride.computeUnsetValue(effectiveSyncAdvise);
		if (unsetValue)
		{
			candidate.setDiscountOverride(null);
			return;
		}

		final boolean setValue = discountOverride.computeSetValue(effectiveSyncAdvise);
		if (setValue)
		{
			candidate.setDiscountOverride(Percent.ofNullable(discountOverride.getValue()));
			return;
		}
	}

	private void syncPriceEnteredOverrideToCandidate(
			@NonNull final ExternallyReferencedCandidate candidate,
			@NonNull final JsonOverride discountOverride,
			@NonNull final SyncAdvise effectiveSyncAdvise)
	{
		final boolean unsetValue = discountOverride.computeUnsetValue(effectiveSyncAdvise);
		if (unsetValue)
		{
			candidate.setDiscountOverride(null);
			return;
		}

		final boolean setValue = discountOverride.computeSetValue(effectiveSyncAdvise);
		if (setValue)
		{
			candidate.setDiscountOverride(Percent.ofNullable(discountOverride.getValue()));
			return;
		}
	}

	private InvoiceCandidateLookupKey createInvoiceCandidateLookupKey(@NonNull final JsonRequestInvoiceCandidateUpsertItem item)
	{
		try
		{
			return InvoiceCandidateLookupKey.builder()
					.invoiceCandidateId(InvoiceCandidateId.ofRepoIdOrNull(MetasfreshId.toValue(item.getMetasfreshId())))
					.externalHeaderId(JsonExternalIds.toExternalIdOrNull(item.getExternalHeaderId()))
					.externalLineId(JsonExternalIds.toExternalIdOrNull(item.getExternalLineId()))
					.build();
		}
		catch (final AdempiereException e)
		{
			throw InvalidEntityException.wrapIfNeeded(e);
		}
	}

}
