package de.metas.tax.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Properties;

import de.metas.logging.TableRecordMDC;
import de.metas.tax.api.TaxId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.X_C_Tax;
import org.compiere.model.X_C_TaxCategory;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.location.CountryId;
import de.metas.location.ICountryAreaBL;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxNotFoundException;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.slf4j.MDC;

public class TaxBL implements de.metas.tax.api.ITaxBL
{
	private static final Logger log = LogManager.getLogger(TaxBL.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

	@Override
	public I_C_Tax getTaxById(final TaxId taxId)
	{
		return taxDAO.getTaxById(taxId);
	}

	/**
	 * Do not attempt to retrieve the C_Tax for an order (i.e invoicing is done at a different time - 1 year - from the order)<br>
	 * Also note that packaging material receipts don't have an order line and if this one had, no IC would be created for it by this handler.<br>
	 * Instead, always rely on taxing BL to bind the tax to the invoice candidate
	 * <p>
	 * Try to rely on the tax category from the pricing result<br>
	 * 07739: If that's not available, then throw an exception; don't attempt to retrieve the German tax because that method proved to return a wrong result
	 */
	@Override
	public int getTax(final Properties ctx,
			final Object model,
			final TaxCategoryId taxCategoryId,
			final int productId,
			@NonNull final Timestamp shipDate,
			@NonNull final OrgId orgId,
			final WarehouseId warehouseId,
			final int shipC_BPartner_Location_ID,
			final boolean isSOTrx)
	{
		if (taxCategoryId != null)
		{
			final CountryId countryFromId;
			if (warehouseId != null)
			{
				countryFromId = Services.get(IWarehouseBL.class).getCountryId(warehouseId);
			}
			else
			{
				final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
				final CountryId orgCountryId = bPartnerOrgBL.getOrgCountryId(orgId);
				if (orgCountryId != null)
				{
					countryFromId = orgCountryId;
				}
				else
				{
					countryFromId = Services.get(ICountryDAO.class).getDefaultCountryId();
				}
			}

			final I_C_BPartner_Location bpLocTo = loadOutOfTrx(shipC_BPartner_Location_ID, I_C_BPartner_Location.class);

			final int taxIdForCategory = retrieveTaxIdForCategory(ctx,
					countryFromId,
					orgId,
					bpLocTo,
					shipDate,
					taxCategoryId,
					isSOTrx,
					false // throwEx
			);
			if (taxIdForCategory > 0)
			{
				return taxIdForCategory;
			}
		}

		final AdempiereException ex = new AdempiereException(StringUtils.formatMessage(
				"Could not retrieve C_Tax_ID; will return the Tax-Not-Found-C_Tax_ID; Method paratmers:"
						+ "model= {}, taxCategoryId={}, productId={}, shipDate={}, adOrgId={}, "
						+ "warehouse={}, shipC_BPartner_Location_ID={}, isSOTrx={}, trxName={}",
				model,
				taxCategoryId,
				productId,
				shipDate,
				orgId,
				warehouseId,
				shipC_BPartner_Location_ID,
				isSOTrx));
		log.warn("getTax - error: ", ex);

		// 07814
		// If we got here, it means that no tax was found to satisfy the conditions
		// In this case, the Tax_Not_Found placeholder will be returned
		final I_C_Tax taxNotFound = taxDAO.retrieveNoTaxFound(ctx);
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
			final CountryId countryFromId,
			final OrgId orgId,
			@NonNull final I_C_BPartner_Location bpLocTo,
			@NonNull final Timestamp date,
			final TaxCategoryId taxCategoryId,
			final boolean isSOTrx,
			final boolean throwEx)
	{
		final I_C_BPartner bPartner = Services.get(IBPartnerDAO.class).getById(bpLocTo.getC_BPartner_ID());

		final boolean hasTaxCertificate = !Check.isEmpty(bPartner.getVATaxID());

		final I_C_Location locationTo = Services.get(ILocationDAO.class).getById(LocationId.ofRepoId(bpLocTo.getC_Location_ID()));
		final CountryId countryToId = CountryId.ofRepoId(locationTo.getC_Country_ID());
		final I_C_Country countryTo = Services.get(ICountryDAO.class).getById(countryToId);
		final boolean toEULocation = Services.get(ICountryAreaBL.class).isMemberOf(ctx,
				ICountryAreaBL.COUNTRYAREAKEY_EU,
				countryTo.getCountryCode(),
				date);

		final boolean toSameCountry = Objects.equals(countryToId, countryFromId);

		final IQueryBuilder<I_C_Tax> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Tax.class, ctx, ITrx.TRXNAME_None)
				.addCompareFilter(I_C_Tax.COLUMNNAME_ValidFrom, Operator.LESS_OR_EQUAL, date)
				.addOnlyActiveRecordsFilter();

		if (countryFromId != null)
		{
			queryBuilder.addEqualsFilter(I_C_Tax.COLUMNNAME_C_Country_ID, countryFromId);
		}
		else
		{
			queryBuilder.addEqualsFilter(I_C_Tax.COLUMNNAME_C_Country_ID, null);
		}

		queryBuilder.addEqualsFilter(I_C_Tax.COLUMNNAME_C_TaxCategory_ID, taxCategoryId);

		if (toSameCountry)
		{
			queryBuilder.addEqualsFilter(I_C_Tax.COLUMNNAME_To_Country_ID, countryToId);
		}
		else if (toEULocation)
		{
			queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_To_Country_ID, countryToId, null);
			queryBuilder.addEqualsFilter(I_C_Tax.COLUMNNAME_IsToEULocation, true);

			if (hasTaxCertificate)
			{
				queryBuilder.addEqualsFilter(I_C_Tax.COLUMNNAME_RequiresTaxCertificate, true);
			}
			else
			{
				queryBuilder.addEqualsFilter(I_C_Tax.COLUMNNAME_RequiresTaxCertificate, false);
			}
		}
		else
		{
			queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_To_Country_ID, countryToId, null);
			queryBuilder.addEqualsFilter(I_C_Tax.COLUMNNAME_IsToEULocation, false);
		}

		if (isSOTrx)
		{
			queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_SOPOType, X_C_Tax.SOPOTYPE_Both, X_C_Tax.SOPOTYPE_SalesTax);
		}
		else
		{
			queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_SOPOType, X_C_Tax.SOPOTYPE_Both, X_C_Tax.SOPOTYPE_PurchaseTax);
		}

		if (orgId != null)
		{
			queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY);
		}

		final IQuery<I_C_Tax> query = queryBuilder
				.orderBy()
				.addColumnDescending(I_C_Tax.COLUMNNAME_AD_Org_ID)
				.addColumn(I_C_Tax.COLUMNNAME_To_Country_ID)
				.addColumnDescending(I_C_Tax.COLUMNNAME_ValidFrom)
				.endOrderBy()
				.create();
		final int taxId = query.firstId();
		if (taxId <= 0)
		{
			TaxNotFoundException.builder()
					.orgId(orgId)
					.taxCategoryId(taxCategoryId)
					.isSOTrx(isSOTrx)
					.billDate(date)
					.shipFromCountryId(countryFromId)
					.billToC_Location_ID(locationTo.getC_Location_ID())
					.build()
					.setParameter("query", query.toString())
					.throwOrLogWarning(throwEx, log);
			return -1;
		}

		return taxId;
	}

	private int getGermanTax(final Properties ctx,
			final ProductId productId,
			final int chargeId,
			final Timestamp billDate,
			final Timestamp shipDate,
			@NonNull final OrgId orgId,
			final WarehouseId warehouseId,
			final int billC_BPartner_Location_ID,
			final int shipC_BPartner_Location_ID,
			final boolean isSOTrx)
	{
		//
		// If organization is tax exempted then we will return the Tax Exempt for that organization (03871)
		final I_C_BPartner orgBPartner = Services.get(IBPartnerDAO.class).retrieveOrgBPartner(ctx, orgId.getRepoId(), I_C_BPartner.class, ITrx.TRXNAME_None);
		log.debug("Org BP: {}", orgBPartner);

		final ICountryAreaBL countryAreaBL = Services.get(ICountryAreaBL.class);

		if (taxDAO.retrieveIsTaxExemptSmallBusiness(BPartnerId.ofRepoId(orgBPartner.getC_BPartner_ID()), billDate))
		{
			final int taxExemptId = taxDAO.retrieveExemptTax(orgId).getRepoId();
			log.debug("Org is tax exempted => C_Tax_ID={}", taxExemptId);
			return taxExemptId;
		}

		// Check Partner/Location
		log.debug("Ship BP_Location={}", shipC_BPartner_Location_ID);

		int taxId = 0;

		final I_C_BPartner_Location shipBPLocation = new MBPartnerLocation(ctx, shipC_BPartner_Location_ID, null);

		final I_C_Location shipToLocation = shipBPLocation.getC_Location();
		final boolean isEULocation = countryAreaBL.isMemberOf(ctx,
				ICountryAreaBL.COUNTRYAREAKEY_EU,
				shipToLocation.getC_Country().getCountryCode(),
				billDate);
		final int shipToCountryRepoId = shipToLocation.getC_Country_ID();

		final CountryId shipFromCountryId;
		if (warehouseId != null)
		{
			shipFromCountryId = Services.get(IWarehouseBL.class).getCountryId(warehouseId);
		}
		else
		{
			final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
			final CountryId orgCountryId = bPartnerOrgBL.getOrgCountryId(orgId);
			if (orgCountryId != null)
			{
				shipFromCountryId = orgCountryId;
			}
			else
			{
				shipFromCountryId = Services.get(ICountryDAO.class).getDefaultCountryId();
			}
		}
		final int shipFromCountryRepoId = CountryId.toRepoId(shipFromCountryId);

		// bp has tax certificate?
		final I_C_BPartner bp = Services.get(IBPartnerDAO.class).getById(shipBPLocation.getC_BPartner_ID());
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
				"WHERE t.validFrom < ? AND t.isActive='Y' AND t.C_Country_ID = " + shipFromCountryRepoId + " ";

		if (shipToCountryRepoId == shipFromCountryRepoId)
		{
			sql += " AND t.To_Country_ID = " + shipToCountryRepoId + " ";
		}
		else if (isEULocation)
		{
			// To_Country_ID should be null for all other taxes
			sql += " AND (pl.C_Country_ID IS NULL OR pl.C_Country_ID = " + shipToCountryRepoId + ") ";
			// metas: Abweichungen zu EU finden wenn definiert
			sql += " AND (t.To_Country_ID IS NULL OR t.To_Country_ID = " + shipToCountryRepoId + ") ";
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
			sql += " AND (pl.C_Country_ID IS NULL OR pl.C_Country_ID = " + shipToCountryRepoId + ") ";
			// Abweichungen zu Drittland finden finden wenn definiert
			sql += " AND (t.To_Country_ID IS NULL OR t.To_Country_ID =" + shipToLocation.getC_Country().getC_Country_ID() + ") ";
			sql += " AND t.IsToEULocation = 'N' ";
		}
		// product or charge
		if (productId != null)
		{
			sql += " AND t.C_TaxCategory_ID = pp.C_TaxCategory_ID AND pp.M_Product_ID = ? ";
		}
		else
		{
			sql += " AND t.C_TaxCategory_ID = c.C_TaxCategory_ID AND c.C_Charge_ID = ? ";
		}

		if (orgId != null)
		{
			sql += " AND (t.AD_Org_ID=0 OR t.AD_Org_ID=" + orgId.getRepoId() + ") ";
		}

		sql += " ORDER BY t.AD_Org_ID DESC, t.To_Country_ID, t.validFrom DESC ";
		log.debug("getGermanTax - sql={}", sql);

		// get tax id
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// metas start: rc: 03083: get the retrieved C_TaxCategory
		TaxCategoryId taxCategoryId = null;
		// metas end: rc: 03083
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setTimestamp(1, billDate);
			if (productId != null)
			{
				pstmt.setInt(2, productId.getRepoId());
			}
			else
			{
				pstmt.setInt(2, chargeId);
			}

			rs = pstmt.executeQuery();
			if (rs.next())
			{
				taxId = rs.getInt(1);
				taxCategoryId = TaxCategoryId.ofRepoId(rs.getInt(4));
			}
		}
		catch (final SQLException e)
		{
			log.error("getGermanTax - error: ", e);
			throw new DBException(e, sql, new Object[] { billDate, productId != null ? productId : chargeId });
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		if (taxId <= 0)
		{
			throw TaxNotFoundException.builder()
					.productId(productId).chargeId(chargeId)
					.taxCategoryId(taxCategoryId)
					.isSOTrx(isSOTrx)
					.shipDate(shipDate)
					.shipFromC_Location_ID(0)
					.shipToC_Location_ID(shipBPLocation.getC_Location_ID())
					.billDate(billDate)
					.billFromC_Location_ID(0)
					.billToC_Location_ID(InterfaceWrapperHelper.loadOutOfTrx(billC_BPartner_Location_ID, I_C_BPartner_Location.class).getC_Location_ID())
					.build();
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
			return getGermanTax(
					ctx,
					ProductId.ofRepoIdOrNull(M_Product_ID),
					C_Charge_ID,
					billDate,
					shipDate,
					OrgId.ofRepoId(AD_Org_ID),
					WarehouseId.ofRepoIdOrNull(M_Warehouse_ID),
					billC_BPartner_Location_ID,
					shipC_BPartner_Location_ID,
					IsSOTrx);
		}
		else
		{
			return taxDAO.retrieveExemptTax(OrgId.ofRepoId(AD_Org_ID)).getRepoId();
		}
	}

	@Override
	public BigDecimal calculateTax(final I_C_Tax tax, final BigDecimal amount, final boolean taxIncluded, final int scale)
	{
		// Null Tax
		if (tax.getRate().signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		BigDecimal multiplier = tax.getRate().divide(Env.ONEHUNDRED, 12, BigDecimal.ROUND_HALF_UP);

		final BigDecimal taxAmt;
		if (tax.isWholeTax())
		{
			Check.assume(taxIncluded, "TaxIncluded shall be set when IsWholeTax is set");
			taxAmt = amount;
		}
		else if (!taxIncluded)    // $100 * 6 / 100 == $6 == $100 * 0.06
		{
			taxAmt = amount.multiply(multiplier);
		}
		else
		// $106 - ($106 / (100+6)/100) == $6 == $106 - ($106/1.06)
		{
			multiplier = multiplier.add(BigDecimal.ONE);
			final BigDecimal base = amount.divide(multiplier, 12, BigDecimal.ROUND_HALF_UP);
			taxAmt = amount.subtract(base);
		}

		final BigDecimal taxAmtFinal = taxAmt.setScale(scale, BigDecimal.ROUND_HALF_UP);

		log.debug("calculateTax: amount={} (incl={}, mult={}, scale={}) = {} [{}]", amount, taxIncluded, multiplier, scale, taxAmtFinal, taxAmt);

		return taxAmtFinal;
	}    // calculateTax

	@Override
	public BigDecimal calculateBaseAmt(
			@NonNull final I_C_Tax tax,
			@NonNull final BigDecimal amount,
			final boolean taxIncluded,
			final int scale)
	{
		try(final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(tax))
		{
			if (tax.isWholeTax())
			{
				log.debug("C_Tax has isWholeTax=true; -> return ZERO");
				return BigDecimal.ZERO;
			}
			if (!taxIncluded)
			{
				// the given amount is without tax => don't subtract the tax that is no included
				log.debug("Parameter taxIncluded=false; -> return given param amount={}", amount);
				return amount;
			}
			final BigDecimal taxAmt = calculateTax(tax, amount, taxIncluded, scale);
			final BigDecimal baseAmt = amount.subtract(taxAmt);
			return baseAmt;
		}
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

	@Override
	public TaxCategoryId retrieveRegularTaxCategoryId()
	{
		final TaxCategoryId taxCategoryId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_TaxCategory.class)
				.addEqualsFilter(I_C_TaxCategory.COLUMN_VATType, X_C_TaxCategory.VATTYPE_RegularVAT)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.orderBy(I_C_TaxCategory.COLUMN_Name)
				.create()
				.firstId(TaxCategoryId::ofRepoIdOrNull);

		if (taxCategoryId == null)
		{
			throw new AdempiereException("No tax category found for Regular VATType");
		}

		return taxCategoryId;
	}
}
