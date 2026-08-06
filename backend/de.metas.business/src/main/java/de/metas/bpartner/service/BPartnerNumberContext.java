package de.metas.bpartner.service;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.interfaces.I_C_BPartner;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

/**
 * Immutable context object passed to {@link BPartnerNumberGenerator}.
 * Carries all per-request data needed to resolve the right sequence or override function
 * for a given org and business-partner role.
 */
@Value
@Builder
public class BPartnerNumberContext
{
	/**
	 * Whether this is a debtor-number request (customer side) or a creditor-number request (vendor side).
	 */
	public enum Kind
	{
		DEBTOR,
		CREDITOR
	}

	@NonNull ClientId clientId;

	@NonNull OrgId orgId;

	/** Whether the partner is a company (vs an individual) — a customer override may split ranges on it. */
	boolean isCompany;

	@NonNull Kind kind;

	/**
	 * Builds the context for one role directly from a {@code C_BPartner} record. The role is carried by
	 * {@code kind} (DEBTOR = customer side, CREDITOR = vendor side) — the override is called once per role,
	 * so the customer/vendor flags and the (not-yet-assigned) partner id are not needed here.
	 *
	 * @param bpartner the record being saved
	 * @param kind     debtor (customer side) or creditor (vendor side)
	 */
	public static BPartnerNumberContext ofBPartner(@NonNull final I_C_BPartner bpartner, @NonNull final Kind kind)
	{
		return builder()
				.clientId(ClientId.ofRepoId(bpartner.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(bpartner.getAD_Org_ID()))
				.isCompany(bpartner.isCompany())
				.kind(kind)
				.build();
	}

	public boolean isDebtor()
	{
		return kind == Kind.DEBTOR;
	}

	public boolean isCreditor()
	{
		return kind == Kind.CREDITOR;
	}
}
