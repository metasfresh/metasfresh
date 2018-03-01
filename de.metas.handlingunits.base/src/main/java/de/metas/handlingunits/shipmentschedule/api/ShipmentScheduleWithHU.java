package de.metas.handlingunits.shipmentschedule.api;

/*
 * #%L
 * de.metas.handlingunits.base
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
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;

import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.IContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.Null;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;

import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUPackageBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.logging.LogManager;
import lombok.NonNull;

public class ShipmentScheduleWithHU
{
	public static final ShipmentScheduleWithHU ofShipmentScheduleQtyPicked(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext();
		return ofShipmentScheduleQtyPickedWithHuContext(shipmentScheduleQtyPicked, huContext);
	}

	public static final ShipmentScheduleWithHU ofShipmentScheduleQtyPickedWithHuContext(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked,
			@NonNull final IHUContext huContext)
	{
		return new ShipmentScheduleWithHU(huContext, shipmentScheduleQtyPicked);
	}

	/**
	 * Create an "empty" instance with no HUs inside. Used if a shipment without HU allocation has to be created.
	 */
	public static final ShipmentScheduleWithHU ofShipmentScheduleWithoutHu(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final BigDecimal qtyPicked)
	{
		return new ShipmentScheduleWithHU(null, shipmentSchedule, qtyPicked);
	}

	private static final Logger logger = LogManager.getLogger(ShipmentScheduleWithHU.class);

	private final IHUContext huContext;
	private final I_M_ShipmentSchedule shipmentSchedule;
	private final BigDecimal qtyPicked;

	private final I_M_HU vhu;
	private final I_M_HU tuHU;
	private final I_M_HU luHU;
	private Object _attributesAggregationKey; // lazy
	private I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked;

	private I_M_InOutLine shipmentLine = null;

	private ShipmentScheduleWithHU(
			final IHUContext huContext,
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleAlloc)
	{
		this.huContext = huContext;
		this.shipmentScheduleQtyPicked = shipmentScheduleAlloc;

		this.shipmentSchedule = create(shipmentScheduleAlloc.getM_ShipmentSchedule(), I_M_ShipmentSchedule.class);
		this.qtyPicked = shipmentScheduleAlloc.getQtyPicked();

		this.vhu = shipmentScheduleAlloc.getVHU_ID() > 0 ? shipmentScheduleAlloc.getVHU() : null;
		this.tuHU = shipmentScheduleAlloc.getM_TU_HU_ID() > 0 ? shipmentScheduleAlloc.getM_TU_HU() : null;
		this.luHU = shipmentScheduleAlloc.getM_LU_HU_ID() > 0 ? shipmentScheduleAlloc.getM_LU_HU() : null;
	}

	/**
	 * Creates a HU-"empty" instance that just references the given shipment schedule.
	 *
	 * @param huContext
	 * @param shipmentSchedule
	 * @param qtyPicked
	 */
	private ShipmentScheduleWithHU(
			final IHUContext huContext,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final BigDecimal qtyPicked)
	{
		this.huContext = huContext;

		this.shipmentScheduleQtyPicked = null; // no allocation, will be created on fly when needed

		this.shipmentSchedule = shipmentSchedule;
		this.qtyPicked = qtyPicked;

		vhu = null; // no VHU
		tuHU = null; // no TU
		luHU = null; // no LU
	}

	@Override
	public String toString()
	{
		return "ShipmentScheduleWithHU ["
				+ "\n    shipmentSchedule=" + shipmentSchedule
				+ "\n    qtyPicked=" + qtyPicked
				+ "\n    vhu=" + vhu
				+ "\n    tuHU=" + tuHU
				+ "\n    luHU=" + luHU
				+ "\n    attributesAggregationKey=" + (_attributesAggregationKey == null ? "<NOT BUILT>" : _attributesAggregationKey)
				+ "\n    shipmentScheduleAlloc=" + shipmentScheduleQtyPicked
				+ "\n    shipmentLine=" + shipmentLine
				+ "\n]";
	}

	public IHUContext getHUContext()
	{
		return huContext;
	}

	public int getM_Product_ID()
	{
		return shipmentSchedule.getM_Product_ID();
	}

	public I_M_Product getM_Product()
	{
		return shipmentSchedule.getM_Product();
	}

	public int getM_AttributeSetInstance_ID()
	{
		final int asiId = shipmentSchedule.getM_AttributeSetInstance_ID();
		return asiId <= 0 ? AttributeConstants.M_AttributeSetInstance_ID_None : asiId;
	}

	public Object getAttributesAggregationKey()
	{
		if (_attributesAggregationKey == null)
		{
			_attributesAggregationKey = createAttributesAggregationKey();
			logger.trace("AttributesAggregationKey created: {}", _attributesAggregationKey);
		}
		return _attributesAggregationKey;
	}

	private Object createAttributesAggregationKey()
	{
		logger.trace("Creating AttributesAggregationKey");

		final I_M_HU hu = getTopLevelHU();
		if (hu == null)
		{
			return ImmutableMap.of("M_AttributeSetInstance_ID", getM_AttributeSetInstance_ID());
		}

		final ShipmentScheduleHandler handler = Services.get(IShipmentScheduleHandlerBL.class).getHandlerFor(shipmentSchedule);

		final ImmutableMap.Builder<String, Object> keyBuilder = ImmutableMap.builder();
		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		final IAttributeStorage attributeStorage = attributeStorageFactory.getAttributeStorage(hu);
		for (final IAttributeValue attributeValue : attributeStorage.getAttributeValues())
		{
			// Only consider attributes which are usable in ASI
			if (!attributeValue.isUseInASI())
			{
				logger.trace("Skip attribute because UseInASI=false; attributeValue={}", attributeValue);
				continue;
			}

			if (!handler.attributeShallBePartOfShipmentLine(shipmentSchedule, attributeValue.getM_Attribute()))
			{
				logger.trace("Skip attribute according to the handler's attribute config; attributeValue={}; handler={}; shipmentSchedule={}",
						attributeValue, handler, shipmentLine);
				continue;
			}

			final String name = attributeValue.getM_Attribute().getValue();
			final Object value = attributeValue.getValue();
			keyBuilder.put(name, Null.box(value));
			logger.trace("Considered attribute name={}, value={}", name, value);
		}

		return keyBuilder.build();
	}

	/**
	 * Might return a value less or equal to zero!
	 */
	public int getC_OrderLine_ID()
	{
		return shipmentSchedule.getC_OrderLine_ID();
	}

	public I_M_ShipmentSchedule getM_ShipmentSchedule()
	{
		return shipmentSchedule;
	}

	public BigDecimal getQtyPicked()
	{
		return qtyPicked;
	}

	public I_C_UOM getQtyPickedUOM()
	{
		return Services.get(IShipmentScheduleBL.class).getUomOfProduct(shipmentSchedule);
	}

	public I_M_HU getVHU()
	{
		return vhu;
	}

	public I_M_HU getM_TU_HU()
	{
		return tuHU;
	}

	public I_M_HU getM_LU_HU()
	{
		return luHU;
	}

	/**
	 * Gets the underlying LU or TU or VHU, depends on which is the first not null
	 *
	 * @return LU/TU/VHU
	 */
	private I_M_HU getTopLevelHU()
	{
		return Util.coalesce(luHU, tuHU, vhu);
	}

	/**
	 * Called by API when a shipment line that includes this candidate was just created.
	 */
	public ShipmentScheduleWithHU setM_InOutLine(final I_M_InOutLine shipmentLine)
	{
		logger.trace("Setting shipmentLine={} to {}", shipmentLine, this);
		this.shipmentLine = shipmentLine;
		return this;
	}

	public I_M_InOutLine getM_InOutLine()
	{
		return shipmentLine;
	}

	/**
	 * Called by API when a shipment that includes this candidate was generated and processed.
	 *
	 * @return
	 */
	public ShipmentScheduleWithHU setM_InOut(@NonNull final I_M_InOut inout)
	{
		final IContextAware context = getContextAware(inout);

		//
		// Create/Update ShipmentSchedule to Shipment Line allocation
		createUpdateShipmentLineAlloc(inout);

		//
		// Update all M_Packages that point to our top level HU (i.e. LU/TU/VHU if any)
		// and set M_InOut to them.
		final I_M_HU topLevelHU = getTopLevelHU();
		if (topLevelHU != null)
		{
			final IHUPackageBL huPackageBL = Services.get(IHUPackageBL.class);
			huPackageBL.assignShipmentToPackages(topLevelHU, inout, context.getTrxName());
		}
		return this;
	}

	public I_M_ShipmentSchedule_QtyPicked getShipmentScheduleQtyPicked()
	{
		return shipmentScheduleQtyPicked;
	}

	private void createUpdateShipmentLineAlloc(@NonNull final I_M_InOut inout)
	{
		// At this point we assume setM_InOutLine() method was called by API
		Check.assumeNotNull(shipmentLine, "shipmentLine not null");

		// If there is no shipment schedule allocation, create one now
		// This happens if you called the factory method which is without shipmentScheduleAlloc parameter
		if (shipmentScheduleQtyPicked == null)
		{
			shipmentScheduleQtyPicked = newInstance(I_M_ShipmentSchedule_QtyPicked.class, inout);
			shipmentScheduleQtyPicked.setM_ShipmentSchedule(shipmentSchedule);
			shipmentScheduleQtyPicked.setQtyPicked(qtyPicked);
			// lu, tu and vhu are null, so no need to set them

			final IDocumentBL documentBL = Services.get(IDocumentBL.class);
			if (documentBL.isDocumentCompletedOrClosed(inout))
			{
				// take that bit we have from the iol. might be useful to have a least the number of TUs
				final de.metas.handlingunits.model.I_M_InOutLine iol = create(getM_InOutLine(), de.metas.handlingunits.model.I_M_InOutLine.class);
				final BigDecimal qtyEnteredTU = iol.getQtyEnteredTU();
				shipmentScheduleQtyPicked.setQtyTU(qtyEnteredTU);
				shipmentScheduleQtyPicked.setQtyLU(qtyEnteredTU.signum() > 0 ? ONE : ZERO);
			}
		}
		else
		{
			updateQtyTUAndQtyLU();
		}

		// Set shipment line link
		shipmentScheduleQtyPicked.setM_InOutLine(shipmentLine);

		// Save allocation
		save(shipmentScheduleQtyPicked);
	}

	public void updateQtyTUAndQtyLU()
	{
		if (shipmentScheduleQtyPicked == null)
		{
			return;
		}

		final BigDecimal qtyLU = luHU == null ? ZERO : ONE;
		shipmentScheduleQtyPicked.setQtyLU(qtyLU);

		final BigDecimal qtyTU = extractTuQty();
		shipmentScheduleQtyPicked.setQtyTU(qtyTU);
	}

	private BigDecimal extractTuQty()
	{
		final BigDecimal qtyTU;
		if (tuHU == null)
		{
			qtyTU = ZERO;
		}
		else if (Services.get(IHandlingUnitsBL.class).isAggregateHU(tuHU))
		{
			Services.get(IHandlingUnitsDAO.class).retrieveParentItem(tuHU);
			qtyTU = tuHU.getM_HU_Item_Parent().getQty();
		}
		else
		{
			qtyTU = ONE;
		}
		return qtyTU;
	}
}
