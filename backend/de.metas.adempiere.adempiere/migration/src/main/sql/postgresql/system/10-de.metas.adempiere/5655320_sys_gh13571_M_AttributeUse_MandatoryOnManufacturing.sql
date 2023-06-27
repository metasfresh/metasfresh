-- 2022-09-08T11:39:00.909Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581432,0,'MandatoryOnManufacturing',TO_TIMESTAMP('2022-09-08 14:38:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Erforderlich für Produktionsauftrag','Erforderlich für Produktionsauftrag',TO_TIMESTAMP('2022-09-08 14:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-08T11:39:00.947Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581432 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-09-08T11:39:36.600Z
UPDATE AD_Element_Trl SET Name='Mandatory for Manufacturing', PrintName='Mandatory for Manufacturing',Updated=TO_TIMESTAMP('2022-09-08 14:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581432 AND AD_Language='en_US'
;

-- 2022-09-08T11:39:36.662Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581432,'en_US') 
;

-- Column: M_AttributeUse.MandatoryOnManufacturing
-- 2022-09-08T11:40:19.971Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584274,581432,0,17,319,563,'MandatoryOnManufacturing',TO_TIMESTAMP('2022-09-08 14:40:19','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,200,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erforderlich für Produktionsauftrag',0,0,TO_TIMESTAMP('2022-09-08 14:40:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-08T11:40:20.009Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584274 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-08T11:40:20.107Z
/* DDL */  select update_Column_Translation_From_AD_Element(581432) 
;

-- Column: M_AttributeUse.MandatoryOnManufacturing
-- 2022-09-08T11:40:47.990Z
UPDATE AD_Column SET FieldLength=1,Updated=TO_TIMESTAMP('2022-09-08 14:40:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584274
;

-- 2022-09-08T11:40:55.408Z
/* DDL */ SELECT public.db_alter_table('M_AttributeUse','ALTER TABLE public.M_AttributeUse ADD COLUMN MandatoryOnManufacturing CHAR(1)')
;

-- Field: Merkmals-Satz -> Merkmals-Verwendung -> Erforderlich für Produktionsauftrag
-- Column: M_AttributeUse.MandatoryOnManufacturing
-- 2022-09-08T12:41:21.835Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584274,707130,0,467,TO_TIMESTAMP('2022-09-08 15:41:21','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Erforderlich für Produktionsauftrag',TO_TIMESTAMP('2022-09-08 15:41:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-08T12:41:21.872Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707130 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-08T12:41:21.909Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581432) 
;

-- 2022-09-08T12:41:21.958Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707130
;

-- 2022-09-08T12:41:21.998Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707130)
;

-- Field: Merkmals-Satz -> Merkmals-Verwendung -> Erforderlich für Produktionsauftrag
-- Column: M_AttributeUse.MandatoryOnManufacturing
-- 2022-09-08T12:42:15.812Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-09-08 15:42:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707130
;

-- UI Element: Merkmals-Satz -> Merkmals-Verwendung.Erforderlich für Produktionsauftrag
-- Column: M_AttributeUse.MandatoryOnManufacturing
-- 2022-09-08T12:43:25.945Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707130,0,467,540488,612943,'F',TO_TIMESTAMP('2022-09-08 15:43:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Erforderlich für Produktionsauftrag',65,0,0,TO_TIMESTAMP('2022-09-08 15:43:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Merkmals-Satz -> Merkmals-Verwendung.Erforderlich für Produktionsauftrag
-- Column: M_AttributeUse.MandatoryOnManufacturing
-- 2022-09-08T12:43:35.808Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-09-08 15:43:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612943
;

-- UI Element: Merkmals-Satz -> Merkmals-Verwendung.Sektion
-- Column: M_AttributeUse.AD_Org_ID
-- 2022-09-08T12:43:36.049Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-09-08 15:43:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544733
;

-- 2022-09-08T14:21:43.703Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545155,0,TO_TIMESTAMP('2022-09-08 17:21:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Das Merkmal "{0}" ist zwingend erforderlich bei Produktionsauftrag des Produktes "{1}".','E',TO_TIMESTAMP('2022-09-08 17:21:43','YYYY-MM-DD HH24:MI:SS'),100,'M_AttributeUse_MandatoryOnManufacturing')
;

-- 2022-09-08T14:21:43.850Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545155 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2022-09-08T14:22:05.694Z
UPDATE AD_Message_Trl SET MsgText='The attribute {0}  is mandatory on manufacturing for the product {1}.',Updated=TO_TIMESTAMP('2022-09-08 17:22:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545155
;



-- 2022-09-08T14:55:38.636Z
UPDATE AD_Message_Trl SET MsgText='The attribute "{0}" is mandatory in the manufacturing of the product "{1}".',Updated=TO_TIMESTAMP('2022-09-08 17:55:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545155
;

-- 2022-09-08T14:56:14.804Z
UPDATE AD_Message SET MsgText='Das Merkmal "{0}" ist zwingend erforderlich bei der Herstellung des Produktes "{1}".',Updated=TO_TIMESTAMP('2022-09-08 17:56:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545155
;

-- 2022-09-08T14:56:24.479Z
UPDATE AD_Message_Trl SET MsgText='Das Merkmal "{0}" ist zwingend erforderlich bei der Herstellung des Produktes "{1}".',Updated=TO_TIMESTAMP('2022-09-08 17:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545155
;

-- 2022-09-08T14:56:28.766Z
UPDATE AD_Message_Trl SET MsgText='Das Merkmal "{0}" ist zwingend erforderlich bei der Herstellung des Produktes "{1}".',Updated=TO_TIMESTAMP('2022-09-08 17:56:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545155
;

-- 2022-09-08T14:56:33.607Z
UPDATE AD_Message_Trl SET MsgText='Das Merkmal "{0}" ist zwingend erforderlich bei der Herstellung des Produktes "{1}".',Updated=TO_TIMESTAMP('2022-09-08 17:56:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545155
;




