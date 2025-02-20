-- Run mode: SWING_CLIENT

-- Column: M_InOut_DesadvLine_V.EDI_DesadvLine_ID
-- 2025-02-17T14:26:26.097Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589672,542692,0,19,542416,'XX','EDI_DesadvLine_ID',TO_TIMESTAMP('2025-02-17 14:26:25.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'DESADV-Position',0,0,TO_TIMESTAMP('2025-02-17 14:26:25.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-02-17T14:26:26.101Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589672 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-02-17T14:26:26.139Z
/* DDL */  select update_Column_Translation_From_AD_Element(542692)
;

-- 2025-02-17T14:26:50.982Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,FilterOperator,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589672,0,TO_TIMESTAMP('2025-02-17 14:26:50.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540433,550953,'E','Y','N','N','EDI_DesadvLine_ID',400,'E',TO_TIMESTAMP('2025-02-17 14:26:50.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDI_DesadvLine_ID')
;

-- 2025-02-17T14:50:24.268Z
UPDATE EXP_Format SET Help='Exports those EDI_DesadvLines from this DESADV that are not part of any EDI_Desadv_Pack_Item',Updated=TO_TIMESTAMP('2025-02-17 14:50:24.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540422
;

-- 2025-02-17T14:53:03.610Z
UPDATE EXP_Format SET Help='The export-Lines are similar to those of the export-format EDI_Exp_DesadvLine',Updated=TO_TIMESTAMP('2025-02-17 14:53:03.608000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540433
;

-- 2025-02-17T14:54:16.983Z
UPDATE EXP_Format SET Name='EDI_Exp_DesadvLineWithNoPack_1PerInOut_ActualDesadvLine',Updated=TO_TIMESTAMP('2025-02-17 14:54:16.980000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540433
;

-- 2025-02-17T14:55:56.579Z
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version) VALUES (0,0,542416,'N',TO_TIMESTAMP('2025-02-17 14:55:56.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540438,'Y','N','EDI_Exp_DesadvLineWithNoPack_1PerInOut','N','N','N',TO_TIMESTAMP('2025-02-17 14:55:56.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDI_Exp_DesadvLineWithNoPack_1PerInOut.','*')
;

-- 2025-02-17T14:56:17.975Z
UPDATE EXP_Format SET Value='EDI_Exp_DesadvLineWithNoPack_1PerInOut_A',Updated=TO_TIMESTAMP('2025-02-17 14:56:17.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540433
;

-- 2025-02-17T14:56:20.257Z
UPDATE EXP_Format SET Value='EDI_Exp_DesadvLineWithNoPack_1PerInOut',Updated=TO_TIMESTAMP('2025-02-17 14:56:20.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540438
;

-- 2025-02-17T14:57:21.764Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_EmbeddedFormat_ID,EXP_Format_ID,EXP_FormatLine_ID,FilterOperator,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589672,0,TO_TIMESTAMP('2025-02-17 14:57:21.638000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi',540433,540438,550954,'E','Y','N','N','EDI_Exp_DesadvLineWithNoPack_1PerInOut_ActualDesadvLine',10,'M',TO_TIMESTAMP('2025-02-17 14:57:21.638000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EDI_Exp_DesadvLineWithNoPack_1PerInOut_A')
;

-- 2025-02-17T14:57:29.717Z
UPDATE EXP_FormatLine SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-02-17 14:57:29.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550954
;

-- 2025-02-17T14:58:00.577Z
UPDATE EXP_Format SET WhereClause='IsDesadvLineInCurrentShipment=''N''',Updated=TO_TIMESTAMP('2025-02-17 14:58:00.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_Format_ID=540438
;

-- 2025-02-17T14:58:44.508Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540438, Name='EDI_Exp_DesadvLineWithNoPack_1PerInOut', Value='EDI_Exp_DesadvLineWithNoPack_1PerInOut',Updated=TO_TIMESTAMP('2025-02-17 14:58:44.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550794
;

-- 2025-02-17T15:00:55.949Z
UPDATE EXP_FormatLine SET Name='EDI_DesadvLine_ID', Type='R', Value='EDI_DesadvLine_ID',Updated=TO_TIMESTAMP('2025-02-17 15:00:55.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550954
;

-- 2025-02-17T15:03:23.951Z
UPDATE EXP_FormatLine SET Name='EDI_Exp_DesadvLineWithNoPack', Value='EDI_Exp_DesadvLineWithNoPack',Updated=TO_TIMESTAMP('2025-02-17 15:03:23.948000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE EXP_FormatLine_ID=550794
;

-- Table: M_InOut_DesadvLine_V
-- 2025-02-17T15:49:25.033Z
UPDATE AD_Table SET TechnicalNote='When using this view in an EXP_Format, be sure to include IsDesadvLineInCurrentShipment in the wherehause, because
M_InOut_DesadvLine_V_ID is a COALESCE of M_InOutLine_ID and EDI_DesadvLine_ID.',Updated=TO_TIMESTAMP('2025-02-17 15:49:25.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542416
;

-- Table: M_InOut_DesadvLine_V
-- 2025-02-17T15:49:59.488Z
UPDATE AD_Table SET TechnicalNote='!! When using this view in an EXP_Format, be sure to include IsDesadvLineInCurrentShipment in Exp_Format.WhereClause, because M_InOut_DesadvLine_V_ID is a COALESCE of M_InOutLine_ID and EDI_DesadvLine_ID !!!',Updated=TO_TIMESTAMP('2025-02-17 15:49:59.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542416
;

