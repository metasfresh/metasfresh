-- 2021-03-29T13:40:35.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ReadOnlyLogic='@IsManualPrice@=N', Updated=TO_TIMESTAMP('2021-03-29 16:40:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573203
;

-- 2021-03-29T13:47:59.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsReadOnly='N', Updated=TO_TIMESTAMP('2021-03-29 16:47:59', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 640705
;

-- 2021-03-29T15:03:37.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET ReadOnlyLogic='', Updated=TO_TIMESTAMP('2021-03-29 18:03:37', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573203
;

-- 2021-03-29T15:24:44.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID, AD_Index_Table_ID, AD_Org_ID, AD_Table_ID, Created, CreatedBy, EntityType, IsActive, IsUnique, Name, Processing, Updated, UpdatedBy)
VALUES (0, 540585, 0, 540861, TO_TIMESTAMP('2021-03-29 18:24:43', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 'N', 'C_PurchaseCandidate_External_header_line', 'N', TO_TIMESTAMP('2021-03-29 18:24:43', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-03-29T15:24:44.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language, AD_Index_Table_ID, ErrorMsg, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Index_Table_ID,
       t.ErrorMsg,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     AD_Index_Table t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Index_Table_ID = 540585
  AND NOT EXISTS(SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Index_Table_ID = t.AD_Index_Table_ID)
;

-- 2021-03-29T15:25:10.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID, AD_Column_ID, AD_Index_Column_ID, AD_Index_Table_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 573209, 541082, 540585, 0, TO_TIMESTAMP('2021-03-29 18:25:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y', 10, TO_TIMESTAMP('2021-03-29 18:25:10', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-03-29T15:25:16.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID, AD_Column_ID, AD_Index_Column_ID, AD_Index_Table_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 573210, 541083, 540585, 0, TO_TIMESTAMP('2021-03-29 18:25:16', 'YYYY-MM-DD HH24:MI:SS'), 100, 'U', 'Y', 20, TO_TIMESTAMP('2021-03-29 18:25:16', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-03-29T15:25:26.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID, AD_Column_ID, AD_Index_Column_ID, AD_Index_Table_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, SeqNo, Updated, UpdatedBy)
VALUES (0, 557851, 541084, 540585, 0, TO_TIMESTAMP('2021-03-29 18:25:26', 'YYYY-MM-DD HH24:MI:SS'), 100, 'U', 'Y', 30, TO_TIMESTAMP('2021-03-29 18:25:26', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-03-29T15:25:30.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column
SET EntityType='D', Updated=TO_TIMESTAMP('2021-03-29 18:25:30', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Index_Column_ID = 541083
;

-- 2021-03-29T15:25:35.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column
SET EntityType='D', Updated=TO_TIMESTAMP('2021-03-29 18:25:35', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Index_Column_ID = 541084
;

-- 2021-03-29T16:06:40.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table
SET IsUnique='Y', Updated=TO_TIMESTAMP('2021-03-29 19:06:40', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Index_Table_ID = 540585
;

-- 2021-03-29T16:10:22.955Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table
SET WhereClause='IsActive=''Y'' AND COALESCE(ExternalHeaderId, '''') != ''''  AND COALESCE(ExternalLineId, '''') != ''''', Updated=TO_TIMESTAMP('2021-03-29 19:10:22', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Index_Table_ID = 540585
;

-- 2021-03-29T16:14:36.181Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS C_PurchaseCandidate_External_header_line
;

-- 2021-03-29T16:14:36.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX C_PurchaseCandidate_External_header_line ON C_PurchaseCandidate (ExternalHeaderId, ExternalLineId, AD_Org_ID) WHERE IsActive = 'Y' AND COALESCE(ExternalHeaderId, '') != '' AND COALESCE(ExternalLineId, '') != ''
;

-- 2021-03-30T08:12:12.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsAlwaysUpdateable='N', Updated=TO_TIMESTAMP('2021-03-30 11:12:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573199
;

-- 2021-03-30T08:13:21.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsUpdateable='N', Updated=TO_TIMESTAMP('2021-03-30 11:13:21', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573200
;

-- 2021-03-30T08:14:26.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsUpdateable='N', Updated=TO_TIMESTAMP('2021-03-30 11:14:26', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573206
;

-- 2021-03-30T08:15:03.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column
VALUES ('c_purchasecandidate', 'DiscountInternal', 'NUMERIC', NULL, NULL)
;

-- 2021-03-30T08:16:56.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-03-30 11:16:56', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 640714
;

-- 2021-03-30T08:17:46.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-03-30 11:17:46', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 640712
;

-- 2021-03-30T08:36:17.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET AD_Element_ID=1123, ColumnName='PriceEffective', Description='Datum zu dem dieser Preis gültig wird', Help='The Price Effective indicates the date this price is for. This allows you to enter future prices for products which will become effective when appropriate.', Name='Preis gültig', Updated=TO_TIMESTAMP('2021-03-30 11:36:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573203
;

-- 2021-03-30T08:36:17.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET Name='Preis gültig', Description='Datum zu dem dieser Preis gültig wird', Help='The Price Effective indicates the date this price is for. This allows you to enter future prices for products which will become effective when appropriate.'
WHERE AD_Column_ID = 573203
;

-- 2021-03-30T08:36:17.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_Column_Translation_From_AD_Element(1123)
;

-- 2021-03-30T08:36:20.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT public.db_alter_table('C_PurchaseCandidate', 'ALTER TABLE public.C_PurchaseCandidate ADD COLUMN PriceEffective NUMERIC')
;

-- 2021-03-30T08:51:43.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsUpdateable='N', Updated=TO_TIMESTAMP('2021-03-30 11:51:43', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573203
;

-- 2021-03-30T08:53:12.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-03-30 11:53:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 640706
;

-- 2021-03-30T08:53:21.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-03-30 11:53:21', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 640715
;

-- 2021-03-30T08:54:11.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column
SET IsUpdateable='N', Updated=TO_TIMESTAMP('2021-03-30 11:54:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID = 573208
;

-- 2021-03-30T09:01:08.243Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 582147
;

-- 2021-03-30T09:01:08.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 640706
;

-- 2021-03-30T09:01:08.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 640706
;

-- 2021-03-30T09:01:08.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Field
WHERE AD_Field_ID = 640706
;

-- 2021-03-30T09:01:26.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, Description, DisplayLength, EntityType, Help, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsReadOnly, IsSameLine, Name, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 573203, 641301, 0, 540894, 0, TO_TIMESTAMP('2021-03-30 12:01:26', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Datum zu dem dieser Preis gültig wird', 0, 'D', 'The Price Effective indicates the date this price is for. This allows you to enter future prices for products which will become effective when appropriate.', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Preis gültig', 290, 300, 0, 1, 1,
        TO_TIMESTAMP('2021-03-30 12:01:26', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-03-30T09:01:26.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.AD_Field_ID,
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
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y')
  AND t.AD_Field_ID = 641301
  AND NOT EXISTS(SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2021-03-30T09:01:26.551Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(1123)
;

-- 2021-03-30T09:01:26.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 641301
;

-- 2021-03-30T09:01:26.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(641301)
;

-- 2021-03-30T09:02:01.359Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, Description, Help, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 641301, 0, 540894, 545480, 582475, 'F', TO_TIMESTAMP('2021-03-30 12:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Datum zu dem dieser Preis gültig wird', 'The Price Effective indicates the date this price is for. This allows you to enter future prices for products which will become effective when appropriate.', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Preis gültig', 30, 0, 0,
        TO_TIMESTAMP('2021-03-30 12:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- 2021-03-30T09:02:53.401Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field
SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2021-03-30 12:02:53', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 641301
;
