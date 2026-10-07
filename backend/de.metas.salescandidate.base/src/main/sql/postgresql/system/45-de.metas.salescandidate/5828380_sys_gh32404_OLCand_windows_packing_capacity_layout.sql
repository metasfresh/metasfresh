-- Order candidate windows 541952 (Auftragsdisposition) and 540095 (Auftragsdisposition (EDI-Import)):
-- the packing-capacity fields sat alone in a section at the bottom of the form, apart from the product and packing fields,
-- and the capacity from the ORDERS file (QtyItemCapacity) was only visible in advanced edit.
-- Move all three into a new element group "capacity" in the product/partner section, right below the effective values:
--   Verpackungskapazität int. (master data), Verpackungskapazität (from the order), Manuelle Verpackungskapazität (which one is used).
-- IDs allocated from idserver.metas.de on 2026-10-07: AD_UI_ElementGroup 555807, 555808

-- Window 541952, tab 548442, section 546965, column 548484 (holds group "read" SeqNo 10 and "ship" SeqNo 20)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548484,555807 /*From ID Server*/,TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','capacity',15,TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- QtyItemCapacityInternal
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555807, SeqNo=10, IsAdvancedField='N', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=637363
;

-- QtyItemCapacity
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555807, SeqNo=20, IsAdvancedField='N', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=637362
;

-- IsManualQtyItemCapacity
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555807, SeqNo=30, IsAdvancedField='N', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=637361
;

-- Window 540095, tab 540282, section 540490, column 540658 (holds group "read" SeqNo 10 and "ship" SeqNo 20)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540658,555808 /*From ID Server*/,TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','capacity',15,TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- QtyItemCapacityInternal
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555808, SeqNo=10, IsAdvancedField='N', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=605236
;

-- QtyItemCapacity
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555808, SeqNo=20, IsAdvancedField='N', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=547209
;

-- IsManualQtyItemCapacity
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=555808, SeqNo=30, IsAdvancedField='N', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=605237
;

-- Element 580710 (C_OLCand.QtyItemCapacityInternal, its only usage): the en_US name was still German
UPDATE AD_Element_Trl SET Name='Internal packaging capacity', PrintName='Internal packaging capacity', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=580710 AND AD_Language='en_US'
;

/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580710,'en_US')
;

-- Spelling in the en_US descriptions of the two other capacity elements, now shown in the main form (pure typo fixes, correct in all usages)
UPDATE AD_Element_Trl SET Description='If "no", then the internal packaging capacity is applied', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=542287 AND AD_Language='en_US'
;

/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542287,'en_US')
;

UPDATE AD_Element_Trl SET Description='Capacity in the respective product''s unit of measurement', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=542232 AND AD_Language='en_US'
;

/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542232,'en_US')
;
