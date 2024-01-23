-- Column: C_Project_WO_Resource.ResourceType
-- 2024-01-23T09:07:10.330694500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587843,582919,0,17,541846,542161,'ResourceType',TO_TIMESTAMP('2024-01-23 11:07:10.202','YYYY-MM-DD HH24:MI:SS.US'),100,'N','M','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Resource Type',0,0,TO_TIMESTAMP('2024-01-23 11:07:10.202','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-01-23T09:07:10.333821200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587843 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-23T09:07:10.848717Z
/* DDL */  select update_Column_Translation_From_AD_Element(582919) 
;

-- 2024-01-23T09:07:12.392151800Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource','ALTER TABLE public.C_Project_WO_Resource ADD COLUMN ResourceType CHAR(1) DEFAULT ''M'' NOT NULL')
;

-- Field: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> Resource Type
-- Column: C_Project_WO_Resource.ResourceType
-- 2024-01-23T12:45:41.361434900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587843,723841,0,546560,TO_TIMESTAMP('2024-01-23 14:45:41.01','YYYY-MM-DD HH24:MI:SS.US'),100,1,'de.metas.endcustomer.ip180','Y','N','N','N','N','N','N','N','Resource Type',TO_TIMESTAMP('2024-01-23 14:45:41.01','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-01-23T12:45:41.365096900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723841 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-23T12:45:41.367689800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582919) 
;

-- 2024-01-23T12:45:41.371822700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723841
;

-- 2024-01-23T12:45:41.373380600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723841)
;

-- UI Element: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> main -> 10 -> resource.Resource Type
-- Column: C_Project_WO_Resource.ResourceType
-- 2024-01-23T12:57:56.924420500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723841,0,546560,549767,622135,'F',TO_TIMESTAMP('2024-01-23 14:57:56.702','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Resource Type',30,0,0,TO_TIMESTAMP('2024-01-23 14:57:56.702','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> main -> 10 -> resource.Resource Type
-- Column: C_Project_WO_Resource.ResourceType
-- 2024-01-23T12:58:14.068866400Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2024-01-23 14:58:14.068','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622135
;

-- UI Element: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> main -> 10 -> resource.Resource Type
-- Column: C_Project_WO_Resource.ResourceType
-- 2024-01-23T12:58:36.155828600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-01-23 14:58:36.155','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622135
;

-- UI Element: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> main -> 10 -> dates & duration.Duration
-- Column: C_Project_WO_Resource.Duration
-- 2024-01-23T12:58:36.165814Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-01-23 14:58:36.165','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=611760
;

-- UI Element: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> main -> 10 -> dates & duration.Resolved hours
-- Column: C_Project_WO_Resource.ResolvedHours
-- 2024-01-23T12:58:36.174912200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-01-23 14:58:36.174','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=616098
;

-- UI Element: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> main -> 10 -> dates & duration.SOLL-MannStd
-- Column: C_Project_WO_Resource.WOPlannedPersonDurationHours
-- 2024-01-23T12:58:36.184383900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-01-23 14:58:36.183','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=612841
;

-- UI Element: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> main -> 10 -> dates & duration.Zuordnung von
-- Column: C_Project_WO_Resource.AssignDateFrom
-- 2024-01-23T12:58:36.192970400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-01-23 14:58:36.192','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=611756
;

-- UI Element: Prüfauftrag(541553,de.metas.endcustomer.ip180) -> Ressource(546560,de.metas.endcustomer.ip180) -> main -> 10 -> dates & duration.Zuordnung bis
-- Column: C_Project_WO_Resource.AssignDateTo
-- 2024-01-23T12:58:36.201388700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-01-23 14:58:36.201','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=611757
;

