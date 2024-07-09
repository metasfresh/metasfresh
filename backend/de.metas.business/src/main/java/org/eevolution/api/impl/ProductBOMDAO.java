package org.eevolution.api.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
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
import org.adempiere.ad.dao.ICompositeQueryFilter;
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
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.BOMCreateRequest;
import org.eevolution.api.BOMType;
import org.eevolution.api.BOMUse;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicReference;

import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.GREATER;
import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.LESS_OR_EQUAL;
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

	private final CCache<ProductBOMRequest, Optional<ProductBOM>> productBOMCCache = CCache.<ProductBOMRequest, Optional<ProductBOM>>builder()
			.tableName(I_PP_Product_BOM.Table_Name)
			.additionalTableNameToResetFor(I_PP_Product_BOMLine.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(2000)
			.build();

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
				.flatMap(this::getDefaultBOMRecordByVersionId);
	}

	@Override
	public Optional<I_PP_Product_BOM> getByProductIdAndType(@NonNull final ProductId productId, @NonNull final Set<BOMType> bomTypes)
	{
		return getProductBOMVersionsDAO()
				.retrieveBOMVersionsId(productId)
				.flatMap(bomVersionsId -> getLatestBOMRecordByVersionAndType(bomVersionsId, bomTypes));
	}

	@Override
	public Optional<ProductBOM> retrieveValidProductBOM(@NonNull final ProductBOMRequest request)
	{
		return productBOMCCache.getOrLoad(request, this::retrieveValidProductBOM0);
	}

	private Optional<ProductBOM> retrieveValidProductBOM0(@NonNull final ProductBOMRequest request)
	{
		final ProductId productId = ProductId.ofRepoId(request.getProductDescriptor().getProductId());
		final ICompositeQueryFilter<I_PP_Product_BOM> validToFilter = queryBL.createCompositeQueryFilter(I_PP_Product_BOM.class)
				.setJoinOr()
				.addCompareFilter(I_PP_Product_BOM.COLUMNNAME_ValidTo, GREATER, request.getDate())
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_ValidTo, null);
		return queryBL.createQueryBuilder(I_PP_Product_BOM.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_BOMType, BOMType.CurrentActive.getCode())
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_BOMUse, BOMUse.Manufacturing.getCode())
				.addCompareFilter(I_PP_Product_BOM.COLUMNNAME_ValidFrom, LESS_OR_EQUAL, request.getDate())
				.orderByDescending(I_PP_Product_BOM.COLUMNNAME_ValidFrom)
				.orderByDescending(I_PP_Product_BOM.COLUMNNAME_AD_Org_ID)
				.orderByDescending(I_PP_Product_BOM.COLUMNNAME_PP_Product_BOM_ID)
				.filter(validToFilter)
				.create()
				.firstOptional(I_PP_Product_BOM.class)
				.map(productBOM -> toProductBOM(productBOM, request));
	}

    private ProductBOM toProductBOM(@NonNull final I_PP_Product_BOM ppProductBom, @NonNull final ProductBOMRequest request)
    {
        final ProductBOMId productBOMId = ProductBOMId.ofRepoId(ppProductBom.getPP_Product_BOM_ID());
        final ImmutableList<I_PP_Product_BOMLine> bomLines = queryBL.createQueryBuilder(I_PP_Product_BOMLine.class)
                .addOnlyActiveRecordsFilter()
                .addEqualsFilter(I_PP_Product_BOMLine.COLUMNNAME_PP_Product_BOM_ID, productBOMId)
                .create()
                .listImmutable(I_PP_Product_BOMLine.class);

        final List<I_PP_Product_BOMLine> components = bomLines.stream()
                .filter((bomLine) -> bomLine.getComponentType() != null)
                .filter((bomLine) -> BOMComponentType.ofCode(bomLine.getComponentType()).isComponent())
                .toList();

        final Map<ProductDescriptor, ProductBOM> componentsProductBOMs = new HashMap<>();
        for (final I_PP_Product_BOMLine component : components)
        {
            final ProductId productId = ProductId.ofRepoId(component.getM_Product_ID());

            final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(productId.getRepoId(), AttributesKey.NONE, component.getM_AttributeSetInstance_ID());
            final ProductBOMRequest subBOMRequest = ProductBOMRequest.builder()
                    .productDescriptor(productDescriptor)
                    .date(request.getDate())
                    .build();

            retrieveValidProductBOM(subBOMRequest).ifPresent(subBOM -> componentsProductBOMs.put(productDescriptor, subBOM));
        }

        final List<ProductBOMLine> coProducts = bomLines.stream()
                .filter((bomLine) -> bomLine.getComponentType() != null)
                .filter((bomLine) -> BOMComponentType.ofCode(bomLine.getComponentType()).isCoProduct())
                .map(this::toProductBOMLine)
                .toList();

        return ProductBOM.builder()
                .productBOMId(productBOMId)
                .productDescriptor(request.getProductDescriptor())
                .uomId(UomId.ofRepoId(ppProductBom.getC_UOM_ID()))
                .components(components)
                .componentsProductBOMs(componentsProductBOMs)
                .coProducts(coProducts)
                .build();
    }

    private ProductBOMLine toProductBOMLine(@NonNull final I_PP_Product_BOMLine record)
    {
        return ProductBOMLine.builder()
                .productBOMLineId(ProductBOMLineId.ofRepoId(record.getPP_Product_BOMLine_ID()))
                .productId(ProductId.ofRepoId(record.getM_Product_ID()))
                .build();
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

		if (request.getResourceId() != null)
		{
			bomRecord.setS_PreferredResource_ID(request.getResourceId().getRepoId());
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

		if (line.getHelp() != null)
		{
			bomLineRecord.setHelp(line.getHelp());
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
	public boolean hasBOMs(final @NonNull ProductBOMVersionsId bomVersionsId)
	{
		return queryBL.createQueryBuilder(I_PP_Product_BOM.class)
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_PP_Product_BOMVersions_ID, bomVersionsId)
				.create()
				.anyMatch();
	}

	/**
	 * @param docStatus if set, then more recent versions without the given docstatus are skipped, and the returned version - if any - has this docStatus.
	 */
	@Override
	@NonNull
	public Optional<I_PP_Product_BOM> getPreviousVersion(final @NonNull I_PP_Product_BOM bomVersion, final @Nullable Set<BOMType> bomTypes, final @Nullable DocStatus docStatus)
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

		if (bomTypes != null)
		{
			queryBuilder.addInArrayFilter(I_PP_Product_BOM.COLUMNNAME_BOMType, bomTypes);
		}

		return queryBuilder
				.orderByDescending(I_PP_Product_BOM.COLUMNNAME_ValidFrom)
				.create()
				.firstOptional(I_PP_Product_BOM.class);
	}

	@Override
	@NonNull
	public List<I_PP_Product_BOM> getSiblings(final @NonNull I_PP_Product_BOM productBom)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_PP_Product_BOM.class)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_PP_Product_BOM.COLUMNNAME_PP_Product_BOM_ID, productBom.getPP_Product_BOM_ID())
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_PP_Product_BOMVersions_ID, productBom.getPP_Product_BOMVersions_ID())
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_BOMType, productBom.getBOMType())
				.orderByDescending(I_PP_Product_BOM.COLUMNNAME_ValidFrom)
				.create()
				.list();
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

	@Override
	public Optional<ProductBOMId> getLatestBOMIdByVersionAndType(@NonNull final ProductBOMVersionsId bomVersionsId, final @Nullable Set<BOMType> bomTypes)
	{
		return Optional.ofNullable(
				queryLatestBOMRecordByVersionAndType(bomVersionsId, bomTypes).firstId(ProductBOMId::ofRepoIdOrNull)
		);
	}

	@Override
	@NonNull
	public Optional<I_PP_Product_BOM> getLatestBOMRecordByVersionAndType(
			final @NonNull ProductBOMVersionsId bomVersionsId,
			final @Nullable Set<BOMType> bomTypes)
	{
		return queryLatestBOMRecordByVersionAndType(bomVersionsId, bomTypes).firstOptional(I_PP_Product_BOM.class);
	}

	@NonNull
	private IQuery<I_PP_Product_BOM> queryLatestBOMRecordByVersionAndType(
			final @NonNull ProductBOMVersionsId bomVersionsId,
			final @Nullable Set<BOMType> bomTypes)
	{
		final I_PP_Product_BOMVersions bomVersions = getProductBOMVersionsDAO().getBOMVersions(bomVersionsId);

		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(bomVersions.getAD_Org_ID()));

		final IQueryBuilder<I_PP_Product_BOM> productBOMQueryBuilder = queryBL
				.createQueryBuilder(I_PP_Product_BOM.class)
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_DocStatus, X_PP_Product_BOM.DOCSTATUS_Completed)
				.addOnlyActiveRecordsFilter();

		if (!Check.isEmpty(bomTypes))
		{
			productBOMQueryBuilder.addInArrayFilter(I_PP_Product_BOM.COLUMNNAME_BOMType, bomTypes);
		}

		return productBOMQueryBuilder
				.addEqualsFilter(I_PP_Product_BOM.COLUMNNAME_PP_Product_BOMVersions_ID, bomVersionsId.getRepoId())
				.addCompareFilter(I_PP_Product_BOM.COLUMNNAME_ValidFrom, CompareQueryFilter.Operator.LESS_OR_EQUAL, TimeUtil.asTimestamp(ZonedDateTime.now(zoneId)))
				.orderByDescending(I_PP_Product_BOM.COLUMNNAME_ValidFrom)
				.orderByDescending(I_PP_Product_BOM.COLUMNNAME_PP_Product_BOM_ID)
				.create();
	}

	@NonNull
	private Optional<I_PP_Product_BOM> getDefaultBOMRecordByVersionId(@NonNull final ProductBOMVersionsId bomVersionsId)
	{
		return Optionals.firstPresentOfSuppliers(
				() -> getLatestBOMRecordByVersionAndType(bomVersionsId, ImmutableSet.of(BOMType.CurrentActive)),
				() -> getLatestBOMRecordByVersionAndType(bomVersionsId, ImmutableSet.of(BOMType.MakeToOrder)));
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
			final Quantity qty = Quantitys.of(bomLine.getIssuingTolerance_Qty(), uomId);
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
