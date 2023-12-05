-- Column: WEBUI_KPI.ChartType
-- 2023-11-30T09:48:00.058Z
UPDATE AD_Column SET FieldLength=40,Updated=TO_TIMESTAMP('2023-11-30 11:48:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556298
;

-- 2023-11-30T09:48:01.422Z
INSERT INTO t_alter_column values('webui_kpi','ChartType','VARCHAR(40)',null,null)
;

-- 2023-11-30T09:48:20.802Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540701,543597,TO_TIMESTAMP('2023-11-30 11:48:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','URLs',TO_TIMESTAMP('2023-11-30 11:48:20','YYYY-MM-DD HH24:MI:SS'),100,'urls','URLs')
;

-- 2023-11-30T09:48:20.803Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543597 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2023-11-30T09:48:56.780Z
UPDATE AD_Ref_List SET EntityType='de.metas.ui.web',Updated=TO_TIMESTAMP('2023-11-30 11:48:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543597
;

