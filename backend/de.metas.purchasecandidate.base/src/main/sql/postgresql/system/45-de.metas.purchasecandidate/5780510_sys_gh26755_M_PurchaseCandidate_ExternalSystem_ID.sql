-- Run mode: SWING_CLIENT

-- Column: C_PurchaseCandidate.ExternalSystem_ID
-- 2025-12-10T13:49:35.442Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591700,583968,0,19,540861,'XX','ExternalSystem_ID',TO_TIMESTAMP('2025-12-10 13:49:35.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','540008','de.metas.purchasecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externes System',0,0,TO_TIMESTAMP('2025-12-10 13:49:35.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-10T13:49:35.458Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591700 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-10T13:49:35.621Z
/* DDL */  select update_Column_Translation_From_AD_Element(583968)
;

-- 2025-12-10T13:49:37.012Z
/* DDL */ SELECT public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN ExternalSystem_ID NUMERIC(10) DEFAULT 540008')
;

-- 2025-12-10T13:49:37.154Z
ALTER TABLE C_PurchaseCandidate ADD CONSTRAINT ExternalSystem_CPurchaseCandidate FOREIGN KEY (ExternalSystem_ID) REFERENCES public.ExternalSystem DEFERRABLE INITIALLY DEFERRED
;

-- Run mode: SWING_CLIENT

-- 2025-12-10T13:54:10.339Z
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540585
;

-- 2025-12-10T13:54:10.349Z
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540585
;

-- 2025-12-10T13:54:29.524Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540848,0,540861,TO_TIMESTAMP('2025-12-10 13:54:29.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Y','C_PurchaseCandidate_External_Ids','N',TO_TIMESTAMP('2025-12-10 13:54:29.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsActive=''Y'' AND COALESCE(ExternalHeaderId, '''') != ''''  AND COALESCE(ExternalLineId, '''') != '''' AND ExternalSystem_ID != NULL')
;

-- 2025-12-10T13:54:29.528Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540848 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-12-10T13:54:48.492Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591700,541497,540848,0,TO_TIMESTAMP('2025-12-10 13:54:48.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2025-12-10 13:54:48.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-10T13:55:04.997Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573209,541498,540848,0,TO_TIMESTAMP('2025-12-10 13:55:04.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',20,TO_TIMESTAMP('2025-12-10 13:55:04.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-10T13:55:11.924Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,573210,541499,540848,0,TO_TIMESTAMP('2025-12-10 13:55:11.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',30,TO_TIMESTAMP('2025-12-10 13:55:11.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-10T13:55:18.845Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,557851,541500,540848,0,TO_TIMESTAMP('2025-12-10 13:55:18.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',40,TO_TIMESTAMP('2025-12-10 13:55:18.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-10T13:55:25.709Z
CREATE UNIQUE INDEX C_PurchaseCandidate_External_Ids ON C_PurchaseCandidate (ExternalSystem_ID,ExternalHeaderId,ExternalLineId,AD_Org_ID) WHERE IsActive='Y' AND COALESCE(ExternalHeaderId, '') != ''  AND COALESCE(ExternalLineId, '') != '' AND ExternalSystem_ID IS NOT NULL
;

-- Run mode: SWING_CLIENT

-- Value: ERR_RECEIPT_SCHEDULE_INVALID_IDENTIFICATION_METHOD
-- 2025-12-10T17:39:21.912Z
UPDATE AD_Message SET MsgText='Es muss genau eine Identifikationsmethode angegeben werden: receiptScheduleId, (externalSystemCode, externalHeaderId und externalLineId) oder orderLineId.',Updated=TO_TIMESTAMP('2025-12-10 17:39:21.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545619
;

-- 2025-12-10T17:39:21.922Z
UPDATE AD_Message_Trl trl SET MsgText='Es muss genau eine Identifikationsmethode angegeben werden: receiptScheduleId, (externalSystemCode, externalHeaderId und externalLineId) oder orderLineId.' WHERE AD_Message_ID=545619 AND AD_Language='de_DE'
;

-- Value: ERR_RECEIPT_SCHEDULE_INVALID_IDENTIFICATION_METHOD
-- 2025-12-10T17:39:29.571Z
UPDATE AD_Message_Trl SET MsgText='Es muss genau eine Identifikationsmethode angegeben werden: receiptScheduleId, (externalSystemCode, externalHeaderId und externalLineId) oder orderLineId.',Updated=TO_TIMESTAMP('2025-12-10 17:39:29.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545619
;

-- 2025-12-10T17:39:29.572Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: ERR_RECEIPT_SCHEDULE_INVALID_IDENTIFICATION_METHOD
-- 2025-12-10T17:39:33.415Z
UPDATE AD_Message_Trl SET MsgText='Es muss genau eine Identifikationsmethode angegeben werden: receiptScheduleId, (externalSystemCode, externalHeaderId und externalLineId) oder orderLineId.',Updated=TO_TIMESTAMP('2025-12-10 17:39:33.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545619
;

-- 2025-12-10T17:39:33.417Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: ERR_RECEIPT_SCHEDULE_INVALID_IDENTIFICATION_METHOD
-- 2025-12-10T17:39:51.303Z
UPDATE AD_Message_Trl SET MsgText='Exactly one identification method must be provided: receiptScheduleId, (externalSystemCode, externalHeaderId and externalLineId), or orderLineId.',Updated=TO_TIMESTAMP('2025-12-10 17:39:51.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545619
;

-- 2025-12-10T17:39:51.304Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Run mode: SWING_CLIENT

-- Field: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> Externes System
-- Column: C_PurchaseCandidate.ExternalSystem_ID
-- 2025-12-10T18:02:07.635Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591700,760259,0,540894,TO_TIMESTAMP('2025-12-10 18:02:07.318000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.purchasecandidate','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-12-10 18:02:07.318000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-10T18:02:07.639Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760259 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-10T18:02:07.818Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-12-10T18:02:07.839Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760259
;

-- 2025-12-10T18:02:07.843Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760259)
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 20 -> ext.Externes System
-- Column: C_PurchaseCandidate.ExternalSystem_ID
-- 2025-12-10T18:02:34.627Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,760259,0,540894,640834,545479,'F',TO_TIMESTAMP('2025-12-10 18:02:34.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',5,0,0,TO_TIMESTAMP('2025-12-10 18:02:34.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Externes System
-- Column: C_Invoice_Candidate.ExternalSystem_ID
-- 2025-12-10T18:04:55.755Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591421,760260,0,543052,TO_TIMESTAMP('2025-12-10 18:04:55.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-12-10 18:04:55.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-10T18:04:55.757Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760260 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-10T18:04:55.759Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-12-10T18:04:55.766Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760260
;

-- 2025-12-10T18:04:55.772Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760260)
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 20 -> dates & flags.Externes System
-- Column: C_Invoice_Candidate.ExternalSystem_ID
-- 2025-12-10T18:05:16.637Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,760260,0,543052,640835,544365,'F',TO_TIMESTAMP('2025-12-10 18:05:16.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',60,0,0,TO_TIMESTAMP('2025-12-10 18:05:16.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> Externes System
-- Column: C_Invoice.ExternalSystem_ID
-- 2025-12-10T18:06:56.797Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591422,760261,0,290,TO_TIMESTAMP('2025-12-10 18:06:56.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Externes System',TO_TIMESTAMP('2025-12-10 18:06:56.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-10T18:06:56.799Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760261 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-10T18:06:56.801Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2025-12-10T18:06:56.809Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760261
;

-- 2025-12-10T18:06:56.810Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760261)
;

-- UI Element: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> main -> 20 -> doc.Externes System
-- Column: C_Invoice.ExternalSystem_ID
-- 2025-12-10T18:07:51.454Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,760261,0,290,640836,540226,'F',TO_TIMESTAMP('2025-12-10 18:07:51.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',50,0,0,TO_TIMESTAMP('2025-12-10 18:07:51.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> Externe ID
-- Column: C_Invoice.ExternalId
-- 2025-12-10T18:08:13.667Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572375,760262,0,290,TO_TIMESTAMP('2025-12-10 18:08:13.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,100,'D','Y','N','N','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2025-12-10 18:08:13.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-10T18:08:13.668Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760262 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-10T18:08:13.670Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939)
;

-- 2025-12-10T18:08:13.678Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760262
;

-- 2025-12-10T18:08:13.679Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760262)
;

-- UI Element: Eingangsrechnung(183,D) -> Eingangsrechnung(290,D) -> advanced edit -> 10 -> advanced edit.Externe ID
-- Column: C_Invoice.ExternalId
-- 2025-12-10T18:08:37.792Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,760262,0,290,640837,540218,'F',TO_TIMESTAMP('2025-12-10 18:08:37.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Externe ID',350,0,0,TO_TIMESTAMP('2025-12-10 18:08:37.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Eingangsrechnung(183,D) -> Rechnungsposition(291,D) -> External IDs
-- Column: C_InvoiceLine.ExternalIds
-- 2025-12-10T18:09:07.440Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563269,760263,0,291,TO_TIMESTAMP('2025-12-10 18:09:07.277000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'List of external IDs from C_Invoice_Candidates; delimited with '';,;''',255,'D','Y','N','N','N','N','N','N','N','External IDs',TO_TIMESTAMP('2025-12-10 18:09:07.277000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-10T18:09:07.442Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760263 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-10T18:09:07.444Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544448)
;

-- 2025-12-10T18:09:07.450Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760263
;

-- 2025-12-10T18:09:07.451Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760263)
;

-- UI Element: Eingangsrechnung(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.External IDs
-- Column: C_InvoiceLine.ExternalIds
-- 2025-12-10T18:09:25.184Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,760263,0,291,640838,540219,'F',TO_TIMESTAMP('2025-12-10 18:09:25.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'List of external IDs from C_Invoice_Candidates; delimited with '';,;''','Y','N','N','Y','N','N','N',0,'External IDs',140,0,0,TO_TIMESTAMP('2025-12-10 18:09:25.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
