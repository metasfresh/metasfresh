/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.alberta.patient.processor;

import de.metas.camel.externalsystems.common.BPRelationsCamelRequest;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseUpsertItem;
import de.metas.common.bprelation.JsonBPRelationRole;
import de.metas.common.bprelation.request.JsonRequestBPRelationTarget;
import de.metas.common.bprelation.request.JsonRequestBPRelationsUpsert;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_BP_IDENTIFIER_TO_ROLE;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_CURRENT_PATIENT;
import static de.metas.camel.alberta.patient.GetPatientsRouteConstants.ROUTE_PROPERTY_ORG_CODE;

public class CreateBPRelationReqProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final JsonResponseBPartnerCompositeUpsert bPartnerUpsertResult = exchange.getIn().getBody(JsonResponseBPartnerCompositeUpsert.class);

		final String orgCode = exchange.getProperty(ROUTE_PROPERTY_ORG_CODE, String.class);
		if (orgCode == null)
		{
			throw new RuntimeException("Missing camel route property: " + ROUTE_PROPERTY_ORG_CODE);
		}

		final BPartnerRoleInfoProvider bPartnerRoleInfoProvider = exchange.getProperty(ROUTE_PROPERTY_BP_IDENTIFIER_TO_ROLE, BPartnerRoleInfoProvider.class);
		if (bPartnerRoleInfoProvider == null)
		{
			throw new RuntimeException("Missing camel route property: " + ROUTE_PROPERTY_BP_IDENTIFIER_TO_ROLE);
		}

		if (bPartnerUpsertResult == null
				|| bPartnerUpsertResult.getResponseItems() == null
				|| bPartnerUpsertResult.getResponseItems().isEmpty())
		{
			throw new RuntimeException("Empty JsonResponseBPartnerCompositeUpsert.ResponseItems! sourceBPartnerIdentifier:"
					+ bPartnerRoleInfoProvider.getSourceBPartnerIdentifier());
		}

		exchange.getIn().setBody(buildBPRelationsUpsertRequest(orgCode, bPartnerRoleInfoProvider, bPartnerUpsertResult.getResponseItems()));

		exchange.removeProperty(ROUTE_PROPERTY_CURRENT_PATIENT);
		exchange.removeProperty(ROUTE_PROPERTY_BP_IDENTIFIER_TO_ROLE);
	}

	@NonNull
	private BPRelationsCamelRequest buildBPRelationsUpsertRequest(
			@NonNull final String orgCode,
			@NonNull final BPartnerRoleInfoProvider bPartnerRoleInfoProvider,
			@NonNull final List<JsonResponseBPartnerCompositeUpsertItem> responseItems)
	{
		final JsonResponseBPartnerCompositeUpsertItem sourceBPUpsertItem =
				getResponseItemBySourceBPIdentifier(bPartnerRoleInfoProvider.getSourceBPartnerIdentifier(), responseItems);

		final JsonMetasfreshId sourceBPartnerId = getBPartnerMetasfreshId(sourceBPUpsertItem);

		final JsonMetasfreshId sourceLocationId =
				getSourceLocationMFIdByIdentifier(bPartnerRoleInfoProvider.getSourceBPartnerLocationIdentifier(), sourceBPUpsertItem);

		final List<JsonRequestBPRelationTarget> relatesTo = responseItems
				.stream()
				.map(responseItem -> buildBPRelationTarget(bPartnerRoleInfoProvider, responseItem))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());

		final JsonRequestBPRelationsUpsert jsonRequestBPRelationsUpsert = JsonRequestBPRelationsUpsert.builder()
				.locationIdentifier(String.valueOf(sourceLocationId.getValue()))
				.orgCode(orgCode)
				.relatesTo(relatesTo)
				.build();

		return BPRelationsCamelRequest.builder()
				.bpartnerIdentifier(String.valueOf(sourceBPartnerId.getValue()))
				.jsonRequestBPRelationsUpsert(jsonRequestBPRelationsUpsert)
				.build();
	}

	@NonNull
	private Optional<JsonRequestBPRelationTarget> buildBPRelationTarget(
			@NonNull final BPartnerRoleInfoProvider bPartnerRoleInfoProvider,
			@NonNull final JsonResponseBPartnerCompositeUpsertItem responseItem)
	{
		try
		{
			if (responseItem.getResponseBPartnerItem() == null
					|| responseItem.getResponseBPartnerItem().getIdentifier() == null
					|| responseItem.getResponseBPartnerItem().getMetasfreshId() == null)
			{
				throw new RuntimeCamelException("Missing mandatory data from responseItem: " + responseItem);
			}

			final String targetBPartnerIdentifier = responseItem.getResponseBPartnerItem().getIdentifier();

			if (bPartnerRoleInfoProvider.getSourceBPartnerIdentifier().equals(targetBPartnerIdentifier))
			{
				return Optional.empty();
			}

			final JsonMetasfreshId targetBPMFId = responseItem.getResponseBPartnerItem().getMetasfreshId();

			final JsonBPRelationRole relationRole = bPartnerRoleInfoProvider.getRoleByBPIdentifier(targetBPartnerIdentifier)
					.orElseThrow(() -> new RuntimeCamelException("No JsonBPRelationRole found for  bpartnerIdentifier: " + targetBPartnerIdentifier));

			final JsonMetasfreshId targetLocationId = getFirstLocationMFId(responseItem);

			final JsonRequestBPRelationTarget relationTarget = JsonRequestBPRelationTarget.builder()
					.name(relationRole.toString())
					.role(relationRole)
					.targetBpartnerIdentifier(String.valueOf(targetBPMFId.getValue()))
					.targetLocationIdentifier(String.valueOf(targetLocationId.getValue()))
					.build();

			return Optional.of(relationTarget);
		}
		catch (final RuntimeException e)
		{
			throw new RuntimeCamelException("*** ERROR thrown while processing JsonResponseBPartnerCompositeUpsertItem: " + responseItem, e);
		}
	}

	@NonNull
	private JsonResponseBPartnerCompositeUpsertItem getResponseItemBySourceBPIdentifier(
			@NonNull final String sourceBPIdentifier,
			@NonNull final List<JsonResponseBPartnerCompositeUpsertItem> responseItems)
	{
		return responseItems
				.stream()
				.filter(upsertItem -> upsertItem.getResponseBPartnerItem() != null && sourceBPIdentifier.equals(upsertItem.getResponseBPartnerItem().getIdentifier()))
				.findFirst()
				.orElseThrow(() -> new RuntimeCamelException("No JsonResponseBPartnerCompositeUpsertItem found for sourceBPIdentifier:" + sourceBPIdentifier));
	}

	@NonNull
	private JsonMetasfreshId getSourceLocationMFIdByIdentifier(
			@NonNull final String locationIdentifier,
			@NonNull final JsonResponseBPartnerCompositeUpsertItem bpartnerUpsertResponseItem)
	{
		if (bpartnerUpsertResponseItem.getResponseLocationItems() == null || bpartnerUpsertResponseItem.getResponseLocationItems().isEmpty())
		{
			throw new RuntimeCamelException("Empty JsonResponseBPartnerCompositeUpsertItem.getResponseLocationItems! Location identifier: " + locationIdentifier);
		}

		return bpartnerUpsertResponseItem.getResponseLocationItems()
				.stream()
				.filter(locationRespItem -> locationIdentifier.equals(locationRespItem.getIdentifier()))
				.map(JsonResponseUpsertItem::getMetasfreshId)
				.filter(Objects::nonNull)
				.findFirst()
				.orElseThrow(() -> new RuntimeCamelException("No JsonMetasfreshId found for locationIdentifier: " + locationIdentifier));
	}

	@NonNull
	private JsonMetasfreshId getFirstLocationMFId(@NonNull final JsonResponseBPartnerCompositeUpsertItem bpartnerUpsertResponseItem)
	{
		if (bpartnerUpsertResponseItem.getResponseLocationItems() == null || bpartnerUpsertResponseItem.getResponseLocationItems().isEmpty())
		{
			throw new RuntimeCamelException("The given JsonResponseBPartnerCompositeUpsertItem has no responseLocationItems!; bpartnerUpsertResponseItem=" + bpartnerUpsertResponseItem);
		}

		return bpartnerUpsertResponseItem.getResponseLocationItems()
				.stream()
				.map(JsonResponseUpsertItem::getMetasfreshId)
				.filter(Objects::nonNull)
				.findFirst()
				.orElseThrow(() -> new RuntimeCamelException("No location JsonResponseUpsertItem found!"));
	}

	@NonNull
	private JsonMetasfreshId getBPartnerMetasfreshId(@NonNull final JsonResponseBPartnerCompositeUpsertItem bpUpsertItem)
	{
		if (bpUpsertItem.getResponseBPartnerItem() == null || bpUpsertItem.getResponseBPartnerItem().getMetasfreshId() == null)
		{
			throw new RuntimeCamelException("ResponseBPartnerItem.JsonMetasfreshId is missing for bpUpsertItem: " + bpUpsertItem);
		}

		return bpUpsertItem.getResponseBPartnerItem().getMetasfreshId();
	}
}
