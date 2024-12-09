package de.metas.ui.web.material.cockpit;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.dimension.DimensionSpecGroup;
import de.metas.i18n.IMsgBL;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
<<<<<<< HEAD
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
=======
import de.metas.money.Money;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.resource.ResourceService;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowType;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout.Displayed;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.collections.ListUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
<<<<<<< HEAD
=======
import org.adempiere.warehouse.WarehouseId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
<<<<<<< HEAD
import org.compiere.model.I_S_Resource;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@ToString
@EqualsAndHashCode(of = "documentId")
public class MaterialCockpitRow implements IViewRow
{
	/**
	 * Please keep its prefix in sync with {@link MaterialCockpitViewFactory#SYSCFG_DisplayIncludedRows}.
	 */
	public static final String SYSCFG_PREFIX = "de.metas.ui.web.material.cockpit.field";

	public static MaterialCockpitRow cast(final IViewRow row)
	{
		return (MaterialCockpitRow)row;
	}

	private static final String SEPARATOR = "-";
	private static final Joiner DOCUMENT_ID_JOINER = Joiner.on(SEPARATOR).skipNulls();

	@Getter
	private final LocalDate date;

	@Getter
<<<<<<< HEAD
	private final int productId;

	public static final String FIELDNAME_QtyStockEstimateSeqNo = I_MD_Cockpit.COLUMNNAME_QtyStockEstimateSeqNo;
	@ViewColumn(fieldName = FIELDNAME_QtyStockEstimateSeqNo, //
			captionKey = FIELDNAME_QtyStockEstimateSeqNo, //
			widgetType = DocumentFieldWidgetType.Integer, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 5, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final Integer qtyStockEstimateSeqNo;
=======
	private final ProductId productId;

	public static final String FIELDNAME_QtyStockEstimateSeqNoAtDate = I_MD_Cockpit.COLUMNNAME_QtyStockEstimateSeqNo_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyStockEstimateSeqNoAtDate, //
			captionKey = FIELDNAME_QtyStockEstimateSeqNoAtDate, //
			widgetType = DocumentFieldWidgetType.Integer, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 5, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final Integer qtyStockEstimateSeqNoAtDate;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, //
			captionKey = I_MD_Cockpit.COLUMNNAME_ProductValue, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 10) })
	private final String productValue;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, //
			captionKey = I_MD_Cockpit.COLUMNNAME_ProductName, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 20) })
	private final String productName;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Text, //
			captionKey = I_M_Product.COLUMNNAME_M_Product_Category_ID, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 30, displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	@Getter
	@VisibleForTesting
	private final String productCategoryOrSubRowName;

	@Getter
	private final DimensionSpecGroup dimensionGroupOrNull;

	public static final String FIELDNAME_Manufacturer_ID = I_M_Product.COLUMNNAME_Manufacturer_ID;

	/**
	 * Use supplier in order to make this work with unit tests; getting the LookupValue uses LookupDAO.retrieveLookupDisplayInfo which goes directly to the DB.
	 */
	@ViewColumn(fieldName = FIELDNAME_Manufacturer_ID, //
			captionKey = FIELDNAME_Manufacturer_ID, //
			widgetType = DocumentFieldWidgetType.Lookup, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 40, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	private final Supplier<LookupValue> manufacturer;

	public static final String FIELDNAME_PackageSize = I_M_Product.COLUMNNAME_PackageSize;
	@ViewColumn(fieldName = FIELDNAME_PackageSize, //
			captionKey = FIELDNAME_PackageSize, //
			widgetType = DocumentFieldWidgetType.Text, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 50, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	private final String packageSize;

	public static final String FIELDNAME_C_UOM_ID = I_M_Product.COLUMNNAME_C_UOM_ID;

	/**
	 * Use supplier in order to make this work with unit tests; getting the LookupValue uses LookupDAO.retrieveLookupDisplayInfo which goes directly to the DB.
	 */
	@ViewColumn(fieldName = FIELDNAME_C_UOM_ID, //
			captionKey = FIELDNAME_C_UOM_ID, //
			widgetType = DocumentFieldWidgetType.Lookup, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 60, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
	private final Supplier<LookupValue> uom;

	// Zusage Lieferant
	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
<<<<<<< HEAD
			captionKey = I_MD_Cockpit.COLUMNNAME_PMM_QtyPromised_OnDate, //
=======
			captionKey = I_MD_Cockpit.COLUMNNAME_PMM_QtyPromised_OnDate_AtDate, //
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			layouts = {
					@ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 70, //
							displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX)
			})
<<<<<<< HEAD
	private final BigDecimal pmmQtyPromised;

	public static final String FIELDNAME_QtyDemand_SalesOrder = I_MD_Cockpit.COLUMNNAME_QtyDemand_SalesOrder;
	@ViewColumn(fieldName = FIELDNAME_QtyDemand_SalesOrder, //
			captionKey = FIELDNAME_QtyDemand_SalesOrder, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 80,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyDemandSalesOrder;

	public static final String FIELDNAME_QtyDemand_DD_Order = I_MD_Cockpit.COLUMNNAME_QtyDemand_DD_Order;
	@ViewColumn(fieldName = FIELDNAME_QtyDemand_DD_Order, //
			captionKey = FIELDNAME_QtyDemand_DD_Order, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 90,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyDemandDDOrder;

	public static final String FIELDNAME_QtyDemandSum = I_MD_Cockpit.COLUMNNAME_QtyDemandSum;
	@ViewColumn(fieldName = FIELDNAME_QtyDemandSum, //
			captionKey = FIELDNAME_QtyDemandSum, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 100,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyDemandSum;

	public static final String FIELDNAME_QtySupplyPPOrder = I_MD_Cockpit.COLUMNNAME_QtySupply_PP_Order;
	@ViewColumn(fieldName = FIELDNAME_QtySupplyPPOrder, //
			captionKey = FIELDNAME_QtySupplyPPOrder, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 110,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplyPPOrder;

	public static final String FIELDNAME_QtySupply_PurchaseOrder = I_MD_Cockpit.COLUMNNAME_QtySupply_PurchaseOrder;
	@ViewColumn(fieldName = FIELDNAME_QtySupply_PurchaseOrder, //
			captionKey = FIELDNAME_QtySupply_PurchaseOrder, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 120) })
	@Getter // note that we use the getter for testing
	private final BigDecimal qtySupplyPurchaseOrder;

	public static final String FIELDNAME_QtySupplyDDOrder = I_MD_Cockpit.COLUMNNAME_QtySupply_DD_Order;
	@ViewColumn(fieldName = FIELDNAME_QtySupplyDDOrder, //
			captionKey = FIELDNAME_QtySupplyDDOrder, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 130,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplyDDOrder;

	public static final String FIELDNAME_QtySupplySum = I_MD_Cockpit.COLUMNNAME_QtySupplySum;
	@ViewColumn(fieldName = FIELDNAME_QtySupplySum, //
			captionKey = FIELDNAME_QtySupplySum, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 140,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplySum;

	public static final String FIELDNAME_QtySupplyRequired = I_MD_Cockpit.COLUMNNAME_QtySupplyRequired;
	@ViewColumn(fieldName = FIELDNAME_QtySupplyRequired, //
			captionKey = FIELDNAME_QtySupplyRequired, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 150,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplyRequired;

	public static final String FIELDNAME_QtySupplyToSchedule = I_MD_Cockpit.COLUMNNAME_QtySupplyToSchedule;
	@ViewColumn(fieldName = FIELDNAME_QtySupplyToSchedule, //
			captionKey = FIELDNAME_QtySupplyToSchedule, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 160,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplyToSchedule;

	public static final String FIELDNAME_QtyMaterialentnahme = I_MD_Cockpit.COLUMNNAME_QtyMaterialentnahme;
	@ViewColumn(fieldName = FIELDNAME_QtyMaterialentnahme, //
			captionKey = FIELDNAME_QtyMaterialentnahme, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 170, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyMaterialentnahme;

	// MRP MEnge
	public static final String FIELDNAME_QtyDemand_PP_Order = I_MD_Cockpit.COLUMNNAME_QtyDemand_PP_Order;
	@ViewColumn(fieldName = FIELDNAME_QtyDemand_PP_Order, //
			captionKey = FIELDNAME_QtyDemand_PP_Order, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 180, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyDemandPPOrder;

	// Zaehlbestand
	public static final String FIELDNAME_QtyStockCurrent = I_MD_Cockpit.COLUMNNAME_QtyStockCurrent;
	@ViewColumn(fieldName = FIELDNAME_QtyStockCurrent, //
			captionKey = FIELDNAME_QtyStockCurrent, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 190, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyStockCurrent;

	public static final String FIELDNAME_QtyStockEstimateCount = I_MD_Cockpit.COLUMNNAME_QtyStockEstimateCount;
	@ViewColumn(fieldName = FIELDNAME_QtyStockEstimateCount, //
			captionKey = FIELDNAME_QtyStockEstimateCount, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 210, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyStockEstimateCount;

	public static final String FIELDNAME_QtyStockEstimateTime = I_MD_Cockpit.COLUMNNAME_QtyStockEstimateTime;
	@ViewColumn(fieldName = FIELDNAME_QtyStockEstimateTime, //
			captionKey = FIELDNAME_QtyStockEstimateTime, //
			widgetType = DocumentFieldWidgetType.Timestamp, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 220, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final Instant qtyStockEstimateTime;

	public static final String FIELDNAME_QtyInventoryCount = I_MD_Cockpit.COLUMNNAME_QtyInventoryCount;
	@ViewColumn(fieldName = FIELDNAME_QtyInventoryCount, //
			captionKey = FIELDNAME_QtyInventoryCount, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 230, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyInventoryCount;

	public static final String FIELDNAME_QtyInventoryTime = I_MD_Cockpit.COLUMNNAME_QtyInventoryTime;
	@ViewColumn(fieldName = FIELDNAME_QtyInventoryTime, //
			captionKey = FIELDNAME_QtyInventoryTime, //
			widgetType = DocumentFieldWidgetType.Timestamp, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 240, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final Instant qtyInventoryTime;

	// zusagbar Zaehlbestand
	public static final String FIELDNAME_QtyExpectedSurplus = I_MD_Cockpit.COLUMNNAME_QtyExpectedSurplus;
	@ViewColumn(fieldName = FIELDNAME_QtyExpectedSurplus, //
			captionKey = FIELDNAME_QtyExpectedSurplus, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 250, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyExpectedSurplus;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
			captionKey = I_MD_Stock.COLUMNNAME_QtyOnHand, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 260) })
	@Getter // note that we use the getter for testing
	private final BigDecimal qtyOnHandStock;

=======
	private final BigDecimal pmmQtyPromisedAtDate;

	public static final String FIELDNAME_QtyDemand_SalesOrder_AtDate = I_MD_Cockpit.COLUMNNAME_QtyDemand_SalesOrder_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyDemand_SalesOrder_AtDate, //
			captionKey = FIELDNAME_QtyDemand_SalesOrder_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 80,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyDemandSalesOrderAtDate;

	public static final String FIELDNAME_QtyDemand_SalesOrder = "QtyDemand_SalesOrder";
	@ViewColumn(fieldName = FIELDNAME_QtyDemand_SalesOrder, //
			captionKey = FIELDNAME_QtyDemand_SalesOrder, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 90,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyDemandSalesOrder;

	public static final String FIELDNAME_QtyDemand_DD_Order_AtDate = I_MD_Cockpit.COLUMNNAME_QtyDemand_DD_Order_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyDemand_DD_Order_AtDate, //
			captionKey = FIELDNAME_QtyDemand_DD_Order_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 100,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyDemandDDOrderAtDate;

	public static final String FIELDNAME_QtyDemandSum_AtDate = I_MD_Cockpit.COLUMNNAME_QtyDemandSum_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyDemandSum_AtDate, //
			captionKey = FIELDNAME_QtyDemandSum_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 110,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyDemandSumAtDate;

	public static final String FIELDNAME_QtySupplyPPOrder_AtDate = I_MD_Cockpit.COLUMNNAME_QtySupply_PP_Order_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtySupplyPPOrder_AtDate, //
			captionKey = FIELDNAME_QtySupplyPPOrder_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 120,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplyPPOrderAtDate;

	public static final String FIELDNAME_QtySupply_PurchaseOrder_AtDate = I_MD_Cockpit.COLUMNNAME_QtySupply_PurchaseOrder_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtySupply_PurchaseOrder_AtDate, //
			captionKey = FIELDNAME_QtySupply_PurchaseOrder_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 130,
			displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	@Getter // note that we use the getter for testing
	private final BigDecimal qtySupplyPurchaseOrderAtDate;

	public static final String FIELDNAME_QtySupply_PurchaseOrder = "QtySupply_PurchaseOrder";
	@ViewColumn(fieldName = FIELDNAME_QtySupply_PurchaseOrder, //
			captionKey = FIELDNAME_QtySupply_PurchaseOrder, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 140,
			displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplyPurchaseOrder;

	public static final String FIELDNAME_QtySupplyDDOrder_AtDate = I_MD_Cockpit.COLUMNNAME_QtySupply_DD_Order_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtySupplyDDOrder_AtDate, //
			captionKey = FIELDNAME_QtySupplyDDOrder_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 150,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplyDDOrderAtDate;

	public static final String FIELDNAME_QtySupplySum_AtDate = I_MD_Cockpit.COLUMNNAME_QtySupplySum_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtySupplySum_AtDate, //
			captionKey = FIELDNAME_QtySupplySum_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 160,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplySumAtDate;

	public static final String FIELDNAME_QtySupplyRequired_AtDate = I_MD_Cockpit.COLUMNNAME_QtySupplyRequired_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtySupplyRequired_AtDate, //
			captionKey = FIELDNAME_QtySupplyRequired_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 170,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplyRequiredAtDate;

	public static final String FIELDNAME_QtySupplyToSchedule_AtDate = I_MD_Cockpit.COLUMNNAME_QtySupplyToSchedule_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtySupplyToSchedule_AtDate, //
			captionKey = FIELDNAME_QtySupplyToSchedule_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 180,
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtySupplyToScheduleAtDate;

	public static final String FIELDNAME_QtyMaterialentnahme_AtDate = I_MD_Cockpit.COLUMNNAME_QtyMaterialentnahme_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyMaterialentnahme_AtDate, //
			captionKey = FIELDNAME_QtyMaterialentnahme_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 190, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyMaterialentnahmeAtDate;

	// MRP MEnge
	public static final String FIELDNAME_QtyDemand_PP_Order_AtDate = I_MD_Cockpit.COLUMNNAME_QtyDemand_PP_Order_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyDemand_PP_Order_AtDate, //
			captionKey = FIELDNAME_QtyDemand_PP_Order_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 200, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyDemandPPOrderAtDate;

	// Zaehlbestand
	public static final String FIELDNAME_QtyStockCurrent_AtDate = I_MD_Cockpit.COLUMNNAME_QtyStockCurrent_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyStockCurrent_AtDate, //
			captionKey = FIELDNAME_QtyStockCurrent_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 210, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyStockCurrentAtDate;

	public static final String FIELDNAME_QtyStockEstimateCount_AtDate = I_MD_Cockpit.COLUMNNAME_QtyStockEstimateCount_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyStockEstimateCount_AtDate, //
			captionKey = FIELDNAME_QtyStockEstimateCount_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 220, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyStockEstimateCountAtDate;

	public static final String FIELDNAME_QtyStockEstimateTime_AtDate = I_MD_Cockpit.COLUMNNAME_QtyStockEstimateTime_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyStockEstimateTime_AtDate, //
			captionKey = FIELDNAME_QtyStockEstimateTime_AtDate, //
			widgetType = DocumentFieldWidgetType.Timestamp, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 230, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final Instant qtyStockEstimateTimeAtDate;

	public static final String FIELDNAME_QtyInventoryCount_AtDate = I_MD_Cockpit.COLUMNNAME_QtyInventoryCount_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyInventoryCount_AtDate, //
			captionKey = FIELDNAME_QtyInventoryCount_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 240, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyInventoryCountAtDate;

	public static final String FIELDNAME_QtyInventoryTime_AtDate = I_MD_Cockpit.COLUMNNAME_QtyInventoryTime_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyInventoryTime_AtDate, //
			captionKey = FIELDNAME_QtyInventoryTime_AtDate, //
			widgetType = DocumentFieldWidgetType.Timestamp, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 250, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final Instant qtyInventoryTimeAtDate;

	// zusagbar Zaehlbestand
	public static final String FIELDNAME_QtyExpectedSurplus_AtDate = I_MD_Cockpit.COLUMNNAME_QtyExpectedSurplus_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyExpectedSurplus_AtDate, //
			captionKey = FIELDNAME_QtyExpectedSurplus_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 260, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyExpectedSurplusAtDate;

	@ViewColumn(widgetType = DocumentFieldWidgetType.Quantity, //
			captionKey = I_MD_Stock.COLUMNNAME_QtyOnHand, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 270) })
	@Getter // note that we use the getter for testing
	private final BigDecimal qtyOnHandStock;

	public static final String FIELDNAME_procurementStatus = I_M_Product.COLUMNNAME_ProcurementStatus;
	@ViewColumn(fieldName = FIELDNAME_procurementStatus, //
			captionKey = FIELDNAME_procurementStatus, //
			widgetType = DocumentFieldWidgetType.Color, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 280, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final String procurementStatus;

	public static final String FIELDNAME_HighestPurchasePrice_AtDate = "HighestPurchasePrice_AtDate";
	@ViewColumn(fieldName = FIELDNAME_HighestPurchasePrice_AtDate, //
			captionKey = FIELDNAME_HighestPurchasePrice_AtDate, //
			widgetType = DocumentFieldWidgetType.CostPrice, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 290, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal highestPurchasePrice_AtDate;

	public static final String FIELDNAME_QtyOrdered_PurchaseOrder_AtDate = I_MD_Cockpit.COLUMNNAME_QtyOrdered_PurchaseOrder_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyOrdered_PurchaseOrder_AtDate, //
			captionKey = FIELDNAME_QtyOrdered_PurchaseOrder_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 300, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyOrdered_PurchaseOrder_AtDate;

	public static final String FIELDNAME_QtyOrdered_SalesOrder_AtDate = I_MD_Cockpit.COLUMNNAME_QtyOrdered_SalesOrder_AtDate;
	@ViewColumn(fieldName = FIELDNAME_QtyOrdered_SalesOrder_AtDate, //
			captionKey = FIELDNAME_QtyOrdered_SalesOrder_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 310, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal qtyOrdered_SalesOrder_AtDate;

	public static final String FIELDNAME_AvailableQty_AtDate = "AvailableQty_AtDate";
	@ViewColumn(fieldName = FIELDNAME_AvailableQty_AtDate, //
			captionKey = FIELDNAME_AvailableQty_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 320, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal availableQty_AtDate;

	public static final String FIELDNAME_RemainingStock_AtDate = "RemainingStock_AtDate";
	@ViewColumn(fieldName = FIELDNAME_RemainingStock_AtDate, //
			captionKey = FIELDNAME_RemainingStock_AtDate, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 330, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal remainingStock_AtDate;

	public static final String FIELDNAME_PMM_QtyPromised_NextDay = I_MD_Cockpit.COLUMNNAME_PMM_QtyPromised_NextDay;
	@ViewColumn(fieldName = FIELDNAME_PMM_QtyPromised_NextDay, //
			captionKey = FIELDNAME_PMM_QtyPromised_NextDay, //
			widgetType = DocumentFieldWidgetType.Quantity, //
			layouts = { @ViewColumnLayout(when = JSONViewDataType.grid, seqNo = 340, //
					displayed = Displayed.SYSCONFIG, displayedSysConfigPrefix = SYSCFG_PREFIX) })
	private final BigDecimal pmm_QtyPromised_NextDay;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private final DocumentId documentId;

	private final DocumentPath documentPath;

	private final List<MaterialCockpitRow> includedRows;

	private final IViewRowType rowType;

	@Getter
	private final Set<Integer> allIncludedCockpitRecordIds;

	@Getter
	private final Set<Integer> allIncludedStockRecordIds;

	private final ViewRowFieldNameAndJsonValuesHolder<MaterialCockpitRow> values = ViewRowFieldNameAndJsonValuesHolder.newInstance(MaterialCockpitRow.class);

	@lombok.Builder(builderClassName = "MainRowBuilder", builderMethodName = "mainRowBuilder")
	private MaterialCockpitRow(
<<<<<<< HEAD
			final Quantity qtyDemandSalesOrder,
			final Quantity qtyDemandPPOrder,
			final Quantity qtyDemandDDOrder,
			final Quantity pmmQtyPromised,
			final Quantity qtyDemandSum,
			final Quantity qtySupplyPPOrder,
			final Quantity qtySupplyPurchaseOrder,
			final Quantity qtySupplyDDOrder,
			final Quantity qtySupplySum,
			final Quantity qtySupplyRequired,
			final Quantity qtySupplyToSchedule,
			final Quantity qtyMaterialentnahme,
			final Quantity qtyStockEstimateCount,
			final Instant qtyStockEstimateTime,
			@Nullable final Integer qtyStockEstimateSeqNo,
			final Quantity qtyInventoryCount,
			final Instant qtyInventoryTime,
			final Quantity qtyExpectedSurplus,
			final Quantity qtyStockCurrent,
			final Quantity qtyOnHandStock,
			@NonNull final ProductId productId,
			@NonNull final LocalDate date,
=======
			@NonNull final MaterialCockpitRowCache cache,
			@NonNull final MaterialCockpitRowLookups lookups,
			final Quantity qtyDemandSalesOrderAtDate,
			final Quantity qtyDemandSalesOrder,
			final Quantity qtyDemandPPOrderAtDate,
			final Quantity qtyDemandDDOrderAtDate,
			final Quantity pmmQtyPromisedAtDate,
			final Quantity qtyDemandSumAtDate,
			final Quantity qtySupplyPPOrderAtDate,
			final Quantity qtySupplyPurchaseOrderAtDate,
			final Quantity qtySupplyPurchaseOrder,
			final Quantity qtySupplyDDOrderAtDate,
			final Quantity qtySupplySumAtDate,
			final Quantity qtySupplyRequiredAtDate,
			final Quantity qtySupplyToScheduleAtDate,
			final Quantity qtyMaterialentnahmeAtDate,
			final Quantity qtyStockEstimateCountAtDate,
			final Instant qtyStockEstimateTimeAtDate,
			@Nullable final Integer qtyStockEstimateSeqNoAtDate,
			final Quantity qtyInventoryCountAtDate,
			final Instant qtyInventoryTimeAtDate,
			final Quantity qtyExpectedSurplusAtDate,
			final Quantity qtyStockCurrentAtDate,
			final Quantity qtyOnHandStock,
			@NonNull final ProductId productId,
			@NonNull final LocalDate date,
			@Nullable final String procurementStatus,
			final Money highestPurchasePrice_AtDate,
			final Quantity qtyOrdered_PurchaseOrder_AtDate,
			final Quantity qtyOrdered_SalesOrder_AtDate,
			final Quantity availableQty_AtDate,
			final Quantity remainingStock_AtDate,
			final Quantity pmm_QtyPromised_NextDay,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			@Singular final List<MaterialCockpitRow> includedRows,
			@NonNull final Set<Integer> allIncludedCockpitRecordIds,
			@NonNull final Set<Integer> allIncludedStockRecordIds,
			@Nullable final QtyConvertor qtyConvertor)
	{
		this.rowType = DefaultRowType.Row;

		this.date = date;

		this.dimensionGroupOrNull = null;

<<<<<<< HEAD
		this.productId = productId.getRepoId();
=======
		this.productId = productId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		this.documentId = DocumentId.of(DOCUMENT_ID_JOINER.join(
				"main",
				date,
				productId.getRepoId()));

		this.documentPath = DocumentPath.rootDocumentPath(
				MaterialCockpitUtil.WINDOWID_MaterialCockpitView,
				documentId);

		final IProductDAO productDAO = Services.get(IProductDAO.class);

		final I_M_Product productRecord = productDAO.getById(productId);
		final I_M_Product_Category productCategoryRecord = productDAO.getProductCategoryById(ProductCategoryId.ofRepoId(productRecord.getM_Product_Category_ID()));

		this.productValue = productRecord.getValue();
		this.productName = productRecord.getName();
		this.productCategoryOrSubRowName = productCategoryRecord.getName();

		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;

		final int uomRepoId = CoalesceUtil.firstGreaterThanZero(productRecord.getPackage_UOM_ID(), productRecord.getC_UOM_ID());
		final QtyConvertor convertor = qtyConvertor != null ? qtyConvertor : QtyConvertor.getNoOp(UomId.ofRepoId(uomRepoId));

		this.uom = () -> lookupFactory
				.searchInTableLookup(I_C_UOM.Table_Name)
				.findById(convertor.getTargetUomId());
		this.manufacturer = () -> lookupFactory
				.searchInTableLookup(I_C_BPartner.Table_Name)
				.findById(productRecord.getManufacturer_ID());

		this.packageSize = productRecord.getPackageSize();

		this.includedRows = includedRows;

<<<<<<< HEAD
		this.pmmQtyPromised = Quantity.toBigDecimal(convertor.convert(pmmQtyPromised));
		this.qtyDemandSalesOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandSalesOrder));
		this.qtyDemandDDOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandDDOrder));
		this.qtyDemandPPOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandPPOrder));
		this.qtyDemandSum = Quantity.toBigDecimal(convertor.convert(qtyDemandSum));
		this.qtySupplyPPOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyPPOrder));
		this.qtySupplyPurchaseOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyPurchaseOrder));
		this.qtySupplyDDOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyDDOrder));
		this.qtySupplySum = Quantity.toBigDecimal(convertor.convert(qtySupplySum));
		this.qtySupplyRequired = Quantity.toBigDecimal(convertor.convert(qtySupplyRequired));
		this.qtySupplyToSchedule = Quantity.toBigDecimal(convertor.convert(qtySupplyToSchedule));
		this.qtyMaterialentnahme = Quantity.toBigDecimal(convertor.convert(qtyMaterialentnahme));
		this.qtyStockCurrent = Quantity.toBigDecimal(convertor.convert(qtyStockCurrent));
		this.qtyExpectedSurplus = Quantity.toBigDecimal(convertor.convert(qtyExpectedSurplus));
		this.qtyOnHandStock = Quantity.toBigDecimal(convertor.convert(qtyOnHandStock));
		this.qtyStockEstimateCount = Quantity.toBigDecimal(convertor.convert(qtyStockEstimateCount));
		this.qtyStockEstimateTime = qtyStockEstimateTime;
		this.qtyStockEstimateSeqNo = qtyStockEstimateSeqNo;
		this.qtyInventoryCount = Quantity.toBigDecimal(qtyInventoryCount);
		this.qtyInventoryTime = qtyInventoryTime;

		final List<Quantity> quantitiesToVerify = Arrays.asList(
				pmmQtyPromised,
				qtyDemandSalesOrder,
				qtySupplyPurchaseOrder,
				qtySupplyPPOrder,
				qtySupplyDDOrder,
				qtySupplySum,
				qtySupplyRequired,
				qtySupplyToSchedule,
				qtyMaterialentnahme,
				qtyDemandPPOrder,
				qtyDemandDDOrder,
				qtyDemandSum,
				qtyStockCurrent,
				qtyExpectedSurplus,
				qtyOnHandStock);
=======
		this.pmmQtyPromisedAtDate = Quantity.toBigDecimal(convertor.convert(pmmQtyPromisedAtDate));
		this.qtyDemandSalesOrderAtDate = Quantity.toBigDecimal(convertor.convert(qtyDemandSalesOrderAtDate));
		this.qtyDemandSalesOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandSalesOrder));
		this.qtyDemandDDOrderAtDate = Quantity.toBigDecimal(convertor.convert(qtyDemandDDOrderAtDate));
		this.qtyDemandPPOrderAtDate = Quantity.toBigDecimal(convertor.convert(qtyDemandPPOrderAtDate));
		this.qtyDemandSumAtDate = Quantity.toBigDecimal(convertor.convert(qtyDemandSumAtDate));
		this.qtySupplyPPOrderAtDate = Quantity.toBigDecimal(convertor.convert(qtySupplyPPOrderAtDate));
		this.qtySupplyPurchaseOrderAtDate = Quantity.toBigDecimal(convertor.convert(qtySupplyPurchaseOrderAtDate));
		this.qtySupplyPurchaseOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyPurchaseOrder));
		this.qtySupplyDDOrderAtDate = Quantity.toBigDecimal(convertor.convert(qtySupplyDDOrderAtDate));
		this.qtySupplySumAtDate = Quantity.toBigDecimal(convertor.convert(qtySupplySumAtDate));
		this.qtySupplyRequiredAtDate = Quantity.toBigDecimal(convertor.convert(qtySupplyRequiredAtDate));
		this.qtySupplyToScheduleAtDate = Quantity.toBigDecimal(convertor.convert(qtySupplyToScheduleAtDate));
		this.qtyMaterialentnahmeAtDate = Quantity.toBigDecimal(convertor.convert(qtyMaterialentnahmeAtDate));
		this.qtyStockCurrentAtDate = Quantity.toBigDecimal(convertor.convert(qtyStockCurrentAtDate));
		this.qtyExpectedSurplusAtDate = Quantity.toBigDecimal(convertor.convert(qtyExpectedSurplusAtDate));
		this.qtyOnHandStock = Quantity.toBigDecimal(convertor.convert(qtyOnHandStock));
		this.qtyStockEstimateCountAtDate = Quantity.toBigDecimal(convertor.convert(qtyStockEstimateCountAtDate));
		this.qtyStockEstimateTimeAtDate = qtyStockEstimateTimeAtDate;
		this.qtyStockEstimateSeqNoAtDate = qtyStockEstimateSeqNoAtDate;
		this.qtyInventoryCountAtDate = Quantity.toBigDecimal(qtyInventoryCountAtDate);
		this.qtyInventoryTimeAtDate = qtyInventoryTimeAtDate;

		this.procurementStatus = procurementStatus;
		this.highestPurchasePrice_AtDate = Money.toBigDecimalOrZero(highestPurchasePrice_AtDate);
		this.qtyOrdered_PurchaseOrder_AtDate = Quantity.toBigDecimal(qtyOrdered_PurchaseOrder_AtDate);
		this.qtyOrdered_SalesOrder_AtDate = Quantity.toBigDecimal(qtyOrdered_SalesOrder_AtDate);
		this.availableQty_AtDate = Quantity.toBigDecimal(availableQty_AtDate);
		this.remainingStock_AtDate = Quantity.toBigDecimal(remainingStock_AtDate);
		this.pmm_QtyPromised_NextDay = Quantity.toBigDecimal(pmm_QtyPromised_NextDay);

		final List<Quantity> quantitiesToVerify = Arrays.asList(
				pmmQtyPromisedAtDate,
				qtyDemandSalesOrderAtDate,
				qtyDemandSalesOrder,
				qtySupplyPurchaseOrderAtDate,
				qtySupplyPurchaseOrder,
				qtySupplyPPOrderAtDate,
				qtySupplyDDOrderAtDate,
				qtySupplySumAtDate,
				qtySupplyRequiredAtDate,
				qtySupplyToScheduleAtDate,
				qtyMaterialentnahmeAtDate,
				qtyDemandPPOrderAtDate,
				qtyDemandDDOrderAtDate,
				qtyDemandSumAtDate,
				qtyStockCurrentAtDate,
				qtyExpectedSurplusAtDate,
				qtyOnHandStock,
				qtyOrdered_PurchaseOrder_AtDate,
				qtyOrdered_SalesOrder_AtDate,
				availableQty_AtDate,
				remainingStock_AtDate,
				pmm_QtyPromised_NextDay);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		assertNullOrCommonUomId(quantitiesToVerify);

		this.allIncludedCockpitRecordIds = ImmutableSet.copyOf(allIncludedCockpitRecordIds);
		this.allIncludedStockRecordIds = ImmutableSet.copyOf(allIncludedStockRecordIds);
	}

	private void assertNullOrCommonUomId(@NonNull final List<Quantity> quantitiesToVerify)
	{
		final boolean notOK = CollectionUtils.hasDifferentValues(
				ListUtils.copyAndFilter(quantitiesToVerify, Objects::nonNull),
				Quantity::getUomId);
		Check.errorIf(notOK, "Some of the given quantities have different UOMs; quantities={}", quantitiesToVerify);
	}

	private static LocalDate extractDate(@NonNull final List<MaterialCockpitRow> includedRows)
	{
		return CollectionUtils.extractSingleElement(includedRows, row -> row.date);
	}

<<<<<<< HEAD
	private static int extractProductId(@NonNull final List<MaterialCockpitRow> includedRows)
=======
	private static ProductId extractProductId(@NonNull final List<MaterialCockpitRow> includedRows)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return CollectionUtils.extractSingleElement(includedRows, MaterialCockpitRow::getProductId);
	}

	@lombok.Builder(builderClassName = "AttributeSubRowBuilder", builderMethodName = "attributeSubRowBuilder")
	private MaterialCockpitRow(
<<<<<<< HEAD
			final int productId,
			final LocalDate date,
			@NonNull final DimensionSpecGroup dimensionGroup,
			final Quantity pmmQtyPromised,
			final Quantity qtyDemandSalesOrder,
			final Quantity qtyDemandDDOrder,
			final Quantity qtyDemandSum,
			final Quantity qtySupplyPPOrder,
			final Quantity qtySupplyPurchaseOrder,
			final Quantity qtySupplyDDOrder,
			final Quantity qtySupplySum,
			final Quantity qtySupplyRequired,
			final Quantity qtySupplyToSchedule,
			final Quantity qtyMaterialentnahme,
			final Quantity qtyDemandPPOrder,
			final Quantity qtyStockEstimateCount,
			final Instant qtyStockEstimateTime,
			@Nullable final Integer qtyStockEstimateSeqNo,
			final Quantity qtyInventoryCount,
			final Instant qtyInventoryTime,
			final Quantity qtyExpectedSurplus,
			final Quantity qtyOnHandStock,
=======
			final ProductId productId,
			final LocalDate date,
			@NonNull final DimensionSpecGroup dimensionGroup,
			final Quantity pmmQtyPromisedAtDate,
			final Quantity qtyDemandSalesOrderAtDate,
			final Quantity qtyDemandSalesOrder,
			final Quantity qtyDemandDDOrderAtDate,
			final Quantity qtyDemandSumAtDate,
			final Quantity qtySupplyPPOrderAtDate,
			final Quantity qtySupplyPurchaseOrderAtDate,
			final Quantity qtySupplyPurchaseOrder,
			final Quantity qtySupplyDDOrderAtDate,
			final Quantity qtySupplySumAtDate,
			final Quantity qtySupplyRequiredAtDate,
			final Quantity qtySupplyToScheduleAtDate,
			final Quantity qtyMaterialentnahmeAtDate,
			final Quantity qtyDemandPPOrderAtDate,
			final Quantity qtyStockEstimateCountAtDate,
			final Instant qtyStockEstimateTimeAtDate,
			@Nullable final Integer qtyStockEstimateSeqNoAtDate,
			final Quantity qtyInventoryCountAtDate,
			final Instant qtyInventoryTimeAtDate,
			final Quantity qtyExpectedSurplusAtDate,
			final Quantity qtyOnHandStock,
			final String procurementStatus,
			final Money highestPurchasePrice_AtDate,
			final Quantity qtyOrdered_PurchaseOrder_AtDate,
			final Quantity  qtyOrdered_SalesOrder_AtDate,
			final Quantity  availableQty_AtDate,
			final Quantity remainingStock_AtDate,
			final Quantity pmm_QtyPromised_NextDay,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			@NonNull final Set<Integer> allIncludedCockpitRecordIds,
			@NonNull final Set<Integer> allIncludedStockRecordIds,
			@Nullable final QtyConvertor qtyConvertor)
	{
		this.rowType = DefaultRowType.Line;

		this.dimensionGroupOrNull = dimensionGroup;
		final String dimensionGroupName = dimensionGroup.getGroupName().translate(Env.getAD_Language());

		this.documentId = DocumentId.of(DOCUMENT_ID_JOINER.join(
				"attributes",
				date,
				productId,
				dimensionGroupName));

		this.documentPath = DocumentPath.rootDocumentPath(
				MaterialCockpitUtil.WINDOWID_MaterialCockpitView,
				documentId);

		this.productId = productId;

		final I_M_Product productRecord = loadOutOfTrx(productId, I_M_Product.class);
		this.productValue = productRecord.getValue();
		this.productName = productRecord.getName();
		this.productCategoryOrSubRowName = dimensionGroupName;

		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;

		final int uomRepoId = CoalesceUtil.firstGreaterThanZero(productRecord.getPackage_UOM_ID(), productRecord.getC_UOM_ID());
		final QtyConvertor convertor = qtyConvertor != null ? qtyConvertor : QtyConvertor.getNoOp(UomId.ofRepoId(uomRepoId));
		this.uom = () -> lookupFactory
				.searchInTableLookup(I_C_UOM.Table_Name)
				.findById(convertor.getTargetUomId());

		this.manufacturer = () -> lookupFactory
				.searchInTableLookup(I_C_BPartner.Table_Name)
				.findById(productRecord.getManufacturer_ID());

		this.packageSize = productRecord.getPackageSize();

		this.date = date;

		this.includedRows = ImmutableList.of();

<<<<<<< HEAD
		this.pmmQtyPromised = Quantity.toBigDecimal(convertor.convert(pmmQtyPromised));
		this.qtyDemandSalesOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandSalesOrder));
		this.qtyDemandDDOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandDDOrder));
		this.qtyDemandSum = Quantity.toBigDecimal(convertor.convert(qtyDemandSum));
		this.qtyDemandPPOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandPPOrder));
		this.qtyMaterialentnahme = Quantity.toBigDecimal(convertor.convert(qtyMaterialentnahme));

		this.qtySupplyPurchaseOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyPurchaseOrder));
		this.qtySupplyPPOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyPPOrder));
		this.qtySupplyDDOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyDDOrder));
		this.qtySupplySum = Quantity.toBigDecimal(convertor.convert(qtySupplySum));
		this.qtySupplyRequired = Quantity.toBigDecimal(convertor.convert(qtySupplyRequired));
		this.qtySupplyToSchedule = Quantity.toBigDecimal(convertor.convert(qtySupplyToSchedule));

		this.qtyStockCurrent = null;
		this.qtyOnHandStock = Quantity.toBigDecimal(convertor.convert(qtyOnHandStock));
		this.qtyExpectedSurplus = Quantity.toBigDecimal(convertor.convert(qtyExpectedSurplus));
		this.qtyStockEstimateCount = Quantity.toBigDecimal(convertor.convert(qtyStockEstimateCount));
		this.qtyStockEstimateTime = qtyStockEstimateTime;
		this.qtyStockEstimateSeqNo = qtyStockEstimateSeqNo;
		this.qtyInventoryCount = Quantity.toBigDecimal(convertor.convert(qtyInventoryCount));
		this.qtyInventoryTime = qtyInventoryTime;
=======
		this.pmmQtyPromisedAtDate = Quantity.toBigDecimal(convertor.convert(pmmQtyPromisedAtDate));
		this.qtyDemandSalesOrderAtDate = Quantity.toBigDecimal(convertor.convert(qtyDemandSalesOrderAtDate));
		this.qtyDemandSalesOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandSalesOrder));
		this.qtyDemandDDOrderAtDate = Quantity.toBigDecimal(convertor.convert(qtyDemandDDOrderAtDate));
		this.qtyDemandSumAtDate = Quantity.toBigDecimal(convertor.convert(qtyDemandSumAtDate));
		this.qtyDemandPPOrderAtDate = Quantity.toBigDecimal(convertor.convert(qtyDemandPPOrderAtDate));
		this.qtyMaterialentnahmeAtDate = Quantity.toBigDecimal(convertor.convert(qtyMaterialentnahmeAtDate));

		this.qtySupplyPurchaseOrderAtDate = Quantity.toBigDecimal(convertor.convert(qtySupplyPurchaseOrderAtDate));
		this.qtySupplyPurchaseOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyPurchaseOrder));
		this.qtySupplyPPOrderAtDate = Quantity.toBigDecimal(convertor.convert(qtySupplyPPOrderAtDate));
		this.qtySupplyDDOrderAtDate = Quantity.toBigDecimal(convertor.convert(qtySupplyDDOrderAtDate));
		this.qtySupplySumAtDate = Quantity.toBigDecimal(convertor.convert(qtySupplySumAtDate));
		this.qtySupplyRequiredAtDate = Quantity.toBigDecimal(convertor.convert(qtySupplyRequiredAtDate));
		this.qtySupplyToScheduleAtDate = Quantity.toBigDecimal(convertor.convert(qtySupplyToScheduleAtDate));

		this.qtyStockCurrentAtDate = null;
		this.qtyOnHandStock = Quantity.toBigDecimal(convertor.convert(qtyOnHandStock));
		this.qtyExpectedSurplusAtDate = Quantity.toBigDecimal(convertor.convert(qtyExpectedSurplusAtDate));
		this.qtyStockEstimateCountAtDate = Quantity.toBigDecimal(convertor.convert(qtyStockEstimateCountAtDate));
		this.qtyStockEstimateTimeAtDate = qtyStockEstimateTimeAtDate;
		this.qtyStockEstimateSeqNoAtDate = qtyStockEstimateSeqNoAtDate;
		this.qtyInventoryCountAtDate = Quantity.toBigDecimal(convertor.convert(qtyInventoryCountAtDate));
		this.qtyInventoryTimeAtDate = qtyInventoryTimeAtDate;

		this.procurementStatus = procurementStatus;
		this.highestPurchasePrice_AtDate = Money.toBigDecimalOrZero(highestPurchasePrice_AtDate);
		this.qtyOrdered_PurchaseOrder_AtDate = Quantity.toBigDecimal(convertor.convert(qtyOrdered_PurchaseOrder_AtDate));
		this.qtyOrdered_SalesOrder_AtDate = Quantity.toBigDecimal(convertor.convert(qtyOrdered_SalesOrder_AtDate));
		this.availableQty_AtDate = Quantity.toBigDecimal(convertor.convert(availableQty_AtDate));
		this.remainingStock_AtDate = Quantity.toBigDecimal(convertor.convert(remainingStock_AtDate));
		this.pmm_QtyPromised_NextDay = Quantity.toBigDecimal(convertor.convert(pmm_QtyPromised_NextDay));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		this.allIncludedCockpitRecordIds = ImmutableSet.copyOf(allIncludedCockpitRecordIds);
		this.allIncludedStockRecordIds = ImmutableSet.copyOf(allIncludedStockRecordIds);
	}

	@lombok.Builder(builderClassName = "CountingSubRowBuilder", builderMethodName = "countingSubRowBuilder")
	private MaterialCockpitRow(
<<<<<<< HEAD
			final int productId,
			final LocalDate date,
			final int plantId,
			@Nullable final Quantity qtyStockEstimateCount,
			@Nullable final Instant qtyStockEstimateTime,
			@Nullable final Integer qtyStockEstimateSeqNo,
			@Nullable final Quantity qtyInventoryCount,
			@Nullable final Instant qtyInventoryTime,
			@Nullable final Quantity qtyStockCurrent,
=======
			@NonNull final MaterialCockpitRowCache cache,
			final ProductId productId,
			final LocalDate date,
			@NonNull final MaterialCockpitDetailsRowAggregationIdentifier detailsRowAggregationIdentifier,
			@Nullable final Quantity qtyDemandSalesOrder,
			@Nullable final Quantity qtySupplyPurchaseOrder,
			@Nullable final Quantity qtyStockEstimateCountAtDate,
			@Nullable final Instant qtyStockEstimateTimeAtDate,
			@Nullable final Integer qtyStockEstimateSeqNoAtDate,
			@Nullable final Quantity qtyInventoryCountAtDate,
			@Nullable final Instant qtyInventoryTimeAtDate,
			@Nullable final Quantity qtyStockCurrentAtDate,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			@Nullable final Quantity qtyOnHandStock,
			@NonNull final Set<Integer> allIncludedCockpitRecordIds,
			@NonNull final Set<Integer> allIncludedStockRecordIds,
			@Nullable final QtyConvertor qtyConvertor)
	{
		this.rowType = DefaultRowType.Line;

		this.dimensionGroupOrNull = null;

<<<<<<< HEAD
		final String plantName;
		if (plantId > 0)
		{
			final I_S_Resource plant = loadOutOfTrx(plantId, I_S_Resource.class);
			plantName = plant.getName();
		}
		else
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			plantName = msgBL.getMsg(Env.getCtx(), "de.metas.ui.web.material.cockpit.MaterialCockpitRow.No_Plant_Info");
=======
		final String aggregatorName;
		final MaterialCockpitDetailsRowAggregation detailsRowAggregation = detailsRowAggregationIdentifier.getDetailsRowAggregation();

		if (detailsRowAggregation.isPlant())
		{

			final ResourceId plantId = ResourceId.ofRepoIdOrNull(detailsRowAggregationIdentifier.getAggregationId());

			if (plantId != null)
			{
				aggregatorName = ResourceService.Legacy.getResourceName(plantId);
			}
			else
			{
				final IMsgBL msgBL = Services.get(IMsgBL.class);
				aggregatorName = msgBL.getMsg(Env.getCtx(), "de.metas.ui.web.material.cockpit.MaterialCockpitRow.No_Plant_Info");
			}
		}
		else if (detailsRowAggregation.isWarehouse())
		{

			final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(detailsRowAggregationIdentifier.getAggregationId());
			if (warehouseId != null)
			{
				aggregatorName = cache.getWarehouseById(warehouseId).getName();
			}
			else
			{
				final IMsgBL msgBL = Services.get(IMsgBL.class);
				aggregatorName = msgBL.getMsg(Env.getCtx(), "de.metas.ui.web.material.cockpit.MaterialCockpitRow.No_Warehouse_Info");
			}
		}
		else
		{
			aggregatorName = "";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		this.documentId = DocumentId.of(DOCUMENT_ID_JOINER.join(
				"countingRow",
				date,
				productId,
<<<<<<< HEAD
				plantName));
=======
				aggregatorName));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		this.documentPath = DocumentPath.rootDocumentPath(
				MaterialCockpitUtil.WINDOWID_MaterialCockpitView,
				documentId);

		this.productId = productId;

		final I_M_Product productRecord = loadOutOfTrx(productId, I_M_Product.class);
		this.productValue = productRecord.getValue();
		this.productName = productRecord.getName();
<<<<<<< HEAD
		this.productCategoryOrSubRowName = plantName;
=======
		this.productCategoryOrSubRowName = aggregatorName;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		final LookupDataSourceFactory lookupFactory = LookupDataSourceFactory.instance;

		final int uomRepoId = CoalesceUtil.firstGreaterThanZero(productRecord.getPackage_UOM_ID(), productRecord.getC_UOM_ID());
		final QtyConvertor convertor = qtyConvertor != null ? qtyConvertor : QtyConvertor.getNoOp(UomId.ofRepoId(uomRepoId));
		this.uom = () -> lookupFactory
				.searchInTableLookup(I_C_UOM.Table_Name)
				.findById(convertor.getTargetUomId());

		this.manufacturer = () -> lookupFactory
				.searchInTableLookup(I_C_BPartner.Table_Name)
				.findById(productRecord.getManufacturer_ID());

		this.packageSize = productRecord.getPackageSize();

		this.date = date;
		this.includedRows = ImmutableList.of();

<<<<<<< HEAD
		this.pmmQtyPromised = null;
		this.qtyDemandSalesOrder = null;
		this.qtyDemandDDOrder = null;
		this.qtyDemandSum = null;
		this.qtySupplyPPOrder = null;
		this.qtySupplyPurchaseOrder = null;
		this.qtyMaterialentnahme = null;
		this.qtyDemandPPOrder = null;
		this.qtySupplyDDOrder = null;
		this.qtySupplySum = null;
		this.qtySupplyRequired = null;
		this.qtySupplyToSchedule = null;
		this.qtyStockCurrent = Quantity.toBigDecimal(convertor.convert(qtyStockCurrent));
		this.qtyOnHandStock = Quantity.toBigDecimal(convertor.convert(qtyOnHandStock));
		this.qtyExpectedSurplus = null;
		this.qtyStockEstimateCount = Quantity.toBigDecimal(convertor.convert(qtyStockEstimateCount));
		this.qtyStockEstimateTime = qtyStockEstimateTime;
		this.qtyStockEstimateSeqNo = qtyStockEstimateSeqNo;
		this.qtyInventoryCount = Quantity.toBigDecimal(convertor.convert(qtyInventoryCount));
		this.qtyInventoryTime = qtyInventoryTime;
=======
		this.pmmQtyPromisedAtDate = null;
		this.qtyDemandSalesOrderAtDate = null;
		this.qtyDemandSalesOrder = Quantity.toBigDecimal(convertor.convert(qtyDemandSalesOrder));
		this.qtyDemandDDOrderAtDate = null;
		this.qtyDemandSumAtDate = null;
		this.qtySupplyPPOrderAtDate = null;
		this.qtySupplyPurchaseOrderAtDate = null;
		this.qtySupplyPurchaseOrder = Quantity.toBigDecimal(convertor.convert(qtySupplyPurchaseOrder));
		this.qtyMaterialentnahmeAtDate = null;
		this.qtyDemandPPOrderAtDate = null;
		this.qtySupplyDDOrderAtDate = null;
		this.qtySupplySumAtDate = null;
		this.qtySupplyRequiredAtDate = null;
		this.qtySupplyToScheduleAtDate = null;
		this.qtyStockCurrentAtDate = Quantity.toBigDecimal(convertor.convert(qtyStockCurrentAtDate));
		this.qtyOnHandStock = Quantity.toBigDecimal(convertor.convert(qtyOnHandStock));
		this.qtyExpectedSurplusAtDate = null;
		this.qtyStockEstimateCountAtDate = Quantity.toBigDecimal(convertor.convert(qtyStockEstimateCountAtDate));
		this.qtyStockEstimateTimeAtDate = qtyStockEstimateTimeAtDate;
		this.qtyStockEstimateSeqNoAtDate = qtyStockEstimateSeqNoAtDate;
		this.qtyInventoryCountAtDate = Quantity.toBigDecimal(convertor.convert(qtyInventoryCountAtDate));
		this.qtyInventoryTimeAtDate = qtyInventoryTimeAtDate;

		this.procurementStatus = null;
		this.highestPurchasePrice_AtDate = null;
		this.qtyOrdered_PurchaseOrder_AtDate = null;
		this.qtyOrdered_SalesOrder_AtDate = null;
		this.availableQty_AtDate = null;
		this.remainingStock_AtDate = null;
		this.pmm_QtyPromised_NextDay = null;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		this.allIncludedCockpitRecordIds = ImmutableSet.copyOf(allIncludedCockpitRecordIds);
		this.allIncludedStockRecordIds = ImmutableSet.copyOf(allIncludedStockRecordIds);
	}

	@Override
	public List<MaterialCockpitRow> getIncludedRows()
	{
		return includedRows;
	}

	@Override
	public DocumentId getId()
	{
		return documentId;
	}

	@Override
	public IViewRowType getType()
	{
		return rowType;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		return documentPath;
	}

	/**
	 * Return false, because with true, all rows are "grayed" out. This does not mean that the rows are editable.
	 */
	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Override
	public ImmutableSet<String> getFieldNames()
	{
		return values.getFieldNames();
	}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
	{
		return values.get(this);
	}
}
