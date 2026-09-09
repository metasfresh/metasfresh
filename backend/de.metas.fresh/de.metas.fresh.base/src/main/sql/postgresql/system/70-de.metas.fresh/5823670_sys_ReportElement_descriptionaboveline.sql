-- Run mode: SWING_CLIENT

-- Make the report element 'descriptionaboveline' configurable.
--
-- Docs_Sales_InOut_Details gates the new free-text column exactly like its sibling line
-- description does:
--     WHEN report.IsHiddenReportElement(io.C_DocType_ID, 'descriptionaboveline') = 'N' THEN ...
-- That predicate reads C_DocType_ReportElement.ReportElement, which is a List column bound to
-- AD_Reference 541946 'ReportElements'. Without a matching AD_Ref_List value nobody can ever
-- create the C_DocType_ReportElement row, so the gate could never fire and the configurability
-- was dead. Its two neighbours 'description' (Line Description) and 'p_description'
-- (Product Description) are both in that list; this adds the third so the new element can be
-- hidden per document type just like they can.
--
-- The value must stay lowercase and byte-identical to the string in the function above.
--
-- IDs allocated from idserver.metas.de on 2026-09-09:
--   AD_Ref_List 544365 (ReportElements -> descriptionaboveline)

-- 2026-09-09T21:40:00.000Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541946,544365 /*From ID Server*/,TO_TIMESTAMP('2026-09-09 21:40:00','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Free Text Above Line',TO_TIMESTAMP('2026-09-09 21:40:00','YYYY-MM-DD HH24:MI:SS'),100,'descriptionaboveline','DescriptionAboveLine')
;

-- 2026-09-09T21:40:01.000Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544365 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
