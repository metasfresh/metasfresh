package de.metas.ordercandidate.api;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.sql.Timestamp;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.ordercandidate.model.I_C_OLCand;

/**
 * Use this service to get the "actual" values for a given order line candidate. If this service has no getter for a given field (like <code>DateCandidate</code>), it is save to get the value directly
 * from the olCand instead.
 *
 * @author ts
 *
 */
public interface IOLCandEffectiveValuesBL extends ISingletonService
{

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>C_BPartner_Override_ID</code></li>
	 * <li><code>C_BPartner_ID</code></li>
	 * </ul>
	 *
	 * @param olCand
	 * @return
	 */
	int getC_BPartner_Effective_ID(I_C_OLCand olCand);

	/**
	 * Like {@link #getC_BPartner_Effective_ID(I_C_OLCand)}, but returns the actual partner.
	 *
	 * @param olCand
	 * @return
	 */
	I_C_BPartner getC_BPartner_Effective(I_C_OLCand olCand);

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>C_BP_Location_Override_ID</code></li>
	 * <li><code>C_BP_Location_ID</code></li>
	 * </ul>
	 *
	 * @param olCand
	 * @return
	 */
	int getC_BP_Location_Effective_ID(I_C_OLCand olCand);

	/**
	 * The effective location set in the olcand. See {@link #getC_BP_Location_Effective_ID(I_C_OLCand)}.
	 * <p>
	 * Note: Do not use the getter from the generated interface because the C_BPartner_Location_Effective column is an sql one
	 *
	 * @param olcand
	 * @return C_BPartner_Location_Override if set, C_BPartner_Location otherwise
	 */
	I_C_BPartner_Location getC_BP_Location_Effective(I_C_OLCand olCand);

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>AD_User_ID</code></li>
	 * </ul>
	 *
	 * @param olCand
	 * @return
	 */
	int getAD_User_Effective_ID(I_C_OLCand olCand);

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>DatePromised_Override</code></li>
	 * <li><code>DatePromised</code></li>
	 * <li>The current system time</li>
	 * </ul>
	 *
	 * @param olCand
	 * @return
	 */
	Timestamp getDatePromised_Effective(I_C_OLCand olCand);

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>Bill_BPartner_ID</code></li>
	 * <li><code>C_BPartner_Override_ID</code></li>
	 * <li><code>C_BPartner_ID</code></li>
	 * </ul>
	 *
	 * @param olCand
	 * @return
	 */
	int getBill_BPartner_Effective_ID(I_C_OLCand olCand);

	/**
	 *
	 * @param olCand
	 * @param clazz
	 * @return
	 * @see #getBill_BPartner_Effective_ID(I_C_OLCand)
	 */
	<T extends I_C_BPartner> T getBill_BPartner_Effective(I_C_OLCand olCand, Class<T> clazz);

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>Bill_Location_ID</code></li>
	 * <li><code>C_BP_Location_Override_ID</code></li>
	 * <li><code>C_BPartner_Location_ID</code></li>
	 * </ul>
	 *
	 * @param olCand
	 * @return
	 */
	int getBill_Location_Effective_ID(I_C_OLCand olCand);

	/**
	 * See #getBill_Location_Effective_ID
	 *
	 * @param olCand
	 * @return
	 */
	I_C_BPartner_Location getBill_Location_Effective(I_C_OLCand olCand);

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>Bill_User_ID</code></li>
	 * <li><code>AD_User_ID</code></li>
	 * </ul>
	 *
	 * @param olCand
	 * @return
	 */
	int getBill_User_Effective_ID(I_C_OLCand olCand);

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>DropShip_BPartner_Override_ID</code></li>
	 * <li><code>DropShip_BPartner_ID</code></li>
	 * <li><code>C_BPartner_Override_ID</code></li>
	 * <li><code>C_BPartner_ID</code></li>
	 * </ul>
	 *
	 * #100 FRESH-435: even if the (effective) DropShip_BPartner_ID is the same as the (effective) C_BPartner_ID, this method shall not return 0
	 *
	 * @param olCand
	 * @return
	 */
	int getDropShip_BPartner_Effective_ID(I_C_OLCand olCand);

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>DropShip_Location_Override_ID</code></li>
	 * <li><code>DropShip_Location_ID</code></li>
	 * <li><code>C_BP_Location_Override_ID</code></li>
	 * <li><code>C_BPartner_Location_ID</code></li>
	 * </ul>
	 *
	 * #100 FRESH-435: even if the (effective) DropShip_Location_ID is the same as the (effective) C_BPartner_Location_ID, this method shall not return 0.
	 *
	 * @param olCand
	 * @return
	 */
	int getDropShip_Location_Effective_ID(I_C_OLCand olCand);

	/**
	 * See {@link #getDropShip_Location_Effective_ID(I_C_OLCand)}.
	 *
	 * @param olCand
	 * @return
	 */
	I_C_BPartner_Location getDropShip_Location_Effective(I_C_OLCand olCand);

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>AD_User_ID</code></li>
	 * </ul>
	 *
	 * @param olCand
	 * @return
	 */
	int getDropShip_User_Effective_ID(I_C_OLCand olCand);

	/**
	 * @Param olCand
	 *
	 * @return datePromised_Override if set, datePromised if not
	 */
	Timestamp getDatePromisedEffective(I_C_OLCand olCand);

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>M_Product_Override_ID</code></li>
	 * <li><code>M_Product_ID</code></li>
	 * </ul>
	 *
	 * @param olCand
	 * @return
	 */
	int getM_Product_Effective_ID(I_C_OLCand olCand);

	/**
	 * Like {@link #getM_Product_Effective_ID(I_C_OLCand)}, but returns the actual product.
	 *
	 * @param olCand
	 * @return
	 */
	I_M_Product getM_Product_Effective(I_C_OLCand olCand);

	/**
	 * Returns <code>C_UOM_ID</code> if <code>IsManualPrice='Y'</code> and <code>C_UOM_Internal_ID</code> otherwise.
	 *
	 * @param olCand
	 * @return
	 */
	int getC_UOM_Effective_ID(I_C_OLCand olCand);

	/**
	 * Like {@link #getC_UOM_Effective_ID(I_C_OLCand)}, but return the actual uom.
	 *
	 * @param olCand
	 * @return
	 */
	I_C_UOM getC_UOM_Effective(I_C_OLCand olCand);

	/**
	 * Returns, falling back to the next if not set:
	 *
	 * <ul>
	 * <li><code>HandOver_Partner_Override_ID</code></li>
	 * <li><code>HandOver_Partner_ID</code></li>
 	 * <li><code>C_BPartner_Override_ID</code></li>
 	 * <li><code>C_BPartner_ID</code></li>
	 * </ul>
	 *
	 * #100 FRESH-435: even if the (effective) HandOver_Partner_ID is the same as the (effective) C_BPartner_ID, this method shall not return 0.
	 *
	 * @param olCand
	 * @return
	 */
	int getHandOver_Partner_Effective_ID(I_C_OLCand olCand);

	/**
	 * Like {@link #getHandOver_Partner_Effective_ID(I_C_OLCand)}, but returns the actual partner.
	 *
	 * @param olCand
	 * @return
	 */
	I_C_BPartner getHandOver_Partner_Effective(I_C_OLCand olCand);

	/**
	 *
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>HandOver_Location_Override_ID</code></li>
	 * <li><code>HandOver_Location_ID</code></li>
	 * <li><code>C_BPartner_Location_Override_ID</code></li>
	 * <li><code>C_BPartner_Location_ID</code></li>
	 * </ul>
	 *
	 * #100 FRESH-435: even if the (effective) HandOver_Location_ID is the same as the (effective) C_BPartner_Location_ID, this method shall not return 0.
	 *
	 * @param olCand
	 * @return
	 */

	int getHandOver_Location_Effective_ID(I_C_OLCand olCand);

	/**
	 * Like {@link #getHandOver_Location_Effective_ID(I_C_OLCand)}, but returns the actual location.
	 *
	 * @param olCand
	 * @return
	 */

	I_C_BPartner_Location getHandOver_Location_Effective(I_C_OLCand olCand);

}
