/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.bpartner.service;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.contracts.FlatrateTermId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static de.metas.common.util.CoalesceUtil.coalesce;

@Data
public class OrgChangeBPartnerComposite
{
	private BPartnerComposite bPartnerComposite;

	private OrgMappingId bPartnerOrgMappingId;

	private List<FlatrateTermId> membershipSubscriptions; // TODO create object

	private List<FlatrateTermId> nonMembershipSubscriptions;

	@Builder(toBuilder = true)
	private OrgChangeBPartnerComposite(
			@NonNull final BPartnerComposite bPartnerComposite,
			@NonNull final OrgMappingId bPartnerOrgMappingId,
			@Singular final List<FlatrateTermId> membershipSubscriptions,
			@Singular final List<FlatrateTermId> nonMembershipSubscriptions)
	{
		this.bPartnerComposite = bPartnerComposite;
		this.bPartnerOrgMappingId = bPartnerOrgMappingId;

		this.membershipSubscriptions = new ArrayList<>(coalesce(membershipSubscriptions, ImmutableList.of()));
		this.nonMembershipSubscriptions = new ArrayList<>(coalesce(nonMembershipSubscriptions, ImmutableList.of()));
	}
}
