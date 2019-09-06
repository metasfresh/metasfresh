package de.metas.materialtracking.qualityBasedInvoicing.impl;

/*
 * #%L
 * de.metas.materialtracking
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterial;
import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterialQuery;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedInvoicingDAO;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedSpiProviderService;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.ProductionMaterialType;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IInvoicedSumProvider;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityBasedConfig;
import de.metas.util.Check;
import de.metas.util.Services;

/* package */class QualityInspectionOrder implements IQualityInspectionOrder
{
	public static final QualityInspectionOrder cast(IQualityInspectionOrder qualityInspectionOrder)
	{
		return (QualityInspectionOrder)qualityInspectionOrder;
	}

	// Services
	private final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);
	private final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
	private final IQualityBasedInvoicingDAO qualityBasedInvoicingDAO = Services.get(IQualityBasedInvoicingDAO.class);
	private final IQualityBasedSpiProviderService qualityBasedConfigProviderService = Services.get(IQualityBasedSpiProviderService.class);

	// Parameters
	private final I_PP_Order _ppOrder;
	private final boolean _qualityInspection;
	private I_M_Material_Tracking _materialTracking = null;

	// Loaded values
	private IQualityBasedConfig _qualityBasedConfig = null;
	private Integer _inspectionNumber = null;
	private List<IProductionMaterial> _productionMaterials;
	/** Raw material */
	private IProductionMaterial _productionMaterial_Raw;
	/** Main produced material (i.e. the one from PP_Order header) */
	private IProductionMaterial _productionMaterial_Main;
	/** Scrap */
	private IProductionMaterial _productionMaterial_Scrap;
	/** amount that was already invoiced in recent waschproben-invoices */
	private BigDecimal _alreadyInvoicedSum = null;

	private List<IQualityInspectionOrder> allOrders;

	/**
	 * Constructor.
	 *
	 * NOTE: please don't call it directly, it will be called by API
	 *
	 * @param ppOrder
	 * @param materialTracking
	 */
	public QualityInspectionOrder(final org.eevolution.model.I_PP_Order ppOrder, final I_M_Material_Tracking materialTracking)
	{
		//
		// PP_Order
		Check.assumeNotNull(ppOrder, "ppOrder not null");
		_ppOrder = InterfaceWrapperHelper.create(ppOrder, I_PP_Order.class);
		_qualityInspection = materialTrackingPPOrderBL.isQualityInspection(ppOrder);

		//
		// Material tracking
		// NOTE: in case is null, it will be lazy retrieved on demand
		_materialTracking = materialTracking;
	}

	@Override
	public I_M_Material_Tracking getM_Material_Tracking()
	{
		if (_materialTracking == null)
		{
			final I_PP_Order ppOrder = getPP_Order();
			_materialTracking = materialTrackingDAO.retrieveSingleMaterialTrackingForModel(ppOrder);
			Check.assumeNotNull(_materialTracking, "material tracking exists for {}", ppOrder);
		}

		return _materialTracking;
	}

	@Override
	public final boolean isQualityInspection()
	{
		return _qualityInspection;
	}

	private final void assumeQualityInspection()
	{
		Check.assume(isQualityInspection(), "inspection number is available only for quality inspection orders");
	}

	@Override
	public IQualityBasedConfig getQualityBasedConfig()
	{
		if (_qualityBasedConfig == null)
		{
			final I_M_Material_Tracking materialTracking = getM_Material_Tracking();
			_qualityBasedConfig = qualityBasedConfigProviderService
					.getQualityBasedConfigProvider()
					.provideConfigFor(materialTracking);
			Check.assumeNotNull(_qualityBasedConfig, "_qualityBasedConfig not null");
		}
		return _qualityBasedConfig;
	}

	@Override
	public int getInspectionNumber()
	{
		if (_inspectionNumber == null)
		{
			assumeQualityInspection();

			final I_PP_Order ppOrder = getPP_Order();
			_inspectionNumber = materialTrackingDAO.retrieveNumberOfInspection(ppOrder);
		}
		return _inspectionNumber;
	}

	/**
	 * Called by API to set the Inspection Number (just in case this is a quality inspection order).
	 *
	 * @param inspectionNumber
	 */
	protected void setInspectionNumber(final int inspectionNumber)
	{
		assumeQualityInspection();

		Check.assume(inspectionNumber >= 1, "inspectionNumber >= 1");
		_inspectionNumber = inspectionNumber;
	}

	@Override
	public final I_PP_Order getPP_Order()
	{
		return _ppOrder;
	}

	@Override
	public List<IProductionMaterial> getProductionMaterials()
	{
		if (_productionMaterials == null)
		{
			final IProductionMaterialQuery query = qualityBasedInvoicingDAO.createInitialQuery()
					.setPP_Order(getPP_Order())
					.setTypes(ProductionMaterialType.RAW, ProductionMaterialType.PRODUCED);
			final List<IProductionMaterial> productionMaterials = qualityBasedInvoicingDAO.retriveProductionMaterials(query);
			_productionMaterials = Collections.unmodifiableList(productionMaterials);
		}
		return _productionMaterials;
	}

	@Override
	public IProductionMaterial getRawProductionMaterial()
	{
		// If we already found the raw material line, just return it
		if (_productionMaterial_Raw != null)
		{
			return _productionMaterial_Raw;
		}

		final List<IProductionMaterial> rawProductionMaterials = new ArrayList<IProductionMaterial>();
		for (final IProductionMaterial productionMaterial : getProductionMaterials())
		{
			if (productionMaterial.getType() != ProductionMaterialType.RAW)
			{
				continue;
			}
			if (productionMaterial.getM_Product().getM_Product_ID() != _materialTracking.getM_Product_ID())
			{
				continue; // beware of unrelated products that could have been issued together with "our" raw materials
			}

			rawProductionMaterials.add(productionMaterial);
		}

		if (rawProductionMaterials.isEmpty())
		{
			throw new AdempiereException("Raw production material lines shall exists"); // TRL
		}

		// If we got more then one raw production material lines, then try to delete all the ZERO lines
		// and see if we get only one which is not zero
		// (per Tobi advice/request)
		if (rawProductionMaterials.size() > 1)
		{
			for (final Iterator<IProductionMaterial> it = rawProductionMaterials.iterator(); it.hasNext();)
			{
				final IProductionMaterial rawProductionMaterial = it.next();
				if (rawProductionMaterial.getQty().signum() == 0 && rawProductionMaterials.size() > 1)
				{
					it.remove();
				}
			}
		}
		Check.assumeNotEmpty(rawProductionMaterials, "rawProductionMaterials shall not be empty"); // shall never ever happen

		if (rawProductionMaterials.size() > 1)
		{
			throw new AdempiereException("More than one raw production material lines where found")
					.appendParametersToMessage()
					.setParameter("inspectionNumber", _inspectionNumber)
					.setParameter("rawProductionMaterials", rawProductionMaterials)
					.setParameter("ppOrderRecord", _ppOrder);
		}

		_productionMaterial_Raw = rawProductionMaterials.get(0);
		return _productionMaterial_Raw;
	}

	@Override
	public IProductionMaterial getMainProductionMaterial()
	{
		if (_productionMaterial_Main != null)
		{
			return _productionMaterial_Main;
		}

		_productionMaterial_Main = null;
		for (final IProductionMaterial productionMaterial : getProductionMaterials())
		{
			if (ProductionMaterialType.PRODUCED != productionMaterial.getType())
			{
				continue;
			}
			if (productionMaterial.getComponentType() != null)
			{
				// skip if is not the main product (i.e. on main products where is no component type)
				continue;
			}

			Check.assumeNull(_productionMaterial_Main, "only one main produced material shall exist");
			_productionMaterial_Main = productionMaterial;
		}

		Check.assumeNotNull(_productionMaterial_Main, "main produced material shall exist");
		return _productionMaterial_Main;
	}

	@Override
	public IProductionMaterial getScrapProductionMaterial()
	{
		//
		// If the scrap production material was already calculated, return it
		if (_productionMaterial_Scrap != null)
		{
			return _productionMaterial_Scrap;
		}

		//
		// Get the Scrap Configuration
		final IQualityBasedConfig qualityBasedConfig = getQualityBasedConfig();
		final I_M_Product scrapProduct = qualityBasedConfig.getScrapProduct();
		final I_C_UOM scrapUOM = qualityBasedConfig.getScrapUOM();

		//
		// Get Qty/UOM of Raw materials
		final IProductionMaterial rawMaterial = getRawProductionMaterial();
		final BigDecimal rawQtyInScrapUOM = rawMaterial.getQty(scrapUOM);
		final BigDecimal rawQtyInScrapUOMAvg = rawMaterial.getQM_QtyDeliveredAvg(scrapUOM);

		//
		// Get Qty/UOM of Produced materials
		BigDecimal producedQtyInScrapUOMSum = BigDecimal.ZERO;
		BigDecimal producedQtyInScrapUOMSumAvg = BigDecimal.ZERO;
		for (final IProductionMaterial producedMaterial : getProductionMaterials(ProductionMaterialType.PRODUCED))
		{
			final BigDecimal producedQtyInScrapUOM = producedMaterial.getQty(scrapUOM);
			producedQtyInScrapUOMSum = producedQtyInScrapUOMSum.add(producedQtyInScrapUOM);

			final BigDecimal producedQtyInScrapUOMAvg = producedMaterial.getQM_QtyDeliveredAvg(scrapUOM);
			producedQtyInScrapUOMSumAvg = producedQtyInScrapUOMSumAvg.add(producedQtyInScrapUOMAvg);
		}

		//
		// Create the Scrap IProductionMaterial line
		// FIXME: in case "producedQtyInScrapUOMSum" is ZERO and order is not closed yet => scrapQty=0
		final BigDecimal scrapQty = rawQtyInScrapUOM.subtract(producedQtyInScrapUOMSum);
		final BigDecimal scrapQtyAvg = rawQtyInScrapUOMAvg.subtract(producedQtyInScrapUOMSumAvg);

		_productionMaterial_Scrap = new PlainProductionMaterial(ProductionMaterialType.SCRAP, scrapProduct, scrapQty, scrapUOM);
		_productionMaterial_Scrap.setQM_QtyDeliveredAvg(scrapQtyAvg);

		return _productionMaterial_Scrap;
	}

	@Override
	public IProductionMaterial getProductionMaterial(final I_M_Product product)
	{
		Check.assumeNotNull(product, "product not null");
		final int productId = product.getM_Product_ID();

		IProductionMaterial productionMaterialFound = null;
		for (final IProductionMaterial productionMaterial : getProductionMaterials())
		{
			final I_M_Product productionMaterialProduct = productionMaterial.getM_Product();
			final int productionMaterialProductId = productionMaterialProduct.getM_Product_ID();
			if (productionMaterialProductId != productId)
			{
				continue;
			}

			Check.assumeNull(productionMaterialFound, "Only one production material shall be for product {}", product);
			productionMaterialFound = productionMaterial;
		}

		return productionMaterialFound;
	}

	@Override
	public List<IProductionMaterial> getProductionMaterials(final ProductionMaterialType type)
	{
		final I_M_Product product = null;
		return getProductionMaterials(type, product);
	}

	@Override
	public IProductionMaterial getProductionMaterial(final ProductionMaterialType type, final I_M_Product product)
	{
		final List<IProductionMaterial> productionMaterials = getProductionMaterials(type, product);
		if (productionMaterials == null || productionMaterials.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @ProductionMaterial@"
					+ "\n@Type@: " + type
					+ "\n@M_Product_ID@: " + product);
		}
		else if (productionMaterials.size() == 1)
		{
			return productionMaterials.get(0);
		}
		else
		{
			throw new AdempiereException("More then one production material found"
					+ "\n@Type@: " + type
					+ "\n@M_Product_ID@: " + product);
		}
	}

	private List<IProductionMaterial> getProductionMaterials(final ProductionMaterialType type, final I_M_Product product)
	{
		Check.assumeNotNull(type, "type not null");
		final int productId = product == null ? -1 : product.getM_Product_ID();

		final List<IProductionMaterial> result = new ArrayList<IProductionMaterial>();
		for (final IProductionMaterial productionMaterial : getProductionMaterials())
		{
			if (productionMaterial.getType() != type)
			{
				continue;
			}

			if (productId > 0)
			{
				final I_M_Product productionMaterialProduct = productionMaterial.getM_Product();
				final int productionMaterialProductId = productionMaterialProduct.getM_Product_ID();
				if (productionMaterialProductId != productId)
				{
					continue;
				}
			}

			result.add(productionMaterial);
		}

		return result;
	}

	protected void setAllOrders(final List<IQualityInspectionOrder> allOrders)
	{
		assumeQualityInspection();

		Check.assumeNotNull(allOrders, "allOrders not null");
		this.allOrders = Collections.unmodifiableList(new ArrayList<>(allOrders));
	}

	@Override
	public List<IQualityInspectionOrder> getAllOrders()
	{
		return allOrders;
	}

	@Override
	public BigDecimal getAlreadyInvoicedNetSum()
	{
		if (_alreadyInvoicedSum == null)
		{
			final IInvoicedSumProvider invoicedSumProvider = qualityBasedConfigProviderService.getInvoicedSumProvider();
			_alreadyInvoicedSum = invoicedSumProvider.getAlreadyInvoicedNetSum(_materialTracking);
		}
		if (_alreadyInvoicedSum == null)
		{
			_alreadyInvoicedSum = BigDecimal.ZERO;
		}

		return _alreadyInvoicedSum;
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("QualityInspectionOrder [_ppOrder=");
		builder.append(_ppOrder);
		builder.append(", _qualityInspection=");
		builder.append(_qualityInspection);
		builder.append(", _materialTracking=");
		builder.append(_materialTracking);
		builder.append(", _qualityBasedConfig=");
		builder.append(_qualityBasedConfig);
		builder.append(", _inspectionNumber=");
		builder.append(_inspectionNumber);
		builder.append(", _productionMaterials=");
		builder.append(_productionMaterials);
		builder.append(", _productionMaterial_Raw=");
		builder.append(_productionMaterial_Raw);
		builder.append(", _productionMaterial_Main=");
		builder.append(_productionMaterial_Main);
		builder.append(", _productionMaterial_Scrap=");
		builder.append(_productionMaterial_Scrap);
		builder.append(", _alreadyInvoicedSum=");
		builder.append(_alreadyInvoicedSum);
		// builder.append(", preceedingOrders=");
		// builder.append(preceedingOrders);
		builder.append("]");
		return builder.toString();
	}
}
