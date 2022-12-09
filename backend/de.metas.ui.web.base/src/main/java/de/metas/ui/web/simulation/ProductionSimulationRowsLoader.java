/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.ui.web.simulation;

import com.google.common.collect.ImmutableList;
import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DemandDetailsQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.commons.repository.query.SimulatedQueryQualifier;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.organization.IOrgDAO;
import de.metas.product.ProductId;
import de.metas.ui.web.window.datatypes.ColorValue;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_OrderLine_Candidate;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Value
public class ProductionSimulationRowsLoader
{
	@NonNull
	IOrgDAO orgDAO;

	@NonNull
	LookupDataSource productLookup;

	@NonNull
	LookupDataSource attributeSetInstanceLookup;

	@NonNull
	LookupDataSource warehouseLookup;

	@NonNull
	CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	@NonNull
	PPOrderCandidateDAO ppOrderCandidateDAO;

	@NonNull
	OrderLineDescriptor orderLineDescriptor;

	@NonNull
	Map<CandidateId, Candidate> candidateId2Stock = new HashMap<>();

	@Builder
	public ProductionSimulationRowsLoader(
			@NonNull final LookupDataSource productLookup,
			@NonNull final LookupDataSource attributeSetInstanceLookup,
			@NonNull final LookupDataSource warehouseLookup,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final PPOrderCandidateDAO ppOrderCandidateDAO,
			@NonNull final OrderLineDescriptor orderLineDescriptor,
			@NonNull final IOrgDAO orgDAO)
	{
		this.productLookup = productLookup;
		this.attributeSetInstanceLookup = attributeSetInstanceLookup;
		this.warehouseLookup = warehouseLookup;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
		this.ppOrderCandidateDAO = ppOrderCandidateDAO;
		this.orderLineDescriptor = orderLineDescriptor;
		this.orgDAO = orgDAO;
	}

	@NonNull
	public ProductionSimulationRows load()
	{
		final ImmutableList<ProductionSimulationRow> rows = buildRowsFromCandidates(getCandidatesToDisplay());

		return ProductionSimulationRows.builder()
				.rows(rows)
				.build();
	}

	@NonNull
	private ImmutableList<ProductionSimulationRow> buildRowsFromCandidates(@NonNull final ImmutableList<Candidate> demandOrSupplyCandidates)
	{
		final Iterator<Candidate> candidateIterator = demandOrSupplyCandidates.iterator();

		//dev-note: we made sure that the first candidate is always the simulated demand
		final Candidate simulatedDemandCandidate = candidateIterator.next();

		final ProductionSimulationRow.ProductionSimulationRowBuilder simulatedDemandRowBuilder = getRowBuilderForCandidateAndStock(simulatedDemandCandidate, candidateId2Stock.get(simulatedDemandCandidate.getId()));

		final ImmutableList.Builder<ProductionSimulationRow> includedRowsToDisplay = ImmutableList.builder();

		while (candidateIterator.hasNext())
		{
			final Candidate candidate = candidateIterator.next();

			final Candidate stockCandidate = candidateId2Stock.get(candidate.getId());

			final ProductionSimulationRow productionSimulationRow = buildRowFromCandidateAndStock(candidate, stockCandidate);

			includedRowsToDisplay.add(productionSimulationRow);
		}

		simulatedDemandRowBuilder.includedRows(includedRowsToDisplay.build());

		return ImmutableList.of(simulatedDemandRowBuilder.build());
	}

	@NonNull
	private ProductionSimulationRow.ProductionSimulationRowBuilder getRowBuilderForCandidateAndStock(@NonNull final Candidate candidate, @NonNull final Candidate stockCandidate)
	{
		final ZoneId zoneId = orgDAO.getTimeZone(candidate.getClientAndOrgId().getOrgId());

		final ZonedDateTime dateProjected = TimeUtil.asZonedDateTime(candidate.getDate(), zoneId);

		final ProductionSimulationRow.ProductionSimulationRowBuilder productionSimulationRowBuilder = ProductionSimulationRow.builder()
				.rowId(buildRowId(candidate))
				.product(productLookup.findById(ProductId.ofRepoId(candidate.getProductId())))
				.attributeSetInstance(attributeSetInstanceLookup.findById(AttributeSetInstanceId.ofRepoIdOrNone(candidate.getMaterialDescriptor().getAttributeSetInstanceId())))
				.warehouse(warehouseLookup.findById(candidate.getWarehouseId()))
				.qty(candidate.getQuantity())
				.dateProjected(dateProjected)
				.type(candidate.getType().getCode())
				.simulatedStock(stockCandidate.getQuantity())
				.lineStatusColor(stockCandidate.getQuantity().signum() >= 0 ? ColorValue.GREEN : null)
				.isSimulated(candidate.isSimulated());

		if (candidate.getBusinessCase() != null)
		{
			productionSimulationRowBuilder.businessCase(candidate.getBusinessCase().getCode());
		}

		return productionSimulationRowBuilder;
	}

	@NonNull
	private ProductionSimulationRow buildRowFromCandidateAndStock(@NonNull final Candidate candidate, @NonNull final Candidate stockCandidate)
	{
		final ProductionSimulationRow.ProductionSimulationRowBuilder productionSimulationRowBuilder = getRowBuilderForCandidateAndStock(candidate, stockCandidate);

		if (CandidateBusinessCase.PRODUCTION.equals(candidate.getBusinessCase())
				&& CandidateType.SUPPLY.equals(candidate.getType()))
		{
			productionSimulationRowBuilder.includedRows(getIncludedRowsForProductionSupply(candidate));
		}
		else if (CandidateBusinessCase.PRODUCTION.equals(candidate.getBusinessCase())
				&& CandidateType.DEMAND.equals(candidate.getType()))
		{
			final ImmutableList<ProductionSimulationRow> includedRows = getIncludedRowForProductionDemand(candidate)
					.map(ImmutableList::of)
					.orElseGet(ImmutableList::of);

			productionSimulationRowBuilder.includedRows(includedRows);
		}

		return productionSimulationRowBuilder.build();
	}

	@NonNull
	private ProductionSimulationRow buildRowFromCandidate(@NonNull final Candidate candidate)
	{
		final Candidate stockCandidate = getStockCandidate(candidate);

		return buildRowFromCandidateAndStock(candidate, stockCandidate);
	}

	@NonNull
	private ImmutableList<ProductionSimulationRow> getIncludedRowsForProductionSupply(@NonNull final Candidate candidate)
	{
		final ProductionDetail productionDetail = ProductionDetail.cast(candidate.getBusinessCaseDetail());

		final ImmutableList<I_PP_OrderLine_Candidate> ppOrderLineCandidates = getPPOrderLineCandidates(productionDetail);

		return ppOrderLineCandidates
				.stream()
				.map(this::getDemandCandidateForPPOrderLineCand)
				.map(this::buildRowFromCandidate)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private Optional<ProductionSimulationRow> getIncludedRowForProductionDemand(@NonNull final Candidate candidate)
	{
		return getResolvedSupplyCandidateForDemandCandidate(candidate)
				.map(this::buildRowFromCandidate);
	}

	@NonNull
	private ImmutableList<I_PP_OrderLine_Candidate> getPPOrderLineCandidates(@NonNull final ProductionDetail productionDetail)
	{
		final PPOrderCandidateId ppOrderCandidateId = PPOrderCandidateId.ofRepoIdOrNull(productionDetail.getPpOrderCandidateId());

		if (ppOrderCandidateId == null)
		{
			return ImmutableList.of();
		}

		return ppOrderCandidateDAO.getLinesByCandidateId(ppOrderCandidateId);
	}

	@NonNull
	private Optional<Candidate> getResolvedSupplyCandidateForDemandCandidate(@NonNull final Candidate demandCandidate)
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.warehouseId(demandCandidate.getMaterialDescriptor().getWarehouseId())
				.productId(demandCandidate.getMaterialDescriptor().getProductId())
				.storageAttributesKey(demandCandidate.getMaterialDescriptor().getStorageAttributesKey())
				.customer(BPartnerClassifier.specificOrAny(demandCandidate.getMaterialDescriptor().getCustomerId()))
				.customerIdOperator(MaterialDescriptorQuery.CustomerIdOperator.GIVEN_ID_OR_NULL)
				.build();

		final CandidatesQuery lookupSupplyQuery = CandidatesQuery.builder()
				.demandDetailsQuery(DemandDetailsQuery.ofDemandDetail(demandCandidate.getDemandDetail()))
				.materialDescriptorQuery(materialDescriptorQuery)
				.type(CandidateType.SUPPLY)
				.simulatedQueryQualifier(SimulatedQueryQualifier.INCLUDE_SIMULATED)
				.build();

		return candidateRepositoryRetrieval.retrieveLatestMatch(lookupSupplyQuery);
	}

	@NonNull
	private Candidate getDemandCandidateForPPOrderLineCand(@NonNull final I_PP_OrderLine_Candidate orderLineCandidate)
	{
		final ProductionDetailsQuery productionDetailsQuery = ProductionDetailsQuery.builder()
				.ppOrderCandidateLineId(orderLineCandidate.getPP_OrderLine_Candidate_ID())
				.build();

		return candidateRepositoryRetrieval.retrieveLatestMatch(CandidatesQuery.builder()
																		.productionDetailsQuery(productionDetailsQuery)
																		.simulatedQueryQualifier(SimulatedQueryQualifier.INCLUDE_SIMULATED)
																		.build())
				.orElseThrow(() -> new AdempiereException("No Demand candidate found for PP_OrderLine_Candidate=" + orderLineCandidate.getPP_OrderLine_Candidate_ID()));
	}

	@NonNull
	private Candidate getStockCandidate(@NonNull final Candidate candidate)
	{
		if (CandidateId.isRegularNonNull(candidate.getParentId()))
		{
			return Optional.ofNullable(candidateRepositoryRetrieval.retrieveLatestMatchOrNull(CandidatesQuery.fromId(candidate.getParentId())))
					.orElseThrow(() -> new AdempiereException("No stock candidate found for candidate: ").appendParametersToMessage()
							.setParameter("candidate", candidate));
		}
		else
		{
			return candidateRepositoryRetrieval
					.retrieveSingleChild(candidate.getId())
					.orElseThrow(() -> new AdempiereException("No stock candidate found for candidate: ").appendParametersToMessage()
							.setParameter("candidate", candidate));
		}
	}

	@NonNull
	private ImmutableList<Candidate> getCandidatesToDisplay()
	{
		final ImmutableList<Candidate> candidates = getAllMatchingCandidatesOrdered();

		mapCandidateId2Stock(candidates);

		applySimulatedChangesToCandidates(candidates);

		return removeNonRelevantCandidates(candidates);
	}

	@NonNull
	private ImmutableList<Candidate> getAllMatchingCandidatesOrdered()
	{
		final Candidate simulatedDemandCandidate = getSimulatedDemandCandidate();

		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.warehouseId(simulatedDemandCandidate.getMaterialDescriptor().getWarehouseId())
				.productId(simulatedDemandCandidate.getMaterialDescriptor().getProductId())
				.storageAttributesKey(simulatedDemandCandidate.getMaterialDescriptor().getStorageAttributesKey())
				.timeRangeStart(DateAndSeqNo.builder()
										.date(simulatedDemandCandidate.getDate())
										.seqNo(simulatedDemandCandidate.getSeqNo())
										.operator(DateAndSeqNo.Operator.EXCLUSIVE)
										.build())
				.build();

		final CandidatesQuery candidatesQuery = CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.simulatedQueryQualifier(SimulatedQueryQualifier.INCLUDE_SIMULATED)
				.build();

		final ImmutableList.Builder<Candidate> sortedCandidates = ImmutableList.builder();
		sortedCandidates.add(simulatedDemandCandidate);
		sortedCandidates.add(getStockCandidate(simulatedDemandCandidate));
		//FIXME: avoid loading candidates that will be later filtered out
		sortedCandidates.addAll(candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(candidatesQuery));

		return sortedCandidates.build();
	}

	@NonNull
	private Candidate getSimulatedDemandCandidate()
	{
		final CandidatesQuery candidatesQuery = CandidatesQuery.builder()
				.demandDetailsQuery(DemandDetailsQuery.forDocumentLine(orderLineDescriptor))
				.simulatedQueryQualifier(SimulatedQueryQualifier.ONLY_SIMULATED)
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.SHIPMENT)
				.build();

		return candidateRepositoryRetrieval.retrieveLatestMatch(candidatesQuery)
				.orElseThrow(() -> new AdempiereException("No MD_Candidate found for orderLineDescriptor=" + orderLineDescriptor));
	}

	private void applySimulatedChangesToCandidates(@NonNull final List<Candidate> orderedCandidates)
	{
		boolean nonSimulatedCandidateEncountered = false;
		BigDecimal qtyToAdd = BigDecimal.ZERO;

		for (final Candidate demandOrSupplyCandidate : orderedCandidates)
		{
			if (CandidateType.STOCK.equals(demandOrSupplyCandidate.getType()))
			{
				continue;
			}

			if (!demandOrSupplyCandidate.isSimulated())
			{
				nonSimulatedCandidateEncountered = true;
			}

			if (nonSimulatedCandidateEncountered)
			{
				final Candidate stockCandidate = candidateId2Stock.get(demandOrSupplyCandidate.getId());

				final BigDecimal newQty = stockCandidate.getQuantity().add(qtyToAdd);

				candidateId2Stock.put(demandOrSupplyCandidate.getId(), stockCandidate.withQuantity(newQty));
			}

			if (demandOrSupplyCandidate.isSimulated())
			{
				final boolean isSupply = CandidateId.isRegularNonNull(demandOrSupplyCandidate.getParentId());

				qtyToAdd = qtyToAdd.add(isSupply ? demandOrSupplyCandidate.getQuantity() : demandOrSupplyCandidate.getQuantity().negate());
			}
		}
	}

	private void mapCandidateId2Stock(@NonNull final List<Candidate> candidates)
	{
		final Function<Candidate, Candidate> locateMatchingStockCandidate = (candidate) -> candidates.stream()
				.filter(potentialStockCandidate -> CandidateType.STOCK.equals(potentialStockCandidate.getType()))
				.filter(potentialStockCandidate -> {

					final boolean isStockForDemandCandidate = CandidateId.isRegularNonNull(potentialStockCandidate.getParentId()) && potentialStockCandidate.getParentId().equals(candidate.getId());

					final boolean isStockForSupplyCandidate = CandidateId.isRegularNonNull(candidate.getParentId()) && candidate.getParentId().equals(potentialStockCandidate.getId());

					return isStockForDemandCandidate || isStockForSupplyCandidate;
				})
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No StockCandidate found for Candidate!")
						.appendParametersToMessage()
						.setParameter("MD_Candidate_ID", candidate.getId()));

		candidates.stream()
				.filter(candidate -> !CandidateType.STOCK.equals(candidate.getType()))
				.forEach(candidate -> candidateId2Stock.put(candidate.getId(), locateMatchingStockCandidate.apply(candidate)));
	}

	/**
	 * We consider relevant only the candidates created till the point where the simulated demand is "resolved",
	 * i.e. we reached a point in time where there is enough stock to fulfill the demand.
	 */
	@NonNull
	private ImmutableList<Candidate> removeNonRelevantCandidates(@NonNull final List<Candidate> candidates)
	{
		final ImmutableList.Builder<Candidate> onlyRelevantCandidatesCollector = ImmutableList.builder();

		for (final Candidate candidate : candidates)
		{
			if (candidate.isSimulated() && candidate.getQuantity().signum() == 0)
			{
				continue;
			}

			if (CandidateType.STOCK.equals(candidate.getType()))
			{
				continue;
			}

			onlyRelevantCandidatesCollector.add(candidate);

			final boolean isEnoughStockToResolveDemand = candidateId2Stock.get(candidate.getId()).getQuantity().signum() >= 0;

			if (isEnoughStockToResolveDemand)
			{
				break;
			}
		}

		return onlyRelevantCandidatesCollector.build();
	}

	private DocumentId buildRowId(@NonNull final Candidate candidate)
	{
		return DocumentId.of(candidate.getId());
	}
}