-- gh#28337: Add translations for WriteOffType discriminator on C_AllocationLine
-- This is a follow-up to 5789810_sys_gh28337_C_AllocationLine_WriteOffType.sql

-- 1. AD_Reference_Trl (ID=542053, "C_AllocationLine WriteOffType")
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=542053
AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2. AD_Ref_List_Trl (ID=544125, "WO" = "Standard Write-Off")
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544125
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 3. AD_Ref_List_Trl (ID=544126, "BF" = "Bank Fee")
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=544126
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 4. AD_Element_Trl (ID=584561, "WriteOffType")
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID,
                            CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName,
                            WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
                            IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID,
       t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName,
       t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=584561
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 5. AD_Column_Trl (ID=592061, "WriteOffType") — skeleton rows for each system language
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592061
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 6. Set German translations (de_DE, de_CH)
-- AD_Reference base record: Name is already in English ("C_AllocationLine WriteOffType") — that's fine, it's a technical name
-- AD_Ref_List: set German names
UPDATE AD_Ref_List SET Name='Standardabschreibung', Updated=TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy=99 WHERE AD_Ref_List_ID=544125;
UPDATE AD_Ref_List SET Name='Bankgebühr', Updated=TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy=99 WHERE AD_Ref_List_ID=544126;

UPDATE AD_Ref_List_Trl SET Name='Standardabschreibung', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy=99
WHERE AD_Ref_List_ID=544125 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Ref_List_Trl SET Name='Bankgebühr', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy=99
WHERE AD_Ref_List_ID=544126 AND AD_Language IN ('de_DE','de_CH');

-- AD_Ref_List: set English translations
UPDATE AD_Ref_List_Trl SET Name='Standard Write-Off', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy=99
WHERE AD_Ref_List_ID=544125 AND AD_Language='en_US';
UPDATE AD_Ref_List_Trl SET Name='Bank Fee', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy=99
WHERE AD_Ref_List_ID=544126 AND AD_Language='en_US';

-- AD_Element: set German base (Name, PrintName)
UPDATE AD_Element SET Name='Abschreibungsart', PrintName='Abschreibungsart', Updated=TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy=99
WHERE AD_Element_ID=584561;

UPDATE AD_Element_Trl SET Name='Abschreibungsart', PrintName='Abschreibungsart', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy=99
WHERE AD_Element_ID=584561 AND AD_Language IN ('de_DE','de_CH');

-- AD_Element: set English translation
UPDATE AD_Element_Trl SET Name='Write-Off Type', PrintName='Write-Off Type', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-02-24 08:00','YYYY-MM-DD HH24:MI'), UpdatedBy=99
WHERE AD_Element_ID=584561 AND AD_Language='en_US';

-- 7. Propagate translations from AD_Element_Trl into AD_Column_Trl, AD_Field_Trl, etc.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584561);
SELECT update_Column_Translation_From_AD_Element(584561);
