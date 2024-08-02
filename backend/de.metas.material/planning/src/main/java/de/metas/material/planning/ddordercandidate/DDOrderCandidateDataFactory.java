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

@Service
@RequiredArgsConstructor
class DDOrderCandidateDataFactory
{
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final ReplenishInfoRepository replenishInfoRepository;

	public List<DDOrderCandidateData> create(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context)
	{
		try
		{
			return create0(supplyRequiredDescriptor, context);
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("supplyRequiredDescriptor", supplyRequiredDescriptor)
					.setParameter("context", context);
		}
	}

	public List<DDOrderCandidateData> create0(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context)
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

		final Quantity qtyToSupplyTotal = extractQtyToSupply(supplyRequiredDescriptor);
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

			final Quantity qtyToMove = qtyToSupplyRemaining.multiply(networkLine.getTransferPercent());
			if (qtyToMove.signum() == 0)
			{
				continue;
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
					.ppOrderRef(supplyRequiredDescriptor.getPpOrderRef())
					.productDescriptor(createProductDescriptor(context))
					.fromWarehouseMinMaxDescriptor(replenishInfoRepository.getBy(sourceWarehouseId, context.getProductId()).toMinMaxDescriptor())
					.qty(qtyToMoveInProductUOM.toBigDecimal())
					.uomId(qtyToMoveInProductUOM.getUomId().getRepoId())
					.simulated(supplyRequiredDescriptor.isSimulated())
					.build();

			result.add(candidate);

			qtyToSupplyRemaining = qtyToSupplyRemaining.subtract(qtyToMove);
		} // end of the for-loop over networkLines

		//
		// Check: remaining qtyToSupply shall be ZERO
		if (qtyToSupplyRemaining.signum() != 0)
		{
			throw new AdempiereException("Cannot create DD Order Candidate for required Qty To Supply.")
					.setParameter("QtyToSupply", qtyToSupplyTotal)
					.setParameter("QtyToSupply (remaining)", qtyToSupplyRemaining)
					.setParameter("@DD_NetworkDistribution_ID@", network)
					.setParameter("context", context);
		}

		return result;
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
