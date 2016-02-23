
UPDATE AD_ModelValidator
SET ModelValidationClass=replace(ModelValidationClass, 'org.adempiere.product', 'de.metas.product')
WHERE ModelValidationClass ilike 'org.adempiere.product%'
;



UPDATE AD_ColumnCallout
SET Classname=replace(ClassName, 'org.adempiere.order', 'de.metas.order')
WHERE ClassName ilike 'org.adempiere.order%'
;
UPDATE AD_ColumnCallout
SET Classname=replace(ClassName, 'org.adempiere.document', 'de.metas.document')
WHERE ClassName ilike 'org.adempiere.document%'
;

UPDATE C_Queue_PackageProcessor
SET ClassName=replace(ClassName, 'org.adempiere.product', 'de.metas.product')
WHERE ClassName ilike 'org.adempiere.product%';

