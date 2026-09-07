-- gh26253 - Cost Revaluation: add RevaluationSource + CopyFrom cost element
-- IDs allocated from idserver.metas.de:
--   AD_Reference       542117 (RevaluationSource list)
--   AD_Reference       542118 (Table ref to M_CostElement, for the source-cost-element FK)
--   AD_Ref_List        544317 (Calculated)
--   AD_Ref_List        544318 (CopyFromCostElement)
--   AD_Element         585099 (RevaluationSource column)
--   AD_Element         585100 (CopyFrom_M_CostElement_ID column)
--   AD_Column          592961 (M_CostRevaluation.RevaluationSource)
--   AD_Column          592962 (M_CostRevaluation.CopyFrom_M_CostElement_ID)
--   AD_Field           781420 (RevaluationSource on tab 546464 "Cost Revaluation")
--   AD_Field           781421 (CopyFrom_M_CostElement_ID on tab 546464)
--   AD_UI_Element      652536 (RevaluationSource)
--   AD_UI_Element      652537 (CopyFrom_M_CostElement_ID)

-------------------------------------------------------------------
-- 1) RevaluationSource: List reference (AD_Reference + AD_Ref_List)
-------------------------------------------------------------------

INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ValidationType, Name, EntityType, IsOrderByValue)
VALUES (0, 0, 542117 /*From ID Server*/, 'Y', TO_TIMESTAMP('2026-07-14 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-14 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'L', 'M_CostRevaluation RevaluationSource', 'D', 'N')
;

INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Help, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Help, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Reference_ID=542117
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- AD_Ref_List: Calculated (default = existing behaviour)
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Name, Description, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, EntityType)
VALUES (542117, 0, 0, 'Y', TO_TIMESTAMP('2026-07-14 14:00:01','YYYY-MM-DD HH24:MI:SS'), 100, 'Berechnet', 'Die Kosten werden für diese Neubewertung neu berechnet (Standardverhalten).', TO_TIMESTAMP('2026-07-14 14:00:01','YYYY-MM-DD HH24:MI:SS'), 100, 544317 /*From ID Server*/, 'Calculated', 'Calculated', 'D')
;

-- AD_Ref_List: CopyFromCostElement (value-neutral copy from another cost element)
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Name, Description, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, EntityType)
VALUES (542117, 0, 0, 'Y', TO_TIMESTAMP('2026-07-14 14:00:02','YYYY-MM-DD HH24:MI:SS'), 100, 'Übernahme aus Kostenart', 'Die Kosten werden unverändert von der unter "Quell-Kostenart" gewählten Kostenart übernommen.', TO_TIMESTAMP('2026-07-14 14:00:02','YYYY-MM-DD HH24:MI:SS'), 100, 544318 /*From ID Server*/, 'CopyFromCostElement', 'CopyFromCostElement', 'D')
;

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID IN (544317, 544318)
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Calculated', Description='Cost is recalculated for this revaluation (default behaviour).',
  Updated=TO_TIMESTAMP('2026-07-14 14:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544317
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Copy from cost element', Description='Cost is copied unchanged from the cost element selected under "Copy from cost element".',
  Updated=TO_TIMESTAMP('2026-07-14 14:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544318
;

-------------------------------------------------------------------
-- 2) RevaluationSource: AD_Element + AD_Column + physical column
-------------------------------------------------------------------

INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, AD_Element_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (0, 0, 585099 /*From ID Server*/, 'Y', TO_TIMESTAMP('2026-07-14 14:00:05','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-14 14:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
  'RevaluationSource', 'Neubewertungsquelle', 'Neubewertungsquelle', 'Legt fest, wie die Kosten dieser Neubewertung ermittelt werden.',
  'Wählen Sie, ob die Kosten neu berechnet oder unverändert von einer anderen Kostenart übernommen werden.', 'D')
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585099
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Revaluation Source', PrintName='Revaluation Source', Description='Determines how the cost for this revaluation is derived.',
  Help='Choose whether the cost is recalculated or copied unchanged from another cost element.',
  Updated=TO_TIMESTAMP('2026-07-14 14:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585099
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-14 14:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=585099
;

-- Minimal explicit column set: only non-default columns listed (Is* flags rely on AD_Column defaults).
-- RevaluationSource is a List column (AD_Reference_ID=17), NOT NULL DEFAULT 'Calculated' → IsMandatory='Y'.
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, AD_Element_ID, AD_Table_ID, AD_Reference_ID, AD_Reference_Value_ID, ColumnName, Name, Description, DefaultValue, FieldLength, IsMandatory, IsUpdateable, IsSyncDatabase, EntityType, PersonalDataCategory, Version, Created, CreatedBy, Updated, UpdatedBy)
VALUES (592961 /*From ID Server*/, 0, 0, 585099, 542190, 17, 542117, 'RevaluationSource', 'Neubewertungsquelle', 'Legt fest, wie die Kosten dieser Neubewertung ermittelt werden.', 'Calculated', 40, 'Y', 'Y', 'Y', 'D', 'NP', 0, TO_TIMESTAMP('2026-07-14 14:01:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-14 14:01:00','YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592961
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(585099)
;

/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN RevaluationSource VARCHAR(40) DEFAULT ''Calculated'' NOT NULL')
;

ALTER TABLE M_CostRevaluation ADD CONSTRAINT RevaluationSource_Check CHECK (RevaluationSource IN ('Calculated','CopyFromCostElement'))
;

-------------------------------------------------------------------
-- 3) CopyFrom_M_CostElement_ID: Table reference to M_CostElement (needed
--    because the column name is prefixed and does not equal <TableName>_ID)
-------------------------------------------------------------------

INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ValidationType, Name, EntityType, IsOrderByValue)
VALUES (0, 0, 542118 /*From ID Server*/, 'Y', TO_TIMESTAMP('2026-07-14 14:00:09','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-14 14:00:09','YYYY-MM-DD HH24:MI:SS'), 100, 'T', 'M_CostElement (Trx)', 'D', 'N')
;

INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Help, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Help, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Reference_ID=542118
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

INSERT INTO AD_Ref_Table (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Table_ID, AD_Key, AD_Display, IsValueDisplayed, WhereClause, EntityType, ShowInactiveValues)
VALUES (542118, 0, 0, 'Y', TO_TIMESTAMP('2026-07-14 14:00:10','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-14 14:00:10','YYYY-MM-DD HH24:MI:SS'), 100, 770, 13452, 13460, 'Y', 'M_CostElement.IsActive=''Y''', 'D', 'N')
;

-------------------------------------------------------------------
-- 4) CopyFrom_M_CostElement_ID: AD_Element + AD_Column + physical column
-------------------------------------------------------------------

INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, AD_Element_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (0, 0, 585100 /*From ID Server*/, 'Y', TO_TIMESTAMP('2026-07-14 14:00:11','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-14 14:00:11','YYYY-MM-DD HH24:MI:SS'), 100,
  'CopyFrom_M_CostElement_ID', 'Quell-Kostenart', 'Quell-Kostenart', 'Kostenart, aus der die Kosten wertneutral übernommen werden.',
  'Nur relevant, wenn als Neubewertungsquelle "Übernahme aus Kostenart" gewählt wurde: Kostenart, deren Kosten unverändert übernommen werden sollen.', 'D')
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585100
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Source Cost Element', PrintName='Source Cost Element', Description='Cost element the cost is copied from, unchanged.',
  Help='Only relevant when Revaluation Source is set to "Copy from cost element": the cost element whose cost is copied unchanged.',
  Updated=TO_TIMESTAMP('2026-07-14 14:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585100
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-14 14:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=585100
;

-- CopyFrom_M_CostElement_ID is a Table-reference FK (AD_Reference_ID=19), nullable → IsMandatory='N'.
-- DDL_NoForeignKey='Y': the FK constraint is created explicitly below, so AD sync must not add a duplicate.
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, AD_Element_ID, AD_Table_ID, AD_Reference_ID, AD_Reference_Value_ID, ColumnName, Name, Description, FieldLength, IsMandatory, IsUpdateable, IsSyncDatabase, DDL_NoForeignKey, EntityType, PersonalDataCategory, Version, Created, CreatedBy, Updated, UpdatedBy)
VALUES (592962 /*From ID Server*/, 0, 0, 585100, 542190, 18, 542118, 'CopyFrom_M_CostElement_ID', 'Quell-Kostenart', 'Kostenart, aus der die Kosten wertneutral übernommen werden.', 10, 'N', 'Y', 'Y', 'Y', 'D', 'NP', 0, TO_TIMESTAMP('2026-07-14 14:01:10','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-14 14:01:10','YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592962
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(585100)
;

/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN CopyFrom_M_CostElement_ID NUMERIC(10)')
;

ALTER TABLE M_CostRevaluation ADD CONSTRAINT CopyFromMCostElement_MCostRevaluation FOREIGN KEY (CopyFrom_M_CostElement_ID) REFERENCES public.M_CostElement DEFERRABLE INITIALLY DEFERRED
;

-------------------------------------------------------------------
-- 5) AD_Field + AD_UI_Element on the "Cost Revaluation" tab (546464,
--    window 541568 "Kosten Neubewertung"), element group 549560 ("main"),
--    right after the existing M_CostElement_ID field (AD_UI_Element seqno 15)
-------------------------------------------------------------------

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy)
VALUES (0,592961,781420 /*From ID Server*/,0,546464,0,TO_TIMESTAMP('2026-07-14 14:00:15','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wie die Kosten dieser Neubewertung ermittelt werden.',0,NULL,'D',0,'Y','Y','Y','N','N','N','N','N','Neubewertungsquelle',0,60,0,1,1,TO_TIMESTAMP('2026-07-14 14:00:15','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781420
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585099)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=781420
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(781420)
;

INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781420,0,546464,549560,652536 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-14 14:00:16','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, wie die Kosten dieser Neubewertung ermittelt werden.','Y','N','N','Y','Y','N','N',0,'Neubewertungsquelle',16,60,0,TO_TIMESTAMP('2026-07-14 14:00:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- CopyFrom_M_CostElement_ID field: only shown when RevaluationSource='CopyFromCostElement'
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy)
VALUES (0,592962,781421 /*From ID Server*/,0,546464,0,TO_TIMESTAMP('2026-07-14 14:00:17','YYYY-MM-DD HH24:MI:SS'),100,'Kostenart, aus der die Kosten wertneutral übernommen werden.',0,'@RevaluationSource@=CopyFromCostElement','D',0,'Y','Y','N','N','N','N','N','N','Quell-Kostenart',0,0,0,1,1,TO_TIMESTAMP('2026-07-14 14:00:17','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781421
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585100)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=781421
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(781421)
;

INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781421,0,546464,549560,652537 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-14 14:00:18','YYYY-MM-DD HH24:MI:SS'),100,'Kostenart, aus der die Kosten wertneutral übernommen werden.','Y','N','N','Y','N','N','N',0,'Quell-Kostenart',17,0,0,TO_TIMESTAMP('2026-07-14 14:00:18','YYYY-MM-DD HH24:MI:SS'),100)
;
