-- Transport Auftrag / Versandpackung subtab (tab 540097), virtual columns on M_ShippingPackage (540031).
-- Tighten the single-product gate on TransportQtys (592873) and TransportUOM_ID (592874) to also require a
-- single distinct C_UOM_ID. C_OrderLine.C_UOM_ID / M_InOutLine.C_UOM_ID is the ENTERED uom and is not
-- guaranteed to equal the product stock uom; a single product spread across lines with different entered uoms
-- (e.g. PCE on one line, Karton on another) would otherwise make sum(QtyEntered) add unlike units and
-- max(C_UOM_ID) pick an arbitrary uom. With the extra gate, those columns stay empty in that ambiguous case,
-- consistent with the existing "more than one product -> empty" behaviour.

-- Column: M_ShippingPackage.TransportQtys (592873)
UPDATE AD_Column SET ColumnSQL='(case when @JoinTableNameOrAliasIncludingDot@M_InOut_ID is not null then (select case when count(distinct iol.M_Product_ID) = 1 and count(distinct iol.C_UOM_ID) = 1 then sum(iol.QtyEntered) end from m_inoutline iol where iol.M_InOut_ID = @JoinTableNameOrAliasIncludingDot@M_InOut_ID and iol.IsActive = ''Y'' and iol.IsPackagingMaterial = ''N'' and iol.M_Product_ID is not null) when @JoinTableNameOrAliasIncludingDot@C_Order_ID is not null then (select case when count(distinct ol.M_Product_ID) = 1 and count(distinct ol.C_UOM_ID) = 1 then sum(ol.QtyEntered) end from c_orderline ol where ol.C_Order_ID = @JoinTableNameOrAliasIncludingDot@C_Order_ID and ol.IsActive = ''Y'' and ol.IsPackagingMaterial = ''N'' and ol.M_Product_ID is not null) end)',Updated=TO_TIMESTAMP('2026-06-22 16:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=592873
;

-- Column: M_ShippingPackage.TransportUOM_ID (592874)
UPDATE AD_Column SET ColumnSQL='(case when @JoinTableNameOrAliasIncludingDot@M_InOut_ID is not null then (select case when count(distinct iol.M_Product_ID) = 1 and count(distinct iol.C_UOM_ID) = 1 then max(iol.C_UOM_ID) end from m_inoutline iol where iol.M_InOut_ID = @JoinTableNameOrAliasIncludingDot@M_InOut_ID and iol.IsActive = ''Y'' and iol.IsPackagingMaterial = ''N'' and iol.M_Product_ID is not null) when @JoinTableNameOrAliasIncludingDot@C_Order_ID is not null then (select case when count(distinct ol.M_Product_ID) = 1 and count(distinct ol.C_UOM_ID) = 1 then max(ol.C_UOM_ID) end from c_orderline ol where ol.C_Order_ID = @JoinTableNameOrAliasIncludingDot@C_Order_ID and ol.IsActive = ''Y'' and ol.IsPackagingMaterial = ''N'' and ol.M_Product_ID is not null) end)',Updated=TO_TIMESTAMP('2026-06-22 16:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=592874
;
