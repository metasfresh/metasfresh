
--
-- Create column as non-mandatory
--

-- Column: EDI_Desadv_Pack.Line
-- Column: EDI_Desadv_Pack.Line
-- 2024-07-29T07:48:53.388Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588875,439,0,11,542170,'XX','Line',TO_TIMESTAMP('2024-07-29 09:48:53','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Einzelne Zeile in dem Dokument','de.metas.esb.edi',0,22,'Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Zeile Nr.',0,0,TO_TIMESTAMP('2024-07-29 09:48:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-29T07:48:53.390Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588875 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-29T07:48:53.439Z
/* DDL */  select update_Column_Translation_From_AD_Element(439) 
;

-- Column: EDI_Desadv_Pack.Line
-- Column: EDI_Desadv_Pack.Line
-- 2024-07-29T07:50:23.268Z
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM EDI_Desadv_Pack WHERE EDI_Desadv_ID=@EDI_Desadv_ID/0@',Updated=TO_TIMESTAMP('2024-07-29 09:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588875
;

-- Column: EDI_Desadv_Pack.Line
-- Column: EDI_Desadv_Pack.Line
-- 2024-07-29T08:16:21.276Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-07-29 10:16:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588875
;


-- 2024-07-29T07:50:39.006Z
/* DDL */ SELECT public.db_alter_table('EDI_Desadv_Pack','ALTER TABLE public.EDI_Desadv_Pack ADD COLUMN Line NUMERIC(10)')
;

-- 2024-07-29T08:05:01.051Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588875,0,TO_TIMESTAMP('2024-07-29 10:05:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540419,550771,'Y','Y','N','Line',5,'E',TO_TIMESTAMP('2024-07-29 10:05:00','YYYY-MM-DD HH24:MI:SS'),100,'Line')
;
