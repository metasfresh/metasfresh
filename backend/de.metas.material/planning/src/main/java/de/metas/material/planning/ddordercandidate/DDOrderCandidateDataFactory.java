package de.metas.material.planning.ddordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddordercandidate.DDOrderCandidateData;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ddorder.DDOrderUtil;
import de.metas.material.planning.ddorder.DistributionNetwork;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.material.planning.ddorder.DistributionNetworkLine;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.material.replenish.ReplenishInfo;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DDOrderCandidateDataFactory
{
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final ReplenishInfoRepository replenishInfoRepository;
	/**
	 * Source-warehouse ATP provider used to clip each candidate so the remainder can spill
	 * over to manufacturing / purchasing (me03#28877). Pass {@link SourceWarehouseAvailableQtyProvider#UNLIMITED}
	 * when clipping is not desired (e.g. in tests that exercise the pre-ATP behavior).
	 */
	@NonNull private final SourceWarehouseAvailableQtyProvider availableQtyProvider;

	public List<DDOrderCandidateData> create(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context)
	{
		return create(supplyRequiredDescriptor, context, extractQtyToSupply(supplyRequiredDescriptor));
	}

	/**
	 * Variant that accepts an explicit {@code qtyToSupply} — used by the leftover-quantity
	 * dispatch in {@code SupplyRequiredHandler}, so that when a higher-priority advisor has
	 * already claimed part of the demand, the distribution candidates only cover the remainder.
	 */
	public List<DDOrderCandidateData> create(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context,
			@NonNull final Quantity qtyToSupply)
	{
		try
		{
			return create0(supplyRequiredDescriptor, context, qtyToSupply);
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("supplyRequiredDescriptor", supplyRequiredDescriptor)
					.setParameter("context", context)
					.setParameter("qtyToSupply", qtyToSupply);
		}
	}

	private List<DDOrderCandidateData> create0(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context,
			@NonNull final Quantity qtyToSupplyTotal)
	{
		if (context.getProductId().getRepoId() != supplyRequiredDescriptor.getProductId())
		{
			throw new AdempiereException("Product not matching: " + supplyRequiredDescriptor + ", " + context);
		}

		final ArrayList<DDOrderCandidateData> result = new ArrayList<>();

		final ProductPlanning productPlanningData = context.getProductPlanning();
		final ResourceId targetPlantId = context.getPlantId();

		if (productPlanningData.getDistributionNetworkId() == null)
		{
			// Indicates that the Product Planning Data for this product does not specify a valid network distribution.
			Loggables.addLog(
					"PP_Product_Planning has no DD_NetworkDistribution_ID; {} returns empty list; productPlanningData={}",
					this.getClass(), productPlanningData);

			return ImmutableList.of();
		}

		final DistributionNetwork network = distributionNetworkRepository.getById(productPlanningData.getDistributionNetworkId());
		final List<DistributionNetworkLine> networkLines = network.getLinesByTargetWarehouse(productPlanningData.getWarehouseIdNotNull());
		if (networkLines.isEmpty())
		{
			// No network lines were found for our target warehouse
			Loggables.addLog(
					"DD_NetworkDistribution has no lines for target M_Warehouse_ID={}; {} returns empty list; "
							+ "networkDistribution={}"
							+ "targetWarehouseId={}",
					productPlanningData.getWarehouseId(), this.getClass(), network, productPlanningData.getWarehouseId());
			return ImmutableList.of();
		}

		Quantity qtyToSupplyRemaining = qtyToSupplyTotal;
		for (final DistributionNetworkLine networkLine : networkLines)
		{
			if (qtyToSupplyRemaining.signum() <= 0)
			{
				break;
			}

			final WarehouseId sourceWarehouseId = networkLine.getSourceWarehouseId();
			final WarehouseId targetWarehouseId = networkLine.getTargetWarehouseId();

			if (!checkWarehouseInTransitExists(sourceWarehouseId))
			{
				continue;
			}

			final OrgId targetWarehouseOrgId = warehouseBL.getWarehouseOrgId(targetWarehouseId);
			if (!checkOrgBPartnerExists(targetWarehouseOrgId))
			{
				continue;
			}

			Quantity qtyToMove = qtyToSupplyRemaining.multiply(networkLine.getTransferPercent());
			if (qtyToMove.signum() == 0)
			{
				continue;
			}

			// Clip the line's share by the source warehouse's ATP. If the source cannot cover
			// the requested share, we take what we can and let the unclaimed remainder fall
			// through to the next advisor (manufacturing / purchasing) via the leftover-qty
			// dispatch in SupplyRequiredHandler. See me03#28877.
			final Optional<Quantity> atpAtSource = availableQtyProvider.availableQtyAtSource(
					sourceWarehouseId,
					context.getProductId(),
					extractStorageAttributesKey(context),
					supplyRequiredDescriptor.getDemandDate());
			if (atpAtSource.isPresent())
			{
				final Quantity atpInLineUOM = uomConversionBL.convertQuantityTo(
						atpAtSource.get(),
						context.getProductId(),
						qtyToMove.getUomId());
				if (atpInLineUOM.signum() <= 0)
				{
					Loggables.addLog(
							"Source warehouse {} has no ATP for product {}; skipping this network line; atp={}",
							sourceWarehouseId, context.getProductId(), atpAtSource.get());
					continue;
				}
				if (atpInLineUOM.compareTo(qtyToMove) < 0)
				{
					Loggables.addLog(
							"Clipping DD candidate qty {} to source-warehouse ATP {} (source {}, product {})",
							qtyToMove, atpInLineUOM, sourceWarehouseId, context.getProductId());
					qtyToMove = atpInLineUOM;
				}
			}

			final ProductPlanning productPlanning = context.getProductPlanning();

			final Quantity qtyToMoveInProductUOM = uomConversionBL.convertToProductUOM(qtyToMove, context.getProductId());

			final Instant supplyDate = supplyRequiredDescriptor.getDemandDate();
			final int durationDays = DDOrderUtil.calculateDurationDays(productPlanning, networkLine);
			final Instant demandDate = supplyDate.minus(durationDays, ChronoUnit.DAYS);

			final DDOrderCandidateData candidate = DDOrderCandidateData.builder()
					.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(ClientId.METASFRESH, targetWarehouseOrgId))
					.productPlanningId(productPlanning.getIdNotNull())
					.distributionNetworkAndLineId(DistributionNetworkAndLineId.of(network.getId(), networkLine.getId()))
					.sourceWarehouseId(sourceWarehouseId)
					.targetWarehouseId(targetWarehouseId)
					.targetPlantId(targetPlantId)
					.supplyDate(supplyDate)
					.demandDate(demandDate)
					.shipperId(networkLine.getShipperId())
					.customerId(BPartnerId.toRepoId(supplyRequiredDescriptor.getCustomerId()))
					.salesOrderLineId(supplyRequiredDescriptor.getOrderLineId())
					.forwardPPOrderRef(supplyRequiredDescriptor.getPpOrderRef())
					.productDescriptor(createProductDescriptor(context))
					.fromWarehouseMinMaxDescriptor(replenishInfoRepository.getBy(ReplenishInfo.Identifier.of(sourceWarehouseId, context.getProductId())).toMinMaxDescriptor())
					.qty(qtyToMoveInProductUOM.toBigDecimal())
					.uomId(qtyToMoveInProductUOM.getUomId().getRepoId())
					.simulated(supplyRequiredDescriptor.isSimulated())
					.build();

			result.add(candidate);

			qtyToSupplyRemaining = qtyToSupplyRemaining.subtract(qtyToMove);
		} // end of the for-loop over networkLines

		//
		// Leftover check: with source-warehouse ATP clipping, it is a legitimate outcome to end up
		// with qtyToSupplyRemaining > 0 (partial DD fulfillment). That remainder is handed back to
		// SupplyRequiredHandler via the advisor's consumedQty so the next advisor (manufacturing /
		// purchasing) can cover it. Log the shortfall for observability but do NOT throw.
		if (qtyToSupplyRemaining.signum() > 0)
		{
			Loggables.addLog(
					"DD_NetworkDistribution {} could only cover {} of the requested {}; remaining {} will be delegated to the next advisor",
					network, qtyToSupplyTotal.subtract(qtyToSupplyRemaining), qtyToSupplyTotal, qtyToSupplyRemaining);
		}

		return result;
	}

	private static AttributesKey extractStorageAttributesKey(@NonNull final MaterialPlanningContext context)
	{
		return AttributesKeys.createAttributesKeyFromASIStorageAttributes(context.getAttributeSetInstanceId())
				.orElse(AttributesKey.NONE);
	}

	private static ProductDescriptor createProductDescriptor(final @NonNull MaterialPlanningContext context)
	{
		final ProductId productId = context.getProductId();
		final AttributeSetInstanceId asiId = context.getAttributeSetInstanceId();

		final AttributesKey storageAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(asiId).orElse(AttributesKey.NONE);
		return ProductDescriptor.forProductAndAttributes(productId.getRepoId(), storageAttributesKey, asiId.getRepoId());
	}

	private boolean checkWarehouseInTransitExists(final WarehouseId sourceWarehouseId)
	{
		// Get the warehouse in transit
		final OrgId warehouseFromOrgId = warehouseBL.getWarehouseOrgId(sourceWarehouseId);
		final WarehouseId warehouseInTrasitId = DDOrderUtil.retrieveInTransitWarehouseIdIfExists(warehouseFromOrgId).orElse(null);
		if (warehouseInTrasitId == null)
		{
			// DRP-010: Do not exist Transit Warehouse to this Organization
			Loggables.addLog(
					"No in-transit warehouse found for AD_Org_ID={} of the source warehouse {}; {} returns empty list; "
							+ "sourceWarehouseId={}",
					warehouseFromOrgId.getRepoId(), sourceWarehouseId, this.getClass());

			return false;
		}
		else
		{
			return true;
		}
	}

	private boolean checkOrgBPartnerExists(@NonNull final OrgId orgId)
	{
		final BPartnerId orgBPartnerId = DDOrderUtil.retrieveOrgBPartnerId(orgId).orElse(null);
		if (orgBPartnerId == null)
		{
			// DRP-020: Target Org has no BP linked to it
			Loggables.addLog(
					"No org-bpartner found for AD_Org_ID={}; {} returns empty list; ",
					orgId.getRepoId(), this.getClass());
			return false;
		}
		else
		{
			return true;
		}

	}

	@NonNull
	private Quantity extractQtyToSupply(@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		final ProductId productId = ProductId.ofRepoId(supplyRequiredDescriptor.getProductId());
		final BigDecimal qtyToSupplyBD = supplyRequiredDescriptor.getQtyToSupplyBD();
		final I_C_UOM uom = productBL.getStockUOM(productId);
		return Quantity.of(qtyToSupplyBD, uom);
	}

}
