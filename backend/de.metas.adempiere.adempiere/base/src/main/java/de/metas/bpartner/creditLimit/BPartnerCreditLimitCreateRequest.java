/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.creditLimit;

import de.metas.bpartner.BPartnerId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_BPartner_CreditLimit;

import javax.annotation.Nullable;
import java.util.function.Consumer;

@Builder
@Value
public class BPartnerCreditLimitCreateRequest
{
	@NonNull
	BPartnerId bpartnerId;

	@NonNull
	BPartnerCreditLimit creditLimit;

	@Nullable
	OrgId orgId;

	/**
	 * Use-Case for {@code null}: when transferring a customer to another org, the user who does the transfer might not have access to the target-org. Still we need to be able to create the creditLimit in that target-org.
	 */
	@Nullable
	Consumer<I_C_BPartner_CreditLimit> validatePermissions;
}
