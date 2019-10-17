-- 2019-04-03T14:30:24.483
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.I_Postal (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Country_ID NUMERIC(10), City VARCHAR(250), CountryCode VARCHAR(2), C_Postal_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, C_Region_ID NUMERIC(10), I_ErrorMsg VARCHAR(2000), I_IsImported CHAR(1) DEFAULT 'N' NOT NULL, I_Postal_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Postal VARCHAR(10), Processed CHAR(1) DEFAULT 'N' CHECK (Processed IN ('Y','N')) NOT NULL, RegionName VARCHAR(255), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, ValidFrom TIMESTAMP WITHOUT TIME ZONE, ValidTo TIMESTAMP WITHOUT TIME ZONE, CONSTRAINT CCountry_IPostal FOREIGN KEY (C_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CPostal_IPostal FOREIGN KEY (C_Postal_ID) REFERENCES public.C_Postal DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CRegion_IPostal FOREIGN KEY (C_Region_ID) REFERENCES public.C_Region DEFERRABLE INITIALLY DEFERRED, CONSTRAINT I_Postal_Key PRIMARY KEY (I_Postal_ID))
;

-- 2019-04-03T15:00:11.116
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540490,0,541342,TO_TIMESTAMP('2019-04-03 15:00:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Postal_Index','N',TO_TIMESTAMP('2019-04-03 15:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T15:00:11.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540490 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2019-04-03T15:00:41.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,567575,540940,540490,0,TO_TIMESTAMP('2019-04-03 15:00:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2019-04-03 15:00:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-04-03T15:01:45.042
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET WhereClause='isActive=''Y''',Updated=TO_TIMESTAMP('2019-04-03 15:01:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540490
;

-- 2019-04-03T15:03:16.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=540940
;

-- 2019-04-03T15:03:21.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540490
;

-- 2019-04-03T15:03:21.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540490
;

