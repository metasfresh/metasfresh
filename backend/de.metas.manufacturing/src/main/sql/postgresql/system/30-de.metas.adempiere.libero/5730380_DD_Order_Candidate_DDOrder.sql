-- Column: DD_Order.Forward_PP_Order_ID
-- Column: DD_Order.Forward_PP_Order_ID
-- 2024-07-30T13:40:49.826Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588876,583182,0,30,540503,53037,'XX','Forward_PP_Order_ID',TO_TIMESTAMP('2024-07-30 16:40:49','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Forward Manufacturing Order',0,0,TO_TIMESTAMP('2024-07-30 16:40:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:40:49.829Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588876 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:40:49.860Z
/* DDL */  select update_Column_Translation_From_AD_Element(583182) 
;

-- 2024-07-30T13:40:50.821Z
/* DDL */ SELECT public.db_alter_table('DD_Order','ALTER TABLE public.DD_Order ADD COLUMN Forward_PP_Order_ID NUMERIC(10)')
;

-- 2024-07-30T13:40:51.163Z
ALTER TABLE DD_Order ADD CONSTRAINT ForwardPPOrder_DDOrder FOREIGN KEY (Forward_PP_Order_ID) REFERENCES public.PP_Order DEFERRABLE INITIALLY DEFERRED
;

-- Name: PP_Order_BOMLine
-- 2024-07-30T13:42:21.474Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541880,TO_TIMESTAMP('2024-07-30 16:42:21','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','PP_Order_BOMLine',TO_TIMESTAMP('2024-07-30 16:42:21','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-07-30T13:42:21.476Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541880 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PP_Order_BOMLine
-- Table: PP_Order_BOMLine
-- Key: PP_Order_BOMLine.PP_Order_BOMLine_ID
-- 2024-07-30T13:42:35.453Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,53575,0,541880,53025,TO_TIMESTAMP('2024-07-30 16:42:35','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N',TO_TIMESTAMP('2024-07-30 16:42:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: PP_Order_BOMLine
-- Table: PP_Order_BOMLine
-- Key: PP_Order_BOMLine.PP_Order_BOMLine_ID
-- 2024-07-30T13:42:38.490Z
UPDATE AD_Ref_Table SET EntityType='EE01',Updated=TO_TIMESTAMP('2024-07-30 16:42:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541880
;

-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- 2024-07-30T13:42:59.824Z
UPDATE AD_Column SET AD_Reference_Value_ID=541880,Updated=TO_TIMESTAMP('2024-07-30 16:42:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588829
;

alter table dd_order_candidate drop constraint if exists forwardpporderbomline_ddordercandidate;
ALTER TABLE dd_order_candidate
    ADD CONSTRAINT forwardpporderbomline_ddordercandidate
        FOREIGN KEY (Forward_PP_Order_BOMLine_ID)
            REFERENCES pp_product_bomline
            DEFERRABLE INITIALLY DEFERRED
;

-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- 2024-07-30T13:47:30.023Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588877,583183,0,30,541880,53037,'XX','Forward_PP_Order_BOMLine_ID',TO_TIMESTAMP('2024-07-30 16:47:29','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Forward Manufacturing Order BOM Line',0,0,TO_TIMESTAMP('2024-07-30 16:47:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:47:30.026Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588877 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:47:30.030Z
/* DDL */  select update_Column_Translation_From_AD_Element(583183) 
;

-- 2024-07-30T13:47:30.676Z
/* DDL */ SELECT public.db_alter_table('DD_Order','ALTER TABLE public.DD_Order ADD COLUMN Forward_PP_Order_BOMLine_ID NUMERIC(10)')
;

-- 2024-07-30T13:47:30.770Z
ALTER TABLE DD_Order ADD CONSTRAINT ForwardPPOrderBOMLine_DDOrder FOREIGN KEY (Forward_PP_Order_BOMLine_ID) REFERENCES public.PP_Order_BOMLine DEFERRABLE INITIALLY DEFERRED
;





























-- Table: DD_Order_Candidate_DDOrder
-- Table: DD_Order_Candidate_DDOrder
-- 2024-07-30T13:48:54.729Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542429,'A','N',TO_TIMESTAMP('2024-07-30 16:48:54','YYYY-MM-DD HH24:MI:SS'),100,'A','EE01','N','Y','N','N','Y','N','N','N','N','N',0,'DD_Order_Candidate - DD_Order','NP','L','DD_Order_Candidate_DDOrder','DTI',TO_TIMESTAMP('2024-07-30 16:48:54','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-07-30T13:48:54.733Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542429 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-07-30T13:48:54.850Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,
                         --StartNewYear,
                         StartNo,Updated,UpdatedBy) 
VALUES (0,0,556361,TO_TIMESTAMP('2024-07-30 16:48:54','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table DD_Order_Candidate_DDOrder',1,'Y','N','Y','Y','DD_Order_Candidate_DDOrder',
        --'N',
        1000000,TO_TIMESTAMP('2024-07-30 16:48:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-30T13:48:54.863Z
CREATE SEQUENCE DD_ORDER_CANDIDATE_DDORDER_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: DD_Order_Candidate_DDOrder.AD_Client_ID
-- Column: DD_Order_Candidate_DDOrder.AD_Client_ID
-- 2024-07-30T13:48:58.273Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588878,102,0,19,542429,'AD_Client_ID',TO_TIMESTAMP('2024-07-30 16:48:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','EE01',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-07-30 16:48:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:48:58.276Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588878 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:48:58.279Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: DD_Order_Candidate_DDOrder.AD_Org_ID
-- Column: DD_Order_Candidate_DDOrder.AD_Org_ID
-- 2024-07-30T13:48:59.151Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588879,113,0,30,542429,'AD_Org_ID',TO_TIMESTAMP('2024-07-30 16:48:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','EE01',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-07-30 16:48:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:48:59.153Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588879 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:48:59.156Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: DD_Order_Candidate_DDOrder.Created
-- Column: DD_Order_Candidate_DDOrder.Created
-- 2024-07-30T13:48:59.791Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588880,245,0,16,542429,'Created',TO_TIMESTAMP('2024-07-30 16:48:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','EE01',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-07-30 16:48:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:48:59.793Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588880 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:48:59.796Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: DD_Order_Candidate_DDOrder.CreatedBy
-- Column: DD_Order_Candidate_DDOrder.CreatedBy
-- 2024-07-30T13:49:00.471Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588881,246,0,18,110,542429,'CreatedBy',TO_TIMESTAMP('2024-07-30 16:49:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','EE01',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-07-30 16:49:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:49:00.474Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588881 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:49:00.477Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: DD_Order_Candidate_DDOrder.IsActive
-- Column: DD_Order_Candidate_DDOrder.IsActive
-- 2024-07-30T13:49:01.068Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588882,348,0,20,542429,'IsActive',TO_TIMESTAMP('2024-07-30 16:49:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','EE01',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-07-30 16:49:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:49:01.070Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588882 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:49:01.072Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: DD_Order_Candidate_DDOrder.Updated
-- Column: DD_Order_Candidate_DDOrder.Updated
-- 2024-07-30T13:49:01.909Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588883,607,0,16,542429,'Updated',TO_TIMESTAMP('2024-07-30 16:49:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','EE01',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-07-30 16:49:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:49:01.910Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588883 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:49:01.913Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: DD_Order_Candidate_DDOrder.UpdatedBy
-- Column: DD_Order_Candidate_DDOrder.UpdatedBy
-- 2024-07-30T13:49:02.523Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588884,608,0,18,110,542429,'UpdatedBy',TO_TIMESTAMP('2024-07-30 16:49:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','EE01',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-07-30 16:49:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:49:02.525Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588884 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:49:02.527Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-07-30T13:49:03.022Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583203,0,'DD_Order_Candidate_DDOrder_ID',TO_TIMESTAMP('2024-07-30 16:49:02','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','DD_Order_Candidate - DD_Order','DD_Order_Candidate - DD_Order',TO_TIMESTAMP('2024-07-30 16:49:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-30T13:49:03.025Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583203 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_DDOrder_ID
-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_DDOrder_ID
-- 2024-07-30T13:49:03.585Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588885,583203,0,13,542429,'DD_Order_Candidate_DDOrder_ID',TO_TIMESTAMP('2024-07-30 16:49:02','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','DD_Order_Candidate - DD_Order',0,0,TO_TIMESTAMP('2024-07-30 16:49:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:49:03.586Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588885 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:49:03.589Z
/* DDL */  select update_Column_Translation_From_AD_Element(583203) 
;

-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_ID
-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_ID
-- 2024-07-30T13:49:24.069Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588886,583179,0,30,542429,'XX','DD_Order_Candidate_ID',TO_TIMESTAMP('2024-07-30 16:49:23','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Distribution Order Candidate',0,0,TO_TIMESTAMP('2024-07-30 16:49:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:49:24.071Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588886 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:49:24.074Z
/* DDL */  select update_Column_Translation_From_AD_Element(583179) 
;

-- 2024-07-30T13:49:24.723Z
/* DDL */ CREATE TABLE public.DD_Order_Candidate_DDOrder (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DD_Order_Candidate_DDOrder_ID NUMERIC(10) NOT NULL, DD_Order_Candidate_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT DD_Order_Candidate_DDOrder_Key PRIMARY KEY (DD_Order_Candidate_DDOrder_ID), CONSTRAINT DDOrderCandidate_DDOrderCandidateDDOrder FOREIGN KEY (DD_Order_Candidate_ID) REFERENCES public.DD_Order_Candidate DEFERRABLE INITIALLY DEFERRED)
;

-- Column: DD_Order_Candidate_DDOrder.DD_Order_ID
-- Column: DD_Order_Candidate_DDOrder.DD_Order_ID
-- 2024-07-30T13:49:41.912Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588887,53311,0,30,542429,'XX','DD_Order_ID',TO_TIMESTAMP('2024-07-30 16:49:41','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Distribution Order',0,0,TO_TIMESTAMP('2024-07-30 16:49:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:49:41.913Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588887 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:49:41.916Z
/* DDL */  select update_Column_Translation_From_AD_Element(53311) 
;

-- 2024-07-30T13:49:42.521Z
/* DDL */ SELECT public.db_alter_table('DD_Order_Candidate_DDOrder','ALTER TABLE public.DD_Order_Candidate_DDOrder ADD COLUMN DD_Order_ID NUMERIC(10) NOT NULL')
;

-- 2024-07-30T13:49:42.528Z
ALTER TABLE DD_Order_Candidate_DDOrder ADD CONSTRAINT DDOrder_DDOrderCandidateDDOrder FOREIGN KEY (DD_Order_ID) REFERENCES public.DD_Order DEFERRABLE INITIALLY DEFERRED
;

-- Column: DD_Order_Candidate_DDOrder.DD_OrderLine_ID
-- Column: DD_Order_Candidate_DDOrder.DD_OrderLine_ID
-- 2024-07-30T13:49:58.961Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588888,53313,0,30,542429,'XX','DD_OrderLine_ID',TO_TIMESTAMP('2024-07-30 16:49:58','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Distribution Order Line',0,0,TO_TIMESTAMP('2024-07-30 16:49:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:49:58.963Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588888 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:49:58.966Z
/* DDL */  select update_Column_Translation_From_AD_Element(53313) 
;

-- 2024-07-30T13:49:59.574Z
/* DDL */ SELECT public.db_alter_table('DD_Order_Candidate_DDOrder','ALTER TABLE public.DD_Order_Candidate_DDOrder ADD COLUMN DD_OrderLine_ID NUMERIC(10) NOT NULL')
;

-- 2024-07-30T13:49:59.581Z
ALTER TABLE DD_Order_Candidate_DDOrder ADD CONSTRAINT DDOrderLine_DDOrderCandidateDDOrder FOREIGN KEY (DD_OrderLine_ID) REFERENCES public.DD_OrderLine DEFERRABLE INITIALLY DEFERRED
;

-- Column: DD_Order_Candidate_DDOrder.Qty
-- Column: DD_Order_Candidate_DDOrder.Qty
-- 2024-07-30T13:50:58.322Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588889,526,0,29,542429,'XX','Qty',TO_TIMESTAMP('2024-07-30 16:50:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Menge','EE01',0,10,'Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Menge',0,0,TO_TIMESTAMP('2024-07-30 16:50:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:50:58.325Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588889 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:50:58.329Z
/* DDL */  select update_Column_Translation_From_AD_Element(526) 
;

-- 2024-07-30T13:50:58.982Z
/* DDL */ SELECT public.db_alter_table('DD_Order_Candidate_DDOrder','ALTER TABLE public.DD_Order_Candidate_DDOrder ADD COLUMN Qty NUMERIC NOT NULL')
;

-- Column: DD_Order_Candidate_DDOrder.C_UOM_ID
-- Column: DD_Order_Candidate_DDOrder.C_UOM_ID
-- 2024-07-30T13:51:11.790Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588890,215,0,30,542429,'XX','C_UOM_ID',TO_TIMESTAMP('2024-07-30 16:51:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Maßeinheit','EE01',0,10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2024-07-30 16:51:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-30T13:51:11.791Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588890 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-30T13:51:11.794Z
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- 2024-07-30T13:51:13.245Z
/* DDL */ SELECT public.db_alter_table('DD_Order_Candidate_DDOrder','ALTER TABLE public.DD_Order_Candidate_DDOrder ADD COLUMN C_UOM_ID NUMERIC(10) NOT NULL')
;

-- 2024-07-30T13:51:13.251Z
ALTER TABLE DD_Order_Candidate_DDOrder ADD CONSTRAINT CUOM_DDOrderCandidateDDOrder FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_DDOrder_ID
-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_DDOrder_ID
-- 2024-07-30T13:51:27.955Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=1,Updated=TO_TIMESTAMP('2024-07-30 16:51:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588885
;

-- Table: DD_Order_Candidate_DDOrder
-- Table: DD_Order_Candidate_DDOrder
-- 2024-07-30T13:52:15.608Z
UPDATE AD_Table SET AD_Window_ID=541807,Updated=TO_TIMESTAMP('2024-07-30 16:52:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542429
;

-- Tab: Distribution Order Candidate -> Distributionsauftrag
-- Table: DD_Order_Candidate_DDOrder
-- Tab: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag
-- Table: DD_Order_Candidate_DDOrder
-- 2024-07-30T13:55:11.497Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,574325,0,547567,542429,541807,'Y',TO_TIMESTAMP('2024-07-30 16:55:11','YYYY-MM-DD HH24:MI:SS'),100,'Distribution Order allow create Order inter warehouse to supply a demand ','EE01','N','Distribution Order allow create Order inter warehouse to supply a demand ','N','A','DD_Order_Candidate_DDOrder','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Distributionsauftrag','N',20,0,TO_TIMESTAMP('2024-07-30 16:55:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-30T13:55:11.501Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547567 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-07-30T13:55:11.504Z
/* DDL */  select update_tab_translation_from_ad_element(574325) 
;

-- 2024-07-30T13:55:11.514Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547567)
;

-- Tab: Distribution Order Candidate -> Distributionsauftrag
-- Table: DD_Order_Candidate_DDOrder
-- Tab: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag
-- Table: DD_Order_Candidate_DDOrder
-- 2024-07-30T13:55:31.208Z
UPDATE AD_Tab SET AD_Column_ID=588886, IsInsertRecord='N', IsReadOnly='Y', Parent_Column_ID=588723, TabLevel=1,Updated=TO_TIMESTAMP('2024-07-30 16:55:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547567
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Mandant
-- Column: DD_Order_Candidate_DDOrder.AD_Client_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Mandant
-- Column: DD_Order_Candidate_DDOrder.AD_Client_ID
-- 2024-07-30T13:55:35.419Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588878,729773,0,547567,TO_TIMESTAMP('2024-07-30 16:55:34','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-07-30 16:55:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-30T13:55:35.422Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729773 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-30T13:55:35.426Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-07-30T13:55:37.207Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729773
;

-- 2024-07-30T13:55:37.208Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729773)
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Sektion
-- Column: DD_Order_Candidate_DDOrder.AD_Org_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Sektion
-- Column: DD_Order_Candidate_DDOrder.AD_Org_ID
-- 2024-07-30T13:55:37.328Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588879,729774,0,547567,TO_TIMESTAMP('2024-07-30 16:55:37','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-07-30 16:55:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-30T13:55:37.330Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729774 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-30T13:55:37.333Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-07-30T13:55:37.815Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729774
;

-- 2024-07-30T13:55:37.816Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729774)
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Aktiv
-- Column: DD_Order_Candidate_DDOrder.IsActive
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Aktiv
-- Column: DD_Order_Candidate_DDOrder.IsActive
-- 2024-07-30T13:55:37.928Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588882,729775,0,547567,TO_TIMESTAMP('2024-07-30 16:55:37','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-07-30 16:55:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-30T13:55:37.929Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729775 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-30T13:55:37.931Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-07-30T13:55:38.004Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729775
;

-- 2024-07-30T13:55:38.005Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729775)
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> DD_Order_Candidate - DD_Order
-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_DDOrder_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> DD_Order_Candidate - DD_Order
-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_DDOrder_ID
-- 2024-07-30T13:55:38.123Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588885,729776,0,547567,TO_TIMESTAMP('2024-07-30 16:55:38','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','DD_Order_Candidate - DD_Order',TO_TIMESTAMP('2024-07-30 16:55:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-30T13:55:38.124Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729776 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-30T13:55:38.127Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583203) 
;

-- 2024-07-30T13:55:38.130Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729776
;

-- 2024-07-30T13:55:38.130Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729776)
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Distribution Order Candidate
-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Distribution Order Candidate
-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_ID
-- 2024-07-30T13:55:38.232Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588886,729777,0,547567,TO_TIMESTAMP('2024-07-30 16:55:38','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Distribution Order Candidate',TO_TIMESTAMP('2024-07-30 16:55:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-30T13:55:38.233Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729777 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-30T13:55:38.234Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583179) 
;

-- 2024-07-30T13:55:38.237Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729777
;

-- 2024-07-30T13:55:38.238Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729777)
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Distribution Order
-- Column: DD_Order_Candidate_DDOrder.DD_Order_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Distribution Order
-- Column: DD_Order_Candidate_DDOrder.DD_Order_ID
-- 2024-07-30T13:55:38.353Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588887,729778,0,547567,TO_TIMESTAMP('2024-07-30 16:55:38','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Distribution Order',TO_TIMESTAMP('2024-07-30 16:55:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-30T13:55:38.354Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729778 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-30T13:55:38.356Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53311) 
;

-- 2024-07-30T13:55:38.364Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729778
;

-- 2024-07-30T13:55:38.364Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729778)
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Distribution Order Line
-- Column: DD_Order_Candidate_DDOrder.DD_OrderLine_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Distribution Order Line
-- Column: DD_Order_Candidate_DDOrder.DD_OrderLine_ID
-- 2024-07-30T13:55:38.483Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588888,729779,0,547567,TO_TIMESTAMP('2024-07-30 16:55:38','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Distribution Order Line',TO_TIMESTAMP('2024-07-30 16:55:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-30T13:55:38.485Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729779 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-30T13:55:38.487Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53313) 
;

-- 2024-07-30T13:55:38.491Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729779
;

-- 2024-07-30T13:55:38.492Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729779)
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Menge
-- Column: DD_Order_Candidate_DDOrder.Qty
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Menge
-- Column: DD_Order_Candidate_DDOrder.Qty
-- 2024-07-30T13:55:38.594Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588889,729780,0,547567,TO_TIMESTAMP('2024-07-30 16:55:38','YYYY-MM-DD HH24:MI:SS'),100,'Menge',10,'EE01','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2024-07-30 16:55:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-30T13:55:38.596Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729780 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-30T13:55:38.597Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526) 
;

-- 2024-07-30T13:55:38.677Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729780
;

-- 2024-07-30T13:55:38.678Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729780)
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Maßeinheit
-- Column: DD_Order_Candidate_DDOrder.C_UOM_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Maßeinheit
-- Column: DD_Order_Candidate_DDOrder.C_UOM_ID
-- 2024-07-30T13:55:38.800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588890,729781,0,547567,TO_TIMESTAMP('2024-07-30 16:55:38','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',10,'EE01','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2024-07-30 16:55:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-30T13:55:38.801Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729781 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-30T13:55:38.802Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2024-07-30T13:55:38.885Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729781
;

-- 2024-07-30T13:55:38.886Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729781)
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Distribution Order
-- Column: DD_Order_Candidate_DDOrder.DD_Order_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Distribution Order
-- Column: DD_Order_Candidate_DDOrder.DD_Order_ID
-- 2024-07-30T13:56:03.351Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2024-07-30 16:56:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729778
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Distribution Order Line
-- Column: DD_Order_Candidate_DDOrder.DD_OrderLine_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Distribution Order Line
-- Column: DD_Order_Candidate_DDOrder.DD_OrderLine_ID
-- 2024-07-30T13:56:03.359Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2024-07-30 16:56:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729779
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Maßeinheit
-- Column: DD_Order_Candidate_DDOrder.C_UOM_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Maßeinheit
-- Column: DD_Order_Candidate_DDOrder.C_UOM_ID
-- 2024-07-30T13:56:03.366Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2024-07-30 16:56:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729781
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Menge
-- Column: DD_Order_Candidate_DDOrder.Qty
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Menge
-- Column: DD_Order_Candidate_DDOrder.Qty
-- 2024-07-30T13:56:03.372Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2024-07-30 16:56:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729780
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Mandant
-- Column: DD_Order_Candidate_DDOrder.AD_Client_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Mandant
-- Column: DD_Order_Candidate_DDOrder.AD_Client_ID
-- 2024-07-30T13:56:10.295Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-07-30 16:56:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729773
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Sektion
-- Column: DD_Order_Candidate_DDOrder.AD_Org_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Sektion
-- Column: DD_Order_Candidate_DDOrder.AD_Org_ID
-- 2024-07-30T13:56:10.301Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-07-30 16:56:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729774
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Aktiv
-- Column: DD_Order_Candidate_DDOrder.IsActive
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Aktiv
-- Column: DD_Order_Candidate_DDOrder.IsActive
-- 2024-07-30T13:56:10.309Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-07-30 16:56:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729775
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> DD_Order_Candidate - DD_Order
-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_DDOrder_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> DD_Order_Candidate - DD_Order
-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_DDOrder_ID
-- 2024-07-30T13:56:10.315Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-07-30 16:56:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729776
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Distribution Order Candidate
-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Distribution Order Candidate
-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_ID
-- 2024-07-30T13:56:10.321Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-07-30 16:56:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729777
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Distribution Order
-- Column: DD_Order_Candidate_DDOrder.DD_Order_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Distribution Order
-- Column: DD_Order_Candidate_DDOrder.DD_Order_ID
-- 2024-07-30T13:56:10.327Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-07-30 16:56:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729778
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Distribution Order Line
-- Column: DD_Order_Candidate_DDOrder.DD_OrderLine_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Distribution Order Line
-- Column: DD_Order_Candidate_DDOrder.DD_OrderLine_ID
-- 2024-07-30T13:56:10.332Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-07-30 16:56:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729779
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Menge
-- Column: DD_Order_Candidate_DDOrder.Qty
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Menge
-- Column: DD_Order_Candidate_DDOrder.Qty
-- 2024-07-30T13:56:10.337Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-07-30 16:56:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729780
;

-- Field: Distribution Order Candidate -> Distributionsauftrag -> Maßeinheit
-- Column: DD_Order_Candidate_DDOrder.C_UOM_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distributionsauftrag(547567,EE01) -> Maßeinheit
-- Column: DD_Order_Candidate_DDOrder.C_UOM_ID
-- 2024-07-30T13:56:10.341Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-07-30 16:56:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729781
;































drop index if exists dd_order_candidate_ddorder_uq;
create unique index dd_order_candidate_ddorder_uq on dd_order_candidate_ddorder (dd_order_candidate_id, dd_order_id, dd_orderline_id); 

drop index if exists dd_order_candidate_ddorder_dd_order_candidate_id;
create index dd_order_candidate_ddorder_dd_order_candidate_id on dd_order_candidate_ddorder (dd_order_candidate_id);

drop index if exists dd_order_candidate_ddorder_dd_order_id;
create index dd_order_candidate_ddorder_dd_order_id on dd_order_candidate_ddorder (dd_order_id);

drop index if exists dd_order_candidate_ddorder_dd_orderline_id;
create index dd_order_candidate_ddorder_dd_orderline_id on dd_order_candidate_ddorder (dd_orderline_id);



















