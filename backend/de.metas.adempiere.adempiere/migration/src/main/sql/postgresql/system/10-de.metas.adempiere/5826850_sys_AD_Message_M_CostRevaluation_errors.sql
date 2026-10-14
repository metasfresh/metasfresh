-- Error messages of the Kosten Neubewertung (cost revaluation) document and its line quick-input.

-- M_CostRevaluation.LineAlreadyExistsForProduct
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545870 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Für das Produkt {0} gibt es in dieser Kosten Neubewertung bereits eine Zeile.','E',TO_TIMESTAMP('2026-09-29 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.LineAlreadyExistsForProduct');
UPDATE AD_Message SET ErrorCode='CostRevaluationLineAlreadyExists', Updated=TO_TIMESTAMP('2026-09-29 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545870;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545870
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='This cost revaluation already has a line for product {0}.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545870;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545870;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545870;

-- M_CostRevaluation.NoCurrentCostForProduct
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545871 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Für das Produkt {0} konnten keine aktuellen Kosten ermittelt werden. Bitte die Kostenrechnungs-Einstellungen des Produkts prüfen.','E',TO_TIMESTAMP('2026-09-29 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.NoCurrentCostForProduct');
UPDATE AD_Message SET ErrorCode='CostRevaluationNoCurrentCost', Updated=TO_TIMESTAMP('2026-09-29 10:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545871;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545871
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='No current cost could be determined for product {0}. Please check the product''s costing setup.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545871;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545871;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545871;

-- M_CostRevaluation.OrgRequiredForOrgCostingLevel
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545872 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 10:00:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Das Produkt {0} wird auf Organisationsebene bewertet. Bitte in der Kosten Neubewertung eine Organisation auswählen.','E',TO_TIMESTAMP('2026-09-29 10:00:10','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.OrgRequiredForOrgCostingLevel');
UPDATE AD_Message SET ErrorCode='CostRevaluationOrgRequired', Updated=TO_TIMESTAMP('2026-09-29 10:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545872;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545872
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='Product {0} is costed at organization level. Please select an organization on the cost revaluation.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545872;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545872;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545872;

-- M_CostRevaluation.AmbiguousCurrentCost
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545873 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 10:00:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Für das Produkt {0} gibt es {1} passende Kostendatensätze. Es kann nicht eindeutig bestimmt werden, welcher neu bewertet werden soll.','E',TO_TIMESTAMP('2026-09-29 10:00:15','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.AmbiguousCurrentCost');
UPDATE AD_Message SET ErrorCode='CostRevaluationAmbiguousCurrentCost', Updated=TO_TIMESTAMP('2026-09-29 10:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545873;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545873
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='Product {0} has {1} matching cost records. It cannot be determined which one to revaluate.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545873;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545873;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545873;

-- M_CostRevaluation.NewCostPriceNegative
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545874 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 10:00:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Der neue Einstandspreis darf nicht negativ sein.','E',TO_TIMESTAMP('2026-09-29 10:00:20','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.NewCostPriceNegative');
UPDATE AD_Message SET ErrorCode='CostRevaluationNewCostPriceNegative', Updated=TO_TIMESTAMP('2026-09-29 10:00:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545874;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545874
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='The new cost price must not be negative.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545874;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545874;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545874;

-- M_CostRevaluation.DocumentNotDraft
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545875 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 10:00:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zeilen können nur hinzugefügt werden, solange die Kosten Neubewertung nicht fertiggestellt ist.','E',TO_TIMESTAMP('2026-09-29 10:00:25','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.DocumentNotDraft');
UPDATE AD_Message SET ErrorCode='CostRevaluationDocumentNotDraft', Updated=TO_TIMESTAMP('2026-09-29 10:00:26','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545875;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545875
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='Lines can only be added while the cost revaluation is not completed.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545875;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545875;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545875;

-- M_CostRevaluation.DeleteLinesFirstError
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545876 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-29 10:00:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Buchführungs-Schema, Kostenart und Startdatum der Bewertung (das dem Buchungsdatum folgt, solange es nicht separat gesetzt wurde) können nur geändert werden, wenn die Kosten Neubewertung keine Zeilen hat. Bitte zuerst die Zeilen löschen.','E',TO_TIMESTAMP('2026-09-29 10:00:30','YYYY-MM-DD HH24:MI:SS'),100,'M_CostRevaluation.DeleteLinesFirstError');
UPDATE AD_Message SET ErrorCode='CostRevaluationDeleteLinesFirst', Updated=TO_TIMESTAMP('2026-09-29 10:00:31','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545876;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545876
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID);
UPDATE AD_Message_Trl SET MsgText='Accounting Schema, Cost Element and Evaluation Start Date (which follows the Accounting Date unless it was set separately) can only be changed while the cost revaluation has no lines. Please delete the lines first.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545876;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545876;
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 10:00:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545876;
