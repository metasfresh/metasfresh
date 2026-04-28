
-- 08.10.2015 08:36
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=542117, ColumnName='IsDirectEnqueue', Description='Entscheidet, ob beim erstellen des DruckstÃ¼cks (Archiv) automatisch eine Druck-Warteschlange-Datensatz erstellt werden soll', EntityType='de.metas.printing', Help=NULL, Name='In Druck-Warteschlange',Updated=TO_TIMESTAMP('2015-10-08 08:36:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548244
;

-- 08.10.2015 08:36
-- URL zum Konzept
UPDATE AD_Column_Trl SET IsTranslated='N' WHERE AD_Column_ID=548244
;

-- 08.10.2015 08:36
-- URL zum Konzept
UPDATE AD_Field SET Name='In Druck-Warteschlange', Description='Entscheidet, ob beim erstellen des DruckstÃ¼cks (Archiv) automatisch eine Druck-Warteschlange-Datensatz erstellt werden soll', Help=NULL WHERE AD_Column_ID=548244 AND IsCentrallyMaintained='Y'
;

-- 08.10.2015 08:36
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-10-08 08:36:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551545
;

-- 08.10.2015 08:36
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-10-08 08:36:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555208
;

COMMIT;

ALTER TABLE ad_archive RENAME isdirectprint  TO IsDirectEnqueue;

