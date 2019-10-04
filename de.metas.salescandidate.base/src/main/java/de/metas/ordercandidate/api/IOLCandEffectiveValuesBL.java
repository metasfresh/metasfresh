package de.metas.ordercandidate.api;

import java.time.ZonedDateTime;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.ISingletonService;

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
	 * @return id or {@code null}.
	 */
	BPartnerId getBPartnerEffectiveId(I_C_OLCand olCand);

	/**
	 * Like {@link #getBPartnerEffectiveId(I_C_OLCand)}, but returns the actual partner.
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
	 */
	BPartnerLocationId getLocationEffectiveId(I_C_OLCand olCand);

	/**
	 * The effective location set in the olcand. See {@link #getLocationEffectiveId(I_C_OLCand)}.
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
	 */
	BPartnerContactId getContactEffectiveId(I_C_OLCand olCand);

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
	ZonedDateTime getDatePromised_Effective(I_C_OLCand olCand);

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>Bill_BPartner_ID</code></li>
	 * <li><code>C_BPartner_Override_ID</code></li>
	 * <li><code>C_BPartner_ID</code></li>
	 * </ul>
	 *
	 * @param olCand
	 * @return id or {@code null}.
	 */
	BPartnerId getBillBPartnerEffectiveId(I_C_OLCand olCand);

	/**
	 *
	 * @param olCand
	 * @param clazz
	 * @return
	 * @see #getBillBPartnerEffectiveId(I_C_OLCand)
	 */
	<T extends I_C_BPartner> T getBill_BPartner_Effective(I_C_OLCand olCand, Class<T> clazz);

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>Bill_Location_ID</code></li>
	 * <li><code>C_BP_Location_Override_ID</code></li>
	 * <li><code>C_BPartner_Location_ID</code></li>
	 * </ul>
	 */
	BPartnerLocationId getBillLocationEffectiveId(I_C_OLCand olCand);

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
	 */
	BPartnerContactId getBillContactEffectiveId(I_C_OLCand olCand);

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
	 * @return id of {@code null}.
	 */
	BPartnerId getDropShipBPartnerEffectiveId(I_C_OLCand olCand);

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
	 */
	BPartnerLocationId getDropShipLocationEffectiveId(I_C_OLCand olCand);

	/**
	 * See {@link #getDropShipLocationEffectiveId(I_C_OLCand)}.
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
	 */
	BPartnerContactId getDropShipContactEffectiveId(I_C_OLCand olCand);

	/**
	 * Returns, falling back to the next if not set:
	 * <ul>
	 * <li><code>M_Product_Override_ID</code></li>
	 * <li><code>M_Product_ID</code></li>
	 * </ul>
	 */
	ProductId getM_Product_Effective_ID(I_C_OLCand olCand);

	/**
	 * Like {@link #getM_Product_Effective_ID(I_C_OLCand)}, but returns the actual product.
	 *
	 * @param olCand
	 * @return
	 */
	I_M_Product getM_Product_Effective(I_C_OLCand olCand);

	/**
	 * Returns <code>C_UOM_ID</code> if <code>IsManualPrice='Y'</code> and {@link #getRecordOrStockUOMId(I_C_OLCand)} otherwise.
	 */
	int getC_UOM_Effective_ID(I_C_OLCand olCand);

	UomId getRecordOrStockUOMId(I_C_OLCand olCandRecord);

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
	 * @return id or {@code null}.
	 */
	BPartnerId getHandOverPartnerEffectiveId(I_C_OLCand olCand);

	/**
	 * Like {@link #getHandOverPartnerEffectiveId(I_C_OLCand)}, but returns the actual partner.
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

	BPartnerLocationId getHandOverLocationEffectiveId(I_C_OLCand olCand);

	/**
	 * Like {@link #getHandOverLocationEffectiveId(I_C_OLCand)}, but returns the actual location.
	 *
	 * @param olCand
	 * @return
	 */

	I_C_BPartner_Location getHandOver_Location_Effective(I_C_OLCand olCand);
}
