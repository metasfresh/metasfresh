package org.eevolution.api.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMCreateRequest;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.TreeSet;

import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ProductBOMDAO implements IProductBOMDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);

	@Override
	public ImmutableList<I_PP_Product_BOMLine> retrieveLines(final I_PP_Product_BOM productBOM)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(productBOM);
		final String trxName = InterfaceWrapperHelper.getTrxName(productBOM);
		final ProductBOMId bomId = ProductBOMId.ofRepoId(productBOM.getPP_Product_BOM_ID());
		return retrieveLines(ctx, bomId, trxName);
	}

	@Cached(cacheName = I_PP_Product_BOMLine.Table_Name + "#by#" + I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOM_ID)
	ImmutableList<I_PP_Product_BOMLine> retrieveLines(
			@CacheCtx final Properties ctx,
			final ProductBOMId bomId,
			@CacheTrx final String trxName)
	{
		return queryBL.createQueryBuilder(I_PP_Product_BOMLine.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOM_ID, bomId)
				.orderBy(I_PP_Product_BOMLine.COLUMNNAME_Line)
				.orderBy(I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOMLine_ID)
				.create()
				.listImmutable(I_PP_Product_BOMLine.class);
	}	// getLines

	@Override
	public List<I_PP_Product_BOMLine> retrieveLinesByBOMIds(@NonNull final Collection<ProductBOMId> bomIds)
	{
		if (bomIds.isEmpty())
		{
			return ImmutableList.of();
		}
		return queryBL.createQueryBuilderOutOfTrx(I_PP_Product_BOMLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOM_ID, bomIds)
				.create()
				.list();

	}

	@Override
	public Optional<ProductBOMId> getDefaultBOMIdByProductId(@NonNull final ProductId productId)
	{
		final I_M_Product product = productsRepo.getById(productId);

		return getDefaultBOM(product)
				.map(bom -> ProductBOMId.ofRepoId(bom.getPP_Product_BOM_ID()));
	}

	@Override
	public Optional<I_PP_Product_BOM> getDefaultBOMByProductId(@NonNull final ProductId productId)
	{
		final I_M_Product product = productsRepo.getById(productId);
		return getDefaultBOM(product);
	}

	@Override
	public Optional<I_PP_Product_BOM> getDefaultBOM(@NonNull final I_M_Product product)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(product);
		final String trxName = InterfaceWrapperHelper.getTrxName(product);
		final int productId = product.getM_Product_ID();
		final String productValue = product.getValue();

		return retrieveDefaultBOM(ctx, productId, productValue, trxName);
	}

	@Cached(cacheName = I_PP_Product_BOM.Table_Name + "#by#IsDefault")
	/* package */ Optional<I_PP_Product_BOM> retrieveDefaultBOM(
			@CacheCtx final Properties ctx,
			final int productId,
			final String productValue,
			@CacheTrx final String trxName)
	{
		final I_PP_Product_BOM bom = queryBL
				.createQueryBuilder(I_PP_Product_BOM.class, ctx, trxName)
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_Value, productValue)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_PP_Product_BOM.class);

		return Optional.ofNullable(bom);
	}

	@Override
	public I_PP_Product_BOM getById(@NonNull final ProductBOMId bomId)
	{
		return retrieveById(Env.getCtx(), bomId);
	}

	@Override
	public List<I_PP_Product_BOM> getByIds(@NonNull final Collection<ProductBOMId> bomIds)
	{
		return loadByRepoIdAwaresOutOfTrx(bomIds, I_PP_Product_BOM.class);
	}

	@Cached(cacheName = I_PP_Product_BOM.Table_Name + "#by#" + I_PP_Product_BOM.COLUMNNAME_PP_Product_BOM_ID)
	public I_PP_Product_BOM retrieveById(@CacheCtx final Properties ctx, final ProductBOMId productBomId)
	{
		return loadOutOfTrx(productBomId, I_PP_Product_BOM.class);
	}

	@Override
	public boolean hasBOMs(final ProductId productId)
	{
		// IMPORTANT: fetch in current trx because this pice of code can be called from some PP_Product_BOM model interceptor!
		return queryBL.createQueryBuilder(I_PP_Product_BOM.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_M_Product_ID, productId)
				.create()
				.anyMatch();
	}

	@Override
	public I_PP_Product_BOMLine getBOMLineById(final int productBOMLineId)
	{
		Check.assumeGreaterThanZero(productBOMLineId, "productBOMLineId");
		return loadOutOfTrx(productBOMLineId, I_PP_Product_BOMLine.class);
	}

	@Override
	public IQuery<I_PP_Product_BOMLine> retrieveBOMLinesForProductQuery(final Properties ctx, final int productId, final String trxName)
	{
		return queryBL
				.createQueryBuilder(I_PP_Product_BOMLine.class, ctx, trxName)
				.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_M_Product_ID, productId)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx)
				.create();
	}

	@Override
	public int retrieveLastLineNo(final int ppProductBOMId)
	{
		return queryBL
				.createQueryBuilder(I_PP_Product_BOMLine.class)
				.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOM_ID, ppProductBOMId)
				.create()
				.maxInt(I_PP_Product_BOMLine.COLUMNNAME_Line);
	}

	@Override
	public List<I_PP_Product_BOM> retrieveBOMsContainingExactProducts(final Collection<Integer> productIds)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_PP_Product_BOM.class)
				.addOnlyActiveRecordsFilter()
				.filter(MatchBOMProductsFilter.exactProducts(productIds))
				.create()
				.listImmutable(I_PP_Product_BOM.class);
	}

	@lombok.ToString
	@lombok.EqualsAndHashCode
	private static final class MatchBOMProductsFilter implements IQueryFilter<I_PP_Product_BOM>, ISqlQueryFilter
	{
		public static MatchBOMProductsFilter exactProducts(final Collection<Integer> productIds)
		{
			return new MatchBOMProductsFilter(productIds);
		}

		private static final String SQL = "("
				+ " select array_agg(distinct bl.M_Product_ID order by bl.M_Product_ID)"
				+ " from " + I_PP_Product_BOMLine.Table_Name + " bl"
				+ " where bl.PP_Product_BOM_ID=PP_Product_BOM.PP_Product_BOM_ID"
				+ " and bl.IsActive='Y'"
				+ ") = ?::numeric[]";

		private final ImmutableList<Object> sqlParams;

		public MatchBOMProductsFilter(final Collection<Integer> productIds)
		{
			Check.assumeNotEmpty(productIds, "productIds is not empty");

			final TreeSet<Integer> productIdsSortedSet = new TreeSet<>(productIds);
			final String sqlProductIdsArray = toSqlArrayString(productIdsSortedSet);
			sqlParams = ImmutableList.of(sqlProductIdsArray);
		}

		@Override
		public boolean accept(final I_PP_Product_BOM model)
		{
			throw new UnsupportedOperationException("not implemented");
		}

		@Override
		public String getSql()
		{
			return SQL;
		}

		@Override
		public List<Object> getSqlParams(final Properties ctx_NOTUSED)
		{
			return sqlParams;
		}

		private static String toSqlArrayString(final Collection<Integer> ids)
		{
			final StringBuilder sql = new StringBuilder();
			sql.append("{");
			Joiner.on(",").appendTo(sql, ids);
			sql.append("}");
			return sql.toString();
		}
	}

	@Override
	public void save(@NonNull final I_PP_Product_BOMLine bomLine)
	{
		saveRecord(bomLine);
	}

	@Override
	public ProductBOMId createBOM(@NonNull final BOMCreateRequest request)
	{
		final OrgId orgId = request.getOrgId();
		final LocalDate validFrom = request.getValidFrom();

		final I_PP_Product_BOM bomRecord = newInstance(I_PP_Product_BOM.class);
		bomRecord.setAD_Org_ID(orgId.getRepoId());
		bomRecord.setM_Product_ID(request.getProductId().getRepoId());
		bomRecord.setValue(request.getProductValue());
		bomRecord.setName(request.getProductName());
		bomRecord.setC_UOM_ID(request.getUomId().getRepoId());
		bomRecord.setBOMType(request.getBomType().getCode());
		bomRecord.setBOMUse(request.getBomUse().getCode());
		bomRecord.setValidFrom(TimeUtil.asTimestamp(validFrom));
		saveRecord(bomRecord);
		final ProductBOMId bomId = ProductBOMId.ofRepoId(bomRecord.getPP_Product_BOM_ID());

		request.getLines().forEach(line -> createBOMLine(line, bomId, orgId, validFrom));

		return bomId;
	}

	private void createBOMLine(
			final BOMCreateRequest.BOMLine line,
			final ProductBOMId bomId,
			final OrgId orgId,
			final LocalDate validFrom)
	{
		final I_PP_Product_BOMLine bomLineRecord = newInstance(I_PP_Product_BOMLine.class);
		bomLineRecord.setAD_Org_ID(orgId.getRepoId());
		bomLineRecord.setPP_Product_BOM_ID(bomId.getRepoId());
		bomLineRecord.setM_Product_ID(line.getProductId().getRepoId());
		bomLineRecord.setC_UOM_ID(line.getQty().getUomId().getRepoId());
		bomLineRecord.setQtyBOM(line.getQty().toBigDecimal());
		bomLineRecord.setIsQtyPercentage(false);
		bomLineRecord.setComponentType(line.getComponentType().getCode());
		bomLineRecord.setValidFrom(TimeUtil.asTimestamp(validFrom));
		saveRecord(bomLineRecord);
	}

	@Override
	public ProductId getBOMProductId(@NonNull final ProductBOMId bomId)
	{
		final I_PP_Product_BOM bom = getById(bomId);
		return ProductId.ofRepoId(bom.getM_Product_ID());
	}
}
