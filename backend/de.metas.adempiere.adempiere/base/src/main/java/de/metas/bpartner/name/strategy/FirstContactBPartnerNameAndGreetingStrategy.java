/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.name.strategy;

import de.metas.bpartner.name.NameAndGreeting;
import de.metas.i18n.ExplainedOptional;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import org.compiere.model.X_C_BP_Group;
import org.springframework.stereotype.Component;

@Component
public final class FirstContactBPartnerNameAndGreetingStrategy implements BPartnerNameAndGreetingStrategy
{
	public static final BPartnerNameAndGreetingStrategyId ID = BPartnerNameAndGreetingStrategyId.ofString(X_C_BP_Group.BPNAMEANDGREETINGSTRATEGY_FirstContact);

	private final IUserBL userBL = Services.get(IUserBL.class);

	@Override
	public BPartnerNameAndGreetingStrategyId getId()
	{
		return ID;
	}

	@Override
	public ExplainedOptional<NameAndGreeting> compute(final ComputeNameAndGreetingRequest request)
	{
		final ComputeNameAndGreetingRequest.Contact primaryContact = request.getPrimaryContact().orElse(null);
		if (primaryContact == null)
		{
			return ExplainedOptional.emptyBecause("no primary contact");
		}
		else
		{
			return ExplainedOptional.of(NameAndGreeting.builder()
					.name(userBL.buildContactName(primaryContact.getFirstName(), primaryContact.getLastName()))
					.greetingId(primaryContact.getGreetingId())
					.build());
		}
	}
}
