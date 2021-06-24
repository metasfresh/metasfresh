
UPDATE ad_table
SET tableName='AD_User_Attribute_legacy', technicalnote='metas-ts: This table existed as AD_User_Attribute in instances, but i did not find it''s SQL. ' ||
                                                      'Now there was a collision with 43-de.metas.acct/5592420_sys_gh11268_UserAttributeListAndTab.sql,' ||
                                                      'So I''m renaming it to AD_User_Attribute_legacy.'
  , updatedby=99, updated='2021-06-24 11:57:56.452723 +02:00'
WHERE tablename = 'AD_User_Attribute'
;

ALTER TABLE AD_User_Attribute
    RENAME TO AD_User_Attribute_legacy
;

ALTER TABLE AD_User_Attribute_legacy
    RENAME COLUMN AD_User_Attribute_id TO AD_User_Attribute_legacy_id
;


UPDATE ad_element
SET columnname='AD_User_Attribute_legacy_ID', updatedby=99, updated='2021-06-24 11:57:56.452723 +02:00'
WHERE columnname='AD_User_Attribute_ID';
;

UPDATE ad_column
SET columnname='AD_User_Attribute_legacy_ID', updatedby=99, updated='2021-06-24 11:57:56.452723 +02:00'
WHERE ad_element_id = (select ad_element_id from ad_element where columnname='AD_User_Attribute_legacy_ID');
;

UPDATE AD_Sequence
SET Name='AD_User_Attribute_legacy', description='Table AD_User_Attribute_legacy', updatedby=99, updated='2021-06-24 11:57:56.452723 +02:00'
WHERE Name = 'AD_User_Attribute'
;

ALTER SEQUENCE AD_USER_ATTRIBUTE_SEQ RENAME TO AD_USER_ATTRIBUTE_LEGACY_SEQ;

alter table ad_user_attribute_legacy
    drop constraint ad_user_attribute_key;

alter table ad_user_attribute_legacy
    add constraint ad_user_attribute_legacy_key
        primary key (ad_user_attribute_legacy_id);

