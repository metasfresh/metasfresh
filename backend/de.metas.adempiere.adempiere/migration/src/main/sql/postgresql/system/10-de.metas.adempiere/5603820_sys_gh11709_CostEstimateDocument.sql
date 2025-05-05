-- New JasperDocument
-- 2021-07-08T12:07:57.620Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,JasperReport,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584863,'N','de.metas.report.jasper.client.process.JasperReportStarter',TO_TIMESTAMP('2021-07-08 14:07:57','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','Y','N','N','N','Y','Y','Y','@PREFIX@de/metas/docs/sales/order/report.jasper','CostEstimate(Jasper)','json','N','N','JasperReportsSQL',TO_TIMESTAMP('2021-07-08 14:07:57','YYYY-MM-DD HH24:MI:SS'),100,'CostEstimate(Jasper)')
;

-- 2021-07-08T12:07:57.628Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584863 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-07-08T12:08:01.838Z
-- URL zum Konzept
UPDATE AD_Process SET IsDirectPrint='Y',Updated=TO_TIMESTAMP('2021-07-08 14:08:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584863
;

--Create docType
-- 2021-07-08T12:24:00.618Z
-- URL zum Konzept
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541019,TO_TIMESTAMP('2021-07-08 14:24:00','YYYY-MM-DD HH24:MI:SS'),100,'SOO',1,'de.metas.swat',0,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','CostEstimate','CostEstimate',TO_TIMESTAMP('2021-07-08 14:24:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-08T12:24:00.618Z
-- URL zum Konzept
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_DocType_ID=541019 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2021-07-08T12:24:00.619Z
-- URL zum Konzept
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541019 AND rol.IsManual='N')
;

-- 2021-07-08T12:24:10.966Z
-- URL zum Konzept
UPDATE C_DocType SET DocumentCopies=0,Updated=TO_TIMESTAMP('2021-07-08 14:24:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541019
;

-- 2021-07-08T12:25:27.195Z
-- URL zum Konzept
UPDATE C_DocType SET IsSOTrx='Y',Updated=TO_TIMESTAMP('2021-07-08 14:25:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541019
;

-- 2021-07-08T12:26:25.099Z
-- URL zum Konzept
UPDATE C_DocType SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-07-08 14:26:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541019
;

-- 2021-07-08T12:26:39.403Z
-- URL zum Konzept
UPDATE C_DocType SET DocSubType='ON',Updated=TO_TIMESTAMP('2021-07-08 14:26:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541019
;

-- CeateSequence
-- 2021-07-08T12:28:31.801Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,1000000,555460,TO_TIMESTAMP('2021-07-08 14:28:31','YYYY-MM-DD HH24:MI:SS'),100,1000000,100,1,'Y','N','N','N','Belegnummer CostEstimate','N',1000000,TO_TIMESTAMP('2021-07-08 14:28:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-08T12:28:35.736Z
-- URL zum Konzept
UPDATE AD_Sequence SET IsAutoSequence='Y',Updated=TO_TIMESTAMP('2021-07-08 14:28:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555460
;

-- 2021-07-08T12:29:10.395Z
-- URL zum Konzept
UPDATE AD_Sequence SET CurrentNext=10,Updated=TO_TIMESTAMP('2021-07-08 14:29:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555460
;

-- 2021-07-08T12:29:19.785Z
-- URL zum Konzept
UPDATE AD_Sequence SET DecimalPattern='000',Updated=TO_TIMESTAMP('2021-07-08 14:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555460
;

-- 2021-07-08T12:29:23.800Z
-- URL zum Konzept
UPDATE AD_Sequence SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-07-08 14:29:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555460
;

-- Set Sequence in the new DocumentType
-- 2021-07-08T12:31:48.331Z
-- URL zum Konzept
UPDATE C_DocType SET DocNoSequence_ID=555460,Updated=TO_TIMESTAMP('2021-07-08 14:31:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541019
;

-- Trl Document
-- 2021-07-08T12:34:47.948Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET Name='Kostenvoranschlag',Updated=TO_TIMESTAMP('2021-07-08 14:34:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541019
;

-- 2021-07-08T12:34:51.542Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET PrintName='Kostenvoranschlag',Updated=TO_TIMESTAMP('2021-07-08 14:34:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541019
;

-- 2021-07-08T12:34:51.649Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-08 14:34:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541019
;

-- 2021-07-08T12:35:14.712Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET Name='Kostenvoranschlag',Updated=TO_TIMESTAMP('2021-07-08 14:35:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541019
;

-- 2021-07-08T12:35:17.920Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET PrintName='Kostenvoranschlag',Updated=TO_TIMESTAMP('2021-07-08 14:35:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541019
;

-- 2021-07-08T12:35:17.995Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-08 14:35:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541019
;

-- 2021-07-08T12:35:32.635Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET Name='Kostenvoranschlag',Updated=TO_TIMESTAMP('2021-07-08 14:35:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_DocType_ID=541019
;

-- 2021-07-08T12:35:35.513Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET PrintName='Kostenvoranschlag',Updated=TO_TIMESTAMP('2021-07-08 14:35:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_DocType_ID=541019
;

-- 2021-07-08T12:35:35.576Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-08 14:35:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_DocType_ID=541019
;

-- 2021-07-08T12:35:43.427Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET Name='Cost Estimate',Updated=TO_TIMESTAMP('2021-07-08 14:35:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541019
;

-- 2021-07-08T12:35:46.213Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET PrintName='Cost Estimate',Updated=TO_TIMESTAMP('2021-07-08 14:35:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541019
;

-- 2021-07-08T12:35:46.320Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-08 14:35:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541019
;


-- create Print format
-- 2021-07-08T13:05:08.203Z
-- URL zum Konzept
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_Table_ID,Created,CreatedBy,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,Name,Updated,UpdatedBy) VALUES (1000000,1000000,100,540006,'None',540119,102,496,TO_TIMESTAMP('2021-07-08 15:05:08','YYYY-MM-DD HH24:MI:SS'),100,0,0,'Y','N','N','Y','Y','CostEstimate',TO_TIMESTAMP('2021-07-08 15:05:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-08T13:05:13.875Z
-- URL zum Konzept
UPDATE AD_PrintFormat SET JasperProcess_ID=500007,Updated=TO_TIMESTAMP('2021-07-08 15:05:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540119
;

-- 2021-07-08T13:05:31.755Z
-- URL zum Konzept
UPDATE AD_PrintFormat SET Description='Druckformat Auftrag',Updated=TO_TIMESTAMP('2021-07-08 15:05:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540119
;

-- 2021-07-08T13:05:31.907Z
-- URL zum Konzept
UPDATE AD_PrintFormat SET IsForm='Y',Updated=TO_TIMESTAMP('2021-07-08 15:05:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540119
;

-- 2021-07-08T13:06:12.652Z
-- URL zum Konzept
UPDATE AD_PrintFormat SET AD_PrintFont_ID=540008,Updated=TO_TIMESTAMP('2021-07-08 15:06:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540119
;

-- 2021-07-08T13:06:19.452Z
-- URL zum Konzept
UPDATE AD_PrintFormat SET AD_PrintPaper_ID=103,Updated=TO_TIMESTAMP('2021-07-08 15:06:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540119
;

-- 2021-07-08T13:06:28.815Z
-- URL zum Konzept
UPDATE AD_PrintFormat SET IsStandardHeaderFooter='N',Updated=TO_TIMESTAMP('2021-07-08 15:06:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540119
;

-- 2021-07-08T13:11:16.434Z
-- URL zum Konzept
INSERT INTO AD_PrintFormatItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_PrintFormat_ID,AD_PrintFormatItem_ID,ArcDiameter,Created,CreatedBy,FieldAlignmentType,ImageIsAttached,IsActive,IsAveraged,IsCentrallyMaintained,IsCounted,IsDeviationCalc,IsFilledRectangle,IsFixedWidth,IsGroupBy,IsHeightOneLine,IsImageField,IsMaxCalc,IsMinCalc,IsNextLine,IsNextPage,IsOrderBy,IsPageBreak,IsPrinted,IsRelativePosition,IsRunningTotal,IsSetNLPosition,IsSummarized,IsSuppressNull,IsSuppressRepeats,IsVarianceCalc,LineAlignmentType,LineWidth,MaxHeight,MaxWidth,Name,PrintAreaType,PrintFormatType,RunningTotalLines,SeqNo,ShapeType,SortNo,Updated,UpdatedBy,XPosition,XSpace,YPosition,YSpace) VALUES (1000000,7086,1000000,540119,540981,0,TO_TIMESTAMP('2021-07-08 15:11:16','YYYY-MM-DD HH24:MI:SS'),100,'L','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','Y','Y','N','N','N','N','N','N','X',1,0,0,'Message Text','C','F',20,10,'N',0,TO_TIMESTAMP('2021-07-08 15:11:16','YYYY-MM-DD HH24:MI:SS'),100,0,0,0,0)
;

-- 2021-07-08T13:11:16.434Z
-- URL zum Konzept
INSERT INTO AD_PrintFormatItem_Trl (AD_Language,AD_PrintFormatItem_ID, PrintName,PrintNameSuffix, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_PrintFormatItem_ID, t.PrintName,t.PrintNameSuffix, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_PrintFormatItem t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_PrintFormatItem_ID=540981 AND NOT EXISTS (SELECT 1 FROM AD_PrintFormatItem_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_PrintFormatItem_ID=t.AD_PrintFormatItem_ID)
;

-- 2021-07-08T13:11:40.049Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem SET IsActive='Y', IsGroupBy='N', IsPageBreak='N', SortNo=0, XPosition=0, YPosition=0,Updated=TO_TIMESTAMP('2021-07-08 15:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormatItem_ID=540981
;

-- 2021-07-08T13:11:45.723Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem SET AD_Org_ID=0, IsGroupBy='N', IsPageBreak='N', SortNo=0, XPosition=0, YPosition=0,Updated=TO_TIMESTAMP('2021-07-08 15:11:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormatItem_ID=540981
;

-- 2021-07-08T13:11:49.210Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem SET IsActive='N', IsGroupBy='N', IsPageBreak='N', SortNo=0, XPosition=0, YPosition=0,Updated=TO_TIMESTAMP('2021-07-08 15:11:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormatItem_ID=540981
;

-- Change Print text and add TRL
-- 2021-07-09T08:22:39.720Z
-- URL zum Konzept
UPDATE C_DocType SET PrintName='CostEstimate Nr.',Updated=TO_TIMESTAMP('2021-07-09 10:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541019
;

-- 2021-07-09T08:22:39.723Z
-- URL zum Konzept
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='CostEstimate', PrintName='CostEstimate Nr.'  WHERE C_DocType_ID=541019 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-07-09T08:24:33.359Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET PrintName='Kostenvoranschlag Nr.',Updated=TO_TIMESTAMP('2021-07-09 10:24:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541019
;

-- 2021-07-09T08:38:30.288Z
-- URL zum Konzept
UPDATE C_DocType SET Name='Kostenvoranschlag',Updated=TO_TIMESTAMP('2021-07-09 10:38:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541019
;

-- 2021-07-09T08:38:30.290Z
-- URL zum Konzept
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Kostenvoranschlag', PrintName='CostEstimate Nr.'  WHERE C_DocType_ID=541019 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-07-09T08:38:34.683Z
-- URL zum Konzept
UPDATE C_DocType SET PrintName='Kostenvoranschlag Nr.',Updated=TO_TIMESTAMP('2021-07-09 10:38:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541019
;

-- 2021-07-09T08:38:34.688Z
-- URL zum Konzept
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Kostenvoranschlag', PrintName='Kostenvoranschlag Nr.'  WHERE C_DocType_ID=541019 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- Set Cost Estimate document subtype to cost estimate
-- 2021-07-09T09:57:03.976Z
-- URL zum Konzept
UPDATE C_DocType SET DocSubType='CE',Updated=TO_TIMESTAMP('2021-07-09 11:57:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541019
;

-- 2021-07-16T07:50:11.367Z
-- URL zum Konzept
UPDATE C_DocType SET AD_PrintFormat_ID=540119,Updated=TO_TIMESTAMP('2021-07-16 09:50:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541019
;