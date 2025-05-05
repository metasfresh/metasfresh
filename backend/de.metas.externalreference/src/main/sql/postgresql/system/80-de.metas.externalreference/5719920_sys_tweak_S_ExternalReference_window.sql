
-- UI Element: Externe Referenz -> External reference.URL im externen system
-- Column: S_ExternalReference.ExternalReferenceURL
-- 2024-03-21T15:19:45.898Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2024-03-21 16:19:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585968
;

-- 2024-03-21T15:20:38.629Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542372,551713,TO_TIMESTAMP('2024-03-21 16:20:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','details',10,TO_TIMESTAMP('2024-03-21 16:20:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Externe Referenz -> External reference.Aktiv
-- Column: S_ExternalReference.IsActive
-- 2024-03-21T15:21:32.106Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551713, SeqNo=10,Updated=TO_TIMESTAMP('2024-03-21 16:21:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=567189
;

-- UI Element: Externe Referenz -> External reference.Version
-- Column: S_ExternalReference.Version
-- 2024-03-21T15:22:34.943Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551713, SeqNo=20,Updated=TO_TIMESTAMP('2024-03-21 16:22:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586795
;

-- UI Element: Externe Referenz -> External reference.URL im externen system
-- Column: S_ExternalReference.ExternalReferenceURL
-- 2024-03-21T15:23:49.285Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551713, SeqNo=30,Updated=TO_TIMESTAMP('2024-03-21 16:23:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585968
;

-- Column: C_BPartner.ExternalId
-- 2024-03-21T15:42:04.180Z
UPDATE AD_Column SET TechnicalNote='!This column is deprecated! Instead of using it, please work with S_ExternalReference.ExternalReference',Updated=TO_TIMESTAMP('2024-03-21 16:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=563299
;

