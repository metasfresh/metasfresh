
-- 26.03.2016 16:33
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='PMM_Trend_InTwoWeeks', Name='Trend (in zwei Wochen)', PrintName='Trend (in zwei Wochen)',Updated=TO_TIMESTAMP('2016-03-26 16:33:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543045
;

-- 26.03.2016 16:33
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543045
;

-- 26.03.2016 16:33
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='PMM_Trend_InTwoWeeks', Name='Trend (in zwei Wochen)', Description=NULL, Help=NULL WHERE AD_Element_ID=543045
;

-- 26.03.2016 16:33
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PMM_Trend_InTwoWeeks', Name='Trend (in zwei Wochen)', Description=NULL, Help=NULL, AD_Element_ID=543045 WHERE UPPER(ColumnName)='PMM_TREND_INTWOWEEKS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 26.03.2016 16:33
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='PMM_Trend_InTwoWeeks', Name='Trend (in zwei Wochen)', Description=NULL, Help=NULL WHERE AD_Element_ID=543045 AND IsCentrallyMaintained='Y'
;

-- 26.03.2016 16:33
-- URL zum Konzept
UPDATE AD_Field SET Name='Trend (in zwei Wochen)', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543045) AND IsCentrallyMaintained='Y'
;

-- 26.03.2016 16:33
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Trend (in zwei Wochen)', Name='Trend (in zwei Wochen)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543045)
;

-- 26.03.2016 16:34
-- URL zum Konzept
UPDATE AD_Column SET ColumnSQL='(de_metas_procurement.getPMM_PurchaseCandidate_Weekly(PMM_PurchaseCandidate, +2)).PMM_Trend',Updated=TO_TIMESTAMP('2016-03-26 16:34:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554263
;

-- 26.03.2016 16:34
-- URL zum Konzept
UPDATE AD_Column SET IsActive='N',Updated=TO_TIMESTAMP('2016-03-26 16:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554264
;

-- 26.03.2016 16:35
-- URL zum Konzept
UPDATE AD_Element SET Name='Zusagbar (nächste Woche)', PrintName='Zusagbar (nächste Woche)',Updated=TO_TIMESTAMP('2016-03-26 16:35:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543028
;

-- 26.03.2016 16:35
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543028
;

-- 26.03.2016 16:35
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='QtyPromised_NextWeek', Name='Zusagbar (nächste Woche)', Description=NULL, Help=NULL WHERE AD_Element_ID=543028
;

-- 26.03.2016 16:35
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='QtyPromised_NextWeek', Name='Zusagbar (nächste Woche)', Description=NULL, Help=NULL, AD_Element_ID=543028 WHERE UPPER(ColumnName)='QTYPROMISED_NEXTWEEK' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 26.03.2016 16:35
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='QtyPromised_NextWeek', Name='Zusagbar (nächste Woche)', Description=NULL, Help=NULL WHERE AD_Element_ID=543028 AND IsCentrallyMaintained='Y'
;

-- 26.03.2016 16:35
-- URL zum Konzept
UPDATE AD_Field SET Name='Zusagbar (nächste Woche)', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543028) AND IsCentrallyMaintained='Y'
;

-- 26.03.2016 16:35
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zusagbar (nächste Woche)', Name='Zusagbar (nächste Woche)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543028)
;

-- 26.03.2016 16:36
-- URL zum Konzept
UPDATE AD_Element SET Name='Zusagbar (diese Woche)', PrintName='Zusagbar (diese Woche)',Updated=TO_TIMESTAMP('2016-03-26 16:36:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543027
;

-- 26.03.2016 16:36
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543027
;

-- 26.03.2016 16:36
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='QtyPromised_ThisWeek', Name='Zusagbar (diese Woche)', Description=NULL, Help=NULL WHERE AD_Element_ID=543027
;

-- 26.03.2016 16:36
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='QtyPromised_ThisWeek', Name='Zusagbar (diese Woche)', Description=NULL, Help=NULL, AD_Element_ID=543027 WHERE UPPER(ColumnName)='QTYPROMISED_THISWEEK' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 26.03.2016 16:36
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='QtyPromised_ThisWeek', Name='Zusagbar (diese Woche)', Description=NULL, Help=NULL WHERE AD_Element_ID=543027 AND IsCentrallyMaintained='Y'
;

-- 26.03.2016 16:36
-- URL zum Konzept
UPDATE AD_Field SET Name='Zusagbar (diese Woche)', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543027) AND IsCentrallyMaintained='Y'
;

-- 26.03.2016 16:36
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zusagbar (diese Woche)', Name='Zusagbar (diese Woche)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543027)
;


-- 26.03.2016 16:38
-- URL zum Konzept
UPDATE AD_Ref_List SET Name=E'\u25cf gleich',Updated=TO_TIMESTAMP('2016-03-26 16:38:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541193
;

-- 26.03.2016 16:38
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541193
;



-- 26.03.2016 16:42
-- URL zum Konzept
UPDATE AD_Ref_List SET Name=E'\u25B2 mehr',Updated=TO_TIMESTAMP('2016-03-26 16:42:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541191
;

-- 26.03.2016 16:42
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541191
;

-- 26.03.2016 16:42
-- URL zum Konzept
UPDATE AD_Ref_List SET Name=E'\u25BC weniger',Updated=TO_TIMESTAMP('2016-03-26 16:42:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541192
;

-- 26.03.2016 16:42
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='N' WHERE AD_Ref_List_ID=541192
;

--
-----------------------
--
-- 26.03.2016 20:23
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Bestellen',Updated=TO_TIMESTAMP('2016-03-26 20:23:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543808
;

-- 26.03.2016 20:23
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543808
;

-- 26.03.2016 20:24
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Soll die Auswahl bestellt werden?',Updated=TO_TIMESTAMP('2016-03-26 20:24:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543809
;

-- 26.03.2016 20:24
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543809
;

