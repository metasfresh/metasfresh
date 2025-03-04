-- 2023-10-25T07:16:47.582Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582779,0,'IsCatchWeight',TO_TIMESTAMP('2023-10-25 10:16:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Catch Weight','Catch Weight',TO_TIMESTAMP('2023-10-25 10:16:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-25T07:16:47.584Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582779 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsCatchWeight
-- 2023-10-25T07:16:53.590Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-25 10:16:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582779 AND AD_Language='de_CH'
;

-- 2023-10-25T07:16:53.593Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582779,'de_CH') 
;

-- Element: IsCatchWeight
-- 2023-10-25T07:16:54.992Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-25 10:16:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582779 AND AD_Language='en_US'
;

-- 2023-10-25T07:16:54.998Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582779,'en_US') 
;

-- Element: IsCatchWeight
-- 2023-10-25T07:16:56.249Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-25 10:16:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582779 AND AD_Language='de_DE'
;

-- 2023-10-25T07:16:56.252Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582779,'de_DE') 
;

-- 2023-10-25T07:16:56.253Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582779,'de_DE') 
;

-- Column: M_ShipmentSchedule.IsCatchWeight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- 2023-10-25T07:18:01.769Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587606,582779,0,20,500221,'IsCatchWeight',TO_TIMESTAMP('2023-10-25 10:18:01','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.inoutcandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Catch Weight',0,0,TO_TIMESTAMP('2023-10-25 10:18:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-25T07:18:01.771Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587606 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-25T07:18:01.774Z
/* DDL */  select update_Column_Translation_From_AD_Element(582779) 
;

-- 2023-10-25T07:18:06.485Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN IsCatchWeight CHAR(1) DEFAULT ''N'' CHECK (IsCatchWeight IN (''Y'',''N'')) NOT NULL')
;

-- Field: Lieferdisposition -> Auslieferplan -> Catch Weight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Catch Weight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- 2023-10-25T07:19:16.799Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587606,721723,0,500221,TO_TIMESTAMP('2023-10-25 10:19:16','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Catch Weight',TO_TIMESTAMP('2023-10-25 10:19:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-25T07:19:16.801Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721723 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-25T07:19:16.803Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582779) 
;

-- 2023-10-25T07:19:16.817Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721723
;

-- 2023-10-25T07:19:16.818Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721723)
;

-- Field: Lieferdisposition -> Auslieferplan -> Catch Weight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Catch Weight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- 2023-10-25T07:19:30.737Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-10-25 10:19:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721723
;

-- UI Element: Lieferdisposition -> Auslieferplan.Catch Weight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> catch.Catch Weight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- 2023-10-25T08:03:12.670Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721723,0,500221,542709,621167,'F',TO_TIMESTAMP('2023-10-25 11:03:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Catch Weight',30,0,0,TO_TIMESTAMP('2023-10-25 11:03:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferdisposition -> Auslieferplan.Catch Weight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> main -> 20 -> catch.Catch Weight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- 2023-10-25T08:03:34.076Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2023-10-25 11:03:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621167
;

-- Column: M_ShipmentSchedule.IsCatchWeight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- 2023-10-25T08:04:02.441Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-10-25 11:04:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587606
;

-- Field: Lieferdisposition -> Auslieferplan -> Abw. Catch Weight Menge
-- Column: M_ShipmentSchedule.QtyToDeliverCatch_Override
-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Abw. Catch Weight Menge
-- Column: M_ShipmentSchedule.QtyToDeliverCatch_Override
-- 2023-10-25T08:04:38.851Z
UPDATE AD_Field SET DisplayLogic='@IsCatchWeight/N@=Y & @Catch_UOM_ID/0@>0',Updated=TO_TIMESTAMP('2023-10-25 11:04:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582462
;

-- Field: Lieferdisposition -> Auslieferplan -> Catch Einheit
-- Column: M_ShipmentSchedule.Catch_UOM_ID
-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Catch Einheit
-- Column: M_ShipmentSchedule.Catch_UOM_ID
-- 2023-10-25T08:05:13.613Z
UPDATE AD_Field SET DisplayLogic='@IsCatchWeight/N@=Y & @Catch_UOM_ID/0@>0',Updated=TO_TIMESTAMP('2023-10-25 11:05:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582461
;

-- Field: Lieferdisposition -> Auslieferplan -> Catch Weight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Catch Weight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- 2023-10-25T08:05:46.548Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=575,Updated=TO_TIMESTAMP('2023-10-25 11:05:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721723
;

-- Field: Lieferdisposition -> Auslieferplan -> Catch Weight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Catch Weight
-- Column: M_ShipmentSchedule.IsCatchWeight
-- 2023-10-25T08:06:00.924Z
UPDATE AD_Field SET DisplayLogic='@IsCatchWeight/N@=Y',Updated=TO_TIMESTAMP('2023-10-25 11:06:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721723
;

