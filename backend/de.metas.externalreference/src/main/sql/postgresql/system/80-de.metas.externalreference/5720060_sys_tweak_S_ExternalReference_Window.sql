
-- Table: S_ExternalReference
-- 2024-03-22T08:06:22.877Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2024-03-22 09:06:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541486
;


-- UI Element: Geschäftspartner -> Geschäftspartner.Hubspot Company ID
-- Column: C_BPartner.yo98_Hubspot_Partner_ID
-- 2024-03-22T08:45:27Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2024-03-22 09:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605317
;

-- UI Element: Geschäftspartner -> Geschäftspartner.Hubspot Company ID
-- Column: C_BPartner.yo98_Hubspot_Partner_ID
-- 2024-03-22T08:45:41.288Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=605317
;

-- UI Element: Externe Referenz -> External reference.Version
-- Column: S_ExternalReference.Version
-- 2024-03-22T08:49:11.312Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543614, SeqNo=70,Updated=TO_TIMESTAMP('2024-03-22 09:49:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586795
;

-- UI Element: Externe Referenz -> External reference.URL im externen system
-- Column: S_ExternalReference.ExternalReferenceURL
-- 2024-03-22T08:49:45.852Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543614, SeqNo=80,Updated=TO_TIMESTAMP('2024-03-22 09:49:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585968
;

-- UI Element: Externe Referenz -> External reference.Datensatz-ID
-- Column: S_ExternalReference.Record_ID
-- 2024-03-22T08:50:42.297Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551713, SeqNo=20,Updated=TO_TIMESTAMP('2024-03-22 09:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=567191
;

-- UI Element: Externe Referenz -> External reference.Referenced record ID
-- Column: S_ExternalReference.Referenced_Record_ID
-- 2024-03-22T08:51:21.301Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551713, SeqNo=30,Updated=TO_TIMESTAMP('2024-03-22 09:51:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=567569
;

-- UI Element: Externe Referenz -> External reference.URL im externen system
-- Column: S_ExternalReference.ExternalReferenceURL
-- 2024-03-22T08:52:01.982Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2024-03-22 09:52:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585968
;

-- UI Element: Externe Referenz -> External reference.Referenced table ID
-- Column: S_ExternalReference.Referenced_AD_Table_ID
-- 2024-03-22T08:53:28.035Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649726,0,542376,551713,623806,'F',TO_TIMESTAMP('2024-03-22 09:53:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Referenced table ID',40,0,0,TO_TIMESTAMP('2024-03-22 09:53:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe Referenz -> External reference.Referenced record ID
-- Column: S_ExternalReference.Referenced_Record_ID
-- 2024-03-22T08:53:38.721Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2024-03-22 09:53:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=567569
;

-- Field: Externe Referenz -> External reference -> Referenced table ID
-- Column: S_ExternalReference.Referenced_AD_Table_ID
-- 2024-03-22T08:54:07.453Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-03-22 09:54:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649726
;

-- Field: Externe Referenz -> External reference -> Referenced record ID
-- Column: S_ExternalReference.Referenced_Record_ID
-- 2024-03-22T08:54:23.476Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-03-22 09:54:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=601778
;

-- 2024-03-22T08:55:29.848Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='metasfresh-Tabelle', PrintName='metasfresh-Tabelle',Updated=TO_TIMESTAMP('2024-03-22 09:55:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577669 AND AD_Language='de_CH'
;

-- 2024-03-22T08:55:29.898Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577669,'de_CH')
;

-- 2024-03-22T08:55:36.583Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='metasfresh-Tabelle', PrintName='metasfresh-Tabelle',Updated=TO_TIMESTAMP('2024-03-22 09:55:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577669 AND AD_Language='de_DE'
;

-- 2024-03-22T08:55:36.630Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577669,'de_DE')
;

-- 2024-03-22T08:55:36.651Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577669,'de_DE')
;

-- 2024-03-22T08:56:16.813Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='metasfresh-Datensatz', PrintName='metasfresh-Datensatz',Updated=TO_TIMESTAMP('2024-03-22 09:56:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577670 AND AD_Language='de_CH'
;

-- 2024-03-22T08:56:16.855Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577670,'de_CH')
;

-- 2024-03-22T08:56:22.735Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='metasfresh-Datensatz', PrintName='metasfresh-Datensatz',Updated=TO_TIMESTAMP('2024-03-22 09:56:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577670 AND AD_Language='de_DE'
;

-- 2024-03-22T08:56:22.781Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577670,'de_DE')
;

-- 2024-03-22T08:56:22.802Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577670,'de_DE')
;

-- 2024-03-22T08:58:44.564Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583051,0,TO_TIMESTAMP('2024-03-22 09:58:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','metasfresh-Datensatz-ID','metasfresh-Datensatz-ID',TO_TIMESTAMP('2024-03-22 09:58:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-22T08:58:44.586Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583051 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-03-22T08:58:53.408Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-22 09:58:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583051 AND AD_Language='de_CH'
;

-- 2024-03-22T08:58:53.450Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583051,'de_CH')
;

-- 2024-03-22T08:58:56.345Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-03-22 09:58:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583051 AND AD_Language='de_DE'
;

-- 2024-03-22T08:58:56.389Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583051,'de_DE')
;

-- 2024-03-22T08:58:56.416Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583051,'de_DE')
;

-- 2024-03-22T08:59:08.569Z
UPDATE AD_Element SET ColumnName='metasfresh-Datensatz-ID',Updated=TO_TIMESTAMP('2024-03-22 09:59:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583051
;

-- 2024-03-22T08:59:08.698Z
UPDATE AD_Column SET ColumnName='metasfresh-Datensatz-ID' WHERE AD_Element_ID=583051
;

-- 2024-03-22T08:59:08.722Z
UPDATE AD_Process_Para SET ColumnName='metasfresh-Datensatz-ID' WHERE AD_Element_ID=583051
;

-- 2024-03-22T08:59:08.805Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583051,'de_DE')
;

-- Field: Externe Referenz -> External reference -> metasfresh-Datensatz-ID
-- Column: S_ExternalReference.Record_ID
-- 2024-03-22T08:59:21.618Z
UPDATE AD_Field SET AD_Name_ID=583051, Description=NULL, Help=NULL, Name='metasfresh-Datensatz-ID',Updated=TO_TIMESTAMP('2024-03-22 09:59:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=599756
;

-- 2024-03-22T08:59:21.660Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583051)
;

-- 2024-03-22T08:59:21.699Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=599756
;

-- 2024-03-22T08:59:21.720Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(599756)
;

-- Field: Externe Referenz -> External reference -> metasfresh-Datensatz-ID
-- Column: S_ExternalReference.Record_ID
-- 2024-03-22T08:59:38.937Z
UPDATE AD_Field SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2024-03-22 09:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=599756
;

-- Field: Externe Referenz -> External reference -> Aktiv
-- Column: S_ExternalReference.IsActive
-- 2024-03-22T09:00:05.056Z
UPDATE AD_Field SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2024-03-22 10:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=599748
;

-- Field: Externe Referenz -> External reference -> metasfresh-Datensatz-ID
-- Column: S_ExternalReference.Record_ID
-- 2024-03-22T09:00:09.924Z
UPDATE AD_Field SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2024-03-22 10:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=599756
;

-- Field: Externe Referenz -> External reference -> metasfresh-Datensatz
-- Column: S_ExternalReference.Referenced_Record_ID
-- 2024-03-22T09:00:13.983Z
UPDATE AD_Field SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2024-03-22 10:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=601778
;

-- Field: Externe Referenz -> External reference -> URL im externen system
-- Column: S_ExternalReference.ExternalReferenceURL
-- 2024-03-22T09:00:17.680Z
UPDATE AD_Field SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2024-03-22 10:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647937
;

-- Field: Externe Referenz -> External reference -> Sektion
-- Column: S_ExternalReference.AD_Org_ID
-- 2024-03-22T09:00:21.022Z
UPDATE AD_Field SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2024-03-22 10:00:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=599747
;

-- Field: Externe Referenz -> External reference -> Version
-- Column: S_ExternalReference.Version
-- 2024-03-22T09:00:27.540Z
UPDATE AD_Field SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2024-03-22 10:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649727
;

-- Field: Externe Referenz -> External reference -> Mandant
-- Column: S_ExternalReference.AD_Client_ID
-- 2024-03-22T09:00:56.141Z
UPDATE AD_Field SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2024-03-22 10:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=599746
;

-- Field: Externe Referenz -> External reference -> External reference
-- Column: S_ExternalReference.S_ExternalReference_ID
-- 2024-03-22T09:00:59.902Z
UPDATE AD_Field SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2024-03-22 10:00:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=599750
;

-- Field: Externe Referenz -> External reference -> Externe Referenz
-- Column: S_ExternalReference.ExternalReference
-- 2024-03-22T09:01:02.605Z
UPDATE AD_Field SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2024-03-22 10:01:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=599754
;

-- Field: Externe Referenz -> External reference -> metasfresh-Tabelle
-- Column: S_ExternalReference.Referenced_AD_Table_ID
-- 2024-03-22T09:01:07.709Z
UPDATE AD_Field SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2024-03-22 10:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=649726
;

-- Field: Externe Referenz -> External reference -> External system
-- Column: S_ExternalReference.ExternalSystem
-- 2024-03-22T09:01:11.306Z
UPDATE AD_Field SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2024-03-22 10:01:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=599751
;

-- Field: Externe Referenz -> External reference -> Art
-- Column: S_ExternalReference.Type
-- 2024-03-22T09:01:16.281Z
UPDATE AD_Field SET EntityType='de.metas.externalreference',Updated=TO_TIMESTAMP('2024-03-22 10:01:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=599749
;

