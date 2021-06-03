package de.metas.tax.api.impl;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.annotation.CacheCtx;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.location.CountryId;
import de.metas.location.ICountryAreaBL;
import de.metas.location.ICountryDAO;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.SOPOType;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxQuery;
import de.metas.tax.api.TypeOfDestCountry;
import de.metas.tax.model.I_C_VAT_SmallBusiness;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.ExemptTaxNotFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static de.metas.tax.api.TypeOfDestCountry.DOMESTIC;
import static de.metas.tax.api.TypeOfDestCountry.OUTSIDE_COUNTRY_AREA;
import static de.metas.tax.api.TypeOfDestCountry.WITHIN_COUNTRY_AREA;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class TaxDAO implements ITaxDAO
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);
	private final ICountryAreaBL countryAreaBL = Services.get(ICountryAreaBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private static final transient Logger logger = LogManager.getLogger(TaxDAO.class);

	@Override
	public I_C_Tax getTaxById(final int taxRepoId)
	{
		return getTaxById(TaxId.ofRepoId(taxRepoId));
	}

	@Override
	public I_C_Tax getTaxById(@NonNull final TaxId taxId)
	{
		return loadOutOfTrx(taxId, I_C_Tax.class);
	}

	@Override
	public I_C_Tax getTaxByIdOrNull(final int taxRepoId)
	{
		if (taxRepoId <= 0)
		{
			return null;
		}

		return loadOutOfTrx(taxRepoId, I_C_Tax.class);
	}

	@Override
	@Cached(cacheName = I_C_VAT_SmallBusiness.Table_Name + "#By#C_BPartner_ID#Date")
	public boolean retrieveIsTaxExemptSmallBusiness(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final Timestamp date)
	{
		return queryBL.createQueryBuilder(I_C_VAT_SmallBusiness.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_VAT_SmallBusiness.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.addCompareFilter(I_C_VAT_SmallBusiness.COLUMNNAME_ValidFrom, Operator.LESS_OR_EQUAL, date)
				.addCompareFilter(I_C_VAT_SmallBusiness.COLUMNNAME_ValidTo, Operator.GREATER_OR_EQUAL, date)
				.create()
				.anyMatch();
	}

	@Override
	public TaxId retrieveExemptTax(@NonNull final OrgId orgId)
	{
		int C_Tax_ID = queryBL.createQueryBuilder(I_C_Tax.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Tax.COLUMNNAME_IsTaxExempt, true)
				.addEqualsFilter(I_C_Tax.COLUMNNAME_AD_Org_ID, orgId)
				.orderByDescending(I_C_Tax.COLUMNNAME_Rate)
				.create()
				.firstId();
		if (C_Tax_ID <= 0)
		{
			throw new ExemptTaxNotFoundException(orgId.getRepoId());
		}
		return TaxId.ofRepoId(C_Tax_ID);
	}

	@Override
	public I_C_Tax getDefaultTax(final I_C_TaxCategory taxCategory)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(taxCategory);
		final String trxName = InterfaceWrapperHelper.getTrxName(taxCategory);
		final I_C_Tax tax;

		String whereClause = I_C_Tax.COLUMNNAME_C_TaxCategory_ID + "=? AND " + I_C_Tax.COLUMNNAME_IsDefault + "='Y'";
		List<I_C_Tax> list = new Query(ctx, I_C_Tax.Table_Name, whereClause, trxName)
				.setParameters(taxCategory.getC_TaxCategory_ID())
				.list(I_C_Tax.class);
		if (list.size() == 1)
		{
			tax = list.get(0);
		}
		else
		{
			// Error - should only be one default
			throw new AdempiereException("TooManyDefaults");
		}

		return tax;
	}

	@Override
	@Cached(cacheName = I_C_Tax.Table_Name + "#NoTaxFound")
	public I_C_Tax retrieveNoTaxFound(@CacheCtx final Properties ctx)
	{
		return queryBL.createQueryBuilder(I_C_Tax.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Tax.COLUMNNAME_C_Tax_ID, C_TAX_ID_NO_TAX_FOUND)
				.create()
				.firstOnlyNotNull(I_C_Tax.class);
	}

	@Override
	@Cached(cacheName = I_C_TaxCategory.Table_Name + "#NoTaxFound")
	public I_C_TaxCategory retrieveNoTaxCategoryFound(@CacheCtx final Properties ctx)
	{
		return queryBL.createQueryBuilder(I_C_TaxCategory.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID, TaxCategoryId.NOT_FOUND)
				.create()
				.firstOnlyNotNull(I_C_TaxCategory.class);
	}

	@Override
	public int findTaxCategoryId(@NonNull final TaxCategoryQuery query)
	{
		final IQueryBL queryBL = this.queryBL;

		final IQueryBuilder<I_C_TaxCategory> queryBuilder = queryBL.createQueryBuilder(I_C_TaxCategory.class);

		final IQuery<I_C_Tax> querytaxes = queryBL.createQueryBuilder(I_C_Tax.class)
				.addInArrayFilter(I_C_Tax.COLUMNNAME_C_Country_ID, null, query.getCountryId())
				.create();

		return queryBuilder
				.addInArrayFilter(I_C_TaxCategory.COLUMN_VATType, query.getType().getValue(), null)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addInSubQueryFilter(I_C_TaxCategory.COLUMNNAME_C_TaxCategory_ID, I_C_Tax.COLUMNNAME_C_TaxCategory_ID, querytaxes)
				.orderBy(I_C_TaxCategory.COLUMNNAME_Name)
				.create()
				.firstId();
	}

	@Override
	public I_C_TaxCategory getTaxCategoryById(@NonNull final TaxCategoryId id)
	{
		return loadOutOfTrx(id, I_C_TaxCategory.class);
	}

	@Override
	public ITranslatableString getTaxCategoryNameById(@Nullable final TaxCategoryId id)
	{
		if (id == null)
		{
			return TranslatableStrings.anyLanguage("?");
		}

		final I_C_TaxCategory taxCategory = getTaxCategoryById(id);
		if (taxCategory == null)
		{
			return TranslatableStrings.anyLanguage("<" + id.getRepoId() + ">");
		}

		return InterfaceWrapperHelper.getModelTranslationMap(taxCategory)
				.getColumnTrl(I_C_TaxCategory.COLUMNNAME_Name, taxCategory.getName());
	}

	@Override
	public Optional<TaxCategoryId> getTaxCategoryIdByName(@NonNull final String name)
	{
		final TaxCategoryId taxCategoryId = queryBL.createQueryBuilder(I_C_TaxCategory.class)
				.addEqualsFilter(I_C_TaxCategory.COLUMN_Name, name)
				.create()
				.firstId(TaxCategoryId::ofRepoIdOrNull);

		return Optional.ofNullable(taxCategoryId);
	}

	@Override
	public Percent getRateById(@NonNull final TaxId taxId)
	{
		final I_C_Tax tax = getTaxById(taxId);
		return Percent.of(tax.getRate());
	}

	@Override
	public Collection<Tax> getBy(final TaxQuery taxQuery)
	{
		return getBy(taxQuery, Env.getCtx());
	}

	@Override
	public Collection<Tax> getBy(final TaxQuery taxQuery, final Properties ctx)
	{
		final IQueryBuilder<I_C_Tax> queryBuilder = getTaxQueryBuilder(taxQuery, ctx);
		return queryBuilder.create().list().stream().map(this::from).collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private IQueryBuilder<I_C_Tax> getTaxQueryBuilder(final TaxQuery taxQuery, final Properties ctx)
	{
		final IQueryBuilder<I_C_Tax> queryBuilder = queryBL.createQueryBuilder(I_C_Tax.class, ctx);
		final OrgId orgId = taxQuery.getOrgId();
		CountryId countryId = null;
		if (orgId != null)
		{
			queryBuilder.addInArrayFilter(I_C_Tax.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY);
			countryId = getCountryIdFromOrgId(orgId);
			if (countryId != null)
			{
				queryBuilder.addEqualsFilter(I_C_Tax.COLUMN_C_Country_ID, countryId);
			}
		}

		final WarehouseId warehouseId = taxQuery.getWarehouseId();
		if (warehouseId != null)
		{
			final LocationId locationId = LocationId.ofRepoIdOrNull(warehouseDAO.getById(warehouseId).getC_Location_ID());
			if (locationId != null)
			{
				countryId = CoalesceUtil.coalesce(countryId, locationDAO.getCountryIdByLocationId(locationId));
				queryBuilder.addEqualsFilter(I_C_Tax.COLUMN_C_Country_ID, countryId);
			}
		}
		if (countryId == null)
		{
			throw new AdempiereException("No country could be identified for the given orgId: " + orgId + " and warehouse: " + warehouseId);
		}

		final BPartnerLocationId bPartnerLocationId = taxQuery.getBPartnerLocationId();
		final CountryId toCountryId = getCountryId(bPartnerLocationId);
		queryBuilder.addInArrayFilter(I_C_Tax.COLUMN_To_Country_ID, toCountryId, null);
		queryBuilder.addEqualsFilter(I_C_Tax.COLUMNNAME_TypeOfDestCountry, getTypeOfDestCountry(countryId, toCountryId).getCode());

		final I_C_BPartner bpartner = bPartnerDAO.getById(bPartnerLocationId.getBpartnerId());
		queryBuilder.addEqualsFilter(I_C_Tax.COLUMN_RequiresTaxCertificate, !Check.isBlank(bpartner.getVATaxID()));

		final boolean isSmallBusiness = retrieveIsTaxExemptSmallBusiness(BPartnerId.ofRepoId(bpartner.getC_BPartner_ID()), TimeUtil.asTimestamp(taxQuery.getDateOfInterest()));
		queryBuilder.addEqualsFilter(I_C_Tax.COLUMN_IsSmallbusiness, isSmallBusiness);

		Loggables.withLogger(logger, Level.INFO).addLog("Created tax search criteria from: {}  to {}", taxQuery, queryBuilder);
		return queryBuilder;
	}

	@Nullable
	private CountryId getCountryIdFromOrgId(final OrgId orgId)
	{
		final I_C_BPartner orgBPartner = bPartnerDAO.retrieveOrgBPartner(Env.getCtx(), orgId.getRepoId(), I_C_BPartner.class, ITrx.TRXNAME_None);
		final BPartnerLocationId billToLocationId = bPartnerDAO.retrieveCurrentBillLocationOrNull(BPartnerId.ofRepoId(orgBPartner.getC_BPartner_ID()));
		if (billToLocationId == null)
		{
			return null;
		}
		return bPartnerDAO.retrieveBPartnerLocationCountryId(billToLocationId);
	}

	@NonNull
	private TypeOfDestCountry getTypeOfDestCountry(final CountryId countryId, final CountryId toCountryId)
	{
		final TypeOfDestCountry typeOfDestCountry;
		if (countryId.equals(toCountryId))
		{
			typeOfDestCountry = DOMESTIC;
		}
		else
		{
			final String countryCode = countryDAO.retrieveCountryCode2ByCountryId(toCountryId);
			final boolean isEULocation = countryAreaBL.isMemberOf(Env.getCtx(),
					ICountryAreaBL.COUNTRYAREAKEY_EU,
					countryCode,
					TimeUtil.asTimestamp(new Date()));
			typeOfDestCountry = isEULocation ? WITHIN_COUNTRY_AREA : OUTSIDE_COUNTRY_AREA;
		}
		return typeOfDestCountry;
	}

	private CountryId getCountryId(final BPartnerLocationId bPartnerLocationId)
	{
		final I_C_BPartner_Location bpartnerLocation = bPartnerDAO.getBPartnerLocationByIdEvenInactive(bPartnerLocationId);
		if (bpartnerLocation == null)
		{
			throw new AdempiereException("No location found for bpartnerLocationdId: " + bPartnerLocationId);
		}
		return locationDAO.getCountryIdByLocationId(LocationId.ofRepoId(bpartnerLocation.getC_Location_ID()));
	}

	private Tax from(@NonNull final I_C_Tax from)
	{
		return Tax.builder()
				.taxId(TaxId.ofRepoId(from.getC_Tax_ID()))
				.orgId(OrgId.ofRepoId(from.getAD_Org_ID()))
				.validFrom(from.getValidFrom())
				.countryId(CountryId.ofRepoId(from.getC_Country_ID()))
				.toCountryId(CountryId.ofRepoIdOrNull(from.getTo_Country_ID()))
				.typeOfDestCountry(TypeOfDestCountry.ofNullableCode(from.getTypeOfDestCountry()))
				.taxCategoryId(TaxCategoryId.ofRepoId(from.getC_TaxCategory_ID()))
				.requiresTaxCertificate(from.isRequiresTaxCertificate())
				.sopoType(SOPOType.ofCode(from.getSOPOType()))
				.isTaxExempt(from.isTaxExempt())
				.isSmallBusiness(from.isSmallbusiness())
				.isFiscalRepresentation(from.isFiscalRepresentation())
				.build();
	}

}
