-- gh31743 - Cost Revaluation: show Posted status + posting-error link on the window.
--
-- M_CostRevaluation (AD_Window 541568 "Kosten Neubewertung", tab 546464 "Cost Revaluation") is a
-- postable document, but its Posted status and PostingError_Issue_ID were not on the window, so an
-- operator could not see from the UI whether the document posted, or why it failed.
--
-- Purely additive UI. No DDL, no data change, no new AD_Element: the columns already exist
-- (Posted = AD_Column 584135 / AD_Element 1308; PostingError_Issue_ID = AD_Column 584136 /
-- AD_Element 577755) and are only being surfaced on the window, read-only.
--
-- Layout: a NEW element group "posted" in the RIGHT AD_UI_Column (546193), at SeqNo 15 — directly
-- above the trailing org+client group (549564, SeqNo 20) and below "status" (549561, SeqNo 10).
-- Follows the house "posting status" pattern (windows 294 / 107 / 53014 / 541656):
--   * Posted                 shown once the document is processed (@Processed/N@='Y'), read-only.
--   * PostingError_Issue_ID  shown only when a posting-error issue exists (@PostingError_Issue_ID/0@>0),
--                            read-only. (AD_Field.IsDisplayed='N' mirrors the house rows — the legacy
--                            Swing flag; WebUI visibility comes from the AD_UI_Element + DisplayLogic.)
-- Posted is ALSO shown in the list/grid (SeqNoGrid 65, Org kept last), matching the majority
-- house pattern (107/294/541656) so unposted/failed documents are scannable from the list.
-- PostingError_Issue_ID stays grid-hidden (as in all four precedent windows).
--
-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_UI_ElementGroup 555639 (new "posted" group, right column 546193, SeqNo 15)
--   AD_Field           783024 (Posted                 on tab 546464, reusing AD_Element 1308)
--   AD_Field           783025 (PostingError_Issue_ID  on tab 546464, reusing AD_Element 577755)
--   AD_UI_Element      653673 (Posted)
--   AD_UI_Element      653674 (PostingError_Issue_ID)

-------------------------------------------------------------------
-- 1) New element group "posted" — right AD_UI_Column 546193, SeqNo 15
--    (between existing groups status[10] and org[20])
-------------------------------------------------------------------

INSERT INTO AD_UI_ElementGroup (AD_Client_ID, AD_Org_ID, AD_UI_ElementGroup_ID, AD_UI_Column_ID, Name, SeqNo, UIStyle, IsActive, Created, CreatedBy, Updated, UpdatedBy)
VALUES (0, 0, 555639 /*From ID Server*/, 546193, 'posted', 15, NULL, 'Y', TO_TIMESTAMP('2026-08-27 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-08-27 12:00:00','YYYY-MM-DD HH24:MI:SS'), 100)
;

-------------------------------------------------------------------
-- 2) Posted — AD_Field + AD_UI_Element (read-only; shown once processed)
-------------------------------------------------------------------

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy)
VALUES (0,584135,783024 /*From ID Server*/,0,546464,0,TO_TIMESTAMP('2026-08-27 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Buchungsstatus',1,'@Processed/N@=''Y''','D',0,'Y','Y','Y','N','N','N','Y','N','Buchungsstatus',0,65,0,1,1,TO_TIMESTAMP('2026-08-27 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=783024
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(1308)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=783024
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(783024)
;

INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,783024,0,546464,555639,653673 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-27 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Buchungsstatus','Y','N','N','Y','Y','N','N',0,'Buchungsstatus',10,65,0,TO_TIMESTAMP('2026-08-27 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-------------------------------------------------------------------
-- 3) PostingError_Issue_ID — AD_Field + AD_UI_Element (read-only;
--    shown only when a posting-error issue exists)
-------------------------------------------------------------------

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy)
VALUES (0,584136,783025 /*From ID Server*/,0,546464,0,TO_TIMESTAMP('2026-08-27 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Verbuchungsfehler',10,'@PostingError_Issue_ID/0@>0','D',0,'Y','N','N','N','N','N','Y','N','Verbuchungsfehler',0,0,0,1,1,TO_TIMESTAMP('2026-08-27 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=783025
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(577755)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=783025
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(783025)
;

INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,783025,0,546464,555639,653674 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-27 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Verbuchungsfehler','Y','N','N','Y','N','N','N',0,'Verbuchungsfehler',20,0,0,TO_TIMESTAMP('2026-08-27 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
