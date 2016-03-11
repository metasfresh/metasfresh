--
-- processed=Y means that we can't edit and flatrate term or flatrate dataentry. That's not what we wanted,
-- so we replace "processed" with "hasContracts"
--



-- Mar 9, 2016 10:31 AM
-- URL zum Konzept
ALTER TABLE C_Flatrate_Data ADD HasContracts CHAR(1) DEFAULT 'N' CHECK (HasContracts IN ('Y','N')) NOT NULL
;

COMMIT;


-- Mar 9, 2016 10:29 AM
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543036,0,'HasContracts',TO_TIMESTAMP('2016-03-09 10:29:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.flatrate','Y','Existierende Verträge','Existierende Verträge',TO_TIMESTAMP('2016-03-09 10:29:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Mar 9, 2016 10:29 AM
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543036 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Mar 9, 2016 10:29 AM
-- URL zum Konzept
UPDATE AD_Column SET AD_Element_ID=543036, ColumnName='HasContracts', Description=NULL, Help=NULL, Name='Existierende Verträge',Updated=TO_TIMESTAMP('2016-03-09 10:29:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545851
;

-- Mar 9, 2016 10:29 AM
-- URL zum Konzept
UPDATE AD_Column_Trl SET IsTranslated='N' WHERE AD_Column_ID=545851
;

-- Mar 9, 2016 10:29 AM
-- URL zum Konzept
UPDATE AD_Field SET Name='Existierende Verträge', Description=NULL, Help=NULL WHERE AD_Column_ID=545851 AND IsCentrallyMaintained='Y'
;

-- Mar 9, 2016 10:29 AM
-- URL zum Konzept
UPDATE AD_Column SET ReadOnlyLogic='@HasContracts/N@=''N''',Updated=TO_TIMESTAMP('2016-03-09 10:29:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=545641
;

UPDATE C_Flatrate_Data SET HasContracts=Processed;

COMMIT;

ALTER TABLE C_Flatrate_Data DROP Processed;
