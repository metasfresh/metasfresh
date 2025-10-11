package org.eevolution.process;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElement;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegment;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.costing.CostingMethod;
import de.metas.costing.ICostElementRepository;
import de.metas.costing.ICurrentCostsRepository;
import de.metas.costing.IProductCostingBL;
import de.metas.currency.CurrencyPrecision;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.IResourceProductService;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.RoutingService;
import de.metas.material.planning.RoutingServiceFactory;
import de.metas.material.planning.pporder.IPPRoutingRepository;
import de.metas.material.planning.pporder.PPRouting;
import de.metas.material.planning.pporder.PPRoutingActivity;
import de.metas.material.planning.pporder.PPRoutingActivityId;
import de.metas.material.planning.pporder.PPRoutingChangeRequest;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * RollUp of Cost Manufacturing Workflow
 * This process calculate the Labor, Overhead, Burden Cost
 *
 * @author Victor Perez, e-Evolution, S.C.
 * @version $Id: RollupWorkflow.java,v 1.1 2004/06/22 05:24:03 vpj-cd Exp $
 *
 * @author Bogdan Ioan, www.arhipac.ro
 *         <li>BF [ 2093001 ] Error in Cost Workflow & Process Details
 */
public class RollupWorkflow extends JavaProcess
{
	// services
	private final ICostElementRepository costElementsRepo = Adempiere.getBean(ICostElementRepository.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
	private final IResourceProductService resourceProductService = Services.get(IResourceProductService.class);
	private final IPPRoutingRepository routingsRepo = Services.get(IPPRoutingRepository.class);
	private final ICurrentCostsRepository currentCostsRepo = Adempiere.getBean(ICurrentCostsRepository.class);
	private final IProductPlanningDAO productPlanningsRepo = Services.get(IProductPlanningDAO.class);

	// parameters
	private OrgId p_AD_Org_ID = OrgId.ANY;
	private CostTypeId p_M_CostType_ID;
	private ProductId p_M_Product_ID;
	private ProductCategoryId p_M_Product_Category_ID;
	private CostingMethod p_ConstingMethod = CostingMethod.StandardCosting;
	private AcctSchema acctSchema = null;

	//
	private RoutingService routingService = null;
	private List<CostElement> costElements;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();

			if (para.getParameter() == null)
			{
				
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_AD_Org_ID))
			{
				p_AD_Org_ID = OrgId.ofRepoIdOrAny(para.getParameterAsInt());
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_C_AcctSchema_ID))
			{
				final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(para.getParameterAsInt());
				acctSchema = acctSchemasRepo.getById(acctSchemaId);
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_M_CostType_ID))
			{
				p_M_CostType_ID = CostTypeId.ofRepoIdOrNull(para.getParameterAsInt());
			}
			else if (name.equals(I_M_CostElement.COLUMNNAME_CostingMethod))
			{
				p_ConstingMethod = CostingMethod.ofCode(para.getParameterAsString());
			}
			else if (name.equals(I_M_Product.COLUMNNAME_M_Product_ID))
			{
				p_M_Product_ID = ProductId.ofRepoIdOrNull(para.getParameterAsInt());
			}
			else if (name.equals(I_M_Product.COLUMNNAME_M_Product_Category_ID))
			{
				p_M_Product_Category_ID = ProductCategoryId.ofRepoIdOrNull(para.getParameterAsInt());
			}
			else
			{
				log.error("prepare - Unknown Parameter: " + name);
			}
		}
	}	// prepare

	@Override
	protected String doIt()
	{
		routingService = RoutingServiceFactory.get().getRoutingService();
		costElements = costElementsRepo.getByCostingMethod(p_ConstingMethod);

		for (final I_M_Product product : getProducts())
		{
			rollup(product);
		}
		return "@OK@";
	}

	private void rollup(final I_M_Product product)
	{
		log.info("Product: {}", product);

		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		PPRoutingId routingId = null;
		ProductPlanning productPlanning = null;
		if (routingId == null)
		{
			routingId = routingsRepo.getRoutingIdByProductId(productId);
		}
		if (routingId == null)
		{
			productPlanning = productPlanningsRepo
					.find(ProductPlanningQuery.builder()
							.orgId(p_AD_Org_ID)
							.productId(productId)
							.build())
					.orElse(null);
			if (productPlanning != null)
			{
				routingId = productPlanning.getWorkflowId();
			}
			else
			{
				createNotice(product, "@NotFound@ @PP_Product_Planning_ID@");
			}
		}
		if (routingId == null)
		{
			createNotice(product, "@NotFound@ @AD_Workflow_ID@");
			return;
		}

		final PPRouting routing = routingsRepo.getById(routingId);
		rollup(Context.of(product, productPlanning, routing));
	}

	private List<I_M_Product> getProducts()
	{
		final IQueryBuilder<I_M_Product> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMNNAME_AD_Client_ID, getAD_Client_ID())
				.addEqualsFilter(I_M_Product.COLUMN_ProductType, X_M_Product.PRODUCTTYPE_Item)
				.addEqualsFilter(I_M_Product.COLUMN_IsBOM, true);

		if (p_M_Product_ID != null)
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, p_M_Product_ID);
		}
		else if (p_M_Product_Category_ID != null)
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMN_M_Product_ID, p_M_Product_Category_ID);
		}

		return queryBuilder
				.create()
				.list();
	}

	public void rollup(final Context context)
	{
		log.info("{}", context);

		final RoutingDurationsAndYield routingDurationsAndYield = computeRoutingDurationsAndYield(context.getRouting());

		final CostSegment costSegment = createCostSegment(context.getProduct());

		final List<RoutingActivitySegmentCost> costs = costElements.stream()
				.filter(costElement -> !costElement.isActivityControlElement())
				.map(costElement -> costSegment.withCostElementId(costElement.getId()))
				.flatMap(costSegmentAndElement -> computeRoutingSegmentCostsAndStream(context.getRouting(), costSegmentAndElement))
				.collect(ImmutableList.toImmutableList());

		//
		// Save to database
		updateCostRecords(costSegment, costs);
		updateRoutingRecord(context.getRouting().getId(), routingDurationsAndYield, costs);

		// // Update Product Data Planning
		// final ProductPlanning productPlanning = context.getProductPlanning();
		// if (productPlanning != null)
		// {
		// 	productPlanning.setYield(routingDurationsAndYield.getYield().toInt());
		// 	saveRecord(productPlanning);
		// }
	}

	private void updateRoutingRecord(final PPRoutingId routingId, final RoutingDurationsAndYield routingDurationsAndYield, final List<RoutingActivitySegmentCost> costs)
	{
		final PPRoutingChangeRequest changeRequest = PPRoutingChangeRequest.newInstance(routingId);

		changeRequest.setYield(routingDurationsAndYield.getYield());
		changeRequest.setQueuingTime(routingDurationsAndYield.getQueuingTime());
		changeRequest.setSetupTime(routingDurationsAndYield.getSetupTime());
		changeRequest.setDurationPerOneUnit(routingDurationsAndYield.getDurationPerOneUnit());
		changeRequest.setWaitingTime(routingDurationsAndYield.getWaitingTime());
		changeRequest.setMovingTime(routingDurationsAndYield.getMovingTime());

		costs.forEach(cost -> changeRequest.addActivityCost(cost.getRoutingActivityId(), cost.getCostAsBigDecimal()));
	}

	private void updateCostRecords(final CostSegment costSegment, final List<RoutingActivitySegmentCost> costs)
	{

		final Map<CostElementId, BigDecimal> costsByCostElementId = costs.stream().collect(RoutingActivitySegmentCost.groupByCostElementId());
		costsByCostElementId.forEach(updateCostRecord(costSegment));
	}

	private BiConsumer<CostElementId, BigDecimal> updateCostRecord(final CostSegment costSegment)
	{
		return (costElementId, costValue) -> currentCostsRepo.updateCostRecord(
				costSegment.withCostElementId(costElementId),
				costRecord -> costRecord.setCurrentCostPrice(costValue));
	}

	private RoutingDurationsAndYield computeRoutingDurationsAndYield(final PPRouting routing)
	{
		final RoutingDurationsAndYield result = new RoutingDurationsAndYield();
		routing.getActivities().forEach(result::addActivity);
		return result;
	}

	private Stream<RoutingActivitySegmentCost> computeRoutingSegmentCostsAndStream(final PPRouting routing, final CostSegmentAndElement costSegmentAndElement)
	{
		final CurrencyPrecision precision = getCostingPrecision(costSegmentAndElement.getAcctSchemaId());

		return routing.getActivities()
				.stream()
				.map(activity -> computeRoutingActivitySegmentCost(activity, costSegmentAndElement, precision));
	}

	private CurrencyPrecision getCostingPrecision(final AcctSchemaId acctSchemaId)
	{
		final AcctSchema acctSchema = acctSchemasRepo.getById(acctSchemaId);
		return acctSchema.getCosting().getCostingPrecision();
	}

	private RoutingActivitySegmentCost computeRoutingActivitySegmentCost(
			final PPRoutingActivity activity,
			final CostSegmentAndElement costSegmentAndElement,
			final CurrencyPrecision precision)
	{
		final ResourceId stdResourceId = activity.getResourceId();
		final CostSegmentAndElement resourceCostSegmentAndElement = createCostSegment(costSegmentAndElement, stdResourceId);

		final CostPrice rate = currentCostsRepo.getOrCreate(resourceCostSegmentAndElement)
				.getCostPrice();

		final Duration duration = routingService.getResourceBaseValue(activity);

		final CostAmount cost = rate.multiply(duration, activity.getDurationUnit().getTemporalUnit())
				.roundToPrecisionIfNeeded(precision);

		return RoutingActivitySegmentCost.of(cost, activity.getId(), costSegmentAndElement.getCostElementId());
	}

	private CostSegment createCostSegment(final I_M_Product product)
	{
		final CostTypeId costTypeId = p_M_CostType_ID != null ? p_M_CostType_ID : acctSchema.getCosting().getCostTypeId();
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(product, acctSchema);

		return CostSegment.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(acctSchema.getId())
				.costTypeId(costTypeId)
				.productId(ProductId.ofRepoId(product.getM_Product_ID()))
				.clientId(ClientId.ofRepoId(getAD_Client_ID()))
				.orgId(p_AD_Org_ID)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.build();
	}

	private CostSegmentAndElement createCostSegment(final CostSegmentAndElement costSegmentAndElement, final ResourceId resourceId)
	{
		final I_M_Product product = resourceProductService.getProductByResourceId(resourceId);
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final AcctSchema acctSchema = acctSchemasRepo.getById(costSegmentAndElement.getAcctSchemaId());
		final CostingLevel costingLevel = productCostingBL.getCostingLevel(product, acctSchema);

		return costSegmentAndElement.withProductIdAndCostingLevel(productId, costingLevel);
	}

	/**
	 * Create Cost Rollup Notice
	 */
	private void createNotice(final I_M_Product product, final String msg)
	{
		final String productValue = product != null ? product.getValue() : "-";
		addLog("WARNING: Product " + productValue + ": " + msg);
	}

	@lombok.Value(staticConstructor = "of")
	private static class Context
	{
		@NonNull I_M_Product product;
		@NonNull ProductPlanning productPlanning;
		@NonNull PPRouting routing;
	}

	@lombok.Getter
	@lombok.ToString
	private static class RoutingDurationsAndYield
	{
		private Percent yield = Percent.ONE_HUNDRED;
		private Duration queuingTime = Duration.ZERO;
		private Duration setupTime = Duration.ZERO;
		private Duration durationPerOneUnit = Duration.ZERO;
		private Duration waitingTime = Duration.ZERO;
		private Duration movingTime = Duration.ZERO;

		public void addActivity(final PPRoutingActivity activity)
		{
			if (!activity.getYield().isZero())
			{
				yield = yield.multiply(activity.getYield(), 0);
			}

			queuingTime = queuingTime.plus(activity.getQueuingTime());
			setupTime = setupTime.plus(activity.getSetupTime());
			waitingTime = waitingTime.plus(activity.getWaitingTime());
			movingTime = movingTime.plus(activity.getMovingTime());

			// We use node.getDuration() instead of m_routingService.estimateWorkingTime(node) because
			// this will be the minimum duration of this node. So even if the node have defined units/cycle
			// we consider entire duration of the node.
			durationPerOneUnit = durationPerOneUnit.plus(activity.getDurationPerOneUnit());
		}
	}

	@lombok.Value(staticConstructor = "of")
	private static class RoutingActivitySegmentCost
	{
		public static Collector<RoutingActivitySegmentCost, ?, Map<CostElementId, BigDecimal>> groupByCostElementId()
		{
			return Collectors.groupingBy(RoutingActivitySegmentCost::getCostElementId, sumCostsAsBigDecimal());
		}

		private static Collector<RoutingActivitySegmentCost, ?, BigDecimal> sumCostsAsBigDecimal()
		{
			return Collectors.reducing(BigDecimal.ZERO, RoutingActivitySegmentCost::getCostAsBigDecimal, BigDecimal::add);
		}

		@NonNull
		CostAmount cost;
		@NonNull
		PPRoutingActivityId routingActivityId;
		@NonNull
		CostElementId costElementId;

		public BigDecimal getCostAsBigDecimal()
		{
			return cost.toBigDecimal();
		}
	}
}
