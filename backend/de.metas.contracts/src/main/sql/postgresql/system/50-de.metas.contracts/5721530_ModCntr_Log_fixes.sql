-- Run mode: SWING_CLIENT

-- Field: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> Produktname
-- Column: ModCntr_Log.ProductName
-- 2024-04-10T09:03:32.946Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588088,727822,0,547012,0,TO_TIMESTAMP('2024-04-10 12:03:32.656','YYYY-MM-DD HH24:MI:SS.US'),100,'Name des Produktes',0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Produktname',0,10,0,1,1,TO_TIMESTAMP('2024-04-10 12:03:32.656','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-10T09:03:32.957Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727822 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-10T09:03:32.990Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2659)
;

-- 2024-04-10T09:03:33.006Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727822
;

-- 2024-04-10T09:03:33.008Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727822)
;

-- Element: InvoicingGroup
-- 2024-04-12T07:20:35.846Z
UPDATE AD_Element_Trl SET Name='Invoice Line Group', PrintName='Invoice Line Group',Updated=TO_TIMESTAMP('2024-04-12 10:20:35.846','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582427 AND AD_Language='en_US'
;

-- 2024-04-12T07:20:35.887Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582427,'en_US')
;

-- Element: InvoicingGroup
-- 2024-04-12T07:20:38.094Z
UPDATE AD_Element_Trl SET Name='Abrechnungszeilengruppe', PrintName='Abrechnungszeilengruppe',Updated=TO_TIMESTAMP('2024-04-12 10:20:38.094','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582427 AND AD_Language='de_CH'
;

-- 2024-04-12T07:20:38.095Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582427,'de_CH')
;

-- Element: InvoicingGroup
-- 2024-04-12T07:20:39.841Z
UPDATE AD_Element_Trl SET Name='Abrechnungszeilengruppe', PrintName='Abrechnungszeilengruppe',Updated=TO_TIMESTAMP('2024-04-12 10:20:39.841','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582427 AND AD_Language='de_DE'
;

-- 2024-04-12T07:20:39.842Z
UPDATE AD_Element SET Name='Abrechnungszeilengruppe', PrintName='Abrechnungszeilengruppe' WHERE AD_Element_ID=582427
;

-- 2024-04-12T07:20:40.102Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582427,'de_DE')
;

-- 2024-04-12T07:20:40.103Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582427,'de_DE')
;

-- Element: InvoicingGroup
-- 2024-04-12T07:20:41.815Z
UPDATE AD_Element_Trl SET Name='Abrechnungszeilengruppe', PrintName='Abrechnungszeilengruppe',Updated=TO_TIMESTAMP('2024-04-12 10:20:41.815','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582427 AND AD_Language='fr_CH'
;

-- 2024-04-12T07:20:41.817Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582427,'fr_CH')
;

-- Element: InvoicingGroup
-- 2024-04-12T07:20:47.059Z
UPDATE AD_Element_Trl SET Name='Abrechnungszeilengruppe', PrintName='Abrechnungszeilengruppe',Updated=TO_TIMESTAMP('2024-04-12 10:20:47.059','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582427 AND AD_Language='it_IT'
;

-- 2024-04-12T07:20:47.061Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582427,'it_IT')
;

-- UI Element: Log für modulare Verträge(541711,de.metas.contracts) -> Contract Module Log(547012,de.metas.contracts) -> main -> 20 -> numbers.Produktname
-- Column: ModCntr_Log.ProductName
-- 2024-04-12T10:37:24.189Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727822,0,547012,550801,624518,'F',TO_TIMESTAMP('2024-04-12 13:37:24.005','YYYY-MM-DD HH24:MI:SS.US'),100,'Name des Produktes','Y','N','N','Y','N','N','N',0,'Produktname',5,0,0,TO_TIMESTAMP('2024-04-12 13:37:24.005','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: ModCntr_Log.ProductName
-- 2024-04-12T10:55:27.440Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-12 13:55:27.44','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588088
;

UPDATE modcntr_log l
SET productName = '-'
FROM m_product p
;

UPDATE modcntr_log l
SET productName = name
FROM m_product p
WHERE l.m_product_id = p.m_product_id
;

UPDATE modcntr_log l
SET productName= name
FROM modcntr_module m
WHERE l.modcntr_module_id = m.modcntr_module_id
;

COMMIT;

-- 2024-04-12T11:02:15.177Z
INSERT INTO t_alter_column values('modcntr_log','ProductName','VARCHAR(255)',null,null)
;

-- 2024-04-12T11:02:15.180Z
INSERT INTO t_alter_column values('modcntr_log','ProductName',null,'NOT NULL',null)
;

-- Column: ModCntr_Module.ModCntr_Settings_ID
-- 2024-04-12T11:28:49.135Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=541866, IsExcludeFromZoomTargets='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-04-12 14:28:49.135','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586804
;

-- 2024-04-12T11:33:46.745Z
INSERT INTO t_alter_column values('modcntr_module','ModCntr_Settings_ID','NUMERIC(10)',null,null)
;

