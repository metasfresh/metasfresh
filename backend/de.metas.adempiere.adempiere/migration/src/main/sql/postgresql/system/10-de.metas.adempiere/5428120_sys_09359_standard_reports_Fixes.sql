
-- I deactivated some of the print format Items that seem not to exist as columns

-- 21.09.2015 12:50
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem SET IsActive='N', IsGroupBy='N', IsPageBreak='N', SortNo=0, XPosition=0, YPosition=0,Updated=TO_TIMESTAMP('2015-09-21 12:50:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormatItem_ID=51066
;

-- 21.09.2015 12:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem SET IsActive='N', IsGroupBy='N', IsPageBreak='N', SortNo=0, XPosition=0, YPosition=0,Updated=TO_TIMESTAMP('2015-09-21 12:53:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormatItem_ID=51067
;

-- 21.09.2015 12:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2015-09-21 12:55:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=56687
;

-- 21.09.2015 12:57
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsActive='Y',Updated=TO_TIMESTAMP('2015-09-21 12:57:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=56687
;

-- 21.09.2015 13:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_PrintFormatItem_Trl WHERE AD_PrintFormatItem_ID=51066
;

-- 21.09.2015 13:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_PrintFormatItem WHERE AD_PrintFormatItem_ID=51066
;

-- 21.09.2015 13:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_PrintFormatItem_Trl WHERE AD_PrintFormatItem_ID=51067
;

-- 21.09.2015 13:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_PrintFormatItem WHERE AD_PrintFormatItem_ID=51067
;


-- 21.09.2015 16:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_PrintFont (AD_Client_ID,AD_Org_ID,AD_PrintFont_ID,Code,Created,CreatedBy,IsActive,IsDefault,Name,Updated,UpdatedBy) VALUES (0,0,540006,'sansserif-PLAIN-6',TO_TIMESTAMP('2015-09-21 16:22:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','SansSerif 6',TO_TIMESTAMP('2015-09-21 16:22:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 21.09.2015 16:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFont SET IsDefault='N',Updated=TO_TIMESTAMP('2015-09-21 16:22:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFont_ID=163
;

-- 21.09.2015 16:22
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFont SET IsDefault='Y',Updated=TO_TIMESTAMP('2015-09-21 16:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFont_ID=540006
;

-- 21.09.2015 16:44
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintTableFormat SET IsPaintHeaderLines='N', IsPaintHLines='N', IsPaintVLines='N',Updated=TO_TIMESTAMP('2015-09-21 16:44:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintTableFormat_ID=100
;

-- 21.09.2015 16:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintTableFormat SET IsPaintBoundaryLines='Y', IsPaintHeaderLines='Y', IsPaintHLines='Y', IsPaintVLines='Y',Updated=TO_TIMESTAMP('2015-09-21 16:45:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintTableFormat_ID=100
;

-- 21.09.2015 16:47
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintTableFormat SET IsPaintBoundaryLines='N', IsPaintHeaderLines='N', IsPaintHLines='N', IsPaintVLines='N',Updated=TO_TIMESTAMP('2015-09-21 16:47:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintTableFormat_ID=100
;

-- 21.09.2015 17:05
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintTableFormat SET IsPaintBoundaryLines='Y', IsPaintHeaderLines='Y', IsPaintHLines='Y', IsPaintVLines='Y',Updated=TO_TIMESTAMP('2015-09-21 17:05:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintTableFormat_ID=100
;

-- 21.09.2015 17:45
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintTableFormat SET IsPaintBoundaryLines='N', IsPaintHeaderLines='N', IsPaintHLines='N', IsPaintVLines='N',Updated=TO_TIMESTAMP('2015-09-21 17:45:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintTableFormat_ID=100
;

-- 21.09.2015 18:02
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintTableFormat SET IsMultiLineHeader='Y',Updated=TO_TIMESTAMP('2015-09-21 18:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintTableFormat_ID=100
;

-- 21.09.2015 18:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintTableFormat SET FunctFG_PrintColor_ID=100, HdrTextFG_PrintColor_ID=100, Line_PrintColor_ID=108,Updated=TO_TIMESTAMP('2015-09-21 18:30:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintTableFormat_ID=100
;

-- 21.09.2015 18:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintTableFormat SET Funct_PrintFont_ID=540006,Updated=TO_TIMESTAMP('2015-09-21 18:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintTableFormat_ID=100
;














-- Modify the Default Print Format




-- 22.09.2015 16:33
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintTableFormat SET LineStroke=0,Updated=TO_TIMESTAMP('2015-09-22 16:33:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintTableFormat_ID=100
;
-- 22.09.2015 16:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_PrintFont (AD_Client_ID,AD_Org_ID,AD_PrintFont_ID,Code,Created,CreatedBy,IsActive,IsDefault,Name,Updated,UpdatedBy) VALUES (0,0,540008,'sansserif-PLAIN-7',TO_TIMESTAMP('2015-09-22 16:41:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','SansSerif7',TO_TIMESTAMP('2015-09-22 16:41:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.09.2015 16:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFont SET Name='SansSerif 7',Updated=TO_TIMESTAMP('2015-09-22 16:42:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFont_ID=540008
;

-- 22.09.2015 16:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintTableFormat SET Funct_PrintFont_ID=540008,Updated=TO_TIMESTAMP('2015-09-22 16:42:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintTableFormat_ID=100
;

-- 22.09.2015 17:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintTableFormat SET Funct_PrintFont_ID=NULL,Updated=TO_TIMESTAMP('2015-09-22 17:13:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintTableFormat_ID=100
;

-- 22.09.2015 17:14
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintTableFormat SET IsMultiLineHeader='N',Updated=TO_TIMESTAMP('2015-09-22 17:14:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintTableFormat_ID=100
;









-- UPDATE A4 Landscape





-- 23.09.2015 15:51
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintPaper SET MarginBottom=18,Updated=TO_TIMESTAMP('2015-09-23 15:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintPaper_ID=102
;

-- 23.09.2015 15:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintPaper SET MarginBottom=36, MarginLeft=18,Updated=TO_TIMESTAMP('2015-09-23 15:52:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintPaper_ID=102
;

-- 23.09.2015 15:55
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintPaper SET MarginLeft=36, MarginTop=18,Updated=TO_TIMESTAMP('2015-09-23 15:55:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintPaper_ID=102
;

-- 23.09.2015 15:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintPaper SET MarginRight=18, MarginTop=36,Updated=TO_TIMESTAMP('2015-09-23 15:56:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintPaper_ID=102
;


-- 23.09.2015 16:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintPaper SET MarginLeft=18, MarginRight=36,Updated=TO_TIMESTAMP('2015-09-23 16:56:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintPaper_ID=102
;

-- 23.09.2015 17:35
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintPaper SET MarginLeft=36, MarginRight=18,Updated=TO_TIMESTAMP('2015-09-23 17:35:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintPaper_ID=102
;

-- 24.09.2015 09:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543629,0,TO_TIMESTAMP('2015-09-24 09:52:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','/','I',TO_TIMESTAMP('2015-09-24 09:52:03','YYYY-MM-DD HH24:MI:SS'),100,'slash')
;

-- 24.09.2015 09:52
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543629 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;













UPDATE AD_PrintFormatItem SET
AD_PrintFont_ID=null, AD_PrintColor_ID=null;



UPDATE AD_PrintFormat SET
AD_PrintTableFormat_ID=100;


UPDATE AD_PrintFormat SET
 AD_PrintFont_ID=540008, AD_PrintColor_ID=100; -- SS7
