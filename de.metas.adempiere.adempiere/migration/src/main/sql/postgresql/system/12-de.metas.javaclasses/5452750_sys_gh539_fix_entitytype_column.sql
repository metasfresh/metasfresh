
--
-- DDL to replance the not-working AD_EntityType.AD_EntityType_ID reference with an AD_EntityType.EntityType reference
-- includes DML to update columns before setting them to not-null
--
ALTER TABLE AD_JavaClass ADD COLUMN EntityType character varying(40);
UPDATE AD_JavaClass r SET EntityType=(select EntityType from AD_EntityType et where et.AD_EntityType_ID=r.AD_EntityType_ID);
UPDATE AD_JavaClass r SET EntityType='de.metas.procurement' WHERE Name ilike 'de.metas.procurement.%'; -- manual fix for a broken reference
COMMIT;
ALTER TABLE AD_JavaClass ALTER COLUMN EntityType SET NOT NULL;
ALTER TABLE AD_JavaClass DROP COLUMN AD_EntityType_ID;


ALTER TABLE AD_JavaClass_Type ADD COLUMN EntityType character varying(40);
UPDATE AD_JavaClass_Type r SET EntityType=(select EntityType from AD_EntityType et where et.AD_EntityType_ID=r.AD_EntityType_ID);
UPDATE AD_JavaClass_Type r SET EntityType='de.metas.jax.rs' WHERE ClassName='javax.ws.rs.Path'; -- manual fix for a broken reference
COMMIT;
ALTER TABLE AD_JavaClass_Type ALTER COLUMN EntityType SET NOT NULL;
ALTER TABLE AD_JavaClass_Type DROP COLUMN AD_EntityType_ID;


--
-- DML to change the application dictionary accordingly
--
-- 28.10.2016 07:50
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=1682, AD_Reference_ID=18, AD_Reference_Value_ID=389, ColumnName='EntityType', Description='Dictionary Entity Type; Determines ownership and synchronization', Help='The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!', Name='Entitäts-Art',Updated=TO_TIMESTAMP('2016-10-28 07:50:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=57959
;

-- 28.10.2016 07:50
-- URL zum Konzept
UPDATE AD_Field SET Name='Entitäts-Art', Description='Dictionary Entity Type; Determines ownership and synchronization', Help='The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!' WHERE AD_Column_ID=57959 AND IsCentrallyMaintained='Y'
;

-- 28.10.2016 07:50
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=1682, AD_Reference_ID=18, AD_Reference_Value_ID=389, ColumnName='EntityType', Description='Dictionary Entity Type; Determines ownership and synchronization', EntityType='de.metas.javaclasses', Help='The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!', Name='Entitäts-Art',Updated=TO_TIMESTAMP('2016-10-28 07:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549455
;

-- 28.10.2016 07:50
-- URL zum Konzept
UPDATE AD_Field SET Name='Entitäts-Art', Description='Dictionary Entity Type; Determines ownership and synchronization', Help='The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!' WHERE AD_Column_ID=549455 AND IsCentrallyMaintained='Y'
;

-- 28.10.2016 07:50
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=1682, AD_Reference_ID=18, AD_Reference_Value_ID=389, ColumnName='EntityType', Description='Dictionary Entity Type; Determines ownership and synchronization', EntityType='de.metas.javaclasses', Help='The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!', Name='Entitäts-Art',Updated=TO_TIMESTAMP('2016-10-28 07:50:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549456
;

-- 28.10.2016 07:50
-- URL zum Konzept
UPDATE AD_Column_Trl SET IsTranslated='N' WHERE AD_Column_ID=549456
;

-- 28.10.2016 07:50
-- URL zum Konzept
UPDATE AD_Field SET Name='Entitäts-Art', Description='Dictionary Entity Type; Determines ownership and synchronization', Help='The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.  

For customizations, copy the entity and select "User"!' WHERE AD_Column_ID=549456 AND IsCentrallyMaintained='Y'
;
