-- Run mode: SWING_CLIENT

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Produkt
-- Column: QM_Analysis_Report.M_Product_ID
-- 2025-06-27T10:47:14.095Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552716, SeqNo=50,Updated=TO_TIMESTAMP('2025-06-27 10:47:14.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=632339
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Geschäftspartner
-- Column: QM_Analysis_Report.C_BPartner_ID
-- 2025-06-27T10:47:28.466Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2025-06-27 10:47:28.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631283
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Beschreibung
-- Column: QM_Analysis_Report.Description
-- 2025-06-27T10:47:31.399Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2025-06-27 10:47:31.399000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631337
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Endergebnis
-- Column: QM_Analysis_Report.IsFinalResult
-- 2025-06-27T10:47:34.258Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2025-06-27 10:47:34.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=633901
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Produkt
-- Column: QM_Analysis_Report.M_Product_ID
-- 2025-06-27T10:47:40.686Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2025-06-27 10:47:40.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=632339
;

-- Column: QM_Analysis_Report.M_Material_Tracking_ID
-- 2025-06-27T10:50:25.623Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590462,542532,0,30,542468,'XX','M_Material_Tracking_ID',TO_TIMESTAMP('2025-06-27 10:50:25.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.materialtracking',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Material-Vorgang-ID',0,0,TO_TIMESTAMP('2025-06-27 10:50:25.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-27T10:50:25.626Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590462 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-27T10:50:25.803Z
/* DDL */  select update_Column_Translation_From_AD_Element(542532)
;

-- Column: QM_Analysis_Report.M_Material_Tracking_ID
-- 2025-06-27T11:00:50.716Z
UPDATE AD_Column SET ColumnSQL='(SELECT ai.value  from qm_analysis_report qm           JOIN M_AttributeSetInstance asi on qm.M_AttributeSetInstance_ID = asi.M_AttributeSetInstance_ID           JOIN M_AttributeInstance ai on asi.m_attributesetinstance_id = ai.m_attributesetinstance_id  where ai.M_Attribute_ID = 540018    AND qm.qm_analysis_report_id = qm_analysis_report_id)', IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2025-06-27 11:00:50.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590462
;

-- Field: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> Material-Vorgang-ID
-- Column: QM_Analysis_Report.M_Material_Tracking_ID
-- 2025-06-27T11:01:05.090Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590462,747889,0,547945,TO_TIMESTAMP('2025-06-27 11:01:04.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Material-Vorgang-ID',TO_TIMESTAMP('2025-06-27 11:01:04.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-27T11:01:05.093Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747889 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-27T11:01:05.096Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542532)
;

-- 2025-06-27T11:01:05.130Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747889
;

-- 2025-06-27T11:01:05.135Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747889)
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Material-Vorgang-ID
-- Column: QM_Analysis_Report.M_Material_Tracking_ID
-- 2025-06-27T11:01:25.981Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747889,0,547945,552716,634190,'F',TO_TIMESTAMP('2025-06-27 11:01:25.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Material-Vorgang-ID',60,0,0,TO_TIMESTAMP('2025-06-27 11:01:25.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Material-Vorgang-ID
-- Column: QM_Analysis_Report.M_Material_Tracking_ID
-- 2025-06-27T11:01:38.805Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2025-06-27 11:01:38.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=634190
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Geschäftspartner
-- Column: QM_Analysis_Report.C_BPartner_ID
-- 2025-06-27T11:03:35.569Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-06-27 11:03:35.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631283
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Beschreibung
-- Column: QM_Analysis_Report.Description
-- 2025-06-27T11:03:35.575Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-06-27 11:03:35.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631337
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Endergebnis
-- Column: QM_Analysis_Report.IsFinalResult
-- 2025-06-27T11:03:35.581Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-06-27 11:03:35.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=633901
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Produkt
-- Column: QM_Analysis_Report.M_Product_ID
-- 2025-06-27T11:03:35.586Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-06-27 11:03:35.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=632339
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> main.Material-Vorgang-ID
-- Column: QM_Analysis_Report.M_Material_Tracking_ID
-- 2025-06-27T11:03:35.593Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-06-27 11:03:35.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=634190
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 10 -> product.Merkmale
-- Column: QM_Analysis_Report.M_AttributeSetInstance_ID
-- 2025-06-27T11:03:35.598Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-06-27 11:03:35.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=632340
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20 -> doctype.Belegart
-- Column: QM_Analysis_Report.C_DocType_ID
-- 2025-06-27T11:03:35.603Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-06-27 11:03:35.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631287
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20 -> doctype.Nr.
-- Column: QM_Analysis_Report.DocumentNo
-- 2025-06-27T11:03:35.609Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-06-27 11:03:35.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631288
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20 -> doctype.Belegdatum
-- Column: QM_Analysis_Report.DateDoc
-- 2025-06-27T11:03:35.614Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-06-27 11:03:35.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631338
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20 -> doctype.Referenz
-- Column: QM_Analysis_Report.POReference
-- 2025-06-27T11:03:35.619Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-06-27 11:03:35.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631289
;

-- UI Element: Analysebericht(541876,D) -> QM_Analysis_Report(547945,D) -> main -> 20 -> org.Sektion
-- Column: QM_Analysis_Report.AD_Org_ID
-- 2025-06-27T11:03:35.624Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-06-27 11:03:35.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631297
;

-- SysConfig Name: MaterialTracking_Query_IgnorePartnerAndProduct
-- SysConfig Value: N
-- 2025-06-27T12:09:23.137Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541765,'S',TO_TIMESTAMP('2025-06-27 12:09:22.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','MaterialTracking_Query_IgnorePartnerAndProduct',TO_TIMESTAMP('2025-06-27 12:09:22.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N')
;

-- Column: QM_Analysis_Report.M_Material_Tracking_ID
-- Column SQL (old): (SELECT ai.value  from qm_analysis_report qm           JOIN M_AttributeSetInstance asi on qm.M_AttributeSetInstance_ID = asi.M_AttributeSetInstance_ID           JOIN M_AttributeInstance ai on asi.m_attributesetinstance_id = ai.m_attributesetinstance_id  where ai.M_Attribute_ID = 540018    AND qm.qm_analysis_report_id = qm_analysis_report_id)
-- 2025-06-27T12:23:21.260Z
UPDATE AD_Column SET ColumnSQL='(SELECT ai.value::numeric  from qm_analysis_report qm           JOIN M_AttributeSetInstance asi on qm.M_AttributeSetInstance_ID = asi.M_AttributeSetInstance_ID           JOIN M_AttributeInstance ai on asi.m_attributesetinstance_id = ai.m_attributesetinstance_id  where ai.M_Attribute_ID = 540018    AND qm.qm_analysis_report_id = qm_analysis_report_id)',Updated=TO_TIMESTAMP('2025-06-27 12:23:21.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590462
;



-- SysConfig Name: MaterialTracking_Query_IgnorePartnerAndProduct
-- SysConfig Value: Y
-- 2025-06-27T12:52:16.179Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='When ''N'' , the material tracking attribute value must belong to the partner and product of the document.
When ''Y'', the material tracking attribute value could belong to any partner and product.',Updated=TO_TIMESTAMP('2025-06-27 12:52:16.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541765
;

