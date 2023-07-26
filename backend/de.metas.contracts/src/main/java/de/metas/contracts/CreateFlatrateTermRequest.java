/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.organization.OrgId;
import de.metas.product.ProductAndCategoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;
import java.sql.Timestamp;

@Value
@Builder
public class CreateFlatrateTermRequest
{
	@NonNull
	IContextAware context;

	@NonNull
	OrgId orgId;
	
	/**
	 * the partner to be used as <code>Bill_BPartner</code> and <code>DropShip_BPartner</code>. Also this partner's sales rep and billto location are used.
	 */
	@NonNull
	I_C_BPartner bPartner;

	@NonNull
	I_C_Flatrate_Conditions conditions;

	/**
	 * the start date for the new term
	 */
	@NonNull
	Timestamp startDate;

	@Nullable
	Timestamp endDate;

	/**
	 * may be <code>null</code>. If set, then this value is used for <code>C_FLatrate_Term.AD_User_InCharge_ID</code>. Otherwise, the method tries
	 * <code>C_BPartner.SalesRep_ID</code>
	 */
	@Nullable
	I_AD_User userInCharge;

	/**
	 *  may be <code>null</code>. If set, then this value is used for <code>C_Flatrate_Term.M_Product_ID</code>.
	 */
	@Nullable
	ProductAndCategoryId productAndCategoryId;

	boolean isSimulation;

	/**
	 *  if <code>true</code>, then attempt to complete the new term
	 */
	boolean completeIt;
}
