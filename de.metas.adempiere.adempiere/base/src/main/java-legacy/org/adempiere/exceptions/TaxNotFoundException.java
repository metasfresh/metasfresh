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
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_Product;
import org.compiere.model.MLocation;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.service.ICountryDAO;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.util.Services;
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
		super(ImmutableTranslatableString.empty());

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
	protected ITranslatableString buildMessage()
	{
		final TranslatableStringBuilder message = TranslatableStringBuilder.newInstance();
		message.appendADMessage(MSG_TaxNotFound);

		//
		if (productId > 0)
		{
			final I_M_Product product = InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, ITrx.TRXNAME_None);
			final String productValue = product != null ? product.getValue() : "<" + productId + ">";
			message.append(" - ").appendADElement("M_Product_ID").append(": ").append(productValue);
		}
		if (chargeId > 0)
		{
			final String chargeName = InterfaceWrapperHelper.create(Env.getCtx(), chargeId, I_C_Charge.class, ITrx.TRXNAME_None).getName();
			message.append(" - ").appendADElement("C_Charge_ID").append(": ").append(chargeName);
		}

		//
		if (taxCategoryId > 0)
		{
			final String taxCategoryName = getTaxCategoryString(taxCategoryId);
			message.append(" - ").appendADElement("C_TaxCategory_ID").append(": ").append(taxCategoryName);
		}

		if (isSOTrx != null)
		{
			message.append(" - ").appendADElement("IsSOTrx").append(": ").append(isSOTrx);
		}

		if (orgId != null && orgId >= 0)
		{
			final String orgName = Services.get(IOrgDAO.class).retrieveOrgName(orgId);
			message.append(" - ").appendADElement("AD_Org_ID").append(": ").append(orgName);
		}

		//
		// Ship info
		if (shipDate != null)
		{
			message.append(" - ").appendADElement("ShipDate").append(": ").appendDate(shipDate);
		}
		if (shipFromC_Location_ID > 0 || shipFromCountryId > 0)
		{
			final String locationString = getLocationString(shipFromC_Location_ID, shipFromCountryId);
			message.append(" - ").appendADElement("ShipFrom").append(": ").append(locationString);
		}
		if (shipToC_Location_ID > 0)
		{
			final String locationString = getLocationString(shipToC_Location_ID);
			message.append(" - ").appendADElement("ShipTo").append(": ").append(locationString);
		}

		//
		// Bill info
		if (billDate != null)
		{
			message.append(" - ").appendADElement("BillDate").append(": ").appendDate(billDate);
		}
		if (billFromC_Location_ID > 0 || billFromCountryId > 0)
		{
			final String locationString = getLocationString(billFromC_Location_ID, billFromCountryId);
			message.append(" - ").appendADElement("BillFrom").append(": ").append(locationString);
		}
		if (billToC_Location_ID > 0)
		{
			final String locationString = getLocationString(billToC_Location_ID);
			message.append(" - ").appendADElement("BillTo").append(": ").append(locationString);
		}

		//
		//
		return message.build();
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
