package de.metas.impexp.product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.ModelValidationEngine;

import java.util.Objects;
import com.google.common.collect.ImmutableSet;

import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.pricing.PriceListId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.IProductDAO;
import de.metas.product.impexp.MProductImportTableSqlUpdater;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;
import de.metas.vertical.pharma.model.I_M_Product;
import de.metas.vertical.pharma.model.X_I_Pharma_Product;
import lombok.NonNull;

public class IFAProductImportProcess extends SimpleImportProcessTemplate<I_I_Pharma_Product>
{
	private final String DEACTIVATE_OPERATION_CODE = "2";
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	@Override
	public Class<I_I_Pharma_Product> getImportModelClass()
	{
		return I_I_Pharma_Product.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Pharma_Product.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return org.compiere.model.I_M_Product.Table_Name;
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Pharma_Product.COLUMNNAME_A01GDAT;
	}

	@Override
	protected I_I_Pharma_Product retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_Pharma_Product(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		MProductImportTableSqlUpdater.builder()
		.selection(selection)
		.ctx(getCtx())
		.tableName(getImportTableName())
		.valueName(I_I_Pharma_Product.COLUMNNAME_A00PZN)
		.updateIPharmaProduct();
	}

	@Override
	protected ImportRecordResult importRecord(@NonNull final IMutable<Object> state, @NonNull final I_I_Pharma_Product importRecord, final boolean isInsertOnly)
	{
		final org.compiere.model.I_M_Product existentProduct = productDAO.retrieveProductByValue(importRecord.getA00PZN());

		final String operationCode = importRecord.getA00SSATZ();
		if (DEACTIVATE_OPERATION_CODE.equals(operationCode) && existentProduct != null)
		{
			IFAProductImportHelper.deactivateProduct(existentProduct);
			return ImportRecordResult.Updated;
		}
		else if (!DEACTIVATE_OPERATION_CODE.equals(operationCode))
		{
			final I_M_Product product;
			final boolean newProduct = existentProduct == null || importRecord.getM_Product_ID() <= 0;

			if (!newProduct && isInsertOnly)
			{
				// #4994 do not update entry
				return ImportRecordResult.Nothing;
			}
			if (newProduct)
			{
				product = IFAProductImportHelper.createProduct(importRecord);
			}
			else
			{
				product = IFAProductImportHelper.updateProduct(importRecord, existentProduct);
			}

			importRecord.setM_Product_ID(product.getM_Product_ID());

			ModelValidationEngine.get().fireImportValidate(this, importRecord, importRecord.getM_Product(), IImportInterceptor.TIMING_AFTER_IMPORT);

			IFAProductImportHelper.importPrices(importRecord, false);

			return newProduct ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
		}

		return ImportRecordResult.Nothing;
	}

	@Override
	protected void markImported(@NonNull final I_I_Pharma_Product importRecord)
	{
		importRecord.setI_IsImported(X_I_Pharma_Product.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}

	@Override
	protected void afterImport()
	{
		final List<I_M_PriceList_Version> versions = retrieveLatestPriceListVersion();
		versions.stream()
		.filter(plv -> plv.getM_Pricelist_Version_Base_ID() > 0)
		.forEach(plv -> {

			final MProductPriceCloningCommand productPriceCloning = MProductPriceCloningCommand.builder()
					.source_PriceList_Version_ID(plv.getM_Pricelist_Version_Base_ID())
					.target_PriceList_Version_ID(plv.getM_PriceList_Version_ID())
					.build();

			productPriceCloning.cloneProductPrice();
		});

		final String whereClause = I_I_Pharma_Product.COLUMNNAME_IsPriceCopied + " = 'N' ";
		MProductImportTableSqlUpdater.dbUpdateIsPriceCopiedToYes(whereClause, I_I_Pharma_Product.COLUMNNAME_IsPriceCopied);
	}

	private List<I_M_PriceList_Version> retrieveLatestPriceListVersion()
	{
		final List<I_M_PriceList_Version> priceListVersions = new ArrayList<>();

		final Set<PriceListId> priceListIds = retrievePriceLists();
		priceListIds.forEach(priceListId -> {
			final I_M_PriceList_Version plv = Services.get(IPriceListDAO.class).retrieveNewestPriceListVersion(priceListId);
			if (plv != null)
			{
				priceListVersions.add(plv);
			}
		});

		return priceListVersions;
	}

	/**
	 * create a set of price lists from the records which don't have the price copies <code>I_I_Pharma_Product.COLUMNNAME_IsPriceCopied</code> on 'N'
	 * @return
	 */
	private Set<PriceListId> retrievePriceLists()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final String trxName = ITrx.TRXNAME_None;

		final IQuery<I_I_Pharma_Product> pharmaPriceListQuery = queryBL.createQueryBuilder(I_I_Pharma_Product.class, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_I_Pharma_Product.COLUMNNAME_IsPriceCopied, false)
				.create();

		return queryBL.createQueryBuilder(I_M_PriceList.class, trxName)
				.setJoinOr()
				.addInSubQueryFilter(I_M_PriceList.COLUMNNAME_M_PriceList_ID, I_I_Pharma_Product.COLUMNNAME_AEP_Price_List_ID, pharmaPriceListQuery)
				.addInSubQueryFilter(I_M_PriceList.COLUMNNAME_M_PriceList_ID, I_I_Pharma_Product.COLUMNNAME_APU_Price_List_ID, pharmaPriceListQuery)
				.addInSubQueryFilter(I_M_PriceList.COLUMNNAME_M_PriceList_ID, I_I_Pharma_Product.COLUMNNAME_AVP_Price_List_ID, pharmaPriceListQuery)
				.addInSubQueryFilter(I_M_PriceList.COLUMNNAME_M_PriceList_ID, I_I_Pharma_Product.COLUMNNAME_KAEP_Price_List_ID, pharmaPriceListQuery)
				.addInSubQueryFilter(I_M_PriceList.COLUMNNAME_M_PriceList_ID, I_I_Pharma_Product.COLUMNNAME_UVP_Price_List_ID, pharmaPriceListQuery)
				.addInSubQueryFilter(I_M_PriceList.COLUMNNAME_M_PriceList_ID, I_I_Pharma_Product.COLUMNNAME_ZBV_Price_List_ID, pharmaPriceListQuery)
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions)
				.create()
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.listDistinct(I_M_PriceList.COLUMNNAME_M_PriceList_ID)
				.stream()
				.map(map -> extractPriceListIdorNull(map))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Nullable private PriceListId extractPriceListIdorNull(@NonNull final Map<String, Object> map)
	{
		final int priceListId = NumberUtils.asInt(map.get(I_M_PriceList.COLUMNNAME_M_PriceList_ID), -1);
		if (priceListId <= 0)
		{
			// shall not happen
			return null;
		}
		return PriceListId.ofRepoId(priceListId);
	}
}
