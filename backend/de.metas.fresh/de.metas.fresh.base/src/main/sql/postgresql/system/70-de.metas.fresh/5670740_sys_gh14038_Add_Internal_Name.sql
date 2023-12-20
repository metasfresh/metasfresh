-- 2023-01-08T06:50:05.157Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,414,545365,TO_TIMESTAMP('2023-01-08 07:50:04','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-01-08 07:50:04','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2023-01-08T06:50:05.247Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545365 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2023-01-08T06:53:35.420Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546543,545365,TO_TIMESTAMP('2023-01-08 07:53:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-08 07:53:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-08T06:54:01.537Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546543,550209,TO_TIMESTAMP('2023-01-08 07:54:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','InternalName',10,TO_TIMESTAMP('2023-01-08 07:54:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Ressource_OLD -> Ressource.InternalName
-- Column: S_Resource.InternalName
-- 2023-01-08T06:54:50.192Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550209, SeqNo=10,Updated=TO_TIMESTAMP('2023-01-08 07:54:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610372
;

-- UI Element: Ressource_OLD -> Ressource.InternalName
-- Column: S_Resource.InternalName
-- 2023-01-08T06:55:09.876Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-01-08 07:55:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610372
;