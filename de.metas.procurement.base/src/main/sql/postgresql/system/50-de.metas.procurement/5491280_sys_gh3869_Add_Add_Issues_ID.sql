-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559716 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PMM_QtyReport_Event','ALTER TABLE public.PMM_QtyReport_Event ADD COLUMN AD_Issue_ID NUMERIC(10)')
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--ALTER TABLE PMM_QtyReport_Event ADD CONSTRAINT ADIssue_PMMQtyReportEvent FOREIGN KEY (AD_Issue_ID) REFERENCES public.AD_Issue DEFERRABLE INITIALLY DEFERRED
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563595 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559717 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PMM_RfQResponse_ChangeEvent','ALTER TABLE public.PMM_RfQResponse_ChangeEvent ADD COLUMN AD_Issue_ID NUMERIC(10)')
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--ALTER TABLE PMM_RfQResponse_ChangeEvent ADD CONSTRAINT ADIssue_PMMRfQResponseChangeEvent FOREIGN KEY (AD_Issue_ID) REFERENCES public.AD_Issue DEFERRABLE INITIALLY DEFERRED
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559718 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PMM_WeekReport_Event','ALTER TABLE public.PMM_WeekReport_Event ADD COLUMN AD_Issue_ID NUMERIC(10)')
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--ALTER TABLE PMM_WeekReport_Event ADD CONSTRAINT ADIssue_PMMWeekReportEvent FOREIGN KEY (AD_Issue_ID) REFERENCES public.AD_Issue DEFERRABLE INITIALLY DEFERRED
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563596 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
;

