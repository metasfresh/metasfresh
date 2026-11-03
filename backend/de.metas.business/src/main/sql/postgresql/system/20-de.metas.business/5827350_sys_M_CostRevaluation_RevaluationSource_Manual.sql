-- Revaluation source "Calculated" is renamed to "Manual" (the new cost price is entered by the user; nothing is recalculated).
-- Step 2 of 3: existing revaluations, the list value AD_Ref_List 544317, the column default and the help of the
-- RevaluationSource element 585099. German text in every language except en_US (fr_CH carries German, like de_CH).

SELECT backup_table('m_costrevaluation', '_RevaluationSource_Manual')
;

UPDATE M_CostRevaluation SET RevaluationSource='Manual', Updated=TO_TIMESTAMP('2026-10-01 16:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE RevaluationSource = 'Calculated'
;

-- List value
UPDATE AD_Ref_List
SET Value='Manual', ValueName='Manual', Name='Manuell',
    Description='Der neue Kostenpreis wird manuell eingegeben; nichts wird neu berechnet.',
    Updated=TO_TIMESTAMP('2026-10-01 16:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 544317
;

UPDATE AD_Ref_List_Trl
SET Name='Manuell',
    Description='Der neue Kostenpreis wird manuell eingegeben; nichts wird neu berechnet.',
    Updated=TO_TIMESTAMP('2026-10-01 16:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 544317 AND AD_Language <> 'en_US'
;

UPDATE AD_Ref_List_Trl
SET Name='Manual',
    Description='The new cost price is entered manually; nothing is recalculated.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-01 16:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID = 544317 AND AD_Language = 'en_US'
;

-- Column default (the physical default is set by 5827340)
UPDATE AD_Column SET DefaultValue='Manual', Updated=TO_TIMESTAMP('2026-10-01 16:00:04', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 592961 -- M_CostRevaluation.RevaluationSource
;

-- Element help: no longer says the cost is recalculated
UPDATE AD_Element
SET Help='Wählen Sie, ob der neue Kostenpreis manuell eingegeben oder unverändert von einer anderen Kostenart übernommen wird.',
    Updated=TO_TIMESTAMP('2026-10-01 16:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 585099
;

UPDATE AD_Element_Trl
SET Help='Wählen Sie, ob der neue Kostenpreis manuell eingegeben oder unverändert von einer anderen Kostenart übernommen wird.',
    Updated=TO_TIMESTAMP('2026-10-01 16:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 585099 AND AD_Language <> 'en_US'
;

UPDATE AD_Element_Trl
SET Help='Choose whether the new cost price is entered manually or copied unchanged from another cost element.',
    Updated=TO_TIMESTAMP('2026-10-01 16:00:07', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 585099 AND AD_Language = 'en_US'
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585099)
;
