package de.metas.costing.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostSegment;
import de.metas.costing.CostingLevel;
import de.metas.costing.CurrentCost;
import de.metas.costing.CurrentCostId;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Cost;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class CurrentCostsLoader
{
	private final IAcctSchemaDAO acctSchemasRepo;
	private final IUOMDAO uomsRepo;
	private final IProductCostingBL productCostingBL;
	private final ICostElementRepository costElementRepo;

	private final HashMap<UomId, I_C_UOM> uomCache = new HashMap<>();
	private final HashMap<CostElementId, CostElement> costElementCache = new HashMap<>();
	private final HashMap<AcctSchemaId, AcctSchema> acctSchemaCache = new HashMap<>();
	private final HashMap<ProductAndAcctSchemaId, CostingLevel> costingLevelsCache = new HashMap<>();

	@Builder
	private CurrentCostsLoader(
			@NonNull final IAcctSchemaDAO acctSchemasRepo,
			@NonNull final IUOMDAO uomsRepo,
			@NonNull final IProductCostingBL productCostingBL,
			@NonNull final ICostElementRepository costElementRepo)
	{
		this.acctSchemasRepo = acctSchemasRepo;
		this.uomsRepo = uomsRepo;
		this.productCostingBL = productCostingBL;
		this.costElementRepo = costElementRepo;
	}

	public ImmutableList<CurrentCost> toCurrentCosts(final Collection<I_M_Cost> records)
	{
		if (records.isEmpty())
		{
			return ImmutableList.of();
		}

		loadCostingLevelsForCostRecords(records);

		final ImmutableList.Builder<CurrentCost> result = ImmutableList.builder();
		for (final I_M_Cost record : records)
		{
			final CurrentCost currentCost = toCurrentCost(record);
			result.add(currentCost);
		}

		return result.build();
	}

	public CurrentCost toCurrentCost(@NonNull final I_M_Cost record)
	{
		final I_C_UOM uom = extractUOM(record);
		final CostElement costElement = extractCostElement(record);
		final AcctSchemaId acctSchemaId = extractAcctSchemaId(record);
		final AcctSchema acctSchema = getAcctSchema(acctSchemaId);
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		final ProductId productId = extractProductId(record);

		final CostSegment costSegment = CostSegment.builder()
				.costingLevel(getCostingLevel(productId, acctSchemaId))
				.acctSchemaId(acctSchema.getId())
				.costTypeId(acctSchema.getCosting().getCostTypeId())
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.build();

		return CurrentCost.builder()
				.id(CurrentCostId.ofRepoId(record.getM_Cost_ID()))
				.costSegment(costSegment)
				.costElement(costElement)
				.currencyId(currencyId)
				.precision(acctSchema.getCosting().getCostingPrecision())
				.uom(uom)
				.ownCostPrice(record.getCurrentCostPrice())
				.componentsCostPrice(record.getCurrentCostPriceLL())
				.currentQty(record.getCurrentQty())
				.cumulatedAmt(record.getCumulatedAmt())
				.cumulatedQty(record.getCumulatedQty())
				.build();
	}

	private void loadCostingLevelsForCostRecords(@NonNull final Collection<I_M_Cost> costRecords)
	{
		if (costRecords.isEmpty())
		{
			return;
		}

		final ImmutableSetMultimap<AcctSchemaId, ProductId> productIdsByAcctSchemaId = costRecords.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						CurrentCostsLoader::extractAcctSchemaId,
						CurrentCostsLoader::extractProductId
				));

		for (final AcctSchemaId acctSchemaId : productIdsByAcctSchemaId.keySet())
		{
			final AcctSchema acctSchema = getAcctSchema(acctSchemaId);

			final ImmutableSet<ProductId> productIds = productIdsByAcctSchemaId.get(acctSchemaId);
			final Map<ProductId, CostingLevel> costingLevels = productCostingBL.getCostingLevels(productIds, acctSchema);
			costingLevels.forEach((productId, costingLevel) -> {
				final ProductAndAcctSchemaId key = ProductAndAcctSchemaId.of(productId, acctSchemaId);
				this.costingLevelsCache.put(key, costingLevel);
			});
		}
	}

	private CostingLevel getCostingLevel(@NonNull final ProductId productId, @NonNull final AcctSchemaId acctSchemaId)
	{
		return this.costingLevelsCache.computeIfAbsent(
				ProductAndAcctSchemaId.of(productId, acctSchemaId),
				key -> productCostingBL.getCostingLevel(productId, getAcctSchema(acctSchemaId)));
	}

	@NonNull
	private static ProductId extractProductId(final @NonNull I_M_Cost record)
	{
		return ProductId.ofRepoId(record.getM_Product_ID());
	}

	@NonNull
	private CostElement extractCostElement(final @NonNull I_M_Cost record)
	{
		return costElementCache.computeIfAbsent(extractCostElementId(record), costElementRepo::getById);
	}

	@NonNull
	private static CostElementId extractCostElementId(final @NonNull I_M_Cost record)
	{
		return CostElementId.ofRepoId(record.getM_CostElement_ID());
	}

	private AcctSchema getAcctSchema(final AcctSchemaId acctSchemaId)
	{
		return acctSchemaCache.computeIfAbsent(acctSchemaId, acctSchemasRepo::getById);
	}

	@NonNull
	private static AcctSchemaId extractAcctSchemaId(final @NonNull I_M_Cost record)
	{
		return AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID());
	}

	private I_C_UOM extractUOM(final @NonNull I_M_Cost record)
	{
		return uomCache.computeIfAbsent(extractUomId(record), uomsRepo::getById);
	}

	private static UomId extractUomId(final @NonNull I_M_Cost record)
	{
		return UomId.ofRepoId(record.getC_UOM_ID());
	}

	//
	//
	//

	@Value(staticConstructor = "of")
	private static class ProductAndAcctSchemaId
	{
		@NonNull ProductId productId;
		@NonNull AcctSchemaId acctSchemaId;
	}
}
