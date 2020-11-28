
-- 23.02.2016 08:16
-- URL zum Konzept
UPDATE AD_Table SET Name='Lagerstatistik - Zuordnungszeile',Updated=TO_TIMESTAMP('2016-02-23 08:16:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540694
;

-- 23.02.2016 08:16
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540694
;

-- 23.02.2016 08:16
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2016-02-23 08:16:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552939
;

-- 23.02.2016 08:17
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2016-02-23 08:17:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552937
;

-- 23.02.2016 08:17
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2016-02-23 08:17:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552934
;

-- 23.02.2016 08:17
-- URL zum Konzept
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2016-02-23 08:17:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540694
;

-- 23.02.2016 08:18
-- URL zum Konzept
UPDATE AD_Table SET Name='Lagerstatistik - Zeile',Updated=TO_TIMESTAMP('2016-02-23 08:18:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540693
;

-- 23.02.2016 08:18
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540693
;

-- 23.02.2016 08:18
-- URL zum Konzept
UPDATE AD_Table SET IsDeleteable='N',Updated=TO_TIMESTAMP('2016-02-23 08:18:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540693
;

-- 23.02.2016 08:18
-- URL zum Konzept
UPDATE AD_Table SET IsHighVolume='Y',Updated=TO_TIMESTAMP('2016-02-23 08:18:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540694
;

-- 23.02.2016 08:18
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2016-02-23 08:18:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552920
;

-- 23.02.2016 08:18
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2016-02-23 08:18:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552921
;

-- 23.02.2016 08:18
-- URL zum Konzept
UPDATE M_Material_Tracking_Report_Line SET IsActive='Y' WHERE IsActive IS NULL
;

-- 23.02.2016 08:19
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, IsUpdateable='N',Updated=TO_TIMESTAMP('2016-02-23 08:19:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552929
;

-- 23.02.2016 08:19
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2016-02-23 08:19:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552923
;

-- 23.02.2016 08:19
-- URL zum Konzept
UPDATE AD_Table SET Name='Lagerstatistik',Updated=TO_TIMESTAMP('2016-02-23 08:19:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540692
;

-- 23.02.2016 08:19
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540692
;

-- 23.02.2016 08:19
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2016-02-23 08:19:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552915
;

-- 23.02.2016 08:19
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2016-02-23 08:19:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552909
;

-- 23.02.2016 08:20
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2016-02-23 08:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552910
;


-- 23.02.2016 08:20
-- URL zum Konzept
UPDATE M_Material_Tracking_Report SET IsActive='Y' WHERE IsActive IS NULL
;

-- 23.02.2016 08:20
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2016-02-23 08:20:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552912
;

-- 23.02.2016 08:20
-- URL zum Konzept
UPDATE AD_Element SET Name='Lagerstatistik', PrintName='Lagerstatistik',Updated=TO_TIMESTAMP('2016-02-23 08:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542930
;

-- 23.02.2016 08:20
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542930
;

-- 23.02.2016 08:20
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_Material_Tracking_Report_ID', Name='Lagerstatistik', Description=NULL, Help=NULL WHERE AD_Element_ID=542930
;

-- 23.02.2016 08:20
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Material_Tracking_Report_ID', Name='Lagerstatistik', Description=NULL, Help=NULL, AD_Element_ID=542930 WHERE UPPER(ColumnName)='M_MATERIAL_TRACKING_REPORT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 23.02.2016 08:20
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Material_Tracking_Report_ID', Name='Lagerstatistik', Description=NULL, Help=NULL WHERE AD_Element_ID=542930 AND IsCentrallyMaintained='Y'
;

-- 23.02.2016 08:20
-- URL zum Konzept
UPDATE AD_Field SET Name='Lagerstatistik', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542930) AND IsCentrallyMaintained='Y'
;

-- 23.02.2016 08:20
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Lagerstatistik', Name='Lagerstatistik' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542930)
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_Element SET Name='Lagerstatistik - Zeile', PrintName='Lagerstatistik - Zeile',Updated=TO_TIMESTAMP('2016-02-23 08:21:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542931
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542931
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_Material_Tracking_Report_Line_ID', Name='Lagerstatistik - Zeile', Description=NULL, Help=NULL WHERE AD_Element_ID=542931
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Material_Tracking_Report_Line_ID', Name='Lagerstatistik - Zeile', Description=NULL, Help=NULL, AD_Element_ID=542931 WHERE UPPER(ColumnName)='M_MATERIAL_TRACKING_REPORT_LINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Material_Tracking_Report_Line_ID', Name='Lagerstatistik - Zeile', Description=NULL, Help=NULL WHERE AD_Element_ID=542931 AND IsCentrallyMaintained='Y'
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_Field SET Name='Lagerstatistik - Zeile', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542931) AND IsCentrallyMaintained='Y'
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Lagerstatistik - Zeile', Name='Lagerstatistik - Zeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542931)
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_Element SET Name='Lagerstatistik - Zuordnungszeile', PrintName='Lagerstatistik - Zuordnungszeile',Updated=TO_TIMESTAMP('2016-02-23 08:21:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542932
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542932
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='M_Material_Tracking_Report_Line_Alloc_ID', Name='Lagerstatistik - Zuordnungszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=542932
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Material_Tracking_Report_Line_Alloc_ID', Name='Lagerstatistik - Zuordnungszeile', Description=NULL, Help=NULL, AD_Element_ID=542932 WHERE UPPER(ColumnName)='M_MATERIAL_TRACKING_REPORT_LINE_ALLOC_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='M_Material_Tracking_Report_Line_Alloc_ID', Name='Lagerstatistik - Zuordnungszeile', Description=NULL, Help=NULL WHERE AD_Element_ID=542932 AND IsCentrallyMaintained='Y'
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_Field SET Name='Lagerstatistik - Zuordnungszeile', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542932) AND IsCentrallyMaintained='Y'
;

-- 23.02.2016 08:21
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Lagerstatistik - Zuordnungszeile', Name='Lagerstatistik - Zuordnungszeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542932)
;

-- 23.02.2016 08:22
-- URL zum Konzept
UPDATE AD_Process SET Name='Bereichtszeilen erstellen',Updated=TO_TIMESTAMP('2016-02-23 08:22:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540631
;

-- 23.02.2016 08:22
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540631
;

-- 23.02.2016 08:22
-- URL zum Konzept
UPDATE AD_Process SET Name='Bericht (Jasper)',Updated=TO_TIMESTAMP('2016-02-23 08:22:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540633
;

-- 23.02.2016 08:22
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540633
;

-- 23.02.2016 08:23
-- URL zum Konzept
UPDATE AD_Tab SET MaxQueryRecords=10000,Updated=TO_TIMESTAMP('2016-02-23 08:23:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540714
;

-- 23.02.2016 08:26
-- URL zum Konzept
UPDATE AD_Tab SET Name='Lagerstatistik',Updated=TO_TIMESTAMP('2016-02-23 08:26:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540712
;

-- 23.02.2016 08:26
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540712
;

-- 23.02.2016 08:26
-- URL zum Konzept
UPDATE AD_Tab SET Name='Lagerstatistik - Zeile',Updated=TO_TIMESTAMP('2016-02-23 08:26:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540713
;

-- 23.02.2016 08:26
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540713
;

-- 23.02.2016 08:26
-- URL zum Konzept
UPDATE AD_Tab SET Name='Lagerstatistik - Zuordnungszeile',Updated=TO_TIMESTAMP('2016-02-23 08:26:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540714
;

-- 23.02.2016 08:26
-- URL zum Konzept
UPDATE AD_Tab_Trl SET IsTranslated='N' WHERE AD_Tab_ID=540714
;


-- 23.02.2016 08:30
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2016-02-23 08:30:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556523
;

COMMIT;

-- 23.02.2016 08:16
-- URL zum Konzept
INSERT INTO t_alter_column values('m_material_tracking_report_line_alloc','M_Material_Tracking_Report_Line_ID','NUMERIC(10)',null,null)
;

-- 23.02.2016 08:16
-- URL zum Konzept
INSERT INTO t_alter_column values('m_material_tracking_report_line_alloc','M_Material_Tracking_Report_Line_ID',null,'NOT NULL',null)
;

-- 23.02.2016 08:18
-- URL zum Konzept
INSERT INTO t_alter_column values('m_material_tracking_report_line','IsActive','CHAR(1)',null,'Y')
;

-- 23.02.2016 08:20
-- URL zum Konzept
INSERT INTO t_alter_column values('m_material_tracking_report','IsActive','CHAR(1)',null,'Y')
;
