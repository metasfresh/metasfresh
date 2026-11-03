
--
-- fix name for 
-- Parent_HU_Trx_Line_ID
--
-- 30.10.2015 08:03
-- URL zum Konzept
UPDATE AD_Element SET Name='Eltern-Transaktionszeile', PrintName='Eltern-Transaktionszeile',Updated=TO_TIMESTAMP('2015-10-30 08:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542148
;

-- 30.10.2015 08:03
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542148
;

-- 30.10.2015 08:03
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='Parent_HU_Trx_Line_ID', Name='Eltern-Transaktionszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=542148
;

-- 30.10.2015 08:03
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Parent_HU_Trx_Line_ID', Name='Eltern-Transaktionszeile', Description=NULL, Help=NULL, AD_Element_ID=542148 WHERE UPPER(ColumnName)='PARENT_HU_TRX_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 30.10.2015 08:03
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='Parent_HU_Trx_Line_ID', Name='Eltern-Transaktionszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=542148 AND IsCentrallyMaintained='Y'
;

-- 30.10.2015 08:03
-- URL zum Konzept
UPDATE AD_Field SET Name='Eltern-Transaktionszeile', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542148) AND IsCentrallyMaintained='Y'
;

-- 30.10.2015 08:03
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Eltern-Transaktionszeile', Name='Eltern-Transaktionszeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542148)
;

-- 30.10.2015 08:06
-- URL zum Konzept
UPDATE AD_Tab SET Description='Note; we don''t store HU-Trx-Attributes anymore', IsActive='N',Updated=TO_TIMESTAMP('2015-10-30 08:06:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540516
;

-- 30.10.2015 08:06
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540516
;

-- 30.10.2015 08:06
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-10-30 08:06:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552230
;


-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552230
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552234
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552227
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552228
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555493
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552232
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553769
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552233
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552231
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552229
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552225
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555492
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555491
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552226
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553983
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553173
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553172
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552235
;

-- 30.10.2015 08:10
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2015-10-30 08:10:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552219
;

-- 30.10.2015 08:11
-- URL zum Konzept
UPDATE AD_Field SET IsCentrallyMaintained='N', Name='Eltern-Zeile',Updated=TO_TIMESTAMP('2015-10-30 08:11:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552232
;

-- 30.10.2015 08:11
-- URL zum Konzept
UPDATE AD_Field_Trl SET IsTranslated='N' WHERE AD_Field_ID=552232
;

-- 30.10.2015 08:11
-- URL zum Konzept
UPDATE AD_Element SET Name='HU Transaktionskopf', PrintName='HU Transaktionskopf',Updated=TO_TIMESTAMP('2015-10-30 08:11:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542138
;

-- 30.10.2015 08:11
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542138
;

-- 30.10.2015 08:11
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_HU_Trx_Hdr_ID', Name='HU Transaktionskopf', Description=NULL, Help=NULL WHERE AD_Element_ID=542138
;

-- 30.10.2015 08:11
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_HU_Trx_Hdr_ID', Name='HU Transaktionskopf', Description=NULL, Help=NULL, AD_Element_ID=542138 WHERE UPPER(ColumnName)='M_HU_TRX_HDR_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 30.10.2015 08:11
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_HU_Trx_Hdr_ID', Name='HU Transaktionskopf', Description=NULL, Help=NULL WHERE AD_Element_ID=542138 AND IsCentrallyMaintained='Y'
;

-- 30.10.2015 08:11
-- URL zum Konzept
UPDATE AD_Field SET Name='HU Transaktionskopf', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542138) AND IsCentrallyMaintained='Y'
;

-- 30.10.2015 08:11
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='HU Transaktionskopf', Name='HU Transaktionskopf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542138)
;

-- 30.10.2015 08:12
-- URL zum Konzept
UPDATE AD_Element SET Name='HU Transaktionszeile', PrintName='HU Transaktionszeile',Updated=TO_TIMESTAMP('2015-10-30 08:12:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542139
;

-- 30.10.2015 08:12
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542139
;

-- 30.10.2015 08:12
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_HU_Trx_Line_ID', Name='HU Transaktionszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=542139
;

-- 30.10.2015 08:12
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_HU_Trx_Line_ID', Name='HU Transaktionszeile', Description=NULL, Help=NULL, AD_Element_ID=542139 WHERE UPPER(ColumnName)='M_HU_TRX_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 30.10.2015 08:12
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_HU_Trx_Line_ID', Name='HU Transaktionszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=542139 AND IsCentrallyMaintained='Y'
;

-- 30.10.2015 08:12
-- URL zum Konzept
UPDATE AD_Field SET Name='HU Transaktionszeile', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542139) AND IsCentrallyMaintained='Y'
;

-- 30.10.2015 08:12
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='HU Transaktionszeile', Name='HU Transaktionszeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542139)
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Table SET Name='HU Transaktionskopf',Updated=TO_TIMESTAMP('2015-10-30 08:13:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540514
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540514
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Window SET Name='HU-Transaktion',Updated=TO_TIMESTAMP('2015-10-30 08:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540190
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Window_Trl SET IsTranslated='N' WHERE AD_Window_ID=540190
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='HU-Transaktion',Updated=TO_TIMESTAMP('2015-10-30 08:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540482
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=540482
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Table SET Name='HU-Transaktionskopf',Updated=TO_TIMESTAMP('2015-10-30 08:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540514
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540514
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Element SET Name='HU-Transaktionskopf', PrintName='HU-Transaktionskopf',Updated=TO_TIMESTAMP('2015-10-30 08:13:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542138
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542138
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_HU_Trx_Hdr_ID', Name='HU-Transaktionskopf', Description=NULL, Help=NULL WHERE AD_Element_ID=542138
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_HU_Trx_Hdr_ID', Name='HU-Transaktionskopf', Description=NULL, Help=NULL, AD_Element_ID=542138 WHERE UPPER(ColumnName)='M_HU_TRX_HDR_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_HU_Trx_Hdr_ID', Name='HU-Transaktionskopf', Description=NULL, Help=NULL WHERE AD_Element_ID=542138 AND IsCentrallyMaintained='Y'
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_Field SET Name='HU-Transaktionskopf', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542138) AND IsCentrallyMaintained='Y'
;

-- 30.10.2015 08:13
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='HU-Transaktionskopf', Name='HU-Transaktionskopf' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542138)
;

-- 30.10.2015 08:14
-- URL zum Konzept
UPDATE AD_Tab SET Name='HU-Transaktionskopf',Updated=TO_TIMESTAMP('2015-10-30 08:14:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540514
;

-- 30.10.2015 08:14
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540514
;

-- 30.10.2015 08:14
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2015-10-30 08:14:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552216
;

-- 30.10.2015 08:14
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y', SeqNo=5, SeqNoGrid=5,Updated=TO_TIMESTAMP('2015-10-30 08:14:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552215
;

-- 30.10.2015 08:14
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2015-10-30 08:14:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549235
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_Tab SET Name='HU-Transaktionszeile',Updated=TO_TIMESTAMP('2015-10-30 08:15:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540515
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540515
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_Table SET Name='HU-Transaktionszeile',Updated=TO_TIMESTAMP('2015-10-30 08:15:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540515
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540515
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N', Name='HU-Transaktionszeile',Updated=TO_TIMESTAMP('2015-10-30 08:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549243
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_Column_Trl SET IsTranslated='N' WHERE AD_Column_ID=549243
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_Field SET Name='HU-Transaktionszeile', Description=NULL, Help=NULL WHERE AD_Column_ID=549243 AND IsCentrallyMaintained='Y'
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_Element SET Name='HU-Transaktionszeile', PrintName='HU-Transaktionszeile',Updated=TO_TIMESTAMP('2015-10-30 08:15:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542139
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542139
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_HU_Trx_Line_ID', Name='HU-Transaktionszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=542139
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_HU_Trx_Line_ID', Name='HU-Transaktionszeile', Description=NULL, Help=NULL, AD_Element_ID=542139 WHERE UPPER(ColumnName)='M_HU_TRX_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_HU_Trx_Line_ID', Name='HU-Transaktionszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=542139 AND IsCentrallyMaintained='Y'
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_Field SET Name='HU-Transaktionszeile', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542139) AND IsCentrallyMaintained='Y'
;

-- 30.10.2015 08:15
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='HU-Transaktionszeile', Name='HU-Transaktionszeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542139)
;

-- 30.10.2015 08:16
-- URL zum Konzept
UPDATE AD_Column SET AllowZoomTo='Y',Updated=TO_TIMESTAMP('2015-10-30 08:16:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549312
;

-- 30.10.2015 08:16
-- URL zum Konzept
UPDATE AD_Column SET AllowZoomTo='N',Updated=TO_TIMESTAMP('2015-10-30 08:16:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549312
;

-- 30.10.2015 08:18
-- URL zum Konzept
UPDATE AD_Column SET AllowZoomTo='Y',Updated=TO_TIMESTAMP('2015-10-30 08:18:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549312
;

-- 30.10.2015 08:19
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2015-10-30 08:19:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549235
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', SeqNo=0,Updated=TO_TIMESTAMP('2015-10-30 08:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552230
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2015-10-30 08:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552234
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2015-10-30 08:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552227
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2015-10-30 08:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552228
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2015-10-30 08:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552233
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2015-10-30 08:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552231
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2015-10-30 08:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552229
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=70,Updated=TO_TIMESTAMP('2015-10-30 08:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552235
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=80,Updated=TO_TIMESTAMP('2015-10-30 08:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552219
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2015-10-30 08:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552225
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2015-10-30 08:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552226
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2015-10-30 08:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553983
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2015-10-30 08:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553173
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2015-10-30 08:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553172
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2015-10-30 08:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552232
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2015-10-30 08:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553769
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2015-10-30 08:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555493
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2015-10-30 08:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555492
;

-- 30.10.2015 08:22
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=180,Updated=TO_TIMESTAMP('2015-10-30 08:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555491
;

-- 30.10.2015 08:26
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2015-10-30 08:26:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540514
;

-- 30.10.2015 08:26
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2015-10-30 08:26:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540515
;

-- 30.10.2015 08:27
-- URL zum Konzept
UPDATE AD_Tab SET Description='Note: currently the trx header contains barely any usefull information..it''s actually just an enverlope for transaction lines', IsActive='N',Updated=TO_TIMESTAMP('2015-10-30 08:27:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540514
;

-- 30.10.2015 08:27
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540514
;


-- 30.10.2015 08:27
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=0,Updated=TO_TIMESTAMP('2015-10-30 08:27:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540515
;




-- 30.10.2015 08:27
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2015-10-30 08:27:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540515
;

-- 30.10.2015 08:28
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2015-10-30 08:28:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549312
;

-- 30.10.2015 08:28
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2015-10-30 08:28:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551467
;

-- 30.10.2015 08:28
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2015-10-30 08:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549313
;

-- 30.10.2015 08:28
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2015-10-30 08:28:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551468
;

-- 30.10.2015 08:28
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2015-10-30 08:28:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551812
;

-- 30.10.2015 08:28
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2015-10-30 08:28:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549841
;

-- 30.10.2015 08:29
-- URL zum Konzept
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-10-30 08:29:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552228
;

-- 30.10.2015 08:34
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2015-10-30 08:34:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540669
;

-- 30.10.2015 08:34
-- URL zum Konzept
UPDATE AD_Table SET AD_Window_ID=540189,Updated=TO_TIMESTAMP('2015-10-30 08:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540669
;

-- 30.10.2015 08:38
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2015-10-30 08:38:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549310
;

-- 30.10.2015 08:38
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N', SeqNo=20,Updated=TO_TIMESTAMP('2015-10-30 08:38:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549243
;

-- 30.10.2015 08:39
-- URL zum Konzept
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=30,Updated=TO_TIMESTAMP('2015-10-30 08:39:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551812
;

-- 30.10.2015 08:41
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2015-10-30 08:41:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549310
;

-- 30.10.2015 08:42
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2015-10-30 08:42:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549235
;

-- 30.10.2015 08:45
-- URL zum Konzept
UPDATE AD_Column SET IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2015-10-30 08:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549235
;

-- 30.10.2015 08:45
-- URL zum Konzept
UPDATE AD_Table SET ACTriggerLength=4, IsAutocomplete='Y',Updated=TO_TIMESTAMP('2015-10-30 08:45:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540514
;

-- 30.10.2015 08:51
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=540499, IsUpdateable='N',Updated=TO_TIMESTAMP('2015-10-30 08:51:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552697
;


