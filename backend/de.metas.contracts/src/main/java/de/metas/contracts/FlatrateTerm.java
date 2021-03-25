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

package de.metas.contracts;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class FlatrateTerm
{
	private FlatrateTermId flatrateTermId;

	private OrgId orgId;

	private BPartnerId billPartnerID;

	private BPartnerLocationId billLocationId;

	private BPartnerId shiToBPartnerId;

	private BPartnerLocationId shipToLocationId;

	private ProductId productId;

	private UserId userId;

	boolean isSimulation;

	private ConditionsId flartareConditionsId;

	private LocalDate startDate;

	private
	LocalDate masterStartDate;

	private
	LocalDate endDate;

	private
	LocalDate masterEndDate;

	private
	FlatrateTermStatus status;
	
	private BigDecimal plannedQtyPerUnit;

	private UomId uomId;

}
