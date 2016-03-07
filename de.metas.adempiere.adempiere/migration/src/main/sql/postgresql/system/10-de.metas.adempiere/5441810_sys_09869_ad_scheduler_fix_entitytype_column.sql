
-- 07.03.2016 14:59
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=1682, ColumnName='EntityType', Description='Dictionary Entity Type; Determines ownership and synchronization', Help='The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!', Name='Entitäts-Art',Updated=TO_TIMESTAMP('2016-03-07 14:59:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554179
;

-- 07.03.2016 14:59
-- URL zum Konzept
UPDATE AD_Field SET Name='Entitäts-Art', Description='Dictionary Entity Type; Determines ownership and synchronization', Help='The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!' WHERE AD_Column_ID=554179 AND IsCentrallyMaintained='Y'
;

