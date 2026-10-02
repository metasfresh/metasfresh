-- M_CostRevaluationLine.IsRevaluated: marks a cost revaluation line whose details were already created by "Run Revaluation".
-- The generated model already has this column, but neither the application dictionary nor the database had it.
-- Internal flag, not shown in any window.

INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, AD_Element_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ColumnName, Name, PrintName, Description, EntityType)
VALUES (0, 0, 585499 /*From ID Server*/, 'Y', TO_TIMESTAMP('2026-10-01 12:10:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 12:10:00','YYYY-MM-DD HH24:MI:SS'), 100,
  'IsRevaluated', 'Neubewertet', 'Neubewertet', 'Die Zeile wurde bereits bewertet, ihre Details sind erstellt.', 'D')
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585499
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Revaluated', PrintName='Revaluated', Description='The line was already evaluated and its details are created.',
  Updated=TO_TIMESTAMP('2026-10-01 12:10:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Element_ID=585499
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 12:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE','de_CH') AND AD_Element_ID=585499
;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, AD_Element_ID, AD_Table_ID, AD_Reference_ID, ColumnName, Name, Description, DefaultValue, FieldLength, IsMandatory, IsUpdateable, IsSyncDatabase, EntityType, PersonalDataCategory, Version, Created, CreatedBy, Updated, UpdatedBy)
VALUES (593673 /*From ID Server*/, 0, 0, 585499, 542191, 20, 'IsRevaluated', 'Neubewertet', 'Die Zeile wurde bereits bewertet, ihre Details sind erstellt.', 'N', 1, 'Y', 'Y', 'Y', 'D', 'NP', 0, TO_TIMESTAMP('2026-10-01 12:11:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-01 12:11:00','YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593673
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(585499)
;

-- existing lines get 'N' from the default
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN IsRevaluated CHAR(1) DEFAULT ''N'' CHECK (IsRevaluated IN (''Y'',''N'')) NOT NULL')
;
