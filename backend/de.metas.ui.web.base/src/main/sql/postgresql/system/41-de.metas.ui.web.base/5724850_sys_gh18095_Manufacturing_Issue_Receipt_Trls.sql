-- Run mode: SWING_CLIENT

-- Value: de.metas.ui.web.pporder.MANUFACTURING_ISSUE_RECEIPT_CAPTION
-- 2024-05-29T09:58:38.641Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545416,0,TO_TIMESTAMP('2024-05-29 12:58:38.078','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Produktionsauftrag Zuteilung/Entnahme','I',TO_TIMESTAMP('2024-05-29 12:58:38.078','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.ui.web.pporder.MANUFACTURING_ISSUE_RECEIPT_CAPTION')
;

-- 2024-05-29T09:58:38.652Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545416 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.ui.web.pporder.MANUFACTURING_ISSUE_RECEIPT_CAPTION
-- 2024-05-29T09:58:49.566Z
UPDATE AD_Message_Trl SET MsgText='Manufacturing Issue/Receipt',Updated=TO_TIMESTAMP('2024-05-29 12:58:49.566','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545416
;

-- Element: PlanningStatus
-- 2024-05-29T09:59:09.386Z
UPDATE AD_Element_Trl SET Name='Planungsstatus', PrintName='Planungsstatus',Updated=TO_TIMESTAMP('2024-05-29 12:59:09.386','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=543313 AND AD_Language='de_DE'
;

-- 2024-05-29T09:59:09.387Z
UPDATE AD_Element SET Name='Planungsstatus', PrintName='Planungsstatus' WHERE AD_Element_ID=543313
;

-- 2024-05-29T09:59:10.031Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543313,'de_DE')
;

-- 2024-05-29T09:59:10.038Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543313,'de_DE')
;

-- Element: PlanningStatus
-- 2024-05-29T09:59:13.908Z
UPDATE AD_Element_Trl SET Name='Planungsstatus', PrintName='Planungsstatus',Updated=TO_TIMESTAMP('2024-05-29 12:59:13.907','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=543313 AND AD_Language='de_CH'
;

-- 2024-05-29T09:59:13.910Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543313,'de_CH')
;

