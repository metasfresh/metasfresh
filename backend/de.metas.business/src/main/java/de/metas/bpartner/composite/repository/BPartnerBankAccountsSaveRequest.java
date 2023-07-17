/*
 * #%L
 * de.metas.business
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

package de.metas.bpartner.composite.repository;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Builder
@Value
public class BPartnerBankAccountsSaveRequest
{
	@NonNull
	final BPartnerId bpartnerId;
	@NonNull
	final BPartnerBankAccount bankAccount;
	@Nullable
	final OrgId orgId;

	/**
	 * Use-Case for {@code false}: when transferring a customer to another org, the user who does the transfer might not have access to the target-org. Still we need to be able to create the bank-account in that target-org.
	 */
	boolean validatePermissions;
}
