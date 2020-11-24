-- 2020-09-17T12:51:11.500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 541175, TO_TIMESTAMP('2020-09-17 15:51:11', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'C_Project_Target_For_C_Invoice', TO_TIMESTAMP('2020-09-17 15:51:11', 'YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;

-- 2020-09-17T12:51:11.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Reference_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Reference_ID = 541175
  AND NOT EXISTS(SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- 2020-09-17T12:51:25.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference
SET EntityType='de.metas.invoice', Updated=TO_TIMESTAMP('2020-09-17 15:51:25', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Reference_ID = 541175
;

-- 2020-09-17T12:55:36.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy, WhereClause)
VALUES (0, 1349, 0, 541175, 203, 130, TO_TIMESTAMP('2020-09-17 15:55:36', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', TO_TIMESTAMP('2020-09-17 15:55:36', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'exists (  select 1  from C_Project p  join C_InvoiceLine iol on iol.C_Project_ID = p.C_Project_ID  join C_Invoice i on iol.C_Invoice_ID = i.C_Invoice_ID    where  i.C_Invoice_ID =@C_Invoice_ID/01@ and C_Project.C_Project_ID = iol.C_Project_ID )')
;

-- 2020-09-17T12:55:49.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, IsActive, IsDirected, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 540670, 541136, 540251, TO_TIMESTAMP('2020-09-17 15:55:49', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', 'M_InOut(SO) -> C_Project', TO_TIMESTAMP('2020-09-17 15:55:49', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-09-17T12:56:20.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_RelationType
WHERE AD_RelationType_ID = 540251
;

-- 2020-09-17T12:56:57.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, IsActive, IsDirected, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 540678, 541175, 540252, TO_TIMESTAMP('2020-09-17 15:56:57', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', 'C_Invoice(SO) -> C_Project', TO_TIMESTAMP('2020-09-17 15:56:57', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-09-17T12:58:48.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 541176, TO_TIMESTAMP('2020-09-17 15:58:48', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'C_Project_Target_For_M_InOut', TO_TIMESTAMP('2020-09-17 15:58:48', 'YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;

-- 2020-09-17T12:58:48.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Reference_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Reference_ID = 541176
  AND NOT EXISTS(SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- 2020-09-17T13:00:26.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy, WhereClause)
VALUES (0, 1349, 0, 541176, 203, 130, TO_TIMESTAMP('2020-09-17 16:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', TO_TIMESTAMP('2020-09-17 16:00:26', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'exists (  select 1  from C_Project p  join M_InOutLine iol on iol.C_Project_ID = p.C_Project_ID  join M_InOut i on iol.M_InOut_ID = i.M_InOut_ID    where  i.M_InOut_ID =@M_InOut_ID/-1@ and C_Project.C_Project_ID = iol.C_Project_ID )')
;

-- 2020-09-17T13:00:32.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table
SET WhereClause='exists (  select 1  from C_Project p  join C_InvoiceLine iol on iol.C_Project_ID = p.C_Project_ID  join C_Invoice i on iol.C_Invoice_ID = i.C_Invoice_ID    where  i.C_Invoice_ID =@C_Invoice_ID/-1@ and C_Project.C_Project_ID = iol.C_Project_ID )', Updated=TO_TIMESTAMP('2020-09-17 16:00:32', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Reference_ID = 541175
;

-- 2020-09-17T13:01:44.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, IsActive, IsDirected, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 540670, 541176, 540253, TO_TIMESTAMP('2020-09-17 16:01:43', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', 'M_InOut (SO) -> C_Project', TO_TIMESTAMP('2020-09-17 16:01:43', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-09-17T13:06:57.399Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 541177, TO_TIMESTAMP('2020-09-17 16:06:57', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'C_Project_Target_For_C_Order', TO_TIMESTAMP('2020-09-17 16:06:57', 'YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;

-- 2020-09-17T13:06:57.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Reference_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Reference_ID = 541177
  AND NOT EXISTS(SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- 2020-09-17T13:08:36.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy, WhereClause)
VALUES (0, 1349, 0, 541177, 203, 143, TO_TIMESTAMP('2020-09-17 16:08:36', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', TO_TIMESTAMP('2020-09-17 16:08:36', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'exists (  select 1  from C_Project p  join C_OrderLine ol on ol.C_Project_ID = p.C_Project_ID  join C_Order o on ol.C_Order_ID = o.C_Order_ID where  o.C_Order_ID =@C_Order_ID/-1@ and C_Project.C_Project_ID = ol.C_Project_ID )')
;

-- 2020-09-17T13:08:42.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table
SET AD_Window_ID=130, Updated=TO_TIMESTAMP('2020-09-17 16:08:42', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Reference_ID = 541177
;

-- 2020-09-17T13:09:12.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, IsActive, IsDirected, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 540666, 541177, 540254, TO_TIMESTAMP('2020-09-17 16:09:12', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', 'C_Order(SO) -> C_Project', TO_TIMESTAMP('2020-09-17 16:09:12', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-09-17T13:14:12.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 541178, TO_TIMESTAMP('2020-09-17 16:14:12', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'C_Order_Target_For_C_Project', TO_TIMESTAMP('2020-09-17 16:14:12', 'YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;

-- 2020-09-17T13:14:12.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Reference_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Reference_ID = 541178
  AND NOT EXISTS(SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- 2020-09-17T13:18:42.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy, WhereClause)
VALUES (0, 2161, 0, 541178, 259, 143, TO_TIMESTAMP('2020-09-17 16:18:42', 'YYYY-MM-DD HH24:MI:SS'), 100, 'U', 'Y', 'N', 'N', TO_TIMESTAMP('2020-09-17 16:18:42', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'exists ( select 1 from C_Order o join C_OrderLine ol on ol.C_Order_ID = o.C_Order_ID join C_Project p on ol.C_Project_ID = p.C_Project_ID where p.C_Project_ID=@C_Project_ID/-1@ and C_Order.C_Order_ID = ol.C_Order_ID)')
;

-- 2020-09-17T13:19:25.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, IsActive, IsDirected, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 541136, 541178, 540255, TO_TIMESTAMP('2020-09-17 16:19:25', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', 'C_Invoice -> Order (SO)', TO_TIMESTAMP('2020-09-17 16:19:25', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-09-17T13:31:42.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType
SET Name='C_Project -> Order (SO)', Updated=TO_TIMESTAMP('2020-09-17 16:31:42', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_RelationType_ID = 540255
;

-- 2020-09-17T13:32:13.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 541180, TO_TIMESTAMP('2020-09-17 16:32:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'M_InOut_Target_For_C_Project', TO_TIMESTAMP('2020-09-17 16:32:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;

-- 2020-09-17T13:32:13.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Reference_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Reference_ID = 541180
  AND NOT EXISTS(SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- 2020-09-17T13:33:42.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy, WhereClause)
VALUES (0, 3521, 0, 541180, 319, 169, TO_TIMESTAMP('2020-09-17 16:33:42', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', TO_TIMESTAMP('2020-09-17 16:33:42', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'exists ( select 1 from M_InOut io join M_InOutLine iol on iol.M_InOut_ID = io.M_InOut_ID join C_Project p on iol.C_Project_ID = p.C_Project_ID where p.C_Project_ID=@C_Project_ID/-1@ and M_InOut.M_InOut_ID = iol.M_InOut_ID)')
;

-- 2020-09-17T13:34:00.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 541181, TO_TIMESTAMP('2020-09-17 16:34:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'C_Invoice_Target_For_C_Project', TO_TIMESTAMP('2020-09-17 16:34:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;

-- 2020-09-17T13:34:00.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Reference_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Reference_ID = 541181
  AND NOT EXISTS(SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- 2020-09-17T13:35:34.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy, WhereClause)
VALUES (0, 3484, 0, 541181, 318, 167, TO_TIMESTAMP('2020-09-17 16:35:34', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', TO_TIMESTAMP('2020-09-17 16:35:34', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'exists ( select 1 from C_Invoice i join C_InvoiceLine il on il.C_Invoice_ID = i.C_Invoice_ID join C_Project p on il.C_Project_ID = p.C_Project_ID where p.C_Project_ID=@C_Project_ID/-1@ and C_Invoice.C_Invoice_ID = il.C_Invoice_ID)')
;

-- 2020-09-17T13:36:20.797Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, IsActive, IsDirected, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 541136, 541180, 540256, TO_TIMESTAMP('2020-09-17 16:36:20', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', 'C_Project -> M_InOut(SO)', TO_TIMESTAMP('2020-09-17 16:36:20', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-09-17T13:36:52.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, IsActive, IsDirected, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 541136, 541181, 540257, TO_TIMESTAMP('2020-09-17 16:36:52', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', 'C_Project -> C_Invoice(SO)', TO_TIMESTAMP('2020-09-17 16:36:52', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-09-17T13:38:43.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table
SET WhereClause='exists ( select 1 from C_Order o join C_OrderLine ol on ol.C_Order_ID = o.C_Order_ID join C_Project p on ol.C_Project_ID = p.C_Project_ID where p.C_Project_ID=@C_Project_ID/-1@ and C_Order.C_Order_ID = ol.C_Order_ID and C_Order.isSOTrx=''Y'')', Updated=TO_TIMESTAMP('2020-09-17 16:38:43', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Reference_ID = 541178
;

-- 2020-09-17T13:41:22.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table
SET WhereClause='exists ( select 1 from C_Order o join C_OrderLine ol on ol.C_Order_ID = o.C_Order_ID join C_Project p on ol.C_Project_ID = p.C_Project_ID where p.C_Project_ID=@C_Project_ID/-1@ and C_Order.C_Order_ID = ol.C_Order_ID and o.isSOTrx=''Y'')', Updated=TO_TIMESTAMP('2020-09-17 16:41:22', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Reference_ID = 541178
;

-- 2020-09-17T13:42:20.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table
SET WhereClause='exists ( select 1 from M_InOut io join M_InOutLine iol on iol.M_InOut_ID = io.M_InOut_ID join C_Project p on iol.C_Project_ID = p.C_Project_ID where p.C_Project_ID=@C_Project_ID/-1@ and M_InOut.M_InOut_ID = iol.M_InOut_ID and io.isSOTrx=''Y'')', Updated=TO_TIMESTAMP('2020-09-17 16:42:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Reference_ID = 541180
;

-- 2020-09-17T13:43:00.362Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table
SET WhereClause='exists ( select 1 from C_Invoice i join C_InvoiceLine il on il.C_Invoice_ID = i.C_Invoice_ID join C_Project p on il.C_Project_ID = p.C_Project_ID where p.C_Project_ID=@C_Project_ID/-1@ and C_Invoice.C_Invoice_ID = il.C_Invoice_ID and i.isSOTrx=''Y'')', Updated=TO_TIMESTAMP('2020-09-17 16:43:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Reference_ID = 541181
;

-- 2020-09-17T13:47:09.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 541182, TO_TIMESTAMP('2020-09-17 16:47:09', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'C_Project_Target_For_Fact_Acct', TO_TIMESTAMP('2020-09-17 16:47:09', 'YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;

-- 2020-09-17T13:47:09.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Reference_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Reference_ID = 541182
  AND NOT EXISTS(SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- 2020-09-17T13:49:15.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy, WhereClause)
VALUES (0, 1349, 0, 541182, 203, 130, TO_TIMESTAMP('2020-09-17 16:49:15', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', TO_TIMESTAMP('2020-09-17 16:49:15', 'YYYY-MM-DD HH24:MI:SS'), 100, 'exists (  select 1  from C_Project p  join Fact_Acct f on f.C_Project_ID = p.C_Project_ID  where  f.Fact_Acct_ID =@Fact_Acct_ID/-1@ and C_Project.C_Project_ID = f.C_Project_ID )')
;

-- 2020-09-17T13:49:55.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, IsActive, IsDirected, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 540773, 541182, 540258, TO_TIMESTAMP('2020-09-17 16:49:55', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', 'Fact_Acct -> C_Project', TO_TIMESTAMP('2020-09-17 16:49:55', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2020-09-17T13:50:35.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 541183, TO_TIMESTAMP('2020-09-17 16:50:35', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'Fact_Acct_Target_For_C_Project', TO_TIMESTAMP('2020-09-17 16:50:35', 'YYYY-MM-DD HH24:MI:SS'), 100, 'T')
;

-- 2020-09-17T13:50:35.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Reference_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Reference_ID = 541183
  AND NOT EXISTS(SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- 2020-09-17T13:52:01.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID, AD_Key, AD_Org_ID, AD_Reference_ID, AD_Table_ID, AD_Window_ID, Created, CreatedBy, EntityType, IsActive, IsValueDisplayed, ShowInactiveValues, Updated, UpdatedBy, WhereClause)
VALUES (0, 3001, 0, 541183, 270, 162, TO_TIMESTAMP('2020-09-17 16:52:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', TO_TIMESTAMP('2020-09-17 16:52:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'exists ( select 1 from Fact_Acct f join C_Project p on f.C_Project_ID = p.C_Project_ID where p.C_Project_ID=@C_Project_ID/-1@ and Fact_Acct.Fact_Acct_ID = f.Fact_Acct_ID)')
;

-- 2020-09-17T13:52:43.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, IsActive, IsDirected, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 540773, 541183, 540259, TO_TIMESTAMP('2020-09-17 16:52:43', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'N', 'C_Project -> Fact_Acct', TO_TIMESTAMP('2020-09-17 16:52:43', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

