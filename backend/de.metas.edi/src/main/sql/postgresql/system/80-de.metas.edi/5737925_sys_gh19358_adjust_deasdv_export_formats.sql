-- Column: EDI_Desadv_Pack.M_InOut_Desadv_ID
-- Column SQL (old): null
-- Column: EDI_Desadv_Pack.M_InOut_Desadv_ID
-- 2024-11-14T19:16:46.054Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589362,583143,0,13,542170,'XX','M_InOut_Desadv_ID','(SELECT line.m_inout_id         from M_HU hu                  INNER JOIN M_HU_Assignment assignment on hu.m_hu_id = assignment.m_hu_id AND assignment.ad_table_id = get_table_id(''M_InOutLine'')                  INNER JOIN M_InOutLine line on line.m_inoutline_id = assignment.record_id         where hu.m_hu_id = EDI_Desadv_Pack.m_hu_id)',TO_TIMESTAMP('2024-11-14 21:16:44','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'M_InOut_Desadv_ID',0,0,TO_TIMESTAMP('2024-11-14 21:16:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-14T19:16:46.062Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589362 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-14T19:16:46.117Z
/* DDL */  select update_Column_Translation_From_AD_Element(583143) 
;

-- Column: EDI_Desadv_Pack.M_InOut_Desadv_ID
-- Column: EDI_Desadv_Pack.M_InOut_Desadv_ID
-- 2024-11-14T19:17:51.638Z
UPDATE AD_Column SET FieldLength=22,Updated=TO_TIMESTAMP('2024-11-14 21:17:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589362
;

----

-- 2024-11-14T19:21:52.124Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550696
;

-- 2024-11-14T19:23:33.529Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550666
;

-- 2024-11-14T19:23:53.535Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550667
;

-- 2024-11-14T19:23:58.414Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550668
;

-- 2024-11-14T19:24:04.556Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550669
;

-- 2024-11-14T19:24:20.864Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550670
;

-- 2024-11-14T19:24:20.891Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550671
;

-- 2024-11-14T19:24:20.939Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550672
;

-- 2024-11-14T19:24:20.988Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550673
;

-- 2024-11-14T19:24:21.021Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550674
;

-- 2024-11-14T19:24:21.043Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550675
;

-- 2024-11-14T19:24:21.067Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550676
;

-- 2024-11-14T19:24:21.087Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550677
;

-- 2024-11-14T19:24:21.110Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550678
;

-- 2024-11-14T19:24:21.134Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550679
;

-- 2024-11-14T19:24:21.160Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550680
;

-- 2024-11-14T19:24:21.182Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550681
;

-- 2024-11-14T19:24:21.205Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550682
;

-- 2024-11-14T19:24:21.228Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550683
;

-- 2024-11-14T19:24:21.249Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550684
;

-- 2024-11-14T19:24:21.271Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550685
;

-- 2024-11-14T19:24:21.293Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550686
;

-- 2024-11-14T19:24:21.313Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550687
;

-- 2024-11-14T19:24:21.334Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550688
;

-- 2024-11-14T19:24:21.356Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550689
;

-- 2024-11-14T19:24:21.378Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550690
;

-- 2024-11-14T19:24:21.399Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550691
;

-- 2024-11-14T19:24:21.420Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550692
;

-- 2024-11-14T19:24:21.440Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550693
;

-- 2024-11-14T19:24:21.459Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550694
;

-- 2024-11-14T19:24:27.664Z
DELETE FROM EXP_Format WHERE EXP_Format_ID=540430
;

-- Table: M_InOut_DesadvLine_Pack_V
-- Table: M_InOut_DesadvLine_Pack_V
-- 2024-11-14T19:25:35.291Z
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=542417
;

-- 2024-11-14T19:25:35.292Z
DELETE FROM AD_Table WHERE AD_Table_ID=542417
;

-- 2024-11-14T19:32:17.718Z
UPDATE EXP_Format SET IsActive='N',Updated=TO_TIMESTAMP('2024-11-14 21:32:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Format_ID=540405
;

-- 2024-11-14T19:32:21.302Z
UPDATE EXP_Format SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 21:32:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Format_ID=540428
;

-- 2024-11-14T19:33:52.900Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_EmbeddedFormat_ID,EXP_Format_ID,EXP_FormatLine_ID,FilterOperator,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-11-14 21:33:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540419,540428,550793,'E','Y','N','N','EDI_Exp_Desadv_Pack',350,'M',TO_TIMESTAMP('2024-11-14 21:33:52','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Exp_Desadv_Pack')
;

-- 2024-11-14T19:34:32.808Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_EmbeddedFormat_ID,EXP_Format_ID,EXP_FormatLine_ID,FilterOperator,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-11-14 21:34:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540422,540428,550794,'E','Y','N','N','EDI_Exp_DesadvLineWithNoPack',360,'M',TO_TIMESTAMP('2024-11-14 21:34:32','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Exp_DesadvLineWithNoPack')
;

---

-- 2024-11-14T19:38:14.199Z
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version) VALUES (0,0,542170,'N',TO_TIMESTAMP('2024-11-14 21:38:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,'Y','N','EDI_Exp_Desadv_Pack_1PerInOut','N','N','N',TO_TIMESTAMP('2024-11-14 21:38:14','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Exp_Desadv_Pack_1PerInOut','*')
;

-- 2024-11-14T19:39:05.719Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,FilterOperator,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588875,0,TO_TIMESTAMP('2024-11-14 21:39:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550795,'E','Y','Y','N','Line',5,'E',TO_TIMESTAMP('2024-11-14 21:39:05','YYYY-MM-DD HH24:MI:SS'),100,'Line')
;

-- 2024-11-14T19:40:01.457Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,FilterOperator,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583418,0,TO_TIMESTAMP('2024-11-14 21:40:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550796,'E','N','Y','N','AD_Client_ID',10,'R',TO_TIMESTAMP('2024-11-14 21:40:01','YYYY-MM-DD HH24:MI:SS'),100,'AD_Client_ID')
;

-- 2024-11-14T19:40:27.303Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583419,0,TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.esb.edi',540431,550797,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','N','Y','Sektion',20,'R',TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'AD_Org_ID')
;

-- 2024-11-14T19:40:27.377Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583420,0,TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',540431,550798,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','N','Y','Erstellt',30,'E',TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'Created')
;

-- 2024-11-14T19:40:27.460Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583421,0,TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',540431,550799,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','N','Y','Erstellt durch',40,'R',TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'CreatedBy')
;

-- 2024-11-14T19:40:27.537Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583426,0,TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550800,'Y','Y','Y','DESADV',50,'R',TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Desadv_ID')
;

-- 2024-11-14T19:40:27.615Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583425,0,TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550801,'N','Y','EDI Lieferavis Pack (DESADV)',60,'R',TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Desadv_Pack_ID')
;

-- 2024-11-14T19:40:27.708Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583466,0,TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550802,'N','N','Übergeordneter Pack',70,'R',TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Desadv_Parent_Pack_ID')
;

-- 2024-11-14T19:40:27.801Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583431,0,TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.','de.metas.esb.edi',540431,550803,'N','N','LU Gebinde-GTIN',80,'E',TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'GTIN_LU_PackingMaterial')
;

-- 2024-11-14T19:40:27.890Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583427,0,TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550804,'Y','Y','Y','SSCC18',90,'E',TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'IPA_SSCC18')
;

-- 2024-11-14T19:40:27.969Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583422,0,TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','de.metas.esb.edi',540431,550805,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','N','Y','Aktiv',100,'E',TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'IsActive')
;

-- 2024-11-14T19:40:28.067Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583428,0,TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.','de.metas.esb.edi',540431,550806,'N','Y','manuelle SSCC18',110,'E',TO_TIMESTAMP('2024-11-14 21:40:27','YYYY-MM-DD HH24:MI:SS'),100,'IsManual_IPA_SSCC18')
;

-- 2024-11-14T19:40:28.152Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583435,0,TO_TIMESTAMP('2024-11-14 21:40:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550807,'N','N','Handling Unit',120,'R',TO_TIMESTAMP('2024-11-14 21:40:28','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_ID')
;

-- 2024-11-14T19:40:28.232Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583433,0,TO_TIMESTAMP('2024-11-14 21:40:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550808,'N','N','LU Verpackungscode',130,'R',TO_TIMESTAMP('2024-11-14 21:40:28','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_PackagingCode_LU_ID')
;

-- 2024-11-14T19:40:28.319Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583430,0,TO_TIMESTAMP('2024-11-14 21:40:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550809,'The current PackagingCode string from the current M_HU_PackagingCode_TU_ID.
Not for display, just for EDI-export.','N','N','M_HU_PackagingCode_LU_Text',140,'E',TO_TIMESTAMP('2024-11-14 21:40:28','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_PackagingCode_LU_Text')
;

-- 2024-11-14T19:40:28.412Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589362,0,TO_TIMESTAMP('2024-11-14 21:40:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550810,'N','N','M_InOut_Desadv_ID',150,'R',TO_TIMESTAMP('2024-11-14 21:40:28','YYYY-MM-DD HH24:MI:SS'),100,'M_InOut_Desadv_ID')
;

-- 2024-11-14T19:40:28.502Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583423,0,TO_TIMESTAMP('2024-11-14 21:40:28','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',540431,550811,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','N','Y','Aktualisiert',160,'E',TO_TIMESTAMP('2024-11-14 21:40:28','YYYY-MM-DD HH24:MI:SS'),100,'Updated')
;

-- 2024-11-14T19:40:28.590Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583424,0,TO_TIMESTAMP('2024-11-14 21:40:28','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',540431,550812,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','N','Y','Aktualisiert durch',170,'R',TO_TIMESTAMP('2024-11-14 21:40:28','YYYY-MM-DD HH24:MI:SS'),100,'UpdatedBy')
;

-- 2024-11-14T19:40:55.680Z
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2024-11-14 21:40:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550800
;

-- 2024-11-14T19:41:26.212Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 21:41:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550803
;

-- 2024-11-14T19:41:40.463Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 21:41:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550809
;

-- 2024-11-14T19:43:02.583Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_EmbeddedFormat_ID,EXP_Format_ID,EXP_FormatLine_ID,FilterOperator,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-11-14 21:43:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540418,540431,550813,'E','Y','N','N','EDI_Exp_Desadv_Pack_Item',200,'M',TO_TIMESTAMP('2024-11-14 21:43:02','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Exp_Desadv_Pack_Item')
;

-- 2024-11-14T19:48:36.764Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 21:48:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550810
;

---

-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- Column SQL (old): null
-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- 2024-11-14T20:07:03.839Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589364,583142,0,13,542171,'XX','M_InOut_DesadvLine_V_ID','(m_inoutline_id || '''')',TO_TIMESTAMP('2024-11-14 22:07:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,22,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'M_InOut_DesadvLine_V',0,0,TO_TIMESTAMP('2024-11-14 22:07:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-14T20:07:03.843Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589364 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-14T20:07:03.848Z
/* DDL */  select update_Column_Translation_From_AD_Element(583142)
;

---

-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- 2024-11-14T20:43:59.555Z
UPDATE AD_Column SET AD_Reference_ID=30, IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2024-11-14 22:43:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589364
;

-- 2024-11-14T20:45:48.775Z
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version) VALUES (0,0,542171,'N',TO_TIMESTAMP('2024-11-14 22:45:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540432,'Y','N','EDI_Desadv_Pack_Item_1PerInOut','N','N','N',TO_TIMESTAMP('2024-11-14 22:45:48','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Desadv_Pack_Item_1PerInOut','*')
;

-- 2024-11-14T20:45:57.851Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583436,0,TO_TIMESTAMP('2024-11-14 22:45:57','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','de.metas.esb.edi',540432,550818,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','N','Y','Mandant',10,'R',TO_TIMESTAMP('2024-11-14 22:45:57','YYYY-MM-DD HH24:MI:SS'),100,'AD_Client_ID')
;

-- 2024-11-14T20:45:57.942Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583437,0,TO_TIMESTAMP('2024-11-14 22:45:57','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.esb.edi',540432,550819,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','N','Y','Sektion',20,'R',TO_TIMESTAMP('2024-11-14 22:45:57','YYYY-MM-DD HH24:MI:SS'),100,'AD_Org_ID')
;

-- 2024-11-14T20:45:58.034Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583438,0,TO_TIMESTAMP('2024-11-14 22:45:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540432,550820,'N','N','Mindesthaltbarkeitsdatum',30,'E',TO_TIMESTAMP('2024-11-14 22:45:57','YYYY-MM-DD HH24:MI:SS'),100,'BestBeforeDate')
;

-- 2024-11-14T20:45:58.387Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583440,0,TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',540432,550821,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','N','Y','Erstellt',40,'E',TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'Created')
;

-- 2024-11-14T20:45:58.476Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583441,0,TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',540432,550822,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','N','Y','Erstellt durch',50,'R',TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'CreatedBy')
;

-- 2024-11-14T20:45:58.560Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583465,0,TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540432,550823,'N','Y','EDI Lieferavis Pack (DESADV)',60,'R',TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Desadv_Pack_ID')
;

-- 2024-11-14T20:45:58.646Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583444,0,TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540432,550824,'N','Y','Pack Item',70,'R',TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Desadv_Pack_Item_ID')
;

-- 2024-11-14T20:45:58.730Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583443,0,TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540432,550825,'Y','Y','Y','DESADV-Position',80,'R',TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'EDI_DesadvLine_ID')
;

-- 2024-11-14T20:45:58.811Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583547,0,TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'GTIN des verwendeten Gebindes, z.B. Karton. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.','de.metas.esb.edi',540432,550826,'N','N','TU Gebinde-GTIN',90,'E',TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'GTIN_TU_PackingMaterial')
;

-- 2024-11-14T20:45:58.895Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583448,0,TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','de.metas.esb.edi',540432,550827,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','N','Y','Aktiv',100,'E',TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'IsActive')
;

-- 2024-11-14T20:45:58.980Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583450,0,TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540432,550828,'N','N','Chargennummer',110,'E',TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'LotNumber')
;

-- 2024-11-14T20:45:59.066Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583548,0,TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540432,550829,'N','N','TU Verpackungscode',120,'R',TO_TIMESTAMP('2024-11-14 22:45:58','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_PackagingCode_TU_ID')
;

-- 2024-11-14T20:45:59.152Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583549,0,TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540432,550830,'The current PackagingCode string from the current M_HU_PackagingCode_TU_ID.
Not for display, just for EDI-export.','N','N','M_HU_PackagingCode_TU_Text',130,'E',TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_PackagingCode_TU_Text')
;

-- 2024-11-14T20:45:59.236Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589364,0,TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540432,550831,'N','N','M_InOut_DesadvLine_V',140,'R',TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'M_InOut_DesadvLine_V_ID')
;

-- 2024-11-14T20:45:59.319Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583456,0,TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document','de.metas.esb.edi',540432,550832,'The Material Shipment / Receipt ','N','N','Lieferung/Wareneingang',150,'R',TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'M_InOut_ID')
;

-- 2024-11-14T20:45:59.401Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583457,0,TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'Position auf Versand- oder Wareneingangsbeleg','de.metas.esb.edi',540432,550833,'"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.','N','N','Versand-/Wareneingangsposition',160,'R',TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'M_InOutLine_ID')
;

-- 2024-11-14T20:45:59.738Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583458,0,TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'Menge eines bewegten Produktes.','de.metas.esb.edi',540432,550834,'Die "Bewegungs-Menge" bezeichnet die Menge einer Ware, die bewegt wurde.','N','Y','Bewegungs-Menge',170,'E',TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'MovementQty')
;

-- 2024-11-14T20:45:59.839Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583460,0,TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540432,550835,'N','N','Menge CU/LU',180,'E',TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'QtyCUsPerLU')
;

-- 2024-11-14T20:45:59.924Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,587651,0,TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540432,550836,'N','N','Menge CU/LU (Preiseinh.)',190,'E',TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'QtyCUsPerLU_InInvoiceUOM')
;

-- 2024-11-14T20:46:00.013Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583459,0,TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'Menge der CUs pro Einzelgebinde (normalerweise TU)','de.metas.esb.edi',540432,550837,'N','N','Menge CU/TU',200,'E',TO_TIMESTAMP('2024-11-14 22:45:59','YYYY-MM-DD HH24:MI:SS'),100,'QtyCUsPerTU')
;

-- 2024-11-14T20:46:00.104Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,587650,0,TO_TIMESTAMP('2024-11-14 22:46:00','YYYY-MM-DD HH24:MI:SS'),100,'Menge der CUs pro Einzelgebinde (normalerweise TU) in der jew. Preiseinheit','de.metas.esb.edi',540432,550838,'N','N','Menge CU/TU (Preiseinh.)',210,'E',TO_TIMESTAMP('2024-11-14 22:46:00','YYYY-MM-DD HH24:MI:SS'),100,'QtyCUsPerTU_InInvoiceUOM')
;

-- 2024-11-14T20:46:00.195Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583461,0,TO_TIMESTAMP('2024-11-14 22:46:00','YYYY-MM-DD HH24:MI:SS'),100,'Fassungsvermögen in der Lager-Maßeinheit des jeweiligen Produktes','de.metas.esb.edi',540432,550839,'N','N','Verpackungskapazität',220,'E',TO_TIMESTAMP('2024-11-14 22:46:00','YYYY-MM-DD HH24:MI:SS'),100,'QtyItemCapacity')
;

-- 2024-11-14T20:46:00.289Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583462,0,TO_TIMESTAMP('2024-11-14 22:46:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540432,550840,'N','N','TU Anzahl',230,'E',TO_TIMESTAMP('2024-11-14 22:46:00','YYYY-MM-DD HH24:MI:SS'),100,'QtyTU')
;

-- 2024-11-14T20:46:00.376Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583463,0,TO_TIMESTAMP('2024-11-14 22:46:00','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',540432,550841,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','N','Y','Aktualisiert',240,'E',TO_TIMESTAMP('2024-11-14 22:46:00','YYYY-MM-DD HH24:MI:SS'),100,'Updated')
;

-- 2024-11-14T20:46:00.458Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,583464,0,TO_TIMESTAMP('2024-11-14 22:46:00','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',540432,550842,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','N','Y','Aktualisiert durch',250,'R',TO_TIMESTAMP('2024-11-14 22:46:00','YYYY-MM-DD HH24:MI:SS'),100,'UpdatedBy')
;

-- 2024-11-14T20:47:46.895Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540429, IsActive='Y', Name='M_InOut_DesadvLine_V_ID',Updated=TO_TIMESTAMP('2024-11-14 22:47:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550831
;

-- 2024-11-14T20:47:58.705Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 22:47:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550820
;

-- 2024-11-14T20:48:13.460Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 22:48:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550826
;

-- 2024-11-14T20:48:44.035Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 22:48:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550830
;

-- 2024-11-14T20:48:51.260Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 22:48:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550836
;

-- 2024-11-14T20:49:04.368Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 22:49:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550835
;

-- 2024-11-14T20:49:09.477Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 22:49:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550838
;

-- 2024-11-14T20:49:16.089Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 22:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550837
;

-- 2024-11-14T20:49:23.789Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 22:49:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550840
;

-- 2024-11-14T20:49:45.612Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 22:49:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550828
;

---

-- 2024-11-14T20:51:57.060Z
INSERT INTO EXP_Format (AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,EXP_Format_ID,IsActive,IsAlwaysFlagWithIssue,Name,Processing,TestExportModel,TestImportModel,Updated,UpdatedBy,Value,Version,WhereClause) VALUES (0,0,542416,'N',TO_TIMESTAMP('2024-11-14 22:51:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,'Y','N','EDI_Exp_DesadvLineWithNoPack_1PerInOut','N','N','N',TO_TIMESTAMP('2024-11-14 22:51:56','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Exp_DesadvLineWithNoPack_1PerInOut','*','M_InOut_DesadvLine_V_ID NOT IN (select M_InOutLine_ID || '''' from Edi_Desadv_Pack_Item)')
;

-- 2024-11-14T20:54:17.307Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588551,0,TO_TIMESTAMP('2024-11-14 22:54:10','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','de.metas.esb.edi',540433,550843,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','N','Y','Mandant',10,'R',TO_TIMESTAMP('2024-11-14 22:54:10','YYYY-MM-DD HH24:MI:SS'),100,'AD_Client_ID')
;

-- 2024-11-14T20:54:17.396Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588552,0,TO_TIMESTAMP('2024-11-14 22:54:17','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.esb.edi',540433,550844,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','N','Y','Sektion',20,'R',TO_TIMESTAMP('2024-11-14 22:54:17','YYYY-MM-DD HH24:MI:SS'),100,'AD_Org_ID')
;

-- 2024-11-14T20:54:17.480Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588553,0,TO_TIMESTAMP('2024-11-14 22:54:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550845,'N','N','BPartner Qty Item Capacity',30,'E',TO_TIMESTAMP('2024-11-14 22:54:17','YYYY-MM-DD HH24:MI:SS'),100,'BPartner_QtyItemCapacity')
;

-- 2024-11-14T20:54:17.569Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588554,0,TO_TIMESTAMP('2024-11-14 22:54:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550846,'N','N','BPartner UOM',40,'R',TO_TIMESTAMP('2024-11-14 22:54:17','YYYY-MM-DD HH24:MI:SS'),100,'C_UOM_BPartner_ID')
;

-- 2024-11-14T20:54:17.921Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588555,0,TO_TIMESTAMP('2024-11-14 22:54:17','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','de.metas.esb.edi',540433,550847,'Eine eindeutige (nicht monetäre) Maßeinheit','N','Y','Maßeinheit',50,'R',TO_TIMESTAMP('2024-11-14 22:54:17','YYYY-MM-DD HH24:MI:SS'),100,'C_UOM_ID')
;

-- 2024-11-14T20:54:18.010Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588556,0,TO_TIMESTAMP('2024-11-14 22:54:17','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit in der die betreffende Zeile abgerechnet wird','de.metas.esb.edi',540433,550848,'N','N','Abrechnungseinheit',60,'R',TO_TIMESTAMP('2024-11-14 22:54:17','YYYY-MM-DD HH24:MI:SS'),100,'C_UOM_Invoice_ID')
;

-- 2024-11-14T20:54:18.103Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588557,0,TO_TIMESTAMP('2024-11-14 22:54:18','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',540433,550849,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','N','Y','Erstellt',70,'E',TO_TIMESTAMP('2024-11-14 22:54:18','YYYY-MM-DD HH24:MI:SS'),100,'Created')
;

-- 2024-11-14T20:54:18.190Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588558,0,TO_TIMESTAMP('2024-11-14 22:54:18','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',540433,550850,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','N','Y','Erstellt durch',80,'R',TO_TIMESTAMP('2024-11-14 22:54:18','YYYY-MM-DD HH24:MI:SS'),100,'CreatedBy')
;

-- 2024-11-14T20:54:18.527Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588559,0,TO_TIMESTAMP('2024-11-14 22:54:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550851,'N','N','CU-EAN',90,'E',TO_TIMESTAMP('2024-11-14 22:54:18','YYYY-MM-DD HH24:MI:SS'),100,'EAN_CU')
;

-- 2024-11-14T20:54:18.893Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588560,0,TO_TIMESTAMP('2024-11-14 22:54:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550852,'N','N','TU-EAN',100,'E',TO_TIMESTAMP('2024-11-14 22:54:18','YYYY-MM-DD HH24:MI:SS'),100,'EAN_TU')
;

-- 2024-11-14T20:54:18.989Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588561,0,TO_TIMESTAMP('2024-11-14 22:54:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550853,'N','N','EanCom_Invoice_UOM',110,'E',TO_TIMESTAMP('2024-11-14 22:54:18','YYYY-MM-DD HH24:MI:SS'),100,'EanCom_Invoice_UOM')
;

-- 2024-11-14T20:54:19.075Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588562,0,TO_TIMESTAMP('2024-11-14 22:54:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550854,'N','Y','DESADV',120,'R',TO_TIMESTAMP('2024-11-14 22:54:18','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Desadv_ID')
;

-- 2024-11-14T20:54:19.166Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588564,0,TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550855,'N','N','ExternalSeqNo',130,'E',TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'ExternalSeqNo')
;

-- 2024-11-14T20:54:19.254Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588565,0,TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550856,'N','N','GTIN',140,'E',TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'GTIN')
;

-- 2024-11-14T20:54:19.344Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588566,0,TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest wie die abrechenbare Menge ermittelt wird, wenn die tatsächlich gelieferte Menge von der mominal gelieferten Menge abweicht.','de.metas.esb.edi',540433,550857,'N','Y','Abr. Menge basiert auf',150,'E',TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'InvoicableQtyBasedOn')
;

-- 2024-11-14T20:54:19.436Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588567,0,TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','de.metas.esb.edi',540433,550858,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','N','Y','Aktiv',160,'E',TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'IsActive')
;

-- 2024-11-14T20:54:19.542Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588618,0,TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550859,'N','Y','Lieferung geschlossen',170,'E',TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'IsDeliveryClosed')
;

-- 2024-11-14T20:54:19.628Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588568,0,TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'Falls "ja", wird das Feld "Abweichungscode" in der DESADV-Datei auf "BP" (back order to follow) gesetzt, d.h. es wird signalisiert, das später noch eine Nachliefrung erfolgen wird.','de.metas.esb.edi',540433,550860,'N','Y','Spätere Nachlieferung',180,'E',TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'IsSubsequentDeliveryPlanned')
;

-- 2024-11-14T20:54:19.713Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588569,0,TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument','de.metas.esb.edi',540433,550861,'Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','Zeile Nr.',190,'E',TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'Line')
;

-- 2024-11-14T20:54:19.794Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588587,0,TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550862,'N','N','M_InOut_Desadv_ID',200,'R',TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'M_InOut_Desadv_ID')
;

-- 2024-11-14T20:54:20.139Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588563,0,TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550863,'N','Y','M_InOut_DesadvLine_V',210,'E',TO_TIMESTAMP('2024-11-14 22:54:19','YYYY-MM-DD HH24:MI:SS'),100,'M_InOut_DesadvLine_V_ID')
;

-- 2024-11-14T20:54:20.505Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588570,0,TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','de.metas.esb.edi',540433,550864,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','N','Y','Produkt',220,'R',TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'M_Product_ID')
;

-- 2024-11-14T20:54:20.601Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588571,0,TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550865,'N','N','Auftragszeile',230,'E',TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'OrderLine')
;

-- 2024-11-14T20:54:20.690Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588572,0,TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550866,'N','N','Auftragsreferenz',240,'E',TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'OrderPOReference')
;

-- 2024-11-14T20:54:20.769Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588573,0,TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'Effektiver Preis','de.metas.esb.edi',540433,550867,'Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','N','N','Einzelpreis',250,'E',TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'PriceActual')
;

-- 2024-11-14T20:54:20.864Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588574,0,TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'Produktbeschreibung','de.metas.esb.edi',540433,550868,'N','N','Produktbeschreibung',260,'E',TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'ProductDescription')
;

-- 2024-11-14T20:54:20.948Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588575,0,TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550869,'N','N','Produktnummer',270,'E',TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'ProductNo')
;

-- 2024-11-14T20:54:21.285Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588576,0,TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550870,'N','N','Liefermenge (Abrechnungseinheit)',280,'E',TO_TIMESTAMP('2024-11-14 22:54:20','YYYY-MM-DD HH24:MI:SS'),100,'QtyDeliveredInInvoiceUOM')
;

-- 2024-11-14T20:54:21.628Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588577,0,TO_TIMESTAMP('2024-11-14 22:54:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550871,'N','N','Geliefert (Lagereinheit)',290,'E',TO_TIMESTAMP('2024-11-14 22:54:21','YYYY-MM-DD HH24:MI:SS'),100,'QtyDeliveredInStockingUOM')
;

-- 2024-11-14T20:54:21.715Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588578,0,TO_TIMESTAMP('2024-11-14 22:54:21','YYYY-MM-DD HH24:MI:SS'),100,'Liefermenge in der Maßeinheit der jeweiligen Zeile (kann von der Maßeinheit des betreffenden Produktes abweichen)','de.metas.esb.edi',540433,550872,'N','N','Liefermenge',300,'E',TO_TIMESTAMP('2024-11-14 22:54:21','YYYY-MM-DD HH24:MI:SS'),100,'QtyDeliveredInUOM')
;

-- 2024-11-14T20:54:22.069Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588579,0,TO_TIMESTAMP('2024-11-14 22:54:21','YYYY-MM-DD HH24:MI:SS'),100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit','de.metas.esb.edi',540433,550873,'Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt','N','N','Menge',310,'E',TO_TIMESTAMP('2024-11-14 22:54:21','YYYY-MM-DD HH24:MI:SS'),100,'QtyEntered')
;

-- 2024-11-14T20:54:22.155Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588580,0,TO_TIMESTAMP('2024-11-14 22:54:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550874,'N','N','Qty Entered In BPartner UOM',320,'E',TO_TIMESTAMP('2024-11-14 22:54:22','YYYY-MM-DD HH24:MI:SS'),100,'QtyEnteredInBPartnerUOM')
;

-- 2024-11-14T20:54:22.492Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588581,0,TO_TIMESTAMP('2024-11-14 22:54:22','YYYY-MM-DD HH24:MI:SS'),100,'Fassungsvermögen in der Lager-Maßeinheit des jeweiligen Produktes','de.metas.esb.edi',540433,550875,'N','N','Verpackungskapazität',330,'E',TO_TIMESTAMP('2024-11-14 22:54:22','YYYY-MM-DD HH24:MI:SS'),100,'QtyItemCapacity')
;

-- 2024-11-14T20:54:22.576Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588582,0,TO_TIMESTAMP('2024-11-14 22:54:22','YYYY-MM-DD HH24:MI:SS'),100,'Bestellt/ Beauftragt','de.metas.esb.edi',540433,550876,'Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','N','Y','Bestellt/ Beauftragt',340,'E',TO_TIMESTAMP('2024-11-14 22:54:22','YYYY-MM-DD HH24:MI:SS'),100,'QtyOrdered')
;

-- 2024-11-14T20:54:22.657Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588583,0,TO_TIMESTAMP('2024-11-14 22:54:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550877,'N','N','CU-UPC',350,'E',TO_TIMESTAMP('2024-11-14 22:54:22','YYYY-MM-DD HH24:MI:SS'),100,'UPC_CU')
;

-- 2024-11-14T20:54:22.994Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588584,0,TO_TIMESTAMP('2024-11-14 22:54:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540433,550878,'N','N','TU-UPC',360,'E',TO_TIMESTAMP('2024-11-14 22:54:22','YYYY-MM-DD HH24:MI:SS'),100,'UPC_TU')
;

-- 2024-11-14T20:54:23.076Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588585,0,TO_TIMESTAMP('2024-11-14 22:54:22','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',540433,550879,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','N','Y','Aktualisiert',370,'E',TO_TIMESTAMP('2024-11-14 22:54:22','YYYY-MM-DD HH24:MI:SS'),100,'Updated')
;

-- 2024-11-14T20:54:23.416Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588586,0,TO_TIMESTAMP('2024-11-14 22:54:23','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',540433,550880,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','N','Y','Aktualisiert durch',380,'R',TO_TIMESTAMP('2024-11-14 22:54:23','YYYY-MM-DD HH24:MI:SS'),100,'UpdatedBy')
;

-- 2024-11-14T21:00:12.542Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-14 23:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550619
;

-- 2024-11-14T21:02:25.709Z
UPDATE EXP_FormatLine SET Name='M_InOut_ID', Type='E',Updated=TO_TIMESTAMP('2024-11-14 23:02:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550619
;

-- 2024-11-14T21:07:31.024Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540431, Name='EDI_Exp_Desadv_Pack_1PerInOut', Value='EDI_Exp_Desadv_Pack_1PerInOut',Updated=TO_TIMESTAMP('2024-11-14 23:07:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550793
;

---

-- 2024-11-15T07:48:49.919Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-15 09:48:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550621
;

-- 2024-11-15T07:49:20.383Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550695
;

-- 2024-11-15T07:50:05.124Z
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2024-11-15 09:50:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550609
;

-- 2024-11-15T07:51:39.182Z
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2024-11-15 09:51:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550810
;

-- Column: EDI_Desadv_Pack.M_InOut_ID
-- Column: EDI_Desadv_Pack.M_InOut_ID
-- 2024-11-15T08:00:41.388Z
UPDATE AD_Column SET AD_Element_ID=1025, AD_Reference_ID=19, ColumnName='M_InOut_ID', Description='Material Shipment Document', FieldLength=10, Help='The Material Shipment / Receipt ', IsExcludeFromZoomTargets='N', Name='Lieferung/Wareneingang',Updated=TO_TIMESTAMP('2024-11-15 10:00:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589362
;

-- 2024-11-15T08:00:41.396Z
UPDATE AD_Field SET Name='Lieferung/Wareneingang', Description='Material Shipment Document', Help='The Material Shipment / Receipt ' WHERE AD_Column_ID=589362
;

-- 2024-11-15T08:00:41.434Z
/* DDL */  select update_Column_Translation_From_AD_Element(1025)
;

-- 2024-11-15T08:01:55.212Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,FilterOperator,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589362,0,TO_TIMESTAMP('2024-11-15 10:01:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550887,'E','Y','N','N','M_InOut_ID',210,'E',TO_TIMESTAMP('2024-11-15 10:01:55','YYYY-MM-DD HH24:MI:SS'),100,'M_InOut_ID')
;

-- 2024-11-15T08:03:15.045Z
UPDATE EXP_FormatLine SET IsPartUniqueIndex='Y',Updated=TO_TIMESTAMP('2024-11-15 10:03:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550619
;

-- 2024-11-15T08:05:46.811Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540432, Name='EDI_Desadv_Pack_Item_1PerInOut', Value='EDI_Desadv_Pack_Item_1PerInOut',Updated=TO_TIMESTAMP('2024-11-15 10:05:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550813
;

-- 2024-11-15T08:06:33.783Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550825
;

-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- Column SQL (old): (m_inoutline_id || '')
-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- Column SQL (old): (m_inoutline_id || '')
-- 2024-11-15T08:08:02.126Z
UPDATE AD_Column SET AD_Reference_ID=13, ColumnSQL='(m_inoutline_id)', IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2024-11-15 10:08:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589364
;

-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- Column SQL (old): (m_inoutline_id)
-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- Column SQL (old): (m_inoutline_id)
-- 2024-11-15T08:09:55.775Z
UPDATE AD_Column SET ColumnSQL='(m_inoutline_id || '''')',Updated=TO_TIMESTAMP('2024-11-15 10:09:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589364
;

-- 2024-11-15T08:13:56.805Z
UPDATE EXP_FormatLine SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-11-15 10:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550619
;

-- 2024-11-15T08:14:02.722Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550618
;

-- Column: M_InOut_Desadv_V.M_InOut_Desadv_ID
-- Column: M_InOut_Desadv_V.M_InOut_Desadv_ID
-- 2024-11-15T08:14:50.492Z
UPDATE AD_Column SET IsKey='N', IsMandatory='N',Updated=TO_TIMESTAMP('2024-11-15 10:14:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588376
;

-- Column: M_InOut_Desadv_V.M_InOut_ID
-- Column: M_InOut_Desadv_V.M_InOut_ID
-- 2024-11-15T08:14:56.307Z
UPDATE AD_Column SET IsKey='Y', IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-11-15 10:14:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588377
;

-- Column: M_InOut_Desadv_V.M_InOut_Desadv_ID
-- Column: M_InOut_Desadv_V.M_InOut_Desadv_ID
-- 2024-11-15T08:15:00.457Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588376
;

-- 2024-11-15T08:15:00.460Z
DELETE FROM AD_Column WHERE AD_Column_ID=588376
;

-- 2024-11-15T08:15:36.739Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550810
;

-- Table: M_InOut_Desadv_Pack_V
-- Table: M_InOut_Desadv_Pack_V
-- 2024-11-15T08:20:54.586Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542451,'A','N',TO_TIMESTAMP('2024-11-15 10:20:54','YYYY-MM-DD HH24:MI:SS'),100,'A','de.metas.esb.edi','N','Y','N','N','N','N','N','N','N','Y',0,'M_InOut_Desadv_Pack_V','NP','L','M_InOut_Desadv_Pack_V','DTI',TO_TIMESTAMP('2024-11-15 10:20:54','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-11-15T08:20:54.593Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542451 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- Column: M_InOut_Desadv_Pack_V.AD_Client_ID
-- Column: M_InOut_Desadv_Pack_V.AD_Client_ID
-- 2024-11-15T08:21:38.675Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589391,102,0,19,542451,'AD_Client_ID',TO_TIMESTAMP('2024-11-15 10:21:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.esb.edi',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-11-15 10:21:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:38.681Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589391 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:38.691Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: M_InOut_Desadv_Pack_V.AD_Org_ID
-- Column: M_InOut_Desadv_Pack_V.AD_Org_ID
-- 2024-11-15T08:21:39.491Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589392,113,0,30,542451,'AD_Org_ID',TO_TIMESTAMP('2024-11-15 10:21:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.esb.edi',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-11-15 10:21:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:39.497Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589392 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:39.503Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: M_InOut_Desadv_Pack_V.Created
-- Column: M_InOut_Desadv_Pack_V.Created
-- 2024-11-15T08:21:40.273Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589393,245,0,16,542451,'Created',TO_TIMESTAMP('2024-11-15 10:21:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-11-15 10:21:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:40.277Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589393 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:40.282Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: M_InOut_Desadv_Pack_V.CreatedBy
-- Column: M_InOut_Desadv_Pack_V.CreatedBy
-- 2024-11-15T08:21:41.007Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589394,246,0,18,110,542451,'CreatedBy',TO_TIMESTAMP('2024-11-15 10:21:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-11-15 10:21:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:41.009Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589394 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:41.013Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: M_InOut_Desadv_Pack_V.EDI_Desadv_ID
-- Column: M_InOut_Desadv_Pack_V.EDI_Desadv_ID
-- 2024-11-15T08:21:41.737Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589395,542691,0,30,542451,'EDI_Desadv_ID',TO_TIMESTAMP('2024-11-15 10:21:41','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'E','Y','Y','N','N','N','N','N','Y','N','Y','N','Y','N','N','N','N','N','DESADV',0,10,TO_TIMESTAMP('2024-11-15 10:21:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:41.739Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589395 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:41.740Z
/* DDL */  select update_Column_Translation_From_AD_Element(542691)
;

-- 2024-11-15T08:21:42.292Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583369,0,'M_InOut_Desadv_Pack_V_ID',TO_TIMESTAMP('2024-11-15 10:21:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','M_InOut_Desadv_Pack_V','M_InOut_Desadv_Pack_V',TO_TIMESTAMP('2024-11-15 10:21:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-11-15T08:21:42.300Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583369 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_InOut_Desadv_Pack_V.M_InOut_Desadv_Pack_V_ID
-- Column: M_InOut_Desadv_Pack_V.M_InOut_Desadv_Pack_V_ID
-- 2024-11-15T08:21:43.112Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589396,583369,0,13,542451,'M_InOut_Desadv_Pack_V_ID',TO_TIMESTAMP('2024-11-15 10:21:42','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'Y','N','N','N','N','N','Y','Y','Y','N','N','N','N','Y','N','N','M_InOut_Desadv_Pack_V',0,20,TO_TIMESTAMP('2024-11-15 10:21:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:43.116Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589396 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:43.120Z
/* DDL */  select update_Column_Translation_From_AD_Element(583369)
;

-- Column: M_InOut_Desadv_Pack_V.EDI_Desadv_Parent_Pack_ID
-- Column: M_InOut_Desadv_Pack_V.EDI_Desadv_Parent_Pack_ID
-- 2024-11-15T08:21:43.864Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589397,581053,0,30,541596,542451,'EDI_Desadv_Parent_Pack_ID',TO_TIMESTAMP('2024-11-15 10:21:43','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Übergeordneter Pack',0,0,TO_TIMESTAMP('2024-11-15 10:21:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:43.866Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589397 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:43.869Z
/* DDL */  select update_Column_Translation_From_AD_Element(581053)
;

-- Column: M_InOut_Desadv_Pack_V.GTIN_PackingMaterial
-- Column: M_InOut_Desadv_Pack_V.GTIN_PackingMaterial
-- 2024-11-15T08:21:44.587Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589398,583257,0,10,542451,'GTIN_PackingMaterial',TO_TIMESTAMP('2024-11-15 10:21:44','YYYY-MM-DD HH24:MI:SS'),100,'N','GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.','de.metas.esb.edi',0,50,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Gebinde-GTIN',0,0,TO_TIMESTAMP('2024-11-15 10:21:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:44.589Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589398 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:44.594Z
/* DDL */  select update_Column_Translation_From_AD_Element(583257)
;

-- Column: M_InOut_Desadv_Pack_V.IPA_SSCC18
-- Column: M_InOut_Desadv_Pack_V.IPA_SSCC18
-- 2024-11-15T08:21:45.309Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589399,542693,0,10,542451,'IPA_SSCC18',TO_TIMESTAMP('2024-11-15 10:21:45','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,40,'E','Y','Y','N','N','N','N','N','Y','N','Y','N','Y','N','N','N','N','Y','SSCC18','@IsManual_IPA_SSCC18/''N''@=''Y''',0,30,TO_TIMESTAMP('2024-11-15 10:21:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:45.313Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589399 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:45.316Z
/* DDL */  select update_Column_Translation_From_AD_Element(542693)
;

-- Column: M_InOut_Desadv_Pack_V.IsActive
-- Column: M_InOut_Desadv_Pack_V.IsActive
-- 2024-11-15T08:21:46.050Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589400,348,0,20,542451,'IsActive',TO_TIMESTAMP('2024-11-15 10:21:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.esb.edi',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-11-15 10:21:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:46.053Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589400 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:46.059Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: M_InOut_Desadv_Pack_V.IsManual_IPA_SSCC18
-- Column: M_InOut_Desadv_Pack_V.IsManual_IPA_SSCC18
-- 2024-11-15T08:21:47.036Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589401,542729,0,20,542451,'IsManual_IPA_SSCC18',TO_TIMESTAMP('2024-11-15 10:21:46','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.','de.metas.esb.edi',0,1,'E','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','Y','manuelle SSCC18',0,0,TO_TIMESTAMP('2024-11-15 10:21:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:47.040Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589401 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:47.044Z
/* DDL */  select update_Column_Translation_From_AD_Element(542729)
;

-- Column: M_InOut_Desadv_Pack_V.M_HU_ID
-- Column: M_InOut_Desadv_Pack_V.M_HU_ID
-- 2024-11-15T08:21:47.752Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589402,542140,0,30,542451,'M_HU_ID',TO_TIMESTAMP('2024-11-15 10:21:47','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'E','Y','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','Handling Unit',0,0,TO_TIMESTAMP('2024-11-15 10:21:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:47.756Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589402 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:47.760Z
/* DDL */  select update_Column_Translation_From_AD_Element(542140)
;

-- Column: M_InOut_Desadv_Pack_V.M_HU_PackagingCode_ID
-- Column: M_InOut_Desadv_Pack_V.M_HU_PackagingCode_ID
-- 2024-11-15T08:21:48.480Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589403,577196,0,19,541064,542451,'M_HU_PackagingCode_ID',TO_TIMESTAMP('2024-11-15 10:21:48','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'E','Y','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','Verpackungscode',0,0,TO_TIMESTAMP('2024-11-15 10:21:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:48.484Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589403 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:48.490Z
/* DDL */  select update_Column_Translation_From_AD_Element(577196)
;

-- Column: M_InOut_Desadv_Pack_V.M_HU_PackagingCode_Text
-- Column SQL (old): null
-- Column: M_InOut_Desadv_Pack_V.M_HU_PackagingCode_Text
-- 2024-11-15T08:21:49.240Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589404,583256,0,10,542451,'M_HU_PackagingCode_Text','(select c.PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=EDI_Desadv_Pack.M_HU_PackagingCode_ID)',TO_TIMESTAMP('2024-11-15 10:21:48','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,4,'The current PackagingCode string from the current M_HU_PackagingCode_ID.
Not for display, just for EDI-export.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','M_HU_PackagingCode_Text',0,0,TO_TIMESTAMP('2024-11-15 10:21:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:49.241Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589404 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:49.243Z
/* DDL */  select update_Column_Translation_From_AD_Element(583256)
;

-- Column: M_InOut_Desadv_Pack_V.M_InOut_ID
-- Column SQL (old): null
-- Column: M_InOut_Desadv_Pack_V.M_InOut_ID
-- 2024-11-15T08:21:50.086Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589405,1025,0,19,542451,'M_InOut_ID','(SELECT line.m_inout_id         from M_HU hu                  INNER JOIN M_HU_Assignment assignment on hu.m_hu_id = assignment.m_hu_id AND assignment.ad_table_id = get_table_id(''M_InOutLine'')                  INNER JOIN M_InOutLine line on line.m_inoutline_id = assignment.record_id         where hu.m_hu_id = EDI_Desadv_Pack.m_hu_id)',TO_TIMESTAMP('2024-11-15 10:21:49','YYYY-MM-DD HH24:MI:SS'),100,'N','Material Shipment Document','de.metas.esb.edi',0,10,'The Material Shipment / Receipt ','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Lieferung/Wareneingang',0,0,TO_TIMESTAMP('2024-11-15 10:21:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:50.088Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589405 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:50.090Z
/* DDL */  select update_Column_Translation_From_AD_Element(1025)
;

-- Column: M_InOut_Desadv_Pack_V.SeqNo
-- Column: M_InOut_Desadv_Pack_V.SeqNo
-- 2024-11-15T08:21:50.976Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589406,566,0,11,542451,'SeqNo',TO_TIMESTAMP('2024-11-15 10:21:50','YYYY-MM-DD HH24:MI:SS'),100,'N','@SQL=SELECT COALESCE(MAX(SeqNo),0)+1 AS DefaultValue FROM EDI_Desadv_Pack WHERE EDI_Desadv_ID=@EDI_Desadv_ID/0@','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','de.metas.esb.edi',0,22,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Reihenfolge',0,0,TO_TIMESTAMP('2024-11-15 10:21:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:50.979Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589406 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:50.983Z
/* DDL */  select update_Column_Translation_From_AD_Element(566)
;

-- Column: M_InOut_Desadv_Pack_V.Updated
-- Column: M_InOut_Desadv_Pack_V.Updated
-- 2024-11-15T08:21:51.740Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589407,607,0,16,542451,'Updated',TO_TIMESTAMP('2024-11-15 10:21:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-11-15 10:21:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:51.742Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589407 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:51.744Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: M_InOut_Desadv_Pack_V.UpdatedBy
-- Column: M_InOut_Desadv_Pack_V.UpdatedBy
-- 2024-11-15T08:21:52.653Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589408,608,0,18,110,542451,'UpdatedBy',TO_TIMESTAMP('2024-11-15 10:21:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-11-15 10:21:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:21:52.655Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589408 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:21:52.657Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- Column: M_InOut_Desadv_Pack_V.M_InOut_ID
-- Column SQL (old): (SELECT line.m_inout_id         from M_HU hu                  INNER JOIN M_HU_Assignment assignment on hu.m_hu_id = assignment.m_hu_id AND assignment.ad_table_id = get_table_id('M_InOutLine')                  INNER JOIN M_InOutLine line on line.m_inoutline_id = assignment.record_id         where hu.m_hu_id = EDI_Desadv_Pack.m_hu_id)
-- Column: M_InOut_Desadv_Pack_V.M_InOut_ID
-- Column SQL (old): (SELECT line.m_inout_id         from M_HU hu                  INNER JOIN M_HU_Assignment assignment on hu.m_hu_id = assignment.m_hu_id AND assignment.ad_table_id = get_table_id('M_InOutLine')                  INNER JOIN M_InOutLine line on line.m_inoutline_id = assignment.record_id         where hu.m_hu_id = EDI_Desadv_Pack.m_hu_id)
-- 2024-11-15T08:22:20.018Z
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2024-11-15 10:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589405
;

-- Column: M_InOut_Desadv_Pack_V.M_HU_PackagingCode_Text
-- Column SQL (old): (select c.PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=EDI_Desadv_Pack.M_HU_PackagingCode_ID)
-- Column: M_InOut_Desadv_Pack_V.M_HU_PackagingCode_Text
-- Column SQL (old): (select c.PackagingCode from M_HU_PackagingCode c where c.M_HU_PackagingCode_ID=EDI_Desadv_Pack.M_HU_PackagingCode_ID)
-- 2024-11-15T08:22:48.590Z
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2024-11-15 10:22:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589404
;

-- 2024-11-15T08:24:14.819Z
UPDATE EXP_Format SET AD_Table_ID=542451, Name='M_InOut_Desadv_Pack_V', Value='M_InOut_Desadv_Pack_V',Updated=TO_TIMESTAMP('2024-11-15 10:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Format_ID=540431
;

-- 2024-11-15T08:25:29.914Z
UPDATE EXP_FormatLine SET AD_Column_ID=589406, Name='SeqNo', Value='SeqNo',Updated=TO_TIMESTAMP('2024-11-15 10:25:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550795
;

-- Column: M_InOut_Desadv_Pack_V.EDI_Desadv_Pack_ID
-- Column: M_InOut_Desadv_Pack_V.EDI_Desadv_Pack_ID
-- 2024-11-15T08:26:26.618Z
UPDATE AD_Column SET AD_Element_ID=581051, AD_Reference_ID=19, ColumnName='EDI_Desadv_Pack_ID', Description=NULL, Help=NULL, IsExcludeFromZoomTargets='N', IsUpdateable='N', Name='EDI Lieferavis Pack (DESADV)',Updated=TO_TIMESTAMP('2024-11-15 10:26:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589396
;

-- 2024-11-15T08:26:26.619Z
UPDATE AD_Field SET Name='EDI Lieferavis Pack (DESADV)', Description=NULL, Help=NULL WHERE AD_Column_ID=589396
;

-- 2024-11-15T08:26:26.622Z
/* DDL */  select update_Column_Translation_From_AD_Element(581051)
;

-- 2024-11-15T08:27:08.640Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550796
;

-- 2024-11-15T08:27:15.709Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550795
;

-- 2024-11-15T08:27:18.832Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550797
;

-- 2024-11-15T08:27:25.924Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550798
;

-- 2024-11-15T08:27:25.945Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550799
;

-- 2024-11-15T08:27:25.963Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550800
;

-- 2024-11-15T08:27:25.981Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550801
;

-- 2024-11-15T08:27:25.999Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550802
;

-- 2024-11-15T08:27:26.017Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550803
;

-- 2024-11-15T08:27:26.033Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550804
;

-- 2024-11-15T08:27:26.054Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550805
;

-- 2024-11-15T08:27:26.071Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550806
;

-- 2024-11-15T08:27:26.087Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550807
;

-- 2024-11-15T08:27:26.103Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550808
;

-- 2024-11-15T08:27:26.118Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550809
;

-- 2024-11-15T08:27:26.135Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550811
;

-- 2024-11-15T08:27:26.153Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550812
;

-- 2024-11-15T08:27:26.170Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550813
;

-- 2024-11-15T08:27:26.188Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550887
;

-- 2024-11-15T08:27:29.140Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589391,0,TO_TIMESTAMP('2024-11-15 10:27:28','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','de.metas.esb.edi',540431,550890,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','N','Y','Mandant',10,'R',TO_TIMESTAMP('2024-11-15 10:27:28','YYYY-MM-DD HH24:MI:SS'),100,'AD_Client_ID')
;

-- 2024-11-15T08:27:29.254Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589392,0,TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','de.metas.esb.edi',540431,550891,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','N','Y','Sektion',20,'R',TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'AD_Org_ID')
;

-- 2024-11-15T08:27:29.390Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589393,0,TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','de.metas.esb.edi',540431,550892,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','N','Y','Erstellt',30,'E',TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'Created')
;

-- 2024-11-15T08:27:29.508Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589394,0,TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','de.metas.esb.edi',540431,550893,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','N','Y','Erstellt durch',40,'R',TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'CreatedBy')
;

-- 2024-11-15T08:27:29.618Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589395,0,TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550894,'Y','Y','Y','DESADV',50,'R',TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Desadv_ID')
;

-- 2024-11-15T08:27:29.729Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589396,0,TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550895,'N','Y','EDI Lieferavis Pack (DESADV)',60,'R',TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Desadv_Pack_ID')
;

-- 2024-11-15T08:27:29.848Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589397,0,TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550896,'N','N','Übergeordneter Pack',70,'R',TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Desadv_Parent_Pack_ID')
;

-- 2024-11-15T08:27:29.964Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589398,0,TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'GTIN des verwendeten Gebindes, z.B. Palette. Wird automatisch über die Packvorschrift aus den Produkt-Stammdaten zum jeweiligen Lieferempfänger ermittelt.','de.metas.esb.edi',540431,550897,'N','N','Gebinde-GTIN',80,'E',TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'GTIN_PackingMaterial')
;

-- 2024-11-15T08:27:30.078Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589399,0,TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550898,'Y','Y','Y','SSCC18',90,'E',TO_TIMESTAMP('2024-11-15 10:27:29','YYYY-MM-DD HH24:MI:SS'),100,'IPA_SSCC18')
;

-- 2024-11-15T08:27:30.185Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589400,0,TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','de.metas.esb.edi',540431,550899,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','N','Y','Aktiv',100,'E',TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'IsActive')
;

-- 2024-11-15T08:27:30.297Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589401,0,TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'Wenn der jeweiligen Lieferzeile keine HU zugeordnet ist, dann setzt metasfresh dieses Feld auf "Ja" und der Nutzer kann manuell eine SSCC18 Nummer eintragen.','de.metas.esb.edi',540431,550900,'N','Y','manuelle SSCC18',110,'E',TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'IsManual_IPA_SSCC18')
;

-- 2024-11-15T08:27:30.407Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589402,0,TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550901,'N','N','Handling Unit',120,'R',TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_ID')
;

-- 2024-11-15T08:27:30.516Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589403,0,TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550902,'N','N','Verpackungscode',130,'R',TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_PackagingCode_ID')
;

-- 2024-11-15T08:27:30.617Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589404,0,TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540431,550903,'The current PackagingCode string from the current M_HU_PackagingCode_ID.
Not for display, just for EDI-export.','N','N','M_HU_PackagingCode_Text',140,'E',TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'M_HU_PackagingCode_Text')
;

-- 2024-11-15T08:27:30.716Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589405,0,TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document','de.metas.esb.edi',540431,550904,'The Material Shipment / Receipt ','N','N','Lieferung/Wareneingang',150,'R',TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'M_InOut_ID')
;

-- 2024-11-15T08:27:30.830Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589406,0,TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','de.metas.esb.edi',540431,550905,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','N','Y','Reihenfolge',160,'E',TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'SeqNo')
;

-- 2024-11-15T08:27:30.939Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589407,0,TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.esb.edi',540431,550906,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','N','Y','Aktualisiert',170,'E',TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'Updated')
;

-- 2024-11-15T08:27:31.048Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,EXP_Format_ID,EXP_FormatLine_ID,Help,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589408,0,TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','de.metas.esb.edi',540431,550907,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','N','Y','Aktualisiert durch',180,'R',TO_TIMESTAMP('2024-11-15 10:27:30','YYYY-MM-DD HH24:MI:SS'),100,'UpdatedBy')
;

-- 2024-11-15T08:28:09.027Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-15 10:28:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550905
;

-- 2024-11-15T08:28:19.563Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-15 10:28:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550897
;

-- 2024-11-15T08:28:28.850Z
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2024-11-15 10:28:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550903
;

-- 2024-11-15T08:28:42.114Z
UPDATE EXP_FormatLine SET IsActive='N',Updated=TO_TIMESTAMP('2024-11-15 10:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550894
;

-- 2024-11-15T08:48:10.335Z
UPDATE EXP_FormatLine SET EXP_EmbeddedFormat_ID=540433, Name='EDI_Exp_DesadvLineWithNoPack_1PerInOut', Value='EDI_Exp_DesadvLineWithNoPack_1PerInOut',Updated=TO_TIMESTAMP('2024-11-15 10:48:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550794
;

-- 2024-11-15T08:52:45.364Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550642
;

-- Column: M_InOut_DesadvLine_V.GTIN_TU
-- Column: M_InOut_DesadvLine_V.GTIN_TU
-- 2024-11-15T08:53:23.871Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589409,583258,0,10,542416,'XX','GTIN_TU',TO_TIMESTAMP('2024-11-15 10:53:23','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,50,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'TU-GTIN',0,0,TO_TIMESTAMP('2024-11-15 10:53:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:53:23.884Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589409 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:53:23.898Z
/* DDL */  select update_Column_Translation_From_AD_Element(583258)
;

-- Column: M_InOut_DesadvLine_V.GTIN_CU
-- Column: M_InOut_DesadvLine_V.GTIN_CU
-- 2024-11-15T08:53:34.710Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589410,583259,0,10,542416,'XX','GTIN_CU',TO_TIMESTAMP('2024-11-15 10:53:34','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,50,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'CU-GTIN',0,0,TO_TIMESTAMP('2024-11-15 10:53:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-11-15T08:53:34.723Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589410 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-11-15T08:53:34.745Z
/* DDL */  select update_Column_Translation_From_AD_Element(583259)
;

-- Column: M_InOut_DesadvLine_V.GTIN
-- Column: M_InOut_DesadvLine_V.GTIN
-- 2024-11-15T08:54:39.413Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588565
;

-- 2024-11-15T08:54:39.424Z
DELETE FROM AD_Column WHERE AD_Column_ID=588565
;

-- 2024-11-15T08:55:08.428Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,FilterOperator,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589410,0,TO_TIMESTAMP('2024-11-15 10:55:08','YYYY-MM-DD HH24:MI:SS'),100,'U',540429,550908,'E','Y','N','N','GTIN_CU',400,'E',TO_TIMESTAMP('2024-11-15 10:55:08','YYYY-MM-DD HH24:MI:SS'),100,'GTIN_CU')
;

-- 2024-11-15T08:55:21.013Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,FilterOperator,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,589409,0,TO_TIMESTAMP('2024-11-15 10:55:20','YYYY-MM-DD HH24:MI:SS'),100,'U',540429,550909,'E','Y','N','N','GTIN_TU',410,'E',TO_TIMESTAMP('2024-11-15 10:55:20','YYYY-MM-DD HH24:MI:SS'),100,'GTIN_TU')
;

-- 2024-11-15T09:03:47.332Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_EmbeddedFormat_ID,EXP_Format_ID,EXP_FormatLine_ID,FilterOperator,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2024-11-15 11:03:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540432,540431,550910,'E','Y','N','N','EDI_Desadv_Pack_Item_1PerInOut',190,'M',TO_TIMESTAMP('2024-11-15 11:03:47','YYYY-MM-DD HH24:MI:SS'),100,'EDI_Desadv_Pack_Item_1PerInOut')
;

-- Column: M_InOut_DesadvLine_V.M_InOut_DesadvLine_V_ID
-- Column: M_InOut_DesadvLine_V.M_InOut_DesadvLine_V_ID
-- 2024-11-15T09:07:42.282Z
UPDATE AD_Column SET AD_Reference_ID=13, IsUpdateable='N',Updated=TO_TIMESTAMP('2024-11-15 11:07:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588563
;

-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- 2024-11-15T09:10:22.579Z
UPDATE AD_Column SET AD_Reference_ID=19, IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2024-11-15 11:10:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589364
;

-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- Column SQL (old): (m_inoutline_id || '')
-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- Column SQL (old): (m_inoutline_id || '')
-- 2024-11-15T09:16:01.790Z
UPDATE AD_Column SET ColumnSQL='(m_inoutline_id || ''-'')',Updated=TO_TIMESTAMP('2024-11-15 11:16:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589364
;

-- 2024-11-15T09:17:56.276Z
UPDATE EXP_Format SET WhereClause='M_InOut_DesadvLine_V_ID NOT IN (select M_InOutLine_ID || ''-'' from Edi_Desadv_Pack_Item)',Updated=TO_TIMESTAMP('2024-11-15 11:17:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Format_ID=540433
;

---

-- 2024-11-15T09:40:30.302Z
UPDATE EXP_Format SET WhereClause='M_InOut_DesadvLine_V_ID NOT IN (select M_InOutLine_ID from Edi_Desadv_Pack_Item)',Updated=TO_TIMESTAMP('2024-11-15 11:40:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_Format_ID=540433
;

-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- Column SQL (old): (m_inoutline_id || '-')
-- Column: EDI_Desadv_Pack_Item.M_InOut_DesadvLine_V_ID
-- Column SQL (old): (m_inoutline_id || '-')
-- 2024-11-15T09:40:55.070Z
UPDATE AD_Column SET ColumnSQL='(m_inoutline_id)',Updated=TO_TIMESTAMP('2024-11-15 11:40:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589364
;

-- 2024-11-15T09:52:24.113Z
UPDATE EXP_FormatLine SET Name='EDI_Exp_Desadv_Pack', Value='EDI_Exp_Desadv_Pack',Updated=TO_TIMESTAMP('2024-11-15 11:52:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550793
;

-- 2024-11-15T09:52:38.260Z
UPDATE EXP_FormatLine SET Name='EDI_Exp_DesadvLineWithNoPack', Value='EDI_Exp_DesadvLineWithNoPack',Updated=TO_TIMESTAMP('2024-11-15 11:52:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550794
;

-- 2024-11-15T09:53:11.922Z
UPDATE EXP_FormatLine SET Name='EDI_Exp_Desadv_Pack_Item', Value='EDI_Exp_Desadv_Pack_Item',Updated=TO_TIMESTAMP('2024-11-15 11:53:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550910
;

-- 2024-11-15T09:54:31.519Z
UPDATE EXP_FormatLine SET Name='EDI_DesadvLine_ID', Value='EDI_DesadvLine_ID',Updated=TO_TIMESTAMP('2024-11-15 11:54:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550831
;

