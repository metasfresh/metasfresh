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

package de.metas.rest_api.bpartner.relation;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.api.IBPRelationDAO;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.service.BPRelation;
import de.metas.organization.OrgId;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.bpartner.request.JsonRequestBPRelationTarget;
import de.metas.rest_api.bpartner.response.JsonResponseBPRelationComposite;
import de.metas.rest_api.bpartner.response.JsonResponseBPRelationItem;
import de.metas.rest_api.common.JsonBPRelationRole;
import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.rest_api.utils.MetasfreshId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BPRelationsService
{

	private final JsonServiceFactory jsonServiceFactory;
	private final IBPRelationDAO bpRelationDAO = Services.get(IBPRelationDAO.class);

	public BPRelationsService(@NonNull final JsonServiceFactory jsonServiceFactory)
	{
		this.jsonServiceFactory = jsonServiceFactory;
	}

	public JsonResponseBPRelationComposite getRelationsForPartner(@NonNull final OrgId orgId, @NonNull final IdentifierString bpartnerIdentifier)
	{
		final Optional<BPartnerComposite> bPartnerComposite = jsonServiceFactory.createRetriever().getBPartnerComposite(orgId, bpartnerIdentifier);

		final Collection<JsonResponseBPRelationItem> relations = new ArrayList<>();
		bPartnerComposite.ifPresent(rel -> relations.addAll(getRelationsForBPartner(orgId, rel.getBpartner().getId())));
		return JsonResponseBPRelationComposite.builder()
				.responseItems(relations)
				.build();
	}

	private Collection<JsonResponseBPRelationItem> getRelationsForBPartner(final @NonNull OrgId orgId, @NonNull final BPartnerId id)
	{
		final Stream<BPRelation> relationsForBpartner = bpRelationDAO.getRelationsForBpartner(orgId, id);
		return relationsForBpartner.map(this::toJson).collect(Collectors.toList());
	}

	private JsonResponseBPRelationItem toJson(@NonNull final BPRelation relation)
	{
		final JsonResponseBPRelationItem.JsonResponseBPRelationItemBuilder builder = JsonResponseBPRelationItem.builder()
				.bpartnerId(relation.getBpartnerId().toJson())
				.targetBPartnerId(relation.getTargetBPartnerId().toJson())
				.targetBPLocationId(relation.getTargetBPLocationId().getRepoId())
				.name(relation.getName())
				.description(relation.getDescription())
				.billTo(relation.getBillTo())
				.fetchedFrom(relation.getFetchedFrom())
				.handoverLocation(relation.getHandoverLocation())
				.payFrom(relation.getPayFrom())
				.remitTo(relation.getRemitTo())
				.shipTo(relation.getShipTo())
				.active(relation.getActive());
		final BPRelation.BPRelationRole role = relation.getRole();
		if (role != null)
		{
			builder.role(JsonBPRelationRole.valueOf(role.name()));
		}
		final BPartnerLocationId bpLocationId = relation.getBpLocationId();
		if (bpLocationId != null)
		{
			builder.locationId(bpLocationId.getRepoId());
		}
		final ExternalId externalId = relation.getExternalId();
		if (externalId != null)
		{
			builder.externalId(JsonExternalId.ofOrNull(externalId.getValue()));
		}

		return builder.build();
	}

	public void createOrUpdateRelations(@NonNull final OrgId orgId,
			@NonNull final IdentifierString bpartnerIdentifier,
			@Nullable final IdentifierString locationIdentifier,
			@NonNull final List<JsonRequestBPRelationTarget> relatesTo)
	{
		final Optional<BPartnerComposite> bPartnerComposite = jsonServiceFactory.createRetriever().getBPartnerComposite(orgId, bpartnerIdentifier);
		final BPartnerComposite bpartner = Check.assumePresent(bPartnerComposite, "No bpartner found for identifier: " + bpartnerIdentifier);

		final Optional<BPartnerLocation> location = getBpartnerLocation(bpartnerIdentifier, locationIdentifier, bpartner);
		final Stream<BPRelation> relations = relatesTo.stream().map(relatedBp -> fromJson(bpartner, location, relatedBp));

		relations.forEach(relation -> bpRelationDAO.saveOrUpdate(orgId, relation));
	}

	@NonNull
	private Optional<BPartnerLocation> getBpartnerLocation(final @NonNull IdentifierString bpartnerIdentifier, final @Nullable IdentifierString locationIdentifier, @NonNull final BPartnerComposite bPartnerComposite)
	{
		final IdentifierString lookupLocationIdentifier = (locationIdentifier == null && IdentifierString.Type.GLN.equals(bpartnerIdentifier.getType()))
				? bpartnerIdentifier
				: locationIdentifier;
		return bPartnerComposite.extractLocation(bPartnerLocation -> locationMatchesIdentifier(bPartnerLocation, lookupLocationIdentifier));
	}

	private boolean locationMatchesIdentifier(@NonNull final BPartnerLocation bPartnerLocation, @Nullable final IdentifierString locationIdentifier)
	{
		if (locationIdentifier == null)
		{
			return false;
		}
		switch (locationIdentifier.getType())
		{
			case EXTERNAL_ID:
				return Objects.equals(bPartnerLocation.getExternalId(), locationIdentifier.asExternalId());
			case GLN:
				return GLN.equals(bPartnerLocation.getGln(), locationIdentifier.asGLN());
			case METASFRESH_ID:
				return MetasfreshId.equals(locationIdentifier.asMetasfreshId(), MetasfreshId.of(bPartnerLocation.getId()));
			default:
				// note: currently, "val-" is not supported with bpartner locations
				throw new AdempiereException("Unexpected type=" + locationIdentifier.getType());
		}
	}

	private BPRelation fromJson(final BPartnerComposite sourceBPartner, final Optional<BPartnerLocation> location, final JsonRequestBPRelationTarget relatedBp)
	{
		final BPartnerId bpartnerId = sourceBPartner.getBpartner().getId();
		final IdentifierString targetBpIdentifier = IdentifierString.of(relatedBp.getTargetBpartnerIdentifier());
		final BPartnerComposite targetBpartnerComposite =
				Check.assumePresent(jsonServiceFactory.createRetriever().getBPartnerComposite(sourceBPartner.getOrgId(), targetBpIdentifier)
						, "No bpartner found for identifier: " + targetBpIdentifier);

		final BPartnerId targetBPartnerId = targetBpartnerComposite.getBpartner().getId();
		final IdentifierString targetLocationIdentifier = IdentifierString.ofOrNull(relatedBp.getTargetLocationIdentifier());
		final Optional<BPartnerLocation> targetLocationOpt = getBpartnerLocation(targetBpIdentifier, targetLocationIdentifier, targetBpartnerComposite);
		final BPartnerLocation targetLocation = Check.assumeNotNull(targetLocationOpt.orElseGet(getTargetPartnerLocationForRelation(targetBpartnerComposite, relatedBp.getShipTo(), relatedBp.getBillTo())), "Cannot identify location for target BPartner: {}, LocationIdentifier: {}", targetBpIdentifier, targetLocationIdentifier);

		final BPRelation.BPRelationBuilder builder = BPRelation.builder()
				.bpartnerId(bpartnerId)
				.targetBPartnerId(targetBPartnerId)
				.targetBPLocationId(targetLocation.getId())
				.name(relatedBp.getName())
				.description(relatedBp.getDescription())
				.billTo(relatedBp.getBillTo())
				.fetchedFrom(relatedBp.getFetchedFrom())
				.handoverLocation(relatedBp.getHandOverLocation())
				.payFrom(relatedBp.getPayFrom())
				.remitTo(relatedBp.getRemitTo())
				.shipTo(relatedBp.getShipTo())
				.active(relatedBp.getActive());
		if (relatedBp.getRole() != null)
		{
			builder.role(BPRelation.BPRelationRole.valueOf(relatedBp.getRole().name()));
		}
		if (relatedBp.getExternalId() != null)
		{
			builder.externalId(ExternalId.ofOrNull(relatedBp.getExternalId()));
		}
		location.ifPresent(loc -> builder.bpLocationId(loc.getId()));
		return builder.build();
	}

	private Supplier<? extends BPartnerLocation> getTargetPartnerLocationForRelation(@NonNull final BPartnerComposite bpartnerComposite, @Nullable final Boolean isShipTo, @Nullable final Boolean isBillTo)
	{
		Optional<BPartnerLocation> location = Optional.empty();
		if (Boolean.TRUE.equals(isBillTo))
		{
			location = bpartnerComposite.extractBillToLocation();
			if (!location.isPresent())
			{
				location = bpartnerComposite.extractLocation(l -> l.getLocationType().getIsBillToOr(false));
			}
		}
		if (Boolean.TRUE.equals(isShipTo))
		{
			location = bpartnerComposite.extractShipToLocation();
			if (!location.isPresent())
			{
				location = bpartnerComposite.extractLocation(l -> l.getLocationType().getIsShipToOr(false));
			}
		}
		if (!location.isPresent())
		{
			location = bpartnerComposite.extractLocation(Objects::nonNull);
		}
		final Optional<BPartnerLocation> result = location;
		return () -> Check.assumePresent(result, "Cannot infer location for source BPartner");
	}

}
