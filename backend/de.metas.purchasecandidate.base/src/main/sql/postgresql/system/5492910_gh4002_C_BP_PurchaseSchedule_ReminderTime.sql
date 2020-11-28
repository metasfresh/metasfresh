-- 2018-05-08T07:22:12.366
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'Wiedervorlage','D','ReminderTime',544046,0,'Wiedervorlage',100,TO_TIMESTAMP('2018-05-08 07:22:12','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-05-08 07:22:12','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-05-08T07:22:12.374
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544046 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-05-08T07:22:27.389
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-08 07:22:27','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Reminder',PrintName='Reminder' WHERE AD_Element_ID=544046 AND AD_Language='en_US'
;

-- 2018-05-08T07:22:27.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544046,'en_US') 
;

-- 2018-05-08T07:23:08.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsForceIncludeInGeneratedModel,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated,IsGenericZoomOrigin) VALUES (24,'N','N','N','N',0,'Y',100,544046,'Y','N','N','N','N','N','N','N','Y','N','N','N',540975,'N','N','ReminderTime',560046,'N','N','N','N','N','N',0,100,'Wiedervorlage','de.metas.purchasecandidate','N',7,0,0,TO_TIMESTAMP('2018-05-08 07:23:08','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-05-08 07:23:08','YYYY-MM-DD HH24:MI:SS'),'N')
;

-- 2018-05-08T07:23:08.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560046 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-05-08T07:23:24.114
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_BP_PurchaseSchedule (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_BP_PurchaseSchedule_ID NUMERIC(10) NOT NULL, C_BPartner_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(10), Frequency NUMERIC(10) DEFAULT 1 NOT NULL, FrequencyType CHAR(1) DEFAULT 'W' NOT NULL, IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL, MonthDay NUMERIC(10), OnFriday CHAR(1) DEFAULT 'N' CHECK (OnFriday IN ('Y','N')), OnMonday CHAR(1) DEFAULT 'N' CHECK (OnMonday IN ('Y','N')), OnSaturday CHAR(1) DEFAULT 'N' CHECK (OnSaturday IN ('Y','N')), OnSunday CHAR(1) DEFAULT 'N' CHECK (OnSunday IN ('Y','N')), OnThursday CHAR(1) DEFAULT 'N' CHECK (OnThursday IN ('Y','N')), OnTuesday CHAR(1) DEFAULT 'N' CHECK (OnTuesday IN ('Y','N')), OnWednesday CHAR(1) DEFAULT 'N' CHECK (OnWednesday IN ('Y','N')), PreparationTime_1 TIMESTAMP WITHOUT TIME ZONE, PreparationTime_2 TIMESTAMP WITHOUT TIME ZONE, PreparationTime_3 TIMESTAMP WITHOUT TIME ZONE, PreparationTime_4 TIMESTAMP WITHOUT TIME ZONE, PreparationTime_5 TIMESTAMP WITHOUT TIME ZONE, PreparationTime_6 TIMESTAMP WITHOUT TIME ZONE, PreparationTime_7 TIMESTAMP WITHOUT TIME ZONE, ReminderTime TIMESTAMP WITHOUT TIME ZONE, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, ValidFrom TIMESTAMP WITHOUT TIME ZONE NOT NULL, CONSTRAINT C_BP_PurchaseSchedule_Key PRIMARY KEY (C_BP_PurchaseSchedule_ID), CONSTRAINT CBPartner_CBPPurchaseSchedule FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED)
;

