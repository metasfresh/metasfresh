
-- Column: AD_Tab_Trl.Description
-- 2023-03-24T10:09:18.012Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2023-03-24 12:09:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=267 AND FieldLength < 2000
;


-- 2023-03-24T10:15:10.587Z
INSERT INTO t_alter_column SELECT 'ad_tab_trl','Description','VARCHAR(2000)',null,null WHERE EXISTS (SELECT 1 from AD_Column WHERE AD_Column_ID=267 AND FieldLength=2000)
;



-- Column: AD_Window_Trl.Description
-- 2023-03-24T12:09:19.044Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2023-03-24 14:09:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=308 AND FieldLength < 2000
;

-- 2023-03-24T12:09:19.793Z
INSERT INTO t_alter_column SELECT 'ad_window_trl','Description','VARCHAR(2000)',null,null WHERE EXISTS (SELECT 1 from AD_Column WHERE AD_Column_ID=308 AND FieldLength=2000)
;


-- Column: AD_Menu_Trl.Description
-- 2023-03-24T12:14:54.945Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2023-03-24 14:14:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=248 AND FieldLength < 2000
;

-- 2023-03-24T12:14:55.859Z
INSERT INTO t_alter_column SELECT 'ad_menu_trl','Description','VARCHAR(2000)',null,null WHERE EXISTS (SELECT 1 from AD_Column WHERE AD_Column_ID=248 AND FieldLength = 2000)
;


-- Column: AD_Element.Description
-- 2023-03-24T12:23:06.853Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2023-03-24 14:23:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2604 AND FieldLength < 2000
;

-- 2023-03-24T12:23:07.454Z
INSERT INTO t_alter_column SELECT 'ad_element','Description','VARCHAR(2000)',null,null WHERE EXISTS (SELECT 1 from AD_Column WHERE AD_Column_ID=2604 AND FieldLength = 2000)
;

-- Column: AD_Element_Trl.Description
-- 2023-03-24T12:23:23.267Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2023-03-24 14:23:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2647 AND FieldLength < 2000
;

-- 2023-03-24T12:23:24.483Z
INSERT INTO t_alter_column SELECT 'ad_element_trl','Description','VARCHAR(2000)',null,null WHERE EXISTS (SELECT 1 from AD_Column WHERE AD_Column_ID=2647 AND FieldLength = 2000)
;


-- Column: AD_Process_Para.Description
-- 2023-03-24T12:53:57.005Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2023-03-24 14:53:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2823  AND FieldLength < 2000
;

-- 2023-03-24T12:53:57.837Z
INSERT INTO t_alter_column SELECT 'ad_process_para','Description','VARCHAR(2000)',null,null WHERE EXISTS (SELECT 1 from AD_Column WHERE AD_Column_ID=2823  AND FieldLength = 2000)
;

-- Column: AD_Process_Para_Trl.Description
-- 2023-03-24T12:54:15.081Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2023-03-24 14:54:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2841  AND FieldLength < 2000
;

-- 2023-03-24T12:54:15.771Z
INSERT INTO t_alter_column SELECT 'ad_process_para_trl','Description','VARCHAR(2000)',null,null WHERE EXISTS (SELECT 1 from AD_Column WHERE AD_Column_ID=2841  AND FieldLength = 2000)
;





-- Column: AD_Window.Description
-- 2023-03-24T13:21:43.288Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2023-03-24 15:21:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=157 AND FieldLength < 2000
;

-- 2023-03-24T13:21:44.071Z
INSERT INTO t_alter_column SELECT 'ad_window','Description','VARCHAR(2000)',null,null  WHERE EXISTS (SELECT 1 from AD_Column WHERE AD_Column_ID=157  AND FieldLength = 2000)
;





-- Column: AD_Menu.Description
-- 2023-03-24T13:22:45.611Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2023-03-24 15:22:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=230 AND FieldLength < 2000
;

-- 2023-03-24T13:22:46.251Z
INSERT INTO t_alter_column SELECT 'ad_menu','Description','VARCHAR(2000)',null,null WHERE EXISTS (SELECT 1 from AD_Column WHERE AD_Column_ID=230  AND FieldLength = 2000)
;






-- Column: AD_Tab.Description
-- 2023-03-24T13:06:22.166Z
UPDATE AD_Column SET FieldLength=2000,Updated=TO_TIMESTAMP('2023-03-24 15:06:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=162 AND FieldLength < 2000
;

-- 2023-03-24T13:06:22.915Z
INSERT INTO t_alter_column SELECT 'ad_tab','Description','VARCHAR(2000)',null,null WHERE EXISTS (SELECT 1 from AD_Column WHERE AD_Column_ID=162  AND FieldLength = 2000)
;





-- Element: C_DocType_Invoicing_Pool_ID
-- 2023-03-23T16:28:54.296Z
UPDATE AD_Element_Trl SET Name='Rechnungspool', PrintName='Rechnungspool',Updated=TO_TIMESTAMP('2023-03-23 18:28:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581911 AND AD_Language='de_CH'
;

-- 2023-03-23T16:28:54.312Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581911,'de_CH') 
;

-- Element: C_DocType_Invoicing_Pool_ID
-- 2023-03-23T16:28:58.440Z
UPDATE AD_Element_Trl SET Name='Rechnungspool', PrintName='Rechnungspool',Updated=TO_TIMESTAMP('2023-03-23 18:28:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581911 AND AD_Language='de_DE'
;

-- 2023-03-23T16:28:58.441Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581911,'de_DE') 
;

-- 2023-03-23T16:28:58.451Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581911,'de_DE') 
;

-- 2023-03-23T16:28:58.452Z
UPDATE AD_Column SET ColumnName='C_DocType_Invoicing_Pool_ID', Name='Rechnungspool', Description=NULL, Help=NULL WHERE AD_Element_ID=581911
;

-- 2023-03-23T16:28:58.455Z
UPDATE AD_Process_Para SET ColumnName='C_DocType_Invoicing_Pool_ID', Name='Rechnungspool', Description=NULL, Help=NULL, AD_Element_ID=581911 WHERE UPPER(ColumnName)='C_DOCTYPE_INVOICING_POOL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-23T16:28:58.456Z
UPDATE AD_Process_Para SET ColumnName='C_DocType_Invoicing_Pool_ID', Name='Rechnungspool', Description=NULL, Help=NULL WHERE AD_Element_ID=581911 AND IsCentrallyMaintained='Y'
;

-- 2023-03-23T16:28:58.458Z
UPDATE AD_Field SET Name='Rechnungspool', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581911) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581911)
;

-- 2023-03-23T16:28:58.475Z
UPDATE AD_PrintFormatItem pi SET PrintName='Rechnungspool', Name='Rechnungspool' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581911)
;

-- 2023-03-23T16:28:58.477Z
UPDATE AD_Tab SET Name='Rechnungspool', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581911
;

-- 2023-03-23T16:28:58.479Z
UPDATE AD_WINDOW SET Name='Rechnungspool', Description=NULL, Help=NULL WHERE AD_Element_ID = 581911
;

-- 2023-03-23T16:28:58.481Z
UPDATE AD_Menu SET   Name = 'Rechnungspool', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581911
;

-- Element: C_DocType_Invoicing_Pool_ID
-- 2023-03-23T16:29:34.659Z
UPDATE AD_Element_Trl SET Description='An invoicing pool is used to aggregate invoices and credit memos into a single document. It contains specific document types for aggregating positive invoice amounts (e.g., purchase invoice) and negative amounts (e.g., credit memo).', Help='An invoicing pool is used to aggregate invoices and credit memos into a single document. It contains specific document types for aggregating positive invoice amounts (e.g., purchase invoice) and negative amounts (e.g., credit memo).',Updated=TO_TIMESTAMP('2023-03-23 18:29:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581911 AND AD_Language='en_US'
;

-- 2023-03-23T16:29:34.661Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581911,'en_US') 
;

-- Element: C_DocType_Invoicing_Pool_ID
-- 2023-03-23T16:29:52.336Z
UPDATE AD_Element_Trl SET Description='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).', Help='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).',Updated=TO_TIMESTAMP('2023-03-23 18:29:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581911 AND AD_Language='fr_CH'
;

-- 2023-03-23T16:29:52.338Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581911,'fr_CH') 
;

-- Element: C_DocType_Invoicing_Pool_ID
-- 2023-03-23T16:29:59.325Z
UPDATE AD_Element_Trl SET Description='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).', Help='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).',Updated=TO_TIMESTAMP('2023-03-23 18:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581911 AND AD_Language='de_DE'
;

-- 2023-03-23T16:29:59.327Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581911,'de_DE') 
;

-- 2023-03-23T16:29:59.334Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581911,'de_DE') 
;

-- 2023-03-23T16:29:59.335Z
UPDATE AD_Column SET ColumnName='C_DocType_Invoicing_Pool_ID', Name='Rechnungspool', Description='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).', Help='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).' WHERE AD_Element_ID=581911
;

-- 2023-03-23T16:29:59.337Z
UPDATE AD_Process_Para SET ColumnName='C_DocType_Invoicing_Pool_ID', Name='Rechnungspool', Description='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).', Help='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).', AD_Element_ID=581911 WHERE UPPER(ColumnName)='C_DOCTYPE_INVOICING_POOL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-23T16:29:59.338Z
UPDATE AD_Process_Para SET ColumnName='C_DocType_Invoicing_Pool_ID', Name='Rechnungspool', Description='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).', Help='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).' WHERE AD_Element_ID=581911 AND IsCentrallyMaintained='Y'
;

-- 2023-03-23T16:29:59.339Z
UPDATE AD_Field SET Name='Rechnungspool', Description='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).', Help='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581911) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581911)
;

-- 2023-03-23T16:29:59.356Z
UPDATE AD_Tab SET Name='Rechnungspool', Description='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).', Help='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).', CommitWarning = NULL WHERE AD_Element_ID = 581911
;

-- 2023-03-23T16:29:59.358Z
UPDATE AD_WINDOW SET Name='Rechnungspool', Description='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).', Help='Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).' WHERE AD_Element_ID = 581911
;

-- 2023-03-23T16:29:59.359Z
UPDATE AD_Menu SET   Name = 'Rechnungspool', Description = 'Ein Rechnungspool dient dazu, Rechnungen und Gutschriften in einem einzelnen Beleg zusammenzufassen (Rechnungssammelaufstellung). Der Pool beinhaltet spezifische Belegarten für die Aggregation von positiven Rechnungsbeträgen (z.B. Eingangsrechnung) und negativen Beträgen (z.B. Gutschrift).', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581911
;

-- Element: IsOnDistinctICTypes
-- 2023-03-23T16:30:49.708Z
UPDATE AD_Element_Trl SET Description='If ticked, the invoicing pool will be applied only if the selected invoice candidates have different invoice document types. Otherwise, the document type of the respective invoice candidates applies. If unchecked, the pool will be applied regardless of the document types. If both different document types and pools exist, the respective invoice candidate''s document type will be applied individually.', Help='If ticked, the invoicing pool will be applied only if the selected invoice candidates have different invoice document types. Otherwise, the document type of the respective invoice candidates applies. If unchecked, the pool will be applied regardless of the document types. If both different document types and pools exist, the respective invoice candidate''s document type will be applied individually.', Name='(Apply to) different invoice document types only', PrintName='(Apply to) different invoice document types only',Updated=TO_TIMESTAMP('2023-03-23 18:30:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582147 AND AD_Language='en_US'
;

-- 2023-03-23T16:30:49.710Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582147,'en_US') 
;

-- Element: IsOnDistinctICTypes
-- 2023-03-23T16:31:15.926Z
UPDATE AD_Element_Trl SET Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', PrintName='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)',Updated=TO_TIMESTAMP('2023-03-23 18:31:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582147 AND AD_Language='de_CH'
;

-- 2023-03-23T16:31:15.927Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582147,'de_CH') 
;

-- Element: IsOnDistinctICTypes
-- 2023-03-23T16:31:22.946Z
UPDATE AD_Element_Trl SET Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', PrintName='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)',Updated=TO_TIMESTAMP('2023-03-23 18:31:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582147 AND AD_Language='de_DE'
;

-- 2023-03-23T16:31:22.947Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582147,'de_DE') 
;

-- 2023-03-23T16:31:22.954Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582147,'de_DE') 
;

-- 2023-03-23T16:31:22.955Z
UPDATE AD_Column SET ColumnName='IsOnDistinctICTypes', Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description=NULL, Help=NULL WHERE AD_Element_ID=582147
;

-- 2023-03-23T16:31:22.956Z
UPDATE AD_Process_Para SET ColumnName='IsOnDistinctICTypes', Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description=NULL, Help=NULL, AD_Element_ID=582147 WHERE UPPER(ColumnName)='ISONDISTINCTICTYPES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-23T16:31:22.957Z
UPDATE AD_Process_Para SET ColumnName='IsOnDistinctICTypes', Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description=NULL, Help=NULL WHERE AD_Element_ID=582147 AND IsCentrallyMaintained='Y'
;

-- 2023-03-23T16:31:22.958Z
UPDATE AD_Field SET Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582147) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582147)
;

-- 2023-03-23T16:31:22.975Z
UPDATE AD_PrintFormatItem pi SET PrintName='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=582147)
;

-- 2023-03-23T16:31:22.977Z
UPDATE AD_Tab SET Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582147
;

-- 2023-03-23T16:31:22.979Z
UPDATE AD_WINDOW SET Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description=NULL, Help=NULL WHERE AD_Element_ID = 582147
;

-- 2023-03-23T16:31:22.980Z
UPDATE AD_Menu SET   Name = 'Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582147
;

-- Element: IsOnDistinctICTypes
-- 2023-03-23T16:31:35.415Z
UPDATE AD_Element_Trl SET Description='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.', Help='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.',Updated=TO_TIMESTAMP('2023-03-23 18:31:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582147 AND AD_Language='de_DE'
;

-- 2023-03-23T16:31:35.416Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582147,'de_DE') 
;

-- 2023-03-23T16:31:35.422Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582147,'de_DE') 
;

-- 2023-03-23T16:31:35.423Z
UPDATE AD_Column SET ColumnName='IsOnDistinctICTypes', Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.', Help='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.' WHERE AD_Element_ID=582147
;

-- 2023-03-23T16:31:35.424Z
UPDATE AD_Process_Para SET ColumnName='IsOnDistinctICTypes', Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.', Help='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.', AD_Element_ID=582147 WHERE UPPER(ColumnName)='ISONDISTINCTICTYPES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-23T16:31:35.425Z
UPDATE AD_Process_Para SET ColumnName='IsOnDistinctICTypes', Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.', Help='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.' WHERE AD_Element_ID=582147 AND IsCentrallyMaintained='Y'
;

-- 2023-03-23T16:31:35.427Z
UPDATE AD_Field SET Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.', Help='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582147) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582147)
;

-- 2023-03-23T16:31:35.443Z
UPDATE AD_Tab SET Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.', Help='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.', CommitWarning = NULL WHERE AD_Element_ID = 582147
;

-- 2023-03-23T16:31:35.445Z
UPDATE AD_WINDOW SET Name='Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.', Help='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.' WHERE AD_Element_ID = 582147
;

-- 2023-03-23T16:31:35.446Z
UPDATE AD_Menu SET   Name = 'Nur (auf) unterschiedliche Rechnungsbelegarten (anwenden)', Description = 'Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582147
;

-- Element: IsOnDistinctICTypes
-- 2023-03-23T16:31:43.875Z
UPDATE AD_Element_Trl SET Description='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.', Help='Wenn angekreuzt, wird der Rechnungspool nur angewendet, wenn die ausgewählten Rechnungskandidaten unterschiedliche Rechnungsbelegarten haben. Ansonsten gilt die Belegart der jeweiligen Rechnungskandidaten. Ist das Häkchen nicht gesetzt, wird der Pool unabhängig von den Belegarten angewendet. Wenn sowohl unterschiedliche Belegarten als auch Pools vorhanden sind, wird die Belegart des jeweiligen Rechnungskandidaten einzeln angewendet.',Updated=TO_TIMESTAMP('2023-03-23 18:31:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582147 AND AD_Language='de_CH'
;

-- 2023-03-23T16:31:43.876Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582147,'de_CH') 
;

-- Table: C_DocType_Invoicing_Pool
-- 2023-03-23T16:34:13.714Z
UPDATE AD_Table SET Description='An invoicing pool is used to aggregate invoices and credit memos into a single document. It contains specific document types for aggregating positive invoice amounts (e.g., purchase invoice) and negative amounts (e.g., credit memo).', Help='An invoicing pool is used to aggregate invoices and credit memos into a single document. It contains specific document types for aggregating positive invoice amounts (e.g., purchase invoice) and negative amounts (e.g., credit memo).', TechnicalNote='An invoicing pool is used to aggregate invoices and credit memos into a single document. It contains specific document types for aggregating positive invoice amounts (e.g., purchase invoice) and negative amounts (e.g., credit memo).',Updated=TO_TIMESTAMP('2023-03-23 18:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542277
;

