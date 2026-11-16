-- gh#33319: add M_Product_Category_ID (Produkt Kategorie / Warengruppe) to C_Order_M_InOut_C_Invoice_Overview_V
-- and show it in window "Ein- und Verkaufsübersicht" (542070) right after Produkt, also as filter,
-- so that it is included in the Excel export.

SELECT db_alter_view('C_Order_M_InOut_C_Invoice_Overview_V', $$
SELECT doc.line_id                                                AS C_Order_M_InOut_C_Invoice_Overview_V_ID,
       doc.ad_Table_id,
       doc.Record_ID,
       doc.issotrx,
       doc.c_doctype_id,
       dt.docbasetype,
       doc.documentno,
       doc.processed,
       doc.docstatus,
       doc.date,
       doc.c_bpartner_id,
       doc.c_bpartner_location_id,
       doc.m_product_id,
       p.m_product_category_id,
       p.c_uom_id,
       doc.qty,
       doc.linenetamt,
       COALESCE(stock.qtyonhand, 0)                               AS current_qty_sum,
       doc.priceactual,
       doc.c_orderline_id,
       doc.ad_client_id,
       doc.ad_org_id,
       doc.created,
       doc.createdby,
       doc.isactive,
       doc.posted
FROM (SELECT get_table_id('C_Order')                                   AS ad_Table_id,
             o.c_order_id                                              AS Record_ID,
             o.c_order_id                                              AS head_id,
             ol.c_orderline_id                                         AS line_id,
             ol.ad_client_id,
             ol.ad_org_id,
             ol.created,
             ol.createdby,
             GREATEST(ol.updated, o.updated)                           AS updated,
             CASE
                 WHEN ol.updated > o.updated THEN ol.updatedby
                                             ELSE o.updatedby
             END                                                       AS updatedby,
             ol.isactive,
             o.issotrx,
             COALESCE(NULLIF(o.c_doctype_id, 0), o.c_doctypetarget_id) AS c_doctype_id,
             o.documentno,
             o.processed,
             o.docstatus,
             o.dateordered                                             AS date,
             o.c_bpartner_id,
             o.c_bpartner_location_id,
             o.m_warehouse_id,
             o.posted,
             ol.m_product_id,
             ol.qtyordered                                             AS qty,
             ol.m_hu_pi_item_product_id,
             ol.priceactual,
             ol.linenetamt,
             ol.c_orderline_id
      FROM c_order o
               JOIN c_orderline ol ON ol.c_order_id = o.c_order_id

      UNION ALL

      SELECT get_table_id('M_InOut')                             AS ad_Table_id,
             io.m_inout_id                                       AS Record_ID,
             io.m_inout_id                                       AS head_id,
             1000000000 + iol.m_inoutline_id                     AS line_id,
             iol.ad_client_id,
             iol.ad_org_id,
             iol.created,
             iol.createdby,
             GREATEST(iol.updated, io.updated)                   AS updated,
             CASE
                 WHEN iol.updated > io.updated THEN iol.updatedby
                                               ELSE io.updatedby
             END                                                 AS updatedby,
             iol.isactive,
             io.issotrx,
             io.c_doctype_id,
             io.documentno,
             io.processed,
             io.docstatus,
             io.movementdate,
             io.c_bpartner_id,
             io.c_bpartner_location_id,
             io.m_warehouse_id,
             io.posted,
             iol.m_product_id,
             iol.movementqty,
             iol.m_hu_pi_item_product_id,
             NULL,
             NULL,
             iol.c_orderline_id
      FROM m_inout io
               JOIN m_inoutline iol ON iol.m_inout_id = io.m_inout_id

      UNION ALL

      SELECT get_table_id('C_Invoice')                                   AS ad_Table_id,
             i.c_invoice_id                                              AS Record_ID,
             i.c_invoice_id                                              AS head_id,
             2000000000 + il.c_invoiceline_id                            AS line_id,
             il.ad_client_id,
             il.ad_org_id,
             il.created,
             il.createdby,
             GREATEST(il.updated, i.updated)                             AS updated,
             CASE
                 WHEN il.updated > i.updated THEN il.updatedby
                                             ELSE i.updatedby
             END                                                         AS updatedby,
             il.isactive,
             i.issotrx,
             COALESCE(NULLIF(i.c_doctype_id, 0), i.c_doctypetarget_id)  AS c_doctype_id,
             i.documentno,
             i.processed,
             i.docstatus,
             i.dateinvoiced,
             i.c_bpartner_id,
             i.c_bpartner_location_id,
             i.m_warehouse_id,
             i.posted,
             il.m_product_id,
             il.qtyinvoiced,
             il.m_hu_pi_item_product_id,
             il.priceactual,
             il.linenetamt,
             il.c_orderline_id
      FROM c_invoice i
               JOIN c_invoiceline il ON il.c_invoice_id = i.c_invoice_id) doc
         LEFT JOIN c_doctype dt ON dt.c_doctype_id = doc.c_doctype_id
         LEFT JOIN m_product p ON p.m_product_id = doc.m_product_id
         LEFT JOIN (SELECT s.m_product_id, s.m_warehouse_id, SUM(s.qtyonhand) AS qtyonhand
                    FROM md_stock s
                    WHERE s.isactive = 'Y'
                    GROUP BY s.m_product_id, s.m_warehouse_id) stock ON doc.m_product_id = stock.m_product_id AND doc.m_warehouse_id = stock.m_warehouse_id
$$)
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_Category_ID
-- 2026-11-04T12:00:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
SELECT 0,593768,e.AD_Element_ID,0,19 /* Table Direct */,542578,'M_Product_Category_ID',TO_TIMESTAMP('2026-11-04 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,e.Description,'D',10,'E',e.Help,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N',e.Name,'NP',65 /* right after Produkt (60) */,0,TO_TIMESTAMP('2026-11-04 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0
FROM AD_Element e
WHERE e.AD_Element_ID = 453 /* M_Product_Category_ID */
;

-- 2026-11-04T12:00:00.010Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593768 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-11-04T12:00:00.020Z
/* DDL */  select update_Column_Translation_From_AD_Element(453)
;

-- Field: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> Produkt Kategorie
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_Category_ID
-- 2026-11-04T12:00:01.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
SELECT 0,593768,788124,0,548990,TO_TIMESTAMP('2026-11-04 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,c.Description,10,'D',c.Help,'Y','N','N','N','N','N','N','N',c.Name,TO_TIMESTAMP('2026-11-04 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100
FROM AD_Column c
WHERE c.AD_Column_ID = 593768
;

-- 2026-11-04T12:00:01.010Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=788124 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-11-04T12:00:01.020Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453)
;

-- 2026-11-04T12:00:01.030Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=788124
;

-- 2026-11-04T12:00:01.040Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(788124)
;

-- UI Element: Ein- und Verkaufsübersicht(542070,D) -> Ein- und Verkaufsübersicht(548990,D) -> main -> 10 -> Product.Produkt Kategorie
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_Category_ID
-- grid: right after Produkt (SeqNoGrid=70), before Menge (80)
-- 2026-11-04T12:00:02.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
SELECT 0,788124,0,548990,554808 /* Product */,656440,'F',TO_TIMESTAMP('2026-11-04 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,f.Description,f.Help,'Y','N','N','Y','Y','N','N',0,f.Name,11 /* between Produkt (10) and Menge (15) */,75,0,TO_TIMESTAMP('2026-11-04 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100
FROM AD_Field f
WHERE f.AD_Field_ID = 788124
;
