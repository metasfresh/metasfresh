package de.metas.shipment.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipment_Declaration;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.shipment.ShipmentDeclaration;
import de.metas.shipment.ShipmentDeclarationConfig;
import de.metas.shipment.ShipmentDeclarationLine;
import de.metas.shipment.ShipmentDeclarationVetoer;
import de.metas.shipment.repo.ShipmentDeclarationConfigRepository;
import de.metas.shipment.repo.ShipmentDeclarationRepository;
import de.metas.uom.IUOMDAO;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Service
public class ShipmentDeclarationCreator
{
	private static final Logger logger = LogManager.getLogger(ShipmentDeclarationCreator.class);

	private final ShipmentDeclarationRepository shipmentDeclarationRepo;
	private final ShipmentDeclarationConfigRepository shipmentDeclarationConfigRepo;
	private final ImmutableSet<ShipmentDeclarationVetoer> shipmentDeclarationVetoers;

	public ShipmentDeclarationCreator(
			@NonNull final ShipmentDeclarationRepository shipmentDeclarationRepo,
			@NonNull final ShipmentDeclarationConfigRepository shipmentDeclarationConfigRepo,
			@NonNull final Optional<List<ShipmentDeclarationVetoer>> shipmentDeclarationVetoers)
	{
		this.shipmentDeclarationRepo = shipmentDeclarationRepo;
		this.shipmentDeclarationConfigRepo = shipmentDeclarationConfigRepo;

		this.shipmentDeclarationVetoers = shipmentDeclarationVetoers
				.map(ImmutableSet::copyOf)
				.orElseGet(ImmutableSet::of);
		logger.info("{}", shipmentDeclarationVetoers);
	}

	public void createShipmentDeclarationsIfNeeded(final InOutId shipmentId)
	{
		final Collection<ShipmentDeclarationConfig> configs = shipmentDeclarationConfigRepo.getAll();
		if (configs.isEmpty())
		{
			return;
		}

		final IInOutDAO inoutsRepo = Services.get(IInOutDAO.class);
		final Set<InOutAndLineId> allShipmentLineIds = inoutsRepo.retrieveLinesForInOutId(shipmentId);

		for (final ShipmentDeclarationConfig config : configs)
		{
			final List<InOutAndLineId> eligibleShipmentLineIds = new ArrayList<>();
			for (InOutAndLineId shipmentLineId : allShipmentLineIds)
			{
				if (isShipmentLineEligibleForShipmentDeclaration(shipmentLineId, config))
				{
					eligibleShipmentLineIds.add(shipmentLineId);
				}
			}

			if (eligibleShipmentLineIds.isEmpty())
			{
				continue;
			}

			generateShipmentDeclarations(config, eligibleShipmentLineIds);
		}
	}

	private boolean isShipmentLineEligibleForShipmentDeclaration(final InOutAndLineId shipmentLineId, final ShipmentDeclarationConfig config)
	{
		for (ShipmentDeclarationVetoer vetoer : shipmentDeclarationVetoers)
		{
			if (ShipmentDeclarationVetoer.OnShipmentDeclarationConfig.I_VETO.equals(vetoer.foundShipmentLineForConfig(shipmentLineId, config)))
			{
				return true;
			}
		}

		return false;
	}

	private void generateShipmentDeclarations(
			@NonNull final ShipmentDeclarationConfig config,
			@NonNull final List<InOutAndLineId> shipmentLineIds)
	{
		final IDocumentBL documentBL = Services.get(IDocumentBL.class);

		final int documentLinesNumber = config.getDocumentLinesNumber();

		for (final List<InOutAndLineId> shipmentLineIdsPartition : Lists.partition(shipmentLineIds, documentLinesNumber))
		{

			final DocTypeId docTypeId = config.getDocTypeId();
			final String documentNo = reserveDocumentNo(docTypeId);

			final ShipmentDeclaration shipmentDeclaration = createShipmentDeclaration(docTypeId,
					ImmutableSet.copyOf(shipmentLineIdsPartition),
					IDocument.ACTION_Complete,
					documentNo);
			final I_M_Shipment_Declaration shipmentDeclarationRecord = shipmentDeclarationRepo.save(shipmentDeclaration);

			if (config.getDocTypeCorrectionId() != null)
			{
				final ShipmentDeclaration shipmentDeclarationCorrection = shipmentDeclaration.copyToNew(
						config.getDocTypeCorrectionId(),
						IDocument.ACTION_Void);
				shipmentDeclarationCorrection.setBaseShipmentDeclarationId(shipmentDeclaration.getId());
				shipmentDeclarationRepo.save(shipmentDeclarationCorrection);

				shipmentDeclaration.setCorrectionShipmentDeclarationId(shipmentDeclarationCorrection.getId());
				shipmentDeclarationRepo.save(shipmentDeclaration);
			}

			documentBL.processEx(shipmentDeclarationRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		}
	}

	private String reserveDocumentNo(@NonNull final DocTypeId docTypeId)
	{
		final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);

		final String documentNo = documentNoFactory.forDocType(docTypeId.getRepoId(), /* useDefiniteSequence */false)
				.setClientId(Env.getClientId())
				.setFailOnError(true)
				.build();

		if (documentNo == null || documentNo == IDocumentNoBuilder.NO_DOCUMENTNO)
		{
			throw new AdempiereException("Cannot fetch documentNo for " + docTypeId);
		}

		return documentNo;
	}

	private ShipmentDeclaration createShipmentDeclaration(
			@NonNull final DocTypeId docTypeId,
			@NonNull final Set<InOutAndLineId> shipmentAndLineIds,
			@NonNull final String docAction,
			@NonNull final String documentNo)
	{
		Check.assumeNotEmpty(shipmentAndLineIds, "shipmentAndLineIds is not empty");

		final InOutId shipmentId = CollectionUtils.extractSingleElement(shipmentAndLineIds, InOutAndLineId::getInOutId);
		final I_M_InOut shipment = Services.get(IInOutDAO.class).getById(shipmentId);

		final ImmutableList<ShipmentDeclarationLine> shipmentDeclarationLines = shipmentAndLineIds
				.stream()
				.map(shipmentAndLineId -> createShipmentDeclarationLine(shipmentAndLineId))
				.collect(ImmutableList.toImmutableList());

		final ShipmentDeclaration shipmentDeclaration = ShipmentDeclaration.builder()
				.docTypeId(docTypeId)
				.documentNo(documentNo)
				//
				.bpartnerAndLocationId(BPartnerLocationId.ofRepoId(shipment.getC_BPartner_ID(), shipment.getC_BPartner_Location_ID()))
				.userId(UserId.ofRepoIdOrNull(shipment.getAD_User_ID()))
				//
				.shipmentDate(TimeUtil.asLocalDate(shipment.getMovementDate()))
				.orgId(OrgId.ofRepoId(shipment.getAD_Org_ID()))
				.shipmentId(shipmentId)
				//
				.docStatus(IDocument.STATUS_Drafted)
				.docAction(docAction)
				//
				.lines(shipmentDeclarationLines)
				//
				.build();

		shipmentDeclaration.updateLineNos();

		return shipmentDeclaration;
	}

	private ShipmentDeclarationLine createShipmentDeclarationLine(final InOutAndLineId shipmentAndLineId)
	{
		final InOutLineId shipmentLineId = shipmentAndLineId.getInOutLineId();

		final I_M_InOutLine shipmentLineRecord = Services.get(IInOutDAO.class).getLineByIdInTrx(shipmentLineId);

		final ProductId productId = ProductId.ofRepoId(shipmentLineRecord.getM_Product_ID());
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);

		final I_C_UOM uom = Services.get(IUOMDAO.class).getById(shipmentLineRecord.getC_UOM_ID());

		return ShipmentDeclarationLine.builder()
				.orgId(OrgId.ofRepoId(shipmentLineRecord.getAD_Org_ID()))
				.packageSize(product.getPackageSize())
				.productId(productId)
				.quantity(Quantity.of(shipmentLineRecord.getMovementQty(), uom))
				.shipmentLineId(shipmentLineId)
				.build();
	}

}
