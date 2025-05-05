/*
 * #%L
 * marketing-activecampaign
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

package de.metas.marketing.gateway.activecampaign.restapi.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

public enum Subscription
{
	Unconfirmed("0"),
	Subscribed("1"),
	Unsubscribed("2"),
	Bounced("3"),
	;

	@Getter
	private final String code;

	Subscription(final String code)
	{
		this.code = code;
	}

	public static Subscription ofCode(@NonNull final String code)
	{
		final Subscription type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + Subscription.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, Subscription> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), Subscription::getCode);
}
