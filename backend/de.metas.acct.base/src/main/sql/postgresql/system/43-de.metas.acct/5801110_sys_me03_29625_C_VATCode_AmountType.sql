-- https://github.com/metasfresh/me03/issues/29625
-- Add C_VAT_Code.AmountType CHAR(1) NOT NULL DEFAULT 'T'
-- Values: N=Net (Netto), T=Tax (Steuer)

-- AD_Reference (list container)
INSERT INTO AD_Reference (AD_Client_ID, IsActive, Created, CreatedBy, IsOrderByValue,
  Updated, UpdatedBy, AD_Reference_ID, ValidationType, Name, AD_Org_ID, EntityType)
VALUES (0, 'Y', TO_TIMESTAMP('2026-05-06 12:00:00','YYYY-MM-DD HH24:MI:SS'), 0, 'N',
  TO_TIMESTAMP('2026-05-06 12:00:00','YYYY-MM-DD HH24:MI:SS'), 0,
  542087, 'L', 'C_VAT_Code AmountType', 0, 'de.metas.acct');

-- AD_Reference_Trl propagation
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Help, Name, Description,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Reference_ID, t.Help, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Reference t
WHERE l.IsActive='Y'
  AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N')
  AND t.AD_Reference_ID=542087
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID);

-- AD_Ref_List: N = Net (Netto)
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, EntityType)
VALUES (542087, 0, 'Y',
  TO_TIMESTAMP('2026-05-06 12:00:01','YYYY-MM-DD HH24:MI:SS'), 0,
  'Netto',
  TO_TIMESTAMP('2026-05-06 12:00:01','YYYY-MM-DD HH24:MI:SS'), 0,
  544226, 'Net', 'N', 0, 'de.metas.acct');

-- AD_Ref_List_Trl propagation for N
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y'
  AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N')
  AND t.AD_Ref_List_ID=544226
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

-- English translation for N
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Name='Net',
  Updated=TO_TIMESTAMP('2026-05-06 12:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544226;

-- AD_Ref_List: T = Tax (Steuer)
INSERT INTO AD_Ref_List (AD_Reference_ID, AD_Client_ID, IsActive, Created, CreatedBy,
  Name, Updated, UpdatedBy, AD_Ref_List_ID, ValueName, Value, AD_Org_ID, EntityType)
VALUES (542087, 0, 'Y',
  TO_TIMESTAMP('2026-05-06 12:00:03','YYYY-MM-DD HH24:MI:SS'), 0,
  'Steuer',
  TO_TIMESTAMP('2026-05-06 12:00:03','YYYY-MM-DD HH24:MI:SS'), 0,
  544227, 'Tax', 'T', 0, 'de.metas.acct');

-- AD_Ref_List_Trl propagation for T
INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description,
  IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description,
  'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive='Y'
  AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N')
  AND t.AD_Ref_List_ID=544227
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID);

-- English translation for T
UPDATE AD_Ref_List_Trl
SET IsTranslated='Y', Name='Tax',
  Updated=TO_TIMESTAMP('2026-05-06 12:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=0
WHERE AD_Language='en_US' AND AD_Ref_List_ID=544227;

-- AD_Column for C_VAT_Code.AmountType (using existing AD_Element_ID 1602)
INSERT INTO AD_Column (
    AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID,
    AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID,
    ColumnName, Created, CreatedBy, EntityType, FieldLength,
    IsActive, IsAlwaysUpdateable, IsEncrypted, IsIdentifier, IsKey,
    IsMandatory, IsParent, IsSelectionColumn, IsSyncDatabase, IsTranslated, IsUpdateable,
    Name, DefaultValue, SeqNo, Updated, UpdatedBy, Version
) VALUES (
    0, 592499, 1602, 0,
    17, 542087, 540703,
    'AmountType', TO_TIMESTAMP('2026-05-06 12:00:06','YYYY-MM-DD HH24:MI:SS'), 0, 'de.metas.acct', 1,
    'Y', 'N', 'N', 'N', 'N',
    'Y', 'N', 'N', 'Y', 'N', 'Y',
    'Betragsart', 'T', 0,
    TO_TIMESTAMP('2026-05-06 12:00:06','YYYY-MM-DD HH24:MI:SS'), 0, 0
);

-- AD_Column_Trl propagation
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID,
  Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
  t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND t.AD_Column_ID=592499
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
    WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID);

-- DDL: add AmountType column to C_VAT_Code (existing rows get DEFAULT 'T')
/* DDL */ SELECT public.db_alter_table('C_VAT_Code', 'ALTER TABLE public.C_VAT_Code ADD COLUMN AmountType CHAR(1) NOT NULL DEFAULT ''T''');

-- Named check constraint (per metasfresh review rule: <ColumnName>_Check)
/* DDL */ SELECT public.db_alter_table('C_VAT_Code', 'ALTER TABLE public.C_VAT_Code ADD CONSTRAINT AmountType_Check CHECK (AmountType IN (''N'',''T''))');
