-- 2017-09-14T11:18:46.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543417,0,'IsShipToContact_Default',TO_TIMESTAMP('2017-09-14 11:18:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ShipTo Contact Default','ShipTo Contact Default',TO_TIMESTAMP('2017-09-14 11:18:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T11:18:46.393
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543417 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-09-14T11:19:16.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543418,0,'IsBillToContact_Default',TO_TIMESTAMP('2017-09-14 11:19:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','BillTo Contact Default','BillTo Contact Default',TO_TIMESTAMP('2017-09-14 11:19:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T11:19:16.811
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543418 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2017-09-14T11:19:44.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557164,543418,0,20,114,'N','IsBillToContact_Default',TO_TIMESTAMP('2017-09-14 11:19:44','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','BillTo Contact Default','',0,0,TO_TIMESTAMP('2017-09-14 11:19:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-14T11:19:44.936
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557164 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-14T11:19:47.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ad_user','ALTER TABLE public.AD_User ADD COLUMN IsBillToContact_Default CHAR(1) DEFAULT ''N'' CHECK (IsBillToContact_Default IN (''Y'',''N'')) NOT NULL')
;

-- 2017-09-14T11:20:11.882
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557165,543417,0,20,114,'N','IsShipToContact_Default',TO_TIMESTAMP('2017-09-14 11:20:11','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','ShipTo Contact Default','',0,0,TO_TIMESTAMP('2017-09-14 11:20:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-09-14T11:20:11.884
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557165 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-14T11:20:14.866
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ad_user','ALTER TABLE public.AD_User ADD COLUMN IsShipToContact_Default CHAR(1) DEFAULT ''N'' CHECK (IsShipToContact_Default IN (''Y'',''N'')) NOT NULL')
;

-- 2017-09-14T11:37:44.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCode,BeforeChangeCodeType,BeforeChangeWarning,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540407,0,114,'IsBillContact_Default=''N''','SQLS','Möchten sie wirklich die Standard Verkaufskontakt ändern?',TO_TIMESTAMP('2017-09-14 11:37:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Es darf nur eine Standard Verkaufskontakt aktiviert sein. Bei Änderung wird bei der vorherigen Standard Verkaufskontakt automatisch der Haken entfernt.','Y','Y','AD_User_IsBillContact_Default','N',TO_TIMESTAMP('2017-09-14 11:37:43','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_ID IS NOT NULL AND IsBillContact_Default=''Y'' AND IsActive=''Y''')
;

-- 2017-09-14T11:37:44.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540407 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2017-09-14T11:38:03.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5844,540811,540407,0,TO_TIMESTAMP('2017-09-14 11:38:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2017-09-14 11:38:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T11:38:18.170
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,557164,540812,540407,0,TO_TIMESTAMP('2017-09-14 11:38:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2017-09-14 11:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T11:41:51.348
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET BeforeChangeCode='IsBillToContact_Default=''N''', WhereClause='C_BPartner_ID IS NOT NULL AND IsBillToContact_Default=''Y'' AND IsActive=''Y''',Updated=TO_TIMESTAMP('2017-09-14 11:41:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540407
;

-- 2017-09-14T11:41:58.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_User_IsBillContact_Default ON AD_User (C_BPartner_ID,IsBillToContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y' AND IsActive='Y'
;

-- 2017-09-14T11:41:58.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE OR REPLACE FUNCTION AD_User_IsBillContact_Default_tgfn()
 RETURNS TRIGGER AS $AD_User_IsBillContact_Default_tg$
 BEGIN
 IF TG_OP='INSERT' THEN
UPDATE AD_User SET IsBillToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsBillToContact_Default=NEW.IsBillToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y' AND IsActive='Y';
 ELSE
IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsBillToContact_Default<>NEW.IsBillToContact_Default THEN
UPDATE AD_User SET IsBillToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsBillToContact_Default=NEW.IsBillToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsBillToContact_Default='Y' AND IsActive='Y';
 END IF;
 END IF;
 RETURN NEW;
 END;
 $AD_User_IsBillContact_Default_tg$ LANGUAGE plpgsql;
;

-- 2017-09-14T11:41:58.636
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS AD_User_IsBillContact_Default_tg ON AD_User
;

-- 2017-09-14T11:41:58.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER AD_User_IsBillContact_Default_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsBillContact_Default_tgfn()
;

-- 2017-09-14T11:44:10.898
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,BeforeChangeCode,BeforeChangeCodeType,BeforeChangeWarning,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540408,0,114,'_IsShipToContact_Default=''N''','SQLS','Möchten sie wirklich die Standard ShipTo ändern?',TO_TIMESTAMP('2017-09-14 11:44:10','YYYY-MM-DD HH24:MI:SS'),100,'D','','Y','Y','AD_User_IsShipToContact_Default','N',TO_TIMESTAMP('2017-09-14 11:44:10','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_ID IS NOT NULL AND _IsShipToContact_Default=''Y'' AND IsActive=''Y''')
;

-- 2017-09-14T11:44:10.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540408 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2017-09-14T11:44:18.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,5844,540813,540408,0,TO_TIMESTAMP('2017-09-14 11:44:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2017-09-14 11:44:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T11:44:33.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,557165,540814,540408,0,TO_TIMESTAMP('2017-09-14 11:44:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2017-09-14 11:44:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T11:44:50.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET BeforeChangeCode='IsShipToContact_Default=''N''', WhereClause='C_BPartner_ID IS NOT NULL AND IsShipToContact_Default=''Y'' AND IsActive=''Y''',Updated=TO_TIMESTAMP('2017-09-14 11:44:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540408
;

-- 2017-09-14T11:44:52.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_User_IsShipToContact_Default ON AD_User (C_BPartner_ID,IsShipToContact_Default) WHERE C_BPartner_ID IS NOT NULL AND IsShipToContact_Default='Y' AND IsActive='Y'
;

-- 2017-09-14T11:44:52.281
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE OR REPLACE FUNCTION AD_User_IsShipToContact_Default_tgfn()
 RETURNS TRIGGER AS $AD_User_IsShipToContact_Default_tg$
 BEGIN
 IF TG_OP='INSERT' THEN
UPDATE AD_User SET IsShipToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsShipToContact_Default=NEW.IsShipToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsShipToContact_Default='Y' AND IsActive='Y';
 ELSE
IF OLD.C_BPartner_ID<>NEW.C_BPartner_ID OR OLD.IsShipToContact_Default<>NEW.IsShipToContact_Default THEN
UPDATE AD_User SET IsShipToContact_Default='N', Updated=NEW.Updated, UpdatedBy=NEW.UpdatedBy WHERE 1=1  AND C_BPartner_ID=NEW.C_BPartner_ID AND IsShipToContact_Default=NEW.IsShipToContact_Default AND AD_User_ID<>NEW.AD_User_ID AND C_BPartner_ID IS NOT NULL AND IsShipToContact_Default='Y' AND IsActive='Y';
 END IF;
 END IF;
 RETURN NEW;
 END;
 $AD_User_IsShipToContact_Default_tg$ LANGUAGE plpgsql;
;

-- 2017-09-14T11:44:52.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP TRIGGER IF EXISTS AD_User_IsShipToContact_Default_tg ON AD_User
;

-- 2017-09-14T11:44:52.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TRIGGER AD_User_IsShipToContact_Default_tg BEFORE INSERT OR UPDATE  ON AD_User FOR EACH ROW EXECUTE PROCEDURE AD_User_IsShipToContact_Default_tgfn()
;

-- 2017-09-14T11:45:18.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET BeforeChangeWarning='Möchten sie wirklich die default BillTo ändern?',Updated=TO_TIMESTAMP('2017-09-14 11:45:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540407
;

-- 2017-09-14T11:45:28.441
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET ErrorMsg='darf nur eine Standard Verkaufskontakt aktiviert sein. Bei Änderung wird bei der vorherigen Standard Verkaufskontakt automatisch der Haken entfernt.',Updated=TO_TIMESTAMP('2017-09-14 11:45:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540407
;

-- 2017-09-14T11:46:05.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET ErrorMsg='Only one default BillTo contact may be activated. If changed, the previous standard sales contact automatically removes the hook.',Updated=TO_TIMESTAMP('2017-09-14 11:46:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540407
;

-- 2017-09-14T11:46:13.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET ErrorMsg='Only one default ShipTo contact may be activated. If changed, the previous standard sales contact automatically removes the hook.',Updated=TO_TIMESTAMP('2017-09-14 11:46:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540408
;

-- 2017-09-14T11:47:40.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557164,559901,0,496,0,TO_TIMESTAMP('2017-09-14 11:47:40','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','N','BillTo Contact Default',290,285,0,1,1,TO_TIMESTAMP('2017-09-14 11:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T11:47:40.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559901 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-14T11:47:52.052
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557165,559902,0,496,0,TO_TIMESTAMP('2017-09-14 11:47:51','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','Y','N','N','N','N','Y','ShipTo Contact Default',290,285,0,1,1,TO_TIMESTAMP('2017-09-14 11:47:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T11:47:52.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=559902 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-14T12:08:40.939
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,559901,0,496,548438,1000035,TO_TIMESTAMP('2017-09-14 12:08:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','BillTo Contact',12,0,0,TO_TIMESTAMP('2017-09-14 12:08:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T12:09:21.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,559902,0,496,548439,1000035,TO_TIMESTAMP('2017-09-14 12:09:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','ShipTo Contact Default',12,0,0,TO_TIMESTAMP('2017-09-14 12:09:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-14T12:09:45.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2017-09-14 12:09:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000312
;

-- 2017-09-14T12:09:53.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2017-09-14 12:09:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548438
;

-- 2017-09-14T12:09:58.294
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2017-09-14 12:09:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548439
;

-- 2017-09-14T12:10:01.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2017-09-14 12:10:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000316
;

-- 2017-09-14T12:10:04.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2017-09-14 12:10:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544832
;

-- 2017-09-14T12:10:08.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2017-09-14 12:10:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000313
;

-- 2017-09-14T12:10:11.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2017-09-14 12:10:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000317
;

-- 2017-09-14T12:10:14.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2017-09-14 12:10:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544833
;

-- 2017-09-14T12:10:19.414
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2017-09-14 12:10:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000320
;

-- 2017-09-14T12:10:25.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2017-09-14 12:10:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000309
;

-- 2017-09-14T12:11:15.386
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Rechnungskontakt',Updated=TO_TIMESTAMP('2017-09-14 12:11:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548438
;

-- 2017-09-14T12:11:26.618
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='Lieferkontakt',Updated=TO_TIMESTAMP('2017-09-14 12:11:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548439
;

-- 2017-09-14T12:11:43.032
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferkontakt',Updated=TO_TIMESTAMP('2017-09-14 12:11:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559902
;

-- 2017-09-14T12:13:27.887
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungskontakt',Updated=TO_TIMESTAMP('2017-09-14 12:13:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=559901
;

-- 2017-09-14T12:14:11.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Rechnungskontakt', PrintName='Rechnungskontakt',Updated=TO_TIMESTAMP('2017-09-14 12:14:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543418
;

-- 2017-09-14T12:14:11.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsBillToContact_Default', Name='Rechnungskontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=543418
;

-- 2017-09-14T12:14:11.514
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsBillToContact_Default', Name='Rechnungskontakt', Description=NULL, Help=NULL, AD_Element_ID=543418 WHERE UPPER(ColumnName)='ISBILLTOCONTACT_DEFAULT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-09-14T12:14:11.519
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsBillToContact_Default', Name='Rechnungskontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=543418 AND IsCentrallyMaintained='Y'
;

-- 2017-09-14T12:14:11.520
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungskontakt', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543418) AND IsCentrallyMaintained='Y'
;

-- 2017-09-14T12:14:11.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnungskontakt', Name='Rechnungskontakt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543418)
;

-- 2017-09-14T12:14:30.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Lieferkontakt', PrintName='Lieferkontakt',Updated=TO_TIMESTAMP('2017-09-14 12:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543417
;

-- 2017-09-14T12:14:30.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsShipToContact_Default', Name='Lieferkontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=543417
;

-- 2017-09-14T12:14:30.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsShipToContact_Default', Name='Lieferkontakt', Description=NULL, Help=NULL, AD_Element_ID=543417 WHERE UPPER(ColumnName)='ISSHIPTOCONTACT_DEFAULT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-09-14T12:14:30.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsShipToContact_Default', Name='Lieferkontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=543417 AND IsCentrallyMaintained='Y'
;

-- 2017-09-14T12:14:30.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Lieferkontakt', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543417) AND IsCentrallyMaintained='Y'
;

-- 2017-09-14T12:14:30.262
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferkontakt', Name='Lieferkontakt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543417)
;

