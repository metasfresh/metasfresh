-- 17.03.2016 22:14
-- URL zum Konzept
UPDATE AD_Field SET SpanX=2,Updated=TO_TIMESTAMP('2016-03-17 22:14:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556766
;

-- 17.03.2016 22:19
-- URL zum Konzept
UPDATE AD_Element SET Description='Determines if the endpoint is started by the JAX-RS framework. Even if that is not the case, an endpoint can be started "programatically" and with non-default paramters, by particualr modules', Name='Endpoint started automatically', PrintName='Endpoint started automatically',Updated=TO_TIMESTAMP('2016-03-17 22:19:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543035
;

-- 17.03.2016 22:19
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543035
;

-- 17.03.2016 22:19
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsEndpointActive', Name='Endpoint started automatically', Description='Determines if the endpoint is started by the JAX-RS framework. Even if that is not the case, an endpoint can be started "programatically" and with non-default paramters, by particualr modules', Help=NULL WHERE AD_Element_ID=543035
;

-- 17.03.2016 22:19
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsEndpointActive', Name='Endpoint started automatically', Description='Determines if the endpoint is started by the JAX-RS framework. Even if that is not the case, an endpoint can be started "programatically" and with non-default paramters, by particualr modules', Help=NULL, AD_Element_ID=543035 WHERE UPPER(ColumnName)='ISENDPOINTACTIVE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 17.03.2016 22:19
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsEndpointActive', Name='Endpoint started automatically', Description='Determines if the endpoint is started by the JAX-RS framework. Even if that is not the case, an endpoint can be started "programatically" and with non-default paramters, by particualr modules', Help=NULL WHERE AD_Element_ID=543035 AND IsCentrallyMaintained='Y'
;

-- 17.03.2016 22:19
-- URL zum Konzept
UPDATE AD_Field SET Name='Endpoint started automatically', Description='Determines if the endpoint is started by the JAX-RS framework. Even if that is not the case, an endpoint can be started "programatically" and with non-default paramters, by particualr modules', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543035) AND IsCentrallyMaintained='Y'
;

-- 17.03.2016 22:19
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Endpoint started automatically', Name='Endpoint started automatically' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543035)
;

