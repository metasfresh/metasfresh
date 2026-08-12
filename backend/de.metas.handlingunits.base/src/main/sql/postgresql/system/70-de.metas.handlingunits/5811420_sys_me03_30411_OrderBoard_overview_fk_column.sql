-- Source DDL: backend/de.metas.handlingunits.base/src/main/sql/postgresql/ddl/views/M_Picking_OrderBoard_v.sql
-- Adds FK column M_Picking_OrderBoard_Overview_v_ID to M_Picking_OrderBoard_v.
-- The FK hash is computed from the same grouping columns as the overview PK
-- (product/UOM/date/country/client/org — without isassigned/processed),
-- so every detail row can be joined to its parent overview row.
--
-- IDs allocated from idserver.metas.de on 2026-07-02:
--   AD_Column 581169  (M_Picking_OrderBoard_Overview_v_ID in AD_Table 542622)

-- ============================================================
-- 1. Update M_Picking_OrderBoard_v DDL to add the FK column
-- ============================================================
DROP VIEW IF EXISTS M_Picking_OrderBoard_v$new;

CREATE OR REPLACE VIEW M_Picking_OrderBoard_v$new AS
SELECT
    (('x' || substr(md5(
        b.m_product_id::text || '_' ||
        b.c_uom_id::text || '_' ||
        COALESCE(b.isassigned, '') || '_' ||
        COALESCE(b.processed, '') || '_' ||
        COALESCE(CAST(b.deliverydate AS date)::text, '') || '_' ||
        loc.c_country_id::text || '_' ||
        b.ad_client_id::text || '_' ||
        b.ad_org_id::text
    ), 1, 8))::bit(32) & x'7fffffff'::bit(32))::int AS M_Picking_OrderBoard_v_ID,
    (('x' || substr(md5(
        b.m_product_id::text || '_' ||
        b.c_uom_id::text || '_' ||
        COALESCE(CAST(b.deliverydate AS date)::text, '') || '_' ||
        loc.c_country_id::text || '_' ||
        b.ad_client_id::text || '_' ||
        b.ad_org_id::text
    ), 1, 8))::bit(32) & x'7fffffff'::bit(32))::int AS M_Picking_OrderBoard_Overview_v_ID,
    b.m_product_id,
    prod.value                                      AS ProductValue,
    prod.name                                       AS ProductName,
    b.c_uom_id,
    CASE
        WHEN b.isassigned = 'N'                        THEN 'W'
        WHEN b.isassigned = 'Y' AND b.processed = 'N' THEN 'K'
        WHEN b.isassigned = 'Y' AND b.processed = 'Y' THEN 'P'
        ELSE NULL
    END                                             AS OrderBoardStatus,
    CAST(b.deliverydate AS date)                    AS DeliveryDate,
    loc.c_country_id,
    ctry.name                                       AS CountryName,
    SUM(COALESCE(b.qtytopick, b.qtytoscheduleforpicking, 0)) AS QtyTotal,
    COUNT(DISTINCT b.m_shipmentschedule_id)         AS OrderLineCount,
    b.ad_client_id,
    b.ad_org_id,
    MAX(b.updated)                                  AS updated,
    MAX(b.updatedby)                                AS updatedby,
    MAX(b.created)                                  AS created,
    MAX(b.createdby)                                AS createdby,
    'Y'::bpchar                                     AS isactive
FROM m_picking_job_schedule_view b
JOIN m_product prod ON prod.m_product_id = b.m_product_id
JOIN c_bpartner_location bl ON bl.c_bpartner_location_id = b.c_bpartner_location_id
JOIN c_location loc ON loc.c_location_id = bl.c_location_id
JOIN c_country ctry ON ctry.c_country_id = loc.c_country_id
WHERE
    b.isassigned = 'Y'
    OR b.qtyonhand > 0
GROUP BY
    b.m_product_id,
    prod.value,
    prod.name,
    b.c_uom_id,
    b.isassigned,
    b.processed,
    CAST(b.deliverydate AS date),
    loc.c_country_id,
    ctry.name,
    b.ad_client_id,
    b.ad_org_id;

SELECT db_alter_view(
    'M_Picking_OrderBoard_v',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(table_name) = lower('M_Picking_OrderBoard_v$new'))
);

DROP VIEW IF EXISTS M_Picking_OrderBoard_v$new;

-- ============================================================
-- 2. Register the new FK column in AD_Column (AD_Table 542622)
-- ============================================================
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,581169/*From ID Server*/,581145/*AD_Element for M_Picking_OrderBoard_Overview_v_ID*/,0,30,542622/*M_Picking_OrderBoard_v*/,'M_Picking_OrderBoard_Overview_v_ID',TO_TIMESTAMP('2026-07-02 22:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','N','Auftrags-Board-Übersicht','NP',TO_TIMESTAMP('2026-07-02 22:30:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=581169 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(581145)
;
