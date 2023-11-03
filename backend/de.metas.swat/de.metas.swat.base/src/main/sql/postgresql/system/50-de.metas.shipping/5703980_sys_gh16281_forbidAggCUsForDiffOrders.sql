-- 2023-09-25T15:55:02.008Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582722,0,'IsForbidAggCUsForDifferentOrders',TO_TIMESTAMP('2023-09-25 18:55:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Forbid Aggregation of CUs for diff. orders','Forbid Aggregation of CUs for diff. orders',TO_TIMESTAMP('2023-09-25 18:55:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-09-25T15:55:02.030Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582722 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Picking_Config.IsForbidAggCUsForDifferentOrders
-- 2023-09-25T15:55:27.978Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587483,582722,0,20,540873,'IsForbidAggCUsForDifferentOrders',TO_TIMESTAMP('2023-09-25 18:55:27','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.picking',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Forbid Aggregation of CUs for diff. orders',0,0,TO_TIMESTAMP('2023-09-25 18:55:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-09-25T15:55:27.986Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587483 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-25T15:55:28.070Z
/* DDL */  select update_Column_Translation_From_AD_Element(582722) 
;

-- 2023-09-25T15:55:30.476Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Config','ALTER TABLE public.M_Picking_Config ADD COLUMN IsForbidAggCUsForDifferentOrders CHAR(1) DEFAULT ''N'' CHECK (IsForbidAggCUsForDifferentOrders IN (''Y'',''N'')) NOT NULL')
;

-- Value: WEBUI_Picking_AggregatingCUsToDiffOrdersIsForbidden
-- 2023-09-25T15:57:10.231Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545341,0,TO_TIMESTAMP('2023-09-25 18:57:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.picking','Y','Aggregating CUs to TUs picked for different orders is not allowed','I',TO_TIMESTAMP('2023-09-25 18:57:10','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Picking_AggregatingCUsToDiffOrdersIsForbidden')
;

-- 2023-09-25T15:57:10.243Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545341 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: WEBUI_Picking_AggregatingCUsToDiffOrdersIsForbidden
-- 2023-09-25T15:58:30.529Z
UPDATE AD_Message_Trl SET MsgText='Das Zusammenfassen von CUs zu TUs, die für verschiedene Aufträge kommissioniert wurden, ist nicht erlaubt.',Updated=TO_TIMESTAMP('2023-09-25 18:58:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545341
;

-- Value: WEBUI_Picking_AggregatingCUsToDiffOrdersIsForbidden
-- 2023-09-25T15:58:36.385Z
UPDATE AD_Message_Trl SET MsgText='Das Zusammenfassen von CUs zu TUs, die für verschiedene Aufträge kommissioniert wurden, ist nicht erlaubt.',Updated=TO_TIMESTAMP('2023-09-25 18:58:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545341
;

-- Value: WEBUI_Picking_AggregatingCUsToDiffOrdersIsForbidden
-- 2023-09-25T15:58:39.551Z
UPDATE AD_Message_Trl SET MsgText='Das Zusammenfassen von CUs zu TUs, die für verschiedene Aufträge kommissioniert wurden, ist nicht erlaubt.',Updated=TO_TIMESTAMP('2023-09-25 18:58:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545341
;

-- Element: IsForbidAggCUsForDifferentOrders
-- 2023-09-25T15:59:36.269Z
UPDATE AD_Element_Trl SET Name='Verbot der Zusammenlegung von CUs für verschiedene Aufträge', PrintName='Verbot der Zusammenlegung von CUs für verschiedene Aufträge',Updated=TO_TIMESTAMP('2023-09-25 18:59:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582722 AND AD_Language='de_CH'
;

-- 2023-09-25T15:59:36.278Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582722,'de_CH') 
;

-- Element: IsForbidAggCUsForDifferentOrders
-- 2023-09-25T15:59:39.422Z
UPDATE AD_Element_Trl SET Name='Verbot der Zusammenlegung von CUs für verschiedene Aufträge', PrintName='Verbot der Zusammenlegung von CUs für verschiedene Aufträge',Updated=TO_TIMESTAMP('2023-09-25 18:59:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582722 AND AD_Language='de_DE'
;

-- 2023-09-25T15:59:39.422Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582722,'de_DE') 
;

-- 2023-09-25T15:59:39.446Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582722,'de_DE') 
;

-- 2023-09-25T15:59:39.456Z
UPDATE AD_Column SET ColumnName='IsForbidAggCUsForDifferentOrders', Name='Verbot der Zusammenlegung von CUs für verschiedene Aufträge', Description=NULL, Help=NULL WHERE AD_Element_ID=582722
;

-- 2023-09-25T15:59:39.457Z
UPDATE AD_Process_Para SET ColumnName='IsForbidAggCUsForDifferentOrders', Name='Verbot der Zusammenlegung von CUs für verschiedene Aufträge', Description=NULL, Help=NULL, AD_Element_ID=582722 WHERE UPPER(ColumnName)='ISFORBIDAGGCUSFORDIFFERENTORDERS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-09-25T15:59:39.463Z
UPDATE AD_Process_Para SET ColumnName='IsForbidAggCUsForDifferentOrders', Name='Verbot der Zusammenlegung von CUs für verschiedene Aufträge', Description=NULL, Help=NULL WHERE AD_Element_ID=582722 AND IsCentrallyMaintained='Y'
;

-- 2023-09-25T15:59:39.464Z
UPDATE AD_Field SET Name='Verbot der Zusammenlegung von CUs für verschiedene Aufträge', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582722) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582722)
;

-- 2023-09-25T15:59:39.513Z
UPDATE AD_PrintFormatItem pi SET PrintName='Verbot der Zusammenlegung von CUs für verschiedene Aufträge', Name='Verbot der Zusammenlegung von CUs für verschiedene Aufträge' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=582722)
;

-- 2023-09-25T15:59:39.514Z
UPDATE AD_Tab SET Name='Verbot der Zusammenlegung von CUs für verschiedene Aufträge', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582722
;

-- 2023-09-25T15:59:39.515Z
UPDATE AD_WINDOW SET Name='Verbot der Zusammenlegung von CUs für verschiedene Aufträge', Description=NULL, Help=NULL WHERE AD_Element_ID = 582722
;

-- 2023-09-25T15:59:39.516Z
UPDATE AD_Menu SET   Name = 'Verbot der Zusammenlegung von CUs für verschiedene Aufträge', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582722
;

-- Element: IsForbidAggCUsForDifferentOrders
-- 2023-09-25T15:59:48.873Z
UPDATE AD_Element_Trl SET Name='Verbot der Zusammenlegung von CUs für verschiedene Aufträge', PrintName='Verbot der Zusammenlegung von CUs für verschiedene Aufträge',Updated=TO_TIMESTAMP('2023-09-25 18:59:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582722 AND AD_Language='nl_NL'
;

-- 2023-09-25T15:59:48.874Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582722,'nl_NL') 
;

-- Field: Kommissionierung Profil(540382,de.metas.picking) -> Kommissionierung Profil(540902,de.metas.picking) -> Verbot der Zusammenlegung von CUs für verschiedene Aufträge
-- Column: M_Picking_Config.IsForbidAggCUsForDifferentOrders
-- 2023-09-26T05:09:36.062Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587483,720679,0,540902,TO_TIMESTAMP('2023-09-26 08:09:35','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.picking','Y','N','N','N','N','N','N','N','Verbot der Zusammenlegung von CUs für verschiedene Aufträge',TO_TIMESTAMP('2023-09-26 08:09:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-09-26T05:09:36.071Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=720679 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-26T05:09:36.088Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582722)
;

-- 2023-09-26T05:09:36.117Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720679
;

-- 2023-09-26T05:09:36.129Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720679)
;

-- UI Element: Kommissionierung Profil(540382,de.metas.picking) -> Kommissionierung Profil(540902,de.metas.picking) -> main -> 20 -> flags.Verbot der Zusammenlegung von CUs für verschiedene Aufträge
-- Column: M_Picking_Config.IsForbidAggCUsForDifferentOrders
-- 2023-09-26T05:10:14.603Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,720679,0,540902,620594,541292,'F',TO_TIMESTAMP('2023-09-26 08:10:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Verbot der Zusammenlegung von CUs für verschiedene Aufträge',40,0,0,TO_TIMESTAMP('2023-09-26 08:10:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Kommissionierung Profil(540382,de.metas.picking) -> Kommissionierung Profil(540902,de.metas.picking) -> main -> 20 -> flags.Verbot der Zusammenlegung von CUs für verschiedene Aufträge
-- Column: M_Picking_Config.IsForbidAggCUsForDifferentOrders
-- 2023-09-26T05:10:23.584Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-09-26 08:10:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=620594
;

-- UI Element: Kommissionierung Profil(540382,de.metas.picking) -> Kommissionierung Profil(540902,de.metas.picking) -> main -> 20 -> org.Sektion
-- Column: M_Picking_Config.AD_Org_ID
-- 2023-09-26T05:10:23.595Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-09-26 08:10:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549591
;

-- UI Element: Kommissionierung Profil(540382,de.metas.picking) -> Kommissionierung Profil(540902,de.metas.picking) -> main -> 20 -> org.Mandant
-- Column: M_Picking_Config.AD_Client_ID
-- 2023-09-26T05:10:23.601Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-09-26 08:10:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549590
;

