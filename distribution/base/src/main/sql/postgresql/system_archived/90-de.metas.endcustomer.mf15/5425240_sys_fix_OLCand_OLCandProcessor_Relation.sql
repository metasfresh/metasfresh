
-- delete obsolete ones
DELETE FROM AD_Ref_Table WHERE AD_Reference_ID IN (540236,540237);
DELETE FROM AD_Reference WHERE AD_Reference_ID IN (540236,540237);

UPDATE AD_RelationType 
SET InternalName=  'C_OLCandProcessor_1000000<=>C_OLCand'
WHERE InternalName='C_OLCandProcessor_1000003<=>C_OLCand'
;
UPDATE AD_Reference SET Name='RelType_C_OLCandProcessor_1000000'
WHERE  ad_reference_ID=540475;

UPDATE AD_Reference SET Name='RelType_C_OLCand_1000000'
WHERE  ad_reference_ID=540476;

---this reference belong to AD_RelationType with InternaName C_OLCandProcessor_1000003<=>C_OLCand..the olcand was rekeyed to 1000000
--already ok
UPDATE AD_Ref_Table 
SET WhereClause='C_OLCandProcessor_ID=1000000'
WHERE ad_reference_ID=540475
;