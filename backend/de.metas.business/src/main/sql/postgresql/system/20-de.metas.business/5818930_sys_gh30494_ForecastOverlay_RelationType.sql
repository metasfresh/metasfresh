-- Make 'Sprung zu Prognose' (AD_Process 585515) open the product-scoped forecast overlay declaratively.
--
-- An AD_RelationType links the Material-Cockpit-v2 row (source QtyDemand_QtySupply_V) to the aggregate
-- view M_Forecast_ProductQty_V (target), scoped to the cockpit row's product / warehouse / organisation /
-- attributes-key. RelationTypeInOverlayProcess then resolves the relation type from
-- AD_Process.AD_RelationType_ID and opens the target window's grid as a modal overlay, so no Java of our
-- own is involved -- and its "no related documents found" message is exactly the behaviour required when
-- nothing matches.
--
-- Two details worth keeping in mind when reading the WhereClause below:
--   * The variables are resolved against the SOURCE record's context (POZoomSource puts every registered
--     AD_Column of QtyDemand_QtySupply_V into it), and all four scoping columns are registered there.
--   * AttributesKey is a String column (AD_Reference_ID=10), so it has to be QUOTED -- unlike the integer
--     columns, whose values substitute bare. An unquoted @AttributesKey@ would emit tokens such as
--     1000123-1000456 and fail as SQL. An attribute-less line yields the literal '-1002'.
--
-- Also note: the AD_Process interceptor that derives Classname from Type does not run for migration SQL,
-- so both columns are set here explicitly.

-- Source reference: the Material Cockpit v2 row.
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType)
VALUES (0,0,542127 /*From ID Server*/,TO_TIMESTAMP('2026-08-13 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',
        'RelType_Source_QtyDemand_QtySupply_V->M_Forecast_ProductQty_V',TO_TIMESTAMP('2026-08-13 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM   AD_Language l, AD_Reference t
WHERE  l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND  t.AD_Reference_ID=542127
  AND  NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Source AD_Ref_Table: QtyDemand_QtySupply_V (AD_Table_ID=542218), key QtyDemand_QtySupply_V_ID (AD_Column_ID=591429).
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy)
VALUES (0,591429,0,542127,542218,TO_TIMESTAMP('2026-08-13 14:00:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2026-08-13 14:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Target reference: the forecast quantities of the cockpit row's product.
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType)
VALUES (0,0,542128 /*From ID Server*/,TO_TIMESTAMP('2026-08-13 14:00:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',
        'Prognosemenge pro Produkt',TO_TIMESTAMP('2026-08-13 14:00:02','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Reference_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM   AD_Language l, AD_Reference t
WHERE  l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND  t.AD_Reference_ID=542128
  AND  NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

UPDATE AD_Reference_Trl SET Name='Forecast quantity per product',IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-13 14:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Reference_ID=542128
;

UPDATE AD_Reference_Trl SET IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-13 14:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Reference_ID=542128
;

-- Target AD_Ref_Table: M_Forecast_ProductQty_V (AD_Table_ID=542640), key M_Forecast_ProductQty_V_ID
-- (AD_Column_ID=593293), scoped to the cockpit row's product / warehouse / organisation / attributes-key.
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause)
VALUES (0,593293,0,542128,542640,TO_TIMESTAMP('2026-08-13 14:00:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2026-08-13 14:00:05','YYYY-MM-DD HH24:MI:SS'),100,
        'M_Forecast_ProductQty_V.M_Product_ID = @M_Product_ID/-1@ AND M_Forecast_ProductQty_V.M_Warehouse_ID = @M_Warehouse_ID/-1@ AND M_Forecast_ProductQty_V.AD_Org_ID = @AD_Org_ID/-1@ AND M_Forecast_ProductQty_V.AttributesKey = ''@AttributesKey@''')
;

INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy)
VALUES (0,0,542127,542128,540503 /*From ID Server*/,TO_TIMESTAMP('2026-08-13 14:00:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',
        'QtyDemand_QtySupply_V_to_M_Forecast_ProductQty_V',TO_TIMESTAMP('2026-08-13 14:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Repoint 'Sprung zu Prognose'. Type and Classname are both set because the interceptor that derives one
-- from the other only runs for ORM writes, not for migration SQL.
--
-- Value is set back to its long-standing QtyDemand_QtySupply_V_to_Forecast: it describes what the process
-- does (cockpit row -> forecast) rather than a class name, which is what it needs to be now that the
-- process has no bespoke class at all. Writing it explicitly also converges a database on which an earlier,
-- since-withdrawn iteration of this branch had already renamed it.
UPDATE AD_Process
SET Type='RelationTypeInOverlay',
    Classname='de.metas.ui.web.view.process.RelationTypeInOverlayProcess',
    Value='QtyDemand_QtySupply_V_to_Forecast',
    AD_RelationType_ID=540503,
    Updated=TO_TIMESTAMP('2026-08-13 14:00:07','YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_ID=585515
;
