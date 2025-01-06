-- Run mode: SWING_CLIENT

-- Reference: Computing Methods
-- Value: InformativeLogs
-- ValueName: InformativeLogs
-- 2024-05-08T15:30:33.253Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541838,543679,TO_TIMESTAMP('2024-05-08 18:30:33.068','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Informative Logs',TO_TIMESTAMP('2024-05-08 18:30:33.068','YYYY-MM-DD HH24:MI:SS.US'),100,'InformativeLogs','InformativeLogs')
;

-- 2024-05-08T15:30:33.262Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543679 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Computing Methods
-- Value: InformativeLogs
-- ValueName: InformativeLogs
-- 2024-05-08T15:30:36.142Z
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2024-05-08 18:30:36.141','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543679
;



-- Reference: Computing Methods
-- Value: InformativeLogs
-- ValueName: InformativeLogs
-- 2024-05-08T15:46:19.641Z
UPDATE AD_Ref_List SET Description='Dieser Vertragsbausteintyp erzeugt informative Logs über abgeschlossene Bestellzeilen und die erstellten Vertragsbaustein-Verträge. Für sie wird kein Rechnungskandidat erstellt.',Updated=TO_TIMESTAMP('2024-05-08 18:46:19.641','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543679
;

-- 2024-05-08T15:46:19.646Z
UPDATE AD_Ref_List_Trl trl SET Description='Dieser Vertragsbausteintyp erzeugt informative Logs über abgeschlossene Bestellzeilen und die erstellten Vertragsbaustein-Verträge. Für sie wird kein Rechnungskandidat erstellt.' WHERE AD_Ref_List_ID=543679 AND AD_Language='de_DE'
;

-- Reference Item: Computing Methods -> InformativeLogs_InformativeLogs
-- 2024-05-08T15:46:23.037Z
UPDATE AD_Ref_List_Trl SET Description='Dieser Vertragsbausteintyp erzeugt informative Logs über abgeschlossene Bestellzeilen und die erstellten Vertragsbaustein-Verträge. Für sie wird kein Rechnungskandidat erstellt.',Updated=TO_TIMESTAMP('2024-05-08 18:46:23.037','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543679
;

-- Reference Item: Computing Methods -> InformativeLogs_InformativeLogs
-- 2024-05-08T15:46:37.972Z
UPDATE AD_Ref_List_Trl SET Description='This computing method type generates informative logs about completed purchase order lines and the creation of the modular contracts. No Invoice Candidate is created for them.',Updated=TO_TIMESTAMP('2024-05-08 18:46:37.971','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543679
;

