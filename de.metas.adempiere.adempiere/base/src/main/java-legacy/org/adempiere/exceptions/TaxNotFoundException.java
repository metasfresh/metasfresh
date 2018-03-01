/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 2009 SC ARHIPAC SERVICE SRL. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package org.adempiere.exceptions;

import java.time.LocalDate;
import java.util.Date;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_Product;
import org.compiere.model.MLocation;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.service.ICountryDAO;
import lombok.Builder;

/**
 * Throw by Tax Engine where no tax found for given criteria
 *
 * @author Teo Sarca, www.arhipac.ro
 *         <li>FR [ 2758097 ] Implement TaxNotFoundException
 */
public class TaxNotFoundException extends AdempiereException
{
	private static final long serialVersionUID = -5471615720092096644L;

	public static final String MSG_TaxNotFound = "TaxNotFound";
	public static final String MSG_TaxCategoryNotFound = "TaxCategoryNotFound";

	private final String adMessage;

	private final int productId;
	private final int chargeId;

	private final int taxCategoryId;
	private final Boolean isSOTrx;

	private final Integer orgId;

	private final LocalDate shipDate;
	private final int shipFromCountryId;
	private final int shipFromC_Location_ID;
	private final int shipToC_Location_ID;

	private final LocalDate billDate;
	private final int billFromCountryId;
	private final int billFromC_Location_ID;
	private final int billToC_Location_ID;

	@Builder
	private TaxNotFoundException(
			final String adMessage,
			final int productId,
			final int chargeId,
			final int taxCategoryId,
			final Boolean isSOTrx,
			//
			final Integer orgId,
			//
			final Date shipDate,
			final int shipFromC_Location_ID,
			final int shipFromCountryId,
			final int shipToC_Location_ID,
			//
			final Date billDate,
			final int billFromC_Location_ID,
			final int billFromCountryId,
			final int billToC_Location_ID)
	{
		this.adMessage = adMessage != null ? adMessage : MSG_TaxNotFound;

		this.productId = productId;
		setParameter("productId", productId > 0 ? productId : null);
		this.chargeId = chargeId;
		setParameter("chargeId", chargeId > 0 ? chargeId : null);

		this.taxCategoryId = taxCategoryId;
		setParameter("taxCategoryId", taxCategoryId > 0 ? taxCategoryId : null);
		this.isSOTrx = isSOTrx;
		setParameter("isSOTrx", isSOTrx);

		this.orgId = orgId;
		setParameter("orgId", orgId);

		this.shipDate = TimeUtil.asLocalDate(shipDate);
		setParameter("shipDate", shipDate);
		this.shipFromC_Location_ID = shipFromC_Location_ID;
		setParameter("shipFromC_Location_ID", shipFromC_Location_ID > 0 ? shipFromC_Location_ID : null);
		this.shipFromCountryId = shipFromCountryId;
		setParameter("shipFromCountryId", shipFromCountryId > 0 ? shipFromCountryId : null);
		this.shipToC_Location_ID = shipToC_Location_ID;
		setParameter("shipToC_Location_ID", shipToC_Location_ID > 0 ? shipToC_Location_ID : null);

		this.billDate = TimeUtil.asLocalDate(billDate);
		setParameter("billDate", billDate);
		this.billFromC_Location_ID = billFromC_Location_ID;
		setParameter("billFromC_Location_ID", billFromC_Location_ID > 0 ? billFromC_Location_ID : null);
		this.billFromCountryId = billFromCountryId;
		setParameter("billFromCountryId", billFromCountryId > 0 ? billFromCountryId : null);
		this.billToC_Location_ID = billToC_Location_ID;
		setParameter("billToC_Location_ID", billToC_Location_ID > 0 ? billToC_Location_ID : null);
	}

	@Override
	protected String buildMessage()
	{
		final StringBuilder msg = new StringBuilder("@").append(adMessage).append("@");

		//
		if (productId > 0)
		{
			final I_M_Product product = InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, ITrx.TRXNAME_None);
			final String productValue = product != null ? product.getValue() : "<" + productId + ">";
			msg.append(" - @M_Product_ID@:").append(productValue);
		}
		if (chargeId > 0)
		{
			msg.append(" - @C_Charge_ID@:").append(InterfaceWrapperHelper.create(Env.getCtx(), chargeId, I_C_Charge.class, ITrx.TRXNAME_None).getName());
		}

		//
		if (taxCategoryId > 0)
		{
			msg.append(" - @C_TaxCategory_ID@:").append(getTaxCategoryString(taxCategoryId));
		}

		if (isSOTrx != null)
		{
			msg.append(", @IsSOTrx@:@").append(isSOTrx ? "Y" : "N").append("@");
		}

		if (orgId != null && orgId >= 0)
		{
			final String orgName = Services.get(IOrgDAO.class).retrieveOrgName(orgId);
			msg.append(", @AD_Org_ID@:").append(orgName);
		}

		//
		// Ship info
		if (shipDate != null)
		{
			msg.append(", @ShipDate@:").append(shipDate);
		}
		if (shipFromC_Location_ID > 0 || shipFromCountryId > 0)
		{
			msg.append(", @ShipFrom@:").append(getLocationString(shipFromC_Location_ID, shipFromCountryId));
		}
		if (shipToC_Location_ID > 0)
		{
			msg.append(", @ShipTo@").append(getLocationString(shipToC_Location_ID));
		}

		//
		// Bill info
		if (billDate != null)
		{
			msg.append(", @BillDate@:").append(billDate);
		}
		if (billFromC_Location_ID > 0 || billFromCountryId > 0)
		{
			msg.append(", @BillFrom@:").append(getLocationString(billFromC_Location_ID, billFromCountryId));
		}
		if (billToC_Location_ID > 0)
		{
			msg.append(", @BillTo@:").append(getLocationString(billToC_Location_ID));
		}

		//
		//
		return msg.toString();
	}

	private static final String getTaxCategoryString(final int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID <= 0)
		{
			return "?";
		}
		final I_C_TaxCategory taxCategory = InterfaceWrapperHelper.loadOutOfTrx(C_TaxCategory_ID, I_C_TaxCategory.class);
		if (taxCategory == null)
		{
			return "?";
		}

		return taxCategory.getName();
	}

	private static final String getLocationString(final int locationId, final int fallbackCountryId)
	{
		if (locationId > 0)
		{
			return getLocationString(locationId);
		}
		if (fallbackCountryId > 0)
		{
			final I_C_Country country = Services.get(ICountryDAO.class).get(Env.getCtx(), fallbackCountryId);
			if (country != null)
			{
				return country.getName();
			}
		}

		return "?";
	}

	private static final String getLocationString(final int locationId)
	{
		if (locationId <= 0)
		{
			return "?";
		}

		final MLocation loc = MLocation.get(Env.getCtx(), locationId, null);
		if (loc == null || loc.get_ID() != locationId)
		{
			return "?";
		}

		return loc.toString();
	}

}
