package de.metas.manufacturing.job.service.commands.issue_what_was_received;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.pporder.source_hu.PPOrderSourceHUService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.RawMaterialsIssueStrategy;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

class SourceHUsCollectionProvider
{
	// services
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO;
	@NonNull private final IPPOrderBOMBL ppOrderBOMBL;
	@NonNull private final PPOrderSourceHUService ppOrderSourceHUService;
	@NonNull private final SourceHUsService sourceHUsService;

	// params
	@NonNull private final PPOrderId ppOrderId;
	@NonNull private final WarehouseId manufacturingWarehouseId;
	@NonNull private final RawMaterialsIssueStrategy issueStrategy;

	// state
	@Nullable private ImmutableSet<HuId> _ppOrderSourceHUIds; // lazy

	@Builder
	private SourceHUsCollectionProvider(
			@NonNull final IHandlingUnitsDAO handlingUnitsDAO,
			@NonNull final IPPOrderBOMBL ppOrderBOMBL,
			@NonNull final PPOrderSourceHUService ppOrderSourceHUService,
			@NonNull final SourceHUsService sourceHUsService,
			//
			@NonNull final PPOrderId ppOrderId,
			@NonNull final WarehouseId manufacturingWarehouseId,
			@NonNull final RawMaterialsIssueStrategy issueStrategy)
	{
		this.handlingUnitsDAO = handlingUnitsDAO;
		this.ppOrderBOMBL = ppOrderBOMBL;
		this.ppOrderSourceHUService = ppOrderSourceHUService;
		this.sourceHUsService = sourceHUsService;

		this.ppOrderId = ppOrderId;
		this.manufacturingWarehouseId = manufacturingWarehouseId;
		this.issueStrategy = issueStrategy;
	}

	@NonNull
	public SourceHUsCollection getByProductId(@NonNull final ProductId productId)
	{
		switch (issueStrategy)
		{
			case AssignedHUsOnly:
				return retrieveActiveSourceHusForHus(productId);
			case DEFAULT:
				return retrieveActiveSourceHusFromWarehouse(productId);
			default:
				throw new AdempiereException("Unknown issue strategy!");
		}
	}

	private ImmutableSet<WarehouseId> getIssueFromWarehouseIds()
	{
		return ppOrderBOMBL.getIssueFromWarehouseIds(manufacturingWarehouseId);
	}

	private @NonNull ImmutableSet<HuId> getPPOrderSourceHUIds()
	{
		if (_ppOrderSourceHUIds == null)
		{
			_ppOrderSourceHUIds = ppOrderSourceHUService.getSourceHUIds(ppOrderId);
		}
		return _ppOrderSourceHUIds;
	}

	@NonNull
	private SourceHUsCollection retrieveActiveSourceHusFromWarehouse(@NonNull final ProductId productId)
	{
		final List<I_M_Source_HU> sourceHUs = sourceHUsService.retrieveMatchingSourceHuMarkers(
				SourceHUsService.MatchingSourceHusQuery.builder()
						.productId(productId)
						.warehouseIds(getIssueFromWarehouseIds())
						.build()
		);

		final List<I_M_HU> hus = handlingUnitsDAO.getByIds(extractHUIdsFromSourceHUs(sourceHUs));

		return SourceHUsCollection.builder()
				.husThatAreFlaggedAsSource(ImmutableList.copyOf(hus))
				.sourceHUs(sourceHUs)
				.build();
	}

	@NonNull
	private SourceHUsCollection retrieveActiveSourceHusForHus(@NonNull final ProductId productId)
	{
		final ImmutableList<I_M_HU> activeHUsMatchingProduct = handlingUnitsDAO.createHUQueryBuilder()
				.setOnlyActiveHUs(true)
				.setAllowEmptyStorage()
				.addOnlyHUIds(getPPOrderSourceHUIds())
				.addOnlyWithProductId(productId)
				.createQuery()
				.listImmutable(I_M_HU.class);

		final ImmutableList<I_M_Source_HU> sourceHUs = sourceHUsService.retrieveSourceHuMarkers(extractHUIdsFromHUs(activeHUsMatchingProduct));

		return SourceHUsCollection.builder()
				.husThatAreFlaggedAsSource(activeHUsMatchingProduct)
				.sourceHUs(sourceHUs)
				.build();
	}

	private static ImmutableSet<HuId> extractHUIdsFromSourceHUs(final Collection<I_M_Source_HU> sourceHUs)
	{
		return sourceHUs.stream().map(sourceHU -> HuId.ofRepoId(sourceHU.getM_HU_ID())).collect(ImmutableSet.toImmutableSet());
	}

	private static ImmutableSet<HuId> extractHUIdsFromHUs(final Collection<I_M_HU> hus)
	{
		return hus.stream().map(I_M_HU::getM_HU_ID).map(HuId::ofRepoId).collect(ImmutableSet.toImmutableSet());
	}
}
