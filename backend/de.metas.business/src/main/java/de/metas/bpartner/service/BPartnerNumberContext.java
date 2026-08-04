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

import de.metas.bpartner.BPartnerId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

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

	/**
	 * The {@code C_BPartner_ID} of the business partner being processed.
	 *
	 * <p>May be {@code null} when the interceptor fires at {@code TYPE_BEFORE_NEW}:
	 * in native-sequence mode the primary key is {@code 0} until {@code saveNew()} assigns it,
	 * so the interceptor cannot provide a valid {@link BPartnerId}.
	 * Only the override-function branch uses this value; the sequence branch ignores it.
	 */
	@Nullable BPartnerId bPartnerId;

	boolean isCustomer;
	boolean isVendor;
	boolean isCompany;

	@NonNull Kind kind;
}
