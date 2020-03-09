-- 2020-02-28T08:46:03.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570071,541356,0,30,173,541429,'C_BPartner_Customer_ID',TO_TIMESTAMP('2020-02-28 09:46:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts.commission',10,'Y','N','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Kunde',0,0,TO_TIMESTAMP('2020-02-28 09:46:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-02-28T08:46:03.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570071 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-02-28T08:46:03.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(541356) 
;

-- 2020-02-28T08:46:29.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_CommissionSettingsLine','ALTER TABLE public.C_CommissionSettingsLine ADD COLUMN C_BPartner_Customer_ID NUMERIC(10)')
;

-- 2020-02-28T08:46:29.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_CommissionSettingsLine ADD CONSTRAINT CBPartnerCustomer_CCommissionSettingsLine FOREIGN KEY (C_BPartner_Customer_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2020-02-28T08:47:15.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570071,598432,0,542093,TO_TIMESTAMP('2020-02-28 09:47:15','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.contracts','Y','Y','N','N','N','N','N','Kunde',TO_TIMESTAMP('2020-02-28 09:47:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-28T08:47:15.383Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598432 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-02-28T08:47:15.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541356) 
;

-- 2020-02-28T08:47:15.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598432
;

-- 2020-02-28T08:47:15.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598432)
;

-- 2020-02-28T08:48:11.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=542888, Description=NULL, Help=NULL, Name='Kundengruppe',Updated=TO_TIMESTAMP('2020-02-28 09:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=590617
;

-- 2020-02-28T08:48:11.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542888) 
;

-- 2020-02-28T08:48:11.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=590617
;

-- 2020-02-28T08:48:11.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(590617)
;

-- 2020-02-28T08:49:53.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,598432,0,542093,543137,566555,TO_TIMESTAMP('2020-02-28 09:49:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kunde',60,0,0,TO_TIMESTAMP('2020-02-28 09:49:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-28T08:50:05.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='C_BPartner_Customer_ID',Updated=TO_TIMESTAMP('2020-02-28 09:50:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566555
;

-- 2020-02-28T08:50:38.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2020-02-28 09:50:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566555
;

-- 2020-02-28T08:52:57.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn eine Kundegruppe oder ein Kunde ausgewählt ist, entscheided dieses Feld, ob es ein Ein- oder Ausschlusskriterium ist.', Name='Kunde bzw. Gruppe ausschließen', PrintName='Kunde bzw. Gruppe ausschließen',Updated=TO_TIMESTAMP('2020-02-28 09:52:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577569 AND AD_Language='de_CH'
;

-- 2020-02-28T08:52:57.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577569,'de_CH') 
;

-- 2020-02-28T08:53:02.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Kunde bzw. Gruppe ausschließen', PrintName='Kunde bzw. Gruppe ausschließen',Updated=TO_TIMESTAMP('2020-02-28 09:53:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577569 AND AD_Language='de_DE'
;

-- 2020-02-28T08:53:02.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577569,'de_DE') 
;

-- 2020-02-28T08:53:02.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577569,'de_DE') 
;

-- 2020-02-28T08:53:02.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsExcludeBPGroup', Name='Kunde bzw. Gruppe ausschließen', Description='Wenn eine Geschäftspartnergruppe ausgewählt ist, entscheided dieses Feld, ob diese Gruppe ein Ein- oder Ausschlusskriterium ist', Help=NULL WHERE AD_Element_ID=577569
;

-- 2020-02-28T08:53:02.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExcludeBPGroup', Name='Kunde bzw. Gruppe ausschließen', Description='Wenn eine Geschäftspartnergruppe ausgewählt ist, entscheided dieses Feld, ob diese Gruppe ein Ein- oder Ausschlusskriterium ist', Help=NULL, AD_Element_ID=577569 WHERE UPPER(ColumnName)='ISEXCLUDEBPGROUP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-28T08:53:02.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExcludeBPGroup', Name='Kunde bzw. Gruppe ausschließen', Description='Wenn eine Geschäftspartnergruppe ausgewählt ist, entscheided dieses Feld, ob diese Gruppe ein Ein- oder Ausschlusskriterium ist', Help=NULL WHERE AD_Element_ID=577569 AND IsCentrallyMaintained='Y'
;

-- 2020-02-28T08:53:02.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kunde bzw. Gruppe ausschließen', Description='Wenn eine Geschäftspartnergruppe ausgewählt ist, entscheided dieses Feld, ob diese Gruppe ein Ein- oder Ausschlusskriterium ist', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577569) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577569)
;

-- 2020-02-28T08:53:02.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kunde bzw. Gruppe ausschließen', Name='Kunde bzw. Gruppe ausschließen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577569)
;

-- 2020-02-28T08:53:02.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kunde bzw. Gruppe ausschließen', Description='Wenn eine Geschäftspartnergruppe ausgewählt ist, entscheided dieses Feld, ob diese Gruppe ein Ein- oder Ausschlusskriterium ist', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577569
;

-- 2020-02-28T08:53:02.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kunde bzw. Gruppe ausschließen', Description='Wenn eine Geschäftspartnergruppe ausgewählt ist, entscheided dieses Feld, ob diese Gruppe ein Ein- oder Ausschlusskriterium ist', Help=NULL WHERE AD_Element_ID = 577569
;

-- 2020-02-28T08:53:02.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kunde bzw. Gruppe ausschließen', Description = 'Wenn eine Geschäftspartnergruppe ausgewählt ist, entscheided dieses Feld, ob diese Gruppe ein Ein- oder Ausschlusskriterium ist', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577569
;

-- 2020-02-28T08:53:07.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn eine Kundegruppe oder ein Kunde ausgewählt ist, entscheided dieses Feld, ob es ein Ein- oder Ausschlusskriterium ist',Updated=TO_TIMESTAMP('2020-02-28 09:53:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577569 AND AD_Language='de_DE'
;

-- 2020-02-28T08:53:07.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577569,'de_DE') 
;

-- 2020-02-28T08:53:07.060Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577569,'de_DE') 
;

-- 2020-02-28T08:53:07.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsExcludeBPGroup', Name='Kunde bzw. Gruppe ausschließen', Description='Wenn eine Kundegruppe oder ein Kunde ausgewählt ist, entscheided dieses Feld, ob es ein Ein- oder Ausschlusskriterium ist', Help=NULL WHERE AD_Element_ID=577569
;

-- 2020-02-28T08:53:07.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExcludeBPGroup', Name='Kunde bzw. Gruppe ausschließen', Description='Wenn eine Kundegruppe oder ein Kunde ausgewählt ist, entscheided dieses Feld, ob es ein Ein- oder Ausschlusskriterium ist', Help=NULL, AD_Element_ID=577569 WHERE UPPER(ColumnName)='ISEXCLUDEBPGROUP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-02-28T08:53:07.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsExcludeBPGroup', Name='Kunde bzw. Gruppe ausschließen', Description='Wenn eine Kundegruppe oder ein Kunde ausgewählt ist, entscheided dieses Feld, ob es ein Ein- oder Ausschlusskriterium ist', Help=NULL WHERE AD_Element_ID=577569 AND IsCentrallyMaintained='Y'
;

-- 2020-02-28T08:53:07.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kunde bzw. Gruppe ausschließen', Description='Wenn eine Kundegruppe oder ein Kunde ausgewählt ist, entscheided dieses Feld, ob es ein Ein- oder Ausschlusskriterium ist', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577569) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577569)
;

-- 2020-02-28T08:53:07.076Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kunde bzw. Gruppe ausschließen', Description='Wenn eine Kundegruppe oder ein Kunde ausgewählt ist, entscheided dieses Feld, ob es ein Ein- oder Ausschlusskriterium ist', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577569
;

-- 2020-02-28T08:53:07.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kunde bzw. Gruppe ausschließen', Description='Wenn eine Kundegruppe oder ein Kunde ausgewählt ist, entscheided dieses Feld, ob es ein Ein- oder Ausschlusskriterium ist', Help=NULL WHERE AD_Element_ID = 577569
;

-- 2020-02-28T08:53:07.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kunde bzw. Gruppe ausschließen', Description = 'Wenn eine Kundegruppe oder ein Kunde ausgewählt ist, entscheided dieses Feld, ob es ein Ein- oder Ausschlusskriterium ist', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577569
;

-- 2020-02-28T08:53:09.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn eine Kundegruppe oder ein Kunde ausgewählt ist, entscheided dieses Feld, ob es ein Ein- oder Ausschlusskriterium ist',Updated=TO_TIMESTAMP('2020-02-28 09:53:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577569 AND AD_Language='de_CH'
;

-- 2020-02-28T08:53:09.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577569,'de_CH') 
;

-- 2020-02-28T08:53:22.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exclude customer or group', PrintName='Exclude customer or group',Updated=TO_TIMESTAMP('2020-02-28 09:53:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577569 AND AD_Language='en_US'
;

-- 2020-02-28T08:53:22.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577569,'en_US') 
;

-- 2020-02-28T09:07:02.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2020-02-28 10:07:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566555
;

-- 2020-02-28T09:07:02.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2020-02-28 10:07:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566553
;

-- 2020-02-28T09:07:02.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2020-02-28 10:07:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563737
;

-- 2020-02-28T09:07:02.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2020-02-28 10:07:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566554
;

-- 2020-02-28T09:07:02.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2020-02-28 10:07:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563738
;

-- 2020-02-28T09:07:31.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=542070, SeqNo=30,Updated=TO_TIMESTAMP('2020-02-28 10:07:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543138
;

-- 2020-02-28T09:07:37.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='seqno',Updated=TO_TIMESTAMP('2020-02-28 10:07:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543137
;

-- 2020-02-28T09:07:52.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542069,543524,TO_TIMESTAMP('2020-02-28 10:07:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','bpartner-matching',20,TO_TIMESTAMP('2020-02-28 10:07:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-28T09:08:08.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543524, SeqNo=10,Updated=TO_TIMESTAMP('2020-02-28 10:08:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563736
;

-- 2020-02-28T09:08:15.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543524, SeqNo=20,Updated=TO_TIMESTAMP('2020-02-28 10:08:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566555
;

-- 2020-02-28T09:08:24.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543524, SeqNo=30,Updated=TO_TIMESTAMP('2020-02-28 10:08:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566553
;

-- 2020-02-28T09:08:38.392Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542069,543525,TO_TIMESTAMP('2020-02-28 10:08:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','product-matching',30,TO_TIMESTAMP('2020-02-28 10:08:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-28T09:08:46.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543525, SeqNo=10,Updated=TO_TIMESTAMP('2020-02-28 10:08:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563737
;

-- 2020-02-28T09:08:52.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543525, SeqNo=20,Updated=TO_TIMESTAMP('2020-02-28 10:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566554
;

-- 2020-02-28T09:10:26.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=542888, AD_Reference_Value_ID=540996, ColumnName='Customer_Group_ID', Description=NULL, Help=NULL, Name='Kundengruppe',Updated=TO_TIMESTAMP('2020-02-28 10:10:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569269
;

-- 2020-02-28T09:10:26.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kundengruppe', Description=NULL, Help=NULL WHERE AD_Column_ID=569269
;

-- 2020-02-28T09:10:26.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(542888) 
;

/* DDL */ SELECT public.db_alter_table('C_CommissionSettingsLine','ALTER TABLE C_CommissionSettingsLine RENAME COLUMN C_BP_Group_ID TO Customer_Group_ID;');
