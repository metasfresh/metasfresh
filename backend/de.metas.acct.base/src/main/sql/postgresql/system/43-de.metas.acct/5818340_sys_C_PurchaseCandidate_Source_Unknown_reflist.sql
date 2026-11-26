-- Add a truthful 'Unknown' source to the PO Sources reference (AD_Reference 541284).
-- Purchase candidates created before Source existed carry Source=NULL; they get backfilled to 'UNK'
-- (a following script) so the column can become mandatory. 'Unknown' means the origin is not recorded;
-- the sales-order-completion interceptor only auto-orders Source='SO' candidates, so 'UNK' is never auto-ordered.
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,541284,544337 /*From ID Server*/,TO_TIMESTAMP('2026-08-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','Unbekannt',TO_TIMESTAMP('2026-08-11 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'UNK','Unknown')
;

-- seed _Trl for every active system language (base German 'Unbekannt', not yet translated)
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID,Description,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544337
AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- English override
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Unknown', Updated=TO_TIMESTAMP('2026-08-11 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544337
;

-- German (base text already correct) — mark translated
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-11 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Ref_List_ID=544337
;
