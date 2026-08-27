-- New PP cost-collector type "CostDifferenceDistribution" (Value 170) on the PP_CostCollectorType
-- reference (AD_Reference 53287): the collector that discharges a manufacturing order's WIP cost residual.

-- EntityType EE01 (org.eevolution), matching the reference's other rows.
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
VALUES (0,0,544328 /*From ID Server*/,53287,TO_TIMESTAMP('2026-08-09 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Kostendifferenz-Verteilung',TO_TIMESTAMP('2026-08-09 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'170','CostDifferenceDistribution')
;

-- Seed _Trl rows for every active system language incl. the base.
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID,Description,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544328
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- en_US override: English label
UPDATE AD_Ref_List_Trl SET Name='Cost difference distribution', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-09 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544328
;

-- de_DE / de_CH: mark as actively translated (same German text as the base)
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-09 10:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544328
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-09 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544328
;
