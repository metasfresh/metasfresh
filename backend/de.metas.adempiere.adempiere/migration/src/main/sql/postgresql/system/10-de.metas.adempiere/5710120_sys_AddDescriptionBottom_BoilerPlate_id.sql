-- 2023-11-09T16:17:52.676135900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582798,0,'DescriptionBottom_BoilerPlate_ID',TO_TIMESTAMP('2023-11-09 18:17:52.459','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Description Bottom List','Description Bottom List',TO_TIMESTAMP('2023-11-09 18:17:52.459','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-09T16:17:52.695066400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582798 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: DescriptionBottom_BoilerPlate_ID
-- 2023-11-09T16:18:06.436617700Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Schlusstext Liste', PrintName='Schlusstext Liste',Updated=TO_TIMESTAMP('2023-11-09 18:18:06.436','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582798 AND AD_Language='de_CH'
;

-- 2023-11-09T16:18:06.460639500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582798,'de_CH') 
;

-- Element: DescriptionBottom_BoilerPlate_ID
-- 2023-11-09T16:18:11.547550100Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Schlusstext Liste', PrintName='Schlusstext Liste',Updated=TO_TIMESTAMP('2023-11-09 18:18:11.547','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582798 AND AD_Language='de_DE'
;

-- 2023-11-09T16:18:11.550237700Z
UPDATE AD_Element SET Name='Schlusstext Liste', PrintName='Schlusstext Liste' WHERE AD_Element_ID=582798
;

-- 2023-11-09T16:18:11.795441Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582798,'de_DE') 
;

-- 2023-11-09T16:18:11.797045700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582798,'de_DE') 
;

-- Column: C_Order.DescriptionBottom_BoilerPlate_ID
-- 2023-11-09T16:18:42.929830700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587632,582798,0,18,540008,259,'DescriptionBottom_BoilerPlate_ID',TO_TIMESTAMP('2023-11-09 18:18:42.794','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Schlusstext Liste',0,0,TO_TIMESTAMP('2023-11-09 18:18:42.794','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-11-09T16:18:42.932423600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587632 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-09T16:18:43.289149200Z
/* DDL */  select update_Column_Translation_From_AD_Element(582798) 
;

-- 2023-11-09T16:18:55.946821200Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN DescriptionBottom_BoilerPlate_ID NUMERIC(10)')
;

-- 2023-11-09T16:18:56.870034900Z
ALTER TABLE C_Order ADD CONSTRAINT DescriptionBottomBoilerPlate_COrder FOREIGN KEY (DescriptionBottom_BoilerPlate_ID) REFERENCES public.AD_BoilerPlate DEFERRABLE INITIALLY DEFERRED
;

-- Field: Auftrag(143,D) -> Auftrag(186,D) -> Schlusstext Liste
-- Column: C_Order.DescriptionBottom_BoilerPlate_ID
-- 2023-11-09T16:20:00.113247Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587632,721801,0,186,0,TO_TIMESTAMP('2023-11-09 18:19:59.949','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Schlusstext Liste',0,760,0,1,1,TO_TIMESTAMP('2023-11-09 18:19:59.949','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-09T16:20:00.115303800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721801 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-09T16:20:00.117375400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582798) 
;

-- 2023-11-09T16:20:00.126689900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721801
;

-- 2023-11-09T16:20:00.128239200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721801)
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> advanced edit -> 10 -> advanced edit.Schlusstext
-- Column: C_Order.DescriptionBottom
-- 2023-11-09T16:23:05.421835900Z
UPDATE AD_UI_Element SET SeqNo=8,Updated=TO_TIMESTAMP('2023-11-09 18:23:05.421','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=563600
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> advanced edit -> 10 -> advanced edit.Schlusstext Liste
-- Column: C_Order.DescriptionBottom_BoilerPlate_ID
-- 2023-11-09T16:23:22.633191400Z
UPDATE AD_UI_Element SET AD_Field_ID=721801, Name='Schlusstext Liste', SeqNo=6,Updated=TO_TIMESTAMP('2023-11-09 18:23:22.632','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=563600
;

-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Schlusstext Liste
-- Column: C_Order.DescriptionBottom_BoilerPlate_ID
-- 2023-11-09T16:24:16.428287500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587632,721802,0,294,0,TO_TIMESTAMP('2023-11-09 18:24:16.304','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Schlusstext Liste',0,220,0,1,1,TO_TIMESTAMP('2023-11-09 18:24:16.304','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-09T16:24:16.430374800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721802 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-09T16:24:16.432445800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582798) 
;

-- 2023-11-09T16:24:16.435539900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721802
;

-- 2023-11-09T16:24:16.436574200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721802)
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> advanced edit.Schlusstext
-- Column: C_Order.DescriptionBottom
-- 2023-11-09T16:24:47.252904100Z
UPDATE AD_UI_Element SET SeqNo=8,Updated=TO_TIMESTAMP('2023-11-09 18:24:47.252','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=552492
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> advanced edit.Schlusstext Liste
-- Column: C_Order.DescriptionBottom_BoilerPlate_ID
-- 2023-11-09T16:24:58.340756500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721802,0,294,540961,621252,'F',TO_TIMESTAMP('2023-11-09 18:24:58.223','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N','Schlusstext Liste',6,0,0,TO_TIMESTAMP('2023-11-09 18:24:58.223','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Element: DescriptionBottom_BoilerPlate_ID
-- 2023-11-09T16:49:27.835285100Z
UPDATE AD_Element_Trl SET Name='Auswahl Schlusstext', PrintName='Auswahl Schlusstext',Updated=TO_TIMESTAMP('2023-11-09 18:49:27.834','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582798 AND AD_Language='de_CH'
;

-- 2023-11-09T16:49:27.841092500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582798,'de_CH') 
;

-- Element: DescriptionBottom_BoilerPlate_ID
-- 2023-11-09T16:49:56.772592100Z
UPDATE AD_Element_Trl SET Name='Auswahl Schlusstext', PrintName='Auswahl Schlusstext',Updated=TO_TIMESTAMP('2023-11-09 18:49:56.772','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582798 AND AD_Language='de_DE'
;

-- 2023-11-09T16:49:56.774143700Z
UPDATE AD_Element SET Name='Auswahl Schlusstext', PrintName='Auswahl Schlusstext' WHERE AD_Element_ID=582798
;

-- 2023-11-09T16:49:57.001036700Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582798,'de_DE') 
;

-- 2023-11-09T16:49:57.002113800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582798,'de_DE') 
;

