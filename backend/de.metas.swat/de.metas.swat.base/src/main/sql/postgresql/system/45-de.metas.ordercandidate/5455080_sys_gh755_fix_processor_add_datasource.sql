
-- choose this form for the insert in case this ad_inputdatasource was already added.
INSERT INTO ad_inputdatasource (ad_client_id, ad_inputdatasource_id, ad_org_id, created, createdby, description, isactive, m_product_id, name, updated, updatedby, internalname, a_asset_id, isdestination, entitytype, isedienabled) 
SELECT
0, 170, 0, now(), 100, 'vgl. https://github.com/metasfresh/metasfresh/issues/755' , 'Y', NULL, 'Importiert via SQL COPY Befehl', now(), 100, 'SOURCE.SQL-COPY', NULL, 'N', 'de.metas.ordercandidate', 'N'
WHERE NOT EXISTS (select 1 from ad_inputdatasource where ad_inputdatasource_ID=170);

-- to allow postgres to pick a correct C_OLCand_ID
ALTER TABLE c_olcand ALTER COLUMN c_olcand_id SET DEFAULT nextval('c_olcand_seq');

UPDATE AD_RelationType SET InternalName='C_OLCandProcessor_1000003<=>C_OLCand' WHERE InternalName='C_OLCandProcessor_1000000<=>C_OLCand';
UPDATE AD_Reference SET Name='RelType_C_OLCandProcessor_1000003' WHERE AD_Reference_ID=540475;
UPDATE AD_Ref_Table SET WhereClause='C_OLCandProcessor_ID=1000003' WHERE AD_Reference_ID=540475;

UPDATE AD_Reference SET Name='RelType_C_OLCand_1000003' WHERE AD_reference_ID=540476;
UPDATE AD_Ref_Table SET WhereClause='C_OLCand.AD_InputDataSource_ID IN (150,160,170) AND C_OLCand.Processed=''N'' AND C_OLCand.IsActive=''Y'' AND C_OLCand.IsError=''N'' AND C_OLCand.IsImportedWithIssues=''N''' where AD_reference_ID=540476;

UPDATE C_BPartner SET IsEmployee='Y' WHERE C_BPartner_ID=2155894; -- this happens to be the ID of our "own" BPartner, i.e. the one that is per default associated with our org

UPDATE AD_Scheduler SET CreatedBy=0 WHERE AD_Scheduler_ID=550015;
