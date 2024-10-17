-- Field: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV) -> Zeile Nr.
-- Column: EDI_Desadv_Pack.Line
-- Field: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> Zeile Nr.
-- Column: EDI_Desadv_Pack.Line
-- 2024-07-29T08:28:43.240Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588875,729145,0,546395,0,TO_TIMESTAMP('2024-07-29 10:28:42','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument',0,'de.metas.esb.edi','Indicates the unique line for a document.  It will also control the display order of the lines within a document.',0,'Y','Y','Y','N','N','N','N','N','Zeile Nr.',0,10,0,1,1,TO_TIMESTAMP('2024-07-29 10:28:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-29T08:28:43.243Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729145 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-29T08:28:43.276Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(439) 
;

-- 2024-07-29T08:28:43.301Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729145
;

-- 2024-07-29T08:28:43.305Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729145)
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV).Zeile Nr.
-- Column: EDI_Desadv_Pack.Line
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> main -> 10 -> default.Zeile Nr.
-- Column: EDI_Desadv_Pack.Line
-- 2024-07-29T08:29:57.853Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729145,0,546395,549396,625034,'F',TO_TIMESTAMP('2024-07-29 10:29:57','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','N','Y','N','N','N',0,'Zeile Nr.',11,0,0,TO_TIMESTAMP('2024-07-29 10:29:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV).Handling Unit
-- Column: EDI_Desadv_Pack.M_HU_ID
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> main -> 10 -> default.Handling Unit
-- Column: EDI_Desadv_Pack.M_HU_ID
-- 2024-07-29T08:30:20.908Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2024-07-29 10:30:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609659
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV).SSCC18
-- Column: EDI_Desadv_Pack.IPA_SSCC18
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> main -> 10 -> default.SSCC18
-- Column: EDI_Desadv_Pack.IPA_SSCC18
-- 2024-07-29T08:31:11.866Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2024-07-29 10:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609657
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV).manuelle SSCC18
-- Column: EDI_Desadv_Pack.IsManual_IPA_SSCC18
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> main -> 10 -> default.manuelle SSCC18
-- Column: EDI_Desadv_Pack.IsManual_IPA_SSCC18
-- 2024-07-29T08:31:20.086Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2024-07-29 10:31:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609656
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV).EDI_Desadv_Parent_Pack_ID
-- Column: EDI_Desadv_Pack.EDI_Desadv_Parent_Pack_ID
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> main -> 10 -> default.EDI_Desadv_Parent_Pack_ID
-- Column: EDI_Desadv_Pack.EDI_Desadv_Parent_Pack_ID
-- 2024-07-29T08:31:29.697Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2024-07-29 10:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609658
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV).Zeile Nr.
-- Column: EDI_Desadv_Pack.Line
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> main -> 10 -> default.Zeile Nr.
-- Column: EDI_Desadv_Pack.Line
-- 2024-07-29T08:32:28.526Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-07-29 10:32:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625034
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV).Handling Unit
-- Column: EDI_Desadv_Pack.M_HU_ID
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> main -> 10 -> default.Handling Unit
-- Column: EDI_Desadv_Pack.M_HU_ID
-- 2024-07-29T08:32:28.543Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-07-29 10:32:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609659
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV).manuelle SSCC18
-- Column: EDI_Desadv_Pack.IsManual_IPA_SSCC18
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> main -> 10 -> default.manuelle SSCC18
-- Column: EDI_Desadv_Pack.IsManual_IPA_SSCC18
-- 2024-07-29T08:32:28.561Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-07-29 10:32:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609656
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV).SSCC18
-- Column: EDI_Desadv_Pack.IPA_SSCC18
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> main -> 10 -> default.SSCC18
-- Column: EDI_Desadv_Pack.IPA_SSCC18
-- 2024-07-29T08:32:28.578Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-07-29 10:32:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609657
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV).LU Verpackungscode
-- Column: EDI_Desadv_Pack.M_HU_PackagingCode_LU_ID
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> main -> 20 -> packaging identifiers.LU Verpackungscode
-- Column: EDI_Desadv_Pack.M_HU_PackagingCode_LU_ID
-- 2024-07-29T08:32:28.596Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-07-29 10:32:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609662
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV).LU Gebinde-GTIN
-- Column: EDI_Desadv_Pack.GTIN_LU_PackingMaterial
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> main -> 20 -> packaging identifiers.LU Gebinde-GTIN
-- Column: EDI_Desadv_Pack.GTIN_LU_PackingMaterial
-- 2024-07-29T08:32:28.612Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-07-29 10:32:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609663
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV).Aktiv
-- Column: EDI_Desadv_Pack.IsActive
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> main -> 20 -> flags and org.Aktiv
-- Column: EDI_Desadv_Pack.IsActive
-- 2024-07-29T08:32:28.628Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-07-29 10:32:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609660
;

-- UI Element: EDI Lieferavis Pack (DESADV) -> EDI Lieferavis Pack (DESADV).Sektion
-- Column: EDI_Desadv_Pack.AD_Org_ID
-- UI Element: EDI Lieferavis Pack (DESADV)(541543,de.metas.esb.edi) -> EDI Lieferavis Pack (DESADV)(546395,de.metas.esb.edi) -> main -> 20 -> flags and org.Sektion
-- Column: EDI_Desadv_Pack.AD_Org_ID
-- 2024-07-29T08:32:28.644Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-07-29 10:32:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=609661
;

