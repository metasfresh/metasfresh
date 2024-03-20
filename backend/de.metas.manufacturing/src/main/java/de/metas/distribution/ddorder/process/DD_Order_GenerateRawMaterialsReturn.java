package de.metas.distribution.ddorder.process;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.bpartner.BPartnerLocationId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.storage.IStorageEngine;
import de.metas.storage.IStorageEngineService;
import de.metas.storage.IStorageQuery;
import de.metas.storage.IStorageRecord;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.ASICopy;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.PlainAttributeSetInstanceAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TrxRunnable2;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @implSpec Task <a href="http://dewiki908/mediawiki/index.php/08118_Wie_geht_das_zur%C3%BCck%2C_was_noch_bei_der_Linie_steht_%28Prozess%29_%28107566315908%29">...</a>
 */
public class DD_Order_GenerateRawMaterialsReturn extends JavaProcess
{
	// Services
	private final transient IStorageEngineService storageEngineService = Services.get(IStorageEngineService.class);
	private final transient IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final transient DDOrderLowLevelService ddOrderLowLevelService = SpringContextHolder.instance.getBean(DDOrderLowLevelService.class);

	//
	// Parameters
	private int p_M_Warehouse_ID = -1;
	private boolean p_IsTest = false;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String parameterName = para.getParameterName();
			if (I_M_Warehouse.COLUMNNAME_M_Warehouse_ID.equals(parameterName))
			{
				p_M_Warehouse_ID = para.getParameterAsInt();
			}
			else if ("IsTest".equals(parameterName))
			{
				p_IsTest = para.getParameterAsBoolean();
			}
		}

		if (p_M_Warehouse_ID <= 0 && I_M_Warehouse.Table_Name.equals(getTableName()))
		{
			p_M_Warehouse_ID = getRecord_ID();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final WarehouseId warehouseId = getWarehouseId();

		final IStorageEngine storageEngine = storageEngineService.getStorageEngine();
		final IStorageQuery storageQuery = storageEngine.newStorageQuery();
		storageQuery.addWarehouseId(warehouseId);
		final Map<ArrayKey, RawMaterialsReturnDDOrderLineCandidate> key2candidate = new HashMap<>();

		//
		// Retrieve all storages and aggregate them to "Raw Materials to return" candidates
		final Collection<IStorageRecord> storageRecords = storageEngine.retrieveStorageRecords(this, storageQuery);
		for (final IStorageRecord storageRecord : storageRecords)
		{
			//
			// Get/Create candidate
			final ArrayKey key = mkDDOrderLineCandidateKey(storageRecord);
			RawMaterialsReturnDDOrderLineCandidate candidate = key2candidate.get(key);
			if (candidate == null)
			{
				final I_M_AttributeSetInstance attributeSetInstance = attributeSetInstanceBL.createASIFromAttributeSet(storageRecord.getAttributes());
				final PlainAttributeSetInstanceAware attributeSetIinstanceAware = PlainAttributeSetInstanceAware.forProductIdAndAttributeSetInstanceId(
						storageRecord.getProductId(),
						attributeSetInstance.getM_AttributeSetInstance_ID());

				candidate = new RawMaterialsReturnDDOrderLineCandidate(
						attributeSetIinstanceAware,
						storageRecord.getLocator());
				key2candidate.put(key, candidate);
			}

			//
			// Aggregate current storage record to our candidate
			candidate.addStorageRecord(storageRecord);
		}

		//
		// Iterate the candidates and create a DD_Order for each of them
		for (final RawMaterialsReturnDDOrderLineCandidate candidate : key2candidate.values())
		{
			// Skip zero Qty candidates (shall not happen)
			if (candidate.getQty().signum() == 0)
			{
				addLog("@NotValid@ " + candidate.getSummary() + ": @Qty@=0");
				continue;
			}

			// Skip invalid candidates
			if (!candidate.isValid())
			{
				addLog("@NotValid@ " + candidate.getSummary() + ": " + candidate.getNotValidReasons());
				continue;
			}

			//
			// Create DD_Order for candidate
			trxManager.run(getTrxName(), new TrxRunnable2()
			{

				@Override
				public void run(final String localTrxName)
				{
					final I_DD_Order ddOrder = createAndComplete(candidate);
					addLog("@Created@ " + candidate.getSummary() + ": @DD_Order_ID@=" + ddOrder.getDocumentNo());
				}

				@Override
				public boolean doCatch(final Throwable ex)
				{
					addLog("@NotValid@ " + candidate.getSummary() + ": " + ex.getLocalizedMessage());
					return true; // rollback
				}

				@Override
				public void doFinally()
				{
					// nothing
				}
			});
		}

		// Force a ROLLBACK if we are running in testing mode
		if (p_IsTest)
		{
			throw new AdempiereException("@IsTest@=@Y@");
		}

		return MSG_OK;
	}

	private WarehouseId getWarehouseId()
	{
		return WarehouseId.ofRepoId(p_M_Warehouse_ID);
	}

	private ArrayKey mkDDOrderLineCandidateKey(final IStorageRecord storageRecord)
	{
		final ProductId productId = storageRecord.getProductId();
		final I_M_Locator locator = storageRecord.getLocator();
		return Util.mkKey(productId.getRepoId(), locator.getM_Locator_ID());
	}

	private I_DD_Order createAndComplete(final RawMaterialsReturnDDOrderLineCandidate candidate)
	{
		final I_DD_Order ddOrder = createDD_Order(candidate);
		createDD_OrderLine(ddOrder, candidate);

		docActionBL.processEx(ddOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		return ddOrder;
	}

	private I_DD_Order createDD_Order(final RawMaterialsReturnDDOrderLineCandidate candidate)
	{
		Check.assume(candidate.isValid(), "candidate is valid: {}", candidate);

		final IContextAware context = PlainContextAware.newWithThreadInheritedTrx(getCtx());
		final Timestamp dateOrdered = candidate.getDateOrdered();
		final int shipperId = candidate.getDD_NetworkDistributionLine().getM_Shipper_ID();
		final OrgId orgId = candidate.getOrgId();
		final BPartnerLocationId orgBPLocationId = candidate.getOrgBPLocationId();
		final UserId salesRepId = candidate.getPlannerId();
		final WarehouseId warehouseInTrasitId = candidate.getInTransitWarehouseId();
		final ResourceId rawMaterialsPlantId = candidate.getRawMaterialsPlantId();

		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class, context);
		ddOrder.setAD_Org_ID(orgId.getRepoId());
		ddOrder.setPP_Plant_ID(ResourceId.toRepoId(rawMaterialsPlantId));
		ddOrder.setC_BPartner_ID(orgBPLocationId != null ? orgBPLocationId.getBpartnerId().getRepoId() : -1);
		ddOrder.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(orgBPLocationId));
		ddOrder.setSalesRep_ID(UserId.toRepoId(salesRepId));

		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(DocBaseType.DistributionOrder)
				.adClientId(ddOrder.getAD_Client_ID())
				.adOrgId(ddOrder.getAD_Org_ID())
				.build();
		final int docTypeId = docTypeDAO.getDocTypeId(query).getRepoId();
		ddOrder.setC_DocType_ID(docTypeId);
		ddOrder.setM_Warehouse_ID(warehouseInTrasitId.getRepoId());
		ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		ddOrder.setDocAction(X_DD_Order.DOCACTION_Complete);
		ddOrder.setDateOrdered(dateOrdered);
		ddOrder.setDatePromised(dateOrdered);
		ddOrder.setM_Shipper_ID(shipperId);
		ddOrder.setIsInDispute(false);
		ddOrder.setIsInTransit(false);

		ddOrderLowLevelService.save(ddOrder);
		return ddOrder;
	}

	private void createDD_OrderLine(final I_DD_Order ddOrder, final RawMaterialsReturnDDOrderLineCandidate candidate)
	{
		Check.assume(candidate.isValid(), "candidate is valid: {}", candidate);

		final IAttributeSetInstanceAware attributeSetInstanceAware = candidate.getM_Product();
		final Quantity qtyToMove = Quantity.of(candidate.getQty(), candidate.getC_UOM());
		final I_DD_NetworkDistributionLine networkLine = candidate.getDD_NetworkDistributionLine();
		final I_M_Locator locatorFrom = candidate.getM_Locator();
		final I_M_Locator locatorTo = candidate.getRawMaterialsLocator();

		//
		// Create DD Order Line
		final I_DD_OrderLine ddOrderline = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class, ddOrder);
		ddOrderline.setAD_Org_ID(ddOrder.getAD_Org_ID());
		ddOrderline.setDD_Order(ddOrder);
		// ddOrderline.setC_BPartner_ID(bpartnerId);

		//
		// Locator From/To
		ddOrderline.setM_Locator_ID(locatorFrom.getM_Locator_ID());
		ddOrderline.setM_LocatorTo_ID(locatorTo.getM_Locator_ID());

		//
		// Product, UOM, Qty
		ddOrderline.setM_Product_ID(attributeSetInstanceAware.getM_Product_ID());

		final ASICopy asiCopy = ASICopy.newInstance(attributeSetInstanceAware.getM_AttributeSetInstance());
		ddOrderline.setM_AttributeSetInstance(asiCopy.copy());
		ddOrderline.setM_AttributeSetInstanceTo(asiCopy.copy());

		ddOrderline.setC_UOM_ID(qtyToMove.getUomId().getRepoId());
		ddOrderline.setQtyEntered(qtyToMove.toBigDecimal());
		ddOrderline.setQtyOrdered(qtyToMove.toBigDecimal());
		ddOrderline.setTargetQty(qtyToMove.toBigDecimal());

		//
		// Dates
		ddOrderline.setDateOrdered(ddOrder.getDateOrdered());
		ddOrderline.setDatePromised(ddOrder.getDatePromised());

		//
		// Other flags
		ddOrderline.setIsInvoiced(false);
		ddOrderline.setDD_AllowPush(networkLine.isDD_AllowPush());
		ddOrderline.setIsKeepTargetPlant(networkLine.isKeepTargetPlant());

		ddOrderLowLevelService.save(ddOrderline);
	}
}
