-- 2023-06-08T16:29:02.381Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582402,0,'IsDunningContact',TO_TIMESTAMP('2023-06-08 19:29:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Dunning contact','Dunning contact',TO_TIMESTAMP('2023-06-08 19:29:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-08T16:29:02.401Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582402 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsDunningContact
-- 2023-06-08T16:29:20.655Z
UPDATE AD_Element_Trl SET Name='Mahnung kontakt', PrintName='Mahnung kontakt',Updated=TO_TIMESTAMP('2023-06-08 19:29:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582402 AND AD_Language='de_CH'
;

-- 2023-06-08T16:29:20.703Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582402,'de_CH') 
;

-- Element: IsDunningContact
-- 2023-06-08T16:29:35.569Z
UPDATE AD_Element_Trl SET Name='Mahnung kontakt', PrintName='Mahnung kontakt',Updated=TO_TIMESTAMP('2023-06-08 19:29:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582402 AND AD_Language='de_DE'
;

-- 2023-06-08T16:29:35.571Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582402,'de_DE') 
;

-- 2023-06-08T16:29:35.578Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582402,'de_DE') 
;

-- Column: AD_User.IsDunningContact
-- Column: AD_User.IsDunningContact
-- 2023-06-08T16:35:54.406Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586757,582402,0,20,114,'IsDunningContact',TO_TIMESTAMP('2023-06-08 19:35:54','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Mahnung kontakt',0,0,TO_TIMESTAMP('2023-06-08 19:35:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-08T16:35:54.413Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586757 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T16:35:54.428Z
/* DDL */  select update_Column_Translation_From_AD_Element(582402) 
;

-- 2023-06-08T16:35:56.870Z
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN IsDunningContact CHAR(1) DEFAULT ''N'' CHECK (IsDunningContact IN (''Y'',''N''))')
;

-- Field: Geschäftspartner_OLD -> Nutzer / Kontakt -> Mahnung kontakt
-- Column: AD_User.IsDunningContact
-- Field: Geschäftspartner_OLD(123,D) -> Nutzer / Kontakt(496,D) -> Mahnung kontakt
-- Column: AD_User.IsDunningContact
-- 2023-06-08T16:37:07.371Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586757,716310,0,496,0,TO_TIMESTAMP('2023-06-08 19:37:07','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Mahnung kontakt',0,320,0,1,1,TO_TIMESTAMP('2023-06-08 19:37:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-08T16:37:07.378Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716310 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-08T16:37:07.385Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582402) 
;

-- 2023-06-08T16:37:07.405Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716310
;

-- 2023-06-08T16:37:07.415Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716310)
;

-- UI Element: Geschäftspartner_OLD -> Nutzer / Kontakt.Mahnung kontakt
-- Column: AD_User.IsDunningContact
-- UI Element: Geschäftspartner_OLD(123,D) -> Nutzer / Kontakt(496,D) -> main -> 10 -> main.Mahnung kontakt
-- Column: AD_User.IsDunningContact
-- 2023-06-08T16:38:42.090Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716310,0,496,617961,540578,'F',TO_TIMESTAMP('2023-06-08 19:38:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mahnung kontakt',260,0,0,TO_TIMESTAMP('2023-06-08 19:38:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner_OLD -> Nutzer / Kontakt.IsSubjectMatterContact
-- Column: AD_User.IsSubjectMatterContact
-- UI Element: Geschäftspartner_OLD(123,D) -> Nutzer / Kontakt(496,D) -> main -> 10 -> main.IsSubjectMatterContact
-- Column: AD_User.IsSubjectMatterContact
-- 2023-06-08T16:39:49.351Z
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2023-06-08 19:39:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=559997
;

-- UI Element: Geschäftspartner_OLD -> Nutzer / Kontakt.Mahnung kontakt
-- Column: AD_User.IsDunningContact
-- UI Element: Geschäftspartner_OLD(123,D) -> Nutzer / Kontakt(496,D) -> main -> 10 -> main.Mahnung kontakt
-- Column: AD_User.IsDunningContact
-- 2023-06-08T16:40:01.785Z
UPDATE AD_UI_Element SET SeqNo=145,Updated=TO_TIMESTAMP('2023-06-08 19:40:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617961
;

CREATE UNIQUE INDEX c_bpartner_unique_dunning
    ON ad_user (c_bpartner_id, isdunningcontact)
    WHERE ((c_bpartner_id IS NOT NULL) AND (isdunningcontact = 'Y'::bpchar) AND (isactive = 'Y'::bpchar))
;