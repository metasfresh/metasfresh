package de.metas.tax.api.impl;

import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.tax.api.CalculateTaxResult;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxQuery;
import de.metas.tax.api.TaxUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.X_C_TaxCategory;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

public class TaxBL implements de.metas.tax.api.ITaxBL
{
	private static final Logger log = LogManager.getLogger(TaxBL.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

	@Override
	public Tax getTaxById(final TaxId taxId)
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
	@NonNull
	public TaxId getTaxNotNull(
			@Nullable final Object model,
			@Nullable final TaxCategoryId taxCategoryId,
			final int productId,
			@NonNull final Timestamp shipDate,
			@NonNull final OrgId orgId,
			@Nullable final WarehouseId warehouseId,
			@NonNull final BPartnerLocationAndCaptureId shipBPartnerLocationId,
			@NonNull final SOTrx soTrx)
	{
		if (taxCategoryId != null)
		{
			final Optional<TaxId> taxId = getTaxId(taxCategoryId,
					shipDate,
					orgId,
					warehouseId,
					shipBPartnerLocationId,
					soTrx);

			if (taxId.isPresent())
			{
				return taxId.get();
			}
		}

		final AdempiereException ex = new AdempiereException(StringUtils.formatMessage(
				"Could not retrieve C_Tax_ID; will return the Tax-Not-Found-C_Tax_ID; Method paratmers:"
						+ "model= {}, taxCategoryId={}, productId={}, shipDate={}, adOrgId={}, "
						+ "warehouse={}, shipBPartnerLocationId={}, isSOTrx={}, trxName={}",
				model,
				taxCategoryId,
				productId,
				shipDate,
				orgId,
				warehouseId,
				shipBPartnerLocationId,
				soTrx.isSales()));
		log.warn("getTax - error: ", ex);

		// 07814
		// If we got here, it means that no tax was found to satisfy the conditions
		// In this case, the Tax_Not_Found placeholder will be returned
		return TaxId.ofRepoId(Tax.C_TAX_ID_NO_TAX_FOUND);
	}

	@Override
	@NonNull
	public TaxId getTaxOrNoTaxId(
			@Nullable final Object model,
			@Nullable final TaxCategoryId taxCategoryId,
			final int productId,
			@NonNull final Timestamp shipDate,
			@NonNull final OrgId orgId,
			@Nullable final WarehouseId warehouseId,
			@NonNull final BPartnerLocationAndCaptureId shipBPartnerLocationId,
			@NonNull final SOTrx soTrx)
	{
		return getTaxId(taxCategoryId,
				shipDate,
				orgId,
				warehouseId,
				shipBPartnerLocationId,
				soTrx)
				.orElseGet(() -> TaxId.ofRepoId(Tax.C_TAX_ID_NO_TAX_FOUND));
	}

	public CalculateTaxResult calculateTax(final I_C_Tax tax, final BigDecimal amount, final boolean taxIncluded, final int scale)
	{
		return TaxUtils.from(tax).calculateTax(amount, taxIncluded, scale);
	}

	public BigDecimal calculateTaxAmt(final I_C_Tax tax, final BigDecimal amount, final boolean taxIncluded, final int scale)
	{
		return calculateTax(tax, amount, taxIncluded, scale).getTaxAmount();
	}

	@Override
	public BigDecimal calculateBaseAmt(@NonNull final I_C_Tax tax, @NonNull final BigDecimal amount, final boolean taxIncluded, final int scale)
	{

		return TaxUtils.from(tax).calculateBaseAmt(amount, taxIncluded, scale);
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

	@NonNull
	public Optional<TaxCategoryId> getTaxCategoryIdByInternalName(@NonNull final String internalName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_TaxCategory.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_TaxCategory.COLUMNNAME_InternalName, internalName)
				.create()
				.firstOnlyOptional(I_C_TaxCategory.class)
				.map(I_C_TaxCategory::getC_TaxCategory_ID)
				.map(TaxCategoryId::ofRepoId);
	}

	@NonNull
	private Optional<TaxId> getTaxId(
			@Nullable final TaxCategoryId taxCategoryId,
			@NonNull final Timestamp shipDate,
			@NonNull final OrgId orgId,
			@Nullable final WarehouseId warehouseId,
			@NonNull final BPartnerLocationAndCaptureId shipBPartnerLocationId,
			@NonNull final SOTrx soTrx)
	{
		final CountryId countryFromId = Optional.ofNullable(warehouseId)
				.map(warehouseBL::getCountryId)
				.orElseGet(() -> Optional.ofNullable(bPartnerOrgBL.getOrgCountryId(orgId))
						.orElseGet(countryDAO::getDefaultCountryId));

		return Optional.ofNullable(taxDAO.getBy(TaxQuery.builder()
						.fromCountryId(countryFromId)
						.orgId(orgId)
						.bPartnerLocationId(shipBPartnerLocationId)
						.dateOfInterest(shipDate)
						.taxCategoryId(taxCategoryId)
						.warehouseId(warehouseId)
						.soTrx(soTrx)
						.build()))
				.map(Tax::getTaxId);
	}
}
