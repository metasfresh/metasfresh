package org.eevolution.api.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IssuingToleranceSpec;
import de.metas.product.IssuingToleranceValueType;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Optionals;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMCreateRequest;
import org.eevolution.api.BOMType;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMLineId;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.model.X_PP_Product_BOM;
import org.eevolution.model.X_PP_Product_BOMLine;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;

import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ProductBOMDAO implements IProductBOMDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	private final AtomicReference<ProductBOMVersionsDAO> productBOMVersionsDAO = new AtomicReference<>();

	@NonNull
	private ProductBOMVersionsDAO getProductBOMVersionsDAO()
	{
		if (productBOMVersionsDAO.get() == null)
		{
			productBOMVersionsDAO.set(SpringContextHolder.instance.getBean(ProductBOMVersionsDAO.class));
		}

		return productBOMVersionsDAO.get();
	}

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
	}    // getLines

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
	public Optional<ProductBOMLineId> getBomLineByProductId(@NonNull final  ProductBOMId productBOMId, @NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_PP_Product_BOMLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOM_ID, productBOMId)
				.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_M_Product_ID, productId)
				.create()
				.firstIdOnlyOptional(ProductBOMLineId::ofRepoId);
	}

	@Override
	public Optional<ProductBOMId> getDefaultBOMIdByProductId(@NonNull final ProductId productId)
	{
		return getDefaultBOMByProductId(productId)
				.map(bom -> ProductBOMId.ofRepoId(bom.getPP_Product_BOM_ID()));
	}

	@Override
	public Optional<I_PP_Product_BOM> getDefaultBOMByProductId(@NonNull final ProductId productId)
	{
		return getProductBOMVersionsDAO()
				.retrieveBOMVersionsId(productId)
				.flatMap(this::getDefaultBOM);
	}

	@Override
	public Optional<I_PP_Product_BOM> getDefaultBOM(@NonNull final ProductId productId, @NonNull final BOMType bomType)
	{
		return getProductBOMVersionsDAO()
				.retrieveBOMVersionsId(productId)
				.flatMap(bomVersionsId -> getLatestBOMRecordByVersion(bomVersionsId, bomType));
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
	public List<I_PP_Product_BOMLine> retrieveBOMLinesByComponentIdInTrx(@NonNull final ProductId productId)
	{
		return queryBL
				.createQueryBuilder(I_PP_Product_BOMLine.class)
				.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_M_Product_ID, productId)
				.addOnlyActiveRecordsFilter()
				.create()
				.listImmutable(I_PP_Product_BOMLine.class);
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
	@NonNull
	public I_PP_Product_BOM createBOM(@NonNull final ProductBOMVersionsId bomVersionsId, @NonNull final BOMCreateRequest request)
	{
		final OrgId orgId = request.getOrgId();

		final I_PP_Product_BOM bomRecord = newInstance(I_PP_Product_BOM.class);
		bomRecord.setAD_Org_ID(orgId.getRepoId());
		bomRecord.setM_Product_ID(request.getProductId().getRepoId());
		bomRecord.setValue(request.getProductValue());
		bomRecord.setName(request.getProductName());
		bomRecord.setC_UOM_ID(request.getUomId().getRepoId());
		bomRecord.setPP_Product_BOMVersions_ID(bomVersionsId.getRepoId());
		bomRecord.setValidFrom(TimeUtil.asTimestamp(request.getValidFrom()));
		bomRecord.setM_AttributeSetInstance_ID(AttributeSetInstanceId.toRepoId(request.getAttributeSetInstanceId()));

		bomRecord.setDateDoc(TimeUtil.asTimestamp(Instant.now()));
		bomRecord.setC_DocType_ID(getBOMDocTypeId(orgId).getRepoId());
		bomRecord.setDocStatus(DocStatus.Drafted.getCode());
		bomRecord.setDocAction(X_PP_Product_BOM.DOCACTION_Complete);

		if (request.getIsActive() != null)
		{
			bomRecord.setIsActive(request.getIsActive());
		}

		if (request.getBomUse() != null)
		{
			bomRecord.setBOMUse(request.getBomUse().getCode());
		}

		if (request.getBomType() != null)
		{
			bomRecord.setBOMType(request.getBomType().getCode());
		}

		saveRecord(bomRecord);

		final ProductBOMId bomId = ProductBOMId.ofRepoId(bomRecord.getPP_Product_BOM_ID());

		request.getLines()
				.stream()
				.map(line -> CreateBOMLineRequest.builder()
						.bomId(bomId)
						.orgId(orgId)
						.isActive(request.getIsActive())
						.validFrom(request.getValidFrom())
						.line(line)
						.build()
				)
				.forEach(this::createBOMLine);

		return bomRecord;
	}

	private void createBOMLine(@NonNull final CreateBOMLineRequest createBOMLineRequest)
	{
		final I_PP_Product_BOMLine bomLineRecord = newInstance(I_PP_Product_BOMLine.class);

		final BOMCreateRequest.BOMLine line = createBOMLineRequest.getLine();

		bomLineRecord.setAD_Org_ID(createBOMLineRequest.getOrgId().getRepoId());
		bomLineRecord.setPP_Product_BOM_ID(createBOMLineRequest.getBomId().getRepoId());
		bomLineRecord.setM_Product_ID(line.getProductId().getRepoId());
		bomLineRecord.setC_UOM_ID(line.getQty().getUomId().getRepoId());
		bomLineRecord.setComponentType(line.getComponentType().getCode());
		bomLineRecord.setValidFrom(TimeUtil.asTimestamp(createBOMLineRequest.getValidFrom()));
		bomLineRecord.setM_AttributeSetInstance_ID(AttributeSetInstanceId.toRepoId(line.getAttributeSetInstanceId()));

		if (createBOMLineRequest.getIsActive() != null)
		{
			bomLineRecord.setIsActive(createBOMLineRequest.getIsActive());
		}

		if (line.getIsQtyPercentage() != null && Boolean.TRUE.equals(line.getIsQtyPercentage()))
		{
			bomLineRecord.setIsQtyPercentage(line.getIsQtyPercentage());
			bomLineRecord.setQtyBatch(line.getQty().toBigDecimal());
		}
		else
		{
			bomLineRecord.setQtyBOM(line.getQty().toBigDecimal());
		}

		if (line.getIssueMethod() != null)
		{
			bomLineRecord.setIssueMethod(line.getIssueMethod().getCode());
		}

		if (line.getScrap() != null)
		{
			bomLineRecord.setScrap(line.getScrap());
		}

		if (line.getLine() != null)
		{
			bomLineRecord.setLine(line.getLine());
		}

		saveRecord(bomLineRecord);
	}

	@Override
	@NonNull
	public ProductId getBOMProductId(@NonNull final ProductBOMId bomId)
	{
		final I_PP_Product_BOM bom = getById(bomId);
		return ProductId.ofRepoId(bom.getM_Product_ID());
	}

	@Override
	@NonNull
	public Optional<ProductBOMId> getLatestBOMByVersion(final @NonNull ProductBOMVersionsId bomVersionsId)
	{
		return getLatestBOMRecordByVersion(bomVersionsId, null)
				.map(I_PP_Product_BOM::getPP_Product_BOM_ID)
				.map(ProductBOMId::ofRepoId);
	}

	@Override
	@NonNull
	public Optional<I_PP_Product_BOM> getLatestBOMRecordByVersionId(final @NonNull ProductBOMVersionsId bomVersionsId)
	{
		return getLatestBOMRecordByVersion(bomVersionsId, null);
	}

	/**
	 * @param docStatus if set, then more recent versions without the given docstatus are skipped, and the returned version - if any - has this docStatus.
	 */
	@Override
	@NonNull
	public Optional<I_PP_Product_BOM> getPreviousVersion(final @NonNull I_PP_Product_BOM bomVersion, final @Nullable DocStatus docStatus)
	{
		final IQueryBuilder<I_PP_Product_BOM> queryBuilder = queryBL
				.createQueryBuilderOutOfTrx(I_PP_Product_BOM.class)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_PP_Product_BOM.COLUMNNAME_PP_Product_BOM_ID, bomVersion.getPP_Product_BOM_ID())
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_PP_Product_BOMVersions_ID, bomVersion.getPP_Product_BOMVersions_ID())
				.addCompareFilter(I_PP_Product_BOM.COLUMNNAME_ValidFrom, CompareQueryFilter.Operator.LESS_OR_EQUAL, bomVersion.getValidFrom());

		if (docStatus != null)
		{
			queryBuilder.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_DocStatus, docStatus.getCode());
		}

		return queryBuilder
				.orderByDescending(I_PP_Product_BOM.COLUMNNAME_ValidFrom)
				.create()
				.firstOptional(I_PP_Product_BOM.class);
	}

	@Override
	public boolean isComponent(final ProductId productId)
	{
		return queryBL
				.createQueryBuilder(I_PP_Product_BOMLine.class)
				.addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_M_Product_ID, productId.getRepoId())
				.addInArrayFilter(I_PP_Product_BOMLine.COLUMNNAME_ComponentType
						, X_PP_Product_BOMLine.COMPONENTTYPE_Component
						, X_PP_Product_BOMLine.COMPONENTTYPE_Variant)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

	@NonNull
	private Optional<I_PP_Product_BOM> getLatestBOMRecordByVersion(
			final @NonNull ProductBOMVersionsId bomVersionsId,
			final @Nullable BOMType bomType)
	{
		final I_PP_Product_BOMVersions bomVersions = getProductBOMVersionsDAO().getBOMVersions(bomVersionsId);

		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(bomVersions.getAD_Org_ID()));

		final IQueryBuilder<I_PP_Product_BOM> productBOMQueryBuilder = queryBL
				.createQueryBuilder(I_PP_Product_BOM.class)
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_DocStatus, X_PP_Product_BOM.DOCSTATUS_Completed)
				.addOnlyActiveRecordsFilter();

		if (bomType != null)
		{
			productBOMQueryBuilder.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_BOMType, bomType.getCode());
		}

		return productBOMQueryBuilder
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_PP_Product_BOMVersions_ID, bomVersionsId.getRepoId())
				.addCompareFilter(I_PP_Product_BOM.COLUMNNAME_ValidFrom, CompareQueryFilter.Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(ZonedDateTime.now(zoneId)))
				.orderByDescending(I_PP_Product_BOM.COLUMNNAME_ValidFrom)
				.create()
				.firstOptional(I_PP_Product_BOM.class);
	}

	@NonNull
	private Optional<I_PP_Product_BOM> getDefaultBOM(@NonNull final ProductBOMVersionsId bomVersionsId)
	{
		return Optionals.firstPresentOfSuppliers(
				() -> getLatestBOMRecordByVersion(bomVersionsId, BOMType.CurrentActive),
				() -> getLatestBOMRecordByVersion(bomVersionsId, BOMType.MakeToOrder));
	}

	@NonNull
	private DocTypeId getBOMDocTypeId(@NonNull final OrgId orgId)
	{
		final DocTypeQuery query = DocTypeQuery.builder()
				.adOrgId(orgId.getRepoId())
				.docBaseType(DocBaseType.BillOfMaterialVersion)
				.adClientId(Env.getAD_Client_ID())
				.build();

		return docTypeDAO.getDocTypeId(query);
	}

	public static Optional<IssuingToleranceSpec> extractIssuingToleranceSpec(@NonNull final I_PP_Product_BOMLine bomLine)
	{
		if (!bomLine.isEnforceIssuingTolerance())
		{
			return Optional.empty();
		}

		final IssuingToleranceValueType valueType = IssuingToleranceValueType.ofNullableCode(bomLine.getIssuingTolerance_ValueType());
		if (valueType == null)
		{
			throw new FillMandatoryException(I_M_Product.COLUMNNAME_IssuingTolerance_ValueType);
		}
		else if (valueType == IssuingToleranceValueType.PERCENTAGE)
		{
			final Percent percent = Percent.of(bomLine.getIssuingTolerance_Perc());
			return Optional.of(IssuingToleranceSpec.ofPercent(percent));
		}
		else if (valueType == IssuingToleranceValueType.QUANTITY)
		{
			final UomId uomId = UomId.ofRepoId(bomLine.getIssuingTolerance_UOM_ID());
			final Quantity qty = Quantitys.create(bomLine.getIssuingTolerance_Qty(), uomId);
			return Optional.of(IssuingToleranceSpec.ofQuantity(qty));
		}
		else
		{
			throw new AdempiereException("Unknown valueType: " + valueType);
		}
	}

	@Value
	@Builder
	private static class CreateBOMLineRequest
	{
		@NonNull
		OrgId orgId;

		@NonNull
		ProductBOMId bomId;

		@NonNull
		BOMCreateRequest.BOMLine line;

		@NonNull
		Instant validFrom;

		@Nullable
		Boolean isActive;
	}
}
