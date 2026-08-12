/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.rest_api.v2.bpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.effective.BPartnerEffectiveBL;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.impl.GLNQuery;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.externalreference.ExternalBusinessKey;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.ExternalUserReferenceType;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.bpartnerlocation.BPLocationExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.incoterms.Incoterms;
import de.metas.incoterms.IncotermsRepository;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BPartnerMasterdataProvider
{
	@NonNull private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final ExternalReferenceRestControllerService externalReferenceService;
	@NonNull private final BPartnerEffectiveBL bPartnerEffectiveBL;
	@NonNull private final IncotermsRepository incotermsRepository;

	@NonNull
	public Optional<BPartnerId> resolveBPartnerExternalBusinessKey(@NonNull final OrgId orgId, @NonNull final ExternalBusinessKey externalBusinessKey)
	{
		if (ExternalBusinessKey.Type.VALUE.equals(externalBusinessKey.getType()))
		{
			return bPartnerDAO.retrieveBPartnerIdBy(BPartnerQuery.builder()
					.bpartnerValue(externalBusinessKey.asValue())
					.onlyOrgId(orgId)
					.build());
		}
		else if (ExternalBusinessKey.Type.EXTERNAL_REFERENCE.equals(externalBusinessKey.getType()))
		{
			return externalReferenceService
					.getJsonMetasfreshIdFromExternalBusinessKey(orgId, externalBusinessKey, BPartnerExternalReferenceType.BPARTNER_VALUE)
					.map(jsonMetasfreshId -> BPartnerId.ofRepoId(jsonMetasfreshId.getValue()));
		}
		else
		{
			throw new InvalidIdentifierException("Given ExternalBusinessKeyType is not supported!")
					.appendParametersToMessage()
					.setParameter("ExternalBusinessKeyType", externalBusinessKey.getType())
					.setParameter("RawValue", externalBusinessKey.getRawValue());
		}
	}

	@NonNull
	public Optional<BPartnerId> resolveBPartnerExternalIdentifier(@NonNull final OrgId orgId, @NonNull final ExternalIdentifier externalIdentifier)
	{
		if (ExternalIdentifier.Type.METASFRESH_ID.equals(externalIdentifier.getType()))
		{
			return Optional.of(BPartnerId.ofRepoId(externalIdentifier.asMetasfreshId().getValue()));
		}
		else if (ExternalIdentifier.Type.VALUE.equals(externalIdentifier.getType()))
		{
			return bPartnerDAO.retrieveBPartnerIdBy(BPartnerQuery.builder()
					.bpartnerValue(externalIdentifier.asValue())
					.onlyOrgId(orgId)
					.build());
		}
		else if (ExternalIdentifier.Type.GLN.equals(externalIdentifier.getType()))
		{
			return bPartnerDAO.retrieveBPartnerIdBy(BPartnerQuery.builder()
					.gln(externalIdentifier.asGLN())
					.onlyOrgId(orgId)
					.build());
		}
		else if (ExternalIdentifier.Type.EXTERNAL_REFERENCE.equals(externalIdentifier.getType()))
		{
			return externalReferenceService
					.resolveExternalReference(orgId, externalIdentifier, BPartnerExternalReferenceType.BPARTNER)
					.map(jsonMetasfreshId -> BPartnerId.ofRepoId(jsonMetasfreshId.getValue()));
		}
		else
		{
			throw new InvalidIdentifierException("Given ExternalIdentifier is not supported!")
					.appendParametersToMessage()
					.setParameter("ExternalIdentifier", externalIdentifier.getType())
					.setParameter("RawValue", externalIdentifier.getRawValue());
		}
	}

	@NonNull
	public Optional<BPartnerLocationId> resolveBPartnerLocationExternalIdentifier(@NonNull final BPartnerId bPartnerId, @NonNull final OrgId orgId, @NonNull final ExternalIdentifier externalIdentifier)
	{
		if (ExternalIdentifier.Type.METASFRESH_ID.equals(externalIdentifier.getType()))
		{
			return Optional.of(BPartnerLocationId.ofRepoId(bPartnerId, externalIdentifier.asMetasfreshId().getValue()));
		}
		else if (ExternalIdentifier.Type.GLN.equals(externalIdentifier.getType()))
		{
			final GLNQuery glnQuery = GLNQuery.builder()
					.onlyOrgId(orgId)
					.gln(externalIdentifier.asGLN())
					.build();
			return bPartnerDAO.retrieveSingleBPartnerLocationIdBy(glnQuery);
		}
		else if (ExternalIdentifier.Type.EXTERNAL_REFERENCE.equals(externalIdentifier.getType()))
		{
			return externalReferenceService
					.resolveExternalReference(orgId, externalIdentifier, BPLocationExternalReferenceType.BPARTNER_LOCATION)
					.map(jsonMetasfreshId -> BPartnerLocationId.ofRepoId(bPartnerId, jsonMetasfreshId.getValue()));
		}
		else
		{
			throw new InvalidIdentifierException("Given ExternalIdentifier is not supported!")
					.appendParametersToMessage()
					.setParameter("ExternalIdentifier", externalIdentifier.getType())
					.setParameter("RawValue", externalIdentifier.getRawValue());
		}
	}

	@NonNull
	public Optional<UserId> resolveUserExternalIdentifier(@NonNull final OrgId orgId, @NonNull final ExternalIdentifier externalIdentifier)
	{
		if (ExternalIdentifier.Type.METASFRESH_ID.equals(externalIdentifier.getType()))
		{
			return Optional.of(UserId.ofRepoId(externalIdentifier.asMetasfreshId().getValue()));
		}
		else if (ExternalIdentifier.Type.EXTERNAL_REFERENCE.equals(externalIdentifier.getType()))
		{
			return externalReferenceService
					.resolveExternalReference(orgId, externalIdentifier, ExternalUserReferenceType.USER_ID)
					.map(jsonMetasfreshId -> UserId.ofRepoId(jsonMetasfreshId.getValue()));
		}
		else
		{
			throw new InvalidIdentifierException("Given ExternalIdentifier is not supported!")
					.appendParametersToMessage()
					.setParameter("ExternalIdentifier", externalIdentifier.getType())
					.setParameter("RawValue", externalIdentifier.getRawValue());
		}
	}

	public boolean isAutoInvoice(@NonNull final JsonOLCandCreateRequest request, @NonNull final BPartnerId bpartnerId)
	{
		if (request.getIsAutoInvoice() != null)
		{
			return request.getIsAutoInvoice();
		}
		return bPartnerEffectiveBL.getById(bpartnerId).isAutoInvoice(SOTrx.SALES);
	}

	@Nullable
	public Incoterms getIncoterms(@NonNull final JsonOLCandCreateRequest request,
								  @NonNull final OrgId orgId,
								  @NonNull final BPartnerId bPartnerId)
	{
		final Incoterms incoterms;
		if(request.getIncotermsValue() != null)
		{
			incoterms = incotermsRepository.getByValue(request.getIncotermsValue(), orgId);
		}
		else
		{
			incoterms = bPartnerEffectiveBL.getById(bPartnerId).getIncoterms(SOTrx.SALES);
		}

		if(incoterms == null)
		{
			return null;
		}

		return StringUtils.trimBlankToNull(request.getIncotermsLocation()) == null ? incoterms
				: incoterms.withLocationEffective(request.getIncotermsLocation());
	}
}
