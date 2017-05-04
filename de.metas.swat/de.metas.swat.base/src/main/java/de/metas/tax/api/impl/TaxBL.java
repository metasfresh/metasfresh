package de.metas.tax.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.TaxNoExemptFoundException;
import org.adempiere.exceptions.TaxNotFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MLocation;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.adempiere.service.ICountryAreaBL;
import de.metas.adempiere.service.ICountryDAO;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import de.metas.tax.api.ITaxDAO;

public class TaxBL implements de.metas.tax.api.ITaxBL
{
	private static final transient Logger log = LogManager.getLogger(TaxBL.class);

	/**
	 * Do not attempt to retrieve the C_Tax for an order (i.e invoicing is done at a different time - 1 year - from the order)<br>
	 * Also note that packaging material receipts don't have an order line and if this one had, no IC would be created for it by this handler.<br>
	 * Instead, always rely on taxing BL to bind the tax to the invoice candidate
	 *
	 * Try to rely on the tax category from the pricing result<br>
	 * 07739: If that's not available, then throw an exception; don't attempt to retrieve the German tax because that method proved to return a wrong result
	 */
	@Override
	public int getTax(final Properties ctx,
			final Object model,
			final int taxCategoryId,
			final int productId,
			final int chargeId,
			final Timestamp billDate,
			final Timestamp shipDate,
			final int adOrgId,
			final I_M_Warehouse warehouse,
			final int billC_BPartner_Location_ID,
			final int shipC_BPartner_Location_ID,
			final boolean isSOTrx,
			final String trxName)
	{
		if (taxCategoryId > 0)
		{
			final int countryFromId;
			if (warehouse != null)
			{
				final I_C_Location locationFrom = Services.get(IWarehouseBL.class).getC_Location(warehouse);
				countryFromId = locationFrom.getC_Country_ID();
			}
			else
			{
				// 03378 : retrieve default country ID for org_ID
				final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
				if (bPartnerOrgBL.retrieveOrgLocation(ctx, adOrgId, null) != null) // 03378 : Temporary. Will be removed when OrgBP_Location is mandatory.
				{
					countryFromId = bPartnerOrgBL.retrieveOrgLocation(ctx, adOrgId, null).getC_Country_ID();
				}
				else
				{
					// 03378 : Temporary. Will be removed when OrgBP_Location is mandatory.
					countryFromId = Services.get(ICountryDAO.class).getDefault(ctx).getC_Country_ID();
				}
			}

			final I_C_BPartner_Location bpLocTo = InterfaceWrapperHelper.create(ctx, billC_BPartner_Location_ID, I_C_BPartner_Location.class, trxName);

			final int taxIdForCategory = retrieveTaxIdForCategory(ctx
					, countryFromId
					, adOrgId
					, bpLocTo
					, billDate
					, taxCategoryId
					, isSOTrx
					, trxName, false);
			if (taxIdForCategory > 0)
			{
				return taxIdForCategory;
			}
		}

		final AdempiereException ex =
				new AdempiereException(StringUtils.formatMessage(
						"Could not retrieve C_Tax_ID; will return the Tax-Not-Found-C_Tax_ID; Method paratmers:"
								+ "model= {0}, taxCategoryId={1}, productId={2}, chargeId={3}, billDate={4}, shipDate={5}, adOrgId={6}, "
								+ "warehouse={7}, billC_BPartner_Location_ID={8}, shipC_BPartner_Location_ID={9}, isSOTrx={10}, trxName={11}",
						model,
						taxCategoryId,
						productId,
						chargeId,
						billDate,
						shipDate,
						adOrgId,
						warehouse,
						billC_BPartner_Location_ID,
						shipC_BPartner_Location_ID,
						isSOTrx,
						trxName));
		log.warn("getTax - error: ", ex);

		// 07814
		// If we got here, it means that no tax was found to satisfy the conditions
		// In this case, the Tax_Not_Found placeholder will be returned
		final I_C_Tax taxNotFound = Services.get(ITaxDAO.class).retrieveNoTaxFound(ctx);
		return taxNotFound.getC_Tax_ID();
	}

	/**
	 * Important: This implementation makes two assumptions:
	 * <ul>
	 * <li>You are inside the EU</li>
	 * </ul>
	 */
	@Override
	public int retrieveTaxIdForCategory(final Properties ctx,
			final int countryFromId,
			final int orgId,
			final I_C_BPartner_Location bpLocTo,
			final Timestamp date,
			final int taxCategoryId,
			final boolean isSOTrx,
			final String trxName,
			final boolean throwEx)
	{
		Check.assumeNotNull(bpLocTo, "bpLocTo not null");
		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(bpLocTo.getC_BPartner(), I_C_BPartner.class);

		final boolean hasTaxCertificate = !Check.isEmpty(bPartner.getVATaxID());

		final I_C_Location locationTo = MLocation.get(ctx, bpLocTo.getC_Location_ID(), trxName);

		final boolean toEULocation = Services.get(ICountryAreaBL.class).isMemberOf(ctx,
				ICountryAreaBL.COUNTRYAREAKEY_EU,
				locationTo.getC_Country().getCountryCode(),
				date);

		final int countryToId = locationTo.getC_Country_ID();

		final boolean toSameCountry = countryToId == countryFromId;

		final StringBuilder sql = new StringBuilder();

		final List<Object> params = new ArrayList<Object>();
		sql.append("validFrom < ? ");
		params.add(date);

		if (countryFromId > 0)
		{
			sql.append(" AND ").append(I_C_Tax.COLUMNNAME_C_Country_ID).append("=? ");
			params.add(countryFromId);
		}
		else
		{
			sql.append(" AND ").append(I_C_Tax.COLUMNNAME_C_Country_ID).append(" IS NULL ");
		}

		sql.append(" AND C_TaxCategory_ID=?");
		params.add(taxCategoryId);

		if (toSameCountry)
		{
			// hardcoded from/to germany
			sql.append(" AND To_Country_ID=" + countryToId);
		}
		else if (toEULocation)
		{
			sql.append(
					" AND (To_Country_ID IS NULL OR To_Country_ID="
							+ countryToId).append(") ");
			sql.append(" AND IsToEULocation = 'Y' ");

			if (hasTaxCertificate)
			{
				sql.append(" AND RequiresTaxCertificate = 'Y' ");
			}
			else
			{
				sql.append(" AND RequiresTaxCertificate = 'N' ");
			}
		}
		else
		{
			// rest of the world
			sql.append(" AND (To_Country_ID IS NULL OR To_Country_ID =");
			// Abweichungen zu Drittland finden finden wenn definiert
			sql.append(countryToId + ") ");
			sql.append(" AND IsToEULocation = 'N' ");
		}

		sql.append(" AND SOPOType in ('B', ");
		if (isSOTrx)
		{
			sql.append("'S')");
		}
		else
		{
			sql.append("'P')");
		}

		if (orgId > 0)
		{
			sql.append(" AND (AD_Org_ID=0 OR AD_Org_ID=" + orgId + ") ");
		}

		final String orderBy = " AD_Org_ID DESC, To_Country_ID, validFrom DESC ";

		final IQuery<I_C_Tax> query = new TypedSqlQuery<I_C_Tax>(ctx, I_C_Tax.class, sql.toString(), trxName)
				.setParameters(params)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy);
		final int taxId = query.firstId();

		if (taxId <= 0)
		{
			final AdempiereException ex = new AdempiereException("@NotFound@ @C_Tax_ID@" + "\nQuery: " + query);
			if(throwEx)
			{
				throw ex;
			}
			log.warn("Tax not found. Return -1.", ex);
			return -1;
		}

		return taxId;
	}

	private int getGermanTax(final Properties ctx,
			final int productId,
			final int chargeId,
			final Timestamp billDate,
			final Timestamp shipDate,
			final int org_ID,
			final int warehouseId,
			final int billC_BPartner_Location_ID,
			final int shipC_BPartner_Location_ID,
			final boolean isSOTrx)
	{
		//
		// If organization is tax exempted then we will return the Tax Exempt for that organization (03871)
		final I_C_BPartner orgBPartner = Services.get(IBPartnerDAO.class).retrieveOrgBPartner(ctx, org_ID, I_C_BPartner.class, ITrx.TRXNAME_None);
		log.debug("Org BP: {}", orgBPartner);
		if (Services.get(ITaxDAO.class).retrieveIsTaxExempt(orgBPartner, billDate))
		{
			final int taxExemptId = getExemptTax(ctx, org_ID);
			log.debug("Org is tax exempted => C_Tax_ID={}", taxExemptId);
			return taxExemptId;
		}

		// Check Partner/Location
		log.debug("Ship BP_Location={}", shipC_BPartner_Location_ID);

		int taxId = 0;

		final I_C_BPartner_Location shipBPLocation = new MBPartnerLocation(ctx, shipC_BPartner_Location_ID, null);

		final I_C_Location locationTo = shipBPLocation.getC_Location();
		final boolean isEULocation = Services.get(ICountryAreaBL.class).isMemberOf(ctx,
				ICountryAreaBL.COUNTRYAREAKEY_EU,
				locationTo.getC_Country().getCountryCode(),
				billDate);

		int countryFromId = -1;
		if (warehouseId > 0)
		{
			final I_M_Warehouse warehouse = InterfaceWrapperHelper.create(ctx, warehouseId, I_M_Warehouse.class, ITrx.TRXNAME_None);
			final I_C_Location location = Services.get(IWarehouseBL.class).getC_Location(warehouse);
			countryFromId = location.getC_Country_ID();
		}
		if (countryFromId == -1)
		{
			// 03378 : retrieve default country ID for org_ID
			final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
			if (bPartnerOrgBL.retrieveOrgLocation(ctx, org_ID, null) != null) // 03378 : Temporary. Will be removed when OrgBP_Location is mandatory.
			{
				countryFromId = bPartnerOrgBL.retrieveOrgLocation(ctx, org_ID, null).getC_Country_ID();
			}
			else
			{
				// 03378 : Temporary. Will be removed when OrgBP_Location is mandatory.
				countryFromId = Services.get(ICountryDAO.class).getDefault(ctx).getC_Country_ID();
			}
		}

		// bp has tax certificate?
		final I_C_BPartner bp = InterfaceWrapperHelper.create(ctx, shipBPLocation.getC_BPartner_ID(), I_C_BPartner.class, null);
		final boolean hasTaxCertificate = !Check.isEmpty(bp.getVATaxID(), true);

		// String sql = "SELECT DISTINCT t.C_Tax_ID,t.validFrom, t.To_Country_ID FROM C_Tax t,M_Product pr,C_Charge c " +
		// " WHERE t.validFrom < ? AND t.isActive='Y' AND t.C_Country_ID = 101";

		String sql = "SELECT t.C_Tax_ID, t.validFrom, t.To_Country_ID "
				// metas start rc: 03083: retrieve the C_TaxCategory for using it in the error message
				+ ", t.C_TaxCategory_ID " +
				// metas end
				"FROM C_Tax t " +
				"LEFT JOIN M_ProductPrice pp ON pp.C_TaxCategory_ID = t.C_TaxCategory_ID " +
				"LEFT JOIN M_Pricelist_Version plv on pp.M_Pricelist_Version_ID = plv.M_Pricelist_Version_ID " +
				"LEFT JOIN M_Pricelist pl on plv.M_Pricelist_ID = pl.M_Pricelist_ID " +
				"LEFT JOIN C_Charge c ON c.C_TaxCategory_ID = t.C_TaxCategory_ID " +
				"WHERE t.validFrom < ? AND t.isActive='Y' AND t.C_Country_ID = " + countryFromId + " ";

		if (locationTo.getC_Country_ID() == countryFromId)
		{
			sql += " AND t.To_Country_ID = " + locationTo.getC_Country_ID() + " ";
		}
		else if (isEULocation)
		{
			// To_Country_ID should be null for all other taxes
			sql += " AND (pl.C_Country_ID IS NULL OR pl.C_Country_ID = ";
			sql += locationTo.getC_Country().getC_Country_ID() + ") ";
			sql += " AND (t.To_Country_ID IS NULL OR t.To_Country_ID = ";
			// metas: Abweichungen zu EU finden wenn definiert
			sql += locationTo.getC_Country().getC_Country_ID() + ") ";
			sql += " AND t.IsToEULocation = 'Y' ";
			if (hasTaxCertificate)
			{
				sql += " AND t.RequiresTaxCertificate = 'Y' ";
			}
			else
			{
				sql += " AND t.RequiresTaxCertificate = 'N' ";
			}
		}
		else
		{
			// rest of the world
			sql += " AND (pl.C_Country_ID IS NULL OR pl.C_Country_ID = ";
			sql += locationTo.getC_Country().getC_Country_ID() + ") ";
			sql += " AND (t.To_Country_ID IS NULL OR t.To_Country_ID =";
			// Abweichungen zu Drittland finden finden wenn definiert
			sql += locationTo.getC_Country().getC_Country_ID() + ") ";
			sql += " AND t.IsToEULocation = 'N' ";
		}
		// product or charge
		if (productId != 0)
		{
			sql += " AND t.C_TaxCategory_ID = pp.C_TaxCategory_ID " +
					" AND pp.M_Product_ID = ? ";
		}
		else
		{
			sql += " AND t.C_TaxCategory_ID = c.C_TaxCategory_ID " +
					" AND c.C_Charge_ID = ? ";
		}

		if (org_ID > 0)
		{
			sql += " AND (t.AD_Org_ID=0 OR t.AD_Org_ID=" + org_ID + ") ";
		}

		sql += " ORDER BY t.AD_Org_ID DESC, t.To_Country_ID, t.validFrom DESC ";
		log.debug("getGermanTax - sql={}", sql);

		// get tax id
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// metas start: rc: 03083: get the retrieved C_TaxCategory
		int taxCategoryID = 0;
		// metas end: rc: 03083
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setTimestamp(1, billDate);
			if (productId != 0)
			{
				pstmt.setInt(2, productId);
			}
			else
			{
				pstmt.setInt(2, chargeId);
			}

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				taxId = rs.getInt(1);
				taxCategoryID = rs.getInt(4);
			}
		}
		catch (final SQLException e)
		{
			log.error("getGermanTax - error: ", e);
			throw new DBException(e, sql, new Object[] { billDate, productId != 0 ? productId : chargeId });
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		if (taxId <= 0)
		{
			throw new TaxNotFoundException(productId, chargeId,
					// metas start: rc: 03083: call the TaxNotFoundException with parameter C_TaxCategory, too.
					taxCategoryID,
					// metas end: rc: 03083
					isSOTrx, shipDate, 0, shipBPLocation.getC_Location_ID(), billDate, 0,
					new MBPartnerLocation(ctx, billC_BPartner_Location_ID, null).getC_Location_ID());
		}
		return taxId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.adempiere.tax.api.ITaxBL#get(java.util.Properties, int, int, java.sql.Timestamp, java.sql.Timestamp, int, int, int, int, boolean)
	 */
	@Override
	public int get(final Properties ctx,
			final int M_Product_ID,
			final int C_Charge_ID,
			final Timestamp billDate,
			final Timestamp shipDate,
			final int AD_Org_ID,
			final int M_Warehouse_ID,
			final int billC_BPartner_Location_ID,
			final int shipC_BPartner_Location_ID,
			final boolean IsSOTrx)
	{
		if (M_Product_ID > 0 || C_Charge_ID > 0)
		{
			return getGermanTax(ctx, M_Product_ID, C_Charge_ID, billDate, shipDate, AD_Org_ID, M_Warehouse_ID, billC_BPartner_Location_ID, shipC_BPartner_Location_ID, IsSOTrx);
		}
		else
		{
			return getExemptTax(ctx, AD_Org_ID);
		}
	}

	/**
	 * Get Exempt Tax Code
	 * @param ctx context
	 * @param AD_Org_ID org to find client
	 * @return C_Tax_ID
	 * @throws TaxNoExemptFoundException if no tax exempt found
	 */
	@Override
	public int getExemptTax (Properties ctx, int AD_Org_ID)
	{
		final String sql = "SELECT t.C_Tax_ID "
			+ "FROM C_Tax t"
			+ " INNER JOIN AD_Org o ON (t.AD_Client_ID=o.AD_Client_ID) "
			+ "WHERE t.IsTaxExempt='Y' AND o.AD_Org_ID=? "
			+ "ORDER BY t.Rate DESC";
		int C_Tax_ID = DB.getSQLValueEx(null, sql, AD_Org_ID);
		log.debug("getExemptTax - TaxExempt=Y - C_Tax_ID={}", C_Tax_ID);
		if (C_Tax_ID <= 0)
		{
			throw new TaxNoExemptFoundException(AD_Org_ID);
		}
		else
		{
			return C_Tax_ID;
		}
	}	//	getExemptTax

	@Override
	public BigDecimal calculateTax(final I_C_Tax tax, final BigDecimal amount, final boolean taxIncluded, final int scale)
	{
		// Null Tax
		if (tax.getRate().signum() == 0)
		{
			return Env.ZERO;
		}

		BigDecimal multiplier = tax.getRate().divide(Env.ONEHUNDRED, 12, BigDecimal.ROUND_HALF_UP);

		final BigDecimal taxAmt;
		if (tax.isWholeTax())
		{
			Check.assume(taxIncluded, "TaxIncluded shall be set when IsWholeTax is set");
			taxAmt = amount;
		}
		else if (!taxIncluded)	// $100 * 6 / 100 == $6 == $100 * 0.06
		{
			taxAmt = amount.multiply(multiplier);
		}
		else
		// $106 - ($106 / (100+6)/100) == $6 == $106 - ($106/1.06)
		{
			multiplier = multiplier.add(Env.ONE);
			final BigDecimal base = amount.divide(multiplier, 12, BigDecimal.ROUND_HALF_UP);
			taxAmt = amount.subtract(base);
		}

		final BigDecimal taxAmtFinal = taxAmt.setScale(scale, BigDecimal.ROUND_HALF_UP);

		log.debug("calculateTax: amount={} (incl={}, mult={}, scale={}) = {} [{}]", amount, taxIncluded, multiplier, scale, taxAmtFinal, taxAmt);

		return taxAmtFinal;
	}	// calculateTax

	@Override
	public BigDecimal calculateBaseAmt(final I_C_Tax tax, final BigDecimal amount, final boolean taxIncluded, final int scale)
	{
		if (tax.isWholeTax())
		{
			Check.assume(taxIncluded, "TaxIncluded shall be set when IsWholeTax is set");
			return BigDecimal.ZERO;
		}
		if (!taxIncluded)
		{
			// the given amount is without tax => don't subtract the tax that is no included
			return amount;
		}
		final BigDecimal taxAmt = calculateTax(tax, amount, taxIncluded, scale);
		final BigDecimal baseAmt = amount.subtract(taxAmt);
		return baseAmt;
	}

	@Override
	public void setupIfIsWholeTax(final I_C_Tax tax)
	{
		if (!tax.isWholeTax())
		{
			return;
		}

		tax.setRate(BigDecimal.valueOf(100));
		tax.setIsTaxExempt(false);
		tax.setIsDocumentLevel(true);
		// tax.setIsSalesTax(false); // does not matter
	}
}
