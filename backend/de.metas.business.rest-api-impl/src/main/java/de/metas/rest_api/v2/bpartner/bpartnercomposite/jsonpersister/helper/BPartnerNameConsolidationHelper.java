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

package de.metas.rest_api.v2.bpartner.bpartnercomposite.jsonpersister.helper;

import de.metas.bpartner.composite.BPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import de.metas.util.web.exception.InvalidEntityException;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.springframework.lang.Nullable;

public class BPartnerNameConsolidationHelper
{
	private static final AdMessageKey CANNOT_SET_NAME_TO_NULL = AdMessageKey.of("BPartnerNameConsolidationRule.CANNOT_SET_NAME_TO_NULL");
	private static final AdMessageKey INCONSISTENT_PROPERTIES = AdMessageKey.of("BPartnerNameConsolidationRule.INCONSISTENT_PROPERTIES");

	public static BPartnerNameConsolidationHelper.Result consolidate(@NonNull final JsonRequestBPartner jsonRequestBPartner, @NonNull final BPartner partner)
	{
		if (jsonRequestBPartner.isNameSet() && Check.isBlank(jsonRequestBPartner.getName()))
		{
			throw new InvalidEntityException(TranslatableStrings.adMessage(CANNOT_SET_NAME_TO_NULL));
		}

		if (Check.isNotBlank(jsonRequestBPartner.getName()))
		{
			return consolidateWithName(jsonRequestBPartner.getName(), jsonRequestBPartner, partner);
		}
		else if (jsonRequestBPartner.isCompanyNameSet())
		{
			return consolidateWithCompanyName(jsonRequestBPartner.getCompanyName(), jsonRequestBPartner, partner);
		}

		// nothing changed
		return Result.builder()
				.name(partner.getName())
				.companyName(partner.getCompanyName())
				.isCompany(partner.isCompany())
				.build();
	}

	@NonNull
	private static BPartnerNameConsolidationHelper.Result consolidateWithName(
			@NonNull final String name,
			@NonNull final JsonRequestBPartner jsonRequestBPartner,
			@NonNull final BPartner partner)
	{
		final Result.ResultBuilder builder = Result.builder().name(name);
		if (jsonRequestBPartner.isCompanyNameSet() && Check.isBlank(jsonRequestBPartner.getCompanyName()))
		{
			return builder
					.isCompany(false)
					.companyName(null)
					.build();
		}
		else if (jsonRequestBPartner.isCompanyNameSet())
		{
			if (!name.equals(jsonRequestBPartner.getCompanyName()))
			{
				throw new InvalidEntityException(TranslatableStrings.adMessage(INCONSISTENT_PROPERTIES));
			}

			return builder
					.isCompany(true)
					.companyName(name)
					.build();
		}
		else
		{
			return builder
					.isCompany(partner.isCompany())
					.companyName(partner.isCompany() ? name : null)
					.build();
		}
	}

	@NonNull
	private static BPartnerNameConsolidationHelper.Result consolidateWithCompanyName(
			@Nullable final String companyName,
			@NonNull final JsonRequestBPartner jsonRequestBPartner,
			@NonNull final BPartner partner)
	{
		Check.assume(!jsonRequestBPartner.isNameSet(), "If name is set, then consolidateWithName should be used.");
		return Result.builder()
				.companyName(companyName)
				.isCompany(Check.isNotBlank(companyName))
				.name(Check.isNotBlank(companyName) ? companyName : partner.getName())
				.build();
	}

	@Value
	@Builder
	public static class Result
	{
		String name;
		String companyName;
		boolean isCompany;
	}
}
