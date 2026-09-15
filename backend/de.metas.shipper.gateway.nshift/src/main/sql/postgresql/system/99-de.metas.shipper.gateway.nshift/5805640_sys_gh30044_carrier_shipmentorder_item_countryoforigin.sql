-- nShift: add CountryOfOrigin column to Carrier_ShipmentOrder_Item
-- Reuses existing AD_Element 584091 (CountryOfOrigin, "Herkunftsland")

-- IDs allocated from idserver.metas.de on 2026-06-01:
--   AD_Column      592669  (Carrier_ShipmentOrder_Item.CountryOfOrigin)
--   AD_Field       780646  (Paketposition tab, seqNo=100, after Zolltarifnummer)
--   AD_UI_Element  651946  (main group 553610, seqNo=100)

-- DDL: add column
ALTER TABLE carrier_shipmentorder_item ADD COLUMN IF NOT EXISTS CountryOfOrigin CHAR(2);

-- AD_Column
INSERT INTO AD_Column (AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Element_ID,AD_Reference_ID,AD_Table_ID,
                       ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,
                       EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,
                       IsExcludeFromZoomTargets,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,
                       IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,PersonalDataCategory,
                       Name,Updated,UpdatedBy,Version)
VALUES (0,0,592669 /*From ID Server*/,584091,10,542536,
        'CountryOfOrigin',TO_TIMESTAMP('2026-06-01 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'N',NULL,
        'D',2,'Y','Y','N','N',
        'Y','N','N','N','N',
        'N','N','N','Y','NP',
        'Herkunftsland',TO_TIMESTAMP('2026-06-01 10:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0);

-- AD_Column_Trl skeleton
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Column_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592669
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

-- AD_Field (tab 548459 = Paketposition, last content field before system section)
INSERT INTO AD_Field (AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Column_ID,AD_Tab_ID,
                      Created,CreatedBy,DisplayLength,EntityType,
                      IsActive,IsDisplayed,Name,Updated,UpdatedBy)
VALUES (0,0,780646 /*From ID Server*/,592669,548459,
        TO_TIMESTAMP('2026-06-01 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,2,'D',
        'Y','Y','Herkunftsland',TO_TIMESTAMP('2026-06-01 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100);

-- AD_Field_Trl skeleton + sync name from AD_Element + element link
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Field_ID,t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780646
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT update_FieldTranslation_From_AD_Name_Element(584091);
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780646;
SELECT AD_Element_Link_Create_Missing_Field(780646);

-- AD_UI_Element (group 553610 = main, seqNo=100, after Zolltarifnummer at seqNo=90)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_UI_Element_ID,AD_Field_ID,AD_UI_ElementGroup_ID,AD_Tab_ID,
                           Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,
                           Name,SeqNo,Updated,UpdatedBy,WidgetSize)
VALUES (0,0,651946 /*From ID Server*/,780646,553610,548459,
        TO_TIMESTAMP('2026-06-01 10:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y',
        'Herkunftsland',100,TO_TIMESTAMP('2026-06-01 10:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'S');

-- Propagate element translations to column and field
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584091);
