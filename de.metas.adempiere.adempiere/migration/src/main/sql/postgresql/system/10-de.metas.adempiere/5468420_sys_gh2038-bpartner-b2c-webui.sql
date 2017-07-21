-- 2017-07-21T14:44:48.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,Updated,UpdatedBy) VALUES (0,543385,0,'SalesRepIntern_ID',TO_TIMESTAMP('2017-07-21 14:44:48','YYYY-MM-DD HH24:MI:SS'),100,'Sales Responsible Internal','D','','Y','Sales Responsible','Sales Responsible','','Sales Responsible','Sales Responsible','Sales Responsible',TO_TIMESTAMP('2017-07-21 14:44:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-21T14:44:48.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543385 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-07-21T14:47:30.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557022,543385,0,18,316,291,'N','SalesRepIntern_ID',TO_TIMESTAMP('2017-07-21 14:47:30','YYYY-MM-DD HH24:MI:SS'),100,'N','@NULL@','Sales Responsible Internal','D',10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Sales Responsible',0,TO_TIMESTAMP('2017-07-21 14:47:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-07-21T14:47:30.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557022 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-07-21T14:47:43.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('c_bpartner','ALTER TABLE public.C_BPartner ADD COLUMN SalesRepIntern_ID NUMERIC(10)')
;

-- 2017-07-21T14:47:44.176
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BPartner ADD CONSTRAINT SalesRepIntern_CBPartner FOREIGN KEY (SalesRepIntern_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 2017-07-21T14:48:42.010
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557022,559425,0,540843,0,TO_TIMESTAMP('2017-07-21 14:48:41','YYYY-MM-DD HH24:MI:SS'),100,'Sales Responsible Internal',0,'D','',0,'Y','Y','Y','Y','N','N','N','N','N','Sales Responsible',260,290,0,1,1,TO_TIMESTAMP('2017-07-21 14:48:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-21T14:48:42.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559425 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-07-21T14:49:03.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLength=14,Updated=TO_TIMESTAMP('2017-07-21 14:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559425
;

-- 2017-07-21T14:49:37.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,558953,0,540843,540903,547004,TO_TIMESTAMP('2017-07-21 14:49:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aussendienst',50,0,0,TO_TIMESTAMP('2017-07-21 14:49:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-21T14:49:51.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,559425,0,540843,540903,547005,TO_TIMESTAMP('2017-07-21 14:49:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Innendienst',60,0,0,TO_TIMESTAMP('2017-07-21 14:49:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-21T14:50:03.837
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2017-07-21 14:50:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547004
;

-- 2017-07-21T14:50:08.533
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-07-21 14:50:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547005
;

-- 2017-07-21T14:50:11.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546984
;

-- 2017-07-21T14:50:15.207
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=546979
;

-- 2017-07-21T14:50:56.878
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='', Help='', Name='Aussendienst', PrintName='Aussendienst',Updated=TO_TIMESTAMP('2017-07-21 14:50:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1063
;

-- 2017-07-21T14:50:56.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='SalesRep_ID', Name='Aussendienst', Description='', Help='' WHERE AD_Element_ID=1063
;

-- 2017-07-21T14:50:56.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesRep_ID', Name='Aussendienst', Description='', Help='', AD_Element_ID=1063 WHERE UPPER(ColumnName)='SALESREP_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-07-21T14:50:56.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='SalesRep_ID', Name='Aussendienst', Description='', Help='' WHERE AD_Element_ID=1063 AND IsCentrallyMaintained='Y'
;

-- 2017-07-21T14:50:56.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Aussendienst', Description='', Help='' WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1063) AND IsCentrallyMaintained='Y'
;

-- 2017-07-21T14:50:56.919
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Aussendienst', Name='Aussendienst' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1063)
;

-- 2017-07-21T14:52:23.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Innendienst',Updated=TO_TIMESTAMP('2017-07-21 14:52:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559425
;

