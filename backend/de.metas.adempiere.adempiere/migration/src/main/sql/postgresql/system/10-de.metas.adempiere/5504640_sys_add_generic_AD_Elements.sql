
-- 2018-10-24T18:43:52.024
-- #298 changing anz. stellen
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,558941,0,TO_TIMESTAMP('2018-10-24 18:43:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Geschäftspartner-ID','Geschäftspartner-ID',TO_TIMESTAMP('2018-10-24 18:43:51','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2018-10-24T18:43:52.068
-- #298 changing anz. stellen
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=558941 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-24T18:43:55.973
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-24 18:43:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=558941 AND AD_Language='de_CH'
;

-- 2018-10-24T18:43:56.088
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(558941,'de_CH') 
;

-- 2018-10-24T18:59:50.841
-- #298 changing anz. stellen
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,558942,0,TO_TIMESTAMP('2018-10-24 18:59:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Standort-ID','Standort-ID',TO_TIMESTAMP('2018-10-24 18:59:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-24T18:59:50.896
-- #298 changing anz. stellen
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=558942 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-10-24T18:59:59.693
-- #298 changing anz. stellen
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-10-24 18:59:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=558942 AND AD_Language='de_CH'
;

-- 2018-10-24T18:59:59.818
-- #298 changing anz. stellen
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(558942,'de_CH') 
;


-- 2018-10-24T19:01:40.098
-- #298 changing anz. stellen
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,558943,0,TO_TIMESTAMP('2018-10-24 19:01:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Anschrift-ID','Anschrift-ID',TO_TIMESTAMP('2018-10-24 19:01:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-24T19:01:40.141
-- #298 changing anz. stellen
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=558943 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


-- 2018-10-24T19:39:33.757
-- #298 changing anz. stellen
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,558944,0,TO_TIMESTAMP('2018-10-24 19:39:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ansprechpartner-ID','Ansprechpartner-ID',TO_TIMESTAMP('2018-10-24 19:39:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-24T19:39:33.799
-- #298 changing anz. stellen
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=558944 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;



-- 2018-10-24T19:57:37.097
-- #298 changing anz. stellen
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,558945,0,TO_TIMESTAMP('2018-10-24 19:57:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Vorgang Status','Vorgang Status',TO_TIMESTAMP('2018-10-24 19:57:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-10-24T19:57:37.142
-- #298 changing anz. stellen
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=558945 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
