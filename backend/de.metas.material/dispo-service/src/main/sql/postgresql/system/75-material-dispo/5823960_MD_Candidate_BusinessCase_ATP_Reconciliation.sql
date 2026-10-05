-- New MD_Candidate_BusinessCase value for the correction candidate an ATP reconciliation run writes, so it is
-- identifiable on the MD_Candidate window instead of looking like any other INVENTORY_UP/DOWN row.

INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544366 /*From ID Server*/,540709,TO_TIMESTAMP('2026-09-10 21:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Korrekturkandidat, den ein ATP-Abgleich-Lauf geschrieben hat.','de.metas.material.dispo','Y','ATP-Abgleich',TO_TIMESTAMP('2026-09-10 21:00:00','YYYY-MM-DD HH24:MI:SS'),100,'ATP_RECONCILIATION','ATP-Abgleich')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID,Description,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544366
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET Description='Correction candidate that an ATP reconciliation run wrote.', Name='ATP reconciliation', Updated=TO_TIMESTAMP('2026-09-10 21:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544366
;
