-- 2022-01-27T16:21:02.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-01-27 17:21:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501627
;

-- 2022-01-27T16:24:06.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DefaultValue='@SQL=SELECT C_Incoterms_Vendor_ID FROM C_BPartner WHERE C_BPartner_ID=@C_BPartner_ID/-1@ AND isVendor =''Y''',Updated=TO_TIMESTAMP('2022-01-27 17:24:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501627
;

-- 2022-01-27T16:25:21.277Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580529,0,TO_TIMESTAMP('2022-01-27 17:25:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Incoterms Sadt','Incoterms Sadt',TO_TIMESTAMP('2022-01-27 17:25:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T16:25:21.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580529 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-01-27T16:26:21.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Incoterm City', PrintName='Incoterm City',Updated=TO_TIMESTAMP('2022-01-27 17:26:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580529 AND AD_Language='en_US'
;

-- 2022-01-27T16:26:21.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580529,'en_US') 
;

-- 2022-01-27T16:27:45.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=580529, Description=NULL, DisplayLogic='@C_Incoterms_ID@!null & @C_Incoterms_ID@!540001', Help=NULL, Name='Incoterms Sadt',Updated=TO_TIMESTAMP('2022-01-27 17:27:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501628
;

-- 2022-01-27T16:27:45.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580529) 
;

-- 2022-01-27T16:27:45.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=501628
;

-- 2022-01-27T16:27:45.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(501628)
;

-- 2022-01-27T16:28:10.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@C_Incoterms_ID@!null & @C_Incoterms_ID@!540001',Updated=TO_TIMESTAMP('2022-01-27 17:28:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=501614
;

-- 2022-01-27T16:31:22.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Stornieren',Updated=TO_TIMESTAMP('2022-01-27 17:31:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=182
;

-- 2022-01-27T16:31:27.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Stornieren',Updated=TO_TIMESTAMP('2022-01-27 17:31:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=182
;

-- 2022-01-27T16:31:29.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Stornieren',Updated=TO_TIMESTAMP('2022-01-27 17:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=182
;

-- 2022-01-27T16:31:30.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Stornieren',Updated=TO_TIMESTAMP('2022-01-27 17:31:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Ref_List_ID=182
;

-- 2022-01-27T16:31:31.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Stornieren',Updated=TO_TIMESTAMP('2022-01-27 17:31:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Ref_List_ID=182
;

-- 2022-01-27T16:31:34.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Stornieren',Updated=TO_TIMESTAMP('2022-01-27 17:31:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=182
;

-- 2022-01-27T16:32:08.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Stornieren',Updated=TO_TIMESTAMP('2022-01-27 17:32:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=182
;

-- 2022-01-27T16:34:01.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540056,547972,TO_TIMESTAMP('2022-01-27 17:34:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','incoterms',30,TO_TIMESTAMP('2022-01-27 17:34:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T16:34:24.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,501627,0,294,547972,600100,'F',TO_TIMESTAMP('2022-01-27 17:34:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Incoterms',10,0,0,TO_TIMESTAMP('2022-01-27 17:34:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T16:34:34.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,501628,0,294,547972,600101,'F',TO_TIMESTAMP('2022-01-27 17:34:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Incoterms Sadt',20,0,0,TO_TIMESTAMP('2022-01-27 17:34:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T16:38:53.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_FieldGroup_ID=NULL,Updated=TO_TIMESTAMP('2022-01-27 17:38:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501628
;

-- 2022-01-27T16:44:06.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='N',Updated=TO_TIMESTAMP('2022-01-27 17:44:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501628
;


-- 2022-01-27T17:32:02.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_Incoterms_ID/-1@>0 & @C_Incoterms_ID@!540001',Updated=TO_TIMESTAMP('2022-01-27 18:32:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501628
;

-- 2022-01-27T17:54:27.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@C_Incoterms_ID/-1@>0 & @C_Incoterms_ID@!540001',Updated=TO_TIMESTAMP('2022-01-27 18:54:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=501614
;

