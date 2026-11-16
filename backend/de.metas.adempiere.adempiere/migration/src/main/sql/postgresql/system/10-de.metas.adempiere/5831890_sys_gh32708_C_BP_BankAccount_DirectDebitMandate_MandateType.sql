-- SEPA direct debit: mandate type (CORE / B2B) on C_BP_BankAccount_DirectDebitMandate.
-- Existing mandates become CORE (the default of the new mandatory column).
-- ID server allocations: script 5831890, AD_Reference 542151, AD_Ref_List 544381 / 544382, AD_Element 585569, AD_Column 593765

-- Reference: DirectDebitMandateType
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType)
VALUES (0,0,542151 /*From ID Server*/,TO_TIMESTAMP('2026-10-28 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','DirectDebitMandateType',TO_TIMESTAMP('2026-10-28 10:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'L');

INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Reference_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542151
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID);

-- Ref list value: CORE
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544381 /*From ID Server*/,542151,TO_TIMESTAMP('2026-10-28 10:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','CORE (Basislastschrift)',TO_TIMESTAMP('2026-10-28 10:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'CORE','CORE');

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Ref_List_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544381
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

UPDATE AD_Ref_List_Trl SET Name='CORE', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 10:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=544381 AND AD_Language='en_US';

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 10:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=544381 AND AD_Language IN ('de_DE','de_CH');

-- Ref list value: B2B
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544382 /*From ID Server*/,542151,TO_TIMESTAMP('2026-10-28 10:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','B2B (Firmenlastschrift)',TO_TIMESTAMP('2026-10-28 10:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'B2B','B2B');

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Ref_List_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544382
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

UPDATE AD_Ref_List_Trl SET Name='B2B', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 10:00:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=544382 AND AD_Language='en_US';

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 10:00:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Ref_List_ID=544382 AND AD_Language IN ('de_DE','de_CH');

-- Element: MandateType
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585569 /*From ID Server*/,0,'MandateType',TO_TIMESTAMP('2026-10-28 10:00:08','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Mandatstyp','Mandatstyp',TO_TIMESTAMP('2026-10-28 10:00:08','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100);

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Name,PrintName,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Element_ID,t.Name,t.PrintName,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585569
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name='Mandate Type', PrintName='Mandate Type', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 10:00:09','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585569 AND AD_Language='en_US';

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-28 10:00:10','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585569 AND AD_Language IN ('de_DE','de_CH');

-- Column: C_BP_BankAccount_DirectDebitMandate.MandateType
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAllowLogging,IsExcludeFromZoomTargets,IsMandatory,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version)
VALUES (0,593765 /*From ID Server*/,585569,0,17,542151,542522,'XX','MandateType',TO_TIMESTAMP('2026-10-28 10:01:11','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'CORE','D',4,'Y','Y','Y','Y','Y','Mandatstyp','NP',TO_TIMESTAMP('2026-10-28 10:01:11','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0);

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Column_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593765
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585569);

/* DDL */ SELECT public.db_alter_table('C_BP_BankAccount_DirectDebitMandate','ALTER TABLE public.C_BP_BankAccount_DirectDebitMandate ADD COLUMN MandateType VARCHAR(4) DEFAULT ''CORE'' NOT NULL');

/* DDL */ SELECT public.db_alter_table('C_BP_BankAccount_DirectDebitMandate','ALTER TABLE public.C_BP_BankAccount_DirectDebitMandate ADD CONSTRAINT MandateType_Check CHECK (MandateType IN (''CORE'',''B2B''))');
