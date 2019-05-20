package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

import java.util.List;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlBalance;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlDocument;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlEsr;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlLaw;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlProlog;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTiers;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlTreatment;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlBalance.BalanceMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlProlog.PrologMod;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.XmlService.ServiceModWithSelector;

/*
 * #%L
 * vertical-healthcare_ch.invoice_gateway.forum_datenaustausch_ch.invoice_commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder(toBuilder = true)
public class XmlBody
{
	@Nullable
	String roleTitle;

	@NonNull
	String role;

	@NonNull
	String place;

	@NonNull
	XmlProlog prolog;

	@Nullable
	String remark;

	@NonNull
	XmlBalance balance;

	@NonNull
	XmlEsr esr;

	@NonNull
	XmlTiers tiers;

	@NonNull
	XmlLaw law;

	@NonNull
	XmlTreatment treatment;

	@Singular
	List<XmlService> services;

	@Singular
	List<XmlDocument> documents;

	public XmlBody withMod(@Nullable final BodyMod bodyMod)
	{
		if (bodyMod == null)
		{
			return this;
		}

		final XmlBodyBuilder builder = toBuilder();
		if (bodyMod.getEsr() != null)
		{
			builder.esr(bodyMod.getEsr());
		}

		builder
				.prolog(prolog.withMod(bodyMod.getPrologMod()))
				.balance(balance.withMod(bodyMod.getBalanceMod()));

		if (bodyMod.getServiceModsWithSelectors() != null)
		{
			final ImmutableMap<Integer, XmlService> recordId2xService = Maps.uniqueIndex(services, XmlService::getRecordId);
			builder.clearServices();
			for (final ServiceModWithSelector serviceModWithSelector : bodyMod.getServiceModsWithSelectors())
			{
				// we assume that this method would never have been invoked if this was null
				final XmlService xServiceToModify = recordId2xService.get(serviceModWithSelector.getRecordId());

				builder.service(xServiceToModify.withMod(serviceModWithSelector.getServiceMod()));
			}
		}

		if (bodyMod.getDocuments() != null)
		{
			builder
					.clearDocuments()
					.documents(bodyMod.getDocuments());
		}

		return builder.build();
	}

	@Value
	@Builder
	public static class BodyMod
	{
		@Nullable
		PrologMod prologMod;

		@Nullable
		BalanceMod balanceMod;

		@Nullable
		XmlEsr esr;

		@Nullable
		List<ServiceModWithSelector> serviceModsWithSelectors;

		/** if not null, it will completely replace the body's former documents. */
		List<XmlDocument> documents;
	}

}
