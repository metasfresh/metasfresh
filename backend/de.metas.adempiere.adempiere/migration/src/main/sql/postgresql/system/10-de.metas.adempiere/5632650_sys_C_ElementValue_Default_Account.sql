-- Column: C_ElementValue.Default_Account
-- 2022-03-30T21:49:19.774403Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582648,1908,0,10,188,'Default_Account',TO_TIMESTAMP('2022-03-31 00:49:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnung des Standard-Kontos','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standard-Konto',0,0,TO_TIMESTAMP('2022-03-31 00:49:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-03-30T21:49:19.787532300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582648 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-03-30T21:49:19.842734500Z
/* DDL */  select update_Column_Translation_From_AD_Element(1908) 
;

-- 2022-03-30T21:49:22.099552800Z
/* DDL */ SELECT public.db_alter_table('C_ElementValue','ALTER TABLE public.C_ElementValue ADD COLUMN Default_Account VARCHAR(255)')
;

-- Column: I_ElementValue.Default_Account
-- 2022-03-30T21:49:38.314708Z
UPDATE AD_Column SET FieldLength=255,Updated=TO_TIMESTAMP('2022-03-31 00:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=7918
;

-- 2022-03-30T21:49:39.315662100Z
INSERT INTO t_alter_column values('i_elementvalue','Default_Account','VARCHAR(255)',null,null)
;

-- Field: Konten -> Kontenart -> Standard-Konto
-- Column: C_ElementValue.Default_Account
-- 2022-03-30T21:53:04.674296500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582648,691577,0,542127,TO_TIMESTAMP('2022-03-31 00:53:04','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung des Standard-Kontos',255,'D','Y','N','N','N','N','N','N','N','Standard-Konto',TO_TIMESTAMP('2022-03-31 00:53:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-30T21:53:04.690344900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691577 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-03-30T21:53:04.730575800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1908) 
;

-- 2022-03-30T21:53:04.760258Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691577
;

-- 2022-03-30T21:53:04.803486500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691577)
;

-- UI Element: Konten -> Kontenart.Standard-Konto
-- Column: C_ElementValue.Default_Account
-- 2022-03-30T21:54:52.681016200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,691577,0,542127,605281,543190,'F',TO_TIMESTAMP('2022-03-31 00:54:52','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung des Standard-Kontos','Y','N','Y','N','N','Standard-Konto',80,0,0,TO_TIMESTAMP('2022-03-31 00:54:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Konten -> Kontenart.Standard-Konto
-- Column: C_ElementValue.Default_Account
-- 2022-03-30T22:00:05.207723700Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-03-31 01:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605281
;

