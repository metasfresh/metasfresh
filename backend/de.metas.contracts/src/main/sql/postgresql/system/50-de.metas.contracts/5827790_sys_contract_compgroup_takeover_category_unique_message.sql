-- Declares the existing partial unique index c_compgroup_contractsettings_takeover_category_active_uq
-- (one active take-over record per product category and compensation-group contract settings, created by 5827430)
-- in AD_Index_Table, so that a violation shows a translated, user-friendly message instead of the raw DB error.
-- The physical index already exists; its name is reused unchanged (AD_Index_Table.Name must equal the index name).
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540874 /*From ID Server*/,0,542652,TO_TIMESTAMP('2026-10-05 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Eine aktive Übernahme-Zeile pro Produktkategorie und Kompensationsgruppen-Vertragseinstellung.','de.metas.contracts','Für diese Produktkategorie gibt es in diesen Einstellungen bereits eine aktive Übernahme-Zeile.','Y','Y','c_compgroup_contractsettings_takeover_category_active_uq','N',TO_TIMESTAMP('2026-10-05 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID,ErrorMsg,Description,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Index_Table t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Index_Table_ID=540874
  AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

UPDATE AD_Index_Table_Trl SET ErrorMsg='There is already an active take-over line for this product category in these settings.', Description='One active take-over line per product category and compensation group contract settings.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-05 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Index_Table_ID=540874
;

-- de_DE/de_CH already carry the base (German) text as seeded above; just mark them as translated.
UPDATE AD_Index_Table_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-05 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Index_Table_ID=540874
;
UPDATE AD_Index_Table_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-05 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Index_Table_ID=540874
;

-- Index columns: C_CompensationGroup_ContractSettings_ID, M_Product_Category_ID (same order as the physical index)
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,593682,541547 /*From ID Server*/,540874,0,TO_TIMESTAMP('2026-10-05 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y',10,TO_TIMESTAMP('2026-10-05 10:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,593683,541548 /*From ID Server*/,540874,0,TO_TIMESTAMP('2026-10-05 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y',20,TO_TIMESTAMP('2026-10-05 10:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;
